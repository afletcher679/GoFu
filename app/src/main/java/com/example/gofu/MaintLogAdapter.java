package com.example.gofu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MaintLogAdapter extends FirestoreRecyclerAdapter<Repair, MaintLogAdapter.RepairLogHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String docId;

    public MaintLogAdapter(@NonNull FirestoreRecyclerOptions<Repair> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RepairLogHolder repairLogHolder, int i, @NonNull Repair repair) {
        //DocumentReference CartRef = db.collection("Golf Carts").document().getId();
        //repairLogHolder.CartID.setText(String.valueOf(db.collection("Golf Carts").document(docId)));
        //repairLogHolder.CartPath.
        repairLogHolder.RepairNum.setText(String.valueOf(repair.getReportNum()));
        repairLogHolder.RepairDate.setText(repair.getReportDate());
        repairLogHolder.RepairStatus.setText(repair.getStatus());
        repairLogHolder.RepairIssues.setText(repair.getIssues());
    }

    @NonNull
    @Override
    public RepairLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repair, parent, false);
        return new RepairLogHolder(v);
    }

    class RepairLogHolder extends RecyclerView.ViewHolder{
        TextView CartID;
        TextView RepairNum;
        TextView RepairDate;
        TextView RepairStatus;
        TextView RepairIssues;
        String CartPath;

        public RepairLogHolder(@NonNull View itemView) {
            super(itemView);

            CartID = itemView.findViewById(R.id.tvCart);
            RepairNum = itemView.findViewById(R.id.tvRepairNum);
            RepairDate = itemView.findViewById(R.id.tvRepairDate);
            RepairStatus = itemView.findViewById(R.id.tvRepairStatus);
            RepairIssues = itemView.findViewById(R.id.tvIssues);

            //docId = getSnapshots().getSnapshot(getAdapterPosition()).getId();
            //CartID.setText(String.valueOf(db.collection("Golf Carts").document()));

        }
    }
}
