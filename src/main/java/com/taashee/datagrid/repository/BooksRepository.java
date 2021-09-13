package com.taashee.datagrid.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taashee.datagrid.model.Books;
public interface BooksRepository extends JpaRepository<Books, Integer>
{
}
