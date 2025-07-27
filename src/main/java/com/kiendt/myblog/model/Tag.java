package com.kiendt.myblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    /**
     * The id.
     */
    @Id
    private Long id;
    private String name;
    private String slug;

    /**
     * The articles.
     */
    // This is a many-to-many relationship with Article
    @ManyToMany(mappedBy = "tags")
    private List<Article> articles;
}
