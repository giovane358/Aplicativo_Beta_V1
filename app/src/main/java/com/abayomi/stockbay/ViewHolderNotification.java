package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderNotification extends RecyclerView.ViewHolder {

    TextView editNome, editQTD,editDt, editHr;
    View mViewNotification;

    public ViewHolderNotification(@NonNull View itemView) {
        super(itemView);

        mViewNotification = itemView;

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

        editNome = itemView.findViewById(R.id.editNome);
        editQTD = itemView.findViewById(R.id.editQTDS);
        editDt = itemView.findViewById(R.id.editDt);
        editHr = itemView.findViewById(R.id.editHr);


    }

    private ViewHolderNotification.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderNotification.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}

