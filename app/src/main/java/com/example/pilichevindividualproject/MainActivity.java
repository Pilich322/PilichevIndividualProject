package com.example.pilichevindividualproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.pilichevindividualproject.Fragments.GroupListFragment;
import com.example.pilichevindividualproject.Fragments.StudentListFragment;
import com.example.pilichevindividualproject.databinding.ActivityMainBinding;

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