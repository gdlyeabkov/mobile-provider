package softtrack.apps.mobileprovider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewStateAdapter extends FragmentStateAdapter {

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CommunicationFragment();
        } else if (position == 1) {
            return new FinancesFragment();
        } else if (position == 2) {
            return new ServicesFragment();
        } else if (position == 3) {
            return new ForMeFragment();
        } else if (position == 4) {
            return new MoreFragment();
        }
        return new CommunicationFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
