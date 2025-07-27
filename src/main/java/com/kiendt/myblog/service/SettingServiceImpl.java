package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Configuration;
import com.kiendt.myblog.model.Navigation;
import com.kiendt.myblog.repository.ConfigurationRepository;
import com.kiendt.myblog.repository.NavigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Setting service implementation.
 * This class is responsible for managing the settings of the application,
 * including retrieving the configuration and navigation menus.
 */
@Component
@Service
public class SettingServiceImpl implements SettingService {
    /**
     * The default ID for the configuration.
     */
    private static final long DEFAULT_ID = 1L;

    /**
     * The navigation repository.
     */
    private final NavigationRepository navigationRepository;

    /**
     * The configuration repository.
     */
    private final ConfigurationRepository configurationRepository;

    @Autowired
    public SettingServiceImpl(NavigationRepository navigationRepository, ConfigurationRepository configurationRepository) {
        this.navigationRepository = navigationRepository;
        this.configurationRepository = configurationRepository;
    }


    /**
     * Get the configuration.
     *
     * @return an optional configuration
     */
    @Override
    public Optional<Configuration> getConfiguration() {
        return configurationRepository.findById(DEFAULT_ID);
    }

    /**
     * Get the navigation menus.
     *
     * @return a list of navigation menus
     */
    @Override
    public List<Navigation> getNavigationMenus() {
        var sort = Sort.by(Sort.Order.asc("navOrder"));
        return navigationRepository.findAll(sort);
    }
}
