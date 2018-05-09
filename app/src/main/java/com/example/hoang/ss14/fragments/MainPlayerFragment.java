package com.example.hoang.ss14.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.databases.TopSongModel;
import com.example.hoang.ss14.events.OnClickTopSong;
import com.example.hoang.ss14.ultis.DownloadSong;
import com.example.hoang.ss14.ultis.MusicHandle;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_singer_name)
    TextView tvSingerName;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_duration_time_song)
    TextView tvDurationTimeSong;
    @BindView(R.id.tv_current_time_song)
    TextView tvCurrentTimeSong;
    @BindView(R.id.sb_main)
    SeekBar sbMain;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    Unbinder unbinder;
    TopSongModel topSongModel;

    public MainPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_player, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_close, R.id.iv_download, R.id.iv_pre, R.id.fb_play, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                getActivity().onBackPressed();
                break;
            case R.id.iv_download:
                Log.d(TAG, "onViewClicked: "+ "download button");
                DownloadSong.download(getContext(),topSongModel);
                break;
            case R.id.iv_pre:
                break;
            case R.id.fb_play:

                MusicHandle.playPause();
                break;
            case R.id.iv_next:
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onReceiveTopSong(OnClickTopSong onClickTopSong){
        topSongModel = onClickTopSong.topSongModel;

        tvSongName.setText(topSongModel.song);
        tvSingerName.setText(topSongModel.artist);

        Picasso.get().load(topSongModel.image)
                .transform(new CropCircleTransformation()).into(ivSong);

        MusicHandle.updateRealtimeUI(sbMain, fbPlay,tvCurrentTimeSong,tvDurationTimeSong,ivSong);
    }
}
