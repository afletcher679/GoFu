package com.example.gofu.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.Login;
import com.example.gofu.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserMoreFragment extends Fragment {

    private Button btnLogout;
    private Button btnRentHistory;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_more,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnRentHistory = view.findViewById(R.id.btnHistory);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), Login.class);
                startActivity(i);
            }
        });

        btnRentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentHistoryFragment rentHistoryFragment = new RentHistoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //repairLogFragment.setArguments(bundle);
                transaction.replace(R.id.flContainer,rentHistoryFragment);
                transaction.commit();
            }
        });
    }
}
