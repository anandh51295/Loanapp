package com.example.loanapp.adopter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loanapp.R;
import com.example.loanapp.model.CustomerModel;
import com.example.loanapp.page.LoanActivity;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;

public class CustomerAdopter  extends RecyclerView.Adapter<CustomerAdopter.ViewHolder> implements Filterable {
    private ArrayList<CustomerModel> data;
    private ArrayList<CustomerModel> mFilteredList;

    public CustomerAdopter(ArrayList data) {
        this.data = data;
        mFilteredList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText("Name: "+mFilteredList.get(i).getCustomername());
        String custid=String.valueOf(mFilteredList.get(i).getCustomerid());
        viewHolder.ci.setText("Cid:"+custid);
        viewHolder.cdob.setText("DOB: "+mFilteredList.get(i).getDob());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = data;
                } else {

                    ArrayList<CustomerModel> filteredList = new ArrayList<>();

                    for (CustomerModel cust : data) {

                        if (cust.getCustomername().toLowerCase().contains(charString) || String.valueOf(cust.getCustomerid()).toLowerCase().contains(charString) || cust.getDob().toLowerCase().contains(charString)) {

                            filteredList.add(cust);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,ci,cdob;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.l_c);
            ci=itemView.findViewById(R.id.tvnc);
            cdob=itemView.findViewById(R.id.tvnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoanActivity.class);
                    intent.putExtra("customerid",mFilteredList.get(getLayoutPosition()).getCustomerid());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
