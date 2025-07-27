package com.kiendt.myblog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for representing a tag item in view.
 */
@Builder
@Data
public class TagDto {
    private Long id;
    private String name;
    private String slug;
}
