package com.kiendt.myblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "configurations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    @Id
    private Long id;

    private String blogTitle;

    private String subTitle;

    private String blogDescription;

    private String blogAuthor;
}
