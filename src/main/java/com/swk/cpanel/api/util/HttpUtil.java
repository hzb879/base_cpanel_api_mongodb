package com.swk.cpanel.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MultiValueMap;

import com.swk.cpanel.api.util.component.JsonUtil;

public class HttpUtil {
	
	private static final String SERVER_IP=getMyServerIp();
	
	/**
	 * 把http请求输入转化为字符串
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getHttpInputStreamAsString(HttpServletRequest request) throws Exception{
		InputStream inStream= request.getInputStream();
		ByteArrayOutputStream outSteam= new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
		return outSteam.toString("utf-8");
	}
	/**
	 * 获取客户端真实ip
	 * @param request
	 * @return
	 */
	public static String getClientIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
	}  
	
	/**
	 * 获取服务器端ip地址	
	 * @return
	 */
	public static String getServerIp(){  
		return SERVER_IP;
	}
	
	
	/**
	 * 通过paramsMap取出其中的键值对来构造url查询参数 如?a=b&c=d
	 * @param params
	 * @return
	 */
	public static String createQueryParams(MultiValueMap<String, String> params){
		StringBuilder sb=new StringBuilder("?");
		for(Map.Entry<String, List<String>> entry: params.entrySet()){
			for(String val:entry.getValue()){
				if(val == null || val.equals(""))
					continue;
				sb.append(entry.getKey()).append("=").append(val).append("&");
			}
		}
		return sb.substring(0, sb.length()-1);
	}
	
	/**
	 * 通过paramsMap取出其中的键值对来构造url查询参数 如?a=b&c=d
	 * @param params
	 * @return
	 */
	public static String createQueryParams(Map<String, String> params){
		StringBuilder sb=new StringBuilder("?");
		for(Map.Entry<String,String> entry: params.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return sb.substring(0, sb.length()-1);
	}
	
	/**
	 * 获取服务器本地ip地址
	 * @return
	 */
	private static String getMyServerIp(){  
		 String serverIp ="";
	        // 根据网卡取本机配置的IP  
	        Enumeration<NetworkInterface> allNetInterfaces;  //定义网络接口枚举类  
	        try {  
	            allNetInterfaces = NetworkInterface.getNetworkInterfaces();  //获得网络接口  
	            InetAddress ip = null; //声明一个InetAddress类型ip地址  
	            while (allNetInterfaces.hasMoreElements()) //遍历所有的网络接口  
	            {  
	                NetworkInterface netInterface = allNetInterfaces.nextElement();  
	                Enumeration<InetAddress> addresses = netInterface.getInetAddresses(); //同样再定义网络地址枚举类  
	                while (addresses.hasMoreElements())  
	                {  
	                    ip = addresses.nextElement();  
	                    if (ip != null && (ip instanceof Inet4Address)) //InetAddress类包括Inet4Address和Inet6Address  
	                    {  
	                      if(!ip.getHostAddress().equals("127.0.0.1")){
	                          serverIp= ip.getHostAddress();  
	                      } 
	                    }   
	                }  
	            } 
	        } catch (SocketException e) {  
	            e.printStackTrace();  
	        }
	        return serverIp;  
	}  
	
	public static void generateJsonResponse(HttpServletResponse response,Object responseData) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		try(PrintWriter writer = response.getWriter()){
			writer.write(JsonUtil.createJson(responseData));
		}
	}
	
	
}
