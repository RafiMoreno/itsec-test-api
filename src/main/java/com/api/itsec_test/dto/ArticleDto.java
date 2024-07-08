package com.api.itsec_test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDto {
    private int id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Writer cannot be empty")
    private String writer;
}
