package pt.uminho.pg42819.attendance.model;

import java.util.List;

/**
 * Represents an  UMinho course, such as an MIEI course like Machine Learning for Applications.
 *
 */
public class Course
{
	private String code;
	private String name;
	private String type;
	private List<Lesson> lessons;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Lesson> getLessons()
	{
		return lessons;
	}

	public void setLessons(List<Lesson> lessons)
	{
		this.lessons = lessons;
	}

	@Override
	public String toString()
	{
		StringBuilder lessonStrings = new StringBuilder();
		for (Lesson lesson : this.lessons) {
			lessonStrings.append(lesson.toString());
		}

		return "Course{" +
			   "_code='" + code + '\'' +
			   ", _name='" + name + '\'' +
			   ", _type='" + type + '\'' +
			   ", _lessons=" + lessonStrings.toString() +
			   '}';
	}
}
