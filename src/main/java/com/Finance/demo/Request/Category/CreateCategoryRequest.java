package com.Finance.demo.Request.Category;

import lombok.Data;


@Data
public class CreateCategoryRequest {
    private String name;
    private Long userId;
}
