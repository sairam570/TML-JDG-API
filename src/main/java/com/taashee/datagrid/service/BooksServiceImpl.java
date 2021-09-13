package com.taashee.datagrid.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taashee.datagrid.model.Books;
import com.taashee.datagrid.repository.BooksRepository;

@Service
public class BooksServiceImpl implements BooksService
{
	
@Autowired
BooksRepository booksRepository;

public List<Books> getAllBooks() 
{
List<Books> books = new ArrayList<Books>();
booksRepository.findAll().forEach(books1 -> books.add(books1));
return books;
}

public void saveOrUpdate(Books books) 
{
booksRepository.save(books);
}
}