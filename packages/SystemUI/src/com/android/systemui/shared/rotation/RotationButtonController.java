package com.android.systemui.shared.rotation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.IRotationWatcher;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.core.view.OneShotPreDrawListener;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.shared.recents.utilities.ViewRippler;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RotationButtonController {
    public final AccessibilityManager mAccessibilityManager;
    public SystemBarProxy mBarProxy;
    public Executor mBgExecutor;
    public final Context mContext;
    public final int mDarkIconColor;
    public boolean mDocked;
    public boolean mHomeRotationEnabled;
    public boolean mHoveringRotationSuggestion;
    public final int mIconCcwStart0ResId;
    public final int mIconCcwStart90ResId;
    public final int mIconCwStart0ResId;
    public final int mIconCwStart90ResId;
    public int mIconResId;
    public boolean mIsNavigationBarShowing;
    public boolean mIsRecentsAnimationRunning;
    public final KeyguardManager mKeyguardManager;
    public int mLastRotationSuggestion;
    public final int mLightIconColor;
    public int mNavBarMode;
    public boolean mPendingRotationSuggestion;
    public Animator mRotateHideAnimator;
    public FloatingRotationButton mRotationButton;
    public int mSamsungIconCCWStart0ResId;
    public int mSamsungIconCCWStart180ResId;
    public int mSamsungIconCCWStart90ResId;
    public int mSamsungIconCWStart0ResId;
    public int mSamsungIconCWStart180ResId;
    public int mSamsungIconCWStart90ResId;
    public int mSamsungRotateButtonResId;
    public boolean mSkipOverrideUserLockPrefsOnce;
    public int mStyleRes;
    public final Supplier mWindowRotationProvider;
    public static final boolean OEM_DISALLOW_ROTATION_IN_SUW = SystemProperties.getBoolean("ro.setupwizard.rotation_locked", false);
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public final UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final ViewRippler mViewRippler = new ViewRippler();
    public boolean mListenersRegistered = false;
    public boolean mRotationWatcherRegistered = false;
    public int mBehavior = 1;
    public final RotationButtonController$$ExternalSyntheticLambda0 mRemoveRotationProposal = new RotationButtonController$$ExternalSyntheticLambda0(this, 0);
    public final RotationButtonController$$ExternalSyntheticLambda0 mCancelPendingRotationProposal = new RotationButtonController$$ExternalSyntheticLambda0(this, 1);
    public final AnonymousClass1 mDockedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shared.rotation.RotationButtonController.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            boolean z = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
            rotationButtonController.getClass();
            if (intent == null) {
                return;
            }
            rotationButtonController.mDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0;
        }
    };
    public final AnonymousClass2 mRotationWatcher = new AnonymousClass2();
    public long mLastUnknownRotationProposedTick = 0;
    public final RotationButtonController$$ExternalSyntheticLambda0 mRotateSuggestionButtonShowRunnable = new RotationButtonController$$ExternalSyntheticLambda0(this, 2);
    public final RotationButtonController$$ExternalSyntheticLambda3 mRotationLockCallback = new Consumer() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            rotationButtonController.getClass();
            if (((Boolean) obj).booleanValue()) {
                rotationButtonController.mLastUnknownRotationProposedTick = 0L;
            }
        }
    };
    public final TaskStackListenerImpl mTaskStackListener = new TaskStackListenerImpl(this, 0);

    /* renamed from: com.android.systemui.shared.rotation.RotationButtonController$2, reason: invalid class name */
    public final class AnonymousClass2 extends IRotationWatcher.Stub {
        public AnonymousClass2() {
        }

        public final void onRotationChanged(int i) {
            RotationButtonController.this.mMainThreadHandler.postAtFrontOfQueue(new RotationButtonController$2$$ExternalSyntheticLambda0(this, i, 0));
        }
    }

    enum RotationButtonEvent implements UiEventLogger.UiEventEnum {
        ROTATION_SUGGESTION_SHOWN(206),
        ROTATION_SUGGESTION_ACCEPTED(207);

        private final int mId;

        RotationButtonEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final class TaskStackListenerImpl implements TaskStackChangeListener {
        public /* synthetic */ TaskStackListenerImpl(RotationButtonController rotationButtonController, int i) {
            this();
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onActivityRequestedOrientationChanged(int i) {
            RotationButtonController.this.mBgExecutor.execute(new RotationButtonController$2$$ExternalSyntheticLambda0(this, i, 1));
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskMovedToFront() {
            RotationButtonController.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskRemoved() {
            RotationButtonController.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskStackChanged() {
            RotationButtonController.this.setRotateSuggestionButtonState(false);
        }

        private TaskStackListenerImpl() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.shared.rotation.RotationButtonController$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3] */
    public RotationButtonController(Context context, int i, int i2, int i3, int i4, int i5, int i6, Supplier<Integer> supplier) {
        this.mContext = context;
        this.mLightIconColor = i;
        this.mDarkIconColor = i2;
        this.mIconCcwStart0ResId = i3;
        this.mIconCcwStart90ResId = i4;
        this.mIconCwStart0ResId = i5;
        this.mIconCwStart90ResId = i6;
        this.mIconResId = i4;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mWindowRotationProvider = supplier;
        this.mBgExecutor = context.getMainExecutor();
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            this.mKeyguardManager = (KeyguardManager) getContext().getSystemService("keyguard");
        }
    }

    public boolean canShowRotationButton() {
        if (this.mIsNavigationBarShowing || this.mBehavior == 1 || QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            return true;
        }
        return BasicRuneWrapper.NAVBAR_ENABLED && this.mNavBarMode == 0;
    }

    public final Context getContext() {
        return BasicRuneWrapper.NAVBAR_ENABLED ? new ContextThemeWrapper(this.mContext.getApplicationContext(), this.mStyleRes) : this.mContext;
    }

    public final Boolean isRotationLocked() {
        try {
            return BasicRuneWrapper.NAVBAR_ENABLED ? Boolean.valueOf(((SamsungNavigationBarProxy) this.mBarProxy).rotationLocked) : Boolean.valueOf(RotationPolicy.isRotationLocked(this.mContext));
        } catch (SecurityException e) {
            Log.e("RotationButtonController", "Failed to get isRotationLocked", e);
            return null;
        }
    }

    public final void onRotationWatcherChanged(int i) {
        Boolean isRotationLocked;
        Boolean isRotationLocked2;
        if (this.mListenersRegistered && (isRotationLocked = isRotationLocked()) != null) {
            if (isRotationLocked.booleanValue() || this.mRotationButton.mIsShowing) {
                if (this.mSkipOverrideUserLockPrefsOnce) {
                    this.mSkipOverrideUserLockPrefsOnce = false;
                } else if (i == 0 && isRotationLocked.booleanValue() && !this.mDocked && (isRotationLocked2 = isRotationLocked()) != null) {
                    RotationPolicy.setRotationLockAtAngle(this.mContext, isRotationLocked2.booleanValue(), i, "RotationButtonController#onRotationWatcherChanged");
                }
                setRotateSuggestionButtonState(false, true);
            }
        }
    }

    public final void rescheduleRotationTimeout(boolean z) {
        Animator animator;
        if (!z || (((animator = this.mRotateHideAnimator) == null || !animator.isRunning()) && this.mRotationButton.mIsShowing)) {
            Handler handler = this.mMainThreadHandler;
            RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0 = this.mRemoveRotationProposal;
            handler.removeCallbacks(rotationButtonController$$ExternalSyntheticLambda0);
            handler.postDelayed(rotationButtonController$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(this.mHoveringRotationSuggestion ? VolumePanelState.DIALOG_HOVERING_TIMEOUT_MILLIS : 5000, 4));
        }
    }

    public final void setRotateSuggestionButtonState(boolean z) {
        if (!BasicRuneWrapper.NAVBAR_ENABLED) {
            setRotateSuggestionButtonState(z, false);
            return;
        }
        Handler handler = this.mMainThreadHandler;
        RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0 = this.mRotateSuggestionButtonShowRunnable;
        if (z) {
            handler.postDelayed(rotationButtonController$$ExternalSyntheticLambda0, 500L);
        } else {
            handler.removeCallbacks(rotationButtonController$$ExternalSyntheticLambda0);
            setRotateSuggestionButtonState(false, false);
        }
    }

    public final void showAndLogRotationSuggestion() {
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            FloatingRotationButton floatingRotationButton = this.mRotationButton;
            if (floatingRotationButton.mIsShowing) {
                floatingRotationButton.hide();
            }
        }
        setRotateSuggestionButtonState(true);
        rescheduleRotationTimeout(false);
        this.mUiEventLogger.log(RotationButtonEvent.ROTATION_SUGGESTION_SHOWN);
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            this.mLastUnknownRotationProposedTick = 0L;
            ((SamsungNavigationBarProxy) this.mBarProxy).getClass();
            MetricsLogger metricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
            if (metricsLogger != null) {
                metricsLogger.visible(1288);
            }
        }
    }

    public final void setRotateSuggestionButtonState(boolean z, boolean z2) {
        FloatingRotationButton floatingRotationButton;
        FloatingRotationButtonView floatingRotationButtonView;
        AnimatedVectorDrawable animatedVectorDrawable;
        int i = 1;
        if ((!z && !this.mRotationButton.mIsShowing) || (floatingRotationButtonView = (floatingRotationButton = this.mRotationButton).mKeyButtonView) == null || (animatedVectorDrawable = floatingRotationButton.mAnimatedDrawable) == null) {
            return;
        }
        this.mPendingRotationSuggestion = false;
        this.mMainThreadHandler.removeCallbacks(this.mCancelPendingRotationProposal);
        ViewRippler viewRippler = this.mViewRippler;
        if (z) {
            Animator animator = this.mRotateHideAnimator;
            if (animator != null && animator.isRunning()) {
                this.mRotateHideAnimator.cancel();
            }
            this.mRotateHideAnimator = null;
            floatingRotationButtonView.setAlpha(1.0f);
            animatedVectorDrawable.reset();
            animatedVectorDrawable.start();
            if (Settings.Secure.getInt(this.mContext.getContentResolver(), "num_rotation_suggestions_accepted", 0) < 1) {
                View view = viewRippler.mRoot;
                ViewRippler.AnonymousClass1 anonymousClass1 = viewRippler.mRipple;
                if (view != null) {
                    view.removeCallbacks(anonymousClass1);
                }
                viewRippler.mRoot = floatingRotationButtonView;
                floatingRotationButtonView.postOnAnimationDelayed(anonymousClass1, 50L);
                viewRippler.mRoot.postOnAnimationDelayed(anonymousClass1, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                viewRippler.mRoot.postOnAnimationDelayed(anonymousClass1, 4000L);
                viewRippler.mRoot.postOnAnimationDelayed(anonymousClass1, 6000L);
                viewRippler.mRoot.postOnAnimationDelayed(anonymousClass1, 8000L);
            }
            FloatingRotationButton floatingRotationButton2 = this.mRotationButton;
            if (floatingRotationButton2.mIsShowing) {
                return;
            }
            floatingRotationButton2.mIsShowing = true;
            floatingRotationButton2.mWindowManager.addView(floatingRotationButton2.mKeyButtonContainer, floatingRotationButton2.adjustViewPositionAndCreateLayoutParams());
            AnimatedVectorDrawable animatedVectorDrawable2 = floatingRotationButton2.mAnimatedDrawable;
            if (animatedVectorDrawable2 != null) {
                animatedVectorDrawable2.reset();
                floatingRotationButton2.mAnimatedDrawable.start();
            }
            OneShotPreDrawListener.add(floatingRotationButton2.mKeyButtonView, new FloatingRotationButton$$ExternalSyntheticLambda0(floatingRotationButton2, i));
            return;
        }
        View view2 = viewRippler.mRoot;
        if (view2 != null) {
            view2.removeCallbacks(viewRippler.mRipple);
        }
        if (z2) {
            Animator animator2 = this.mRotateHideAnimator;
            if (animator2 != null && animator2.isRunning()) {
                this.mRotateHideAnimator.pause();
            }
            this.mRotationButton.hide();
            return;
        }
        Animator animator3 = this.mRotateHideAnimator;
        if (animator3 == null || !animator3.isRunning()) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(floatingRotationButtonView, "alpha", 0.0f);
            ofFloat.setDuration(100L);
            ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shared.rotation.RotationButtonController.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator4) {
                    RotationButtonController.this.mRotationButton.hide();
                }
            });
            this.mRotateHideAnimator = ofFloat;
            ofFloat.start();
        }
    }
}
