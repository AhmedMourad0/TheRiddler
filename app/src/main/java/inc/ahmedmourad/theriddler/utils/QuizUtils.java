package inc.ahmedmourad.theriddler.utils;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.model.classes.MultiAnswerQuestion;
import inc.ahmedmourad.theriddler.model.classes.MultiChoiceQuestion;
import inc.ahmedmourad.theriddler.model.classes.TextQuestion;
import inc.ahmedmourad.theriddler.model.interfaces.Question;

public final class QuizUtils {

	private static final List<Question> questions = new ArrayList<>(10);
	public static final int QUESTIONS_COUNT = 10;

	public static void prepare(@NonNull final Context context) {

		if (questions.size() < QUESTIONS_COUNT) {

			questions.clear();

			questions.add(MultiChoiceQuestion.create(getString(context, R.string.question_1),
					getString(context, R.string.question_1_answer),
					getStringArray(context, R.array.question_1_wrong_answers)
			));

			questions.add(MultiChoiceQuestion.create(getString(context, R.string.question_2),
					getString(context, R.string.question_2_answer),
					getStringArray(context, R.array.question_2_wrong_answers)
			));

			questions.add(MultiAnswerQuestion.create(getString(context, R.string.question_3),
					getStringArray(context, R.array.question_3_answer),
					getStringArray(context, R.array.question_3_wrong_answers)
			));

			questions.add(TextQuestion.create(getString(context, R.string.question_4),
					getString(context, R.string.question_4_answer)
			));

			questions.add(MultiChoiceQuestion.create(getString(context, R.string.question_5),
					getString(context, R.string.question_5_answer),
					getStringArray(context, R.array.question_5_wrong_answers)
			));

			questions.add(TextQuestion.create(getString(context, R.string.question_6),
					getString(context, R.string.question_6_answer)
			));

			questions.add(TextQuestion.create(getString(context, R.string.question_7),
					getString(context, R.string.question_7_answer)
			));

			questions.add(MultiChoiceQuestion.create(getString(context, R.string.question_8),
					getString(context, R.string.question_8_answer),
					getStringArray(context, R.array.question_8_wrong_answers)
			));

			questions.add(MultiChoiceQuestion.create(getString(context, R.string.question_9),
					getString(context, R.string.question_9_answer),
					getStringArray(context, R.array.question_9_wrong_answers)
			));

			questions.add(MultiAnswerQuestion.create(getString(context, R.string.question_10),
					getStringArray(context, R.array.question_10_answer),
					getStringArray(context, R.array.question_10_wrong_answers)
			));
		}

		Collections.shuffle(questions);
	}

	@NonNull
	private static String getString(final Context context, @StringRes final int stringId) {
		return context.getString(stringId);
	}

	@NonNull
	private static String[] getStringArray(final Context context, @ArrayRes final int arrayId) {
		return context.getResources().getStringArray(arrayId);
	}

	public static Question getQuestion(@IntRange(from = 0, to = QUESTIONS_COUNT - 1) final int index) {
		return questions.get(index);
	}
}
