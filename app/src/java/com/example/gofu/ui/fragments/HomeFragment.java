package com.example.gofu.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofu.Cart;
import com.example.gofu.Homepage;
import com.example.gofu.Login;
import com.example.gofu.R;
import com.example.gofu.RentCartAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {
private Button btnRentCart;
private Button btnReturnCart;
private Button btnReportIssue;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "RentCartAdapter";
    Dialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRentCart = view.findViewById(R.id.btnRent);
        btnReturnCart = view.findViewById(R.id.btnReturn);
        btnReportIssue = view.findViewById(R.id.btnReport);

        mDialog = new Dialog(getContext());

        //Click on Rent Cart to open available carts
        btnRentCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference userRef = db.collection("Users").document(user.getUid());

                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(task.isSuccessful()){
                            if(documentSnapshot.exists()){
                                String banStatus = documentSnapshot.getString("Ban_status");
                                if(banStatus.equals("Banned")){
                                    showPopup(v);
                                }else{
                                    RentCartFragment rentCartFragment = new RentCartFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.flContainer,rentCartFragment);
                                    transaction.commit();
                                }
                            }
                        }
                    }
                });


            }
        });

        //Click on Return Cart to return rented cart
        btnReturnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReturnCartFragment returnCartFragment = new ReturnCartFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,returnCartFragment);
                transaction.commit();

            }
        });

        //Click on Report Issue to report issues with cart
        btnReportIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportIncidentFragment reportIncidentFragment = new ReportIncidentFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer,reportIncidentFragment);
                transaction.commit();

            }
        });
    }
    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_ban, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                900,900);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1]+ anchorView.getHeight() );

    }
}
