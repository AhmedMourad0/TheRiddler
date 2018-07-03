package inc.ahmedmourad.theriddler.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.adapters.base.StateRecyclerAdapter;
import inc.ahmedmourad.theriddler.adapters.pojo.MultiItem;

public class MultiAnswerRecyclerAdapter extends StateRecyclerAdapter<MultiAnswerRecyclerAdapter.ViewHolder> {

	private static final String STATE_CHOICES_LIST = "ma_choices_list";

	private final List<MultiItem> choicesList;

	private final OnAnswerStateChanged onAnswerStateChanged;

	public MultiAnswerRecyclerAdapter(@NonNull List<MultiItem> choicesList, @NonNull OnAnswerStateChanged onAnswerStateChanged) {
		this.choicesList = choicesList;
		this.onAnswerStateChanged = onAnswerStateChanged;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup container, final int viewType) {
		return new ViewHolder(LayoutInflater.from(container.getContext()).inflate(R.layout.item_multi_answer, container, false));
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.bind(choicesList.get(position));
	}

	@Override
	public int getItemCount() {
		return choicesList.size();
	}

	public String[] getAnswer() {

		final List<String> chosenAnswers = new ArrayList<>();

		for (int i = 0; i < choicesList.size(); ++i)
			if (choicesList.get(i).isSelected())
				chosenAnswers.add(choicesList.get(i).getChoice());

		return chosenAnswers.toArray(new String[chosenAnswers.size()]);
	}

	@Override
	public void saveState(@NonNull final Bundle outState) {

		final Parcelable[] parcelables = new Parcelable[choicesList.size()];

		for (int i = 0; i < choicesList.size(); ++i)
			parcelables[i] = Parcels.wrap(choicesList.get(i));

		outState.putParcelableArray(STATE_CHOICES_LIST, parcelables);
	}

	@Override
	public void restoreState(@NonNull final Bundle savedInstanceState) {

		choicesList.clear();

		Parcelable[] parcelables = savedInstanceState.getParcelableArray(STATE_CHOICES_LIST);

		if (parcelables != null) {

			for (Parcelable parcelable : parcelables)
				choicesList.add(Parcels.unwrap(parcelable));

			notifyDataSetChanged();
		}

		handleSelectionState();
	}

	private void handleSelectionState() {

		boolean isAnswered = false;

		for (int i = 0; i < choicesList.size(); ++i) {
			if (choicesList.get(i).isSelected()) {
				isAnswered = true;
				break;
			}
		}

		onAnswerStateChanged.onStateChanged(isAnswered);
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.item_multi_answer_check_box)
		CheckBox choiceCheckBox;

		ViewHolder(final View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

		private void bind(final MultiItem item) {

			choiceCheckBox.setText(item.getChoice());
			choiceCheckBox.setChecked(item.isSelected());

			itemView.setOnClickListener(v -> {
				item.setSelected(!item.isSelected());
				handleSelectionState();
			});
		}
	}

	@FunctionalInterface
	public interface OnAnswerStateChanged {
		void onStateChanged(final boolean isAnswered);
	}
}
