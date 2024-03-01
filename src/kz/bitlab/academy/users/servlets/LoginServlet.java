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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserEntity currentUser = UserService.authenticate(email, password);

        if (currentUser != null) {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", currentUser);
            resp.sendRedirect("/profile");
            return;
        }

        resp.sendRedirect("/login?incorrectCredentials");
    }

}
