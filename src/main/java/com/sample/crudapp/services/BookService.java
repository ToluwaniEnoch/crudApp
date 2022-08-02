package com.sample.crudapp.services;

import com.sample.crudapp.entities.Book;
import com.sample.crudapp.entities.CreateBookDto;

import java.util.List;
public interface BookService {
    Book addBook(CreateBookDto createBookDto);
    Book getBookById(long id);
    List<Book> getAllBooks();

}
