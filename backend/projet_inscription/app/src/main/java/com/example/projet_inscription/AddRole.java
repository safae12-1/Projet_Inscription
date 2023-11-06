package com.example.projet_inscription;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddRole extends AppCompatActivity implements View.OnClickListener {
    private EditText name;

    private Button bnAdd;
    private Button bnretour;
    RequestQueue requestQueue;
    String insertUrl="http://10.0.2.2:8087/api/roles";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_role);
        name = (EditText) findViewById(R.id.name);
        bnAdd = (Button) findViewById(R.id.bnAdd);

        bnAdd.setOnClickListener(this);
        bnretour = (Button) findViewById(R.id.bnretour);
        bnretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRole.this, ListeRole.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name.getText().toString() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext()); //file d'attente de requêtes réseau, initialisée avec biblio Volley==>utilisé pour effectuer des requêtes HTTP

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() { //si requete est reussie on fait appel au methode onResponse
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
                Toast.makeText(AddRole.this, "Role a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(AddRole.this, ListeRole.class);//retourner a la page de liste des roles
                startActivity(refreshIntent);
            }
        }, new Response.ErrorListener() { //si requete est échoue on fait appel au methpde onErrorResponse
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);// la requête est ajoutée à la file d'attente de requêtes pour etre executé
    }



}
