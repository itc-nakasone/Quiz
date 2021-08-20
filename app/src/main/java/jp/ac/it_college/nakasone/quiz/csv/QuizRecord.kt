package jp.ac.it_college.nakasone.quiz.csv

import com.opencsv.bean.CsvBindByName

data class QuizRecord(
    @CsvBindByName(column = "問題文")
    val question: String = "",

    @CsvBindByName(column = "画像ファイル")
    val imageFilename: String? = null,

    @CsvBindByName(column = "画像の著作権表記")
    val imageCopyright: String? = null,

    @CsvBindByName(column = "選択肢1")
    val choice1: String = "",

    @CsvBindByName(column = "選択肢2")
    val choice2: String = "",

    @CsvBindByName(column = "選択肢3")
    val choice3: String = "",

    @CsvBindByName(column = "選択肢4")
    val choice4: String = "",


    )