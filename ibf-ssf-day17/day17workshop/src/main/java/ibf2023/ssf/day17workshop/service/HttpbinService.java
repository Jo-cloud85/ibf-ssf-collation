package ibf2023.ssf.day17workshop.service;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/* Different appl will have their own way of checking liveness.
 * This is just our way of doing so. */

@Service
public class HttpbinService {
    
    public boolean isAlive(){

        RequestEntity<Void> req = RequestEntity
                .get("https://httpbin.org/get")
                .build();
        
        try {
            RestTemplate template = new RestTemplate();
            template.exchange(req, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
