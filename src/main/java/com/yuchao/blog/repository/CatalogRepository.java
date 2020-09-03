package com.yuchao.blog.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Catalog;

public interface CatalogRepository extends CrudRepository<Catalog, Long> {

	@Query(value = "select * from catalog where catalog	= ?1 " ,nativeQuery = true)
	Catalog findCatalogByCatalog(String catalog);

}
