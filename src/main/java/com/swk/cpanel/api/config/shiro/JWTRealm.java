package com.swk.cpanel.api.config.shiro;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

import com.swk.cpanel.api.config.constants.ResponseMsgEnum;
import com.swk.cpanel.api.entity.User;
import com.swk.cpanel.api.exception.ServiceException;
import com.swk.cpanel.api.service.UserService;
import com.swk.cpanel.api.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JWTRealm extends AuthorizingRealm {
	
	@Value("${jwt.secret}")
	private String secret;

	@Resource
	private UserService userService;
	
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    		String id = principals.getPrimaryPrincipal().toString();
    		User user;
		try {
			user = userService.getInfoWithRolesAndPermissions(id);
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
    		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    		simpleAuthorizationInfo.addRoles(user.getRoles());
    		simpleAuthorizationInfo.addStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String id = null;
        try {
        		Object idClaim =JwtUtil.parseJWT(token, secret).get("id");
        		if(idClaim == null) {
        			throw new AuthenticationException(ResponseMsgEnum.MISS_INFO_TOKEN.name());
        		}
        		id = idClaim.toString();
        		if(Objects.isNull(userService.getById(id))) {
        			  throw new AuthenticationException(ResponseMsgEnum.USER_MISS_TOKEN.name());
        		}
        }catch(ExpiredJwtException e) {
        		throw new AuthenticationException(ResponseMsgEnum.EXPIRED_TOKEN.name());
        }catch(JwtException e) {
        		throw new AuthenticationException(ResponseMsgEnum.INVALID_TOKEN.name());
        }
        return new SimpleAuthenticationInfo(id, token, getName());
    }
}
