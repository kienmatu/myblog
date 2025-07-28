package com.kiendt.myblog.repository;

import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.model.ArticleType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing articles.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 * Readme: <a href="https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html">...</a>
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    /**
     * Get the total number of views for all articles.
     *
     * @return the total number of views
     */
    @Query("SELECT COALESCE(SUM(a.views), 0) FROM Article a")
    Optional<Integer> sumAllViews();

    /**
     * Find all articles by their type.
     *
     * @param articleType the type of the article
     * @param sort        the sorting criteria
     * @return a list of articles of the specified type
     */
    List<Article> findByArticleType(ArticleType articleType, Sort sort);

    /**
     * Find an article by its slug.
     *
     * @param slug the slug of the article
     * @return an optional containing the article if found, or empty if not found
     */
    Optional<Article> findBySlug(String slug);
}
