package com.namkit.namki.novafolio.Signup;

import java.io.Serializable;

/**
 * Created by namki on 2018-02-25.
 */

public class SignupData implements Serializable {
    public String name;
    public String age;

    public SignupData(){ }

    public SignupData(String name, String age){
        this.name = name;
        this.age = age;
    }
}


