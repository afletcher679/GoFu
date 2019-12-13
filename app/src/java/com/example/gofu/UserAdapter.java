package com.example.gofu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "UserAdapter";

    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder userHolder, int i, @NonNull User user) {
        userHolder.txtRatId.setText(user.getRattlerID());
        userHolder.txtName.setText(user.getName());
        userHolder.txtBanStatus.setText(user.getBan_status());

    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserHolder(v);
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtRatId;
        TextView txtName;
        TextView txtBanStatus;
        Button btnBanUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            txtRatId = itemView.findViewById(R.id.tvRatId);
            txtName = itemView.findViewById(R.id.tvName);
            txtBanStatus = itemView.findViewById(R.id.tvStatus);
            btnBanUser = itemView.findViewById(R.id.btnBan);

            btnBanUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String docId = getSnapshots().getSnapshot(getAdapterPosition()).getId();
                    final DocumentReference UserRef = db.collection("Users").document(docId);
                    UserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                                assert documentSnapshot != null;
                                if(documentSnapshot.exists()){
                              String ban = documentSnapshot.getString("Ban_status");
                              if(ban.equals("Not Banned")){
                                  UserRef.update("Ban_status", "Banned").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                              }else if(ban.equals("Banned")) {
                                  UserRef.update("Ban_status", "Not Banned").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        }}
                    }});

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
