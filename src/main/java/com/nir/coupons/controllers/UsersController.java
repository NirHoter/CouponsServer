package com.nir.coupons.controllers;

import com.nir.coupons.dto.User;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.UserLogic;
import com.nir.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UserLogic userLogic;
    private UserLoginDetails userLoginDetails;
    private VerificationLogic emailLogic;


    @Autowired
    public UsersController(UserLogic userLogic, VerificationLogic emailLogic) {
        this.userLogic = userLogic;
        this.emailLogic = emailLogic;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws Exception {
        return userLogic.login(userLoginData);
    }


    @PostMapping("/createByAdmin")
    public void createUserFromAdmin(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);

        if (!userLoginDetails.getUserType().equals("Admin")) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE, "Only an admin can create a user");
        }

        this.userLogic.createUser(user);
    }

    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        user.setId(userLoginDetails.getUserId());
        user.setUserType(userLoginDetails.getUserType());
        user.setCompanyId(userLoginDetails.getCompanyId());
        this.userLogic.updateUser(user);
    }

    //TODO עובד
    @DeleteMapping("/DeleteMyUser")
    public void deleteMyUser(@RequestHeader("Authorization") String token) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.userLogic.deleteMyUser(userLoginDetails.getUserId());
    }

    //TODO עובד
    @DeleteMapping("/AdminDelete/{id}")
    public void deleteUserByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int userId) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.userLogic.deleteUserByAdmin(userId, userLoginDetails.getUserType());
    }


    @GetMapping("/usersList")
    public Page<User> getAllUsersToAdmin(@RequestHeader("Authorization") String token ,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.userLogic.getUsers(userLoginDetails.getUserType(),page,size);
    }

    //TODO עובד
    @GetMapping
    public User getMyUser(@RequestHeader("Authorization") String token) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.userLogic.getUser(userLoginDetails.getUserId());
    }
}
