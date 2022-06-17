package com.example.cards.card

import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cards.R
import java.lang.Double.max
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name = "card_question")
    var question: String,
    var answer: String,
    var creationDate: String = LocalDateTime.now().toString(),

    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var deckId: String = ""
) {
    var quality: Int = -1
    var repetitions: Int = 0
    var interval: Long = 1L
    var easiness: Double = 2.5
    var nextPracticeDate: String = LocalDateTime.now().toString()
    var answered: Boolean = false

    constructor() : this(
        "question",
        "answer",
        LocalDateTime.now().toString(),
        UUID.randomUUID().toString(),
        ""
    )

    override fun toString(): String = run {
        return ("card | $question | $answer | $creationDate | $easiness | $interval | $repetitions | $nextPracticeDate")
    }

    open fun show() {
        print("  $question \n  (press any key to see the answer)")
        readLine()

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