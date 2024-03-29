package ibf2023.ssf.day13.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.ui.Model;

import ibf2023.ssf.day13.model.ContactForm;

/* Since hexadecimal numbers are base-16, we want to generate random numbers within the range of
hexadecimal digits, which goes from 0 to 15. */

public class ContactService {
    
    private String dataDirFilename;


    public ContactService(String dataDirFilename) {
        this.dataDirFilename = dataDirFilename;
        File directory = new File(dataDirFilename);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    
    private String generateId(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(16);
            stringBuilder.append(Integer.toHexString(digit));
        }

        String randomHexString = stringBuilder.toString();
        System.out.println("Random Hex String: " + randomHexString);
        return randomHexString; 
    }


    public File getContactFile(String id) {
        return new File(dataDirFilename + File.separator + id + ".txt");
    }


    public List<String> getAllFileIds() {
        List<String> idList = new LinkedList<>();
        File directory = new File(dataDirFilename);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Check if it is a file (not a directory)
                    if (file.isFile()) {
                        idList.add(file.getName().substring(0, 8));
                    }
                }
            }
        } else {
            System.out.println("Directory does not exist or is not a directory");
        }
        return idList;
    }


    public void createAndWriteToContactFile(ContactForm contactForm) throws IOException {
        String id = generateId(8);
        File file = getContactFile(id);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Name: " + contactForm.getContactName() + "\n");
            writer.write("Email: " + contactForm.getEmail() + "\n");
            writer.write("Phone Number: " + contactForm.getPhoneNumber() + "\n");
            writer.write("Date of Birth: " + contactForm.getDateOfBirth().toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ContactForm readContactFile(String id, Model model) {
        File contactFile = getContactFile(id);
    
        if (!contactFile.exists()) {
            model.addAttribute("errorMessage", "Contact not found");
            return null;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(contactFile))) {
            StringBuilder content = new StringBuilder();
    
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            String[] contentArr = content.toString().split("\n");
    
            ContactForm contactForm = new ContactForm();
            contactForm.setId(id);
            String contactName = contentArr[0].split(": ")[1].trim();
            contactForm.setContactName(capitalizeFirstLetter(contactName));
            contactForm.setEmail(contentArr[1].split(": ")[1].trim());
            contactForm.setPhoneNumber(contentArr[2].split(": ")[1].trim());
            String birthDateStr = contentArr[3].split(": ")[1].trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
            contactForm.setDateOfBirth(birthDate);
    
            return contactForm;
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error reading contact file");
            e.printStackTrace();
            return null;
        }
    }


    public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str; // Return the original string if it's null or empty
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
}
