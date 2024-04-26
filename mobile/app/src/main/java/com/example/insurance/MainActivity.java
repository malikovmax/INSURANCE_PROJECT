/**
 * @file InsuranceMainActivity.java
 * @brief Содержит определение класса InsuranceMainActivity.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @brief Основная активность для аутентификации пользователей в приложении страхования.
 */
public class InsuranceMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private TextView textViewErrorLogin, textViewSelectAgent, textViewInvalidUser, textViewEmailVerification;
    private ProgressBar progressBar;
    private Button buttonLogin, buttonClient, buttonAgent;
    private RadioButton clientRadio, agentRadio;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_main);

        editTextEmail = findViewById(R.id.loginPageEmailEditText);
        editTextPassword = findViewById(R.id.loginPagePasswordEditText);
        textViewErrorLogin = findViewById(R.id.textViewErrorLogin);
        textViewSelectAgent = findViewById(R.id.textViewSelectRadio);
        textViewInvalidUser = findViewById(R.id.textViewInvalidUser);
        textViewEmailVerification = findViewById(R.id.textViewEmailNotVerified);
        progressBar = findViewById(R.idmainActivityIndeterminateProgressbar);

        clientRadio = findViewById(R.id.radioClient);
        agentRadio = findViewById(R.id.radioAgent);

        buttonLogin = findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(this);

        buttonClient = findViewById(R.id.buttonClient);
        buttonClient.setOnClickListener(this);

        buttonAgent = findViewById(R.id.buttonAgent);
        buttonAgent.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClient:
                startActivity(new Intent(this, RegisterClient.class));
                break;

            case R.id.buttonAgent:
                startActivity(new Intent(this, RegisterAgent.class));
                break;

            case R.id.login_button:
                textViewInvalidUser.setVisibility(View.GONE);
                textViewErrorLogin.setVisibility(View.GONE);
                textViewSelectAgent.setVisibility(View.GONE);
                textViewEmailVerification.setVisibility(View.INVISIBLE);
                loginUser();
                break;

            case R.id.textViewForgotPassword:
                startActivity(new Intent(InsuranceMainActivity.this, ForgotPassword.class));
                break;
        }
    }

    /**
     * @brief Метод для аутентификации пользователя.
     */
    void loginUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Проверка валидности email и пароля

        // Если все в порядке
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean flag = false;

                        if (clientRadio.isChecked()) {
                            // Проверить, является ли пользователь клиентом и обработать вход соответственно
                            flag = true;
                        }

                        if (agentRadio.isChecked()) {
                            // Проверить, является ли пользователь агентом и обработать вход соответственно
                            flag = true;
                        }

                        // Обработать случай, когда не выбраны ни клиент, ни агент
                        if (!flag) {
                            progressBar.setVisibility(View.GONE);
                            textViewEmailVerification.setVisibility(View.INVISIBLE);
                            textViewSelectAgent.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        textViewEmailVerification.setVisibility(View.INVISIBLE);
                        textViewErrorLogin.setVisibility(View.VISIBLE);
                    }
                });
    }
}