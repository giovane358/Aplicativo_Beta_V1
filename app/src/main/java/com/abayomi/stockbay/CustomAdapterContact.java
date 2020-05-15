package com.abayomi.stockbay;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterContact extends RecyclerView.Adapter<ViewHolderContact> {

    List<ModelContact> modelContactList;

    public CustomAdapterContact(List<ModelContact> modelContactList){
        this.modelContactList = modelContactList;
    }

    @NonNull
    @Override
    public ViewHolderContact onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_contact_layout, viewGroup, false);

        ViewHolderContact viewHolderContact = new ViewHolderContact(itemView);
        viewHolderContact.setOnClickListener(new ViewHolderContact.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nome = modelContactList.get(position).getNome();
                String fone = modelContactList.get(position).getFone();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        return viewHolderContact;
    }

    public void onBindViewHolder(@NonNull ViewHolderContact viewHolderContact, int i) {
        viewHolderContact.editNome.setText(modelContactList.get(i).getNome());
        viewHolderContact.editFone.setText(modelContactList.get(i).getFone());
    }


    public int getItemCount() {
        return modelContactList.size();
    }


}
