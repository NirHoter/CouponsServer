package com.nir.coupons.logic;

import com.nir.coupons.dal.CompaniesDal;
import com.nir.coupons.dal.IUsersDal;
import com.nir.coupons.dal.UsersDal;
import com.nir.coupons.dto.User;
import com.nir.coupons.dto.UserLoginData;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.entity.UserEntity;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.utils.JWTUtils;
import com.nir.coupons.utils.StatisticsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class UserLogic {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,}|co\\.il)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private IUsersDal usersDal;
    private CompaniesDal companiesDal;

    @Autowired
    public UserLogic(IUsersDal usersDal, CompaniesDal companiesDal) {
        this.usersDal = usersDal;
        this.companiesDal = companiesDal; // logic
    }

    public String login(UserLoginData userLoginData) throws Exception {
        UserEntity userEntity = usersDal.login(userLoginData.getUserName(), userLoginData.getPassword());
        if (userEntity == null) {
            throw new ServerException(ErrorType.UNAUTHORIZED); ///// TODO:CHACK THIS FUNCTION
        }
        StatisticsUtils.sendStatistics(userLoginData.getUserName(), "login");
        UserLogin userLoginDetails = new UserLogin(userEntity.getId(), userEntity.getUserType(), userEntity.getCompanyId());
        String token = JWTUtils.createJWT(userLoginDetails);
        return token;
    }

    //
//    public void deleteUsersByCompanyId(int companyId) throws ServerException {
//        usersDal.deleteUsersByCompanyId(companyId);
//    }
//
    public int createUser(User user) throws ServerException {
        validateUser(user);
        // TODO:
//        if (!isUserNameUnique(user)) {
//            throw new ServerException(ErrorType.USER_NAME_ALREADY_EXIST, user.toString());
//        }
        UserEntity userEntity = convertUserToUserEntity(user);
        usersDal.save(userEntity);
        return user.getId();
    }

    private UserEntity convertUserToUserEntity(User user) {
        UserEntity userEntity = new UserEntity(user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getUserType(),
                user.getCompanyId());
        return userEntity;
    }

    //
//    private boolean isUserNameUnique(User user) throws ServerException {
//        User userToCheck = usersDal.getUserByUserName(user.getUserName());
//        if (userToCheck == null) {
//            return true;
//        }
//        return false;
//    }
//
    public void updateUser(User user) throws ServerException {
        validateUser(user);
        Optional<UserEntity> userNameBeforeUpdate = usersDal.findById(user.getId());

        // Is present checks if the value is not null
        if (!userNameBeforeUpdate.isPresent()) {
            throw new ServerException("ץ[");
        }

        if (!userNameBeforeUpdate.get().getUserName().equals(user.getUserName())) {
            // TODO:
//            if (!isUserNameUnique(user)) {
//                throw new ServerException(ErrorType.USER_NAME_ALREADY_EXIST, user.toString());
//            }
        }
        UserEntity userEntity = convertUserToUserEntity(user);
        usersDal.save(userEntity);
    }

    //
    public List<User> getUsers() throws ServerException {
        List<UserEntity> users = (List<UserEntity>) usersDal.findAll();
    }

    //TODO:
//    public User getUser(int id, UserLoginDetails userLoginDetails) throws ServerException {
//        return usersDal.getUser(id);
//    }
//
    public void deleteUser(int id) throws ServerException {
        usersDal.deleteById(id);
    }

    private void validateUser(User user) throws ServerException {

        if ((user.getUserType() == "company") && (companiesDal.getCompany(user.getCompanyId()) == null)) {
            throw new ServerException(ErrorType.INVALID_COMPANY_ID, user.toString());
        }

        if (user.getUserName() == null) {
            throw new ServerException(ErrorType.INVALID_USER_NAME_LENGTH, user.toString());
        }

        if (user.getUserName().length() > 45) {
            throw new ServerException(ErrorType.INVALID_USER_NAME_LENGTH, user.toString());
        }

        if (user.getPassword() == null) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, user.toString());
        }

        if (user.getPassword().length() > 45) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, user.toString());
        }

        if (user.getUserType() == null) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, user.toString());
        }

        if (user.getUserType().length() > 45) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, user.toString());
        }

        if (user.getUserType() == "company" && (user.getCompanyId() == null || user.getCompanyId() <= 0)) {
            throw new ServerException(ErrorType.INVALID_COMPANY_ID, user.toString());
        }

        if (!isValidateEmail(user.getUserName())) {
            throw new ServerException(ErrorType.INVALID_USER_NAME, user.toString());
        }

        if (!user.getUserType().equals("Customer") && !user.getUserType().equals("Company") && !user.getUserType().equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, user.toString());
        }

        if (!user.getUserType().equals("Company") && user.getCompanyId() != null) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "only company can have a companyId");
        }
    }

    private boolean isValidateEmail(String username) {
        Matcher matcher = UserLogic.EMAIL_PATTERN.matcher(username);
        return matcher.matches();
    }
}
