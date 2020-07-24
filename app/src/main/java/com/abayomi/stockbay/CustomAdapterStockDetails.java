package com.abayomi.stockbay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.Model;
import com.abayomi.stockbay.Model.ModelNotification;
import com.abayomi.stockbay.Model.StockDetalhe;

import java.util.List;

public class CustomAdapterStockDetails extends RecyclerView.Adapter<ViewHolderStockDetails> {

    List<StockDetalhe> modelListDetailsDetails;

    public CustomAdapterStockDetails(List<StockDetalhe> modelListDetailsDetails) {
        this.modelListDetailsDetails = modelListDetailsDetails;
    }

    @NonNull
    @Override
    public ViewHolderStockDetails onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout_details, viewGroup, false);

        ViewHolderStockDetails viewHolderStockDetails = new ViewHolderStockDetails(itemView);
        viewHolderStockDetails.setOnClickListener(new ViewHolderStockDetails.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nome = modelListDetailsDetails.get(position).getNome();
                String qtd = modelListDetailsDetails.get(position).getQuantidade();
                String Vlvenda = modelListDetailsDetails.get(position).getVlCompra();
                String VlCusto = modelListDetailsDetails.get(position).getVlCusto();
                String DtCompra = modelListDetailsDetails.get(position).getDtCompra();
                String Desc     = modelListDetailsDetails.get(position).getDesc();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        return viewHolderStockDetails;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStockDetails viewHolderStockDetails, int i) {
        viewHolderStockDetails.rTitle.setText(modelListDetailsDetails.get(i).getNome());
        viewHolderStockDetails.rQtd.setText(modelListDetailsDetails.get(i).getQuantidade());
        viewHolderStockDetails.rDataCompra.setText(modelListDetailsDetails.get(i).getDtCompra());
        viewHolderStockDetails.rValorCusto.setText(modelListDetailsDetails.get(i).getVlCusto());
        viewHolderStockDetails.rValoreVenda.setText(modelListDetailsDetails.get(i).getVlCompra());
        viewHolderStockDetails.rDescricao.setText(modelListDetailsDetails.get(i).getDesc());

    }

    @Override
    public int getItemCount() {
        return modelListDetailsDetails.size();
    }
}
