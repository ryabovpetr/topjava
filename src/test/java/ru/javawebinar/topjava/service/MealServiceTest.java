package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getUserMeal() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, mealUser1);
    }

    @Test
    public void getAdminMeal() {
        Meal meal = service.get(MEAL_ID + 1, ADMIN_ID);
        assertMatch(meal, mealAdmin1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2023, 7, 24),
                LocalDate.of(2023, 7, 25), USER_ID);
        assertMatch(meals, mealUser2, mealUser1);
    }

    @Test
    public void getBetweenInclusiveNullDates() {
        List<Meal> meals = service.getBetweenInclusive(null, null, USER_ID);
        assertMatch(meals, USER_MEALS);
    }

    @Test
    public void getAllByUser() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, USER_MEALS);
    }

    @Test
    public void getAllByAdmin() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, ADMIN_MEALS);
    }

    @Test
    public void updateByUser() {
        Meal updated = getUpdatedUserMeal();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), getUpdatedUserMeal());
    }

    @Test
    public void updateByAdmin() {
        Meal updated = getUpdatedAdminMeal();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_ID + 1, ADMIN_ID), getUpdatedAdminMeal());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal
                (null, LocalDateTime.of(2023, 7, 25, 10, 15), "Duplicate", 15), USER_ID));
    }
}