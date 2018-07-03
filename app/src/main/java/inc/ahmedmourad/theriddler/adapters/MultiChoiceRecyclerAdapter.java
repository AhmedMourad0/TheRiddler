package inc.ahmedmourad.theriddler.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.ahmedmourad.theriddler.R;
import inc.ahmedmourad.theriddler.adapters.base.StateRecyclerAdapter;
import inc.ahmedmourad.theriddler.adapters.pojo.MultiItem;

public class MultiChoiceRecyclerAdapter extends StateRecyclerAdapter<MultiChoiceRecyclerAdapter.ViewHolder> {

	private static final String STATE_SELECTED_CHOICE_POSITION = "mc_adapter_scp";
	private static final String STATE_CHOICES_LIST = "mc_choices_list";

	private final List<MultiItem> choicesList;

	private final Runnable onItemClickListener;

	private int selectedChoicePosition = -1;

	public MultiChoiceRecyclerAdapter(@NonNull List<MultiItem> choicesList, @NonNull Runnable onItemClickListener) {
		this.choicesList = choicesList;
		this.onItemClickListener = onItemClickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup container, final int viewType) {
		return new ViewHolder(LayoutInflater.from(container.getContext()).inflate(R.layout.item_multi_choice, container, false));
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.bind(position, choicesList.get(position));
	}

	@Override
	public int getItemCount() {
		return choicesList.size();
	}

	private boolean select(final int position) {

		if (position == -1 || position >= getItemCount())
			return false;

		if (position == selectedChoicePosition) {
			notifyItemChanged(position);
			return true;
		}

		choicesList.get(position).setSelected(true);
		notifyItemChanged(position);

		if (selectedChoicePosition != -1 && selectedChoicePosition < getItemCount()) {
			choicesList.get(selectedChoicePosition).setSelected(false);
			notifyItemChanged(selectedChoicePosition);
		}

		selectedChoicePosition = position;

		return true;
	}

	public String getAnswer() {
		return choicesList.get(selectedChoicePosition).getChoice();
	}

	@Override
	public void saveState(@NonNull final Bundle outState) {

		outState.putInt(STATE_SELECTED_CHOICE_POSITION, selectedChoicePosition);

		final Parcelable[] parcelables = new Parcelable[choicesList.size()];

		for (int i = 0; i < choicesList.size(); ++i)
			parcelables[i] = Parcels.wrap(choicesList.get(i));

		outState.putParcelableArray(STATE_CHOICES_LIST, parcelables);
	}

	@Override
	public void restoreState(@NonNull final Bundle savedInstanceState) {

		selectedChoicePosition = savedInstanceState.getInt(STATE_SELECTED_CHOICE_POSITION, -1);

		if (selectedChoicePosition != -1)
			onItemClickListener.run();

		choicesList.clear();

		Parcelable[] parcelables = savedInstanceState.getParcelableArray(STATE_CHOICES_LIST);

		if (parcelables != null) {

			for (Parcelable parcelable : parcelables)
				choicesList.add(Parcels.unwrap(parcelable));

			notifyDataSetChanged();
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.item_multi_choice_radio_button)
		RadioButton choiceRadioButton;

		ViewHolder(final View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

		private void bind(final int position, final MultiItem item) {

			choiceRadioButton.setText(item.getChoice());
			choiceRadioButton.setChecked(item.isSelected());

			itemView.setOnClickListener(v -> {
				if (select(position))
					onItemClickListener.run();
			});
		}
	}
}
