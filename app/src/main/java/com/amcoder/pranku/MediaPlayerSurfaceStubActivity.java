package com.amcoder.pranku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MediaPlayerSurfaceStubActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MediaPlayerSurfaceStubActivity";

    protected Resources mResources;
    private Context pContext;

    private VideoSurfaceView mVideoView = null;
    private MediaPlayer mMediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pContext = this;
        mResources = getResources();
        mMediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor afd = mResources.openRawResourceFd(R.raw.testvideo);
            mMediaPlayer.setDataSource(
                    afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        mVideoView = new VideoSurfaceView(this, mMediaPlayer);
        setContentView(mVideoView);

//        LinearLayout ll = new LinearLayout(this);
//        Button button = new Button(this);
//        button.setOnClickListener(this);
//        button.setText("Prank your friends");
//        button.setBackgroundColor(getResources().getColor(R.color.primary_light));
//        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        ll.addView(button);
//        ll.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        this.addContentView(ll,
//                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.onResume();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(pContext, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
