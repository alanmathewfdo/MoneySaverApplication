package com.mathew.moneysever.features.signin;

import com.mathew.moneysever.data.dto.User;
import com.mathew.moneysever.util.ConsoleInput;

public class SignInView {

    private final SignInModel model = new SignInModel();

    public User show() {
        System.out.println();
        System.out.println("-- Sign In --");
        String email = ConsoleInput.readLine("Email: ");
        String password = ConsoleInput.readLine("Password: ");
        User user = model.signIn(email, password);
        if (user == null) {
            System.out.println("Invalid email or password.");
        }
        return user;
    }
}
