package ibf2023.ssf.day12.controllers;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day12.models.QuickPick;


@Controller
@RequestMapping(path="/rnd")
public class RandomNumberController {

	// <a href="/rnd/1?startNum=1&endNum=10">ABC</a>

	@GetMapping(path="/picks")
	public ModelAndView getPicks(
		@RequestParam(defaultValue = "10") int pickCount) {

		List<QuickPick> picks = new LinkedList<>();
		for (int i = 0; i < pickCount; i++)
			picks.add(new QuickPick(-100, 100, i + 1));

		ModelAndView mav = new ModelAndView();
		mav.setViewName("quick_picks"); // "quick_picks is the HTML Thymeleaf template"
		mav.addObject("picks", picks);
		mav.setStatus(HttpStatusCode.valueOf(200));

		return mav;
	}
	

	// GET /rnd/list
	@GetMapping(path="/list")
	public ModelAndView getRandomList(
		@RequestParam(defaultValue = "1") int startNum,
		@RequestParam(defaultValue = "100") int endNum,
		@RequestParam(required = true) int count) {

		ModelAndView mav = new ModelAndView();

		if (startNum > endNum) {
			mav.setViewName("error");
			mav.addObject("messge", 
					"Start number %d is greater than end number %d".formatted(startNum, endNum));
			mav.setStatus(HttpStatusCode.valueOf(400));
			return mav;
		}

      Random rand = new SecureRandom();

		List<Integer> nums = new LinkedList<>();
		for (int i = 0; i < count; i++)
			nums.add(rand.nextInt(startNum, endNum + 1));

		mav.setViewName("numlist");
		mav.addObject("randNums", nums);
		mav.setStatus(HttpStatusCode.valueOf(200));

		return mav;
	}
	
	// GET /rnd/{count}
   @GetMapping(path="{count}")
   public ModelAndView getRandom(
		@PathVariable int count,
		@RequestParam(defaultValue = "1") int startNum,
		@RequestParam(defaultValue = "100") int endNum,
		@RequestParam MultiValueMap<String, String> queryParams) {

		System.out.printf(">>> count: %d, startNum: %d, endNum: %d\n"
				, count, startNum, endNum);

		return getRandomList(startNum, endNum, count);
	} 

	/*
    // GET /rnd/
	// Accept: text/html
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String getRandom(Model model) {
		Random rand = new SecureRandom();
		int value = rand.nextInt(1, 11);
		boolean odd = (value % 2) > 0;

		model.addAttribute("rndNum", value);
		model.addAttribute("odd", odd);
		
		return "numbers"; // Thymeleaf template under templates/numbers.html
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getMethodName() {
		return ResponseEntity.ok("{\"rnd\": 45}");
	}
	*/
}
