package com.swk.cpanel.api.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * shiro加密工具类
 * @author spacewalker
 *
 */
public class ShiroEncryptUtil {
	
	public static final String ALGORITHM_NAME = "md5";
	
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	public static final int DEFAULT_HASH_ITERATIONS = 12;
	
	
	/**
	 * md5加盐生成新的密码,散列次数默认
	 * @param oldPassword 原密码
	 * @return 盐和新密码
	 */
	public static SaltPwd encryptPwdWithRandomSalt(String oldPassword) {
		return encryptPwdWithRandomSalt(oldPassword,DEFAULT_HASH_ITERATIONS);
	}
	
	/**
	 * md5加盐生成新的密码
	 * @param oldPassword 原密码
	 * @param hashIterations 散列次数
	 * @return 盐和新密码
	 */
	public static SaltPwd encryptPwdWithRandomSalt(String oldPassword, int hashIterations) {
		String salt=randomNumberGenerator.nextBytes().toHex();
		return new SaltPwd(salt, new SimpleHash(ALGORITHM_NAME, oldPassword, ByteSource.Util.bytes(salt), hashIterations).toHex());
	}
	
	/**
	 * 校验密码是否正确,散列次数默认
	 * @param password 密码
	 * @param encryptPassword 加密后的密码
	 * @param salt 盐
	 * @return
	 */
	public static boolean verifyPassword(String password, String encryptPassword, String salt) {
		return verifyPassword(password, encryptPassword, salt, DEFAULT_HASH_ITERATIONS);
	}
	
	
	/**
	 * 校验密码是否正确
	 * @param password 密码
	 * @param encryptPassword 加密后的密码
	 * @param salt 盐
	 * @param hashIterations 散列次数
	 * @return
	 */
	public static boolean verifyPassword(String password, String encryptPassword, String salt, int hashIterations) {
		return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), hashIterations).toHex().equals(encryptPassword);
	}
	
	@Data
	@AllArgsConstructor
	public static class SaltPwd{
		private String salt;
		private String password;
	}
	
	
}
