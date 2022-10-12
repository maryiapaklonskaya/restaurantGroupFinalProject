package Sasha;

public class Meal {
    private String mealTitle;
    private int price;
    private MealType mealType;

    private int mealTypeId;

    public Meal(String mealTitle, int price, int mealTypeId){
        this.mealTitle = mealTitle;
        this.price = price;
        this.mealTypeId = mealTypeId;
    }

    public String getMealTitle() {return mealTitle;}

    public int getPrice() {return price;}
}
