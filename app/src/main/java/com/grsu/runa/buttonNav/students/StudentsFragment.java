package com.grsu.runa.buttonNav.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grsu.runa.databinding.FragmentStudentsBinding;
import com.grsu.runa.users.User;
import com.grsu.runa.users.UsersAdapter;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {
    FragmentStudentsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentStudentsBinding.inflate(inflater, container, false);
        loadUsers();
        return binding.getRoot();

    }
    private void loadUsers(){
        ArrayList<User> users = new ArrayList<User>();
        FirebaseDatabase.getInstance("https://runaapp-651b2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    if (userSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        continue;
                    }

                    String username = userSnapshot.child("username").getValue().toString();
                    String profileImage = userSnapshot.child("profileImage").getValue().toString();

                    users.add(new User(username, profileImage));
                }

                binding.usersRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.usersRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                binding.usersRv.setAdapter(new UsersAdapter(users));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

