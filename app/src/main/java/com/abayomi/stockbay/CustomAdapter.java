package com.abayomi.stockbay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {


    List<Model> modelList;
    List<ModelContact> modelContactList;

    public CustomAdapter(List<Model> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

       ViewHolder viewHolder = new ViewHolder(itemView);
       viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
           @Override
           public void onItemClick(View view, int position) {



               String nome = modelList.get(position).getNome();
               String qtd = modelList.get(position).getQuantidade();
               String Vlvenda = modelList.get(position).getValoreVenda();


           }

           @Override
           public void onLongClick(View view, int position) {

           }
       });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        viewholder.rTitle.setText(modelList.get(i).getNome());
        viewholder.rQtd.setText(modelList.get(i).getQuantidade());
        viewholder.rValoreVenda.setText(modelList.get(i).getValoreVenda());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
