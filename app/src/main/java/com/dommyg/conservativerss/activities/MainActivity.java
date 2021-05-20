package com.dommyg.conservativerss.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.adapters.OnDisplayItemClickListener;
import com.dommyg.conservativerss.databinding.ActivityMainBinding;
import com.dommyg.conservativerss.fragments.ArticleListFragment;
import com.dommyg.conservativerss.util.Constants;

public class MainActivity extends AppCompatActivity implements OnDisplayItemClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            // This activity's creation was not from a configuration change (such as screen
            // rotation). Therefore, it does not have already have a fragment inflated and the
            // default one must be inflated.
            init();
        }
    }

    /**
     * Initializes the activity by adding the default fragment
     * ({@link com.dommyg.conservativerss.fragments.ArticleListFragment}).
     */
    private void init() {
        // TODO: Process setup.
        ArticleListFragment fragment = new ArticleListFragment();
        doFragmentTransaction(fragment, Constants.TAG_ARTICLE_LIST_FRAGMENT, false);
    }

    /**
     * Adds a fragment to the {@link MainActivity}'s FrameLayout.
     * @param fragment The fragment to be used in the transaction.
     * @param fragmentTag The fragment's tag, available from the hosting activity.
     * @param addToBackStack If the fragment should be added to the back stack, meaning that if
     *                       another fragment is added after this fragment, will this fragment be
     *                       left in the stack behind it (or will it be removed). Fragments in the
     *                       back stack can be navigated to in order of addition by the user
     *                       pressing the back button.
     */
    private void doFragmentTransaction(Fragment fragment, String fragmentTag,
                                       boolean addToBackStack) {
        // Create a fragment transaction.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replaces the current fragment in the FrameLayout (if there is one).
        transaction.replace(binding.mainActivityFrameLayout.getId(), fragment, fragmentTag);

        if (addToBackStack) {
            // Add the fragment in this transaction to the back stack so that it will be remembered
            // if this fragment is replaced with another later on.
            transaction.addToBackStack(fragmentTag);
        }
        transaction.commit();
    }

    @Override
    public void onArticleClick(int position) {

    }

    @Override
    public void onSourceHeaderClick(int position) {

    }

    @Override
    public void onSourceMenuClick(int position) {

    }

    @Override
    public void onSettingsMenuClick(int position) {

    }
}