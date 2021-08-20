package jp.ac.it_college.nakasone.quiz.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import jp.ac.it_college.nakasone.quiz.databinding.ActivityMainBinding
import jp.ac.it_college.nakasone.quiz.realm.model.Quiz

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Realm.getDefaultInstance()

        val quizList = db.where<Quiz>().findAll()
        for (quiz in quizList) {
            Log.d("Quiz Data", quiz.toString())
        }
    }
}