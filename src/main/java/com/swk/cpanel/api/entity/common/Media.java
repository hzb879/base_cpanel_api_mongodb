package com.swk.cpanel.api.entity.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 媒体资源:如图片,音频,视屏等
 * @author spacewalker
 *
 */
@Data
@Accessors(chain=true)
public class Media {
	/**
	 * 访问链接
	 */
	private String src;
	
	/**
	 * 存储空间bucket下的文件唯一标识
	 */
	private String key;
	
	/**
	 * 存储空间标识
	 */
	private String bucket;
	
	/**
	 * 文件原始名称
	 */
	private String name;

}
