package com.abayomi.stockbay;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderContact extends RecyclerView.ViewHolder {

    TextView editNome, editFone;
    View mView;

    public ViewHolderContact(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //Item Click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onLongClick(view, getAdapterPosition());
                return false;
            }
        });
        //initialize views with model_layout.xml
        editNome = itemView.findViewById(R.id.editNome);
        editFone = itemView.findViewById(R.id.editFone);
    }

    private ViewHolderContact.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderContact.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
