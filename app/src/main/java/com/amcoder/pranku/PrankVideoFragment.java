//package com.amcoder.pranku;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//
//public class PrankVideoFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {
//    private static final int RECOVERY_REQUEST = 1;
//    private YouTubePlayerView youTubeView;
//    //private MyPlayerStateChangeListener playerStateChangeListener;
//    private YouTubePlaybackEventListener playbackEventListener;
//    private YouTubePlayer player;
//    private Context mContext;
//
//    public void PrankVideoFragment(Context pContext){
//        mContext = pContext;
//    }
//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        View v = layoutInflater.inflate(R.layout.prank_video_fragment, viewGroup);
//        youTubeView = (YouTubePlayerView)v. findViewById(R.id.youtube_view);
//        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
//
//        //playerStateChangeListener = new MyPlayerStateChangeListener();
//        playbackEventListener = new YouTubePlaybackEventListener();
//        return v;
//
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//        this.player = player;
//        //player.setPlayerStateChangeListener(playerStateChangeListener);
//        player.setPlaybackEventListener(playbackEventListener);
//
//        if (!wasRestored) {
//            player.cueVideo("lMtDN7HWyis"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        if (youTubeInitializationResult.isUserRecoverableError()) {
//            youTubeInitializationResult.getErrorDialog((Activity) mContext, RECOVERY_REQUEST).show();
//        } else {
//            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
//            Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//}
