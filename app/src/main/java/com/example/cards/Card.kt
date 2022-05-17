package com.example.cards

import android.view.View
import java.lang.Double.max
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


open class Card(
    var question: String,
    var answer: String,
    var quality: Int = -1,

    var repetitions: Int = 0,
    var interval: Long = 1L,
    var easiness: Double = 2.5,

    var creationDate: String = LocalDateTime.now().toString(),
    var nextPracticeDate: String = LocalDateTime.now().toString(),
    var id: String = UUID.randomUUID().toString(),

    var answered: Boolean = false
) {
    override fun toString(): String = run {
        return ("card | $question | $answer | $creationDate | $easiness | $interval | $repetitions | $nextPracticeDate")
    }

    open fun show() {
        print("  $question \n  (press any key to see the answer)")
        var entry = readLine()

        print("  $answer\n  0 -> Difficult 3 -> Doubt 5 -> Easy :")
        var difficulty = readLine()?.toIntOrNull() ?: -1

        while (difficulty != 0 && difficulty != 3 && difficulty != 5) {
            print(
                "  Incorrect input, please try again.\n" +
                        "  0 -> Difficult 3 -> Doubt 5 -> Easy :"
            )
            difficulty = readLine()?.toIntOrNull() ?: -1
        }
        quality = difficulty
    }

    fun update(currentDate: LocalDateTime) {
        easiness = max(1.3, easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))
        repetitions =
            if (quality < 3) 0
            else repetitions + 1
        interval =
            if (repetitions <= 1) 1
            else if (repetitions == 2) 6
            else (interval * easiness).toLong()

        val date = currentDate.plusDays(interval)
        nextPracticeDate = date.toString()
    }

    fun details() {
        println(
            "  easiness : ${"%.2f".format(easiness)}\trepetitions : $repetitions" +
                    "\tinterval : $interval\tdate : $nextPracticeDate\n"
        )
    }

    fun simulate(period: Long) {
        var now = LocalDateTime.now()

        val inputFormat = DateTimeFormatter.ISO_DATE_TIME
        val outputFormat = DateTimeFormatter.ISO_LOCAL_DATE

        for (i in 1..period) {

            println(now.format(outputFormat))

            val nextPD = (LocalDateTime.parse(nextPracticeDate, inputFormat)).format(outputFormat)

            if (nextPD.compareTo(now.format(outputFormat)) == 0) {
                show()
                update(now)
                details()
            }
            now = now.plusDays(1)
        }
    }

    fun update_from_view(view: View) {
        quality = when(view.id) {
            R.id.easy_button -> 5
            R.id.doubt_button -> 3
            R.id.hard_button -> 0
            else -> throw Exception("Unavailable quality")
        }
        update(LocalDateTime.now())
    }

    fun update_card(quality: Int) {
        this.quality = quality
        update(LocalDateTime.now())
    }

    fun isDue(date: LocalDateTime): Boolean {
        return date.isAfter(LocalDateTime.parse(nextPracticeDate))
    }

}

/*

fun fromString(cad: String): Card {
    var card: Card
    var chunks = cad.split(" | ")
    chunks.forEach {
        it.apply {
            trim()
            trimEnd()
        }
    }
    if (chunks[0] == "cloze") card = Cloze(chunks[1], chunks[2])
    else if (chunks[0] == "card") card = Card(chunks[1], chunks[2])
    else {
        throw(Exception("Incorrect type for card in file : '${chunks[0]}'"))
    }
    card.apply {
        creationDate = chunks[3]
        easiness = chunks[4].toDouble()
        interval = chunks[5].toLong()
        repetitions = chunks[6].toInt()
        nextPracticeDate = chunks[7]
    }
    return card
}

*/