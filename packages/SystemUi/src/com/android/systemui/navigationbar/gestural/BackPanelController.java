package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import androidx.core.animation.Interpolator;
import androidx.core.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.gestural.BackPanel;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgePanelParams;
import com.android.systemui.navigationbar.gestural.Step;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.PrintWriter;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BackPanelController extends ViewController implements NavigationEdgeBackPlugin {
    public NavigationEdgeBackPlugin.BackCallback backCallback;
    public final ConfigurationController configurationController;
    public final BackPanelController$configurationListener$1 configurationListener;
    public GestureState currentState;
    public final Point displaySize;
    public float entryToActiveDelay;
    public final Function0 entryToActiveDelayCalculation;
    public final BackPanelController$failsafeRunnable$1 failsafeRunnable;
    public float fullyStretchedThreshold;
    public long gestureEntryTime;
    public long gestureInactiveTime;
    public boolean hasPassedDragSlop;
    public WindowManager.LayoutParams layoutParams;
    public final Handler mainHandler;
    public int minFlingDistance;
    public final DelayedOnAnimationEndListener onAlphaEndSetGoneStateListener;
    public final DelayedOnAnimationEndListener onEndSetCommittedStateListener;
    public final DelayedOnAnimationEndListener onEndSetGoneStateListener;
    public final EdgePanelParams params;
    public long pastThresholdWhileEntryOrInactiveTime;
    public Interpolator previousPreThresholdWidthInterpolator;
    public GestureState previousState;
    public float previousXTranslation;
    public float previousXTranslationOnActiveOffset;
    public float startX;
    public float startY;
    public float totalTouchDeltaActive;
    public float totalTouchDeltaInactive;
    public float touchDeltaStartX;
    public VelocityTracker velocityTracker;
    public final VibratorHelper vibratorHelper;
    public final ViewConfiguration viewConfiguration;
    public final WindowManager windowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DelayedOnAnimationEndListener implements DynamicAnimation.OnAnimationEndListener {
        public final Handler handler;
        public final Runnable runnable;
        public final long runnableDelay;

        public DelayedOnAnimationEndListener(Handler handler, long j, Runnable runnable) {
            this.handler = handler;
            this.runnableDelay = j;
            this.runnable = runnable;
        }

        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            dynamicAnimation.removeEndListener(this);
            if (z) {
                return;
            }
            BackPanelController backPanelController = BackPanelController.this;
            backPanelController.getClass();
            this.handler.postDelayed(this.runnable, Math.max(0L, this.runnableDelay - (SystemClock.uptimeMillis() - backPanelController.gestureEntryTime)));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final ConfigurationController configurationController;
        public final LatencyTracker latencyTracker;
        public final Handler mainHandler;
        public final VibratorHelper vibratorHelper;
        public final ViewConfiguration viewConfiguration;
        public final WindowManager windowManager;

        public Factory(WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker) {
            this.windowManager = windowManager;
            this.viewConfiguration = viewConfiguration;
            this.mainHandler = handler;
            this.vibratorHelper = vibratorHelper;
            this.configurationController = configurationController;
            this.latencyTracker = latencyTracker;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum GestureState {
        GONE,
        ENTRY,
        ACTIVE,
        INACTIVE,
        FLUNG,
        COMMITTED,
        CANCELLED
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureState.values().length];
            try {
                iArr[GestureState.ENTRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GestureState.INACTIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GestureState.ACTIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[GestureState.GONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[GestureState.FLUNG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[GestureState.COMMITTED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[GestureState.CANCELLED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1] */
    public BackPanelController(Context context, WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker) {
        super(BasicRune.NAVBAR_GESTURE ? new SamsungBackPanel(context, latencyTracker, (NavBarStore) Dependency.get(NavBarStore.class)) : new BackPanel(context, latencyTracker));
        this.windowManager = windowManager;
        this.viewConfiguration = viewConfiguration;
        this.mainHandler = handler;
        this.vibratorHelper = vibratorHelper;
        this.configurationController = configurationController;
        EdgePanelParams edgePanelParams = new EdgePanelParams(getResources());
        this.params = edgePanelParams;
        GestureState gestureState = GestureState.GONE;
        this.currentState = gestureState;
        this.previousState = gestureState;
        this.displaySize = new Point();
        this.entryToActiveDelayCalculation = new Function0() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$entryToActiveDelayCalculation$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                float f;
                BackPanelController backPanelController = BackPanelController.this;
                if (backPanelController.velocityTracker == null) {
                    backPanelController.velocityTracker = VelocityTracker.obtain();
                }
                VelocityTracker velocityTracker = backPanelController.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.computeCurrentVelocity(1);
                    f = MathUtils.smoothStep(0.5f, 1.0f, Math.abs(velocityTracker.getXVelocity()));
                } else {
                    f = 10.0f;
                }
                return Float.valueOf(MathUtils.lerp(10.0f, 100.0f, 1 - f));
            }
        };
        this.failsafeRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController backPanelController = BackPanelController.this;
                backPanelController.getClass();
                backPanelController.updateArrowState(BackPanelController.GestureState.GONE, true);
            }
        };
        this.onEndSetCommittedStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onEndSetCommittedStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController.this.updateArrowState(BackPanelController.GestureState.COMMITTED, false);
            }
        });
        this.onEndSetGoneStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onEndSetGoneStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController backPanelController = BackPanelController.this;
                backPanelController.mainHandler.removeCallbacks(backPanelController.failsafeRunnable);
                BackPanelController.this.updateArrowState(BackPanelController.GestureState.GONE, false);
            }
        });
        this.onAlphaEndSetGoneStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onAlphaEndSetGoneStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                BackPanelController.this.updateRestingArrowDimens();
                BackPanelController backPanelController = BackPanelController.this;
                BackPanel backPanel = (BackPanel) backPanelController.mView;
                BackPanel.AnimatedFloat animatedFloat = backPanel.backgroundAlpha;
                backPanel.getClass();
                SpringAnimation springAnimation = animatedFloat.animation;
                boolean z2 = springAnimation.mRunning;
                BackPanelController.DelayedOnAnimationEndListener delayedOnAnimationEndListener = backPanelController.onEndSetGoneStateListener;
                if (z2) {
                    springAnimation.addEndListener(delayedOnAnimationEndListener);
                    z = true;
                } else {
                    delayedOnAnimationEndListener.runnable.run();
                    z = false;
                }
                if (z) {
                    return;
                }
                BackPanelController backPanelController2 = BackPanelController.this;
                Handler handler2 = backPanelController2.mainHandler;
                BackPanelController$failsafeRunnable$1 backPanelController$failsafeRunnable$1 = backPanelController2.failsafeRunnable;
                handler2.removeCallbacks(backPanelController$failsafeRunnable$1);
                handler2.postDelayed(backPanelController$failsafeRunnable$1, 350L);
            }
        });
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                BackPanelController.this.updateConfiguration();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(boolean z) {
                BackPanel backPanel = (BackPanel) BackPanelController.this.mView;
                if (backPanel.arrowsPointLeft != z) {
                    backPanel.invalidate();
                    backPanel.arrowsPointLeft = z;
                }
            }
        };
        PathInterpolator pathInterpolator = edgePanelParams.entryWidthInterpolator;
        this.previousPreThresholdWidthInterpolator = pathInterpolator == null ? null : pathInterpolator;
    }

    public static boolean isFlungAwayFromEdge$default(BackPanelController backPanelController, float f) {
        float f2;
        float f3 = backPanelController.touchDeltaStartX;
        float f4 = ((BackPanel) backPanelController.mView).isLeftPanel ? f - f3 : f3 - f;
        if (backPanelController.velocityTracker == null) {
            backPanelController.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker = backPanelController.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            Float valueOf = Float.valueOf(velocityTracker.getXVelocity());
            valueOf.floatValue();
            if (!((BackPanel) backPanelController.mView).isLeftPanel) {
                valueOf = null;
            }
            f2 = valueOf != null ? valueOf.floatValue() : velocityTracker.getXVelocity() * (-1);
        } else {
            f2 = 0.0f;
        }
        return f4 > ((float) backPanelController.minFlingDistance) && ((f2 > ((float) backPanelController.viewConfiguration.getScaledMinimumFlingVelocity()) ? 1 : (f2 == ((float) backPanelController.viewConfiguration.getScaledMinimumFlingVelocity()) ? 0 : -1)) > 0);
    }

    public static boolean isPastThresholdToActive$default(BackPanelController backPanelController, boolean z, final Float f, Function0 function0, int i) {
        if ((i & 2) != 0) {
            f = null;
        }
        if ((i & 4) != 0) {
            function0 = new Function0() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$isPastThresholdToActive$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Float f2 = f;
                    return Float.valueOf(f2 != null ? f2.floatValue() : 0.0f);
                }
            };
        }
        boolean z2 = backPanelController.pastThresholdWhileEntryOrInactiveTime == 0;
        if (!z) {
            backPanelController.pastThresholdWhileEntryOrInactiveTime = 0L;
            return false;
        }
        if (z2) {
            backPanelController.pastThresholdWhileEntryOrInactiveTime = SystemClock.uptimeMillis();
            backPanelController.entryToActiveDelay = ((Number) function0.invoke()).floatValue();
        }
        return ((float) (SystemClock.uptimeMillis() - backPanelController.pastThresholdWhileEntryOrInactiveTime)) > backPanelController.entryToActiveDelay;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void dump(PrintWriter printWriter) {
        printWriter.println("BackPanelController:");
        printWriter.println("  currentState=" + this.currentState);
        printWriter.println("  isLeftPanel=" + this.mView + ".isLeftPanel");
    }

    @Override // com.android.systemui.plugins.Plugin
    public final void onDestroy() {
        this.mainHandler.removeCallbacks(this.failsafeRunnable);
        this.windowManager.removeView(this.mView);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void onMotionEvent(MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        boolean z;
        Step.Value value;
        Step.Value value2;
        Interpolator interpolator;
        Step.Value value3;
        VelocityTracker velocityTracker2;
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker3 = this.velocityTracker;
        Intrinsics.checkNotNull(velocityTracker3);
        velocityTracker3.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        EdgePanelParams edgePanelParams = this.params;
        Handler handler = this.mainHandler;
        if (actionMasked == 0) {
            handler.removeCallbacks(this.failsafeRunnable);
            Iterator it = ((BackPanel) this.mView).allAnimatedFloat.iterator();
            while (it.hasNext()) {
                ((BackPanel.AnimatedFloat) it.next()).animation.cancel();
            }
            handler.removeCallbacks(this.onEndSetCommittedStateListener.runnable);
            handler.removeCallbacks(this.onEndSetGoneStateListener.runnable);
            handler.removeCallbacks(this.onAlphaEndSetGoneStateListener.runnable);
            this.startX = motionEvent.getX();
            this.startY = motionEvent.getY();
            updateArrowState(GestureState.GONE, false);
            float max = Math.max(this.startY - edgePanelParams.fingerOffset, edgePanelParams.minArrowYPosition);
            WindowManager.LayoutParams layoutParams = this.layoutParams;
            (layoutParams == null ? null : layoutParams).y = MathUtils.constrain((int) (max - ((layoutParams == null ? null : layoutParams).height / 2.0f)), 0, this.displaySize.y);
            BackPanel backPanel = (BackPanel) this.mView;
            boolean z2 = backPanel.isLeftPanel;
            this.hasPassedDragSlop = false;
            backPanel.backgroundAlpha.snapTo(1.0f);
            backPanel.verticalTranslation.snapTo(0.0f);
            backPanel.scale.snapTo(1.0f);
            backPanel.horizontalTranslation.snapToRestingPosition();
            backPanel.arrowLength.snapToRestingPosition();
            backPanel.arrowHeight.snapToRestingPosition();
            backPanel.arrowAlpha.snapToRestingPosition();
            backPanel.backgroundWidth.snapToRestingPosition();
            backPanel.backgroundHeight.snapToRestingPosition();
            backPanel.backgroundEdgeCornerRadius.snapToRestingPosition();
            backPanel.backgroundFarCornerRadius.snapToRestingPosition();
            return;
        }
        if (actionMasked == 1) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
            VibratorHelper vibratorHelper = this.vibratorHelper;
            switch (i) {
                case 1:
                    if (!isFlungAwayFromEdge$default(this, motionEvent.getX()) && this.previousXTranslation <= edgePanelParams.staticTriggerThreshold) {
                        updateArrowState(GestureState.CANCELLED, false);
                        break;
                    } else {
                        updateArrowState(GestureState.FLUNG, false);
                        if (BasicRune.NAVBAR_GESTURE) {
                            vibratorHelper.cancel();
                            handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BackPanelController.this.vibratorHelper.vibrateGesture();
                                }
                            }, 10L);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (!isFlungAwayFromEdge$default(this, motionEvent.getX())) {
                        updateArrowState(GestureState.CANCELLED, false);
                        break;
                    } else {
                        NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
                        if (backCallback == null) {
                            backCallback = null;
                        }
                        backCallback.setTriggerBack(true);
                        if (BasicRune.NAVBAR_GESTURE) {
                            vibratorHelper.cancel();
                            handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BackPanelController.this.vibratorHelper.vibrateGesture();
                                }
                            }, 10L);
                        }
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$3
                            @Override // java.lang.Runnable
                            public final void run() {
                                BackPanelController.this.updateArrowState(BackPanelController.GestureState.FLUNG, false);
                            }
                        }, 50L);
                        break;
                    }
                case 3:
                    if (this.previousState == GestureState.ENTRY && SystemClock.uptimeMillis() - this.gestureEntryTime < 100) {
                        updateArrowState(GestureState.FLUNG, false);
                        break;
                    } else if (this.previousState == GestureState.INACTIVE && SystemClock.uptimeMillis() - this.gestureInactiveTime < 400) {
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$4
                            @Override // java.lang.Runnable
                            public final void run() {
                                BackPanelController.this.updateArrowState(BackPanelController.GestureState.COMMITTED, false);
                            }
                        }, 130L);
                        break;
                    } else {
                        updateArrowState(GestureState.COMMITTED, false);
                        break;
                    }
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    updateArrowState(GestureState.CANCELLED, false);
                    break;
            }
            if (!Intrinsics.areEqual(this.velocityTracker, null) && (velocityTracker = this.velocityTracker) != null) {
                velocityTracker.recycle();
            }
            this.velocityTracker = null;
            return;
        }
        if (actionMasked != 2) {
            if (actionMasked != 3) {
                return;
            }
            updateArrowState(GestureState.GONE, false);
            if (!Intrinsics.areEqual(this.velocityTracker, null) && (velocityTracker2 = this.velocityTracker) != null) {
                velocityTracker2.recycle();
            }
            this.velocityTracker = null;
            return;
        }
        float x = motionEvent.getX();
        float f = this.startX;
        boolean z3 = this.hasPassedDragSlop;
        ViewConfiguration viewConfiguration = this.viewConfiguration;
        if (z3) {
            z = true;
        } else {
            if (Math.abs(x - f) > viewConfiguration.getScaledEdgeSlop()) {
                updateArrowState(GestureState.ENTRY, false);
                View view = this.mView;
                WindowManager.LayoutParams layoutParams2 = this.layoutParams;
                if (layoutParams2 == null) {
                    layoutParams2 = null;
                }
                this.windowManager.updateViewLayout(view, layoutParams2);
                BackPanel backPanel2 = (BackPanel) this.mView;
                backPanel2.latencyTracker.onActionStart(15);
                backPanel2.trackingBackArrowLatency = true;
                this.hasPassedDragSlop = true;
            }
            z = this.hasPassedDragSlop;
        }
        if (z) {
            float x2 = motionEvent.getX();
            float y = motionEvent.getY() - this.startY;
            float abs = Math.abs(y);
            float max2 = Math.max(0.0f, ((BackPanel) this.mView).isLeftPanel ? x2 - this.startX : this.startX - x2);
            float f2 = max2 - this.previousXTranslation;
            this.previousXTranslation = max2;
            if (Math.abs(f2) > 0.0f) {
                boolean z4 = Math.signum(f2) == Math.signum(this.totalTouchDeltaActive);
                ClosedFloatRange closedFloatRange = edgePanelParams.dynamicTriggerThresholdRange;
                if (closedFloatRange == null) {
                    closedFloatRange = null;
                }
                Float valueOf = Float.valueOf(this.totalTouchDeltaActive);
                closedFloatRange.getClass();
                float floatValue = valueOf.floatValue();
                if (z4 || ((floatValue > closedFloatRange._start ? 1 : (floatValue == closedFloatRange._start ? 0 : -1)) >= 0 && (floatValue > closedFloatRange._endInclusive ? 1 : (floatValue == closedFloatRange._endInclusive ? 0 : -1)) <= 0)) {
                    this.totalTouchDeltaActive += f2;
                } else {
                    this.totalTouchDeltaActive = f2;
                    this.touchDeltaStartX = x2;
                }
                float f3 = -viewConfiguration.getScaledTouchSlop();
                float f4 = this.totalTouchDeltaInactive + f2;
                if (f4 >= f3) {
                    f3 = f4;
                }
                this.totalTouchDeltaInactive = f3;
            }
            boolean z5 = ((float) 2) * max2 >= abs;
            boolean z6 = max2 > edgePanelParams.staticTriggerThreshold;
            GestureState gestureState = this.currentState;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i2 = iArr[gestureState.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    if (isPastThresholdToActive$default(this, z6 && ((this.totalTouchDeltaInactive > edgePanelParams.reactivationTriggerThreshold ? 1 : (this.totalTouchDeltaInactive == edgePanelParams.reactivationTriggerThreshold ? 0 : -1)) >= 0) && z5, Float.valueOf(160.0f), null, 4)) {
                        updateArrowState(GestureState.ACTIVE, false);
                    }
                } else if (i2 == 3) {
                    boolean z7 = this.totalTouchDeltaActive <= (-edgePanelParams.deactivationTriggerThreshold);
                    boolean z8 = SystemClock.uptimeMillis() - this.gestureEntryTime > 300;
                    if ((!z5 || z7) && z8) {
                        updateArrowState(GestureState.INACTIVE, false);
                    }
                }
            } else if (isPastThresholdToActive$default(this, z6, null, this.entryToActiveDelayCalculation, 2)) {
                updateArrowState(GestureState.ACTIVE, false);
            }
            int i3 = iArr[this.currentState.ordinal()];
            Float valueOf2 = i3 != 1 ? i3 != 2 ? (i3 == 3 && !BasicRune.NAVBAR_GESTURE) ? Float.valueOf(MathUtils.saturate((max2 - this.previousXTranslationOnActiveOffset) / this.fullyStretchedThreshold)) : null : Float.valueOf(MathUtils.saturate(this.totalTouchDeltaInactive / edgePanelParams.reactivationTriggerThreshold)) : Float.valueOf(MathUtils.saturate(max2 / edgePanelParams.staticTriggerThreshold));
            if (valueOf2 != null) {
                valueOf2.floatValue();
                int i4 = iArr[this.currentState.ordinal()];
                if (i4 == 1) {
                    float floatValue2 = valueOf2.floatValue();
                    BackPanel backPanel3 = (BackPanel) this.mView;
                    Interpolator interpolator2 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator2 == null) {
                        interpolator2 = null;
                    }
                    float interpolation = interpolator2.getInterpolation(floatValue2);
                    PathInterpolator pathInterpolator = edgePanelParams.entryWidthInterpolator;
                    if (pathInterpolator == null) {
                        pathInterpolator = null;
                    }
                    float interpolation2 = pathInterpolator.getInterpolation(floatValue2);
                    PathInterpolator pathInterpolator2 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator2 == null) {
                        pathInterpolator2 = null;
                    }
                    float interpolation3 = pathInterpolator2.getInterpolation(floatValue2);
                    Step step = edgePanelParams.getEntryIndicator().arrowDimens.alphaInterpolator;
                    float floatValue3 = (step == null || (value2 = step.get(floatValue2)) == null) ? 0.0f : ((Number) value2.value).floatValue();
                    PathInterpolator pathInterpolator3 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator3 == null) {
                        pathInterpolator3 = null;
                    }
                    float interpolation4 = pathInterpolator3.getInterpolation(floatValue2);
                    PathInterpolator pathInterpolator4 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator4 == null) {
                        pathInterpolator4 = null;
                    }
                    backPanel3.setStretch(0.0f, interpolation, floatValue3, interpolation2, interpolation3, interpolation4, pathInterpolator4.getInterpolation(floatValue2), edgePanelParams.getPreThresholdIndicator());
                } else if (i4 == 2) {
                    float floatValue4 = valueOf2.floatValue();
                    BackPanel backPanel4 = (BackPanel) this.mView;
                    Interpolator interpolator3 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator3 == null) {
                        interpolator3 = null;
                    }
                    float interpolation5 = interpolator3.getInterpolation(floatValue4);
                    if (!(this.totalTouchDeltaInactive > ((float) viewConfiguration.getScaledTouchSlop()))) {
                        interpolator = this.previousPreThresholdWidthInterpolator;
                    } else if (this.totalTouchDeltaInactive <= 0.0f ? (interpolator = edgePanelParams.entryWidthTowardsEdgeInterpolator) == null : (interpolator = edgePanelParams.entryWidthInterpolator) == null) {
                        interpolator = null;
                    }
                    this.previousPreThresholdWidthInterpolator = interpolator;
                    float interpolation6 = interpolator.getInterpolation(floatValue4);
                    float f5 = interpolation6 < 0.0f ? 0.0f : interpolation6;
                    PathInterpolator pathInterpolator5 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator5 == null) {
                        pathInterpolator5 = null;
                    }
                    float interpolation7 = pathInterpolator5.getInterpolation(floatValue4);
                    Step step2 = edgePanelParams.getPreThresholdIndicator().arrowDimens.alphaInterpolator;
                    float floatValue5 = (step2 == null || (value3 = step2.get(floatValue4)) == null) ? 0.0f : ((Number) value3.value).floatValue();
                    PathInterpolator pathInterpolator6 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator6 == null) {
                        pathInterpolator6 = null;
                    }
                    float interpolation8 = pathInterpolator6.getInterpolation(floatValue4);
                    PathInterpolator pathInterpolator7 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator7 == null) {
                        pathInterpolator7 = null;
                    }
                    backPanel4.setStretch(0.0f, interpolation5, floatValue5, f5, interpolation7, interpolation8, pathInterpolator7.getInterpolation(floatValue4), edgePanelParams.getPreThresholdIndicator());
                } else if (i4 == 3) {
                    float floatValue6 = valueOf2.floatValue();
                    BackPanel backPanel5 = (BackPanel) this.mView;
                    PathInterpolator pathInterpolator8 = edgePanelParams.horizontalTranslationInterpolator;
                    if (pathInterpolator8 == null) {
                        pathInterpolator8 = null;
                    }
                    float interpolation9 = pathInterpolator8.getInterpolation(floatValue6);
                    Interpolator interpolator4 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator4 == null) {
                        interpolator4 = null;
                    }
                    float interpolation10 = interpolator4.getInterpolation(floatValue6);
                    PathInterpolator pathInterpolator9 = edgePanelParams.activeWidthInterpolator;
                    if (pathInterpolator9 == null) {
                        pathInterpolator9 = null;
                    }
                    float interpolation11 = pathInterpolator9.getInterpolation(floatValue6);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.fullyStretchedIndicator;
                    backPanel5.setStretch(interpolation9, interpolation10, 1.0f, interpolation11, 1.0f, 1.0f, 1.0f, backIndicatorDimens != null ? backIndicatorDimens : null);
                }
            }
            switch (iArr[this.currentState.ordinal()]) {
                case 1:
                case 2:
                    break;
                case 3:
                case 5:
                case 6:
                    valueOf2 = Float.valueOf(1.0f);
                    break;
                case 4:
                case 7:
                    valueOf2 = Float.valueOf(0.0f);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i5 = iArr[this.currentState.ordinal()];
            EdgePanelParams.BackIndicatorDimens preThresholdIndicator = i5 != 1 ? i5 != 2 ? i5 != 3 ? edgePanelParams.getPreThresholdIndicator() : edgePanelParams.getActiveIndicator() : edgePanelParams.getPreThresholdIndicator() : edgePanelParams.getEntryIndicator();
            if (valueOf2 != null) {
                float floatValue7 = valueOf2.floatValue();
                Step step3 = preThresholdIndicator.arrowDimens.alphaSpring;
                if (step3 != null && (value = step3.get(floatValue7)) != null) {
                    if (!value.isNewState) {
                        value = null;
                    }
                    if (value != null) {
                        BackPanel backPanel6 = (BackPanel) this.mView;
                        SpringForce springForce = (SpringForce) value.value;
                        BackPanel.AnimatedFloat animatedFloat = backPanel6.arrowAlpha;
                        Float valueOf3 = Float.valueOf(0.0f);
                        SpringAnimation springAnimation = animatedFloat.animation;
                        if (valueOf3 != null) {
                            float floatValue8 = valueOf3.floatValue();
                            springAnimation.cancel();
                            springAnimation.mVelocity = floatValue8;
                        }
                        if (springForce != null) {
                            springAnimation.mSpring = springForce;
                        }
                        springAnimation.animateToFinalPosition(animatedFloat.restingPosition + 0.0f);
                    }
                }
            }
            float abs2 = Math.abs(y);
            float height = (((BackPanel) this.mView).getHeight() - edgePanelParams.getEntryIndicator().backgroundDimens.height) / 2.0f;
            float saturate = MathUtils.saturate(abs2 / (15.0f * height));
            PathInterpolator pathInterpolator10 = edgePanelParams.verticalTranslationInterpolator;
            if (pathInterpolator10 == null) {
                pathInterpolator10 = null;
            }
            BackPanel.AnimatedFloat.stretchTo$default(((BackPanel) this.mView).verticalTranslation, Math.signum(y) * pathInterpolator10.getInterpolation(saturate) * height, null, 6);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        updateConfiguration();
        ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.configurationController;
        boolean isLayoutRtl = configurationControllerImpl.isLayoutRtl();
        BackPanel backPanel = (BackPanel) this.mView;
        if (backPanel.arrowsPointLeft != isLayoutRtl) {
            backPanel.invalidate();
            backPanel.arrowsPointLeft = isLayoutRtl;
        }
        updateArrowState(GestureState.GONE, true);
        updateRestingArrowDimens();
        configurationControllerImpl.addCallback(this.configurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
    }

    public final void playWithBackgroundWidthAnimation(final DelayedOnAnimationEndListener delayedOnAnimationEndListener, long j) {
        boolean z;
        Handler handler = this.mainHandler;
        if (j != 0) {
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$playWithBackgroundWidthAnimation$$inlined$postDelayed$default$1
                @Override // java.lang.Runnable
                public final void run() {
                    BackPanelController.this.playWithBackgroundWidthAnimation(delayedOnAnimationEndListener, 0L);
                }
            }, j);
            return;
        }
        updateRestingArrowDimens();
        BackPanel backPanel = (BackPanel) this.mView;
        BackPanel.AnimatedFloat animatedFloat = backPanel.backgroundWidth;
        backPanel.getClass();
        SpringAnimation springAnimation = animatedFloat.animation;
        if (springAnimation.mRunning) {
            springAnimation.addEndListener(delayedOnAnimationEndListener);
            z = true;
        } else {
            delayedOnAnimationEndListener.runnable.run();
            z = false;
        }
        if (z) {
            return;
        }
        BackPanelController$failsafeRunnable$1 backPanelController$failsafeRunnable$1 = this.failsafeRunnable;
        handler.removeCallbacks(backPanelController$failsafeRunnable$1);
        handler.postDelayed(backPanelController$failsafeRunnable$1, 350L);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setBackCallback(NavigationEdgeBackPlugin.BackCallback backCallback) {
        this.backCallback = backCallback;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setDisplaySize(Point point) {
        this.displaySize.set(point.x, point.y);
        this.fullyStretchedThreshold = Math.min(point.x, this.params.swipeProgressThreshold);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setIsLeftPanel(boolean z) {
        ((BackPanel) this.mView).isLeftPanel = z;
        WindowManager.LayoutParams layoutParams = this.layoutParams;
        if (layoutParams == null) {
            layoutParams = null;
        }
        layoutParams.gravity = z ? 51 : 53;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        this.windowManager.addView(this.mView, layoutParams);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        EdgePanelParams edgePanelParams = this.params;
        edgePanelParams.getClass();
        if (f < 0.0f || f2 < 0.0f || f2 > 1.0f) {
            f = 1000.0f;
            f2 = 0.8f;
        }
        edgePanelParams.activeIndicator = EdgePanelParams.BackIndicatorDimens.copy$default(edgePanelParams.getActiveIndicator(), 0.0f, null, null, EdgePanelParamsKt.createSpring(f, f2), null, 191);
    }

    public final void updateArrowState(GestureState gestureState, boolean z) {
        Step.Value value;
        if (z || this.currentState != gestureState) {
            this.previousState = this.currentState;
            this.currentState = gestureState;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i = iArr[gestureState.ordinal()];
            SpringForce springForce = null;
            if (i == 1 || i == 2) {
                NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
                if (backCallback == null) {
                    backCallback = null;
                }
                backCallback.setTriggerBack(false);
            } else if (i == 3) {
                NavigationEdgeBackPlugin.BackCallback backCallback2 = this.backCallback;
                if (backCallback2 == null) {
                    backCallback2 = null;
                }
                backCallback2.setTriggerBack(true);
            } else if (i == 5 || i == 6) {
                if (this.previousState != GestureState.FLUNG) {
                    NavigationEdgeBackPlugin.BackCallback backCallback3 = this.backCallback;
                    if (backCallback3 == null) {
                        backCallback3 = null;
                    }
                    backCallback3.triggerBack();
                }
            } else if (i == 7) {
                NavigationEdgeBackPlugin.BackCallback backCallback4 = this.backCallback;
                if (backCallback4 == null) {
                    backCallback4 = null;
                }
                backCallback4.cancelBack();
            }
            int i2 = iArr[this.currentState.ordinal()];
            DelayedOnAnimationEndListener delayedOnAnimationEndListener = this.onEndSetGoneStateListener;
            VibratorHelper vibratorHelper = this.vibratorHelper;
            EdgePanelParams edgePanelParams = this.params;
            Handler handler = this.mainHandler;
            switch (i2) {
                case 1:
                    this.mView.setVisibility(0);
                    updateRestingArrowDimens();
                    this.gestureEntryTime = SystemClock.uptimeMillis();
                    break;
                case 2:
                    this.gestureInactiveTime = SystemClock.uptimeMillis();
                    this.totalTouchDeltaInactive = -edgePanelParams.deactivationTriggerThreshold;
                    ((BackPanel) this.mView).popOffEdge(-1.5f);
                    if (!BasicRune.NAVBAR_GESTURE) {
                        vibratorHelper.vibrate(BackPanelControllerKt.VIBRATE_DEACTIVATED_EFFECT);
                    }
                    updateRestingArrowDimens();
                    break;
                case 3:
                    this.previousXTranslationOnActiveOffset = this.previousXTranslation;
                    updateRestingArrowDimens();
                    vibratorHelper.cancel();
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (BasicRune.NAVBAR_GESTURE) {
                                BackPanelController.this.vibratorHelper.vibrateGesture();
                            } else {
                                BackPanelController.this.vibratorHelper.vibrate(BackPanelControllerKt.VIBRATE_ACTIVATED_EFFECT);
                            }
                        }
                    }, 10L);
                    ((BackPanel) this.mView).popOffEdge(this.previousState == GestureState.INACTIVE ? 4.7f : 4.5f);
                    break;
                case 4:
                    updateRestingArrowDimens();
                    this.mView.setVisibility(8);
                    break;
                case 5:
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackPanel backPanel = (BackPanel) BackPanelController.this.mView;
                            backPanel.scalePivotX.snapTo(backPanel.backgroundWidth.pos / 2);
                            BackPanel.AnimatedFloat.stretchTo$default(backPanel.scale, 0.0f, Float.valueOf(2.0f), 4);
                        }
                    }, 60L);
                    updateRestingArrowDimens();
                    handler.postDelayed(this.onEndSetCommittedStateListener.runnable, 160L);
                    break;
                case 6:
                    if (this.previousState != GestureState.FLUNG) {
                        BackPanel backPanel = (BackPanel) this.mView;
                        backPanel.scalePivotX.snapTo(backPanel.backgroundWidth.pos / 2);
                        BackPanel.AnimatedFloat.stretchTo$default(backPanel.scale, 0.0f, Float.valueOf(3.0f), 4);
                        handler.postDelayed(this.onAlphaEndSetGoneStateListener.runnable, 80L);
                        break;
                    } else {
                        updateRestingArrowDimens();
                        handler.postDelayed(delayedOnAnimationEndListener.runnable, 120L);
                        break;
                    }
                case 7:
                    playWithBackgroundWidthAnimation(delayedOnAnimationEndListener, Math.max(0L, 200 - (SystemClock.uptimeMillis() - this.gestureEntryTime)));
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.cancelledIndicator;
                    if (backIndicatorDimens == null) {
                        backIndicatorDimens = null;
                    }
                    Step step = backIndicatorDimens.arrowDimens.alphaSpring;
                    if (step != null && (value = step.get(0.0f)) != null) {
                        springForce = (SpringForce) value.value;
                    }
                    BackPanel.AnimatedFloat animatedFloat = ((BackPanel) this.mView).arrowAlpha;
                    Float valueOf = Float.valueOf(0.0f);
                    SpringAnimation springAnimation = animatedFloat.animation;
                    if (valueOf != null) {
                        float floatValue = valueOf.floatValue();
                        springAnimation.cancel();
                        springAnimation.mVelocity = floatValue;
                    }
                    if (springForce != null) {
                        springAnimation.mSpring = springForce;
                    }
                    springAnimation.animateToFinalPosition(animatedFloat.restingPosition + 0.0f);
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackPanelController.this.vibratorHelper.cancel();
                        }
                    }, 10L);
                    break;
            }
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        View view = this.mView;
        if (view == null) {
            Log.d("BackPanelController", "updateBackPanelColor fail, view is null");
        } else {
            ((BackPanel) view).mo168x7d33eef5(i, i2, i3, i4);
            updateConfiguration();
        }
    }

    public final void updateConfiguration() {
        Resources resources = getResources();
        EdgePanelParams edgePanelParams = this.params;
        edgePanelParams.update(resources);
        ((BackPanel) this.mView).mo167x4d69e1c4(edgePanelParams.arrowThickness);
        this.minFlingDistance = this.viewConfiguration.getScaledTouchSlop() * 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x027f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRestingArrowDimens() {
        Float f;
        Float f2;
        Float f3;
        EdgePanelParams.ArrowDimens arrowDimens;
        EdgePanelParams.BackgroundDimens backgroundDimens;
        GestureState gestureState = this.currentState;
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i = iArr[gestureState.ordinal()];
        EdgePanelParams edgePanelParams = this.params;
        switch (i) {
            case 1:
            case 4:
                BackPanel backPanel = (BackPanel) this.mView;
                SpringForce springForce = edgePanelParams.getEntryIndicator().arrowDimens.lengthSpring;
                SpringForce springForce2 = edgePanelParams.getEntryIndicator().arrowDimens.heightSpring;
                BackPanel.setSpring$default(backPanel, edgePanelParams.getEntryIndicator().horizontalTranslationSpring, edgePanelParams.getEntryIndicator().verticalTranslationSpring, edgePanelParams.getEntryIndicator().scaleSpring, springForce, springForce2, edgePanelParams.getEntryIndicator().backgroundDimens.alphaSpring, edgePanelParams.getEntryIndicator().backgroundDimens.farCornerRadiusSpring, edgePanelParams.getEntryIndicator().backgroundDimens.edgeCornerRadiusSpring, edgePanelParams.getEntryIndicator().backgroundDimens.widthSpring, edgePanelParams.getEntryIndicator().backgroundDimens.heightSpring, 32);
                break;
            case 2:
                BackPanel.setSpring$default((BackPanel) this.mView, edgePanelParams.getPreThresholdIndicator().horizontalTranslationSpring, null, edgePanelParams.getPreThresholdIndicator().scaleSpring, edgePanelParams.getPreThresholdIndicator().arrowDimens.lengthSpring, edgePanelParams.getPreThresholdIndicator().arrowDimens.heightSpring, null, edgePanelParams.getPreThresholdIndicator().backgroundDimens.farCornerRadiusSpring, edgePanelParams.getPreThresholdIndicator().backgroundDimens.edgeCornerRadiusSpring, edgePanelParams.getPreThresholdIndicator().backgroundDimens.widthSpring, edgePanelParams.getPreThresholdIndicator().backgroundDimens.heightSpring, 98);
                break;
            case 3:
                BackPanel.setSpring$default((BackPanel) this.mView, edgePanelParams.getActiveIndicator().horizontalTranslationSpring, null, edgePanelParams.getActiveIndicator().scaleSpring, edgePanelParams.getActiveIndicator().arrowDimens.lengthSpring, edgePanelParams.getActiveIndicator().arrowDimens.heightSpring, null, edgePanelParams.getActiveIndicator().backgroundDimens.farCornerRadiusSpring, edgePanelParams.getActiveIndicator().backgroundDimens.edgeCornerRadiusSpring, edgePanelParams.getActiveIndicator().backgroundDimens.widthSpring, edgePanelParams.getActiveIndicator().backgroundDimens.heightSpring, 98);
                break;
            case 5:
                BackPanel.setSpring$default((BackPanel) this.mView, null, null, null, edgePanelParams.getFlungIndicator().arrowDimens.lengthSpring, edgePanelParams.getFlungIndicator().arrowDimens.heightSpring, null, edgePanelParams.getFlungIndicator().backgroundDimens.farCornerRadiusSpring, edgePanelParams.getFlungIndicator().backgroundDimens.edgeCornerRadiusSpring, edgePanelParams.getFlungIndicator().backgroundDimens.widthSpring, edgePanelParams.getFlungIndicator().backgroundDimens.heightSpring, 103);
                break;
            case 6:
                BackPanel.setSpring$default((BackPanel) this.mView, null, null, edgePanelParams.getCommittedIndicator().scaleSpring, edgePanelParams.getCommittedIndicator().arrowDimens.lengthSpring, edgePanelParams.getCommittedIndicator().arrowDimens.heightSpring, edgePanelParams.getCommittedIndicator().backgroundDimens.alphaSpring, edgePanelParams.getCommittedIndicator().backgroundDimens.farCornerRadiusSpring, edgePanelParams.getCommittedIndicator().backgroundDimens.edgeCornerRadiusSpring, edgePanelParams.getCommittedIndicator().backgroundDimens.widthSpring, edgePanelParams.getCommittedIndicator().backgroundDimens.heightSpring, 35);
                break;
            case 7:
                BackPanel backPanel2 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.cancelledIndicator;
                if (backIndicatorDimens == null) {
                    backIndicatorDimens = null;
                }
                BackPanel.setSpring$default(backPanel2, null, null, null, null, null, backIndicatorDimens.backgroundDimens.alphaSpring, null, null, null, null, 1983);
                break;
        }
        BackPanel backPanel3 = (BackPanel) this.mView;
        GestureState gestureState2 = this.currentState;
        boolean z = (gestureState2 == GestureState.FLUNG || gestureState2 == GestureState.COMMITTED) ? false : true;
        int i2 = iArr[gestureState2.ordinal()];
        float f4 = (i2 == 3 || i2 == 5) ? edgePanelParams.getActiveIndicator().scale : i2 != 6 ? edgePanelParams.getPreThresholdIndicator().scale : edgePanelParams.getCommittedIndicator().scale;
        switch (iArr[this.currentState.ordinal()]) {
            case 1:
            case 2:
            case 4:
            case 7:
                f = edgePanelParams.getPreThresholdIndicator().scalePivotX;
                break;
            case 3:
                f = edgePanelParams.getActiveIndicator().scalePivotX;
                break;
            case 5:
            case 6:
                f = edgePanelParams.getCommittedIndicator().scalePivotX;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Float f5 = f;
        int i3 = iArr[this.currentState.ordinal()];
        if (i3 == 1 || i3 == 2) {
            f2 = edgePanelParams.getEntryIndicator().horizontalTranslation;
        } else {
            if (i3 != 3) {
                if (i3 == 4) {
                    Float f6 = edgePanelParams.getActiveIndicator().backgroundDimens.width;
                    if (f6 != null) {
                        f2 = Float.valueOf(f6.floatValue() * (-1));
                    }
                    f3 = null;
                } else if (i3 != 5) {
                    if (i3 == 7) {
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens2 = edgePanelParams.cancelledIndicator;
                        if (backIndicatorDimens2 == null) {
                            backIndicatorDimens2 = null;
                        }
                        f2 = backIndicatorDimens2.horizontalTranslation;
                    }
                    f3 = null;
                } else {
                    f2 = edgePanelParams.getActiveIndicator().horizontalTranslation;
                }
                switch (iArr[this.currentState.ordinal()]) {
                    case 1:
                    case 2:
                    case 4:
                        arrowDimens = edgePanelParams.getEntryIndicator().arrowDimens;
                        break;
                    case 3:
                        arrowDimens = edgePanelParams.getActiveIndicator().arrowDimens;
                        break;
                    case 5:
                        arrowDimens = edgePanelParams.getFlungIndicator().arrowDimens;
                        break;
                    case 6:
                        arrowDimens = edgePanelParams.getCommittedIndicator().arrowDimens;
                        break;
                    case 7:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens3 = edgePanelParams.cancelledIndicator;
                        if (backIndicatorDimens3 == null) {
                            backIndicatorDimens3 = null;
                        }
                        arrowDimens = backIndicatorDimens3.arrowDimens;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                EdgePanelParams.ArrowDimens arrowDimens2 = arrowDimens;
                switch (iArr[this.currentState.ordinal()]) {
                    case 1:
                    case 2:
                    case 4:
                        backgroundDimens = edgePanelParams.getEntryIndicator().backgroundDimens;
                        break;
                    case 3:
                        backgroundDimens = edgePanelParams.getActiveIndicator().backgroundDimens;
                        break;
                    case 5:
                        backgroundDimens = edgePanelParams.getActiveIndicator().backgroundDimens;
                        break;
                    case 6:
                        backgroundDimens = edgePanelParams.getCommittedIndicator().backgroundDimens;
                        break;
                    case 7:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens4 = edgePanelParams.cancelledIndicator;
                        backgroundDimens = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).backgroundDimens;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens5 = new EdgePanelParams.BackIndicatorDimens(f3, f4, f5, arrowDimens2, backgroundDimens, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
                backPanel3.horizontalTranslation.updateRestingPosition(backIndicatorDimens5.horizontalTranslation, true);
                backPanel3.scale.updateRestingPosition(Float.valueOf(backIndicatorDimens5.scale), true);
                BackPanel.AnimatedFloat animatedFloat = backPanel3.backgroundAlpha;
                EdgePanelParams.BackgroundDimens backgroundDimens2 = backIndicatorDimens5.backgroundDimens;
                animatedFloat.updateRestingPosition(Float.valueOf(backgroundDimens2.alpha), true);
                BackPanel.AnimatedFloat animatedFloat2 = backPanel3.arrowAlpha;
                EdgePanelParams.ArrowDimens arrowDimens3 = backIndicatorDimens5.arrowDimens;
                animatedFloat2.updateRestingPosition(Float.valueOf(arrowDimens3.alpha), z);
                backPanel3.arrowLength.updateRestingPosition(arrowDimens3.length, z);
                backPanel3.arrowHeight.updateRestingPosition(arrowDimens3.height, z);
                backPanel3.scalePivotX.updateRestingPosition(backIndicatorDimens5.scalePivotX, z);
                backPanel3.backgroundWidth.updateRestingPosition(backgroundDimens2.width, z);
                backPanel3.backgroundHeight.updateRestingPosition(Float.valueOf(backgroundDimens2.height), z);
                backPanel3.backgroundEdgeCornerRadius.updateRestingPosition(Float.valueOf(backgroundDimens2.edgeCornerRadius), z);
                backPanel3.backgroundFarCornerRadius.updateRestingPosition(Float.valueOf(backgroundDimens2.farCornerRadius), z);
            }
            f2 = edgePanelParams.getActiveIndicator().horizontalTranslation;
        }
        f3 = f2;
        switch (iArr[this.currentState.ordinal()]) {
        }
        EdgePanelParams.ArrowDimens arrowDimens22 = arrowDimens;
        switch (iArr[this.currentState.ordinal()]) {
        }
        EdgePanelParams.BackIndicatorDimens backIndicatorDimens52 = new EdgePanelParams.BackIndicatorDimens(f3, f4, f5, arrowDimens22, backgroundDimens, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        backPanel3.horizontalTranslation.updateRestingPosition(backIndicatorDimens52.horizontalTranslation, true);
        backPanel3.scale.updateRestingPosition(Float.valueOf(backIndicatorDimens52.scale), true);
        BackPanel.AnimatedFloat animatedFloat3 = backPanel3.backgroundAlpha;
        EdgePanelParams.BackgroundDimens backgroundDimens22 = backIndicatorDimens52.backgroundDimens;
        animatedFloat3.updateRestingPosition(Float.valueOf(backgroundDimens22.alpha), true);
        BackPanel.AnimatedFloat animatedFloat22 = backPanel3.arrowAlpha;
        EdgePanelParams.ArrowDimens arrowDimens32 = backIndicatorDimens52.arrowDimens;
        animatedFloat22.updateRestingPosition(Float.valueOf(arrowDimens32.alpha), z);
        backPanel3.arrowLength.updateRestingPosition(arrowDimens32.length, z);
        backPanel3.arrowHeight.updateRestingPosition(arrowDimens32.height, z);
        backPanel3.scalePivotX.updateRestingPosition(backIndicatorDimens52.scalePivotX, z);
        backPanel3.backgroundWidth.updateRestingPosition(backgroundDimens22.width, z);
        backPanel3.backgroundHeight.updateRestingPosition(Float.valueOf(backgroundDimens22.height), z);
        backPanel3.backgroundEdgeCornerRadius.updateRestingPosition(Float.valueOf(backgroundDimens22.edgeCornerRadius), z);
        backPanel3.backgroundFarCornerRadius.updateRestingPosition(Float.valueOf(backgroundDimens22.farCornerRadius), z);
    }

    /* renamed from: getCurrentState$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m169x268685d4() {
    }

    /* renamed from: getParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m170xefde6942() {
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setInsets(int i, int i2) {
    }
}
