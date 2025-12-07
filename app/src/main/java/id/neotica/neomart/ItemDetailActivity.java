package id.neotica.neomart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import id.neotica.neomart.data.DummyRepository;
import id.neotica.neomart.model.ItemDetail;

public class ItemDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        TextView tvCreatedAt = (TextView) findViewById(R.id.tv_created_at);

        String getIntentExtra = getIntent().getStringExtra("ITEM_ID");
        ItemDetail itemDetail = DummyRepository.getItemDetail(getIntentExtra);


        tvTitle.setText(itemDetail.getId());
        tvDesc.setText(itemDetail.getDesc());
        tvCreatedAt.setText(itemDetail.getCreatedAt());
//        tvTitle.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                // set onclick here
//            }
//        });

    }
}
