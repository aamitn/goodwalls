package com.app.goodwalls.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.goodwalls.R;
import com.app.goodwalls.database.prefs.SharedPref;

import java.util.ArrayList;

public class AdapterTags extends RecyclerView.Adapter<AdapterTags.ViewHolder> {

    Context context;
    ArrayList<String> arrayList;
    private OnItemClickListener mOnItemClickListener;
    SharedPref sharedPref;

    public interface OnItemClickListener {
        void onItemClick(View view, String keyword, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout lyt_parent;
        public TextView txt_tags;

        public ViewHolder(View view) {
            super(view);
            lyt_parent = view.findViewById(R.id.lyt_parent);
            txt_tags = view.findViewById(R.id.item_tags);
        }

    }

    public AdapterTags(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.sharedPref = new SharedPref(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final String keyword = arrayList.get(position).toLowerCase();

        holder.txt_tags.setText(keyword);

        holder.txt_tags.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, keyword, position);
            }
        });

        if (sharedPref.getIsDarkTheme()) {
            holder.lyt_parent.setBackgroundResource(R.drawable.bg_rounded_dark);
        } else {
            holder.lyt_parent.setBackgroundResource(R.drawable.bg_rounded_primary);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}