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

public class AddFiliere extends AppCompatActivity implements View.OnClickListener {
    private EditText code;
    private EditText libelle;
    private Button bnAdd;
    private Button bnretourf;
    RequestQueue requestQueue;
    String insertUrl="http://10.0.2.2:8087/api/v1/filieres";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filiere);
        code = (EditText) findViewById(R.id.code);
        libelle = (EditText) findViewById(R.id.libelle);
        bnAdd = (Button) findViewById(R.id.bnAdd);

        bnAdd.setOnClickListener(this);//mettre un listener==>this: fait appel à l'instance actuelle de la fonction AddFiliere ==>la méthode onClick de la classe MainActivity sera appelée.

        bnretourf = (Button) findViewById(R.id.bnretourf);
        bnretourf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFiliere.this, ListeFiliere.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        JSONObject jsonBody = new JSONObject(); //objet JSON est utilisé pour stocker des données sous forme de paires clé-valeur
        try {
            jsonBody.put("code", code.getText().toString() );
            jsonBody.put("libelle", libelle.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext()); //file d'attente de requêtes réseau, initialisée avec biblio Volley==>utilisé pour effectuer des requêtes HTTP

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() { //si requete est reussie on fait appel au methode onResponse
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
                Toast.makeText(AddFiliere.this, "Filière a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(AddFiliere.this, ListeFiliere.class);//retourner a la page de liste des filiere
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
