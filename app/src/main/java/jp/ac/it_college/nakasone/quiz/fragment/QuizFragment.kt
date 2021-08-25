package jp.ac.it_college.nakasone.quiz.fragment

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.ac.it_college.nakasone.quiz.databinding.FragmentQuizBinding
import jp.ac.it_college.nakasone.quiz.realm.model.Quiz
import jp.ac.it_college.nakasone.quiz.realm.model.randomChooseQuiz

const val MAX_COUNT = 10

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var current = -1
    private var timeLeftCountdown = TimeLeftCountdown()
    private var startTime = 0L
    private var totalElapsedTime = 0L
    private var correctCount = 0
    private val currentElapsedTime: Long get() = SystemClock.elapsedRealtime() - startTime

    private lateinit var quizList: List<Quiz>

    private val onChoiceClick = View.OnClickListener { v ->
        isBulkEnableButton(false)
        timeLeftCountdown.cancel()
        totalElapsedTime += currentElapsedTime
        if (v is Button && v.text == quizList[current].choices[0]) {
            binding.goodIcon.visibility = View.VISIBLE
            correctCount++
            delayNext(3000L)
        } else {
            binding.badIcon.visibility = View.VISIBLE
            delayNext(3000L)
        }
    }

    inner class TimeLeftCountdown : CountDownTimer(10000, 50) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timeLeftBar.progress = millisUntilFinished.toInt() / 10
        }

        override fun onFinish() {
            totalElapsedTime += currentElapsedTime
            isBulkEnableButton(false)
            binding.timeLeftBar.progress = 0
            binding.timeupIcon.visibility = View.VISIBLE
            // タイムアップの表示
            delayNext(2000L)
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
        timeLeftCountdown.cancel()
        _binding = null
    }

    private fun next() {
        if (++current < MAX_COUNT) {
            timeLeftCountdown.cancel()
            setQuiz(current)
            timeLeftCountdown.start()
            isBulkEnableButton(true)
            binding.badIcon.visibility = View.GONE
            binding.goodIcon.visibility = View.GONE
            binding.timeupIcon.visibility = View.GONE
            startTime = SystemClock.elapsedRealtime()
        } else {
            toResult()
        }
    }

    private fun delayNext(delay: Long) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(delay) {
            next()
        }
    }

    private fun isBulkEnableButton(flag: Boolean) {
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
        val action = QuizFragmentDirections.actionToResult(correctCount, totalElapsedTime)
        findNavController().navigate(action)
    }
}