package ibf2023.ssf.day13;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 1. mvn package -Dmaven.test.skip=true
// 2. java -jar target/day13-0.0.1-SNAPSHOT.jar --dataDir /opt/temp/data

@SpringBootApplication
public class Day13Application {

	public static void main(String[] args) throws IOException {

		// Check if --dataDir parameter is provided
		String dataDir = null;
		
		if (args[0].equals("--dataDir")) {
			dataDir = args[1].substring(1);
			System.out.println("Directory found! Starting application...");
		} else {
			System.err.println("Error: --dataDir option requires a directory path");
			System.exit(1);
		}

		// If --dataDir is not provided, print error message and stop
		if (dataDir == null) {
			System.err.println("Error: --dataDir option is required");
			System.exit(1);
		}

		SpringApplication.run(Day13Application.class, args);
	}
}
