package com.api.itsec_test.repository;

import com.api.itsec_test.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


}
