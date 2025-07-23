package com.android.wm.shell.compatui.coverlauncher;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverLauncherAppCompatUILayout extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HashMap mAnimationListenerWrappers;
    public final HashMap mButtons;
    public CoverLauncherAppCompatUIController mController;
    public final AnonymousClass1 mFrameCommitCallback;
    public final Handler mHandler;
    public final ViewTreeObserver.OnGlobalLayoutListener mLayoutListener;
    public int mNaviButtonSize;
    public final Region mTouchableRegion;
    public final CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda0 mTouchableRegionCalculator;
    public CoverLauncherAppCompatUIWindowManager mWindowManager;

    /* renamed from: $r8$lambda$4WPzlIeSoSg8-Q-IF7a0VjEY2q8, reason: not valid java name */
    public static /* synthetic */ void m3228$r8$lambda$4WPzlIeSoSg8QIF7a0VjEY2q8(CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout, View view) {
        Rect rect = new Rect();
        view.getBoundsOnScreen(rect);
        coverLauncherAppCompatUILayout.mTouchableRegion.union(rect);
    }

    /* renamed from: $r8$lambda$Zq0T92Sd_iEwka0niF0qFU-7lB0, reason: not valid java name */
    public static void m3229$r8$lambda$Zq0T92Sd_iEwka0niF0qFU7lB0(CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout) {
        coverLauncherAppCompatUILayout.configureTouchableRegion(coverLauncherAppCompatUILayout.mTouchableRegionCalculator);
        coverLauncherAppCompatUILayout.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(coverLauncherAppCompatUILayout.mLayoutListener);
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$1] */
    public CoverLauncherAppCompatUILayout(Context context) {
        super(context);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                CoverLauncherAppCompatUILayout.m3229$r8$lambda$Zq0T92Sd_iEwka0niF0qFU7lB0(CoverLauncherAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = CoverLauncherAppCompatUILayout.this.mController;
                Handler handler = coverLauncherAppCompatUIController.mHandler;
                handler.removeCallbacksAndMessages(coverLauncherAppCompatUIController);
                handler.postDelayed(new CoverLauncherAppCompatUIController$$ExternalSyntheticLambda0(coverLauncherAppCompatUIController), coverLauncherAppCompatUIController, 5000L);
                CoverLauncherAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(CoverLauncherAppCompatUILayout.this.mFrameCommitCallback);
            }
        };
        this.mHandler = new Handler(Looper.myLooper());
    }

    public final void configureTouchableRegion(CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda0 coverLauncherAppCompatUILayout$$ExternalSyntheticLambda0) {
        if (getRootView().isAttachedToWindow()) {
            this.mTouchableRegion.setEmpty();
            Iterator it = this.mButtons.entrySet().iterator();
            while (it.hasNext()) {
                ImageButton imageButton = (ImageButton) ((Map.Entry) it.next()).getValue();
                if (imageButton.getVisibility() == 0) {
                    m3228$r8$lambda$4WPzlIeSoSg8QIF7a0VjEY2q8(coverLauncherAppCompatUILayout$$ExternalSyntheticLambda0.f$0, imageButton);
                }
            }
            CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.mController;
            coverLauncherAppCompatUIController.mCoverLauncherAppCompatUIWindowManager.setTouchRegion(this.mTouchableRegion);
        }
    }

    public final void refreshButtonVisibility(boolean z) {
        getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this.mLayoutListener);
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this.mLayoutListener);
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_UI) {
            int i = this.mController.mAlignment & 7;
            if (i == 1) {
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_left_button, 0, z);
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_right_button, 0, z);
            } else if (i == 3) {
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_left_button, 8, z);
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_right_button, 0, z);
            } else if (i == 5) {
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_left_button, 0, z);
                setButtonVisibility(R.id.fw_cover_launcher_app_compat_align_right_button, 8, z);
            }
        }
        configureTouchableRegion(this.mTouchableRegionCalculator);
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.mController;
        Handler handler = coverLauncherAppCompatUIController.mHandler;
        handler.removeCallbacksAndMessages(coverLauncherAppCompatUIController);
        handler.postDelayed(new CoverLauncherAppCompatUIController$$ExternalSyntheticLambda0(coverLauncherAppCompatUIController), coverLauncherAppCompatUIController, 5000L);
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
        return "CoverLauncherAppCompatUILayout{mController=" + this.mController + "}";
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$1] */
    public CoverLauncherAppCompatUILayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                CoverLauncherAppCompatUILayout.m3229$r8$lambda$Zq0T92Sd_iEwka0niF0qFU7lB0(CoverLauncherAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = CoverLauncherAppCompatUILayout.this.mController;
                Handler handler = coverLauncherAppCompatUIController.mHandler;
                handler.removeCallbacksAndMessages(coverLauncherAppCompatUIController);
                handler.postDelayed(new CoverLauncherAppCompatUIController$$ExternalSyntheticLambda0(coverLauncherAppCompatUIController), coverLauncherAppCompatUIController, 5000L);
                CoverLauncherAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(CoverLauncherAppCompatUILayout.this.mFrameCommitCallback);
            }
        };
        this.mHandler = new Handler(Looper.myLooper());
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$1] */
    public CoverLauncherAppCompatUILayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mButtons = new HashMap();
        this.mAnimationListenerWrappers = new HashMap();
        this.mTouchableRegion = new Region();
        this.mTouchableRegionCalculator = new CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda0(this);
        this.mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                CoverLauncherAppCompatUILayout.m3229$r8$lambda$Zq0T92Sd_iEwka0niF0qFU7lB0(CoverLauncherAppCompatUILayout.this);
            }
        };
        this.mFrameCommitCallback = new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout.1
            @Override // java.lang.Runnable
            public final void run() {
                CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = CoverLauncherAppCompatUILayout.this.mController;
                Handler handler = coverLauncherAppCompatUIController.mHandler;
                handler.removeCallbacksAndMessages(coverLauncherAppCompatUIController);
                handler.postDelayed(new CoverLauncherAppCompatUIController$$ExternalSyntheticLambda0(coverLauncherAppCompatUIController), coverLauncherAppCompatUIController, 5000L);
                CoverLauncherAppCompatUILayout.this.getRootView().getViewTreeObserver().unregisterFrameCommitCallback(CoverLauncherAppCompatUILayout.this.mFrameCommitCallback);
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
            CoverLauncherAppCompatUILayout.this.mHandler.post(new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUILayout.AnimationListenerWrapper.1
                @Override // java.lang.Runnable
                public final void run() {
                    CoverLauncherAppCompatUILayout coverLauncherAppCompatUILayout = CoverLauncherAppCompatUILayout.this;
                    coverLauncherAppCompatUILayout.configureTouchableRegion(coverLauncherAppCompatUILayout.mTouchableRegionCalculator);
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
}
