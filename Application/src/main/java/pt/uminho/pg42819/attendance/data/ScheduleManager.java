package pt.uminho.pg42819.attendance.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.uminho.pg42819.attendance.model.Course;
import pt.uminho.pg42819.attendance.model.Lesson;
import pt.uminho.pg42819.attendance.model.ScheduleItem;

public class ScheduleManager implements SharedPreferences.OnSharedPreferenceChangeListener
{
	private static final String LOGTAG = ScheduleManager.class.getSimpleName();
//	private final static LocalDateTime NEVER = LocalDateTime.of(1970, 1, 1, 0, 0);

	public static final long ALERT_FRESHNESS_MILLISECONDS = 2 * 60 * 1000; // 2 mins
	private long _lastUpdate = 0;
	private Context _context;
	private CourseLoader _courseLoader;
	List<ScheduleItem> _userSchedule = null;
	private SharedPreferences _sharedPreferences;

	public ScheduleManager(Context context, CourseLoader courseLoader)
	{
		_context = context;
		_courseLoader = courseLoader;
		_sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		_sharedPreferences = sharedPreferences;
		_userSchedule = null;
	}

	public List<ScheduleItem> getUserSchedule()
	{
		if (_userSchedule == null) {
			if (_sharedPreferences == null) {
				// never initialized
				return ScheduleItem.getEmptyShedule(_context);
			}

			List<ScheduleItem> unsortedSchedule = new ArrayList<>();
			final Course[] courses = _courseLoader.getCourses();
			for (Course course : courses) {
				if (_sharedPreferences.getBoolean(course.getCode(), false)) {
					// user has this course, so add the items to their schedule
					for (Lesson lesson : course.getLessons()) {
						unsortedSchedule.add(new ScheduleItem(_context, course, lesson));
					}
				}
			}
			Collections.sort(unsortedSchedule);
			_userSchedule = unsortedSchedule;
		}
		return _userSchedule;
	}

	public int getItemCount()
	{
		return getUserSchedule().size();
	}

	public void reset()
	{
		_userSchedule = null;
		_lastUpdate = 0;
	}

	public void updateAlertsIfNeeded()
	{
		final long now = System.currentTimeMillis();
		// if a couple of minuts have passed update
		if (now - _lastUpdate > ALERT_FRESHNESS_MILLISECONDS) {
			Log.d(LOGTAG, "Updating alerts");
			_lastUpdate = now;
			updateAlerts();
		}
	}

	public void updateAlerts()
	{
		final List<ScheduleItem> userSchedule = getUserSchedule();
		for (ScheduleItem scheduleItem : userSchedule) {
			scheduleItem.updateAlert();
		}
	}
}
