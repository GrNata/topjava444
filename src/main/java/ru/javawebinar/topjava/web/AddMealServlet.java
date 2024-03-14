package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class AddMealServlet extends HttpServlet {
    private static final Logger log = getLogger(AddMealServlet.class);

    private MealsUtil mealsUtil;
    private MealRepository repository;

    public AddMealServlet() {
        this.mealsUtil = new MealsUtil();
        this.repository = new MealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to AddUpdateMeal - GET");

        Meal newMeal = new Meal(LocalDateTime.now(), "Еда", 100);
        req.setAttribute("meal", newMeal);
        req.getRequestDispatcher("addUpdateMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("redirect to AddUpdateMeal - POST");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");


        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), format);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        System.out.println(dateTime);
        System.out.println(description);
        System.out.println(calories);

        Meal newMeal = new Meal(dateTime, description, calories);
        repository.addMeal(newMeal);

//        resp.sendRedirect("meals.jsp");
        List<Meal> mealList = MealsUtil.repository.getAllMeals();
        List<MealTo> mealToList = MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, 2000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");

        req.setAttribute("formatter", formatter);
        req.setAttribute("meals", mealToList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
