package com.example.pilichevindividualproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilichevindividualproject.databinding.FragmentGroupListBinding;
import com.example.pilichevindividualproject.databinding.GroupListItemBinding;

public class GroupListFragment extends Fragment {


   private FragmentGroupListBinding binding;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}