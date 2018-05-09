package com.example.hoang.ss14.ultis;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.databases.TopSongModel;
import com.example.hoang.ss14.network.LocationResponse;
import com.example.hoang.ss14.network.MusicService;
import com.example.hoang.ss14.network.RetrofitInstance;
import com.example.hoang.ss14.network.SearchSongResponse;


import hybridmediaplayer.HybridMediaPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoang on 5/3/2018.
 */

public class MusicHandle {
    private static final String TAG = "MusicHandle";
    private static HybridMediaPlayer hybridMediaPlayer;
    static Boolean keepUpdate = true;
    public static void getSearchSong(final TopSongModel topSongModel, final Context context) {
        MusicService musicService = RetrofitInstance.getRetrofitInstance()
                .create(MusicService.class);
        musicService.getSeachSong(topSongModel.song + " " +topSongModel.artist)
            .enqueue(new Callback<SearchSongResponse>() {
                @Override
                public void onResponse(Call<SearchSongResponse> call, Response<SearchSongResponse> response) {
                    Log.d(TAG, "onResponse: " + response.body().data.url);
                    String url = response.body().data.url;
                    getLocationSong(topSongModel,url,context);
                }

                @Override
                public void onFailure(Call<SearchSongResponse> call, Throwable t) {

                }
            });
    }

    public static void getLocationSong(final TopSongModel topSongModel,String url, final Context context){
        MusicService musicService = RetrofitInstance.getRetrofitXMLInstance()
                .create(MusicService.class);
        musicService.getLocation(url.split("=")[1]).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                Log.d(TAG, "onResponse: "+ response.body().track.location.trim());
                topSongModel.url = response.body().track.location.trim();
                if (hybridMediaPlayer != null){
                    if (hybridMediaPlayer.isPlaying()){
                        hybridMediaPlayer.pause();
                    }
                    hybridMediaPlayer.release();
                }
                hybridMediaPlayer = HybridMediaPlayer.getInstance(context);
                hybridMediaPlayer.setDataSource(response.body().track.location.trim());
                hybridMediaPlayer.prepare();
                hybridMediaPlayer.setOnPreparedListener(new HybridMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(HybridMediaPlayer hybridMediaPlayer) {
                        hybridMediaPlayer.play();
                    }
                });
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
    }

    public static void playPause(){
        if (hybridMediaPlayer != null ) {
            if (hybridMediaPlayer.isPlaying()){
                hybridMediaPlayer.pause();

            }else {
                hybridMediaPlayer.play();
            }
        }
    }

    public static void updateRealtimeUI(final SeekBar seekBar,
                                        final FloatingActionButton floatingActionButton,
                                        final TextView tvCurrent,
                                        final TextView tvDurration,
                                        final ImageView imageView){
        final android.os.Handler handler= new android.os.Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //update
                if (hybridMediaPlayer != null && keepUpdate){
                    seekBar.setMax(hybridMediaPlayer.getDuration());
                    seekBar.setProgress(hybridMediaPlayer.getCurrentPosition());

                    if (hybridMediaPlayer.isPlaying()){
                        floatingActionButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    }
                    else {
                        floatingActionButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                    if (tvCurrent != null){
                        tvCurrent.setText(Ultis.formatTime(hybridMediaPlayer.getCurrentPosition()));
                        tvDurration.setText(Ultis.formatTime(hybridMediaPlayer.getDuration()));
                    }
                    Ultis.rotateImage(imageView,hybridMediaPlayer.isPlaying());
                }
                //100ms run code

                handler.postDelayed(this,100);

            }
        };
        runnable.run();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                keepUpdate = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                keepUpdate = true;
                hybridMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }




}
