package sg.edu.nus.iss.day16.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;

/* Since I have both @Controller and @ResponseBody, essentially these do what @RestController would do. 
 * Go to both urls to see (and you will see both give the same thing i.e. just a stringified json object):
 * 1. localhost:8080/home/time
 * 2. localhost:8080/home/test
*/

@Controller
@RequestMapping("/home")
public class HomeController {
    
    // Returning String
    @GetMapping("/time")
    // If you dont incl this, it will try to look for a Thymeleaf template as the String return should be a
    // Thymeleaf template. If you want to use this getMapping as API then you need this @ResponseBody
    @ResponseBody
    public String getTestEmployee1(){
        JsonObject jEmployee = Json.createObjectBuilder()
            .add("firstName", "Taylor")
            .add("lastName", "Swift")
            .build();
        return jEmployee.toString();
    }

    // Usually we use this with @RestController else you have to incl @ResponseBody
    // Returning ResponseEntity<String>
    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> getTestEmployee2(){
        JsonObject jEmployee = Json.createObjectBuilder()
            .add("firstName", "Taylor")
            .add("lastName", "Swift")
            .build();

        //return ResponseEntity.ok(jEmployee.toString());
        return ResponseEntity.status(HttpStatus.OK)
                             .body(jEmployee.toString());
    }
}
