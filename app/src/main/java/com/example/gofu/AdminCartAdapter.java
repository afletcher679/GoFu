package com.example.gofu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class AdminCartAdapter extends FirestoreRecyclerAdapter<Cart, AdminCartAdapter.AdminCartHolder> {
    private CartAdapter.OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminCartAdapter(@NonNull FirestoreRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminCartHolder adminCartHolder, int i, @NonNull Cart cart) {

        adminCartHolder.tvCartStatus.setText(cart.getStatus());
        adminCartHolder.tvCartId.setText(String.valueOf(cart.getId()));
        adminCartHolder.tvTotalUse.setText(String.valueOf(cart.getTotal_use()));
    }

    @NonNull
    @Override
    public AdminCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_use_cart, parent, false);
        return new AdminCartHolder(v);
    }

    class AdminCartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCartId;
        TextView tvCartStatus;
        TextView tvTotalUse;

        public AdminCartHolder(@NonNull View itemView) {
            super(itemView);

            tvCartId = itemView.findViewById(R.id.tvCart);
            tvCartStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalUse = itemView.findViewById(R.id.tvTotUse);

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

    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
