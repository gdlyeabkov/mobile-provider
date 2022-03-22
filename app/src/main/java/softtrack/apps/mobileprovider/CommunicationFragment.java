package softtrack.apps.mobileprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CommunicationFragment extends Fragment {

    MainActivity parentActivity;
    LinearLayout activityCommunicationContainerCosts;

    CommunicationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_communication, container, false);
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
        addHandlers();
    }

    public void initializeConstants() {
        parentActivity = (MainActivity) getActivity();
    }

    public void findViews() {
        activityCommunicationContainerCosts = parentActivity.findViewById(R.id.activity_communication_container_costs);
    }

    public void addHandlers() {
        activityCommunicationContainerCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentActivity, CostsActivity.class);
                parentActivity.startActivity(intent);
            }
        });
    }

}
