package com.abayomi.stockbay;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.UploadImg;
import com.abayomi.stockbay.Model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdpter extends RecyclerView.Adapter<ImageAdpter.ImageViewHolder> {

    private Context mContext;
    private List<UploadImg> mUploads;


    public ImageAdpter(Context context, List<UploadImg> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageAdpter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_img, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        UploadImg uploadCurrent = mUploads.get(position);


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView IMageList;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            //Ver o nome
            IMageList = itemView.findViewById(R.id.imgList);
        }
    }
}
