package pt.uminho.pg42819.attendance;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

/**
 * Overriding the main application class to set a theme
 *
 * TODO: consider getting rid of this - since we don't care about dark theme per se
 */
public class AttendanceApplication extends Application {

    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE);
        ThemeHelper.applyTheme(themePref);
    }
}
