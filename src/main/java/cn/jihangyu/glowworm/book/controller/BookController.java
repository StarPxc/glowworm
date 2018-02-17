package cn.jihangyu.glowworm.book.controller;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.book.service.BookService;
import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("book")
@Slf4j
public class BookController  {

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    @ApiOperation(value="创建书", notes="根据Book对象创建书")
    @ApiImplicitParam(name = "user", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    public ApiResult addBook(HttpServletRequest req, @RequestBody Book book)throws Exception{
        try {
            String result = bookService.addBook(book);
            return ResultUtil.success(result);
        }catch (Exception e){
            log.error("创建书失败");
            return ResultUtil.error(ResultEnum.OBJECT_ADD_ERROR.getCode(),ResultEnum.OBJECT_ADD_ERROR.getMsg());
        }
    }


    @ApiOperation(value="为书上传图片", notes="为书上传图片")
    @ApiImplicitParam(name = "user", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/uploadBookImg",method = RequestMethod.POST)
    public ApiResult uploadBookImg(HttpServletRequest req, @RequestParam(required=true ) MultipartFile[] files,@RequestParam(required=true ) Integer id )throws Exception{
        try {
            Integer bid=id;
            //upload_img_result为上传图片成功的数量
            String upload_img_result=bookService.uploadBookImgs(files,bid);
            return ResultUtil.success(upload_img_result);
        }catch (Exception e){
            log.error("上传图片失败");
            return ResultUtil.error(ResultEnum.FILE_ERROR.getCode(),ResultEnum.FILE_ERROR.getMsg());
        }
    }



    @ApiOperation(value="修改书", notes="根据Book对象修改书")
    @ApiImplicitParam(name = "book", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
    public ApiResult updateBook(@RequestBody Book book) throws Exception {
        try {
            String result = bookService.updateBook(book);
            return ResultUtil.success(result);
        }catch (Exception e){
            log.error("更新书失败");
            return ResultUtil.error(ResultEnum.OBJECT_UPDATE_ERROR.getCode(),ResultEnum.OBJECT_UPDATE_ERROR.getMsg());
        }
    }

    @ApiOperation(value="根据id查找书", notes="根据id查找书")
    @RequestMapping(value = "/getBookById/{id}",method = RequestMethod.GET)
    public ApiResult getBookById(@PathVariable int id) throws Exception {
        try{
        Book book=bookService.findBookById(id);
        return ResultUtil.success(book);
        }
        catch (NullPointerException w) {
            log.error("id为："+id+"的书不存在");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }catch (Exception e){
            log.error("查找id为："+id+"的书失败");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }

    }

    @ApiOperation(value="根据type查找书", notes="根据type查找书")
    @RequestMapping(value = "/getBookByType/{type}",method = RequestMethod.GET)
    public ApiResult getBookByType(@PathVariable String type) throws Exception {
        try{
            List<Book> books=  bookService.findBookByType(type);
            return ResultUtil.success(books);
        }
        catch (NullPointerException w) {
            log.error("type为："+type+"的书不存在");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }catch (Exception e){
            log.error("查找type为："+type+"的书失败");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }

    }

//    @ApiOperation(value="根据id删除书", notes="根据id删除书")
//    @RequestMapping(value = "/deleteBookById/{id}",method = RequestMethod.GET)
//    public ApiResult deleteBookById(@PathVariable Integer id) throws Exception {
//        //用户id应该从后台获取，不能让前端传
//        UserElement ue=getCurrentUser();//这个方法应该是很多controller都可以用的，所以可以做一个BaseCOntroller
//        //判断当前用户是否为管理员用户(假设管理员用户的uid是1)
//        if(ue==null){
//            return ResultUtil.error(ResultEnum.CURRENT_USER_ERROR.getCode(),ResultEnum.CURRENT_USER_ERROR.getMsg());
//        }else{
//            if(ue.getUserId()==1){
//                bookService.deleteBookById(id);
//            }else{
//                return ResultUtil.error(ResultEnum.NO_LOGIN.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
//            }
//        }
//        return ResultUtil.success("删除用户成功");
//    }

}
