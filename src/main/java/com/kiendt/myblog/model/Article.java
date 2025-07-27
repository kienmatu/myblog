package com.kiendt.myblog.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private Long id;
    private ArticleType articleType;
    @Column(unique = true)
    private String slug;
    private String title;
    private String content;
    private String excerpt;
    private String image;
    private Date publishDate;
    private Integer views;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;
}
