package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.List;

public interface MealRepository {
    void save(Meal meal);

    void delete(Integer id);

    Collection<MealTo> getAll();

    Meal get(Integer id);
}
