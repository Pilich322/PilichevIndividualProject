package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


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
    private Group group;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            StudentUpdateFragment studentUpdateFragment = new StudentUpdateFragment(student);
            setFragment(studentUpdateFragment);
        };

        StudentAdapter.OnDeleteClickListener onDeleteClickListener = (student, position) -> {
            dataBaseManager.deleteStudent(student);
            studentAdapter.updateAdapter(dataBaseManager.getAllStudentsFromDb());
        };

        binding.editTextTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentAdapter.updateAdapter(dataBaseManager.getAllStudentsFromDbIntoEditText(s.toString()));
                binding.recyclerViewStudent.setAdapter(studentAdapter);
                if (s.length() == 0) {
                    group = (Group) binding.spinnerStudentGroup.getSelectedItem();
                    studentAdapter.updateAdapter(dataBaseManager.updateStudentAdapter(group));
                    binding.recyclerViewStudent.setAdapter(studentAdapter);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        studentAdapter = new StudentAdapter(getContext(), dataBaseManager.getAllStudentsFromDb(), onDeleteClickListener, onChangeClickListener);
        updateRecyclerView();
        binding.spinnerStudentGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = (Group) binding.spinnerStudentGroup.getItemAtPosition(position);
                studentAdapter.updateAdapter(dataBaseManager.updateStudentAdapter(group));
                updateRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    //Загрузка данных в Recycler в потоке
    public void updateRecyclerView(){
        Thread newThread = new Thread(() -> binding.recyclerViewStudent.post(() -> binding.recyclerViewStudent.setAdapter(studentAdapter)));
        newThread.start();
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
}