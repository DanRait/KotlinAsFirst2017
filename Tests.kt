package lesson8.task2

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Tests {
    @Test
    @Tag("Example")
    fun parseExpr() {
        assertEquals(mapOf(1 to 7, 2 to 16, -1 to 13), parseExpr("input/expr_in1.txt", listOf(1, 2, -1)))
        assertEquals(mapOf(1 to -1, 3 to 9, 4 to 6), parseExpr("input/expr_in2.txt", listOf(1, 3, 4)))
        try {
            parseExpr("input/expr_in3.txt", listOf(0))
            throw AssertionError("NumberFormatException expected")
        }
        catch (e: NumberFormatException) {}
        assertEquals(mapOf(-2 to 12, -1 to 2, 0 to 0, 1 to 0, 2 to -4), parseExpr("input/expr_in4.txt", listOf(-2, -1, 0, 1, 2)))
        assertEquals(mapOf(1 to -16, 3 to -54, 4 to -73), parseExpr("input/expr_in5.txt", listOf(1, 3, 4)))
    }

    @Test
    @Tag("Easy")
    fun food() {
        assertEquals(listOf("яблоки", "молоко"), food("input/products.txt", "1000", "520"))
        assertEquals(listOf("яблоки"), food("input/products.txt", "1000", "420"))
        assertEquals(listOf("0"), food("input/products.txt", "1000", "20"))
        assertEquals(listOf("яблоки"), food("input/products_denied.txt", "1000", "420"))
    }

    @Test
    @Tag("Hard")
    fun food2() {
        assertEquals(mapOf("икра" to 173, "молоко" to 1040, "яблоки" to 1300), food2("input/products.txt", "100", "520"))
        assertEquals(mapOf("икра" to 173, "молоко" to 1040, "яблоки" to 1300), food2("input/products.txt", "300", "520"))
        //assertEquals(listOf("яблоки"), food2("input/products.txt", "1040", "420"))
        //assertEquals(listOf("0"), food2("input/products.txt", "1050", "20"))
        //assertEquals(listOf("яблоки"), food2("input/products_denied.txt", "10", "420"))
    }


    @Test
    fun footballFirst(){
        assertEquals("Арсенал: commonBall = 4 pobed = 1 nicnyua = 1 porazhenii = 1 Zabito = 6 Propusheno = 5 Бавария: commonBall = 1 pobed = 0 nicnyua = 1 porazhenii = 2 Zabito = 6 Propusheno = 14 Интер: commonBall = 3 pobed = 0 nicnyua = 3 porazhenii = 0 Zabito = 3 Propusheno = 3 Барселона: commonBall = 7 pobed = 2 nicnyua = 1 porazhenii = 0 Zabito = 11 Propusheno = 4 ", footballFirst("input/footChempionat.txt"))



    }
}