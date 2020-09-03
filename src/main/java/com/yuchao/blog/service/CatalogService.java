package com.yuchao.blog.service;

import java.util.List;

import com.yuchao.blog.domain.Catalog;

public interface CatalogService {


	Catalog saveCatalog(Catalog c);

	Catalog findCatalogByCatalog(String catalog);

	List<Catalog> findCatalog();

	Catalog findCatalogById(Long id);

}
