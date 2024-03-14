package com.example.project_economic.repository;

import com.example.project_economic.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "select * from comments as c where c.product_id=?1 and c.step=1 ",nativeQuery = true)
    List<Comment>findCommentFirstInProduct(Long productId);
    List<Comment>findByProductId(Long productId);
}
