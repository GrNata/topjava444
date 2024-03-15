package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private SecurityUtil securityUtil;

    public UserServlet() {
        this.securityUtil = new SecurityUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectUser = req.getParameter("userId");

        System.out.println("selectUser = " + selectUser);

        securityUtil.setAuthUserId(Integer.parseInt(selectUser));

        System.out.println("ServletUsr, SecurityUtil.authUserId() = " + SecurityUtil.authUserId());

        resp.sendRedirect("meals");
    }
}
