package com.grsu.runa;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.grsu.runa.buttonNav.chats.ChatFragment;
import com.grsu.runa.buttonNav.friends.FriendFragment;
import com.grsu.runa.buttonNav.newsFeed.NewsFeedFragment;
import com.grsu.runa.buttonNav.profile.ProfileFragment;
import com.grsu.runa.buttonNav.students.StudentsFragment;
import com.grsu.runa.databinding.ActivityLoginBinding;
import com.grsu.runa.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new NewsFeedFragment()).commit();
        binding.buttonNav.setSelectedItemId(R.id.news_feed);
        Map<Integer, Fragment> fragmentMap=new HashMap<>();
        fragmentMap.put(R.id.chats, new ChatFragment());
        fragmentMap.put(R.id.friends, new FriendFragment());
        fragmentMap.put(R.id.news_feed, new NewsFeedFragment());
        fragmentMap.put(R.id.profile, new ProfileFragment());
        fragmentMap.put(R.id.students, new StudentsFragment());
        binding.buttonNav.setOnItemSelectedListener(item -> {
            Fragment fragment=fragmentMap.get(item.getItemId());
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), fragment).commit();

            return true;
        });
    }
}