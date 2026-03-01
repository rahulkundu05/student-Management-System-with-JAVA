import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
class Student {
    private String id;
    private String name;
    private String dept;
    private String marks;

    public Student(String id, String name, String dept, String marks) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.marks = marks;
    }

    public String toString() {
        return id + "," + name + "," + dept + "," + marks;
    }
}

public class StudentManagementSystem extends JFrame {

    private JTextField txtId, txtName, txtDept, txtMarks;
    private DefaultListModel<String> listModel;
    private JList<String> studentList;

    private static final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(600, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ----------- Top Panel (Form) -----------
        JPanel panel = new JPanel(new GridLayout(3,2,10,10));

        panel.add(new JLabel("Student ID"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Name"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Department"));
        txtDept = new JTextField();
        panel.add(txtDept);

        panel.add(new JLabel("Marks"));
        txtMarks = new JTextField();
        panel.add(txtMarks);

        JButton btnAdd = new JButton("Add Student");
        JButton btnUpdate = new JButton("Update Student");
        JButton btnDelete = new JButton("Delete Student");
        JButton btnClear = new JButton("Clear");

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        add(panel, BorderLayout.NORTH);

        // ----------- List Section -----------
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        add(new JScrollPane(studentList), BorderLayout.CENTER);

        // Load existing data
        loadStudents();

        // ----------- Button Actions -----------
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void addStudent() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() ||
            txtDept.getText().isEmpty() || txtMarks.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        Student student = new Student(
                txtId.getText(),
                txtName.getText(),
                txtDept.getText(),
                txtMarks.getText()
        );

        listModel.addElement(student.toString());
        saveToFile();
        clearFields();
    }

    private void updateStudent() {
        int index = studentList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to update!");
            return;
        }

        Student student = new Student(
                txtId.getText(),
                txtName.getText(),
                txtDept.getText(),
                txtMarks.getText()
        );

        listModel.set(index, student.toString());
        saveToFile();
        clearFields();
    }

    private void deleteStudent() {
        int index = studentList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete!");
            return;
        }

        listModel.remove(index);
        saveToFile();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDept.setText("");
        txtMarks.setText("");
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.println(listModel.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}