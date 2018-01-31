package lesson8.task2

import lesson3.task1.isPrime
import lesson7.task1.Cell
import lesson7.task1.createMatrix
import lesson7.task1.MatrixImpl
import lesson7.task1.Matrix
import lesson8.task2.Expression.Operation.*
import java.io.File
import java.io.IOException
import java.util.regex.Pattern
import java.io.IOException.*

/**
 * Пример
 *
 * Во входном файле с именем inputName
 * содержится строчка, содержащая описание функции от x, например:
 *
 * 3*x*x - 2 / x  + 7 -x
 *
 * В списке values содержатся целочисленные значения аргумента x, например, (1, 2, -1)
 *
 * Вернуть ассоциативный массив (map), содержащий отображение заданных значений аргумента
 * в значение заданной в файле функции, в данном случае
 *
 * (1 to 7, 2 to 16, -1 to 13)
 *
 * В функции могут присутствовать четыре арифметических действия и круглые скобки.
 * Обратите внимание, что функция является целочисленной,
 * то есть деление также следует трактовать как целочисленное.
 */
fun parseExpr(inputName: String, values: List<Int>): Map<Int, Int> {
    val expr = File(inputName).readLines().firstOrNull()?.parseExpr() ?: throw IllegalArgumentException()
    val result = mutableMapOf<Int, Int>()
    for (value in values) {
        result[value] = expr.calculate(value)
    }
    return result
}

fun String.parseExpr(): Expression {
    val pattern = Pattern.compile("""x|\+|-|\*|/|\(|\)|\d+| +?|.+?""")
    val matcher = pattern.matcher(this)
    val groups = mutableListOf<String>()
    while (matcher.find()) {
        val group = matcher.group()
        if (group[0] != ' ') {
            groups.add(group)
        }
    }
    return Parser(groups).parse()
}

sealed class Expression {
    object Variable : Expression()

    class Constant(val value: Int) : Expression()

    enum class Operation {
        PLUS,
        MINUS,
        TIMES,
        DIV;
    }

    class Binary(val left: Expression, val op: Operation, val right: Expression) : Expression()

    class Negate(val arg: Expression) : Expression()

    fun calculate(x: Int): Int = when (this) {
        Variable -> x
        is Constant -> value
        is Binary -> when (op) {
            PLUS  -> left.calculate(x) + right.calculate(x)
            MINUS -> left.calculate(x) - right.calculate(x)
            TIMES -> left.calculate(x) * right.calculate(x)
            DIV   -> left.calculate(x) / right.calculate(x)
        }
        is Negate -> -arg.calculate(x)
    }
}

class Parser(private val groups: List<String>) {
    private var pos = 0

    fun parse(): Expression {
        val result = parseExpression()
        if (pos < groups.size) {
            throw IllegalStateException("Unexpected expression remainder: ${groups.subList(pos, groups.size)}")
        }
        return result
    }

    private fun parseExpression(): Expression {
        var left = parseItem()
        while (pos < groups.size) {
            val op = operationMap[groups[pos]]
            when (op) {
                PLUS, MINUS -> {
                    pos++
                    val right = parseItem()
                    left = Expression.Binary(left, op, right)
                }
                else -> return left
            }
        }
        return left
    }

    private fun parseItem(): Expression {
        var left = parseFactor()
        while (pos < groups.size) {
            val op = operationMap[groups[pos]]
            when (op) {
                TIMES, DIV -> {
                    pos++
                    val right = parseFactor()
                    left = Expression.Binary(left, op, right)
                }
                else -> return left
            }
        }
        return left
    }

    private fun parseFactor(): Expression =
            if (pos >= groups.size) throw IllegalStateException("Unexpected expression end")
            else {
                val group = groups[pos++]
                when (group) {
                    "x" -> Expression.Variable
                    "-" -> Expression.Negate(parseFactor())
                    "(" -> {
                        val arg = parseExpression()
                        val next = groups[pos++]
                        if (next == ")") arg
                        else throw IllegalStateException(") expected instead of $next")
                    }
                    else -> Expression.Constant(group.toInt())
                }
            }

    private val operationMap = mapOf("+" to PLUS, "-" to MINUS, "*" to TIMES, "/" to DIV)
}

fun food(inputName: String, weight: String, energy: String): List<String> {
    var badProduct = true
    val list = mutableListOf<String>()
    try {
        for (line in File(inputName).readLines()) {
            println(line)
            val correct = line.matches(Regex("""[а-я]*:\s\d+\sг\sбелков,\s\d+\sг\sжиров,\s\d+\sг\sуглеводов"""))
            if (correct) {
                val nameProduct = Regex(":.*").replace(line + " ", "")
                val belok = Regex(".*:\\s|\\sг белков.*").replace(line + " ", "")
                val zhirok = Regex(".*белков,\\s|\\sг жиров.*").replace(line + " ", "")
                val ugolek = Regex(".*жиров,\\s|\\sг углеводов.*").replace(line + " ", "")
                println("nameProduct = " + nameProduct)
                println("belok = " + belok)
                println("zhirok = " + zhirok)
                println("ugolek = " + ugolek)
                val energyProduct = weight.toInt() / 100 * (zhirok.toInt() * 9 + belok.toInt() * 4 + ugolek.toInt() * 4)
                println("nameProduct = " + nameProduct + " energy = " + energyProduct)
                if (energyProduct < energy.toInt()) {
                    list.add(nameProduct)
                    badProduct = false
                }
            } else {
                throw IllegalArgumentException("Bad expression ")
            }
        }
        if (badProduct) {
            return listOf<String>("0")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return list
}

fun <E> Map<E, Int>.bubbleSort(): Map<E, Int> {
    val containerKey = keys.toMutableList()
    val containerSortedValues = values.toMutableList()

    (containerSortedValues.size - 1 downTo 1).forEach { i ->
        (0 until i).forEach { j ->
            if (containerSortedValues[j] > containerSortedValues[j + 1]) {
                val temp = Pair(containerSortedValues[j], containerKey[j])
                containerSortedValues[j] = containerSortedValues[j + 1]
                containerSortedValues[j + 1] = temp.first
                containerKey[j] = containerKey[j + 1]
                containerKey[j + 1] = temp.second
            }
        }
    }
    val answer = mutableMapOf<E, Int>()
    for ((index, key) in containerKey.withIndex()) {
        answer[key] = containerSortedValues[index]
        if (index == minOf(19, containerKey.size)) break
    }
    return answer
}


fun food2(inputName: String, weight: String, energy: String): Map<String, Int> {
    var bestProduct = weight.toInt()*100
    var name = ""
    var nameProduct = ""
    var massaProduct = "0"
    val result = mutableMapOf<String, Int>()
    try {
        for (line in File(inputName).readLines()) {
            //println(line)
            val correct = line.matches(Regex("""[а-я]*:\s\d+\sг\sбелков,\s\d+\sг\sжиров,\s\d+\sг\sуглеводов"""))
            if (correct) {
                nameProduct = Regex(":.*").replace(line + " ", "")
                val belok = Regex(".*:\\s|\\sг белков.*").replace(line + " ", "")
                val zhirok = Regex(".*белков,\\s|\\sг жиров.*").replace(line + " ", "")
                val ugolek = Regex(".*жиров,\\s|\\sг углеводов.*").replace(line + " ", "")
                val massaProduct = energy.toInt() * 100 / (zhirok.toInt() * 9 + belok.toInt() * 4 + ugolek.toInt() * 4)
                result.put(nameProduct, massaProduct)
                if ((massaProduct) > weight.toInt() && (massaProduct < bestProduct) ) {
                    bestProduct = massaProduct
                    name = nameProduct
                }
            } else {
                throw IllegalArgumentException("Bad expression ")
            }
        }
        println(name + " это продукт, рассчитанная масса которого больше weight, но минимальна относительно других продуктов " + bestProduct + " gramm blizhe k weight = " + weight)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return result
}


fun footballFirst(inputName: String): String {
    val result = mutableMapOf<String, Int>()
    var resultStr = ""
    val list = mutableListOf<String>()
    var nRow = 0
    var nColumn = 0
    var commonBall = 0
    var Zabito = 0
    var Propusheno = 0
    var pobed = 0
    var nicnyua = 0
    var porazhenii = 0
    val tmp = createMatrix(4, 4, 0)
    var nameCommand = ""
    var ochki = " "
    try {
        for (line in File(inputName).readLines()) {
           // println("kolichestvoKomand " + nRow)
            val correct = line.matches(Regex("""[А-Я][а-яё]*\s*\d\s\d\s\d\s\d"""))
            if (correct) {
                nameCommand = Regex("\\s.*").replace(line + " ", "")
                list.add(nameCommand)
                ochki = Regex("[А-Я][а-яё]*\\s*").replace(line, "")
                //println ("ochki = " + ochki)
                val parts = ochki.split(" ")
                nColumn = 0
                for (part in parts) {
                    tmp.set(nRow, nColumn, parts[nColumn].toInt())
                    //println ("nRow = " + nRow + "nColumn = " + nColumn)
                    nColumn++
                }
                nRow++
            } else {
                throw IllegalArgumentException("Bad expression ")
            }
        }
        for (i in 0..list.size - 1) {
            Zabito = 0
            Propusheno = 0
            pobed = 0
            nicnyua = 0
            porazhenii = 0
            commonBall = 0
            for (j in 0..list.size - 1) {
                Zabito += tmp[i, j]
                Propusheno += tmp[j, i]
                if (tmp[i,j] > tmp [j,i]) {
                    commonBall += 3
                    pobed +=1
                }
                if ((tmp[i,j] == tmp[j,i]) && (i !=j)) {
                    commonBall += 1
                    nicnyua +=1
                }
                if ((tmp[i,j] < tmp [j,i]) ) {
                    porazhenii += 1
                }
            }
            result.put(list[i], commonBall)
            println (list[i] + " commonBall = " + commonBall + " pobed = " + pobed + " nicnyua = " + nicnyua + " porazhenii = " + porazhenii + " Zabito = " + Zabito + " Propusheno = " + Propusheno)
            resultStr += list[i] + ": commonBall = " + commonBall + " pobed = " + pobed + " nicnyua = " + nicnyua + " porazhenii = " + porazhenii + " Zabito = " + Zabito + " Propusheno = " + Propusheno + " "
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    print(result.bubbleSort())
    return resultStr
}


fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    val answer = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    if (matrix.width != matrix.height) throw IllegalArgumentException()
    for (i in 0..matrix.width - 1) {
        for (j in 0..matrix.height - 1) {
            answer[i, j] = matrix[matrix.width - j - 1, i]
        }
    }
    return answer
}
