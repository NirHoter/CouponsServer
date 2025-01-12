package com.nir.coupons.dal;

import com.nir.coupons.dto.Company;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompaniesDal {    כאן עצרתי בפרויקט שלב 2 . נשאר זה ומטה

//asdfghj
    // adasffgd
    //asdfgh
    //asdfghjk

    public int createCompany(Company company) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "INSERT INTO companies (name, address, phone) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, company.getName());
            if (company.getAddress() != null) {
                preparedStatement.setString(2, company.getAddress());
            } else {
                preparedStatement.setNull(2, Types.NULL);
            }
            if (company.getPhone() != null) {
                preparedStatement.setString(3, company.getPhone());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }


            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
            throw new ServerException(ErrorType.GENERAL_ERROR);

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCompany(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager

            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "DELETE FROM companies WHERE id = ?";

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

    public List<Company> getCompanies() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Company> companies = new ArrayList<>();
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id, name, address, phone FROM companies";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Company company = extractCompanyFromResultSet(resultSet);
                companies.add(company);
            }
            return companies;

        } catch (SQLException e) {

            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get companies");

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private Company extractCompanyFromResultSet(ResultSet resultSet) throws ServerException {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String phone = resultSet.getString("phone");

            Company company = new Company(id, name, address, phone);

            return company;

        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "Failed to get company");
        }
    }

    public void updateCompany(Company company) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            // ID is defined as a primary key and auto incremented
            String sqlStatement = "UPDATE companies SET name = ?, address = ?, phone = ?  WHERE id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);

            // Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, company.getName());
            if (company.getAddress() != null) {
                preparedStatement.setString(2, company.getAddress());
            } else {
                preparedStatement.setNull(2, Types.NULL);
            }
            if (company.getPhone() != null) {
                preparedStatement.setString(3, company.getPhone());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }
            preparedStatement.setInt(4, company.getId());

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

    public Company getCompany(int id) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Company company = null;
        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query
            String sqlStatement = "SELECT id,name,address,phone FROM companies where id = ?";

            // Combining between the syntax and our connection
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                company = extractCompanyFromResultSet(resultSet);
            }

            return company;

        } catch (Exception e) {
            e.printStackTrace();
            // If there was an exception in the "try" block above, it is caught here and
            // notifies a level above.
            throw new ServerException(ErrorType.GENERAL_ERROR, e, "failed to get company " + id);

        } finally {
            // Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}
