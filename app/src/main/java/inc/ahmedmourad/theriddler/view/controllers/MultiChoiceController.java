package inc.ahmedmourad.theriddler.view.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.adapters.MultiChoiceRecyclerAdapter;
import inc.ahmedmourad.theriddler.adapters.base.StateRecyclerAdapter;
import inc.ahmedmourad.theriddler.adapters.pojo.MultiItem;
import inc.ahmedmourad.theriddler.model.classes.MultiChoiceQuestion;
import inc.ahmedmourad.theriddler.view.activity.MainActivity;
import inc.ahmedmourad.theriddler.view.controllers.bases.MultiController;

public class MultiChoiceController extends MultiController {

	private static final String ARG_QUESTION = "mc_question";
	private static final String ARG_QUESTION_INDEX = "mc_question_index";

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.multi_recycler_view)
	RecyclerView recyclerView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.multi_question_text_view)
	TextView questionTextView;

	private MultiChoiceQuestion question;

	private MultiChoiceRecyclerAdapter recyclerAdapter;
	private MainActivity activity;

	private Context context;

	private Unbinder unbinder;

	@NonNull
	public static MultiChoiceController newInstance(final int questionIndex, final MultiChoiceQuestion question) {

		Bundle args = new Bundle();

		args.putParcelable(ARG_QUESTION, Parcels.wrap(question));
		args.putInt(ARG_QUESTION_INDEX, questionIndex);

		return new MultiChoiceController(args);
	}

	@SuppressWarnings("WeakerAccess")
	public MultiChoiceController(@Nullable Bundle args) {
		super(args);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_multi, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		question = Parcels.unwrap(getArgs().getParcelable(ARG_QUESTION));

		if (question == null)
			question = MultiChoiceQuestion.create("N/A", "N/A");

		questionTextView.setText(context.getString(R.string.question_text,
				getArgs().getInt(ARG_QUESTION_INDEX, 0) + 1,
				question.getValue()
		));

		activity = (MainActivity) getActivity();

		if (activity != null)
			activity.setFabVisible(false);

		recyclerAdapter = new MultiChoiceRecyclerAdapter(createChoicesList(), () -> {
			if (activity != null)
				activity.setFabVisible(true);
		});

		initializeRecyclerView();

		return view;
	}

	private List<MultiItem> createChoicesList() {

		List<MultiItem> choices = new ArrayList<>(question.getWrongAnswers().length + 1);

		for (int i = 0; i < question.getWrongAnswers().length; ++i)
			choices.add(MultiItem.create(question.getWrongAnswers()[i]));

		choices.add(MultiItem.create(question.getAnswer()));

		Collections.shuffle(choices);

		return choices;
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}

	@NonNull
	@Override
	protected StateRecyclerAdapter getRecyclerAdapter() {
		return recyclerAdapter;
	}

	@Nullable
	@Override
	protected Context getContext() {
		return context;
	}

	@Nullable
	@Override
	protected RecyclerView getRecyclerView() {
		return recyclerView;
	}

	@Override
	public boolean isAnswerCorrect() {
		return recyclerAdapter.getAnswer().equals(question.getAnswer());
	}
}
