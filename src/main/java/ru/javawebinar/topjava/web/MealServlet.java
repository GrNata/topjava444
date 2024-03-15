package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private InMemoryMealRepository repository;
    private ConfigurableApplicationContext app;
    private MealRestController controller;
    private MealService service;


    @Override
    public void init() throws ServletException{
        log.info("init method");

        app = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = app.getBean(MealRestController.class);
        service = app.getBean(MealService.class);
        repository = app.getBean(InMemoryMealRepository.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("POST");

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        String action = request.getParameter("action");
        System.out.println("POST action: "+ action);

        if (action.equals("filter")) {
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            Collection<MealTo> meals = controller.getAllByFilterDateTime(fromDate, toDate, fromTime, toTime);
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

//            response.sendRedirect("meals");
        } else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")),
                    SecurityUtil.authUserId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            controller.save(meal);
//        controller.save(meal, SecurityUtil.authUserId());
            response.sendRedirect("meals");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET");

        String action = request.getParameter("action");
//        String fromTime = request.getParameter("fromTime");
//        System.out.println("GET fromTime: " + fromTime);

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                controller.delete(id);
//                controller.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "cancel":
                log.info("cancel");
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
//                        MealsUtil.getTos(controller.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                        controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        if (app != null) {
            app.close();
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}