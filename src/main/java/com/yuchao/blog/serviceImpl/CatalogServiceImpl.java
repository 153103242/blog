package com.yuchao.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.Catalog;
import com.yuchao.blog.repository.CatalogRepository;
import com.yuchao.blog.service.CatalogService;

@Service
public class CatalogServiceImpl  implements CatalogService{

	@Autowired
	private CatalogRepository catalogRepository;

	public Catalog findCatalogByCatalog(String catalog) {
		return catalogRepository.findCatalogByCatalog(catalog);
	}

	public Catalog saveCatalog(Catalog c) {
		return catalogRepository.save(c);
	}

	public List<Catalog> findCatalog() {
		return (List<Catalog>) catalogRepository.findAll();
	}

	public Catalog findCatalogById(Long id) {
		return catalogRepository.findById(id).get();
	}

	
	
}
