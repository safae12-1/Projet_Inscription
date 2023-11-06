package com.example.projet_inscription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {
    private Button bnGfiliere;
    private Button bnGrole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnGfiliere = (Button) findViewById(R.id.bnGfiliere);
        bnGfiliere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListeFiliere.class);
                startActivity(intent);
            }
        });

        bnGrole = (Button) findViewById(R.id.bnGrole);
        bnGrole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListeRole.class);
                startActivity(intent);
            }
        });


    }


}