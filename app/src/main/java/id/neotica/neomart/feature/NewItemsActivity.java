package id.neotica.neomart.feature;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import id.neotica.neomart.R;
import id.neotica.neomart.feature.detail.ItemDetailActivity;
import id.neotica.neomart.model.ItemModel;

public class NewItemsActivity extends Activity {

    private ListView mainList;
    private ArrayAdapter<ItemModel> adapter;
    private ArrayList<ItemModel> dataList;

    private final String API_URL = "http://dev.neotica.id/neomart/products";
    private final String IMG_BASE_URL = "http://dev.neotica.id/neomart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_items);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("New Items!");

        mainList = (ListView) findViewById(R.id.lv_main);
        dataList = new ArrayList<ItemModel>();
        adapter = new ArrayAdapter<ItemModel>(this, android.R.layout.simple_list_item_1, dataList);
        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel currentItem = adapter.getItem(position);

                if (currentItem != null) {
                    Intent intent = new Intent(NewItemsActivity.this, ItemDetailActivity.class);
                    intent.putExtra("ITEM_ID", currentItem.getId());
                    startActivity(intent);
                }
            }
        });

        new FetchProductsTask().execute(API_URL);

    }

    private class FetchProductsTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = ProgressDialog.show(NewItemsActivity.this, "", "Loading products...", true);
            } catch (Throwable t) {
                // Ignore window crashes
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                conn.disconnect();
                return response.toString();
            } catch (Throwable e) {
                return "Network error: " + e.toString();
            }
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            try {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            } catch (Throwable e) {

            }

            if (jsonResponse == null) {
                Toast.makeText(NewItemsActivity.this, "Failed to connect to server.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonResponse);
                dataList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String rawImgPath = obj.optString("image_url", "");
                    String fullImageUrl = (rawImgPath == null || rawImgPath.length() == 0) ? "" : IMG_BASE_URL + rawImgPath;

                    ItemModel item = new ItemModel(
                            obj.getString("id"),
                            obj.getString("name"),
                            obj.getDouble("price"),
                            obj.getInt("stock"),
                            obj.optString("description", "null"),
                            fullImageUrl
                    );

                    dataList.add(item);
                }

                adapter.notifyDataSetChanged();
            } catch (Throwable e) {
                e.printStackTrace();
                Toast.makeText(NewItemsActivity.this, "Error parsing server data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
