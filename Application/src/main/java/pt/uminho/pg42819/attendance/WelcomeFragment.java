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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pt.uminho.pg42819.attendance.data.ScheduleManager;
import pt.uminho.pg42819.attendance.model.Course;
import pt.uminho.pg42819.attendance.data.CourseLoader;

public class WelcomeFragment extends Fragment {

    static final String TAG = "VectorDrawableFragmentTag";
    private Course[] courses;
    private ScheduleManager _scheduleManager;

    public WelcomeFragment(ScheduleManager scheduleManager)
    {
        _scheduleManager = scheduleManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO move the dataset into the server layer to allow fixes in course info
        initDataset();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View welcomeView = inflater.inflate(R.layout.fragment_welcome, container, false);
        welcomeView.setTag(TAG);

        // set up the RecyclerView
        RecyclerView recyclerView = welcomeView.findViewById(R.id.lessonsRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                                                                 DividerItemDecoration.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ScheduleRecycleAdapter adapter = new ScheduleRecycleAdapter(_scheduleManager);
        recyclerView.setAdapter(adapter);

        return welcomeView;
    }

    private void initDataset()
    {
//    	getActivity().getPreferences(dd
        courses = new CourseLoader(getActivity().getApplicationContext()).getCourses();
    }
}
