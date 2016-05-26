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

/**
 * Created by Mirzaadr on 1/13/2015.
 */
public class detilpenginapan extends Activity {
    // Declare Variables
    public ImageLoader imageLoader;
    {
        imageLoader = new ImageLoader(null);

    }

    private static final String url_search = "http://himatipa.tp.ugm.ac.id/lookate/tpt_tinggal_detail.php";
    static String TAG_TOKO = "tpt_tinggal";
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.detilpenginapan);
        map = (Button) findViewById(R.id.resident_map);
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
        Intent myIntent = getIntent();
        // gets the arguments from previously created intent
        searchkey = myIntent.getStringExtra("key");
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
            mProgressDialog = new ProgressDialog(detilpenginapan.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Informasi Penginapan");
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


                        try {
                            JSONObject array = toko.getJSONObject(0);
                            String nama = array.getString("nama");
                            String alamat = array.getString("alamat");
                            String cp = array.getString("cp");
                            String bintang = array.getString("bintang");
                            String harga = array.getString("harga");
                            String lo = array.getString("lo");
                            String la = array.getString("la");


                            rank.setText(nama);
                            country.setText(alamat);
                            population.setText("Contact Person : "+String.valueOf(cp));
                            text1.setText("Rating : "+String.valueOf(bintang));
                            text2.setText("Harga : "+String.valueOf(harga));
                            //text3.setText(lo);
                            //text4.setText(la);
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
