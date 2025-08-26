package com.samsung.android.multiwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class FreeformResizeGuideView extends FrameLayout {
    private ValueAnimator mAlphaAnimator;
    private final ArrayList<Animator> mAnimList;
    private final AnimatorSet mAnimatorSet;
    private int mAppIconSize;
    private ImageView mAppIconView;
    private ImageView mDimView;
    private int mDimViewMargin;
    private int mFullscreenDimViewMargin;
    private ValueAnimator mHeightAnimator;
    private boolean mIsTransition;
    private ValueAnimator mLeftMarginAnimator;
    private boolean mToFullScreen;
    private ValueAnimator mTopMarginAnimator;
    private ValueAnimator mWidthAnimator;

    private int getGuideResourceId(int i) {
        return i != 1 ? i != 2 ? R.drawable.mw_popupview_img_resizingguide : R.drawable.dex_docking_resize_guide_right : R.drawable.dex_docking_resize_guide_left;
    }

    public FreeformResizeGuideView(Context context) {
        super(context);
        this.mAnimList = new ArrayList<>();
        this.mAnimatorSet = new AnimatorSet();
    }

    public FreeformResizeGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimList = new ArrayList<>();
        this.mAnimatorSet = new AnimatorSet();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDimView = (ImageView) findViewById(R.id.freeform_resize_guide_dim);
        this.mAppIconView = (ImageView) findViewById(R.id.freeform_resize_guide_app_icon);
        this.mDimViewMargin = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_dim_margin);
        this.mFullscreenDimViewMargin = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_fullscreen_dim_margin);
        this.mAppIconSize = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_app_icon_size);
    }

    void update(int i, ComponentName componentName) {
        this.mDimView.lambda$setImageURIAsync$2(getResources().getDrawable(getGuideResourceId(i)));
        if (componentName != null) {
            this.mAppIconView.lambda$setImageURIAsync$2(MultiWindowUtils.getAppIcon(this.mContext, componentName, UserHandle.myUserId(), componentName.getPackageName()));
        }
    }

    void show(Rect rect, Rect rect2, boolean z) {
        show(rect, rect2, z, z, null);
    }

    void show(Rect rect, Rect rect2, boolean z, boolean z2, FreeformResizeGuide.TransitionInfo transitionInfo) {
        boolean z3;
        if (this.mIsTransition == z && this.mToFullScreen == z2) {
            z3 = false;
        } else {
            this.mIsTransition = z;
            this.mToFullScreen = z2;
            z3 = true;
        }
        int i = this.mDimViewMargin;
        int i2 = z2 ? this.mFullscreenDimViewMargin : i;
        int i3 = rect.left - i;
        int i4 = rect.top - i;
        int i5 = i * 2;
        int iWidth = rect.width() + i5;
        int iHeight = rect.height() + i5;
        int i6 = rect2.left - i2;
        int i7 = rect2.top - i2;
        int i8 = i2 * 2;
        int iWidth2 = rect2.width() + i8;
        int iHeight2 = rect2.height() + i8;
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mDimView.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mAppIconView.getLayoutParams();
        if (z3) {
            this.mAnimList.clear();
            if (layoutParams != null) {
                removeAllUpdateListenersIfNeeded(this.mLeftMarginAnimator, this.mTopMarginAnimator, this.mWidthAnimator, this.mHeightAnimator, this.mAlphaAnimator);
                if (i3 != i6) {
                    ValueAnimator orCreateValueAnimator = getOrCreateValueAnimator(this.mLeftMarginAnimator, i3, i6, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$show$0(layoutParams, valueAnimator);
                        }
                    });
                    this.mLeftMarginAnimator = orCreateValueAnimator;
                    this.mAnimList.add(orCreateValueAnimator);
                }
                if (i4 != i7) {
                    ValueAnimator orCreateValueAnimator2 = getOrCreateValueAnimator(this.mTopMarginAnimator, i4, i7, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$show$1(layoutParams, valueAnimator);
                        }
                    });
                    this.mTopMarginAnimator = orCreateValueAnimator2;
                    this.mAnimList.add(orCreateValueAnimator2);
                }
                if (iWidth != iWidth2) {
                    ValueAnimator orCreateValueAnimator3 = getOrCreateValueAnimator(this.mWidthAnimator, iWidth, iWidth2, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$show$2(layoutParams, valueAnimator);
                        }
                    });
                    this.mWidthAnimator = orCreateValueAnimator3;
                    this.mAnimList.add(orCreateValueAnimator3);
                }
                if (iHeight != iHeight2) {
                    ValueAnimator orCreateValueAnimator4 = getOrCreateValueAnimator(this.mHeightAnimator, iHeight, iHeight2, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$show$3(layoutParams, valueAnimator);
                        }
                    });
                    this.mHeightAnimator = orCreateValueAnimator4;
                    this.mAnimList.add(orCreateValueAnimator4);
                }
                TimeInterpolator interpolator = InterpolatorUtils.ONE_EASING;
                long animationDuration = 300;
                if (transitionInfo != null) {
                    animationDuration = transitionInfo.getAnimationDuration(300L);
                    interpolator = transitionInfo.getInterpolator(interpolator);
                    int fromAlpha = transitionInfo.getFromAlpha();
                    int toAlpha = transitionInfo.getToAlpha();
                    if (fromAlpha >= 0 && toAlpha >= 0) {
                        ValueAnimator orCreateValueAnimator5 = getOrCreateValueAnimator(this.mAlphaAnimator, fromAlpha, toAlpha, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda4
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                this.f$0.lambda$show$4(valueAnimator);
                            }
                        });
                        this.mAlphaAnimator = orCreateValueAnimator5;
                        this.mAnimList.add(orCreateValueAnimator5);
                    }
                    transitionInfo.addDismissListener(this.mAnimatorSet);
                }
                this.mAnimatorSet.setDuration(animationDuration);
                this.mAnimatorSet.setInterpolator(interpolator);
                this.mAnimatorSet.playTogether(this.mAnimList);
                this.mAnimatorSet.start();
            }
        } else if (!this.mIsTransition) {
            this.mAnimatorSet.cancel();
            if (layoutParams != null) {
                layoutParams.leftMargin = i6;
                layoutParams.topMargin = i7;
                layoutParams.width = iWidth2;
                layoutParams.height = iHeight2;
                this.mDimView.setLayoutParams(layoutParams);
            }
            if (layoutParams2 != null) {
                layoutParams2.leftMargin = (rect2.left + (rect2.width() / 2)) - (this.mAppIconSize / 2);
                layoutParams2.topMargin = (rect2.top + (rect2.height() / 2)) - (this.mAppIconSize / 2);
                this.mAppIconView.setLayoutParams(layoutParams2);
            }
        }
        this.mDimView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(FrameLayout.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        layoutParams.leftMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$1(FrameLayout.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        layoutParams.topMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$2(FrameLayout.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        layoutParams.width = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$3(FrameLayout.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$4(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mDimView.setAlpha(iIntValue > 0 ? iIntValue / 100.0f : 0.0f);
    }

    void hide() {
        this.mDimView.setVisibility(8);
        this.mAppIconView.setVisibility(8);
    }

    void dismiss() {
        this.mAnimatorSet.cancel();
        this.mAnimList.clear();
        removeAllViews();
    }

    void setDimViewVisibility(int i) {
        this.mDimView.setVisibility(i);
    }

    void startShowAppIconAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.0f, 0.95f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.0f, 0.95f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(InterpolatorUtils.SINE_IN_OUT_60);
        animatorSet.setDuration(300L);
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                FreeformResizeGuideView.this.mAppIconView.setVisibility(0);
                FreeformResizeGuideView.this.mAppIconView.setAlpha(1.0f);
            }
        });
        animatorSet.start();
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.95f, 0.9f);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.95f, 0.9f);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setInterpolator(AnimationUtils.loadInterpolator(this.mContext, R.interpolator.sine_in_out_33));
        animatorSet2.setDuration(300L);
        animatorSet2.setStartDelay(300L);
        animatorSet2.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat4);
        animatorSet2.start();
    }

    void startHideAppIconAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.9f, 0.5f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.9f, 0.5f);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.mAppIconView, "alpha", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(100L);
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FreeformResizeGuideView.this.mAppIconView.setVisibility(4);
            }
        });
        animatorSet.start();
    }

    boolean isShowingAppIcon() {
        return this.mAppIconView.getVisibility() == 0;
    }

    private void removeAllUpdateListenersIfNeeded(ValueAnimator... valueAnimatorArr) {
        if (valueAnimatorArr == null || valueAnimatorArr.length == 0) {
            return;
        }
        for (ValueAnimator valueAnimator : valueAnimatorArr) {
            if (valueAnimator != null) {
                valueAnimator.removeAllUpdateListeners();
            }
        }
    }

    private ValueAnimator getOrCreateValueAnimator(ValueAnimator valueAnimator, int i, int i2, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        if (valueAnimator == null) {
            valueAnimator = new ValueAnimator();
        }
        valueAnimator.setIntValues(i, i2);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        return valueAnimator;
    }
}
