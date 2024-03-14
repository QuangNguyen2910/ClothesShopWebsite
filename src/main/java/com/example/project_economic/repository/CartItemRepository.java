package com.example.project_economic.repository;

import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {

    List<CartItemEntity>findByUser(UserEntity user);
//    @Query(value = "select * from cart_items as c where c.user_id=?1 and c.size =:size and c.color =:color",nativeQuery = true)
//    CartItemEntity findByUserAndProductAttribute(UserEntity user, ProductEntity product, String size, String color);
//    @Query(value = "select * from cart_items as c where c.user_id = ?1 and c.size = ?2 and c.color = ?3", nativeQuery = true)
//    CartItemEntity findByUserAndProductAttribute(UserEntity user, ProductEntity product, String size, String color);

    @Query(value = "SELECT * FROM cart_items c WHERE c.user_id = :userId AND c.product_id = :productId AND c.size = :size AND c.color = :color", nativeQuery = true)
    CartItemEntity findByUserAndProductAttribute(@Param("userId") Long userId, @Param("productId") Long productId, @Param("size") String size, @Param("color") String color);


    @Query(value = "select count(c.id) from cart_items as c where c.user_id=?1",nativeQuery = true)
    Long countCart(Long userId);
    @Query(value = "select count(c.id) from cart_items as c where c.product_id=?1 and c.user_id =?2",nativeQuery = true)
    Long countCartByProductIdAndUserId(Long productId,Long userId);
    
    @Modifying
    @Transactional
    @Query(value = "delete from cart_items as c where c.user_id=:userId",nativeQuery = true)
    void deleteAllCartByUserId(@Param("userId") Long userId);

}
