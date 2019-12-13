package com.example.gofu.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AccountFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference UserRef = db.collection("Users");
    private Button btnEditEmail;
    private Button btnEditPassword;
    private TextView PhoneNum;
    private TextView CurrentEmail;
    private TextView Name;
    private TextView RattlerId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PhoneNum = view.findViewById(R.id.tvPhne);
        CurrentEmail = view.findViewById(R.id.tvEmail);
        Name = view.findViewById(R.id.tvNme);
        RattlerId = view.findViewById(R.id.tvRatID);
        btnEditPassword = view.findViewById(R.id.btnEditPW);
        btnEditEmail = view.findViewById(R.id.btnEditEmail);

        DocumentReference User = UserRef.document(user.getUid());

        User.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Name.setText(documentSnapshot.getString("Name"));
                        RattlerId.setText(documentSnapshot.getString("RattlerId"));
                        PhoneNum.setText(documentSnapshot.getString("Phone"));
                        CurrentEmail.setText(documentSnapshot.getString("Email"));
                    }
                }


            }
        });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordFragment passwordFragment = new PasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,passwordFragment);
                transaction.commit();

            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailFragment emailFragment = new EmailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,emailFragment);
                transaction.commit();
            }
        });


    }
}
