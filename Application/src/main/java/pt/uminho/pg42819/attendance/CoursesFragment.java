/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.uminho.pg42819.attendance;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import pt.uminho.pg42819.attendance.data.ScheduleManager;
import pt.uminho.pg42819.attendance.model.Course;
import pt.uminho.pg42819.attendance.data.CourseLoader;

public class CoursesFragment extends PreferenceFragmentCompat
{
    static final String TAG = "CoursesFragmentTag";
    private final CourseLoader _courseLoader;
    private final ScheduleManager _scheduleManager;

    public CoursesFragment(CourseLoader courseLoader, ScheduleManager scheduleManager)
    {
        _courseLoader = courseLoader;
        _scheduleManager = scheduleManager;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        final Course[] courses = _courseLoader.getCourses();

        PreferenceCategory profileCategory = new PreferenceCategory(context);
        profileCategory.setKey("profiles");
        profileCategory.setTitle("Profiles");
        profileCategory.setSummary("Select the profiles you're enrolled in");
        screen.addPreference(profileCategory);

        for (Course course : courses) {
            SwitchPreferenceCompat courseSwitch = new SwitchPreferenceCompat(context);
            courseSwitch.setKey(course.getCode());
            courseSwitch.setTitle(course.getCode());
            courseSwitch.setSummary(course.getName());
            profileCategory.addPreference(courseSwitch);
        }

        setPreferenceScreen(screen);

//        ListPreference themePreference = findPreference("themePref");
//        if (themePreference != null) {
//            themePreference.setOnPreferenceChangeListener(
//                    new Preference.OnPreferenceChangeListener() {
//                        @Override
//                        public boolean onPreferenceChange(Preference preference, Object newValue) {
//                            String themeOption = (String) newValue;
//                            ThemeHelper.applyTheme(themeOption);
//                            return true;
//                        }
//                    });
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // add the schedule manager to update the schedule when the user changes their course list
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(_scheduleManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(_scheduleManager);
    }

}
