package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        ValidationUtil.checkNew(meal);
        log.info("Create {} for userId={}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = authUserId();
        ValidationUtil.assureIdConsistent(meal, id);
        log.info("Update {} for userId={}", meal, userId);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("Delete id={} for userId={}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("Get id={} for userId={}", id, userId);
        return service.get(id, userId);
    }

    public Collection<MealTo> getAll() {
        int userId = authUserId();
        log.info("getAll for userId={}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getFilteredByDateAndTimeMeals(LocalDate startDate, LocalDate endDate,
                                                            LocalTime startTime, LocalTime endTime) {
        int userId = authUserId();
        log.info("getFilteredByDateAndTimeMeals between dates {} - {} and time {} - {} for userId={}",
                startDate, endDate, startTime, endTime, userId);

        Collection<Meal> mealsFilteredByDate = service.getFilteredByDateMeals(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsFilteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Collection<MealTo> getByDescription(String description) {
        int userId = authUserId();
        log.info("Get with description={} for userId={}", description, userId);

        Collection<Meal> mealsFilteredByUserId = service.getAll(userId);
        return MealsUtil.getFilteredTosByDescription(mealsFilteredByUserId, SecurityUtil.authUserCaloriesPerDay(),
                description);
    }

}