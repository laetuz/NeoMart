package id.neotica.neomart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        Button btnNewItems = (Button) findViewById(R.id.btn_new_items) ;
        Button btnHotItems = (Button) findViewById(R.id.btn_hot_items) ;

        tvTitle.setText("Welcome User!");

        btnNewItems.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewItemsActivity.class);

                startActivity(intent);
            }
        });


        btnHotItems.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HotItemsActivity.class);

                startActivity(intent);
            }
        });
    }
}
