package pt.uminho.pg42819.attendance.model;

import android.content.Context;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import pt.uminho.pg42819.attendance.R;

/**
 * Represents an actual lesson in the week which might or might not be
 * assigned to the current user.
 *
 * ScheduleItems are Comparable so that they can be sorted by start time
 * and day, and can be decorated with "alert" information based on the current
 * time, indicating for instance, if the item is about to start, is
 * scheduled for tomorrow, etc.
 */
public class ScheduleItem implements Comparable<ScheduleItem>
{
	public static final DateTimeFormatter _PORTUGUESE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH'h'mm");
	public static final DateTimeFormatter _ENGLISH_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	private static List<ScheduleItem> _emptySchedule = null;

	private Course _course;
	private Lesson _lesson;
	private DayOfWeek _day;
	private LocalTime _start;
	private LocalTime _end;
	private String _name;
	DateTimeFormatter _timeFormat;
	Locale _locale;
	private Context _context;
	private int _alert;

	public ScheduleItem(Context context, Course course, Lesson lesson)
	{
		this(context);
		_alert = -1;
		_course = course;
		_lesson = lesson;
		_day = lesson.getDay();
		_start = LocalTime.parse(_lesson.start);
		_end = LocalTime.parse(_lesson.end);

		// TODO make this system dependent but only when I i18n all the strings
		_locale = Locale.UK;
		_timeFormat = _ENGLISH_TIME_FORMAT;
	}

	private ScheduleItem(Context context)
	{
		_context = context;
	}

	protected Context getContext()
	{
		return _context;
	}

	public String getCode()
	{
		return _course.getCode();
	}

	public String getLabel()
	{
		return _lesson.getName();
	}

	public String getTimeSlot()
	{
		String dayAbbr = _day.getDisplayName(TextStyle.SHORT, _locale);
		String dayAndTime = String.format("%s %s-%s", dayAbbr,
			_start.format(_timeFormat), _end.format(_timeFormat));
		return dayAndTime;
	}

	public DayOfWeek getDay()
	{
		return _day;
	}

	/**
	 * Returns a string resource identifier for an alert message, indicating if the scheduled
	 * item is Now, Soon, Tomorrow, etc.
	 *
	 * Returns -1 if there is no special alert
	 */
	public int getAlert()
	{
		return _alert;
	}

	/**
	 * Updates the alert string reference.
	 */
	public void updateAlert()
	{
		_alert = _calculateAlert();
	}

	/**
	 * Works out the most relevant "alert" descriptor based on the current time
	 */
	private int _calculateAlert()
	{
		// TODO cache this and update it based on job schedule events rather than calc every time
		int mostRelevant = -1;
		LocalDateTime now = LocalDateTime.now();
		if (now.getDayOfWeek().equals(_day)) {
			mostRelevant = R.string.today;
			LocalTime timeNow = now.toLocalTime();

			if (_start.isBefore(timeNow)) {
				if (_end.isAfter(timeNow)) {
					mostRelevant = R.string.now;
				}
				// else finished for today - no alert
			}
			else if (_start.isBefore(timeNow.plusMinutes(5))) {
				mostRelevant = R.string.starting;
			}
			else if (_start.isBefore(timeNow.plusMinutes(20))) {
				mostRelevant = R.string.startingSoon;
			}
		}
		else if (now.plusHours(24).getDayOfWeek().equals(_day)) {
			mostRelevant = R.string.tomorrow;
		}
		// all other cases leave it as -1  = ends up blank

		return mostRelevant;
	}

	public static List<ScheduleItem> getEmptyShedule(Context context)
	{
		if (_emptySchedule == null) {
			ScheduleItem emptyScheduleItem = new _EmptyScheduleItem(context);
			_emptySchedule = Collections.singletonList(emptyScheduleItem);
		}
		return _emptySchedule;
	}

	@Override
	public int compareTo(ScheduleItem that)
	{
		if (that == _emptySchedule || this == _emptySchedule) {
			return 0;
		}

		Integer thatDay = new Integer(that._day.getValue());
		Integer thisDay = new Integer(this._day.getValue());
		int dayDiff = thisDay.compareTo(thatDay);
		if (dayDiff != 0) {
			return dayDiff;
		}
		// if we got here, the scheduled items are on the same day of the week so
		// we have to compare the local time
		int timeOfDayDiff = _start.compareTo(that._start); // local int for easy debug
		return timeOfDayDiff;
	}

	private static class _EmptyScheduleItem extends ScheduleItem
	{
		public _EmptyScheduleItem(Context context)
		{
			super(context);
		}

		@Override
		public String getCode()
		{
			return "!";
		}

		@Override
		public String getLabel()
		{
			return getContext().getResources().getString(R.string.emtpy_schedule);
		}

		@Override
		public String getTimeSlot()
		{
			return getContext().getResources().getString(R.string.emtpy_schedule_message);
		}
	}
}
