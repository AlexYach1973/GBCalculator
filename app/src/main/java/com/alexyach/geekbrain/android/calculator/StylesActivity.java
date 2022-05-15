package com.alexyach.geekbrain.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StylesActivity extends MainActivity {

    Button btnLightTheme, btnDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles);

        btnLightTheme = findViewById(R.id.button_light);
        btnDarkTheme = findViewById(R.id.button_dark);

        btnLightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCurrentTheme("light");
                // пересоздадим активити, чтобы тема применилась
                recreate();
            }
        });

        btnDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCurrentTheme("dark");
                // пересоздадим активити, чтобы тема применилась
                recreate();
            }
        });
    }

}