package com.example.gofu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RentHistoryAdapter extends FirestoreRecyclerAdapter<CartUse, RentHistoryAdapter.RentHistoryHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RentHistoryAdapter(@NonNull FirestoreRecyclerOptions<CartUse> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RentHistoryHolder rentHistoryHolder, int i, @NonNull CartUse cartUse) {
        rentHistoryHolder.tvTimeOut.setText(cartUse.getChecked_out_time());
        rentHistoryHolder.tvTimeIn.setText(cartUse.getChecked_in_time());
        rentHistoryHolder.tvDate.setText(cartUse.getChecked_out_date());

    }

    @NonNull
    @Override
    public RentHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new RentHistoryHolder(v);
    }

    class RentHistoryHolder extends RecyclerView.ViewHolder{
        TextView tvCartId;
        TextView tvTimeOut;
        TextView tvTimeIn;
        TextView tvDate;


        public RentHistoryHolder(@NonNull View itemView) {
            super(itemView);

            tvCartId=itemView.findViewById(R.id.tvCartid);
            tvDate = itemView.findViewById(R.id.tvdate);
            tvTimeIn = itemView.findViewById(R.id.tvtimeIn);
            tvTimeOut= itemView.findViewById(R.id.tvtimeOut);
        }
    }
}
