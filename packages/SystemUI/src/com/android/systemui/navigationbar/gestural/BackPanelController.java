package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
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
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.PrintWriter;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;

/* loaded from: classes2.dex */
public final class BackPanelController extends ViewController implements NavigationEdgeBackPlugin {
    public NavigationEdgeBackPlugin.BackCallback backCallback;
    public final ConfigurationController configurationController;
    public final BackPanelController$configurationListener$1 configurationListener;
    public GestureState currentState;
    public final Point displaySize;
    public float entryToActiveDelay;
    public final BackPanelController$$ExternalSyntheticLambda0 entryToActiveDelayCalculation;
    public final BackPanelController$failsafeRunnable$1 failsafeRunnable;
    public float fullyStretchedThreshold;
    public long gestureEntryTime;
    public long gestureInactiveTime;
    public boolean hasPassedDragSlop;
    public final InteractionJankMonitor interactionJankMonitor;
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
    public final SystemClock systemClock;
    public float totalTouchDeltaActive;
    public float totalTouchDeltaInactive;
    public float touchDeltaStartX;
    public VelocityTracker velocityTracker;
    public final VibratorHelper vibratorHelper;
    public final ViewConfiguration viewConfiguration;
    public final WindowManager windowManager;

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
            this.handler.postDelayed(this.runnable, Math.max(0L, this.runnableDelay - (backPanelController.systemClock.uptimeMillis() - backPanelController.gestureEntryTime)));
        }
    }

    public interface Factory {
        BackPanelController create(Context context, WindowManager windowManager, Handler handler);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class GestureState {
        public static final /* synthetic */ GestureState[] $VALUES;
        public static final GestureState ACTIVE;
        public static final GestureState CANCELLED;
        public static final GestureState COMMITTED;
        public static final GestureState ENTRY;
        public static final GestureState FLUNG;
        public static final GestureState GONE;
        public static final GestureState INACTIVE;

        static {
            GestureState gestureState = new GestureState("GONE", 0);
            GONE = gestureState;
            GestureState gestureState2 = new GestureState("ENTRY", 1);
            ENTRY = gestureState2;
            GestureState gestureState3 = new GestureState("ACTIVE", 2);
            ACTIVE = gestureState3;
            GestureState gestureState4 = new GestureState("INACTIVE", 3);
            INACTIVE = gestureState4;
            GestureState gestureState5 = new GestureState("FLUNG", 4);
            FLUNG = gestureState5;
            GestureState gestureState6 = new GestureState("COMMITTED", 5);
            COMMITTED = gestureState6;
            GestureState gestureState7 = new GestureState("CANCELLED", 6);
            CANCELLED = gestureState7;
            GestureState[] gestureStateArr = {gestureState, gestureState2, gestureState3, gestureState4, gestureState5, gestureState6, gestureState7};
            $VALUES = gestureStateArr;
            EnumEntriesKt.enumEntries(gestureStateArr);
        }

        private GestureState(String str, int i) {
        }

        public static GestureState valueOf(String str) {
            return (GestureState) Enum.valueOf(GestureState.class, str);
        }

        public static GestureState[] values() {
            return (GestureState[]) $VALUES.clone();
        }
    }

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

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1] */
    public BackPanelController(Context context, WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, SystemClock systemClock, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker, InteractionJankMonitor interactionJankMonitor) {
        super(BasicRune.NAVBAR_GESTURE ? new SamsungBackPanel(context, latencyTracker, (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class)) : new BackPanel(context, latencyTracker));
        this.windowManager = windowManager;
        this.viewConfiguration = viewConfiguration;
        this.mainHandler = handler;
        this.systemClock = systemClock;
        this.vibratorHelper = vibratorHelper;
        this.configurationController = configurationController;
        this.interactionJankMonitor = interactionJankMonitor;
        EdgePanelParams edgePanelParams = new EdgePanelParams(getResources());
        this.params = edgePanelParams;
        GestureState gestureState = GestureState.GONE;
        this.currentState = gestureState;
        this.previousState = gestureState;
        this.displaySize = new Point();
        this.entryToActiveDelayCalculation = new BackPanelController$$ExternalSyntheticLambda0(this, 1);
        this.failsafeRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController backPanelController = this.this$0;
                backPanelController.getClass();
                backPanelController.updateArrowState(BackPanelController.GestureState.GONE, true);
            }
        };
        this.onEndSetCommittedStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onEndSetCommittedStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.updateArrowState(BackPanelController.GestureState.COMMITTED, false);
            }
        });
        this.onEndSetGoneStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onEndSetGoneStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController backPanelController = this.this$0;
                backPanelController.mainHandler.removeCallbacks(backPanelController.failsafeRunnable);
                this.this$0.updateArrowState(BackPanelController.GestureState.GONE, false);
            }
        });
        this.onAlphaEndSetGoneStateListener = new DelayedOnAnimationEndListener(handler, 0L, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onAlphaEndSetGoneStateListener$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.updateRestingArrowDimens();
                BackPanel backPanel = (BackPanel) ((ViewController) this.this$0).mView;
                BackPanel.AnimatedFloat animatedFloat = ((BackPanel) ((ViewController) this.this$0).mView).backgroundAlpha;
                BackPanelController.DelayedOnAnimationEndListener delayedOnAnimationEndListener = this.this$0.onEndSetGoneStateListener;
                backPanel.getClass();
                SpringAnimation springAnimation = animatedFloat.animation;
                if (springAnimation.mRunning) {
                    springAnimation.addEndListener(delayedOnAnimationEndListener);
                    return;
                }
                delayedOnAnimationEndListener.runnable.run();
                BackPanelController backPanelController = this.this$0;
                Handler handler2 = backPanelController.mainHandler;
                BackPanelController$failsafeRunnable$1 backPanelController$failsafeRunnable$1 = backPanelController.failsafeRunnable;
                handler2.removeCallbacks(backPanelController$failsafeRunnable$1);
                handler2.postDelayed(backPanelController$failsafeRunnable$1, 350L);
            }
        });
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
                this.this$0.updateConfiguration$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(boolean z) {
                this.this$0.updateArrowDirection(z);
            }
        };
        PathInterpolator pathInterpolator = edgePanelParams.entryWidthInterpolator;
        this.previousPreThresholdWidthInterpolator = pathInterpolator == null ? null : pathInterpolator;
    }

    public static boolean isFlungAwayFromEdge$default(BackPanelController backPanelController, float f) {
        float fFloatValue;
        float f2 = backPanelController.touchDeltaStartX;
        float f3 = ((BackPanel) backPanelController.mView).isLeftPanel ? f - f2 : f2 - f;
        if (backPanelController.velocityTracker == null) {
            backPanelController.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker = backPanelController.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            Float fValueOf = Float.valueOf(velocityTracker.getXVelocity());
            if (!((BackPanel) backPanelController.mView).isLeftPanel) {
                fValueOf = null;
            }
            fFloatValue = fValueOf != null ? fValueOf.floatValue() : velocityTracker.getXVelocity() * (-1);
        } else {
            fFloatValue = 0.0f;
        }
        return f3 > ((float) backPanelController.minFlingDistance) && ((fFloatValue > ((float) backPanelController.viewConfiguration.getScaledMinimumFlingVelocity()) ? 1 : (fFloatValue == ((float) backPanelController.viewConfiguration.getScaledMinimumFlingVelocity()) ? 0 : -1)) > 0);
    }

    public static boolean isPastThresholdToActive$default(BackPanelController backPanelController, boolean z, BackPanelController$$ExternalSyntheticLambda0 backPanelController$$ExternalSyntheticLambda0, int i) {
        Float fValueOf = Float.valueOf(160.0f);
        if ((i & 2) != 0) {
            fValueOf = null;
        }
        if ((i & 4) != 0) {
            backPanelController$$ExternalSyntheticLambda0 = new BackPanelController$$ExternalSyntheticLambda0(fValueOf, 0);
        }
        boolean z2 = backPanelController.pastThresholdWhileEntryOrInactiveTime == 0;
        if (!z) {
            backPanelController.pastThresholdWhileEntryOrInactiveTime = 0L;
            return false;
        }
        SystemClock systemClock = backPanelController.systemClock;
        if (z2) {
            backPanelController.pastThresholdWhileEntryOrInactiveTime = systemClock.uptimeMillis();
            backPanelController.entryToActiveDelay = ((Number) backPanelController$$ExternalSyntheticLambda0.invoke()).floatValue();
        }
        return ((float) (systemClock.uptimeMillis() - backPanelController.pastThresholdWhileEntryOrInactiveTime)) > backPanelController.entryToActiveDelay;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void dump(PrintWriter printWriter) {
        printWriter.println("BackPanelController:");
        printWriter.println("  currentState=" + this.currentState);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isLeftPanel=", ((BackPanel) this.mView).isLeftPanel);
    }

    public final BackPanel getBackPanelView$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return (BackPanel) this.mView;
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
        float f;
        float f2;
        float f3;
        EdgePanelParams.BackIndicatorDimens backIndicatorDimens;
        Step.Value value;
        Step.Value value2;
        Interpolator interpolator;
        Step.Value value3;
        VelocityTracker velocityTracker2;
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker3 = this.velocityTracker;
        velocityTracker3.getClass();
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
            float fMax = Math.max(this.startY - edgePanelParams.fingerOffset, edgePanelParams.minArrowYPosition);
            WindowManager.LayoutParams layoutParams = this.layoutParams;
            (layoutParams != null ? layoutParams : null).y = MathUtils.constrain((int) (fMax - ((layoutParams == null ? null : layoutParams).height / 2.0f)), 0, this.displaySize.y);
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
        SystemClock systemClock = this.systemClock;
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
                                    this.this$0.vibratorHelper.vibrateGesture();
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
                                    this.this$0.vibratorHelper.vibrateGesture();
                                }
                            }, 10L);
                        }
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.this$0.updateArrowState(BackPanelController.GestureState.FLUNG, false);
                            }
                        }, 50L);
                        break;
                    }
                case 3:
                    if (this.previousState == GestureState.ENTRY && systemClock.uptimeMillis() - this.gestureEntryTime < 100) {
                        updateArrowState(GestureState.FLUNG, false);
                        break;
                    } else if (this.previousState == GestureState.INACTIVE && systemClock.uptimeMillis() - this.gestureInactiveTime < 400) {
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$onMotionEvent$$inlined$postDelayed$default$4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.this$0.updateArrowState(BackPanelController.GestureState.COMMITTED, false);
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
                default:
                    throw new NoWhenBranchMatchedException();
            }
            if (!Intrinsics.areEqual(this.velocityTracker, (Object) null) && (velocityTracker = this.velocityTracker) != null) {
                velocityTracker.recycle();
            }
            this.velocityTracker = null;
            return;
        }
        if (actionMasked != 2) {
            if (actionMasked != 3) {
                return;
            }
            this.interactionJankMonitor.cancel(88);
            updateArrowState(GestureState.GONE, false);
            if (!Intrinsics.areEqual(this.velocityTracker, (Object) null) && (velocityTracker2 = this.velocityTracker) != null) {
                velocityTracker2.recycle();
            }
            this.velocityTracker = null;
            return;
        }
        float x = motionEvent.getX();
        float f4 = this.startX;
        if (this.hasPassedDragSlop) {
            z = true;
        } else {
            if (Math.abs(x - f4) > this.viewConfiguration.getScaledEdgeSlop()) {
                updateArrowState(GestureState.ENTRY, false);
                if (BasicRune.NAVBAR_ENABLED) {
                    WindowManager.LayoutParams layoutParams2 = this.layoutParams;
                    if (layoutParams2 == null) {
                        layoutParams2 = null;
                    }
                    layoutParams2.setTitle("BackPanelController");
                }
                WindowManager windowManager = this.windowManager;
                View view = this.mView;
                WindowManager.LayoutParams layoutParams3 = this.layoutParams;
                if (layoutParams3 == null) {
                    layoutParams3 = null;
                }
                windowManager.updateViewLayout(view, layoutParams3);
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
            float fAbs = Math.abs(y);
            float fMax2 = Math.max(0.0f, ((BackPanel) this.mView).isLeftPanel ? x2 - this.startX : this.startX - x2);
            float f5 = fMax2 - this.previousXTranslation;
            this.previousXTranslation = fMax2;
            if (Math.abs(f5) > 0.0f) {
                f3 = 2.0f;
                boolean z3 = Math.signum(f5) == Math.signum(this.totalTouchDeltaActive);
                f2 = 1.0f;
                ClosedFloatRange closedFloatRange = edgePanelParams.dynamicTriggerThresholdRange;
                if (closedFloatRange == null) {
                    closedFloatRange = null;
                }
                f = 0.0f;
                boolean zContains = closedFloatRange.contains(Float.valueOf(this.totalTouchDeltaActive));
                if (z3 || zContains) {
                    this.totalTouchDeltaActive += f5;
                } else {
                    this.totalTouchDeltaActive = f5;
                    this.touchDeltaStartX = x2;
                }
                float f6 = -this.viewConfiguration.getScaledTouchSlop();
                float f7 = this.totalTouchDeltaInactive + f5;
                if (f7 >= f6) {
                    f6 = f7;
                }
                this.totalTouchDeltaInactive = f6;
            } else {
                f = 0.0f;
                f2 = 1.0f;
                f3 = 2.0f;
            }
            boolean z4 = ((float) 2) * fMax2 >= fAbs;
            boolean z5 = fMax2 > edgePanelParams.staticTriggerThreshold;
            GestureState gestureState = this.currentState;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i2 = iArr[gestureState.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    if (isPastThresholdToActive$default(this, z5 && ((this.totalTouchDeltaInactive > edgePanelParams.reactivationTriggerThreshold ? 1 : (this.totalTouchDeltaInactive == edgePanelParams.reactivationTriggerThreshold ? 0 : -1)) >= 0) && z4, null, 4)) {
                        updateArrowState(GestureState.ACTIVE, false);
                    }
                } else if (i2 == 3) {
                    boolean z6 = this.totalTouchDeltaActive <= (-edgePanelParams.deactivationTriggerThreshold);
                    boolean z7 = systemClock.uptimeMillis() - this.gestureEntryTime > 300;
                    if ((!z4 || z6) && z7) {
                        updateArrowState(GestureState.INACTIVE, false);
                    }
                }
            } else if (isPastThresholdToActive$default(this, z5, this.entryToActiveDelayCalculation, 2)) {
                updateArrowState(GestureState.ACTIVE, false);
            }
            int i3 = iArr[this.currentState.ordinal()];
            Float fValueOf = i3 != 1 ? i3 != 2 ? (i3 == 3 && !BasicRune.NAVBAR_GESTURE) ? Float.valueOf(MathUtils.saturate((fMax2 - this.previousXTranslationOnActiveOffset) / this.fullyStretchedThreshold)) : null : Float.valueOf(MathUtils.saturate(this.totalTouchDeltaInactive / edgePanelParams.reactivationTriggerThreshold)) : Float.valueOf(MathUtils.saturate(fMax2 / edgePanelParams.staticTriggerThreshold));
            if (fValueOf != null) {
                int i4 = iArr[this.currentState.ordinal()];
                if (i4 == 1) {
                    float fFloatValue = fValueOf.floatValue();
                    BackPanel backPanel3 = (BackPanel) this.mView;
                    Interpolator interpolator2 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator2 == null) {
                        interpolator2 = null;
                    }
                    float interpolation = interpolator2.getInterpolation(fFloatValue);
                    PathInterpolator pathInterpolator = edgePanelParams.entryWidthInterpolator;
                    if (pathInterpolator == null) {
                        pathInterpolator = null;
                    }
                    float interpolation2 = pathInterpolator.getInterpolation(fFloatValue);
                    PathInterpolator pathInterpolator2 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator2 == null) {
                        pathInterpolator2 = null;
                    }
                    float interpolation3 = pathInterpolator2.getInterpolation(fFloatValue);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens2 = edgePanelParams.entryIndicator;
                    if (backIndicatorDimens2 == null) {
                        backIndicatorDimens2 = null;
                    }
                    Step step = backIndicatorDimens2.arrowDimens.alphaInterpolator;
                    float fFloatValue2 = (step == null || (value2 = step.get(fFloatValue)) == null) ? f : ((Number) value2.value).floatValue();
                    PathInterpolator pathInterpolator3 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator3 == null) {
                        pathInterpolator3 = null;
                    }
                    float interpolation4 = pathInterpolator3.getInterpolation(fFloatValue);
                    PathInterpolator pathInterpolator4 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator4 == null) {
                        pathInterpolator4 = null;
                    }
                    float interpolation5 = pathInterpolator4.getInterpolation(fFloatValue);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens3 = edgePanelParams.preThresholdIndicator;
                    backPanel3.setStretch(0.0f, interpolation, fFloatValue2, interpolation2, interpolation3, interpolation4, interpolation5, backIndicatorDimens3 != null ? backIndicatorDimens3 : null);
                } else if (i4 == 2) {
                    float fFloatValue3 = fValueOf.floatValue();
                    BackPanel backPanel4 = (BackPanel) this.mView;
                    Interpolator interpolator3 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator3 == null) {
                        interpolator3 = null;
                    }
                    float interpolation6 = interpolator3.getInterpolation(fFloatValue3);
                    if (this.totalTouchDeltaInactive <= this.viewConfiguration.getScaledTouchSlop()) {
                        interpolator = this.previousPreThresholdWidthInterpolator;
                    } else if (this.totalTouchDeltaInactive <= f ? (interpolator = edgePanelParams.entryWidthTowardsEdgeInterpolator) == null : (interpolator = edgePanelParams.entryWidthInterpolator) == null) {
                        interpolator = null;
                    }
                    this.previousPreThresholdWidthInterpolator = interpolator;
                    float interpolation7 = interpolator.getInterpolation(fFloatValue3);
                    float f8 = interpolation7 < f ? f : interpolation7;
                    PathInterpolator pathInterpolator5 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator5 == null) {
                        pathInterpolator5 = null;
                    }
                    float interpolation8 = pathInterpolator5.getInterpolation(fFloatValue3);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens4 = edgePanelParams.preThresholdIndicator;
                    if (backIndicatorDimens4 == null) {
                        backIndicatorDimens4 = null;
                    }
                    Step step2 = backIndicatorDimens4.arrowDimens.alphaInterpolator;
                    float fFloatValue4 = (step2 == null || (value3 = step2.get(fFloatValue3)) == null) ? f : ((Number) value3.value).floatValue();
                    PathInterpolator pathInterpolator6 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator6 == null) {
                        pathInterpolator6 = null;
                    }
                    float interpolation9 = pathInterpolator6.getInterpolation(fFloatValue3);
                    PathInterpolator pathInterpolator7 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator7 == null) {
                        pathInterpolator7 = null;
                    }
                    float interpolation10 = pathInterpolator7.getInterpolation(fFloatValue3);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens5 = edgePanelParams.preThresholdIndicator;
                    backPanel4.setStretch(0.0f, interpolation6, fFloatValue4, f8, interpolation8, interpolation9, interpolation10, backIndicatorDimens5 != null ? backIndicatorDimens5 : null);
                } else if (i4 == 3) {
                    float fFloatValue5 = fValueOf.floatValue();
                    BackPanel backPanel5 = (BackPanel) this.mView;
                    PathInterpolator pathInterpolator8 = edgePanelParams.horizontalTranslationInterpolator;
                    if (pathInterpolator8 == null) {
                        pathInterpolator8 = null;
                    }
                    float interpolation11 = pathInterpolator8.getInterpolation(fFloatValue5);
                    Interpolator interpolator4 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator4 == null) {
                        interpolator4 = null;
                    }
                    float interpolation12 = interpolator4.getInterpolation(fFloatValue5);
                    PathInterpolator pathInterpolator9 = edgePanelParams.activeWidthInterpolator;
                    if (pathInterpolator9 == null) {
                        pathInterpolator9 = null;
                    }
                    float interpolation13 = pathInterpolator9.getInterpolation(fFloatValue5);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens6 = edgePanelParams.fullyStretchedIndicator;
                    backPanel5.setStretch(interpolation11, interpolation12, 1.0f, interpolation13, 1.0f, 1.0f, 1.0f, backIndicatorDimens6 != null ? backIndicatorDimens6 : null);
                }
            }
            switch (iArr[this.currentState.ordinal()]) {
                case 1:
                case 2:
                    break;
                case 3:
                case 5:
                case 6:
                    fValueOf = Float.valueOf(f2);
                    break;
                case 4:
                case 7:
                    fValueOf = Float.valueOf(f);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i5 = iArr[this.currentState.ordinal()];
            if (i5 == 1 ? (backIndicatorDimens = edgePanelParams.entryIndicator) == null : i5 == 2 ? (backIndicatorDimens = edgePanelParams.preThresholdIndicator) == null : i5 == 3 ? (backIndicatorDimens = edgePanelParams.activeIndicator) == null : (backIndicatorDimens = edgePanelParams.preThresholdIndicator) == null) {
                backIndicatorDimens = null;
            }
            if (fValueOf != null) {
                float fFloatValue6 = fValueOf.floatValue();
                Step step3 = backIndicatorDimens.arrowDimens.alphaSpring;
                if (step3 != null && (value = step3.get(fFloatValue6)) != null) {
                    if (!value.isNewState) {
                        value = null;
                    }
                    if (value != null) {
                        BackPanel backPanel6 = (BackPanel) this.mView;
                        SpringForce springForce = (SpringForce) value.value;
                        BackPanel.AnimatedFloat animatedFloat = backPanel6.arrowAlpha;
                        SpringAnimation springAnimation = animatedFloat.animation;
                        springAnimation.cancel();
                        float f9 = f;
                        springAnimation.mVelocity = f9;
                        if (springForce != null) {
                            springAnimation.mSpring = springForce;
                        }
                        springAnimation.animateToFinalPosition(animatedFloat.restingPosition + f9);
                    }
                }
            }
            float fAbs2 = Math.abs(y);
            float height = ((BackPanel) this.mView).getHeight();
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens7 = edgePanelParams.entryIndicator;
            if (backIndicatorDimens7 == null) {
                backIndicatorDimens7 = null;
            }
            float f10 = (height - backIndicatorDimens7.backgroundDimens.height) / f3;
            float fSaturate = MathUtils.saturate(fAbs2 / (15.0f * f10));
            PathInterpolator pathInterpolator10 = edgePanelParams.verticalTranslationInterpolator;
            if (pathInterpolator10 == null) {
                pathInterpolator10 = null;
            }
            BackPanel.AnimatedFloat.stretchTo$default(((BackPanel) this.mView).verticalTranslation, Math.signum(y) * pathInterpolator10.getInterpolation(fSaturate) * f10, null, 6);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        updateConfiguration$1();
        ConfigurationController configurationController = this.configurationController;
        updateArrowDirection(((ConfigurationControllerImpl) configurationController).isLayoutRtl());
        updateArrowState(GestureState.GONE, true);
        updateRestingArrowDimens();
        ((ConfigurationControllerImpl) configurationController).addCallback(this.configurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
    }

    public final void playWithBackgroundWidthAnimation(final DelayedOnAnimationEndListener delayedOnAnimationEndListener, long j) {
        Handler handler = this.mainHandler;
        if (j != 0) {
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$playWithBackgroundWidthAnimation$$inlined$postDelayed$default$1
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.playWithBackgroundWidthAnimation(delayedOnAnimationEndListener, 0L);
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
            return;
        }
        delayedOnAnimationEndListener.runnable.run();
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
        EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.activeIndicator;
        if (backIndicatorDimens == null) {
            backIndicatorDimens = null;
        }
        edgePanelParams.activeIndicator = EdgePanelParams.BackIndicatorDimens.copy$default(backIndicatorDimens, null, null, EdgePanelParamsKt.createSpring(f, f2), null, 191);
    }

    public final void updateArrowDirection(boolean z) {
        BackPanel backPanel = (BackPanel) this.mView;
        if (backPanel.arrowsPointLeft != z) {
            backPanel.invalidate();
            backPanel.arrowsPointLeft = z;
        }
    }

    public final void updateArrowState(GestureState gestureState, boolean z) {
        Step.Value value;
        if (z || this.currentState != gestureState) {
            GestureState gestureState2 = this.currentState;
            this.previousState = gestureState2;
            this.currentState = gestureState;
            boolean z2 = BasicRune.NAVBAR_GESTURE;
            if (z2) {
                Log.d("BackPanelController", "updateArrowState: " + gestureState2 + " -> " + gestureState);
            }
            GestureState gestureState3 = this.currentState;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i = iArr[gestureState3.ordinal()];
            if (i == 1) {
                this.interactionJankMonitor.cancel(88);
                this.interactionJankMonitor.begin(this.mView, 88);
            } else if (i == 4) {
                this.interactionJankMonitor.end(88);
            }
            SpringForce springForce = null;
            switch (iArr[this.currentState.ordinal()]) {
                case 1:
                case 2:
                    NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
                    if (backCallback == null) {
                        backCallback = null;
                    }
                    backCallback.setTriggerBack(false);
                    break;
                case 3:
                    NavigationEdgeBackPlugin.BackCallback backCallback2 = this.backCallback;
                    if (backCallback2 == null) {
                        backCallback2 = null;
                    }
                    backCallback2.setTriggerBack(true);
                    break;
                case 4:
                    break;
                case 5:
                case 6:
                    if (this.previousState != GestureState.FLUNG) {
                        NavigationEdgeBackPlugin.BackCallback backCallback3 = this.backCallback;
                        if (backCallback3 == null) {
                            backCallback3 = null;
                        }
                        backCallback3.triggerBack();
                        break;
                    }
                    break;
                case 7:
                    NavigationEdgeBackPlugin.BackCallback backCallback4 = this.backCallback;
                    if (backCallback4 == null) {
                        backCallback4 = null;
                    }
                    backCallback4.cancelBack();
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i2 = iArr[this.currentState.ordinal()];
            EdgePanelParams edgePanelParams = this.params;
            VibratorHelper vibratorHelper = this.vibratorHelper;
            SystemClock systemClock = this.systemClock;
            DelayedOnAnimationEndListener delayedOnAnimationEndListener = this.onEndSetGoneStateListener;
            Handler handler = this.mainHandler;
            switch (i2) {
                case 1:
                    this.mView.setVisibility(0);
                    updateRestingArrowDimens();
                    this.gestureEntryTime = systemClock.uptimeMillis();
                    return;
                case 2:
                    this.gestureInactiveTime = systemClock.uptimeMillis();
                    this.totalTouchDeltaInactive = -edgePanelParams.deactivationTriggerThreshold;
                    ((BackPanel) this.mView).popOffEdge(-1.5f);
                    if (!z2) {
                        T t = this.mView;
                        vibratorHelper.getClass();
                        t.performHapticFeedback(24);
                    }
                    updateRestingArrowDimens();
                    return;
                case 3:
                    this.previousXTranslationOnActiveOffset = this.previousXTranslation;
                    updateRestingArrowDimens();
                    if (z2) {
                        vibratorHelper.cancel();
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.this$0.vibratorHelper.vibrateGesture();
                            }
                        }, 10L);
                    } else {
                        T t2 = this.mView;
                        vibratorHelper.getClass();
                        t2.performHapticFeedback(23);
                    }
                    ((BackPanel) this.mView).popOffEdge(this.previousState == GestureState.INACTIVE ? 4.7f : 4.5f);
                    return;
                case 4:
                    updateRestingArrowDimens();
                    this.mView.setVisibility(8);
                    return;
                case 5:
                    if (!z2 && this.previousState != GestureState.ACTIVE) {
                        T t3 = this.mView;
                        vibratorHelper.getClass();
                        t3.performHapticFeedback(23);
                    }
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackPanel backPanel = (BackPanel) ((ViewController) this.this$0).mView;
                            backPanel.scalePivotX.snapTo(backPanel.backgroundWidth.pos / 2);
                            BackPanel.AnimatedFloat.stretchTo$default(backPanel.scale, 0.0f, Float.valueOf(2.0f), 4);
                        }
                    }, 60L);
                    handler.postDelayed(this.onEndSetCommittedStateListener.runnable, 160L);
                    updateRestingArrowDimens();
                    return;
                case 6:
                    if (this.previousState == GestureState.FLUNG) {
                        updateRestingArrowDimens();
                        handler.postDelayed(delayedOnAnimationEndListener.runnable, 120L);
                        return;
                    } else {
                        BackPanel backPanel = (BackPanel) this.mView;
                        backPanel.scalePivotX.snapTo(backPanel.backgroundWidth.pos / 2);
                        BackPanel.AnimatedFloat.stretchTo$default(backPanel.scale, 0.0f, Float.valueOf(3.0f), 4);
                        handler.postDelayed(this.onAlphaEndSetGoneStateListener.runnable, 80L);
                        return;
                    }
                case 7:
                    playWithBackgroundWidthAnimation(delayedOnAnimationEndListener, Math.max(0L, 200 - (systemClock.uptimeMillis() - this.gestureEntryTime)));
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.cancelledIndicator;
                    if (backIndicatorDimens == null) {
                        backIndicatorDimens = null;
                    }
                    Step step = backIndicatorDimens.arrowDimens.alphaSpring;
                    if (step != null && (value = step.get(0.0f)) != null) {
                        springForce = (SpringForce) value.value;
                    }
                    BackPanel.AnimatedFloat animatedFloat = ((BackPanel) this.mView).arrowAlpha;
                    SpringAnimation springAnimation = animatedFloat.animation;
                    springAnimation.cancel();
                    springAnimation.mVelocity = 0.0f;
                    if (springForce != null) {
                        springAnimation.mSpring = springForce;
                    }
                    springAnimation.animateToFinalPosition(animatedFloat.restingPosition + 0.0f);
                    return;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
        T t = this.mView;
        if (t != 0) {
            ((BackPanel) t).updateBackGestureIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(drawable, drawable2);
        } else {
            Log.d("BackPanelController", "updateBackGestureIcon fail, view is null");
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        T t = this.mView;
        if (t == 0) {
            Log.d("BackPanelController", "updateBackPanelColor fail, view is null");
        } else {
            ((BackPanel) t).updateBackPanelColor$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, i2, i3, i4);
            updateConfiguration$1();
        }
    }

    public final void updateConfiguration$1() throws Resources.NotFoundException {
        Resources resources = getResources();
        EdgePanelParams edgePanelParams = this.params;
        edgePanelParams.update(resources);
        ((BackPanel) this.mView).updateArrowPaint$frameworks__base__packages__SystemUI__android_common__SystemUI_core(edgePanelParams.arrowThickness);
        this.minFlingDistance = this.viewConfiguration.getScaledTouchSlop() * 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:263:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x0305  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRestingArrowDimens() {
        float f;
        Float f2;
        Float fValueOf;
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
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens = edgePanelParams.entryIndicator;
                SpringForce springForce = (backIndicatorDimens != null ? backIndicatorDimens : null).arrowDimens.lengthSpring;
                SpringForce springForce2 = (backIndicatorDimens != null ? backIndicatorDimens : null).arrowDimens.heightSpring;
                SpringForce springForce3 = (backIndicatorDimens != null ? backIndicatorDimens : null).scaleSpring;
                SpringForce springForce4 = (backIndicatorDimens != null ? backIndicatorDimens : null).verticalTranslationSpring;
                SpringForce springForce5 = (backIndicatorDimens != null ? backIndicatorDimens : null).horizontalTranslationSpring;
                SpringForce springForce6 = (backIndicatorDimens != null ? backIndicatorDimens : null).backgroundDimens.alphaSpring;
                SpringForce springForce7 = (backIndicatorDimens != null ? backIndicatorDimens : null).backgroundDimens.widthSpring;
                SpringForce springForce8 = (backIndicatorDimens != null ? backIndicatorDimens : null).backgroundDimens.heightSpring;
                SpringForce springForce9 = (backIndicatorDimens != null ? backIndicatorDimens : null).backgroundDimens.edgeCornerRadiusSpring;
                if (backIndicatorDimens == null) {
                    backIndicatorDimens = null;
                }
                BackPanel.setSpring$default(backPanel, springForce5, springForce4, springForce3, springForce, springForce2, springForce6, backIndicatorDimens.backgroundDimens.farCornerRadiusSpring, springForce9, springForce7, springForce8, 32);
                break;
            case 2:
                BackPanel backPanel2 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens2 = edgePanelParams.preThresholdIndicator;
                SpringForce springForce10 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).arrowDimens.lengthSpring;
                SpringForce springForce11 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).arrowDimens.heightSpring;
                SpringForce springForce12 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).horizontalTranslationSpring;
                SpringForce springForce13 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).scaleSpring;
                SpringForce springForce14 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).backgroundDimens.widthSpring;
                SpringForce springForce15 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).backgroundDimens.heightSpring;
                SpringForce springForce16 = (backIndicatorDimens2 != null ? backIndicatorDimens2 : null).backgroundDimens.edgeCornerRadiusSpring;
                if (backIndicatorDimens2 == null) {
                    backIndicatorDimens2 = null;
                }
                BackPanel.setSpring$default(backPanel2, springForce12, null, springForce13, springForce10, springForce11, null, backIndicatorDimens2.backgroundDimens.farCornerRadiusSpring, springForce16, springForce14, springForce15, 98);
                break;
            case 3:
                BackPanel backPanel3 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens3 = edgePanelParams.activeIndicator;
                SpringForce springForce17 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).arrowDimens.lengthSpring;
                SpringForce springForce18 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).arrowDimens.heightSpring;
                SpringForce springForce19 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).scaleSpring;
                SpringForce springForce20 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).horizontalTranslationSpring;
                SpringForce springForce21 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).backgroundDimens.widthSpring;
                SpringForce springForce22 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).backgroundDimens.heightSpring;
                SpringForce springForce23 = (backIndicatorDimens3 != null ? backIndicatorDimens3 : null).backgroundDimens.edgeCornerRadiusSpring;
                if (backIndicatorDimens3 == null) {
                    backIndicatorDimens3 = null;
                }
                BackPanel.setSpring$default(backPanel3, springForce20, null, springForce19, springForce17, springForce18, null, backIndicatorDimens3.backgroundDimens.farCornerRadiusSpring, springForce23, springForce21, springForce22, 98);
                break;
            case 5:
                BackPanel backPanel4 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens4 = edgePanelParams.flungIndicator;
                SpringForce springForce24 = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).arrowDimens.lengthSpring;
                SpringForce springForce25 = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).arrowDimens.heightSpring;
                SpringForce springForce26 = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).backgroundDimens.widthSpring;
                SpringForce springForce27 = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).backgroundDimens.heightSpring;
                SpringForce springForce28 = (backIndicatorDimens4 != null ? backIndicatorDimens4 : null).backgroundDimens.edgeCornerRadiusSpring;
                if (backIndicatorDimens4 == null) {
                    backIndicatorDimens4 = null;
                }
                BackPanel.setSpring$default(backPanel4, null, null, null, springForce24, springForce25, null, backIndicatorDimens4.backgroundDimens.farCornerRadiusSpring, springForce28, springForce26, springForce27, 103);
                break;
            case 6:
                BackPanel backPanel5 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens5 = edgePanelParams.committedIndicator;
                SpringForce springForce29 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).arrowDimens.lengthSpring;
                SpringForce springForce30 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).arrowDimens.heightSpring;
                SpringForce springForce31 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).scaleSpring;
                SpringForce springForce32 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).backgroundDimens.alphaSpring;
                SpringForce springForce33 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).backgroundDimens.widthSpring;
                SpringForce springForce34 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).backgroundDimens.heightSpring;
                SpringForce springForce35 = (backIndicatorDimens5 != null ? backIndicatorDimens5 : null).backgroundDimens.edgeCornerRadiusSpring;
                if (backIndicatorDimens5 == null) {
                    backIndicatorDimens5 = null;
                }
                BackPanel.setSpring$default(backPanel5, null, null, springForce31, springForce29, springForce30, springForce32, backIndicatorDimens5.backgroundDimens.farCornerRadiusSpring, springForce35, springForce33, springForce34, 35);
                break;
            case 7:
                BackPanel backPanel6 = (BackPanel) this.mView;
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens6 = edgePanelParams.cancelledIndicator;
                if (backIndicatorDimens6 == null) {
                    backIndicatorDimens6 = null;
                }
                BackPanel.setSpring$default(backPanel6, null, null, null, null, null, backIndicatorDimens6.backgroundDimens.alphaSpring, null, null, null, null, 1983);
                break;
        }
        BackPanel backPanel7 = (BackPanel) this.mView;
        GestureState gestureState2 = this.currentState;
        boolean z = (gestureState2 == GestureState.FLUNG || gestureState2 == GestureState.COMMITTED) ? false : true;
        int i2 = iArr[gestureState2.ordinal()];
        if (i2 == 3 || i2 == 5) {
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens7 = edgePanelParams.activeIndicator;
            if (backIndicatorDimens7 == null) {
                backIndicatorDimens7 = null;
            }
            f = backIndicatorDimens7.scale;
        } else if (i2 != 6) {
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens8 = edgePanelParams.preThresholdIndicator;
            if (backIndicatorDimens8 == null) {
                backIndicatorDimens8 = null;
            }
            f = backIndicatorDimens8.scale;
        } else {
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens9 = edgePanelParams.committedIndicator;
            if (backIndicatorDimens9 == null) {
                backIndicatorDimens9 = null;
            }
            f = backIndicatorDimens9.scale;
        }
        float f4 = f;
        switch (iArr[this.currentState.ordinal()]) {
            case 1:
            case 2:
            case 4:
            case 7:
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens10 = edgePanelParams.preThresholdIndicator;
                if (backIndicatorDimens10 == null) {
                    backIndicatorDimens10 = null;
                }
                f2 = backIndicatorDimens10.scalePivotX;
                break;
            case 3:
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens11 = edgePanelParams.activeIndicator;
                if (backIndicatorDimens11 == null) {
                    backIndicatorDimens11 = null;
                }
                f2 = backIndicatorDimens11.scalePivotX;
                break;
            case 5:
            case 6:
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens12 = edgePanelParams.committedIndicator;
                if (backIndicatorDimens12 == null) {
                    backIndicatorDimens12 = null;
                }
                f2 = backIndicatorDimens12.scalePivotX;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Float f5 = f2;
        int i3 = iArr[this.currentState.ordinal()];
        if (i3 == 1 || i3 == 2) {
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens13 = edgePanelParams.entryIndicator;
            if (backIndicatorDimens13 == null) {
                backIndicatorDimens13 = null;
            }
            fValueOf = backIndicatorDimens13.horizontalTranslation;
        } else {
            if (i3 != 3) {
                if (i3 == 4) {
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens14 = edgePanelParams.activeIndicator;
                    if (backIndicatorDimens14 == null) {
                        backIndicatorDimens14 = null;
                    }
                    Float f6 = backIndicatorDimens14.backgroundDimens.width;
                    if (f6 != null) {
                        fValueOf = Float.valueOf(f6.floatValue() * (-1));
                    }
                    f3 = null;
                } else if (i3 != 5) {
                    if (i3 == 7) {
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens15 = edgePanelParams.cancelledIndicator;
                        if (backIndicatorDimens15 == null) {
                            backIndicatorDimens15 = null;
                        }
                        fValueOf = backIndicatorDimens15.horizontalTranslation;
                    }
                    f3 = null;
                } else {
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens16 = edgePanelParams.activeIndicator;
                    if (backIndicatorDimens16 == null) {
                        backIndicatorDimens16 = null;
                    }
                    fValueOf = backIndicatorDimens16.horizontalTranslation;
                }
                switch (iArr[this.currentState.ordinal()]) {
                    case 1:
                    case 2:
                    case 4:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens17 = edgePanelParams.entryIndicator;
                        if (backIndicatorDimens17 == null) {
                            backIndicatorDimens17 = null;
                        }
                        arrowDimens = backIndicatorDimens17.arrowDimens;
                        break;
                    case 3:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens18 = edgePanelParams.activeIndicator;
                        if (backIndicatorDimens18 == null) {
                            backIndicatorDimens18 = null;
                        }
                        arrowDimens = backIndicatorDimens18.arrowDimens;
                        break;
                    case 5:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens19 = edgePanelParams.flungIndicator;
                        if (backIndicatorDimens19 == null) {
                            backIndicatorDimens19 = null;
                        }
                        arrowDimens = backIndicatorDimens19.arrowDimens;
                        break;
                    case 6:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens20 = edgePanelParams.committedIndicator;
                        if (backIndicatorDimens20 == null) {
                            backIndicatorDimens20 = null;
                        }
                        arrowDimens = backIndicatorDimens20.arrowDimens;
                        break;
                    case 7:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens21 = edgePanelParams.cancelledIndicator;
                        if (backIndicatorDimens21 == null) {
                            backIndicatorDimens21 = null;
                        }
                        arrowDimens = backIndicatorDimens21.arrowDimens;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                EdgePanelParams.ArrowDimens arrowDimens2 = arrowDimens;
                switch (iArr[this.currentState.ordinal()]) {
                    case 1:
                    case 2:
                    case 4:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens22 = edgePanelParams.entryIndicator;
                        backgroundDimens = (backIndicatorDimens22 != null ? backIndicatorDimens22 : null).backgroundDimens;
                        break;
                    case 3:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens23 = edgePanelParams.activeIndicator;
                        backgroundDimens = (backIndicatorDimens23 != null ? backIndicatorDimens23 : null).backgroundDimens;
                        break;
                    case 5:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens24 = edgePanelParams.activeIndicator;
                        backgroundDimens = (backIndicatorDimens24 != null ? backIndicatorDimens24 : null).backgroundDimens;
                        break;
                    case 6:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens25 = edgePanelParams.committedIndicator;
                        backgroundDimens = (backIndicatorDimens25 != null ? backIndicatorDimens25 : null).backgroundDimens;
                        break;
                    case 7:
                        EdgePanelParams.BackIndicatorDimens backIndicatorDimens26 = edgePanelParams.cancelledIndicator;
                        backgroundDimens = (backIndicatorDimens26 != null ? backIndicatorDimens26 : null).backgroundDimens;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                EdgePanelParams.BackIndicatorDimens backIndicatorDimens27 = new EdgePanelParams.BackIndicatorDimens(f3, f4, f5, arrowDimens2, backgroundDimens, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
                backPanel7.horizontalTranslation.updateRestingPosition(true, backIndicatorDimens27.horizontalTranslation);
                backPanel7.scale.updateRestingPosition(true, Float.valueOf(backIndicatorDimens27.scale));
                BackPanel.AnimatedFloat animatedFloat = backPanel7.backgroundAlpha;
                EdgePanelParams.BackgroundDimens backgroundDimens2 = backIndicatorDimens27.backgroundDimens;
                animatedFloat.updateRestingPosition(true, Float.valueOf(backgroundDimens2.alpha));
                BackPanel.AnimatedFloat animatedFloat2 = backPanel7.arrowAlpha;
                EdgePanelParams.ArrowDimens arrowDimens3 = backIndicatorDimens27.arrowDimens;
                animatedFloat2.updateRestingPosition(z, Float.valueOf(arrowDimens3.alpha));
                backPanel7.arrowLength.updateRestingPosition(z, arrowDimens3.length);
                backPanel7.arrowHeight.updateRestingPosition(z, arrowDimens3.height);
                backPanel7.scalePivotX.updateRestingPosition(z, backIndicatorDimens27.scalePivotX);
                backPanel7.backgroundWidth.updateRestingPosition(z, backgroundDimens2.width);
                backPanel7.backgroundHeight.updateRestingPosition(z, Float.valueOf(backgroundDimens2.height));
                backPanel7.backgroundEdgeCornerRadius.updateRestingPosition(z, Float.valueOf(backgroundDimens2.edgeCornerRadius));
                backPanel7.backgroundFarCornerRadius.updateRestingPosition(z, Float.valueOf(backgroundDimens2.farCornerRadius));
            }
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens28 = edgePanelParams.activeIndicator;
            if (backIndicatorDimens28 == null) {
                backIndicatorDimens28 = null;
            }
            fValueOf = backIndicatorDimens28.horizontalTranslation;
        }
        f3 = fValueOf;
        switch (iArr[this.currentState.ordinal()]) {
        }
        EdgePanelParams.ArrowDimens arrowDimens22 = arrowDimens;
        switch (iArr[this.currentState.ordinal()]) {
        }
        EdgePanelParams.BackIndicatorDimens backIndicatorDimens272 = new EdgePanelParams.BackIndicatorDimens(f3, f4, f5, arrowDimens22, backgroundDimens, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
        backPanel7.horizontalTranslation.updateRestingPosition(true, backIndicatorDimens272.horizontalTranslation);
        backPanel7.scale.updateRestingPosition(true, Float.valueOf(backIndicatorDimens272.scale));
        BackPanel.AnimatedFloat animatedFloat3 = backPanel7.backgroundAlpha;
        EdgePanelParams.BackgroundDimens backgroundDimens22 = backIndicatorDimens272.backgroundDimens;
        animatedFloat3.updateRestingPosition(true, Float.valueOf(backgroundDimens22.alpha));
        BackPanel.AnimatedFloat animatedFloat22 = backPanel7.arrowAlpha;
        EdgePanelParams.ArrowDimens arrowDimens32 = backIndicatorDimens272.arrowDimens;
        animatedFloat22.updateRestingPosition(z, Float.valueOf(arrowDimens32.alpha));
        backPanel7.arrowLength.updateRestingPosition(z, arrowDimens32.length);
        backPanel7.arrowHeight.updateRestingPosition(z, arrowDimens32.height);
        backPanel7.scalePivotX.updateRestingPosition(z, backIndicatorDimens272.scalePivotX);
        backPanel7.backgroundWidth.updateRestingPosition(z, backgroundDimens22.width);
        backPanel7.backgroundHeight.updateRestingPosition(z, Float.valueOf(backgroundDimens22.height));
        backPanel7.backgroundEdgeCornerRadius.updateRestingPosition(z, Float.valueOf(backgroundDimens22.edgeCornerRadius));
        backPanel7.backgroundFarCornerRadius.updateRestingPosition(z, Float.valueOf(backgroundDimens22.farCornerRadius));
    }

    public static /* synthetic */ void getCurrentState$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setInsets(int i, int i2) {
    }
}
