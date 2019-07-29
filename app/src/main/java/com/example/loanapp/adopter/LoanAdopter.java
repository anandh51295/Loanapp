package com.example.loanapp.adopter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loanapp.R;
import com.example.loanapp.model.LoanModel;

import java.util.ArrayList;

public class LoanAdopter extends RecyclerView.Adapter<LoanAdopter.ViewHolder>  {
    private ArrayList<LoanModel> data;

    public LoanAdopter(ArrayList data) {
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loan_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText("A/C: "+data.get(i).getAccountid());
        String bal=String.valueOf(data.get(i).getAmount());
        viewHolder.lamt.setText("Bal: "+ bal);
        viewHolder.ladate.setText("Date: "+data.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,lamt,ladate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.lc);
            lamt = itemView.findViewById(R.id.lbal);
            ladate = itemView.findViewById(R.id.ldate);
        }
    }
}
