package com.example.project_economic.repository;

import com.example.project_economic.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query(value = "select * from categories where is_actived=true and is_deleted=false;",nativeQuery = true)
    List<CategoryEntity>findByActived();
}
