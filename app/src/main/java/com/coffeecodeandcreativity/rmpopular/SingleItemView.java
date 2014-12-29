package com.coffeecodeandcreativity.rmpopular;

/**
 * Created by christoph on 28.12.14.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SingleItemView extends Activity {
    // Declare Variables
    String title;
    String imdb;
    TextView txtimdb;
    String poster;
    String url;
    ListView listviewDetailrls;
    static String LATESTRLS = "latestrls";
    static String IMDB = "IMDB";
    static String RMLink = "RMLINK";

    ListViewDetailAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
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
        url = i.getStringExtra("url");

        // Get the result of flag
        poster = i.getStringExtra("poster");
        Log.d("IIMAGE LINK", poster);
        poster = poster.replace("108x155", "900x1331");
        Log.d("IIMAGE LINK", poster);

        // Locate the TextViews in Sine('i', 'x'));
        TextView txttitle = (TextView) findViewById(R.id.Titlelabel);
        TextView txtimdb = (TextView) findViewById(R.id.imdb);


        new ParseURL().execute();

        // Locate the ImageView in SingleItemView.xml
        ImageView imgPoster = (ImageView) findViewById(R.id.poster);

        // Set results to the TextViews
        txttitle.setText(title);
        txtimdb.setText(imdb);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        imageLoader.DisplayImage(poster, imgPoster);
    }

    private class ParseURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleItemView.this);
            // Set progressdialog title
            mProgressDialog.setTitle("RM Popular");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
                mProgressDialog.show();


        }
        @Override
        protected Void doInBackground(Void... params) {

            arraylist = new ArrayList<HashMap<String, String>>();
            try {

                Log.d("JSwa", "Connecting to [" + url + "]");
                int maxBodySize = 5048000;
                Document docdetail = Jsoup.connect("http://rapidmoviez.com/" + url).maxBodySize(maxBodySize).get();
                Log.d("JSwa", "Connected to [" + url + "]");
                // Get document (HTML page) title
                String title = docdetail.title();
                Log.d("JSwA", "Title [" + title + "]");

                // Get meta info
                Elements metaElems = docdetail.select("ul.allrls li");





                  Log.d("elemente", metaElems.text());
                for (Element metaElem : metaElems) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    Elements URLLink = metaElem.select("a[herf]");
                    map.put("RMLINK", URLLink.text());

                    map.put("latestrls", metaElem.text());

                    arraylist.add(map);

                }

                Elements imdbLink = docdetail.select(".imdbref a[href]");

                String linkText = imdbLink.text();

                Document imdbrating = Jsoup.connect(linkText).get();
                Elements imdbElems = imdbrating.select(".titlePageSprite.star-box-giga-star");

                Log.d("elemente IMDB", imdbElems.text());

                imdb = imdbElems.text();





            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listviewDetailrls = (ListView) findViewById(R.id.listViewRLS);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewDetailAdapter(SingleItemView.this, arraylist);
            // Set the adapter to the ListView
            listviewDetailrls.setAdapter(adapter);

            txtimdb = (TextView)findViewById(R.id.imdb);

            txtimdb.setText("IMDB-Rating: " + imdb);
            // Close the progressdialog
            mProgressDialog.dismiss();


        }
    }

}