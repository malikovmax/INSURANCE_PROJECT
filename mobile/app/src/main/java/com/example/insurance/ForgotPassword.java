/**
 * @file ForgotPasswordActivity.java
 * @brief Содержит определение класса ForgotPasswordActivity.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @brief Активность для сброса пароля пользователя.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText; ///< Поле для ввода email.
    private Button resetPasswordButton; ///< Кнопка для сброса пароля.
    private FirebaseAuth mAuth; ///< Объект FirebaseAuth для аутентификации.
    private TextView messageTextView; ///< TextView для вывода сообщений.
    private ProgressBar progressBar; ///< Прогрессбар.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email);
        resetPasswordButton = findViewById(R.id.buttonResetPassword);
        messageTextView = findViewById(R.id.resetPassword);
        progressBar = findViewById(R.id.forgotPasswordIndeterminateProgressbar);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    /**
     * @brief Метод для сброса пароля.
     */
    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Поле email обязательно!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setText("");
            emailEditText.setError("Введите корректный e-mail адрес!");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            messageTextView.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Что-то пошло не так! Попробуйте снова позже!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}