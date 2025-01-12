package com.nir.coupons.logic;

import com.nir.coupons.dal.CategoriesDal;
import com.nir.coupons.dal.ICategoriesDal;
import com.nir.coupons.dto.Category;
import com.nir.coupons.dto.User;
import com.nir.coupons.entity.CategoryEntity;
import com.nir.coupons.entity.UserEntity;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesLogic {

    private ICategoriesDal categoriesDal;

    @Autowired
    public CategoriesLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public void createCategories(Category category, String userType) throws ServerException {
        validateCategories(category, userType);
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        categoriesDal.save(categoryEntity);
    }

    private CategoryEntity convertCategoryToCategoryEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category.getId(), category.getName());
        return categoryEntity;
    }
}


    public void updateCategories (Category category, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        validateCategories(category);
        categoriesDal.updateCategory(category);
    }

    public void deleteCategory ( int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        categoriesDal.deleteCategory(id);
    }

    public List<Category> getCategories () throws ServerException {
        return categoriesDal.getCategories();
    }

    public Category getCategory ( int id) throws ServerException {
        return categoriesDal.getCategory(id);
    }


    private void validateCategories (Category category) throws ServerException {
        if (category.getName() == null || category.getName().length() > 45) {
            throw new ServerException(ErrorType.INVALID_CATEGORY_NAME, category.toString());
        }
        if (!isCategoriesNameUnique(category.getName())) {
            throw new ServerException(ErrorType.INVALID_CATEGORY_NAME, category.toString());
        }
    }

    private boolean isCategoriesNameUnique (String categoryName) throws ServerException {

        Category categoryToCheck = categoriesDal.getUserByUserName(categoryName);

        if (categoryToCheck == null) {
            return true;
        }
        return false;
    }

}
