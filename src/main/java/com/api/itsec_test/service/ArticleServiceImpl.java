package com.api.itsec_test.service;

import com.api.itsec_test.dto.ArticleDto;
import com.api.itsec_test.exceptions.ArticleNotFoundException;
import com.api.itsec_test.models.Article;
import com.api.itsec_test.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setWriter(articleDto.getWriter());

        Article newArticle = articleRepository.save(article);

        ArticleDto articleResp = new ArticleDto();
        articleResp.setId(newArticle.getId());
        articleResp.setTitle(newArticle.getTitle());
        articleResp.setContent(newArticle.getContent());
        articleResp.setWriter(newArticle.getWriter());
        return articleResp;
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> allArticles = articleRepository.findAll();
        return allArticles.stream().map(article -> mapToDto(article)).collect(Collectors.toList());
    }

    @Override
    public ArticleDto getArticleById(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Article Doesn't Exist"));
        return mapToDto(article);
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto,int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Article Doesn't Exist"));

        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setWriter(articleDto.getWriter());

        Article updatedArticle = articleRepository.save(article);
        return mapToDto(updatedArticle);
    }

    @Override
    public void deleteArticle(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Article Doesn't Exist"));
        articleRepository.delete(article);
    }

    private ArticleDto mapToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setWriter(article.getWriter());
        return articleDto;
    }

    private Article mapToEntity(ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setWriter(articleDto.getWriter());
        return article;
    }
}
