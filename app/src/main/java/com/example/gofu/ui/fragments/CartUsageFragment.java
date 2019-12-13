package com.example.gofu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gofu.CartUsageAdapter;
import com.example.gofu.CartUse;
import com.example.gofu.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CartUsageFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference CartUseRef = db.collection("Cart_use");
    private CartUsageAdapter adapter;
    TextView tvCart;
    Spinner SortSpinner;
    EditText etSearchKey;
    Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_usage,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String CartId = bundle.getString("Cart");

        etSearchKey = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        final String Key = etSearchKey.getText().toString();

        final DocumentReference CartRef = db.collection("Golf Carts").document(CartId);

        final Query query = CartUseRef.whereEqualTo("Cart_id", CartRef);
        final Query query1 = CartUseRef.whereEqualTo("Cart_id",CartRef).whereArrayContains("Checked_out_date", Key);


        final FirestoreRecyclerOptions<CartUse> options = new FirestoreRecyclerOptions.Builder<CartUse>()
                    .setQuery(query, CartUse.class).build();
            //adapter = new CartUsageAdapter(options);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
            }
        });
        adapter = new CartUsageAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.rvCartUse);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        tvCart = view.findViewById(R.id.tvCartid);
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
