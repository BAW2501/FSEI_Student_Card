import java.time.LocalDate;

public class Main {
    public static StudentList bdd = null;

    public static void main(String[] args) {
        bdd = StudentList.readFromFile("BDD.ETD");
        if (bdd == null || bdd.isEmpty()) {
            fillStudentList();
            bdd.saveAsFile();
        }
        bdd.getList().forEach(System.out::println);
        new Interface();
    }

    private static void fillStudentList() {
        bdd = new StudentList();

        bdd.add(new Student("181837030546", "BELHADJ", "AHMED WALID", "بلحاج", "احمد وليد", LocalDate.of(1970, 1, 1), Data.wilayasAr[0], Data.commonBranchesAr[5][0], Data.FacultyNames[5], "2018/2019", "pp.jpg"));
        bdd.add(new Student("181836030241", "MOUMEN", "RAOUF", "مومن", "رؤوف", LocalDate.of(1997, 12, 11), Data.wilayasAr[16], Data.commonBranchesAr[7][0], Data.FacultyNames[1], "2016/2017", "pp1.jpg"));
        bdd.add(new Student("181836055272", "FAEYET", "LATIFA", "فايت", "لطيفة", LocalDate.of(1992, 2, 15), Data.wilayasAr[9], Data.commonBranchesAr[2][0], Data.FacultyNames[1], "2012/2013", "pp1.jpg"));
        bdd.add(new Student("181837034715", "NAIM", "FEIROUZ", "نعيم", "فيروز", LocalDate.of(1999, 9, 27), Data.wilayasAr[6], Data.commonBranchesAr[3][0], Data.FacultyNames[5], "2018/2019", "pp1.jpg"));
        bdd.add(new Student("181836021170", "BETTAHAR", "FADI", "بطاهر", "فادي", LocalDate.of(1998, 4, 19), Data.wilayasAr[11], Data.commonBranchesAr[9][0], Data.FacultyNames[1], "2017/2018", "pp1.jpg"));

    }

}