package com.tnc.proxy.netty.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tnc.proxy.netty.common.NettyConstant;
import com.tnc.proxy.netty.common.NettyException;
import com.tnc.proxy.netty.common.NettyUtil;
import com.tnc.proxy.netty.common.NettyVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * <pre>
 * RequestBody로 들어온 Class와 Method를 실행하고 결과를 OutboundHandler에게 넘겨주는 Class
 * </pre>
 * @author MJ
 * @since 2020-03-22
 */
public class ExternalReqeustHandler {
	Logger logger = LoggerFactory.getLogger(ExternalReqeustHandler.class);
	
	private NettyVO nettyVO;
	private ChannelHandlerContext ctx;
	
	public ExternalReqeustHandler(NettyVO nettyVO, ChannelHandlerContext ctx) {
		this.nettyVO = nettyVO;
		this.ctx = ctx;
	}

	public void request() {
		String resMsg = "";
		
		try {
//			Class<?> c = Class.forName(NettyConstant.EXTERNAL_PACKAGE + nettyVO.getClassName());
			Class<?> c = Class.forName(nettyVO.getClassName());
			Method methods[] = c.getDeclaredMethods();
			Method invokeMethod = null;
			
			for(Method method : methods) {
				if(method.getName().equals(nettyVO.getMethod())) {
					invokeMethod = method;
					break;
				}
			}
			
			if(invokeMethod == null) {
				throw new  NettyException(NettyConstant.BUSINESS_ERROR_005);
			}

			resMsg = (String) invokeMethod.invoke(c.newInstance(), nettyVO.getParameters().toArray());
			
		} catch (ClassNotFoundException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_004;
			e.printStackTrace();
		} catch (NettyException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_005;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_000;
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_006;
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_000;
			e.printStackTrace();
		} catch (SecurityException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_007;
			e.printStackTrace();
		} catch (InstantiationException e) {
			resMsg = NettyConstant.BUSINESS_ERROR_007;
			e.printStackTrace();
		} finally {
			logger.info(resMsg);
			FullHttpResponse response = NettyUtil.createFullHttpResponse(HttpResponseStatus.OK, resMsg);
			ctx.write(response.retain());
		}
	}
}
