package cn.cie.controller;

import cn.cie.entity.User;
import cn.cie.services.GameService;
import cn.cie.services.UserService;
import cn.cie.utils.MsgCenter;
import cn.cie.utils.Result;
import cn.cie.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@Controller
public class CommonController extends AbstractController {

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = "login")
    public String login() {
        String referer = getReferer();
        // if user is already logged in and status is normal, redirect to previous page
        if (userHolder.getUser() != null && userHolder.getUser().getStat().equals(User.STAT_OK)) {
            return "redirect:" + referer;
        }
        return "login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public Result login(String username, String password,
                        @RequestParam(value = "remember", defaultValue = "false", required = false) boolean remember,
                        HttpServletResponse response) {
        String referer = getReferer();
        // if user is already logged in, redirect to previous page
        if (userHolder.getUser() != null && userHolder.getUser().getStat().equals(User.STAT_OK)) {
            return Result.fail(MsgCenter.OK, referer);
        }
        Result result = userService.login(username, password, remember, this.getRemoteIp(), this.getUserAgent());
        if (result.isSuccess()) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("referer", referer);
            // add cookie to response, all future requests will include the cookie
            Cookie cookie = new Cookie("token", (String) result.getData());
            cookie.setPath("/");
            if (remember) {
                cookie.setMaxAge(60 * 60 * 24 * 7);
            } else {
                cookie.setMaxAge(60 * 60 * 24);
            }
            response.addCookie(cookie);
            return Result.success(data);
        } else {
            return result;
        }
    }

    @PostMapping(value = "logout")
    @ResponseBody
    public Result logout() {
        String token = null;
        // get token from request
        if (this.getRequest().getCookies() != null) {
            for (Cookie cookie : this.getRequest().getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        return userService.logout(token);
    }

    @GetMapping(value = "register")
    public String register() {
        String referer = getReferer();
        if (userHolder.getUser() != null) {
            return "redirect:" + referer;
        }
        return "register";
    }

    @PostMapping(value = "register")
    @ResponseBody
    public Result register(User user, HttpServletResponse response) {
        Result result = userService.register(user);
        String pwd = user.getPassword();
        // auto login after successful registration, frontend redirects to validation page
        if (result.isSuccess()) {
            login(user.getUsername(), pwd, false, response);
            return Result.success();
        }
        return result;
    }

    /**
     * Get daily recommendations, randomly select 5 games, generated once daily
     *
     * @return
     */
    @PostMapping(value = "everyday")
    @ResponseBody
    public Result everyday() {
        return gameService.getRandomGames();
    }


    @GetMapping(value = "shoppingcart")
    public String shoppingcart() {
        return "shoppingcart";
    }

    /**
     * Latest 5 games, sorted by release time, stored in cache
     *
     * @return
     */
    @PostMapping(value = "newestgames")
    @ResponseBody
    public Result newestGames() {
        return gameService.newestGames();
    }

    /**
     * Latest 5 unreleased games, sorted by time, stored in cache
     *
     * @return
     */
    @PostMapping(value = "preupgames")
    @ResponseBody
    public Result preUpGames() {
        return gameService.preUpGames();
    }

    @PostMapping(value = "freegames")
    @ResponseBody
    public Result getFreeGames() {
        return gameService.getFreeGames();
    }

    @GetMapping(value = "search")
    public String search() {
        return "search";
    }

    @PostMapping(value = "search")
    @ResponseBody
    public Result search(String info) {
        return gameService.search(info);
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
