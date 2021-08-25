package jp.ac.it_college.nakasone.quiz.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.ac.it_college.nakasone.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}