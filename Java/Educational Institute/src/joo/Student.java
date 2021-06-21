package joo;

// joo.Student is inherit from Person
public class Student extends Person {
    private int ID ;
    public Subject [] Courses; //Schema have no public seters so I made it public to make it easy to use

    public void setID(int I) {
        this.ID = I;
    }
    public int getID() {
        return this.ID;
    }

    public Subject[] getCourses( ) {
        return this.Courses ;
    }
    public int TotalGrade() {
        int TotalGrade = 0; //buffer
        for(Subject subject: Courses) {
            TotalGrade += subject.getGrade();
        }
        return TotalGrade;
    }
}
