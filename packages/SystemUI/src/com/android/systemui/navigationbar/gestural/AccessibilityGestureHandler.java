package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.InputEvent;
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
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

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
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.setAction(3);
        motionEventObtain.recycle();
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
        boolean zIsLargeCoverScreenSyncEnabled = navBarStateManagerImpl.isLargeCoverScreenSyncEnabled();
        boolean zIsGestureMode = navBarStateManagerImpl.isGestureMode();
        NavBarHelper navBarHelper = this.navBarHelper;
        boolean z = false;
        boolean z2 = (navBarHelper.mA11yButtonState & 32) != 0;
        boolean z3 = new NavBarHelper.CurrentSysuiState(navBarHelper, 1).mWindowState != 2;
        if (z2 && ((!zIsGestureMode && ((zIsLargeCoverScreenSyncEnabled || this.isCoverNavBarVisible) && z3)) || !z3)) {
            z = true;
        }
        boolean z4 = this.isCoverNavBarVisible;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("a11yButtonState: ", ", coverScreenNavBarEnabled: ", " gestureMode: ", z2, zIsLargeCoverScreenSyncEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zIsGestureMode, ", isWindowShowing: ", z3, ", isCoverNavBarVisible: ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sbM, z4, ", isEnabled: ", z, "AccessibilityGestureHandler");
        disposeInputChannel();
        if (z) {
            InputManager inputManager = (InputManager) this.coverContext.getSystemService(InputManager.class);
            InputMonitor inputMonitorMonitorGestureInput = inputManager != null ? inputManager.monitorGestureInput("a11yGesture-swipe", 1) : null;
            this.inputMonitor = inputMonitorMonitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorMonitorGestureInput != null ? inputMonitorMonitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:128:0x01f5  */
                /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.navigationbar.gestural.MotionPauseDetector$$ExternalSyntheticLambda0] */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onInputEvent(InputEvent inputEvent) {
                    int iFindPointerIndex;
                    final MotionPauseDetector motionPauseDetector;
                    String str;
                    boolean z5;
                    String str2;
                    AccessibilityGestureHandler accessibilityGestureHandler = this.$tmp0;
                    accessibilityGestureHandler.getClass();
                    if (inputEvent instanceof MotionEvent) {
                        final MotionEvent motionEvent = (MotionEvent) inputEvent;
                        if (accessibilityGestureHandler.velocityTracker == null) {
                            accessibilityGestureHandler.velocityTracker = VelocityTracker.obtain();
                        }
                        if (accessibilityGestureHandler.motionPauseDetector == null) {
                            accessibilityGestureHandler.motionPauseDetector = new MotionPauseDetector(accessibilityGestureHandler.context, true, accessibilityGestureHandler, true);
                        }
                        VelocityTracker velocityTracker = accessibilityGestureHandler.velocityTracker;
                        velocityTracker.getClass();
                        velocityTracker.addMovement(motionEvent);
                        int actionMasked = motionEvent.getActionMasked();
                        if (actionMasked == 1) {
                            if (accessibilityGestureHandler.gestureDetected && !accessibilityGestureHandler.isPaused) {
                                accessibilityGestureHandler.totalY = (motionEvent.getY() - accessibilityGestureHandler.downY) + accessibilityGestureHandler.totalY;
                                VelocityTracker velocityTracker2 = accessibilityGestureHandler.velocityTracker;
                                velocityTracker2.getClass();
                                velocityTracker2.computeCurrentVelocity(1000);
                                if ((-accessibilityGestureHandler.totalY) > accessibilityGestureHandler.inGestureDistance) {
                                    Log.d("AccessibilityGestureHandler", "accessibilityButtonClicked");
                                    accessibilityGestureHandler.navBarHelper.mAccessibilityManager.notifyAccessibilityButtonClicked(1);
                                } else {
                                    VelocityTracker velocityTracker3 = accessibilityGestureHandler.velocityTracker;
                                    Float fValueOf = velocityTracker3 != null ? Float.valueOf(velocityTracker3.getYVelocity()) : null;
                                    fValueOf.getClass();
                                    if ((-fValueOf.floatValue()) > accessibilityGestureHandler.inFlingVelocity) {
                                    }
                                }
                            }
                            accessibilityGestureHandler.clear(motionEvent);
                            return;
                        }
                        boolean z6 = 0;
                        z6 = 0;
                        if (actionMasked != 2) {
                            if (actionMasked == 3) {
                                accessibilityGestureHandler.clear(motionEvent);
                                return;
                            }
                            if (actionMasked != 5) {
                                if (actionMasked == 6 && accessibilityGestureHandler.gestureDetected) {
                                    int actionIndex = motionEvent.getActionIndex();
                                    if (motionEvent.getPointerId(actionIndex) == accessibilityGestureHandler.activePointerId) {
                                        i = actionIndex != 0 ? 0 : 1;
                                        accessibilityGestureHandler.totalY = (motionEvent.getY(actionIndex) - accessibilityGestureHandler.downY) + accessibilityGestureHandler.totalY;
                                        accessibilityGestureHandler.downY = motionEvent.getY(i);
                                        accessibilityGestureHandler.activePointerId = motionEvent.getPointerId(i);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            int actionIndex2 = motionEvent.getActionIndex();
                            if (accessibilityGestureHandler.gestureDetected) {
                                return;
                            }
                            float x = motionEvent.getX(actionIndex2);
                            float y = motionEvent.getY(actionIndex2);
                            float navBarHeight = ((NavBarStateManagerImpl) ((NavBarStoreImpl) accessibilityGestureHandler.navBarStore).getNavStateManager(1)).getNavBarHeight(0);
                            float screenHeight = DeviceState.getScreenHeight(accessibilityGestureHandler.coverContext);
                            float f = screenHeight - navBarHeight;
                            float screenWidth = DeviceState.getScreenWidth(accessibilityGestureHandler.coverContext);
                            if (f > y || y > screenHeight || 0.0f > x || x > screenWidth) {
                                return;
                            }
                            accessibilityGestureHandler.updateAccessibilityGestureDetected(true);
                            accessibilityGestureHandler.activePointerId = motionEvent.getPointerId(actionIndex2);
                            accessibilityGestureHandler.downY = motionEvent.getY(actionIndex2);
                            return;
                        }
                        if (!accessibilityGestureHandler.gestureDetected || (iFindPointerIndex = motionEvent.findPointerIndex(accessibilityGestureHandler.activePointerId)) == -1 || (motionPauseDetector = accessibilityGestureHandler.motionPauseDetector) == null) {
                            return;
                        }
                        boolean z7 = motionPauseDetector.makePauseHarderToTrigger;
                        motionPauseDetector.timer.start(z7 ? 400L : 300L, new Function0() { // from class: com.android.systemui.navigationbar.gestural.MotionPauseDetector$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                MotionPauseDetector motionPauseDetector2 = motionPauseDetector;
                                Log.i(motionPauseDetector2.tag, "motion pause detected by force pause timeout");
                                motionPauseDetector2.updatePaused("Force pause timeout", true);
                                return Unit.INSTANCE;
                            }
                        });
                        MotionPauseDetector.SystemVelocityProvider systemVelocityProvider = motionPauseDetector.velocityProvider;
                        systemVelocityProvider.velocityTracker.addMovement(motionEvent);
                        systemVelocityProvider.velocityTracker.computeCurrentVelocity(1);
                        float yVelocity = motionPauseDetector.directionY ? systemVelocityProvider.velocityTracker.getYVelocity(motionEvent.getPointerId(iFindPointerIndex)) : systemVelocityProvider.velocityTracker.getXVelocity(motionEvent.getPointerId(iFindPointerIndex));
                        Float f2 = motionPauseDetector.previousVelocity;
                        if (f2 != null && !Intrinsics.areEqual(f2, 0.0f)) {
                            Float f3 = motionPauseDetector.previousVelocity;
                            f3.getClass();
                            float fFloatValue = f3.floatValue();
                            float fAbs = Math.abs(yVelocity);
                            float fAbs2 = Math.abs(fFloatValue);
                            if (motionPauseDetector.isPaused) {
                                float f4 = motionPauseDetector.speedFast;
                                if (fAbs >= f4 && fAbs2 >= f4) {
                                    i = 0;
                                }
                                str2 = "Was paused, but started moving at a fast speed";
                            } else {
                                if ((yVelocity < 0.0f) != (fFloatValue < 0.0f)) {
                                    str2 = "Velocity changed directions";
                                } else {
                                    float f5 = motionPauseDetector.speedVerySlow;
                                    boolean z8 = fAbs < f5 && fAbs2 < f5;
                                    if (z8 || motionPauseDetector.hasEverBeenPaused) {
                                        str = "Pause requires back to back slow speeds";
                                        z5 = z8;
                                    } else {
                                        str = "Didn't have back to back slow speeds, checking for rapid deceleration on first pause only";
                                        z5 = fAbs < fAbs2 * 0.6f && fAbs < motionPauseDetector.speedSomewhatFast;
                                    }
                                    if (!z7) {
                                        z6 = z5;
                                        str2 = str;
                                    } else if (fAbs < motionPauseDetector.speedSlow) {
                                        if (motionPauseDetector.slowStartTime == 0) {
                                            motionPauseDetector.slowStartTime = motionEvent.getEventTime();
                                        }
                                        i = motionEvent.getEventTime() - motionPauseDetector.slowStartTime < 400 ? 0 : 1;
                                        str2 = "Maintained slow speed for sufficient duration when making pause harder to trigger";
                                    } else {
                                        motionPauseDetector.slowStartTime = 0L;
                                        str2 = "Intentionally making pause harder to trigger";
                                    }
                                }
                                motionPauseDetector.updatePaused(str2, z6);
                            }
                            z6 = i;
                            motionPauseDetector.updatePaused(str2, z6);
                        }
                        motionPauseDetector.previousVelocity = Float.valueOf(yVelocity);
                    }
                }
            });
        }
    }
}
