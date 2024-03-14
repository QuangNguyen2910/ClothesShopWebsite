package com.example.project_economic.service;

import com.example.project_economic.dto.CountProuductDto;
import com.example.project_economic.dto.ProductCategoryDTO;
import com.example.project_economic.dto.ProductDto;
import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.response.PageProductResponse;
import com.example.project_economic.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponse>findAll();
    List<ProductResponse>findAllIsActived();
    List<ProductResponse>findAllIsActived(int pageSize, Integer pageNumber);
    List<ProductResponse> findAllProductByUserId(Long userId);
    ProductResponse save(ProductDto productDto, MultipartFile file);
    ProductResponse update(ProductDto productDto, Long productId,MultipartFile file);
    ProductResponse findById(Long productId);

    void deleteById(Long productId);
    void activeById(Long productId);
    void likeById(Long productId);


    List<Object[]> countProductByCategoryId();
    ProductEntity findByIdInRescontroller(Long id);
    PageProductResponse findAllPagination(int pageNumber, int pageSize);
    PageProductResponse findAllProductByKeyword(String keyword,Integer pageSize,Integer offsetNumber);
    PageProductResponse findAllProductByCategory(Long id,int pageSize,int pageNumber);
    PageProductResponse findAllProductByCostPrice(int first_price,int second_price,int pageSize,int offsetNumber);

}
