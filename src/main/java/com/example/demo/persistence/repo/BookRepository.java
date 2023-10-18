package com.example.demo.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.example.demo.persistence.model.Book;

/**
 * * Making good use of Spring Data, I define my own Book repository 
 */
public interface BookRepository  extends CrudRepository<Book, Long>{
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
}
