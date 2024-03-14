package com.example.project_economic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "name",unique = true)
    private String name;
    private String createdDate;
    private boolean is_deleted = false;
    private boolean is_actived=true;
    public CategoryEntity(String name){
        this.name=name;
        this.is_actived=true;
        this.is_deleted=false;
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss,dd/MM/yyyy");
        this.createdDate=dateTimeFormatter.format(LocalDateTime.now());
    }
    public void updateTime(){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss,dd/MM/yyyy");
        this.createdDate=dateTimeFormatter.format(LocalDateTime.now());
    }
}
