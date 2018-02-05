package com.swk.cpanel.api.config;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * 
 * 当前修新增或修改数据的人员设置 @CreatedBy @LastModifiedBy 所需数据
 * @author spacewalker
 *
 */
@Configuration
public class OpeatorConfig implements AuditorAware<String>{

	@Override
	public String getCurrentAuditor() {
		try {
			Object principal = SecurityUtils.getSubject().getPrincipal();
			if(principal!=null) {
				return principal.toString();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
}
