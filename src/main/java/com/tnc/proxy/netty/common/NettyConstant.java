package com.tnc.proxy.netty.common;

public class NettyConstant {
	// 외부 연동 Util Class의 package명 (Java Reflection에 필요)
	public static final String EXTERNAL_PACKAGE = "com.tnc.proxy.external.";
	
	// 비지니스 Error
	public static final String BUSINESS_ERROR_000 = "올바른 요청이 아닙니다. 관리자에게 문의하세요";
	public static final String BUSINESS_ERROR_001 = "Http RequestBody가 비어있습니다.";
	public static final String BUSINESS_ERROR_002 = "Http RequestBody에 허용되지 않은 파라미터가 존재합니다.";
	public static final String BUSINESS_ERROR_003 = "HttpMethod는 POST만 가능합니다.";
	public static final String BUSINESS_ERROR_004 = "해당 클래스가 존재하지 않습니다.";
	public static final String BUSINESS_ERROR_005 = "해당 메서드가 존재하지 않습니다.";
	public static final String BUSINESS_ERROR_006 = "해당 메서드의 파라미터정보가 잘못되었습니다.";
	public static final String BUSINESS_ERROR_007 = "해당 클래스의 생성자 정보가 잘못되었습니다.";

}
