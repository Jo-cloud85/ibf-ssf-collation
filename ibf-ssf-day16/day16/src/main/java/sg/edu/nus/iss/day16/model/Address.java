package sg.edu.nus.iss.day16.model;

public class Address {
    
    private String line1;
    private String line2;
    private String postalCode;

    public Address() {

    }

    public Address(String line1, String line2, String postalCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.postalCode = postalCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address [line1=" + line1 + ", line2=" + line2 + ", postalCode=" + postalCode + "]";
    }
}
