package com.android.internal.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BaseInterpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes5.dex */
class ViewGroupFader {
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

    interface AnimationCallback {
        boolean shouldFadeFromBottom(View view);

        boolean shouldFadeFromTop(View view);

        void viewHasBecomeFullSize(View view);
    }

    interface ChildViewBoundsProvider {
        void provideBounds(ViewGroup viewGroup, View view, Rect rect);
    }

    interface ContainerBoundsProvider {
        void provideBounds(ViewGroup viewGroup, Rect rect);
    }

    static final class ScreenContainerBoundsProvider implements ContainerBoundsProvider {
        ScreenContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(ViewGroup parent, Rect bounds) {
            bounds.set(0, 0, parent.getResources().getDisplayMetrics().widthPixels, parent.getResources().getDisplayMetrics().heightPixels);
        }
    }

    static final class ParentContainerBoundsProvider implements ContainerBoundsProvider {
        ParentContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(ViewGroup parent, Rect bounds) {
            parent.getGlobalVisibleRect(bounds);
        }
    }

    static final class DefaultViewBoundsProvider implements ChildViewBoundsProvider {
        DefaultViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(ViewGroup parent, View child, Rect bounds) {
            child.getDrawingRect(bounds);
            bounds.offset(0, (int) child.getTranslationY());
            parent.offsetDescendantRectToMyCoords(child, bounds);
            Rect parentGlobalVisibleBounds = new Rect();
            parent.getGlobalVisibleRect(parentGlobalVisibleBounds);
            bounds.offset(parentGlobalVisibleBounds.left, parentGlobalVisibleBounds.top);
        }
    }

    static final class GlobalVisibleViewBoundsProvider implements ChildViewBoundsProvider {
        GlobalVisibleViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(ViewGroup parent, View child, Rect bounds) {
            child.getGlobalVisibleRect(bounds);
        }
    }

    ViewGroupFader(ViewGroup parent, AnimationCallback callback, ChildViewBoundsProvider childViewBoundsProvider) {
        this.mParent = parent;
        this.mCallback = callback;
        this.mChildViewBoundsProvider = childViewBoundsProvider;
    }

    AnimationCallback getAnimationCallback() {
        return this.mCallback;
    }

    void setScaleLowerBound(float scale) {
        this.mScaleLowerBound = scale;
    }

    void setAlphaLowerBound(float alpha) {
        this.mAlphaLowerBound = alpha;
    }

    void setTopInterpolator(BaseInterpolator interpolator) {
        this.mTopInterpolator = interpolator;
    }

    void setBottomInterpolator(BaseInterpolator interpolator) {
        this.mBottomInterpolator = interpolator;
    }

    void setContainerBoundsProvider(ContainerBoundsProvider boundsProvider) {
        this.mContainerBoundsProvider = boundsProvider;
    }

    void updateFade() {
        this.mContainerBoundsProvider.provideBounds(this.mParent, this.mContainerBounds);
        this.mTopBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsTop;
        this.mBottomBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsBottom;
        updateListElementFades(this.mParent, true);
    }

    private void updateListElementFades(ViewGroup parent, boolean shouldFade) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child.getVisibility() == 0 && shouldFade) {
                fadeElement(parent, child);
            }
        }
    }

    private void fadeElement(ViewGroup parent, View child) {
        this.mChildViewBoundsProvider.provideBounds(parent, child, this.mOffsetViewBounds);
        setViewPropertiesByPosition(child, this.mOffsetViewBounds, this.mTopBoundPixels, this.mBottomBoundPixels);
    }

    private void setViewPropertiesByPosition(View view, Rect bounds, float topBoundPixels, float bottomBoundPixels) {
        float fadeOutRegionFraction;
        if (view.getHeight() < topBoundPixels && view.getHeight() > bottomBoundPixels) {
            fadeOutRegionFraction = lerp(this.mChainedLowerRegion, this.mChainedUpperRegion, (view.getHeight() - bottomBoundPixels) / (topBoundPixels - bottomBoundPixels));
        } else if (view.getHeight() < bottomBoundPixels) {
            fadeOutRegionFraction = this.mChainedLowerRegion;
        } else {
            fadeOutRegionFraction = this.mChainedUpperRegion;
        }
        int fadeOutRegionHeight = (int) (this.mContainerBounds.height() * fadeOutRegionFraction);
        int topFadeBoundary = this.mContainerBounds.top + fadeOutRegionHeight;
        int bottomFadeBoundary = this.mContainerBounds.bottom - fadeOutRegionHeight;
        boolean wasFullSize = view.getScaleX() == 1.0f;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.setPivotX(view.getWidth() * 0.5f);
        if (bounds.top > bottomFadeBoundary && this.mCallback.shouldFadeFromBottom(view)) {
            view.setPivotY(-lp.topMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mBottomInterpolator.getInterpolation((this.mContainerBounds.bottom - bounds.top) / fadeOutRegionHeight));
        } else if (bounds.bottom < topFadeBoundary && this.mCallback.shouldFadeFromTop(view)) {
            view.setPivotY(view.getMeasuredHeight() + lp.bottomMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mTopInterpolator.getInterpolation((bounds.bottom - this.mContainerBounds.top) / fadeOutRegionHeight));
        } else {
            if (!wasFullSize) {
                this.mCallback.viewHasBecomeFullSize(view);
            }
            setDefaultSizeAndAlphaForView(view);
        }
    }

    private void scaleAndFadeByRelativeOffsetFraction(View view, float offsetFraction) {
        float alpha = lerp(this.mAlphaLowerBound, 1.0f, offsetFraction);
        view.setTransitionAlpha(alpha);
        float scale = lerp(this.mScaleLowerBound, 1.0f, offsetFraction);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    private void setDefaultSizeAndAlphaForView(View view) {
        view.setTransitionAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }

    private static float lerp(float min, float max, float fraction) {
        return ((max - min) * fraction) + min;
    }
}
