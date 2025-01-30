package android.view;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.CancellationSignal;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Process;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.text.TextUtils;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsController;
import android.view.InsetsState;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
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
    static final boolean DEBUG = false;
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
    private int mCaptionInsetsHeight;
    private boolean mCompatSysUiVisibilityStaled;
    private final TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer> mConsumerCreator;
    private final ArrayList<WindowInsetsController.OnControllableInsetsChangedListener> mControllableInsetsChangedListeners;
    private int mControllableTypes;
    private int mDisabledUserAnimationInsetsTypes;
    private int mExistingTypes;
    private final Rect mFrame;
    private final Handler mHandler;
    private final Host mHost;
    private final InsetsSourceConsumer mImeSourceConsumer;
    private final Runnable mInvokeControllableInsetsChangedListeners;
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
    private static final Interpolator SYSTEM_BARS_INSETS_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator SYSTEM_BARS_ALPHA_INTERPOLATOR = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
    private static final Interpolator SYSTEM_BARS_DIM_INTERPOLATOR = new Interpolator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return InsetsController.lambda$static$0(f);
        }
    };
    private static final Interpolator SYNC_IME_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    private static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    private static final String PROP_ENABLE_SEP_IME_ANIMATION = "persist.sys.ime.enable_sep_ime_animation";
    private static final boolean ENABLE_SEP_IME_ANIMATION = SystemProperties.getBoolean(PROP_ENABLE_SEP_IME_ANIMATION, true);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface LayoutInsetsDuringAnimation {
    }

    public interface Host {
        void addOnPreDrawRunnable(Runnable runnable);

        void applySurfaceParams(boolean z, SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        int dipToPx(int i);

        void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation);

        void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation);

        WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list);

        WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds);

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

        default void updateCompatSysUiVisibility(int visibleTypes, int requestedVisibleTypes, int controllableTypes) {
        }

        default boolean isSystemBarsAppearanceControlled() {
            return false;
        }

        default boolean isSystemBarsBehaviorControlled() {
            return false;
        }

        default Context getRootViewContext() {
            return null;
        }

        default CompatibilityInfo.Translator getTranslator() {
            return null;
        }

        default InsetsSourceControl[] getScaledInsetHintControls(InsetsSourceControl[] controls) {
            return controls;
        }
    }

    static {
        ENABLE_SEP_FLAGSHIP_IME_ANIMATION = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_CAMERA_CONFIG_STRIDE_OCR_VERSION").equals("V2") && !SystemProperties.get("ro.product.name", "").startsWith("m44x");
        SEP_IME_SHOW_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        SEP_IME_HIDE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        RESIZE_INTERPOLATOR = new LinearInterpolator();
        ID_CAPTION_BAR = InsetsSource.createId(null, 0, WindowInsets.Type.captionBar());
        sEvaluator = new TypeEvaluator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda1
            @Override // android.animation.TypeEvaluator
            public final Object evaluate(float f, Object obj, Object obj2) {
                Insets m14of;
                Insets insets = (Insets) obj;
                Insets insets2 = (Insets) obj2;
                m14of = Insets.m14of((int) (insets.left + ((insets2.left - insets.left) * f)), (int) (insets.top + ((insets2.top - insets.top) * f)), (int) (insets.right + ((insets2.right - insets.right) * f)), (int) (insets.bottom + ((insets2.bottom - insets.bottom) * f)));
                return m14of;
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

    public static class InternalAnimationControlListener implements WindowInsetsAnimationControlListener {
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
        private final ThreadLocal<AnimationHandler> mSfAnimationHandlerThreadLocal;
        private final boolean mShow;

        public InternalAnimationControlListener(boolean show, boolean hasAnimationCallbacks, int requestedTypes, int behavior, boolean disable, int floatingImeBottomInset, WindowInsetsAnimationControlListener loggingListener, ImeTracker.InputMethodJankContext jankContext) {
            this.mFullscreenMode = false;
            this.mSfAnimationHandlerThreadLocal = new ThreadLocal<AnimationHandler>() { // from class: android.view.InsetsController.InternalAnimationControlListener.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.lang.ThreadLocal
                public AnimationHandler initialValue() {
                    AnimationHandler handler = new AnimationHandler();
                    handler.setProvider(new SfVsyncFrameCallbackProvider());
                    return handler;
                }
            };
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

        public InternalAnimationControlListener(boolean show, boolean hasAnimationCallbacks, int requestedTypes, int behavior, boolean disable, int floatingImeBottomInset, WindowInsetsAnimationControlListener loggingListener, ImeTracker.InputMethodJankContext jankContext, boolean fullscreenMode) {
            this.mFullscreenMode = false;
            this.mSfAnimationHandlerThreadLocal = new ThreadLocal<AnimationHandler>() { // from class: android.view.InsetsController.InternalAnimationControlListener.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.lang.ThreadLocal
                public AnimationHandler initialValue() {
                    AnimationHandler handler = new AnimationHandler();
                    handler.setProvider(new SfVsyncFrameCallbackProvider());
                    return handler;
                }
            };
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
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onReady(controller, types);
            }
            if (this.mDisable) {
                onAnimationFinish();
                return;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator = ofFloat;
            ofFloat.setDuration(this.mDurationMs);
            if (ValueAnimator.getDurationScale() < 0.5f && (WindowInsets.Type.ime() & types) == 0) {
                this.mAnimator.overrideDurationScale(0.5f);
            }
            this.mAnimator.setInterpolator(new LinearInterpolator());
            Insets hiddenInsets = controller.getHiddenStateInsets();
            if (controller.hasZeroInsetsIme()) {
                insets = Insets.m14of(hiddenInsets.left, hiddenInsets.top, hiddenInsets.right, this.mFloatingImeBottomInset);
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
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    InsetsController.InternalAnimationControlListener.this.lambda$onReady$0(insetsInterpolator, controller, start, end, alphaInterpolator, valueAnimator);
                }
            });
            this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.view.InsetsController.InternalAnimationControlListener.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onRequestAnimation(InternalAnimationControlListener.this.mInputMethodJankContext, !InternalAnimationControlListener.this.mShow ? 1 : 0, !InternalAnimationControlListener.this.mHasAnimationCallbacks);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onCancelAnimation();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    InternalAnimationControlListener.this.onAnimationFinish();
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onFinishAnimation();
                }
            });
            if (!this.mHasAnimationCallbacks) {
                this.mAnimator.setAnimationHandler(this.mSfAnimationHandlerThreadLocal.get());
            }
            this.mAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$0(Interpolator insetsInterpolator, WindowInsetsAnimationController controller, Insets start, Insets end, Interpolator alphaInterpolator, ValueAnimator animation) {
            float alphaFraction;
            float rawFraction = animation.getAnimatedFraction();
            if (this.mShow) {
                alphaFraction = rawFraction;
            } else {
                alphaFraction = 1.0f - rawFraction;
            }
            float insetsFraction = insetsInterpolator.getInterpolation(rawFraction);
            controller.setInsetsAndAlpha((Insets) InsetsController.sEvaluator.evaluate(insetsFraction, start, end), alphaInterpolator.getInterpolation(alphaFraction), rawFraction);
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onFinished(WindowInsetsAnimationController controller) {
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onFinished(controller);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onCancelled(WindowInsetsAnimationController controller) {
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onCancelled(controller);
            }
        }

        protected Interpolator getInsetsInterpolator() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) != 0) {
                if (InsetsController.ENABLE_SEP_IME_ANIMATION && InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION) {
                    return this.mShow ? InsetsController.SEP_IME_SHOW_INTERPOLATOR : InsetsController.SEP_IME_HIDE_INTERPOLATOR;
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
            return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    float lambda$getInsetsInterpolator$1;
                    lambda$getInsetsInterpolator$1 = InsetsController.InternalAnimationControlListener.this.lambda$getInsetsInterpolator$1(f);
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
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda2
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$2(f);
                        }
                    };
                }
                if (this.mHasAnimationCallbacks) {
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda3
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$3(f);
                        }
                    };
                }
                if (this.mShow) {
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda4
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
                return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda5
                    @Override // android.animation.TimeInterpolator
                    public final float getInterpolation(float f) {
                        return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$5(f);
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
        }

        public long getDurationMs() {
            return this.mDurationMs;
        }

        private long calculateDurationMs() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) == 0) {
                return (this.mBehavior == 2 || CoreRune.FW_CUSTOM_BASIC_INSET_ANIM) ? this.mShow ? 275L : 340L : this.mShow ? 500L : 1500L;
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
            return this.mShow ? InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 350 : 280 : InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 300 : 280;
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

        PendingControlRequest(int types, WindowInsetsAnimationControlListener listener, long durationMs, Interpolator interpolator, int animationType, int layoutInsetsDuringAnimation, CancellationSignal cancellationSignal, boolean useInsetsAnimationThread) {
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
        this(host, new TriFunction() { // from class: android.view.InsetsController$$ExternalSyntheticLambda7
            @Override // com.android.internal.util.function.TriFunction
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return InsetsController.lambda$new$2((InsetsController) obj, (Integer) obj2, (Integer) obj3);
            }
        }, host.getHandler());
    }

    static /* synthetic */ InsetsSourceConsumer lambda$new$2(InsetsController controller, Integer id, Integer type) {
        if (type.intValue() == WindowInsets.Type.ime()) {
            return new ImeInsetsSourceConsumer(id.intValue(), controller.mState, new InsetsController$$ExternalSyntheticLambda8(), controller);
        }
        return new InsetsSourceConsumer(id.intValue(), type.intValue(), controller.mState, new InsetsController$$ExternalSyntheticLambda8(), controller);
    }

    public InsetsController(Host host, TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer> consumerCreator, Handler handler) {
        this.mJankContext = new ImeTracker.InputMethodJankContext() { // from class: android.view.InsetsController.1
            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public Context getDisplayContext() {
                if (InsetsController.this.mHost != null) {
                    return InsetsController.this.mHost.getRootViewContext();
                }
                return null;
            }

            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public SurfaceControl getTargetSurfaceControl() {
                InsetsSourceControl imeSourceControl = InsetsController.this.getImeSourceConsumer().getControl();
                if (imeSourceControl != null) {
                    return imeSourceControl.getLeash();
                }
                return null;
            }

            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public String getHostPackageName() {
                if (InsetsController.this.mHost != null) {
                    return InsetsController.this.mHost.getRootViewContext().getPackageName();
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
        this.mPendingControlTimeout = new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda9
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
        this.mInvokeControllableInsetsChangedListeners = new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                InsetsController.this.invokeControllableInsetsChangedListeners();
            }
        };
        this.mRemoveGoneSources = new InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsController.2
            private final IntArray mPendingRemoveIndexes = new IntArray();

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdNotFoundInState2(int index1, InsetsSource source1) {
                if (!ViewRootImpl.CAPTION_ON_SHELL && source1.getType() == WindowInsets.Type.captionBar()) {
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
        this.mStartResizingAnimationIfNeeded = new InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsController.3
            private InsetsState mToState;
            private int mTypes;

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onStart(InsetsState state1, InsetsState state2) {
                this.mTypes = 0;
                this.mToState = null;
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(InsetsSource source1, InsetsSource source2) {
                InsetsSourceConsumer consumer;
                int type = source1.getType();
                if (type != WindowInsets.Type.navigationBars() && (consumer = (InsetsSourceConsumer) InsetsController.this.mSourceConsumers.get(source1.getId())) != null && consumer.getControl() != null && (WindowInsets.Type.systemBars() & type) != 0 && source1.isVisible() && source2.isVisible() && !source1.getFrame().equals(source2.getFrame())) {
                    if (!Rect.intersects(InsetsController.this.mFrame, source1.getFrame()) && !Rect.intersects(InsetsController.this.mFrame, source2.getFrame())) {
                        return;
                    }
                    this.mTypes |= type;
                    if (this.mToState == null) {
                        this.mToState = new InsetsState();
                    }
                    this.mToState.addSource(new InsetsSource(source2));
                }
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onFinish(InsetsState state1, InsetsState state2) {
                int i = this.mTypes;
                if (i == 0) {
                    return;
                }
                InsetsController.this.cancelExistingControllers(i);
                InsetsAnimationControlRunner runner = new InsetsResizeAnimationRunner(InsetsController.this.mFrame, state1, this.mToState, InsetsController.RESIZE_INTERPOLATOR, 300L, this.mTypes, InsetsController.this);
                InsetsController.this.mRunningAnimations.add(new RunningAnimation(runner, runner.getAnimationType()));
                Log.m98i(InsetsController.TAG, "startResizingAnimationIfNeeded: types=" + WindowInsets.Type.toString(this.mTypes) + " host=" + InsetsController.this.mHost.getRootViewTitle());
            }
        };
        this.mHost = host;
        this.mConsumerCreator = consumerCreator;
        this.mHandler = handler;
        this.mAnimCallback = new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda11
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
        WindowInsets insets = state.calculateInsets(rect, insetsState, isRound, i3, i4, i, this.mWindowType, this.mLastActivityType, null);
        this.mHost.dispatchWindowInsetsAnimationProgress(insets, Collections.unmodifiableList(runningAnimations));
        for (int i5 = finishedAnimations.size() - 1; i5 >= 0; i5--) {
            dispatchAnimationEnd(finishedAnimations.get(i5));
        }
    }

    public void onFrameChanged(Rect frame) {
        if (this.mFrame.equals(frame)) {
            return;
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
        boolean stateChanged;
        if (!ViewRootImpl.CAPTION_ON_SHELL) {
            stateChanged = !this.mState.equals(state, true, false) || captionInsetsUnchanged();
        } else {
            stateChanged = !this.mState.equals(state, false, false);
        }
        if (!stateChanged && this.mLastDispatchedState.equals(state)) {
            return false;
        }
        Log.m98i(TAG, "onStateChanged: host=" + this.mHost.getRootViewTitle() + ", from=" + Debug.getCaller() + ", state=" + state);
        this.mLastDispatchedState.set(state, true);
        InsetsState lastState = new InsetsState(this.mState, true);
        updateState(state);
        applyLocalVisibilityOverride();
        updateCompatSysUiVisibility();
        if (!this.mState.equals(lastState, false, true)) {
            this.mHost.notifyInsetsChanged();
            if (lastState.getDisplayFrame().equals(this.mState.getDisplayFrame())) {
                InsetsState.traverse(lastState, this.mState, this.mStartResizingAnimationIfNeeded);
            }
        }
        return true;
    }

    public boolean hasNavigationBarFrameMismatch() {
        Rect navBarClientFrame = new Rect();
        Rect navBarServerFrame = new Rect();
        int size = this.mState.sourceSize();
        for (int i = 0; i < size; i++) {
            InsetsSource source = this.mState.sourceAt(i);
            if (source.getType() == 2) {
                navBarClientFrame.set(source.getFrame());
            }
        }
        int size2 = this.mLastDispatchedState.sourceSize();
        for (int i2 = 0; i2 < size2; i2++) {
            InsetsSource source2 = this.mLastDispatchedState.sourceAt(i2);
            if (source2.getType() == 2) {
                navBarServerFrame.set(source2.getFrame());
            }
        }
        return !navBarClientFrame.equals(navBarServerFrame);
    }

    private void updateState(InsetsState newState) {
        boolean displayFrameChanged = !this.mState.getDisplayFrame().equals(newState.getDisplayFrame());
        this.mState.set(newState, 0);
        int existingTypes = 0;
        int visibleTypes = 0;
        int disabledUserAnimationTypes = 0;
        final int[] cancelledUserAnimationTypes = {0};
        int size = newState.sourceSize();
        for (int i = 0; i < size; i++) {
            InsetsSource source = newState.sourceAt(i);
            int type = source.getType();
            int animationType = getAnimationType(type);
            if (!source.isUserControllable()) {
                disabledUserAnimationTypes |= type;
                if (animationType == 2) {
                    animationType = -1;
                    cancelledUserAnimationTypes[0] = cancelledUserAnimationTypes[0] | type;
                }
            }
            InsetsSourceConsumer consumer = this.mSourceConsumers.get(source.getId());
            if (consumer != null) {
                consumer.updateSource(source, animationType, displayFrameChanged);
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
        int i3 = this.mVisibleTypes;
        if (i3 != visibleTypes2) {
            if (WindowInsets.Type.hasCompatSystemBars(i3 ^ visibleTypes2)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mVisibleTypes = visibleTypes2;
        }
        int i4 = this.mExistingTypes;
        if (i4 != existingTypes) {
            if (WindowInsets.Type.hasCompatSystemBars(i4 ^ existingTypes)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mExistingTypes = existingTypes;
        }
        InsetsState.traverse(this.mState, newState, this.mRemoveGoneSources);
        updateDisabledUserAnimationTypes(disabledUserAnimationTypes);
        if (cancelledUserAnimationTypes[0] != 0) {
            this.mHandler.post(new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda3
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

    private void updateDisabledUserAnimationTypes(int disabledUserAnimationTypes) {
        int diff = this.mDisabledUserAnimationInsetsTypes ^ disabledUserAnimationTypes;
        if (diff != 0) {
            int i = this.mSourceConsumers.size() - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
                if (consumer.getControl() == null || (consumer.getType() & diff) == 0) {
                    i--;
                } else {
                    this.mHandler.removeCallbacks(this.mInvokeControllableInsetsChangedListeners);
                    this.mHandler.post(this.mInvokeControllableInsetsChangedListeners);
                    break;
                }
            }
            this.mDisabledUserAnimationInsetsTypes = disabledUserAnimationTypes;
        }
    }

    private boolean captionInsetsUnchanged() {
        if (ViewRootImpl.CAPTION_ON_SHELL) {
            return false;
        }
        InsetsSource source = this.mState.peekSource(ID_CAPTION_BAR);
        if (source == null && this.mCaptionInsetsHeight == 0) {
            return false;
        }
        return source == null || this.mCaptionInsetsHeight != source.getFrame().height();
    }

    public WindowInsets calculateInsets(boolean isScreenRound, int windowType, int activityType, int legacySoftInputMode, int legacyWindowFlags, int legacySystemUiFlags) {
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
        WindowInsets calculateInsets = insetsState.calculateInsets(rect, null, isScreenRound, legacySoftInputMode, legacyWindowFlags, i, windowType, activityType, null);
        this.mLastInsets = calculateInsets;
        return calculateInsets;
    }

    public Insets calculateVisibleInsets(int windowType, int activityType, int softInputMode, int windowFlags) {
        return this.mState.calculateVisibleInsets(this.mFrame, windowType, activityType, softInputMode, windowFlags);
    }

    public void onControlsChanged(InsetsSourceControl[] activeControls) {
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            activeControls = this.mHost.getScaledInsetHintControls(activeControls);
        }
        this.mSystemBarControlledByPolicy = false;
        if (activeControls != null) {
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
            InsetsSourceControl control = this.mTmpControlArray.get(consumer.getId());
            if (control != null) {
                controllableTypes |= control.getType();
                consumedControlCount++;
            }
            consumer.setControl(control, showTypes, hideTypes);
        }
        if (consumedControlCount != this.mTmpControlArray.size()) {
            for (int i2 = this.mTmpControlArray.size() - 1; i2 >= 0; i2--) {
                InsetsSourceControl control2 = this.mTmpControlArray.valueAt(i2);
                getSourceConsumer(control2.getId(), control2.getType()).setControl(control2, showTypes, hideTypes);
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
        if (showTypes[0] != 0) {
            applyAnimation(showTypes[0], true, false, null);
        }
        if (hideTypes[0] != 0) {
            applyAnimation(hideTypes[0], false, false, null);
        }
        int i4 = this.mControllableTypes;
        if (i4 != controllableTypes) {
            if (WindowInsets.Type.hasCompatSystemBars(i4 ^ controllableTypes)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mControllableTypes = controllableTypes;
        }
        reportRequestedVisibleTypes();
    }

    @Override // android.view.WindowInsetsController
    public void show(int types) {
        ImeTracker.Token statsToken = null;
        if ((WindowInsets.Type.ime() & types) != 0) {
            statsToken = ImeTracker.forLogging().onRequestShow(null, Process.myUid(), 1, 26);
        }
        show(types, false, statsToken);
    }

    public void show(int types, boolean fromIme, ImeTracker.Token statsToken) {
        if ((WindowInsets.Type.ime() & types) != 0) {
            Log.m94d(TAG, "show(ime(), fromIme=" + fromIme + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (fromIme) {
            ImeTracing.getInstance().triggerClientDump("InsetsController#show", this.mHost.getInputMethodManager(), null);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            Trace.asyncTraceBegin(8L, "IC.showRequestFromIme", 0);
        } else {
            Trace.asyncTraceBegin(8L, "IC.showRequestFromApi", 0);
            Trace.asyncTraceBegin(8L, "IC.showRequestFromApiToImeReady", 0);
        }
        if (fromIme && this.mPendingImeControlRequest != null) {
            if ((WindowInsets.Type.ime() & types) != 0) {
                ImeTracker.forLatency().onShown(statsToken, new InsetsController$$ExternalSyntheticLambda2());
            }
            handlePendingControlRequest(statsToken);
            return;
        }
        int typesReady = 0;
        boolean imeVisible = this.mState.isSourceOrDefaultVisible(this.mImeSourceConsumer.getId(), WindowInsets.Type.ime());
        int type = 1;
        while (true) {
            if (type > 512) {
                break;
            }
            if ((types & type) != 0) {
                int animationType = getAnimationType(type);
                boolean requestedVisible = (this.mRequestedVisibleTypes & type) != 0;
                boolean isIme = type == WindowInsets.Type.ime();
                boolean alreadyVisible = requestedVisible && (!isIme || imeVisible) && animationType == -1;
                boolean alreadyAnimatingShow = animationType == 0;
                if (alreadyVisible || alreadyAnimatingShow) {
                    if (isIme) {
                        ImeTracker.forLogging().onCancelled(statsToken, 32);
                    }
                } else if (fromIme && animationType == 2) {
                    if (isIme) {
                        ImeTracker.forLogging().onFailed(statsToken, 32);
                    }
                } else {
                    if (isIme) {
                        ImeTracker.forLogging().onProgress(statsToken, 32);
                    }
                    typesReady |= type;
                }
            }
            type <<= 1;
        }
        if (fromIme && (WindowInsets.Type.ime() & typesReady) != 0) {
            ImeTracker.forLatency().onShown(statsToken, new InsetsController$$ExternalSyntheticLambda2());
        }
        applyAnimation(typesReady, true, fromIme, statsToken);
    }

    private void handlePendingControlRequest(ImeTracker.Token statsToken) {
        PendingControlRequest pendingRequest = this.mPendingImeControlRequest;
        this.mPendingImeControlRequest = null;
        this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        controlAnimationUnchecked(pendingRequest.types, pendingRequest.cancellationSignal, pendingRequest.listener, null, true, pendingRequest.durationMs, pendingRequest.interpolator, pendingRequest.animationType, pendingRequest.layoutInsetsDuringAnimation, pendingRequest.useInsetsAnimationThread, statsToken);
    }

    @Override // android.view.WindowInsetsController
    public void hide(int types) {
        ImeTracker.Token statsToken = null;
        if ((WindowInsets.Type.ime() & types) != 0) {
            statsToken = ImeTracker.forLogging().onRequestHide(null, Process.myUid(), 2, 28);
        }
        hide(types, false, statsToken);
    }

    public void hide(int types, boolean fromIme, ImeTracker.Token statsToken) {
        if (!fromIme) {
            Trace.asyncTraceBegin(8L, "IC.hideRequestFromApi", 0);
        } else {
            ImeTracing.getInstance().triggerClientDump("InsetsController#hide", this.mHost.getInputMethodManager(), null);
            Trace.asyncTraceBegin(8L, "IC.hideRequestFromIme", 0);
        }
        int typesReady = 0;
        boolean hasImeRequestedHidden = false;
        boolean hadPendingImeControlRequest = this.mPendingImeControlRequest != null;
        int type = 1;
        while (type <= 512) {
            if ((types & type) != 0) {
                int animationType = getAnimationType(type);
                boolean requestedVisible = (this.mRequestedVisibleTypes & type) != 0;
                boolean isImeAnimation = type == WindowInsets.Type.ime();
                PendingControlRequest pendingControlRequest = this.mPendingImeControlRequest;
                if (pendingControlRequest != null && !requestedVisible) {
                    pendingControlRequest.types &= ~type;
                    if (this.mPendingImeControlRequest.types == 0) {
                        abortPendingImeControlRequest();
                    }
                }
                if (isImeAnimation && !requestedVisible && animationType == -1) {
                    hasImeRequestedHidden = true;
                    if (hadPendingImeControlRequest || getImeSourceConsumer().isRequestedVisibleAwaitingControl()) {
                        getImeSourceConsumer().requestHide(fromIme, statsToken);
                    }
                }
                if ((!requestedVisible && animationType == -1) || animationType == 1) {
                    if (isImeAnimation) {
                        ImeTracker.forLogging().onCancelled(statsToken, 32);
                    }
                } else {
                    if (isImeAnimation) {
                        ImeTracker.forLogging().onProgress(statsToken, 32);
                    }
                    typesReady |= type;
                }
            }
            type <<= 1;
        }
        if (hasImeRequestedHidden && this.mPendingImeControlRequest != null) {
            handlePendingControlRequest(statsToken);
            getImeSourceConsumer().removeSurface();
        }
        applyAnimation(typesReady, false, fromIme, statsToken);
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(int types, long durationMillis, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener listener) {
        controlWindowInsetsAnimation(types, cancellationSignal, listener, false, durationMillis, interpolator, 2);
    }

    private void controlWindowInsetsAnimation(int types, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener listener, boolean fromIme, long durationMs, Interpolator interpolator, int animationType) {
        if ((this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame) & types) != 0) {
            listener.onCancelled(null);
            return;
        }
        if (fromIme) {
            ImeTracing.getInstance().triggerClientDump("InsetsController#controlWindowInsetsAnimation", this.mHost.getInputMethodManager(), null);
        }
        controlAnimationUnchecked(types, cancellationSignal, listener, this.mFrame, fromIme, durationMs, interpolator, animationType, getLayoutInsetsDuringAnimationMode(types), false, null);
    }

    private void controlAnimationUnchecked(int types, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener listener, Rect frame, boolean fromIme, long durationMs, Interpolator interpolator, int animationType, int layoutInsetsDuringAnimation, boolean useInsetsAnimationThread, ImeTracker.Token statsToken) {
        boolean visible = layoutInsetsDuringAnimation == 0;
        setRequestedVisibleTypes(visible ? types : 0, types);
        controlAnimationUncheckedInner(types, cancellationSignal, listener, frame, fromIme, durationMs, interpolator, animationType, layoutInsetsDuringAnimation, useInsetsAnimationThread, statsToken);
        reportRequestedVisibleTypes();
    }

    private void controlAnimationUncheckedInner(int types, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener listener, Rect frame, boolean fromIme, long durationMs, Interpolator interpolator, int animationType, int layoutInsetsDuringAnimation, boolean useInsetsAnimationThread, ImeTracker.Token statsToken) {
        int types2;
        int types3;
        String str;
        final InsetsController insetsController;
        InsetsAnimationControlRunner insetsAnimationControlImpl;
        int i;
        ImeTracker.Token token;
        int i2;
        long j;
        ImeTracker.forLogging().onProgress(statsToken, 33);
        boolean z = true;
        if ((types & this.mTypesBeingCancelled) != 0) {
            if (animationType != 0 && animationType != 1) {
                z = false;
            }
            boolean monitoredAnimation = z;
            if (monitoredAnimation && (types & WindowInsets.Type.ime()) != 0) {
                if (animationType == 0) {
                    ImeTracker.forLatency().onShowCancelled(statsToken, 40, new InsetsController$$ExternalSyntheticLambda2());
                } else {
                    ImeTracker.forLatency().onHideCancelled(statsToken, 40, new InsetsController$$ExternalSyntheticLambda2());
                }
            }
            throw new IllegalStateException("Cannot start a new insets animation of " + WindowInsets.Type.toString(types) + " while an existing " + WindowInsets.Type.toString(this.mTypesBeingCancelled) + " is being cancelled.");
        }
        if (animationType != 2) {
            types2 = types;
        } else {
            int disabledTypes = types & this.mDisabledUserAnimationInsetsTypes;
            Log.m94d(TAG, "user animation disabled types: " + disabledTypes);
            int types4 = types & (~this.mDisabledUserAnimationInsetsTypes);
            if ((WindowInsets.Type.ime() & disabledTypes) != 0) {
                ImeTracker.forLogging().onFailed(statsToken, 34);
                if (fromIme && !this.mState.isSourceOrDefaultVisible(this.mImeSourceConsumer.getId(), WindowInsets.Type.ime())) {
                    setRequestedVisibleTypes(0, WindowInsets.Type.ime());
                    if (this.mImeSourceConsumer.onAnimationStateChanged(false)) {
                        notifyVisibilityChanged();
                    }
                }
            }
            types2 = types4;
        }
        if (types2 == 0) {
            listener.onCancelled(null);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            return;
        }
        ImeTracker.forLogging().onProgress(statsToken, 34);
        this.mLastStartedAnimTypes |= types2;
        SparseArray<InsetsSourceControl> controls = new SparseArray<>();
        Pair<Integer, Boolean> typesReadyPair = collectSourceControls(fromIme, types2, controls, animationType, statsToken);
        int typesReady = typesReadyPair.first.intValue();
        boolean imeReady = typesReadyPair.second.booleanValue();
        if (imeReady) {
            int types5 = types2;
            cancelExistingControllers(types5);
            if (typesReady == 0) {
                listener.onCancelled(null);
                Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
                if (!fromIme) {
                    Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                    return;
                }
                return;
            }
            if (useInsetsAnimationThread) {
                InsetsState insetsState = this.mState;
                CompatibilityInfo.Translator translator = this.mHost.getTranslator();
                Handler handler = this.mHost.getHandler();
                str = TAG;
                types3 = types5;
                insetsController = this;
                insetsAnimationControlImpl = new InsetsAnimationThreadControlRunner(controls, frame, insetsState, listener, typesReady, this, durationMs, interpolator, animationType, layoutInsetsDuringAnimation, translator, handler, statsToken);
            } else {
                types3 = types5;
                str = TAG;
                insetsController = this;
                insetsAnimationControlImpl = new InsetsAnimationControlImpl(controls, frame, insetsController.mState, listener, typesReady, this, durationMs, interpolator, animationType, layoutInsetsDuringAnimation, insetsController.mHost.getTranslator(), statsToken);
            }
            final InsetsAnimationControlRunner runner = insetsAnimationControlImpl;
            if ((WindowInsets.Type.ime() & typesReady) == 0) {
                i = animationType;
                token = statsToken;
            } else {
                ImeTracing.getInstance().triggerClientDump("InsetsAnimationControlImpl", insetsController.mHost.getInputMethodManager(), null);
                i = animationType;
                if (i != 1) {
                    token = statsToken;
                } else {
                    token = statsToken;
                    ImeTracker.forLatency().onHidden(token, new InsetsController$$ExternalSyntheticLambda2());
                }
            }
            ImeTracker.forLogging().onProgress(token, 39);
            insetsController.mRunningAnimations.add(new RunningAnimation(runner, i));
            Log.m98i(str, "controlAnimationUncheckedInner: Added types=" + WindowInsets.Type.toString(typesReady) + ", animType=" + i + ", host=" + insetsController.mHost.getRootViewTitle() + ", from=" + Debug.getCallers(3));
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda5
                    @Override // android.os.CancellationSignal.OnCancelListener
                    public final void onCancel() {
                        InsetsController.this.lambda$controlAnimationUncheckedInner$6(runner);
                    }
                });
                i2 = 0;
                j = 8;
            } else {
                i2 = 0;
                j = 8;
                Trace.asyncTraceBegin(8L, "IC.pendingAnim", 0);
            }
            insetsController.onAnimationStateChanged(types3, true);
            if (fromIme) {
                switch (i) {
                    case 0:
                        Trace.asyncTraceEnd(j, "IC.showRequestFromIme", i2);
                        return;
                    case 1:
                        Trace.asyncTraceEnd(j, "IC.hideRequestFromIme", i2);
                        return;
                    default:
                        return;
                }
            }
            if (i == 1) {
                Trace.asyncTraceEnd(j, "IC.hideRequestFromApi", i2);
                return;
            }
            return;
        }
        abortPendingImeControlRequest();
        int types6 = types2;
        final PendingControlRequest request = new PendingControlRequest(types2, listener, durationMs, interpolator, animationType, layoutInsetsDuringAnimation, cancellationSignal, useInsetsAnimationThread);
        this.mPendingImeControlRequest = request;
        this.mHandler.postDelayed(this.mPendingControlTimeout, 2000L);
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda4
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    InsetsController.this.lambda$controlAnimationUncheckedInner$5(request);
                }
            });
        }
        setRequestedVisibleTypes(this.mReportedRequestedVisibleTypes, types6);
        Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
        if (!fromIme) {
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$5(PendingControlRequest request) {
        if (this.mPendingImeControlRequest == request) {
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$6(InsetsAnimationControlRunner runner) {
        cancelAnimation(runner, true);
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(WindowInsetsAnimationControlListener listener) {
        this.mLoggingListener = listener;
    }

    private Pair<Integer, Boolean> collectSourceControls(boolean fromIme, int types, SparseArray<InsetsSourceControl> controls, int animationType, ImeTracker.Token statsToken) {
        ImeTracker.forLogging().onProgress(statsToken, 35);
        int typesReady = 0;
        boolean imeReady = true;
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; i--) {
            InsetsSourceConsumer consumer = this.mSourceConsumers.valueAt(i);
            if ((consumer.getType() & types) != 0) {
                boolean show = animationType == 0 || animationType == 2;
                boolean canRun = true;
                if (show) {
                    switch (consumer.requestShow(fromIme, statsToken)) {
                        case 1:
                            imeReady = false;
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
                        Log.m102w(TAG, String.format("collectSourceControls can't continue show for type: %s fromIme: %b", WindowInsets.Type.toString(consumer.getType()), Boolean.valueOf(fromIme)));
                    }
                } else {
                    InsetsSourceControl control = consumer.getControl();
                    if (control != null && control.getLeash() != null) {
                        controls.put(control.getId(), new InsetsSourceControl(control));
                        typesReady |= consumer.getType();
                    }
                }
            }
        }
        return new Pair<>(Integer.valueOf(typesReady), Boolean.valueOf(imeReady));
    }

    private int getLayoutInsetsDuringAnimationMode(int types) {
        if ((this.mRequestedVisibleTypes & types) != types) {
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
        PendingControlRequest pendingControlRequest = this.mPendingImeControlRequest;
        if (pendingControlRequest != null) {
            pendingControlRequest.listener.onCancelled(null);
            this.mPendingImeControlRequest = null;
            this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        }
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void notifyFinished(InsetsAnimationControlRunner runner, boolean shown) {
        if (runner.isCancelRequested()) {
            Log.m94d(TAG, "Ignore notifyFinished beacuse the animation has already been canceled.");
            return;
        }
        setRequestedVisibleTypes(shown ? runner.getTypes() : 0, runner.getTypes());
        cancelAnimation(runner, false);
        if (runner.getAnimationType() == 3) {
            return;
        }
        ImeTracker.Token statsToken = runner.getStatsToken();
        if (shown) {
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

    void applySurfaceParams(boolean ignoreVisibility, SyncRtSurfaceTransactionApplier.SurfaceParams... params) {
        this.mHost.applySurfaceParams(ignoreVisibility, params);
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
        Log.m98i(TAG, TextUtils.formatSimple("cancelAnimation: types=%s, animType=%d, host=%s, from=%s", WindowInsets.Type.toString(control.getTypes()), Integer.valueOf(control.getAnimationType()), this.mHost.getRootViewTitle(), Debug.getCallers(3)));
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
                }
            }
        }
        onAnimationStateChanged(removedTypes, false);
    }

    private void onAnimationStateChanged(int types, boolean running) {
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

    public InsetsSourceConsumer getSourceConsumer(int id, int type) {
        InsetsSourceConsumer consumer;
        InsetsSourceConsumer insetsSourceConsumer;
        InsetsSourceConsumer consumer2 = this.mSourceConsumers.get(id);
        if (consumer2 != null) {
            return consumer2;
        }
        if (type == WindowInsets.Type.ime() && (insetsSourceConsumer = this.mImeSourceConsumer) != null) {
            this.mSourceConsumers.remove(insetsSourceConsumer.getId());
            consumer = this.mImeSourceConsumer;
            consumer.setId(id);
        } else {
            consumer = this.mConsumerCreator.apply(this, Integer.valueOf(id), Integer.valueOf(type));
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
            this.mHost.updateCompatSysUiVisibility(this.mVisibleTypes, this.mRequestedVisibleTypes, this.mControllableTypes | (~this.mExistingTypes));
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
        int i = this.mRequestedVisibleTypes;
        int requestedVisibleTypes = ((~mask) & i) | (visibleTypes & mask);
        if (i != requestedVisibleTypes) {
            this.mRequestedVisibleTypes = requestedVisibleTypes;
            Log.m98i(TAG, "setRequestedVisibleTypes: visible=" + ((requestedVisibleTypes & mask) != 0) + ", mask=" + WindowInsets.Type.toString(mask) + ", host=" + getHost().getRootViewTitle() + ", from=" + Debug.getCallers(10));
        }
    }

    private void reportRequestedVisibleTypes() {
        int i = this.mReportedRequestedVisibleTypes;
        int i2 = this.mRequestedVisibleTypes;
        if (i != i2) {
            int diff = i ^ i2;
            if (WindowInsets.Type.hasCompatSystemBars(diff)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            int i3 = this.mRequestedVisibleTypes;
            this.mReportedRequestedVisibleTypes = i3;
            this.mHost.updateRequestedVisibleTypes(i3);
        }
        updateCompatSysUiVisibility();
    }

    public void applyAnimation(int types, boolean show, boolean fromIme, ImeTracker.Token statsToken) {
        InsetsSourceControl imeControl;
        boolean skipAnim = false;
        if ((WindowInsets.Type.ime() & types) != 0 && (imeControl = this.mImeSourceConsumer.getControl()) != null) {
            skipAnim = imeControl.getAndClearSkipAnimationOnce() && show && this.mImeSourceConsumer.hasViewFocusWhenWindowFocusGain();
        }
        applyAnimation(types, show, fromIme, skipAnim, statsToken);
    }

    public void applyAnimation(int i, boolean z, boolean z2, boolean z3, ImeTracker.Token token) {
        boolean z4;
        if (i == 0) {
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            if (!z2) {
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
        InternalAnimationControlListener internalAnimationControlListener = new InternalAnimationControlListener(z, hasAnimationCallbacks, i, this.mHost.getSystemBarsBehavior(), z3 || this.mAnimationsDisabled, this.mHost.dipToPx(-80), this.mLoggingListener, this.mJankContext, z4);
        controlAnimationUnchecked(i, null, internalAnimationControlListener, null, z2, internalAnimationControlListener.getDurationMs(), internalAnimationControlListener.getInsetsInterpolator(), !z ? 1 : 0, !z ? 1 : 0, !hasAnimationCallbacks, token);
    }

    public void cancelExistingAnimations() {
        cancelExistingControllers(WindowInsets.Type.all());
    }

    void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.println("InsetsController:");
        this.mState.dump(prefix + "  ", pw);
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
    public <T extends InsetsAnimationControlRunner & InternalInsetsAnimationController> void startAnimation(final T runner, final WindowInsetsAnimationControlListener listener, final int types, final WindowInsetsAnimation animation, final WindowInsetsAnimation.Bounds bounds) {
        this.mHost.dispatchWindowInsetsAnimationPrepare(animation);
        this.mHost.addOnPreDrawRunnable(new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                InsetsController.this.lambda$startAnimation$7(runner, types, animation, bounds, listener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$7(InsetsAnimationControlRunner runner, int types, WindowInsetsAnimation animation, WindowInsetsAnimation.Bounds bounds, WindowInsetsAnimationControlListener listener) {
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
        ((InternalInsetsAnimationController) runner).setReadyDispatched(true);
        listener.onReady((WindowInsetsAnimationController) runner, types);
        this.mStartingAnimation = false;
    }

    public void dispatchAnimationEnd(WindowInsetsAnimation animation) {
        Trace.asyncTraceEnd(8L, "InsetsAnimation: " + WindowInsets.Type.toString(animation.getTypeMask()), animation.getTypeMask());
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
        this.mHost.setSystemBarsAppearance(appearance, mask);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        if (!this.mHost.isSystemBarsAppearanceControlled()) {
            return 0;
        }
        return this.mHost.getSystemBarsAppearance();
    }

    @Override // android.view.WindowInsetsController
    public void setCaptionInsetsHeight(int height) {
        if (!ViewRootImpl.CAPTION_ON_SHELL && this.mCaptionInsetsHeight != height) {
            this.mCaptionInsetsHeight = height;
            if (height != 0) {
                this.mState.getOrCreateSource(ID_CAPTION_BAR, WindowInsets.Type.captionBar()).setFrame(this.mFrame.left, this.mFrame.top, this.mFrame.right, this.mFrame.top + this.mCaptionInsetsHeight);
            } else {
                this.mState.removeSource(ID_CAPTION_BAR);
            }
            this.mHost.notifyInsetsChanged();
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int behavior) {
        this.mHost.setSystemBarsBehavior(behavior);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (!this.mHost.isSystemBarsBehaviorControlled()) {
            return 0;
        }
        return this.mHost.getSystemBarsBehavior();
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
            if (consumer.getControl() != null && source != null && source.isUserControllable()) {
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
            this.mControllableInsetsChangedListeners.get(i).onControllableInsetsChanged(this, types);
        }
        int i2 = this.mLastStartedAnimTypes;
        return i2;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener listener) {
        Objects.requireNonNull(listener);
        this.mControllableInsetsChangedListeners.add(listener);
        listener.onControllableInsetsChanged(this, calculateControllableTypes());
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener listener) {
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

    Host getHost() {
        return this.mHost;
    }
}
