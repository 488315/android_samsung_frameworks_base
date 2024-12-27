package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.gestural.MotionPauseDetector;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AccessibilityGestureHandler implements MotionPauseListener {
    public int activePointerId;
    public final Context context;
    public final Context coverContext;
    public float downY;
    public boolean gestureDetected;
    public final int inFlingVelocity;
    public final int inGestureDistance;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitor inputMonitor;
    public boolean isAttached;
    public boolean isCoverNavBarVisible;
    public boolean isPaused;
    public MotionPauseDetector motionPauseDetector;
    public final NavBarHelper navBarHelper;
    public final NavBarStore navBarStore;
    public float totalY;
    public VelocityTracker velocityTracker;
    public final String tag = "AccessibilityGestureHandler";
    public final int displayId = 1;

    public AccessibilityGestureHandler(Context context, NavBarHelper navBarHelper, NavBarStore navBarStore, DisplayManager displayManager) {
        this.context = context;
        this.navBarHelper = navBarHelper;
        this.navBarStore = navBarStore;
        Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            context = context.createDisplayContext(displays[1]);
            Intrinsics.checkNotNull(context);
        }
        this.coverContext = context;
        ViewConfiguration.get(context).getScaledTouchSlop();
        this.inGestureDistance = context.getResources().getDimensionPixelSize(R.dimen.large_cover_accessibility_gesture_threshold);
        this.inFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public final void clear(MotionEvent motionEvent) {
        MotionPauseDetector motionPauseDetector = this.motionPauseDetector;
        if (motionPauseDetector != null) {
            Log.d(motionPauseDetector.tag, "clear");
            try {
                MotionPauseDetector.SystemVelocityProvider systemVelocityProvider = motionPauseDetector.velocityProvider;
                systemVelocityProvider.velocityTracker.clear();
                systemVelocityProvider.velocityTracker.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            motionPauseDetector.previousVelocity = null;
            motionPauseDetector.hasEverBeenPaused = false;
            motionPauseDetector.isPaused = false;
            motionPauseDetector.slowStartTime = 0L;
            motionPauseDetector.timer.cancel();
        }
        this.motionPauseDetector = null;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.velocityTracker = null;
        updateAccessibilityGestureDetected(false);
        this.totalY = 0.0f;
        this.downY = 0.0f;
        this.activePointerId = 0;
        this.isPaused = false;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        obtain.recycle();
    }

    public final void disposeInputChannel() {
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
    }

    public final void updateAccessibilityGestureDetected(boolean z) {
        if (this.gestureDetected != z) {
            Log.d(this.tag, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("updateAccessibilityGestureDetected: ", z));
            this.gestureDetected = z;
            Iterator it = ((ArrayList) this.navBarHelper.mStateListeners).iterator();
            while (it.hasNext()) {
                ((NavBarHelper.NavbarTaskbarStateUpdater) it.next()).updateAccessibilityGestureDetected(z);
            }
        }
    }

    public final void updateIsEnabled() {
        if (!this.isAttached) {
            disposeInputChannel();
            return;
        }
        NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
        int i = this.displayId;
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStoreImpl.getNavStateManager(i);
        if (!navBarStateManagerImpl.states.supportLargeCoverScreen) {
            disposeInputChannel();
            return;
        }
        boolean isLargeCoverScreenSyncEnabled = navBarStateManagerImpl.isLargeCoverScreenSyncEnabled();
        boolean isGestureMode = navBarStateManagerImpl.isGestureMode();
        NavBarHelper navBarHelper = this.navBarHelper;
        boolean z = false;
        boolean z2 = (navBarHelper.mA11yButtonState & 32) != 0;
        navBarHelper.getClass();
        boolean z3 = new NavBarHelper.CurrentSysuiState(navBarHelper, i).mWindowState != 2;
        if (z2 && ((!isGestureMode && ((isLargeCoverScreenSyncEnabled || this.isCoverNavBarVisible) && z3)) || !z3)) {
            z = true;
        }
        boolean z4 = this.isCoverNavBarVisible;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("a11yButtonState: ", ", coverScreenNavBarEnabled: ", " gestureMode: ", z2, isLargeCoverScreenSyncEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isGestureMode, ", isWindowShowing: ", z3, ", isCoverNavBarVisible: ");
        Log.d(this.tag, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z4, ", isEnabled: ", z));
        disposeInputChannel();
        if (z) {
            InputManager inputManager = (InputManager) this.coverContext.getSystemService(InputManager.class);
            InputMonitor monitorGestureInput = inputManager != null ? inputManager.monitorGestureInput("a11yGesture-swipe", i) : null;
            this.inputMonitor = monitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(monitorGestureInput != null ? monitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1
                /* JADX WARN: Code restructure failed: missing block: B:132:0x01fe, code lost:
                
                    if ((-r0.floatValue()) > r11.inFlingVelocity) goto L124;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onInputEvent(android.view.InputEvent r12) {
                    /*
                        Method dump skipped, instructions count: 530
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1.onInputEvent(android.view.InputEvent):void");
                }
            });
        }
    }
}
