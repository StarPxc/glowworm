package cn.jihangyu.glowworm.usBo.service;

import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.usBo.entity.UsBo;

import java.util.List;

public interface UBService {
    List<Book> findAllHadBookByUid(Integer uid);

    List<Book> findAllUsedBookByUid(Integer id);

    String orderBook(UsBo usBo);

    String returnBook(UsBo usBo);
}
