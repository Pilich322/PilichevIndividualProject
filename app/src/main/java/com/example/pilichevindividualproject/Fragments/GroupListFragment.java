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

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;
import com.example.pilichevindividualproject.adapters.GroupAdapter;
import com.example.pilichevindividualproject.databinding.FragmentGroupListBinding;

import java.util.List;

public class GroupListFragment extends Fragment {

    private DataBaseManager dataBaseManager;
    private FragmentGroupListBinding binding;

    private GroupAdapter groupAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToWrite();
        binding.floatingActionButtonAddGroup.setOnClickListener(v -> {
            GroupAddFragment addFragment = new GroupAddFragment();
            setFragment(addFragment);
        });
        GroupAdapter.OnChangeClickListener onChangeClickListener = (group, position) -> {
            GroupUpdateFragment groupUpdateFragment = new GroupUpdateFragment(group);
            setFragment(groupUpdateFragment);
        };
        GroupAdapter.OnDeleteClickListener onDeleteClickListener = (group, position) -> {
            dataBaseManager.deleteGroup(group);
            groupAdapter.updateAdapter(dataBaseManager.getAllGroupsFromDb());
        };
        groupAdapter = new GroupAdapter(getContext(), dataBaseManager.getAllGroupsFromDb(), onChangeClickListener,onDeleteClickListener);
        binding.recyclerViewGroup.setAdapter(groupAdapter);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}