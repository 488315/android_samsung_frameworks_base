package com.android.systemui.media.controls.p010ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import com.android.p038wm.shell.animation.PhysicsAnimatorKt;
import java.util.WeakHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaScrollView extends HorizontalScrollView {
    public ViewGroup contentContainer;

    public MediaScrollView(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.contentContainer = (ViewGroup) getChildAt(0);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        float translationX;
        ViewGroup viewGroup = this.contentContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
        PhysicsAnimator.Companion.getClass();
        if (PhysicsAnimator.Companion.getInstance(viewGroup).isRunning()) {
            translationX = 0.0f;
        } else {
            ViewGroup viewGroup2 = this.contentContainer;
            translationX = (viewGroup2 != null ? viewGroup2 : null).getTranslationX();
        }
        if (translationX == 0.0f) {
            return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        return false;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final void scrollTo(int i, int i2) {
        int i3 = ((HorizontalScrollView) this).mScrollX;
        if (i3 == i && ((HorizontalScrollView) this).mScrollY == i2) {
            return;
        }
        int i4 = ((HorizontalScrollView) this).mScrollY;
        ((HorizontalScrollView) this).mScrollX = i;
        ((HorizontalScrollView) this).mScrollY = i2;
        invalidateParentCaches();
        onScrollChanged(((HorizontalScrollView) this).mScrollX, ((HorizontalScrollView) this).mScrollY, i3, i4);
        if (awakenScrollBars()) {
            return;
        }
        postInvalidateOnAnimation();
    }

    public MediaScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ MediaScrollView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public MediaScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
