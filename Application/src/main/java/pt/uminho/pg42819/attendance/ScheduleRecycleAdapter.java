package pt.uminho.pg42819.attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pt.uminho.pg42819.attendance.data.ScheduleManager;
import pt.uminho.pg42819.attendance.model.ScheduleItem;

public class ScheduleRecycleAdapter extends RecyclerView.Adapter<ScheduleRecycleAdapter.ScheduleItemViewHolder>
{
	private ScheduleManager _scheduleManager;

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder).
	 */
	public static class ScheduleItemViewHolder extends RecyclerView.ViewHolder
	{
		private final TextView _codeView;
		private final TextView _titleView;
		private final TextView _timeView;
		private final TextView _alertView;

		public ScheduleItemViewHolder(View view)
		{
			super(view);
			// Define click listener for the ViewHolder's View
			_codeView = view.findViewById(R.id.scheduleCodeView);
			_titleView = view.findViewById(R.id.scheduleTitle);
			_timeView = view.findViewById(R.id.scheduleTime);
			_alertView = view.findViewById(R.id.scheduleAlert);
		}

		public TextView getCodeView()
		{
			return _codeView;
		}

		public TextView getTitleView()
		{
			return _titleView;
		}

		public TextView getTimeView()
		{
			return _timeView;
		}

		public TextView getAlertView()
		{
			return _alertView;
		}
	}

	/**
	 * Initialize the dataset of the Adapter.
	 *
	 * @param scheduleManager holding schedule items to populate the view
	 * by RecyclerView.
	 */
	public ScheduleRecycleAdapter(ScheduleManager scheduleManager)
	{
		_scheduleManager = scheduleManager;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public ScheduleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		// Create a new view, which defines the UI of the list item
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.lesson_row, viewGroup, false);

		return new ScheduleItemViewHolder(view);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ScheduleItemViewHolder viewHolder, final int position) {

		// Needs to be fast
		_scheduleManager.updateAlertsIfNeeded();

		// Get element from your dataset at this position and replace the
		// contents of the view with the scehdule info
		final ScheduleItem scheduleItem = _scheduleManager.getUserSchedule().get(position);
		viewHolder.getCodeView().setText(scheduleItem.getCode());
		viewHolder.getTitleView().setText(scheduleItem.getLabel());
		viewHolder.getTimeView().setText(scheduleItem.getTimeSlot());
		// update the alert in realtime
		final TextView alertView = viewHolder.getAlertView();

		int alertString = scheduleItem.getAlert();
		if (alertString == -1) {
			// no alert so hide it
			alertView.setText("cannot see this");
			alertView.setVisibility(View.INVISIBLE);
		}
		else {
			alertView.setText(alertString);
		}
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return _scheduleManager.getItemCount();
	}
}
