package com.tweentyscoops.contactworkshop.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements ContactViewHolder.ContactViewHolderListener {

    public interface ContactAdapterListener {
        void onAddedItemContact();

        void onItemClick(String phoneNumber);

        void onItemClick(int position, ContactModel model);
    }

    private List<ContactModel> items = new ArrayList<>();
    private ContactAdapterListener listener;

    public void setListener(ContactAdapterListener listener) {
        this.listener = listener;
    }

    public void setItems(List<ContactModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(ContactModel model) {
        items.add(0, model);
        notifyItemChanged(0);
        if (listener != null) {
            listener.onAddedItemContact();
        }
    }

    public void updateItem(int positionItem, ContactModel model) {
        items.set(positionItem, model);
        notifyItemChanged(positionItem);
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
        holder.setListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(int position) {
        if (listener != null) {
            listener.onItemClick(items.get(position).getPhoneNumber());
        }
    }

    @Override
    public void onViewMoreDetail(int position) {
        if (listener != null) {
            listener.onItemClick(position, items.get(position));
        }
    }
}
