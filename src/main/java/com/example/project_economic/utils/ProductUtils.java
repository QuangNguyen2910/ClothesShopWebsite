package com.example.project_economic.utils;

import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.response.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ProductUtils {
    public ProductResponse enity_to_response(ProductEntity productSaved){
        ProductResponse productResponse=new ProductResponse();
        productResponse.setId(productSaved.getId());
        productResponse.setName(productSaved.getName());
        productResponse.setDescription(productSaved.getDescription());
        productResponse.setCostPrice(productSaved.getCostPrice());
        productResponse.setSalePrice(productSaved.getSalePrice());
        productResponse.setCurrentQuantity(productSaved.getCurrentQuantity());
        productResponse.setLikes(productSaved.getLikes());
        productResponse.setImage(productSaved.getImage());
        //url
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/products")
                .path("/get/")
                .path(productSaved.getId().toString())
                .toUriString();
        productResponse.setImage_url(url);
        productResponse.setImage_type(productSaved.getImage_type());
        productResponse.setCategoryEntity(productSaved.getCategoryEntity());
        productResponse.setIs_deleted(productSaved.getIs_deteted());
        productResponse.setIs_actived(productSaved.getIs_actived());
        return productResponse;
    }
}
