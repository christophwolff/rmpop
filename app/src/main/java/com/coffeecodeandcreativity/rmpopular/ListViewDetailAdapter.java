package com.coffeecodeandcreativity.rmpopular;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewDetailAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewDetailAdapter(Context context,
                                 ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

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

        TextView latestrls;
        TextView rmlink;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item_rls, parent, false);
        // Get the position
        resultp = data.get(position);
        latestrls = (TextView) itemView.findViewById(R.id.latestrls);

        latestrls.setText(resultp.get(SingleItemView.LATESTRLS));



        itemView.setOnClickListener(new View.OnClickListener() {
            String RMLINKresult = resultp.get(SingleItemView.RMLink);
            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Uri uriUrl = Uri.parse("http://rapidmoviez.com" + RMLINKresult);
                        // Pass all data rank
                        Log.d("LINKS", RMLINKresult);
                // Start SingleItemView Class
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);



            }
        });
        return itemView;
    }
}
