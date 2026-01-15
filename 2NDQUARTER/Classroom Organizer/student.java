package MyObject;

public class student extends person {
    private int grade;

    public student(String firstName, String middleName, String lastName) {
        super(firstName, middleName, lastName);
    }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    @Override
    public void displayInfo() {
        System.out.println("Student: " + fullName());
        System.out.println("Grade: " + grade);
    }
}
