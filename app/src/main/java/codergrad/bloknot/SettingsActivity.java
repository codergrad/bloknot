package codergrad.bloknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.AccessController;

public class SettingsActivity extends AppCompatActivity {
    int cardviewFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_type_);

        TextView textView = (TextView) findViewById(R.id.cardviewTitle);
        Button btnTextSize = (Button) findViewById(R.id.size_text_50);

        btnTextSize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float cardviewFontSize = 40;
                Intent intent = new Intent();
                intent.putExtra("cardviewFontSizeExtra", cardviewFontSize);
                setResult(RESULT_OK, intent);
                finish();
               // textView.setTextSize(40); //значение присваивается в sp (px лучше не использовать)
            }
        });
    }



}

