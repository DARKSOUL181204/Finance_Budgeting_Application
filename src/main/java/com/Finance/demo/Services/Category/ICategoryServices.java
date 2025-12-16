package com.Finance.demo.Services.Category;

import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Request.Category.CreateCategoryRequest;
import com.Finance.demo.Request.Category.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryServices {
    CategoryDto getCategoryById(Long Id);
    CategoryDto getCategoryByName(String name, Long userId);
    CategoryDto createCategory(CreateCategoryRequest request);
    void deleteCategory(Long Id);
    CategoryDto updateCategory(UpdateCategoryRequest request, Long Id);
    List<CategoryDto> getAllCategories(Long userId);

}
