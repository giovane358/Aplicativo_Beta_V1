package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderHistoric extends RecyclerView.ViewHolder {

    TextView rTitle, rQtd, rValorVenda, rValorTotal;
    View mViewHistoric;

    public ViewHolderHistoric(@NonNull View itemView) {
        super(itemView);

        mViewHistoric = itemView;

        //Item Click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //Item Click onLOng
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onLongClick(view, getAdapterPosition());
                return false;
            }
        });

        rTitle = itemView.findViewById(R.id.rTitle);
        rQtd = itemView.findViewById(R.id.rQtd);
        rValorTotal = itemView.findViewById(R.id.rValorTotal);
        rValorVenda = itemView.findViewById(R.id.rValorVenda);
    }


    private ViewHolderHistoric.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderHistoric.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
