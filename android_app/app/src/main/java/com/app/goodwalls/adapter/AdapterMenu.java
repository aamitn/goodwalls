package com.app.goodwalls.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder> {

    private List<Menu> items;
    Context context;
    private OnItemClickListener mOnItemClickListener;
    private int clickedItemPosition = -1;
    SharedPref sharedPref;

    public interface OnItemClickListener {
        void onItemClick(View view, Menu obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterMenu(Context context, List<Menu> items) {
        this.items = items;
        this.context = context;
        this.sharedPref = new SharedPref(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView menuTitle;
        public ImageView menuIcon;
        public LinearLayout lytItem;
        public LinearLayout lytParent;

        public ViewHolder(View v) {
            super(v);
            menuTitle = v.findViewById(R.id.menu_title);
            menuIcon = v.findViewById(R.id.menu_icon);
            lytItem = v.findViewById(R.id.lyt_item);
            lytParent = v.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"RecyclerView", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Menu obj = items.get(position);

        holder.menuTitle.setText(obj.menu_title);

        holder.lytParent.setOnClickListener(view -> {
            Tools.postDelayed(()-> {
                Constant.LAST_SELECTED_ITEM_POSITION = position;
                clickedItemPosition = Constant.LAST_SELECTED_ITEM_POSITION;
                Constant.ORDER = obj.menu_order;
                Constant.FILTER = obj.menu_filter;
                notifyDataSetChanged();
            }, 200);

        });

        if (clickedItemPosition == position) {
            holder.menuTitle.setTextColor(ContextCompat.getColor(context, R.color.color_light_primary));
            holder.menuIcon.setImageResource(R.drawable.ic_radio_button_on);
            holder.menuIcon.setColorFilter(context.getResources().getColor(R.color.color_light_primary), PorterDuff.Mode.SRC_IN);
        } else {
            holder.menuIcon.setImageResource(R.drawable.ic_radio_button_off);
            if (sharedPref.getIsDarkTheme()) {
                holder.menuTitle.setTextColor(ContextCompat.getColor(context, R.color.color_dark_text_default));
                holder.menuIcon.setColorFilter(context.getResources().getColor(R.color.color_dark_text_default), PorterDuff.Mode.SRC_IN);
            } else {
                holder.menuTitle.setTextColor(ContextCompat.getColor(context, R.color.color_light_text_default));
                holder.menuIcon.setColorFilter(context.getResources().getColor(R.color.color_light_text_default), PorterDuff.Mode.SRC_IN);
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListData(List<Menu> items) {
        this.items = items;
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