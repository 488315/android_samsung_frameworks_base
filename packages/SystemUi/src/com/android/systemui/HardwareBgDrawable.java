package com.android.systemui;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.android.settingslib.Utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HardwareBgDrawable extends LayerDrawable {
    public final Drawable[] mLayers;
    public final Paint mPaint;
    public final boolean mRoundTop;

    public HardwareBgDrawable(boolean z, Drawable[] drawableArr) {
        super(drawableArr);
        this.mPaint = new Paint();
        if (drawableArr.length != 2) {
            throw new IllegalArgumentException("Need 2 layers");
        }
        this.mRoundTop = z;
        this.mLayers = drawableArr;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = bounds.top;
        int i2 = i + 0;
        int i3 = bounds.bottom;
        if (i2 > i3) {
            i2 = i3;
        }
        if (this.mRoundTop) {
            this.mLayers[0].setBounds(bounds.left, i, bounds.right, i2);
        } else {
            this.mLayers[1].setBounds(bounds.left, i2, bounds.right, i3);
        }
        if (this.mRoundTop) {
            this.mLayers[1].draw(canvas);
            this.mLayers[0].draw(canvas);
        } else {
            this.mLayers[0].draw(canvas);
            this.mLayers[1].draw(canvas);
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public HardwareBgDrawable(boolean z, boolean z2, Context context) {
        this(z, r6);
        Drawable[] drawableArr;
        int i = z2 ? R.drawable.rounded_bg_full : R.drawable.rounded_bg;
        if (z) {
            drawableArr = new Drawable[]{context.getDrawable(i).mutate(), context.getDrawable(i).mutate()};
        } else {
            Drawable[] drawableArr2 = new Drawable[2];
            drawableArr2[0] = context.getDrawable(i).mutate();
            drawableArr2[1] = context.getDrawable(z2 ? R.drawable.rounded_full_bg_bottom : R.drawable.rounded_bg_bottom).mutate();
            drawableArr = drawableArr2;
        }
        drawableArr[1].setTintList(Utils.getColorAttr(R.attr.colorPrimary, context));
    }
}
