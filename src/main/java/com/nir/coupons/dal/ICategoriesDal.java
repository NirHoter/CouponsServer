package com.nir.coupons.dal;

import com.nir.coupons.dto.Category;
import com.nir.coupons.entity.CategoryEntity;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ICategoriesDal extends JpaRepository<CategoryEntity, Integer> {

    @Query("SELECT com.nir.coupons.dto.Category(c.id, c.name) FROM CategoryEntity c")
    List<Category> getCategories();

    @Query("SELECT count(c.id)>0 FROM CategoryEntity c WHERE c.name = :name")
    boolean isNotUniqueNameCategory(@Param("name") String name);


//   public int createCategory(Category category) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "INSERT INTO categories (name) VALUES(?)";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setString(1, category.getName());
//
//            // Executing the update
//            preparedStatement.executeUpdate();
//
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//
//            // If the user was created, returning the user ID
//            if (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                return id;
//            }
//            throw new ServerException(ErrorType.GENERAL_ERROR);
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//   }


    //
//    public void deleteCategory(int id) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//
//
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "DELETE FROM categories WHERE id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setInt(1, id);
//
//
//            // Executing the update
//            preparedStatement.executeUpdate();
//
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }


//
//    public List<Category> getCategories() throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        List<Category> categories = new ArrayList<>();
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT id, name FROM categories";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Category category = extractCategoryFromResultSet(resultSet);
//                categories.add(category);
//            }
//            return categories;
//
//        } catch (SQLException e) {
//
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    private Category extractCategoryFromResultSet(ResultSet resultSet) throws ServerException {
//        try {
//
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            Category category = new Category(id, name);
//            return category;
//
//        } catch (SQLException e) {
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get category");
//        }
//    }
//
//    public void updateCategory(Category category) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "UPDATE categories SET name = ?  WHERE id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setString(1, category.getName());
//            preparedStatement.setInt(2, category.getId());
//
//            // Executing the update
//            preparedStatement.executeUpdate();
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to be updated " + category.getId());
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public Category getCategory(int id) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        Category category = null;
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT id,name FROM categories where id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, id);
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                category = extractCategoryFromResultSet(resultSet);
//            }
//            return category;
//
//        } catch (Exception e) {
//
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get category " + id);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public Category getUserByUserName(String name) throws ServerException {
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        Category category = null;
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT id,name \n" + "FROM categories \n" + "where name = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setString(1, name);
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                category = extractCategoryFromResultSet(resultSet);
//            }
//
//            return category;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get category " + name);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
}
