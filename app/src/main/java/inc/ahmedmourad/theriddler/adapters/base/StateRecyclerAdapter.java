package inc.ahmedmourad.theriddler.adapters.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class StateRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	public abstract void saveState(@NonNull final Bundle outState);

	public abstract void restoreState(@NonNull final Bundle savedInstanceState);
}
