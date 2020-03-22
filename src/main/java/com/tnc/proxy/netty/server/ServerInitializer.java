package com.tnc.proxy.netty.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tnc.proxy.util.ConfigManager;
import com.tnc.proxy.util.DefineCode;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslProvider;

/**
 * <pre>
 * 서버 역할을 하는 Channel에 요청과 응답을 하는 Hadler를 모아 놓은 Class
 * </pre>
 * @author MJ
 * @since 2020-03-22
 */
public class ServerInitializer extends ChannelInitializer<Channel> {
	private Logger logger = LoggerFactory.getLogger(ServerInitializer.class);
	private final String listenType;
	
	public ServerInitializer(String listenType) {
		this.listenType = listenType;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// HTTPS라면 SslHandler를 추가한다.
		if(DefineCode.LISTEN_TYPE_HTTPS.equals(listenType)) {
			SslContext context = getSSLContext();
			SSLEngine engine = context.newEngine(ch.alloc());
			
			pipeline.addFirst("ssl", new SslHandler(engine));
		}

		pipeline.addLast("codec", new HttpServerCodec());							// Http요청을 Decode하고 Http응답을 Encode하는 Codec 추가
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
		pipeline.addLast("serverOutboundHandler", new ServerOutboundHandler());		// 응답을 처리하는 Handler
		pipeline.addLast("serverInboundHandler", new ServerInboundHandler());		// 요청을 처리하는 Handler
	}

	private SslContext getSSLContext() throws SSLException {
		ConfigManager configManager = ConfigManager.getInstance();
		SslContext context = null;

		// file 등록
		File certFile = new File(configManager.getValue(DefineCode.HTTP_SSL_CERT_NAME));
		File keyFile = new File(configManager.getValue(DefineCode.HTTP_SSL_KEY_NAME));

		List<File> files = new ArrayList<File>();
		files.add(certFile);
		files.add(keyFile);

		// file 존재하는지 확인
		for (File file : files) {
			if (file.exists() == false) {
				logger.error("SSL 파일 미존재(" + file.getName() + ")");
//				throw new UserDefineException("", "SSL 파일 미존재(" + file.getName() + ")");
			}
		}
		
		context = SslContextBuilder.forServer(files.get(0), files.get(1)).sslProvider(SslProvider.JDK).build();

		return context;
	}
}
