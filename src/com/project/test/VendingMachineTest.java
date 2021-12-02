package com.project.test;

import com.project.Product;
import com.project.VendingMachine;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class VendingMachineTest {
    VendingMachine machine = new VendingMachine();

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void cancelRequestAfterInsertMoney() {
        machine.insertMoney(20000);
        machine.cancelRequest();

        assertEquals("Your 20000 is refunded", outputStreamCaptor.toString().trim());
    }

    @Test
    void cancelRequestBeforeInsertMoney() {
        Exception exception = null;
        try{
            machine.cancelRequest();
        } catch (Exception e){
            exception = e;
        }
        String expectedMessage = "No money inserted";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void insertNotEnoughMoney() {
        machine.insertMoney(10000);
        machine.insertProduct(Product.SODA);
        machine.dispense();

        assertEquals("Money inserted is not enough\r\n" +
                "Your 10000 is refunded", outputStreamCaptor.toString().trim());
    }

    @Test
    void insertMoneyAndProductThenCancel(){
        machine.insertMoney(10000);
        machine.insertProduct(Product.SODA);
        machine.cancelRequest();

        assertEquals("Your 10000 is refunded", outputStreamCaptor.toString().trim());
    }

    @Test
    void insertMoneyAndRequestDispense(){
        Exception exception = null;
        try{
            machine.insertMoney(10000);
            machine.dispense();
        }catch (Exception e){
            exception = e;
        }
        String expectedMessage = "No product selected";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void insertNotSuitableMoney() {
        machine.insertMoney(5000);
        assertEquals("Money is not suitable\r\n" +
                "Your 5000 is refunded", outputStreamCaptor.toString().trim());
    }

    @Test
    void insertMoreMoneyThanProductPrice(){
        machine.insertMoney(50000);
        machine.insertProduct(Product.SODA);
        machine.dispense();
        assertEquals("Your SODA here.\r\n" +
                "Your change is 30000 VND.", outputStreamCaptor.toString().trim());
    }

    @Test
    void insertTwoNotesOfMoneyAndReceiveProduct() {
        machine.insertMoney(10000);
        machine.insertMoney(10000);
        machine.insertProduct(Product.SODA);
        machine.dispense();
        assertEquals("Your SODA here.", outputStreamCaptor.toString().trim());
    }

    @Test
    void insertMoneyTwiceAfterInsertProduct() {
        Exception exception = null;
        try{
            machine.insertMoney(10000);
            machine.insertProduct(Product.PEPSI);
            machine.insertMoney(10000);
        }catch (Exception e){
            exception = e;
        }
        String expectedMessage = "Money inserted";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void insertProductManyTime() {
        machine.insertMoney(10000);
        machine.insertProduct(Product.PEPSI);
        machine.insertProduct(Product.PEPSI);
        machine.insertProduct(Product.COKE);
        machine.dispense();
        assertEquals("Your COKE here.", outputStreamCaptor.toString().trim());
    }

    @Test
    void promotionUpdatedWhenDayEnd() {
        for(int i = 0; i < 10; i++) {
            machine.insertMoney(10000);
            machine.insertMoney(10000);
            machine.insertProduct(Product.SODA);
            machine.dispense();

            machine.insertMoney(20000);
            machine.insertProduct(Product.SODA);
            machine.dispense();

            machine.insertMoney(20000);
            machine.insertProduct(Product.SODA);
            machine.dispense();
            System.out.println();
        }

        int promotionPercentExpected = 10;
        if(machine.getPromotionBudget() < 50000){
            promotionPercentExpected = 50;
        }
        machine.calculatePromotionInEndDay();    //end of day 1
        assertEquals(promotionPercentExpected, machine.getPromotionPercent());
    }
}