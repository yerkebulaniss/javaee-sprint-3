package kz.bitlab.academy.news.service;

import kz.bitlab.academy.categories.entity.CategoryEntity;
import kz.bitlab.academy.categories.service.CategoryService;
import kz.bitlab.academy.core.DBManager;
import kz.bitlab.academy.news.entity.NewsEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsService extends DBManager {

    public static void create(Long categoryId, String title, String content) {
        try {
            if (!CategoryService.existsById(categoryId))
                throw new RuntimeException("Category not found");

            PreparedStatement statement = connection.prepareStatement(
                    "insert into news (category_id, title, content) " +
                            "values (?, ?, ?)");
            statement.setLong(1, categoryId);
            statement.setString(2, title);
            statement.setString(3, content);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Long id, Long categoryId, String title, String content) {
        try {
            if (!CategoryService.existsById(categoryId))
                throw new RuntimeException("Category not found");

            PreparedStatement statement = connection.prepareStatement(
                    "update news set category_id = ?, title = ?, content = ? where id =?");
            statement.setLong(1, categoryId);
            statement.setString(2, title);
            statement.setString(3, content);
            statement.setLong(4, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<NewsEntity> findAll() {
        List<NewsEntity> newsList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from news");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                NewsEntity news = generateNews(resultSet);
                newsList.add(news);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public static NewsEntity findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from news where id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return generateNews(resultSet);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("News not found");
    }

    public static void delete(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "delete from news where id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllByCategory(Long categoryId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "delete from news where category_id = ?");
            statement.setLong(1, categoryId);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NewsEntity generateNews(ResultSet resultSet) throws SQLException {
        CategoryEntity category = CategoryService.findById(resultSet.getLong("category_id"));

        NewsEntity news = new NewsEntity();
        news.setId(resultSet.getLong("id"));
        news.setCategory(category);
        news.setPostDate(resultSet.getTimestamp("post_date").toLocalDateTime());
        news.setTitle(resultSet.getString("title"));
        news.setContent(resultSet.getString("content"));

        return news;
    }

}
