package org.tus.bookService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tus.bookService.entity.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findBookByBookId(long id);

    Optional<Book> findBookByBookName(String title);

    Page<Book> findAll(Pageable pageable);
}
