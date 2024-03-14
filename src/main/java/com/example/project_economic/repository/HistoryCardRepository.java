package com.example.project_economic.repository;

import com.example.project_economic.entity.HistoryCard;
import com.example.project_economic.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryCardRepository extends JpaRepository<HistoryCard,Long> {
    List<HistoryCard> findByUser(UserEntity user);
}
