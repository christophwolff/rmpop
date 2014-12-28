package com.example.designflaws.rmpopular;

/**
 * Created by christoph on 28.12.14.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SingleItemView extends Activity {
    // Declare Variables
    String title;
    String imdb;
    String poster;
    String url;
    String position;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from SingleItemView.xml
        setContentView(R.layout.singleview_item);

        Intent i = getIntent();
        // Get the result of rank
        title = i.getStringExtra("title");
        // Get the result of country
        imdb = i.getStringExtra("imdb");

        // Get the result of flag
        poster = i.getStringExtra("poster");


        //Elements latestreleaseElems = detaildoc.select("ul.allrls:first-child");

        //Log.d("Lastest", latestreleaseElems.first().text());
        // Locate the TextViews in SingleItemView.xml
        TextView txttitle = (TextView) findViewById(R.id.Titlelabel);
        TextView txtimdb = (TextView) findViewById(R.id.imdb);


        // Locate the ImageView in SingleItemView.xml
        ImageView imgflag = (ImageView) findViewById(R.id.poster);

        // Set results to the TextViews
        txttitle.setText(title);
        txtimdb.setText(imdb);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(poster, imgflag);
    }

}