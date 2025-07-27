package com.kiendt.myblog;

import com.github.javafaker.Faker;
import com.kiendt.myblog.model.Article;
import com.kiendt.myblog.model.ArticleType;
import com.kiendt.myblog.model.Navigation;
import com.kiendt.myblog.model.Tag;
import com.kiendt.myblog.repository.ArticleRepository;
import com.kiendt.myblog.repository.ConfigurationRepository;
import com.kiendt.myblog.repository.NavigationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * The type Database seeder.
 * This class is responsible for seeding the database with initial data.
 * It uses the CommandLineRunner interface to execute code after the application context is loaded.
 */
@Configuration
public class DatabaseSeeder {
    private static final Faker faker = new Faker();
    private static final com.kiendt.myblog.model.Configuration CONFIGURATION =
            com.kiendt.myblog.model.Configuration.builder()
                    .id(1L)
                    .blogTitle("Blog của Kiên")
                    .subTitle("Welcome to my blog")
                    .blogAuthor("Kiendt")
                    .blogDescription("A blog about everything.")
                    .build();
    private static final List<Tag> TAGS = List.of(
            Tag.builder()
                    .id(1L)
                    .name("Java")
                    .slug("java")
                    .build(),
            Tag.builder()
                    .id(2L)
                    .name("Spring Boot")
                    .slug("spring-boot")
                    .build(),
            Tag.builder()
                    .id(3L)
                    .name("JavaScript")
                    .slug("javascript")
                    .build()
    );
    private static final List<Navigation> NAVIGATIONS =
            List.of(
                    Navigation.builder()
                            .id(1L)
                            .name("My blog")
                            .url("/")
                            .navOrder(1)
                            .build(),
                    Navigation.builder()
                            .id(2L)
                            .name("About me")
                            .url("/articles/about-me")
                            .navOrder(2)
                            .build()
            );

    /**
     * The genesis articles.
     */
    private static final List<Article> GENESIS_ARTICLES = List.of(
            Article.builder()
                    .id(1L)
                    .articleType(ArticleType.PAGE)
                    .title("About me")
                    .content("About me is " + faker.lorem().paragraph(20))
                    .excerpt("About me is " + faker.lorem().paragraph(15))
                    .image("1.jpg")
                    .publishDate(Date.from(
                            LocalDate.of(2023, 1, 1).
                                    atStartOfDay(ZoneId.systemDefault()).toInstant()
                    ))
                    .slug("about-me")
                    .views(1234)
                    .tags(List.of(TAGS.get(1)))
                    .build(),
            Article.builder()
                    .id(2L)
                    .articleType(ArticleType.ARTICLE)
                    .title("The best way to predict the future is to create it.")
                    .content(faker.lorem().paragraph(40))
                    .excerpt(faker.lorem().paragraph(15))
                    .image("1.jpg")
                    .publishDate(Date.from(
                            LocalDate.of(2023, 1, 1).
                                    atStartOfDay(ZoneId.systemDefault()).toInstant()
                    ))
                    .slug("the-best-way-to-predict-the-future-is-to-create-it")
                    .views(1234)
                    .tags(TAGS)
                    .build(),
            Article.builder()
                    .id(3L)
                    .articleType(ArticleType.ARTICLE)
                    .title("The only limit to our realization of tomorrow is our doubts of today.")
                    .slug("the-only-limit-to-our-realization-of-tomorrow-is-our-doubts-of-today")
                    .content(faker.lorem().paragraph(50))
                    .excerpt(faker.lorem().paragraph(12))
                    .image("2.jpg")
                    .publishDate(Date.from(
                            LocalDate.of(2023, 1, 2).
                                    atStartOfDay(ZoneId.systemDefault()).toInstant()
                    ))
                    .views(5678)
                    .build(),
            Article.builder()
                    .id(4L)
                    .articleType(ArticleType.ARTICLE)
                    .title("Success is not the key to happiness. Happiness is the key to success.")
                    .slug("success-is-not-the-key-to-happiness-happiness-is-the-key-to-success")
                    .content(faker.lorem().paragraph(50))
                    .excerpt(faker.lorem().paragraph(8))
                    .image("3.jpg")
                    .publishDate(Date.from(
                            LocalDate.of(2025, 12, 3).
                                    atStartOfDay(ZoneId.systemDefault()).toInstant()
                    ))
                    .views(91011)
                    .tags(TAGS)
                    .build()
    );

    @Bean
    CommandLineRunner initDatabase(ArticleRepository articleRepo,
                                   ConfigurationRepository configurationRepo,
                                   NavigationRepository navigationRepo) {
        return args -> {
            navigationRepo.saveAll(NAVIGATIONS);
            configurationRepo.save(CONFIGURATION);
            articleRepo.saveAll(GENESIS_ARTICLES);
        };
    }
}
