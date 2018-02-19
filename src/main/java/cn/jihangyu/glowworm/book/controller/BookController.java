package cn.jihangyu.glowworm.book.controller;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.book.service.BookService;
import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
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
public class BookController extends BaseController {

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    @ApiOperation(value = "创建书", notes = "根据Book对象创建书")
    @ApiImplicitParam(name = "user", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ApiResult addBook(HttpServletRequest req, @RequestBody Book book) throws Exception {

            UserElement ue = getCurrentUser();
            String result = null;
            if (ue.getRole().equals("admin")) {
                //管理员可以为任意用户上传书
                result = bookService.addBook(book);
                book.setbStatus("0");
            } else {
                //用户首次上传的书默认拥有者和使用者都是自己，状态是0
                book.setbOwnerId(ue.getUserId());
                book.setbUserId(ue.getUserId());
                book.setbStatus("0");
                result = bookService.addBook(book);
            }
            return ResultUtil.success(result);

    }


    @ApiOperation(value = "为书上传图片", notes = "为书上传图片")
    @ApiImplicitParam(name = "book", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ApiResult upload(@RequestParam(required = true) MultipartFile file, @RequestParam(required = true) Integer id) throws Exception {
        UserElement ue = getCurrentUser();
        if (ue.getRole().equals("admin")||isOwner(ue.getUserId(),id)) {
            //管理员可以修改任意书
            String resultFileName = bookService.upload(file, id);
            return ResultUtil.success(resultFileName);
        }else {
            throw new GlowwormExecption(ResultEnum.IDENTITY_AUTHENTICATION_FAILURE);
        }

    }

    private boolean isOwner(String userId,Integer id) {
        if(bookService.findBookById(id).getbOwnerId().equals(userId)){
            return true;
        }else {
            return false;
        }
    }


    @ApiOperation(value = "修改书", notes = "根据Book对象修改书")
    @ApiImplicitParam(name = "book", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public ApiResult updateBook(@RequestBody Book book) throws Exception {
        String result = null;
        UserElement ue = getCurrentUser();
        try {
            if (ue.getRole().equals("admin")) {
                //管理员可以修改任意书
                bookService.updateBook(book);
                return ResultUtil.success(result);
            } else {
                //当前用户为书的所有者才能修改书的信息
                if (book.getbOwnerId().equals(ue.getUserId())) {
                    result = bookService.updateBook(book);
                    return ResultUtil.success(result);
                } else {
                    //否则抛出没有权限异常
                    return ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(), ResultEnum.NO_AUTHORITY.getMsg());
                }
            }
        } catch (Exception e) {
            log.error("更新书失败");
            return ResultUtil.error(ResultEnum.OBJECT_UPDATE_ERROR.getCode(), ResultEnum.OBJECT_UPDATE_ERROR.getMsg());
        }
    }

    @ApiOperation(value = "根据id查找书", notes = "根据id查找书")
    @RequestMapping(value = "/getBookById/{id}", method = RequestMethod.GET)
    public ApiResult getBookById(@PathVariable int id) throws Exception {
        try {
            Book book = bookService.findBookById(id);
            return ResultUtil.success(book);
        } catch (NullPointerException w) {
            log.error("id为：" + id + "的书不存在");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        } catch (Exception e) {
            log.error("查找id为：" + id + "的书失败");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }

    }

    @ApiOperation(value = "根据书名查找书", notes = "根据书名查找书")
    @RequestMapping(value = "/getBookByName/{bookname}", method = RequestMethod.GET)
    public ApiResult getBookByBookname(@PathVariable String bookname) throws Exception {
        try {
            List<Book> books = bookService.findBookByBookname(bookname);
            return ResultUtil.success(books);
        } catch (NullPointerException w) {
            log.error("bookname为：" + bookname + "的书不存在");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        } catch (Exception e) {
            log.error("查找bookname为：" + bookname + "的书失败");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }

    }

    @ApiOperation(value = "根据type查找书", notes = "根据type查找书")
    @RequestMapping(value = "/getBookByType/{type}", method = RequestMethod.GET)
    public ApiResult getBookByType(@PathVariable String type) throws Exception {
        try {
            List<Book> books = bookService.findBookByType(type);
            return ResultUtil.success(books);
        } catch (NullPointerException w) {
            log.error("type为：" + type + "的书不存在");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        } catch (Exception e) {
            log.error("查找type为：" + type + "的书失败");
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }
    }

    @ApiOperation(value = "根据id删除书", notes = "根据id删除书")
    @RequestMapping(value = "/deleteBookById/{id}", method = RequestMethod.GET)
    public ApiResult deleteBookById(@PathVariable Integer id) throws Exception {
        //用户id应该从后台获取，不能让前端传
        UserElement ue = getCurrentUser();//这个方法应该是很多controller都可以用的，所以可以做一个BaseCOntroller
        if (ue.getRole().equals("admin")) {
            bookService.deleteBookById(id);
        } else {
            //当前用户必须为书的拥有者，并且这本书的当前使用状态为0（空闲中）才能删除书
            Book book = bookService.findBookById(id);
            if (ue.getUserId().equals(book.getbOwnerId()) && book.getbStatus().equals("0")) {
                bookService.deleteBookById(id);
            }
        }
        return ResultUtil.success("删除书成功");

    }
}
