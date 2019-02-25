package com.fanqiao.secondkill.exception;

import com.fanqiao.secondkill.result.CodeMessage;
import com.fanqiao.secondkill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
//把@ControllerAdvice注解内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法应用到所有的 @RequestMapping注解的方法。
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            return Result.error(globalException.getCm());
        } else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> objectErrorList = ex.getAllErrors();
            ObjectError objectError = objectErrorList.get(0);
            String message = objectError.getDefaultMessage();
            return Result.fillArgs(CodeMessage.BIND_ERROR, message);
        } else {
            return Result.error(CodeMessage.SERVER_ERROR);
        }
    }
}
