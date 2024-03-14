package com.example.project_economic.impl;

import com.example.project_economic.dto.CategoryDto;
import com.example.project_economic.dto.ProductCategoryDTO;
import com.example.project_economic.dto.ProductDto;
import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.repository.ProductRepository;
import com.example.project_economic.response.PageProductResponse;
import com.example.project_economic.response.ProductResponse;
import com.example.project_economic.service.CategoryService;
import com.example.project_economic.service.ProductService;
import com.example.project_economic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private ImageUpload imageUpload;
    @Override
    public List<ProductResponse> findAll() {
        List<ProductEntity>productEntities=this.productRepository.findAll();
        return productEntities.stream().map(this::enity_to_response).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllIsActived() {
        List<ProductEntity> productEntities = this.productRepository.findAllProductIsAvtived();
        List<ProductResponse>productDtos=productEntities.stream().map((product)->{
            return this.enity_to_response(product);
        }).collect(Collectors.toList());
        return productDtos;
    }
//    @Override
//    public List<ProductResponse> findAllIsActived(int pageSize, Integer pageNumber) {
//    pageNumber -=1;
//    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("product_id").descending());
//    Page<ProductEntity>productEntities=this.productRepository.findAllProductIsAvtived(pageable);
//        List<ProductResponse>productDtos=productEntities.stream().map((product)->{
//            return this.enity_to_response(product);
//        }).collect(Collectors.toList());
//        return productDtos;
//    }
    @Override
    public List<ProductResponse> findAllIsActived(int pageSize, Integer pageNumber) {
        pageNumber -=1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("product_id").descending());
        List<Long> ids = this.productRepository.findListIdProduct();
        Page<ProductEntity>productEntities=this.productRepository.findAllProductIsAvtived(pageable, ids);
        List<ProductResponse>productDtos=productEntities.stream().map((product)->{
            return this.enity_to_response(product);
        }).collect(Collectors.toList());
        return productDtos;
    }


    @Override
    public ProductResponse save(ProductDto productDto, MultipartFile file) {
        ProductEntity productEntity=new ProductEntity();
        try{
            productEntity.setImage_type(file.getContentType());
            productEntity.setData(file.getBytes());
            productEntity.setName(productDto.getName());
            productEntity.setCategoryEntity(this.categoryService.findById(productDto.getCategoryId()));
            productEntity.setDescription(productDto.getDescription());
            productEntity.setCostPrice(productDto.getCostPrice());
            productEntity.setSalePrice(productDto.getSalePrice());
            productEntity.setImage(productDto.getImage());
            productEntity.setCurrentQuantity(productDto.getCurrentQuantity());
            productEntity.setLikes(0);
            productEntity.setIs_actived(true);
            productEntity.setIs_deteted(false);
            productEntity.setUserEntity(this.userService.findUserById(productDto.getSellerId()));
//            productEntity.setSellerId();
            ProductEntity productSaved=this.productRepository.save(productEntity);

            return enity_to_response(productSaved);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    private ProductResponse enity_to_response(ProductEntity productSaved) {
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
        productResponse.setUserEntity(productSaved.getUserEntity());
        return productResponse;
    }

    @Override
    public ProductResponse update(ProductDto productDto, Long productId,MultipartFile file) {

        try{
            ProductEntity product=this.productRepository.findById(productId).get();
            if(file!=null){
                product.setData(file.getBytes());
            }
            if(productDto.getName()!=null&&!productDto.getName().equals("")){
                product.setName(productDto.getName());
            }
            if(productDto.getDescription()!=null&&!productDto.getDescription().equals("")){
                product.setDescription(productDto.getDescription());
            }
            if(productDto.getCostPrice()!=null){
                product.setCostPrice(productDto.getCostPrice());
            }
            if(productDto.getSalePrice()!=null){
                product.setSalePrice(productDto.getSalePrice());
            }
            if(productDto.getCurrentQuantity()!=null){
                product.setCurrentQuantity(productDto.getCurrentQuantity());
            }
            ProductEntity productSaved=this.productRepository.save(product);
            return this.enity_to_response(productSaved);
        }catch (Exception exception){
            System.out.println("error update image");
        }
        return null;
    }

    @Override
    public ProductResponse findById(Long productId) {
        ProductEntity productFind=this.productRepository.findById(productId).get();
        return this.enity_to_response(productFind);
    }

    @Override
    public void deleteById(Long productId) {
        ProductEntity product=this.productRepository.findById(productId).get();
        product.setIs_deteted(true);
        product.setIs_actived(false);
        this.productRepository.save(product);
    }

    @Override
    public void activeById(Long productId) {
        ProductEntity product=this.productRepository.findById(productId).get();
        product.setIs_deteted(false);
        product.setIs_actived(true);

        this.productRepository.save(product);
    }

    @Override
    public void likeById(Long productId) {
        ProductEntity product=this.productRepository.findById(productId).get();
        try{
            product.setLikes(product.getLikes() + 1);
        }
        catch (Exception e){
            product.setLikes(1);
        }
        this.productRepository.save(product);
    }

    @Override
    public List<Object[]> countProductByCategoryId() {
        return this.productRepository.countProductByCategoryId();
    }

    @Override
    public ProductEntity findByIdInRescontroller(Long id) {
        return this.productRepository.findById(id).get();
    }

    @Override
    public PageProductResponse findAllPagination(int pageNumber, int pageSize) {
        PageProductResponse pageProductResponse=new PageProductResponse();
        Pageable pageable= PageRequest.of(pageNumber-1,pageSize);
        List<Long> ids = this.productRepository.findListIdProduct();
        Page<ProductEntity> productEntities=this.productRepository.findAllProductIsAvtived(pageable, ids);
        List<ProductResponse>productResponses=productEntities.stream()
                .map(product -> {
                    return this.enity_to_response(product);
                }).collect(Collectors.toList());
        int totalElement=(int)productEntities.getTotalElements();
        int lastPage = totalElement % pageSize == 0 ? totalElement/pageSize : (int)totalElement/pageSize + 1;
        if(lastPage == 0) lastPage = 1;
        pageProductResponse.setTotalPage(productEntities.getTotalPages() > 0 ? productEntities.getTotalPages() : 1);
        pageProductResponse.setCurrentPage(productEntities.getNumber());
        pageProductResponse.setLastPage(lastPage);
        pageProductResponse.setPageSize(pageSize);
        pageProductResponse.setProductResponses(productResponses);
        return pageProductResponse;
    }

    @Override
    public PageProductResponse findAllProductByKeyword(String keyword, Integer pageSize, Integer offsetNumber) {
        PageProductResponse pageProductResponse=new PageProductResponse();

        List<ProductEntity>productEntities=this.productRepository.findAllProductByKeyword('%'+keyword+'%',pageSize,offsetNumber);
        pageProductResponse.setProductResponses(
                productEntities.stream()
                        .map(product -> {
                            return this.enity_to_response(product);
                        }).collect(Collectors.toList())
        );
        int totalProduct=this.productRepository.countProductByKeyword('%'+keyword+'%');
        int totalPage=totalProduct%pageSize==0?totalProduct/pageSize:(int)totalProduct/pageSize+1;
        if(totalPage == 0) totalPage = 1;
        int currentPage=offsetNumber/pageSize+1;
        int lastPage=totalPage;
        pageProductResponse.setTotalPage(totalPage);
        pageProductResponse.setCurrentPage(currentPage);
        pageProductResponse.setLastPage(lastPage);
        pageProductResponse.setPageSize(pageSize);
        return pageProductResponse;
    }

    @Override
    public PageProductResponse findAllProductByCategory(Long id,int pageSize,int offsetNumber) {
        PageProductResponse pageProductResponse=new PageProductResponse();
        List<ProductEntity>productEntities=this.productRepository.findAllProductByCategory(id,pageSize,offsetNumber);
        pageProductResponse.setProductResponses(
                productEntities.stream()
                        .map(product -> {
                            return this.enity_to_response(product);
                        }).collect(Collectors.toList())
        );
        int totalProduct=this.productRepository.countProductByCategory(id);
        int totalPage=totalProduct%pageSize==0?totalProduct/pageSize:(int)totalProduct/pageSize+1;
        if(totalPage == 0) totalPage = 1;
        int currentPage=offsetNumber/pageSize+1;
        int lastPage=totalPage;
        pageProductResponse.setTotalPage(totalPage);
        pageProductResponse.setCurrentPage(currentPage);
        pageProductResponse.setLastPage(lastPage);
        pageProductResponse.setPageSize(pageSize);
        return pageProductResponse;
    }

    @Override
    public PageProductResponse findAllProductByCostPrice(int first_price, int second_price, int pageSize, int offsetNumber) {
        PageProductResponse pageProductResponse=new PageProductResponse();
        List<ProductEntity>productEntities=this.productRepository.findAllProductByPriceAndPagination(first_price,second_price,pageSize,offsetNumber);
        pageProductResponse.setProductResponses(
                productEntities.stream()
                        .map(product -> {
                            return this.enity_to_response(product);
                        }).collect(Collectors.toList())
        );
        int totalProduct=this.productRepository.countProductByPrice(first_price,second_price);
        int totalPage=totalProduct%pageSize==0?totalProduct/pageSize:(int)totalProduct/pageSize+1;
        if(totalPage == 0) totalPage = 1;
        int currentPage=offsetNumber/pageSize+1;
        int lastPage=totalPage;
        pageProductResponse.setTotalPage(totalPage);
        pageProductResponse.setCurrentPage(currentPage);
        pageProductResponse.setLastPage(lastPage);
        pageProductResponse.setPageSize(pageSize);
        return pageProductResponse;
    }

    @Override
    public List<ProductResponse> findAllProductByUserId(Long userId) {
        List<ProductEntity>productEntities=this.productRepository.findAllProductByUserId(userId);
        List<ProductResponse>productDtos=productEntities.stream().map((product)->{
            return this.enity_to_response(product);
        }).collect(Collectors.toList());
        return productDtos;
    }
//    @Override
//    public List<ProductResponse> findAllIsActived() {
//        List<ProductEntity>productEntities=this.productRepository.findAllProductIsAvtived();
//        List<ProductResponse>productDtos=productEntities.stream().map((product)->{
//            return this.enity_to_response(product);
//        }).collect(Collectors.toList());
//        return productDtos;
//    }

}
