package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView rTitle, rQtd, rDataCompra, rValoreVenda, rValorCusto, rDescricao,editNome,editFone;
    View mView;

    public ViewHolder(@NonNull View itemView) {
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
        rDataCompra = itemView.findViewById(R.id.rDataCompra);
        rValoreVenda = itemView.findViewById(R.id.rValoreVenda);
        rValorCusto = itemView.findViewById(R.id.rValorCusto);
        rDescricao = itemView.findViewById(R.id.rDescricao);


        editNome = itemView.findViewById(R.id.editNome);
        editFone = itemView.findViewById(R.id.editFone);
    }
    private ViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
