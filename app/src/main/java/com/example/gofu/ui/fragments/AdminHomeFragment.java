package com.example.gofu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.AdminCartListFragment;
import com.example.gofu.R;

public class AdminHomeFragment extends Fragment {

    private Button btnBrowseUsers;
    private Button btnCartUsageLog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_home, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBrowseUsers = view.findViewById(R.id.btnRent);
        btnCartUsageLog = view.findViewById(R.id.btnLog);


        //Click on Browse Users to see list of users
        btnBrowseUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserListFragment userListFragment = new UserListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,userListFragment);
                transaction.commit();

            }
        });

        //Click on Maintenance Log to view all repairs
        btnCartUsageLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminCartListFragment adminCartListFragment = new AdminCartListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,adminCartListFragment);
                transaction.commit();

            }
        });

    }
}
