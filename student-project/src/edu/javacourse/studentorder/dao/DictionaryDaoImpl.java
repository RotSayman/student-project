package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.CountryArea;
import edu.javacourse.studentorder.domain.PassportOffice;
import edu.javacourse.studentorder.domain.RegisterOffice;
import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {

    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_PASSPORT = "SELECT * " +
            "FROM jc_passport_office WHERE p_office_area_id = ?";
    private static final String GET_REGISTRY = "SELECT * " +
            "FROM jc_register_office WHERE r_office_area_id = ?";

    private static final String GET_AREA = "SELECT * FROM jc_country_struct " +
            "WHERE area_id LIKE ? and area_id <> ?";


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

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();
        PassportOffice passportOffice;
        try(Connection connection = getConnection();
            PreparedStatement preStat = connection.prepareStatement(GET_PASSPORT)){
            preStat.setString(1, areaId);
            ResultSet res = preStat.executeQuery();
            while (res.next()){
                passportOffice = new PassportOffice(
                        res.getLong("p_office_id"),
                        res.getString("p_office_area_id"),
                        res.getString("p_office_name")
                );
                result.add(passportOffice);
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }

        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();
        RegisterOffice registerOffice;
        try(Connection connection = getConnection();
            PreparedStatement preStat = connection.prepareStatement(GET_REGISTRY)){
            preStat.setString(1, areaId);
            ResultSet res = preStat.executeQuery();
            while (res.next()){
                registerOffice = new RegisterOffice(
                        res.getLong("r_office_id"),
                        res.getString("r_office_area_id"),
                        res.getString("r_office_name")
                );
                result.add(registerOffice);
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }

        return result;
    }

    @Override
    public List<CountryArea> findArea(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();
        CountryArea countryArea;
        try(Connection connection = getConnection();
            PreparedStatement preStat = connection.prepareStatement(GET_AREA)){
            String param1 = buildParam(areaId);
            String param2 = areaId;
            preStat.setString(1, param1);
            preStat.setString(2, param2);
            ResultSet res = preStat.executeQuery();
            while (res.next()){
                countryArea = new CountryArea(
                        res.getString("area_id"),
                        res.getString("area_name")
                );
                result.add(countryArea);
            }
        }catch (SQLException ex){
            throw new DaoException(ex);
        }

        return result;
    }

    private String buildParam(String areaId) throws SQLException{
        if(areaId == null || areaId.trim().isEmpty()){
            return "__0000000000";
        }else if(areaId.endsWith("0000000000")){
            return areaId.substring(0, 2) + "___0000000";
        }else if(areaId.endsWith("0000000")){
            return areaId.substring(0, 5) + "___0000";
        }else if(areaId.endsWith("0000")){
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("Invalid parameter 'areaId': " + areaId);
    }
}
