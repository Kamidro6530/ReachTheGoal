package com.example.reachthegoal

import android.content.res.ColorStateList
import android.content.res.ColorStateList.valueOf
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.goalactivity.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goalactivity)

        val Name = intent.getStringExtra("Name")
        val Start = intent.getStringExtra("Start")
        val End = intent.getStringExtra("End")

        NameTV.text = Name
        StartTV.text = "Data rozpoczęcia wyzwania $Start"
        EndTV.text = "Data zakończenia wyzwania $End"


        //Podzielenie stringa do obliczania dni do końca wyzwania
        val splitStartDate = Start.split(" / ")
        val splitEndDate = End.split(" / ")
        var splitNowDate = LocalDate.now().toString().split("-")
        //Sprawdza czy obie daty mają wspólny miesiąc
        var m = months(splitStartDate, splitEndDate)
        var y = years(splitStartDate, splitEndDate)
        if (m == 0 && y == 0) {
            //Odejmuje do siebie dni aby obliczyć ile dni trwa wyzwanie
            daysBetweenTV.text = "Czas trwania : ${days(splitStartDate, splitEndDate)} dni"
        }
        if (m != 0 && y == 0) {
            daysBetweenTV.text = "Czas trwania : ${months(splitStartDate, splitEndDate)} miesięcy"
        }
        if (y != 0)
            when (y) {
                1 -> daysBetweenTV.text = "Czas trwania :${12 + months(
                    splitStartDate,
                    splitEndDate
                )} miesięcy oraz ${years(splitStartDate, splitEndDate)} rok "
                2, 3, 4 -> daysBetweenTV.text = "Czas trwania : ${12 + months(
                    splitStartDate,
                    splitEndDate
                )} miesięcy oraz ${years(splitStartDate, splitEndDate)} lata do"
                else -> daysBetweenTV.text = "Czas trwania : ${12 + months(
                    splitStartDate,
                    splitEndDate
                )} miesięcy oraz ${years(splitStartDate, splitEndDate)} lat do "
            }
        //***********************************Progres Bar *********************************************
        var dtf = SimpleDateFormat("dd/MM/yyyy")

        var currentdate = dtf.parse(
            splitNowDate[2] + "/" + splitNowDate[1] + "/" + splitNowDate[0].replace(
                "\\s".toRegex(),
                ""
            )
        )
        var enddate = dtf.parse(
            splitEndDate[0] + "/" + splitEndDate[1] + "/" + splitEndDate[2].replace(
                "\\s".toRegex(),
                ""
            )
        )
        var startdate = dtf.parse(
            splitStartDate[0] + "/" + splitStartDate[1] + "/" + splitStartDate[2].replace(
                "\\s".toRegex(),
                ""
            )
        )
        //Oblicza ile czasu zostało do końca wyzwania od obecnego dnia
        var daysBetween = enddate.time - currentdate.time
        //Długość całego wyzwania
        var durationInDays = enddate.time - startdate.time
        //Sprawdza czy wyzwanie już się rozpoczęło
        if (currentdate < startdate) {
            ProgressTV.text = "Wyzwanie się jeszcze nie rozpoczeło"
        } else {
            progressBar.max = 100
            progressBar.rotation = 180.toFloat()
            progressBar.progressTintList = valueOf(Color.GRAY)
            progressBar.progressBackgroundTintList = valueOf(Color.rgb(98, 0, 238))
            progressBar.progress =percentage(daysBetween.toDouble(), durationInDays.toDouble()).toInt()
            if (daysBetween > 0)
                ProgressTV.text =
                    "Dni do końca " + TimeUnit.DAYS.convert(daysBetween, TimeUnit.MILLISECONDS)
            else
                ProgressTV.text = "Wyzwanie ukończone ! "


        }
    }

    //Funkcja obliczająca ile procent(czasu)wyzwania już mineło
    fun percentage(first: Double, second: Double): Double {
        return first * 100 / second
    }

    //Funkcja obliczająca ile czasu  zostało do końca wyzwania
    private fun days(splitNowDate: List<String>, splitEndDate: List<String>): Int {
        return splitEndDate[0].replace("\\s".toRegex(), "")
            .toInt() - splitNowDate[0].replace("\\s".toRegex(), "").toInt()
    }

    fun months(splitNowDate: List<String>, splitEndDate: List<String>): Int {
        return splitEndDate[1].replace("\\s".toRegex(), "")
            .toInt() - splitNowDate[1].replace("\\s".toRegex(), "").toInt()
    }

    fun years(splitNowDate: List<String>, splitEndDate: List<String>): Int {
        return splitEndDate[2].replace("\\s".toRegex(), "")
            .toInt() - splitNowDate[2].replace("\\s".toRegex(), "").toInt()
    }
}

