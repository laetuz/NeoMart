package id.neotica.neomart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);

        tvTitle.setText("Welcome User!");
        tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // set onclick here
            }
        });
    }
}
