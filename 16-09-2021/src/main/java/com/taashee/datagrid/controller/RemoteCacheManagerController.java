package com.taashee.datagrid.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.taashee.datagrid.model.Books;
import com.taashee.datagrid.service.BooksService;

/**
 * @author Sai Ram B
 * @created 07 Sep 2021 13:18
 *
 *          <pre>
 *
 *          </pre>
 */
@RestController
public class RemoteCacheManagerController extends BaseController{

	@Autowired
	RemoteCacheManager cacheManager;
	
	@Autowired
	BooksService booksService;

	@RequestMapping(value = "/create/{cacheName}", method = RequestMethod.POST)
	public String createChache(@PathVariable("cacheName") String cacheName) {
		
		cacheManager.start();
		// Create cache
		cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE).getOrCreateCache(cacheName,
				DefaultTemplate.LOCAL);
		// Obtain the remote cache
		RemoteCache<String, String> cache = cacheManager.getCache(cacheName);
		List<Books> books =  booksService.getAllBooks();
		for (Books book : books) {
			System.out.println(book.toString());
			
			cache.put(String.valueOf(book.getBookid()), new Gson().toJson(book));
		}
		/*
		 * // read key,values from request body Set<String> keys = cacheMap.keySet();
		 * for (String key : keys) { /// Store a value cache.put(key,
		 * cacheMap.get(key)); System.out.println(key+"------------"+cacheMap.get(key));
		 * }
		 */
		// Retrieve the value and print it out
		//System.out.printf("key = %s\n", cache.get("Sai"));
		
		cacheManager.stop();

		return cacheName + " cache is created successfully";

	}

	@RequestMapping(value = "/retrieve/{cacheName}", method = RequestMethod.GET)
	public Map<Object, Object> retrieveChache(@PathVariable("cacheName") String cacheName) {
		cacheManager.start();
		
		// Retrieve the value from cache
		RemoteCache<Object, Object> cache = cacheManager.getCache(cacheName);
		
		System.out.println("=============");
		//cacheManager.stop();

		return cache.entrySet().stream()
			     .collect(Collectors.toMap(Map.Entry::getKey, e -> (String)e.getValue()));

	}

@RequestMapping(value = "/remove/keys/{cacheName}", method = RequestMethod.DELETE)
	public String removeChacheKeys(@PathVariable("cacheName") String cacheName) {
		cacheManager.start();
		
		// Retrieve the value from cache
		RemoteCache<Object, Object> cache = cacheManager.getCache(cacheName);
		
		cache.clear();
		System.out.println("=============");
		cacheManager.stop();

		return "Keys are removed for "+cacheName ;

	}
	
	@RequestMapping(value = "/remove/{key}/{cacheName}", method = RequestMethod.DELETE)
	public String removeChacheKey(@PathVariable("key") String key,@PathVariable("cacheName") String cacheName) {
		cacheManager.start();
		
		// Retrieve the value from cache
		RemoteCache<Object, Object> cache = cacheManager.getCache(cacheName);
		
		cache.remove(key);
		System.out.println("=============");
		cacheManager.stop();

		return "Key is removed for "+cacheName ;

	}
	
	
}
