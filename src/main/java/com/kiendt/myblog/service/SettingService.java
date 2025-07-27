package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Configuration;
import com.kiendt.myblog.model.Navigation;

import java.util.List;
import java.util.Optional;

public interface SettingService {
    /**
     * Get the configuration settings for the blog.
     *
     * @return an Optional containing the configuration if found, or empty if not found
     */
    Optional<Configuration> getConfiguration();

    /**
     * Get the navigation menus.
     *
     * @return a list of navigation menus
     */
    List<Navigation> getNavigationMenus();

}
