package com.leonardo_soares_santos.chatbotlss.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leonardo_soares_santos.chatbotlss.R;

import java.util.ArrayList;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class RelatorioActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
ListView listView ;

private ArrayList<String>arrayList= new ArrayList<>();
private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);


        mDatabase= FirebaseDatabase.getInstance().getReference();

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        listView = (ListView) findViewById(R.id.listRela);
        listView.setAdapter(adapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
