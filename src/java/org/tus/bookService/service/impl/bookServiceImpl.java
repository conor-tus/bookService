package org.tus.bookService.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tus.bookService.dto.BookDto;
import org.tus.bookService.entity.Book;
import org.tus.bookService.exception.ResourceAlreadyExistsException;
import org.tus.bookService.exception.ResourceNotFoundException;
import org.tus.bookService.mapper.BooksMapper;
import org.tus.bookService.repository.BookRepository;
import org.tus.bookService.service.IBookService;

import java.util.List;
import java.util.Optional;

import static org.tus.bookService.mapper.BooksMapper.mapToBooksDto;

@Service
@AllArgsConstructor
public class bookServiceImpl implements IBookService {

    private BookRepository bookRepository;

    @Override
    public void addBook(BookDto bookDto) {
        Book book = BooksMapper.mapToBooks(bookDto, new Book());
        //check for duplicate books

        Optional<Book> bookcheck = bookRepository.findBookByBookName(book.getBookName());
        if (bookcheck.isPresent()) {
            throw new ResourceAlreadyExistsException("Book already exists");
        }

        bookRepository.save(book);
    }

    public List<BookDto> findAllBooks() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Book> books = bookRepository.findAll(pageable);

        return books.stream()
                .map(book -> mapToBooksDto(book, new BookDto()))
                .toList();
    }

    @Override
    public BookDto fetchBook(String bookName) {
        Book books = bookRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("Books","BookName",bookName)
        );
        BookDto booksDto = mapToBooksDto(books, new BookDto());
        booksDto.setBookId(books.getBookId());
        return booksDto;
    }

    @Override
    public boolean updateBook(String bookName, BookDto booksDto) {
        boolean isUpdated = false;
        if(booksDto != null) {
            Book book = bookRepository.findBookByBookName(bookName).orElseThrow(
                    () -> new ResourceNotFoundException("Books","bookName",bookName)
            );
            Book bookRecord = BooksMapper.mapToBooks(booksDto, book);
            bookRepository.save(bookRecord);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteBook(String bookName) {
        Book books = bookRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",bookName)
        );
        bookRepository.delete(books);
        return true;
    }

}
