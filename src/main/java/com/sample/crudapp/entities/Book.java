package com.sample.crudapp.entities;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Book {
    public long id;
    private String title;
    private String author;
    private Instant createdAt;
    private boolean isAvailable;
    private String isbn;
}
