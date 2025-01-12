package com.nir.coupons.dal;

import com.nir.coupons.dto.Purchase;
import com.nir.coupons.dto.PurchaseDetails;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchasesDal {
    public int createPurchase(Purchase purchase) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "INSERT INTO purchases (customer_id, coupon_id, amount, timestamp) VALUES(?,?,?,?)";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setInt(1, purchase.getCustomerId());
            preparedStatement.setInt(2, purchase.getCouponId());
            preparedStatement.setInt(3, purchase.getAmount());
            preparedStatement.setTimestamp(4, purchase.getTimestamp());


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
            throw new ServerException(ErrorType.GENERAL_ERROR, e, purchase.toString());

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchase(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE FROM purchases WHERE id = ?";

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
            throw new ServerException(ErrorType.GENERAL_ERROR, e);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Purchase> getPurchases() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Purchase> purchases = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id, customer_id, coupon_id, amount, timestamp FROM purchases";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = extractPurchaseFromResultSet(resultSet);
                purchases.add(purchase);
            }
            return purchases;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private Purchase extractPurchaseFromResultSet(ResultSet resultSet) throws ServerException {
        try {
            int id = resultSet.getInt("id");
            int customerId = resultSet.getInt("customer_id");
            int couponId = resultSet.getInt("coupon_id");
            Integer amount = resultSet.getInt("amount");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");

            Purchase purchase = new Purchase(id, customerId, couponId, amount, timestamp);

            return purchase;

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e);
        }
    }

    public void updatePurchase(Purchase purchase) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "UPDATE purchases SET customer_id = ?, coupon_id = ?, amount = ?, timestamp = ?  WHERE id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setInt(1, purchase.getCustomerId());
            preparedStatement.setInt(2, purchase.getCouponId());
            preparedStatement.setInt(3, purchase.getAmount());
            preparedStatement.setTimestamp(4, purchase.getTimestamp());
            preparedStatement.setInt(5, purchase.getId());

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

    public Purchase getPurchase(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Purchase purchase = null;
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id,customer_id,coupon_id,amount,timestamp FROM purchases where id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            purchase = extractPurchaseFromResultSet(resultSet);
            return purchase;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "filed to get purchase" + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<PurchaseDetails> getPurchasesByCustomerId(int customerId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PurchaseDetails> purchaseToResult = new ArrayList<>();

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT p.id,p.coupon_id,p.amount,p.timestamp,cou.end_date,(p.amount*cou.price) AS total_price,cou.title,cou.description, cou.image_url \n" +
                    "FROM purchases p \n" +
                    "join coupons cou on p.coupon_id = cou.id \n" +
                    "where p.customer_id =  ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                PurchaseDetails purchasesDetails = extractPurchaseFromResultSetPurchasesDetails(resultSet);
                purchaseToResult.add(purchasesDetails);
            }
            return purchaseToResult;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private PurchaseDetails extractPurchaseFromResultSetPurchasesDetails(ResultSet resultSet) throws ServerException {
        try {
            int id = resultSet.getInt("id");
            int couponId = resultSet.getInt("coupon_id");
            int amount = resultSet.getInt("amount");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            float totalPrice = resultSet.getFloat("total_price");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String imageUrl = null;
            if (resultSet.getString("image_url") != null) {
                imageUrl = resultSet.getString("image_url");
            }
            Date date = resultSet.getDate("end_date");

            PurchaseDetails purchasesDetails = new PurchaseDetails(id, couponId, amount, timestamp, totalPrice, title, description, imageUrl, date);

            return purchasesDetails;

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get purchases");
        }
    }

    public PurchaseDetails getPurchaseDetailsById(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PurchaseDetails purchaseDetails = null;

        try {
            connection = JdbcUtils.getConnection();

            String sqlStatement = "SELECT p.id,p.coupon_id,p.amount,p.timestamp,cou.end_date,(p.amount*cou.price) AS total_price,cou.title,cou.description, cou.image_url FROM purchases p join coupons cou on p.coupon_id = cou.id where p.id=?";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            purchaseDetails = extractPurchaseFromResultSetPurchasesDetails(resultSet);

            return purchaseDetails;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get purchase detail " + id);

        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<PurchaseDetails> getPurchasesByCategoryOfCompany(int companyId, String categoryName) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PurchaseDetails> purchaseToResult = new ArrayList<>();

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT p.id,p.coupon_id,p.amount,p.timestamp,cou.end_date,(p.amount*cou.price) AS total_price,cou.title,cou.description, cou.image_url \n" +
                    "FROM purchases p \n" +
                    "join coupons cou on p.coupon_id = cou.id \n" +
                    "join categories cat on cat.id = cou.category_id\n" +
                    "where cou.company_id=? and cat.name=?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, categoryName);
            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                PurchaseDetails purchasesDetails = extractPurchaseFromResultSetPurchasesDetails(resultSet);
                purchaseToResult.add(purchasesDetails);
            }
            return purchaseToResult;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<PurchaseDetails> getPurchasesByCategoryOfCustomer(int customerId, String categoryName) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PurchaseDetails> purchaseToResult = new ArrayList<>();

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT p.id,p.coupon_id,p.amount,p.timestamp,cou.end_date,(p.amount*cou.price) AS total_price,cou.title,cou.description, cou.image_url \n" +
                    "FROM purchases p \n" +
                    "join coupons cou on p.coupon_id = cou.id \n" +
                    "join categories cat on cat.id = cou.category_id\n" +
                    "where p.customer_id=? and cat.name=?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, categoryName);
            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                PurchaseDetails purchasesDetails = extractPurchaseFromResultSetPurchasesDetails(resultSet);
                purchaseToResult.add(purchasesDetails);
            }
            return purchaseToResult;

        } catch (Exception e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchasesByCompanyId(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented ;
            String sqlStatement = "DELETE p FROM purchases p \n" +
                    "JOIN coupons c ON p.coupon_id = c.id WHERE c.company_id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to be delete id = " + companyId);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchasesByCouponId(int couponId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented ;
            String sqlStatement = "DELETE FROM purchases WHERE coupon_id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);
            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to be delete id = " + couponId);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchasesByUserId(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented ;
            String sqlStatement = "DELETE p FROM purchases p WHERE  customer_id = ? ";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to be delete id = " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchasesOfExpiredCoupons() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager


            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE p\n" +
                    "FROM purchases p\n" +
                    "JOIN coupons c ON p.coupon_id = c.id\n" +
                    "WHERE end_date < CURDATE()";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data


            // Executing the update
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to delete");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<PurchaseDetails> getPurchasesDetailsByPrice(int customerId, int price) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PurchaseDetails> purchasesDetails = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            String sqlStatement = "SELECT id,title,description,price,company_id,category_id,start_date,end_date,amount,image_url\n" +
                    "FROM coupons\n" +
                    "WHERE customer_id = ? AND price <= ? \n" +
                    "ORDER BY price ASC";
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, price);
            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                PurchaseDetails purchaseDetails = extractPurchaseFromResultSetPurchasesDetails(resultSet);
                purchasesDetails.add(purchaseDetails);

            }
            return purchasesDetails;

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get purchase detail ");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deletePurchasesByCategoryId(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented ;
            String sqlStatement = "delete p FROM purchases p \n" +
                    "join  coupons c\n" +
                    "on p.coupon_id = c.id \n" +
                    "where c.category_id = ? ";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to be delete id = " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}
