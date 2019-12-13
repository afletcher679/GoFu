package com.example.gofu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import static android.widget.Toast.*;
import static java.lang.String.format;

public class CartUsageAdapter extends FirestoreRecyclerAdapter<CartUse, CartUsageAdapter.CartUseHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "CartUsageAdapter";

    public CartUsageAdapter(@NonNull FirestoreRecyclerOptions<CartUse> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final CartUseHolder cartUseHolder, int i, @NonNull CartUse cartUse) {

        cartUseHolder.tvTimeOut.setText(cartUse.getChecked_out_time());
        cartUseHolder.tvTimeIn.setText(cartUse.getChecked_in_time());
        cartUseHolder.tvDate.setText(cartUse.getChecked_out_date());

        String User = cartUse.getUser();



//        db.collection("Users").document(cartUse.getUser()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()) {
//                    cartUseHolder.tvUserId.setText(String.valueOf(documentSnapshot.get("RattlerID")));
//                }
//            }
//        });

    }

    @NonNull
    @Override
    public CartUseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_use, parent, false);
        return new CartUseHolder(v);
    }

    class CartUseHolder extends RecyclerView.ViewHolder{
        TextView tvUserId;
        TextView tvTimeOut;
        TextView tvTimeIn;
        TextView tvDate;

        public CartUseHolder(@NonNull View itemView) {
            super(itemView);

            tvUserId=itemView.findViewById(R.id.tvUserId);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTimeIn = itemView.findViewById(R.id.tvTimeIn);
            tvTimeOut= itemView.findViewById(R.id.tvTimeOut);



        }
    }
}
