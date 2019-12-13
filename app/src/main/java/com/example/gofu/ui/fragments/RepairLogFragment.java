package com.example.gofu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofu.Cart;
import com.example.gofu.CartAdapter;
import com.example.gofu.R;
import com.example.gofu.Repair;
import com.example.gofu.RepairAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RepairLogFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference RepairRef = db.collection("Repair Order");
    private RepairAdapter adapter;
    TextView tvCart;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repair_log,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String CartId = bundle.getString("Cart");

        DocumentReference CartRef = db.collection("Golf Carts").document(CartId);
        Query query = RepairRef.whereEqualTo("CartId", CartRef);

        FirestoreRecyclerOptions<Repair> options = new FirestoreRecyclerOptions.Builder<Repair>()
                .setQuery(query, Repair.class).build();

        adapter = new RepairAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.rvRepairList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        tvCart = view.findViewById(R.id.tvCart);
        tvCart.setText(CartId);
    }


    @Override
    public void onStart () {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop () {
        super.onStop();
        adapter.stopListening();
    }

}
