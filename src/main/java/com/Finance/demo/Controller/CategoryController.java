package com.Finance.demo.Controller;

import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Request.Category.CreateCategoryRequest;
import com.Finance.demo.Request.Category.UpdateCategoryRequest;
import com.Finance.demo.Response.ApiResponse;
import com.Finance.demo.Services.Category.ICategoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/category")
public class CategoryController {

    private final ICategoryServices categoryServices;

    @GetMapping("/get/{Id}")
    ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long Id) {
        CategoryDto category = categoryServices.getCategoryById(Id);
        return ResponseEntity.ok(new ApiResponse("Category Found Successfully", category));
    }
    @GetMapping("/get/name/{userId}")
    ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name, @PathVariable Long userId) {
        CategoryDto category = categoryServices.getCategoryByName(name, userId);
        return ResponseEntity.ok(new ApiResponse("Category Found Successfully", category));
    }
    @PostMapping("/create")
    ResponseEntity<ApiResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryDto category = categoryServices.createCategory(request);
        return ResponseEntity.ok(new ApiResponse("Category created Successfully", category));
    }
    @DeleteMapping("/delete/{Id}")
    ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long Id) {
        categoryServices.deleteCategory(Id);
        return ResponseEntity.ok(new ApiResponse("Category created Successfully", null));
    }
    @PostMapping("/update/{Id}")
    ResponseEntity<ApiResponse> updateCategory(@RequestBody UpdateCategoryRequest request,@PathVariable Long Id){
        CategoryDto category = categoryServices.updateCategory(request,Id);
        return ResponseEntity.ok(new ApiResponse("Category created Successfully", category));
    }
    @GetMapping("/all/{userId}")
    ResponseEntity<ApiResponse> getAllCategories(@PathVariable Long userId){
        List<CategoryDto> categories = categoryServices.getAllCategories(userId);
        return ResponseEntity.ok(new ApiResponse("Category created Successfully", categories));
    }

}
