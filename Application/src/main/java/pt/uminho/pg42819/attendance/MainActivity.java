package pt.uminho.pg42819.attendance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import pt.uminho.pg42819.attendance.data.CourseLoader;
import pt.uminho.pg42819.attendance.data.ScheduleManager;

/**
 * Main Activity of the MEI Schedule App.
 * This sets up the nav bar at the bottom, and holds the event handling

 */
public class MainActivity extends AppCompatActivity {

    private CourseLoader _courseLoader;
    private ScheduleManager _scheduleManager;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(HomeFragment.TAG);
                    return true;
                case R.id.navigation_preferences:
                    showFragment(CoursesFragment.TAG);
                    return true;
                case R.id.navigation_settings:
                    showFragment(AboutFragment.TAG);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationListener);

        if (savedInstanceState == null) {
            showFragment(HomeFragment.TAG);
        }
    }

    public CourseLoader getCourseLoader()
    {
        if (_courseLoader == null) {
            _courseLoader = new CourseLoader(this);
        }
        return _courseLoader;
    }

    public ScheduleManager getScheduleManager()
    {
        if (_scheduleManager == null) {
            _scheduleManager = new ScheduleManager(this, getCourseLoader());
        }
        return _scheduleManager;
    }

    @Override
    // TODO Menus are not used by MEI Schedule right now, consider getting rid of them
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // TODO: This was the overflow button - add it back if we want a menu
//        if (id == R.id.action_more) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Select and display the appopriate fragment: home, courses, or about,
     * when a user clicks on of the navigation buttons.
     *
     * @param tag tag provided by the button click for fragment to show
     */
    private void showFragment(@NonNull String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case HomeFragment.TAG: {
                    fragment = new HomeFragment(getScheduleManager());
                    break;
                }
                case CoursesFragment.TAG: {
                    fragment = new CoursesFragment(getCourseLoader(), getScheduleManager());
                    break;
                }
                case AboutFragment.TAG: {
                    fragment = new AboutFragment();
                    break;
                }
                default: {
                    fragment = new HomeFragment(getScheduleManager());
                    break;
                }
            }
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment, tag)
                .commit();
    }
}
