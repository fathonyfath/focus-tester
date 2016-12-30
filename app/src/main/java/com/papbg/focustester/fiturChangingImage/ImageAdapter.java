package com.papbg.focustester.fiturChangingImage;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Mikhanael on 12/28/2016.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> imageDataResources;

    public ImageAdapter(Context c, List<Integer> imageDataResources) {
        mContext = c;
        this.imageDataResources = imageDataResources;
    }

    public int getCount() {
        return imageDataResources.size();
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageDataResources.get(position));
        return imageView;
    }

}
