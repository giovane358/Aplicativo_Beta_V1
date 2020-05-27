package com.abayomi.stockbay;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
{

    TextView rTitle, rQtd, rDataCompra, rValoreVenda, rValorCusto, rDescricao;
    View mView;
    CardView cardView;

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
        rValoreVenda = itemView.findViewById(R.id.rValoreVenda);
        cardView = itemView.findViewById(R.id.cardView);
        cardView.setOnCreateContextMenuListener(this);

    }
    private ViewHolder.ClickListener mClickListener;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(this.getAdapterPosition(), 121, 0, "Editar");
        menu.add(this.getAdapterPosition(), 122, 1, "Excluir");
        menu.add(this.getAdapterPosition(), 123, 2, "Detalhes");

    }

    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
