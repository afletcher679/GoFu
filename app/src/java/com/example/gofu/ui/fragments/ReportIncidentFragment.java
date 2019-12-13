package com.example.gofu.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.Homepage;
import com.example.gofu.R;
import com.example.gofu.Signup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReportIncidentFragment extends Fragment {

    private TextView tvTime;
    private  TextView tvDate;
    private EditText etCart;
    private Button btnSubmitIssue;
    String TAG = "ReportIncidentFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_incident, container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        etCart = view.findViewById(R.id.etCartId);
        btnSubmitIssue = view.findViewById(R.id.btnSubmit);


        //Get current Date and Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
        String date = simpleDate.format(calendar.getTime());
        tvDate.setText(date);

        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm a");
        String time  = simpleTime.format(calendar.getTime());
        tvTime.setText(time);

        btnSubmitIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String Time = tvTime.getText().toString();
                String Date = tvDate.getText().toString();
                String Id = etCart.getText().toString();
                String Issues = Homepage.list.toString();


                final Map<String,Object> repair = new HashMap<>();

                //Adding Repair Order to database
                DocumentReference cartRef = db.collection("Golf Carts").document(Id);
                DocumentReference repairCount = db.collection("Repair Order").document("Repair Order Count");
                repairCount.update("count", FieldValue.increment(1));
                //int value = db.collection("Repair Order").

//                repairCount.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists()) {
//                            repair.put("Reportnum", documentSnapshot.get("count"));
//                        }else{
//                            Toast.makeText(getContext(),"Document does not exist",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });

                repair.put("CartId",cartRef);
                repair.put("ReportDate",Date);
                repair.put("ReportTime",Time);
                repair.put("Issues",Issues);
                repair.put("Status","Open");
                repair.put("Reportnum", 30012);



                db.collection("Repair Order").add(repair)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(v.getContext(), "Issue Submitted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Error while submitting. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });

                //Update Cart Status to Out of Service
                cartRef.update("Status", "Out of Service").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error while updating", e);
                            }
                        });
                //Go back to User homepage
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,homeFragment);
                transaction.commit();
            }
        });
    }

}
