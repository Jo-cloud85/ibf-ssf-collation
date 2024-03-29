package ibf2023.ssf.day17.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day17.model.GiphyImage;
import ibf2023.ssf.day17.service.GiphyService;

@Controller
@RequestMapping
public class GiphyController {

    @Autowired
    private GiphyService giphySvc;
 
    @GetMapping(path="/search")
    public ModelAndView search(@RequestParam String q) {
       List<GiphyImage> results = giphySvc.search(q);
 
       ModelAndView mav = new ModelAndView("result");
       mav.setStatus(HttpStatusCode.valueOf(200));
       mav.addObject("q", q);
       mav.addObject("gifs", results);
 
       return mav;
    }
}
