package id.neotica.neomart.feature.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import id.neotica.neomart.feature.CheckoutActivity;
import id.neotica.neomart.R;
import id.neotica.neomart.network.ApiCallback;
import id.neotica.neomart.network.ApiTask;
import id.neotica.neomart.utils.Constants;

public class ItemDetailActivity extends Activity {

    private TextView tvTitle, tvDesc, tvPrice, tvCreatedAt;
    private ImageView ivItem;
    private Button btnBuy;
    private ImageLoader imageLoader;

    private String currentItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCreatedAt = (TextView) findViewById(R.id.tv_created_at);
        ivItem = (ImageView) findViewById(R.id.iv_item);
        btnBuy = (Button) findViewById(R.id.btn_buy);

        btnBuy.setEnabled(false);

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .build();
            ImageLoader.getInstance().init(config);
        }

        imageLoader = ImageLoader.getInstance();

        currentItemId = getIntent().getStringExtra("ITEM_ID");

        if (currentItemId == null || currentItemId.length() == 0) {
            Toast.makeText(this, "Error: No Item ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String fullApiUrl = Constants.BASE_URL + "/" + currentItemId;

        ApiCallback detailCallback = new ApiCallback() {
            @Override
            public void onSuccess(String response) {
                btnBuy.setEnabled(true);
                try {
                    JSONObject obj = new JSONObject(response);

                    String name = obj.getString("name");
                    String desc = obj.getString("description");
                    double price = obj.getDouble("price");
                    String rawImgPath = obj.optString("image_url", "");

                    String fullImageUrl = (rawImgPath == null || rawImgPath.length() == 0) ? "" : Constants.IMG_BASE_URL + rawImgPath;
                    tvTitle.setText(name);
                    tvDesc.setText(desc);
                    tvPrice.setText("Price: Rp " + price);

                    if (fullImageUrl.length() > 0) {
                        imageLoader.displayImage(fullImageUrl, ivItem);
                    }

                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ItemDetailActivity.this, CheckoutActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(ItemDetailActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ItemDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        };

        new ApiTask(this, "GET", fullApiUrl, null, "Loading details...", detailCallback).execute();
    }
}
