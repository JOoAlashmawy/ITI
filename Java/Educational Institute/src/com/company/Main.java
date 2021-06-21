package com.company;
import joo.*;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        //Subjects
        Subject s1 = new Subject();
        Subject s2 = new Subject();
        Subject s3 = new Subject();
        Subject s4 = new Subject();
        s1.setName("AI");
        s2.setName("DB");
        s3.setName("python");
        s4.setName("DL");
        //Admin
        Administrator ad = new Administrator();
        ad.setName("Admin");
        //Instructor
        Instructor st = new Instructor();
        st.setName("Instructor");
        st.setOfficeHours( LocalTime.of(8,0,0));
        st.Courses = new Subject[]{s1, s2, s3, s4};
        //Students
        Student p1 = new Student();
        Student p2 = new Student();
        Student p3 = new Student();
        p1.setName("Joo");
        p2.setName("Ahmed");
        p3.setName("Mohamed");
        p1.setID(1);
        p2.setID(2);
        p3.setID(3);
        p1.Courses = new Subject[]{s1, s2 };
        p2.Courses = new Subject[]{ s4, s3 };
        p3.Courses = new Subject[]{s1, s2, s3, s4};

        for(Subject subject: p3.Courses){
            System.out.println(subject.getName());
        }

        st.DisplayInfo();

        Person[] Institute_students = new Person[]{p1, p2, p3};
        Person[] Institute_instructor = new Person[]{st};
        Person[] Institute_admin = new Person[]{ad};

        Educational_Institute EI = new Educational_Institute();
        EI.Students = Institute_students;
        EI.Instructors = Institute_instructor;
        EI.Admins = Institute_admin;

        for(Person student: EI.Students){
            System.out.println(student.getName());
        }
        for(Person Instructor: EI.Instructors){
            System.out.println(Instructor.getName());
        }
        for(Person admin: EI.Admins){
            System.out.println(admin.getName());
        }
    }
}
