package MyObject;

public class person {
    private int age;
    private String firstName;
    private String middleName;
    private String lastName;
    private String sex;
    private int life = 100;

    // Constructors
    public person(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public person() {}

    // Getters and setters
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }

    // Methods
    public void printName() {
        System.out.println(firstName + " " + middleName + " " + lastName);
    }

    public String fullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    // Polymorphic method
    public void displayInfo() {
        System.out.println("Name: " + fullName());
        System.out.println("Age: " + age);
        System.out.println("Sex: " + sex);
    }
}
