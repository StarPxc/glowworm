package cn.jihangyu.glowworm.book.dao;

import cn.jihangyu.glowworm.book.entity.Book;

import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Integer bId);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bId);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> selectByType(String type);

    List<Book> selectAllHadByUid(String uid);

    List<Book> selectAllUsedByUid(String uid);

    List<Book> selectByName(String bookname);
}