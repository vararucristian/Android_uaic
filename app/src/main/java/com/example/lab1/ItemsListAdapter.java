package com.example.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] itemsNames;
    private String[] imagesPath;

    public ItemsListAdapter(@NonNull Context context, String[] itemsNames, String[] imagesPath) {
        super(context, R.layout.item_listview_item);
        this.context = context;
        this.itemsNames = itemsNames;
        this.imagesPath = imagesPath;
    }

    @Override
    public int getCount() {
        return itemsNames.length;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_listview_item, parent, false);
            viewHolder.productImage = convertView.findViewById(R.id.ProductImage);
            viewHolder.itemName = convertView.findViewById(R.id.ItemName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File imgFile = new  File(imagesPath[position]);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        viewHolder.productImage.setImageBitmap(myBitmap);
        viewHolder.itemName.setText(itemsNames[position]);

            return convertView;
    }



    static class ViewHolder {
        ImageView productImage;
        TextView itemName;
}
}