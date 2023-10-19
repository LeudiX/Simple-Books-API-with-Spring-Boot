package com.example.demo;

import com.example.demo.persistence.model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * ! Making some tests to this simple Books API
 */
public class SimpleSpringBootLiveTest {

    public static final String API_ROOT = "http://localhost:8081/apiv1/books";

    // Trying to find books using variant methods
    @Test
    public void whenGetAllBooks_thenReturn200() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetBooksByTitle_thenReturn200() {
        final Book book = createRandomBook();
        createBookAsUrl(book);
        final Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void whenCreatedBookByID_thenReturn201() {
        final Book book = createRandomBook();
        final String location = createBookAsUrl(book);
        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book.getTitle(), response.jsonPath().get("title"));
    }

    @Test
    public void whenGetNotExistBookbyId_thenReturn404() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // Test creating a new book
    @Test
    public void whenCreateNewBook_thenReturn201() {
        final Book book = createRandomBook();
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidBook_thenReturn404() {
        final Book book = createRandomBook();
        book.setAuthor(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    // Test updating an existing book
    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        final Book book = createRandomBook();
        final String location = createBookAsUrl(book);
        book.setId(Long.parseLong(location.split("apiv1/books/")[1]));
        book.setAuthor("Rafael González");

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Mark González", response.jsonPath().get("author"));

    }

    // Test deleting a book
    @Test
    public void whenDeleteCreatedBook_thenOK() {
        final Book book = createRandomBook();
        final String location = createBookAsUrl(book);
        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===========================================================================
    private Book createRandomBook() {
        final Book book = new Book();
        book.setId(8);
        book.setTitle("Spring Boot in Action");
        book.setAuthor("Leudis Estrada González");
        return book;
    }

    private String createBookAsUrl(Book book) {
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }
}
