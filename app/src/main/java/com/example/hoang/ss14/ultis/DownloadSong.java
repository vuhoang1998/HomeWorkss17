package com.example.hoang.ss14.ultis;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.hoang.ss14.R;
import com.example.hoang.ss14.activities.MainActivity;
import com.example.hoang.ss14.adapters.TopSongAdapter;
import com.example.hoang.ss14.databases.TopSongModel;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadManager;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 5/9/2018.
 */

public class DownloadSong {

    private static final String TAG = "DownloadSong" ;
    public static List<TopSongModel> topSongModelList = new ArrayList<>();
    public static void download(final Context context, TopSongModel topSongModel){
        Uri downloadUri = Uri.parse(topSongModel.url);
        Uri destinationUri = Uri.parse(context.getExternalCacheDir().toString()+"/downloadedd/"+topSongModel.song+"---"+topSongModel.artist);
        Log.d(TAG, "download: "+context.getExternalCacheDir().toString());
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Log.d(TAG, "onDownloadComplete: " + id);

                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(context, "Error : Download Failed",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                        Log.d(TAG, "onProgress: "+ "downloading");
                    }
                });
        new ThinDownloadManager().add(downloadRequest);
        //topSongModel.url = context.getExternalCacheDir().toString()+topSongModel.song+topSongModel.artist;

    }

    public static List<TopSongModel> getListSong(Context context){
        List<TopSongModel> topSongModelList = new ArrayList<>();
        String path = context.getExternalCacheDir().toString()+"/downloadedd";
        File directory = new File(path);
        Log.d("Files", "Path: " + path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            String[] split = files[i].getName().split("---");
            TopSongModel topSongModel = new TopSongModel(files[i].getPath(),
                    "https://is3-ssl.mzstatic.com/image/thumb/Music118/v4/04/82/54/048254ea-06f5-38a4-f5c4-9521dc0635c9/844942051825.jpg/170x170bb-85.png",
                    split[0],
                    split[1]);
            topSongModelList.add(topSongModel);
            Log.d(TAG, "getListSong: "+ split[0] + split[1] );
            Log.d("Files", "FileName:" + files[i].getName());
        }
        return topSongModelList;
    }

}
