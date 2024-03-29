package ibf2023.ssf.day19.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import ibf2023.ssf.day19.model.Order;
import ibf2023.ssf.day19.util.Utils;

@Service
public class OrderService {

   public Order createNewOrder(String json) {
      // Generate orderId
      String orderId = UUID.randomUUID().toString().substring(0, 8);
      Order order = Utils.toOrder(orderId, json);
      // Save order to database
      return order;
   }
   
}
