package org.tus.bookService.mapper;

import org.tus.bookService.dto.BookDto;
import org.tus.bookService.entity.Book;

public class BooksMapper {

    public static BookDto mapToBooksDto(Book book, BookDto bookDto) {
        bookDto.setBookId(book.getBookId());
        bookDto.setBookName(book.getBookName());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPageCount(book.getPageCount());
        return bookDto;
    }

    public static Book mapToBooks(BookDto bookDto,Book book) {
        if (bookDto.getBookName() != null) {book.setBookName(bookDto.getBookName());}
        if (bookDto.getAuthor() != null) { book.setAuthor(bookDto.getAuthor());}
        if (bookDto.getPageCount()!= null) { book.setPageCount(bookDto.getPageCount());}
        return book;
    }

}
