/**
 * @file InsurancePolicyAdapter.java
 * @brief Содержит определение класса InsurancePolicyAdapter.
 */

package com.example.insurance_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * @brief Адаптер для отображения списка страховых полисов в RecyclerView.
 */
public class InsurancePolicyAdapter extends RecyclerView.Adapter<InsurancePolicyAdapter.ViewHolder> {

    private ArrayList<InsurancePolicy> policyList; ///< Список страховых полисов.
    private Context context; ///< Контекст приложения.

    /**
     * @brief ViewHolder для отображения элементов списка.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView policyType; ///< Тип страхового полиса.
        TextView startDate; ///< Дата начала действия полиса.
        TextView endDate; ///< Дата окончания действия полиса.

        public ViewHolder(View view) {
            super(view);
            policyType = view.findViewById(R.id.policy_item_textViewType);
            startDate = view.findViewById(R.id.policy_item_textViewStartDate);
            endDate = view.findViewById(R.id.policy_item_textViewEndDate);
        }
    }
    public InsurancePolicyAdapter(ArrayList<InsurancePolicy> policyList, Context context) {
        this.policyList = policyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_insurance_policy, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String type = policyList.get(position).getPolicyType();
        String start = policyList.get(position).getStartDate();
        String end = policyList.get(position).getEndDate();

        holder.policyType.setText(type);
        holder.startDate.setText(start);
        holder.endDate.setText(end);
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }
}