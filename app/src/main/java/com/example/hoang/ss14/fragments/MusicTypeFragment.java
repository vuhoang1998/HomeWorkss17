package com.example.hoang.ss14.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.adapters.MusicTypesAdapter;
import com.example.hoang.ss14.databases.MusicTypeModel;
import com.example.hoang.ss14.network.MusicService;
import com.example.hoang.ss14.network.MusicTypeResponse;
import com.example.hoang.ss14.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeFragment extends Fragment {


    private static final String TAG = "MusicTypeFragment";
    @BindView(R.id.rv_music_type)
    RecyclerView rvMusicTypes;
    Unbinder unbinder;

    MusicTypesAdapter musicTypesAdapter;
    List<MusicTypeModel> musicTypeModels = new ArrayList<>();
    Context context;

    public MusicTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music_type, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicTypesAdapter = new MusicTypesAdapter(musicTypeModels,getContext());
        rvMusicTypes.setAdapter(musicTypesAdapter);
        rvMusicTypes.setLayoutManager(new LinearLayoutManager(getContext()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                2,
                GridLayoutManager.VERTICAL,
                false
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });
        rvMusicTypes.setLayoutManager(gridLayoutManager);

        context = getContext();
        loadData();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadData() {
        MusicService musicService = RetrofitInstance.getRetrofitInstance()
                .create(MusicService.class);
        musicService.getListMusicTypes().enqueue(new Callback<MusicTypeResponse>() {
            @Override
            public void onResponse(Call<MusicTypeResponse> call, Response<MusicTypeResponse> response) {
                List<MusicTypeResponse.MusicTypesJSON> musicTypesJSONList = response.body().subgenres;
                for (MusicTypeResponse.MusicTypesJSON musicTypesJSON : musicTypesJSONList){
                    MusicTypeModel musicTypeModel = new MusicTypeModel(
                            musicTypesJSON.id,
                            musicTypesJSON.translation_key,
                            context.getResources().getIdentifier(
                                    "genre_x2_" + musicTypesJSON.id,
                                    "raw",
                                    context.getPackageName()
                            )
                    );
                    musicTypeModels.add(musicTypeModel);
                }
                musicTypesAdapter.notifyDataSetChanged();
            }



            @Override
            public void onFailure(Call<MusicTypeResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
