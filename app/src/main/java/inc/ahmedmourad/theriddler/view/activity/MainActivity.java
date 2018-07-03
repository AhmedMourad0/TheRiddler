package inc.ahmedmourad.theriddler.view.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.model.classes.MultiAnswerQuestion;
import inc.ahmedmourad.theriddler.model.classes.MultiChoiceQuestion;
import inc.ahmedmourad.theriddler.model.classes.TextQuestion;
import inc.ahmedmourad.theriddler.model.interfaces.Question;
import inc.ahmedmourad.theriddler.utils.QuizUtils;
import inc.ahmedmourad.theriddler.view.controllers.MainController;
import inc.ahmedmourad.theriddler.view.controllers.MultiAnswerController;
import inc.ahmedmourad.theriddler.view.controllers.MultiChoiceController;
import inc.ahmedmourad.theriddler.view.controllers.TextController;
import inc.ahmedmourad.theriddler.view.controllers.bases.QuestionController;

public class MainActivity extends AppCompatActivity {

	private static final String STATE_QUESTION_INDEX = "main_qi";
	private static final String STATE_RESULT = "main_r";

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_container)
	ChangeHandlerFrameLayout container;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_fab)
	FloatingActionButton fab;

	private int questionIndex = 0;
	private int result = 0;

	private boolean quitQuiz = false;

	private QuestionController currentQuestionController;

	private Router router;

	private Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		unbinder = ButterKnife.bind(this);

		router = Conductor.attachRouter(this, container, savedInstanceState);

		if (!router.hasRootController())
			router.setRoot(RouterTransaction.with(MainController.newInstance()));
		else if (router.getBackstackSize() > 1)
			currentQuestionController = (QuestionController) router.getBackstack().get(router.getBackstackSize() - 1).controller();

		fab.setOnClickListener(v -> {

			if (currentQuestionController.isAnswerCorrect())
				++result;

			nextQuestion();
		});
	}

	public void setFabVisible(final boolean visible) {
		if (visible)
			fab.show();
		else
			fab.hide();
	}

	public void startQuiz() {
		QuizUtils.prepare(this);
		questionIndex = 0;
		result = 0;
		fab.setImageResource(R.drawable.ic_next);
		router.popToRoot();
		router.pushController(RouterTransaction.with(createQuestionController(questionIndex)));
		setFabVisible(false);
	}

	private void nextQuestion() {

		++questionIndex;

		setFabVisible(false);

		if (questionIndex > QuizUtils.QUESTIONS_COUNT - 1) {
			router.popToRoot();
			Toast.makeText(this, getString(R.string.result_message, new BigDecimal(result * 100.0 / QuizUtils.QUESTIONS_COUNT).toPlainString()), Toast.LENGTH_LONG).show();
		} else {
			router.replaceTopController(RouterTransaction.with(createQuestionController(questionIndex)));
		}

		if (questionIndex == QuizUtils.QUESTIONS_COUNT - 1)
			fab.setImageResource(R.drawable.ic_done);
	}

	private QuestionController createQuestionController(final int index) {

		final Question question = QuizUtils.getQuestion(index);

		switch (question.getType()) {

			case Question.TYPE_TEXT:
				return currentQuestionController = TextController.newInstance(questionIndex, (TextQuestion) question);

			case Question.TYPE_MULTI_CHOICE:
				return currentQuestionController = MultiChoiceController.newInstance(questionIndex, (MultiChoiceQuestion) question);

			case Question.TYPE_MULTI_ANSWER:
				return currentQuestionController = MultiAnswerController.newInstance(questionIndex, (MultiAnswerQuestion) question);

			default:
				throw new IllegalStateException("Question type is not supported!");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_QUESTION_INDEX, questionIndex);
		outState.putInt(STATE_RESULT, result);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		questionIndex = savedInstanceState.getInt(STATE_QUESTION_INDEX, 0);
		result = savedInstanceState.getInt(STATE_RESULT, 0);

		if (questionIndex == QuizUtils.QUESTIONS_COUNT - 1)
			fab.setImageResource(R.drawable.ic_done);
		else
			fab.setImageResource(R.drawable.ic_next);
	}

	@Override
	public void onBackPressed() {

		if (router.getBackstackSize() <= 1) {

			super.onBackPressed();

		} else {
			if (quitQuiz) {
				quitQuiz = false;
				router.popToRoot();
				setFabVisible(false);
			} else {
				quitQuiz = true;
				Toast.makeText(this, R.string.quit_quiz_prompt, Toast.LENGTH_LONG).show();
				new Handler().postDelayed(() -> quitQuiz = false, 2 * 1000);
			}
		}
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
