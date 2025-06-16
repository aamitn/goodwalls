package com.app.goodwalls.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.goodwalls.R;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Menu;
import com.app.goodwalls.util.Constant;
import com.app.goodwalls.util.Tools;

import java.util.List;

public class AdapterFeatured extends RecyclerView.Adapter<AdapterFeatured.ViewHolder> {

    private List<Menu> items;
    Context context;
    private OnItemClickListener mOnItemClickListener;
    SharedPref sharedPref;
    private int clickedItemPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(View view, Menu menu, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterFeatured(Context context, List<Menu> items) {
        this.items = items;
        this.context = context;
        this.sharedPref = new SharedPref(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLabel;
        public LinearLayout lytLabel;

        public ViewHolder(View v) {
            super(v);
            txtLabel = v.findViewById(R.id.txt_label);
            lytLabel = v.findViewById(R.id.lyt_label);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_chips, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Menu menu = items.get(position);
        if (position == 0) {
            Tools.setMargins(context, holder.lytLabel, 10, 0, 8, 0);
        } else {
            Tools.setMargins(context, holder.lytLabel, 0, 0, 8, 0);
        }
        holder.txtLabel.setText(menu.menu_title);

        holder.lytLabel.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, menu, position);
                clickedItemPosition = position;
                Constant.CURRENT_POSITION = position;
                new Handler(Looper.getMainLooper()).postDelayed(this::notifyDataSetChanged, 10);
            }
        });

        if (sharedPref.getIsDarkTheme()) {
            if (clickedItemPosition == position) {
                holder.lytLabel.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_chips_default));
                holder.txtLabel.setTextColor(ContextCompat.getColor(context, R.color.color_light_text_default));
            } else {
                holder.lytLabel.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_chips_dark));
                holder.txtLabel.setTextColor(ContextCompat.getColor(context, R.color.color_dark_text_default));
            }
        } else {
            if (clickedItemPosition == position) {
                holder.lytLabel.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_chips_dark));
                holder.txtLabel.setTextColor(ContextCompat.getColor(context, R.color.color_dark_text_default));
            } else {
                holder.lytLabel.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_chips_default));
                holder.txtLabel.setTextColor(ContextCompat.getColor(context, R.color.color_light_text_default));
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListData(List<Menu> items) {
        this.items = items;
        //items.add(0, "");
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetListData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}