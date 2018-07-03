package inc.ahmedmourad.theriddler.view.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.model.classes.TextQuestion;
import inc.ahmedmourad.theriddler.view.activity.MainActivity;
import inc.ahmedmourad.theriddler.view.controllers.bases.QuestionController;

public class TextController extends QuestionController {

	private static final String ARG_QUESTION = "t_question";
	private static final String ARG_QUESTION_INDEX = "t_question_index";

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.text_question_text_view)
	TextView questionTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.text_answer_edit_text)
	EditText answerEditText;

	private TextQuestion question;

	private MainActivity activity;

	private Unbinder unbinder;

	@NonNull
	public static TextController newInstance(final int questionIndex, final TextQuestion question) {

		Bundle args = new Bundle();

		args.putParcelable(ARG_QUESTION, Parcels.wrap(question));
		args.putInt(ARG_QUESTION_INDEX, questionIndex);

		return new TextController(args);
	}

	@SuppressWarnings("WeakerAccess")
	public TextController(@Nullable Bundle args) {
		super(args);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_text, container, false);

		unbinder = ButterKnife.bind(this, view);

		question = Parcels.unwrap(getArgs().getParcelable(ARG_QUESTION));

		if (question == null)
			question = TextQuestion.create("N/A", "N/A");

		questionTextView.setText(view.getContext().getString(R.string.question_text,
				getArgs().getInt(ARG_QUESTION_INDEX, 0) + 1,
				question.getValue()
		));

		activity = (MainActivity) getActivity();

		if (activity != null)
			activity.setFabVisible(false);

		answerEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (activity != null)
					activity.setFabVisible(s.length() > 0);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return view;
	}

	@Override
	protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
		super.onSaveViewState(view, outState);
		outState.putCharSequence(ARG_QUESTION, answerEditText.getText());

	}

	@Override
	protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
		super.onRestoreViewState(view, savedViewState);
		answerEditText.setText(savedViewState.getCharSequence(ARG_QUESTION, ""));
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}

	@Override
	public boolean isAnswerCorrect() {
		return answerEditText.getText().toString().trim().equals(question.getAnswer());
	}
}
