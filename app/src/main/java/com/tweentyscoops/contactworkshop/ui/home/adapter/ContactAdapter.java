package com.tweentyscoops.contactworkshop.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private List<ContactModel> items = new ArrayList<>();


    public void setItems(List<ContactModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(ContactModel model) {
        items.add(0, model);
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.onBindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
