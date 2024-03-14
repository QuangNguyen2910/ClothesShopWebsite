package com.example.project_economic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private Long step;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "content")
    private String content;
    private Long star;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> childComments = new ArrayList<>();

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "dislike_count")
    private int dislikeCount;

}

