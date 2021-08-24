package jp.ac.it_college.nakasone.quiz.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jp.ac.it_college.nakasone.quiz.databinding.FragmentQuizBinding
import jp.ac.it_college.nakasone.quiz.realm.model.Quiz
import jp.ac.it_college.nakasone.quiz.realm.model.randomChooseQuiz

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var current: Int = 0

    lateinit var quizList: List<Quiz>

    val onChoiceClick = View.OnClickListener { v ->
        if (v is Button && v.text == quizList[current].choices[0]) {
            Snackbar.make(v, "正解の処理", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(v, "まちがいの処理", Snackbar.LENGTH_SHORT).show()
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

        setQuiz(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setQuiz(position: Int) {
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

}