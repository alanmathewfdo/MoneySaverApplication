package com.mathew.moneysever.features.signup;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.User;

public class SignUpModel {

    public User signUp(String name, String email, String password) {
        if (MoneyServerDB.findUserByEmail(email) != null) {
            return null;
        }
        return MoneyServerDB.addUser(name, email, password);
    }
}
