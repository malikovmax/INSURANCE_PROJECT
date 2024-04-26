/**
 * @file ViewInsurancePoliciesList.java
 * @brief Содержит определение класса ViewInsurancePoliciesList.
 */

package com.example.insurance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @brief Активность для просмотра списка страховых полисов.
 */
public class ViewInsurancePoliciesList extends AppCompatActivity {
    CustomAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<InsurancePolicy> policyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_insurance_policies_list);

        recyclerView = findViewById(R.id.recyclerView);

        // Получаем данные:
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://insurance-agency.firebaseio.com");

        reference.child("InsurancePolicies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    InsurancePolicy policy = data.getValue(InsurancePolicy.class);
                    if (!policyList.contains(policy)) {
                        policyList.add(policy);
                    }
                }
                if (policyList.isEmpty()) {
                    Toast.makeText(ViewInsurancePoliciesList.this, "Извините, доступных полисов страхования не найдено.", Toast.LENGTH_LONG).show();
                } else {
                    adapter = new CustomAdapter(policyList, ViewInsurancePoliciesList.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewInsurancePoliciesList.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewInsurancePoliciesList.this, "Ошибка при чтении данных: " + error.getCode(), Toast.LENGTH_LONG).show();
            }
        });
    }
}