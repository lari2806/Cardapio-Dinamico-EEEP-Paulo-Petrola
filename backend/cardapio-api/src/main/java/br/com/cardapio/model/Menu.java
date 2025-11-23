package br.com.cardapio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private int calories;

    @Column(nullable = false)
    private String mealType; //pode ser lanche da manha, tarde, ou almoco

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private Integer week;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }



}