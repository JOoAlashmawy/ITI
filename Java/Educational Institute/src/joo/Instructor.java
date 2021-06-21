package joo;// joo.Instructor is inherit from Person
import java.time.LocalTime; // import the LocalTime class

public class Instructor extends Person {
    private LocalTime OfficeHours ;
    public Subject[] Courses ; //Schema have no public seters so I made it public to make it easy to use
    //public Subject[] Courses = new Subject[4];

    public void setOfficeHours(LocalTime h) {
        this.OfficeHours = h;
    }
    public LocalTime getOfficeHours() {
        return this.OfficeHours;
    }

    public Subject[] getCourses( ) {
        return this.Courses ;
    }

    public void DisplayInfo() {
        System.out.println(OfficeHours);
        System.out.println(getName());
        for(Subject subject: Courses){
            System.out.println(subject.getName() );
        }

    }
}
