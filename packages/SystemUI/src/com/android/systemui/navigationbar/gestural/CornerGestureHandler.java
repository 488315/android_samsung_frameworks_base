package com.android.systemui.navigationbar.gestural;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class CornerGestureHandler {
    public final String ACTION_LOCK_TASK_MODE;
    public final int FLAG_NAVSTAR_ASSISTANT_ENABLED;
    public boolean allowGesture;
    public final Lazy assistManagerLazy;
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CornerGestureHandler$broadcastReceiver$1 broadcastReceiver;
    public final Context context;
    public final float degreeEnd;
    public final float degreeStart;
    public final int displayId;
    public float distance;
    public final PointF downPos;
    public long downTime;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitor inputMonitor;
    public final IntentFilter intentFilter;
    public boolean isAttached;
    public boolean isInLockTaskMode;
    public boolean isPilfered;
    public float lastProgress;
    public NavBarHelper navBarHelper;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final float progressTouchSlop;
    private final SettingsHelper settingsHelper;
    public boolean startAssistant;
    public float timeFraction;
    public final float touchSlop;
    public final UserTracker userTracker;
    public final VibratorHelper vibratorHelper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.systemui.navigationbar.gestural.CornerGestureHandler$broadcastReceiver$1] */
    public CornerGestureHandler(Context context, NavBarStore navBarStore, LauncherProxyService launcherProxyService, BroadcastDispatcher broadcastDispatcher, SettingsHelper settingsHelper, Lazy lazy, UserTracker userTracker) {
        this.context = context;
        this.navBarStore = navBarStore;
        this.broadcastDispatcher = broadcastDispatcher;
        this.settingsHelper = settingsHelper;
        this.assistManagerLazy = lazy;
        this.userTracker = userTracker;
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
        this.vibratorHelper = (VibratorHelper) Dependency.sDependency.getDependencyInner(VibratorHelper.class);
        this.degreeStart = 110.0f;
        this.degreeEnd = 180.0f;
        this.downPos = new PointF();
        this.touchSlop = context.getResources().getDimension(R.dimen.gestures_assistant_drag_threshold);
        this.timeFraction = 1.0f;
        this.progressTouchSlop = context.getResources().getDimension(android.R.dimen.secondary_waterfall_display_right_edge_size) * 0.5f;
        this.FLAG_NAVSTAR_ASSISTANT_ENABLED = 8;
        this.ACTION_LOCK_TASK_MODE = "com.samsung.android.action.LOCK_TASK_MODE";
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, this.this$0.ACTION_LOCK_TASK_MODE)) {
                    this.this$0.isInLockTaskMode = intent.getBooleanExtra("enable", false);
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isInLockTaskMode=", "CornerGestureHandler", this.this$0.isInLockTaskMode);
                }
            }
        };
        this.intentFilter = AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("com.samsung.android.action.LOCK_TASK_MODE");
        this.bgHandler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.NAVBAR_BG_HANDLER);
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        if (this.allowGesture && !this.startAssistant) {
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.lastProgress, 0.0f);
            valueAnimatorOfFloat.setDuration(300L);
            valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(2.0f));
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((AssistManager) this.this$0.assistManagerLazy.get()).onInvocationProgress(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ((AssistManager) this.this$0.assistManagerLazy.get()).onInvocationProgress(0.0f);
                }
            });
            valueAnimatorOfFloat.start();
        }
        this.timeFraction = 1.0f;
        this.allowGesture = false;
        this.startAssistant = false;
        this.isPilfered = false;
        this.lastProgress = 0.0f;
        this.distance = 0.0f;
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.setAction(3);
        motionEventObtain.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIsEnabled() {
        boolean z;
        boolean z2 = false;
        if (((AssistManager) this.assistManagerLazy.get()).mAssistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId()) == null || !this.isAttached || ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            z = false;
        } else {
            NavBarHelper navBarHelper = this.navBarHelper;
            if (navBarHelper == null) {
                navBarHelper = null;
            }
            if (navBarHelper.mLongPressHomeEnabled && (!BasicRune.SUPPORT_AI_AGENT || (this.settingsHelper.getNavigationBarSPluginFlags() & this.FLAG_NAVSTAR_ASSISTANT_ENABLED) != 0)) {
                z = true;
            }
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
        }
        this.inputEventReceiver = null;
        InputMonitor inputMonitor = this.inputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
        }
        this.inputMonitor = null;
        this.broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
        if (z) {
            InputManager inputManager = (InputManager) this.context.getSystemService(InputManager.class);
            InputMonitor inputMonitorMonitorGestureInput = inputManager != null ? inputManager.monitorGestureInput("corner-swipe", this.displayId) : null;
            this.inputMonitor = inputMonitorMonitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorMonitorGestureInput != null ? inputMonitorMonitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$setInputChannel$1
                /* JADX WARN: Removed duplicated region for block: B:53:0x0142  */
                /* JADX WARN: Removed duplicated region for block: B:62:0x0161  */
                /* JADX WARN: Removed duplicated region for block: B:67:0x016a  */
                /* JADX WARN: Removed duplicated region for block: B:80:0x019a  */
                /* JADX WARN: Removed duplicated region for block: B:90:0x01b8  */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onInputEvent(InputEvent inputEvent) throws PackageManager.NameNotFoundException {
                    CornerGestureHandler cornerGestureHandler = this.$tmp0;
                    cornerGestureHandler.getClass();
                    if (inputEvent instanceof MotionEvent) {
                        MotionEvent motionEvent = (MotionEvent) inputEvent;
                        int actionMasked = motionEvent.getActionMasked();
                        boolean z3 = true;
                        if (actionMasked == 0) {
                            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                                float x = motionEvent.getX();
                                float y = motionEvent.getY();
                                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) cornerGestureHandler.navBarStateManager;
                                int i = navBarStateManagerImpl.states.rotation;
                                float navBarHeight = navBarStateManagerImpl.getNavBarHeight(0);
                                if (i == 0) {
                                    float f = navBarStateManagerImpl.states.displaySize.y;
                                    float f2 = f - navBarHeight;
                                    float spaceWidth = navBarStateManagerImpl.getSpaceWidth(false);
                                    float f3 = navBarStateManagerImpl.states.displaySize.x;
                                    if (f2 > y || y > f || ((0.0f > x || x > spaceWidth) && (f3 - spaceWidth > x || x > f3))) {
                                        z3 = false;
                                    } else if (!QuickStepContract.isAssistantGestureDisabled(((NavBarStoreImpl) cornerGestureHandler.navBarStore).sysUiFlagContainer.getFlags())) {
                                        NavBarHelper navBarHelper2 = cornerGestureHandler.navBarHelper;
                                        if (navBarHelper2 == null) {
                                            navBarHelper2 = null;
                                        }
                                        if (!navBarHelper2.mAssistantTouchGestureEnabled || cornerGestureHandler.isInLockTaskMode) {
                                        }
                                    }
                                } else if (i == 1) {
                                    Point point = navBarStateManagerImpl.states.displaySize;
                                    float f4 = point.y;
                                    float f5 = f4 - navBarHeight;
                                    if (0.0f > x || x > navBarHeight) {
                                        float f6 = point.x;
                                        float f7 = f6 - navBarHeight;
                                        if (x <= f6 && f7 <= x) {
                                            if (f5 > y || y > f4) {
                                            }
                                        }
                                        z3 = false;
                                    }
                                } else {
                                    if (i != 2) {
                                        if (i == 3) {
                                        }
                                    }
                                    z3 = false;
                                }
                            }
                            cornerGestureHandler.allowGesture = z3;
                            if (z3) {
                                Log.d("CornerGestureHandler", "allow down x: " + motionEvent.getX() + ", y:" + motionEvent.getY());
                                cornerGestureHandler.downPos.set(motionEvent.getX(), motionEvent.getY());
                                cornerGestureHandler.downTime = SystemClock.uptimeMillis();
                                return;
                            }
                            return;
                        }
                        if (actionMasked != 1) {
                            if (actionMasked == 2) {
                                if (!cornerGestureHandler.allowGesture || cornerGestureHandler.startAssistant) {
                                    return;
                                }
                                float fHypot = (float) Math.hypot(motionEvent.getX() - cornerGestureHandler.downPos.x, motionEvent.getY() - cornerGestureHandler.downPos.y);
                                cornerGestureHandler.distance = fHypot;
                                boolean z4 = cornerGestureHandler.isPilfered;
                                float f8 = cornerGestureHandler.progressTouchSlop;
                                if (!z4 && fHypot > f8) {
                                    InputMonitor inputMonitor2 = cornerGestureHandler.inputMonitor;
                                    if (inputMonitor2 != null) {
                                        inputMonitor2.pilferPointers();
                                    }
                                    cornerGestureHandler.isPilfered = true;
                                }
                                float f9 = cornerGestureHandler.distance;
                                float f10 = cornerGestureHandler.touchSlop;
                                Lazy lazy = cornerGestureHandler.assistManagerLazy;
                                if (f9 <= f10) {
                                    if (f9 >= f8) {
                                        cornerGestureHandler.lastProgress = Math.min((f9 - f8) / (f10 - f8), Math.min(cornerGestureHandler.timeFraction, 1.0f));
                                        ((AssistManager) lazy.get()).onInvocationProgress(cornerGestureHandler.lastProgress);
                                        return;
                                    }
                                    return;
                                }
                                float fUptimeMillis = SystemClock.uptimeMillis() - cornerGestureHandler.downTime;
                                cornerGestureHandler.timeFraction = Math.min(fUptimeMillis / 50, 1.0f);
                                double dAbs = Math.abs((((float) Math.atan2(r5, r0)) * 180) / 3.141592653589793d);
                                if (dAbs > cornerGestureHandler.degreeEnd || cornerGestureHandler.degreeStart > dAbs || cornerGestureHandler.timeFraction < 1.0f) {
                                    Log.d("CornerGestureHandler", "cancelGesture by degree=" + dAbs + " diff=" + fUptimeMillis + " timeFraction=" + cornerGestureHandler.timeFraction);
                                    cornerGestureHandler.cancelGesture(motionEvent);
                                    return;
                                }
                                Log.d("CornerGestureHandler", "Execute Assistant");
                                DefaultUiController defaultUiController = ((AssistManager) lazy.get()).mUiController;
                                defaultUiController.animateInvocationCompletion();
                                defaultUiController.logInvocationProgressMetrics(1.0f, defaultUiController.mInvocationInProgress);
                                Bundle bundle = new Bundle();
                                bundle.putInt("invocation_type", 1);
                                ((AssistManager) lazy.get()).startAssist(bundle);
                                cornerGestureHandler.startAssistant = true;
                                cornerGestureHandler.vibratorHelper.vibrateGesture();
                                return;
                            }
                            if (actionMasked != 3 && actionMasked != 5 && actionMasked != 6) {
                                return;
                            }
                        }
                        cornerGestureHandler.cancelGesture(motionEvent);
                    }
                }
            });
            BroadcastDispatcher.registerReceiverWithHandler$default(this.broadcastDispatcher, this.broadcastReceiver, this.intentFilter, this.bgHandler, UserHandle.ALL, null, 48);
        }
        ActivityManagerWrapper.sInstance.getClass();
        try {
            if (ActivityTaskManager.getService().getLockTaskModeState() != 0) {
                z2 = true;
            }
        } catch (RemoteException unused) {
        }
        this.isInLockTaskMode = z2;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isInLockTaskMode=", "CornerGestureHandler", z2);
    }
}
