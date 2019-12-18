package com.bxll.contactsdemo.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 需要加强学习的点
 * 1. 各种图形如何绘制，搞清楚参数的意思
 * 2. Path用法
 */
public class IndexerDecoration extends RecyclerView.ItemDecoration {

    private int mCellWidth, mCellHeight;

    // 字体大小: small: 14sp, medium: 18sp, Large:22sp
    private float mTextSize;

    private TextPaint mTextPaint;

    private Rect mTextBound;

    private static final String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 索引条右边间距
    private float mRightMargin; // dp
    private static final float DEFAULT_RIGHT_MARGIN = 8; // 8dp


    private Paint mOutlinePaint;
    private RectF mOutlineRect;
    private Path mOutlinePath;
    private static final int DEFAULT_OUTLINE_STROKE_WIDTH = 1; // dp

    public IndexerDecoration(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, displayMetrics);
        mTextPaint.setTextSize(mTextSize);

        // 获取绘制字母的正方形边长
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mCellWidth = mCellHeight = (int) Math.ceil(fontMetrics.bottom - fontMetrics.top);

        mTextBound = new Rect();

        mRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RIGHT_MARGIN, displayMetrics);

        // 初始化轮廓的画笔
        mOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setColor(Color.BLACK);
        mOutlinePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_OUTLINE_STROKE_WIDTH, displayMetrics));

        // 轮廓的Rect
        mOutlineRect = new RectF();
        mOutlineRect.right = mCellWidth;
        mOutlineRect.bottom = alphabet.length() * mCellHeight + mCellWidth;

        // 轮廓的Path
        mOutlinePath = new Path();
        mOutlinePath.addArc(mOutlineRect.left, mOutlineRect.top, mOutlineRect.width(), mCellHeight,
                180, 180);
        mOutlinePath.rLineTo(0, mOutlineRect.height() - mCellHeight);
        mOutlinePath.addArc(mOutlineRect.left, mOutlineRect.height() - mCellHeight,
                mOutlineRect.width(), mOutlineRect.height(), 0, 180);
        mOutlinePath.lineTo(0, mCellHeight / 2.f);
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        c.save();
        // 先位移Canvas到指定位置，这样就方便绘制
        c.translate(parent.getHeight() / 2.f - mOutlineRect.height() / 2.f,
                parent.getWidth() - mRightMargin - mCellWidth);

        // 绘制轮廓
        c.drawPath(mOutlinePath, mOutlinePaint);

        float top = mCellWidth / 2.f;
        // 绘制索引字母
        for (int i = 0; i < alphabet.length(); i++) {
            String section = String.valueOf(alphabet.charAt(i));
            mTextPaint.getTextBounds(section, 0, section.length(), mTextBound);
            c.drawText(section, mCellWidth / 2.f - mTextBound.width() / 2.f, top + mCellHeight / 2.f + mTextBound.height() / 2.f, mTextPaint);
            top += mCellHeight;
        }

        c.restore();
    }
}
