package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.model.ArticleType;
import com.kiendt.myblog.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetArticleWhenSlugExistsThenReturnArticle() {
        String slug = "test-article";
        Article article = new Article();
        article.setSlug(slug);

        when(articleRepository.findBySlug(slug)).thenReturn(Optional.of(article));

        Optional<Article> result = articleService.getArticleBySlug(slug);

        assertTrue(result.isPresent());
        assertEquals(slug, result.get().getSlug());
        verify(articleRepository, times(1)).findBySlug(slug);
    }

    @Test
    void testGetArticleWhenSlugDoesNotExistThenReturnEmpty() {
        String slug = "non-existent-article";

        when(articleRepository.findBySlug(slug)).thenReturn(Optional.empty());

        Optional<Article> result = articleService.getArticleBySlug(slug);

        assertFalse(result.isPresent());
        verify(articleRepository, times(1)).findBySlug(slug);
    }

    @Test
    void testGetAllArticlesWhenCalledThenReturnSortedArticles() {
        Article article1 = new Article();
        Article article2 = new Article();
        List<Article> articles = List.of(article1, article2);

        when(articleRepository.findByArticleType(eq(ArticleType.ARTICLE), any(Sort.class))).thenReturn(articles);

        List<Article> result = articleService.getAllArticles();

        assertEquals(2, result.size());
        verify(articleRepository, times(1)).findByArticleType(eq(ArticleType.ARTICLE), any(Sort.class));
    }

    @Test
    void testGetRecentArticlesWhenCalledThenReturnLimitedArticles() {
        int count = 2;
        Article article1 = new Article();
        Article article2 = new Article();
        Page<Article> page = new PageImpl<>(List.of(article1, article2));

        when(articleRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Article> result = articleService.getRecentArticles(count);

        assertEquals(2, result.size());
        verify(articleRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testIncreaseArticleViewsWhenArticleExistsThenIncrementViews() {
        Long id = 1L;
        Article article = new Article();
        article.setViews(5);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        boolean result = articleService.increaseArticleViews(id);

        assertTrue(result);
        assertEquals(6, article.getViews());
        verify(articleRepository, times(1)).findById(id);
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testIncreaseArticleViewsWhenArticleDoesNotExistThenReturnFalse() {
        Long id = 1L;

        when(articleRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = articleService.increaseArticleViews(id);

        assertFalse(result);
        verify(articleRepository, times(1)).findById(id);
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testGetAllViewsCountWhenCalledThenReturnSumOfViews() {
        when(articleRepository.sumAllViews()).thenReturn(Optional.of(100));

        int result = articleService.getAllViewsCount();

        assertEquals(100, result);
        verify(articleRepository, times(1)).sumAllViews();
    }

    @Test
    void testGetAllViewsCountWhenNoViewsThenReturnZero() {
        when(articleRepository.sumAllViews()).thenReturn(Optional.empty());

        int result = articleService.getAllViewsCount();

        assertEquals(0, result);
        verify(articleRepository, times(1)).sumAllViews();
    }
}