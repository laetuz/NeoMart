package id.neotica.neomart.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import id.neotica.neomart.BuildConfig;
import id.neotica.neomart.R;
import id.neotica.neomart.feature.detail.ItemDetailActivity;
import id.neotica.neomart.model.ItemModel;
import id.neotica.neomart.network.ApiCallback;
import id.neotica.neomart.network.ApiTask;
import id.neotica.neomart.network.HttpMethod;
import id.neotica.neomart.utils.Constants;

public class NewItemsActivity extends Activity {

    public static final String ITEM_ID_PARAMS = "ITEM_ID";
    private static final String ITEM_ID = "id";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_PRICE = "price";
    private static final String ITEM_STOCK = "stock";
    private static final String ITEM_DESCRIPTION = "description";
    private static final String ITEM_IMAGE_URL = "image_url";

    private ListView mainList;
    private ArrayAdapter<ItemModel> adapter;
    private ArrayList<ItemModel> dataList;

    // Pagination State
    private int currentPage = 1;
    private int totalPages = 1;
    private Button btnLoadMore;
    private View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_items);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("New Items!");

        mainList = (ListView) findViewById(R.id.lv_main);
        dataList = new ArrayList<ItemModel>();

        footerView = getLayoutInflater().inflate(R.layout.footer_load_more, null);
        btnLoadMore = (Button) footerView.findViewById(R.id.btn_load_more);
        mainList.addFooterView(footerView);

        adapter = new ArrayAdapter<ItemModel>(this, android.R.layout.simple_list_item_1, dataList);
        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < dataList.size()) {
                    ItemModel currentItem = adapter.getItem(position);
                    if (currentItem != null) {
                        Intent intent = new Intent(NewItemsActivity.this, ItemDetailActivity.class);
                        intent.putExtra(ITEM_ID_PARAMS, currentItem.getId());
                        startActivity(intent);
                    }
                }
            }
        });

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage < totalPages) {
                    currentPage++;
                    fetchItems(currentPage);
                }
            }
        });

        fetchItems(currentPage);
    }

    private void fetchItems(final int pageToLoad) {
        String targetUrl = BuildConfig.BASE_URL + "?page=" + pageToLoad;

        new ApiTask(this, HttpMethod.GET, targetUrl, null, "Loading...", new ApiCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject rootObject = new JSONObject(response);

                    currentPage = rootObject.optInt("page", 1);
                    totalPages = rootObject.optInt("total_pages", 1);

                    JSONArray jsonArray = rootObject.getJSONArray("data");
                    if (pageToLoad == 1) dataList.clear();

                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String rawImgPath = obj.optString(ITEM_IMAGE_URL, "");
                            String fullImageUrl = (rawImgPath == null || rawImgPath.length() == 0) ? "" : Constants.IMG_BASE_URL + rawImgPath;

                            ItemModel item = new ItemModel(
                                    obj.getString(ITEM_ID),
                                    obj.getString(ITEM_NAME),
                                    obj.getDouble(ITEM_PRICE),
                                    obj.getInt(ITEM_STOCK),
                                    obj.optString(ITEM_DESCRIPTION, "null"),
                                    fullImageUrl
                            );

                            dataList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    if (currentPage >= totalPages) {
                        btnLoadMore.setVisibility(View.GONE);
                    } else {
                        btnLoadMore.setVisibility(View.VISIBLE);
                    }

                } catch (Throwable e) {
                    Toast.makeText(NewItemsActivity.this, "Error parsing Json", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(NewItemsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                if (pageToLoad > 1) {
                    currentPage--;
                }
            }
        }).execute();
    }
}
