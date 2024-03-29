package vttp2023.ssf.day19.day19.model;

public class UserDetail {

   private String username;
   private String password;
   private int answer;

   public String getUsername() { 
      return username; 
   }

   public void setUsername(String username) { 
      this.username = username; 
   }

   public String getPassword() { 
      return password; 
   }

   public void setPassword(String password) { 
      this.password = password; 
   }

   public int getAnswer() { 
      return answer; 
   }

   public void setAnswer(int answer) { 
      this.answer = answer; 
   }

   @Override
   public String toString() {
      return "LoginDetail [username=" + username + ", password=" + password + "]";
   }
 }
