package com.example.cobafirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TANAH = "tanah";
    private static final String KEY_DESA = "desa";
    private static final String KEY_TANAMAN = "tanaman";

    private EditText editTextTanah;
    private EditText editTextDesa;
    private EditText editTextTanaman;
    private EditText editTextInputDesa;
    private TextView textViewData;

    //SetUP Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coba = db.collection("tanahSleman");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTanah = findViewById(R.id.edit_text_tanah);
        editTextDesa = findViewById(R.id.edit_text_desa);
        editTextTanaman = findViewById(R.id.edit_text_tanaman);
        editTextInputDesa = findViewById(R.id.edit_text_inputdesa);
        textViewData = findViewById(R.id.text_view_data);


    }

    //fungsi dinggo nyimpen desa dan tanah ke firestore
    public void saveNote(View v){
        String tanah = editTextTanah.getText().toString();
        String desa = editTextDesa.getText().toString();
        String tanaman = editTextTanaman.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TANAH, tanah);
        note.put(KEY_DESA, desa);
        note.put(KEY_TANAMAN, tanaman);

        coba.add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error !", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNote(View v) {
        String dataDesa = editTextInputDesa.getText().toString();
        coba.whereEqualTo("desa", dataDesa).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snap : queryDocumentSnapshots){
                            String tanah = snap.getString(KEY_TANAH);
                            String desa = snap.getString(KEY_DESA);
                            String tanaman = snap.getString("tanaman");

                            textViewData.setText("Desa: " + desa + "\n" + "Tanah :" + tanah + "\n" +"Tanaman: " +tanaman);

                            Log.d(TAG, snap.getId() + " => " + snap.getData());
                        }
                    }
                });







    }
}