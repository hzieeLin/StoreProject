package com.hziee.store.controller;

import com.hziee.store.controller.ex.*;
import com.hziee.store.service.ex.*;
import com.hziee.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpSession;

//控制类的基类
public class BaseController {
    //操作成功状态码
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户数据不存在");
        }else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户名密码错误");
        }else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册产生异常");
        }else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
