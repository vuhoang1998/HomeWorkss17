package com.example.hoang.ss14.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.adapters.TopSongAdapter;
import com.example.hoang.ss14.databases.TopSongModel;
import com.example.hoang.ss14.ultis.DownloadSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {

    TopSongAdapter topSongAdapter;
    List<TopSongModel> topSongModelList = new ArrayList<>();

    @BindView(R.id.rv_downloaded_songs)
    RecyclerView rvDownloadedSongs;
    Unbinder unbinder;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        unbinder = ButterKnife.bind(this, view);
        topSongModelList = DownloadSong.getListSong(getContext());
        topSongAdapter = new TopSongAdapter(topSongModelList);
        rvDownloadedSongs.setAdapter(topSongAdapter);
        rvDownloadedSongs.setLayoutManager(new LinearLayoutManager(getContext()));


        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
