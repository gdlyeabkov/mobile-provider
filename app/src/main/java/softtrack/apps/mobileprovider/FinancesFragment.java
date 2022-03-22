package softtrack.apps.mobileprovider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class FinancesFragment extends Fragment {

    MainActivity parentActivity;
    public TextView activityFinancesContainerBodyAvailableLabel;

    FinancesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fincances, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initialize();

    }

    public void initialize() {
        initializeConstants();
        findViews();
        initializeWidgets();
    }

    public void initializeConstants() {
        parentActivity = (MainActivity) getActivity();
    }

    public void findViews() {
        activityFinancesContainerBodyAvailableLabel = parentActivity.findViewById(R.id.activity_finances_container_body_available_label);
    }

    public void initializeWidgets() {
        refreshAvailableLabel();
    }

    public void refreshAvailableLabel() {
        boolean isAddPrefix = false;
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        String rawHours = String.valueOf(hours);
        isAddPrefix = hours < 10;
        if (isAddPrefix) {
            rawHours = "0" + rawHours;
        }
        int minutes = calendar.get(Calendar.MINUTE);
        String rawMinutes = String.valueOf(minutes);
        isAddPrefix = minutes < 10;
        if (isAddPrefix) {
            rawMinutes = "0" + rawMinutes;
        }
        String currentTime = rawHours + ":" + rawMinutes;
        String availableLabelContent = "Сейчас доступно, " + currentTime;
        activityFinancesContainerBodyAvailableLabel.setText(availableLabelContent);
    }

}
