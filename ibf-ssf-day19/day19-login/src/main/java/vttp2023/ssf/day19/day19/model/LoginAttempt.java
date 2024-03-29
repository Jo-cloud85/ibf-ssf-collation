package vttp2023.ssf.day19.day19.model;

import java.security.SecureRandom;
import java.util.Random;

import jakarta.servlet.http.HttpSession;

public class LoginAttempt {

    public static final String F_ATTEMPTS = "attempts";
    public static final String F_CAPTCHA = "captcha";
    public static final String F_OPND0 = "opnd0";
    public static final String F_OPND1 = "opnd1";
    public static final String F_ANSWER = "answer";
    public static final String F_USERNAME = "username";
 
    private final HttpSession sess;
    private final boolean newSession;
 
    public LoginAttempt(HttpSession sess) {
       this.sess = sess;
 
       newSession = null == sess.getAttribute(F_ATTEMPTS);
       if (!newSession)
          return;
 
       sess.setAttribute(F_ATTEMPTS, 0);
       sess.setAttribute(F_CAPTCHA, false);
    }
 
    public boolean isNewSession() {
       return newSession;
    }
 
    public void setUsername(String username) {
       sess.setAttribute(F_USERNAME, username);
    }

    public String getUsername() {
       return (String)sess.getAttribute(F_USERNAME);
    }
 
    public int getAttempts() {
       return (int)sess.getAttribute(F_ATTEMPTS);
    }

    public void incrementAttempts() {
       int attempts = getAttempts();
       sess.setAttribute(F_ATTEMPTS, attempts + 1);
    }
 
    public boolean hasCaptcha() {
       return (boolean)sess.getAttribute(F_CAPTCHA);
    }

    public void enableCaptcha() {
       sess.setAttribute(F_CAPTCHA, true);
    }
 
    public int getOperand0() {
       return (int)sess.getAttribute(F_OPND0);
    }

    public int getOperand1() {
       return (int)sess.getAttribute(F_OPND1);
    }
 
    public void generateCaptcha() {
       Random rand = new SecureRandom();
       int opnd0 = rand.nextInt(1, 11);
       int opnd1 = rand.nextInt(1, 11);
       int answer = opnd0 + opnd1;
 
       sess.setAttribute(F_OPND0, opnd0);
       sess.setAttribute(F_OPND1, opnd1);
       sess.setAttribute(F_ANSWER, answer);
    }
 
    public boolean validateCaptcha(int answer) {
       return answer == (int)sess.getAttribute(F_ANSWER);
    }
 
    public void invalidate() {
       sess.invalidate();
    }
    
}
