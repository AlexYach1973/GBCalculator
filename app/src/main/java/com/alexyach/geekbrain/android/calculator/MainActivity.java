package com.alexyach.geekbrain.android.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Имя настроек
    private static final String NameSharedPreference = "LOGIN";
    private static final String KEY_THEME = "theme";

//    LinearLayout rootLL;

    // Ключ для сохранения/чтения данных из Bundle
    private static final String KEY = "Key";

    // класс для сохранения переменнных
    MathExpression mathExpression;

    // Массив id цифровых клавиш
    private final int[] arrayButtonNumId = new int[]{R.id.btn_0, R.id.btn_1, R.id.btn_2,
            R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
            R.id.btn_9};

    // Массив id математических операций
    private final int[] arrayButtonOperationId = new int[]{R.id.btn_div, R.id.btn_mult,
            R.id.btn_minus, R.id.btn_plus};
    // математические операции
    private final String[] mathOperation = new String[]{" / ", " * ",
            " - ", " + "};

    // Текущие переменные
    private double current1 = Double.MIN_VALUE;
    private double current2 = Double.MIN_VALUE;
    private String operation = "";

    // Boolean- переменная очищения поля ввода после нажатия кнопки равно
    Boolean clearField = false;

    private TextView textStory, textDisplay;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnDot, btnDiv, btnPlus, btnMinus, btnMult, btnEquals, btnClear;

    // Массив кнопок с цифрами
    private Button[] arrayButtonNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Установить тему перед установкой макета
        setTheme(getCurrentTheme());

        setContentView(R.layout.activity_main);

        initView();
    }

    // Сохранение настроек
    public void setCurrentTheme(String theme) {
        SharedPreferences sharedPref =
                getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_THEME, theme);
        editor.apply();

        // Вернулись назад так, чтоб пересоздать MainActivity
        startActivity(new Intent(this, MainActivity.class));

        Log.d("myLogs", "Save: " + theme);
    }

    // Чтение настроек, параметр «тема»
    int getCurrentTheme() {
        SharedPreferences sharedPref =
                getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        String strTheme = sharedPref.getString(KEY_THEME, "light");

        Log.d("myLogs", "Read: " + strTheme);

        if (strTheme.equals("light")) {
            return R.style.MyLightTheme;
        } else {
            return R.style.MyDarkTheme;
        }
    }



    // Обработка нажатий
    @Override
    public void onClick(View view) {

        if (clearField) {
            clearDisplay();
            clearField = false;
        }

        // Цифровые кнопки
        for (int i = 0; i < arrayButtonNumId.length; i++) {
            if (view.getId() == arrayButtonNumId[i]) {
                // Вывели на дисплей
                mainDisplay(String.valueOf(i));
                // Вывели в story
                storyDisplay(String.valueOf(i));
            }
        }

        // Кнопка десятичной точки
        if (view.getId() == R.id.btn_dot) {
            mainDisplay(".");
            storyDisplay(".");
        }

        // математические операции
        for (int i = 0; i < arrayButtonOperationId.length; i++) {
            if (view.getId() == arrayButtonOperationId[i]) {

                // Проверка повторного надатие кнопки мат. операции
                if (!operation.equals("")) {
                    Toast.makeText(this, "Неверные значения, начните заново",
                            Toast.LENGTH_SHORT).show();
                    zeroVariable();
                    return;
                }

                // Вывели в историю
                storyDisplay(mathOperation[i]);

                // Записали первую текущую переменную
                saveCurrentVariable();
                // Очистили дисплей
                clearDisplay();

                // Записали мат. операцию
                operation = mathOperation[i];
            }
        }

        // Кнопка равно
        if (view.getId() == R.id.btn_equals) {
            // Записали вторую текущую переменную
            saveCurrentVariable();
            answer();
        }

        // Кнопка очистки поля ввода
        if (view.getId() == R.id.btn_clear) {
            clearDisplay();
        }

        // Очистка поля story
        if (view.getId() == R.id.text_view_story) {
            textStory.setText("");

        }

        // Переход на StylesActivity
        if (view.getId() == R.id.text_view_display) {

            startActivity(new Intent(this, StylesActivity.class));
        }
    }

    // Обнуление вех текущих переменных
    private void zeroVariable() {
        current1 = Double.MIN_VALUE;
        current2 = Double.MIN_VALUE;
        operation = "";
        storyDisplay(" !!! ");
        clearDisplay();
    }

    // Очистка поля ввода
    private void clearDisplay() {
        textDisplay.setText("");
    }

    // Записали текущую переменную
    private void saveCurrentVariable() {

        String str = textDisplay.getText().toString();

        if (str.isEmpty()) {
            Toast.makeText(this, "Пустое поле, начните заново", Toast.LENGTH_SHORT).show();
            zeroVariable();
            return;
        }

        if (str.equals(".") || str.equals("0.") || str.equals(".0")) {
            Toast.makeText(this, "Не верное выражение, начните заново",
                    Toast.LENGTH_SHORT).show();
            zeroVariable();
            return;
        }

        if (current1 == Double.MIN_VALUE) {
            current1 = Double.parseDouble(str);
        } else {
            current2 = Double.parseDouble(str);
        }
    }

    // Кнопка равно
    private void answer() {

        // Проверка на наличие аргументов
        if (current1 == Double.MIN_VALUE || current2 == Double.MIN_VALUE) {
            Toast.makeText(this, "Введены не все аргументы, начните заново",
                    Toast.LENGTH_SHORT).show();
            zeroVariable();
        }

        switch (operation) {
            case " / ":
                textDisplay.setText(String.format(Locale.UK, "%.2f", (current1 / current2)));
                break;

            case " * ":
                textDisplay.setText(String.format(Locale.UK, "%.2f", (current1 * current2)));
                break;

            case " + ":
                textDisplay.setText(String.format(Locale.UK, "%.2f", (current1 + current2)));
                break;

            case " - ":
                textDisplay.setText(String.format(Locale.UK, "%.2f", (current1 - current2)));
//                Log.d("myLogs", "минус: " + (current1 - current2));
                break;

            default:
                Toast.makeText(this, "Не выбрана операция !", Toast.LENGTH_SHORT).show();
                textDisplay.setText("");
                break;
        }

        // Выводим story
        storyDisplay(" = " + textDisplay.getText().toString() + " ☻ ");

        // Обнуляем текущие переменные
        current1 = Double.MIN_VALUE;
        current2 = Double.MIN_VALUE;
        operation = "";

        // Выставляем Boolean- переменную в true
        clearField = true;
    }


    // Печать на дисплей
    @SuppressLint("SetTextI18n")
    private void mainDisplay(String str) {
        String strDisplay = textDisplay.getText().toString();

        // Проверка на точку
        if (strDisplay.contains(".") && str.equals(".")) {
            Toast.makeText(this, "Хватит десятичных точек ! Начните заново", Toast.LENGTH_SHORT).show();
            zeroVariable();
            return;
        }

        textDisplay.setText(textDisplay.getText().toString() + str);
    }

    // Печать в истоию
    @SuppressLint("SetTextI18n")
    private void storyDisplay(String str) {
        textStory.setText(textStory.getText().toString() + str);
    }

    private void initView() {
//        rootLL = findViewById(R.id.root_linear_layout);

        textStory = findViewById(R.id.text_view_story);
        textDisplay = findViewById(R.id.text_view_display);

        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btnDiv = findViewById(R.id.btn_div);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMult = findViewById(R.id.btn_mult);
        btnDot = findViewById(R.id.btn_dot);
        btnEquals = findViewById(R.id.btn_equals);
        btnClear = findViewById(R.id.btn_clear);

        arrayButtonNum = new Button[]{btn0, btn1, btn2, btn3, btn4, btn5, btn6,
                btn7, btn8, btn9, btnDot};

        initListener();
    }

    private void initListener() {

        for (int i = 0; i < 11; i++) {
            arrayButtonNum[i].setOnClickListener(this);
        }

        btnDiv.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMult.setOnClickListener(this);

        btnEquals.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        textStory.setOnClickListener(this);

        textDisplay.setOnClickListener(this);
    }

    // Сохранение данных
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        mathExpression = new MathExpression(current1, current2, operation,
                textStory.getText().toString(),
                textDisplay.getText().toString());
        savedInstanceState.putParcelable(KEY, mathExpression);
    }

    // Считывание данных
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mathExpression = savedInstanceState.getParcelable(KEY);
        setDisplay(mathExpression);
    }

    // Восстановление данных
    private void setDisplay(MathExpression mathExp) {
        storyDisplay(mathExp.getStory());
        mainDisplay(mathExp.getDisplay());

        current1 = mathExp.getCurrent1();
        current2 = mathExp.getCurrent2();
        operation = mathExp.getOperation();

    }

}