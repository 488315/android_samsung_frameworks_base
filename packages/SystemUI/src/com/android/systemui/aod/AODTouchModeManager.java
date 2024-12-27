package com.android.systemui.aod;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.os.Process;
import android.os.UserHandle;
import android.view.Display;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.LsRuneWrapper;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.doze.AODManagerWrapper;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.DeviceTypeWrapper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AODTouchModeManager implements KeyguardFoldController.StateListener {
    public final AODManagerWrapper aodManagerWrapper;
    public int currentDisplayState;
    public int currentTouchMode;
    public final DeviceTypeWrapper deviceTypeWrapper;
    public final DisplayManager displayManager;
    public final Lazy keyguardViewMediatorHelperLazy;
    public final LogWrapper logWrapper;
    public final LsRuneWrapper lsRuneWrapper;
    public String touchNodePath = "/sys/class/sec/tsp/input/enabled";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AODTouchModeManager(Context context, WakefulnessLifecycle wakefulnessLifecycle, KeyguardFoldController keyguardFoldController, LogWrapper logWrapper, LsRuneWrapper lsRuneWrapper, DeviceTypeWrapper deviceTypeWrapper, Lazy lazy, DisplayManager displayManager) {
        this.logWrapper = logWrapper;
        this.lsRuneWrapper = lsRuneWrapper;
        this.deviceTypeWrapper = deviceTypeWrapper;
        this.keyguardViewMediatorHelperLazy = lazy;
        this.displayManager = displayManager;
        AODManagerWrapper.Companion.getClass();
        if (AODManagerWrapper.sInstance == null) {
            AODManagerWrapper.sInstance = new AODManagerWrapper(context, null);
        }
        AODManagerWrapper aODManagerWrapper = AODManagerWrapper.sInstance;
        Intrinsics.checkNotNull(aODManagerWrapper);
        this.aodManagerWrapper = aODManagerWrapper;
        this.currentTouchMode = 1;
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODTouchModeManager.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                AODTouchModeManager aODTouchModeManager = AODTouchModeManager.this;
                if (aODTouchModeManager.currentDisplayState == 2) {
                    aODTouchModeManager.logWrapper.i("AODTouchModeManager", "onStartedWakingUp: set single touch mode");
                    aODTouchModeManager.setTouchMode(1);
                }
            }
        });
        ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(this, 4, false);
        displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.systemui.aod.AODTouchModeManager.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                int state;
                AODTouchModeManager aODTouchModeManager;
                int i2;
                Display display = AODTouchModeManager.this.displayManager.getDisplay(i);
                if (display == null || !Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM) || (i2 = (aODTouchModeManager = AODTouchModeManager.this).currentDisplayState) == (state = display.getState())) {
                    return;
                }
                aODTouchModeManager.logWrapper.i("AODTouchModeManager", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(state, i2, "onDisplayChanged newDisplayState=", ", currentDisplayState="));
                AODTouchModeManager aODTouchModeManager2 = AODTouchModeManager.this;
                int i3 = aODTouchModeManager2.currentDisplayState;
                if ((i3 == 2 || i3 == 1) && (state == 3 || state == 4)) {
                    aODTouchModeManager2.setTouchMode(0);
                }
                AODTouchModeManager.this.currentDisplayState = state;
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        }, null);
    }

    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
    public final void onFoldStateChanged(boolean z) {
        String m = KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("onFolderStateChanged: isOpened=", z);
        LogWrapper logWrapper = this.logWrapper;
        logWrapper.d("AODTouchModeManager", m);
        logWrapper.d("AODTouchModeManager", "setTouchNodePath: isOpened=" + z);
        this.lsRuneWrapper.getClass();
        if (LsRune.AOD_SUB_DISPLAY_LOCK || LsRune.AOD_SUB_DISPLAY_COVER) {
            this.touchNodePath = !z ? "/sys/class/sec/tsp2/input/enabled" : "/sys/class/sec/tsp1/input/enabled";
        }
    }

    public final void setTouchMode(int i) {
        if (this.deviceTypeWrapper.isFactoryBinary()) {
            return;
        }
        AODManagerWrapper aODManagerWrapper = this.aodManagerWrapper;
        LsRuneWrapper lsRuneWrapper = this.lsRuneWrapper;
        LogWrapper logWrapper = this.logWrapper;
        if (i != 0) {
            if (i != 1) {
                logWrapper.e("AODTouchModeManager", "setTouchMode abnormal Touch Mode");
            } else if (this.currentTouchMode == 0) {
                aODManagerWrapper.writeAODCommand(this.touchNodePath, "1");
                logWrapper.i("AODTouchModeManager", "setSingleTouchMode: isSubDisplayNodePath()=" + this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled"));
                lsRuneWrapper.getClass();
                if (!LsRune.AOD_SUB_DISPLAY_COVER && !this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled")) {
                    logWrapper.i("AODTouchModeManager", "setSingleTouchMode : sec.epen.input.enabled : 1");
                    aODManagerWrapper.writeAODCommand("/sys/class/sec/sec_epen/input/enabled", "1");
                }
            }
        } else if (((KeyguardViewMediator) ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperLazy.get())).viewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
            logWrapper.i("AODTouchModeManager", "setDoubleTouchMode: return screen is on");
        } else {
            aODManagerWrapper.writeAODCommand(this.touchNodePath, "0");
            logWrapper.i("AODTouchModeManager", "setDoubleTouchMode: isSubDisplayNodePath()=" + this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled") + " called=" + Debug.getCallers(2));
            lsRuneWrapper.getClass();
            if (!LsRune.AOD_SUB_DISPLAY_COVER && !this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled")) {
                logWrapper.i("AODTouchModeManager", "setDoubleTouchMode : sec.epen.input.enabled : 0");
                aODManagerWrapper.writeAODCommand("/sys/class/sec/sec_epen/input/enabled", "0");
            }
        }
        this.currentTouchMode = i;
    }
}
