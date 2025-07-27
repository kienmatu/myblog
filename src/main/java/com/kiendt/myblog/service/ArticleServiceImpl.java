package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.model.ArticleType;
import com.kiendt.myblog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing articles.
 */
@Component
@Service
public class ArticleServiceImpl implements ArticleService {

    /**
     * The ArticleRepository instance used to interact with the database.
     */
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Retrieve an article by its slug.
     *
     * @param slug the unique identifier for the article
     * @return an Optional containing the article if found, or empty if not found
     */
    @Override
    public Optional<Article> getArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }

    /**
     * Retrieve all articles of type ARTICLE, sorted by publish date in descending order.
     *
     * @return a list of articles
     */
    @Override
    public List<Article> getAllArticles() {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        return articleRepository.findByArticleType(ArticleType.ARTICLE, sort);
    }

    /**
     * Retrieve a specified number of the most recent articles, sorted by publish date in descending order.
     *
     * @param count the number of recent articles to retrieve
     * @return a list of recent articles
     */
    @Override
    public List<Article> getRecentArticles(Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "publishDate"));
        Page<Article> page = articleRepository.findAll(pageable);
        return page.getContent();
    }

    @Override
    public boolean increaseArticleViews(Long id) {
        var article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setViews(article.get().getViews() + 1);
            articleRepository.save(article.get());
            return true;
        }
        return false;
    }

    /**
     * Get the total number of views for all articles.
     *
     * @return the total number of views
     */
    @Override
    public int getAllViewsCount() {
        return articleRepository.sumAllViews().orElse(0);
    }
}
