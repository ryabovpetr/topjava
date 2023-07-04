package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final long serialVersionUID = 1L;
    private static String CREATE_OR_EDIT = "/meal.jsp";
    private static String MEALS = "/meals.jsp";
    private final InMemoryMealRepository inMemoryMealRepository;

    public MealServlet() {
        super();
        inMemoryMealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward = "";
        String action = request.getParameter("action");

        if (action == null || action.equalsIgnoreCase("meals")) {
            forward = MEALS;
            request.setAttribute("mealsTo", inMemoryMealRepository.getAll());
        } else if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            inMemoryMealRepository.delete(id);
            forward = MEALS;
            request.setAttribute("mealsTo", inMemoryMealRepository.getAll());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = CREATE_OR_EDIT;
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = inMemoryMealRepository.get(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("create")) {
            forward = CREATE_OR_EDIT;
            Meal meal = new Meal(LocalDateTime.now(), "", 10);
            request.setAttribute("meal", meal);
        } else {
            forward = MEALS;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8 ");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime"), TimeUtil.DATE_TIME_FORMATTER_REVERSE),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) meal.setId(Integer.parseInt(id));
        inMemoryMealRepository.save(meal);

        RequestDispatcher view = request.getRequestDispatcher(MEALS);
        request.setAttribute("mealsTo", inMemoryMealRepository.getAll());
        view.forward(request, response);
    }
}
