package ibf2023.ssf.day13.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2023.ssf.day13.model.ContactForm;
import ibf2023.ssf.day13.service.ContactService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @GetMapping("/add")
    public String addContactForm(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("newContactObj", contactForm);
        return "contact_add";
    }

    
    @PostMapping("/add")
    public String addContact(
        Model model,
        @ModelAttribute("newContactObj") @Valid ContactForm contactForm, 
        BindingResult bindings) {

        if (bindings.hasErrors()) {
            // If there are binding errors, return the same view with validation errors
            return "contact_add"; // Assuming the view name is "add_contact"
        }

        try {
            ContactService contacts = new ContactService("opt/tmp/data");
            contacts.createAndWriteToContactFile(contactForm);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other exceptions here if needed
        }

        model.addAttribute("contactlist", contactForm);

        // If no binding errors, return a success view
        return "success"; // You can define this view name
    }


    @GetMapping("/{id}")
    public String getContact(@PathVariable String id, Model model) {
        ContactService contacts = new ContactService("opt/tmp/data");

        ContactForm contactForm = contacts.readContactFile(id, model);

        if (contactForm == null) {
            return "error";
        }

        model.addAttribute("singlecontact", contactForm);
        return "single_contact";
    }


    @GetMapping
    public String getAllContacts(Model model) {
        ContactService contacts = new ContactService("opt/tmp/data");

        List<String> idList = contacts.getAllFileIds();
        List<ContactForm> contactList = new LinkedList<>();

        for (String id : idList) {
            ContactForm contactForm = contacts.readContactFile(id, model);

            if (contactForm == null) {
                return "error";
            }

            contactList.add(contactForm);
        }

        model.addAttribute("contactlist", contactList);

        return "contact";
    }
}
