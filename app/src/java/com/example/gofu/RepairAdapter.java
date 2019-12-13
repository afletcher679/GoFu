package com.example.gofu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RepairAdapter extends FirestoreRecyclerAdapter<Repair, RepairAdapter.RepairHolder> {

    public RepairAdapter(@NonNull FirestoreRecyclerOptions<Repair> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RepairHolder repairHolder, int i, @NonNull Repair repair) {
        repairHolder.RepairNum.setText(String.valueOf(repair.getReportNum()));
        repairHolder.RepairDate.setText(repair.getReportDate());
        repairHolder.RepairStatus.setText(repair.getStatus());
        repairHolder.RepairIssues.setText(repair.getIssues());
    }

    @NonNull
    @Override
    public RepairHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_repair, parent, false);
        return new RepairHolder(v);
    }

    class RepairHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       TextView RepairNum;
       TextView RepairDate;
       TextView RepairStatus;
       TextView RepairIssues;

       public RepairHolder(@NonNull View itemView) {
           super(itemView);

           RepairNum = itemView.findViewById(R.id.tvRepairNum);
           RepairDate = itemView.findViewById(R.id.tvRepairDate);
           RepairStatus = itemView.findViewById(R.id.tvRepairStatus);
           RepairIssues = itemView.findViewById(R.id.tvIssues);
       }
        @Override
        public void onClick(View v) {

        }
   }
}
