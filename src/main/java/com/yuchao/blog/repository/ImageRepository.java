package com.yuchao.blog.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
