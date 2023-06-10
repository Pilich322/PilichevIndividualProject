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
import com.example.pilichevindividualproject.databinding.FragmentGroupListBinding;
import com.example.pilichevindividualproject.databinding.GroupListItemBinding;

public class GroupListFragment extends Fragment {

    private DataBaseManager dataBaseManager;
    private FragmentGroupListBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDbToRead();
        GroupAddFragment addFragment = new GroupAddFragment();
        binding.floatingActionButtonAddGroup.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
            fragmentTransaction.replace(R.id.frameLayoutMain, addFragment)
                    .addToBackStack(null)
                    .commit();
        });
        GroupAdapter groupAdapter = new GroupAdapter(getContext(),dataBaseManager.getAllGroupsFromDb());
        binding.recyclerViewGroup.setAdapter(groupAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}