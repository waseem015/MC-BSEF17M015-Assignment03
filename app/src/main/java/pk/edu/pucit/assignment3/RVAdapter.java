package pk.edu.pucit.assignment3;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Field;

import static androidx.core.content.ContextCompat.startActivity;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    Context context;
    private String[] mainTitle;
    private String[] level;
    private String[] imgpaths;
    private String[] content;
    private String[] url;

    public RVAdapter(Context context,String [] mainTitle,String [] level,String [] content,String[] imgpaths,String[] url){
        this.context=context;
        this.mainTitle=mainTitle;
        this.level=level;
        this.imgpaths=imgpaths;
        this.content=content;
        this.url=url;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        //inflate layout here
        View row= LayoutInflater.from(context).inflate(R.layout.list_items,null,false);
        ViewHolder vh=new ViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(mainTitle[position]);
        holder.level.setText(level[position]);
        String p=imgpaths[position];
        p=p.substring(4,p.length()-4);
        p=p.toLowerCase();
        try {
            Class res = R.drawable.class;
            Field field = res.getField(p);
            int drawableId = field.getInt(null);
            holder.icon.setImageResource(drawableId);
        }
        catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        holder.content.setText(content[position]);
        int l2=url[position].length();
        String s=url[position].substring(l2-4,l2);
        if(s.equals("html"))
        {
            holder.download.setText("Read Online");
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[position]));
                    context.startActivity(browserIntent);
                }
            });

        }
        else
        {
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url[position]));
                   request.setDescription("Downloading");
                   request.setTitle(mainTitle[position]);
                   request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                   DownloadManager downloadManager= (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                   downloadManager.enqueue(request);// enqueue puts the download request in the queue.

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mainTitle.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView level;
        public ImageView icon;
        public TextView content;
        public Button download;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            level = itemView.findViewById(R.id.level);
            icon = itemView.findViewById(R.id.imageview);
            content=itemView.findViewById(R.id.info);
            download=itemView.findViewById(R.id.btnDownload);
        }
    }
}