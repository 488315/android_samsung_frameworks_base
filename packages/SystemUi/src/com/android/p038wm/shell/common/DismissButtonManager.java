package com.android.p038wm.shell.common;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DismissButtonManager extends FrameLayout {
    public final int mDismissType;
    public boolean mNeedToRemoveWindow;
    public String mTitle;
    public DismissButtonView mView;
    public boolean mWindowAdded;
    public final WindowManager mWindowManager;
    public final int mWindowType;

    public DismissButtonManager(Context context, int i) {
        this(context, i, 2607);
    }

    public final void cleanUpDismissTarget() {
        Log.i("DismissButtonManager", "cleanUpDismissTarget  isAttachedToWindow=" + isAttachedToWindow() + "  mWindowAdded=" + this.mWindowAdded);
        clearAnimation();
        this.mNeedToRemoveWindow = false;
        DismissButtonView dismissButtonView = this.mView;
        if (dismissButtonView != null && dismissButtonView.mVisible) {
            dismissButtonView.mVisible = false;
            dismissButtonView.clearAnimation();
            dismissButtonView.setVisibility(4);
        }
        if (isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(this);
            this.mWindowAdded = false;
        } else if (this.mWindowAdded) {
            this.mNeedToRemoveWindow = true;
        }
    }

    public final void createDismissButtonView() {
        if (this.mView != null) {
            cleanUpDismissTarget();
            removeView(this.mView);
            this.mView = null;
        }
        this.mView = (DismissButtonView) LayoutInflater.from(getContext()).inflate(R.layout.dismiss_button_view, (ViewGroup) this, false);
        Insets insetsIgnoringVisibility = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        DismissButtonView dismissButtonView = this.mView;
        int i = insetsIgnoringVisibility.bottom;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) dismissButtonView.getLayoutParams();
        marginLayoutParams.bottomMargin = dismissButtonView.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_bottom) + i;
        dismissButtonView.setLayoutParams(marginLayoutParams);
        this.mView.setDismissType(this.mDismissType);
        addView(this.mView);
    }

    public final void createOrUpdateWrapper() {
        Log.i("DismissButtonManager", "createOrUpdateWrapper  isAttachedToWindow=" + isAttachedToWindow());
        if (isAttachedToWindow()) {
            this.mWindowManager.updateViewLayout(this, generateWrapperLayoutParams());
            return;
        }
        setVisibility(4);
        this.mNeedToRemoveWindow = false;
        try {
            this.mWindowManager.addView(this, generateWrapperLayoutParams());
            this.mWindowAdded = true;
        } catch (IllegalStateException unused) {
            this.mWindowManager.updateViewLayout(this, generateWrapperLayoutParams());
        }
    }

    public final WindowManager.LayoutParams generateWrapperLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, this.mWindowType, 16778040, -2);
        layoutParams.setTitle(this.mTitle);
        layoutParams.privateFlags |= 80;
        layoutParams.samsungFlags |= 131072;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.y = 0;
        layoutParams.x = 0;
        Point point = new Point();
        getContext().getDisplay().getRealSize(point);
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        layoutParams.gravity = 8388659;
        return layoutParams;
    }

    public final void hide(Runnable runnable) {
        final DismissButtonView dismissButtonView = this.mView;
        if (dismissButtonView.mVisible) {
            dismissButtonView.mVisible = false;
            Log.i("DismissButtonView", "hide");
            dismissButtonView.clearAnimation();
            Animation animation = dismissButtonView.mIsEnterDismissButton ? dismissButtonView.mInsideHideAnimation : dismissButtonView.mOutsideHideAnimation;
            dismissButtonView.mHideAnimationEnd = runnable;
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DismissButtonView.2
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation2) {
                    int i = DismissButtonView.$r8$clinit;
                    Log.i("DismissButtonView", "hide-Run callback");
                    DismissButtonView.this.setVisibility(4);
                    Runnable runnable2 = DismissButtonView.this.mHideAnimationEnd;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation2) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation2) {
                }
            });
            dismissButtonView.startAnimation(animation);
            return;
        }
        dismissButtonView.updateDismissButtonState(false);
        Log.i("DismissButtonView", "already mVisible=false but the callback should be run.");
        Animation animation2 = dismissButtonView.getAnimation();
        if (animation2 == null || animation2.hasEnded()) {
            runnable.run();
        } else {
            dismissButtonView.mHideAnimationEnd = runnable;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("onAttachedToWindow  mNeedToRemoveWindow="), this.mNeedToRemoveWindow, "DismissButtonManager");
        if (this.mNeedToRemoveWindow) {
            cleanUpDismissTarget();
        }
    }

    public final void show() {
        setVisibility(0);
        DismissButtonView dismissButtonView = this.mView;
        if (dismissButtonView.mVisible) {
            return;
        }
        dismissButtonView.mVisible = true;
        Log.i("DismissButtonView", "show");
        dismissButtonView.startAnimation(dismissButtonView.mEnterAnimation);
    }

    public final void updateDismissTargetView(PointF pointF) {
        DismissButtonView dismissButtonView = this.mView;
        int i = (int) pointF.x;
        int i2 = (int) pointF.y;
        dismissButtonView.updateView(new Rect(i - 3, i2 - 3, i + 3, i2 + 3));
    }

    public DismissButtonManager(Context context, int i, int i2) {
        super(context);
        this.mNeedToRemoveWindow = false;
        this.mWindowAdded = false;
        this.mTitle = "dismiss-button-overlay";
        this.mDismissType = i;
        this.mWindowType = i2;
        this.mWindowManager = (WindowManager) getContext().getSystemService("window");
    }
}
