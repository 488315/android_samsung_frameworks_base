package com.android.systemui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HardwareBgDrawable extends LayerDrawable {
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
        int i2 = bounds.bottom;
        int i3 = i > i2 ? i2 : i;
        if (this.mRoundTop) {
            this.mLayers[0].setBounds(bounds.left, i, bounds.right, i3);
        } else {
            this.mLayers[1].setBounds(bounds.left, i3, bounds.right, i2);
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HardwareBgDrawable(boolean r5, boolean r6, android.content.Context r7) {
        /*
            r4 = this;
            if (r6 == 0) goto L6
            r0 = 2131234991(0x7f0810af, float:1.8086163E38)
            goto L9
        L6:
            r0 = 2131234989(0x7f0810ad, float:1.808616E38)
        L9:
            r1 = 0
            r2 = 2
            r3 = 1
            if (r5 == 0) goto L25
            android.graphics.drawable.Drawable[] r6 = new android.graphics.drawable.Drawable[r2]
            android.graphics.drawable.Drawable r2 = r7.getDrawable(r0)
            android.graphics.drawable.Drawable r2 = r2.mutate()
            r6[r1] = r2
            android.graphics.drawable.Drawable r0 = r7.getDrawable(r0)
            android.graphics.drawable.Drawable r0 = r0.mutate()
            r6[r3] = r0
            goto L45
        L25:
            android.graphics.drawable.Drawable[] r2 = new android.graphics.drawable.Drawable[r2]
            android.graphics.drawable.Drawable r0 = r7.getDrawable(r0)
            android.graphics.drawable.Drawable r0 = r0.mutate()
            r2[r1] = r0
            if (r6 == 0) goto L37
            r6 = 2131235002(0x7f0810ba, float:1.8086186E38)
            goto L3a
        L37:
            r6 = 2131234990(0x7f0810ae, float:1.8086161E38)
        L3a:
            android.graphics.drawable.Drawable r6 = r7.getDrawable(r6)
            android.graphics.drawable.Drawable r6 = r6.mutate()
            r2[r3] = r6
            r6 = r2
        L45:
            r0 = r6[r3]
            r1 = 16843827(0x1010433, float:2.369657E-38)
            android.content.res.ColorStateList r7 = com.android.settingslib.Utils.getColorAttr(r1, r7)
            r0.setTintList(r7)
            r4.<init>(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.HardwareBgDrawable.<init>(boolean, boolean, android.content.Context):void");
    }
}
