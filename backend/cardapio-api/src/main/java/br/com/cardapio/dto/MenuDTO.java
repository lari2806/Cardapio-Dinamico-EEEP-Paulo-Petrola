package br.com.cardapio.dto;

public class MenuDTO {
    private String mealType;
    private String dayOfWeek;
    private String food;
    private int calories; 
    private Integer week; 

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getFood() { return food; }
    public void setFood(String food) { this.food = food; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public Integer getWeek() { return week; }
    public void setWeek(Integer week) { this.week = week; }
}
