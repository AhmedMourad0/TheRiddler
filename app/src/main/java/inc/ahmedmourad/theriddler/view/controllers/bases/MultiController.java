package inc.ahmedmourad.theriddler.view.controllers.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import inc.ahmedmourad.theriddler.adapters.base.StateRecyclerAdapter;

public abstract class MultiController extends QuestionController {

	protected MultiController(@Nullable Bundle args) {
		super(args);
	}

	@NonNull
	protected abstract StateRecyclerAdapter getRecyclerAdapter();

	@Nullable
	protected abstract Context getContext();

	@Nullable
	protected abstract RecyclerView getRecyclerView();

	protected void initializeRecyclerView() {
		if (getRecyclerView() != null && getContext() != null) {
			getRecyclerView().setAdapter(getRecyclerAdapter());
			getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
			getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
			getRecyclerView().setVerticalScrollBarEnabled(true);
			getRecyclerView().setHasFixedSize(true);
		}
	}

	@Override
	protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
		super.onSaveViewState(view, outState);
		getRecyclerAdapter().saveState(outState);
	}

	@Override
	protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
		super.onRestoreViewState(view, savedViewState);
		getRecyclerAdapter().restoreState(savedViewState);
	}
}
