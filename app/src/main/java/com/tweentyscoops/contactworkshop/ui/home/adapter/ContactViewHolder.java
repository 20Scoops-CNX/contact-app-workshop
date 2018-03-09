package com.tweentyscoops.contactworkshop.ui.home.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.utils.ImageLoader;

class ContactViewHolder extends RecyclerView.ViewHolder {

    public interface ContactViewHolderListener {
        void onItemClick(int position);

        void onViewMoreDetail(int position);
    }

    private ImageView imgProfile;
    private AppCompatTextView tvName, tvPhoneNumber;
    private AppCompatImageView imgViewMore;
    private ContactViewHolderListener listener;

    ContactViewHolder(View itemView) {
        super(itemView);
        imgProfile = itemView.findViewById(R.id.imgProfile);
        tvName = itemView.findViewById(R.id.tvName);
        tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
        imgViewMore = itemView.findViewById(R.id.imgViewMore);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            }
        });
    }

    void setListener(ContactViewHolderListener listener) {
        this.listener = listener;
    }

    void onBindData(ContactModel model) {
        tvName.setText(model.getName());
        tvPhoneNumber.setText(model.getPhoneNumber());
        ImageLoader.url(imgProfile, model.getImageUrl());
        imgViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onViewMoreDetail(getAdapterPosition());
                }
            }
        });
    }
}
