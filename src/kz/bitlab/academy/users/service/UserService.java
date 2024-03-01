package kz.bitlab.academy.users.service;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.academy.core.DBManager;
import kz.bitlab.academy.users.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends DBManager {

    public static String register(String email, String password, String rePassword, String fullName) {
        if (existsByEmail(email))
            return "userAlreadyExists";

        if (!password.equals(rePassword))
            return "passwordsNotSame";

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into users (email, password, full_name) " +
                            "values (?, ?, ?)");
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static UserEntity authenticate(String email, String password) {
        UserEntity currentUser = null;

        if (!existsByEmailAndPassword(email, password))
            return currentUser;

        try {
            currentUser = findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentUser;
    }

    public static String update(UserEntity currentUser, String oldPassword, String password, String rePassword, String fullName) {

        if (!oldPassword.equals(currentUser.getPassword()))
            return "wrongOldPassword";

        if (password == null || rePassword == null || !password.equals(rePassword))
            return "passwordsNotSame";

        if (!fullName.isBlank())
            currentUser.setFullName(fullName);

        currentUser.setPassword(password);
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update users set password = ?, full_name = ? where email = ?");
            statement.setString(1, currentUser.getPassword());
            statement.setString(2, currentUser.getFullName());
            statement.setString(3, currentUser.getEmail());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static UserEntity findByEmail(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from users where email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return generateUser(resultSet);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("User not found");
    }

    private static boolean existsByEmail(String email) {
        boolean isExists = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select exists(select from users where email = ?)");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isExists = resultSet.getBoolean(1);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExists;
    }

    private static boolean existsByEmailAndPassword(String email, String password) {
        boolean isExists = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select exists(select from users where email = ? and password = ?)");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isExists = resultSet.getBoolean(1);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExists;
    }

    private static UserEntity generateUser(ResultSet resultSet) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFullName(resultSet.getString("full_name"));
        user.setRoleId(resultSet.getInt("role_id"));

        return user;
    }

}
