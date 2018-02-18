package cn.jihangyu.glowworm.usBo.service;

import cn.jihangyu.glowworm.book.dao.BookMapper;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.usBo.dao.UsBoMapper;
import cn.jihangyu.glowworm.usBo.entity.UsBo;
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
    public List<Book> findAllHadBookByUid(String uid) {
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
    public List<Book> findAllUsedBookByUid(String uid) {
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

    @Override
    public String orderBook(UsBo usBo) {
        try{
            if(usBo!=null){
                String uid=usBo.getuId();
                Integer bid=usBo.getbId();
                Book book=bookMapper.selectByPrimaryKey(bid);
                String status=book.getbStatus();
                //书的状态码，0表示空闲，1表示已被预定
                if(status.equals("1")){
                    throw new GlowwormExecption(ResultEnum.BOOK_ORDERD);
                }else{
                    Integer flag=usBoMapper.insert(usBo);
                    //将书的状态设置为被预定
                    book.setbStatus("1");
                    //将书的buserid设置为租书人的id
                    book.setbUserId(uid);
                    bookMapper.updateByPrimaryKeySelective(book);
                    return flag.toString();
                }
            }else{
                throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
            }
        }catch (Exception e){
                throw new GlowwormExecption(ResultEnum.ORDER_ERROR);
        }
    }

    @Override
    public String returnBook(UsBo usBo) {
        try{
            if(usBo!=null){
                String uid=usBo.getuId();
                Integer bid=usBo.getbId();
                Book book=bookMapper.selectByPrimaryKey(bid);
                String status=book.getbStatus();
                //书的状态码，0表示空闲，1表示已被预定
                if(status.equals("0")){
                    throw new GlowwormExecption(ResultEnum.BOOK_NOT_ORDERD);
                }else{
                    Integer flag=usBoMapper.deleteByPrimaryKey(uid);
                    //将书的状态设置为空闲
                    book.setbStatus("0");
                    //书的buserid设置为0代表此暂时无人使用
                    book.setbUserId("0");
                    bookMapper.updateByPrimaryKeySelective(book);
                    return flag.toString();
                }
            }else{
                throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
            }
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.RETURN_ERROR);
        }
    }
}
