package org.tus.bookService.service;

import org.tus.bookService.dto.BookDto;

import java.util.List;

public interface IBookService {
    void addBook(BookDto bookDto);

    List<BookDto> findAllBooks();

    BookDto fetchBook(String bookName);

    boolean updateBook(String bookName, BookDto booksDto);

    boolean deleteBook(String bookName);
}
