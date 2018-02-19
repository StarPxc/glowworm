package cn.jihangyu.glowworm.book.service;

import cn.jihangyu.glowworm.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    String addBook(Book book) throws Exception;

    String updateBook(Book book);

    Book findBookById(int id);

    List<Book> findBookByType(String type);

    void deleteBookById(Integer id);

    String upload(MultipartFile file, Integer bid) throws IOException;

    List<Book> findBookByBookname(String bookname);
}
