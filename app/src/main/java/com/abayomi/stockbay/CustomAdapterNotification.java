package com.abayomi.stockbay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abayomi.stockbay.Model.ModelNotification;

import java.util.List;

public class CustomAdapterNotification extends RecyclerView.Adapter<ViewHolderNotification> {

    List<ModelNotification> modelNotificationList;

    public CustomAdapterNotification(List<ModelNotification> modelNotificationList) {
        this.modelNotificationList = modelNotificationList;
    }

    @NonNull
    @Override
    public ViewHolderNotification onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout_notification, viewGroup, false);

        ViewHolderNotification viewHolderNotification = new ViewHolderNotification(itemView);
        viewHolderNotification.setOnClickListener(new ViewHolderNotification.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nome = modelNotificationList.get(position).getNome();
                String Quantidade = modelNotificationList.get(position).getQuantidade();
                String DtInclusao = modelNotificationList.get(position).getDtIncluso();
                String HrInclusao = modelNotificationList.get(position).getHrInclusao();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        });
        return viewHolderNotification;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotification ViewHolderNotification, int i) {
        ViewHolderNotification.editNome.setText(modelNotificationList.get(i).getNome());
        ViewHolderNotification.editQTD.setText(modelNotificationList.get(i).getQuantidade());
        ViewHolderNotification.editDt.setText(modelNotificationList.get(i).getDtIncluso());
        ViewHolderNotification.editHr.setText(modelNotificationList.get(i).getHrInclusao());
    }

    @Override
    public int getItemCount() {
        return modelNotificationList.size();
    }
}