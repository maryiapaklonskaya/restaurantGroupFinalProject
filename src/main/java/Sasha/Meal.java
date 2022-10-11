package Sasha;

public class Meal {
    private String mealTitle;
    private int price;
    private MealType mealType;

    Meal(String mealTitle, int price, MealType mealType){
        this.mealTitle = mealTitle;
        this.price = price;
        setMealType(mealType);
    }

    public String getMealTitle() {return mealTitle;}

    public int getPrice() {return price;}

    public void setMealType(MealType mealType){
        this.mealType = mealType;
    }
}
