package sg.edu.nus.iss.day16.model;

import java.util.List;

public class Employee {
    private String firstName;
    private String lastName;
    private Long salary;

    private Address address;
    private List<Phone> phones;

    public Employee() {

    }

    public Employee(String firstName, String lastName, Long salary, Address address, List<Phone> phones) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.address = address;
        this.phones = phones;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Employee [firstName=" + firstName +
               ", lastName=" + lastName + 
               ", salary=" + salary +
               ", address=" + address + 
               ", phones=" + phones + "]";
    }
}
