package softtrack.apps.mobileprovider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ForMeFragment extends Fragment {

    ForMeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_for_me, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initialize();

    }

    public void initialize() {

    }

}
