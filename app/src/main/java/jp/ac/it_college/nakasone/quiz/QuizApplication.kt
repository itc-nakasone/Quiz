package jp.ac.it_college.nakasone.quiz

import android.app.Application
import android.util.Log
import com.opencsv.bean.CsvToBeanBuilder
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import jp.ac.it_college.nakasone.quiz.csv.QuizRecord
import jp.ac.it_college.nakasone.quiz.realm.model.Quiz
import java.io.InputStreamReader

private val quizFiles = listOf<String>(
    "nakasone.csv"
)

class QuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)

        val quizList = loadData()

        val db = Realm.getDefaultInstance()
        val count = db.where<Quiz>().count()
        if (count != quizList.size.toLong()) {
            Log.d("QuizApplication","registration quiz data")
            db.executeTransaction { transaction ->
                transaction.where<Quiz>().findAll().deleteAllFromRealm()
                for (i in quizList.indices) {
                    val quiz = transaction.createObject<Quiz>(i + 1)
                    quiz.apply {
                        question = quizList[i].question
                        imageFilename = quizList[i].imageFilename
                        imageCopyright = quizList[i].imageCopyright
                        choices = RealmList<String>(
                            quizList[i].choice1,
                            quizList[i].choice2,
                            quizList[i].choice3,
                            quizList[i].choice4,
                        )
                    }
                }
            }
        }
    }

    private fun loadData(): List<QuizRecord> {
        val quizList = mutableListOf<QuizRecord>()
        for (file in quizFiles) {
            val records = openAndLoadCsv(file)
            quizList.addAll(records)
        }
        return quizList
    }

    private fun openAndLoadCsv(filename: String): List<QuizRecord> {
        val reader = InputStreamReader(resources.assets.open(filename))

        return CsvToBeanBuilder<QuizRecord>(reader)
            .withType(QuizRecord::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse()
    }
}