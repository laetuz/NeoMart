package id.neotica.neomart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        ArrayList<ItemModel> dataList = new ArrayList<>();
        dataList.add(new ItemModel("101", "Galaxy S2", ""));
        dataList.add(new ItemModel("102", "HTC Nexus", ""));
        dataList.add(new ItemModel("103", "Xperia Explorer", ""));

        ArrayAdapter<ItemModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);

        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // set the logic here later :p
            }
        });
    }
}
