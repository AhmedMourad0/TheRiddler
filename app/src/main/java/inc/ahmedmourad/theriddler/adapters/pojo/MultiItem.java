package inc.ahmedmourad.theriddler.adapters.pojo;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

// Running out of names here
@Parcel(Parcel.Serialization.BEAN)
public class MultiItem {

	private String choice;
	private boolean isSelected;

	@NonNull
	public static MultiItem create(final String choice) {
		final MultiItem multiItem = new MultiItem();
		multiItem.setChoice(choice);
		multiItem.setSelected(false);
		return multiItem;
	}

	public String getChoice() {
		return choice;
	}

	@SuppressWarnings("WeakerAccess")
	public void setChoice(String choice) {
		this.choice = choice;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
