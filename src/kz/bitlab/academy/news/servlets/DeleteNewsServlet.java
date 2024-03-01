package kz.bitlab.academy.news.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.academy.news.service.NewsService;
import kz.bitlab.academy.users.entity.UserEntity;

import java.io.IOException;

@WebServlet("/deleteNews")
public class DeleteNewsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");

        if (currentUser.getRoleId() != 1) {
            throw new RuntimeException("Permissions denied!!!");
        }

        Long id = Long.parseLong(req.getParameter("id"));
        NewsService.delete(id);
        resp.sendRedirect("/");
    }

}
