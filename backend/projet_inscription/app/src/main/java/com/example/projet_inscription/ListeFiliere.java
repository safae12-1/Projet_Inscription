package com.example.projet_inscription;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projet_inscription.AddFiliere;
import com.example.projet_inscription.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListeFiliere extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout filiereContainer;
    private RequestQueue requestQueue;
    private String filieresUrl = "http://10.0.2.2:8087/api/v1/filieres";
    private Button fileaddfiliere;
    private Button fileacc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filiere_list);

        //button retour a l'accueil
        fileacc = (Button) findViewById(R.id.fileacc);
        fileacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListeFiliere.this, MainActivity.class);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        // Effectuer une requête GET pour récupérer la liste des filières
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, filieresUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        displayFiliereList(response); // Utilisez la méthode pour afficher les filières
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Erreur", error.toString());
                        Toast.makeText(ListeFiliere.this, "Erreur lors de la récupération des filières", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }


    private void displayFiliereList(JSONArray response) {//knparcouriw hdik response bch n9dro naffichiw liste des filieres avec les buttons supp et modifier
        fileaddfiliere = (Button) findViewById(R.id.fileaddfiliere);
        fileaddfiliere.setOnClickListener(this);
        filiereContainer = findViewById(R.id.filiereContainer);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject filiereJson = response.getJSONObject(i);
                                String code = filiereJson.getString("code");
                                String libelle = filiereJson.getString("libelle");

                                LinearLayout filiereItemLayout = new LinearLayout(ListeFiliere.this);
                                filiereItemLayout.setOrientation(LinearLayout.HORIZONTAL);

                                TextView codeTextView = new TextView(ListeFiliere.this);
                                codeTextView.setText(code);

                                TextView libelleTextView = new TextView(ListeFiliere.this);
                                libelleTextView.setText(libelle);

                                Button editButton = new Button(ListeFiliere.this);
                                editButton.setText("Modifier");
                                final int finalI = i;
                                editButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showEditDialog(filiereJson, finalI);
                                    }
                                });

                                Button deleteButton = new Button(ListeFiliere.this);
                                deleteButton.setText("Supprimer");
                                deleteButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDeleteDialog(filiereJson);
                                    }
                                });

                                editButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                deleteButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                codeTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                libelleTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                //codeTextView.setGravity(Gravity.CENTER);
                                codeTextView.setPadding(70,0,0,0);
                                //libelleTextView.setGravity(Gravity.CENTER);
                                editButton.setGravity(Gravity.CENTER);
                                deleteButton.setGravity(Gravity.CENTER);

                                filiereItemLayout.addView(codeTextView);
                                filiereItemLayout.addView(libelleTextView);
                                filiereItemLayout.addView(editButton);
                                filiereItemLayout.addView(deleteButton);

                                filiereContainer.addView(filiereItemLayout);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
    }





    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ListeFiliere.this, AddFiliere.class);
        startActivity(intent);
    }

    private void showEditDialog(final JSONObject filiereJson, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier la filière");

        // Créez un LinearLayout pour contenir les deux champs
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        final EditText codeEditText = new EditText(this);
        codeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        codeEditText.setHint("Code");//placeholder
        final EditText libelleEditText = new EditText(this);
        libelleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        libelleEditText.setHint("Libellé");//placeholder

        try {
            codeEditText.setText(filiereJson.getString("code")); //input ghn7tto fihom les valeur li déja kinin
            libelleEditText.setText(filiereJson.getString("libelle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        container.addView(codeEditText);//ghdri n ajoutiwhom f linearlayout
        container.addView(libelleEditText);
        builder.setView(container);//ghn2ajoutiw linear layout f popup
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCode = codeEditText.getText().toString();
                String newLibelle = libelleEditText.getText().toString();

                updateFiliere(filiereJson, newCode, newLibelle, position);//kn3yto 3la hd fct hiya lighdi tkhod code et libelle avec position=id liktb utilisteur o dir requete bch itmodifiya
                Toast.makeText(ListeFiliere.this, "Filière a été modifié avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(ListeFiliere.this, ListeFiliere.class);//une fois nwrko 3la ok tactualisa la page
                startActivity(refreshIntent);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateFiliere(JSONObject filiereJson, String newCode, String newLibelle, int position) {
        int filiereId = filiereJson.optInt("id");
        String updateFiliereUrl = "http://10.0.2.2:8087/api/v1/filieres/" + filiereId;
        JSONObject updatedFiliereData = new JSONObject();
        try {
            updatedFiliereData.put("code", newCode);
            updatedFiliereData.put("libelle", newLibelle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, updateFiliereUrl, updatedFiliereData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ListeFiliere.this, "Filière mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Erreur", error.toString());
                    }
                });

        requestQueue.add(putRequest);
    }


    private void   showDeleteDialog(final JSONObject filiereJson){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//popup smitha builder
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer cette filière ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteFiliere(filiereJson);
                Toast.makeText(ListeFiliere.this, "Filière a été supprimé avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(ListeFiliere.this, ListeFiliere.class);//une fois nwrko 3la ok tactualisa la page
                startActivity(refreshIntent);
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    
    private void deleteFiliere(JSONObject filiereJson) {
        int filiereId = filiereJson.optInt("id");
        String deleteFiliereUrl = "http://10.0.2.2:8087/api/v1/filieres/" + filiereId;
        JSONObject deleteFiliereData = new JSONObject();

        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, deleteFiliereUrl, deleteFiliereData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ListeFiliere.this, "Role à été supprimé avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Erreur", error.toString());
                    }
                });

        requestQueue.add(deleteRequest);
    }

}
