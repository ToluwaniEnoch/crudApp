package com.sample.crudapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.crudapp.controller.BookController;
import com.sample.crudapp.entities.Book;
import com.sample.crudapp.entities.CreateBookDto;
import com.sample.crudapp.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = { BookController.class } )
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(
                        document("{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()))
                )
                .build();
    }

    @Test
    void addBookReturnSuccess() throws Exception{
        CreateBookDto dto = CreateBookDto.builder()
                .author("Tim")
                .title("Hello there")
                .isbn("3295799023976")
                .build();

        when(bookService.addBook(dto)).thenReturn(createBook());
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.title").value("Hello there"))
                .andExpect(jsonPath("$.author").value("Tim"))
                .andExpect(jsonPath("$.isbn").value(3295799023976L))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void getBookByIdSuccessfully() throws Exception{


        when(bookService.getBookById(any(long.class))).thenReturn(createBook());
        long id = 7;
        mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.title").value("Hello there"))
                .andExpect(jsonPath("$.author").value("Tim"))
                .andExpect(jsonPath("$.isbn").value(3295799023976L))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void getAllBookSuccessfully() throws Exception{


        when(bookService.getAllBooks()).thenReturn(List.of(createBook()));
        mockMvc.perform(get("/api/books/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id").value(7))
                .andExpect(jsonPath("$.[0].title").value("Hello there"))
                .andExpect(jsonPath("$.[0].author").value("Tim"))
                .andExpect(jsonPath("$.[0].isbn").value(3295799023976L))
                .andExpect(jsonPath("$.[0].available").value(true));
    }

    private Book createBook(){
        return Book.builder()
                .id(7)
                .isAvailable(true)
                .author("Tim")
                .title("Hello there")
                .isbn("3295799023976")
                .build();
    }
}
