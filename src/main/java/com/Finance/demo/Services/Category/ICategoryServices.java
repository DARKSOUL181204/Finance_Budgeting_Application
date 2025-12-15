package com.Finance.demo.Services.Category;

import com.Finance.demo.Model.Category;
import com.Finance.demo.Request.Category.CreateCategoryRequest;
import com.Finance.demo.Request.Category.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryServices {
    Category getCategoryById(Long Id);
    Category getCategoryByName(String name, Long userId);
    Category createCategory(CreateCategoryRequest request);
    void deleteCategory(Long Id);
    Category updateCategory(UpdateCategoryRequest request, Long Id);
    List<Category> getAllCategories(Long userId);

}
