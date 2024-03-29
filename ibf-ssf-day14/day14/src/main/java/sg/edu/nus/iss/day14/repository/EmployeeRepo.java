package sg.edu.nus.iss.day14.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.day14.model.Employee;

public class EmployeeRepo {

    final String dirPath="/Users/yingfeng/data";
    final String fileName = "employee.txt";
    
    private List<Employee> employees;

    public EmployeeRepo() {
        if(employees == null) {
            employees = new ArrayList<>();
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // Static data
        Date birthDate;
        try {
            birthDate = df.parse("1977-09-09");
            Employee emp = new Employee("Walter", "Loo", birthDate, 22000, 
                                        "walterloo@nus.edu.sg", "97304666", 119615);
            employees.add(emp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Date birthDate2;
        try {
            birthDate2 = df.parse("1975-10-19");
            Employee emp2 = new Employee("James", "Khaw", birthDate2, 15000, 
                                         "jameskhaw@nus.edu.sg", "87008991", 129411);
            employees.add(emp2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAllEmployees() {
        return employees;
    }

    public Boolean save(Employee employee) throws FileNotFoundException {
        Boolean isSaved = false;

        employees.add(employee);

        // Open a file stream to write the new employee record to a file
        File file = new File(dirPath + "/" + fileName);
        OutputStream os = new FileOutputStream(file, true);
        PrintWriter pw = new PrintWriter(os);
        pw.write(employee.toString() + "\n");
        pw.flush();
        pw.close();

        return isSaved;
    }

    public Employee findByEmail(String email) {
        return employees.stream()
                 .filter(emp -> emp.getEmail().equals(email))
                 .findFirst()
                 .get(); //.get() cast Optional<Employee> to Employee object
    }

    public Boolean delete(Employee employee) throws IOException {
        Boolean isDeleted = false;
        
        int indexValue = employees.indexOf(employee);
        if(indexValue >= 0) {
            // Deleting from employee list
            employees.remove(indexValue);

            File file = new File(dirPath + "/" + fileName);
            OutputStream os = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(os);
            System.out.println(employees);
            for (Employee e : employees) {
                pw.write(e.toString() + "\n");
                pw.flush();
                pw.close();
            }
        
            isDeleted = true;
        } else {
            System.out.println("Employee record not found");
        }
        return isDeleted;
    }
}
