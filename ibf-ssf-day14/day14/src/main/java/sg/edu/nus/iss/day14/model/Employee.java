package sg.edu.nus.iss.day14.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/* In real-world practice, you will most likely rely on client-side validation than server-side validation */

public class Employee {

    private String id;

    @NotEmpty(message = "First name is mandatory")
    @Size(min = 3, max = 15, message = "First name must be between 3 and 15 characters")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @Size(min = 2, max = 15, message = "Last name must be between 2 and 15 characters")
    private String lastName;

    @Past(message = "Birth date cannot be a present or future data")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @Min(value = 1500, message = "Minimum salary starts from $1500")
    @Max(value = 50000, message = "Maximum salary cannot exceed $50000")
    private Integer salary;

    @Email(message = "Invalid email format")
    @Size(max=30, message="Email cannot exceed 30 characters")
    @NotBlank(message="Email cannot be empty or blank")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid phone number entered")
    private String phoneNo;

    @Digits(integer = 6, fraction = 0, message = "Postal code must be 6 digits")
    @Min(value = 111111, message = "Postal code starts from 111111")
    @Max(value = 888888, message = "Postal code cannot exceeds 888888")
    private Integer postalCode;

    public Employee() {

    }

    // No "id" as it will be randomly generated using a method
    public Employee(String firstName, String lastName, Date birthday, Integer salary, 
                    String email, String phoneNo, Integer postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.salary = salary;
        this.email = email;
        this.phoneNo = phoneNo;
        this.postalCode = postalCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id +
                         ", firstName=" + firstName + 
                         ", lastName=" + lastName + 
                         ", birthday=" + birthday + 
                         ", salary=" + salary + 
                         ", email=" + email + 
                         ", phoneNo=" + phoneNo + 
                         ", postalCode=" + postalCode + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (salary == null) {
            if (other.salary != null)
                return false;
        } else if (!salary.equals(other.salary))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (phoneNo == null) {
            if (other.phoneNo != null)
                return false;
        } else if (!phoneNo.equals(other.phoneNo))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        return true;
    }
}
