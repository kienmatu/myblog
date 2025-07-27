package com.kiendt.myblog.controller;

import com.kiendt.myblog.dto.ArticleDto;
import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private HomeController homeController;

    private List<Article> mockArticles;
    private Date date1;
    private Date date2;

    @BeforeEach
    void setUp() {
        // Create test dates
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-15");
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-20");
        } catch (Exception e) {
            throw new RuntimeException("Error creating test dates", e);
        }

        // Prepare test articles
        mockArticles = List.of(
                Article.builder()
                        .id(1L)
                        .slug("first-article")
                        .title("First Article")
                        .excerpt("This is the first article")
                        .content("Content of first article")
                        .image("image1.jpg")
                        .publishDate(date1)
                        .views(100)
                        .build(),
                Article.builder()
                        .id(2L)
                        .slug("second-article")
                        .title("Second Article")
                        .excerpt("This is the second article")
                        .content("Content of second article")
                        .image("image2.jpg")
                        .publishDate(date2)
                        .views(200)
                        .build(),
                Article.builder()
                        .id(3L)
                        .slug("third-article")
                        .title("Third Article")
                        .excerpt("This is the third article")
                        .content("Content of third article")
                        .image("image3.jpg")
                        .publishDate(date2)
                        .views(150)
                        .build()
        );
    }

    @Test
    void testIndexGivenValidRequestWhenCalledThenReturnsModelAndViewWithRecentArticles() {
        // Given
        when(articleService.getRecentArticles(3)).thenReturn(mockArticles);

        // When
        ModelAndView modelAndView = homeController.index();

        // Then
        assertEquals("index", modelAndView.getViewName());
        assertTrue(modelAndView.getModel().containsKey("articles"));

        @SuppressWarnings("unchecked")
        List<ArticleDto> articles = (List<ArticleDto>) modelAndView.getModel().get("articles");

        assertEquals(3, articles.size());
        assertEquals(1L, articles.get(0).getId());
        assertEquals("first-article", articles.get(0).getSlug());
        assertEquals("15-01-2023", articles.get(0).getPublishDate());

        verify(articleService, times(1)).getRecentArticles(3);
    }

    @Test
    void testOverviewGivenValidRequestWhenCalledThenReturnsModelAndViewWithGroupedArticles() {
        // Given
        when(articleService.getAllArticles()).thenReturn(mockArticles);

        // When
        ModelAndView modelAndView = homeController.overview();

        // Then
        assertEquals("overview", modelAndView.getViewName());
        assertTrue(modelAndView.getModel().containsKey("groupedArticles"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> groupedArticles = (List<Map<String, Object>>) modelAndView.getModel().get("groupedArticles");

        assertEquals(2, groupedArticles.size());

        // Find January and February groups
        Map<String, Object> januaryGroup = null;
        Map<String, Object> februaryGroup = null;

        for (Map<String, Object> group : groupedArticles) {
            String month = (String) group.get("month");
            if (month.equals("January 2023")) {
                januaryGroup = group;
            } else if (month.equals("February 2023")) {
                februaryGroup = group;
            }
        }

        // Verify January group
        assertNotNull(januaryGroup);
        @SuppressWarnings("unchecked")
        List<ArticleDto> januaryArticles = (List<ArticleDto>) januaryGroup.get("articles");
        assertEquals(1, januaryArticles.size());
        assertEquals("First Article", januaryArticles.get(0).getTitle());

        // Verify February group
        assertNotNull(februaryGroup);
        @SuppressWarnings("unchecked")
        List<ArticleDto> februaryArticles = (List<ArticleDto>) februaryGroup.get("articles");
        assertEquals(2, februaryArticles.size());

        verify(articleService, times(1)).getAllArticles();
    }

    @Test
    void testIndexGivenNoArticlesWhenCalledThenReturnsEmptyList() {
        // Given
        when(articleService.getRecentArticles(3)).thenReturn(List.of());

        // When
        ModelAndView modelAndView = homeController.index();

        // Then
        assertEquals("index", modelAndView.getViewName());
        @SuppressWarnings("unchecked")
        List<ArticleDto> articles = (List<ArticleDto>) modelAndView.getModel().get("articles");
        assertTrue(articles.isEmpty());

        verify(articleService, times(1)).getRecentArticles(3);
    }

    @Test
    void testOverviewGivenNoArticlesWhenCalledThenReturnsEmptyGroupedArticles() {
        // Given
        when(articleService.getAllArticles()).thenReturn(List.of());

        // When
        ModelAndView modelAndView = homeController.overview();

        // Then
        assertEquals("overview", modelAndView.getViewName());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> groupedArticles = (List<Map<String, Object>>) modelAndView.getModel().get("groupedArticles");
        assertTrue(groupedArticles.isEmpty());

        verify(articleService, times(1)).getAllArticles();
    }
}