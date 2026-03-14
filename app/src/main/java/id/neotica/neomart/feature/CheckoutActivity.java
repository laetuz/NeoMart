package id.neotica.neomart.feature;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import id.neotica.neomart.R;

public class CheckoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);

        tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // set onclick here
            }
        });
    }
}
