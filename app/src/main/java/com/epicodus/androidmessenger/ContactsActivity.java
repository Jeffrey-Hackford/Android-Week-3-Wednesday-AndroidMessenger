package com.epicodus.androidmessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity{
    @Bind(R.id.addContactButton) Button mAddContactButton;
    @Bind(R.id.addContactEditText) EditText mAddContactEditText;
    @Bind(R.id.contactListView) ListView mContactListView;
    private DatabaseReference mContactReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    ArrayList<Contact> contacts = new ArrayList<>();
    ArrayList<String> contactDisplay = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        mContactReference = FirebaseDatabase
                .getInstance()
                .getReference("users");

        mContactReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contact contact = snapshot.getValue(Contact.class);
                    contacts.add(contact);
                    contactDisplay.add(contact.getContact());
//                    System.out.println(contact.getContact());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contactDisplay);
        mContactListView.setAdapter(adapter);

        mContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
                intent.putExtra("contact", contacts.get(i).getUid());
                System.out.println(intent.getExtras());
                startActivity(intent);
            }
        });

        mAddContactButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == mAddContactButton) {
//                    Contact contact = new Contact(mAddContactEditText.getText().toString().trim());
//                    contacts.add(contact);
                    adapter.notifyDataSetChanged();
                }
            }

        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
