package com.toastcanvas.book_shop.service;

import com.toastcanvas.book_shop.dto.request.BookCreationRequest;
import com.toastcanvas.book_shop.dto.request.BookUpdateRequest;
import com.toastcanvas.book_shop.entity.Book;
import com.toastcanvas.book_shop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public Book createRequest(BookCreationRequest request){

        /**
         * Tạo mới một cuốn sách.
         *
         * Phương thức này kiểm tra xem ISBN có tồn tại trong cơ sở dữ liệu không.
         * Nếu ISBN đã tồn tại, ném ra lỗi. Nếu không, cuốn sách mới sẽ được tạo và lưu vào cơ sở dữ liệu.
         *
         * @param request Thông tin yêu cầu tạo sách mới
         * @return Sách mới được tạo và lưu vào cơ sở dữ liệu
         * @throws RuntimeException nếu ISBN đã tồn tại trong cơ sở dữ liệu
         */
        Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook.isPresent()) {
            throw new RuntimeException("ISBN đã tồn tại.");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublished_date(request.getPublished_date());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());


        return bookRepository.save(book);
    };

    /**
     * Cập nhật thông tin của sách dựa vào bookId.
     *
     * Phương thức này nhận vào ID của sách và yêu cầu cập nhật. Nó kiểm tra xem ISBN mới có bị trùng lặp
     * với sách khác trong cơ sở dữ liệu không. Nếu trùng, sẽ ném ra lỗi.
     *
     * @param BookId ID của cuốn sách cần cập nhật
     * @param request Thông tin cập nhật sách
     * @return Cuốn sách đã được cập nhật và lưu lại trong cơ sở dữ liệu
     * @throws BookNotFoundException nếu không tìm thấy sách với ID đã cho
     * @throws IllegalArgumentException nếu ISBN mới bị trùng lặp với sách khác
     */
    public Book updateBook(String BookId, BookUpdateRequest request){


        Book book = getBook(BookId);
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("ISBN bị trùng lặp");
        }
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublished_date(request.getPublished_date());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        return bookRepository.save(book);
    }

    /**
     * Lấy danh sách tất cả các sách.
     *
     * @return danh sách sách
     */
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    /**
     * Lấy thông tin chi tiết của sách dựa vào id.
     *
     * @param id id của sách
     * @return sách tìm thấy
     */
    public Book getBook(String id){
        return bookRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Không tìm thấy sách"));
    }

    /**
     * Xóa sách dựa vào id.
     *
     * @param bookId id của sách cần xóa
     */
    public void deleteBook(String BookId){
        bookRepository.deleteById(BookId);
    }
}
