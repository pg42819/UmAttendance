package pt.uminho.pg42819.attendance.data;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

import pt.uminho.pg42819.attendance.R;
import pt.uminho.pg42819.attendance.model.Course;

/**
 * Loads the initial timetable for MEI 2020/2021 from an internal JSON file.
 *
 * TODO: In the future this should be downloaded from the backend server
 *       to allow updates without app upgrades
 */
public class CourseLoader
{
	private static final String LOGTAG = CourseLoader.class.getSimpleName();

	private Context _context;
	private Course[] _courses;

	public CourseLoader(Context context)
	{
		_context = context;
		_courses = null;
	}

	public Course[] getCourses()
	{
		if (_courses == null) {
			_courses = loadCourses();
		}
		return _courses;
	}

	/**
	 * Load an array of courses from the local Json resource
	 */
	public Course[] loadCourses()
	{
		// TODO replace this with GSON loader to load from the service backend service instead of hardcodeing
		Gson gson = new GsonBuilder().create();
		Log.i(LOGTAG, "Loading courses");
		String jsonString = loadJsonFromResource(R.raw.courses);
		final Course[] courses = gson.fromJson(jsonString, Course[].class);
		for (Course course : courses) {
			Log.i(LOGTAG, course.toString());
		}
		return courses;
	}

	/**
	 * Read from a resources file into a JSON string
	 * @param id The id for the resource to load, typically held in the raw/ folder.
	 */
	public String loadJsonFromResource(int id)
	{
		Resources resources = _context.getResources();
		InputStream resourceReader = resources.openRawResource(id);
		Writer writer = new StringWriter();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
			String line = reader.readLine();
			while (line != null) {
				writer.write(line);
				line = reader.readLine();
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
		} finally {
			try {
				resourceReader.close();
			} catch (Exception e) {
				Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
			}
		}

		String jsonString = writer.toString();
		return jsonString;
	}
}
