package com.example.project_economic.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageProductResponse {
    private Integer totalPage;
    private Integer currentPage;
    private Integer lastPage;
    private Integer pageSize;
    private List<ProductResponse>productResponses;
}
