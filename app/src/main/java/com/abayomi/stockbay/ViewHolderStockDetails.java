package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderStockDetails extends RecyclerView.ViewHolder {

    TextView rTitle, rQtd, rDataCompra, rValoreVenda, rValorCusto, rDescricao;
    View mView;


    public ViewHolderStockDetails(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //Item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onLongClick(v, getAdapterPosition());
                return false;
            }
        });

        //initialize views with model_layout.xml
        rTitle = itemView.findViewById(R.id.rTitle);
        rQtd = itemView.findViewById(R.id.rQtd);
        rValoreVenda = itemView.findViewById(R.id.rValoreVenda);
        rDataCompra = itemView.findViewById(R.id.rDTCompr);
        rValorCusto = itemView.findViewById(R.id.rValoreCusto);
        rDescricao = itemView.findViewById(R.id.rDesc);



    }
    private ViewHolderStockDetails.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderStockDetails.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
