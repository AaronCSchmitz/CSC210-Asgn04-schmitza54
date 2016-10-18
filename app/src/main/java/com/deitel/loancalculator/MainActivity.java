// Aaron Schmitz
// CSC-210
// Asgn04: Loan Calculator


package com.deitel.loancalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 5000.0;
    private double rate = 0.15;
    private int months = 12;
    private TextView amountTextView;
    private TextView monthsTextView;
    private TextView percentTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTextView = (TextView) findViewById(R.id.amountTextView);
        monthsTextView = (TextView) findViewById(R.id.monthsTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        totalTextView.setText(currencyFormat.format(0));

        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private void calculate() {
        percentTextView.setText(percentFormat.format(rate));

        double interest = (billAmount/months) * rate;
        double total = (billAmount/months) + interest;

        totalTextView.setText(currencyFormat.format(total));
    }

    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    rate = progress / 100.0;
                    calculate();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };

        private final TextWatcher amountEditTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    billAmount = Double.parseDouble(s.toString()) / 100.0;
                    amountTextView.setText(currencyFormat.format(billAmount));
                } catch (NumberFormatException e) {
                    amountTextView.setText("");
                    billAmount = 0.0;
                }

                try {
                    months = Integer.parseInt(s.toString()) / 100;
                } catch (NumberFormatException e) {
                    monthsTextView.setText(" ");
                    months = 12;
                }

                calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
        };
    }
