package com.android.systemui.aod;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.os.Process;
import android.os.UserHandle;
import android.view.Display;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl;
import com.android.systemui.LsRune;
import com.android.systemui.LsRuneWrapper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.doze.AODManagerWrapper;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.DeviceTypeWrapper;
import com.samsung.android.aod.AODManager;
import dagger.Lazy;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AODTouchModeManager implements KeyguardFoldController.StateListener {
    public final AODManagerWrapper aodManagerWrapper;
    public int currentDisplayState;
    public TouchMode currentTouchMode;
    public final DeviceTypeWrapper deviceTypeWrapper;
    public final DisplayManager displayManager;
    public final KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitor;
    public final Lazy keyguardViewMediatorHelperLazy;
    public final LogWrapper logWrapper;
    public final LsRuneWrapper lsRuneWrapper;
    public String touchNodePath = "/sys/class/sec/tsp/input/enabled";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TouchMode {
        public static final /* synthetic */ TouchMode[] $VALUES;
        public static final TouchMode DOUBLE;
        public static final TouchMode SINGLE;
        public static final TouchMode UNKNOWN;
        private final String value;

        static {
            TouchMode touchMode = new TouchMode("SINGLE", 0, "1");
            SINGLE = touchMode;
            TouchMode touchMode2 = new TouchMode("DOUBLE", 1, "0");
            DOUBLE = touchMode2;
            TouchMode touchMode3 = new TouchMode("UNKNOWN", 2, "-1");
            UNKNOWN = touchMode3;
            TouchMode[] touchModeArr = {touchMode, touchMode2, touchMode3};
            $VALUES = touchModeArr;
            EnumEntriesKt.enumEntries(touchModeArr);
        }

        private TouchMode(String str, int i, String str2) {
            this.value = str2;
        }

        public static TouchMode valueOf(String str) {
            return (TouchMode) Enum.valueOf(TouchMode.class, str);
        }

        public static TouchMode[] values() {
            return (TouchMode[]) $VALUES.clone();
        }

        public final String getValue() {
            return this.value;
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TouchMode.values().length];
            try {
                iArr[TouchMode.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TouchMode.DOUBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TouchMode.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public AODTouchModeManager(Context context, WakefulnessLifecycle wakefulnessLifecycle, KeyguardFoldController keyguardFoldController, LogWrapper logWrapper, LsRuneWrapper lsRuneWrapper, DeviceTypeWrapper deviceTypeWrapper, Lazy lazy, DisplayManager displayManager, KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl) {
        this.logWrapper = logWrapper;
        this.lsRuneWrapper = lsRuneWrapper;
        this.deviceTypeWrapper = deviceTypeWrapper;
        this.keyguardViewMediatorHelperLazy = lazy;
        this.displayManager = displayManager;
        this.keyguardSecUpdateMonitor = keyguardSecUpdateMonitorImpl;
        AODManagerWrapper.Companion.getClass();
        if (AODManagerWrapper.sInstance == null) {
            AODManagerWrapper.sInstance = new AODManagerWrapper(context, null);
        }
        AODManagerWrapper aODManagerWrapper = AODManagerWrapper.sInstance;
        aODManagerWrapper.getClass();
        this.aodManagerWrapper = aODManagerWrapper;
        this.currentTouchMode = TouchMode.SINGLE;
        this.currentDisplayState = 2;
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODTouchModeManager$registerObservers$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                AODTouchModeManager aODTouchModeManager = this.this$0;
                if (aODTouchModeManager.currentTouchMode == AODTouchModeManager.TouchMode.DOUBLE) {
                    aODTouchModeManager.logWrapper.i("onStartedWakingUp: set single touch mode");
                    aODTouchModeManager.setTouchMode(AODTouchModeManager.TouchMode.SINGLE);
                }
            }
        });
        ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(this, 4, false);
        displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.systemui.aod.AODTouchModeManager$registerDisplayListener$1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                AODTouchModeManager aODTouchModeManager = this.this$0;
                Display display = aODTouchModeManager.displayManager.getDisplay(i);
                if (display == null || display.getState() == aODTouchModeManager.currentDisplayState || !Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM)) {
                    return;
                }
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(display.getState(), aODTouchModeManager.currentDisplayState, "onDisplayChanged newDisplayState=", ", currentDisplayState=", ", displayId=");
                sbM.append(i);
                aODTouchModeManager.logWrapper.i(sbM.toString());
                int state = display.getState();
                if (state == 3 || state == 4) {
                    if (ArraysKt___ArraysKt.indexOf(new Integer[]{2, 1}, Integer.valueOf(aODTouchModeManager.currentDisplayState)) >= 0) {
                        aODTouchModeManager.setTouchMode(AODTouchModeManager.TouchMode.DOUBLE);
                    }
                    aODTouchModeManager.setTouchMode(AODTouchModeManager.TouchMode.UNKNOWN);
                }
                aODTouchModeManager.currentDisplayState = display.getState();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        }, null);
    }

    public final void handleSecondaryTouchInput(TouchMode touchMode) {
        this.lsRuneWrapper.getClass();
        if (LsRune.AOD_SUB_DISPLAY_COVER || Intrinsics.areEqual(this.touchNodePath, "/sys/class/sec/tsp2/input/enabled")) {
            return;
        }
        this.logWrapper.i("handleSecondaryTouchInput " + touchMode);
        String value = touchMode.getValue();
        AODManagerWrapper aODManagerWrapper = this.aodManagerWrapper;
        if (aODManagerWrapper.getService() != null) {
            AODManager service = aODManagerWrapper.getService();
            service.getClass();
            service.writeAODCommand("/sys/class/sec/sec_epen/input/enabled", value, (String) null, (String) null, (String) null);
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
    public final void onFoldStateChanged(boolean z) {
        String str;
        LogWrapper logWrapper = this.logWrapper;
        logWrapper.d("AODTouchModeManager", "onFolderStateChanged: isOpened=" + z);
        logWrapper.d("AODTouchModeManager", "setTouchNodePath: isOpened=" + z);
        this.lsRuneWrapper.getClass();
        if (LsRune.AOD_SUB_DISPLAY_LOCK || LsRune.AOD_SUB_DISPLAY_COVER) {
            if (z) {
                str = "/sys/class/sec/tsp1/input/enabled";
            } else {
                if (z) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "/sys/class/sec/tsp2/input/enabled";
            }
            this.touchNodePath = str;
        }
    }

    public final void setTouchMode(TouchMode touchMode) {
        if (this.deviceTypeWrapper.isFactoryBinary()) {
            return;
        }
        this.lsRuneWrapper.getClass();
        if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            int i = WhenMappings.$EnumSwitchMapping$0[touchMode.ordinal()];
            AODManagerWrapper aODManagerWrapper = this.aodManagerWrapper;
            LogWrapper logWrapper = this.logWrapper;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    if (this.currentTouchMode != TouchMode.UNKNOWN) {
                        logWrapper.i("setUnknownTouchMode");
                    }
                } else if (((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperLazy.get())).isScreenOn() || this.keyguardSecUpdateMonitor.mIsEarlyWakeUp) {
                    logWrapper.i("setDoubleTouchMode: return screen is on");
                } else {
                    String str = this.touchNodePath;
                    TouchMode touchMode2 = TouchMode.DOUBLE;
                    String value = touchMode2.getValue();
                    if (aODManagerWrapper.getService() != null) {
                        AODManager service = aODManagerWrapper.getService();
                        service.getClass();
                        service.writeAODCommand(str, value, (String) null, (String) null, (String) null);
                    }
                    logWrapper.i("setDoubleTouchMode: isSubDisplayNodePath()=" + Intrinsics.areEqual(this.touchNodePath, "/sys/class/sec/tsp2/input/enabled") + " called=" + Debug.getCallers(2));
                    handleSecondaryTouchInput(touchMode2);
                }
            } else if (this.currentTouchMode == TouchMode.DOUBLE) {
                logWrapper.i("setSingleTouchMode");
                String str2 = this.touchNodePath;
                TouchMode touchMode3 = TouchMode.SINGLE;
                String value2 = touchMode3.getValue();
                if (aODManagerWrapper.getService() != null) {
                    AODManager service2 = aODManagerWrapper.getService();
                    service2.getClass();
                    service2.writeAODCommand(str2, value2, (String) null, (String) null, (String) null);
                }
                handleSecondaryTouchInput(touchMode3);
            }
            this.currentTouchMode = touchMode;
        }
    }
}
