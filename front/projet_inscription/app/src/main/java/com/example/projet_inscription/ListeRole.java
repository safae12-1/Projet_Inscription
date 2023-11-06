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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListeRole extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout roleContainer;
    private RequestQueue requestQueue;
    private String rolesUrl = "http://10.0.2.2:8087/api/roles";
    private Button fileaddrole;
    private Button fileacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_list);

        //button retour a l'accueil
        fileacc = (Button) findViewById(R.id.fileacc);
        fileacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListeRole.this, MainActivity.class);
                startActivity(intent);
            }
        });
        requestQueue = Volley.newRequestQueue(this);

        // Effectuer une requête GET pour récupérer la liste des roles
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, rolesUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        displayRoleList(response); // Utilisez la méthode pour afficher les roles
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Erreur", error.toString());
                        Toast.makeText(ListeRole.this, "Erreur lors de la récupération des roles", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }


    private void displayRoleList(JSONArray response){ //knparcouriw hdik response bch n9dro naffichiw liste des roles avec les buttons supp et modifier

            fileaddrole = (Button) findViewById(R.id.fileaddrole);

            fileaddrole.setOnClickListener(this);
            roleContainer = findViewById(R.id.roleContainer); // Utilisez un LinearLayout vertical pour afficher les roles un apres l'autre

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject roleJson = response.getJSONObject(i);
                                    String name = roleJson.getString("name");

                                    LinearLayout roleItemLayout = new LinearLayout(ListeRole.this);
                                    roleItemLayout.setOrientation(LinearLayout.HORIZONTAL);

                                    TextView roleTextView = new TextView(ListeRole.this);
                                    roleTextView.setText(name);

                                    Button editButton = new Button(ListeRole.this);
                                    editButton.setText("Modifier");
                                    editButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showEditDialog(roleJson);
                                        }
                                    });

                                    Button deleteButton = new Button(ListeRole.this);
                                    deleteButton.setText("Supprimer");
                                    deleteButton.setOnClickListener(new View.OnClickListener() {
                                        // Code pour gérer la suppression du rôle ici
                                        @Override
                                        public void onClick(View view) {
                                            showDeleteDialog(roleJson);
                                        }
                                    });

                                    // Ajoutez des poids aux boutons "Modifier" et "Supprimer" pour les aligner correctement
                                    editButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                    deleteButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                    roleTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                    roleTextView.setGravity(Gravity.CENTER);

                                    roleItemLayout.addView(roleTextView);

                                    roleItemLayout.addView(editButton);
                                    roleItemLayout.addView(deleteButton);
                                    // Ajouter la disposition LinearLayout imbriquée à la disposition principale
                                    roleContainer.addView(roleItemLayout);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
        }

    private void showEditDialog(final JSONObject filiereJson) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//popup smitha builder
        builder.setTitle("Modifier le role");

        final EditText codeEditText = new EditText(this);
        codeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        codeEditText.setHint("Nom");//placeholder

        try {
            codeEditText.setText(filiereJson.getString("name")); //input ghn7tto fihom l valeur li déja kina
        } catch (JSONException e) {
            e.printStackTrace();
        }


        builder.setView(codeEditText);//ghn2ajoutiw edittext lizdna f popup
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = codeEditText.getText().toString();

                updateRole(filiereJson, newName);//kn3yto 3la hd fct hiya lighdi tkhod name avec pisition=id liktb utilisteur o dir requete bch itmodifiya
                Toast.makeText(ListeRole.this, "Role a été modifié avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(ListeRole.this, ListeRole.class);//une fois nwrko 3la ok tactualisa la page
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


    private void updateRole(JSONObject filiereJson, String newName) {
        int roleId = filiereJson.optInt("id");
        String updateFiliereUrl = "http://10.0.2.2:8087/api/roles/" + roleId;
        JSONObject updatedRoleData = new JSONObject();
        try {
            updatedRoleData.put("name", newName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, updateFiliereUrl, updatedRoleData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ListeRole.this, "Role mise à jour avec succès", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ListeRole.this, AddRole.class);
        startActivity(intent);
    }


    private void   showDeleteDialog(final JSONObject roleJson){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//popup smitha builder
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce role ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteRole(roleJson);
                Toast.makeText(ListeRole.this, "Role a été supprimé avec succès", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(ListeRole.this, ListeRole.class);//une fois nwrko 3la ok tactualisa la page
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

    private void deleteRole(JSONObject roleJson) {
        int roleId = roleJson.optInt("id");
        String deleteFiliereUrl = "http://10.0.2.2:8087/api/roles/" + roleId;
        JSONObject deleteRoleData = new JSONObject();

        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, deleteFiliereUrl, deleteRoleData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ListeRole.this, "Role à été supprimé avec succès", Toast.LENGTH_SHORT).show();
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



