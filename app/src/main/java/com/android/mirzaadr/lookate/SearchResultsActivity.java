/**
 * Created by Mirzaadr on 1/1/2015.
 */

package com.android.mirzaadr.lookate;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResultsActivity extends ActionBarActivity {

    private static String url_search = "http://himatipa.tp.ugm.ac.id/lookate/makanan_cari.php";
    JSONObject jsonobject;
    JSONParser jParser = new JSONParser();
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String TAG_MAKANAN = "makanan";
    static String TAG_ID = "id";
    static String TAG_NAMA = "nama";
    static String TAG_ALAMAT = "alamat";
    static String TAG_CP = "cp";
    static String TAG_IMAGE = "image";
    static String TAG_SUCCESS = "success";
    JSONArray makanan = null;
    public String searchkey;
    private TextView txtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);

        // get the action bar
        ActionBar actionBar = getActionBar();


        txtQuery = (TextView) findViewById(R.id.txtQuery);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            searchkey = query;
            //Loader image - will be shown before loading image
            // Execute DownloadJSON AsyncTask
            new DownloadJSON().execute();


        }

    }
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SearchResultsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Cari Warung");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            // Retrieve JSON Objects from the given URL address
            args.add(new BasicNameValuePair("keyword", searchkey));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_search, "GET", args);

            // Check your log cat for JSON response
            Log.d("Search idioms: ", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Locate the array name in JSON
                    makanan = json.getJSONArray(TAG_MAKANAN);

                    for (int i = 0; i < makanan.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = makanan.getJSONObject(i);
                        // Retrive JSON Objects
                        map.put("id", jsonobject.getString("id"));
                        map.put("nama", jsonobject.getString("nama"));
                        map.put("alamat", jsonobject.getString("alamat"));
                        map.put("cp", jsonobject.getString("cp"));
                        map.put("image", jsonobject.getString("image"));
                        // Set the JSON Objects into the array
                        arraylist.add(map);
                    }
                }
                else if (success == 0)
                {
                    makanan = json.getJSONArray(TAG_MAKANAN);
                    for (int i = 0; i < makanan.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = makanan.getJSONObject(i);
                        // Retrive JSON Objects
                        map.put("id", null);
                        map.put("nama", jsonobject.getString("message"));
                        map.put("alamat", null);
                        map.put("cp", null);
                        map.put("image", "http://img3.wikia.nocookie.net/__cb20110517171552/assassinscreed/images/3/39/Not-found.jpg");
                        // Set the JSON Objects into the array
                        arraylist.add(map);
                    }
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.list);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent in = new Intent(getApplicationContext(), detilmakanan.class);
                                                    String idmem = ((TextView) view.findViewById(R.id.idsearch)).getText().toString();
                                                    in.putExtra("key", idmem);
                                                    startActivity(in);

                                                }
                                            });
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(SearchResultsActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }
}

