package id.neotica.neomart.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import id.neotica.neomart.R;
import id.neotica.neomart.feature.detail.ItemDetailActivity;
import id.neotica.neomart.model.ItemModel;
import id.neotica.neomart.network.ApiCallback;
import id.neotica.neomart.network.ApiTask;
import id.neotica.neomart.utils.Constants;

public class NewItemsActivity extends Activity {

    private ListView mainList;
    private ArrayAdapter<ItemModel> adapter;
    private ArrayList<ItemModel> dataList;

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

        ApiCallback productCallback = new ApiCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    dataList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String rawImgPath = obj.optString("image_url", "");
                        String fullImageUrl = (rawImgPath == null || rawImgPath.length() == 0) ? "" : Constants.IMG_BASE_URL + rawImgPath;

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
                    Toast.makeText(NewItemsActivity.this, "Error parsing Json", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(NewItemsActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        };

        new ApiTask(this, "GET", Constants.BASE_URL, null, "Loading...", productCallback).execute();
    }
}
