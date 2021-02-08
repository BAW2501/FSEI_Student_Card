import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;

public class Student implements Serializable {

    private static final String IMAGE_FORMAT = "jpg";
    private static final String FONT_NAME = "Times New Roman";

    private final String studentNum;
    private final String familyName;
    private final String firstName;
    private final String familyNameAR;
    private final String firstNameAR;
    private final LocalDate birthDay;
    private final String birthPlace;
    private final byte[] photo;
    private final String speciality;
    private final String faculty;
    private final String academicYear;

    public Student(String studentNum, String familyName, String firstName, String familyNameAR, String firstNameAR,
                   LocalDate birthDay, String birthPlace, String speciality, String faculty, String academicYear,
                   String path_photo) {
        this.studentNum = studentNum;
        this.familyName = familyName;
        this.familyNameAR = familyNameAR;
        this.firstName = firstName;
        this.firstNameAR = firstNameAR;
        this.birthDay = birthDay;
        this.birthPlace = birthPlace;
        this.speciality = speciality;
        this.faculty = faculty;
        this.academicYear = academicYear;
        this.photo = convertImageToByteArray(path_photo);
    }

    private static byte[] convertImageToByteArray(String path) {
        // Convert image to byte array and handle IO exceptions
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage img = ImageIO.read(new File(path));
            ImageIO.write(img, IMAGE_FORMAT, bos);
        } catch (IOException e) {
            System.out.print(e.toString());
        }
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "numero_etudiant='" + studentNum + '\'' +
                ", nom='" + familyName + '\'' +
                ", prenom='" + firstName + '\'' +
                ", nom_ar='" + familyNameAR + '\'' +
                ", prenom_ar='" + firstNameAR + '\'' +
                ", date_naissance=" + birthDay +
                ", lieu_naissance='" + birthPlace + '\'' +
                ", filiere='" + speciality + '\'' +
                ", faculte='" + faculty + '\'' +
                ", annee_etude='" + academicYear + '\'' +
                '}';
    }

    public BufferedImage getPhotoBufferedimg() throws IOException {
        return ImageIO.read(new ByteArrayInputStream(this.photo));
    }

    public BufferedImage makeStudentCard() throws IOException {
        final BufferedImage image = ImageIO.read(new File("student_id.png"));

        Graphics g = image.getGraphics();
        setFont(g, Font.BOLD, 26, Color.BLACK);
        drawText(g, getFamilyName(), 275, 225);
        drawText(g, getFirstName(), 275, 280);

        setFont(g, Font.PLAIN, 22, Color.BLACK);
        FontMetrics fontMetrics = g.getFontMetrics();
        drawText(g, getFamilyNameAR(), 805 - fontMetrics.stringWidth(familyNameAR), 225);
        drawText(g, getFirstNameAR(), 805 - fontMetrics.stringWidth(firstNameAR), 280);
        drawText(g, getBirthDay().toString().replace("-", "/"), 805 - fontMetrics.stringWidth(birthDay.toString()), 335);
        drawText(g, getBirthPlace(), 575 - fontMetrics.stringWidth(birthPlace), 335);
        drawText(g, getSpeciality(), 805 - fontMetrics.stringWidth(speciality), 390);
        drawText(g, getAcademicYear(), 645 - fontMetrics.stringWidth(academicYear), 425);

        setFont(g, Font.PLAIN, 37, new Color(150, 77, 78));
        drawText(g, getStudentNum(), 555, 500);
        g.drawImage(getPhotoBufferedimg(), 47, 180, 205, 247, null);

        g.dispose();
        ImageIO.write(image, "png", new File(studentNum + ".png"));
        return image;
    }

    private void setFont(Graphics g, int style, int size, Color color) {
        g.setFont(new Font(FONT_NAME, style, size));
        g.setColor(color);
    }

    private void drawText(Graphics g, String text, int x, int y) {
        g.drawString(text, x, y);
    }

    // Getters
    public String getStudentNum() { return studentNum; }
    public String getFamilyName() { return familyName; }
    public String getFirstName() { return firstName; }
    public LocalDate getBirthDay() { return birthDay; }
    public String getSpeciality() { return speciality; }
    public String getFaculty() { return faculty; }
    public String getAcademicYear() { return academicYear; }
    public String getFamilyNameAR() { return familyNameAR; }
    public String getFirstNameAR() { return firstNameAR; }
    public String getBirthPlace() { return birthPlace; }
}
