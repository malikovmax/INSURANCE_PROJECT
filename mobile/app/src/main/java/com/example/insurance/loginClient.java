/**
 * @file InsurancePolicyLoginClient.java
 * @brief Содержит определение класса InsurancePolicyLoginClient.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Активность для аутентификации клиента в приложении страхования.
 */
public class InsurancePolicyLoginClient extends AppCompatActivity implements View.OnClickListener {

    Button my_profile, view_agents, purchase_policy, view_policy_history, submit_claim_form, calculate_premium, reset_form, sign_out;
    TextView welcome_text, textview_name, textview_email, textview_address, textview_contact, textview_policy_type;
    AutoCompleteTextView textview_agent_name, textview_premium;
    RelativeLayout layout_myprofile, layout_purchase_policy;
    ProgressBar progressBar;
    EditText edittext_start_date, edittext_end_date, edittext_beneficiaries;

    DatabaseReference reference;
    FirebaseUser user;
    String uid, agentId;

    // Список агентов
    ArrayList<String> agent_name = new ArrayList<>();
    ArrayList<InsuranceAgent> agents = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_policy_login_client);

        progressBar = findViewById(R.id.loginActivityIndeterminateProgressbar);

        my_profile = findViewById(R.id.buttonMyProfile);
        view_agents = findViewById(R.id.buttonViewAgents);
        purchase_policy = findViewById(R.id.buttonPurchasePolicy);
        view_policy_history = findViewById(R.id.buttonViewPolicyHistory);
        submit_claim_form = findViewById(R.id.buttonViewSubmitClaimForm);
        reset_form = findViewById(R.id.buttonViewResetClaimForm);
        sign_out = findViewById(R.id.buttonViewSignOut);

        welcome_text = findViewById(R.id.welcome_textView);
        textview_name = findViewById(R.id.textViewName);
        textview_email = findViewById(R.id.textViewEmail);
        textview_address = findViewById(R.id.textViewAddress);
        textview_contact = findViewById(R.id.textViewContact);
        textview_policy_type = findViewById(R.id.textViewPolicyType);

        layout_myprofile = findViewById(R.id.loginClientMyProfileLayout);
        layout_purchase_policy = findViewById(R.id.layoutPurchasePolicy);

        textview_agent_name = findViewById(R.id.loginActivity_textview_agentName);
        textview_premium = findViewById(R.id.loginActivity_textview_PremiumDetail);
        edittext_start_date = findViewById(R.id.loginActivityStartDate);
        edittext_end_date = findViewById(R.id.loginActivityEndDate);
        edittext_beneficiaries = findViewById(R.id.loginActivityBeneficiaries);
        calculate_premium = findViewById(R.id.buttonCalculatePremium);

        my_profile.setOnClickListener(this);
        view_agents.setOnClickListener(this);
        purchase_policy.setOnClickListener(this);
        calculate_premium.setOnClickListener(this);
        submit_claim_form.setOnClickListener(this);
        reset_form.setOnClickListener(this);
        textview_agent_name.setOnClickListener(this);
        sign_out.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);

        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://insurance-agency.firebaseio.com/Clients/");
        user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        uid = user.getUid();

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);

                Client client = snapshot.getValue(Client.class);
                if (client == null) {
                    Toast.makeText(InsurancePolicyLoginClient.this, "Что-то пошло не так! Попробуйте снова позже.", Toast.LENGTH_SHORT).show();
                } else {
                    welcome_text.setText("Добро пожаловать, " + client.getFullName());
                    textview_name.setText(client.getFullName());
                    textview_email.setText(client.getEmailAddress());
                    textview_policy_type.setText("Держатель полиса страхования");
                    textview_contact.setText(client.getPhoneNumber());
                    textview_address.setText(client.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InsurancePolicyLoginClient.this, "Что-то пошло не так! Попробуйте снова позже.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonMyProfile:
            case R.id.buttonViewAgents:
            case R.id.buttonPurchasePolicy:
            case R.id.loginActivity_textview_agentName:
            case R.id.buttonCalculatePremium:
            case R.id.buttonViewSubmitClaimForm:
            case R.id.buttonViewResetClaimForm:
            case R.id.buttonViewSignOut:
                // Реализуйте логику здесь
                break;
        }
    }

    // Другие методы для покупки полиса, подачи заявок на выплаты и т.д.
}