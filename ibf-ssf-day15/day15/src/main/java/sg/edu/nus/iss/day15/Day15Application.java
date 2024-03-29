package sg.edu.nus.iss.day15;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.day15.model.Person;
import sg.edu.nus.iss.day15.repository.TestRepo;
import sg.edu.nus.iss.day15.service.PersonService;
import sg.edu.nus.iss.day15.utils.Util;

@SpringBootApplication
public class Day15Application implements CommandLineRunner {

	@Autowired
	TestRepo testRepo;

	@Autowired
	PersonService personService;

	public static void main(String[] args) {
		SpringApplication.run(Day15Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// testRepo.storeValueData("count", String.valueOf(1000));
		// String countValue = testRepo.retrieveValueData("count");
		// System.out.println("Count from Redis: " + countValue);

		// testRepo.storeValueData("email", "j.tizahn@gmail.com");
		// String email = testRepo.retrieveValueData("email");
		// System.out.println("Email from Redis: " + email);

		// testRepo.addToList("cart", "apple");
		// testRepo.addToList("cart", "orange");
		// testRepo.addToList("cart", "pear");

		// List<String> fruits = testRepo.getList("cart");

		// // testRepo.deleteKey("cart");

		// fruits.forEach(System.out::println);

		// ---------------------------------------------------------------

		// For adding data to list -------------------
		Person p = new Person(1, "Darryl", 20000);
		personService.addPerson(Util.KEY_PERSON, p);

		p = new Person(2, "Farm", 25000); 
		personService.addPerson(Util.KEY_PERSON, p);

		p = new Person(3, "Felicia", 30000); 
		personService.addPerson(Util.KEY_PERSON, p);

		// For updating data -----------------------
		// Person updatedP = new Person(3, "Felicial", 20000);
		// personService.updatePerson(Util.KEY_PERSON, updatedP);

		// For deleting a person -------------------
		// personService.deletePerson(Util.KEY_PERSON, 1);

		// For getting list ------------------------
		List<Person> persons = personService.getPersonList(Util.KEY_PERSON);

		// For deleting entire list ----------------
		// personService.deletePersonList(Util.KEY_PERSON);

		if (persons.size() == 0) {
			System.out.println("List is empty");
		} else {
			persons.forEach(System.out::println);
		}
	}
}
