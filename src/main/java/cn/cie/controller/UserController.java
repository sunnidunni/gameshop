package cn.cie.controller;

import cn.cie.entity.User;
import cn.cie.services.UserService;
import cn.cie.utils.MsgCenter;
import cn.cie.utils.Result;
import cn.cie.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserHolder userHolder;

    @GetMapping(value = "validate")
    public String validate() {
        String referer = getReferer();
        if (userHolder.getUser().getStat().equals(User.STAT_OK)) {
            return "redirect:" + referer;
        }
        return "validate";
    }

    @PostMapping(value = "validate")
    @ResponseBody
    public Result validate(String code) {
        if (userHolder.getUser() == null) {
            return Result.fail(MsgCenter.USER_NOT_LOGIN);
        }
        if (userHolder.getUser().getStat().equals(User.STAT_OK)) {    // user has already been validated
            return Result.fail(MsgCenter.USER_VALIDATED);
        }
        Result result = userService.validate(userHolder.getUser().getId(), code);
        if (result.isSuccess()) {
            return Result.success("/");
        }
        return result;
    }

    @PostMapping(value = "sendMail")
    @ResponseBody
    public Result sendMail() {
        if (userHolder.getUser() == null) {
            return Result.fail(MsgCenter.USER_NOT_LOGIN);
        }
        return userService.sendMail(userHolder.getUser());
    }

    @GetMapping(value = "personal")
    public String personal() {
        return "personal";
    }

    @PostMapping(value = "personal")
    @ResponseBody
    public Result getPersonInfo() {
        User user = userHolder.getUser();
        if (user == null) {
            return Result.fail(MsgCenter.USER_NOT_LOGIN);
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping(value = "update")
    public String update() {
        return "updateUserInfo";
    }

    @PostMapping(value = "update")
    @ResponseBody
    public Result update(User user) {
        return userService.updateUserInfo(user);
    }

    @GetMapping(value = "updatepassword")
    public String updatePassword() {
        return "updatepassword";
    }

    @PostMapping(value = "updatepassword")
    @ResponseBody
    public Result updatePassword(String password) {
        return userService.updatePassword(password);
    }

    @GetMapping(value = "findpassword")
    public String findPassword() {
        return "findpassword";
    }

    @PostMapping(value = "sendfetchpwdmail")
    @ResponseBody
    public Result sendFetchPwdMail(String email) {
        return userService.sendFetchPwdMail(email);
    }

    @PostMapping(value = "findpassword")
    @ResponseBody
    public Result findPassword(String password, String email, String code) {
        return userService.forgetPassword(password, email, code);
    }

    /**
     * Check if user is logged in, return redirect page if logged in, otherwise execute next logic
     * Before each login, get the referer link from request header
     * If empty (redirected from another site), should redirect to homepage
     * First login in the process calls this method to get redirect link, login failure stores referer in session
     * If redirected from login page, might be login error, but referer before login page is stored in session
     * If session has referer, login success redirects to referer and removes referer from session
     *
     * @return
     */
    private String getReferer() {
        String referer = null;
        String tmp = this.getRequest().getHeader("Referer");
        // if empty, not redirected from this site, should redirect to homepage
        if (tmp == null) {
            referer = "/";
        } else if (tmp.endsWith("/login")) {
            referer = (String) this.getSession().getAttribute("Referer");
        } else {
            referer = tmp;
        }
        this.getSession().setAttribute("Referer", referer);
        return referer;
    }

}
