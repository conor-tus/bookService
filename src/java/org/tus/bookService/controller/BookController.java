package org.tus.bookService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tus.bookService.constants.BookConstants;
import org.tus.bookService.dto.BookDto;
import org.tus.bookService.dto.ResponseDto;
import org.tus.bookService.service.IBookService;

import java.util.List;


@RestController
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final IBookService iBooksService;

    @Autowired
    public BookController(IBookService iBooksService) {
        this.iBooksService = iBooksService;
    }

    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }

    @PostMapping("/book")
    public ResponseEntity<ResponseDto> createBook(@RequestBody BookDto bookDto) {
        iBooksService.addBook(bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(BookConstants.STATUS_201, BookConstants.BOOK_CREATED_MESSAGE_201));
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> booksDto = iBooksService.findAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(booksDto);
    }

    @GetMapping("/book/{bookName}")
    public ResponseEntity<BookDto> getBook(@PathVariable String bookName) {
        BookDto booksDto = iBooksService.fetchBook(bookName);
        return ResponseEntity.status(HttpStatus.OK).body(booksDto);
    }

    @PutMapping("/book/{bookName}")
    public ResponseEntity<ResponseDto> updateBook(@PathVariable String bookName, @RequestBody BookDto bookDto) {
        boolean isUpdated = iBooksService.updateBook(bookName,bookDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BookConstants.STATUS_200, BookConstants.BOOK_UPDATED_MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BookConstants.STATUS_500, BookConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/book/{bookName}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable String bookName) {

        boolean isDeleted = iBooksService.deleteBook(bookName);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BookConstants.STATUS_200, BookConstants.BOOK_DELETED_MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BookConstants.STATUS_500, BookConstants.MESSAGE_500));
        }

    }
}
