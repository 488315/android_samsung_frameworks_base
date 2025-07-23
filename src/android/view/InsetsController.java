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
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import android.view.InsetsAnimationControlRunner;
import android.view.InsetsController;
import android.view.InsetsState;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.protolog.ProtoLog;
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
public class InsetsController implements WindowInsetsController, InsetsAnimationControlCallbacks, InsetsAnimationControlRunner.SurfaceParamsApplier {
    private static final int ANIMATION_DELAY_DIM_MS = 500;
    private static final int ANIMATION_DURATION_FADE_IN_MS = 500;
    private static final int ANIMATION_DURATION_FADE_OUT_MS = 1500;
    private static final int ANIMATION_DURATION_MOVE_IN_MS = 275;
    private static final int ANIMATION_DURATION_MOVE_OUT_MS = 340;
    public static final int ANIMATION_DURATION_RESIZE = 300;
    static final int ANIMATION_DURATION_SYNC_IME_MS = 285;
    static final int ANIMATION_DURATION_UNSYNC_IME_MS = 200;
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
    private int mAnimatingTypes;
    private boolean mAnimationsDisabled;
    private int mAppearanceControlled;
    private int mAppearanceFromResource;
    private boolean mBehaviorControlled;
    private int mCancelledForNewAnimationTypes;
    private int mCaptionInsetsHeight;
    private boolean mCompatSysUiVisibilityStaled;
    private final TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer> mConsumerCreator;
    private final ArrayList<WindowInsetsController.OnControllableInsetsChangedListener> mControllableInsetsChangedListeners;
    private int mControllableTypes;
    private int mExistingTypes;
    private final Rect mFrame;
    private final Handler mHandler;
    private final Host mHost;
    private int mImeCaptionBarInsetsHeight;
    private final InsetsSourceConsumer mImeSourceConsumer;
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
    public static final int CONTROLLABLE_TYPES = (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars()) | WindowInsets.Type.ime();
    private static final Interpolator SYSTEM_BARS_INSETS_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator SYSTEM_BARS_ALPHA_INTERPOLATOR = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
    private static final Interpolator SYSTEM_BARS_DIM_INTERPOLATOR = new Interpolator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda3
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return InsetsController.lambda$static$0(f);
        }
    };
    static final Interpolator SYNC_IME_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    private static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    private static final String PROP_ENABLE_SEP_IME_ANIMATION = "persist.sys.ime.enable_sep_ime_animation";
    private static final boolean ENABLE_SEP_IME_ANIMATION = SystemProperties.getBoolean(PROP_ENABLE_SEP_IME_ANIMATION, true);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public interface Host {
        void addOnPreDrawRunnable(Runnable runnable);

        default void applyInsetsHintSandboxingIfNeeded(InsetsSourceControl[] insetsSourceControlArr) {
        }

        void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        int dipToPx(int i);

        void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation);

        void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation);

        WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list);

        WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds);

        Handler getHandler();

        InputMethodManager getInputMethodManager();

        default Context getRootViewContext() {
            return null;
        }

        String getRootViewTitle();

        int getSystemBarsAppearance();

        int getSystemBarsBehavior();

        default CompatibilityInfo.Translator getTranslator() {
            return null;
        }

        IBinder getWindowToken();

        boolean hasAnimationCallbacks();

        default boolean isHandlingPointerEvent() {
            return false;
        }

        void notifyInsetsChanged();

        void postInsetsAnimationCallback(Runnable runnable);

        void releaseSurfaceControlFromRt(SurfaceControl surfaceControl);

        void setSystemBarsAppearance(int i, int i2);

        void setSystemBarsBehavior(int i);

        default boolean shouldIgnoreInsetsAnimation() {
            return false;
        }

        default void updateAnimatingTypes(int i, ImeTracker.Token token) {
        }

        default void updateCompatSysUiVisibility(int i, int i2, int i3) {
        }

        void updateRequestedVisibleTypes(int i, ImeTracker.Token token);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface LayoutInsetsDuringAnimation {
    }

    static {
        ENABLE_SEP_FLAGSHIP_IME_ANIMATION = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_CAMERA_CONFIG_STRIDE_OCR_VERSION").equals("V2") && !SystemProperties.get("ro.product.name", "").startsWith("m44x");
        SEP_IME_SHOW_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        SEP_IME_HIDE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        RESIZE_INTERPOLATOR = new LinearInterpolator();
        ID_CAPTION_BAR = InsetsSource.createId(null, 0, WindowInsets.Type.captionBar());
        DEBUG = CoreRune.FW_INSETS_LOG_DEBUG;
        sEvaluator = new TypeEvaluator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda4
            @Override // android.animation.TypeEvaluator
            public final Object evaluate(float f, Object obj, Object obj2) {
                Insets of;
                Insets insets = (Insets) obj;
                Insets insets2 = (Insets) obj2;
                of = Insets.of((int) (insets.left + ((insets2.left - insets.left) * f)), (int) (insets.top + ((insets2.top - insets.top) * f)), (int) (insets.right + ((insets2.right - insets.right) * f)), (int) (insets.bottom + (f * (insets2.bottom - insets.bottom))));
                return of;
            }
        };
    }

    static /* synthetic */ float lambda$static$0(float f) {
        float f2 = 1.0f - f;
        if (f2 <= 0.33333334f) {
            return 1.0f;
        }
        return 1.0f - SYSTEM_BARS_ALPHA_INTERPOLATOR.getInterpolation((f2 - 0.33333334f) / 0.6666666f);
    }

    public static class InternalAnimationControlListener implements WindowInsetsAnimationControlListener, InsetsAnimationSpec {
        private ValueAnimator mAnimator;
        private final int mBehavior;
        private WindowInsetsAnimationController mController;
        private final boolean mDisable;
        private final int mFloatingImeBottomInset;
        private boolean mFullscreenMode;
        private final boolean mHasAnimationCallbacks;
        private final ImeTracker.InputMethodJankContext mInputMethodJankContext;
        private final WindowInsetsAnimationControlListener mLoggingListener;
        private final int mRequestedTypes;
        private final boolean mShow;

        static /* synthetic */ float lambda$getAlphaInterpolator$3(float f) {
            return 1.0f;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$4(float f) {
            return 1.0f;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$6(float f) {
            return 1.0f;
        }

        public InternalAnimationControlListener(boolean z, boolean z2, int i, int i2, boolean z3, int i3, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, ImeTracker.InputMethodJankContext inputMethodJankContext) {
            this.mFullscreenMode = false;
            this.mShow = z;
            this.mHasAnimationCallbacks = z2;
            this.mRequestedTypes = i;
            this.mBehavior = i2;
            this.mDisable = z3;
            this.mFloatingImeBottomInset = i3;
            this.mLoggingListener = windowInsetsAnimationControlListener;
            this.mInputMethodJankContext = inputMethodJankContext;
        }

        public InternalAnimationControlListener(boolean z, boolean z2, int i, int i2, boolean z3, int i3, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, ImeTracker.InputMethodJankContext inputMethodJankContext, boolean z4) {
            this.mFullscreenMode = z4;
            this.mShow = z;
            this.mHasAnimationCallbacks = z2;
            this.mRequestedTypes = i;
            this.mBehavior = i2;
            this.mDisable = z3;
            this.mFloatingImeBottomInset = i3;
            this.mLoggingListener = windowInsetsAnimationControlListener;
            this.mInputMethodJankContext = inputMethodJankContext;
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onReady(final WindowInsetsAnimationController windowInsetsAnimationController, int i) {
            this.mController = windowInsetsAnimationController;
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "default animation onReady types: " + i);
            }
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onReady(windowInsetsAnimationController, i);
            }
            if (this.mDisable) {
                onAnimationFinish();
                return;
            }
            boolean hasZeroInsetsIme = windowInsetsAnimationController.hasZeroInsetsIme();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator = ofFloat;
            ofFloat.setDuration(windowInsetsAnimationController.getDurationMs());
            if (ValueAnimator.getDurationScale() < 0.5f && (i & WindowInsets.Type.ime()) == 0) {
                this.mAnimator.overrideDurationScale(0.5f);
            }
            this.mAnimator.setInterpolator(new LinearInterpolator());
            Insets hiddenStateInsets = windowInsetsAnimationController.getHiddenStateInsets();
            if (hasZeroInsetsIme) {
                hiddenStateInsets = Insets.of(hiddenStateInsets.left, hiddenStateInsets.top, hiddenStateInsets.right, this.mFloatingImeBottomInset);
            }
            final Insets shownStateInsets = this.mShow ? hiddenStateInsets : windowInsetsAnimationController.getShownStateInsets();
            if (this.mShow) {
                hiddenStateInsets = windowInsetsAnimationController.getShownStateInsets();
            }
            final Insets insets = hiddenStateInsets;
            final Interpolator insetsInterpolator = windowInsetsAnimationController.getInsetsInterpolator();
            final Interpolator alphaInterpolator = getAlphaInterpolator();
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    InsetsController.InternalAnimationControlListener.this.lambda$onReady$0(insetsInterpolator, windowInsetsAnimationController, shownStateInsets, insets, alphaInterpolator, valueAnimator);
                }
            });
            this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.view.InsetsController.InternalAnimationControlListener.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onRequestAnimation(InternalAnimationControlListener.this.mInputMethodJankContext, InternalAnimationControlListener.this.getAnimationType(), !InternalAnimationControlListener.this.mHasAnimationCallbacks);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onCancelAnimation(InternalAnimationControlListener.this.getAnimationType());
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    InternalAnimationControlListener.this.onAnimationFinish();
                    if (InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    ImeTracker.forJank().onFinishAnimation(InternalAnimationControlListener.this.getAnimationType());
                }
            });
            this.mAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$0(Interpolator interpolator, WindowInsetsAnimationController windowInsetsAnimationController, Insets insets, Insets insets2, Interpolator interpolator2, ValueAnimator valueAnimator) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            float f = this.mShow ? animatedFraction : 1.0f - animatedFraction;
            float interpolation = interpolator.getInterpolation(animatedFraction);
            windowInsetsAnimationController.setInsetsAndAlpha((Insets) InsetsController.sEvaluator.evaluate(interpolation, insets, insets2), interpolator2.getInterpolation(f), animatedFraction);
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "Default animation setInsetsAndAlpha fraction: " + interpolation);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "InternalAnimationControlListener onFinished types:" + WindowInsets.Type.toString(this.mRequestedTypes));
            }
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onFinished(windowInsetsAnimationController);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "InternalAnimationControlListener onCancelled types:" + this.mRequestedTypes);
            }
            WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
            if (windowInsetsAnimationControlListener != null) {
                windowInsetsAnimationControlListener.onCancelled(windowInsetsAnimationController);
            }
        }

        @Override // android.view.InsetsAnimationSpec
        public Interpolator getInsetsInterpolator(boolean z) {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) != 0) {
                if (this.mFullscreenMode) {
                    if (this.mShow) {
                        return InsetsController.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
                    }
                    return InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
                }
                if (InsetsController.ENABLE_SEP_IME_ANIMATION && InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION) {
                    return this.mShow ? InsetsController.SEP_IME_SHOW_INTERPOLATOR : InsetsController.SEP_IME_HIDE_INTERPOLATOR;
                }
                if (this.mHasAnimationCallbacks && !z) {
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
            return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda6
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    float lambda$getInsetsInterpolator$1;
                    lambda$getInsetsInterpolator$1 = InsetsController.InternalAnimationControlListener.this.lambda$getInsetsInterpolator$1(f);
                    return lambda$getInsetsInterpolator$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float lambda$getInsetsInterpolator$1(float f) {
            return this.mShow ? 1.0f : 0.0f;
        }

        Interpolator getAlphaInterpolator() {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) != 0) {
                if (this.mFullscreenMode) {
                    if (this.mShow) {
                        return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda1
                            @Override // android.animation.TimeInterpolator
                            public final float getInterpolation(float f) {
                                float min;
                                min = Math.min(1.0f, f * 2.0f);
                                return min;
                            }
                        };
                    }
                    return InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
                }
                if (InsetsController.ENABLE_SEP_IME_ANIMATION) {
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda2
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$3(f);
                        }
                    };
                }
                if (this.mHasAnimationCallbacks && !this.mController.hasZeroInsetsIme()) {
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda3
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$4(f);
                        }
                    };
                }
                if (this.mShow) {
                    return new Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda4
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            float min;
                            min = Math.min(1.0f, f * 2.0f);
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
                        return InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$6(f);
                    }
                };
            }
            if (this.mShow) {
                return InsetsController.SYSTEM_BARS_ALPHA_INTERPOLATOR;
            }
            return InsetsController.SYSTEM_BARS_DIM_INTERPOLATOR;
        }

        protected void onAnimationFinish() {
            this.mController.finish(this.mShow);
            if (InsetsController.DEBUG) {
                Log.d(InsetsController.TAG, "onAnimationFinish showOnFinish: " + this.mShow);
            }
        }

        @Override // android.view.InsetsAnimationSpec
        public long getDurationMs(boolean z) {
            if ((this.mRequestedTypes & WindowInsets.Type.ime()) == 0) {
                return (this.mBehavior == 2 || CoreRune.FW_INSET_ANIM) ? this.mShow ? 275L : 340L : this.mShow ? 500L : 1500L;
            }
            if (this.mFullscreenMode) {
                return 200L;
            }
            if (InsetsController.ENABLE_SEP_IME_ANIMATION) {
                return getSepAnimationDurationIme();
            }
            return (!this.mHasAnimationCallbacks || z) ? 200L : 285L;
        }

        private int getSepAnimationDurationIme() {
            return this.mShow ? InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 350 : 280 : InsetsController.ENABLE_SEP_FLAGSHIP_IME_ANIMATION ? 300 : 280;
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

        RunningAnimation(InsetsAnimationControlRunner insetsAnimationControlRunner, int i) {
            this.runner = insetsAnimationControlRunner;
            this.type = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingControlRequest {
        final int animationType;
        final CancellationSignal cancellationSignal;
        final int layoutInsetsDuringAnimation;
        final WindowInsetsAnimationControlListener listener;
        final InsetsAnimationSpec mInsetsAnimationSpec;
        int types;
        final boolean useInsetsAnimationThread;

        PendingControlRequest(int i, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, InsetsAnimationSpec insetsAnimationSpec, int i2, int i3, CancellationSignal cancellationSignal, boolean z) {
            this.types = i;
            this.listener = windowInsetsAnimationControlListener;
            this.mInsetsAnimationSpec = insetsAnimationSpec;
            this.animationType = i2;
            this.layoutInsetsDuringAnimation = i3;
            this.cancellationSignal = cancellationSignal;
            this.useInsetsAnimationThread = z;
        }
    }

    public InsetsController(Host host) {
        this(host, new TriFunction() { // from class: android.view.InsetsController$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.function.TriFunction
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return InsetsController.lambda$new$2((InsetsController) obj, (Integer) obj2, (Integer) obj3);
            }
        }, host.getHandler());
    }

    static /* synthetic */ InsetsSourceConsumer lambda$new$2(InsetsController insetsController, Integer num, Integer num2) {
        if (!Flags.refactorInsetsController() && num2.intValue() == WindowInsets.Type.ime()) {
            return new ImeInsetsSourceConsumer(num.intValue(), insetsController.mState, insetsController);
        }
        return new InsetsSourceConsumer(num.intValue(), num2.intValue(), insetsController.mState, insetsController);
    }

    public InsetsController(Host host, TriFunction<InsetsController, Integer, Integer, InsetsSourceConsumer> triFunction, Handler handler) {
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
                InsetsSourceControl control = InsetsController.this.getImeSourceConsumer().getControl();
                if (control != null) {
                    return control.getLeash();
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
        this.mImeCaptionBarInsetsHeight = 0;
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
        this.mAnimatingTypes = 0;
        this.mRemoveGoneSources = new InsetsState.OnTraverseCallbacks(this) { // from class: android.view.InsetsController.2
            private final IntArray mPendingRemoveIndexes = new IntArray();

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdNotFoundInState2(int i, InsetsSource insetsSource) {
                if (insetsSource.getId() == InsetsSource.ID_IME_CAPTION_BAR) {
                    return;
                }
                this.mPendingRemoveIndexes.add(i);
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onFinish(InsetsState insetsState, InsetsState insetsState2) {
                for (int size = this.mPendingRemoveIndexes.size() - 1; size >= 0; size--) {
                    insetsState.removeSourceAt(this.mPendingRemoveIndexes.get(size));
                }
                this.mPendingRemoveIndexes.clear();
            }
        };
        this.mStartResizingAnimationIfNeeded = new InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsController.3
            private InsetsState mFromState;
            private InsetsState mToState;
            private int mTypes;

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onStart(InsetsState insetsState, InsetsState insetsState2) {
                this.mTypes = 0;
                this.mFromState = null;
                this.mToState = null;
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(InsetsSource insetsSource, InsetsSource insetsSource2) {
                Rect frame = insetsSource.getFrame();
                Rect frame2 = insetsSource2.getFrame();
                if ((!(insetsSource.hasFlags(8) && insetsSource2.hasFlags(8)) && (!CoreRune.FW_MINIMIZED_IME_INSET_ANIM || (insetsSource.getType() & WindowInsets.Type.ime()) == 0 || insetsSource.getMinimizedInsetHint().equals(insetsSource2.getMinimizedInsetHint()))) || !insetsSource.isVisible() || !insetsSource2.isVisible() || frame.equals(frame2) || frame.isEmpty() || frame2.isEmpty()) {
                    return;
                }
                if (Rect.intersects(InsetsController.this.mFrame, insetsSource.getFrame()) || Rect.intersects(InsetsController.this.mFrame, insetsSource2.getFrame())) {
                    this.mTypes |= insetsSource.getType();
                    if (this.mFromState == null) {
                        this.mFromState = new InsetsState();
                    }
                    if (this.mToState == null) {
                        this.mToState = new InsetsState();
                    }
                    this.mFromState.addSource(new InsetsSource(insetsSource));
                    this.mToState.addSource(new InsetsSource(insetsSource2));
                }
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onFinish(InsetsState insetsState, InsetsState insetsState2) {
                int i = this.mTypes;
                if (i == 0) {
                    return;
                }
                InsetsController.this.cancelExistingControllers(i);
                Rect rect = InsetsController.this.mFrame;
                InsetsState insetsState3 = this.mFromState;
                InsetsState insetsState4 = this.mToState;
                Interpolator interpolator = InsetsController.RESIZE_INTERPOLATOR;
                int i2 = this.mTypes;
                InsetsController insetsController = InsetsController.this;
                InsetsResizeAnimationRunner insetsResizeAnimationRunner = new InsetsResizeAnimationRunner(rect, insetsState3, insetsState4, interpolator, 300L, i2, insetsController, insetsController);
                if (InsetsController.this.mRunningAnimations.isEmpty()) {
                    InsetsController.this.mHost.updateAnimatingTypes(insetsResizeAnimationRunner.getTypes(), insetsResizeAnimationRunner.getAnimationType() == 1 ? insetsResizeAnimationRunner.getStatsToken() : null);
                }
                InsetsController.this.mRunningAnimations.add(new RunningAnimation(insetsResizeAnimationRunner, insetsResizeAnimationRunner.getAnimationType()));
                InsetsController.this.mAnimatingTypes |= insetsResizeAnimationRunner.getTypes();
                Log.i(InsetsController.TAG, "startResizingAnimationIfNeeded: types=" + WindowInsets.Type.toString(this.mTypes) + " host=" + InsetsController.this.mHost.getRootViewTitle());
            }
        };
        this.mHost = host;
        this.mConsumerCreator = triFunction;
        this.mHandler = handler;
        this.mAnimCallback = new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda10
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
        ViewRootImpl viewRoot;
        this.mAnimCallbackScheduled = false;
        if (this.mRunningAnimations.isEmpty()) {
            return;
        }
        ArrayList<WindowInsetsAnimation> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        InsetsState insetsState = new InsetsState(this.mState, true);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            if (DEBUG) {
                Log.d(TAG, "Running animation type: " + runningAnimation.type);
            }
            InsetsAnimationControlRunner insetsAnimationControlRunner = runningAnimation.runner;
            if (insetsAnimationControlRunner instanceof WindowInsetsAnimationController) {
                if (runningAnimation.startDispatched) {
                    arrayList.add(insetsAnimationControlRunner.getAnimation());
                }
                if (((InternalInsetsAnimationController) insetsAnimationControlRunner).applyChangeInsets(insetsState)) {
                    arrayList2.add(insetsAnimationControlRunner.getAnimation());
                }
            }
        }
        Rect rect = this.mFrame;
        InsetsState insetsState2 = this.mState;
        boolean isRound = this.mLastInsets.isRound();
        int i2 = this.mLastLegacySoftInputMode;
        int i3 = this.mLastLegacyWindowFlags;
        if (this.mSystemBarControlledByPolicy) {
            i = this.mLastLegacySystemUiFlags & (-257);
        } else {
            i = this.mLastLegacySystemUiFlags;
        }
        WindowInsets calculateInsets = insetsState.calculateInsets(rect, insetsState2, isRound, i2, i3, i, this.mWindowType, this.mLastActivityType, null);
        if (CoreRune.FW_CAN_DISPATCH_UDC_CUTOUT && insetsState.mCanDispatchUdcCutout) {
            Host host = this.mHost;
            if ((host instanceof ViewRootInsetsControllerHost) && (viewRoot = ((ViewRootInsetsControllerHost) host).getViewRoot()) != null && viewRoot.isCutoutRemoveNeeded()) {
                calculateInsets = calculateInsets.removeCutoutInsets(insetsState.mCanDispatchUdcCutout);
            }
        }
        this.mHost.dispatchWindowInsetsAnimationProgress(calculateInsets, Collections.unmodifiableList(arrayList));
        if (DEBUG) {
            for (WindowInsetsAnimation windowInsetsAnimation : arrayList) {
                Log.d(TAG, String.format("Running animation on insets type: %d, progress: %f", Integer.valueOf(windowInsetsAnimation.getTypeMask()), Float.valueOf(windowInsetsAnimation.getInterpolatedFraction())));
            }
        }
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            dispatchAnimationEnd((WindowInsetsAnimation) arrayList2.get(size2));
        }
    }

    public void onFrameChanged(Rect rect) {
        if (this.mFrame.equals(rect)) {
            return;
        }
        int i = this.mImeCaptionBarInsetsHeight;
        if (i != 0) {
            setImeCaptionBarInsetsHeight(i);
        }
        this.mHost.notifyInsetsChanged();
        this.mFrame.set(rect);
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

    public boolean onStateChanged(InsetsState insetsState) {
        if (this.mState.equals(insetsState, false, false) && this.mLastDispatchedState.equals(insetsState)) {
            return false;
        }
        Log.i(TAG, "onStateChanged: host=" + this.mHost.getRootViewTitle() + ", from=" + Debug.getCaller() + ", state=" + insetsState);
        InsetsState insetsState2 = new InsetsState(this.mState, true);
        updateState(insetsState);
        applyLocalVisibilityOverride();
        updateCompatSysUiVisibility();
        if (!this.mState.equals(insetsState2, false, true)) {
            if (DEBUG) {
                Log.d(TAG, "onStateChanged, notifyInsetsChanged");
            }
            this.mHost.notifyInsetsChanged();
            if (this.mLastDispatchedState.getDisplayFrame().equals(insetsState.getDisplayFrame())) {
                InsetsState.traverse(this.mLastDispatchedState, insetsState, this.mStartResizingAnimationIfNeeded);
            }
        }
        this.mLastDispatchedState.set(insetsState, true);
        return true;
    }

    private void updateState(InsetsState insetsState) {
        this.mState.set(insetsState, 0);
        final int[] iArr = {0};
        int sourceSize = insetsState.sourceSize();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sourceSize; i3++) {
            InsetsSource insetsSource = new InsetsSource(insetsState.sourceAt(i3));
            int type = insetsSource.getType();
            int animationType = getAnimationType(type);
            InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(insetsSource.getId());
            if (insetsSourceConsumer != null) {
                insetsSourceConsumer.updateSource(insetsSource, animationType);
            } else {
                this.mState.addSource(insetsSource);
            }
            i2 |= type;
            if (insetsSource.isVisible()) {
                i |= type;
            }
        }
        int defaultVisible = (WindowInsets.Type.defaultVisible() & (~i2)) | i;
        int i4 = this.mVisibleTypes;
        if (i4 != defaultVisible) {
            if (WindowInsets.Type.hasCompatSystemBars(i4 ^ defaultVisible)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mVisibleTypes = defaultVisible;
        }
        int i5 = this.mExistingTypes;
        if (i5 != i2) {
            if (WindowInsets.Type.hasCompatSystemBars(i5 ^ i2)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mExistingTypes = i2;
        }
        InsetsState.traverse(this.mState, insetsState, this.mRemoveGoneSources);
        if (iArr[0] != 0) {
            this.mHandler.post(new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InsetsController.this.lambda$updateState$4(iArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$4(int[] iArr) {
        show(iArr[0]);
    }

    public WindowInsets calculateInsets(boolean z, int i, int i2, int i3, int i4, int i5) {
        return calculateInsets(z, i, i2, i3, i4, i5, false);
    }

    public WindowInsets calculateInsets(boolean z, int i, int i2, int i3, int i4, int i5, boolean z2) {
        int i6 = i5;
        this.mWindowType = i;
        this.mLastActivityType = i2;
        this.mLastLegacySoftInputMode = i3;
        this.mLastLegacyWindowFlags = i4;
        this.mLastLegacySystemUiFlags = i6;
        InsetsState insetsState = this.mState;
        Rect rect = this.mFrame;
        if (this.mSystemBarControlledByPolicy) {
            i6 &= -257;
        }
        WindowInsets calculateInsets = insetsState.calculateInsets(rect, null, z, i3, i4, i6, i, i2, null, z2);
        this.mLastInsets = calculateInsets;
        return calculateInsets;
    }

    public Insets calculateVisibleInsets(int i, int i2, int i3, int i4) {
        return this.mState.calculateVisibleInsets(this.mFrame, i, i2, i3, i4);
    }

    public void onControlsChanged(InsetsSourceControl[] insetsSourceControlArr) {
        InsetsController insetsController = this;
        insetsController.mSystemBarControlledByPolicy = false;
        if (insetsSourceControlArr != null) {
            insetsController.mHost.applyInsetsHintSandboxingIfNeeded(insetsSourceControlArr);
            for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    if (insetsSourceControl.isControlledByPolicy()) {
                        insetsController.mSystemBarControlledByPolicy = true;
                    } else {
                        insetsController.mTmpControlArray.put(insetsSourceControl.getId(), insetsSourceControl);
                    }
                }
            }
        }
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        int[] iArr3 = new int[1];
        int[] iArr4 = new int[1];
        int i = 0;
        int i2 = 0;
        ImeTracker.Token token = null;
        for (int size = insetsController.mSourceConsumers.size() - 1; size >= 0; size--) {
            InsetsSourceConsumer valueAt = insetsController.mSourceConsumers.valueAt(size);
            if (valueAt.getId() != InsetsSource.ID_IME_CAPTION_BAR) {
                InsetsSourceControl insetsSourceControl2 = insetsController.mTmpControlArray.get(valueAt.getId());
                if (insetsSourceControl2 != null) {
                    int type = insetsSourceControl2.getType() | i2;
                    i++;
                    if (Flags.refactorInsetsController() && insetsSourceControl2.getId() == InsetsSource.ID_IME) {
                        token = insetsSourceControl2.getImeStatsToken();
                    }
                    i2 = type;
                }
                valueAt.setControl(insetsSourceControl2, iArr, iArr2, iArr3, iArr4);
            }
        }
        if (i != insetsController.mTmpControlArray.size()) {
            for (int size2 = insetsController.mTmpControlArray.size() - 1; size2 >= 0; size2--) {
                InsetsSourceControl valueAt2 = insetsController.mTmpControlArray.valueAt(size2);
                insetsController.getSourceConsumer(valueAt2.getId(), valueAt2.getType()).setControl(valueAt2, iArr, iArr2, iArr3, iArr4);
            }
        }
        if (insetsController.mTmpControlArray.size() > 0) {
            for (int size3 = insetsController.mRunningAnimations.size() - 1; size3 >= 0; size3--) {
                insetsController.mRunningAnimations.get(size3).runner.updateSurfacePosition(insetsController.mTmpControlArray);
            }
        }
        insetsController.mTmpControlArray.clear();
        int i3 = iArr3[0];
        if (i3 != 0) {
            insetsController.cancelExistingControllers(i3);
        }
        int invokeControllableInsetsChangedListeners = insetsController.invokeControllableInsetsChangedListeners();
        int i4 = iArr[0];
        int i5 = ~invokeControllableInsetsChangedListeners;
        iArr[0] = i4 & i5;
        iArr2[0] = i5 & iArr2[0];
        if (Flags.refactorInsetsController()) {
            if (insetsController.mPendingImeControlRequest != null && insetsController.getImeSourceConsumer().getControl() != null && insetsController.getImeSourceConsumer().getControl().getLeash() != null) {
                insetsController.handlePendingControlRequest(token);
            } else {
                int i6 = iArr[0];
                if (i6 != 0) {
                    if ((i6 & WindowInsets.Type.ime()) != 0) {
                        ImeTracker.forLogging().onProgress(token, 76);
                    }
                    insetsController.applyAnimation(iArr[0], true, false, false, token);
                }
                int i7 = iArr2[0];
                if (i7 != 0) {
                    if ((i7 & WindowInsets.Type.ime()) != 0) {
                        ImeTracker.forLogging().onProgress(token, 76);
                    }
                    int i8 = iArr2[0];
                    insetsController = this;
                    insetsController.applyAnimation(i8, false, false, ((~iArr4[0]) & i8) == 0, token);
                } else {
                    insetsController = this;
                }
                if ((iArr[0] & WindowInsets.Type.ime()) == 0 && (iArr2[0] & WindowInsets.Type.ime()) == 0) {
                    ImeTracker.forLogging().onCancelled(token, 76);
                }
            }
        } else {
            int i9 = iArr[0];
            if (i9 != 0) {
                insetsController.applyAnimation(iArr[0], true, false, false, (i9 & WindowInsets.Type.ime()) == 0 ? null : ImeTracker.forLogging().onStart(1, 5, 46, insetsController.mHost.isHandlingPointerEvent()));
            }
            int i10 = iArr2[0];
            if (i10 != 0) {
                ImeTracker.Token onStart = (i10 & WindowInsets.Type.ime()) == 0 ? null : ImeTracker.forLogging().onStart(2, 5, 46, insetsController.mHost.isHandlingPointerEvent());
                int i11 = iArr2[0];
                insetsController.applyAnimation(i11, false, false, ((~iArr4[0]) & i11) == 0, onStart);
            }
        }
        int i12 = insetsController.mControllableTypes;
        if (i12 != i2) {
            if (WindowInsets.Type.hasCompatSystemBars(i12 ^ i2)) {
                insetsController.mCompatSysUiVisibilityStaled = true;
            }
            insetsController.mControllableTypes = i2;
        }
        if (Flags.refactorInsetsController()) {
            insetsController.applyLocalVisibilityOverride();
        }
        insetsController.reportRequestedVisibleTypes(null);
    }

    public void setPredictiveBackImeHideAnimInProgress(boolean z) {
        this.mIsPredictiveBackImeHideAnimInProgress = z;
        if (z) {
            for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
                InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
                if ((insetsAnimationControlRunner.getTypes() & WindowInsets.Type.ime()) != 0) {
                    insetsAnimationControlRunner.updateLayoutInsetsDuringAnimation(1);
                    return;
                }
            }
        }
    }

    public boolean isPredictiveBackImeHideAnimInProgress() {
        return this.mIsPredictiveBackImeHideAnimInProgress;
    }

    @Override // android.view.WindowInsetsController
    public void show(int i) {
        show(i, false, null);
    }

    public void show(int i, boolean z, ImeTracker.Token token) {
        if ((WindowInsets.Type.ime() & i) != 0) {
            Log.d(TAG, "show(ime(), fromIme=" + z + NavigationBarInflaterView.KEY_CODE_END);
            if (token == null) {
                token = ImeTracker.forLogging().onStart(1, 5, 26, this.mHost.isHandlingPointerEvent());
            }
        }
        ImeTracker.Token token2 = token;
        if (z) {
            ImeTracing.getInstance().triggerClientDump("InsetsController#show", this.mHost.getInputMethodManager(), null);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            Trace.asyncTraceBegin(8L, "IC.showRequestFromIme", 0);
        } else {
            Trace.asyncTraceBegin(8L, "IC.showRequestFromApi", 0);
            Trace.asyncTraceBegin(8L, "IC.showRequestFromApiToImeReady", 0);
        }
        if (!Flags.refactorInsetsController() && z && this.mPendingImeControlRequest != null) {
            if ((i & WindowInsets.Type.ime()) != 0) {
                ImeTracker.forLatency().onShown(token2, new InsetsController$$ExternalSyntheticLambda2());
            }
            handlePendingControlRequest(token2);
            return;
        }
        boolean isSourceOrDefaultVisible = this.mState.isSourceOrDefaultVisible(this.mImeSourceConsumer.getId(), WindowInsets.Type.ime());
        int i2 = 0;
        int i3 = 1;
        while (i3 <= 512) {
            if ((i & i3) != 0) {
                int animationType = getAnimationType(i3);
                boolean z2 = (this.mRequestedVisibleTypes & i3) != 0;
                boolean z3 = i3 == WindowInsets.Type.ime();
                boolean z4 = z2 && (!z3 || isSourceOrDefaultVisible) && animationType == -1;
                boolean z5 = animationType == 0;
                if (z4 || z5) {
                    if (DEBUG) {
                        Log.d(TAG, String.format("show ignored for type: %d animType: %d requestedVisible: %s", Integer.valueOf(i3), Integer.valueOf(animationType), Boolean.valueOf(z2)));
                    }
                    if (z3) {
                        ImeTracker.forLogging().onCancelled(token2, 32);
                    }
                } else if (Flags.refactorInsetsController() || !z || animationType != 2 || this.mIsPredictiveBackImeHideAnimInProgress) {
                    if (z3) {
                        ImeTracker.forLogging().onProgress(token2, 32);
                    }
                    i2 |= i3;
                } else if (z3) {
                    ImeTracker.forLogging().onFailed(token2, 32);
                }
            }
            i3 <<= 1;
        }
        if (DEBUG) {
            Log.d(TAG, "show typesReady: " + i2);
        }
        if ((Flags.refactorInsetsController() || z) && (WindowInsets.Type.ime() & i2) != 0) {
            ImeTracker.forLatency().onShown(token2, new InsetsController$$ExternalSyntheticLambda2());
        }
        applyAnimation(i2, true, z, false, token2);
    }

    private void handlePendingControlRequest(ImeTracker.Token token) {
        PendingControlRequest pendingControlRequest = this.mPendingImeControlRequest;
        this.mPendingImeControlRequest = null;
        this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        controlAnimationUnchecked(pendingControlRequest.types, pendingControlRequest.cancellationSignal, pendingControlRequest.listener, null, true, pendingControlRequest.mInsetsAnimationSpec, pendingControlRequest.animationType, pendingControlRequest.layoutInsetsDuringAnimation, pendingControlRequest.useInsetsAnimationThread, token, false);
    }

    @Override // android.view.WindowInsetsController
    public void hide(int i) {
        hide(i, false, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void hide(int r17, boolean r18, android.view.inputmethod.ImeTracker.Token r19) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.InsetsController.hide(int, boolean, android.view.inputmethod.ImeTracker$Token):void");
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        controlWindowInsetsAnimation(i, cancellationSignal, windowInsetsAnimationControlListener, false, j, interpolator, 2, false);
    }

    public void controlWindowInsetsAnimation(int i, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, boolean z, final long j, final Interpolator interpolator, int i2, boolean z2) {
        if ((this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame) & i) != 0 || (z2 && (this.mRequestedVisibleTypes & WindowInsets.Type.ime()) == 0)) {
            windowInsetsAnimationControlListener.onCancelled(null);
            return;
        }
        if (z) {
            ImeTracing.getInstance().triggerClientDump("InsetsController#controlWindowInsetsAnimation", this.mHost.getInputMethodManager(), null);
        }
        controlAnimationUnchecked(i, cancellationSignal, windowInsetsAnimationControlListener, this.mFrame, z, new InsetsAnimationSpec(this) { // from class: android.view.InsetsController.4
            @Override // android.view.InsetsAnimationSpec
            public long getDurationMs(boolean z3) {
                return j;
            }

            @Override // android.view.InsetsAnimationSpec
            public Interpolator getInsetsInterpolator(boolean z3) {
                return interpolator;
            }
        }, i2, getLayoutInsetsDuringAnimationMode(i, z2), false, null, z2);
    }

    private void controlAnimationUnchecked(int i, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, Rect rect, boolean z, InsetsAnimationSpec insetsAnimationSpec, int i2, int i3, boolean z2, ImeTracker.Token token, boolean z3) {
        boolean z4 = i3 == 0;
        if (Flags.refactorInsetsController() && !z3 && !z4 && (WindowInsets.Type.ime() & i) != 0 && (this.mRequestedVisibleTypes & WindowInsets.Type.ime()) != 0) {
            this.mHost.getInputMethodManager().getImeOnBackInvokedDispatcher().preliminaryClear();
        }
        setRequestedVisibleTypes(z4 ? i : 0, i);
        controlAnimationUncheckedInner(i, cancellationSignal, windowInsetsAnimationControlListener, rect, z, insetsAnimationSpec, i2, i3, z2, token, z3);
        reportRequestedVisibleTypes(token);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6, types: [android.view.inputmethod.ImeTracker$Token, byte[]] */
    /* JADX WARN: Type inference failed for: r13v7 */
    private void controlAnimationUncheckedInner(int i, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, Rect rect, boolean z, InsetsAnimationSpec insetsAnimationSpec, int i2, int i3, boolean z2, ImeTracker.Token token, boolean z3) {
        CancellationSignal cancellationSignal2;
        String str;
        String str2;
        int i4;
        String str3;
        WindowInsetsAnimationController windowInsetsAnimationController;
        final InsetsController insetsController;
        SparseArray<InsetsSourceControl> sparseArray;
        int i5;
        CancellationSignal cancellationSignal3;
        ?? r13;
        final InsetsController insetsController2;
        final InsetsAnimationControlRunner insetsAnimationControlImpl;
        SparseArray<InsetsSourceControl> sparseArray2;
        int i6;
        int i7;
        ImeTracker.Token token2;
        long j;
        int i8;
        String str4;
        int i9;
        Integer num;
        if ((this.mTypesBeingCancelled & i) != 0) {
            if ((i2 == 0 || i2 == 1) && (WindowInsets.Type.ime() & i) != 0) {
                if (i2 == 0) {
                    ImeTracker.forLatency().onShowCancelled(token, 40, new InsetsController$$ExternalSyntheticLambda2());
                } else {
                    ImeTracker.forLatency().onHideCancelled(token, 40, new InsetsController$$ExternalSyntheticLambda2());
                }
                ImeTracker.forLogging().onCancelled(token, 33);
            }
            throw new IllegalStateException("Cannot start a new insets animation of " + WindowInsets.Type.toString(i) + " while an existing " + WindowInsets.Type.toString(this.mTypesBeingCancelled) + " is being cancelled.");
        }
        ImeTracker.forLogging().onProgress(token, 33);
        if (i == 0) {
            windowInsetsAnimationControlListener.onCancelled(null);
            if (DEBUG) {
                Log.d(TAG, "no types to animate in controlAnimationUnchecked");
            }
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            ImeTracker.forLogging().onFailed(token, 33);
            return;
        }
        boolean z4 = DEBUG;
        if (z4) {
            Log.d(TAG, "controlAnimation types: " + i);
        }
        this.mLastStartedAnimTypes |= i;
        SparseArray<InsetsSourceControl> sparseArray3 = new SparseArray<>();
        if (!Flags.refactorInsetsController()) {
            cancellationSignal2 = cancellationSignal;
            str = "IC.showRequestFromApiToImeReady";
            str2 = "IC.showRequestFromApi";
            i4 = 1;
            str3 = TAG;
            windowInsetsAnimationController = null;
            Pair<Integer, Boolean> collectSourceControls = collectSourceControls(z, i, sparseArray3, i2, token, z3);
            insetsController = this;
            sparseArray = sparseArray3;
            Integer num2 = collectSourceControls.first;
            int intValue = num2.intValue();
            Boolean bool = collectSourceControls.second;
            boolean booleanValue = bool.booleanValue();
            if (z4) {
                Log.d(str3, TextUtils.formatSimple("controlAnimationUnchecked, typesReady: %s imeReady: %s", num2, bool));
            }
            if (!booleanValue) {
                insetsController.abortPendingImeControlRequest();
                final PendingControlRequest pendingControlRequest = new PendingControlRequest(i, windowInsetsAnimationControlListener, insetsAnimationSpec, i2, i3, cancellationSignal2, z2);
                insetsController.mPendingImeControlRequest = pendingControlRequest;
                insetsController.mHandler.postDelayed(insetsController.mPendingControlTimeout, 2000L);
                if (z4) {
                    Log.d(str3, "Ime not ready. Create pending request");
                }
                if (cancellationSignal2 != null) {
                    cancellationSignal2.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda6
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            InsetsController.this.lambda$controlAnimationUncheckedInner$6(pendingControlRequest);
                        }
                    });
                }
                releaseControls(sparseArray);
                insetsController.setRequestedVisibleTypes(insetsController.mReportedRequestedVisibleTypes, i);
                Trace.asyncTraceEnd(8L, str2, 0);
                if (z) {
                    return;
                }
                Trace.asyncTraceEnd(8L, str, 0);
                return;
            }
            i5 = intValue;
        } else {
            Pair<Integer, Integer> collectSourceControlsV2 = collectSourceControlsV2(i, sparseArray3);
            Integer num3 = collectSourceControlsV2.first;
            int intValue2 = num3.intValue();
            if (i2 != 2) {
                cancellationSignal2 = cancellationSignal;
                str4 = "IC.showRequestFromApiToImeReady";
                sparseArray = sparseArray3;
                str3 = TAG;
                i9 = intValue2;
                i4 = 1;
            } else {
                int intValue3 = collectSourceControlsV2.second.intValue();
                if ((WindowInsets.Type.ime() & i) == 0 || (intValue3 & i) == 0) {
                    cancellationSignal2 = cancellationSignal;
                    str4 = "IC.showRequestFromApiToImeReady";
                    sparseArray = sparseArray3;
                    str3 = TAG;
                    num = num3;
                    i9 = intValue2;
                    i4 = 1;
                } else {
                    str4 = "IC.showRequestFromApiToImeReady";
                    sparseArray = sparseArray3;
                    i9 = intValue2;
                    i4 = 1;
                    cancellationSignal2 = cancellationSignal;
                    num = num3;
                    final PendingControlRequest pendingControlRequest2 = new PendingControlRequest(i, windowInsetsAnimationControlListener, insetsAnimationSpec, i2, 0, cancellationSignal2, false);
                    this.mPendingImeControlRequest = pendingControlRequest2;
                    this.mHandler.postDelayed(this.mPendingControlTimeout, 2000L);
                    str3 = TAG;
                    if (z4) {
                        Log.d(str3, "Ime not ready. Create pending request");
                    }
                    if (cancellationSignal2 != null) {
                        cancellationSignal2.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda5
                            @Override // android.os.CancellationSignal.OnCancelListener
                            public final void onCancel() {
                                InsetsController.this.lambda$controlAnimationUncheckedInner$5(pendingControlRequest2);
                            }
                        });
                    }
                }
                if (i9 != (i & (CoreRune.FW_TEMP_INSETS_BUG_FIX ? CONTROLLABLE_TYPES : i))) {
                    if (z4) {
                        Log.d(str3, TextUtils.formatSimple("not all types are ready yet, waiting. typesReady: %s, types: %s", num, Integer.valueOf(i)));
                        return;
                    }
                    return;
                }
            }
            str2 = "IC.showRequestFromApi";
            i5 = i9;
            insetsController = this;
            str = str4;
            windowInsetsAnimationController = null;
        }
        CancellationSignal cancellationSignal4 = cancellationSignal2;
        if (i5 == 0) {
            if (Flags.refactorInsetsController()) {
                Trace.asyncTraceEnd(8L, str2, 0);
                windowInsetsAnimationControlListener.onCancelled(windowInsetsAnimationController);
                return;
            }
            if (z4) {
                Log.d(str3, "No types ready. onCancelled()");
            }
            windowInsetsAnimationControlListener.onCancelled(windowInsetsAnimationController);
            Trace.asyncTraceEnd(8L, str2, 0);
            if (z) {
                return;
            }
            Trace.asyncTraceEnd(8L, str, 0);
            return;
        }
        if (Flags.refactorInsetsController()) {
            insetsController.mCancelledForNewAnimationTypes = i5;
            insetsController.cancelExistingControllers(i5);
            insetsController.mCancelledForNewAnimationTypes = 0;
        } else {
            insetsController.cancelExistingControllers(i5);
        }
        if (z2) {
            cancellationSignal3 = cancellationSignal;
            r13 = windowInsetsAnimationController;
            insetsController2 = insetsController;
            sparseArray2 = sparseArray;
            insetsAnimationControlImpl = new InsetsAnimationThreadControlRunner(sparseArray2, rect, insetsController.mState, windowInsetsAnimationControlListener, i5, insetsController2, insetsAnimationSpec, i2, i3, insetsController.mHost.getTranslator(), insetsController.mHost.getHandler(), token);
            i7 = i2;
            i6 = i5;
            token2 = token;
        } else {
            cancellationSignal3 = cancellationSignal4;
            r13 = windowInsetsAnimationController;
            insetsController2 = insetsController;
            sparseArray2 = sparseArray;
            insetsAnimationControlImpl = new InsetsAnimationControlImpl(sparseArray2, rect, insetsController2.mState, windowInsetsAnimationControlListener, i5, insetsController2, this, insetsAnimationSpec, i2, i3, insetsController2.mHost.getTranslator(), token);
            i6 = i5;
            i7 = i2;
            token2 = token;
        }
        for (int size = sparseArray2.size() - 1; size >= 0; size--) {
            InsetsSourceConsumer insetsSourceConsumer = insetsController2.mSourceConsumers.get(sparseArray2.keyAt(size));
            if (insetsSourceConsumer != null) {
                insetsSourceConsumer.setSurfaceParamsApplier(insetsAnimationControlImpl.getSurfaceParamsApplier());
            }
        }
        if ((WindowInsets.Type.ime() & i6) != 0) {
            ImeTracing.getInstance().triggerClientDump("InsetsAnimationControlImpl", insetsController2.mHost.getInputMethodManager(), r13);
            if (i7 == i4) {
                ImeTracker.forLatency().onHidden(token2, new InsetsController$$ExternalSyntheticLambda2());
            }
        }
        ImeTracker.forLogging().onProgress(token2, 39);
        int types = insetsController2.mAnimatingTypes | insetsAnimationControlImpl.getTypes();
        insetsController2.mAnimatingTypes = types;
        insetsController2.mHost.updateAnimatingTypes(types, r13);
        insetsController2.mRunningAnimations.add(new RunningAnimation(insetsAnimationControlImpl, i7));
        Log.i(str3, "controlAnimationUncheckedInner: Added types=" + WindowInsets.Type.toString(i6) + ", animType=" + i7 + ", host=" + insetsController2.mHost.getRootViewTitle() + ", from=" + Debug.getCallers(3));
        if (cancellationSignal3 != null) {
            cancellationSignal3.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda7
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    InsetsController.this.lambda$controlAnimationUncheckedInner$7(insetsAnimationControlImpl);
                }
            });
            j = 8;
            i8 = 0;
        } else {
            j = 8;
            i8 = 0;
            Trace.asyncTraceBegin(8L, "IC.pendingAnim", 0);
        }
        insetsController2.onAnimationStateChanged(i, true);
        if (!z) {
            if (i7 == 1) {
                Trace.asyncTraceEnd(j, "IC.hideRequestFromApi", i8);
            }
        } else if (i7 == 0) {
            Trace.asyncTraceEnd(j, "IC.showRequestFromIme", i8);
        } else {
            if (i7 != 1) {
                return;
            }
            Trace.asyncTraceEnd(j, "IC.hideRequestFromIme", i8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$5(PendingControlRequest pendingControlRequest) {
        if (this.mPendingImeControlRequest == pendingControlRequest) {
            if (DEBUG) {
                Log.d(TAG, "Cancellation signal abortPendingImeControlRequest");
            }
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$6(PendingControlRequest pendingControlRequest) {
        if (this.mPendingImeControlRequest == pendingControlRequest) {
            if (DEBUG) {
                Log.d(TAG, "Cancellation signal abortPendingImeControlRequest");
            }
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$7(InsetsAnimationControlRunner insetsAnimationControlRunner) {
        cancelAnimation(insetsAnimationControlRunner, true);
    }

    static void releaseControls(SparseArray<InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray.valueAt(size).release(new InsetsController$$ExternalSyntheticLambda8());
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        this.mLoggingListener = windowInsetsAnimationControlListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.lang.Integer, java.lang.Boolean> collectSourceControls(boolean r15, int r16, android.util.SparseArray<android.view.InsetsSourceControl> r17, int r18, android.view.inputmethod.ImeTracker.Token r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.InsetsController.collectSourceControls(boolean, int, android.util.SparseArray, int, android.view.inputmethod.ImeTracker$Token, boolean):android.util.Pair");
    }

    private Pair<Integer, Integer> collectSourceControlsV2(int i, SparseArray<InsetsSourceControl> sparseArray) {
        InsetsSourceControl control;
        int i2 = 0;
        int i3 = 0;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0 && (control = valueAt.getControl()) != null) {
                if (control.getLeash() != null || control.getId() == InsetsSource.ID_IME_CAPTION_BAR) {
                    sparseArray.put(control.getId(), new InsetsSourceControl(control));
                    i2 |= valueAt.getType();
                } else {
                    i3 |= valueAt.getType();
                }
            }
        }
        return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3));
    }

    private int getLayoutInsetsDuringAnimationMode(int i, boolean z) {
        return ((!z || this.mHost.hasAnimationCallbacks()) && (this.mRequestedVisibleTypes & i) == i) ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelExistingControllers(int i) {
        int i2 = this.mTypesBeingCancelled;
        this.mTypesBeingCancelled = i2 | i;
        try {
            for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
                InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
                if ((insetsAnimationControlRunner.getTypes() & i) != 0) {
                    cancelAnimation(insetsAnimationControlRunner, true);
                }
            }
            if ((i & WindowInsets.Type.ime()) != 0) {
                abortPendingImeControlRequest();
            }
        } finally {
            this.mTypesBeingCancelled = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortPendingImeControlRequest() {
        PendingControlRequest pendingControlRequest = this.mPendingImeControlRequest;
        if (pendingControlRequest != null) {
            pendingControlRequest.listener.onCancelled(null);
            this.mPendingImeControlRequest = null;
            this.mHandler.removeCallbacks(this.mPendingControlTimeout);
            if (DEBUG) {
                Log.d(TAG, "abortPendingImeControlRequest");
            }
        }
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void notifyFinished(InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z) {
        if (insetsAnimationControlRunner.isCancelRequested()) {
            Log.d(TAG, "Ignore notifyFinished, because the animation has already been cancelled.");
            return;
        }
        setRequestedVisibleTypes(z ? insetsAnimationControlRunner.getTypes() : 0, insetsAnimationControlRunner.getTypes());
        cancelAnimation(insetsAnimationControlRunner, false);
        if (DEBUG) {
            Log.d(TAG, "notifyFinished. shown: " + z);
        }
        if (insetsAnimationControlRunner.getAnimationType() == 3) {
            return;
        }
        ImeTracker.Token statsToken = insetsAnimationControlRunner.getStatsToken();
        if (insetsAnimationControlRunner.getAnimationType() == 2) {
            ImeTracker.forLogging().onUserFinished(statsToken, z);
        } else if (z) {
            ImeTracker.forLogging().onProgress(statsToken, 41);
            ImeTracker.forLogging().onShown(statsToken);
        } else if (!Flags.refactorInsetsController()) {
            ImeTracker.forLogging().onProgress(statsToken, 42);
            ImeTracker.forLogging().onHidden(statsToken);
        }
        reportRequestedVisibleTypes(null);
    }

    @Override // android.view.InsetsAnimationControlRunner.SurfaceParamsApplier
    public void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        this.mHost.applySurfaceParams(surfaceParamsArr);
    }

    void notifyControlRevoked(InsetsSourceConsumer insetsSourceConsumer) {
        int type = insetsSourceConsumer.getType();
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
            insetsAnimationControlRunner.notifyControlRevoked(type);
            if (insetsAnimationControlRunner.getControllingTypes() == 0) {
                cancelAnimation(insetsAnimationControlRunner, true);
            }
        }
        if (type == WindowInsets.Type.ime()) {
            abortPendingImeControlRequest();
        }
        if (insetsSourceConsumer.getType() != WindowInsets.Type.ime()) {
            this.mSourceConsumers.remove(insetsSourceConsumer.getId());
        }
    }

    private void cancelAnimation(InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z) {
        int i;
        if (z) {
            ImeTracker.forLogging().onCancelled(insetsAnimationControlRunner.getStatsToken(), 40);
            insetsAnimationControlRunner.cancel();
        } else {
            ImeTracker.forLogging().onProgress(insetsAnimationControlRunner.getStatsToken(), 40);
        }
        Log.i(TAG, TextUtils.formatSimple("cancelAnimation: types=%s, animType=%d, host=%s, from=%s", WindowInsets.Type.toString(insetsAnimationControlRunner.getTypes()), Integer.valueOf(insetsAnimationControlRunner.getAnimationType()), this.mHost.getRootViewTitle(), Debug.getCallers(3)));
        int size = this.mRunningAnimations.size() - 1;
        while (true) {
            if (size < 0) {
                i = 0;
                break;
            }
            RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            if (runningAnimation.runner == insetsAnimationControlRunner) {
                this.mRunningAnimations.remove(size);
                i = insetsAnimationControlRunner.getTypes();
                if (z) {
                    dispatchAnimationEnd(runningAnimation.runner.getAnimation());
                } else if (Flags.refactorInsetsController() && (WindowInsets.Type.ime() & i) != 0 && insetsAnimationControlRunner.getAnimationType() == 1 && this.mHost != null) {
                    reportRequestedVisibleTypes(!Flags.reportAnimatingInsetsTypes() ? insetsAnimationControlRunner.getStatsToken() : null);
                    this.mHost.getInputMethodManager().removeImeSurface(this.mHost.getWindowToken());
                }
            } else {
                size--;
            }
        }
        if (i > 0) {
            this.mAnimatingTypes &= ~i;
            if (this.mHost != null) {
                this.mHost.updateAnimatingTypes(this.mAnimatingTypes, Flags.reportAnimatingInsetsTypes() && (WindowInsets.Type.ime() & i) != 0 && insetsAnimationControlRunner.getAnimationType() == 1 ? insetsAnimationControlRunner.getStatsToken() : null);
            }
        }
        onAnimationStateChanged(i, false);
    }

    void onAnimationStateChanged(int i, boolean z) {
        boolean z2 = false;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0) {
                z2 |= valueAt.onAnimationStateChanged(z);
            }
        }
        if (z2) {
            notifyVisibilityChanged();
        }
    }

    private void applyLocalVisibilityOverride() {
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            this.mSourceConsumers.valueAt(size).applyLocalVisibilityOverride();
        }
    }

    int getCancelledForNewAnimationTypes() {
        return this.mCancelledForNewAnimationTypes;
    }

    public InsetsSourceConsumer getSourceConsumer(int i, int i2) {
        InsetsSourceConsumer apply;
        InsetsSourceConsumer insetsSourceConsumer;
        InsetsSourceConsumer insetsSourceConsumer2 = this.mSourceConsumers.get(i);
        if (insetsSourceConsumer2 != null) {
            return insetsSourceConsumer2;
        }
        if (i2 == WindowInsets.Type.ime() && (insetsSourceConsumer = this.mImeSourceConsumer) != null) {
            this.mSourceConsumers.remove(insetsSourceConsumer.getId());
            apply = this.mImeSourceConsumer;
            apply.setId(i);
        } else {
            apply = this.mConsumerCreator.apply(this, Integer.valueOf(i), Integer.valueOf(i2));
        }
        this.mSourceConsumers.put(i, apply);
        return apply;
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
            this.mHost.updateCompatSysUiVisibility(this.mVisibleTypes, this.mRequestedVisibleTypes, (~this.mExistingTypes) | this.mControllableTypes);
        }
    }

    public void onWindowFocusGained(boolean z) {
        this.mImeSourceConsumer.onWindowFocusGained(z);
    }

    public void onWindowFocusLost() {
        this.mImeSourceConsumer.onWindowFocusLost();
    }

    public int getAnimationType(int i) {
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            if (this.mRunningAnimations.get(size).runner.controlsType(i)) {
                return this.mRunningAnimations.get(size).type;
            }
        }
        return -1;
    }

    boolean hasSurfaceAnimation(int i) {
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
            if (insetsAnimationControlRunner.controlsType(i) && insetsAnimationControlRunner.willUpdateSurface()) {
                return true;
            }
        }
        return false;
    }

    public void setRequestedVisibleTypes(int i, int i2) {
        int i3 = this.mRequestedVisibleTypes;
        int i4 = (i & i2) | ((~i2) & i3);
        if (i3 != i4) {
            if (Flags.refactorInsetsController() && (this.mRequestedVisibleTypes & WindowInsets.Type.ime()) == 0 && (WindowInsets.Type.ime() & i4) != 0) {
                getHost().getInputMethodManager().getImeOnBackInvokedDispatcher().undoPreliminaryClear();
            }
            ProtoLog.d(ViewProtoLogGroups.IME_INSETS_CONTROLLER, "Setting requestedVisibleTypes to %d (was %d)", Integer.valueOf(i4), Integer.valueOf(this.mRequestedVisibleTypes));
            this.mRequestedVisibleTypes = i4;
            StringBuilder sb = new StringBuilder("setRequestedVisibleTypes: visible=");
            sb.append((i4 & i2) != 0);
            sb.append(", mask=");
            sb.append(WindowInsets.Type.toString(i2));
            sb.append(", host=");
            sb.append(getHost().getRootViewTitle());
            sb.append(", from=");
            sb.append(Debug.getCallers(10));
            Log.i(TAG, sb.toString());
        }
    }

    public int computeUserAnimatingTypes() {
        int i = 0;
        for (int i2 = 0; i2 < this.mRunningAnimations.size(); i2++) {
            if (this.mRunningAnimations.get(i2).runner.getAnimationType() == 2) {
                i |= this.mRunningAnimations.get(i2).runner.getTypes();
            }
        }
        return i;
    }

    private void reportRequestedVisibleTypes(ImeTracker.Token token) {
        int i;
        InsetsSourceConsumer insetsSourceConsumer;
        InsetsSourceControl control;
        if (Flags.refactorInsetsController()) {
            if (Flags.reportAnimatingInsetsTypes()) {
                i = this.mRequestedVisibleTypes;
            } else {
                i = this.mRequestedVisibleTypes | (this.mAnimatingTypes & WindowInsets.Type.ime());
            }
        } else {
            i = this.mRequestedVisibleTypes;
        }
        int i2 = this.mReportedRequestedVisibleTypes;
        if (i != i2) {
            if (WindowInsets.Type.hasCompatSystemBars(i2 ^ i)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            if (Flags.refactorInsetsController()) {
                ImeTracker.forLogging().onProgress(token, 48);
                if (Flags.reportAnimatingInsetsTypes() && (i & WindowInsets.Type.ime()) == 0) {
                    token = null;
                }
            }
            int i3 = this.mRequestedVisibleTypes;
            this.mReportedRequestedVisibleTypes = i3;
            this.mHost.updateRequestedVisibleTypes(i3, token);
        } else if (Flags.refactorInsetsController() && (i & WindowInsets.Type.ime()) != 0 && (insetsSourceConsumer = this.mImeSourceConsumer) != null && ((control = insetsSourceConsumer.getControl()) == null || control.getLeash() == null)) {
            ImeTracker.forLogging().onCancelled(token, 48);
        }
        updateCompatSysUiVisibility();
    }

    public void applyAnimation(int i, boolean z, boolean z2, boolean z3, ImeTracker.Token token) {
        InsetsSourceControl control;
        boolean z4 = false;
        if ((WindowInsets.Type.ime() & i) != 0 && (control = this.mImeSourceConsumer.getControl()) != null && control.getAndClearSkipAnimationOnce() && z && this.mImeSourceConsumer.hasViewFocusWhenWindowFocusGain()) {
            z4 = true;
        }
        applyAnimation(i, z, z2, z4, z3, token);
    }

    public void applyAnimation(int i, boolean z, boolean z2, boolean z3, boolean z4, ImeTracker.Token token) {
        boolean z5;
        if (i == 0) {
            if (DEBUG) {
                Log.d(TAG, "applyAnimation, nothing to animate. Stopping here");
            }
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            if (Flags.refactorInsetsController() || z2) {
                return;
            }
            Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            return;
        }
        boolean hasAnimationCallbacks = this.mHost.hasAnimationCallbacks();
        boolean isFullscreenModeAnim = (i & WindowInsets.Type.ime()) != 0 ? this.mHost.getInputMethodManager().isFullscreenModeAnim() : false;
        int size = this.mSourceConsumers.size() - 1;
        while (true) {
            if (size < 0) {
                z5 = z3;
                break;
            }
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0 && valueAt.getControl() != null) {
                InsetsSource orCreateSource = this.mState.getOrCreateSource(valueAt.getId(), valueAt.getType());
                int insetSide = InsetsSource.getInsetSide(valueAt.getControl().getInsetsHint());
                if (insetSide != 0 && orCreateSource.getSideHint() != 0 && insetSide != orCreateSource.getSideHint()) {
                    Log.d(TAG, "applyAnimation, skip insets animation, because hint of source is not equal to hint of control, source=" + orCreateSource + ", control=" + valueAt.getControl());
                    z5 = true;
                    break;
                }
            }
            size--;
        }
        if (!z5 && this.mHost.shouldIgnoreInsetsAnimation()) {
            z5 = true;
        }
        InternalAnimationControlListener internalAnimationControlListener = new InternalAnimationControlListener(z, hasAnimationCallbacks, i, this.mHost.getSystemBarsBehavior(), z5 || this.mAnimationsDisabled, this.mHost.dipToPx(-80), this.mLoggingListener, this.mJankContext, isFullscreenModeAnim);
        controlAnimationUnchecked(i, null, internalAnimationControlListener, null, z2, internalAnimationControlListener, !z ? 1 : 0, !z ? 1 : 0, !hasAnimationCallbacks || z4, token, false);
    }

    public void cancelExistingAnimations() {
        cancelExistingControllers(WindowInsets.Type.all());
    }

    void dump(String str, PrintWriter printWriter) {
        String str2 = str + "    ";
        printWriter.println(str + "InsetsController:");
        this.mState.dump(str2, printWriter);
        printWriter.println(str2 + "mIsPredictiveBackImeHideAnimInProgress=" + this.mIsPredictiveBackImeHideAnimInProgress);
    }

    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mState.dumpDebug(protoOutputStream, 1146756268033L);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            this.mRunningAnimations.get(size).runner.dumpDebug(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public <T extends InsetsAnimationControlRunner & InternalInsetsAnimationController> void startAnimation(final T t, final WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, final int i, final WindowInsetsAnimation windowInsetsAnimation, final WindowInsetsAnimation.Bounds bounds) {
        this.mHost.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
        this.mHost.addOnPreDrawRunnable(new Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                InsetsController.this.lambda$startAnimation$8(t, i, windowInsetsAnimation, bounds, windowInsetsAnimationControlListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$8(InsetsAnimationControlRunner insetsAnimationControlRunner, int i, WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        WindowInsetsAnimationController windowInsetsAnimationController = (WindowInsetsAnimationController) insetsAnimationControlRunner;
        if (windowInsetsAnimationController.isCancelled()) {
            return;
        }
        Trace.asyncTraceBegin(8L, "InsetsAnimation: " + WindowInsets.Type.toString(i), i);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            if (runningAnimation.runner == insetsAnimationControlRunner) {
                runningAnimation.startDispatched = true;
            }
        }
        Trace.asyncTraceEnd(8L, "IC.pendingAnim", 0);
        this.mHost.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
        this.mStartingAnimation = true;
        if (insetsAnimationControlRunner.getAnimationType() == 2) {
            ImeTracker.forLogging().onDispatched(insetsAnimationControlRunner.getStatsToken());
        }
        ((InternalInsetsAnimationController) insetsAnimationControlRunner).setReadyDispatched(true);
        windowInsetsAnimationControlListener.onReady(windowInsetsAnimationController, i);
        this.mStartingAnimation = false;
    }

    public void dispatchAnimationEnd(WindowInsetsAnimation windowInsetsAnimation) {
        Trace.asyncTraceEnd(8L, "InsetsAnimation: " + WindowInsets.Type.toString(windowInsetsAnimation.getTypeMask()), windowInsetsAnimation.getTypeMask());
        this.mHost.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void scheduleApplyChangeInsets(InsetsAnimationControlRunner insetsAnimationControlRunner) {
        if (this.mStartingAnimation || insetsAnimationControlRunner.getAnimationType() == 2) {
            this.mAnimCallback.run();
            this.mAnimCallbackScheduled = false;
        } else {
            if (this.mAnimCallbackScheduled) {
                return;
            }
            this.mHost.postInsetsAnimationCallback(this.mAnimCallback);
            this.mAnimCallbackScheduled = true;
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int i, int i2) {
        this.mAppearanceControlled |= i2;
        this.mHost.setSystemBarsAppearance(i, i2);
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearanceFromResource(int i, int i2) {
        this.mAppearanceFromResource = (this.mAppearanceFromResource & (~i2)) | (i & i2);
        this.mHost.setSystemBarsAppearance(i, (~this.mAppearanceControlled) & i2);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        int systemBarsAppearance = this.mHost.getSystemBarsAppearance();
        int i = this.mAppearanceControlled;
        return (this.mAppearanceFromResource & (~i)) | (systemBarsAppearance & i);
    }

    public int getAppearanceControlled() {
        return this.mAppearanceControlled;
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int i) {
        Rect rect = new Rect(this.mFrame.left, this.mFrame.bottom - i, this.mFrame.right, this.mFrame.bottom);
        InsetsSource peekSource = this.mState.peekSource(InsetsSource.ID_IME_CAPTION_BAR);
        if (this.mImeCaptionBarInsetsHeight == i && (peekSource == null || rect.equals(peekSource.getFrame()))) {
            return;
        }
        this.mImeCaptionBarInsetsHeight = i;
        if (i != 0) {
            this.mState.getOrCreateSource(InsetsSource.ID_IME_CAPTION_BAR, WindowInsets.Type.captionBar()).setFrame(rect);
            getSourceConsumer(InsetsSource.ID_IME_CAPTION_BAR, WindowInsets.Type.captionBar()).setControl(new InsetsSourceControl(InsetsSource.ID_IME_CAPTION_BAR, WindowInsets.Type.captionBar(), null, false, new Point(), Insets.NONE), new int[1], new int[1], new int[1], new int[1]);
        } else {
            this.mState.removeSource(InsetsSource.ID_IME_CAPTION_BAR);
            InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(InsetsSource.ID_IME_CAPTION_BAR);
            if (insetsSourceConsumer != null) {
                insetsSourceConsumer.setControl(null, new int[1], new int[1], new int[1], new int[1]);
            }
        }
        this.mHost.notifyInsetsChanged();
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int i) {
        this.mBehaviorControlled = true;
        this.mHost.setSystemBarsBehavior(i);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (this.mBehaviorControlled) {
            return this.mHost.getSystemBarsBehavior();
        }
        return 1;
    }

    public boolean isBehaviorControlled() {
        return this.mBehaviorControlled;
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean z) {
        this.mAnimationsDisabled = z;
    }

    private int calculateControllableTypes() {
        int i = 0;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            InsetsSource peekSource = this.mState.peekSource(valueAt.getId());
            if (valueAt.getControl() != null && peekSource != null) {
                i |= valueAt.getType();
            }
        }
        return (~this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame)) & i;
    }

    private int invokeControllableInsetsChangedListeners() {
        this.mLastStartedAnimTypes = 0;
        int calculateControllableTypes = calculateControllableTypes();
        int size = this.mControllableInsetsChangedListeners.size();
        for (int i = 0; i < size; i++) {
            this.mControllableInsetsChangedListeners.get(i).onControllableInsetsChanged(this, calculateControllableTypes);
        }
        return this.mLastStartedAnimTypes;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        Objects.requireNonNull(onControllableInsetsChangedListener);
        this.mControllableInsetsChangedListeners.add(onControllableInsetsChangedListener);
        onControllableInsetsChangedListener.onControllableInsetsChanged(this, calculateControllableTypes());
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        Objects.requireNonNull(onControllableInsetsChangedListener);
        this.mControllableInsetsChangedListeners.remove(onControllableInsetsChangedListener);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void releaseSurfaceControlFromRt(SurfaceControl surfaceControl) {
        this.mHost.releaseSurfaceControlFromRt(surfaceControl);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void reportPerceptible(int i, boolean z) {
        int size = this.mSourceConsumers.size();
        for (int i2 = 0; i2 < size; i2++) {
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(i2);
            if ((valueAt.getType() & i) != 0) {
                valueAt.onPerceptible(z);
            }
        }
    }

    public Host getHost() {
        return this.mHost;
    }

    public boolean hasImeOverriddenLocalVisibility() {
        InsetsSourceControl control;
        InsetsSourceConsumer insetsSourceConsumer = this.mImeSourceConsumer;
        if (Flags.refactorInsetsController() && insetsSourceConsumer != null && (control = insetsSourceConsumer.getControl()) != null && control.getLeash() == null) {
            InsetsSource orCreateSource = this.mState.getOrCreateSource(insetsSourceConsumer.getId(), insetsSourceConsumer.getType());
            InsetsSource orCreateSource2 = this.mLastDispatchedState.getOrCreateSource(insetsSourceConsumer.getId(), insetsSourceConsumer.getType());
            if (orCreateSource != null && orCreateSource2 != null && orCreateSource.isVisible() != orCreateSource2.isVisible()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPendingFrame() {
        int size = this.mSourceConsumers.size();
        for (int i = 0; i < size; i++) {
            InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(i);
            if (valueAt != null && valueAt.hasPendingFrame()) {
                return true;
            }
        }
        return false;
    }
}
