package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.shade.SecPanelSplitHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class TouchInterceptFrameLayout extends FrameLayout {
    public View.OnClickListener customClickListener;
    public final GestureDetector gestureDetector;
    public boolean isAnimationInProgress;
    public boolean isTouchInProgress;
    public View touchForwardView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public final TouchInterceptFrameLayout view;

        public MyGestureListener(TouchInterceptFrameLayout touchInterceptFrameLayout) {
            this.view = touchInterceptFrameLayout;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!this.view.isAnimationInProgress) {
                Log.d("TouchInterceptFrameLayout", "onTouchEvent onSingleTapUp");
                TouchInterceptFrameLayout touchInterceptFrameLayout = this.view;
                View.OnClickListener onClickListener = touchInterceptFrameLayout.customClickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(touchInterceptFrameLayout);
                }
            }
            SecPanelSplitHelper secPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
            if (secPanelSplitHelper.isReversed()) {
                secPanelSplitHelper.slide$1(1);
            }
            return true;
        }
    }

    static {
        new Companion(null);
    }

    public TouchInterceptFrameLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        View view;
        Integer numValueOf = motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null;
        if (numValueOf != null && numValueOf.intValue() == 0) {
            this.isTouchInProgress = true;
        } else if (numValueOf != null && numValueOf.intValue() == 1) {
            this.isTouchInProgress = false;
        } else if (numValueOf != null && numValueOf.intValue() == 3) {
            this.isTouchInProgress = false;
        }
        if (!(motionEvent != null ? this.gestureDetector.onTouchEvent(motionEvent) : false) && (view = this.touchForwardView) != null) {
            view.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public final boolean performClick() {
        Log.d("TouchInterceptFrameLayout", "performClick()");
        View.OnClickListener onClickListener = this.customClickListener;
        if (onClickListener == null) {
            return true;
        }
        onClickListener.onClick(this);
        return true;
    }

    public TouchInterceptFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ TouchInterceptFrameLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public TouchInterceptFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gestureDetector = new GestureDetector(context, new MyGestureListener(this));
    }
}
