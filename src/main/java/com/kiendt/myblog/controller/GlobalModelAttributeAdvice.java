package com.kiendt.myblog.controller;

import com.kiendt.myblog.service.ArticleService;
import com.kiendt.myblog.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * Global model attribute advice to add common attributes to all views.
 * This class is responsible for adding global attributes to the model
 * for all controllers in the application.
 */
@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final static String DEFAULT_DOMAIN = "http://localhost:8080";
    private final SettingService settingService;
    private final ArticleService articleService;

    @Autowired
    public GlobalModelAttributeAdvice(SettingService settingService, ArticleService articleService) {
        this.settingService = settingService;
        this.articleService = articleService;
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("domain", DEFAULT_DOMAIN);
        var optSettings = settingService.getConfiguration();
        if (optSettings.isPresent()) {
            var settings = optSettings.get();
            model.addAttribute("blogTitle", settings.getBlogTitle());
            model.addAttribute("blogSubTitle", settings.getSubTitle());
            model.addAttribute("blogDescription", settings.getBlogDescription());
        }

        var navigations = settingService.getNavigationMenus();
        model.addAttribute("navigations", navigations);

        var allViews = articleService.getAllViewsCount();
        List<String> digits = String.valueOf(allViews).chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList();
        model.addAttribute("allViews", Map.of("digits", digits));
    }
}
