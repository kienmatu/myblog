package com.kiendt.myblog.dto;

import com.kiendt.myblog.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * DTO for representing an article item in view.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ArticleDto {
    /**
     * DTO for representing a tag item in view.
     */
    @Builder
    static class TagDto {
        private Long id;
        private String name;
        private String slug;
    }

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Long id;
    private String slug;
    private String title;
    private String excerpt;
    private String content;
    private String image;
    private String publishDate;
    private Date originalPublishDate;
    private Integer views;
    private List<TagDto> tags;


    public static ArticleDto from(Article article) {
        String formattedDate = article.getPublishDate() != null
                ? article.getPublishDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DATE_FORMATTER)
                : null;

        return ArticleDto.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .excerpt(article.getExcerpt())
                .content(article.getContent())
                .image(article.getImage())
                .publishDate(formattedDate)
                .originalPublishDate(article.getPublishDate())
                .views(article.getViews())
                .tags(article.getTags() != null ? article.getTags().stream().map(t -> new TagDto(
                        t.getId(),
                        t.getName(),
                        t.getSlug()
                )).toList() : null)
                .build();
    }
}