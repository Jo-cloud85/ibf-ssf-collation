package ibf2023.ssf.day13.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactForm {

    private String id;

    @NotEmpty(message="Name cannot be empty")
    @Size(min=3, max=64, message="The length must be between 3 and 64")
    private String contactName;

    @NotEmpty(message="Email cannot be empty")
    @Email(message="Email must be valid")
    private String email;

    @NotEmpty(message="Phone number cannot be empty")
    @Pattern(regexp="\\d{7,}", message="Phone number must contain at least 7 digits")
    private String phoneNumber;

    @NotNull(message="You must set your date of birth")
    @Past(message="Date of birth must be in the past")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "ContactForm [id=" + id + 
                            ", contactName=" + contactName + 
                            ", email=" + email + 
                            ", phoneNumber=" + phoneNumber + 
                            ", dateOfBirth=" + dateOfBirth + "]";
    }

    

}
