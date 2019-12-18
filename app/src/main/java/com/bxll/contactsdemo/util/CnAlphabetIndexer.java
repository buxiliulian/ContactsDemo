package com.bxll.contactsdemo.util;

import android.database.Cursor;
import android.widget.AlphabetIndexer;

import com.github.promeg.pinyinhelper.Pinyin;

import java.text.Collator;

/**
 * 1. 支持中文汉字和英文字母的比较。
 * 2. getPositionForSection()返回是一个近似的位置，例如如果手指按在了索引F上，然而联系人中没有F首字母的联系人，那么会往前寻找最接近的联系人首字母。后面可以根据情况改变。
 * 3. getSectionForPosition()暂时还没有功能用到，但是实际中可能会用到。
 */
public class CnAlphabetIndexer extends AlphabetIndexer {

    private final Collator mCollator;

    /**
     * Constructs the indexer.
     *
     * @param cursor            the cursor containing the data set
     * @param sortedColumnIndex the column number in the cursor that is sorted
     *                          alphabetically
     * @param alphabet          string containing the alphabet, with space as the first character.
     *                          For example, use the string " ABCDEFGHIJKLMNOPQRSTUVWXYZ" for English indexing.
     *                          The characters must be uppercase and be sorted in ascii/unicode order. Basically
     */
    public CnAlphabetIndexer(Cursor cursor, int sortedColumnIndex, CharSequence alphabet) {
        super(cursor, sortedColumnIndex, alphabet);
        mCollator = java.text.Collator.getInstance();
        mCollator.setStrength(java.text.Collator.PRIMARY);
    }

    /**
     * 由于需要考虑中文汉字与字母的比较，因此复写这个方法。
     */
    @Override
    protected int compare(String word, String letter) {
        final String firstLetter;
        if (word.length() == 0) {
            firstLetter = " ";
        } else {
            char firstChar = word.charAt(0);
            if (Pinyin.isChinese(firstChar)) {
                firstLetter = Pinyin.toPinyin(firstChar).substring(0, 1);
            } else {
                firstLetter = String.valueOf(firstChar);
            }
        }
        return mCollator.compare(firstLetter, letter);
    }
}
