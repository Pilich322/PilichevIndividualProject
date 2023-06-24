package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentStudentUpdateBinding;

import java.util.Calendar;
import java.util.List;

public class StudentUpdateFragment extends Fragment {

    private FragmentStudentUpdateBinding binding;
    private DataBaseManager dataBaseManager;
    private Student student;
    private String birthday;

    public StudentUpdateFragment(Student student) {
        this.student = student;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentUpdateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        List<Group> groupList = dataBaseManager.getAllGroupsFromDb();
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spinnerGroupUpdate.setAdapter(adapter);
        for (Group group : groupList)
            if (group.getId() == student.getGroupId())
                binding.spinnerGroupUpdate.setSelection(groupList.indexOf(group));
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(student.getBirthday().substring(6, 10)),
                Integer.parseInt(student.getBirthday().substring(3, 5)),
                Integer.parseInt(student.getBirthday().substring(0, 2)));
        binding.calendarViewBirthdayStudent.setDate(date.getTimeInMillis(), true, true);
        binding.editTextTextMiddleName.setText(student.getMiddleName());
        binding.editTextTextFirstName.setText(student.getFirstName());
        binding.editTextTextSecondName.setText(student.getSecondName());
        binding.calendarViewBirthdayStudent.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            month += 1;
            birthday= dayOfMonth<10?"0"+dayOfMonth + ".":dayOfMonth + ".";
            birthday += month<10?"0"+month+".":month + ".";
            birthday+=year;
        });
        binding.buttonUpdateStudent.setOnClickListener(v -> updateStudent());
    }

    public void updateStudent() {
        StudentListFragment studentListFragment = new StudentListFragment();
        student.setSecondName(binding.editTextTextSecondName.getText().toString());
        student.setMiddleName(binding.editTextTextMiddleName.getText().toString());
        student.setFirstName(binding.editTextTextFirstName.getText().toString());
        student.setBirthday(birthday);
        Group group = (Group) binding.spinnerGroupUpdate.getSelectedItem();
        student.setGroupId(group.getId());
        dataBaseManager.updateStudent(student);
        Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, studentListFragment)
                .disallowAddToBackStack()
                .commit();
    }
}