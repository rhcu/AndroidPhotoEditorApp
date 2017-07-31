package com.example.photoeditor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {
    String username;
    EditText etUsername;
    EditText etNewPassword;
    EditText etPasswordCheck;
    Button bChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        etUsername = (EditText)findViewById(R.id.usernameChange);
        etNewPassword =(EditText)findViewById(R.id.newPassword);
        etPasswordCheck = (EditText)findViewById(R.id.repeatPassword);
        bChange = (Button)findViewById(R.id.change);

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = etUsername.getText().toString();
                final String newPW = etNewPassword.getText().toString();
                final String newPWch = etPasswordCheck.getText().toString();


                if (!newPW.equals(newPWch) || !user.equals(username)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this,
                            R.style.AlertDialogCustom);
                    builder.setTitle("Ошибка")
                            .setMessage("Проверьте правильность введённых данных!")
                            .setNegativeButton("ОК", null)
                            .setIcon(R.drawable.ic_error_outline_black_24dp);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this,
                                            R.style.AlertDialogCustom);
                                    builder.setTitle("Поздравляем")
                                            .setMessage("Пароль успешно изменён")
                                            .setNegativeButton("ОК", null)
                                            .setIcon(R.drawable.ic_done_black_24dp);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    etUsername.setText("");
                                    etNewPassword.setText("");
                                    etPasswordCheck.setText("");

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this,
                                            R.style.AlertDialogCustom);
                                    builder.setTitle("Ошибка")
                                            .setMessage("Неизвестная ошибка")
                                            .setNegativeButton("Попробуйте снова", null)
                                            .setIcon(R.drawable.ic_error_outline_black_24dp);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                   ChangePWRequest changePWtRequest = new ChangePWRequest(
                     username, newPW, responseListener);

            RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);

              queue.add(changePWtRequest);
                }
            }
        });
    }
}