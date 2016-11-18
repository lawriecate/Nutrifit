package com.lawriecate.apps.nutrifit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Preguntas extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.agregarPregunta);
        listView = (ListView)findViewById(R.id.listView);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final DatabaseReference databaseReference =
                FirebaseDatabase
                        .getInstance()
                        .getReferenceFromUrl("https://nutrifitpreguntas.firebaseio.com/NutriFitPreguntas");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageToFirebase messageToFirebase =
                        new MessageToFirebase(editText.getText().toString(), "Pregunta");
                editText.setText("");

                databaseReference.push().setValue(messageToFirebase);
            }
        });


        Query query = databaseReference.limitToLast(10);

        FirebaseListAdapter<MessageToFirebase> firebaseListAdapter = new FirebaseListAdapter<MessageToFirebase>(this,MessageToFirebase.class,android.R.layout.two_line_list_item,query) {
            @Override
            protected void populateView(View v, MessageToFirebase model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getAuthor());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getMessage());
            }
        };

        listView.setAdapter(firebaseListAdapter);
    }
}