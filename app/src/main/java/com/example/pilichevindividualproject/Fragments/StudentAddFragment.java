package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentStudentAddBinding;

import java.util.List;

public class StudentAddFragment extends Fragment {
    private FragmentStudentAddBinding binding;
    private DataBaseManager dataBaseManager;
    private String birthday;

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
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spinnerGroup.setAdapter(adapter);
        binding.calendarViewBirthday.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            month += 1;
            birthday = dayOfMonth < 10 ? "0" + dayOfMonth + "." : dayOfMonth + ".";
            birthday += month < 10 ? "0" + month + "." : month + ".";
            birthday += year;
        });
        binding.buttonAddStudent.setOnClickListener(v -> {
            saveStudent();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
    //Сохранение информации о студенте
    private void saveStudent() {
        StudentListFragment studentListFragment = new StudentListFragment();
        String fName = "";
        String sName = "";
        String mName = "";
        Group group = new Group();
        try {
            fName = binding.editTextTextFirstName.getText().toString();
            sName = binding.editTextTextSecondName.getText().toString();
            mName = binding.editTextTextMiddleName.getText().toString();
            group = (Group) binding.spinnerGroup.getSelectedItem();
        } catch (Exception ignored) {
        }
        if(group==null){
            Toast.makeText(getContext(), "Вы не выбрали группу", Toast.LENGTH_SHORT).show();
            return;
        }
        if (birthday == null) {
            Toast.makeText(getContext(), "Вы не выбрали дату", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fName.isEmpty() | sName.isEmpty() | mName.isEmpty())
            Toast.makeText(getContext(), R.string.emptyFields, Toast.LENGTH_SHORT).show();
        else {
            Student student = new Student(fName, sName, mName, birthday, group.getId());
            dataBaseManager.insertStudent(student);
            Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, studentListFragment)
                    .disallowAddToBackStack()
                    .commit();
        }
    }
}