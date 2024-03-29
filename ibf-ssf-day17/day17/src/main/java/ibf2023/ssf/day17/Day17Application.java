package ibf2023.ssf.day17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day17Application implements CommandLineRunner{

	@Autowired
	//private GiphyService giphyServ;

	public static void main(String[] args) {
		SpringApplication.run(Day17Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// giphyServ.search("polar bear");
		// System.exit(0);
	}
}
