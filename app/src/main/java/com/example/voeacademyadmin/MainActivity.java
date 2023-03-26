package com.example.voeacademyadmin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore voe_db_firestore = FirebaseFirestore.getInstance();
    CollectionReference transectionRef;
    private TransectionRecyclerViewAdapter transectionRecyclerViewAdapter;
    private RecyclerView transectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voe_db_firestore = FirebaseFirestore.getInstance();
        transectionRef = voe_db_firestore.collection("ClassBooking");

        transectionView= findViewById(R.id.transection_recyclerView);
        transectionView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        transectionView.setHasFixedSize(true);

        fetchnotifications(transectionView);

    }

    public void fetchnotifications( RecyclerView transectionView) {

        ArrayList<TransectionModal> transections= new ArrayList<>();

        transectionRef = voe_db_firestore.collection("ClassBooking");
        transectionRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                transections.add(new TransectionModal( document.getId(), (String) document.get("userName"),(String)document.get("userMoblileNo"),(String)document.get("userEmail"),(String)document.get("userID"),"Teacher Name : "+document.get("teacherName"),"Classes Booked : "+document.get("no_of_classes"),"Amount paid : "+document.get("amount"),"Payment ID : "+document.get("paymentID"),"Subject : "+document.get("subject"),"Class : "+document.get("selectedClass"), "Checkout Time : "+document.get("timeStamp") , (Boolean) document.get("isClassesCompleted")));

                            }

                            transectionRecyclerViewAdapter = new TransectionRecyclerViewAdapter(getApplicationContext(),transections);
                            transectionView.setAdapter(transectionRecyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Something Bad Happened...", task.getException());
                        }
                    }
                });

    }


}