package Restaurant.Enums;

public enum MealType {
    APPETIZERS("appetizers"),
    MAIN_COURSES("main courses"),
    SIDE_DISHES("side dishes"),
    VEGETARIAN_MENU("vegetarian menu"),
    SOUP("soup"),
    DESSERT("dessert"),
    CHILDREN_MEAL("children meal"),
    NON_ALCOHOLIC_DRINK("non alcoholic drink"),
    ALCOHOLIC_DRINK("alcoholic drink");

    private final String readableName;

    MealType(final String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName(){
        return readableName;
    }
}
