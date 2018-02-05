package com.swk.cpanel.api.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * shiro配置
 * @author spacewalker
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * 跨域配置
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource configSource=new UrlBasedCorsConfigurationSource();
		CorsConfiguration config=new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedOrigin("*");
		config.addAllowedMethod("*");
		configSource.registerCorsConfiguration("/**", config);
		return new CorsFilter(configSource);
	}
	
	
	/**
	 * 认证与授权资源信息
	 * @return
	 */
	@Bean
	public Realm userRealm() {
		AuthorizingRealm realm=new JWTRealm();
		return realm;
	}
	
	/**
	 * 安全管理器配置
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		 /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
		return securityManager;
	}
	
	/**
	 * 自定义认证过滤器配置
	 * @return
	 */
	@Bean
	public JWTFilter jwtFilter() {
		return new JWTFilter();
	}
	
	/**
	 * 取消将自定义认证过滤器自动注册到springboot的FilterChain中
	 * @param formAuthenticationFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistration(JWTFilter jwtFilter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(jwtFilter);
	    registration.setEnabled(false);//取消自动注册到springboot的FilterChain中
	    return registration;
	}
	
	/**
	 * shiroFilter配置
	 * @return
	 * @throws Exception
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(JWTFilter jwtFilter,SecurityManager securityManager) throws Exception {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//设置自定义加强的过滤器
		shiroFilterFactoryBean.getFilters().put("jwt", jwtFilter);
		
		//拦截器匹配规则
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->
		// 配置不会被拦截的链接
//		filterChainDefinitionMap.put("/login", "anon");
//		filterChainDefinitionMap.put("/webjars/**", "anon");
//		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		//需要认证才能访问的
		filterChainDefinitionMap.put("/users/**", "jwt");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	/**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
	
}