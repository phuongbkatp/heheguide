package com.haris.navigato.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.haris.navigato.FontUtil.Font;

public class PrimeRegularTextview extends AppCompatTextView {
    public PrimeRegularTextview(Context context) {
        super(context);
        setFont(context);
    }

    public PrimeRegularTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public PrimeRegularTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.prime_reg_font(context));
    }
}

