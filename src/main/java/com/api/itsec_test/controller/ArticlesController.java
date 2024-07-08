package com.api.itsec_test.controller;

import com.api.itsec_test.dto.ArticleDto;
import com.api.itsec_test.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ArticlesController {

    private ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK) ;
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable int id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @PostMapping("/article")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        return new ResponseEntity<>(articleService.createArticle(articleDto), HttpStatus.CREATED);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable("id") int articleId) {
        ArticleDto response = articleService.updateArticle(articleDto, articleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") int articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("Successfully Deleted Article");
    }

}
