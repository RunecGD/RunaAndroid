package com.grsu.runa;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grsu.runa.databinding.ActivityChatBinding;
import com.grsu.runa.message.Message;
import com.grsu.runa.message.MessagesAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String chatId = getIntent().getStringExtra("chatId");
        loadMessages(chatId);

    }
    private void loadMessages(String chatId){

        if (chatId==null) return;

        FirebaseDatabase.getInstance("https://runaapp-651b2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Chats")
                .child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) return;

                        List<Message> messages = new ArrayList<>();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()){
                            String messageId = messageSnapshot.getKey();
                            String ownerId = messageSnapshot.child("ownerId").getValue().toString();
                            String text = messageSnapshot.child("text").getValue().toString();
                            String date = messageSnapshot.child("date").getValue().toString();

                            messages.add(new Message(messageId, ownerId, text, date));
                        }
                        binding.messagesRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        binding.messagesRv.setAdapter(new MessagesAdapter(messages));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}