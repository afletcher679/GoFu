package com.example.gofu;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class RentCartAdapter extends FirestoreRecyclerAdapter<Cart, RentCartAdapter.RCartHolder>{


    public RentCartAdapter(@NonNull FirestoreRecyclerOptions<Cart> options) {
        super(options);
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "RentCartAdapter";


    @Override
    protected void onBindViewHolder(@NonNull RCartHolder cartHolder, int i, @NonNull Cart cart) {

        cartHolder.Id.setText(String.valueOf(cart.getId()));
        cartHolder.Status.setText(cart.getStatus());
        cartHolder.Seats.setText(String.valueOf(cart.getSize()));
        cartHolder.Location.setText(cart.getLocation());

    }

    @NonNull
    @Override
    public RCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avail_cart, parent, false);
        return new RCartHolder(v);
    }

    class RCartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView Id;
        TextView Status;
        TextView Location;
        TextView Seats;
        Button btnRentCart;

        public RCartHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.tvCartId);
            Status = itemView.findViewById(R.id.tvStatus);
            Location = itemView.findViewById(R.id.tvLocation);
            Seats = itemView.findViewById(R.id.tvSeats);
            btnRentCart = itemView.findViewById(R.id.btnRent);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
            final String date = simpleDate.format(calendar.getTime());


            SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm a");
            final String time  = simpleTime.format(calendar.getTime());

            //Click on Rent Cart to set status as "Rented"
            btnRentCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DocumentReference userRef = db.collection("Users").document(user.getUid());

                                        String docId = getSnapshots().getSnapshot(getAdapterPosition()).getId();

                                        DocumentReference cartRef = db.collection("Golf Carts").document(docId);
                                        cartRef.update("Status", "Rented").addOnSuccessListener(new OnSuccessListener<Void>() {
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



                                        Map<String,Object> CartUse = new HashMap<>();
                                        CartUse.put("Cart_id", cartRef);
                                        CartUse.put("User_id",userRef);
                                        CartUse.put("Checked_out_time", time);
                                        CartUse.put("Checked_out_date", date);
                                        db.collection("Cart_use").add(CartUse)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(v.getContext(),"Cart rented successfully!",LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        makeText(v.getContext(), "Error while submitting. Please try again.", LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });








        }

        @Override
        public void onClick(View v) {

        }

    }
}
