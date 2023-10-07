package uom.sanduni.colourpicker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRed, editTextGreen, editTextBlue;
    private Button buttonGenerate;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRed = findViewById(R.id.editTextRed);
        editTextGreen = findViewById(R.id.editTextGreen);
        editTextBlue = findViewById(R.id.editTextBlue);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        linearLayout = findViewById(R.id.linearLayout);


        addTextChangeListeners();

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateColor();
            }
        });
    }

    private void addTextChangeListeners() {
        editTextRed.addTextChangedListener(new RGBTextWatcher(editTextRed));
        editTextGreen.addTextChangedListener(new RGBTextWatcher(editTextGreen));
        editTextBlue.addTextChangedListener(new RGBTextWatcher(editTextBlue));
    }

    private void generateColor() {
        String redText = editTextRed.getText().toString().trim();
        String greenText = editTextGreen.getText().toString().trim();
        String blueText = editTextBlue.getText().toString().trim();
        
        if (redText.isEmpty() || greenText.isEmpty() || blueText.isEmpty()) {
            showToast("Please fill in all RGB values.");
            return;
        }

        try {
            int red = Integer.parseInt(redText);
            int green = Integer.parseInt(greenText);
            int blue = Integer.parseInt(blueText);

            if (isValidRGBValue(red) && isValidRGBValue(green) && isValidRGBValue(blue)) {
                int color = Color.argb(255,red, green, blue);
                linearLayout.setBackgroundColor(color);
            } else {
                showToast("Invalid RGB values. Please enter values between 0 and 255.");
            }
        } catch (NumberFormatException e) {
            showToast("Please enter valid integer values for RGB.");
        }
    }


    private boolean isValidRGBValue(int value) {
        return value >= 0 && value <= 255;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class RGBTextWatcher implements TextWatcher {
        private EditText editText;

        RGBTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                int value = Integer.parseInt(s.toString());
                if (value < 0 || value > 255) {
                    editText.setError("Enter a value between 0 and 255");
                } else {
                    editText.setError(null);
                }
            } catch (NumberFormatException e) {
                editText.setError("Invalid input");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
