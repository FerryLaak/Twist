package com.hourregistration.ferry.hourregistration;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends AppCompatActivity{

    private EditText editTextProjectName;
    private FirebaseFirestore db;

    List<DocumentSnapshot> allprojects;
    List<String> projecten = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        db = FirebaseFirestore.getInstance();
        editTextProjectName = findViewById(R.id.editTextProjectName);

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

                                                   // Create a List from String Array elements
                                                   final List<String> fruits_list = new ArrayList<String>();


                                                   // DataBind ListView with items from ArrayAdapter
                                                   lv.setAdapter(arrayAdapter);

                                               }
                                           }
        );


        final Button create  = (Button) findViewById(R.id.createProject);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruits_list.add(editTextProjectName.getText().toString());
                arrayAdapter.notifyDataSetChanged();

                String name = editTextProjectName.getText().toString().trim();

                if (!validateInputs(name)) {
                    CollectionReference dbProducts = db.collection("projects");
                    Project project = new Project(name, name, 0.00
                    );
                    dbProducts.add(project)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(ProjectActivity.this, "Project Added", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProjectActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    private boolean validateInputs(String name) {
        if (name.isEmpty()) {
            editTextProjectName.setError("Name required");
            editTextProjectName.requestFocus();
            return true;
        }
        return false;
    }


}
