package com.nir.coupons.dal;

import java.sql.*;

import com.nir.coupons.dto.User;
import com.nir.coupons.dto.UserLoginData;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.entity.UserEntity;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IUsersDal extends CrudRepository<UserEntity, Integer> {

    @Query("Select u from UserEntity u WHERE username = :userName and password")
    UserEntity login(@Param("userName") String userName, @Param("password") String password);

    @Query("Select count(u.id)>0 from UserEntity u WHERE username = :userName")
    boolean isUserNameUnique(@Param("userName") String userName);

}
//
//
//    public int createUser(User user) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "INSERT INTO users (username, password, user_type, company_id) VALUES(?,?,?,?)";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setString(1, user.getUserName());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setString(3, user.getUserType());
//            if (user.getCompanyId() != null) {
//                preparedStatement.setInt(4, user.getCompanyId());
//            } else {
//                preparedStatement.setNull(4, 0);
//            }
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
//    }
//
//    public void deleteUser(int id) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "DELETE FROM users WHERE id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setInt(1, id);
//
//            // Executing the update
//            preparedStatement.executeUpdate();
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "We failed to delete" + id);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public List<User> getUsers() throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        List<User> users = new ArrayList<>();
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT id, username, password, user_type, company_id FROM users";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                User user = extractUserFromResultSet(resultSet);
//                users.add(user);
//            }
//            return users;
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
//    private User extractUserFromResultSet(ResultSet resultSet) throws ServerException {
//        try {
//            int id = resultSet.getInt("id");
//            String username = resultSet.getString("username");
//            String password = resultSet.getString("password");
//            String userType = resultSet.getString("user_type");
//            Integer companyId = null;
//            if (resultSet.getObject("company_id") != null) {
//                companyId = resultSet.getInt("company_id");
//            }
//
//            User user = new User(id, username, password, userType, companyId);
//
//            return user;
//
//        } catch (SQLException e) {
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get user ");
//        }
//    }
//
//    public void updateUser(User user) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "UPDATE users SET username = ?, password = ?,user_type = ?, company_id= ?  WHERE id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setString(1, user.getUserName());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setString(3, user.getUserType());
//            preparedStatement.setInt(4, user.getId());
//
//            if (user.getCompanyId() != null) {
//                preparedStatement.setLong(4, user.getCompanyId());
//            } else {
//                preparedStatement.setNull(4, Types.NULL);
//            }
//            preparedStatement.setInt(5, user.getId());
//
//            // Executing the update
//            preparedStatement.executeUpdate();
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
//    public User getUser(int id) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        User user = null;
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT id,username,password,user_type,company_id FROM users where id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, id);
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//
//            user = extractUserFromResultSet(resultSet);
//            return user;
//
//        } catch (Exception e) {
//
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get user" + id);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public UserLoginDetails login(UserLoginData userLoginData) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        UserLoginDetails userLoginDetails = null;
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "SELECT user_type,company_id FROM users WHERE username = ? AND password = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setString(1, userLoginData.getUserName());
//            preparedStatement.setString(2, userLoginData.getPassword());
//
//            // Executing the query
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//            userLoginDetails = extractUserLoginDetailsFromResultSet(resultSet);
//            return userLoginDetails;
//
//        } catch (Exception e) {
//
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get user" + userLoginData.getUserName());
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public void deleteUsersByCompanyId(int companyId) throws ServerException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            String sqlStatement = "DELETE FROM users WHERE company_id = ?";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, companyId);
//
//            // Executing the query
//            preparedStatement.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, String.valueOf(companyId));
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    public User getUserByUserName(String userName) throws ServerException {
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        User user = null;
//        try {
//            // Establish a connection from the connection manager
//            connection = JdbcUtils.getConnection();
//
//            // Creating the SQL query
//            // ID is defined as a primary key and auto incremented
//            String sqlStatement = "SELECT id,username,password,user_type,company_id FROM users \n" +
//                    " where username = ? ";
//
//            // Combining between the syntax and our connection
//            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            // Replacing the question marks in the statement above with the relevant data
//            preparedStatement.setString(1, userName);
//
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            // If the user was created, returning the user ID
//            if (resultSet.next()) {
//                user = extractUserFromResultSet(resultSet);
//            }
//            return user;
//
//        } catch (Exception e) {
//
//            // If there was an exception in the "try" block above, it is caught here and
//            // notifies a level above.
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, userName);
//
//        } finally {
//            // Closing the resources
//            JdbcUtils.closeResources(connection, preparedStatement);
//        }
//    }
//
//    private UserLoginDetails extractUserLoginDetailsFromResultSet(ResultSet resultSet) throws ServerException {
//        try {
//            int id = resultSet.getInt("id");
//            String userType = resultSet.getString("user_type");
//            Integer companyId = null;
//            if (resultSet.getObject("company_id") != null) {
//                companyId = resultSet.getInt("company_id");
//            }
//
//            UserLoginDetails userLoginDetails = new UserLoginDetails(id, userType, companyId);
//
//            return userLoginDetails;
//
//        } catch (SQLException e) {
//            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get user ");
//        }
//    }
//}
