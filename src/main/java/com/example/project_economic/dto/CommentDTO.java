package com.example.project_economic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private Long productId;
    private String content;
    private Long userId;
    private String userName;
    private Long commentParentId;
    private Long step;
    private String dateCreate;
    private Long star;
}
