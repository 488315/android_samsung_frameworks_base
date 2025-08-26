package com.android.wm.shell.onehanded;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.onehanded.OneHandedAnimationController;

/* loaded from: classes3.dex */
public class OneHandedTutorialHandler implements OneHandedTransitionCallback, OneHandedAnimationCallback {
    public final int mAlphaAnimationDurationMs;
    public ValueAnimator mAlphaAnimator;
    public float mAlphaTransitionStart;
    public final BackgroundWindowManager mBackgroundWindowManager;
    public final Context mContext;
    public int mCurrentState;
    public Rect mDisplayBounds;
    public ViewGroup mTargetViewContainer;
    public int mTutorialAreaHeight;
    public final float mTutorialHeightRatio;
    public View mTutorialView;
    public final WindowManager mWindowManager;

    public OneHandedTutorialHandler(Context context, OneHandedSettingsUtil oneHandedSettingsUtil, WindowManager windowManager, BackgroundWindowManager backgroundWindowManager) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mBackgroundWindowManager = backgroundWindowManager;
        oneHandedSettingsUtil.getClass();
        this.mTutorialHeightRatio = context.getResources().getFraction(R.fraction.config_one_handed_offset, 1, 1);
        this.mAlphaAnimationDurationMs = context.getResources().getInteger(R.integer.config_one_handed_translate_animation_duration);
    }

    public final void checkTransitionEnd() {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null) {
            if (valueAnimator.isRunning() || this.mAlphaAnimator.isStarted()) {
                this.mAlphaAnimator.end();
                this.mAlphaAnimator.removeAllUpdateListeners();
                this.mAlphaAnimator = null;
            }
        }
    }

    public void createViewAndAttachToWindow(Context context) {
        if (isAttached()) {
            return;
        }
        this.mTutorialView = LayoutInflater.from(context).inflate(R.layout.one_handed_tutorial, (ViewGroup) null);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mTargetViewContainer = frameLayout;
        frameLayout.setClipChildren(false);
        this.mTargetViewContainer.setAlpha(this.mCurrentState == 2 ? 1.0f : 0.0f);
        this.mTargetViewContainer.addView(this.mTutorialView);
        this.mTargetViewContainer.setLayerType(2, null);
        try {
            this.mWindowManager.addView(this.mTargetViewContainer, getTutorialTargetLayoutParams());
            this.mBackgroundWindowManager.showBackgroundLayer();
        } catch (IllegalStateException unused) {
            this.mWindowManager.updateViewLayout(this.mTargetViewContainer, getTutorialTargetLayoutParams());
        }
    }

    public final WindowManager.LayoutParams getTutorialTargetLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(this.mDisplayBounds.width(), this.mTutorialAreaHeight, 0, 0, 2024, 264, -3);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("one-handed-tutorial-overlay");
        return layoutParams;
    }

    public boolean isAttached() {
        ViewGroup viewGroup = this.mTargetViewContainer;
        return viewGroup != null && viewGroup.isAttachedToWindow();
    }

    @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
    public final void onAnimationUpdate(float f) {
        if (isAttached()) {
            if (f < this.mAlphaTransitionStart) {
                checkTransitionEnd();
                return;
            }
            ValueAnimator valueAnimator = this.mAlphaAnimator;
            if (valueAnimator == null || valueAnimator.isStarted() || this.mAlphaAnimator.isRunning()) {
                return;
            }
            this.mAlphaAnimator.start();
        }
    }

    @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
    public final void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStartFinished(Rect rect) {
        BackgroundWindowManager backgroundWindowManager;
        ViewGroup viewGroup = this.mTargetViewContainer;
        if (viewGroup == null || (backgroundWindowManager = this.mBackgroundWindowManager) == null) {
            return;
        }
        viewGroup.setBackgroundColor(backgroundWindowManager.getThemeColorForBackground());
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStopFinished(Rect rect) {
        removeBackgroundSurface();
    }

    public void removeBackgroundSurface() {
        BackgroundWindowManager backgroundWindowManager = this.mBackgroundWindowManager;
        if (backgroundWindowManager.mBackgroundView != null) {
            backgroundWindowManager.mBackgroundView = null;
        }
        SurfaceControlViewHost surfaceControlViewHost = backgroundWindowManager.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            backgroundWindowManager.mViewHost = null;
        }
        if (backgroundWindowManager.mLeash != null) {
            backgroundWindowManager.mTransactionFactory.getClass();
            new SurfaceControl.Transaction().remove(backgroundWindowManager.mLeash).apply();
            backgroundWindowManager.mLeash = null;
        }
    }

    public void removeTutorialFromWindowManager() {
        if (isAttached()) {
            this.mTargetViewContainer.setLayerType(0, null);
            this.mWindowManager.removeViewImmediate(this.mTargetViewContainer);
            this.mTargetViewContainer = null;
        }
    }

    public final void setupAlphaTransition(boolean z) {
        float f = z ? 0.0f : 1.0f;
        float f2 = z ? 1.0f : 0.0f;
        int iRound = this.mAlphaAnimationDurationMs;
        if (!z) {
            iRound = Math.round((1.0f - this.mTutorialHeightRatio) * iRound);
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.mAlphaAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        this.mAlphaAnimator.setDuration(iRound);
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.onehanded.OneHandedTutorialHandler$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.mTargetViewContainer.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
    }

    public final void updateThemeColor() {
        if (this.mTutorialView == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = new ContextThemeWrapper(this.mTutorialView.getContext(), android.R.style.Theme.DeviceDefault.DayNight).obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary, android.R.attr.textColorSecondary});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        int color2 = typedArrayObtainStyledAttributes.getColor(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        ((ImageView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_image)).setImageTintList(ColorStateList.valueOf(color));
        TextView textView = (TextView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_title);
        TextView textView2 = (TextView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_description);
        textView.setTextColor(color);
        textView2.setTextColor(color2);
    }
}
