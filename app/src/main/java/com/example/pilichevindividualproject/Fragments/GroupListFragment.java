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
import com.example.pilichevindividualproject.Adapters.GroupAdapter;
import com.example.pilichevindividualproject.databinding.FragmentGroupListBinding;

import java.util.List;

public class GroupListFragment extends Fragment {

    private DataBaseManager dataBaseManager;
    private FragmentGroupListBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToRead();
        List<Group> groupList = dataBaseManager.getAllGroupsFromDb();
        Log.d("LOG", "ТУТ");
        for (Group group : groupList) {
            Log.d("LOG", group.getName() + " " + group.getNumber() + " " + group.getId());
        }
        binding.floatingActionButtonAddGroup.setOnClickListener(v -> {
            GroupAddFragment addFragment = new GroupAddFragment();
            setFragment(addFragment);
        });
        GroupAdapter.OnGroupClickListener onGroupClickListener = (group, position) -> {
            GroupUpdateFragment groupUpdateFragment = new GroupUpdateFragment();
            setFragment(groupUpdateFragment);
        };
        GroupAdapter groupAdapter = new GroupAdapter(getContext(), dataBaseManager.getAllGroupsFromDb(), onGroupClickListener);
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