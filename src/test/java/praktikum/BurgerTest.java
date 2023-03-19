package praktikum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    Burger burger;
    @Mock
    Bun bun;
    Ingredient firstIngredient = (Ingredient)Mockito.mock(Ingredient.class);
    Ingredient secondIngredient = (Ingredient)Mockito.mock(Ingredient.class);

    @Spy
    private Ingredient sauce = new Ingredient(IngredientType.SAUCE, "cosmo",  100500F);
    private Ingredient filling = new Ingredient(IngredientType.FILLING, "Cheese", 10.0F);
    public BurgerTest() {
    }

    @Before
    public void setUp() {
        this.burger = new Burger();
    }

    @Test
    public void checkSetBuns() {
        this.burger.setBuns(this.bun);
        Mockito.when(this.bun.getName()).thenReturn("black bun");
        String actual = this.bun.getName();
        assertEquals("Возвращается неверное имя булочки", "black bun", actual);
    }

    @Test
    public void addIngredientTest() {
        this.burger.addIngredient(this.firstIngredient);
        assertEquals("Неверное количество ингридиентов в бургере", 1L, (long)this.burger.ingredients.size());
    }

    @Test
    public void RemoveIngredientTest() {
        this.burger.addIngredient(this.firstIngredient);
        this.burger.removeIngredient(0);
        Assert.assertTrue("Ингридиент не удален", this.burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientTest() {
        this.burger.addIngredient(this.firstIngredient);
        this.burger.addIngredient(this.secondIngredient);
        this.burger.moveIngredient(0, 1);
        assertEquals("Ингридиенты не поменялись местами", "secondIngredient", ((Ingredient)this.burger.ingredients.get(0)).toString());
    }

    @Test
    public void getReceiptTest() {
        this.burger.setBuns(bun);
        this.burger.addIngredient(sauce);
        burger.addIngredient(filling);

        Mockito.when(bun.getName()).thenReturn("Bun");
        Mockito.when(bun.getPrice()).thenReturn(100500F);

        String expectedResult = "(==== Bun ====)\n= sauce cosmo =\n= filling Cheese =\n(==== Bun ====)\n\nPrice: 301510,000000\n";
        String actualResult = burger.getReceipt();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void checkingGetPrice() {
        this.burger.setBuns(bun);
        this.burger.addIngredient(secondIngredient);

        Mockito.when(bun.getPrice()).thenReturn(100F);

        float expectedResult = 200.0F;
        float actualResult = burger.getPrice();
        assertEquals(expectedResult, actualResult, 0.0F);
    }
}
