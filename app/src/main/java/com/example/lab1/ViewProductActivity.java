package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewProductActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Intent intent = getIntent();
        this.nameTextView = findViewById(R.id.ItemNameId);
        this.descriptionTextView = findViewById(R.id.descriptionTextView);
        this.priceTextView = findViewById(R.id.PriceTextView);
        this.productImageView = findViewById(R.id.ViewProductImageId);
//        String imgPath = intent.getStringExtra("ProductImage");
//        try {
//            Uri contentURI = Uri.parse(imgPath);
//            ContentResolver cr = getContentResolver();
//            InputStream in = cr.openInputStream(contentURI);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 8;
//            Bitmap img = BitmapFactory.decodeStream(in, null, options);
//            this.productImageView.setImageBitmap(img);
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        this.productImageView.setImageDrawable(Drawable.createFromPath(intent.getStringExtra("ProductImage")));
        nameTextView.setText(intent.getStringExtra("ProductName"));
        descriptionTextView.setText(intent.getStringExtra("ProductDescription"));
        priceTextView.setText(intent.getStringExtra("ProductPrice"));


    }
}
