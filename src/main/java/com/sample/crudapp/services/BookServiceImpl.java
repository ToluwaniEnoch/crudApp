package com.sample.crudapp.services;

import com.sample.crudapp.entities.Book;
import com.sample.crudapp.entities.CreateBookDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Override
    public Book addBook(CreateBookDto createBookDto) {
        return Book.builder()
                .id(2)
                .isbn(createBookDto.getIsbn())
                .title(createBookDto.getTitle())
                .author(createBookDto.getAuthor())
                .isAvailable(true)
                .createdAt(Instant.now())
                .build();
    }

    @Override
    public Book getBookById(long id) {
        return buildBook();
    }

    @Override
    public List<Book> getAllBooks() {
        Book book = buildBook();
        return List.of(book);
    }

    private static Book buildBook() {
        Book book = Book.builder()
                .id(1)
                .title("How to write java")
                .author("James Smith")
                .createdAt(Instant.now())
                .isbn("32794823408249")
                .isAvailable(true)
                .build();

        return book;
    }

}
