package com.example.gofu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends FirestoreRecyclerAdapter<Cart, CartAdapter.CartHolder> {

    private OnItemClickListener listener;

    public CartAdapter(@NonNull FirestoreRecyclerOptions<Cart> options) {
        super(options);
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "CartAdapter";

    @Override
    protected void onBindViewHolder(@NonNull CartHolder cartHolder, final int i, @NonNull Cart cart) {

        cartHolder.tvCartStatus.setText(cart.getStatus());
        cartHolder.tvCartId.setText(String.valueOf(cart.getId()));

    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartHolder(v);
    }

    class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCartId;
        TextView tvCartStatus;
        Spinner spinner;
        Button btnUpdate;

        public CartHolder(@NonNull View itemView){
            super(itemView);

            tvCartId = itemView.findViewById(R.id.CartID);
            tvCartStatus = itemView.findViewById(R.id.tvStatus);
            spinner = itemView.findViewById(R.id.Status_drop);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String docId = getSnapshots().getSnapshot(getAdapterPosition()).getId();
                    String value = spinner.getSelectedItem().toString();
                    DocumentReference CartRef = db.collection("Golf Carts").document(docId);
                    CartRef.update("Status", value).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.OnItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {

        }
    }

        public interface OnItemClickListener{
            void OnItemClick(DocumentSnapshot documentSnapshot, int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

}
