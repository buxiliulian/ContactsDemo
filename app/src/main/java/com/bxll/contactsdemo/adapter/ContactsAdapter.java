package com.bxll.contactsdemo.adapter;

import android.database.Cursor;
import android.util.Log;
import android.widget.SectionIndexer;

import com.bxll.contactsdemo.ItemDecoration.StickyHeaderDecoration;
import com.github.promeg.pinyinhelper.Pinyin;

public class ContactsAdapter extends SimpleCursorAdapter implements StickyHeaderDecoration.Callback, SectionIndexer {
    private String mSortColumnName;

    public ContactsAdapter(Cursor cursor, int layoutId, String[] from, int[] to) {
        super(cursor, layoutId, from, to);
    }

    public void setSortColumnName(String name) {
        mSortColumnName = name;
    }

    @Override
    public boolean hasHeader(int adapterPosition) {
        boolean hasHeader = false;
        if (adapterPosition == 0) {
            hasHeader = true;
        } else if (adapterPosition > 0 && adapterPosition < getItemCount()) {
            if (!getFirstLetter(adapterPosition).equals(getFirstLetter(adapterPosition - 1))) {
                hasHeader = true;
            }
        }
        return hasHeader;
    }

    private String getFirstLetter(int adapterPosition) {
        mCursor.moveToPosition(adapterPosition);
        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(mSortColumnName));
        char firstChar = name.charAt(0);
        String firstLetter;
        if (Pinyin.isChinese(firstChar)) {
            firstLetter = Pinyin.toPinyin(firstChar).substring(0, 1);
        } else {
            firstLetter = String.valueOf(Character.toUpperCase(firstChar));
        }
        return firstLetter;
    }

    @Override
    public String getHeaderText(int adapterPosition) {
        return getFirstLetter(adapterPosition);
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
