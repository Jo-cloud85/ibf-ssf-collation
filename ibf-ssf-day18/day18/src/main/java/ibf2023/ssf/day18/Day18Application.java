package ibf2023.ssf.day18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2023.ssf.day18.models.User;
import ibf2023.ssf.day18.service.HttpbinService;

@SpringBootApplication
public class Day18Application implements CommandLineRunner {

	@Autowired
	private HttpbinService httpbinSrvc;

	public static void main(String[] args) {
		SpringApplication.run(Day18Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//httpbinSvc.postByUrlEncodedForm(new User("barney", "barney@gmail.com"));
		httpbinSrvc.postByJson(new User("barney", "barney@gmail.com"));
		System.exit(0);
	}
}
