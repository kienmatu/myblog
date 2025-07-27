package com.kiendt.myblog.controller;

import com.kiendt.myblog.dto.ArticleDto;
import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testArticleDetailWhenSlugIsNullThenReturnErrorView() {
        ModelAndView result = articleController.articleDetail(null);

        assertEquals("error", result.getViewName());
        assertEquals("Article not found", result.getModel().get("message"));
    }

    @Test
    void testArticleDetailWhenSlugIsEmptyThenReturnErrorView() {
        ModelAndView result = articleController.articleDetail("");

        assertEquals("error", result.getViewName());
        assertEquals("Article not found", result.getModel().get("message"));
    }

    @Test
    void testArticleDetailWhenArticleNotFoundThenReturnErrorView() {
        String slug = "non-existent-article";

        when(articleService.getArticleBySlug(slug)).thenReturn(Optional.empty());

        ModelAndView result = articleController.articleDetail(slug);

        assertEquals("error", result.getViewName());
        assertEquals("Article not found", result.getModel().get("message"));
        verify(articleService, times(1)).getArticleBySlug(slug);
    }

    @Test
    void testArticleDetailWhenArticleFoundThenIncreaseArticleView() {
        String slug = "test-article";
        Article article = Article.builder()
                .id(1L)
                .slug(slug)
                .title("Test Article")
                .content("This is a test article.")
                .excerpt("This is a test article.")
                .views(0)
                .build();
        ArticleDto articleDto = ArticleDto.from(article);

        when(articleService.getArticleBySlug(slug)).thenReturn(Optional.of(article));
        when(articleService.increaseArticleViews(article.getId())).thenReturn(true);

        ModelAndView result = articleController.articleDetail(slug);

        assertEquals("article", result.getViewName());
        ArticleDto resultDto = (ArticleDto) result.getModel().get("article");
        assertNotNull(resultDto);
        assertEquals(articleDto.getId(), resultDto.getId());
        assertEquals(articleDto.getSlug(), resultDto.getSlug());
        assertEquals(articleDto.getTitle(), resultDto.getTitle());
        assertEquals(articleDto.getContent(), resultDto.getContent());
        assertEquals(articleDto.getContent(), resultDto.getExcerpt());
        verify(articleService, times(1)).getArticleBySlug(slug);
        verify(articleService, times(1)).increaseArticleViews(article.getId());
    }
}