package com.android.systemui.util.animation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UniqueObjectHostView extends FrameLayout {
    public static final int $stable = 8;
    public MeasurementManager measurementManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MeasurementManager {
        MeasurementOutput onMeasure(MeasurementInput measurementInput);
    }

    public UniqueObjectHostView(Context context) {
        super(context);
    }

    private final boolean isCurrentHost() {
        return getChildCount() != 0;
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new IllegalArgumentException("child must be non-null");
        }
        if (view.getMeasuredWidth() == 0 || getMeasuredWidth() == 0 || UniqueObjectHostViewKt.getRequiresRemeasuring(view)) {
            super.addView(view, i, layoutParams);
            return;
        }
        invalidate();
        addViewInLayout(view, i, layoutParams, true);
        view.resolveRtlPropertiesIfNeeded();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        view.layout(paddingLeft, paddingTop, (getMeasuredWidth() + paddingLeft) - (getPaddingEnd() + getPaddingStart()), (getMeasuredHeight() + paddingTop) - (getPaddingBottom() + getPaddingTop()));
    }

    public final MeasurementManager getMeasurementManager() {
        MeasurementManager measurementManager = this.measurementManager;
        if (measurementManager != null) {
            return measurementManager;
        }
        return null;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        MeasurementOutput onMeasure = getMeasurementManager().onMeasure(new MeasurementInput(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) - paddingEnd, View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) - paddingBottom, View.MeasureSpec.getMode(i2))));
        int component1 = onMeasure.component1();
        int component2 = onMeasure.component2();
        if (isCurrentHost()) {
            super.onMeasure(i, i2);
            View childAt = getChildAt(0);
            if (childAt != null) {
                UniqueObjectHostViewKt.setRequiresRemeasuring(childAt, false);
            }
        }
        setMeasuredDimension(component1 + paddingEnd, component2 + paddingBottom);
    }

    public final void setMeasurementManager(MeasurementManager measurementManager) {
        this.measurementManager = measurementManager;
    }
}
