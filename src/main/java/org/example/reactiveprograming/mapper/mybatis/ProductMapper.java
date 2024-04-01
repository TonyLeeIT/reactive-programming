package org.example.reactiveprograming.mapper.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.reactiveprograming.model.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product")
    List<Product> findAll();

    @Select("SELECT * FROM product where id = '1'")
    Product findOne();

    @Insert("INSERT INTO product (id, name, qty, price) VALUES (#{id}, #{name}, #{qty}, #{price})")
    void insertProduct(Product product);
}
