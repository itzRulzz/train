/*
Необходимо составить программу, которая помогает пользователю составить план поезда. После запуска программа спрашивает пользователя - хочет ли он закончить работу, или составить поезд.

Есть 4 основных шага в создании плана:
-Создать направление - создает направление для поезда (к примеру Бийск - Барнаул). Маршрут составляется случайным выбором двух городов из списка, состоящего, минимум, из 15-и городов. Начальная и конечная точки маршрута должны быть различны.
-Продать билеты - вы получаете рандомное кол-во пассажиров, которые купили билеты на это направление. Количество пассажиров находится в диапазоне от 5-и до 201
-Сформировать поезд - вы создаете поезд и добавляете ему столько вагонов (вместительность каждого вагона определяется рандомно и находится в диапазоне от 5 до 25), сколько хватит для перевозки всех пассажиров. То есть вы прибавляете к поезду по одному вагону рандомной вместительности до тех пор, пока не усадите в них всех пассажиров, купивших билеты
-Отправить поезд - вы отправляете поезд, после чего можете снова создать направление. Программа выдает запрос на окончание или продолжение работы. То есть программа работает до тех пор, пока пользователь не введет слово EXIT.

После каждого этапа выдается соответствующая информация. После 4-го шага программа сообщает, что поезд [направление], состоящий из [количество] вагонов отправлен. Также выдается информация о вместимости каждого вагона и количестве пассажиров в каждом вагоне.

Программа не должна "падать" при вводе неправильных значений
 */

import kotlin.system.exitProcess
import kotlin.math.abs
import kotlin.random.Random

class Train{
    private val cities = arrayOf("Москва",
        "Санкт-Петербург",
        "Тимбукту",
        "Ханты-Мансийск",
        "Париж (село)",
        "Омск",
        "Подольск",
        "Таганрог",
        "Североморск",
        "Архангельск",
        "Владивосток",
        "Тюмень",
        "Сургут",
        "Выборг",
        "Ялта",
        "Саратов"
    )

    private var begin = ""
    private var end = ""

    private var ticketCount = 0

    private var vanVolumes = mutableListOf<Int>()

    fun add_rus_endings(input: Int, type: String):String
    {
        if (type == "ИП"){
            if (input%100 in (11..14)) return "ов"
            return when (input%10) {
                1 -> ""
                in (2..4) -> "а"
                in (5..9) -> "ов"
                0 -> "ов"
                else -> ""
            }
        }
        if (type == "РП"){
            if (input%100 in (11..14)) return "ов"
            return when (input%10) {
                1 -> "а"
                in (2..9) -> "ов"
                0 -> "ов"
                else -> ""
            }
        }
        return ""
    }

    fun route(){
        println("Шаг 1: (Выбор направления)")
        begin = cities[Random.nextInt(0, 15)]
        end = begin
        while (begin == end)
            end = cities[Random.nextInt(0, 15)]
        println("Начальный город: $begin")
        println("Конечный город: $end\n")
    }

    fun sell_tickets(){
        println("Шаг 2: (Продажа билетов)")
        ticketCount = Random.nextInt(5, 201)
        println("Было куплено $ticketCount билет${add_rus_endings(ticketCount, "ИП")}\n")
    }

    fun form_train(){
        println("Шаг 3: (Формирование поезда)")
        var sum = 0
        while (sum < ticketCount){
            vanVolumes.add(Random.nextInt(5, 26))
            println("Вместимость вагона ${vanVolumes.count()}: ${vanVolumes.last()}")
            sum += vanVolumes.last()
        }

        println("(${vanVolumes.count()} вагон${add_rus_endings(vanVolumes.count(), "ИП")})\n")
    }

    fun output(){
        println("Поезд $begin - $end, состоящий из ${vanVolumes.count()} вагон${add_rus_endings(vanVolumes.count(), "РП")} был отправлен")
        for (i in vanVolumes.indices){
            println("Вместимость вагона ${i + 1}: ${vanVolumes[i]}")
            ticketCount -= vanVolumes[i]
        }
    }
}

fun main() {
    do{
        val train = Train()
        train.route()
        train.sell_tickets()
        train.form_train()
        train.output()
        println("(Для выхода напишите 'EXIT', для продолжения любой символ)")
        when(readln().uppercase())
        {
            "EXIT" ->
            {
                println("Вокзал закрывается")
                exitProcess(0)
            }
        }
        println("---------------------------------------------")
    } while (true)
}
