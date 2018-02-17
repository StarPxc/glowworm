package cn.jihangyu.glowworm.book.service;

import cn.jihangyu.glowworm.book.dao.BookMapper;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.common.utils.QiniuFileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class BookServiceImpl implements  BookService{

    @Autowired
    private BookMapper bookMapper;

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
    public String updateBook(Book book) {
        String result = null;
        wlock.lock();
        try {
            int code = bookMapper.updateByPrimaryKeySelective(book);
            if (code != 1) {
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            } else {
                result = ResultEnum.SUCCSEE.getMsg();
            }
        } catch (GlowwormExecption e) {
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        } catch (Exception e) {
            log.error("【修改书失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);
        } finally {
            wlock.unlock();
        }
        return result;
    }

    @Override
    public Book findBookById(int id) {
        Book book=null;
        rlock.lock();
        try{
            book=bookMapper.selectByPrimaryKey(id);
            if(book==null){
                log.error("【id为】"+id+"的书不存在");
                throw new NullPointerException("查找的编号不存在");
            }
        }catch(GlowwormExecption e){
            log.error("【查找id为】"+id+"的书失败");
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }finally {
            rlock.unlock();
        }
        return book;
    }

    @Override
    public List<Book> findBookByType(String type) {
        List<Book> books=null;
        rlock.lock();
        try{
            books=bookMapper.selectByType(type);
            if(books==null){
                log.error("【type为】"+type+"的书不存在");
                throw new NullPointerException("查找的种类不存在");
            }
        }catch(GlowwormExecption e){
            log.error("【查找type为】"+type+"的书失败");
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }finally {
            rlock.unlock();
        }
        return books;
    }

    @Override
    public void deleteBookById(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
        try {
            int code= bookMapper.deleteByPrimaryKey(id);
            if(code!=1){
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }
        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
        catch (Exception e){
            log.error("【删除对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_DELETE_ERROR);

        }finally {
            wlock.unlock();
        }
    }

    @Override
    public String uploadBookImgs(MultipartFile[] files, Integer bid) {
        try{
            Book book=bookMapper.selectByPrimaryKey(bid);
            if(book==null){
                throw  new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
            }
            Integer size=files.length;
            String urls="";
            for(MultipartFile file:files){
                String img_url=QiniuFileUploadUtil.uploadBookImg(file);
                urls=urls+img_url+",";
            }
            book.setbImg(urls);
            bookMapper.updateByPrimaryKeySelective(book);
            return size.toString();
        }catch (Exception e){
            log.error("上传书的图片失败");
            throw new GlowwormExecption(ResultEnum.FILE_ERROR);
        }
    }
}
