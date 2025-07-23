package com.android.internal.widget;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BaseInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.flags.Flags;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class ViewGroupFader {
    private static final float ALPHA_LOWER_BOUND = 0.5f;
    private static final float CHAINED_BOUNDS_BOTTOM_FRACTION = 0.2f;
    private static final float CHAINED_BOUNDS_TOP_FRACTION = 0.6f;
    private static final float CHAINED_LOWER_REGION_FRACTION = 0.35f;
    private static final float CHAINED_UPPER_REGION_FRACTION = 0.55f;
    private static final float SCALE_LOWER_BOUND = 0.7f;
    private float mBottomBoundPixels;
    private final AnimationCallback mCallback;
    private final ChildViewBoundsProvider mChildViewBoundsProvider;
    protected final ViewGroup mParent;
    private float mTopBoundPixels;
    private float mScaleLowerBound = SCALE_LOWER_BOUND;
    private float mAlphaLowerBound = 0.5f;
    private float mChainedBoundsTop = 0.6f;
    private float mChainedBoundsBottom = 0.2f;
    private float mChainedLowerRegion = CHAINED_LOWER_REGION_FRACTION;
    private float mChainedUpperRegion = CHAINED_UPPER_REGION_FRACTION;
    private final Rect mContainerBounds = new Rect();
    private final Rect mOffsetViewBounds = new Rect();
    private BaseInterpolator mTopInterpolator = new PathInterpolator(0.3f, 0.0f, SCALE_LOWER_BOUND, 1.0f);
    private BaseInterpolator mBottomInterpolator = new PathInterpolator(0.3f, 0.0f, SCALE_LOWER_BOUND, 1.0f);
    private ContainerBoundsProvider mContainerBoundsProvider = new ScreenContainerBoundsProvider();

    public interface AnimationCallback {
        boolean shouldFadeFromBottom(View view);

        boolean shouldFadeFromTop(View view);

        void viewHasBecomeFullSize(View view);
    }

    public interface ChildViewBoundsProvider {
        void provideBounds(ViewGroup viewGroup, View view, Rect rect);
    }

    interface ContainerBoundsProvider {
        void provideBounds(ViewGroup viewGroup, Rect rect);
    }

    private static float lerp(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    static final class ScreenContainerBoundsProvider implements ContainerBoundsProvider {
        ScreenContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(ViewGroup viewGroup, Rect rect) {
            rect.set(0, 0, viewGroup.getResources().getDisplayMetrics().widthPixels, viewGroup.getResources().getDisplayMetrics().heightPixels);
        }
    }

    static final class ParentContainerBoundsProvider implements ContainerBoundsProvider {
        ParentContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(ViewGroup viewGroup, Rect rect) {
            viewGroup.getGlobalVisibleRect(rect);
        }
    }

    static final class DefaultViewBoundsProvider implements ChildViewBoundsProvider {
        DefaultViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(ViewGroup viewGroup, View view, Rect rect) {
            view.getDrawingRect(rect);
            rect.offset(0, (int) view.getTranslationY());
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            Rect rect2 = new Rect();
            viewGroup.getGlobalVisibleRect(rect2);
            rect.offset(rect2.left, rect2.top);
        }
    }

    static final class GlobalVisibleViewBoundsProvider implements ChildViewBoundsProvider {
        GlobalVisibleViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(ViewGroup viewGroup, View view, Rect rect) {
            view.getGlobalVisibleRect(rect);
        }
    }

    public ViewGroupFader(ViewGroup viewGroup, AnimationCallback animationCallback, ChildViewBoundsProvider childViewBoundsProvider) {
        this.mParent = viewGroup;
        this.mCallback = animationCallback;
        this.mChildViewBoundsProvider = childViewBoundsProvider;
    }

    AnimationCallback getAnimationCallback() {
        return this.mCallback;
    }

    void setScaleLowerBound(float f) {
        this.mScaleLowerBound = f;
    }

    void setAlphaLowerBound(float f) {
        this.mAlphaLowerBound = f;
    }

    void setTopInterpolator(BaseInterpolator baseInterpolator) {
        this.mTopInterpolator = baseInterpolator;
    }

    void setBottomInterpolator(BaseInterpolator baseInterpolator) {
        this.mBottomInterpolator = baseInterpolator;
    }

    void setContainerBoundsProvider(ContainerBoundsProvider containerBoundsProvider) {
        this.mContainerBoundsProvider = containerBoundsProvider;
    }

    public void updateFade() {
        this.mContainerBoundsProvider.provideBounds(this.mParent, this.mContainerBounds);
        this.mTopBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsTop;
        this.mBottomBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsBottom;
        updateListElementFades(this.mParent, true);
    }

    public void updateListElementFades(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                if (Flags.enableFadingViewGroup() && Resources.getSystem().getBoolean(R.bool.config_enableViewGroupScalingFading) && (childAt instanceof ViewGroup)) {
                    updateListElementFades((ViewGroup) childAt, true);
                }
                if (z) {
                    fadeElement(viewGroup, childAt);
                }
            }
        }
    }

    private void fadeElement(ViewGroup viewGroup, View view) {
        this.mChildViewBoundsProvider.provideBounds(viewGroup, view, this.mOffsetViewBounds);
        setViewPropertiesByPosition(view, this.mOffsetViewBounds, this.mTopBoundPixels, this.mBottomBoundPixels);
    }

    private void setViewPropertiesByPosition(View view, Rect rect, float f, float f2) {
        float f3;
        if (view.getHeight() < f && view.getHeight() > f2) {
            f3 = lerp(this.mChainedLowerRegion, this.mChainedUpperRegion, (view.getHeight() - f2) / (f - f2));
        } else if (view.getHeight() < f2) {
            f3 = this.mChainedLowerRegion;
        } else {
            f3 = this.mChainedUpperRegion;
        }
        int height = (int) (this.mContainerBounds.height() * f3);
        int i = this.mContainerBounds.top + height;
        int i2 = this.mContainerBounds.bottom - height;
        boolean z = view.getScaleX() == 1.0f;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.setPivotX(view.getWidth() * 0.5f);
        if (rect.top > i2 && this.mCallback.shouldFadeFromBottom(view)) {
            view.setPivotY(-marginLayoutParams.topMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mBottomInterpolator.getInterpolation((this.mContainerBounds.bottom - rect.top) / height));
        } else if (rect.bottom < i && this.mCallback.shouldFadeFromTop(view)) {
            view.setPivotY(view.getMeasuredHeight() + marginLayoutParams.bottomMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mTopInterpolator.getInterpolation((rect.bottom - this.mContainerBounds.top) / height));
        } else {
            if (!z) {
                this.mCallback.viewHasBecomeFullSize(view);
            }
            setDefaultSizeAndAlphaForView(view);
        }
    }

    private void scaleAndFadeByRelativeOffsetFraction(View view, float f) {
        view.setTransitionAlpha(lerp(this.mAlphaLowerBound, 1.0f, f));
        float lerp = lerp(this.mScaleLowerBound, 1.0f, f);
        view.setScaleX(lerp);
        view.setScaleY(lerp);
    }

    private void setDefaultSizeAndAlphaForView(View view) {
        view.setTransitionAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }
}
