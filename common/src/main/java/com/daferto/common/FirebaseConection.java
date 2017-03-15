package com.daferto.common;

import android.util.Log;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class FirebaseConection {

    private static FirebaseConection instance;

    private DatabaseReference mDatabase;
    Map<String, Entry> entryMap;

    private FirebaseConection(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Errors");

        //entryList = new ArrayList<Entry>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Entry>> t=new GenericTypeIndicator<Map<String, Entry>>(){};
                entryMap=dataSnapshot.getValue(t);
                Log.e("FBCon","Inicializado");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FBCon","Cancelado");
            }
        });
    }

    public static FirebaseConection getInstance(){
        if(instance==null)
           instance = new FirebaseConection();
        return instance;
    }

    public boolean changeStatus(String location, int state){
        if(entryMap==null)
            return false;
        for (Map.Entry<String,Entry> e : entryMap.entrySet()){
            Log.e("FBCon",e.getValue().toString());
            if(e.getValue().getLocation().equals(location)){
                mDatabase.child(e.getKey()).child("state").setValue(state);
                return true;
            }
        }
        return false;
    }

    public boolean addError(String location, String error, int prob){
        Entry e = new Entry();
        e.setLocation(location);
        e.setError(error);
        e.setState(prob);
        mDatabase.push().setValue(e);
        return true;
    }



}
