package joo;

public class Educational_Institute {
    private String name ;
    public Person [] Students; //Schema have no public seters so I made it public to make it easy to use
    public Person [] Instructors;
    public Person [] Admins;

    public void setName(String n) {
        this.name = n;
    }
    public String getName() {
        return this.name;
    }
    public Person[] getStudents() {
        return this.Students;
    }
    public Person[] getInstructors() {
        return this.Instructors;
    }
    public Person[] getAdmins() {
        return this.Admins;
    }

}
