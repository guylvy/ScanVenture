package org.tensorflow.demo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Account implements Serializable {
    private long level;
    private String username , id ;

    public String getId() {
        return id;
    }

    public long getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }
    public Account(String username){
        this.username = username;
    }
    public Account(String id, String username, long level) {
        this.id = id;
        this.username = username;
        this.level = level;
    }

    public void incLevel(){
        DatabaseReference usersRef = Funcs.DB.child("users");
        usersRef.child(this.id).child("level").setValue(++this.level);
    }
}
