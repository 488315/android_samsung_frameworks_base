package com.android.systemui.util;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.Choreographer;
import androidx.constraintlayout.motion.widget.MotionLayout;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NoRemeasureMotionLayout extends MotionLayout {
    public static final int $stable = 8;
    private boolean disabled;
    private Long lastFrame;
    private Integer lastHeightSpec;
    private Integer lastWidthSpec;

    public NoRemeasureMotionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    @Override // androidx.constraintlayout.motion.widget.MotionLayout, androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        Integer num;
        Integer num2 = this.lastWidthSpec;
        if (num2 != null && num2.intValue() == i && (num = this.lastHeightSpec) != null && num.intValue() == i2) {
            Choreographer mainThreadInstance = Choreographer.getMainThreadInstance();
            if (Intrinsics.areEqual(mainThreadInstance != null ? Long.valueOf(mainThreadInstance.getFrameTime()) : null, this.lastFrame)) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
        }
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            com.android.app.tracing.TraceUtilsKt.beginSlice("NoRemeasureMotionLayout - measure");
        }
        try {
            super.onMeasure(i, i2);
            this.lastWidthSpec = Integer.valueOf(i);
            this.lastHeightSpec = Integer.valueOf(i2);
            Choreographer mainThreadInstance2 = Choreographer.getMainThreadInstance();
            this.lastFrame = mainThreadInstance2 != null ? Long.valueOf(mainThreadInstance2.getFrameTime()) : null;
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                com.android.app.tracing.TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                com.android.app.tracing.TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void setDisable(boolean z) {
        this.disabled = z;
    }

    public final void setDisabled(boolean z) {
        this.disabled = z;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (this.disabled) {
            super.setVisibility(8);
        } else {
            super.setVisibility(i);
        }
    }

    public /* synthetic */ NoRemeasureMotionLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public NoRemeasureMotionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public static /* synthetic */ void getDisabled$annotations() {
    }
}
