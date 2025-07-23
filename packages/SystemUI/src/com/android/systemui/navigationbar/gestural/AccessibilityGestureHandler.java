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
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.gestural.MotionPauseDetector;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public AccessibilityGestureHandler(Context context, NavBarHelper navBarHelper, NavBarStore navBarStore, DisplayManager displayManager) {
        this.context = context;
        this.navBarHelper = navBarHelper;
        this.navBarStore = navBarStore;
        Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            context = context.createDisplayContext(displays[1]);
            context.getClass();
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
            EmergencyButtonController$$ExternalSyntheticOutline0.m("updateAccessibilityGestureDetected: ", "AccessibilityGestureHandler", z);
            this.gestureDetected = z;
            ArrayList arrayList = (ArrayList) this.navBarHelper.mStateListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((NavBarHelper.NavbarTaskbarStateUpdater) obj).updateAccessibilityGestureDetected(z);
            }
        }
    }

    public final void updateIsEnabled() {
        if (!this.isAttached) {
            disposeInputChannel();
            return;
        }
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(1);
        if (!navBarStateManagerImpl.states.supportLargeCoverScreen) {
            disposeInputChannel();
            return;
        }
        boolean isLargeCoverScreenSyncEnabled = navBarStateManagerImpl.isLargeCoverScreenSyncEnabled();
        boolean isGestureMode = navBarStateManagerImpl.isGestureMode();
        NavBarHelper navBarHelper = this.navBarHelper;
        boolean z = false;
        boolean z2 = (navBarHelper.mA11yButtonState & 32) != 0;
        boolean z3 = new NavBarHelper.CurrentSysuiState(navBarHelper, 1).mWindowState != 2;
        if (z2 && ((!isGestureMode && ((isLargeCoverScreenSyncEnabled || this.isCoverNavBarVisible) && z3)) || !z3)) {
            z = true;
        }
        boolean z4 = this.isCoverNavBarVisible;
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("a11yButtonState: ", ", coverScreenNavBarEnabled: ", " gestureMode: ", z2, isLargeCoverScreenSyncEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isGestureMode, ", isWindowShowing: ", z3, ", isCoverNavBarVisible: ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(m, z4, ", isEnabled: ", z, "AccessibilityGestureHandler");
        disposeInputChannel();
        if (z) {
            InputManager inputManager = (InputManager) this.coverContext.getSystemService(InputManager.class);
            InputMonitor monitorGestureInput = inputManager != null ? inputManager.monitorGestureInput("a11yGesture-swipe", 1) : null;
            this.inputMonitor = monitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(monitorGestureInput != null ? monitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1
                /* JADX WARN: Code restructure failed: missing block: B:132:0x01f3, code lost:
                
                    if ((-r0.floatValue()) > r11.inFlingVelocity) goto L128;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.navigationbar.gestural.MotionPauseDetector$$ExternalSyntheticLambda0] */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onInputEvent(android.view.InputEvent r12) {
                    /*
                        Method dump skipped, instructions count: 519
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1.onInputEvent(android.view.InputEvent):void");
                }
            });
        }
    }
}
