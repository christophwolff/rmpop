package com.coffeecodeandcreativity.rmpopular;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import android.support.v4.widget.SwipeRefreshLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    private SwipeRefreshLayout mSwipeLayout;
    final String bothURL = "http://rapidmoviez.com/releases/popxml/b";
    final String moviesURL = "http://rapidmoviez.com/releases/popxml/m";
    final String showsURL = "http://rapidmoviez.com/releases/popxml/s";
    int helper;
    static String TITLE = "title";
    static String POSTER = "poster";
    static String URL = "url";
    static String YEAR = "year";
    static String LATESTRLS = "latestrls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get New Titles On Startup
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.primary_text_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        helper = 1;
        new ParseURL().execute();

     }
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                new ParseURL().execute();
            }
        }, 5000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ParseURL extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("RM Popular");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            if (helper == 1){
                mProgressDialog.show();

            }

        }
        @Override
        protected Void doInBackground(Void... params) {

            arraylist = new ArrayList<HashMap<String, String>>();
            try {

                Log.d("JSwa", "Connecting to [" + bothURL + "]");
                Document doc = Jsoup.connect(bothURL).get();
                Log.d("JSwa", "Connected to [" + bothURL + "]");
                // Get document (HTML page) title
                String title = doc.title();
                Log.d("JSwA", "Title [" + title + "]");

                // Get meta info
                Elements metaElems = doc.select("photo");

                for (Element metaElem : metaElems) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    String image = metaElem.attr("image");
                    String url = metaElem.attr("url");
                    String msText = metaElem.text();

                    //Document detaildoc = Jsoup.connect("http://rapidmoviez.com" + url).get();

                    //Elements latestreleaseElems = detaildoc.select("ul.allrls:first-child");

                    //Log.d("Lastest", latestreleaseElems.first().text());




                    Pattern pattern = Pattern.compile("\\(.*\\)");
                    Matcher matcher = pattern.matcher(msText);
                   // Log.d("matcher", matcher.group(1).toString());
                    while (matcher.find()) { // Find each match in turn; String can't do this.
                        String year = matcher.group(0); // Access a submatch group; String can't do this.
                        Log.d("matcher", year);
                        map.put("title", msText.replace(year, ""));
                        map.put("year", year);
                    }

                    map.put("poster", "http://rapidmoviez.com/" + metaElem.attr("image"));
                    //map.put("latestrls", latestreleaseElems.text());
                    map.put("url", metaElem.attr("url"));

                    arraylist.add(map);


                }


            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listView);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog

            if (helper == 1){
                mProgressDialog.dismiss();
                helper = 0;
            }

        }
    }


}
