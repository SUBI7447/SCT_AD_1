package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeButtons();
    }

    private void initializeButtons() {
        int[] numberButtons = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9};
        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(this::onNumberButtonClick);
        }

        int[] operatorButtons = {R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide};
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(this::onOperatorButtonClick);
        }

        findViewById(R.id.btn_equals).setOnClickListener(v -> onEqualsButtonClick());
        findViewById(R.id.btn_clear).setOnClickListener(v -> onClearButtonClick());
        findViewById(R.id.btn_delete).setOnClickListener(v -> onDeleteButtonClick());
        findViewById(R.id.btn_decimal).setOnClickListener(v -> onDecimalButtonClick());
    }

    private void onNumberButtonClick(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        resultTextView.setText(currentInput);
    }

    private void onOperatorButtonClick(View view) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            currentInput = "";
        }
        Button button = (Button) view;
        operator = button.getText().toString();
    }

    private void onEqualsButtonClick() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }

            resultTextView.setText(String.valueOf(result));
            currentInput = "";
            operator = "";
            firstOperand = 0;
        }
    }

    private void onClearButtonClick() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        resultTextView.setText("");
    }

    private void onDeleteButtonClick() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            resultTextView.setText(currentInput);
        }
    }

    private void onDecimalButtonClick() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            resultTextView.setText(currentInput);
        }
    }
}
