package sg.edu.nus.iss.day15.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day15.model.Person;
import sg.edu.nus.iss.day15.repository.PersonRepo;

@Service
public class PersonService {
    
    @Autowired
    PersonRepo personRepo;

    public void addPerson(String key, Person person) {
        personRepo.addToList(key, person.toString());
    }

    public List<Person> getPersonList(String key) {
        List<String> rawList = personRepo.getList(key);
        List<Person> persons = new ArrayList<>();

        for (String raw : rawList) {
            String[] record = raw.split(",");
            Person p = new Person();
            p.setId(Integer.parseInt(record[0]));
            p.setFullname(record[1]);
            p.setSalary(Integer.parseInt(record[2]));
            persons.add(p);
        }

        return persons;
    }

    public Boolean updatePerson(String key, Person person) {
        Boolean hasUpdated = false;
        personRepo.updatePerson(key, person.getId() - 1, person.toString());
        return hasUpdated;
    }

    public Boolean deletePerson(String key, int id) {
        Boolean hasDeleted = false;
        List<Person> persons = getPersonList(key);
        for (Person person : persons) {
            if (person.getId() == id) {
                personRepo.deletePerson(key, person.toString());
            }
        }
        return hasDeleted;
    }

    public Boolean deletePersonList(String key) {
        Boolean hasDeleted = false;
        personRepo.deleteAll(key);
        return hasDeleted;
    }
}
