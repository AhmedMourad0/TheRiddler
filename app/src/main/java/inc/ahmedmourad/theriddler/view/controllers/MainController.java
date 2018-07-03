package inc.ahmedmourad.theriddler.view.controllers;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.view.activity.MainActivity;

public class MainController extends Controller {

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_start_fab)
	FloatingActionButton startButton;

	private Unbinder unbinder;

	@NonNull
	public static MainController newInstance() {
		return new MainController();
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_main, container, false);

		unbinder = ButterKnife.bind(this, view);

		startButton.setOnClickListener(v -> {
			if (getActivity() != null)
				((MainActivity) getActivity()).startQuiz();
		});

		return view;
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
