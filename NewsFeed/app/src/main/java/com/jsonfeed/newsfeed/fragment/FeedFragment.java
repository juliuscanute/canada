package com.jsonfeed.newsfeed.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.jsonfeed.newsfeed.R;
import com.jsonfeed.newsfeed.inject.DependencyProvider;
import com.jsonfeed.newsfeed.model.data.FeedItem;
import com.jsonfeed.newsfeed.presenter.FeedPresenter;
import java.util.ArrayList;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FeedFragment";
    private OnListFragmentInteractionListener mListener;
    private FeedPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedRecyclerViewAdapter mAdapter;

    public FeedFragment() {}


    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    private void fetchData(){
        mPresenter.loadData(this::dataReceived);
    }

    public void initWith(FeedPresenter presenter){
        mPresenter = presenter;
    }

    public void dataReceived(String source){
        Log.d(TAG,"Data received");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyProvider.shared.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);

        // Set the adapter
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            if(mPresenter.getCache() == null)
                mAdapter = new FeedRecyclerViewAdapter(new ArrayList<>(), mListener,mPresenter);
            else
                mAdapter = new FeedRecyclerViewAdapter(mPresenter.getCache().getRows(), mListener,mPresenter);
            mRecyclerView.setAdapter(mAdapter);

            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout.post(()-> {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchData();
        });
        setupObservables();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    private void setupObservables() {
        mPresenter.feeds().subscribe(feeds -> {
            FeedRecyclerViewAdapter adapter = (FeedRecyclerViewAdapter) mRecyclerView.getAdapter();
            if(feeds.getRows() == null) {
                Toast.makeText(getActivity(),getString(R.string.no_feeds),Toast.LENGTH_SHORT).show();
                adapter.setFeeds(mPresenter.getCache());
            }else {
                adapter.setFeeds(feeds);
                ((AppCompatActivity) (FeedFragment.this.getActivity())).getSupportActionBar().setTitle(feeds.getTitle());
            }
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.clearErrors();
        fetchData();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(FeedItem item);
    }
}
