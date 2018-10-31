package com.hourregistration.ferry.hourregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.linearlayout1).setOnClickListener(this);
        findViewById(R.id.linearlayout2).setOnClickListener(this);
        findViewById(R.id.linearlayout3).setOnClickListener(this);
        findViewById(R.id.linearlayout5).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearlayout1:
                startActivity(new Intent(this, TimerActivity.class));
                break;
            case R.id.linearlayout2:
                startActivity(new Intent(this, TaskActivity.class));
                break;
            case R.id.linearlayout5:
                startActivity(new Intent(this, OverviewActivity.class));
                break;
            case R.id.linearlayout3:
                startActivity(new Intent(this, ProjectActivity.class));
                break;
        }
    }

}
