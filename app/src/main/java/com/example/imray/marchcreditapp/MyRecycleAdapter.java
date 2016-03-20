package com.example.imray.marchcreditapp;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by lenovo on 14.03.2016.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter {

    private ImageKeeper imageKeeper;


    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public ViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }


    public MyRecycleAdapter(ImageKeeper imageKeeper)
    {
        super();
        this.imageKeeper = imageKeeper;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ImageView imageView = new ImageView(parent.getContext());
        // set the view's size, margins, paddings and layout parameters
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(3, 3, 3, 3);

        ViewHolder vh = new ViewHolder(imageView);
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Bitmap bm = imageKeeper.getBitmap(position);
        vh.mImageView.setImageBitmap(bm);
        vh.mImageView.setImageBitmap(bm);
    }


    @Override
    public int getItemCount() {
        return imageKeeper.getItemCount();
    }
}
