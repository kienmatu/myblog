package com.kiendt.myblog.service;

import com.kiendt.myblog.model.Configuration;
import com.kiendt.myblog.model.Navigation;
import com.kiendt.myblog.repository.ConfigurationRepository;
import com.kiendt.myblog.repository.NavigationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingServiceImplTest {

    @Mock
    private NavigationRepository navigationRepository;

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private SettingServiceImpl settingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConfigurationWhenExistsThenReturnConfiguration() {
        Configuration configuration = new Configuration();
        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));

        Optional<Configuration> result = settingService.getConfiguration();

        assertTrue(result.isPresent());
        assertEquals(configuration, result.get());
        verify(configurationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetConfigurationWhenNotExistsThenReturnEmpty() {
        when(configurationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Configuration> result = settingService.getConfiguration();

        assertFalse(result.isPresent());
        verify(configurationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNavigationMenusWhenCalledThenReturnSortedMenus() {
        Navigation nav1 = new Navigation();
        Navigation nav2 = new Navigation();
        List<Navigation> navigationList = List.of(nav1, nav2);

        when(navigationRepository.findAll(any(Sort.class))).thenReturn(navigationList);

        List<Navigation> result = settingService.getNavigationMenus();

        assertEquals(2, result.size());
        assertEquals(navigationList, result);
        verify(navigationRepository, times(1)).findAll(any(Sort.class));
    }
}