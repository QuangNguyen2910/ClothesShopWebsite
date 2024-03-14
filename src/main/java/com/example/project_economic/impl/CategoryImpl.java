package com.example.project_economic.impl;

import com.example.project_economic.dto.CategoryDto;
import com.example.project_economic.entity.CategoryEntity;
import com.example.project_economic.repository.CategoryRepository;
import com.example.project_economic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryEntity> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        CategoryEntity categoryEntitySave=new CategoryEntity(categoryEntity.getName());
        return this.categoryRepository.save(categoryEntitySave);
    }

    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public CategoryEntity update(CategoryEntity categoryEntity, Long id) {
        CategoryEntity categoryEntityFind=new CategoryEntity();
        try{
            categoryEntityFind=this.categoryRepository.findById(id).get();
            if(categoryEntity.getName()!=null){
                categoryEntityFind.setName(categoryEntity.getName());
            }
            categoryEntityFind.updateTime();
            categoryEntityFind.set_deleted(categoryEntity.is_deleted());
            categoryEntityFind.set_actived(categoryEntity.is_actived());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return categoryRepository.save(categoryEntityFind);
    }

    @Override
    public void deleteById(Long id) {
        CategoryEntity categoryEntity=categoryRepository.findById(id).get();
        categoryEntity.set_deleted(true);
        categoryEntity.set_actived(false);
//        categoryEntity.updateTime();
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void enableById(Long id) {
        CategoryEntity category = categoryRepository.getById(id);
        category.set_actived(true);
        category.set_deleted(false);
//        category.updateTime();
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> findAllByActived() {
        return this.categoryRepository.findByActived();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return null;
    }
}
