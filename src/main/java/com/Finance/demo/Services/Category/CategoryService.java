package com.Finance.demo.Services.Category;

import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.Exceptions.AlreadyExistException;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.CategoryRepository;
import com.Finance.demo.Repository.LedgerEntryRepository;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Request.Category.CreateCategoryRequest;
import com.Finance.demo.Request.Category.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryServices{

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LedgerEntryRepository ledgerRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryDto getCategoryById(Long Id) {
        Category category =  categoryRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not Found !!") );
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryByName(String name, Long userId) {
        Category category =  categoryRepository.findByNameAndUserId(name ,userId)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not found !!"));
        return modelMapper.map(category, CategoryDto.class);

    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequest request) {
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
                   Category category1 = categoryRepository.save(category);
                    return modelMapper.map(category1,CategoryDto.class);
                })
                .orElseThrow(()->new AlreadyExistException("category already exist !!"));
    }

    @Override
    public void deleteCategory(Long Id) {
        Category category = categoryRepository.findById(Id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found "));
        if (category.getUser() == null) {
            throw new RuntimeException("You cannot delete system default categories!");
        }
        // is transaction exist category is not deleted
        boolean isUsed = ledgerRepository.existsByCategoryId(Id);
        if (isUsed) {
            throw new RuntimeException("Cannot delete this category: It is used in existing transactions.");
        }
        categoryRepository.deleteById(Id);
    }

    @Override
    public CategoryDto updateCategory(UpdateCategoryRequest request, Long Id) {
        Category category = categoryRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found !!"));
        if (category.getUser() == null) {
            throw new RuntimeException("You cannot edit system default categories!");
        }
        if(request.getName() != null && !request.getName().isEmpty()){
            category.setName(request.getName());
        }
        categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId)
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

}
