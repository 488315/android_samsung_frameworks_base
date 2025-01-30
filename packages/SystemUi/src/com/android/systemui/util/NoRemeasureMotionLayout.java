package com.android.systemui.util;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import androidx.constraintlayout.motion.widget.MotionLayout;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoRemeasureMotionLayout extends MotionLayout {
    public boolean disabled;
    public Long lastFrame;
    public Integer lastHeightSpec;
    public Integer lastWidthSpec;

    public NoRemeasureMotionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    @Override // androidx.constraintlayout.motion.widget.MotionLayout, androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Integer num;
        Integer num2 = this.lastWidthSpec;
        if (num2 != null && num2.intValue() == i && (num = this.lastHeightSpec) != null && num.intValue() == i2) {
            Choreographer mainThreadInstance = Choreographer.getMainThreadInstance();
            if (Intrinsics.areEqual(mainThreadInstance != null ? Long.valueOf(mainThreadInstance.getFrameTime()) : null, this.lastFrame)) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
        }
        if (!Trace.isTagEnabled(4096L)) {
            super.onMeasure(i, i2);
            this.lastWidthSpec = Integer.valueOf(i);
            this.lastHeightSpec = Integer.valueOf(i2);
            Choreographer mainThreadInstance2 = Choreographer.getMainThreadInstance();
            this.lastFrame = mainThreadInstance2 != null ? Long.valueOf(mainThreadInstance2.getFrameTime()) : null;
            return;
        }
        Trace.traceBegin(4096L, "NoRemeasureMotionLayout - measure");
        try {
            super.onMeasure(i, i2);
            this.lastWidthSpec = Integer.valueOf(i);
            this.lastHeightSpec = Integer.valueOf(i2);
            Choreographer mainThreadInstance3 = Choreographer.getMainThreadInstance();
            this.lastFrame = mainThreadInstance3 != null ? Long.valueOf(mainThreadInstance3.getFrameTime()) : null;
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (!this.disabled) {
            super.setVisibility(i);
        } else {
            Log.d("NoRemeasureMotionLayout", "Ignore visibility update since it's disabled");
            super.setVisibility(8);
        }
    }

    public /* synthetic */ NoRemeasureMotionLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public NoRemeasureMotionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
