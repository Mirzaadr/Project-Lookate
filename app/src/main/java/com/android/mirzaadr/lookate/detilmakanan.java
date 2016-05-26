package com.android.mirzaadr.lookate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class detilmakanan extends Activity {
    // Declare Variables
    public ImageLoader imageLoader;
    {
        imageLoader = new ImageLoader(null);

    }

    private static final String url_search = "http://himatipa.tp.ugm.ac.id/lookate/makanan_detail.php";
    static String TAG_TOKO = "makanan";
    static String TAG_NAMA = "nama";
    static String TAG_ALAMAT = "alamat";
    static String TAG_CP = "cp";
    static String TAG_IMAGE = "image";
    static String TAG_SUCCESS = "success";
    public double longitude;
    public double latitude;
    Button map;


    JSONParser jParser = new JSONParser();

    ProgressDialog mProgressDialog;

    JSONArray toko = null;
    public String searchkey;

    TextView rank;
    TextView country;
    TextView population;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    TextView text9;
    TextView text10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.detilmakanan);
        Intent myIntent = getIntent();
        // gets the arguments from previously created intent
        searchkey = myIntent.getStringExtra("key");
        map = (Button) findViewById(R.id.food_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                in.putExtra("lat", latitude);
                in.putExtra("long", longitude);
                Bundle b = new Bundle();
                Bundle c =new Bundle();
                b.putDouble("long", longitude);
                in.putExtras(b);
                c.putDouble("lat", latitude);
                in.putExtras(c);
                in.putExtra("lokasi", TAG_NAMA);
                startActivity(in);
            }
        });
        //Loader image - will be shown before loading image
        // Execute DownloadJSON AsyncTask
        new DownloadJSON().execute();
    }

    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(detilmakanan.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Informasi Warung");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // Create an array
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                // Retrieve JSON Objects from the given URL address
                params1.add(new BasicNameValuePair("id", searchkey));
                // getting JSON string from URL
                JSONObject json = jParser.makeHttpRequest(url_search, "GET", params1);
                toko = json.getJSONArray(TAG_TOKO);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView thumb_image = (ImageView) findViewById(R.id.imageViewmakanan);
                        rank = (TextView) findViewById(R.id.textView);
                        country = (TextView) findViewById(R.id.textView2);
                        population = (TextView) findViewById(R.id.textView3);
                        text1 = (TextView) findViewById(R.id.textView4);
                        text2 = (TextView) findViewById(R.id.textView5);
                        //text3 = (TextView) findViewById(R.id.textView6);
                        //text4 = (TextView) findViewById(R.id.textView7);
                        text5 = (TextView) findViewById(R.id.textView8);
                        text6 = (TextView) findViewById(R.id.textView9);
                        text7 = (TextView) findViewById(R.id.textView10);
                        text8 = (TextView) findViewById(R.id.textView11);
                        text9 = (TextView) findViewById(R.id.textView12);
                        text10 = (TextView) findViewById(R.id.textView13);

                        try {
                            JSONObject array = toko.getJSONObject(0);
                            String nama = array.getString("nama");
                            String alamat = array.getString("alamat");
                            String cp = array.getString("cp");
                            String testimoni = array.getString("testimoni");
                            String rating = array.getString("rating");
                            String lo = array.getString("lo");
                            String la = array.getString("la");
                            String menu1 = array.getString("menu1");
                            String ket1 = array.getString("keterangan1");
                            String harga1 = array.getString("harga1");
                            String menu2 = array.getString("menu2");
                            String ket2 = array.getString("keterangan2");
                            String harga2 = array.getString("harga2");

                            rank.setText(nama);
                            country.setText(alamat);
                            population.setText("Contact Person : "+String.valueOf(cp));
                            text1.setText(testimoni);
                            text2.setText("Rating : "+String.valueOf(rating));
                            //text3.setText(lo);
                            //text4.setText(la);
                            text5.setText(menu1);
                            text6.setText(ket1);
                            text7.setText("Harga : "+String.valueOf(harga1));
                            text8.setText(menu2);
                            text9.setText(ket2);
                            text10.setText("Harga : "+String.valueOf(harga2));
                            longitude = Double.parseDouble(lo);
                            latitude = Double.parseDouble(la);

                            imageLoader.DisplayImage(array.getString(TAG_IMAGE), thumb_image);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            mProgressDialog.dismiss();

        }
    }
}