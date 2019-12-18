package com.bxll.contactsdemo.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 我们不知道ViewHolder需要保存哪些View，因此通过一个缓存，以id为索引保存所有View，从而可以方便高效获取到View。
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder {
    SparseArray<View> mViews;

    public static SimpleViewHolder newViewHolder(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new SimpleViewHolder(itemView);
    }

    private SimpleViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public View getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }
}
