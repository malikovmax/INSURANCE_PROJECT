/**
 * @file RegisterInsuranceAgent.java
 * @brief Содержит определение класса RegisterInsuranceAgent.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @brief Активность для регистрации страховых агентов.
 */
public class RegisterInsuranceAgent extends AppCompatActivity {

    private String[] qualifications;
    private String[] specialization;

    private AutoCompleteTextView editTextQualification;
    private AutoCompleteTextView editTextSpecialization;

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextMobile, editTextAddress, editTextCommission;
    private ProgressBar progressBar;
    private Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_insurance_agent);

        qualifications = getResources().getStringArray(R.array.qualifications);
        specialization = getResources().getStringArray(R.array.specializations);

        ArrayAdapter<String> qualificationAdapter = new ArrayAdapter<>(RegisterInsuranceAgent.this, R.layout.dropdown_item, qualifications);
        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<>(RegisterInsuranceAgent.this, R.layout.dropdown_item, specialization);

        editTextQualification = findViewById(R.id.agent_qualification);
        editTextSpecialization = findViewById(R.id.agent_specialization);

        editTextQualification.setAdapter(qualificationAdapter);
        editTextSpecialization.setAdapter(specializationAdapter);

        editTextFirstName = findViewById(R.id.firstName_agent);
        editTextLastName = findViewById(R.id.lastName_agent);
        editTextEmail = findViewById(R.id.email_agent);
        editTextPassword = findViewById(R.id.password_agent);
        editTextAddress = findViewById(R.id.address_agent);
        editTextCommission = findViewById(R.id.agent_commission);
        editTextMobile = findViewById(R.id.mobile_agent);

        progressBar = findViewById(R.id.registerAgentIndeterminateProgressbar);
        register = findViewById(R.id.buttonRegisterAgent);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> registerAgent());
    }

    /**
     * @brief Метод для регистрации агента.
     */
    void registerAgent() {
        // Ввод данных
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String qualification = editTextQualification.getText().toString().trim();
        String specialization = editTextSpecialization.getText().toString().trim();
        String commission = editTextCommission.getText().toString().trim();

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

        // Выполнение других необходимых проверок

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        InsuranceAgent agent = new InsuranceAgent(uid, firstName, lastName, email, password, mobile, address, commission, qualification, specialization);
                        FirebaseDatabase.getInstance().getReferenceFromUrl("https://insurance-agency.firebaseio.com/Agents/")
                                .child(uid)
                                .setValue(agent)
                                .addOnCompleteListener(task1 -> {
                                    progressBar.setVisibility(View.GONE);
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterInsuranceAgent.this, "Успешная регистрация агента", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterInsuranceAgent.this, "Что-то пошло не так! Попробуйте снова позже!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterInsuranceAgent.this, "Что-то пошло не так! Попробуйте снова позже!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}