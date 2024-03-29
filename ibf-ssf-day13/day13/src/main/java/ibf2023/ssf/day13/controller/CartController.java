package ibf2023.ssf.day13.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day13.models.Item;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/* If you use @RequestBody, you get the entire raw String and you can parse.
Alternatively, you use @RequestParam, you get the specific parameter. */

// Note that @RequestBody is only for POST, not for GET since GET does not have payload/body

@Controller
@RequestMapping
public class CartController {

    // Member
    // Do not do this
    // private List<Item> cart = new LinkedList<>();

    @GetMapping(path = {"/", "/index.html"})
    public ModelAndView getIndex(HttpSession sess) {

        /* "cart" below inside ModelAndView is the Thymelead HTML under templates
        You can also leave it empty, and then use mav.setViewName("cart") */

        ModelAndView mav = new ModelAndView("cart"); 
        // Retrieves the cart items stored in the session
        List<Item> cart = getCart(sess); 

        mav.addObject("itemObj", new Item());
        mav.addObject("cartObj", cart);

        return mav;
    }


    @PostMapping(path="/checkout") // Just for ending the session
    public String postMethodName(HttpSession sess) {

        // Just for printing out the cart bef destroying session
        List<Item> cart = getCart(sess);
        System.out.println(">>>> cart: " + cart);

        // Perform checkout/destroy the session for the NEXT request
        sess.invalidate();
        
        return "thankyou";
    }


    // Method 1 of capturing data ---------------------------------------------
    // Using @ModelAttribute, and this incl. HttpSession
    @PostMapping(path="/cart2")
    public ModelAndView postCart2(
        HttpSession sess,
        @ModelAttribute("itemObj") @Valid Item item,
        BindingResult bindings) {

        /* @BindingResult must appear immediately after what we are validating,
        and it must be the last parameter. */

        ModelAndView mav = new ModelAndView("cart"); 

        // Alternative to "ModelAndView mav = new ModelAndView("cart");"
        // ModelAndView mav = new ModelAndView();
        // mav.setViewName("cart");

        List<Item> cart = getCart(sess);

        mav.addObject("cartObj", cart);

        // Syntactic validation
        if (bindings.hasErrors()) 
            // Return whatever the user has input
            mav.addObject("itemObj", item);

        // Semantic validation
        else if ("apple".equals(item.getName())) {
            System.out.printf(">>>> apple error");
            FieldError err = new FieldError("itemObj", "name", "We don't sell apple here");
            bindings.addError(err);

            mav.addObject("itemObj", item);
            System.out.printf(">>>> sem validation item: %s\n", item);

            ObjectError objErr = new ObjectError("globalError", "This error belongs to the form");
            bindings.addError(objErr);

        } else {
            mav.addObject("itemObj", new Item());
            cart.add(item);
            // Redirect back to "/"
            mav.setViewName("redirect:/");
        }

        System.out.printf(">>>> item: %s\n", item);

        return mav;
    }

    
    // Method 2 of capturing data -------------------------------------------------
    // Using MultiValueMap
    // @PostMapping(path="/cart")
    // public ModelAndView postCart(
    //     @RequestBody MultiValueMap<String, String> form,
    //     @RequestParam String name,
    //     @RequestParam int quantity, 
    //     @RequestBody String payload) {

    //     ModelAndView mav = new ModelAndView("cart");

    //     // Validate item
    //     if (quantity <= 0) {
    //         mav.setStatus(HttpStatusCode.valueOf(400));
    //         return mav;
    //     }

    //     System.out.printf(">>>> name: %s\n", name);
    //     System.out.printf(">>>> payload: %s\n", payload);
    //     System.out.printf(">>>> form: %s\n", form);

    //     return mav;
    // }

    @PostMapping(path = "/cart")
    public ModelAndView postCart2(
            HttpSession sess,
            @RequestBody @Valid MultiValueMap<String, String> formData,
            BindingResult bindings) {

        ModelAndView mav = new ModelAndView("cart");

        List<Item> cart = getCart(sess);

        mav.addObject("cartObj", cart);

        String name;
        String quantityStr;
        Integer quantity = 0;

        // Check if formData is empty
        if (formData.isEmpty()) {
            // Redirect back to root page
            mav.setViewName("redirect:/");
            return mav;
        }

        // Extract parameters from formData
        name = formData.getFirst("name");
        quantityStr = formData.getFirst("quantity");
        quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 0;

        // Syntactic validation
        if (bindings.hasErrors() || quantity <= 0) {
            // Return whatever the user has input
            mav.addObject("itemObj", new Item(name, quantity));
        } else {
            Item newItem = new Item(name, quantity);
            cart.add(newItem);
            // Redirect back to "/"
            mav.setViewName("redirect:/");
        }

        System.out.println(">>>> formData: " + formData); 
        System.out.println(">>>> cart: " + cart); 
        System.out.printf(">>>> name: %s\n", name);
        System.out.printf(">>>> quantity: %d\n", quantity);

        /*
        >>>> formData: {name=[Apple], quantity=[10]}
        >>>> cart: [Item [name=Apple, quantity=10]]
        >>>> name: Apple
        >>>> quantity: 10
        >>>> formData: {name=[Oranges], quantity=[5]}
        >>>> cart: [Item [name=Apple, quantity=10], Item [name=Oranges, quantity=5]]
        >>>> name: Oranges
        >>>> quantity: 5
        >>>> formData: {name=[Cherries], quantity=[5]}
        >>>> cart: [Item [name=Apple, quantity=10], Item [name=Oranges, quantity=5], 
                    Item [name=Cherries, quantity=5]]
        >>>> name: Cherries
        >>>> quantity: 5
        >>>> formData: {name=[Guava], quantity=[2]}
        >>>> cart: [Item [name=Apple, quantity=10], Item [name=Oranges, quantity=5], 
                    Item [name=Cherries, quantity=5], Item [name=Guava, quantity=2]]
        >>>> name: Guava
        >>>> quantity: 2
        >>>> formData: {name=[], quantity=[]}
        >>>> cart: [Item [name=Apple, quantity=10], Item [name=Oranges, quantity=5], 
                    Item [name=Cherries, quantity=5], Item [name=Guava, quantity=2]]
        >>>> name: 
        >>>> quantity: 0
         */

        return mav;
    }


    
    private List<Item> getCart(HttpSession sess) {
        @SuppressWarnings("unchecked")
        List<Item> cart = (List<Item>)sess.getAttribute("cartObj");
        // Check if cart exists, if cart does not exist, then this is a new session
        // Initialize the session
        if (cart == null) {
            cart = new LinkedList<>();
            sess.setAttribute("cartObj", cart);
        }
        return cart;
    }
}
