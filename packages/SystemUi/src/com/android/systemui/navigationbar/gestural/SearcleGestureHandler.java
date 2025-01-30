package com.android.systemui.navigationbar.gestural;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SearcleGestureHandler {
    public final String ACTION_LOCK_TASK_MODE;
    public final String TAG = "SearcleGestureHandler";
    public boolean allowGesture;
    public final AssistManager assistManager;
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public final SearcleGestureHandler$broadcastReceiver$1 broadcastReceiver;
    public final Context context;
    public final float degreeEnd;
    public final float degreeStart;
    public final int displayId;
    public float distance;
    public final PointF downPoint;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitor inputMonitor;
    public final IntentFilter intentFilter;
    public boolean isAttached;
    public boolean isInLockTaskMode;
    public boolean isPilfered;
    public final NavBarHelper navBarHelper;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final float scrollTouchSlop;
    public boolean startSearcle;
    public final float touchSlop;
    public final VibratorHelper vibratorHelper;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.navigationbar.gestural.SearcleGestureHandler$broadcastReceiver$1] */
    public SearcleGestureHandler(Context context, NavBarHelper navBarHelper, NavBarStore navBarStore, OverviewProxyService overviewProxyService, BroadcastDispatcher broadcastDispatcher, AssistManager assistManager) {
        this.context = context;
        this.navBarHelper = navBarHelper;
        this.navBarStore = navBarStore;
        this.broadcastDispatcher = broadcastDispatcher;
        this.assistManager = assistManager;
        this.scrollTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
        this.vibratorHelper = (VibratorHelper) Dependency.get(VibratorHelper.class);
        this.degreeStart = 110.0f;
        this.degreeEnd = 180.0f;
        this.ACTION_LOCK_TASK_MODE = "com.samsung.android.action.LOCK_TASK_MODE";
        this.downPoint = new PointF();
        this.touchSlop = context.getResources().getDimension(R.dimen.gestures_assistant_drag_threshold);
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.gestural.SearcleGestureHandler$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, SearcleGestureHandler.this.ACTION_LOCK_TASK_MODE)) {
                    SearcleGestureHandler.this.isInLockTaskMode = intent.getBooleanExtra("enable", false);
                    SearcleGestureHandler searcleGestureHandler = SearcleGestureHandler.this;
                    ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isInLockTaskMode=", searcleGestureHandler.isInLockTaskMode, searcleGestureHandler.TAG);
                }
            }
        };
        this.intentFilter = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.action.LOCK_TASK_MODE");
        this.bgHandler = (Handler) Dependency.get(Dependency.NAVBAR_BG_HANDLER);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0076, code lost:
    
        if (android.app.ActivityTaskManager.getService().getLockTaskModeState() != 0) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIsEnabled() {
        boolean z = true;
        boolean z2 = this.isAttached && !this.navBarStateManager.isGestureMode();
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
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        SearcleGestureHandler$broadcastReceiver$1 searcleGestureHandler$broadcastReceiver$1 = this.broadcastReceiver;
        broadcastDispatcher.unregisterReceiver(searcleGestureHandler$broadcastReceiver$1);
        if (z2) {
            InputMonitor monitorGestureInput = ((InputManager) this.context.getSystemService(InputManager.class)).monitorGestureInput("searcle-swipe", this.displayId);
            this.inputMonitor = monitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(monitorGestureInput != null ? monitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.SearcleGestureHandler$setInputChannel$1
                /* JADX WARN: Code restructure failed: missing block: B:46:0x00ef, code lost:
                
                    if (r10 != 3) goto L87;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:67:0x0119, code lost:
                
                    if ((r0 <= r3 && r3 - r9 <= r0) != false) goto L57;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:74:0x0126, code lost:
                
                    if ((r8 <= r7 && r7 <= r4) != false) goto L86;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:95:0x0165, code lost:
                
                    if ((r8 - r9 <= r0 && r0 <= r8) != false) goto L86;
                 */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onInputEvent(InputEvent inputEvent) {
                    boolean z3;
                    SearcleGestureHandler searcleGestureHandler = SearcleGestureHandler.this;
                    searcleGestureHandler.getClass();
                    if (inputEvent instanceof MotionEvent) {
                        MotionEvent motionEvent = (MotionEvent) inputEvent;
                        int actionMasked = motionEvent.getActionMasked();
                        PointF pointF = searcleGestureHandler.downPoint;
                        boolean z4 = false;
                        String str = searcleGestureHandler.TAG;
                        if (actionMasked == 0) {
                            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                                float x = motionEvent.getX();
                                float y = motionEvent.getY();
                                NavBarStateManager navBarStateManager = searcleGestureHandler.navBarStateManager;
                                NavBarStateManager.States states = navBarStateManager.states;
                                int i = states.rotation;
                                float barHeight = navBarStateManager.navBarLayoutParams.getBarHeight(states.canMove, 0);
                                if (i != 0) {
                                    if (i != 1) {
                                        if (i != 2) {
                                        }
                                    }
                                    Point point = navBarStateManager.states.displaySize;
                                    float f = point.y;
                                    float f2 = f - barHeight;
                                    if (!(0.0f <= x && x <= barHeight)) {
                                        float f3 = point.x;
                                    }
                                }
                                float f4 = navBarStateManager.states.displaySize.y;
                                float f5 = f4 - barHeight;
                                float spaceWidth = navBarStateManager.getSpaceWidth(false);
                                float f6 = navBarStateManager.states.displaySize.x;
                                if (f5 <= y && y <= f4) {
                                    if (!(0.0f <= x && x <= spaceWidth)) {
                                    }
                                    z3 = true;
                                    if (z3 && !QuickStepContract.isAssistantGestureDisabled(((NavBarStoreImpl) searcleGestureHandler.navBarStore).sysUiFlagContainer.mFlags) && searcleGestureHandler.navBarHelper.mAssistantTouchGestureEnabled && !searcleGestureHandler.isInLockTaskMode) {
                                        z4 = true;
                                    }
                                }
                                z3 = false;
                                if (z3) {
                                    z4 = true;
                                }
                            }
                            searcleGestureHandler.allowGesture = z4;
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("allowGesture: ", z4, str);
                            if (searcleGestureHandler.allowGesture) {
                                Log.d(str, "allow down x: {" + motionEvent + ".x}, y:{" + motionEvent + ".y}");
                                pointF.set(motionEvent.getX(), motionEvent.getY());
                                return;
                            }
                            return;
                        }
                        if (actionMasked != 1) {
                            if (actionMasked == 2) {
                                if (!searcleGestureHandler.allowGesture || searcleGestureHandler.startSearcle) {
                                    return;
                                }
                                float hypot = (float) Math.hypot(motionEvent.getX() - pointF.x, motionEvent.getY() - pointF.y);
                                searcleGestureHandler.distance = hypot;
                                if (!searcleGestureHandler.isPilfered && hypot > searcleGestureHandler.scrollTouchSlop) {
                                    InputMonitor inputMonitor2 = searcleGestureHandler.inputMonitor;
                                    if (inputMonitor2 != null) {
                                        inputMonitor2.pilferPointers();
                                    }
                                    searcleGestureHandler.isPilfered = true;
                                }
                                if (searcleGestureHandler.distance > searcleGestureHandler.touchSlop) {
                                    double abs = Math.abs((((float) Math.atan2(r0, r7)) * 180) / 3.141592653589793d);
                                    if (!(abs <= ((double) searcleGestureHandler.degreeEnd) && ((double) searcleGestureHandler.degreeStart) <= abs)) {
                                        searcleGestureHandler.allowGesture = false;
                                        searcleGestureHandler.startSearcle = false;
                                        searcleGestureHandler.isPilfered = false;
                                        MotionEvent obtain = MotionEvent.obtain(motionEvent);
                                        obtain.setAction(3);
                                        obtain.recycle();
                                        return;
                                    }
                                    Log.d(str, "Execute Assistant");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("invocation_type", 5);
                                    searcleGestureHandler.assistManager.startAssist(bundle);
                                    searcleGestureHandler.startSearcle = true;
                                    searcleGestureHandler.vibratorHelper.vibrateGesture();
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "749007");
                                    return;
                                }
                                return;
                            }
                            if (actionMasked != 3) {
                                return;
                            }
                        }
                        searcleGestureHandler.allowGesture = false;
                        searcleGestureHandler.startSearcle = false;
                        searcleGestureHandler.isPilfered = false;
                        MotionEvent obtain2 = MotionEvent.obtain(motionEvent);
                        obtain2.setAction(3);
                        obtain2.recycle();
                    }
                }
            });
            BroadcastDispatcher.registerReceiverWithHandler$default(this.broadcastDispatcher, searcleGestureHandler$broadcastReceiver$1, this.intentFilter, this.bgHandler, UserHandle.ALL, 48);
        }
        ActivityManagerWrapper.sInstance.getClass();
        z = false;
        this.isInLockTaskMode = z;
        Log.i(this.TAG, AbstractC0866xb1ce8deb.m86m("isInLockTaskMode=", z));
    }
}
