package cn.jihangyu.glowworm.usBo.service;

import cn.jihangyu.glowworm.book.entity.Book;

import java.util.List;

public interface UBService {
    List<Book> findAllHadBookByUid(Integer uid);

    List<Book> findAllUsedBookByUid(Integer id);
}
