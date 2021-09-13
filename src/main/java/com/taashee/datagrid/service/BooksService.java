package com.taashee.datagrid.service;

import java.util.List;

import com.taashee.datagrid.model.Books;


public interface BooksService {

	List<Books> getAllBooks();

	void saveOrUpdate(Books books);

}
