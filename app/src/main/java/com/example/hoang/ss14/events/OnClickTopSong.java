package com.example.hoang.ss14.events;

import com.example.hoang.ss14.databases.TopSongModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by hoang on 5/3/2018.
 */

public class OnClickTopSong {
    public TopSongModel topSongModel;



    public OnClickTopSong(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }
}
