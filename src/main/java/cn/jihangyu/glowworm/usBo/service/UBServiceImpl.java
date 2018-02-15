package cn.jihangyu.glowworm.usBo.service;

import cn.jihangyu.glowworm.book.dao.BookMapper;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.usBo.dao.UsBoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class UBServiceImpl implements UBService {

    @Autowired
    UsBoMapper usBoMapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> findAllHadBookByUid(Integer uid) {
        try {
            if(uid!=null){
                List<Book> list=bookMapper.selectAllHadByUid(uid);
                return list;
            }else{
                throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
            }
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
    }

    @Override
    public List<Book> findAllUsedBookByUid(Integer uid) {
        try {
            if(uid!=null){
                List<Book> list=bookMapper.selectAllUsedByUid(uid);
                return list;
            }else{
                throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
            }
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
    }
}
