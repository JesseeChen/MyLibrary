package com.jesse.ratiolayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.jesse.ratiolayoutlibrary.RatioLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RatioLayout ratioLayout = (RatioLayout) findViewById(R.id.ratio_layout);
        final Button btn = (Button) findViewById(R.id.button);
        ratioLayout.setOnRatioChangedListener(new RatioLayout.OnRatioChangedListener() {
            @Override
            public void onRatioChanged(float ratioX, float ratioY) {
                btn.setText(String.format("%-5s %5.2f\n%-5s %5.2f", "x%", ratioX, "y%", ratioY));
            }
        });
    }
}
