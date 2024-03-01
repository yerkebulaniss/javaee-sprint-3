package kz.bitlab.academy.users.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.academy.users.entity.UserEntity;
import kz.bitlab.academy.users.service.UserService;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("currentUser", session.getAttribute("currentUser"));

        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");
        String fullName = req.getParameter("fullName");
        HttpSession session = req.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");

        String redirect = UserService.update(currentUser, oldPassword, password, rePassword, fullName);

        if (!redirect.isBlank()) {
            resp.sendRedirect("/profile?" + redirect);
            return;
        }

        currentUser.setPassword(password);
        currentUser.setFullName(fullName);
        session.setAttribute("currentUser", currentUser);
        resp.sendRedirect("/profile");
    }

}
