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
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.gestural.MotionPauseDetector;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        context = displays.length > 1 ? context.createDisplayContext(displays[1]) : context;
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
                VelocityTracker velocityTracker = motionPauseDetector.velocityProvider.velocityTracker;
                velocityTracker.clear();
                velocityTracker.recycle();
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
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
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
            Log.d(this.tag, AbstractC0866xb1ce8deb.m86m("updateAccessibilityGestureDetected: ", z));
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
        NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(i);
        if (!navStateManager.states.supportLargeCoverScreen) {
            disposeInputChannel();
            return;
        }
        boolean isLargeCoverScreenSyncEnabled = navStateManager.isLargeCoverScreenSyncEnabled();
        boolean isGestureMode = navStateManager.isGestureMode();
        NavBarHelper navBarHelper = this.navBarHelper;
        boolean z = true;
        boolean z2 = (navBarHelper.mA11yButtonState & 32) != 0;
        navBarHelper.getClass();
        boolean z3 = new NavBarHelper.CurrentSysuiState(navBarHelper, i).mWindowState != 2;
        if (!z2 || ((isGestureMode || ((!isLargeCoverScreenSyncEnabled && !this.isCoverNavBarVisible) || !z3)) && z3)) {
            z = false;
        }
        boolean z4 = this.isCoverNavBarVisible;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("a11yButtonState: ", z2, ", coverScreenNavBarEnabled: ", isLargeCoverScreenSyncEnabled, " gestureMode: ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, isGestureMode, ", isWindowShowing: ", z3, ", isCoverNavBarVisible: ");
        Log.d(this.tag, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m69m, z4, ", isEnabled: ", z));
        disposeInputChannel();
        if (z) {
            InputMonitor monitorGestureInput = ((InputManager) this.coverContext.getSystemService(InputManager.class)).monitorGestureInput("a11yGesture-swipe", i);
            this.inputMonitor = monitorGestureInput;
            this.inputEventReceiver = new InputChannelCompat$InputEventReceiver(monitorGestureInput != null ? monitorGestureInput.getInputChannel() : null, Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler$setInputChannel$1
                /* JADX WARN: Code restructure failed: missing block: B:143:0x0210, code lost:
                
                    if ((-r0.floatValue()) > r11.inFlingVelocity) goto L138;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onInputEvent(InputEvent inputEvent) {
                    int findPointerIndex;
                    final MotionPauseDetector motionPauseDetector;
                    String str;
                    boolean z5;
                    String str2;
                    AccessibilityGestureHandler accessibilityGestureHandler = AccessibilityGestureHandler.this;
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
                        Intrinsics.checkNotNull(velocityTracker);
                        velocityTracker.addMovement(motionEvent);
                        int actionMasked = motionEvent.getActionMasked();
                        int i2 = accessibilityGestureHandler.displayId;
                        if (actionMasked == 1) {
                            if (accessibilityGestureHandler.gestureDetected && !accessibilityGestureHandler.isPaused) {
                                accessibilityGestureHandler.totalY = (motionEvent.getY() - accessibilityGestureHandler.downY) + accessibilityGestureHandler.totalY;
                                VelocityTracker velocityTracker2 = accessibilityGestureHandler.velocityTracker;
                                Intrinsics.checkNotNull(velocityTracker2);
                                velocityTracker2.computeCurrentVelocity(1000);
                                if ((-accessibilityGestureHandler.totalY) <= accessibilityGestureHandler.inGestureDistance) {
                                    VelocityTracker velocityTracker3 = accessibilityGestureHandler.velocityTracker;
                                    Float valueOf = velocityTracker3 != null ? Float.valueOf(velocityTracker3.getYVelocity()) : null;
                                    Intrinsics.checkNotNull(valueOf);
                                }
                                Log.d(accessibilityGestureHandler.tag, "accessibilityButtonClicked");
                                accessibilityGestureHandler.navBarHelper.mAccessibilityManager.notifyAccessibilityButtonClicked(i2);
                            }
                            accessibilityGestureHandler.clear(motionEvent);
                            return;
                        }
                        boolean z6 = 0;
                        r3 = false;
                        boolean z7 = false;
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
                                        r1 = actionIndex != 0 ? 0 : 1;
                                        accessibilityGestureHandler.totalY = (motionEvent.getY(actionIndex) - accessibilityGestureHandler.downY) + accessibilityGestureHandler.totalY;
                                        accessibilityGestureHandler.downY = motionEvent.getY(r1);
                                        accessibilityGestureHandler.activePointerId = motionEvent.getPointerId(r1);
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
                            NavBarStateManager navStateManager2 = ((NavBarStoreImpl) accessibilityGestureHandler.navBarStore).getNavStateManager(i2);
                            float barHeight = navStateManager2.navBarLayoutParams.getBarHeight(navStateManager2.states.canMove, 0);
                            Context context = accessibilityGestureHandler.coverContext;
                            float screenHeight = DeviceState.getScreenHeight(context);
                            float f = screenHeight - barHeight;
                            float screenWidth = DeviceState.getScreenWidth(context);
                            if (f <= y && y <= screenHeight) {
                                if (0.0f <= x && x <= screenWidth) {
                                    z7 = true;
                                }
                            }
                            if (z7) {
                                accessibilityGestureHandler.updateAccessibilityGestureDetected(true);
                                accessibilityGestureHandler.activePointerId = motionEvent.getPointerId(actionIndex2);
                                accessibilityGestureHandler.downY = motionEvent.getY(actionIndex2);
                                return;
                            }
                            return;
                        }
                        if (!accessibilityGestureHandler.gestureDetected || (findPointerIndex = motionEvent.findPointerIndex(accessibilityGestureHandler.activePointerId)) == -1 || (motionPauseDetector = accessibilityGestureHandler.motionPauseDetector) == null) {
                            return;
                        }
                        boolean z8 = motionPauseDetector.makePauseHarderToTrigger;
                        motionPauseDetector.timer.start(z8 ? 400L : 300L, new Function0() { // from class: com.android.systemui.navigationbar.gestural.MotionPauseDetector$startForcePauseTimeout$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Log.i(MotionPauseDetector.this.tag, "motion pause detected by force pause timeout");
                                MotionPauseDetector.this.updatePaused("Force pause timeout", true);
                                return Unit.INSTANCE;
                            }
                        });
                        MotionPauseDetector.SystemVelocityProvider systemVelocityProvider = motionPauseDetector.velocityProvider;
                        VelocityTracker velocityTracker4 = systemVelocityProvider.velocityTracker;
                        velocityTracker4.addMovement(motionEvent);
                        velocityTracker4.computeCurrentVelocity(1);
                        boolean z9 = motionPauseDetector.directionY;
                        VelocityTracker velocityTracker5 = systemVelocityProvider.velocityTracker;
                        float yVelocity = z9 ? velocityTracker5.getYVelocity(motionEvent.getPointerId(findPointerIndex)) : velocityTracker5.getXVelocity(motionEvent.getPointerId(findPointerIndex));
                        Float f2 = motionPauseDetector.previousVelocity;
                        if (f2 != null) {
                            if (!(f2.floatValue() == 0.0f)) {
                                Float f3 = motionPauseDetector.previousVelocity;
                                Intrinsics.checkNotNull(f3);
                                float floatValue = f3.floatValue();
                                float abs = Math.abs(yVelocity);
                                float abs2 = Math.abs(floatValue);
                                if (motionPauseDetector.isPaused) {
                                    float f4 = motionPauseDetector.speedFast;
                                    if (abs >= f4 && abs2 >= f4) {
                                        r1 = 0;
                                    }
                                    str2 = "Was paused, but started moving at a fast speed";
                                } else {
                                    if ((yVelocity < 0.0f) != (floatValue < 0.0f)) {
                                        str2 = "Velocity changed directions";
                                    } else {
                                        float f5 = motionPauseDetector.speedVerySlow;
                                        boolean z10 = abs < f5 && abs2 < f5;
                                        if (z10 || motionPauseDetector.hasEverBeenPaused) {
                                            str = "Pause requires back to back slow speeds";
                                            z5 = z10;
                                        } else {
                                            str = "Didn't have back to back slow speeds, checking for rapid deceleration on first pause only";
                                            z5 = ((abs > (abs2 * 0.6f) ? 1 : (abs == (abs2 * 0.6f) ? 0 : -1)) < 0) && abs < motionPauseDetector.speedSomewhatFast;
                                        }
                                        if (!z8) {
                                            z6 = z5;
                                            str2 = str;
                                        } else if (abs < motionPauseDetector.speedSlow) {
                                            if (motionPauseDetector.slowStartTime == 0) {
                                                motionPauseDetector.slowStartTime = motionEvent.getEventTime();
                                            }
                                            r1 = motionEvent.getEventTime() - motionPauseDetector.slowStartTime < 400 ? 0 : 1;
                                            str2 = "Maintained slow speed for sufficient duration when making pause harder to trigger";
                                        } else {
                                            motionPauseDetector.slowStartTime = 0L;
                                            str2 = "Intentionally making pause harder to trigger";
                                        }
                                    }
                                    motionPauseDetector.updatePaused(str2, z6);
                                }
                                z6 = r1;
                                motionPauseDetector.updatePaused(str2, z6);
                            }
                        }
                        motionPauseDetector.previousVelocity = Float.valueOf(yVelocity);
                    }
                }
            });
        }
    }
}
