package com.nir.coupons.dal;

import com.nir.coupons.dto.Coupon;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CouponsDal {
    public int createCoupon(Coupon coupon) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "INSERT INTO Coupons (title, description, price, company_id, category_id, start_date, end_date, amount, image_url) VALUES(?,?,?,?,?,?,?,?,?)";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setString(2, coupon.getDescription());
            preparedStatement.setFloat(3, coupon.getPrice());
            preparedStatement.setInt(4, coupon.getCompanyId());
            preparedStatement.setInt(5, coupon.getCategoryId());
            preparedStatement.setDate(6, coupon.getStartDate());
            preparedStatement.setDate(7, coupon.getEndDate());
            preparedStatement.setInt(8, coupon.getAmount());
            if (coupon.getImageURL() != null) {
                preparedStatement.setString(9, coupon.getImageURL());
            } else {
                preparedStatement.setNull(9, 0);
            }

            // Executing the update
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            // If the user was created, returning the user ID
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } catch (Exception e) {
            //e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get coupon " + coupon.getId());

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCoupon(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager

            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE FROM coupons WHERE id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setInt(1, id);


            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            //e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to delete coupon " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Coupon> getCoupons() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Coupon> coupons = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id, title, description, price, company_id, category_id, start_date, end_date, amount, image_url FROM coupons";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Coupon coupon = extractCouponFromResultSet(resultSet);
                coupons.add(coupon);
            }
            return coupons;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get coupons");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private Coupon extractCouponFromResultSet(ResultSet resultSet) throws ServerException {
        try {
            // Retrieving the data from the ResultSet
            //id,name,title ,description,price,company_id,category_id,start_date,end_date,amount,image_url
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            float price = resultSet.getInt("price");
            int companyId = resultSet.getInt("company_id");
            int categoryId = resultSet.getInt("category_id");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            int amount = resultSet.getInt("amount");
            String imageUrl = resultSet.getString("image_url");


            // Creating an object that contains all the row data
            Coupon coupon = new Coupon(id, title, description, price, companyId, categoryId, startDate, endDate, amount, imageUrl);

            // Returning the object we created
            return coupon;

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get coupon");
        }
    }

    public void updateCoupon(Coupon coupon) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "UPDATE coupons SET title = ?, description = ?, price= ?, company_id = ?, category_id = ?, start_date = ?, end_date = ?, amount = ?, image_url = ?  WHERE id = ?";
            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setString(2, coupon.getDescription());
            preparedStatement.setFloat(3, coupon.getPrice());
            preparedStatement.setInt(4, coupon.getCompanyId());
            preparedStatement.setInt(5, coupon.getCategoryId());
            preparedStatement.setDate(6, coupon.getStartDate());
            preparedStatement.setDate(7, coupon.getEndDate());
            preparedStatement.setInt(8, coupon.getAmount());
            if (coupon.getImageURL() != null) {
                preparedStatement.setString(9, coupon.getImageURL());
            } else {
                preparedStatement.setNull(9, 0);
            }
            preparedStatement.setInt(10, coupon.getId());

            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            //e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public Coupon getCouponById(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Coupon couponsToResult = new Coupon();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id,title ,description,price,company_id,category_id," +
                    "start_date,end_date,amount,image_url FROM coupons where id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            couponsToResult = extractCouponFromResultSet(resultSet);

            return couponsToResult;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get coupons " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCouponsByCompanyId(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "DELETE FROM coupons WHERE company_id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);

            // Executing the query
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCouponsByCategoryId(int categoryId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "DELETE FROM coupons WHERE category_id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, categoryId);

            // Executing the query
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteExpiredCoupons() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager

            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE FROM coupons WHERE end_date < CURDATE()";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data

            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            //e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to delete coupon ");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Coupon> getCouponsByCompanyId(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Coupon> coupons = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id,title,description,price,company_id,category_id,start_date,end_date,amount,image_url \n" +
                    "FROM coupons\n" +
                    "where company_id = ?\n";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Coupon coupon = extractCouponFromResultSet(resultSet);
                coupons.add(coupon);
            }
            return coupons;

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Coupon> getCouponsBelowPrice(int price) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Coupon> coupons = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id,title,description,price,company_id,category_id,start_date,end_date,amount,image_url \n" +
                    "FROM coupons\n" +
                    "where price < ?\n";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, price);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Coupon coupon = extractCouponFromResultSet(resultSet);
                coupons.add(coupon);
            }
            return coupons;

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}
