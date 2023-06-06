package com.example.pilichevindividualproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pilichevindividualproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new GroupListFragment());
        binding.bnvMenu.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.studentItem)
                setFragment(new StudentListFragment());
            if(item.getItemId()==R.id.groupItem)
                setFragment(new GroupListFragment());
            return true;
        });
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
        ).replace(R.id.frameLayoutMain,fragment)
                .disallowAddToBackStack()
                .commit();
    }
}