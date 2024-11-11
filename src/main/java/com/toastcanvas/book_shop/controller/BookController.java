package com.toastcanvas.book_shop.controller;

import com.toastcanvas.book_shop.dto.request.BookCreationRequest;
import com.toastcanvas.book_shop.dto.request.BookUpdateRequest;
import com.toastcanvas.book_shop.entity.Book;
import com.toastcanvas.book_shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * API để tạo một cuốn sách mới.
     */
    @PostMapping
    Book createBook(@RequestBody BookCreationRequest request){
        return bookService.createRequest(request);
    }

    /**
     * API để lấy danh sách tất cả các cuốn sách.
     */
    @GetMapping
    List<Book> getBooks(){
        return bookService.getBooks();
    }

    /**
     * API để lấy thông tin chi tiết của một cuốn sách theo id.
     */
    @GetMapping("/{bookId}")
    Book getBook(@PathVariable("bookId") String bookId){
        return bookService.getBook(bookId);
    }

    /**
     * API để cập nhật thông tin của một cuốn sách theo id.
     */
    @PutMapping("/{bookId}")
    Book updateBook(@PathVariable String bookId, @RequestBody BookUpdateRequest request){
        return bookService.updateBook(bookId, request);
    }

    /**
     * API để xóa một cuốn sách theo id.
     */
    @DeleteMapping("/{bookId}")
    String deleteBook(@PathVariable String bookId){
        bookService.deleteBook(bookId);
        return "Sách đã được xóa";
    }
}
