package com.vivek.sampleapp.sharedpreference;

/**
 * Created by vivek-pc on 12/13/2015.
 */
public class MySharedPreferenceObject {

    MySharedPreferenceManager s ;

    public MySharedPreferenceManager getS() {
        return s;
    }

    public void setS(MySharedPreferenceManager s) {
        this.s = s;
    }

    String uid;
    String fname;
    long weight;
    boolean signIn;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        if(s != null) {
            s.putUserId(uid);
        }
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
        if(s != null) {
            s.putFirstName(fname);
        }
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
        if(s != null) {
            s.putWeight(weight);
        }
    }

    public boolean isSignIn() {
        return signIn;
    }

    public void setSignIn(boolean signIn) {
        this.signIn = signIn;
        if(s != null) {
            s.putSignIn(signIn);
        }
    }
}
