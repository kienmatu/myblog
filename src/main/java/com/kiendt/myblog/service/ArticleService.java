package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Article;

import java.util.List;
import java.util.Optional;

/**
 * Article service interface.
 * This interface defines the methods for managing articles.
 */
public interface ArticleService {

    /**
     * Retrieve an article by its slug.
     *
     * @param slug the slug of the article
     * @return an Optional containing the article if found, or an empty Optional if not found
     */
    Optional<Article> getArticleBySlug(String slug);

    /**
     * Get all articles.
     *
     * @return a list of all articles
     */
    List<Article> getAllArticles();

    /**
     * Get a list of the most recent articles.
     *
     * @param count the number of recent articles to retrieve
     * @return a list of the most recent articles
     */
    List<Article> getRecentArticles(Integer count);

    /**
     * Increase the view count of an article.
     *
     * @param id the ID of the article
     * @return true if the view count was successfully increased, false otherwise
     */
    boolean increaseArticleViews(Long id);

    /**
     * Get the total number of views for all articles.
     *
     * @return the total number of views
     */
    int getAllViewsCount();
}
