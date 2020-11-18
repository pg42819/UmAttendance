package pt.uminho.pg42819.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Displays the about message describing the app, and nothing else.
 */
public class AboutFragment extends Fragment {

    static final String TAG = "AboutFragmentTag";

    public AboutFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View aboutView = inflater.inflate(R.layout.fragment_about, container, false);
        aboutView.setTag(TAG);

        final TextView aboutText = aboutView.findViewById(R.id.aboutText);
        aboutText.setText(R.string.about_message);

        return aboutView;
    }
}
