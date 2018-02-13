package cn.jihangyu.glowworm.book.service;

import cn.jihangyu.glowworm.book.entity.Book;

public interface BookService {
    String addBook(Book book) throws Exception;

    String updateBook(Book book) throws Exception;
}
