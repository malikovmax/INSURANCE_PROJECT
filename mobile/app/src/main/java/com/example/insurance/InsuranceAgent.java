/**
 * @file InsuranceAgent.java
 * @brief Содержит определение класса InsuranceAgent.
 */

package com.example.insurance_app;

/**
 * @brief Представляет страхового агента.
 */
public class InsuranceAgent extends Person {
    private int agentCommission; ///< Комиссия агента.
    private String department; ///< Отделение агента.
    private String region; ///< Регион агента.

    /**
     * @brief Конструктор по умолчанию.
     */
    InsuranceAgent() {
    }

    /**
     * @brief Создает объект InsuranceAgent с указанными данными.
     * @param id Идентификатор агента.
     * @param firstName Имя агента.
     * @param lastName Фамилия агента.
     * @param emailAddress Адрес электронной почты агента.
     * @param password Пароль агента.
     * @param mobile Номер мобильного телефона агента.
     * @param region Регион агента.
     */
    InsuranceAgent(String id, String firstName, String lastName, String emailAddress, String password, String mobile, String address, String agentCommission, String department, String region) {
        this.setId(id);
        this.setUsertype(Macros.INSURANCE_AGENT);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.mobileNumber = mobile;
        this.address = address;
        this.agentCommission = Integer.valueOf(agentCommission);
        this.department = department;
        this.region = region;
    }

    public int getAgentCommission() {
        return agentCommission;
    }

    public void setAgentCommission(int agentCommission) {
        this.agentCommission = agentCommission;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @brief Получает регион агента.
     * @return Регион агента.
     */
    public String getRegion() {
        return region;
    }

    /**
     * @brief Устанавливает регион агента.
     * @param region Регион агента.
     */
    public void setRegion(String region) {
        this.region = region;
    }
}