package com.mathew.moneysever.features.signup;

import com.mathew.moneysever.data.dto.User;
import com.mathew.moneysever.util.ConsoleInput;

public class SignUpView {

    private final SignUpModel model = new SignUpModel();

    public User show() {
        System.out.println();
        System.out.println("-- Sign Up --");
        String name = ConsoleInput.readLine("Name: ");
        String email = ConsoleInput.readLine("Email: ");
        String password = ConsoleInput.readLine("Password: ");

        User user = model.signUp(name, email, password);
        if (user == null) {
            System.out.println("This email already exists. Please sign in.");
            return null;
        }
        System.out.println("Account created.");
        return user;
    }
}
