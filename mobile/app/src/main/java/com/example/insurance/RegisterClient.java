/**
 * @file RegisterInsuranceClient.java
 * @brief Содержит определение класса RegisterInsuranceClient.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @brief Активность для регистрации страховых клиентов.
 */
public class RegisterInsuranceClient extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextMobile, editTextAddress, editTextCoverageInfo;
    private ProgressBar progressBar;
    private Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_insurance_client);

        editTextFirstName = findViewById(R.id.firstName_client);
        editTextLastName = findViewById(R.id.lastName_client);
        editTextEmail = findViewById(R.id.email_client);
        editTextPassword = findViewById(R.id.password_client);
        editTextAddress = findViewById(R.id.address_client);
        editTextMobile = findViewById(R.id.mobile_client);
        editTextCoverageInfo = findViewById(R.id.coverage_info_client);

        progressBar = findViewById(R.idregisterClientIndeterminateProgressbar);
        register = findViewById(R.id.buttonRegisterClient);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> registerClient());
    }

    /**
     * @brief Метод для регистрации клиента.
     */
    void registerClient() {
        List<String> coverageInfoList = new ArrayList<>();

        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String coverageInfo = editTextCoverageInfo.getText().toString().trim();

        // Проверка данных на валидность

        if (firstName.isEmpty()) {
            editTextFirstName.setError("Поле имени обязательно!");
            editTextFirstName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Поле email обязательно!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Введите корректный e-mail адрес!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Поле пароля обязательно!");
            editTextPassword.requestFocus();
            return;
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            editTextMobile.setError("Поле телефона обязательно и должно содержать 10 цифр!");
            editTextMobile.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Поле адреса обязательно!");
            editTextAddress.requestFocus();
            return;
        }

        if (coverageInfo.isEmpty()) {
            editTextCoverageInfo.setText("");
        } else {
            String[] coverageInfoArray = coverageInfo.split(",");

            for (String info : coverageInfoArray) {
                coverageInfoList.add(info.trim());
            }
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                        InsuranceClient client = new InsuranceClient(uid, firstName, lastName, email, password, mobile, address, coverageInfoList);

                        FirebaseDatabase.getInstance().getReferenceFromUrl("https://insurance-agency.firebaseio.com/Clients/")
                                .child(uid)
                                .setValue(client)
                                .addOnCompleteListener(task1 -> {
                                    progressBar.setVisibility(View.GONE);
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterInsuranceClient.this, "Успешная регистрация клиента", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterInsuranceClient.this, "Что-то пошло не так! Попробуйте снова позже!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterInsuranceClient.this, "Что-то пошло не так! Попробуйте снова позже!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}