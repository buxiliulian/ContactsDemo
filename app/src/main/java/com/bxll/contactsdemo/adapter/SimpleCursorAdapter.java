package com.bxll.contactsdemo.adapter;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter extends ResourceCursorAdapter {
    private int mLayoutId;
    private String[] mFrom;
    private int[] mTo;

    public SimpleCursorAdapter(Cursor cursor, int layoutId, String[] from, int[] to) {
        super(cursor);
        mLayoutId = layoutId;
        mFrom = from;
        mTo = to;
    }

    @Override
    public int getLayoutId(int viewType) {
        return mLayoutId;
    }

    @Override
    public void onBindView(SimpleViewHolder holder, Cursor cursor) {
        if (mFrom.length != mTo.length) {
            return;
        }
        int count = mFrom.length;
        for (int i = 0; i < count; i++) {
            View view = holder.getView(mTo[i]);
            if (view instanceof TextView) {
                ((TextView) view).setText(cursor.getString(cursor.getColumnIndexOrThrow(mFrom[i])));
            } else if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(mFrom[i]))));
            }
        }
    }
}
