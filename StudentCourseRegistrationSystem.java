import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Class representing a course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;

    // Constructor to initialize a course
    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedule = schedule;
    }

    // Getters for course properties
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getSchedule() {
        return schedule;
    }

    // Method to enroll a student
    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    // Method to drop a student
    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    // Method to display course details
    public void displayCourse() {
        System.out.println("Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
                ", Capacity: " + capacity + ", Enrolled Students: " + enrolledStudents + ", Schedule: " + schedule);
    }
}

// Class representing a student
class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    // Constructor to initialize a student
    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters for student properties
    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    // Method to register for a course
    public boolean registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    // Method to drop a course
    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            return true;
        }
        return false;
    }

    // Method to display registered courses
    public void displayRegisteredCourses() {
        System.out.println("Student ID: " + studentID + ", Name: " + name);
        for (Course course : registeredCourses) {
            course.displayCourse();
        }
    }
}

// Main class to run the student course registration system
public class StudentCourseRegistrationSystem {
    private static HashMap<String, Course> courseDatabase = new HashMap<>();
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            // Display menu options
            System.out.println("\nStudent Course Registration System:");
            System.out.println("1. Add Course");
            System.out.println("2. Register Student");
            System.out.println("3. List Available Courses");
            System.out.println("4. Register for a Course");
            System.out.println("5. Drop a Course");
            System.out.println("6. Display Student Courses");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    listAvailableCourses();
                    break;
                case 4:
                    registerForCourse();
                    break;
                case 5:
                    dropCourse();
                    break;
                case 6:
                    displayStudentCourses();
                    break;
                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }

    // Method to add a course to the course database
    private static void addCourse() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        System.out.print("Enter course description: ");
        String description = scanner.nextLine();
        System.out.print("Enter course capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter course schedule: ");
        String schedule = scanner.nextLine();

        Course course = new Course(courseCode, title, description, capacity, schedule);
        courseDatabase.put(courseCode, course);
        System.out.println("Course added successfully.");
    }

    // Method to register a student in the student database
    private static void registerStudent() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentID, name);
        studentDatabase.put(studentID, student);
        System.out.println("Student registered successfully.");
    }

    // Method to list all available courses
    private static void listAvailableCourses() {
        if (courseDatabase.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courseDatabase.values()) {
                course.displayCourse();
            }
        }
    }

    // Method to register a student for a course
    private static void registerForCourse() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Course registered successfully.");
        } else {
            System.out.println("Failed to register for the course. It might be full.");
        }
    }

    // Method to drop a course for a student
    private static void dropCourse() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("Failed to drop the course. It might not be registered.");
        }
    }

    // Method to display all courses registered by a student
    private static void displayStudentCourses() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            student.displayRegisteredCourses();
        }
    }
}
