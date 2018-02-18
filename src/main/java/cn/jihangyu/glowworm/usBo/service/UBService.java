package cn.jihangyu.glowworm.usBo.service;

import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.usBo.entity.UsBo;

import java.util.List;

public interface UBService {
    List<Book> findAllHadBookByUid(String uid);

    List<Book> findAllUsedBookByUid(String id);

    String orderBook(UsBo usBo);

    String returnBook(UsBo usBo);
}
