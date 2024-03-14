package com.example.project_economic.service;


import com.example.project_economic.dto.CommentDTO;
import com.example.project_economic.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDTO>findByPostId(Long postId);
    Comment addComment(Comment comment,Long userId,Long productId);
    Comment addReplyComment(Comment comment,Long userId,Long productId,Long parentId);
}
