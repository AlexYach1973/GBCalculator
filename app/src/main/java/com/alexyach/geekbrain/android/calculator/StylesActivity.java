package com.alexyach.geekbrain.android.calculator;

import android.os.Bundle;
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
                // в текущем активити
                recreate();
            }
        });

        btnDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCurrentTheme("dark");
                // пересоздадим активити, чтобы тема применилась
                // в текущем активити
                recreate();
            }
        });
    }

}