package com.android.systemui.aod;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Debug;
import android.os.FactoryTest;
import android.view.Display;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.AbstractC0970x34f4116a;
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
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DeviceTypeWrapper;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AODTouchModeManager implements KeyguardFoldController.StateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AODManagerWrapper aodManagerWrapper;
    public int currentDisplayState;
    public int currentTouchMode;
    public final DeviceTypeWrapper deviceTypeWrapper;
    public final DisplayManager displayManager;
    public final ExecutorService executor;
    public final Lazy keyguardViewMediatorHelperLazy;
    public final LogWrapper logWrapper;
    public final LsRuneWrapper lsRuneWrapper;
    public String touchNodePath = "/sys/class/sec/tsp/input/enabled";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.executor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.android.systemui.aod.AODTouchModeManager$executor$1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AODTouchModeManager");
            }
        });
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODTouchModeManager.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                AODTouchModeManager.this.setTouchMode(1);
            }
        });
        ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(this, 4);
        displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.systemui.aod.AODTouchModeManager.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display;
                int state;
                AODTouchModeManager aODTouchModeManager;
                int i2;
                if (((KeyguardViewMediator) ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) AODTouchModeManager.this.keyguardViewMediatorHelperLazy.get())).viewMediatorLazy.get()).getViewMediatorCallback().isScreenOn() || (display = AODTouchModeManager.this.displayManager.getDisplay(i)) == null || (i2 = (aODTouchModeManager = AODTouchModeManager.this).currentDisplayState) == (state = display.getState())) {
                    return;
                }
                aODTouchModeManager.logWrapper.m101i("AODTouchModeManager", AbstractC0970x34f4116a.m94m("onDisplayChanged newDisplayState=", state, ", currentDisplayState=", i2));
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
        String m86m = AbstractC0866xb1ce8deb.m86m("onFolderStateChanged: isOpened=", z);
        LogWrapper logWrapper = this.logWrapper;
        logWrapper.m98d("AODTouchModeManager", m86m);
        logWrapper.m98d("AODTouchModeManager", "setTouchNodePath: isOpened=" + z);
        this.lsRuneWrapper.getClass();
        if (LsRune.AOD_SUB_DISPLAY_LOCK || LsRune.AOD_SUB_DISPLAY_COVER) {
            this.touchNodePath = !z ? "/sys/class/sec/tsp2/input/enabled" : "/sys/class/sec/tsp1/input/enabled";
        }
    }

    public final void setTouchMode(final int i) {
        this.deviceTypeWrapper.getClass();
        int i2 = DeviceType.supportTablet;
        if (FactoryTest.isFactoryBinary()) {
            return;
        }
        if (Build.VERSION.SEM_FIRST_SDK_INT >= 31 || !LsRune.SUBSCREEN_UI) {
            switchTouchMode(i);
        } else {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.aod.AODTouchModeManager$setTouchMode$1
                @Override // java.lang.Runnable
                public final void run() {
                    AODTouchModeManager aODTouchModeManager = AODTouchModeManager.this;
                    int i3 = i;
                    int i4 = AODTouchModeManager.$r8$clinit;
                    aODTouchModeManager.switchTouchMode(i3);
                }
            });
        }
    }

    public final void switchTouchMode(int i) {
        AODManagerWrapper aODManagerWrapper = this.aodManagerWrapper;
        LsRuneWrapper lsRuneWrapper = this.lsRuneWrapper;
        LogWrapper logWrapper = this.logWrapper;
        if (i != 0) {
            if (i != 1) {
                logWrapper.m100e("AODTouchModeManager", "setTouchMode abnormal Touch Mode");
            } else if (this.currentTouchMode == 0) {
                aODManagerWrapper.writeAODCommand(this.touchNodePath, "1");
                logWrapper.m101i("AODTouchModeManager", "setSingleTouchMode: isSubDisplayNodePath()=" + this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled"));
                lsRuneWrapper.getClass();
                if (!LsRune.AOD_SUB_DISPLAY_COVER && !this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled")) {
                    logWrapper.m101i("AODTouchModeManager", "setSingleTouchMode : sec.epen.input.enabled : 1");
                    aODManagerWrapper.writeAODCommand("/sys/class/sec/sec_epen/input/enabled", "1");
                }
            }
        } else if (((KeyguardViewMediator) ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperLazy.get())).viewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
            logWrapper.m101i("AODTouchModeManager", "setDoubleTouchMode: return screen is on");
        } else {
            aODManagerWrapper.writeAODCommand(this.touchNodePath, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            logWrapper.m101i("AODTouchModeManager", "setDoubleTouchMode: isSubDisplayNodePath()=" + this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled") + " called=" + Debug.getCallers(2));
            lsRuneWrapper.getClass();
            if (!LsRune.AOD_SUB_DISPLAY_COVER && !this.touchNodePath.equals("/sys/class/sec/tsp2/input/enabled")) {
                logWrapper.m101i("AODTouchModeManager", "setDoubleTouchMode : sec.epen.input.enabled : 0");
                aODManagerWrapper.writeAODCommand("/sys/class/sec/sec_epen/input/enabled", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
        }
        this.currentTouchMode = i;
    }
}
