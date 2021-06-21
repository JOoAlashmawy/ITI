package joo;

// joo.Administrator is inherit from Person
public class Administrator extends Person {
    private String Contact ;
    private  String Role;

    public void setContact(String c) {
        this.Contact = c;
    }
    public String getContact() {
        return this.Contact;
    }

    public void setRole(String r) {
        this.Role = r;
    }
    public String getRole() {
        return this.Role;
    }

}
