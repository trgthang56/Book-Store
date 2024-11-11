package com.toastcanvas.book_shop.repository;

import com.toastcanvas.book_shop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
