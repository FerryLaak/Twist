package com.hourregistration.ferry.hourregistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.*;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerActivity extends AppCompatActivity{

    private FirebaseFirestore db;

    List<DocumentSnapshot> allprojects;
    List<String> projecten = new ArrayList<>();

    public final static String EXTRA_MESSAGE = "com.hourregistration.ferry.hourregistration.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        db = FirebaseFirestore.getInstance();

        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.listViewProjects);

        // Create a List from String Array elements
        final List<String> fruits_list = new ArrayList<String>();

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);

        FirebaseFirestore.getInstance()
                .collection("projects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            allprojects = task.getResult().getDocuments();
                        }
                        for(int i = 0; i < allprojects.size(); i++){
                            projecten.add(allprojects.get(i).getString("projectName"));
                        }
                        fruits_list.addAll(projecten);
                    };

                });


        final Button projectsnotload  = (Button) findViewById(R.id.projectsnotload);

        projectsnotload.setOnClickListener(new View.OnClickListener() {
                                               // Create a List from String Array elements
                                               final List<String> fruits_list = new ArrayList<String>();
                                               @Override
                                               public void onClick(View v) {
                                                   FirebaseFirestore.getInstance()
                                                           .collection("projects")
                                                           .get()
                                                           .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                   if (task.isSuccessful()) {
                                                                       allprojects = task.getResult().getDocuments();
                                                                   }
                                                                   for(int i = 0; i < allprojects.size(); i++){
                                                                       projecten.add(allprojects.get(i).getString("projectName"));
                                                                   }
                                                                   fruits_list.addAll(projecten);
                                                               };
                                                           });

                                                   final ListView lv = (ListView) findViewById(R.id.listViewProjects);


                                                   // DataBind ListView with items from ArrayAdapter
                                                   lv.setAdapter(arrayAdapter);

                                               }
                                           }
        );



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(TimerActivity.this, ProfileActivity.class);

                String message = lv.getItemAtPosition(position).toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });



    }


}
