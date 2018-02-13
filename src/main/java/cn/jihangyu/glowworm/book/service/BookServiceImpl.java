package cn.jihangyu.glowworm.book.service;

import cn.jihangyu.glowworm.book.dao.BookMapper;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.cache.CommonCacheUtil;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MD5Util;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CommonCacheUtil cacheUtil;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    @Override
    public String addBook(Book book) throws Exception {
        String result = null;
        if(book==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(book)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        wlock.lock();
        try {
            bookMapper.insertSelective(book);
            result=ResultEnum.SUCCSEE.getMsg();
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }finally {
            wlock.unlock();
        }
        return result;
    }

    @Override
    public String updateBook(Book book) throws Exception {
        String result=null;
        wlock.lock();
        try {
            int code= bookMapper.updateByPrimaryKeySelective(book);
            if(code!=1){
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }else{
                result=ResultEnum.SUCCSEE.getMsg();
            }
        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }catch (Exception e){
            log.error("【修改书失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);
        }finally {
            wlock.unlock();
        }
        return result;
    }

}
