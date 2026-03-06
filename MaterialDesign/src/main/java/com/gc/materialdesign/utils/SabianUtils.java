package com.gc.materialdesign.utils;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.gc.materialdesign.R;


public class SabianUtils {
    public static void resolveRealBackgroundColor(View view, AttributeSet attrs) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.CustomAttributes);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomAttributes_sabian_backgroundColor || attr == R.styleable.CustomAttributes_android_background) {
                int realBgColor = a.getColor(attr, -1);
                if (realBgColor != -1)
                    view.setBackgroundColor(realBgColor);
            }
        }
        a.recycle();
    }
}
