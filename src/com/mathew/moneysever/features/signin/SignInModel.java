package com.mathew.moneysever.features.signin;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.User;

public class SignInModel {

    public User signIn(String email, String password) {
        return MoneyServerDB.authenticate(email, password);
    }
}
