import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        //Arrange
        addRestaurant();

        LocalTime timeWhenOpen = LocalTime.parse("13:00:00");

        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(timeWhenOpen);

        //Act
        boolean isRestaurantOpen = mockRestaurant.isRestaurantOpen();

        //Assert
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        //Arrange
        addRestaurant();

        LocalTime timeWhenClosed = LocalTime.parse("23:00:00");

        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(timeWhenClosed);

        //Act
        boolean isRestaurantOpen = mockRestaurant.isRestaurantOpen();

        //Assert
        assertFalse(isRestaurantOpen);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void total_order_value_calculated_should_be_same_as_sum_of_price_of_all_menu_items_ordered(){
        //Arrange
        addRestaurant();
        List<String> orderItems = new ArrayList<String>();
        Integer orderValue = 0;

        orderItems.add(restaurant.getMenu().get(0).getName());
        orderValue = restaurant.getMenu().get(0).getPrice();

        orderItems.add(restaurant.getMenu().get(1).getName());
        orderValue += restaurant.getMenu().get(1).getPrice();

        //Act
        Integer orderValueCalculated = restaurant.calculateTotalOrderValue(orderItems);

        //Assert
        assertEquals(orderValue, orderValueCalculated);
    }

    @Test
    public void total_order_value_calculated_should_be_0_when_an_empty_list_menu_items_ordered(){
        //Arrange
        addRestaurant();
        List<String> orderItems = new ArrayList<String>();
        Integer orderValue = 0;

        //Act
        Integer orderValueCalculated = restaurant.calculateTotalOrderValue(orderItems);

        //Assert
        assertEquals(orderValue, orderValueCalculated);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void addRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
}