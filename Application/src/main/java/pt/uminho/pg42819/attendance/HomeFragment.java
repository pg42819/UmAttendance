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

/**
 * Shows the schedule of upcoming lessons
 */
public class HomeFragment extends Fragment {

    static final String TAG = "VectorDrawableFragmentTag";
    private Course[] _courses;
    private ScheduleManager _scheduleManager;

    public HomeFragment(ScheduleManager scheduleManager)
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

        final View homeView = inflater.inflate(R.layout.fragment_welcome, container, false);
        homeView.setTag(TAG);

        // set up the RecyclerView
        RecyclerView recyclerView = homeView.findViewById(R.id.lessonsRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                                                                 DividerItemDecoration.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ScheduleRecycleAdapter adapter = new ScheduleRecycleAdapter(_scheduleManager);
        recyclerView.setAdapter(adapter);

        return homeView;
    }

    private void initDataset()
    {
        _courses = new CourseLoader(getActivity().getApplicationContext()).getCourses();
        _scheduleManager.reset();
        _scheduleManager.updateAlerts();
    }
}
