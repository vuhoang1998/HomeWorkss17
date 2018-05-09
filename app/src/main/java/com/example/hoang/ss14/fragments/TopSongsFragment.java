package com.example.hoang.ss14.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.adapters.TopSongAdapter;
import com.example.hoang.ss14.databases.MusicTypeModel;
import com.example.hoang.ss14.databases.TopSongModel;
import com.example.hoang.ss14.network.MusicService;
import com.example.hoang.ss14.network.RetrofitInstance;
import com.example.hoang.ss14.network.TopSongResponse;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongsFragment extends Fragment {


    private static final String TAG = "TopSongsFragment";
    @BindView(R.id.iv_music_type)
    ImageView ivMusicType;
    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_top_songs)
    RecyclerView rvTopSongs;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    Unbinder unbinder;
    MusicTypeModel musicTypeModel;

    TopSongAdapter topSongAdapter;
    List<TopSongModel> topSongModels = new ArrayList<>();
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoading;

    public TopSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_songs, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicTypeModel = (MusicTypeModel) getArguments().
                getSerializable("music_type_model");
        Picasso.get().load(musicTypeModel.imageID).into(ivMusicType);
        tvMusicType.setText(musicTypeModel.name);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "onOffsetChanged: " + verticalOffset);
                if (verticalOffset == 0) {
                    toolbar.setBackground(getResources().getDrawable(
                            R.drawable.custom_gradient_text_2
                    ));

                } else {
                    toolbar.setBackground(null);
                }
            }
        });


        topSongAdapter = new TopSongAdapter(topSongModels);
        rvTopSongs.setAdapter(topSongAdapter);
        rvTopSongs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTopSongs.setItemAnimator(new SlideInLeftAnimator());

        loadTopSongs();
        return view;
    }

    private void loadTopSongs() {
        MusicService musicService = RetrofitInstance.getRetrofitInstance()
                .create(MusicService.class);
        musicService.getTopSongs(musicTypeModel.id).enqueue(new Callback<TopSongResponse>() {
            @Override
            public void onResponse(Call<TopSongResponse> call, Response<TopSongResponse> response) {
                avLoading.hide();
                //Log.d(TAG, "onResponse: "+ response.body().feed.entry.size());
                List<TopSongResponse.Feed.Entry> entries = response.body().feed.entry;
                for (TopSongResponse.Feed.Entry entry : entries) {
                    TopSongModel topSongModel = new TopSongModel(
                            "",
                            entry.image.get(2).label,//lay con to nhat

                            entry.name.label,
                            entry.artist.label
                    );
                    Log.d(TAG, "imageURL: "+ entry.image.get(2).label);
                    topSongModels.add(topSongModel);
                    topSongAdapter.notifyItemChanged(entries.indexOf(entry));

                }
                //topSongAdapter.notifyDataSetChanged(); //con này báo cho recycle view
            }

            @Override
            public void onFailure(Call<TopSongResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
    }
}
