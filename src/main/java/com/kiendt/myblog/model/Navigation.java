package com.kiendt.myblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "navigation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Navigation {
    @Id
    private Long id;

    private int navOrder;

    private String name;

    private String url;
}
