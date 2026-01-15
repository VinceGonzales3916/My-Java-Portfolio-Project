package MyObject;

public class teacher extends person {
    private String subject;

    public teacher(String firstName, String middleName, String lastName) {
        super(firstName, middleName, lastName);
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    @Override
    public void displayInfo() {
        System.out.println("Teacher: " + fullName());
        System.out.println("Subject: " + subject);
    }
}
