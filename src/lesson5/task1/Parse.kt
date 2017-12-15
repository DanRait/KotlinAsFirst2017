@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

import java.lang.IllegalArgumentException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    var result = ""
    var res = ""
    var day = ""
    var month = ""
    var year = ""
    var number = 0
    try {
        for (part in parts) {
            number += 1
            res = part
            println (res)
            if (number == 1) day = res
            if (number == 2) {
                val listOfPairs = listOf(Pair("января", "01"), Pair("февраля", "02"), Pair("марта", "03"), Pair("апреля", "04"), Pair("мая", "05"), Pair("июня", "06"), Pair("июля", "07"), Pair("августа", "08"), Pair("сентября", "09"), Pair("октября", "10"), Pair("ноября", "11"), Pair("декабря", "12"))
                for (i in 0 until listOfPairs.size) {
                    if (part == listOfPairs[i].first) {
                        res = listOfPairs[i].second
                        println ("res" + res)
                    }
                }
                month = res
            }
            if (number == 3) year = res
            println( "---" + day.toInt() + " " + month + " " + year)
        }
        if (year == "0") {
            return String.format("%02d.%02d.%d", day.toInt(), month.toInt(), year.toInt())
        } else {
            return String.format("%02d.%02d.%02d", day.toInt(), month.toInt(), year.toInt())
        }
    } catch (e: NumberFormatException) {
        return ""
    }
}
 
/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(  ".")
    var result = ""
    var res = ""
    var day = ""
    var month = ""
    var year = ""
    var number = 0
    try {
        for (part in parts) {
            number += 1
            res = part
            if (number == 1) day = res
            if (number == 2) {
                val listOfPairs = listOf(Pair("января", "01"), Pair("февраля", "02"), Pair("марта", "03"), Pair("апреля", "04"), Pair("мая", "05"), Pair("июня", "06"), Pair("июля", "07"), Pair("августа", "08"), Pair("сентября", "09"), Pair("октября", "10"), Pair("ноября", "11"), Pair("декабря", "12"))
                for (i in 0 until listOfPairs.size) {
                    if (part == listOfPairs[i].second) {
                        res = listOfPairs[i].first
                        break
                    } else res = ""
                }
                month = res
            }
            if (number == 3) year = res
            if (number > 3) return ""
        }
        println(day.toInt().toString() + month + year)
        if ( month == "") {
            result = ""
        } else {
            result = day.toInt().toString() + " " + month + " " + year
        }
        //return String.format("%s:%s:%s", day.toString(), month.toString(), year.toString())
        return result.toString()
        //println(String.format("%02d.%02d.%02d", day.toInt(), month, year.toInt()))
        //return String.format("%02d.%02d.%02d", day.toInt(), month, year.toInt())
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val parts = phone.split(" ", "-", "(", ")", "_")
    var result = ""
    var res = ""
    var pattern = Regex("^\\+?[0123456789]*")
    if (Regex("[^\\s\\d\\+\\-\\(\\)]").containsMatchIn(phone)) {
        return ""
    }
    try {
        for (part in parts) {
            res += part
        }
        if (res.matches(pattern)) {
            result = res
        } else result = ""
        return result
        } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    println(jumps)
    var rest = 0
    var biggest = 0
    if (!Regex("""\d+""").containsMatchIn(jumps)) {
        return -1
    }
    if (Regex("[^\\s\\d\\-%]").containsMatchIn(jumps)) {
        return -1
    }
    val matchedResults = Regex(pattern = """\d+""").findAll(jumps)
    try {
        for (matchedText in matchedResults) {
            rest = matchedText.value.toInt()
            println("res = " + rest)
            if (rest > biggest) {
                biggest = rest
            }
        }
        return biggest
        } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var rest = 0
    var bestResult = 0
    if (!Regex("""\d+""").containsMatchIn(jumps)) {
        return -1
    }
    if (Regex("[^\\s\\d+\\-%\\+]").containsMatchIn(jumps)) {
        return -1
    }
    // jump = 220 + 224 %+ 228 %- 230 + 232 %%- 234 %
    val res1 = Regex("\\%+?\\+").replace(jumps, "\\+") // %+ -> +   | 220 + 224 + 228 %- 230 + 232 %%- 234 %
    println ("res1 = " + res1)
    val res2 = Regex("\\%+?\\-*?").replace(res1, "-")  // %%- -> -  | 220 + 224 + 228 -- 230 + 232 --- 234 -
    println ("res2 = " + res2)
    val res3 = Regex("\\d+\\s{1}\\-+").replace(res2, "") // "123 ---" -> ""   | 220 + 224 +  230 +
    println ("res3 = " + res3)
    if (res3 == "") {
        return -1
    }
    val matchedResults = Regex(pattern = "\\d+").findAll(res3)
            for (matchedText in matchedResults) {
            rest = matchedText.value.toInt()
            if (rest > bestResult) {
                bestResult = rest
            }
        }
        return bestResult
    }


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var common_sum = 0
    var negativnie_sum = 0
    var positivnie_sum = 0

    if (Regex("\\d+\\s\\d+").containsMatchIn(expression)) { // двух чисел подряд "1 2" не допускается
        return -1
    }
    if (Regex("[\\+\\-]\\s[\\+\\-]").containsMatchIn(expression)) { //Наличие двух знаков подряд "13 + + 10" не допускается
        return -1
    }
    if (Regex("[^\\+\\-\\d\\s]").containsMatchIn(expression)) throw IllegalArgumentException("Bad expression")
    // expression = 2 + 31 - 40 + 13
    val res1 = Regex("\\-\\s\\d+\\s*").replace(expression, "") // positivnie 2 + 31 + 13
    //println("res1 = " + res1)
    val res2 = Regex("\\+\\s\\d+").replace(expression, "")  // negativnie 2 - 40
    val res3 = Regex("^\\d+\\s").replace(res2, "")  // negativnie - 40
    //println("res3 = " + res3)

    val matchedResults = Regex(pattern = "\\d+").findAll(res1) // 2 31 13
    val matchedResults2 = Regex(pattern = "\\d+").findAll(res3) // 40

    for (matchedText in matchedResults) {
        //println("res = " + matchedText.value.toInt())
        positivnie_sum += matchedText.value.toInt() // 2 + 31 + 13 = 46
        //println("positivnie_sum = " + positivnie_sum)
        }
    for (matchedText2 in matchedResults2) {
        //println("res = " + matchedText2.value.toInt())
        negativnie_sum += matchedText2.value.toInt() // 40
        //println("negativnie_sum = " + negativnie_sum)
        }
    common_sum = positivnie_sum - negativnie_sum // 46 - 40 = 6
    return common_sum

}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String = TODO()

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    println(roman)
    var rest = 0
    var sum = 0
    if (Regex("[^IXMLCVD]").containsMatchIn(roman)) {
        return -1
    }
    if (roman == "") {
        return -1
    }
    val matchedResults = Regex(pattern = """\w+""").findAll(roman)
    val res1 = Regex("IX").replace(roman, "9 ")
    val res2 = Regex("IV").replace(res1, "4 ")
    val res3 = Regex("XL").replace(res2, "40 ")
    val res4 = Regex("XC").replace(res3, "90 ")
    val res5 = Regex("CD").replace(res4, "400 ")
    val res6 = Regex("CM").replace(res5, "900 ")
    val res7 = Regex("M").replace(res6, "1000 ")
    val res8 = Regex("D").replace(res7, "500 ")
    val res9 = Regex("C").replace(res8, "100 ")
    val res10 = Regex("L").replace(res9, "50 ")
    val res11 = Regex("X").replace(res10, "10 ")
    val res12 = Regex("V").replace(res11, "5 ")
    val res13 = Regex("I").replace(res12, "1 ")
    println ("---" + res13)

    val matchedResults2 = Regex(pattern = """\d+""").findAll(res13)
    try {
        for (matchedText in matchedResults2) {
            rest = matchedText.value.toInt()
            println("res = " + rest)
            sum +=  rest
        }
        println ("sum = " + sum)
        return sum
    } catch (e: NumberFormatException) {
        return -1
    }
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()

