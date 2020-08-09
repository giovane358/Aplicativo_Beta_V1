package com.abayomi.stockbay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.ModelTotal;

import java.util.List;

public class CustomAdapterTotal extends RecyclerView.Adapter<ViewHolderTotal> {

    List<ModelTotal> modelTotalList;

    public CustomAdapterTotal(List<ModelTotal> modelTotalList) {
        this.modelTotalList = modelTotalList;
    }

    @NonNull
    @Override
    public ViewHolderTotal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_total, viewGroup, false);

        ViewHolderTotal viewHolderTotal = new ViewHolderTotal(itemView);
        viewHolderTotal.setOnClickListener(new ViewHolderTotal.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Double Total = modelTotalList.get(position).getTotal();
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        });
        return viewHolderTotal;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTotal viewHolderTotal, int i) {
        viewHolderTotal.rTotal.setText(modelTotalList.get(i).getTotal().toString());
    }

    @Override
    public int getItemCount() {
        return modelTotalList.size();
    }
}


