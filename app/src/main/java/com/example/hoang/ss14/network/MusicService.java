package com.example.hoang.ss14.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hoang on 4/15/2018.
 */

public interface MusicService {
    @GET("api")
    Call<MusicTypeResponse> getListMusicTypes();

    @GET("https://itunes.apple.com/us/rss/topsongs/limit=50/genre={idMusicType}/explicit=true/json")
    Call<TopSongResponse>  getTopSongs(@Path("idMusicType") String idMusicType);

    @GET("http://services.techkids.vn/api/audio")
    Call<SearchSongResponse> getSeachSong(@Query("search_terms") String query);

    @GET("xml")
    Call<LocationResponse> getLocation(@Query("key1") String key);
}
