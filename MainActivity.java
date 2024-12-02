package com.example.apiexampleandroidstudio;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button button;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        button = findViewById(R.id.submitButton);
        outputTextView = findViewById(R.id.outputTextView);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!username.isEmpty() && !password.isEmpty())
                {
                    new LoginTask().execute(username, password);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Proszę wypełnić wszystkie pola.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params)
        {
            try
            {
                String username = params[0];
                String password = params[1];

                String baseUrl = "";
                String urlString = baseUrl + "user/get/" + username + "/" + password;
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            }
            catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if (result)
            {
                outputTextView.setText("Zalogowano");
                outputTextView.setTextColor(Color.GREEN);
            }
            else
            {
                outputTextView.setText("Coś poszło nie tak");
                outputTextView.setTextColor(Color.RED);
            }
        }
    }
}