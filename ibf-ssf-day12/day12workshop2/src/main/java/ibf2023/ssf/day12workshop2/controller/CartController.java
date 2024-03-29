package ibf2023.ssf.day12workshop2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day12workshop2.Utils;
import ibf2023.ssf.day12workshop2.models.CartItem;

@Controller
@RequestMapping(path="/cart")
public class CartController {

    // Method 1 - Using parameters straight - more straightforward in this case
    @GetMapping
    public ModelAndView getCart(
        @RequestParam String item, 
        @RequestParam Integer quantity, 
        @RequestParam(defaultValue = "") String _cart) {

        ModelAndView mav = new ModelAndView("cart");
        CartItem newItem = new CartItem();

        // New Item
        newItem.setName(item);
        newItem.setQuantity(quantity);

        System.out.println("This is ITEM...." + item);
        System.out.println("This is QUANTITY...." + quantity);
        System.out.println("This is _CART...." + _cart);

        // name|quantity,name|quantity,...
        // convert String to List
        List<CartItem> cart = Utils.deserializeCart(_cart);
        cart.add(newItem);

        // convert List back to String
        String newCart = Utils.serializeCart(cart);
        System.out.printf(">>>> newCart: %s\n", newCart);

        mav.addObject("newCart", newCart);
        mav.addObject("cart", cart);

        return mav;
    }

    // Method 2 - Using MultiValueMap - less straightforward (explanation behind)
    // @GetMapping
    // public ModelAndView getCart2(
    //     @RequestParam MultiValueMap<String, String> form,
    //     @RequestParam(defaultValue = "") String _cart) {

    //     ModelAndView mav = new ModelAndView("cart");
    //     CartItem newItem = new CartItem();

    //     System.out.println("This is my form:..." + form);

    //     String item = form.getFirst("item");
    //     String quantityStr = form.getFirst("quantity");
    
    //     // Validate quantity parameter
    //     Integer quantity = null;
    //     try {
    //         quantity = Integer.parseInt(quantityStr);
    //     } catch (NumberFormatException e) {
    //         // Handle invalid quantity
    //         // For example, you could set a default value or return an error message
    //         quantity = 0; // Setting a default value, you can adjust this according to your requirements
    //         // You can also return an error message to the user
    //         mav.addObject("error", "Invalid quantity format. Please enter a valid quantity.");
    //     }

    //     // New Item
    //     newItem.setName(item);
    //     newItem.setQuantity(quantity);

    //     // name|quantity,name|quantity,...
    //     // convert String to List
    //     List<CartItem> cart = Utils.deserializeCart(_cart);
    //     cart.add(newItem);

    //     // convert List back to String
    //     String newCart = Utils.serializeCart(cart);
    //     System.out.printf(">>>> newCart: %s\n", newCart);

    //     mav.addObject("newCart", newCart);
    //     mav.addObject("cart", cart);

    //     return mav;
    // }
}
/*
Why MultiValueMap not the most appropriate choice?

In this example, when you navigate to a URL like http://localhost:8080/cart?item=apple&quantity=5, Spring Boot
will map this request to the getCart method. It will extract the parameters item and quantity from the URL's 
query string automatically due to @RequestParam annotation. You can then use these parameters as required 
within your method.

Using MultiValueMap in this context might not be the most appropriate choice because MultiValueMap is typically 
used for handling form data with multiple values for a single parameter.

In your case, you have two distinct parameters: item name and quantity. You could use a MultiValueMap if you 
were expecting multiple values for a single parameter, like multiple selections in a dropdown menu, for 
instance.

However, if you're dealing with two separate parameters as in your scenario (item name and quantity), using 
individual @RequestParam annotations as shown in the previous example would be more straightforward and 
intuitive.
*/