package com.android.systemui.p016qs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ScrollView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NonInterceptingScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mDownY;
    public BooleanSupplier mHeightAnimatingSupplier;
    public boolean mPreventingIntercept;
    public BooleanSupplier mQsExpandSupplier;
    public final boolean mScrollEnabled;
    public final int mTouchSlop;

    public NonInterceptingScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 1;
        this.mScrollEnabled = true;
        final int i2 = 0;
        this.mQsExpandSupplier = new BooleanSupplier() { // from class: com.android.systemui.qs.NonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i2) {
                    case 0:
                        int i3 = NonInterceptingScrollView.$r8$clinit;
                        break;
                    default:
                        int i4 = NonInterceptingScrollView.$r8$clinit;
                        break;
                }
                return false;
            }
        };
        new Path();
        new RectF();
        this.mHeightAnimatingSupplier = new BooleanSupplier() { // from class: com.android.systemui.qs.NonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i) {
                    case 0:
                        int i3 = NonInterceptingScrollView.$r8$clinit;
                        break;
                    default:
                        int i4 = NonInterceptingScrollView.$r8$clinit;
                        break;
                }
                return false;
            }
        };
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setFocusable(false);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        getContext().getResources().getDimensionPixelSize(R.dimen.panel_bar_corner_radius);
        Context context2 = getContext();
        secQSPanelResourcePicker.getClass();
        SecQSPanelResourcePicker.getPanelSidePadding(context2);
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        return this.mQsExpandSupplier.getAsBoolean() && !this.mHeightAnimatingSupplier.getAsBoolean() && this.mScrollEnabled && super.canScrollHorizontally(i);
    }

    @Override // android.view.View
    public final boolean canScrollVertically(int i) {
        return this.mQsExpandSupplier.getAsBoolean() && !this.mHeightAnimatingSupplier.getAsBoolean() && this.mScrollEnabled && super.canScrollVertically(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        return !(this.mQsExpandSupplier.getAsBoolean() || accessibilityManager == null || accessibilityManager.isTouchExplorationEnabled()) || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.widget.ScrollView, android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mPreventingIntercept = false;
            if (canScrollVertically(1)) {
                this.mPreventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            this.mDownY = motionEvent.getY();
        } else if (actionMasked == 2 && ((int) motionEvent.getY()) - this.mDownY < (-this.mTouchSlop) && !canScrollVertically(1)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        invalidate();
    }

    @Override // android.widget.ScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mPreventingIntercept = false;
            if (canScrollVertically(1)) {
                this.mPreventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            } else if (!canScrollVertically(-1)) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }
}
