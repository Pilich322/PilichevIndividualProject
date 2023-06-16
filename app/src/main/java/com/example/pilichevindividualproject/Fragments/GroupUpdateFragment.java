package com.example.pilichevindividualproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.databinding.FragmentGroupAddBinding;
import com.example.pilichevindividualproject.databinding.FragmentGroupUpdateBinding;
import com.example.pilichevindividualproject.databinding.FragmentStudentUpdateBinding;

public class GroupUpdateFragment extends Fragment {

    DataBaseManager dataBaseManager;
    FragmentGroupUpdateBinding binding;
    Group group;

    GroupUpdateFragment(Group group){
        this.group = group;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupUpdateBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        binding.buttonSaveGroup.setOnClickListener(v ->
                updateGroup());
        binding.editTextTextGroupNameUpdate.setText(group.getName());
        binding.editTextNumberGroupNumberUpdate.setText(String.valueOf(group.getNumber()));
    }

    private void updateGroup() {
        GroupListFragment groupListFragment = new GroupListFragment();
        group.setName(binding.editTextTextGroupNameUpdate.getText().toString());
        group.setNumber(Integer.parseInt(binding.editTextNumberGroupNumberUpdate.getText().toString()));
        dataBaseManager.updateGroup(group);
        Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutMain,groupListFragment)
                .disallowAddToBackStack()
                .commit();
    }
}