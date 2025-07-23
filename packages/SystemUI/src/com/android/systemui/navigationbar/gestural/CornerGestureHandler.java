package com.android.systemui.navigationbar.gestural;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.os.Handler;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.progressTouchSlop = context.getResources().getDimension(android.R.dimen.secondary_waterfall_display_left_edge_size) * 0.5f;
        this.FLAG_NAVSTAR_ASSISTANT_ENABLED = 8;
        this.ACTION_LOCK_TASK_MODE = "com.samsung.android.action.LOCK_TASK_MODE";
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, CornerGestureHandler.this.ACTION_LOCK_TASK_MODE)) {
                    CornerGestureHandler.this.isInLockTaskMode = intent.getBooleanExtra("enable", false);
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isInLockTaskMode=", "CornerGestureHandler", CornerGestureHandler.this.isInLockTaskMode);
                }
            }
        };
        this.intentFilter = AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("com.samsung.android.action.LOCK_TASK_MODE");
        this.bgHandler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.NAVBAR_BG_HANDLER);
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        if (this.allowGesture && !this.startAssistant) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(this.lastProgress, 0.0f);
            ofFloat.setDuration(300L);
            ofFloat.setInterpolator(new DecelerateInterpolator(2.0f));
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((AssistManager) CornerGestureHandler.this.assistManagerLazy.get()).onInvocationProgress(((Float) ofFloat.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ((AssistManager) CornerGestureHandler.this.assistManagerLazy.get()).onInvocationProgress(0.0f);
                }
            });
            ofFloat.start();
        }
        this.timeFraction = 1.0f;
        this.allowGesture = false;
        this.startAssistant = false;
        this.isPilfered = false;
        this.lastProgress = 0.0f;
        this.distance = 0.0f;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        obtain.recycle();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|1|(4:7|(1:9)|10|(13:16|17|(1:19)|20|(1:22)|23|(5:25|(1:27)(1:32)|28|(1:30)|31)|33|34|35|(1:37)|38|39))|43|17|(0)|20|(0)|23|(0)|33|34|35|(0)|38|39) */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIsEnabled() {
        /*
            r14 = this;
            dagger.Lazy r0 = r14.assistManagerLazy
            java.lang.Object r0 = r0.get()
            com.android.systemui.assist.AssistManager r0 = (com.android.systemui.assist.AssistManager) r0
            com.android.systemui.settings.UserTracker r1 = r14.userTracker
            com.android.systemui.settings.UserTrackerImpl r1 = (com.android.systemui.settings.UserTrackerImpl) r1
            int r1 = r1.getUserId()
            com.android.internal.app.AssistUtils r0 = r0.mAssistUtils
            android.content.ComponentName r0 = r0.getAssistComponentForUser(r1)
            r1 = 0
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L43
            boolean r0 = r14.isAttached
            if (r0 == 0) goto L43
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r14.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            boolean r0 = r0.isGestureMode()
            if (r0 != 0) goto L43
            com.android.systemui.navigationbar.NavBarHelper r0 = r14.navBarHelper
            if (r0 != 0) goto L2e
            r0 = r3
        L2e:
            boolean r0 = r0.mLongPressHomeEnabled
            if (r0 == 0) goto L43
            boolean r0 = com.android.systemui.BasicRune.SUPPORT_AI_AGENT
            if (r0 == 0) goto L41
            com.android.systemui.util.SettingsHelper r0 = r14.settingsHelper
            int r0 = r0.getNavigationBarSPluginFlags()
            int r4 = r14.FLAG_NAVSTAR_ASSISTANT_ENABLED
            r0 = r0 & r4
            if (r0 == 0) goto L43
        L41:
            r0 = r2
            goto L44
        L43:
            r0 = r1
        L44:
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r4 = r14.inputEventReceiver
            if (r4 == 0) goto L4b
            r4.dispose()
        L4b:
            r14.inputEventReceiver = r3
            android.view.InputMonitor r4 = r14.inputMonitor
            if (r4 == 0) goto L54
            r4.dispose()
        L54:
            r14.inputMonitor = r3
            com.android.systemui.navigationbar.gestural.CornerGestureHandler$broadcastReceiver$1 r4 = r14.broadcastReceiver
            com.android.systemui.broadcast.BroadcastDispatcher r5 = r14.broadcastDispatcher
            r5.unregisterReceiver(r4)
            if (r0 == 0) goto La1
            android.content.Context r0 = r14.context
            java.lang.Class<android.hardware.input.InputManager> r4 = android.hardware.input.InputManager.class
            java.lang.Object r0 = r0.getSystemService(r4)
            android.hardware.input.InputManager r0 = (android.hardware.input.InputManager) r0
            if (r0 == 0) goto L74
            java.lang.String r4 = "corner-swipe"
            int r5 = r14.displayId
            android.view.InputMonitor r0 = r0.monitorGestureInput(r4, r5)
            goto L75
        L74:
            r0 = r3
        L75:
            r14.inputMonitor = r0
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r4 = new com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver
            if (r0 == 0) goto L7f
            android.view.InputChannel r3 = r0.getInputChannel()
        L7f:
            android.os.Looper r0 = android.os.Looper.getMainLooper()
            android.view.Choreographer r5 = android.view.Choreographer.getInstance()
            com.android.systemui.navigationbar.gestural.CornerGestureHandler$setInputChannel$1 r6 = new com.android.systemui.navigationbar.gestural.CornerGestureHandler$setInputChannel$1
            r6.<init>()
            r4.<init>(r3, r0, r5, r6)
            r14.inputEventReceiver = r4
            com.android.systemui.navigationbar.gestural.CornerGestureHandler$broadcastReceiver$1 r8 = r14.broadcastReceiver
            android.content.IntentFilter r9 = r14.intentFilter
            android.os.UserHandle r11 = android.os.UserHandle.ALL
            android.os.Handler r10 = r14.bgHandler
            r12 = 0
            com.android.systemui.broadcast.BroadcastDispatcher r7 = r14.broadcastDispatcher
            r13 = 48
            com.android.systemui.broadcast.BroadcastDispatcher.registerReceiverWithHandler$default(r7, r8, r9, r10, r11, r12, r13)
        La1:
            com.android.systemui.shared.system.ActivityManagerWrapper r0 = com.android.systemui.shared.system.ActivityManagerWrapper.sInstance
            r0.getClass()
            android.app.IActivityTaskManager r0 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> Lb1
            int r0 = r0.getLockTaskModeState()     // Catch: android.os.RemoteException -> Lb1
            if (r0 == 0) goto Lb1
            r1 = r2
        Lb1:
            r14.isInLockTaskMode = r1
            java.lang.String r14 = "isInLockTaskMode="
            java.lang.String r0 = "CornerGestureHandler"
            com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m(r14, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.CornerGestureHandler.updateIsEnabled():void");
    }
}
