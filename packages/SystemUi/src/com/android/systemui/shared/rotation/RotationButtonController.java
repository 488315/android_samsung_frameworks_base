package com.android.systemui.shared.rotation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.IRotationWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.shared.recents.utilities.ViewRippler;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.util.DeviceType;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationButtonController {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public final AccessibilityManager mAccessibilityManager;
    public SystemBarProxy mBarProxy;
    public final RotationButtonController$$ExternalSyntheticLambda0 mCancelPendingRotationProposal;
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
    public final RotationButtonController$$ExternalSyntheticLambda0 mRemoveRotationProposal;
    public Animator mRotateHideAnimator;
    public final RotationButtonController$$ExternalSyntheticLambda0 mRotateSuggestionButtonShowRunnable;
    public RotationButton mRotationButton;
    public int mSamsungIconCCWStart0ResId;
    public int mSamsungIconCCWStart180ResId;
    public int mSamsungIconCCWStart90ResId;
    public int mSamsungIconCWStart0ResId;
    public int mSamsungIconCWStart180ResId;
    public int mSamsungIconCWStart90ResId;
    public int mSamsungRotateButtonResId;
    public boolean mSkipOverrideUserLockPrefsOnce;
    public int mStyleRes;
    public final TaskStackListenerImpl mTaskStackListener;
    public final Supplier mWindowRotationProvider;
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public final UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final ViewRippler mViewRippler = new ViewRippler();
    public boolean mListenersRegistered = false;
    public boolean mRotationWatcherRegistered = false;
    public int mBehavior = 1;
    public final C24911 mDockedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shared.rotation.RotationButtonController.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            Interpolator interpolator = RotationButtonController.LINEAR_INTERPOLATOR;
            rotationButtonController.getClass();
            if (intent == null) {
                return;
            }
            rotationButtonController.mDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0;
        }
    };
    public final C24922 mRotationWatcher = new C24922();
    public long mLastUnknownRotationProposedTick = 0;
    public final RotationButtonController$$ExternalSyntheticLambda1 mRotationLockCallback = new Consumer() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            rotationButtonController.getClass();
            if (((Boolean) obj).booleanValue()) {
                rotationButtonController.mLastUnknownRotationProposedTick = 0L;
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shared.rotation.RotationButtonController$2 */
    public final class C24922 extends IRotationWatcher.Stub {
        public C24922() {
        }

        public final void onRotationChanged(final int i) {
            RotationButtonController.this.mMainThreadHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.shared.rotation.RotationButtonController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RotationButtonController.C24922 c24922 = RotationButtonController.C24922.this;
                    RotationButtonController.this.onRotationWatcherChanged(i);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskStackListenerImpl implements TaskStackChangeListener {
        public /* synthetic */ TaskStackListenerImpl(RotationButtonController rotationButtonController, int i) {
            this();
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onActivityRequestedOrientationChanged(final int i) {
            Optional.ofNullable(ActivityManagerWrapper.sInstance).map(new C2489x8e6fda0e()).ifPresent(new Consumer() { // from class: com.android.systemui.shared.rotation.RotationButtonController$TaskStackListenerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RotationButtonController.TaskStackListenerImpl taskStackListenerImpl = RotationButtonController.TaskStackListenerImpl.this;
                    int i2 = i;
                    taskStackListenerImpl.getClass();
                    if (((ActivityManager.RunningTaskInfo) obj).id == i2) {
                        RotationButtonController.this.setRotateSuggestionButtonState(false);
                    }
                }
            });
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

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.shared.rotation.RotationButtonController$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0] */
    public RotationButtonController(Context context, int i, int i2, int i3, int i4, int i5, int i6, Supplier<Integer> supplier) {
        final int i7 = 0;
        final int i8 = 1;
        this.mRemoveRotationProposal = new Runnable(this) { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0
            public final /* synthetic */ RotationButtonController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i7) {
                    case 0:
                        this.f$0.setRotateSuggestionButtonState(false);
                        break;
                    case 1:
                        this.f$0.mPendingRotationSuggestion = false;
                        break;
                    default:
                        this.f$0.setRotateSuggestionButtonState(true, false);
                        break;
                }
            }
        };
        this.mCancelPendingRotationProposal = new Runnable(this) { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0
            public final /* synthetic */ RotationButtonController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i8) {
                    case 0:
                        this.f$0.setRotateSuggestionButtonState(false);
                        break;
                    case 1:
                        this.f$0.mPendingRotationSuggestion = false;
                        break;
                    default:
                        this.f$0.setRotateSuggestionButtonState(true, false);
                        break;
                }
            }
        };
        final int i9 = 2;
        this.mRotateSuggestionButtonShowRunnable = new Runnable(this) { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0
            public final /* synthetic */ RotationButtonController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i9) {
                    case 0:
                        this.f$0.setRotateSuggestionButtonState(false);
                        break;
                    case 1:
                        this.f$0.mPendingRotationSuggestion = false;
                        break;
                    default:
                        this.f$0.setRotateSuggestionButtonState(true, false);
                        break;
                }
            }
        };
        this.mContext = context;
        this.mLightIconColor = i;
        this.mDarkIconColor = i2;
        this.mIconCcwStart0ResId = i3;
        this.mIconCcwStart90ResId = i4;
        this.mIconCwStart0ResId = i5;
        this.mIconCwStart90ResId = i6;
        this.mIconResId = i4;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mTaskStackListener = new TaskStackListenerImpl(this, i7);
        this.mWindowRotationProvider = supplier;
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            this.mKeyguardManager = (KeyguardManager) getContext().getSystemService("keyguard");
        }
    }

    public boolean canShowRotationButton() {
        return this.mIsNavigationBarShowing || this.mBehavior == 1 || QuickStepContract.isGesturalMode(this.mNavBarMode);
    }

    public final Context getContext() {
        boolean z = BasicRuneWrapper.NAVBAR_ENABLED;
        Context context = this.mContext;
        return z ? new ContextThemeWrapper(context.getApplicationContext(), this.mStyleRes) : context;
    }

    public final boolean isRotationLocked() {
        return BasicRuneWrapper.NAVBAR_ENABLED ? ((SamsungNavigationBarProxy) this.mBarProxy).rotationLocked : RotationPolicy.isRotationLocked(this.mContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRotationWatcherChanged(int i) {
        boolean z;
        RotationButton rotationButton;
        if (this.mListenersRegistered) {
            boolean isRotationLocked = isRotationLocked();
            if (isRotationLocked || (BasicRuneWrapper.NAVBAR_ENABLED && (rotationButton = this.mRotationButton) != null && rotationButton.isVisible())) {
                if (this.mSkipOverrideUserLockPrefsOnce) {
                    this.mSkipOverrideUserLockPrefsOnce = false;
                } else if (i == 0) {
                    z = true;
                    if (z && isRotationLocked && !this.mDocked && BasicRuneWrapper.NAVBAR_ENABLED) {
                        ((SamsungNavigationBarProxy) this.mBarProxy).getClass();
                        if (!DeviceType.isTablet()) {
                            RotationPolicy.setRotationLockAtAngle(this.mContext, isRotationLocked(), i);
                        }
                    }
                    setRotateSuggestionButtonState(false, true);
                }
                z = false;
                if (z) {
                    ((SamsungNavigationBarProxy) this.mBarProxy).getClass();
                    if (!DeviceType.isTablet()) {
                    }
                }
                setRotateSuggestionButtonState(false, true);
            }
        }
    }

    public final void rescheduleRotationTimeout(boolean z) {
        Animator animator;
        if (!z || (((animator = this.mRotateHideAnimator) == null || !animator.isRunning()) && this.mRotationButton.isVisible())) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3] */
    public final void setRotationButton(RotationButton rotationButton, NavigationBarView.C18592 c18592) {
        RotationButton rotationButton2;
        if (BasicRuneWrapper.NAVBAR_ENABLED && (rotationButton2 = this.mRotationButton) != null && rotationButton2 != rotationButton && rotationButton2.isVisible()) {
            this.mRotationButton.hide();
        }
        this.mRotationButton = rotationButton;
        rotationButton.setRotationButtonController(this);
        this.mRotationButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RotationButtonController rotationButtonController = RotationButtonController.this;
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                    ((SamsungNavigationBarProxy) rotationButtonController.mBarProxy).getClass();
                    MetricsLogger metricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
                    if (metricsLogger != null) {
                        metricsLogger.action(1287);
                    }
                }
                rotationButtonController.mUiEventLogger.log(RotationButtonController.RotationButtonEvent.ROTATION_SUGGESTION_ACCEPTED);
                Context context = rotationButtonController.mContext;
                ContentResolver contentResolver = context.getContentResolver();
                int i = Settings.Secure.getInt(contentResolver, "num_rotation_suggestions_accepted", 0);
                if (i < 1) {
                    Settings.Secure.putInt(contentResolver, "num_rotation_suggestions_accepted", i + 1);
                }
                RotationPolicy.setRotationLockAtAngle(context, rotationButtonController.isRotationLocked(), rotationButtonController.mLastRotationSuggestion);
                Log.i("RotationButtonController", "onRotateSuggestionClick() mLastRotationSuggestion=" + rotationButtonController.mLastRotationSuggestion);
                view.performHapticFeedback(1);
            }
        });
        this.mRotationButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                RotationButtonController rotationButtonController = RotationButtonController.this;
                rotationButtonController.getClass();
                int actionMasked = motionEvent.getActionMasked();
                rotationButtonController.mHoveringRotationSuggestion = actionMasked == 9 || actionMasked == 7;
                rotationButtonController.rescheduleRotationTimeout(true);
                return false;
            }
        });
        this.mRotationButton.setUpdatesCallback(c18592);
    }

    public final void showAndLogRotationSuggestion() {
        if (BasicRuneWrapper.NAVBAR_ENABLED && this.mRotationButton.isVisible()) {
            this.mRotationButton.hide();
        }
        setRotateSuggestionButtonState(true);
        rescheduleRotationTimeout(false);
        this.mUiEventLogger.log(RotationButtonEvent.ROTATION_SUGGESTION_SHOWN);
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            this.mLastUnknownRotationProposedTick = 0L;
            ((SamsungNavigationBarProxy) this.mBarProxy).getClass();
            MetricsLogger metricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
            if (metricsLogger != null) {
                metricsLogger.visible(1288);
            }
        }
    }

    public final void setRotateSuggestionButtonState(boolean z, boolean z2) {
        View currentView;
        Drawable imageDrawable;
        if (BasicRuneWrapper.NAVBAR_ENABLED && this.mRotationButton == null) {
            return;
        }
        if ((!z && !this.mRotationButton.isVisible()) || (currentView = this.mRotationButton.getCurrentView()) == null || (imageDrawable = this.mRotationButton.getImageDrawable()) == null) {
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
            currentView.setAlpha(1.0f);
            if (imageDrawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageDrawable;
                animatedVectorDrawable.reset();
                animatedVectorDrawable.start();
            }
            if (!(Settings.Secure.getInt(this.mContext.getContentResolver(), "num_rotation_suggestions_accepted", 0) >= 1)) {
                View view = viewRippler.mRoot;
                if (view != null) {
                    view.removeCallbacks(viewRippler.mRipple);
                }
                viewRippler.mRoot = currentView;
                ViewRippler.RunnableC24881 runnableC24881 = viewRippler.mRipple;
                currentView.postOnAnimationDelayed(runnableC24881, 50L);
                viewRippler.mRoot.postOnAnimationDelayed(runnableC24881, 2000L);
                viewRippler.mRoot.postOnAnimationDelayed(runnableC24881, 4000L);
                viewRippler.mRoot.postOnAnimationDelayed(runnableC24881, 6000L);
                viewRippler.mRoot.postOnAnimationDelayed(runnableC24881, 8000L);
            }
            this.mRotationButton.show();
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
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(currentView, "alpha", 0.0f);
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
