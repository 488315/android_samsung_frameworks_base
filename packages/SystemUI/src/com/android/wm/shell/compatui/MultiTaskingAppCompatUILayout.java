package com.android.wm.shell.compatui;

import android.app.AppCompatTaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayLayout;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MultiTaskingAppCompatUILayout extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HashMap mAnimationListenerWrappers;
    public final HashMap mButtons;
    public MultiTaskingAppCompatUIController mController;
    public final AnonymousClass1 mFrameCommitCallback;
    public final Handler mHandler;
    public int mHorizontalMarginFromActivityBounds;
    public ImageButton mLastVisibleTarget;
    public final ViewTreeObserver.OnGlobalLayoutListener mLayoutListener;
    public int mNaviButtonSize;
    public FrameLayout mSwitchableButtonContainer;
    public final Region mTouchableRegion;
    public final MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda0 mTouchableRegionCalculator;
    public int mVerticalMarginFromActivityBounds;
    public MultiTaskingAppCompatUIWindowManager mWindowManager;

    /* renamed from: $r8$lambda$mCU4zJ-Ek2I7AokD1FchTrCG1cc, reason: not valid java name */
    public static void m3223$r8$lambda$mCU4zJEk2I7AokD1FchTrCG1cc(MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout) {
        multiTaskingAppCompatUILayout.configureTouchableRegion(multiTaskingAppCompatUILayout.mTouchableRegionCalculator);
        multiTaskingAppCompatUILayout.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(multiTaskingAppCompatUILayout.mLayoutListener);
    }

    public static /* synthetic */ void $r8$lambda$w6py6pSGPMO7lM_mAu3k29BmhLs(MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout, View view) {
        Rect rect = new Rect();
        view.getBoundsOnScreen(rect);
        multiTaskingAppCompatUILayout.mTouchableRegion.union(rect);
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$1] */
    public MultiTaskingAppCompatUILayout(Context context) {
        super(context);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mVerticalMarginFromActivityBounds = 0;
        this.mHorizontalMarginFromActivityBounds = 0;
        this.mLastVisibleTarget = null;
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MultiTaskingAppCompatUILayout.m3223$r8$lambda$mCU4zJEk2I7AokD1FchTrCG1cc(MultiTaskingAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingAppCompatUILayout.this.requestDismiss();
                MultiTaskingAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(MultiTaskingAppCompatUILayout.this.mFrameCommitCallback);
            }
        };
        this.mHandler = new Handler(Looper.myLooper());
    }

    public final void adjustMargins(boolean z) {
        if (this.mHorizontalMarginFromActivityBounds < 0 || this.mVerticalMarginFromActivityBounds < 0) {
            return;
        }
        MultiTaskingAppCompatUIWindowManager multiTaskingAppCompatUIWindowManager = this.mWindowManager;
        DisplayLayout displayLayout = multiTaskingAppCompatUIWindowManager.mDisplayLayout;
        if (!z) {
            DisplayCutout displayCutout = displayLayout.mCutout;
            if (displayCutout == null) {
                return;
            }
            Rect boundingRectLeft = displayCutout.getBoundingRectLeft();
            Rect boundingRectRight = displayCutout.getBoundingRectRight();
            if (this.mHorizontalMarginFromActivityBounds * 0.5f < Math.max(boundingRectLeft.width(), boundingRectRight.width())) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.mt_app_compat_button_margin) + ((int) (Math.max(boundingRectLeft.height(), boundingRectRight.height()) * 0.5f));
                View view = (View) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_align_left_button));
                if (view != null) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.bottomMargin = dimensionPixelSize;
                    view.setLayoutParams(layoutParams);
                }
                View view2 = (View) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_align_right_button));
                if (view2 != null) {
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                    layoutParams2.bottomMargin = dimensionPixelSize;
                    view2.setLayoutParams(layoutParams2);
                    return;
                }
                return;
            }
            return;
        }
        Rect taskBounds = multiTaskingAppCompatUIWindowManager.getTaskBounds();
        MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mController;
        multiTaskingAppCompatUIController.getClass();
        int height = ((taskBounds.height() - new Rect(multiTaskingAppCompatUIController.mTaskInfo.appCompatTaskInfo.topActivityBounds).height()) / 2) - this.mWindowManager.mDisplayLayout.mNavBarFrameHeight;
        int i = this.mNaviButtonSize;
        if (height >= i) {
            height = 0;
        }
        float min = height > 0 ? Math.min(0.8f, height / i) : 1.0f;
        View view3 = (View) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_align_top_button));
        if (view3 != null) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) view3.getLayoutParams();
            int i2 = (int) (this.mNaviButtonSize * min);
            layoutParams3.width = i2;
            layoutParams3.height = i2;
            layoutParams3.topMargin = displayLayout.mNavBarFrameHeight + this.mVerticalMarginFromActivityBounds;
            view3.setLayoutParams(layoutParams3);
        }
        View view4 = (View) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_align_bottom_button));
        if (view4 != null) {
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view4.getLayoutParams();
            int i3 = (int) (this.mNaviButtonSize * min);
            layoutParams4.width = i3;
            layoutParams4.height = i3;
            layoutParams4.bottomMargin = displayLayout.mNavBarFrameHeight + this.mVerticalMarginFromActivityBounds;
            view4.setLayoutParams(layoutParams4);
        }
    }

    public final void configureTouchableRegion(MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda0 multiTaskingAppCompatUILayout$$ExternalSyntheticLambda0) {
        if (getRootView().isAttachedToWindow()) {
            this.mTouchableRegion.setEmpty();
            Iterator it = this.mButtons.entrySet().iterator();
            while (it.hasNext()) {
                ImageButton imageButton = (ImageButton) ((Map.Entry) it.next()).getValue();
                if (imageButton.getVisibility() == 0) {
                    $r8$lambda$w6py6pSGPMO7lM_mAu3k29BmhLs(multiTaskingAppCompatUILayout$$ExternalSyntheticLambda0.f$0, imageButton);
                }
            }
            MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mController;
            multiTaskingAppCompatUIController.mMultiTaskingAppCompatUIWindowManager.setTouchRegion(this.mTouchableRegion);
        }
    }

    public final void refreshButtonVisibility(boolean z) {
        getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this.mLayoutListener);
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this.mLayoutListener);
        ImageButton imageButton = this.mLastVisibleTarget;
        if (imageButton != null && imageButton.getVisibility() == 0) {
            this.mLastVisibleTarget.setVisibility(4);
        }
        AppCompatTaskInfo appCompatTaskInfo = this.mController.mTaskInfo.appCompatTaskInfo;
        if (appCompatTaskInfo.topActivityInDisplayCompat) {
            this.mLastVisibleTarget = (ImageButton) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_restart_button));
        } else if (appCompatTaskInfo.hasMinAspectRatioOverride()) {
            this.mLastVisibleTarget = (ImageButton) this.mButtons.get(Integer.valueOf(R.id.mt_app_compat_fixed_aspect_ratio_shortcut_button));
        }
        ImageButton imageButton2 = this.mLastVisibleTarget;
        if (imageButton2 != null && imageButton2.getVisibility() == 4) {
            this.mLastVisibleTarget.setVisibility(0);
        }
        if (MultiTaskingAppCompatUIController.isAlignedVertically(this.mController.mTaskInfo)) {
            adjustMargins(true);
            int i = this.mController.mAlignment & 112;
            if (i == 16) {
                setButtonVisibility(R.id.mt_app_compat_align_top_button, 0, z);
                setButtonVisibility(R.id.mt_app_compat_align_bottom_button, 0, z);
                updateSwitchableButtonContainer(85);
            } else if (i == 48) {
                setButtonVisibility(R.id.mt_app_compat_align_top_button, 8, z);
                setButtonVisibility(R.id.mt_app_compat_align_bottom_button, 0, z);
                updateSwitchableButtonContainer(85);
            } else if (i == 80) {
                setButtonVisibility(R.id.mt_app_compat_align_top_button, 0, z);
                setButtonVisibility(R.id.mt_app_compat_align_bottom_button, 8, z);
                updateSwitchableButtonContainer(53);
            }
        } else {
            adjustMargins(false);
            int i2 = this.mController.mAlignment & 7;
            if (i2 == 1) {
                setButtonVisibility(R.id.mt_app_compat_align_left_button, 0, z);
                setButtonVisibility(R.id.mt_app_compat_align_right_button, 0, z);
                updateSwitchableButtonContainer(85);
            } else if (i2 == 3) {
                setButtonVisibility(R.id.mt_app_compat_align_left_button, 8, z);
                setButtonVisibility(R.id.mt_app_compat_align_right_button, 0, z);
                updateSwitchableButtonContainer(85);
            } else if (i2 == 5) {
                setButtonVisibility(R.id.mt_app_compat_align_left_button, 0, z);
                setButtonVisibility(R.id.mt_app_compat_align_right_button, 8, z);
                updateSwitchableButtonContainer(83);
            }
        }
        configureTouchableRegion(this.mTouchableRegionCalculator);
        requestDismiss();
    }

    public final void requestDismiss() {
        if (!CoreRune.ONE_UI_5_1_1 && this.mController.mTaskInfo.appCompatTaskInfo.topActivityInDisplayCompat) {
            this.mHandler.removeCallbacksAndMessages(this);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = MultiTaskingAppCompatUILayout.this;
                    Iterator it = multiTaskingAppCompatUILayout.mButtons.entrySet().iterator();
                    while (it.hasNext()) {
                        ImageButton imageButton = (ImageButton) ((Map.Entry) it.next()).getValue();
                        if (!imageButton.equals(multiTaskingAppCompatUILayout.mLastVisibleTarget) && imageButton.getVisibility() == 0) {
                            imageButton.setVisibility(8);
                        }
                    }
                    multiTaskingAppCompatUILayout.configureTouchableRegion(multiTaskingAppCompatUILayout.mTouchableRegionCalculator);
                }
            }, this, 5000L);
        } else {
            final MultiTaskingAppCompatUIController multiTaskingAppCompatUIController = this.mController;
            Handler handler = multiTaskingAppCompatUIController.mHandler;
            handler.removeCallbacksAndMessages(multiTaskingAppCompatUIController);
            handler.postDelayed(new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUIController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MultiTaskingAppCompatUIController multiTaskingAppCompatUIController2 = MultiTaskingAppCompatUIController.this;
                    multiTaskingAppCompatUIController2.mController.removeLayouts(multiTaskingAppCompatUIController2.mTaskInfo.taskId);
                }
            }, multiTaskingAppCompatUIController, 5000L);
        }
    }

    public final void setButtonVisibility(int i, int i2, boolean z) {
        View view = (View) this.mButtons.get(Integer.valueOf(i));
        AnimationListenerWrapper animationListenerWrapper = (AnimationListenerWrapper) this.mAnimationListenerWrappers.remove(Integer.valueOf(i));
        if (animationListenerWrapper != null) {
            animationListenerWrapper.mCancel = true;
            animationListenerWrapper.mAnimation.cancel();
        }
        if (view == null || view.getVisibility() == i2) {
            return;
        }
        if (!z) {
            view.setVisibility(i2);
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(((FrameLayout) this).mContext, i2 == 0 ? R.anim.mt_app_compat_ui_btn_release_n_appear : R.anim.mt_app_compat_ui_btn_release_n_disappear);
        this.mAnimationListenerWrappers.put(Integer.valueOf(i), new AnimationListenerWrapper(loadAnimation, view, i2));
        view.startAnimation(loadAnimation);
    }

    @Override // android.view.View
    public final String toString() {
        return "MultiTaskingAppCompatUILayout{mController=" + this.mController + ", mLastVisibleTarget=" + this.mLastVisibleTarget + "}";
    }

    public final void updateSwitchableButtonContainer(int i) {
        FrameLayout frameLayout = this.mSwitchableButtonContainer;
        if (frameLayout == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.gravity = i;
        if ((i & 80) != 0) {
            layoutParams.bottomMargin = this.mWindowManager.mDisplayLayout.mStableInsets.bottom;
        }
        frameLayout.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$1] */
    public MultiTaskingAppCompatUILayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mVerticalMarginFromActivityBounds = 0;
        this.mHorizontalMarginFromActivityBounds = 0;
        this.mLastVisibleTarget = null;
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MultiTaskingAppCompatUILayout.m3223$r8$lambda$mCU4zJEk2I7AokD1FchTrCG1cc(MultiTaskingAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingAppCompatUILayout.this.requestDismiss();
                MultiTaskingAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(MultiTaskingAppCompatUILayout.this.mFrameCommitCallback);
            }
        };
        this.mHandler = new Handler(Looper.myLooper());
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimationListenerWrapper implements Animation.AnimationListener {
        public final Animation mAnimation;
        public boolean mCancel;
        public final View mView;
        public final int mVisibility;

        public AnimationListenerWrapper(Animation animation, View view, int i) {
            animation.setAnimationListener(this);
            this.mAnimation = animation;
            this.mView = view;
            this.mVisibility = i;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationEnd(Animation animation) {
            if (this.mCancel) {
                return;
            }
            this.mView.setVisibility(this.mVisibility);
            MultiTaskingAppCompatUILayout.this.mHandler.post(new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout.AnimationListenerWrapper.1
                @Override // java.lang.Runnable
                public final void run() {
                    MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = MultiTaskingAppCompatUILayout.this;
                    multiTaskingAppCompatUILayout.configureTouchableRegion(multiTaskingAppCompatUILayout.mTouchableRegionCalculator);
                }
            });
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationStart(Animation animation) {
        }
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$1] */
    public MultiTaskingAppCompatUILayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mVerticalMarginFromActivityBounds = 0;
        this.mHorizontalMarginFromActivityBounds = 0;
        this.mLastVisibleTarget = null;
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MultiTaskingAppCompatUILayout.m3223$r8$lambda$mCU4zJEk2I7AokD1FchTrCG1cc(MultiTaskingAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingAppCompatUILayout.this.requestDismiss();
                MultiTaskingAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(MultiTaskingAppCompatUILayout.this.mFrameCommitCallback);
            }
        };
        this.mHandler = new Handler(Looper.myLooper());
    }
}
