package MyObject;

import java.util.ArrayList;

public class section {
    private String name;
    private teacher adviser;
    private ArrayList<student> listStudent = new ArrayList<>();

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public teacher getAdviser() { return adviser; }
    public void setAdviser(teacher adviser) { this.adviser = adviser; }

    public ArrayList<student> getListStudent() { return listStudent; }
    public void setListStudent(ArrayList<student> listStudent) { this.listStudent = listStudent; }

    // Add a student
    public void addStudent(student s) {
        listStudent.add(s);
    }

    // Remove a student by index
    public void removeStudent(int index) {
        if (index >= 0 && index < listStudent.size()) {
            System.out.println(listStudent.get(index).fullName() + " removed from the section.");
            listStudent.remove(index);
        } else {
            System.out.println("Invalid student index.");
        }
    }

    // Display section info
    public void displaySection() {
        System.out.println("\nSection: " + name);
        System.out.print("Adviser: ");
        if (adviser != null) {
            adviser.displayInfo();
        } else {
            System.out.println("No adviser assigned.");
        }

        System.out.println("\nStudents:");
        for (int i = 0; i < listStudent.size(); i++) {
            System.out.println((i + 1) + ".");
            listStudent.get(i).displayInfo(); // polymorphism
            System.out.println();
        }
    }
}
