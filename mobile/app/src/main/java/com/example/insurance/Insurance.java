/**
 * @file InsurancePolicyApplication.java
 * @brief Содержит определение класса InsurancePolicyApplication.
 */

package com.example.insurance_app;

import java.util.ArrayList;

/**
 * @brief Представляет заявку на страхование.
 */
public class InsurancePolicyApplication {
    private String clientId; ///< Идентификатор клиента.
    private String agentId; ///< Идентификатор агента.
    private String policyType; ///< Тип полиса.
    private String startDate; ///< Дата начала полиса.
    private String endDate; ///< Дата окончания полиса.
    private ArrayList<String> conditions = new ArrayList<>(); ///< Список условий полиса.

    /**
     * @brief Получает список условий полиса.
     * @return Список условий.
     */
    public ArrayList<String> getConditions() {
        return this.conditions;
    }

    /**
     * @brief Устанавливает условия для полиса.
     * @param conditions Условия для установки.
     */
    public void setConditions(String conditions) {
        String[] conditionArray = conditions.split(",");

        for (int i = 0; i < conditionArray.length; i++) {
            this.conditions.add(conditionArray[i].trim());
        }
    }

    /**
     * @brief Получает идентификатор клиента.
     * @return Идентификатор клиента.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @brief Устанавливает идентификатор клиента.
     * @param clientId Идентификатор клиента для установки.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @brief Получает идентификатор агента.
     * @return Идентификатор агента.
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * @brief Устанавливает идентификатор агента.
     * @param agentId Идентификатор агента для установки.
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * @brief Получает тип полиса.
     * @return Тип полиса.
     */
    public String getPolicyType() {
        return policyType;
    }

    /**
     * @brief Устанавливает тип полиса.
     * @param policyType Тип полиса для установки.
     */
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    /**
     * @brief Получает дату начала полиса.
     * @return Дата начала полиса.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @brief Устанавливает дату начала полиса.
     * @param startDate Дата начала полиса для установки.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @brief Получает дату окончания полиса.
     * @return Дата окончания полиса.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @brief Устанавливает дату окончания полиса.
     * @param endDate Дата окончания полиса для установки.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @brief Конструктор класса InsurancePolicyApplication.
     * @param clientId Идентификатор клиента.
     * @param agentId Идентификатор агента.
     * @param policyType Тип полиса.
     * @param startDate Дата начала полиса.
     * @param endDate Дата окончания полиса.
     * @param conditions Условия для полиса.
     */
    public InsurancePolicyApplication(String clientId, String agentId, String policyType, String startDate, String endDate, String conditions) {
        this.clientId = clientId;
        this.agentId = agentId;
        this.policyType = policyType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.setConditions(conditions);
    }
}