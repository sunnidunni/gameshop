package cn.cie.services;

import cn.cie.entity.User;
import cn.cie.utils.Result;


public interface UserService {

    /**
     * Register
     * @param user
     * @return
     */
    Result register(User user);

    /**
     * Send verification code to user's registered email
     * @param user
     * @return
     */
    Result sendMail(User user);

    /**
     * Email verification
     * @param uid
     * @param code
     * @return
     */
    Result validate(Integer uid, String code);

    /**
     * Login
     * If "Remember me" is selected during login, token persists for 7 days, otherwise 1 day
     * On successful login, token is stored in database and cache with 1-day expiration
     * For each request, interceptor checks cache first, then database if not found
     * @param username  username
     * @param password  password
     * @param remember  whether to stay logged in (token lifecycle is 7 days)
     * @return
     */
    Result login(String username, String password, boolean remember, String ip, String device);

    /**
     * Logout
     * @return
     */
    Result logout(String token);

    /**
     * Update user information
     * @param user
     * @return
     */
    Result updateUserInfo(User user);

    /**
     * Update password
     * @param password
     * @return
     */
    Result updatePassword(String password);

    /**
     * Forgot password
     * @param password
     * @param code
     * @return
     */
    Result forgetPassword(String password, String email, String code);

    /**
     * Send verification code to email for password recovery
     * @param email
     * @return
     */
    Result sendFetchPwdMail(String email);

    /**
     * Delete users who haven't verified
     */
    void delNotValidateUser();

    /**
     * Delete expired tokens
     */
    void expireToken();
}
