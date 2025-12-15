package com.Finance.demo.Services.Category;

import com.Finance.demo.Exceptions.AlreadyExistException;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.CategoryRepository;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Request.Category.CreateCategoryRequest;
import com.Finance.demo.Request.Category.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class categoryServices implements ICategoryServices{

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final UserRepository userRepository;


    @Override
    public Category getCategoryById(Long Id) {
        return categoryRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not Found !!") );
    }

    @Override
    public Category getCategoryByName(String name, Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Category Not found !!"));
    }

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        return Optional.ofNullable(request)
                .filter(category -> !categoryRepository.existsByNameAndUserId(request.getName(), request.getUserId()))
                .map(req ->{
                    Category category = new Category();
                    category.setName(req.getName());
                    if(req.getUserId() != null){
                    User user = userRepository.findById(req.getUserId())
                            .orElseThrow(()->new ResourceNotFoundException("User not found "));
                        category.setUser(user);
                    }
                   return categoryRepository.save(category);
                })
                .orElseThrow(()->new AlreadyExistException("category already exist !!"));
    }

    @Override
    public void deleteCategory(Long Id) {
        Category category = getCategoryById(Id);
        if (category.getUser() == null) {
            throw new RuntimeException("You cannot delete system default categories!");
        }
        categoryRepository.deleteById(Id);
    }

    @Override
    public Category updateCategory(UpdateCategoryRequest request, Long Id) {
        Category category = categoryRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found !!"));
        if(request.getName() != null && !request.getName().isEmpty()){
            category.setName(request.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }

}
