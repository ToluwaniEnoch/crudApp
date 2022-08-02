package com.sample.crudapp.controller;

import com.sample.crudapp.entities.Book;
import com.sample.crudapp.entities.CreateBookDto;
import com.sample.crudapp.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Book> addBook(@RequestBody CreateBookDto createBookDto){
        return ResponseEntity.ok(bookService.addBook(createBookDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
