package com.jsonfeed.newsfeed.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jsonfeed.newsfeed.R;
import com.jsonfeed.newsfeed.fragment.FeedFragment;
import com.jsonfeed.newsfeed.model.data.FeedItem;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedActivity extends AppCompatActivity implements FeedFragment.OnListFragmentInteractionListener {


    private Fragment createFragment(){
        return FeedFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(FeedItem item) {

    }
}
