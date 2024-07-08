package com.api.itsec_test.service;

import com.api.itsec_test.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);
    List<ArticleDto> getAllArticles();
    ArticleDto getArticleById(int id);
    ArticleDto updateArticle(ArticleDto articleDto,int id);
    void deleteArticle(int id);
}
