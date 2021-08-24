package jp.ac.it_college.nakasone.quiz.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jp.ac.it_college.nakasone.quiz.databinding.FragmentQuizBinding
import jp.ac.it_college.nakasone.quiz.realm.model.Quiz
import jp.ac.it_college.nakasone.quiz.realm.model.randomChooseQuiz

const val MAX_COUNT = 10

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var current: Int = -1
    private var timer = TimeLeftCountdown()

    private lateinit var quizList: List<Quiz>

    private val onChoiceClick = View.OnClickListener { v ->
        setBulkEnabled(false)
        timer.cancel()
        if (v is Button && v.text == quizList[current].choices[0]) {
            // 正解の表示
            next()
        } else {
            // 間違いの表示
            next()
        }
    }

    inner class TimeLeftCountdown : CountDownTimer(10000, 50) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timeLeftBar.progress = millisUntilFinished.toInt() / 10
        }

        override fun onFinish() {
            setBulkEnabled(false)
            binding.timeLeftBar.progress = 0
            // タイムアップの表示
            next()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        quizList = randomChooseQuiz(10)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.choiceA.setOnClickListener(onChoiceClick)
        binding.choiceB.setOnClickListener(onChoiceClick)
        binding.choiceC.setOnClickListener(onChoiceClick)
        binding.choiceD.setOnClickListener(onChoiceClick)

        next()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun next() {
        if (++current < MAX_COUNT) {
            timer.cancel()
            setQuiz(current)
            timer.start()
            setBulkEnabled(true)
        }
    }

    private fun setBulkEnabled(flag: Boolean) {
        binding.choiceA.isEnabled = flag
        binding.choiceB.isEnabled = flag
        binding.choiceC.isEnabled = flag
        binding.choiceD.isEnabled = flag
    }

    private fun setQuiz(position: Int) {
        binding.quizText.text = quizList[position].question
        if (quizList[position].imageFilename.isNullOrBlank()) {
            binding.quizImage.visibility = View.GONE
        } else {
            val resId = resources.getIdentifier(
                quizList[position].imageFilename, "drawable",
                context?.packageName
            )
            binding.quizImage.setImageResource(resId)
            binding.quizImage.visibility = View.VISIBLE
        }

        val randomChoice = quizList[position].choices.shuffled()
        binding.choiceA.text = randomChoice[0]
        binding.choiceB.text = randomChoice[1]
        binding.choiceC.text = randomChoice[2]
        binding.choiceD.text = randomChoice[3]
    }

    private fun toResult() {

    }
}