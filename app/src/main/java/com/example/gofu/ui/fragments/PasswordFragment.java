package com.example.gofu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PasswordFragment extends Fragment {

private EditText etOldPassword;
private EditText etNewPassword;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference UserRef = db.collection("Users").document(user.getUid());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        Button btnSave = view.findViewById(R.id.btnSavePW);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        if(user != null){
            UserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()){
                            String CurrPassword = doc.getString("Password");
                                    if(etOldPassword.getText().toString().equals(CurrPassword)) {
                                        user.updatePassword(etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    UserRef.update("Password",etNewPassword.getText().toString());

                                                    AccountFragment accountFragment = new AccountFragment();
                                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                    transaction.replace(R.id.flContainer,accountFragment);
                                                    transaction.commit();
                                                    Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getContext(), "Error changing password", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }else{
                                        etOldPassword.setError("Current password does not match");
                                        etOldPassword.requestFocus();
                                    }

                        }
                    }
                }
            });
        }
            }
        });
    }
}
