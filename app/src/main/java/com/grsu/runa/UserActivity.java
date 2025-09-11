package com.grsu.runa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grsu.runa.databinding.ActivityUserBinding;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String userId = getIntent().getStringExtra("userId");
        FirebaseDatabase.getInstance("https://runaapp-651b2-default-rtdb.europe-west1.firebasedatabase.app").getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot userSnapshot=snapshot.child("Users").child(userId);
                String username= Objects.requireNonNull(userSnapshot.child("username").getValue()).toString();
                binding.studentUsername.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
