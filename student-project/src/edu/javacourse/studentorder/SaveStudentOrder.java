package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.StudentOrderDao;
import edu.javacourse.studentorder.dao.StudentOrderDaoImpl;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.time.LocalDate;

public class SaveStudentOrder
{
    public static void main(String[] args) throws DaoException{
//        try {
//            List<Street> streetList = new DictionaryDaoImpl().findStreets("про");
//            streetList.forEach(street -> {
//                System.out.print(street.getStreetCode() + " | ");
//                System.out.println(street.getStreetName());
//            });
//            System.out.println();
//            List<PassportOffice> passportOffices = new DictionaryDaoImpl().findPassportOffices("010020000000");
//            passportOffices.forEach(passportOffice -> {
//                System.out.print(passportOffice.getOfficeId() + " | ");
//                System.out.print(passportOffice.getOfficeAreaId() + " | ");
//                System.out.println(passportOffice.getOfficeName() + " | ");
//            });
//
//            System.out.println();
//
//            List<RegisterOffice> registerOffices = new DictionaryDaoImpl().findRegisterOffices("010010000000");
//            registerOffices.forEach(registerOffice -> {
//                System.out.print(registerOffice.getOfficeId() + " | ");
//                System.out.print(registerOffice.getOfficeAreaId() + " | ");
//                System.out.println(registerOffice.getOfficeName() + " | ");
//            });
//            System.out.println();
//            List<CountryArea> countryAreas = new DictionaryDaoImpl().findArea("");
//            countryAreas.forEach(countryArea -> {
//                System.out.print(countryArea.getAreaId() + " | ");
//                System.out.println(countryArea.getAreaName() + " | ");
//            });
//            System.out.println();
//            List<CountryArea> countryAreas2 = new DictionaryDaoImpl().findArea("020000000000");
//            countryAreas2.forEach(countryArea2 -> {
//                System.out.print(countryArea2.getAreaId() + " | ");
//                System.out.println(countryArea2.getAreaName() + " | ");
//            });
//            System.out.println();
//            List<CountryArea> countryAreas3 = new DictionaryDaoImpl().findArea("020010000000");
//            countryAreas3.forEach(countryArea3 -> {
//                System.out.print(countryArea3.getAreaId() + " | ");
//                System.out.println(countryArea3.getAreaName() + " | ");
//            });
//            System.out.println();
//            List<CountryArea> countryAreas4 = new DictionaryDaoImpl().findArea("020010010000");
//            countryAreas4.forEach(countryArea4 -> {
//                System.out.print(countryArea4.getAreaId() + " | ");
//                System.out.println(countryArea4.getAreaName() + " | ");
//            });
//
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }


        StudentOrder s = buildStudentOrder(10);
        StudentOrderDao dao = new StudentOrderDaoImpl();
        Long id = dao.saveStudentOrder(s);
        System.out.println(id);

//        StudentOrder so = new StudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("saveStudentOrder");

        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderId(id);
        so.setMarriageCertificateId("" + (123456000 + id));
        so.setMarriageDate(LocalDate.of(2016, 7, 4));

        RegisterOffice ro = new RegisterOffice(1L, "", "");
        so.setMarriageOffice(ro);

        Street street = new Street(1L, "First street");

        Address address = new Address("195000", street, "12", "", "142");

        // Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 8, 24));
        husband.setPassportSeria("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 15));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentId("" + (100000 + id));
        husband.setAddress(address);

        // Жена
        Adult wife = new Adult("Петрова", "Вероника", "Алекссевна", LocalDate.of(1998, 3, 12));
        wife.setPassportSeria("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4, 5));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);

        // Ребенок
        Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11));
        RegisterOffice ro2 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        // Ребенок
        Child child2 = new Child("Петров", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice ro3 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }
}
