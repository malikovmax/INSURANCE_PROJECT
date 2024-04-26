/**
 * @file InsuranceClient.java
 * @brief Содержит определение класса InsuranceClient.
 */

package com.example.insurance_app;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Представляет клиента страховой компании.
 */
public class InsuranceClient extends Person {

    private List<String> medicalHistory; ///< Список медицинской истории.

    /**
     * @brief Конструктор по умолчанию.
     */
    InsuranceClient() {}

    /**
     * @brief Создает объект InsuranceClient с указанными данными.
     * @param id Идентификатор клиента.
     * @param firstName Имя клиента.
     * @param lastName Фамилия клиента.
     * @param emailAddress Адрес электронной почты клиента.
     * @param password Пароль клиента.
     * @param mobile Номер мобильного телефона клиента.
     * @param address Адрес клиента.
     */
    InsuranceClient(String id, String firstName, String lastName, String emailAddress, String password, String mobile, String address, List<String> medicalHistory) {
        this.setId(id);
        this.setUsertype(Macros.CLIENT);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmailAddress(emailAddress);
        this.setPassword(password);
        this.setMobileNumber(mobile);
        this.setAddress(address);
        this.setMedicalHistory(medicalHistory);
    }

    /**
     * @brief Получает страховую историю клиента.
     * @return Страховая история клиента.
     */
    public List<String> getInsuranceHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}