package com.abayomi.stockbay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.ModelHistoric;
import com.abayomi.stockbay.Model.ModelNotification;

import java.util.List;

public class CustomAdapterHistoric extends RecyclerView.Adapter<ViewHolderHistoric> {

    List<ModelHistoric> modelHistoricList;

    public CustomAdapterHistoric(List<ModelHistoric> modelHistoricList) {
        this.modelHistoricList = modelHistoricList;
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

                String nome = modelHistoricList.get(position).getNome();
                String Quantidade = modelHistoricList.get(position).getQuantidade();
                String ValorVenda = modelHistoricList.get(position).getValorVenda();
                String ValorTotal = modelHistoricList.get(position).getValorTotal();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        });
        return viewHolderHistoric;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistoric viewHolderHistoric, int i) {
        viewHolderHistoric.rTitle.setText(modelHistoricList.get(i).getNome());
        viewHolderHistoric.rQtd.setText(modelHistoricList.get(i).getQuantidade());
        viewHolderHistoric.rValorVenda.setText(modelHistoricList.get(i).getValorVenda());
        viewHolderHistoric.rValorTotal.setText(modelHistoricList.get(i).getValorTotal());
    }

    @Override
    public int getItemCount() {
        return modelHistoricList.size();
    }
}

