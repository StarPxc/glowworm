package cn.jihangyu.glowworm.book.dao;

import cn.jihangyu.glowworm.book.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface BookMapper {
    int deleteByPrimaryKey(Integer bId);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bId);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> selectByType(String type);
}