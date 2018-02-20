package com.jsonfeed.newsfeed.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jsonfeed.newsfeed.R;
import com.jsonfeed.newsfeed.fragment.FeedFragment.OnListFragmentInteractionListener;
import com.jsonfeed.newsfeed.model.data.FeedData;
import com.jsonfeed.newsfeed.model.data.FeedItem;
import com.jsonfeed.newsfeed.presenter.FeedPresenter;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.FeedItemHolder> {
    private static final String TAG = "FeedRecyclerViewAdapter";
    private List<FeedItem> mValues;
    private Set<Integer> mErrors;
    private OnListFragmentInteractionListener mListener;
    private FeedPresenter mPresenter;

    public FeedRecyclerViewAdapter(List<FeedItem> items, OnListFragmentInteractionListener listener, FeedPresenter presenter) {
        mValues = items;
        mListener = listener;
        mPresenter = presenter;
        mErrors = new TreeSet<>();
    }

    @Override
    public FeedItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed_all, parent, false);
        return new FeedItemHolder(view);
    }

    public void setFeeds(FeedData feedData) {
        if(feedData != null && feedData.getRows() != null) {
            mValues = feedData.getRows();
            notifyDataSetChanged();
        }
    }

    public void clearErrors() {
        mErrors.clear();
    }

    @Override
    public void onBindViewHolder(final FeedItemHolder holder, int position) {
        FeedItemHolder allInfoHolder = (FeedItemHolder) holder;
        allInfoHolder.mItem = mValues.get(position);
        if(allInfoHolder.mItem.getDescription() == null && allInfoHolder.mItem.getTitle() == null && allInfoHolder.mItem.getImageHref() == null) {
            allInfoHolder.mContentView.setVisibility(View.GONE);
            allInfoHolder.mImageView.setVisibility(View.GONE);
            allInfoHolder.mProgressBar.setVisibility(View.GONE);
            allInfoHolder.mIdView.setText(holder.mView.getContext().getText(R.string.feed_not_found));
            return;
        }
        allInfoHolder.mIdView.setText(mValues.get(position).getTitle());
        if (mValues.get(position).getDescription() == null) {
            allInfoHolder.mContentView.setVisibility(View.GONE);
        } else {
            allInfoHolder.mContentView.setVisibility(View.VISIBLE);
            allInfoHolder.mContentView.setText(mValues.get(position).getDescription());
        }
        //See if url is present
        String url = mValues.get(position).getImageHref();
        if (url != null) {
            //See if file is downloaded
            File file = new File(allInfoHolder.mView.getContext().getCacheDir() + "/images/" + mValues.get(position).getFileName());
            if (file.exists() && file.length() > 0) {
                //if file is downloaded decode the bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                //show image view
                allInfoHolder.mImageView.setVisibility(View.VISIBLE);
                //set the bitmap
                allInfoHolder.mImageView.setImageBitmap(bitmap);
                //hide the progress
                allInfoHolder.mProgressBar.setVisibility(View.GONE);
            } else if (!mErrors.contains(position)) {
                //if there was no error loading the image at this position or this is the first time
                //show the progress bar
                allInfoHolder.mProgressBar.setVisibility(View.VISIBLE);
                //dont show the image view
                allInfoHolder.mImageView.setVisibility(View.GONE);
                //dont show the description as well
                allInfoHolder.mContentView.setVisibility(View.GONE);
                mPresenter.loadImage(allInfoHolder.mView.getContext().getCacheDir(), url,
                        (data) -> {
                            notifyItemChanged(position);
                        },
                        (error) -> {
                            Log.d(TAG, "Error occurred at this position:" + position);
                            mErrors.add(position);
                            notifyItemChanged(position);
                        });
            } else {
                //if there was an error loading the image hide the progress no use :(
                allInfoHolder.mProgressBar.setVisibility(View.GONE);
                //set the image view to gone.
                allInfoHolder.mImageView.setVisibility(View.GONE);
            }
        } else {
            allInfoHolder.mProgressBar.setVisibility(View.GONE);
            allInfoHolder.mImageView.setVisibility(View.GONE);
            Log.d(TAG, "No image available");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class FeedItemHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public final ProgressBar mProgressBar;
        public FeedItem mItem;

        public FeedItemHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
            mImageView = view.findViewById(R.id.image);
            mProgressBar = view.findViewById(R.id.progress);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
