package com.tnc.proxy.netty.common;

import java.util.List;

public class NettyVO {
	/**
	 * Client -> Proxy로 요청할 때 수신하는 항목  
	 * 요청 httpMethod는 POST만 가능
	 * */
	
	private String requestUri;
	private String httpMehtod;
	private String className;
	private String method;
	private List<String> parameters;
		
	/**
	 * 외부 연동으로 요청할 때 연결할 Host, Port, Path 
	 * */
	
	private String externalRequestHost;
	private int externalRequestPort;
	private String externalRequestPath;
	
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getHttpMehtod() {
		return httpMehtod;
	}
	public void setHttpMehtod(String httpMehtod) {
		this.httpMehtod = httpMehtod;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	public String getExternalRequestHost() {
		return externalRequestHost;
	}
	public void setExternalRequestHost(String externalRequestHost) {
		this.externalRequestHost = externalRequestHost;
	}
	public int getExternalRequestPort() {
		return externalRequestPort;
	}
	public void setExternalRequestPort(int externalRequestPort) {
		this.externalRequestPort = externalRequestPort;
	}
	public String getExternalRequestPath() {
		return externalRequestPath;
	}
	public void setExternalRequestPath(String externalRequestPath) {
		this.externalRequestPath = externalRequestPath;
	}
	
}
