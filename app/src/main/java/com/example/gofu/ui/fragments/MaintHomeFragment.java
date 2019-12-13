package com.example.gofu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class MaintHomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference GolfCartRef = db.collection("Golf Carts");
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maint_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setUpRecyclerView();

        //private void setUpRecyclerView() {
        Query query = GolfCartRef;

        FirestoreRecyclerOptions<Cart> options = new FirestoreRecyclerOptions.Builder<Cart>()
                .setQuery(query, Cart.class).build();

        adapter = new CartAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.rvCartList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //}

        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                 String Cart = documentSnapshot.getId();

                Bundle bundle = new Bundle();
                bundle.putString("Cart", Cart);


                RepairLogFragment repairLogFragment = new RepairLogFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                repairLogFragment.setArguments(bundle);
                transaction.replace(R.id.flContainer,repairLogFragment);
                transaction.commit();
            }
        });
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
