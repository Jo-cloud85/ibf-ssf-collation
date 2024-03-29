package vttp2023.ssf.day17.weather.services;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpbinService {

   // Network access
   public boolean isAlive() {

      RequestEntity<Void> req = RequestEntity
            .get("https://httpbin.org/get")
            .build();

      try {
         RestTemplate template = new RestTemplate();
         template.exchange(req, String.class);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }
   
}
