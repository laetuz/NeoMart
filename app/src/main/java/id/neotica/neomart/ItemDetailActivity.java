package id.neotica.neomart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
        ImageView ivItem = (ImageView) findViewById(R.id.iv_item);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();

        String getIntentExtra = getIntent().getStringExtra("ITEM_ID");
        ItemDetail itemDetail = DummyRepository.getItemDetail(getIntentExtra);


        tvTitle.setText(itemDetail.getName());
        tvDesc.setText(itemDetail.getDesc());
        tvCreatedAt.setText(itemDetail.getCreatedAt());

        imageLoader.displayImage(itemDetail.getImageUrl(), ivItem);

    }
}
