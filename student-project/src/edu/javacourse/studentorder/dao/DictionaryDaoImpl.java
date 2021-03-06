package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {

    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";

    private static Connection getConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD)
        );
        return conn;
    }

    @Override
    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();
        Street street;
        try(Connection connection = getConnection();
            PreparedStatement preStat = connection.prepareStatement(GET_STREET)){
            preStat.setString(1, "%" + pattern + "%");
            ResultSet res = preStat.executeQuery();
            while (res.next()){
                street = new Street(res.getLong("street_code"), res.getString("street_name"));
                result.add(street);
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }

        return result;
    }
}
