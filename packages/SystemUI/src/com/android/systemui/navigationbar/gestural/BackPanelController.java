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
import com.android.systemui.util.concurrency.BackPanelUiThread;
import com.android.systemui.util.concurrency.UiThreadContext;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final ConfigurationController configurationController;
        public final InteractionJankMonitor interactionJankMonitor;
        public final LatencyTracker latencyTracker;
        public final SystemClock systemClock;
        public final UiThreadContext uiThreadContext;
        public final VibratorHelper vibratorHelper;
        public final ViewConfiguration viewConfiguration;
        public final WindowManager windowManager;

        public Factory(WindowManager windowManager, ViewConfiguration viewConfiguration, @BackPanelUiThread UiThreadContext uiThreadContext, SystemClock systemClock, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker, InteractionJankMonitor interactionJankMonitor) {
            this.windowManager = windowManager;
            this.viewConfiguration = viewConfiguration;
            this.uiThreadContext = uiThreadContext;
            this.systemClock = systemClock;
            this.vibratorHelper = vibratorHelper;
            this.configurationController = configurationController;
            this.latencyTracker = latencyTracker;
            this.interactionJankMonitor = interactionJankMonitor;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1] */
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
                View view;
                View view2;
                BackPanelController.this.updateRestingArrowDimens();
                view = ((ViewController) BackPanelController.this).mView;
                view2 = ((ViewController) BackPanelController.this).mView;
                BackPanel.AnimatedFloat animatedFloat = ((BackPanel) view2).backgroundAlpha;
                BackPanelController.DelayedOnAnimationEndListener delayedOnAnimationEndListener = BackPanelController.this.onEndSetGoneStateListener;
                ((BackPanel) view).getClass();
                SpringAnimation springAnimation = animatedFloat.animation;
                if (springAnimation.mRunning) {
                    springAnimation.addEndListener(delayedOnAnimationEndListener);
                    return;
                }
                delayedOnAnimationEndListener.runnable.run();
                BackPanelController backPanelController = BackPanelController.this;
                Handler handler2 = backPanelController.mainHandler;
                BackPanelController$failsafeRunnable$1 backPanelController$failsafeRunnable$1 = backPanelController.failsafeRunnable;
                handler2.removeCallbacks(backPanelController$failsafeRunnable$1);
                handler2.postDelayed(backPanelController$failsafeRunnable$1, 350L);
            }
        });
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                BackPanelController.this.updateConfiguration$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(boolean z) {
                BackPanelController.this.updateArrowDirection(z);
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
        SystemClock systemClock = backPanelController.systemClock;
        if (z2) {
            backPanelController.pastThresholdWhileEntryOrInactiveTime = systemClock.uptimeMillis();
            backPanelController.entryToActiveDelay = ((Number) function0.invoke()).floatValue();
        }
        return ((float) (systemClock.uptimeMillis() - backPanelController.pastThresholdWhileEntryOrInactiveTime)) > backPanelController.entryToActiveDelay;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void dump(PrintWriter printWriter) {
        printWriter.println("BackPanelController:");
        printWriter.println("  currentState=" + this.currentState);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  isLeftPanel=", ((BackPanel) this.mView).isLeftPanel, printWriter);
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
            (layoutParams != null ? layoutParams : null).y = MathUtils.constrain((int) (max - ((layoutParams == null ? null : layoutParams).height / 2.0f)), 0, this.displaySize.y);
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
                    if (this.previousState == GestureState.ENTRY && systemClock.uptimeMillis() - this.gestureEntryTime < 100) {
                        updateArrowState(GestureState.FLUNG, false);
                        break;
                    } else if (this.previousState == GestureState.INACTIVE && systemClock.uptimeMillis() - this.gestureInactiveTime < 400) {
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
        float f = this.startX;
        if (this.hasPassedDragSlop) {
            z = true;
        } else {
            if (Math.abs(x - f) > this.viewConfiguration.getScaledEdgeSlop()) {
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
            float abs = Math.abs(y);
            float max2 = Math.max(0.0f, ((BackPanel) this.mView).isLeftPanel ? x2 - this.startX : this.startX - x2);
            float f2 = max2 - this.previousXTranslation;
            this.previousXTranslation = max2;
            if (Math.abs(f2) > 0.0f) {
                boolean z3 = Math.signum(f2) == Math.signum(this.totalTouchDeltaActive);
                ClosedFloatRange closedFloatRange = edgePanelParams.dynamicTriggerThresholdRange;
                if (closedFloatRange == null) {
                    closedFloatRange = null;
                }
                boolean contains = closedFloatRange.contains(Float.valueOf(this.totalTouchDeltaActive));
                if (z3 || contains) {
                    this.totalTouchDeltaActive += f2;
                } else {
                    this.totalTouchDeltaActive = f2;
                    this.touchDeltaStartX = x2;
                }
                this.totalTouchDeltaInactive = RangesKt___RangesKt.coerceAtLeast(this.totalTouchDeltaInactive + f2, -this.viewConfiguration.getScaledTouchSlop());
            }
            boolean z4 = ((float) 2) * max2 >= abs;
            boolean z5 = max2 > edgePanelParams.staticTriggerThreshold;
            GestureState gestureState = this.currentState;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i2 = iArr[gestureState.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    if (isPastThresholdToActive$default(this, z5 && ((this.totalTouchDeltaInactive > edgePanelParams.reactivationTriggerThreshold ? 1 : (this.totalTouchDeltaInactive == edgePanelParams.reactivationTriggerThreshold ? 0 : -1)) >= 0) && z4, Float.valueOf(160.0f), null, 4)) {
                        updateArrowState(GestureState.ACTIVE, false);
                    }
                } else if (i2 == 3) {
                    boolean z6 = this.totalTouchDeltaActive <= (-edgePanelParams.deactivationTriggerThreshold);
                    boolean z7 = systemClock.uptimeMillis() - this.gestureEntryTime > 300;
                    if ((!z4 || z6) && z7) {
                        updateArrowState(GestureState.INACTIVE, false);
                    }
                }
            } else if (isPastThresholdToActive$default(this, z5, null, this.entryToActiveDelayCalculation, 2)) {
                updateArrowState(GestureState.ACTIVE, false);
            }
            int i3 = iArr[this.currentState.ordinal()];
            Float valueOf = i3 != 1 ? i3 != 2 ? (i3 == 3 && !BasicRune.NAVBAR_GESTURE) ? Float.valueOf(MathUtils.saturate((max2 - this.previousXTranslationOnActiveOffset) / this.fullyStretchedThreshold)) : null : Float.valueOf(MathUtils.saturate(this.totalTouchDeltaInactive / edgePanelParams.reactivationTriggerThreshold)) : Float.valueOf(MathUtils.saturate(max2 / edgePanelParams.staticTriggerThreshold));
            if (valueOf != null) {
                int i4 = iArr[this.currentState.ordinal()];
                if (i4 == 1) {
                    float floatValue = valueOf.floatValue();
                    BackPanel backPanel3 = (BackPanel) this.mView;
                    Interpolator interpolator2 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator2 == null) {
                        interpolator2 = null;
                    }
                    float interpolation = interpolator2.getInterpolation(floatValue);
                    PathInterpolator pathInterpolator = edgePanelParams.entryWidthInterpolator;
                    if (pathInterpolator == null) {
                        pathInterpolator = null;
                    }
                    float interpolation2 = pathInterpolator.getInterpolation(floatValue);
                    PathInterpolator pathInterpolator2 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator2 == null) {
                        pathInterpolator2 = null;
                    }
                    float interpolation3 = pathInterpolator2.getInterpolation(floatValue);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens2 = edgePanelParams.entryIndicator;
                    if (backIndicatorDimens2 == null) {
                        backIndicatorDimens2 = null;
                    }
                    Step step = backIndicatorDimens2.arrowDimens.alphaInterpolator;
                    float floatValue2 = (step == null || (value2 = step.get(floatValue)) == null) ? 0.0f : ((Number) value2.value).floatValue();
                    PathInterpolator pathInterpolator3 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator3 == null) {
                        pathInterpolator3 = null;
                    }
                    float interpolation4 = pathInterpolator3.getInterpolation(floatValue);
                    PathInterpolator pathInterpolator4 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator4 == null) {
                        pathInterpolator4 = null;
                    }
                    float interpolation5 = pathInterpolator4.getInterpolation(floatValue);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens3 = edgePanelParams.preThresholdIndicator;
                    backPanel3.setStretch(0.0f, interpolation, floatValue2, interpolation2, interpolation3, interpolation4, interpolation5, backIndicatorDimens3 != null ? backIndicatorDimens3 : null);
                } else if (i4 == 2) {
                    float floatValue3 = valueOf.floatValue();
                    BackPanel backPanel4 = (BackPanel) this.mView;
                    Interpolator interpolator3 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator3 == null) {
                        interpolator3 = null;
                    }
                    float interpolation6 = interpolator3.getInterpolation(floatValue3);
                    if (this.totalTouchDeltaInactive <= this.viewConfiguration.getScaledTouchSlop()) {
                        interpolator = this.previousPreThresholdWidthInterpolator;
                    } else if (this.totalTouchDeltaInactive <= 0.0f ? (interpolator = edgePanelParams.entryWidthTowardsEdgeInterpolator) == null : (interpolator = edgePanelParams.entryWidthInterpolator) == null) {
                        interpolator = null;
                    }
                    this.previousPreThresholdWidthInterpolator = interpolator;
                    float coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(interpolator.getInterpolation(floatValue3), 0.0f);
                    PathInterpolator pathInterpolator5 = edgePanelParams.heightInterpolator;
                    if (pathInterpolator5 == null) {
                        pathInterpolator5 = null;
                    }
                    float interpolation7 = pathInterpolator5.getInterpolation(floatValue3);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens4 = edgePanelParams.preThresholdIndicator;
                    if (backIndicatorDimens4 == null) {
                        backIndicatorDimens4 = null;
                    }
                    Step step2 = backIndicatorDimens4.arrowDimens.alphaInterpolator;
                    float floatValue4 = (step2 == null || (value3 = step2.get(floatValue3)) == null) ? 0.0f : ((Number) value3.value).floatValue();
                    PathInterpolator pathInterpolator6 = edgePanelParams.edgeCornerInterpolator;
                    if (pathInterpolator6 == null) {
                        pathInterpolator6 = null;
                    }
                    float interpolation8 = pathInterpolator6.getInterpolation(floatValue3);
                    PathInterpolator pathInterpolator7 = edgePanelParams.farCornerInterpolator;
                    if (pathInterpolator7 == null) {
                        pathInterpolator7 = null;
                    }
                    float interpolation9 = pathInterpolator7.getInterpolation(floatValue3);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens5 = edgePanelParams.preThresholdIndicator;
                    backPanel4.setStretch(0.0f, interpolation6, floatValue4, coerceAtLeast, interpolation7, interpolation8, interpolation9, backIndicatorDimens5 != null ? backIndicatorDimens5 : null);
                } else if (i4 == 3) {
                    float floatValue5 = valueOf.floatValue();
                    BackPanel backPanel5 = (BackPanel) this.mView;
                    PathInterpolator pathInterpolator8 = edgePanelParams.horizontalTranslationInterpolator;
                    if (pathInterpolator8 == null) {
                        pathInterpolator8 = null;
                    }
                    float interpolation10 = pathInterpolator8.getInterpolation(floatValue5);
                    Interpolator interpolator4 = edgePanelParams.arrowAngleInterpolator;
                    if (interpolator4 == null) {
                        interpolator4 = null;
                    }
                    float interpolation11 = interpolator4.getInterpolation(floatValue5);
                    PathInterpolator pathInterpolator9 = edgePanelParams.activeWidthInterpolator;
                    if (pathInterpolator9 == null) {
                        pathInterpolator9 = null;
                    }
                    float interpolation12 = pathInterpolator9.getInterpolation(floatValue5);
                    EdgePanelParams.BackIndicatorDimens backIndicatorDimens6 = edgePanelParams.fullyStretchedIndicator;
                    backPanel5.setStretch(interpolation10, interpolation11, 1.0f, interpolation12, 1.0f, 1.0f, 1.0f, backIndicatorDimens6 != null ? backIndicatorDimens6 : null);
                }
            }
            switch (iArr[this.currentState.ordinal()]) {
                case 1:
                case 2:
                    break;
                case 3:
                case 5:
                case 6:
                    valueOf = Float.valueOf(1.0f);
                    break;
                case 4:
                case 7:
                    valueOf = Float.valueOf(0.0f);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i5 = iArr[this.currentState.ordinal()];
            if (i5 == 1 ? (backIndicatorDimens = edgePanelParams.entryIndicator) == null : i5 == 2 ? (backIndicatorDimens = edgePanelParams.preThresholdIndicator) == null : i5 == 3 ? (backIndicatorDimens = edgePanelParams.activeIndicator) == null : (backIndicatorDimens = edgePanelParams.preThresholdIndicator) == null) {
                backIndicatorDimens = null;
            }
            if (valueOf != null) {
                float floatValue6 = valueOf.floatValue();
                Step step3 = backIndicatorDimens.arrowDimens.alphaSpring;
                if (step3 != null && (value = step3.get(floatValue6)) != null) {
                    if (!value.isNewState) {
                        value = null;
                    }
                    if (value != null) {
                        BackPanel backPanel6 = (BackPanel) this.mView;
                        SpringForce springForce = (SpringForce) value.value;
                        BackPanel.AnimatedFloat animatedFloat = backPanel6.arrowAlpha;
                        SpringAnimation springAnimation = animatedFloat.animation;
                        springAnimation.cancel();
                        springAnimation.mVelocity = 0.0f;
                        if (springForce != null) {
                            springAnimation.mSpring = springForce;
                        }
                        springAnimation.animateToFinalPosition(animatedFloat.restingPosition + 0.0f);
                    }
                }
            }
            float abs2 = Math.abs(y);
            float height = ((BackPanel) this.mView).getHeight();
            EdgePanelParams.BackIndicatorDimens backIndicatorDimens7 = edgePanelParams.entryIndicator;
            if (backIndicatorDimens7 == null) {
                backIndicatorDimens7 = null;
            }
            float f3 = (height - backIndicatorDimens7.backgroundDimens.height) / 2.0f;
            float saturate = MathUtils.saturate(abs2 / (15.0f * f3));
            PathInterpolator pathInterpolator10 = edgePanelParams.verticalTranslationInterpolator;
            if (pathInterpolator10 == null) {
                pathInterpolator10 = null;
            }
            BackPanel.AnimatedFloat.stretchTo$default(((BackPanel) this.mView).verticalTranslation, Math.signum(y) * pathInterpolator10.getInterpolation(saturate) * f3, null, 6);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
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
        edgePanelParams.activeIndicator = EdgePanelParams.BackIndicatorDimens.copy$default(backIndicatorDimens, 0.0f, null, null, EdgePanelParamsKt.createSpring(f, f2), null, 191);
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
            int i2 = iArr[this.currentState.ordinal()];
            SpringForce springForce = null;
            if (i2 == 1 || i2 == 2) {
                NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
                if (backCallback == null) {
                    backCallback = null;
                }
                backCallback.setTriggerBack(false);
            } else if (i2 == 3) {
                NavigationEdgeBackPlugin.BackCallback backCallback2 = this.backCallback;
                if (backCallback2 == null) {
                    backCallback2 = null;
                }
                backCallback2.setTriggerBack(true);
            } else if (i2 == 5 || i2 == 6) {
                if (this.previousState != GestureState.FLUNG) {
                    NavigationEdgeBackPlugin.BackCallback backCallback3 = this.backCallback;
                    if (backCallback3 == null) {
                        backCallback3 = null;
                    }
                    backCallback3.triggerBack();
                }
            } else if (i2 == 7) {
                NavigationEdgeBackPlugin.BackCallback backCallback4 = this.backCallback;
                if (backCallback4 == null) {
                    backCallback4 = null;
                }
                backCallback4.cancelBack();
            }
            int i3 = iArr[this.currentState.ordinal()];
            EdgePanelParams edgePanelParams = this.params;
            VibratorHelper vibratorHelper = this.vibratorHelper;
            SystemClock systemClock = this.systemClock;
            DelayedOnAnimationEndListener delayedOnAnimationEndListener = this.onEndSetGoneStateListener;
            Handler handler = this.mainHandler;
            switch (i3) {
                case 1:
                    this.mView.setVisibility(0);
                    updateRestingArrowDimens();
                    this.gestureEntryTime = systemClock.uptimeMillis();
                    break;
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
                    break;
                case 3:
                    this.previousXTranslationOnActiveOffset = this.previousXTranslation;
                    updateRestingArrowDimens();
                    if (z2) {
                        vibratorHelper.cancel();
                        handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BackPanelController.this.vibratorHelper.vibrateGesture();
                            }
                        }, 10L);
                    } else {
                        T t2 = this.mView;
                        vibratorHelper.getClass();
                        t2.performHapticFeedback(23);
                    }
                    ((BackPanel) this.mView).popOffEdge(this.previousState == GestureState.INACTIVE ? 4.7f : 4.5f);
                    break;
                case 4:
                    updateRestingArrowDimens();
                    this.mView.setVisibility(8);
                    break;
                case 5:
                    if (this.previousState != GestureState.ACTIVE) {
                        T t3 = this.mView;
                        vibratorHelper.getClass();
                        t3.performHapticFeedback(23);
                    }
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$updateArrowState$$inlined$postDelayed$default$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            View view;
                            view = ((ViewController) BackPanelController.this).mView;
                            BackPanel backPanel = (BackPanel) view;
                            backPanel.scalePivotX.snapTo(backPanel.backgroundWidth.pos / 2);
                            BackPanel.AnimatedFloat.stretchTo$default(backPanel.scale, 0.0f, Float.valueOf(2.0f), 4);
                        }
                    }, 60L);
                    handler.postDelayed(this.onEndSetCommittedStateListener.runnable, 160L);
                    updateRestingArrowDimens();
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
                    break;
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
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        T t = this.mView;
        if (t == 0) {
            Log.d("BackPanelController", "updateBackPanelColor fail, view is null");
        } else {
            ((BackPanel) t).updateBackPanelColor$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, i2, i3, i4);
            updateConfiguration$1();
        }
    }

    public final void updateConfiguration$1() {
        Resources resources = getResources();
        EdgePanelParams edgePanelParams = this.params;
        edgePanelParams.update(resources);
        ((BackPanel) this.mView).updateArrowPaint$frameworks__base__packages__SystemUI__android_common__SystemUI_core(edgePanelParams.arrowThickness);
        this.minFlingDistance = this.viewConfiguration.getScaledTouchSlop() * 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:205:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x02d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRestingArrowDimens() {
        /*
            Method dump skipped, instructions count: 990
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.BackPanelController.updateRestingArrowDimens():void");
    }

    public static /* synthetic */ void getCurrentState$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setInsets(int i, int i2) {
    }
}
