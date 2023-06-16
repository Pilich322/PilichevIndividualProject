package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.adapters.StudentAdapter;
import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentStudentListBinding;

import java.util.List;


public class StudentListFragment extends Fragment {

    private FragmentStudentListBinding binding;
    private DataBaseManager dataBaseManager;
    private StudentAdapter studentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        binding.floatingActionButtonAddStudent.setOnClickListener(v -> {
            StudentAddFragment studentAddFragment = new StudentAddFragment();
            setFragment(studentAddFragment);
        });
        List<Group> groupList = dataBaseManager.getAllGroupsFromDb();
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spinnerStudentGroup.setAdapter(adapter);

        StudentAdapter.OnChangeClickListener onChangeClickListener = (student, position) -> {
            StudentUpdateFragment studentUpdateFragment = new StudentUpdateFragment();
            setFragment(studentUpdateFragment);
        };

        StudentAdapter.OnDeleteClickListener onDeleteClickListener = (student, position) -> {
            dataBaseManager.deleteStudent(student);
        };

        studentAdapter = new StudentAdapter(getContext(),
                dataBaseManager.getAllStudentsFromDb(),
                onDeleteClickListener,
                onChangeClickListener);
        binding.recyclerViewStudent.setAdapter(studentAdapter);
        binding.spinnerStudentGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Group group = (Group) binding.spinnerStudentGroup.getItemAtPosition(position);
                studentAdapter.updateAdapter(dataBaseManager.updateStudentAdapter(group));
                binding.recyclerViewStudent.setAdapter(studentAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
}