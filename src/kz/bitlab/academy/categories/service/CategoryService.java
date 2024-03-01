package kz.bitlab.academy.categories.service;

import kz.bitlab.academy.categories.entity.CategoryEntity;
import kz.bitlab.academy.core.DBManager;
import kz.bitlab.academy.news.service.NewsService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryService extends DBManager {

    public static void create(String name) {
        try {
            if (existsByName(name)) {
                throw new RuntimeException("Category with this name already exists!");
            }

            PreparedStatement statement = connection.prepareStatement(
                    "insert into news_categories (name) values (?)");
            statement.setString(1, name);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<CategoryEntity> findAll() {
        List<CategoryEntity> categories = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from news_categories");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CategoryEntity category = new CategoryEntity();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));

                categories.add(category);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    public static CategoryEntity findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from news_categories where id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CategoryEntity category = new CategoryEntity();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                return category;
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Category not found");
    }

    public static void delete(Long id) {
        try {
            NewsService.deleteAllByCategory(id);

            PreparedStatement statement = connection.prepareStatement(
                    "delete from news_categories where id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existsById(Long id) {
        boolean isExists = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select exists(select from news_categories where id = ?)");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            isExists = resultSet.getBoolean(1);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExists;
    }

    private static boolean existsByName(String name) {
        boolean isExists = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select exists(select from news_categories where name = ?)");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            isExists = resultSet.getBoolean(1);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExists;
    }

}
