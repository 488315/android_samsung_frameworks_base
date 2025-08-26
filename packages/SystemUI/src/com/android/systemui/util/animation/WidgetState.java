package com.android.systemui.util.animation;

import android.view.View;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class WidgetState {
    public static final int $stable = 8;
    private float alpha;
    private boolean gone;
    private int height;
    private int measureHeight;
    private int measureWidth;
    private float scale;
    private int width;
    private float x;
    private float y;

    public WidgetState() {
        this(0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 511, null);
    }

    public static /* synthetic */ WidgetState copy$default(WidgetState widgetState, float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            f = widgetState.x;
        }
        if ((i5 & 2) != 0) {
            f2 = widgetState.y;
        }
        if ((i5 & 4) != 0) {
            i = widgetState.width;
        }
        if ((i5 & 8) != 0) {
            i2 = widgetState.height;
        }
        if ((i5 & 16) != 0) {
            i3 = widgetState.measureWidth;
        }
        if ((i5 & 32) != 0) {
            i4 = widgetState.measureHeight;
        }
        if ((i5 & 64) != 0) {
            f3 = widgetState.alpha;
        }
        if ((i5 & 128) != 0) {
            f4 = widgetState.scale;
        }
        if ((i5 & 256) != 0) {
            z = widgetState.gone;
        }
        float f5 = f4;
        boolean z2 = z;
        int i6 = i4;
        float f6 = f3;
        int i7 = i3;
        int i8 = i;
        return widgetState.copy(f, f2, i8, i2, i7, i6, f6, f5, z2);
    }

    public final float component1() {
        return this.x;
    }

    public final float component2() {
        return this.y;
    }

    public final int component3() {
        return this.width;
    }

    public final int component4() {
        return this.height;
    }

    public final int component5() {
        return this.measureWidth;
    }

    public final int component6() {
        return this.measureHeight;
    }

    public final float component7() {
        return this.alpha;
    }

    public final float component8() {
        return this.scale;
    }

    public final boolean component9() {
        return this.gone;
    }

    public final WidgetState copy(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z) {
        return new WidgetState(f, f2, i, i2, i3, i4, f3, f4, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WidgetState)) {
            return false;
        }
        WidgetState widgetState = (WidgetState) obj;
        return Float.compare(this.x, widgetState.x) == 0 && Float.compare(this.y, widgetState.y) == 0 && this.width == widgetState.width && this.height == widgetState.height && this.measureWidth == widgetState.measureWidth && this.measureHeight == widgetState.measureHeight && Float.compare(this.alpha, widgetState.alpha) == 0 && Float.compare(this.scale, widgetState.scale) == 0 && this.gone == widgetState.gone;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final boolean getGone() {
        return this.gone;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getMeasureHeight() {
        return this.measureHeight;
    }

    public final int getMeasureWidth() {
        return this.measureWidth;
    }

    public final float getScale() {
        return this.scale;
    }

    public final int getWidth() {
        return this.width;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public int hashCode() {
        return Boolean.hashCode(this.gone) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, ReorderTile$$ExternalSyntheticOutline0.m(this.measureHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.measureWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.height, ReorderTile$$ExternalSyntheticOutline0.m(this.width, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y, Float.hashCode(this.x) * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final void initFromLayout(View view) {
        boolean z = view.getVisibility() == 8;
        this.gone = z;
        if (z) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            this.x = layoutParams.getConstraintWidget().getX();
            this.y = layoutParams.getConstraintWidget().getY();
            this.width = layoutParams.getConstraintWidget().getWidth();
            int height = layoutParams.getConstraintWidget().getHeight();
            this.height = height;
            this.measureHeight = height;
            this.measureWidth = this.width;
            this.alpha = 0.0f;
            this.scale = 0.0f;
            return;
        }
        this.x = view.getLeft();
        this.y = view.getTop();
        this.width = view.getWidth();
        int height2 = view.getHeight();
        this.height = height2;
        this.measureWidth = this.width;
        this.measureHeight = height2;
        this.gone = view.getVisibility() == 8;
        this.alpha = view.getAlpha();
        this.scale = 1.0f;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final void setGone(boolean z) {
        this.gone = z;
    }

    public final void setHeight(int i) {
        this.height = i;
    }

    public final void setMeasureHeight(int i) {
        this.measureHeight = i;
    }

    public final void setMeasureWidth(int i) {
        this.measureWidth = i;
    }

    public final void setScale(float f) {
        this.scale = f;
    }

    public final void setWidth(int i) {
        this.width = i;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public String toString() {
        float f = this.x;
        float f2 = this.y;
        int i = this.width;
        int i2 = this.height;
        int i3 = this.measureWidth;
        int i4 = this.measureHeight;
        float f3 = this.alpha;
        float f4 = this.scale;
        boolean z = this.gone;
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("WidgetState(x=", f, ", y=", f2, ", width=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i, ", height=", i2, ", measureWidth=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i3, ", measureHeight=", i4, ", alpha=");
        sbM.append(f3);
        sbM.append(", scale=");
        sbM.append(f4);
        sbM.append(", gone=");
        return MoveResult$$ExternalSyntheticOutline0.m(sbM, z, ")");
    }

    public WidgetState(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z) {
        this.x = f;
        this.y = f2;
        this.width = i;
        this.height = i2;
        this.measureWidth = i3;
        this.measureHeight = i4;
        this.alpha = f3;
        this.scale = f4;
        this.gone = z;
    }

    public /* synthetic */ WidgetState(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0.0f : f, (i5 & 2) != 0 ? 0.0f : f2, (i5 & 4) != 0 ? 0 : i, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? 0 : i3, (i5 & 32) != 0 ? 0 : i4, (i5 & 64) != 0 ? 1.0f : f3, (i5 & 128) != 0 ? 1.0f : f4, (i5 & 256) != 0 ? false : z);
    }
}
