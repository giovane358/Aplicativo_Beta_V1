package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTotal extends RecyclerView.ViewHolder {

    TextView rTotal;
    View mViewHistoric;

    public ViewHolderTotal(@NonNull View itemView) {
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

        rTotal = (TextView) itemView.findViewById(R.id.rTotal);
    }

    private ViewHolderTotal.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderTotal.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
