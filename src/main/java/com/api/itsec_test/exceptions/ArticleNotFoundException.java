package com.api.itsec_test.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException(String message){
        super(message);
    }
}
