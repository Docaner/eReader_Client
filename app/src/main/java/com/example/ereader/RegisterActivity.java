package com.example.ereader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText login, password;
    public static  ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public void goToLogin(View v){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Register(View view) {
        String login = this.login.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if(TextUtils.isEmpty(login)) {
            Toast.makeText(this,  "Поле обязательно для заполнения", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,  "Поле обязательно для заполнения", Toast.LENGTH_SHORT).show();
        }
        else {
            СreateUserNow();
        }
    }

    private void СreateUserNow() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Ожидание ответа...");
        dialog.show();

        Call<Users> call = apiInterface.performRegister(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users user = response.body();
                String result = user.getResponse();

                if (result.equals("ok")) {
                    Toast.makeText(RegisterActivity.this,  "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this,MainPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this,  "Данный пользователь уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,  "Что-то пошло не так... Пожалуйста, попробуйте снова", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}