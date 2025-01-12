package com.nir.coupons.controllers;


import com.nir.coupons.dto.Category;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.CategoriesLogic;
import com.nir.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesLogic categoriesLogic;
    private UserLoginDetails userLoginDetails;

    @Autowired
    public CategoriesController(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    @PostMapping
    public void createCategory(@RequestHeader ("Authorization") String token, @RequestBody Category category) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.createCategories(category, userLoginDetails.getUserType());
    }

    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable("id")int id) throws ServerException {
        return this.categoriesLogic.getCategory(id);
    }

    @PutMapping
    public void updateCategory(@RequestHeader ("Authorization") String token, @RequestBody Category category) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.updateCategories(category, userLoginDetails.getUserType());
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@RequestHeader ("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.deleteCategory(id, userLoginDetails.getUserType());
    }

    @GetMapping
    public Page<Category> getCategories(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.categoriesLogic.getCategories(page, size);
    }

}
