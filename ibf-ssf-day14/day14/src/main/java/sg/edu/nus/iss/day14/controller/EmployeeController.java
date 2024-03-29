package sg.edu.nus.iss.day14.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.day14.model.Employee;
import sg.edu.nus.iss.day14.repository.EmployeeRepo;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeRepo employeeRepo = new EmployeeRepo();
    
    // http://localhost:<port no>/employees/add
    @GetMapping("/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employeeNew", employee);
        return "employee-add";
    }

    @PostMapping("/add")
    public String saveEmployee(
        @ModelAttribute("employeeNew") @Valid Employee employeeForm, 
        BindingResult result, 
        Model model) {

        // perform some operations
        // i.e. save to a file or database
        // redirect to success page

        if (result.hasErrors()) {
            return "employee-add";
        }

        try {
            employeeRepo.save(employeeForm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        model.addAttribute("savedEmployee", employeeForm);
        
        return "success";
    }

    @GetMapping("/list")
    public String EmployeeList(Model model) {

        List<Employee> employees = employeeRepo.findAllEmployees();
        model.addAttribute("employees", employees);

        return "employee-list";
    }

    @GetMapping("/delete/{email}")
    public String deleteEmployee(@PathVariable("email") String email) throws IOException {

        Employee employee = employeeRepo.findByEmail(email);
        Boolean isDeleted = employeeRepo.delete(employee);
        System.out.println(isDeleted);

        return "redirect:/employees/list";
    }
}
