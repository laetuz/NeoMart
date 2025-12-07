package id.neotica.neomart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.neotica.neomart.data.DummyRepository;
import id.neotica.neomart.model.ItemDetail;
import id.neotica.neomart.model.ItemModel;

public class NewItemsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_items);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        ListView mainList = (ListView) findViewById(R.id.lv_main) ;

        tvTitle.setText("New Items!");
        tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // set onclick here
            }
        });

        String[] data = {"item", "item"};

        List<ItemDetail> itemDummy = DummyRepository.getItemList();

        ArrayList<ItemModel> dataList = new ArrayList<>();

        for (ItemDetail items: itemDummy) {
            dataList.add(
                    new ItemModel(
                            items.getId(),
                            items.getName(),
                            items.getImageUrl()
                    )
            );
        }

        final ArrayAdapter<ItemModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);

        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set the logic here later :p
                ItemModel currentItem = adapter.getItem(position);

                if (currentItem != null) {
                    Intent intent = new Intent(NewItemsActivity.this, ItemDetailActivity.class);
                    intent.putExtra("ITEM_ID", currentItem.getId());
                    startActivity(intent);
                }
            }
        });
    }
}
