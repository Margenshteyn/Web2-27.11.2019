package servlet;

import com.google.gson.Gson;
import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || password == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
                /*User user = userService.getAllUsers().stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password)).findFirst().orElse(null);
            if (user == null) {
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                if (userService.authUser(user)) {
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    resp.setContentType("text/html;charset=utf-8");
                    resp.getWriter().println(json);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }*/
                List<User> users = userService.getAllUsers();
                for (User user : users) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        userService.authUser(user);
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        resp.setContentType("text/html;charset=utf-8");
                        resp.getWriter().println(json);
                        resp.setStatus(HttpServletResponse.SC_OK);
                        return;
                    } else {
                        resp.setContentType("text/html;charset=utf-8");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("authPage"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
