package sg.edu.nus.iss.day16.restcontroller;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

/* By right if I didn't indicate "produces = MediaType.APPLICATION_JSON_VALUE", I will get the same thing
 * as in HomeController when I go to the URL: localhost:8080/api/time. BUT since I indicated that, I get
 * the json instead. Same thing when you go to the URL: localhost:8080/api/time/test */

@RestController
@RequestMapping(path="/api/time", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeRestController {

    // Returning ResponseEntity<String>
    @GetMapping("/test")
    // If you dont incl this, it will try to look for a Thymeleaf template as the String return should be a
    // Thymeleaf template. If you want to use this getMapping as API then you need this @ResponseBody
    public ResponseEntity<String> getTestEmployee1(){
        JsonObject jEmployee = Json.createObjectBuilder()
            .add("firstName", "Taylor")
            .add("lastName", "Swift")
            .build();
        return ResponseEntity.ok(jEmployee.toString());
    }

    @GetMapping
    public ResponseEntity<String> getTimeAsJson() throws ParseException{
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currDate = sdf.format(currentDate);
        JsonObject obj = Json.createObjectBuilder()
                             .add("time", currDate)
                             .build();
        System.out.println("Response Entity: " + ResponseEntity.ok(obj.toString()));
        return ResponseEntity.ok(obj.toString());
    }

    // payload string comes from the above API function
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTimeAsJsonString(@RequestBody String payload) {

        JsonReader jReader = Json.createReader(new StringReader(payload));
        JsonObject jObject = jReader.readObject();
        System.out.println("jObject payload: " + jObject.toString());

        JsonObject responsePayload = Json.createObjectBuilder()
                                        .add("time", jObject.get("time").toString())
                                        .add("status", "unchanged")
                                        .add("updatedBy", "Darryl")
                                        .build();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Darryl", (new Date()).toString());
        headers.add("PostTimeJson", "TESTING ONLY");

        // Method 1 of returning status code
        return new ResponseEntity<String>(responsePayload.toString(), headers, HttpStatus.OK);

        // Method 2 of returning status code
        // return ResponseEntity.ok("");
    }
}
