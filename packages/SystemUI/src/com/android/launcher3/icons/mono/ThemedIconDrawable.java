package com.android.launcher3.icons.mono;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ThemedIconDrawable extends FastBitmapDrawable {
    public static final Companion Companion = new Companion(null);
    public final Bitmap bgBitmap;
    public final BlendModeColorFilter bgFilter;
    public final int colorBg;
    public final int colorFg;
    public final Paint mBgPaint;
    public final BlendModeColorFilter monoFilter;
    public final Bitmap monoIcon;
    public final Paint monoPaint;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ThemedConstantState extends FastBitmapDrawable.FastBitmapConstantState {
        public final int colorBg;
        public final int colorFg;
        public final Bitmap mono;
        public final Bitmap whiteShadowLayer;

        public ThemedConstantState(BitmapInfo bitmapInfo, Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
            super(bitmapInfo);
            this.mono = bitmap;
            this.whiteShadowLayer = bitmap2;
            this.colorBg = i;
            this.colorFg = i2;
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable.FastBitmapConstantState
        public final FastBitmapDrawable createDrawable() {
            return new ThemedIconDrawable(this);
        }
    }

    public ThemedIconDrawable(ThemedConstantState themedConstantState) {
        super(themedConstantState.mBitmapInfo);
        int i = themedConstantState.colorFg;
        this.colorFg = i;
        int i2 = themedConstantState.colorBg;
        this.colorBg = i2;
        this.monoIcon = themedConstantState.mono;
        BlendMode blendMode = BlendMode.SRC_IN;
        BlendModeColorFilter blendModeColorFilter = new BlendModeColorFilter(i, blendMode);
        this.monoFilter = blendModeColorFilter;
        Paint paint = new Paint(3);
        paint.setColorFilter(blendModeColorFilter);
        this.monoPaint = paint;
        this.bgBitmap = themedConstantState.whiteShadowLayer;
        BlendModeColorFilter blendModeColorFilter2 = new BlendModeColorFilter(i2, blendMode);
        this.bgFilter = blendModeColorFilter2;
        Paint paint2 = new Paint(3);
        paint2.setColorFilter(blendModeColorFilter2);
        this.mBgPaint = paint2;
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final void drawInternal(Canvas canvas, Rect rect) {
        canvas.drawBitmap(this.bgBitmap, (Rect) null, rect, this.mBgPaint);
        canvas.drawBitmap(this.monoIcon, (Rect) null, rect, this.monoPaint);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final FastBitmapDrawable.FastBitmapConstantState newConstantState() {
        return new ThemedConstantState(this.mBitmapInfo, this.monoIcon, this.bgBitmap, this.colorBg, this.colorFg);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final void updateFilter() {
        super.updateFilter();
        this.mBgPaint.setAlpha(255);
        this.mBgPaint.setColorFilter(this.bgFilter);
        this.monoPaint.setAlpha(255);
        this.monoPaint.setColorFilter(this.monoFilter);
    }
}
