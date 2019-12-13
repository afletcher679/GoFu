package com.example.gofu.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gofu.R;
import com.example.gofu.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class ReturnCartFragment extends Fragment {
    private  TextView tvTime;
    private  TextView tvDate;
    private EditText etCart;
    private EditText etLocation;
    private Button btnReturnCrt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "ReturnCartFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_return_cart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        etCart = view.findViewById(R.id.etCartId);
        etLocation = view.findViewById(R.id.etLocation);
        btnReturnCrt = view.findViewById(R.id.btnReturnCart);

        //Get current Date and Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
        final String date = simpleDate.format(calendar.getTime());
        tvDate.setText(date);

        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm a");
        final String time  = simpleTime.format(calendar.getTime());
        tvTime.setText(time);



        btnReturnCrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String Id = etCart.getText().toString();
                String location = etLocation.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference userRef = db.collection("Users").document(user.getUid());
                DocumentReference cartRef = db.collection("Golf Carts").document(Id);
                String Cartpath = cartRef.getPath();
                String Userpath = userRef.getPath();

                //Update Cart Total_use +1
                cartRef.update("Total_use", FieldValue.increment(1));

                //Update Cart Status to Available
                cartRef.update("Status", "Available").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating", e);
                            }
                        });

                //Update Cart Location
                cartRef.update("Location", location).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating", e);
                            }
                        });


                db.collection("Cart_use").whereEqualTo("Cart_id",Cartpath).whereEqualTo("User_id", Userpath).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                        //.update("Check_in_time", time)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(v.getContext(),"Cart rented successfully!",LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                makeText(v.getContext(), "Error while submitting. Please try again.", LENGTH_SHORT).show();
//                            }
//                        });

                Toast.makeText(getContext(), "Cart successfully returned!", Toast.LENGTH_SHORT).show();
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,homeFragment);
                transaction.commit();
            }
        });


    }
}
