package com.coffeecodeandcreativity.rmpopular;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffeecodeandcreativity.rmpopular.R;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView title;
        ImageView poster;
        TextView latestrls;
        TextView year;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        title = (TextView) itemView.findViewById(R.id.title);

        latestrls = (TextView) itemView.findViewById(R.id.latestrls);

        year = (TextView) itemView.findViewById(R.id.year);

        // Locate the ImageView in listview_item.xml
        poster = (ImageView) itemView.findViewById(R.id.poster);
        title.setText(resultp.get(MainActivity.TITLE));
        latestrls.setText(resultp.get(MainActivity.LATESTRLS));
        year.setText(resultp.get(MainActivity.YEAR));

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(MainActivity.POSTER), poster);
        // Capture ListView item click

        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("title", resultp.get(MainActivity.TITLE));
                intent.putExtra("poster", resultp.get(MainActivity.POSTER));
                intent.putExtra("url", resultp.get(MainActivity.URL));
                intent.putExtra("year", resultp.get(MainActivity.YEAR));
                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        return itemView;
    }
}
