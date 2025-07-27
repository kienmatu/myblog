package com.kiendt.myblog.controller;

import com.kiendt.myblog.dto.ArticleDto;
import com.kiendt.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Article controller.
 * This class is responsible for handling article-related requests
 * and returning the appropriate views.
 */
@Controller
public class ArticleController {
    private final ArticleService articleService;

    /**
     * Constructor for HomeController.
     *
     * @param articleService the article service
     */
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * The index page.
     *
     * @return the model and view
     */
    @RequestMapping("/articles/{slug}")
    public ModelAndView articleDetail(@PathVariable String slug) {
        if (slug == null || slug.isEmpty()) {
            return new ModelAndView("error")
                    .addObject("message", "Article not found");
        }
        var optionalArticleDto = articleService
                .getArticleBySlug(slug)
                .map(ArticleDto::from);

        if (optionalArticleDto.isEmpty()) {
            return new ModelAndView("error")
                    .addObject("message", "Article not found");
        }

        // increment view count
        articleService.increaseArticleViews(optionalArticleDto.get().getId());

        var article = optionalArticleDto.get();
        return new ModelAndView("article")
                .addObject("article", article);
    }

    @GetMapping("/api/articles/{slug}")
    @ResponseBody
    public ResponseEntity<?> getArticleAjax(@PathVariable String slug) {
        if (slug == null || slug.isEmpty()) {
            return ResponseEntity.badRequest().body("Article not found");
        }
        var optionalArticleDto = articleService
                .getArticleBySlug(slug)
                .map(ArticleDto::from);

        if (optionalArticleDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }

        // Optionally increment view count here if needed
        articleService.increaseArticleViews(optionalArticleDto.get().getId());

        return ResponseEntity.ok(optionalArticleDto.get());
    }

}
