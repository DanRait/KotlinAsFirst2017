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
    if (parts.size != 3) {
        return ""
    }
    var day = parts[0].toInt()
    var month = parts[1]
    var year = parts[2].toInt()
    val listOfMonths = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря")
    if (!listOfMonths.contains(month)) {
        return ""
    }
    for (i in 1..12) {
        if (month == listOfMonths[i - 1]) {
            month = i.toString()
        }
    }
    if (day <= 0 || day > 31) {
        return ""
    }
    if (year < 0) {
        return ""
    }
    return String.format("%02d.%02d.%d", day, month.toInt(), year)
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    try {
        if (parts.size != 3) {
            return ""
        }
        var day = parts[0].toInt()
        var month = parts[1].toInt()
        var year = parts[2].toInt()
        val listOfMonths = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря")
        if (day <= 0 || day > 31) {
            return ""
        }
        if (month <= 0 || month > 12) {
            return ""
        }
        if (year < 0) {
            return ""
        }
        var month_str = listOfMonths[month - 1]
        return (day.toString() + " " + month_str + " " + year.toString())
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
    if (!phone.matches(Regex("""\+?[()\s\d-]+"""))) return ""
    return phone.replace(Regex("""[()\s-]"""), "")
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
    if (Regex("""[%-]+\d+""").containsMatchIn(jumps)) {
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
    val correctFormat = Regex("""\d+\s+[-+%]+\s+""").replace(jumps + " ", " ")
    if (!Regex("""\s+""").containsMatchIn(correctFormat)) return -1
    // jump = 220 + 224 %+ 228 %- 230 + 232 %%- 234 %
    val res1 = Regex("\\%+\\+").replace(jumps, "\\+") // %+ -> +   | 220 + 224 + 228 %-
    val res2 = Regex("\\d+\\s{1}\\%+-*").replace(res1, "")  // %, %-, %%- -> "" | 220 + 224

    if (!Regex("""\d+""").containsMatchIn(res2)) {
        return -1
    }
    val matchedResults = Regex(pattern = "\\d+").findAll(res2)
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
    val correct = expression.matches(Regex("""(?:\d+\s*[-+]\s*)+\d+|\d+"""))
    if (!correct) {
        throw IllegalArgumentException("Bad expression")
    }
    if (Regex("\\d+\\s\\d+").containsMatchIn(expression)) { // двух чисел подряд "1 2" не допускается
        return -1
    }
    if (Regex("[\\+\\-]\\s[\\+\\-]").containsMatchIn(expression)) { //Наличие двух знаков подряд не допускается
        return -1
    }
    if (Regex("[^\\+\\-\\d\\s]").containsMatchIn(expression)) {
        throw IllegalArgumentException("Bad expression")
    }
    val res1 = Regex("\\-\\s\\d+\\s*").replace(expression, "") // positivnie 2 + 31 + 13
    val res2 = Regex("\\+\\s\\d+").replace(expression, "")  // negativnie 2 - 40
    val res3 = Regex("^\\d+\\s").replace(res2, "")  // negativnie - 40

    val matchedResults = Regex(pattern = "\\d+").findAll(res1) // 2 31 13
    val matchedResults2 = Regex(pattern = "\\d+").findAll(res3) // 40

    for (matchedText in matchedResults) {
        positivnie_sum += matchedText.value.toInt() // 2 + 31 + 13 = 46
    }
    for (matchedText2 in matchedResults2) {
        negativnie_sum += matchedText2.value.toInt() // 40
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
    var rest = 0
    var sum = 0
    if (Regex("[^IXMLCVD]").containsMatchIn(roman)) {
        return -1
    }
    if (roman == "") {
        return -1
    }
    val numbers = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val symbols = arrayOf<String>("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var result = roman
    while (result.length > 0) {
        for (i in 0..12) {
            val put = "MMM"
            if (Regex("^${symbols[i]}").containsMatchIn(result)) {
                sum += numbers[i]
                result = Regex("^${symbols[i]}").replace(result, "")
            }
        }
    }
    return sum
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

