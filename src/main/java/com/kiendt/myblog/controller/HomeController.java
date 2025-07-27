package com.kiendt.myblog.controller;

import com.kiendt.myblog.dto.ArticleDto;
import com.kiendt.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Home controller.
 * This class is responsible for handling requests to the home page
 * and returning the appropriate views.
 */
@Controller
public class HomeController {
    private final static int RECENT_ARTICLES_COUNT = 3;
    private final ArticleService articleService;

    /**
     * Constructor for HomeController.
     *
     * @param articleService the article service
     */
    @Autowired
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * The index page.
     *
     * @return the model and view
     */
    @RequestMapping("/")
    public ModelAndView index() {
        var recentArticles = articleService
                .getRecentArticles(RECENT_ARTICLES_COUNT)
                .stream()
                .map(ArticleDto::from)
                .toList();


        return new ModelAndView("index")
                .addObject("articles", recentArticles);
    }

    /**
     * The overview page.
     *
     * @return the model and view
     */
    @RequestMapping("/overview")
    public ModelAndView overview() {
        String monthFormat = "MMMM yyyy";

        // Group articles by month
        var allArticles = articleService
                .getAllArticles()
                .stream()
                .map(ArticleDto::from)
                .collect(Collectors.groupingBy(
                        article -> new SimpleDateFormat(monthFormat)
                                .format(article.getOriginalPublishDate())
                ));

        // Prepare the data for the view
        var groupedArticles = allArticles.entrySet().stream()
                .map(entry -> Map.of(
                        "month", entry.getKey(),
                        "articles", entry.getValue())
                )
                .toList();

        return new ModelAndView("overview")
                .addObject("groupedArticles", groupedArticles);
    }

}
