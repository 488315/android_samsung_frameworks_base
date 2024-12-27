package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemProperties;
import android.os.Trace;
import android.text.TextUtils;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;

import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.util.function.TriFunction;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class InsetsController implements WindowInsetsController, InsetsAnimationControlCallbacks {
    private static final int ANIMATION_DELAY_DIM_MS = 500;
    private static final int ANIMATION_DURATION_FADE_IN_MS = 500;
    private static final int ANIMATION_DURATION_FADE_OUT_MS = 1500;
    private static final int ANIMATION_DURATION_MOVE_IN_MS = 275;
    private static final int ANIMATION_DURATION_MOVE_OUT_MS = 340;
    public static final int ANIMATION_DURATION_RESIZE = 300;
    private static final int ANIMATION_DURATION_SYNC_IME_MS = 285;
    private static final int ANIMATION_DURATION_UNSYNC_IME_MS = 200;
    public static final int ANIMATION_TYPE_HIDE = 1;
    public static final int ANIMATION_TYPE_NONE = -1;
    public static final int ANIMATION_TYPE_RESIZE = 3;
    public static final int ANIMATION_TYPE_SHOW = 0;
    public static final int ANIMATION_TYPE_USER = 2;
    static final boolean DEBUG;
    private static final boolean ENABLE_SEP_FLAGSHIP_IME_ANIMATION;
    private static final int FLOATING_IME_BOTTOM_INSET_DP = -80;
    private static final int ID_CAPTION_BAR;
    public static final int LAYOUT_INSETS_DURING_ANIMATION_HIDDEN = 1;
    public static final int LAYOUT_INSETS_DURING_ANIMATION_SHOWN = 0;
    private static final int PENDING_CONTROL_TIMEOUT_MS = 2000;
    public static final Interpolator RESIZE_INTERPOLATOR;
    private static final int SEP_ANIMATION_DURATION_IME_FLAGSHIP_HIDE_MS = 300;
    private static final int SEP_ANIMATION_DURATION_IME_FLAGSHIP_SHOW_MS = 350;
    private static final int SEP_ANIMATION_DURATION_IME_HIDE_MS = 280;
    private static final int SEP_ANIMATION_DURATION_IME_SHOW_MS = 280;
    private static final Interpolator SEP_IME_HIDE_INTERPOLATOR;
    private static final Interpolator SEP_IME_SHOW_INTERPOLATOR;
    private static final String TAG = "InsetsController";
    static final boolean WARN = false;
    private static TypeEvaluator<Insets> sEvaluator;
    private final Runnable mAnimCallback;
    private boolean mAnimCallbackScheduled;
    private boolean mAnimationsDisabled;
    private int mAppearanceControlled;
    private int mAppearanceFromResource;
    private boolean mBehaviorControlled;
    private int mCancelledForNewAnimationTypes;
    private int mCaptionInsetsHeight;
    private boolean mCompatSysUiVisibilityStaled;
    private final TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer>
            mConsumerCreator;
    private final ArrayList<WindowInsetsController.OnControllableInsetsChangedListener>
            mControllableInsetsChangedListeners;
    private int mControllableTypes;
    private int mExistingTypes;
    private final Rect mFrame;
    private final Handler mHandler;
    private final Host mHost;
    private int mImeCaptionBarInsetsHeight;
    private final InsetsSourceConsumer mImeSourceConsumer;
    private final Runnable mInvokeControllableInsetsChangedListeners;
    private boolean mIsPredictiveBackImeHideAnimInProgress;
    private final ImeTracker.InputMethodJankContext mJankContext;
    private int mLastActivityType;
    private final InsetsState mLastDispatchedState;
    private WindowInsets mLastInsets;
    private int mLastLegacySoftInputMode;
    private int mLastLegacySystemUiFlags;
    private int mLastLegacyWindowFlags;
    private int mLastStartedAnimTypes;
    private WindowInsetsAnimationControlListener mLoggingListener;
    private final Runnable mPendingControlTimeout;
    private PendingControlRequest mPendingImeControlRequest;
    private final InsetsState.OnTraverseCallbacks mRemoveGoneSources;
    private int mReportedRequestedVisibleTypes;
    private int mRequestedVisibleTypes;
    private final ArrayList<RunningAnimation> mRunningAnimations;
    private final SparseArray<InsetsSourceConsumer> mSourceConsumers;
    private final InsetsState.OnTraverseCallbacks mStartResizingAnimationIfNeeded;
    private boolean mStartingAnimation;
    private final InsetsState mState;
    private boolean mSystemBarControlledByPolicy;
    private final SparseArray<InsetsSourceControl> mTmpControlArray;
    private int mTypesBeingCancelled;
    private int mVisibleTypes;
    private int mWindowType;
    private static final Interpolator SYSTEM_BARS_INSETS_INTERPOLATOR =
            new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator SYSTEM_BARS_ALPHA_INTERPOLATOR =
            new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
    private static final Interpolator SYSTEM_BARS_DIM_INTERPOLATOR =
            new Interpolator() { // from class:
                                 // android.view.InsetsController$$ExternalSyntheticLambda6
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    return InsetsController.lambda$static$0(f);
                }
            };
    private static final Interpolator SYNC_IME_INTERPOLATOR =
            new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    private static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR =
            new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR =
            new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    private static final String PROP_ENABLE_SEP_IME_ANIMATION =
            "persist.sys.ime.enable_sep_ime_animation";
    private static final boolean ENABLE_SEP_IME_ANIMATION =
            SystemProperties.getBoolean(PROP_ENABLE_SEP_IME_ANIMATION, true);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {}

    @Retention(RetentionPolicy.SOURCE)
    @interface LayoutInsetsDuringAnimation {}

    public interface Host {
        void addOnPreDrawRunnable(Runnable runnable);

        void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        int dipToPx(int i);

        void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation);

        void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation);

        WindowInsets dispatchWindowInsetsAnimationProgress(
                WindowInsets windowInsets, List<WindowInsetsAnimation> list);

        WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(
                WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds);

        Handler getHandler();

        InputMethodManager getInputMethodManager();

        String getRootViewTitle();

        int getSystemBarsAppearance();

        int getSystemBarsBehavior();

        IBinder getWindowToken();

        boolean hasAnimationCallbacks();

        void notifyInsetsChanged();

        void postInsetsAnimationCallback(Runnable runnable);

        void releaseSurfaceControlFromRt(SurfaceControl surfaceControl);

        void setSystemBarsAppearance(int i, int i2);

        void setSystemBarsBehavior(int i);

        void updateRequestedVisibleTypes(int i);

        default void updateCompatSysUiVisibility(
                int visibleTypes, int requestedVisibleTypes, int controllableTypes) {}

        default Context getRootViewContext() {
            return null;
        }

        default CompatibilityInfo.Translator getTranslator() {
            return null;
        }

        default void applyInsetsHintSandboxingIfNeeded(InsetsSourceControl[] controls) {}

        default void notifyAnimationRunningStateChanged(boolean running) {}

        default boolean isHandlingPointerEvent() {
            return false;
        }
    }

    static {
        ENABLE_SEP_FLAGSHIP_IME_ANIMATION =
                SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_CAMERA_CONFIG_STRIDE_OCR_VERSION")
                                .equals("V2")
                        && !SystemProperties.get("ro.product.name", "").startsWith("m44x");
        SEP_IME_SHOW_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        SEP_IME_HIDE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        RESIZE_INTERPOLATOR = new LinearInterpolator();
        ID_CAPTION_BAR = InsetsSource.createId(null, 0, WindowInsets.Type.captionBar());
        DEBUG = CoreRune.FW_INSETS_LOG_DEBUG;
        sEvaluator =
                new TypeEvaluator() { // from class:
                                      // android.view.InsetsController$$ExternalSyntheticLambda7
                    @Override // android.animation.TypeEvaluator
                    public final Object evaluate(float f, Object obj, Object obj2) {
                        Insets of;
                        Insets insets = (Insets) obj;
                        Insets insets2 = (Insets) obj2;
                        of =
                                Insets.of(
                                        (int) (insets.left + ((insets2.left - insets.left) * f)),
                                        (int) (insets.top + ((insets2.top - insets.top) * f)),
                                        (int) (insets.right + ((insets2.right - insets.right) * f)),
                                        (int)
                                                (insets.bottom
                                                        + ((insets2.bottom - insets.bottom) * f)));
                        return of;
                    }
                };
    }

    static /* synthetic */ float lambda$static$0(float alphaFraction) {
        float fraction = 1.0f - alphaFraction;
        if (fraction <= 0.33333334f) {
            return 1.0f;
        }
        float innerFraction = (fraction - 0.33333334f) / 0.6666666f;
        return 1.0f - SYSTEM_BARS_ALPHA_INTERPOLATOR.getInterpolation(innerFraction);
    }

    public static class InternalAnimationControlListener
            implements WindowInsetsAnimationControlListener {
        private ValueAnimator mAnimator;
        private final int mBehavior;
        private WindowInsetsAnimationController mController;
        private final boolean mDisable;
        private final long mDurationMs;
        private final int mFloatingImeBottomInset;
        private boolean mFullscreenMode;
        private final boolean mHasAnimationCallbacks;
        private final ImeTracker.InputMethodJankContext mInputMethodJankContext;
        private final WindowInsetsAnimationControlListener mLoggingListener;
        private final int mRequestedTypes;
        private final boolean mShow;

        public InternalAnimationControlListener(
                boolean show,
                boolean hasAnimationCallbacks,
                int requestedTypes,
                int behavior,
                boolean disable,
                int floatingImeBottomInset,
                WindowInsetsAnimationControlListener loggingListener,
                ImeTracker.InputMethodJankContext jankContext) {
            this.mFullscreenMode = false;
            this.mShow = show;
            this.mHasAnimationCallbacks = hasAnimationCallbacks;
            this.mRequestedTypes = requestedTypes;
            this.mBehavior = behavior;
            this.mDurationMs = calculateDurationMs();
            this.mDisable = disable;
            this.mFloatingImeBottomInset = floatingImeBottomInset;
            this.mLoggingListener = loggingListener;
            this.mInputMethodJankContext = jankContext;
        }

        public InternalAnimationControlListener(
                boolean show,
                boolean hasAnimationCallbacks,
                int requestedTypes,
                int behavior,
                boolean disable,
                int floatingImeBottomInset,
                WindowInsetsAnimationControlListener loggingListener,
                ImeTracker.InputMethodJankContext jankContext,
                boolean fullscreenMode) {
            this.mFullscreenMode = false;
            this.mFullscreenMode = fullscreenMode;
            this.mShow = show;
            this.mHasAnimationCallbacks = hasAnimationCallbacks;
            this.mRequestedTypes = requestedTypes;
            this.mBehavior = behavior;
            this.mDurationMs = calculateDurationMs();
            this.mDisable = disable;
            this.mFloatingImeBottomInset = floatingImeBottomInset;
            this.mLoggingListener = loggingListener;
            this.mInputMethodJankContext = jankContext;
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onReady(final WindowInsetsAnimationController controller, int types) {
            Insets insets;
            final Insets start;
            final Insets end;
            this.mController = controller;
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "default animation onReady types: " + types);
            }
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onReady(controller, types);
            }
            if (this.mDisable) {
                onAnimationFinish();
                return;
            }
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.setDuration(this.mDurationMs);
            if (ValueAnimator.getDurationScale() < 0.5f && (WindowInsets.Type.ime() & types) == 0) {
                this.mAnimator.overrideDurationScale(0.5f);
            }
            this.mAnimator.setInterpolator(new LinearInterpolator());
            Insets hiddenInsets = controller.getHiddenStateInsets();
            if (controller.hasZeroInsetsIme()) {
                insets =
                        Insets.of(
                                hiddenInsets.left,
                                hiddenInsets.top,
                                hiddenInsets.right,
                                this.mFloatingImeBottomInset);
            } else {
                insets = hiddenInsets;
            }
            Insets hiddenInsets2 = insets;
            if (this.mShow) {
                start = hiddenInsets2;
            } else {
                start = controller.getShownStateInsets();
            }
            if (this.mShow) {
                end = controller.getShownStateInsets();
            } else {
                end = hiddenInsets2;
            }
            final Interpolator insetsInterpolator = getInsetsInterpolator();
            final Interpolator alphaInterpolator = getAlphaInterpolator();
            this.mAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            InsetsController.InternalAnimationControlListener.this.lambda$onReady$0(
                                    insetsInterpolator,
                                    controller,
                                    start,
                                    end,
                                    alphaInterpolator,
                                    valueAnimator);
                        }
                    });
            this.mAnimator.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.view.InsetsController.InternalAnimationControlListener.1
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            if (InternalAnimationControlListener.this.mInputMethodJankContext
                                    == null) {
                                return;
                            }
                            ImeTracker.forJank()
                                    .onRequestAnimation(
                                            InternalAnimationControlListener.this
                                                    .mInputMethodJankContext,
                                            InternalAnimationControlListener.this
                                                    .getAnimationType(),
                                            !InternalAnimationControlListener.this
                                                    .mHasAnimationCallbacks);
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {
                            if (InternalAnimationControlListener.this.mInputMethodJankContext
                                    == null) {
                                return;
                            }
                            ImeTracker.forJank()
                                    .onCancelAnimation(
                                            InternalAnimationControlListener.this
                                                    .getAnimationType());
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            InternalAnimationControlListener.this.onAnimationFinish();
                            if (InternalAnimationControlListener.this.mInputMethodJankContext
                                    == null) {
                                return;
                            }
                            ImeTracker.forJank()
                                    .onFinishAnimation(
                                            InternalAnimationControlListener.this
                                                    .getAnimationType());
                        }
                    });
            this.mAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$0(
                Interpolator insetsInterpolator,
                WindowInsetsAnimationController controller,
                Insets start,
                Insets end,
                Interpolator alphaInterpolator,
                ValueAnimator animation) {
            float alphaFraction;
            float rawFraction = animation.getAnimatedFraction();
            if (this.mShow) {
                alphaFraction = rawFraction;
            } else {
                alphaFraction = 1.0f - rawFraction;
            }
            float insetsFraction = insetsInterpolator.getInterpolation(rawFraction);
            controller.setInsetsAndAlpha(
                    (Insets) InsetsController.sEvaluator.evaluate(insetsFraction, start, end),
                    alphaInterpolator.getInterpolation(alphaFraction),
                    rawFraction);
            if (InsetsController.DEBUG) {
                Log.d(
                        InsetsController.TAG,
                        "Default animation setInsetsAndAlpha fraction: " + insetsFraction);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onFinished(WindowInsetsAnimationController controller) {
            if (InsetsController.DEBUG) {
                Log.d(
                        InsetsController.TAG,
                        "InternalAnimationControlListener onFinished types:"
                                + WindowInsets.Type.toString(this.mRequestedTypes));
            }
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onFinished(controller);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onCancelled(WindowInsetsAnimationController controller) {
            if (this.mAnimator != null) {
                this.mAnimator.cancel();
            }
            if (InsetsController.DEBUG) {
                Log.d(
                        InsetsController.TAG,
                        "InternalAnimationControlListener onCancelled types:"
                                + this.mRequestedTypes);
            }
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onCancelled(controller);
            }
        }

        protected Interpolator getInsetsInterpolator() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) != 0) {
                if (InsetsController.ENABLE_SEP_IME_ANIMATION
                        && InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION) {
                    return this.mShow
                            ? InsetsController.SEP_IME_SHOW_INTERPOLATOR
                            : InsetsController.SEP_IME_HIDE_INTERPOLATOR;
                }
                if (this.mHasAnimationCallbacks) {
                    return InsetsController.SYNC_IME_INTERPOLATOR;
                }
                if (this.mShow) {
                    return InsetsController.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
                }
                return InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            }
            if (this.mBehavior == 2) {
                return InsetsController.SYSTEM_BARS_INSETS_INTERPOLATOR;
            }
            return new Interpolator() { // from class:
                                        // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    float lambda$getInsetsInterpolator$1;
                    lambda$getInsetsInterpolator$1 =
                            InsetsController.InternalAnimationControlListener.this
                                    .lambda$getInsetsInterpolator$1(f);
                    return lambda$getInsetsInterpolator$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float lambda$getInsetsInterpolator$1(float input) {
            return this.mShow ? 1.0f : 0.0f;
        }

        Interpolator getAlphaInterpolator() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) != 0) {
                if (InsetsController.ENABLE_SEP_IME_ANIMATION && !this.mFullscreenMode) {
                    return new Interpolator() { // from class:
                                                // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda2
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener
                                    .lambda$getAlphaInterpolator$2(f);
                        }
                    };
                }
                if (this.mHasAnimationCallbacks) {
                    return new Interpolator() { // from class:
                                                // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda3
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener
                                    .lambda$getAlphaInterpolator$3(f);
                        }
                    };
                }
                if (this.mShow) {
                    return new Interpolator() { // from class:
                                                // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda4
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            float min;
                            min = Math.min(1.0f, 2.0f * f);
                            return min;
                        }
                    };
                }
                return InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            }
            if (this.mBehavior == 2) {
                return new Interpolator() { // from class:
                                            // android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda5
                    @Override // android.animation.TimeInterpolator
                    public final float getInterpolation(float f) {
                        return InsetsController.InternalAnimationControlListener
                                .lambda$getAlphaInterpolator$5(f);
                    }
                };
            }
            if (this.mShow) {
                return InsetsController.SYSTEM_BARS_ALPHA_INTERPOLATOR;
            }
            return InsetsController.SYSTEM_BARS_DIM_INTERPOLATOR;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$2(float input) {
            return 1.0f;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$3(float input) {
            return 1.0f;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$5(float input) {
            return 1.0f;
        }

        protected void onAnimationFinish() {
            this.mController.finish(this.mShow);
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "onAnimationFinish showOnFinish: " + this.mShow);
            }
        }

        public long getDurationMs() {
            return this.mDurationMs;
        }

        private long calculateDurationMs() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) == 0) {
                return (this.mBehavior == 2 || CoreRune.FW_INSET_ANIM)
                        ? this.mShow ? 275L : 340L
                        : this.mShow ? 500L : 1500L;
            }
            if (InsetsController.ENABLE_SEP_IME_ANIMATION && !this.mFullscreenMode) {
                return getSepAnimationDurationIme();
            }
            if (this.mHasAnimationCallbacks) {
                return 285L;
            }
            return 200L;
        }

        private int getSepAnimationDurationIme() {
            return this.mShow
                    ? InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 350 : 280
                    : InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 300 : 280;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getAnimationType() {
            return !this.mShow ? 1 : 0;
        }
    }

    private static class RunningAnimation {
        final InsetsAnimationControlRunner runner;
        boolean startDispatched;
        final int type;

        RunningAnimation(InsetsAnimationControlRunner runner, int type) {
            this.runner = runner;
            this.type = type;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingControlRequest {
        final int animationType;
        final CancellationSignal cancellationSignal;
        final long durationMs;
        final Interpolator interpolator;
        final int layoutInsetsDuringAnimation;
        final WindowInsetsAnimationControlListener listener;
        int types;
        final boolean useInsetsAnimationThread;

        PendingControlRequest(
                int types,
                WindowInsetsAnimationControlListener listener,
                long durationMs,
                Interpolator interpolator,
                int animationType,
                int layoutInsetsDuringAnimation,
                CancellationSignal cancellationSignal,
                boolean useInsetsAnimationThread) {
            this.types = types;
            this.listener = listener;
            this.durationMs = durationMs;
            this.interpolator = interpolator;
            this.animationType = animationType;
            this.layoutInsetsDuringAnimation = layoutInsetsDuringAnimation;
            this.cancellationSignal = cancellationSignal;
            this.useInsetsAnimationThread = useInsetsAnimationThread;
        }
    }

    public InsetsController(Host host) {
        this(
                host,
                new TriFunction() { // from class:
                                    // android.view.InsetsController$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.function.TriFunction
                    public final Object apply(Object obj, Object obj2, Object obj3) {
                        return InsetsController.lambda$new$2(
                                (InsetsController) obj, (Integer) obj2, (Integer) obj3);
                    }
                },
                host.getHandler());
    }

    static /* synthetic */ InsetsSourceConsumer lambda$new$2(
            InsetsController controller, Integer id, Integer type) {
        if (type.intValue() == WindowInsets.Type.ime()) {
            return new ImeInsetsSourceConsumer(
                    id.intValue(),
                    controller.mState,
                    new InsetsController$$ExternalSyntheticLambda13(),
                    controller);
        }
        return new InsetsSourceConsumer(
                id.intValue(),
                type.intValue(),
                controller.mState,
                new InsetsController$$ExternalSyntheticLambda13(),
                controller);
    }

    public InsetsController(
            Host host,
            TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer> consumerCreator,
            Handler handler) {
        this.mJankContext =
                new ImeTracker
                        .InputMethodJankContext() { // from class: android.view.InsetsController.1
                    @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
                    public Context getDisplayContext() {
                        if (InsetsController.this.mHost != null) {
                            return InsetsController.this.mHost.getRootViewContext();
                        }
                        return null;
                    }

                    @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
                    public SurfaceControl getTargetSurfaceControl() {
                        InsetsSourceControl imeSourceControl =
                                InsetsController.this.getImeSourceConsumer().getControl();
                        if (imeSourceControl != null) {
                            return imeSourceControl.getLeash();
                        }
                        return null;
                    }

                    @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
                    public String getHostPackageName() {
                        if (InsetsController.this.mHost != null) {
                            return InsetsController.this
                                    .mHost
                                    .getRootViewContext()
                                    .getPackageName();
                        }
                        return null;
                    }
                };
        this.mState = new InsetsState();
        this.mLastDispatchedState = new InsetsState();
        this.mFrame = new Rect();
        this.mSourceConsumers = new SparseArray<>();
        this.mTmpControlArray = new SparseArray<>();
        this.mRunningAnimations = new ArrayList<>();
        this.mCaptionInsetsHeight = 0;
        this.mImeCaptionBarInsetsHeight = 0;
        this.mPendingControlTimeout =
                new Runnable() { // from class:
                                 // android.view.InsetsController$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsController.this.abortPendingImeControlRequest();
                    }
                };
        this.mControllableInsetsChangedListeners = new ArrayList<>();
        this.mExistingTypes = 0;
        this.mVisibleTypes = WindowInsets.Type.defaultVisible();
        this.mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        this.mReportedRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        this.mInvokeControllableInsetsChangedListeners =
                new Runnable() { // from class:
                                 // android.view.InsetsController$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsController.this.invokeControllableInsetsChangedListeners();
                    }
                };
        this.mRemoveGoneSources =
                new InsetsState
                        .OnTraverseCallbacks() { // from class: android.view.InsetsController.2
                    private final IntArray mPendingRemoveIndexes = new IntArray();

                    @Override // android.view.InsetsState.OnTraverseCallbacks
                    public void onIdNotFoundInState2(int index1, InsetsSource source1) {
                        if (source1.getId() == InsetsSource.ID_IME_CAPTION_BAR) {
                            return;
                        }
                        this.mPendingRemoveIndexes.add(index1);
                    }

                    @Override // android.view.InsetsState.OnTraverseCallbacks
                    public void onFinish(InsetsState state1, InsetsState state2) {
                        for (int i = this.mPendingRemoveIndexes.size() - 1; i >= 0; i--) {
                            state1.removeSourceAt(this.mPendingRemoveIndexes.get(i));
                        }
                        this.mPendingRemoveIndexes.clear();
                    }
                };
        this.mStartResizingAnimationIfNeeded =
                new InsetsState
                        .OnTraverseCallbacks() { // from class: android.view.InsetsController.3
                    private InsetsState mFromState;
                    private InsetsState mToState;
                    private int mTypes;

                    @Override // android.view.InsetsState.OnTraverseCallbacks
                    public void onStart(InsetsState state1, InsetsState state2) {
                        this.mTypes = 0;
                        this.mFromState = null;
                        this.mToState = null;
                    }

                    @Override // android.view.InsetsState.OnTraverseCallbacks
                    public void onIdMatch(InsetsSource source1, InsetsSource source2) {
                        Rect frame1 = source1.getFrame();
                        Rect frame2 = source2.getFrame();
                        if (((source1.hasFlags(8) && source2.hasFlags(8))
                                        || (CoreRune.FW_MINIMIZED_IME_INSET_ANIM
                                                && (source1.getType() & WindowInsets.Type.ime())
                                                        != 0
                                                && !source1.getMinimizedInsetHint()
                                                        .equals(source2.getMinimizedInsetHint())))
                                && source1.isVisible()
                                && source2.isVisible()
                                && !frame1.equals(frame2)
                                && !frame1.isEmpty()
                                && !frame2.isEmpty()) {
                            if (!Rect.intersects(InsetsController.this.mFrame, source1.getFrame())
                                    && !Rect.intersects(
                                            InsetsController.this.mFrame, source2.getFrame())) {
                                return;
                            }
                            this.mTypes |= source1.getType();
                            if (this.mFromState == null) {
                                this.mFromState = new InsetsState();
                            }
                            if (this.mToState == null) {
                                this.mToState = new InsetsState();
                            }
                            this.mFromState.addSource(new InsetsSource(source1));
                            this.mToState.addSource(new InsetsSource(source2));
                        }
                    }

                    @Override // android.view.InsetsState.OnTraverseCallbacks
                    public void onFinish(InsetsState state1, InsetsState state2) {
                        if (this.mTypes == 0) {
                            return;
                        }
                        InsetsController.this.cancelExistingControllers(this.mTypes);
                        InsetsAnimationControlRunner runner =
                                new InsetsResizeAnimationRunner(
                                        InsetsController.this.mFrame,
                                        this.mFromState,
                                        this.mToState,
                                        InsetsController.RESIZE_INTERPOLATOR,
                                        300L,
                                        this.mTypes,
                                        InsetsController.this,
                                        InsetsController.this);
                        if (InsetsController.this.mRunningAnimations.isEmpty()) {
                            InsetsController.this.mHost.notifyAnimationRunningStateChanged(true);
                        }
                        InsetsController.this.mRunningAnimations.add(
                                new RunningAnimation(runner, runner.getAnimationType()));
                        Log.i(
                                InsetsController.TAG,
                                "startResizingAnimationIfNeeded: types="
                                        + WindowInsets.Type.toString(this.mTypes)
                                        + " host="
                                        + InsetsController.this.mHost.getRootViewTitle());
                    }
                };
        this.mHost = host;
        this.mConsumerCreator = consumerCreator;
        this.mHandler = handler;
        this.mAnimCallback =
                new Runnable() { // from class:
                                 // android.view.InsetsController$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsController.this.lambda$new$3();
                    }
                };
        this.mImeSourceConsumer = getSourceConsumer(InsetsSource.ID_IME, WindowInsets.Type.ime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        int i;
        this.mAnimCallbackScheduled = false;
        if (this.mRunningAnimations.isEmpty()) {
            return;
        }
        List<WindowInsetsAnimation> runningAnimations = new ArrayList<>();
        List<WindowInsetsAnimation> finishedAnimations = new ArrayList<>();
        InsetsState state = new InsetsState(this.mState, true);
        for (int i2 = this.mRunningAnimations.size() - 1; i2 >= 0; i2--) {
            RunningAnimation runningAnimation = this.mRunningAnimations.get(i2);
            if (DEBUG) {
                Log.d(TAG, "Running animation type: " + runningAnimation.type);
            }
            InsetsAnimationControlRunner runner = runningAnimation.runner;
            if (runner instanceof WindowInsetsAnimationController) {
                if (runningAnimation.startDispatched) {
                    runningAnimations.add(runner.getAnimation());
                }
                if (((InternalInsetsAnimationController) runner).applyChangeInsets(state)) {
                    finishedAnimations.add(runner.getAnimation());
                }
            }
        }
        Rect rect = this.mFrame;
        InsetsState insetsState = this.mState;
        boolean isRound = this.mLastInsets.isRound();
        int i3 = this.mLastLegacySoftInputMode;
        int i4 = this.mLastLegacyWindowFlags;
        if (this.mSystemBarControlledByPolicy) {
            i = this.mLastLegacySystemUiFlags & (-257);
        } else {
            i = this.mLastLegacySystemUiFlags;
        }
        WindowInsets insets =
                state.calculateInsets(
                        rect,
                        insetsState,
                        isRound,
                        i3,
                        i4,
                        i,
                        this.mWindowType,
                        this.mLastActivityType,
                        null);
        this.mHost.dispatchWindowInsetsAnimationProgress(
                insets, Collections.unmodifiableList(runningAnimations));
        if (DEBUG) {
            for (WindowInsetsAnimation anim : runningAnimations) {
                Log.d(
                        TAG,
                        String.format(
                                "Running animation on insets type: %d, progress: %f",
                                Integer.valueOf(anim.getTypeMask()),
                                Float.valueOf(anim.getInterpolatedFraction())));
            }
        }
        for (int i5 = finishedAnimations.size() - 1; i5 >= 0; i5--) {
            dispatchAnimationEnd(finishedAnimations.get(i5));
        }
    }

    public void onFrameChanged(Rect frame) {
        if (this.mFrame.equals(frame)) {
            return;
        }
        if (this.mImeCaptionBarInsetsHeight != 0) {
            setImeCaptionBarInsetsHeight(this.mImeCaptionBarInsetsHeight);
        }
        this.mHost.notifyInsetsChanged();
        this.mFrame.set(frame);
    }

    @Override // android.view.WindowInsetsController
    public InsetsState getState() {
        return this.mState;
    }

    @Override // android.view.WindowInsetsController
    public int getRequestedVisibleTypes() {
        return this.mRequestedVisibleTypes;
    }

    public InsetsState getLastDispatchedState() {
        return this.mLastDispatchedState;
    }

    public boolean onStateChanged(InsetsState state) {
        boolean stateChanged = !this.mState.equals(state, false, false);
        if (!stateChanged && this.mLastDispatchedState.equals(state)) {
            return false;
        }
        Log.i(
                TAG,
                "onStateChanged: host="
                        + this.mHost.getRootViewTitle()
                        + ", from="
                        + Debug.getCaller()
                        + ", state="
                        + state);
        InsetsState lastState = new InsetsState(this.mState, true);
        updateState(state);
        applyLocalVisibilityOverride();
        updateCompatSysUiVisibility();
        if (!this.mState.equals(lastState, false, true)) {
            if (DEBUG) {
                Log.d(TAG, "onStateChanged, notifyInsetsChanged");
            }
            this.mHost.notifyInsetsChanged();
            if (this.mLastDispatchedState.getDisplayFrame().equals(state.getDisplayFrame())) {
                InsetsState.traverse(
                        this.mLastDispatchedState, state, this.mStartResizingAnimationIfNeeded);
            }
        }
        this.mLastDispatchedState.set(state, true);
        return true;
    }

    private void updateState(InsetsState newState) {
        this.mState.set(newState, 0);
        int existingTypes = 0;
        int visibleTypes = 0;
        final int[] cancelledUserAnimationTypes = {0};
        int size = newState.sourceSize();
        for (int i = 0; i < size; i++) {
            InsetsSource source = newState.sourceAt(i);
            int type = source.getType();
            int animationType = getAnimationType(type);
            InsetsSourceConsumer consumer = this.mSourceConsumers.get(source.getId());
            if (consumer != null) {
                consumer.updateSource(source, animationType);
            } else {
                this.mState.addSource(source);
            }
            existingTypes |= type;
            if (source.isVisible()) {
                visibleTypes |= type;
            }
        }
        int i2 = WindowInsets.Type.defaultVisible();
        int visibleTypes2 = visibleTypes | (i2 & (~existingTypes));
        if (this.mVisibleTypes != visibleTypes2) {
            if (WindowInsets.Type.hasCompatSystemBars(this.mVisibleTypes ^ visibleTypes2)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mVisibleTypes = visibleTypes2;
        }
        if (this.mExistingTypes != existingTypes) {
            if (WindowInsets.Type.hasCompatSystemBars(this.mExistingTypes ^ existingTypes)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mExistingTypes = existingTypes;
        }
        InsetsState.traverse(this.mState, newState, this.mRemoveGoneSources);
        if (cancelledUserAnimationTypes[0] != 0) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.view.InsetsController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InsetsController.this.lambda$updateState$4(cancelledUserAnimationTypes);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$4(int[] cancelledUserAnimationTypes) {
        show(cancelledUserAnimationTypes[0]);
    }

    public WindowInsets calculateInsets(
            boolean isScreenRound,
            int windowType,
            int activityType,
            int legacySoftInputMode,
            int legacyWindowFlags,
            int legacySystemUiFlags) {
        int i;
        this.mWindowType = windowType;
        this.mLastActivityType = activityType;
        this.mLastLegacySoftInputMode = legacySoftInputMode;
        this.mLastLegacyWindowFlags = legacyWindowFlags;
        this.mLastLegacySystemUiFlags = legacySystemUiFlags;
        InsetsState insetsState = this.mState;
        Rect rect = this.mFrame;
        if (this.mSystemBarControlledByPolicy) {
            i = legacySystemUiFlags & (-257);
        } else {
            i = legacySystemUiFlags;
        }
        this.mLastInsets =
                insetsState.calculateInsets(
                        rect,
                        null,
                        isScreenRound,
                        legacySoftInputMode,
                        legacyWindowFlags,
                        i,
                        windowType,
                        activityType,
                        null);
        return this.mLastInsets;
    }

    public Insets calculateVisibleInsets(
            int windowType, int activityType, int softInputMode, int windowFlags) {
        return this.mState.calculateVisibleInsets(
                this.mFrame, windowType, activityType, softInputMode, windowFlags);
    }

    public void onControlsChanged(InsetsSourceControl[] activeControls) {
        this.mSystemBarControlledByPolicy = false;
        if (activeControls != null) {
            this.mHost.applyInsetsHintSandboxingIfNeeded(activeControls);
            for (InsetsSourceControl activeControl : activeControls) {
                if (activeControl != null) {
                    if (activeControl.isControlledByPolicy()) {
                        this.mSystemBarControlledByPolicy = true;
                    } else {
                        this.mTmpControlArray.put(activeControl.getId(), activeControl);
                    }
                }
            }
        }
        int controllableTypes = 0;
        int consumedControlCount = 0;
        int[] showTypes = new int[1];
        int[] hideTypes = new int[1];
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if (consumer.getId() != InsetsSource.ID_IME_CAPTION_BAR) {
                InsetsSourceControl control = this.mTmpControlArray.get(consumer.getId());
                if (control != null) {
                    controllableTypes |= control.getType();
                    consumedControlCount++;
                }
                consumer.setControl(control, showTypes, hideTypes);
            }
        }
        if (consumedControlCount != this.mTmpControlArray.size()) {
            for (int i2 = this.mTmpControlArray.size() - 1; i2 >= 0; i2--) {
                InsetsSourceControl control2 = this.mTmpControlArray.valueAt(i2);
                getSourceConsumer(control2.getId(), control2.getType())
                        .setControl(control2, showTypes, hideTypes);
            }
        }
        if (this.mTmpControlArray.size() > 0) {
            for (int i3 = this.mRunningAnimations.size() - 1; i3 >= 0; i3--) {
                this.mRunningAnimations.get(i3).runner.updateSurfacePosition(this.mTmpControlArray);
            }
        }
        this.mTmpControlArray.clear();
        int animatingTypes = invokeControllableInsetsChangedListeners();
        showTypes[0] = showTypes[0] & (~animatingTypes);
        hideTypes[0] = hideTypes[0] & (~animatingTypes);
        if (Flags.refactorInsetsController()) {
            if (this.mPendingImeControlRequest != null
                    && getImeSourceConsumer().getControl() != null
                    && getImeSourceConsumer().getControl().getLeash() != null) {
                handlePendingControlRequest(null);
            } else {
                if (showTypes[0] != 0) {
                    applyAnimation(showTypes[0], true, false, null);
                }
                if (hideTypes[0] != 0) {
                    applyAnimation(hideTypes[0], false, false, null);
                }
            }
        } else {
            if (showTypes[0] != 0) {
                ImeTracker.Token statsToken =
                        (showTypes[0] & WindowInsets.Type.ime()) == 0
                                ? null
                                : ImeTracker.forLogging()
                                        .onStart(1, 5, 46, this.mHost.isHandlingPointerEvent());
                applyAnimation(showTypes[0], true, false, statsToken);
            }
            if (hideTypes[0] != 0) {
                ImeTracker.Token statsToken2 =
                        (hideTypes[0] & WindowInsets.Type.ime()) != 0
                                ? ImeTracker.forLogging()
                                        .onStart(2, 5, 46, this.mHost.isHandlingPointerEvent())
                                : null;
                applyAnimation(hideTypes[0], false, false, statsToken2);
            }
        }
        if (this.mControllableTypes != controllableTypes) {
            if (WindowInsets.Type.hasCompatSystemBars(
                    this.mControllableTypes ^ controllableTypes)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mControllableTypes = controllableTypes;
        }
        if (Flags.refactorInsetsController()) {
            applyLocalVisibilityOverride();
        }
        reportRequestedVisibleTypes();
    }

    public void setPredictiveBackImeHideAnimInProgress(boolean isInProgress) {
        this.mIsPredictiveBackImeHideAnimInProgress = isInProgress;
        if (isInProgress) {
            for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
                InsetsAnimationControlRunner runner = this.mRunningAnimations.get(i).runner;
                if ((runner.getTypes() & WindowInsets.Type.ime()) != 0) {
                    runner.updateLayoutInsetsDuringAnimation(1);
                    return;
                }
            }
        }
    }

    boolean isPredictiveBackImeHideAnimInProgress() {
        return this.mIsPredictiveBackImeHideAnimInProgress;
    }

    @Override // android.view.WindowInsetsController
    public void show(int types) {
        show(types, false, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void show(int r17, boolean r18, android.view.inputmethod.ImeTracker.Token r19) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.InsetsController.show(int, boolean,"
                    + " android.view.inputmethod.ImeTracker$Token):void");
    }

    private void handlePendingControlRequest(ImeTracker.Token statsToken) {
        PendingControlRequest pendingRequest = this.mPendingImeControlRequest;
        this.mPendingImeControlRequest = null;
        this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        controlAnimationUnchecked(
                pendingRequest.types,
                pendingRequest.cancellationSignal,
                pendingRequest.listener,
                null,
                true,
                pendingRequest.durationMs,
                pendingRequest.interpolator,
                pendingRequest.animationType,
                pendingRequest.layoutInsetsDuringAnimation,
                pendingRequest.useInsetsAnimationThread,
                statsToken);
    }

    @Override // android.view.WindowInsetsController
    public void hide(int types) {
        hide(types, false, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void hide(int r17, boolean r18, android.view.inputmethod.ImeTracker.Token r19) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.InsetsController.hide(int, boolean,"
                    + " android.view.inputmethod.ImeTracker$Token):void");
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(
            int types,
            long durationMillis,
            Interpolator interpolator,
            CancellationSignal cancellationSignal,
            WindowInsetsAnimationControlListener listener) {
        controlWindowInsetsAnimation(
                types, cancellationSignal, listener, false, durationMillis, interpolator, 2, false);
    }

    public void controlWindowInsetsAnimation(
            int types,
            CancellationSignal cancellationSignal,
            WindowInsetsAnimationControlListener listener,
            boolean fromIme,
            long durationMs,
            Interpolator interpolator,
            int animationType,
            boolean fromPredictiveBack) {
        if ((this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame) & types) != 0) {
            listener.onCancelled(null);
            return;
        }
        if (fromIme) {
            ImeTracing.getInstance()
                    .triggerClientDump(
                            "InsetsController#controlWindowInsetsAnimation",
                            this.mHost.getInputMethodManager(),
                            null);
        }
        controlAnimationUnchecked(
                types,
                cancellationSignal,
                listener,
                this.mFrame,
                fromIme,
                durationMs,
                interpolator,
                animationType,
                getLayoutInsetsDuringAnimationMode(types, fromPredictiveBack),
                false,
                null);
    }

    private void controlAnimationUnchecked(
            int types,
            CancellationSignal cancellationSignal,
            WindowInsetsAnimationControlListener listener,
            Rect frame,
            boolean fromIme,
            long durationMs,
            Interpolator interpolator,
            int animationType,
            int layoutInsetsDuringAnimation,
            boolean useInsetsAnimationThread,
            ImeTracker.Token statsToken) {
        boolean visible = layoutInsetsDuringAnimation == 0;
        setRequestedVisibleTypes(visible ? types : 0, types);
        controlAnimationUncheckedInner(
                types,
                cancellationSignal,
                listener,
                frame,
                fromIme,
                durationMs,
                interpolator,
                animationType,
                layoutInsetsDuringAnimation,
                useInsetsAnimationThread,
                statsToken);
        reportRequestedVisibleTypes();
    }

    private void controlAnimationUncheckedInner(
            int types,
            CancellationSignal cancellationSignal,
            WindowInsetsAnimationControlListener listener,
            Rect frame,
            boolean fromIme,
            long durationMs,
            Interpolator interpolator,
            int animationType,
            int layoutInsetsDuringAnimation,
            boolean useInsetsAnimationThread,
            ImeTracker.Token statsToken) {
        SparseArray<InsetsSourceControl> controls;
        String str;
        String str2;
        String str3;
        int i;
        long j;
        int typesReady;
        int typesReady2;
        String str4;
        InsetsAnimationControlRunner insetsAnimationControlImpl;
        int i2;
        ImeTracker.Token token;
        boolean z;
        long j2;
        int i3;
        int typesReady3;
        String str5;
        String str6;
        boolean z2 = true;
        if ((this.mTypesBeingCancelled & types) != 0) {
            if (animationType != 0 && animationType != 1) {
                z2 = false;
            }
            boolean monitoredAnimation = z2;
            if (monitoredAnimation && (WindowInsets.Type.ime() & types) != 0) {
                if (animationType == 0) {
                    ImeTracker.forLatency()
                            .onShowCancelled(
                                    statsToken,
                                    40,
                                    new InsetsController$$ExternalSyntheticLambda2());
                } else {
                    ImeTracker.forLatency()
                            .onHideCancelled(
                                    statsToken,
                                    40,
                                    new InsetsController$$ExternalSyntheticLambda2());
                }
                ImeTracker.forLogging().onCancelled(statsToken, 33);
            }
            throw new IllegalStateException(
                    "Cannot start a new insets animation of "
                            + WindowInsets.Type.toString(types)
                            + " while an existing "
                            + WindowInsets.Type.toString(this.mTypesBeingCancelled)
                            + " is being cancelled.");
        }
        ImeTracker.forLogging().onProgress(statsToken, 33);
        if (types == 0) {
            listener.onCancelled(null);
            if (DEBUG) {
                Log.d(TAG, "no types to animate in controlAnimationUnchecked");
            }
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "controlAnimation types: " + types);
        }
        this.mLastStartedAnimTypes |= types;
        SparseArray<InsetsSourceControl> controls2 = new SparseArray<>();
        if (Flags.refactorInsetsController()) {
            Pair<Integer, Integer> typesReadyPair = collectSourceControlsV2(types, controls2);
            int typesReady4 = typesReadyPair.first.intValue();
            int typesWithoutLeash = typesReadyPair.second.intValue();
            if (animationType != 2) {
                typesReady3 = typesReady4;
                controls = controls2;
                str5 = "IC.showRequestFromApi";
                str6 = "IC.showRequestFromApiToImeReady";
                str = TAG;
            } else {
                if ((WindowInsets.Type.ime() & types) == 0 || (types & typesWithoutLeash) == 0) {
                    typesReady3 = typesReady4;
                    controls = controls2;
                    str5 = "IC.showRequestFromApi";
                    str6 = "IC.showRequestFromApiToImeReady";
                    str = TAG;
                } else {
                    typesReady3 = typesReady4;
                    controls = controls2;
                    str5 = "IC.showRequestFromApi";
                    str6 = "IC.showRequestFromApiToImeReady";
                    final PendingControlRequest request =
                            new PendingControlRequest(
                                    types,
                                    listener,
                                    durationMs,
                                    interpolator,
                                    animationType,
                                    0,
                                    cancellationSignal,
                                    false);
                    this.mPendingImeControlRequest = request;
                    this.mHandler.postDelayed(this.mPendingControlTimeout, 2000L);
                    if (DEBUG) {
                        str = TAG;
                        Log.d(str, "Ime not ready. Create pending request");
                    } else {
                        str = TAG;
                    }
                    if (cancellationSignal != null) {
                        cancellationSignal.setOnCancelListener(
                                new CancellationSignal
                                        .OnCancelListener() { // from class:
                                                              // android.view.InsetsController$$ExternalSyntheticLambda3
                                    @Override // android.os.CancellationSignal.OnCancelListener
                                    public final void onCancel() {
                                        InsetsController.this
                                                .lambda$controlAnimationUncheckedInner$5(request);
                                    }
                                });
                    }
                }
                if (typesReady3 != types) {
                    return;
                }
            }
            typesReady = typesReady3;
            str2 = str5;
            str3 = str6;
            i = 0;
            j = 8;
        } else {
            controls = controls2;
            str = TAG;
            Pair<Integer, Boolean> typesReadyPair2 =
                    collectSourceControls(fromIme, types, controls, animationType, statsToken);
            int typesReady5 = typesReadyPair2.first.intValue();
            boolean imeReady = typesReadyPair2.second.booleanValue();
            if (DEBUG) {
                Log.d(
                        str,
                        TextUtils.formatSimple(
                                "controlAnimationUnchecked, typesReady: %s imeReady: %s",
                                Integer.valueOf(typesReady5), Boolean.valueOf(imeReady)));
            }
            if (imeReady) {
                str2 = "IC.showRequestFromApi";
                str3 = "IC.showRequestFromApiToImeReady";
                i = 0;
                j = 8;
                typesReady = typesReady5;
            } else {
                abortPendingImeControlRequest();
                final PendingControlRequest request2 =
                        new PendingControlRequest(
                                types,
                                listener,
                                durationMs,
                                interpolator,
                                animationType,
                                layoutInsetsDuringAnimation,
                                cancellationSignal,
                                useInsetsAnimationThread);
                this.mPendingImeControlRequest = request2;
                this.mHandler.postDelayed(this.mPendingControlTimeout, 2000L);
                if (DEBUG) {
                    Log.d(str, "Ime not ready. Create pending request");
                }
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(
                            new CancellationSignal
                                    .OnCancelListener() { // from class:
                                                          // android.view.InsetsController$$ExternalSyntheticLambda4
                                @Override // android.os.CancellationSignal.OnCancelListener
                                public final void onCancel() {
                                    InsetsController.this.lambda$controlAnimationUncheckedInner$6(
                                            request2);
                                }
                            });
                }
                releaseControls(controls);
                setRequestedVisibleTypes(this.mReportedRequestedVisibleTypes, types);
                Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
                if (!fromIme) {
                    Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                    return;
                }
                return;
            }
        }
        if (typesReady == 0) {
            if (Flags.refactorInsetsController()) {
                Trace.asyncTraceEnd(j, str2, i);
                listener.onCancelled(null);
                return;
            }
            if (DEBUG) {
                Log.d(str, "No types ready. onCancelled()");
            }
            listener.onCancelled(null);
            Trace.asyncTraceEnd(j, str2, i);
            if (!fromIme) {
                Trace.asyncTraceEnd(j, str3, i);
                return;
            }
            return;
        }
        if (Flags.refactorInsetsController()) {
            this.mCancelledForNewAnimationTypes = typesReady;
            cancelExistingControllers(typesReady);
            this.mCancelledForNewAnimationTypes = i;
        } else {
            cancelExistingControllers(typesReady);
        }
        if (useInsetsAnimationThread) {
            typesReady2 = typesReady;
            str4 = str;
            insetsAnimationControlImpl =
                    new InsetsAnimationThreadControlRunner(
                            controls,
                            frame,
                            this.mState,
                            listener,
                            typesReady,
                            this,
                            durationMs,
                            interpolator,
                            animationType,
                            layoutInsetsDuringAnimation,
                            this.mHost.getTranslator(),
                            this.mHost.getHandler(),
                            statsToken);
        } else {
            typesReady2 = typesReady;
            str4 = str;
            insetsAnimationControlImpl =
                    new InsetsAnimationControlImpl(
                            controls,
                            frame,
                            this.mState,
                            listener,
                            typesReady2,
                            this,
                            durationMs,
                            interpolator,
                            animationType,
                            layoutInsetsDuringAnimation,
                            this.mHost.getTranslator(),
                            statsToken);
        }
        final InsetsAnimationControlRunner runner = insetsAnimationControlImpl;
        int typesReady6 = typesReady2;
        if ((WindowInsets.Type.ime() & typesReady6) == 0) {
            i2 = animationType;
            token = statsToken;
            z = true;
        } else {
            ImeTracing.getInstance()
                    .triggerClientDump(
                            "InsetsAnimationControlImpl", this.mHost.getInputMethodManager(), null);
            i2 = animationType;
            z = true;
            if (i2 != 1) {
                token = statsToken;
            } else {
                token = statsToken;
                ImeTracker.forLatency()
                        .onHidden(token, new InsetsController$$ExternalSyntheticLambda2());
            }
        }
        ImeTracker.forLogging().onProgress(token, 39);
        if (this.mRunningAnimations.isEmpty()) {
            this.mHost.notifyAnimationRunningStateChanged(z);
        }
        this.mRunningAnimations.add(new RunningAnimation(runner, i2));
        Log.i(
                str4,
                "controlAnimationUncheckedInner: Added types="
                        + WindowInsets.Type.toString(typesReady6)
                        + ", animType="
                        + i2
                        + ", host="
                        + this.mHost.getRootViewTitle()
                        + ", from="
                        + Debug.getCallers(3));
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(
                    new CancellationSignal
                            .OnCancelListener() { // from class:
                                                  // android.view.InsetsController$$ExternalSyntheticLambda5
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            InsetsController.this.lambda$controlAnimationUncheckedInner$7(runner);
                        }
                    });
            j2 = 8;
            i3 = 0;
        } else {
            j2 = 8;
            i3 = 0;
            Trace.asyncTraceBegin(8L, "IC.pendingAnim", 0);
        }
        if (!Flags.refactorInsetsController()) {
            onAnimationStateChanged(types, z);
        } else {
            onAnimationStateChanged(typesReady6, z);
        }
        if (fromIme) {
            switch (i2) {
                case 0:
                    Trace.asyncTraceEnd(j2, "IC.showRequestFromIme", i3);
                    return;
                case 1:
                    Trace.asyncTraceEnd(j2, "IC.hideRequestFromIme", i3);
                    return;
                default:
                    return;
            }
        }
        if (i2 == z) {
            Trace.asyncTraceEnd(j2, "IC.hideRequestFromApi", i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$5(
            PendingControlRequest request) {
        if (this.mPendingImeControlRequest == request) {
            if (DEBUG) {
                Log.d(TAG, "Cancellation signal abortPendingImeControlRequest");
            }
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$6(
            PendingControlRequest request) {
        if (this.mPendingImeControlRequest == request) {
            if (DEBUG) {
                Log.d(TAG, "Cancellation signal abortPendingImeControlRequest");
            }
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$7(
            InsetsAnimationControlRunner runner) {
        cancelAnimation(runner, true);
    }

    static void releaseControls(SparseArray<InsetsSourceControl> controls) {
        for (int i = controls.size() - 1; i >= 0; i--) {
            controls.valueAt(i).release(new InsetsController$$ExternalSyntheticLambda8());
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(
            WindowInsetsAnimationControlListener listener) {
        this.mLoggingListener = listener;
    }

    private Pair<Integer, Boolean> collectSourceControls(
            boolean fromIme,
            int types,
            SparseArray<InsetsSourceControl> controls,
            int animationType,
            ImeTracker.Token statsToken) {
        String str;
        ImeTracker.forLogging().onProgress(statsToken, 35);
        int typesReady = 0;
        boolean imeReady = true;
        boolean z = true;
        int i = this.mSourceConsumers.size() - 1;
        while (i >= 0) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if ((consumer.getType() & types) != 0) {
                boolean show = (animationType == 0 || animationType == 2) ? z : false;
                boolean canRun = true;
                if (show) {
                    switch (consumer.requestShow(fromIme, statsToken)) {
                        case 1:
                            imeReady = false;
                            if (DEBUG) {
                                Log.d(TAG, "requestShow IME_SHOW_DELAYED");
                                break;
                            }
                            break;
                        case 2:
                            canRun = false;
                            setRequestedVisibleTypes(0, consumer.getType());
                            break;
                    }
                } else {
                    consumer.requestHide(fromIme, statsToken);
                }
                if (!canRun) {
                    if (fromIme) {
                        Log.w(
                                TAG,
                                String.format(
                                        "collectSourceControls can't continue show for type: %s"
                                            + " fromIme: %b",
                                        WindowInsets.Type.toString(consumer.getType()),
                                        Boolean.valueOf(fromIme)));
                    }
                } else {
                    InsetsSourceControl control = consumer.getControl();
                    if (control != null
                            && (control.getLeash() != null
                                    || control.getId() == InsetsSource.ID_IME_CAPTION_BAR)) {
                        controls.put(control.getId(), new InsetsSourceControl(control));
                        typesReady |= consumer.getType();
                    }
                    if (fromIme) {
                        StringBuilder append =
                                new StringBuilder()
                                        .append(
                                                "collectSourceControls can't continue for type:"
                                                    + " ime, fromIme: true requires a control with"
                                                    + " a leash but we have ");
                        if (control == null) {
                            str = "control: null";
                        } else {
                            str = "control: non-null and control.getLeash(): null";
                        }
                        Log.w(TAG, append.append(str).toString());
                        ImeTracker.forLogging().onFailed(statsToken, 35);
                    }
                }
            }
            i--;
            z = true;
        }
        return new Pair<>(Integer.valueOf(typesReady), Boolean.valueOf(imeReady));
    }

    private Pair<Integer, Integer> collectSourceControlsV2(
            int types, SparseArray<InsetsSourceControl> controls) {
        InsetsSourceControl control;
        int typesReady = 0;
        int typesWithoutLeash = 0;
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if ((consumer.getType() & types) != 0 && (control = consumer.getControl()) != null) {
                if (control.getLeash() != null
                        || control.getId() == InsetsSource.ID_IME_CAPTION_BAR) {
                    controls.put(control.getId(), new InsetsSourceControl(control));
                    typesReady |= consumer.getType();
                } else {
                    typesWithoutLeash |= consumer.getType();
                }
            }
        }
        return new Pair<>(Integer.valueOf(typesReady), Integer.valueOf(typesWithoutLeash));
    }

    private int getLayoutInsetsDuringAnimationMode(int types, boolean fromPredictiveBack) {
        if (fromPredictiveBack || (this.mRequestedVisibleTypes & types) != types) {
            return 0;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelExistingControllers(int types) {
        int originalmTypesBeingCancelled = this.mTypesBeingCancelled;
        this.mTypesBeingCancelled |= types;
        try {
            for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
                InsetsAnimationControlRunner control = this.mRunningAnimations.get(i).runner;
                if ((control.getTypes() & types) != 0) {
                    cancelAnimation(control, true);
                }
            }
            int i2 = WindowInsets.Type.ime();
            if ((i2 & types) != 0) {
                abortPendingImeControlRequest();
            }
        } finally {
            this.mTypesBeingCancelled = originalmTypesBeingCancelled;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortPendingImeControlRequest() {
        if (this.mPendingImeControlRequest != null) {
            this.mPendingImeControlRequest.listener.onCancelled(null);
            this.mPendingImeControlRequest = null;
            this.mHandler.removeCallbacks(this.mPendingControlTimeout);
            if (DEBUG) {
                Log.d(TAG, "abortPendingImeControlRequest");
            }
        }
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void notifyFinished(InsetsAnimationControlRunner runner, boolean shown) {
        setRequestedVisibleTypes(shown ? runner.getTypes() : 0, runner.getTypes());
        cancelAnimation(runner, false);
        if (DEBUG) {
            Log.d(TAG, "notifyFinished. shown: " + shown);
        }
        if (runner.getAnimationType() == 3) {
            return;
        }
        ImeTracker.Token statsToken = runner.getStatsToken();
        if (runner.getAnimationType() == 2) {
            ImeTracker.forLogging().onUserFinished(statsToken, shown);
        } else if (shown) {
            ImeTracker.forLogging().onProgress(statsToken, 41);
            ImeTracker.forLogging().onShown(statsToken);
        } else {
            ImeTracker.forLogging().onProgress(statsToken, 42);
            ImeTracker.forLogging().onHidden(statsToken);
        }
        reportRequestedVisibleTypes();
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... params) {
        this.mHost.applySurfaceParams(params);
    }

    void notifyControlRevoked(InsetsSourceConsumer consumer) {
        int type = consumer.getType();
        for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
            InsetsAnimationControlRunner control = this.mRunningAnimations.get(i).runner;
            control.notifyControlRevoked(type);
            if (control.getControllingTypes() == 0) {
                cancelAnimation(control, true);
            }
        }
        int i2 = WindowInsets.Type.ime();
        if (type == i2) {
            abortPendingImeControlRequest();
        }
        if (consumer.getType() != WindowInsets.Type.ime()) {
            this.mSourceConsumers.remove(consumer.getId());
        }
    }

    private void cancelAnimation(InsetsAnimationControlRunner control, boolean invokeCallback) {
        if (invokeCallback) {
            ImeTracker.forLogging().onCancelled(control.getStatsToken(), 40);
            control.cancel();
        } else {
            ImeTracker.forLogging().onProgress(control.getStatsToken(), 40);
        }
        Log.i(
                TAG,
                TextUtils.formatSimple(
                        "cancelAnimation: types=%s, animType=%d, host=%s, from=%s",
                        WindowInsets.Type.toString(control.getTypes()),
                        Integer.valueOf(control.getAnimationType()),
                        this.mHost.getRootViewTitle(),
                        Debug.getCallers(3)));
        int removedTypes = 0;
        int i = this.mRunningAnimations.size() - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            RunningAnimation runningAnimation = this.mRunningAnimations.get(i);
            if (runningAnimation.runner != control) {
                i--;
            } else {
                this.mRunningAnimations.remove(i);
                removedTypes = control.getTypes();
                if (invokeCallback) {
                    dispatchAnimationEnd(runningAnimation.runner.getAnimation());
                } else if (Flags.refactorInsetsController()
                        && removedTypes == WindowInsets.Type.ime()
                        && control.getAnimationType() == 1
                        && this.mHost != null) {
                    reportRequestedVisibleTypes();
                    this.mHost
                            .getInputMethodManager()
                            .removeImeSurface(this.mHost.getWindowToken());
                }
            }
        }
        if (this.mRunningAnimations.isEmpty()) {
            this.mHost.notifyAnimationRunningStateChanged(false);
        }
        onAnimationStateChanged(removedTypes, false);
    }

    void onAnimationStateChanged(int types, boolean running) {
        boolean insetsChanged = false;
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if ((consumer.getType() & types) != 0) {
                insetsChanged |= consumer.onAnimationStateChanged(running);
            }
        }
        if (insetsChanged) {
            notifyVisibilityChanged();
        }
    }

    private void applyLocalVisibilityOverride() {
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            consumer.applyLocalVisibilityOverride();
        }
    }

    int getCancelledForNewAnimationTypes() {
        return this.mCancelledForNewAnimationTypes;
    }

    public InsetsSourceConsumer getSourceConsumer(int id, int type) {
        InsetsSourceConsumer consumer;
        InsetsSourceConsumer consumer2 = this.mSourceConsumers.get(id);
        if (consumer2 != null) {
            return consumer2;
        }
        if (type == WindowInsets.Type.ime() && this.mImeSourceConsumer != null) {
            this.mSourceConsumers.remove(this.mImeSourceConsumer.getId());
            consumer = this.mImeSourceConsumer;
            consumer.setId(id);
        } else {
            consumer =
                    this.mConsumerCreator.apply(this, Integer.valueOf(id), Integer.valueOf(type));
        }
        this.mSourceConsumers.put(id, consumer);
        return consumer;
    }

    public InsetsSourceConsumer getImeSourceConsumer() {
        return this.mImeSourceConsumer;
    }

    void notifyVisibilityChanged() {
        this.mHost.notifyInsetsChanged();
    }

    public void updateCompatSysUiVisibility() {
        if (this.mCompatSysUiVisibilityStaled) {
            this.mCompatSysUiVisibilityStaled = false;
            this.mHost.updateCompatSysUiVisibility(
                    this.mVisibleTypes,
                    this.mRequestedVisibleTypes,
                    this.mControllableTypes | (~this.mExistingTypes));
        }
    }

    public void onWindowFocusGained(boolean hasViewFocused) {
        this.mImeSourceConsumer.onWindowFocusGained(hasViewFocused);
    }

    public void onWindowFocusLost() {
        this.mImeSourceConsumer.onWindowFocusLost();
    }

    public int getAnimationType(int type) {
        for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
            InsetsAnimationControlRunner control = this.mRunningAnimations.get(i).runner;
            if (control.controlsType(type)) {
                return this.mRunningAnimations.get(i).type;
            }
        }
        return -1;
    }

    public void setRequestedVisibleTypes(int visibleTypes, int mask) {
        int requestedVisibleTypes = (this.mRequestedVisibleTypes & (~mask)) | (visibleTypes & mask);
        if (this.mRequestedVisibleTypes != requestedVisibleTypes) {
            this.mRequestedVisibleTypes = requestedVisibleTypes;
            Log.i(
                    TAG,
                    "setRequestedVisibleTypes: visible="
                            + ((requestedVisibleTypes & mask) != 0)
                            + ", mask="
                            + WindowInsets.Type.toString(mask)
                            + ", host="
                            + getHost().getRootViewTitle()
                            + ", from="
                            + Debug.getCallers(10));
        }
    }

    public int computeUserAnimatingTypes() {
        int animatingTypes = 0;
        for (int i = 0; i < this.mRunningAnimations.size(); i++) {
            if (this.mRunningAnimations.get(i).runner.getAnimationType() == 2) {
                animatingTypes |= this.mRunningAnimations.get(i).runner.getTypes();
            }
        }
        return animatingTypes;
    }

    private int computeAnimatingTypes() {
        int animatingTypes = 0;
        for (int i = 0; i < this.mRunningAnimations.size(); i++) {
            animatingTypes |= this.mRunningAnimations.get(i).runner.getTypes();
        }
        return animatingTypes;
    }

    private void reportRequestedVisibleTypes() {
        int typesToReport;
        if (Flags.refactorInsetsController()) {
            typesToReport =
                    this.mRequestedVisibleTypes
                            | (computeAnimatingTypes() & WindowInsets.Type.ime());
        } else {
            typesToReport = this.mRequestedVisibleTypes;
        }
        if (typesToReport != this.mReportedRequestedVisibleTypes) {
            int diff = this.mReportedRequestedVisibleTypes ^ typesToReport;
            if (WindowInsets.Type.hasCompatSystemBars(diff)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mReportedRequestedVisibleTypes = this.mRequestedVisibleTypes;
            this.mHost.updateRequestedVisibleTypes(this.mReportedRequestedVisibleTypes);
        }
        updateCompatSysUiVisibility();
    }

    public void applyAnimation(
            int types, boolean show, boolean fromIme, ImeTracker.Token statsToken) {
        InsetsSourceControl imeControl;
        boolean skipAnim = false;
        if ((WindowInsets.Type.ime() & types) != 0
                && (imeControl = this.mImeSourceConsumer.getControl()) != null) {
            skipAnim =
                    imeControl.getAndClearSkipAnimationOnce()
                            && show
                            && this.mImeSourceConsumer.hasViewFocusWhenWindowFocusGain();
        }
        applyAnimation(types, show, fromIme, skipAnim, statsToken);
    }

    public void applyAnimation(int i, boolean z, boolean z2, boolean z3, ImeTracker.Token token) {
        boolean z4;
        boolean z5;
        if (i == 0) {
            if (DEBUG) {
                Log.d(TAG, "applyAnimation, nothing to animate. Stopping here");
            }
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            if (!Flags.refactorInsetsController() && !z2) {
                Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                return;
            }
            return;
        }
        boolean hasAnimationCallbacks = this.mHost.hasAnimationCallbacks();
        if ((i & WindowInsets.Type.ime()) == 0) {
            z4 = false;
        } else {
            z4 = this.mHost.getInputMethodManager().isFullscreenMode();
        }
        int size = this.mSourceConsumers.size() - 1;
        while (true) {
            if (size < 0) {
                z5 = z3;
                break;
            }
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0 && valueAt.getControl() != null) {
                InsetsSource orCreateSource =
                        this.mState.getOrCreateSource(valueAt.getId(), valueAt.getType());
                if (InsetsSource.getInsetSide(valueAt.getControl().getInsetsHint())
                        != orCreateSource.getSideHint()) {
                    Log.d(
                            TAG,
                            "applyAnimation, skip insets animation, because hint of source is not"
                                + " equal to hint of control, source="
                                    + orCreateSource
                                    + ", control="
                                    + valueAt.getControl());
                    z5 = true;
                    break;
                }
            }
            size--;
        }
        InternalAnimationControlListener internalAnimationControlListener =
                new InternalAnimationControlListener(
                        z,
                        hasAnimationCallbacks,
                        i,
                        this.mHost.getSystemBarsBehavior(),
                        z5 || this.mAnimationsDisabled,
                        this.mHost.dipToPx(-80),
                        this.mLoggingListener,
                        this.mJankContext,
                        z4);
        controlAnimationUnchecked(
                i,
                null,
                internalAnimationControlListener,
                null,
                z2,
                internalAnimationControlListener.getDurationMs(),
                internalAnimationControlListener.getInsetsInterpolator(),
                !z ? 1 : 0,
                !z ? 1 : 0,
                !hasAnimationCallbacks,
                token);
    }

    public void cancelExistingAnimations() {
        cancelExistingControllers(WindowInsets.Type.all());
    }

    void dump(String prefix, PrintWriter pw) {
        String innerPrefix = prefix + "    ";
        pw.println(prefix + "InsetsController:");
        this.mState.dump(innerPrefix, pw);
        pw.println(
                innerPrefix
                        + "mIsPredictiveBackImeHideAnimInProgress="
                        + this.mIsPredictiveBackImeHideAnimInProgress);
    }

    void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        this.mState.dumpDebug(proto, 1146756268033L);
        for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
            InsetsAnimationControlRunner runner = this.mRunningAnimations.get(i).runner;
            runner.dumpDebug(proto, 2246267895810L);
        }
        proto.end(token);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public <T extends InsetsAnimationControlRunner & InternalInsetsAnimationController>
            void startAnimation(
                    final T runner,
                    final WindowInsetsAnimationControlListener listener,
                    final int types,
                    final WindowInsetsAnimation animation,
                    final WindowInsetsAnimation.Bounds bounds) {
        this.mHost.dispatchWindowInsetsAnimationPrepare(animation);
        this.mHost.addOnPreDrawRunnable(
                new Runnable() { // from class:
                                 // android.view.InsetsController$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsController.this.lambda$startAnimation$8(
                                runner, types, animation, bounds, listener);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$8(
            InsetsAnimationControlRunner runner,
            int types,
            WindowInsetsAnimation animation,
            WindowInsetsAnimation.Bounds bounds,
            WindowInsetsAnimationControlListener listener) {
        if (((WindowInsetsAnimationController) runner).isCancelled()) {
            return;
        }
        Trace.asyncTraceBegin(8L, "InsetsAnimation: " + WindowInsets.Type.toString(types), types);
        for (int i = this.mRunningAnimations.size() - 1; i >= 0; i--) {
            RunningAnimation runningAnimation = this.mRunningAnimations.get(i);
            if (runningAnimation.runner == runner) {
                runningAnimation.startDispatched = true;
            }
        }
        Trace.asyncTraceEnd(8L, "IC.pendingAnim", 0);
        this.mHost.dispatchWindowInsetsAnimationStart(animation, bounds);
        this.mStartingAnimation = true;
        if (runner.getAnimationType() == 2) {
            ImeTracker.forLogging().onDispatched(runner.getStatsToken());
        }
        ((InternalInsetsAnimationController) runner).setReadyDispatched(true);
        listener.onReady((WindowInsetsAnimationController) runner, types);
        this.mStartingAnimation = false;
    }

    public void dispatchAnimationEnd(WindowInsetsAnimation animation) {
        Trace.asyncTraceEnd(
                8L,
                "InsetsAnimation: " + WindowInsets.Type.toString(animation.getTypeMask()),
                animation.getTypeMask());
        this.mHost.dispatchWindowInsetsAnimationEnd(animation);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void scheduleApplyChangeInsets(InsetsAnimationControlRunner runner) {
        if (this.mStartingAnimation || runner.getAnimationType() == 2) {
            this.mAnimCallback.run();
            this.mAnimCallbackScheduled = false;
        } else if (!this.mAnimCallbackScheduled) {
            this.mHost.postInsetsAnimationCallback(this.mAnimCallback);
            this.mAnimCallbackScheduled = true;
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int appearance, int mask) {
        this.mAppearanceControlled |= mask;
        this.mHost.setSystemBarsAppearance(appearance, mask);
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearanceFromResource(int appearance, int mask) {
        this.mAppearanceFromResource =
                (this.mAppearanceFromResource & (~mask)) | (appearance & mask);
        this.mHost.setSystemBarsAppearance(appearance, (~this.mAppearanceControlled) & mask);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        return (this.mHost.getSystemBarsAppearance() & this.mAppearanceControlled)
                | (this.mAppearanceFromResource & (~this.mAppearanceControlled));
    }

    public int getAppearanceControlled() {
        return this.mAppearanceControlled;
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int height) {
        Rect newFrame =
                new Rect(
                        this.mFrame.left,
                        this.mFrame.bottom - height,
                        this.mFrame.right,
                        this.mFrame.bottom);
        InsetsSource source = this.mState.peekSource(InsetsSource.ID_IME_CAPTION_BAR);
        if (this.mImeCaptionBarInsetsHeight != height
                || (source != null && !newFrame.equals(source.getFrame()))) {
            this.mImeCaptionBarInsetsHeight = height;
            if (this.mImeCaptionBarInsetsHeight != 0) {
                this.mState
                        .getOrCreateSource(
                                InsetsSource.ID_IME_CAPTION_BAR, WindowInsets.Type.captionBar())
                        .setFrame(newFrame);
                getSourceConsumer(InsetsSource.ID_IME_CAPTION_BAR, WindowInsets.Type.captionBar())
                        .setControl(
                                new InsetsSourceControl(
                                        InsetsSource.ID_IME_CAPTION_BAR,
                                        WindowInsets.Type.captionBar(),
                                        null,
                                        false,
                                        new Point(),
                                        Insets.NONE),
                                new int[1],
                                new int[1]);
            } else {
                this.mState.removeSource(InsetsSource.ID_IME_CAPTION_BAR);
                InsetsSourceConsumer sourceConsumer =
                        this.mSourceConsumers.get(InsetsSource.ID_IME_CAPTION_BAR);
                if (sourceConsumer != null) {
                    sourceConsumer.setControl(null, new int[1], new int[1]);
                }
            }
            this.mHost.notifyInsetsChanged();
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int behavior) {
        this.mBehaviorControlled = true;
        this.mHost.setSystemBarsBehavior(behavior);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (!this.mBehaviorControlled) {
            return 1;
        }
        return this.mHost.getSystemBarsBehavior();
    }

    public boolean isBehaviorControlled() {
        return this.mBehaviorControlled;
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean disable) {
        this.mAnimationsDisabled = disable;
    }

    private int calculateControllableTypes() {
        int result = 0;
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            InsetsSource source = this.mState.peekSource(consumer.getId());
            if (consumer.getControl() != null && source != null) {
                result |= consumer.getType();
            }
        }
        return (~this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame)) & result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int invokeControllableInsetsChangedListeners() {
        this.mHandler.removeCallbacks(this.mInvokeControllableInsetsChangedListeners);
        this.mLastStartedAnimTypes = 0;
        int types = calculateControllableTypes();
        int size = this.mControllableInsetsChangedListeners.size();
        for (int i = 0; i < size; i++) {
            this.mControllableInsetsChangedListeners
                    .get(i)
                    .onControllableInsetsChanged(this, types);
        }
        int i2 = this.mLastStartedAnimTypes;
        return i2;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(
            WindowInsetsController.OnControllableInsetsChangedListener listener) {
        Objects.requireNonNull(listener);
        this.mControllableInsetsChangedListeners.add(listener);
        listener.onControllableInsetsChanged(this, calculateControllableTypes());
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(
            WindowInsetsController.OnControllableInsetsChangedListener listener) {
        Objects.requireNonNull(listener);
        this.mControllableInsetsChangedListeners.remove(listener);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void releaseSurfaceControlFromRt(SurfaceControl sc) {
        this.mHost.releaseSurfaceControlFromRt(sc);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void reportPerceptible(int types, boolean perceptible) {
        int size = this.mSourceConsumers.size();
        for (int i = 0; i < size; i++) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if ((consumer.getType() & types) != 0) {
                consumer.onPerceptible(perceptible);
            }
        }
    }

    public Host getHost() {
        return this.mHost;
    }
}
