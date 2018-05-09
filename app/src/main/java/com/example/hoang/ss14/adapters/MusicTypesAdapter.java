package com.example.hoang.ss14.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.activities.MainActivity;
import com.example.hoang.ss14.databases.MusicTypeModel;
import com.example.hoang.ss14.fragments.TopSongsFragment;
import com.example.hoang.ss14.ultis.Ultis;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoang on 4/15/2018.
 */

public class MusicTypesAdapter extends RecyclerView.Adapter<MusicTypesAdapter.MusicTypesViewHolder>{
    List<MusicTypeModel> musicTypeModels = new ArrayList<>();
    Context context;

    public MusicTypesAdapter(List<MusicTypeModel> musicTypeModels, Context context){
        this.musicTypeModels = musicTypeModels;
        this.context = context;
    }

    //tao itemView
    @Override
    public MusicTypesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_music,parent,false);
        return new MusicTypesViewHolder(itemView);
    }

    //load Data
    @Override
    public void onBindViewHolder(MusicTypesViewHolder holder, int position) {
        holder.setData(musicTypeModels.get(position));
    }

    @Override
    public int getItemCount() {
        return musicTypeModels.size();
    }

    public class MusicTypesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_music_type)
        ImageView ivMusicType;
        @BindView(R.id.tv_music_type)
        TextView tvMusicType;

        public MusicTypesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        //load data
        public void setData(final MusicTypeModel musicTypeModel){

            Picasso.get().load(musicTypeModel.imageID).into(ivMusicType);
            tvMusicType.setText(musicTypeModel.name);

            ivMusicType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TopSongsFragment topSongsFragment = new TopSongsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("music_type_model",musicTypeModel);
                    topSongsFragment.setArguments(bundle);

                    Ultis.openFragment(
                            ((MainActivity) context).getSupportFragmentManager(),
                            R.id.ll_main,
                            topSongsFragment);
                }
            });
        }
    }
}
