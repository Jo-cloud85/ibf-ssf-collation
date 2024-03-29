package ibf2023.ssf.day13.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day13.model.Task;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class TaskController {

    @GetMapping(path = {"/", "/index.html"})
    public ModelAndView getIndex(HttpSession sess) {
        ModelAndView mav = new ModelAndView("todo");
        List<Task> todoList = getTodoList(sess);

        mav.addObject("task", new Task());
        mav.addObject("todolist", todoList);

        return mav;
    }

    @PostMapping(path="/exit")
    public String postMethodName(HttpSession sess) {
        List<Task> todoList = getTodoList(sess);

        System.out.println(">>>> todoList: " + todoList);

        // perform exit
        // destroy the session for the NEXT request
        sess.invalidate();
        
        return "thankyou";
    }


    @PostMapping(path="/todo")
    public ModelAndView postTodoList(
        HttpSession sess,
        @ModelAttribute @Valid Task task,
        BindingResult bindings) {

        ModelAndView mav = new ModelAndView("todo"); 

        List<Task> todoList = getTodoList(sess);

        mav.addObject("todolist", todoList);

        // Syntactic validation
        if (bindings.hasErrors()) 
            mav.addObject("task", task);

        else {
            mav.addObject("task", new Task());
            todoList.add(task);
            // Redirect back to "/"
            mav.setViewName("redirect:/");
        }

        System.out.printf(">>>> task: %s\n", task);

        return mav;
    }

    private List<Task> getTodoList(HttpSession sess) {
        @SuppressWarnings("unchecked")
        List<Task> todolist = (List<Task>)sess.getAttribute("todolist");
        // Check if cart exists, if cart does not exist, then this is a new session
        // Initialize the session
        if (todolist == null) {
            todolist = new LinkedList<>();
            sess.setAttribute("todolist", todolist);
        }
        return todolist;
    }
}
