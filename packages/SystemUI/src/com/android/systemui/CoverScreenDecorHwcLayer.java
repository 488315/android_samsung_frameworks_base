package com.android.systemui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.util.IndentingPrintWriter;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CoverScreenDecorHwcLayer extends ScreenDecorHwcLayer {
    public final int mDisplayHeight;
    public final int mDisplayWidth;

    public CoverScreenDecorHwcLayer(Context context, DisplayDecorationSupport displayDecorationSupport, boolean z) {
        super(context, displayDecorationSupport, z);
        Display display = context.getDisplay();
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        this.mDisplayWidth = displayInfo.getNaturalWidth();
        Display display2 = context.getDisplay();
        DisplayInfo displayInfo2 = new DisplayInfo();
        display2.getDisplayInfo(displayInfo2);
        this.mDisplayHeight = displayInfo2.getNaturalHeight();
    }

    @Override // com.android.systemui.ScreenDecorHwcLayer
    public final void drawRoundedCorners(Canvas canvas) {
        if (this.hasTopRoundedCorner) {
            canvas.save();
            canvas.rotate((360 - (this.displayRotation * 90)) % 360);
            canvas.translate(getRoundedCornerTranslationX(r0), getRoundedCornerTranslationY(r0));
            drawRoundedCorner(canvas, this.roundedCornerDrawableTop, this.roundedCornerTopSize);
            canvas.restore();
        }
    }

    @Override // com.android.systemui.ScreenDecorHwcLayer, com.android.systemui.DisplayCutoutBaseView
    public final void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("CoverScreenDecorHwcLayer:");
        super.dump(printWriter);
        asIndenting.println("this=" + this);
        asIndenting.println("transparentRect=" + this.transparentRect);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("hasTopRoundedCorner=", this.hasTopRoundedCorner, asIndenting);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("hasBottomRoundedCorner=", this.hasBottomRoundedCorner, asIndenting);
        asIndenting.println("roundedCornerTopSize=" + this.roundedCornerTopSize);
        asIndenting.println("roundedCornerBottomSize=" + this.roundedCornerBottomSize);
        asIndenting.decreaseIndent();
    }

    @Override // com.android.systemui.ScreenDecorHwcLayer
    public final void updateRoundedCornerDrawableBounds() {
        Drawable drawable = this.roundedCornerDrawableTop;
        if (drawable != null && drawable != null) {
            drawable.setBounds(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        }
        invalidate();
    }

    @Override // com.android.systemui.ScreenDecorHwcLayer
    public final void updateRoundedCornerExistenceAndSize(int i, int i2, boolean z, boolean z2) {
        if (this.hasTopRoundedCorner == z && this.hasBottomRoundedCorner == z2 && this.roundedCornerTopSize == i && this.roundedCornerBottomSize == i2) {
            return;
        }
        this.hasTopRoundedCorner = z;
        this.hasBottomRoundedCorner = z2;
        this.roundedCornerTopSize = i;
        this.roundedCornerBottomSize = i2;
        updateRoundedCornerDrawableBounds();
        requestLayout();
    }
}
