package com.abayomi.stockbay;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderHistoric extends RecyclerView.ViewHolder {

    TextView rTotal;
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

        rTotal = itemView.findViewById(R.id.rTotal);
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
