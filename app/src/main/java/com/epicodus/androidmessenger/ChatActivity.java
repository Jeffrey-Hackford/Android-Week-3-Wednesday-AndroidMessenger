package com.epicodus.androidmessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.listView) ListView mMessageListView;
    @Bind(R.id.messageSubmitButton) Button mMessageSubmitButton;
    @Bind(R.id.messageEditText) EditText mMessageEditText;

    private DatabaseReference mMessageReference;
    public String sender;
    public String recipient;

    ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        recipient = intent.getStringExtra("contact");
        mMessageSubmitButton.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sender = user.getUid();

        mMessageReference = FirebaseDatabase
                .getInstance()
                .getReference("message")
                .child(sender);

        mMessageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messages.add(message.getMessage());

//                    contactDisplay.add(contact.getContact());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
        mMessageListView.setAdapter(adapter);
    }

        public void onClick(View v) {
            if (v == mMessageSubmitButton) {
                String message = mMessageEditText.getText().toString().trim();
                Message newMessage = new Message(message, recipient);
                DatabaseReference pushRef = mMessageReference.push();
                String pushId = pushRef.getKey();
                newMessage.setPushId(pushId);
                pushRef.setValue(newMessage);
            }
    }

}
