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
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentGroupAddBinding;
import com.example.pilichevindividualproject.databinding.FragmentGroupListBinding;

public class GroupAddFragment extends Fragment {

    DataBaseManager dataBaseManager;
    FragmentGroupAddBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        binding.buttonSaveGroup.setOnClickListener(v ->
               saveGroup());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
    //Сохранение группы
    private void saveGroup(){
        GroupListFragment groupListFragment = new GroupListFragment();
        String name = "";
        Integer number = null;
        try {
            name = binding.editTextTextGroupName.getText().toString();
            number = Integer.parseInt(binding.editTextNumberGroupNumber.getText().toString());
        }
       catch (Exception ignored){
       }
        if(name.equals("") | number == null) {
            Toast.makeText(getContext(), R.string.emptyFields, Toast.LENGTH_SHORT).show();
        }
        else {
            Group group = new Group(name,number);
            dataBaseManager.insertGroup(group);
            Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutMain,groupListFragment)
                    .disallowAddToBackStack()
                    .commit();
        }
    }
}