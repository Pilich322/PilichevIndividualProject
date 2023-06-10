package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentStudentAddBinding;

import java.util.List;

public class StudentAddFragment extends Fragment {
    FragmentStudentAddBinding binding;
    DataBaseManager dataBaseManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        List<Group> groupList = dataBaseManager.getAllGroupsFromDb();
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spinnerGroup.setAdapter(adapter);
        binding.buttonAddStudent.setOnClickListener(v -> {
            saveStudent();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }

    private void saveStudent() {
        StudentListFragment studentListFragment = new StudentListFragment();
        String fName = null;
        String sName = null;
        String mName = null;
        String birthday = null;
        Group group = new Group();
        try {
            fName = binding.editTextTextFirstName.getText().toString();
            sName = binding.editTextTextSecondName.getText().toString();
            mName = binding.editTextTextMiddleName.getText().toString();
            birthday = String.valueOf(binding.calendarViewBirthday.getDate());
            group = (Group) binding.spinnerGroup.getSelectedItem();
        } catch (Exception ignored) {
        }
        Log.d("LOH", fName + " " + sName + " " + mName + " " + birthday + " " + group);
        if (fName == null | sName == null | mName == null | birthday == null | group == null)
            Toast.makeText(getContext(), "Поля не заполнены", Toast.LENGTH_SHORT).show();
        else {
            Student student = new Student(fName, sName, mName, birthday, group.getId());
            dataBaseManager.insertStudent(student);
            Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, studentListFragment)
                    .disallowAddToBackStack()
                    .commit();
        }
    }
}