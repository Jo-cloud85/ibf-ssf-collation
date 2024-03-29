package vttp2023.ssf.day19.day19.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp2023.ssf.day19.day19.model.LoginAttempt;
import vttp2023.ssf.day19.day19.model.UserDetail;

@Controller
@RequestMapping
public class AuthenticationController {

    @GetMapping(path={"/", "/index.html"})
    public ModelAndView getIndex(HttpSession sess, @ModelAttribute UserDetail user, BindingResult binding) {

        LoginAttempt loginAttempt = new LoginAttempt(sess);

        ModelAndView mav = new ModelAndView();

        mav.setViewName("index");
        mav.addObject("user", new UserDetail());
        mav.addObject("captcha", loginAttempt.hasCaptcha());

        if (loginAttempt.isNewSession()) {
            mav.addObject("attempts", loginAttempt.getAttempts());
            mav.setStatus(HttpStatusCode.valueOf(200));
            return mav;
        }

        return createView(user, loginAttempt, binding);
    }

    @PostMapping(path="/login")
    public ModelAndView postLogin(HttpSession sess, @ModelAttribute UserDetail user, BindingResult binding) {
        LoginAttempt loginAttempt = new LoginAttempt(sess);
        return createView(user, loginAttempt, binding);
    }

    private ModelAndView createView(UserDetail user, LoginAttempt loginAttempt, BindingResult binding) {
        ModelAndView mav = new ModelAndView();

        if (nullLogin(user)) {
            mav.setViewName("index");
            user.setUsername(loginAttempt.getUsername());
            mav.addObject("user", user);
            if (loginAttempt.hasCaptcha()) {
                mav.addObject("captcha", loginAttempt.hasCaptcha());
                mav.addObject("attempts", loginAttempt.getAttempts());
                mav.addObject("opnd0", loginAttempt.getOperand0());
                mav.addObject("opnd1", loginAttempt.getOperand1());
                mav.setStatus(HttpStatusCode.valueOf(200));
            }
            return mav;
        }

        if (loginAttempt.hasCaptcha()) {
            if (!loginAttempt.validateCaptcha(user.getAnswer())) {
                mav.setViewName("redirect:/suspended.html");
                loginAttempt.invalidate();
                return mav;
            }
        }

        if (!validLogin(user)) {
            loginAttempt.setUsername(user.getUsername());
            loginAttempt.incrementAttempts();
            if (loginAttempt.getAttempts() < 2) {
                loginAttempt.enableCaptcha();
                loginAttempt.generateCaptcha();
                mav.setViewName("index");
                mav.addObject("user", user);
                mav.addObject("captcha", loginAttempt.hasCaptcha());
                mav.addObject("attempts", loginAttempt.getAttempts());
                mav.addObject("opnd0", loginAttempt.getOperand0());
                mav.addObject("opnd1", loginAttempt.getOperand1());
                mav.setStatus(HttpStatusCode.valueOf(401));
                ObjectError err = new ObjectError("globalError", "Incorrect login");
                binding.addError(err);
            } else {
                mav.setViewName("redirect:/suspended.html");
                loginAttempt.invalidate();
            }

        } else
            mav.setViewName("redirect:/success.html");

        return mav;
    }

    @PostMapping(path="/logout")
    public ModelAndView postLogout(HttpSession sess) {
        LoginAttempt loginAttempt = new LoginAttempt(sess);

        ModelAndView mav = new ModelAndView();

        loginAttempt.invalidate();
        mav.setViewName("redirect:/");

        return mav;
    }

    private boolean nullLogin(UserDetail user) {
        return (null == user.getUsername()) || user.getUsername().isEmpty();
    }

    private boolean validLogin(UserDetail user) {
        return user.getPassword().equals("%s%s".formatted(user.getUsername(), user.getUsername()));
    }
   
}