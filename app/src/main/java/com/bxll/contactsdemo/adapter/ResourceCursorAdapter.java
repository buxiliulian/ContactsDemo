package com.bxll.contactsdemo.adapter;

import android.database.Cursor;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * 这层关于layoutId的抽象，因为确定了ViewHolder类型。
 */
public abstract class ResourceCursorAdapter extends CursorAdapter<SimpleViewHolder> {
    public ResourceCursorAdapter(Cursor cursor) {
        super(cursor);
    }

    /**
     * 根据类型获取布局ID。
     *
     * @param viewType 子View类型。
     * @return 返回相应类型的布局ID。
     */
    public abstract int getLayoutId(int viewType);

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SimpleViewHolder.newViewHolder(parent, getLayoutId(viewType));
    }
}
