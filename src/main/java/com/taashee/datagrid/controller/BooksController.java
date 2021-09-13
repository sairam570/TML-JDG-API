package com.taashee.datagrid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taashee.datagrid.model.Books;
import com.taashee.datagrid.service.BooksService;

@RestController
public class BooksController extends BaseController {
	
	@Autowired
	BooksService booksService;
	
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public List<Books> getAllBooks() 
	{
		System.out.println("===============");
	return booksService.getAllBooks();
	}

	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public String saveBook(@RequestBody Books books) 
	{
		booksService.saveOrUpdate(books);
	return "Record inserted...";
	}
	

}
