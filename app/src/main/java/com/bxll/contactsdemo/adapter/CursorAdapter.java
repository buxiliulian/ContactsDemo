package com.bxll.contactsdemo.adapter;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 这层是关于Cursor的抽象。
 */
public abstract class CursorAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected Cursor mCursor;

    public CursorAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != cursor) {
            mCursor = cursor;
            // 这里只是简单的全局刷新，如何做到局部刷新
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        mCursor.moveToPosition(position);
        onBindView(holder, mCursor);
    }

    public abstract void onBindView(T holder, Cursor cursor);

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

}
