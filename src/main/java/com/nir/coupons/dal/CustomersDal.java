package com.nir.coupons.dal;

import com.nir.coupons.dto.Customer;
import com.nir.coupons.dto.CustomerDetails;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomersDal {
    public void createCustomer(Customer customer) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "INSERT INTO customers (id,name,address,phone,amount_of_kids) VALUES(?,?,?,?,?)";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());

            if (customer.getAddress() != null) {
                preparedStatement.setString(3, customer.getAddress());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }

            if (customer.getPhone() != null) {
                preparedStatement.setString(4, customer.getPhone());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            if (customer.getAmountOfKids() != null) {
                preparedStatement.setInt(5, customer.getAmountOfKids());
            } else {
                preparedStatement.setNull(5, 0);
            }

            // Executing the update
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }

    }

    public void deleteCustomer(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager


            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE FROM customers WHERE id = ?";

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
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to delete customer " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<CustomerDetails> getCustomers() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CustomerDetails> customersDetails = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT  c.name,c.address,c.amount_of_kids,c.phone,u.username\n" +
                    "from customers c\n" +
                    "join users u \n" +
                    "on c.id = u.id ";
            preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerDetails customerDetails = extractCustomerDetailsFromResultSet(resultSet);
                customersDetails.add(customerDetails);
            }
            return customersDetails;

        } catch (Exception e) {
            e.printStackTrace();

            throw new ServerException(ErrorType.GENERAL_ERROR, e, "error in customer dal");

        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private CustomerDetails extractCustomerDetailsFromResultSet(ResultSet resultSet) throws ServerException {
        try {
            String userName = resultSet.getString("username");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("phone");
            String address = null;
            if (resultSet.getObject("address") != null) {
                address = resultSet.getString("address");
            }
            Integer amountOfKids = null;
            if (resultSet.getObject("amount_of_kids") != null) {
                amountOfKids = resultSet.getInt("amount_of_kids");
            }
            CustomerDetails customerDetails = new CustomerDetails(name,userName,address,phone,amountOfKids);
            return customerDetails;

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get customer");
        }
    }

    public void updateCustomer(Customer customer) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "UPDATE customers SET name = ?, address = ?, amount_of_kids = ?, phone = ?  WHERE id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, customer.getName());
            if (customer.getAddress() != null) {
                preparedStatement.setString(2, customer.getAddress());
            } else {
                preparedStatement.setNull(2, 0);
            }
            if (customer.getAmountOfKids() != null) {
                preparedStatement.setInt(3, customer.getAmountOfKids());
            } else {
                preparedStatement.setNull(3, 0);
            }
            if (customer.getPhone() != null) {
                preparedStatement.setString(4, customer.getPhone());
            } else {
                preparedStatement.setNull(4, 0);
            }
            preparedStatement.setInt(5, customer.getId());

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

    public CustomerDetails getCustomerDetails(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CustomerDetails customerDetails = null;

        try {
            connection = JdbcUtils.getConnection();

            String sqlStatement = "SELECT  c.name,c.address,c.amount_of_kids,c.phone,u.username\n" +
                    "from customers c\n" +
                    "join users u \n" +
                    "on c.id = u.id  where c.id= ?\n ";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            customerDetails = extractCustomerDetailsFromResultSet(resultSet);

            return customerDetails;


        } catch (Exception e) {
            e.printStackTrace();

            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get customer  " + id);

        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}
