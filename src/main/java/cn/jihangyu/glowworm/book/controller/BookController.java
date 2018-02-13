package cn.jihangyu.glowworm.book.controller;

import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.book.service.BookService;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
@Slf4j
public class BookController {

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    @ApiOperation(value="创建书", notes="根据Book对象创建书")
    @ApiImplicitParam(name = "user", value = "书详细实体book", required = true, dataType = "Book")
    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    public ApiResult addBook(@RequestBody Book book)throws Exception{
        try {
            String result = bookService.addBook(book);
            return ResultUtil.success(result);
        }catch (Exception e){
            log.error("创建书失败");
            return ResultUtil.error(ResultEnum.OBJECT_ADD_ERROR.getCode(),ResultEnum.OBJECT_ADD_ERROR.getMsg());
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

}
