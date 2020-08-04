package com.abayomi.stockbay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.ModelTotal;

import java.util.List;

public class CustomAdapterHistoric extends RecyclerView.Adapter<ViewHolderHistoric> {

    List<ModelTotal> modelTotalList;

    public CustomAdapterHistoric(List<ModelTotal> modelTotalList) {
        this.modelTotalList = modelTotalList;
    }

    @NonNull
    @Override
    public ViewHolderHistoric onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_historic, viewGroup, false);

        ViewHolderHistoric viewHolderHistoric = new ViewHolderHistoric(itemView);
        viewHolderHistoric.setOnClickListener(new ViewHolderHistoric.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String Total = modelTotalList.get(position).getTotal();
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        });
        return viewHolderHistoric;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistoric viewHolderHistoric, int i) {
        viewHolderHistoric.rTotal.setText(modelTotalList.get(i).getTotal());
    }

    @Override
    public int getItemCount() {
        return modelTotalList.size();
    }
}


