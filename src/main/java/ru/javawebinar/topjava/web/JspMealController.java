package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    private int getUserId() {
        return authUserId();
    }

    @GetMapping
    public String getMeals(Model model) {
        log.info("meals userId={}", getUserId());
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(getUserId()), authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, getUserId());
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, getUserId());
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        log.info("form of creating meal for userId={}", getUserId());
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping()
    public String updateOrCreate(HttpServletRequest request) {
        int userId = getUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))){
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            log.info("update meal id={} for userId={}", id, userId);
            service.update(meal, userId);
        } else {
            log.info("create meal for userId={}", userId);
            service.create(meal, getUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String updateForm(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        log.info("update meal id={} from userId={}", id, getUserId());
        model.addAttribute("meal", service.get(id, getUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        log.info("delete meal id={} from userId={}", id, getUserId());
        service.delete(id, getUserId());
        return "redirect:/meals";
    }
}
