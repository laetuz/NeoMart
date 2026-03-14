package id.neotica.neomart.feature.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import id.neotica.neomart.feature.CheckoutActivity;
import id.neotica.neomart.R;
import id.neotica.neomart.data.DummyRepository;
import id.neotica.neomart.model.ItemDetail;

public class ItemDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        TextView tvPrice = (TextView) findViewById(R.id.tv_price);
        TextView tvCreatedAt = (TextView) findViewById(R.id.tv_created_at);
        ImageView ivItem = (ImageView) findViewById(R.id.iv_item);
        Button btnBuy = (Button) findViewById(R.id.btn_buy);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();

        String getIntentExtra = getIntent().getStringExtra("ITEM_ID");
        final ItemDetail itemDetail = DummyRepository.getItemDetail(getIntentExtra);

        tvTitle.setText(itemDetail.getName());
        tvDesc.setText(itemDetail.getDesc());

        tvCreatedAt.setText(itemDetail.getCreatedAt());
        tvPrice.setText(itemDetail.getPrice().toString());

        imageLoader.displayImage(itemDetail.getImageUrl(), ivItem);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailActivity.this, CheckoutActivity.class);
                intent.putExtra("ITEM_ID", itemDetail.getId());
                startActivity(intent);
            }
        });

    }
}
