package com.grsu.runa.buttonNav.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grsu.runa.LoginActivity;
import com.grsu.runa.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentProfileBinding.inflate(inflater, container, false);
        loadUserInfo();
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return binding.getRoot();
    }
    private void loadUserInfo(){
        FirebaseDatabase.getInstance("https://runaapp-651b2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username=snapshot.child("username").getValue().toString();
                        String profileImage=snapshot.child("profileImage").getValue().toString();

                        if(username!=null){
                            binding.usernameTv.setText(username);

                        }else{
                            Log.e("DataError", "Username не найден");

                        }
                        if(!profileImage.isEmpty()){
                            Glide.with(getContext()
                            ).load(profileImage).into(binding.profileImageView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
