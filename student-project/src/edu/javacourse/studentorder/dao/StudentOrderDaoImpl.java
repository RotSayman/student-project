package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.domain.StudentOrderStatus;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;

public class StudentOrderDaoImpl implements StudentOrderDao {

    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    "student_order_status, student_order_date," +
                    " h_sur_name, h_given_name, h_patronymic, h_date_of_birth," +
                    " h_passport_seria, h_passport_number, h_passport_date," +
                    " h_passport_office_id, h_post_index, h_street_code," +
                    " h_building, h_extension, h_apartment, w_sur_name, w_given_name," +
                    " w_patronymic, w_date_of_birth, w_passport_seria, w_passport_number," +
                    " w_passport_date, w_passport_office_id, w_post_index, w_street_code," +
                    " w_building, w_extension, w_apartment, certificate_id," +
                    " register_office_id, marriage_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // TODO refactoring make one method
    private static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD)
        );
        return conn;
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement preStat = connection.prepareStatement(INSERT_ORDER)){
            preStat.setInt(1, StudentOrderStatus.START.ordinal());
            preStat.setTimestamp(2, Timestamp.valueOf(so.getStudentOrderDate()));
            preStat.setString(3, so.getHusband().getSurName());
            preStat.setString(4, so.getHusband().getGivenName());
            preStat.setString(5, so.getHusband().getPatronymic());
            preStat.setDate(6, Date.valueOf(so.getHusband().getIssueDate()));



        }catch (SQLException ex){
            throw new DaoException(ex);
        }
        return 0L;
    }
}
