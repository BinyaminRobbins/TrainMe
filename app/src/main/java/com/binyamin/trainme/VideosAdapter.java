package com.binyamin.trainme;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    private List<VideoItem> videoItems;

    public VideosAdapter(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.tiktoklayout_custom_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        holder.setVideoData(videoItems.get(position));

        holder.textVideoDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 openYoutube(v,position);
            }
        });
        holder.textVideoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube(v,position);
            }
        });
    }

    private void openYoutube(View v,int position) {
        Intent intent = new Intent(
                Intent.ACTION_VIEW ,
                Uri.parse(videoItems.get(position).youtubePageUrl));
        intent.setPackage("com.google.android.youtube");
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        PackageManager manager = _Page3_SelectWorkout.context.getPackageManager();
        List<ResolveInfo> infoList = manager.queryIntentActivities(intent, 0);
        if (infoList.size() > 0) {
            v.getContext().startActivity(intent);
        }else{
            //No Application can handle your intent
            Toast.makeText(v.getContext(),"Can't Find Youtube App",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView textVideoTitle, textVideoDescription;
        WebView webView;
        ProgressBar videoProgressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            videoProgressBar = itemView.findViewById(R.id.tiktokProgressBar);
            webView = itemView.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            WebViewClient client = new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    videoProgressBar.setVisibility(View.GONE);
                }
            };
            webView.setWebViewClient(client);
        }

        void setVideoData(VideoItem videoItem){
            textVideoTitle.setText(videoItem.videoTitle);
            textVideoDescription.setText(videoItem.videoDescription);
            webView.loadUrl(videoItem.videoUrl);
        }
    }
}
