package com.swk.cpanel.api.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

import com.swk.cpanel.api.bean.ResponseData;
import com.swk.cpanel.api.config.constants.ResponseMsgEnum;
import com.swk.cpanel.api.util.HttpUtil;

public class JWTFilter extends BasicHttpAuthenticationFilter {
	
	@Value("${jwt.header}")
	private String header;
	
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 获取Authorization字段
        String token = httpServletRequest.getHeader(header);
        if (token != null) {
            try {
                JWTToken jwtToken = new JWTToken(token);
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                getSubject(request, response).login(jwtToken);
                return true;
            } catch (Exception e) {
            		responseAuthProblem(httpServletResponse, e.getMessage());
            		return false;
            }
        } else {
        		responseAuthProblem(httpServletResponse, ResponseMsgEnum.UN_AUTHORIZED.name());
	    		return false;
        }
    }

    /**
     * 响应认证错误信息
     */
    private void responseAuthProblem(HttpServletResponse response, String codeMsgEnum) throws Exception {
		HttpUtil.responseJson(response, ResponseData.responseMsg(ResponseMsgEnum.valueOf(codeMsgEnum)));	 
    }
}
