package com.swk.cpanel.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swk.cpanel.api.bean.ResponseData;
import com.swk.cpanel.api.config.constants.ResponseMsgEnum;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseData<String> shiroException(ShiroException e) {
    		if(UnauthorizedException.class.isInstance(e)) {
    			return ResponseData.responseMsg(ResponseMsgEnum.ACCESS_FORBIDDEN);
    		}
    		return ResponseData.responseMsg(ResponseMsgEnum.UN_AUTHORIZED);
    }
    
    /**
     * 捕捉业务层异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public ResponseData<String> serviceException(ServiceException e) {
    		return ResponseData.fail(e.getMessage());
    }
    
    
    /**
     *  捕捉其他所有异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> globalException(HttpServletRequest request, Exception e) {
        return ResponseData.codeMsg(getStatus(request).value(), e.getMessage());
    }
    
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

