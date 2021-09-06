package course1.lesson5;

import java.util.Date;

public class Employee {

    private String fullName;
    private int age;

    private String position;
    private int salary;

    private String email;
    private String phone;

    public Employee(String fullName, String position, String email, String phone, int salary, int age)
    {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
