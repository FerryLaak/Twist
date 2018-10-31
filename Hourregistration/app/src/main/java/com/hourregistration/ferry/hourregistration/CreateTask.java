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
import java.util.Arrays;
import java.util.List;

public class CreateTask extends AppCompatActivity implements View.OnClickListener {

    private EditText projectName;
    private EditText editTextTaskName;
    private FirebaseFirestore db;

    List<DocumentSnapshot> alltasks;
    ListView listViewProjects;
    List<String> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        projectName = findViewById(R.id.projectName);

        Bundle recdData = getIntent().getExtras();
        String myVal = recdData.getString("com.hourregistration.ferry.hourregistration.MESSAGE");

        projectName.setText(myVal);

        db = FirebaseFirestore.getInstance();
        editTextTaskName = findViewById(R.id.editTextTaskName);

        FirebaseFirestore.getInstance()
                .collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            alltasks = task.getResult().getDocuments();
                        }
                        for(int i = 0; i < alltasks.size(); i++){
                            tasks.add(alltasks.get(i).getString("taskName"));
                        }

                    };

                });

        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.listViewProjects);
        final Button btn = (Button) findViewById(R.id.createTask2);

        // Initializing a new String Array
        String[] fruits = new String[] {
        };

        // Create a List from String Array elements
        final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);



        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruits_list.addAll(tasks);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        final Button create  = (Button) findViewById(R.id.createTask);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruits_list.add(editTextTaskName.getText().toString());
                arrayAdapter.notifyDataSetChanged();

                String name = editTextTaskName.getText().toString().trim();
                String project = projectName.getText().toString().trim();
                if (!validateInputs(name)) {
                    CollectionReference dbProducts = db.collection("tasks");
                    com.hourregistration.ferry.hourregistration.Task task = new com.hourregistration.ferry.hourregistration.Task(name, project, name
                    );
                    dbProducts.add(task)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(CreateTask.this, "Task Added", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateTask.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

    }

    private boolean validateInputs(String name) {
        if (name.isEmpty()) {
            editTextTaskName.setError("Name required");
            editTextTaskName.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
