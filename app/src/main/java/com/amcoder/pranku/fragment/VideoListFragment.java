package com.amcoder.pranku.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.amcoder.pranku.adapters.VideoListAdapter;
import com.amcoder.pranku.activities.CustomLightboxActivity;
import com.amcoder.pranku.R;
import com.amcoder.pranku.content.YouTubeContent;


public class VideoListFragment extends ListFragment {
    /**
     * Empty constructor
     */
    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new VideoListAdapter(getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //setTitleAndBackButtonVisibility(R.string.upload_fragment, true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        final Context context = getActivity();
        final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);
        final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(position);

        //Opens in the the custom Lightbox activity
        launchVideoLightboxActivity(context, video);
    }

    private void launchVideoLightboxActivity(Context context, YouTubeContent.YouTubeVideo video) {
        final Intent lightboxIntent = new Intent(context, CustomLightboxActivity.class);
        lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
        startActivity(lightboxIntent);
    }
}
