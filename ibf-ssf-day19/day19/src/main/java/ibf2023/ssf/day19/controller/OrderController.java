package ibf2023.ssf.day19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ibf2023.ssf.day19.model.Order;
import ibf2023.ssf.day19.service.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

// localhost:8080/api/order

// create controller first
// always make sure the controller is working first i.e.
// recieve the data (is it in json) and print out the data to try first

// once you get the data first, and then parse the data
// like read the json object and then parse it 

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(
        path="/api/order",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postOrder(@RequestBody String payload) {
        System.out.printf(">>>> payload %s\n", payload);

        Order order = orderService.createNewOrder(payload);

        JsonObject jObject = Json.createObjectBuilder()
            .add("orderId", order.getOrderId())
            .build();

        return ResponseEntity.ok(jObject.toString());
    }
}
