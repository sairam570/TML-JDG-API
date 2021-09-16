package com.taashee.datagrid.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RemoteConfigurer {

	
	@Value("${jdg.hostname}")
	private String host;
	
	@Value("${jdg.port}")
	private int port;
	
	@Value("${jdg.username}")
	private String username;
	
	@Value("${jdg.password}")
	private String password;
	
	@Bean
	public RemoteCacheManager remoteCacheManager() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer()
		.host(host)
		//.host("127.0.0.1")
		//.port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
		.port(port)
		.security().authentication()
		.username(username)
		.password(password)
		//.username("myuser")
        //.password("changeme")
		.marshaller(new JavaSerializationMarshaller())
				.addJavaSerialWhiteList("java.util.List", "java.util.ArrayList");

		return new RemoteCacheManager(builder.build());
	}
}
