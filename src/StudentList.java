import java.io.*;
import java.util.ArrayList;

public class StudentList implements Serializable {
    private static final String DEFAULT_FILENAME = "BDD.ETD";

    private final ArrayList<Student> list;

    public StudentList() {
        this.list = new ArrayList<>();
    }

    // Getter method that returns the list of students
    public ArrayList<Student> getList() {
        return new ArrayList<>(list); // Return a copy to avoid exposing internal list
    }
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Add a student to the list
    public void add(Student student) {
        this.list.add(student);
    }

    // Save the StudentList instance to a file for persistence
    public void saveAsFile() {
        try (FileOutputStream fileOutput = new FileOutputStream(DEFAULT_FILENAME);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
            objectOutput.writeObject(this);
        } catch (IOException e) {
            System.out.print("File save error: " + e.toString());
        }
    }

    // Read StudentList instance from the specified file
    public static StudentList readFromFile(String filePath) {
        StudentList studentList = null;
        try (FileInputStream fileInput = new FileInputStream(filePath);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
            studentList = (StudentList) objectInput.readObject();
        } catch (IOException e) {
            System.out.print("File read error: " + e.toString());
        } catch (ClassNotFoundException e) {
            System.out.print("Class not found: " + e.toString());
        }
        return studentList;
    }

    // Convert student list to a 2D array format for display or table input
    public Object[][] convertToData() {
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getStudentNum();
            data[i][1] = list.get(i).getFamilyName();
            data[i][2] = list.get(i).getFirstName();
        }
        return data;
    }
}
