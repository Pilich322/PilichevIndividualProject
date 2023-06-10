package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.adapters.GroupAdapter;
import com.example.pilichevindividualproject.adapters.StudentAdapter;
import com.example.pilichevindividualproject.databinding.FragmentStudentListBinding;


public class StudentListFragment extends Fragment {

    private FragmentStudentListBinding binding;
    private DataBaseManager dataBaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StudentAddFragment studentAddFragment = new StudentAddFragment();
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToRead();
        binding.floatingActionButtonAddStudent.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
            );
            fragmentTransaction.replace(R.id.frameLayoutMain, studentAddFragment)
                    .addToBackStack(null)
                    .commit();
        });
        StudentAdapter studentAdapter= new StudentAdapter(getContext(),dataBaseManager.getAllStudentsFromDb());
        binding.recyclerViewStudent.setAdapter(studentAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
}