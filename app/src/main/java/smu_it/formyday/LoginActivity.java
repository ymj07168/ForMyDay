package smu_it.formyday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText idText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.et_id);

        loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("mine", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Name", idText.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, DailyActivity.class);
                //String nameStr = idText.getText().toString();
                //intent.putExtra("userName", nameStr);
                startActivity(intent);
                finish();
            }
        });
    }
}