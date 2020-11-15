package pt.uminho.pg42819.attendance.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a lesson slot in the course timetable
 */
public class Lesson
{
	DayOfWeek day;
	String start;
	String end;
	String name;
	String description;

	public DayOfWeek getDay()
	{
		return day;
	}

	public void setDay(DayOfWeek day)
	{
		this.day = day;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "Lesson{" +
			   "_day=" + day +
			   ", _start=" + start +
			   ", _end=" + end +
			   ", _name='" + name + '\'' +
			   ", _description='" + description + '\'' +
			   '}';
	}
}
