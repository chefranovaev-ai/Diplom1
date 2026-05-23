package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredientSauce;

    @Mock
    private Ingredient mockIngredientFilling;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        assertEquals("Булочка не установлена в бургер", mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(mockIngredientSauce);
        assertTrue("Список ингредиентов не содержит добавленный элемент", burger.ingredients.contains(mockIngredientSauce));
        assertEquals("Размер списка ингредиентов не увеличился до 1", 1, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredientSauce);
        burger.removeIngredient(0);
        assertTrue("Список ингредиентов не стал пустым после удаления элемента", burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(mockIngredientSauce);   // Индекс 0
        burger.addIngredient(mockIngredientFilling); // Индекс 1

        burger.moveIngredient(0, 1);

        assertEquals("На индексе 0 теперь должен находиться filling", mockIngredientFilling, burger.ingredients.get(0));
        assertEquals("На индексе 1 теперь должен находиться sauce", mockIngredientSauce, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);
        Mockito.when(mockIngredientSauce.getPrice()).thenReturn(50.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredientSauce);

        float expectedPrice = (100.0f * 2) + 50.0f;
        assertEquals("Расчет итоговой стоимости бургера выполнен неверно", expectedPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void testGetReceipt() {
        Mockito.when(mockBun.getName()).thenReturn("Краторная булка");
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);

        Mockito.when(mockIngredientSauce.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(mockIngredientSauce.getName()).thenReturn("Чили");
        Mockito.when(mockIngredientSauce.getPrice()).thenReturn(50.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredientSauce);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Краторная булка ====)"));
        assertTrue(receipt.contains("= sauce Чили ="));
        assertTrue(receipt.contains("Price: 250"));

    }
}
