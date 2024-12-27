package com.android.server.desktopmode;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.input.InputManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Pair;
import android.util.Range;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputDevice;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.wm.WindowManagerService;

import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HardwareManager {
    public static final List sSupportedDockUsbpdIdRanges;
    public static final Map sSupportedDockUsbpdIds;
    public AnonymousClass6 mBatteryChangedListener;
    public DisplayInfo mConnectedDisplay;
    public InputDevice mConnectedMouse;
    public final Context mContext;
    public final AnonymousClass1 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final Handler mHandler;
    public final IDisplayManager mIDisplayManager;
    public final AnonymousClass2 mInputDeviceListener;
    public final InputManager mInputManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public IRefreshRateToken mRefreshRateToken;
    public final ContentResolver mResolver;
    public final SettingsHelper mSettingsHelper;
    public final AnonymousClass5 mStateListener;
    public final IStateManager mStateManager;
    public final AnonymousClass3 mUEventHostDeviceObserver;
    public final AnonymousClass3 mUEventObserver;
    public final WindowManagerService mWindowManager;
    public final Object mLock = new Object();
    public boolean mForcedInternalScreenModeEnabled = false;
    public boolean mIsExternalDisplayConnected = false;
    public boolean mIsMouseConnected = false;
    public boolean mIsPogoKeyboardConnected = false;
    public boolean mIsBtMouseDeepSleep = false;
    public int mRawDockState = -1;
    public String mRawDockUsbpdIds = "";
    public final SparseArray mDisplays = new SparseArray();
    public DockState mDockState = new DockState();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.HardwareManager$6, reason: invalid class name */
    public final class AnonymousClass6 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HardwareManager this$0;

        public /* synthetic */ AnonymousClass6(HardwareManager hardwareManager, int i) {
            this.$r8$classId = i;
            this.this$0 = hardwareManager;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BluetoothClass bluetoothClass;
            switch (this.$r8$classId) {
                case 0:
                    if ("android.bluetooth.device.action.ACL_DISCONNECTED"
                            .equals(intent.getAction())) {
                        int intExtra =
                                intent.getIntExtra(
                                        "com.samsung.bluetooth.device.extra.DISCONNECTION_REASON",
                                        0);
                        BluetoothDevice bluetoothDevice =
                                (BluetoothDevice)
                                        intent.getParcelableExtra(
                                                "android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice == null
                                || (bluetoothClass = bluetoothDevice.getBluetoothClass()) == null) {
                            return;
                        }
                        int semGetPeripheralMinorClass =
                                bluetoothClass.semGetPeripheralMinorClass();
                        boolean z = DesktopModeFeature.DEBUG;
                        if (z) {
                            Map map = HardwareManager.sSupportedDockUsbpdIds;
                            Log.d(
                                    "[DMS]HardwareManager",
                                    "minorClass="
                                            + Integer.toHexString(semGetPeripheralMinorClass)
                                            + ", reason="
                                            + intExtra);
                        }
                        if (intExtra == 19 && semGetPeripheralMinorClass == 1408) {
                            synchronized (this.this$0.mLock) {
                                try {
                                    this.this$0.mIsBtMouseDeepSleep = true;
                                    if (z) {
                                        Map map2 = HardwareManager.sSupportedDockUsbpdIds;
                                        Log.d("[DMS]HardwareManager", "mIsBtMouseDeepSleep=true");
                                    }
                                    HardwareManager hardwareManager = this.this$0;
                                    if (!hardwareManager.mIsMouseConnected) {
                                        hardwareManager.updateInputDeviceStatusLocked();
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 1:
                    updateWiredChargingStatus(
                            ((StateManager) this.this$0.mStateManager).getState(), intent);
                    return;
                default:
                    String action = intent.getAction();
                    if (DesktopModeFeature.DEBUG) {
                        Map map3 = HardwareManager.sSupportedDockUsbpdIds;
                        Log.d("[DMS]HardwareManager", "onReceive(), action=" + action);
                    }
                    if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(action)) {
                        HardwareManager.m413$$Nest$mupdatePogoKeyboardStatus(
                                this.this$0,
                                intent.getBooleanExtra(Constants.JSON_CLIENT_DATA_STATUS, false));
                        return;
                    }
                    return;
            }
        }

        public void updateWiredChargingStatus(
                StateManager.InternalState internalState, Intent intent) {
            if (intent == null) {
                return;
            }
            int intExtra = intent.getIntExtra("plugged", -1);
            boolean z = true;
            if (intExtra != 1 && intExtra != 2) {
                z = false;
            }
            if (internalState.mIsWiredCharging != z) {
                if (DesktopModeFeature.DEBUG) {
                    Map map = HardwareManager.sSupportedDockUsbpdIds;
                    Log.d("[DMS]HardwareManager", "isWiredCharging=" + z);
                }
                ((StateManager) this.this$0.mStateManager).setWiredCharging(z);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DockState {
        public final boolean mDesktopModeSupported;
        public final boolean mIsAdapter;
        public final String mRawDockUsbpdIds;
        public final int mType;

        public DockState() {
            this("", false, false, 10000);
        }

        public DockState(String str, boolean z, boolean z2, int i) {
            this.mType = i;
            this.mDesktopModeSupported = z;
            this.mRawDockUsbpdIds = str;
            this.mIsAdapter = z2;
        }

        public static String dockTypeToString(int i) {
            switch (i) {
                case 10000:
                    return "TYPE_UNDOCKED";
                case 10001:
                    return "TYPE_DEX_STATION";
                case 10002:
                    return "TYPE_UNOFFICIAL_ADAPTER";
                case 10003:
                    return "TYPE_INTERNAL_SCREEN";
                case 10004:
                    return "TYPE_DEX_PAD";
                case FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE /* 10005 */:
                    return "TYPE_DEX_RESERVED";
                case FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER /* 10006 */:
                    return "TYPE_DEX_PAD_RECOVERY";
                case FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO /* 10007 */:
                    return "TYPE_MULTIPORT_ADAPTER_1";
                case 10008:
                    return "TYPE_MULTIPORT_ADAPTER_2";
                case FrameworkStatsLog.CPU_TIME_PER_UID /* 10009 */:
                    return "TYPE_HDMI_ADAPTER";
                case FrameworkStatsLog.CPU_TIME_PER_UID_FREQ /* 10010 */:
                    return "TYPE_DEX_CABLE";
                case FrameworkStatsLog.WIFI_ACTIVITY_INFO /* 10011 */:
                    return "TYPE_MULTIPORT_ADAPTER_3";
                case FrameworkStatsLog.MODEM_ACTIVITY_INFO /* 10012 */:
                    return "TYPE_TARGUS_ADAPTER";
                case FrameworkStatsLog.PROCESS_MEMORY_STATE /* 10013 */:
                    return "TYPE_MULTIPORT_ADAPTER_EE_P5400";
                case FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME /* 10014 */:
                    return "TYPE_DEX_RESERVED_DONGLE";
                default:
                    return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=");
            }
        }

        public final boolean isDexPad() {
            int i = this.mType;
            return i == 10004 || i == 10006;
        }

        public final boolean isDexStation() {
            return this.mType == 10001;
        }

        public final boolean isUndocked() {
            return this.mType == 10000;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DockState(mType=");
            sb.append(dockTypeToString(this.mType));
            sb.append(", mDesktopModeSupported=");
            sb.append(this.mDesktopModeSupported);
            sb.append(", mRawDockUsbpdIds=");
            sb.append(this.mRawDockUsbpdIds);
            sb.append(", mIsAdapter=");
            return OptionalBool$$ExternalSyntheticOutline0.m(")", sb, this.mIsAdapter);
        }
    }

    /* renamed from: -$$Nest$mupdateExternalDisplayStatus, reason: not valid java name */
    public static void m412$$Nest$mupdateExternalDisplayStatus(
            HardwareManager hardwareManager, boolean z, int i) {
        DisplayInfo displayInfo;
        boolean z2 = true;
        if (z) {
            Display display = hardwareManager.mDisplayManager.getDisplay(i);
            synchronized (hardwareManager.mLock) {
                if (display != null) {
                    try {
                        DisplayInfo displayInfo2 = new DisplayInfo(display);
                        hardwareManager.mDisplays.put(i, displayInfo2);
                        if (isSupportedDisplayType(displayInfo2.mType)) {
                            hardwareManager.mConnectedDisplay = displayInfo2;
                            if (hardwareManager.mIsExternalDisplayConnected) {
                                ((StateManager) hardwareManager.mStateManager)
                                        .setExternalDisplayUpdated(displayInfo2);
                            } else {
                                hardwareManager.mIsExternalDisplayConnected = true;
                                hardwareManager.updateDockStatusLocked();
                                ((StateManager) hardwareManager.mStateManager)
                                        .setExternalDisplayConnected(true, displayInfo2);
                            }
                        }
                    } finally {
                    }
                }
            }
        } else {
            synchronized (hardwareManager.mLock) {
                try {
                    DisplayInfo displayInfo3 =
                            (DisplayInfo) hardwareManager.mDisplays.removeReturnOld(i);
                    if (displayInfo3 != null
                            && isSupportedDisplayType(displayInfo3.mType)
                            && hardwareManager.mIsExternalDisplayConnected) {
                        int size = hardwareManager.mDisplays.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= size) {
                                z2 = false;
                                displayInfo = null;
                                break;
                            } else {
                                displayInfo = (DisplayInfo) hardwareManager.mDisplays.valueAt(i2);
                                if (isSupportedDisplayType(displayInfo.mType)) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        hardwareManager.mConnectedDisplay = displayInfo;
                        if (z2) {
                            ((StateManager) hardwareManager.mStateManager)
                                    .setExternalDisplayUpdated(displayInfo);
                        } else if (displayInfo3.mType != 2) {
                            hardwareManager.mIsExternalDisplayConnected = false;
                            hardwareManager.updateDockStatusLocked();
                            ((StateManager) hardwareManager.mStateManager)
                                    .setExternalDisplayConnected(false, null);
                        } else if (Utils.readFile(-1, "/sys/class/dp_sec/dex") == 0) {
                            hardwareManager.mIsExternalDisplayConnected = false;
                            hardwareManager.updateDockStatusLocked();
                            ((StateManager) hardwareManager.mStateManager)
                                    .setExternalDisplayConnected(false, null);
                        } else {
                            hardwareManager.mHandler.postDelayed(
                                    new HardwareManager$$ExternalSyntheticLambda0(hardwareManager),
                                    2000L);
                        }
                    }
                } finally {
                }
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]HardwareManager",
                    "updateExternalDisplayStatus(), mDisplays=" + hardwareManager.mDisplays);
        }
    }

    /* renamed from: -$$Nest$mupdatePogoKeyboardStatus, reason: not valid java name */
    public static void m413$$Nest$mupdatePogoKeyboardStatus(
            HardwareManager hardwareManager, boolean z) {
        hardwareManager.mIsPogoKeyboardConnected = z;
        StateManager stateManager = (StateManager) hardwareManager.mStateManager;
        stateManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "setPogoKeyboardConnected(state=", ")", "[DMS]StateManager", z);
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mIsPogoKeyboardConnected != z) {
                    stateManager.mInternalState.mIsPogoKeyboardConnected = z;
                    stateManager.mHandler.post(
                            new StateManager$$ExternalSyntheticLambda0(
                                    stateManager,
                                    stateManager.copyInternalStateLocked(
                                            stateManager.mInternalState),
                                    4));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        sSupportedDockUsbpdIds = arrayMap;
        arrayMap.put("04e8:a027", 10008);
        arrayMap.put("04e8:a029", 10004);
        arrayMap.put("04e8:a025", Integer.valueOf(FrameworkStatsLog.CPU_TIME_PER_UID));
        arrayMap.put("04e8:a020", 10001);
        arrayMap.put("04b4:2122", Integer.valueOf(FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO));
        arrayMap.put("04b4:f645", Integer.valueOf(FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER));
        arrayMap.put("04e8:a048", Integer.valueOf(FrameworkStatsLog.CPU_TIME_PER_UID_FREQ));
        arrayMap.put("04e8:a056", Integer.valueOf(FrameworkStatsLog.WIFI_ACTIVITY_INFO));
        arrayMap.put("04e8:a066", Integer.valueOf(FrameworkStatsLog.PROCESS_MEMORY_STATE));
        ArrayList arrayList = new ArrayList();
        sSupportedDockUsbpdIdRanges = arrayList;
        arrayList.add(
                new Pair(
                        new Range("04e8:a02a", "04e8:a02e"),
                        Integer.valueOf(FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE)));
        arrayList.add(
                new Pair(
                        new Range("04e8:a02f", "04e8:a033"),
                        Integer.valueOf(FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME)));
        Range range = new Range("1048:4007", "1048:4012");
        Integer valueOf = Integer.valueOf(FrameworkStatsLog.MODEM_ACTIVITY_INFO);
        arrayList.add(new Pair(range, valueOf));
        arrayList.add(new Pair(new Range("1048:5006", "1048:5013"), valueOf));
        arrayList.add(new Pair(new Range("1048:6000", "1048:6fff"), valueOf));
    }

    public HardwareManager(
            Context context,
            IStateManager iStateManager,
            SettingsHelper settingsHelper,
            InputManager inputManager,
            DisplayManager displayManager,
            PowerManagerInternal powerManagerInternal,
            WindowManagerService windowManagerService,
            IDisplayManager iDisplayManager) {
        DisplayManager.DisplayListener displayListener =
                new DisplayManager
                        .DisplayListener() { // from class:
                                             // com.android.server.desktopmode.HardwareManager.1
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i) {
                        if (DesktopModeFeature.DEBUG) {
                            Map map = HardwareManager.sSupportedDockUsbpdIds;
                            DesktopModeService$$ExternalSyntheticOutline0.m(
                                    i, "onDisplayAdded displayId=", "[DMS]HardwareManager");
                        }
                        HardwareManager.m412$$Nest$mupdateExternalDisplayStatus(
                                HardwareManager.this, true, i);
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayChanged(int i) {
                        Display display;
                        if (((StateManager) HardwareManager.this.mStateManager)
                                                .getState()
                                                .mDesktopDisplayId
                                        != i
                                || (display = HardwareManager.this.mDisplayManager.getDisplay(i))
                                        == null) {
                            return;
                        }
                        DisplayInfo displayInfo = new DisplayInfo(display);
                        synchronized (HardwareManager.this.mLock) {
                            try {
                                if (!displayInfo.equals(HardwareManager.this.mDisplays.get(i))) {
                                    Map map = HardwareManager.sSupportedDockUsbpdIds;
                                    Log.d(
                                            "[DMS]HardwareManager",
                                            "onDisplayChanged, DisplayInfo=" + displayInfo);
                                    HardwareManager.this.mDisplays.put(i, displayInfo);
                                }
                            } finally {
                            }
                        }
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i) {
                        if (DesktopModeFeature.DEBUG) {
                            Map map = HardwareManager.sSupportedDockUsbpdIds;
                            DesktopModeService$$ExternalSyntheticOutline0.m(
                                    i, "onDisplayRemoved displayId=", "[DMS]HardwareManager");
                        }
                        HardwareManager.m412$$Nest$mupdateExternalDisplayStatus(
                                HardwareManager.this, false, i);
                    }
                };
        InputManager.InputDeviceListener inputDeviceListener =
                new InputManager
                        .InputDeviceListener() { // from class:
                                                 // com.android.server.desktopmode.HardwareManager.2
                    @Override // android.hardware.input.InputManager.InputDeviceListener
                    public final void onInputDeviceAdded(int i) {
                        synchronized (HardwareManager.this.mLock) {
                            HardwareManager.this.updateInputDeviceStatusLocked();
                        }
                    }

                    @Override // android.hardware.input.InputManager.InputDeviceListener
                    public final void onInputDeviceChanged(int i) {}

                    @Override // android.hardware.input.InputManager.InputDeviceListener
                    public final void onInputDeviceRemoved(int i) {
                        synchronized (HardwareManager.this.mLock) {
                            HardwareManager.this.updateInputDeviceStatusLocked();
                        }
                    }
                };
        final int i = 0;
        UEventObserver uEventObserver =
                new UEventObserver(
                        this) { // from class: com.android.server.desktopmode.HardwareManager.3
                    public final /* synthetic */ HardwareManager this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onUEvent(UEventObserver.UEvent uEvent) {
                        switch (i) {
                            case 0:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        this.this$0.setRawDockStateLocked(
                                                Integer.parseInt(uEvent.get("SWITCH_STATE")));
                                        this.this$0.setRawDockUsbpdIdsLocked(
                                                uEvent.get("USBPD_IDS"));
                                        this.this$0.updateDockStatusLocked();
                                    } catch (NumberFormatException unused) {
                                        Map map = HardwareManager.sSupportedDockUsbpdIds;
                                        Log.e(
                                                "[DMS]HardwareManager",
                                                "Could not parse switch state from event "
                                                        + uEvent);
                                    }
                                }
                                return;
                            default:
                                String str = uEvent.get("ACTION");
                                String str2 = uEvent.get("DEVTYPE");
                                String str3 = uEvent.get("PRODUCT");
                                if ("4b4/f645".equals(str3)) {
                                    if (DesktopModeFeature.DEBUG) {
                                        Map map2 = HardwareManager.sSupportedDockUsbpdIds;
                                        StringBuilder m =
                                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                        "onUEvent(device) :: action = ",
                                                        str,
                                                        ", devType = ",
                                                        str2,
                                                        ", product = ");
                                        m.append(str3);
                                        Log.w("[DMS]HardwareManager", m.toString());
                                    }
                                    synchronized (this.this$0.mLock) {
                                        try {
                                            if ("add".equals(str)) {
                                                Map map3 = HardwareManager.sSupportedDockUsbpdIds;
                                                Log.w(
                                                        "[DMS]HardwareManager",
                                                        "Attached US bootmode for dex pad");
                                                this.this$0.setRawDockStateLocked(114);
                                                this.this$0.setRawDockUsbpdIdsLocked("04b4:f645");
                                                this.this$0.updateDockStatusLocked();
                                            } else if ("remove".equals(str)) {
                                                Map map4 = HardwareManager.sSupportedDockUsbpdIds;
                                                Log.w(
                                                        "[DMS]HardwareManager",
                                                        "Detached US bootmode for dex pad");
                                                this.this$0.setRawDockStateLocked(0);
                                                this.this$0.setRawDockUsbpdIdsLocked("04b4:f645");
                                                this.this$0.updateDockStatusLocked();
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        UEventObserver uEventObserver2 =
                new UEventObserver(
                        this) { // from class: com.android.server.desktopmode.HardwareManager.3
                    public final /* synthetic */ HardwareManager this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onUEvent(UEventObserver.UEvent uEvent) {
                        switch (i2) {
                            case 0:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        this.this$0.setRawDockStateLocked(
                                                Integer.parseInt(uEvent.get("SWITCH_STATE")));
                                        this.this$0.setRawDockUsbpdIdsLocked(
                                                uEvent.get("USBPD_IDS"));
                                        this.this$0.updateDockStatusLocked();
                                    } catch (NumberFormatException unused) {
                                        Map map = HardwareManager.sSupportedDockUsbpdIds;
                                        Log.e(
                                                "[DMS]HardwareManager",
                                                "Could not parse switch state from event "
                                                        + uEvent);
                                    }
                                }
                                return;
                            default:
                                String str = uEvent.get("ACTION");
                                String str2 = uEvent.get("DEVTYPE");
                                String str3 = uEvent.get("PRODUCT");
                                if ("4b4/f645".equals(str3)) {
                                    if (DesktopModeFeature.DEBUG) {
                                        Map map2 = HardwareManager.sSupportedDockUsbpdIds;
                                        StringBuilder m =
                                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                        "onUEvent(device) :: action = ",
                                                        str,
                                                        ", devType = ",
                                                        str2,
                                                        ", product = ");
                                        m.append(str3);
                                        Log.w("[DMS]HardwareManager", m.toString());
                                    }
                                    synchronized (this.this$0.mLock) {
                                        try {
                                            if ("add".equals(str)) {
                                                Map map3 = HardwareManager.sSupportedDockUsbpdIds;
                                                Log.w(
                                                        "[DMS]HardwareManager",
                                                        "Attached US bootmode for dex pad");
                                                this.this$0.setRawDockStateLocked(114);
                                                this.this$0.setRawDockUsbpdIdsLocked("04b4:f645");
                                                this.this$0.updateDockStatusLocked();
                                            } else if ("remove".equals(str)) {
                                                Map map4 = HardwareManager.sSupportedDockUsbpdIds;
                                                Log.w(
                                                        "[DMS]HardwareManager",
                                                        "Detached US bootmode for dex pad");
                                                this.this$0.setRawDockStateLocked(0);
                                                this.this$0.setRawDockUsbpdIdsLocked("04b4:f645");
                                                this.this$0.updateDockStatusLocked();
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                return;
                        }
                    }
                };
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.HardwareManager.5
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDisplayDisconnectionRequested(int i3) {
                        synchronized (HardwareManager.this.mLock) {
                            try {
                                IntArray intArray = new IntArray(1);
                                int size = HardwareManager.this.mDisplays.size();
                                for (int i4 = 0; i4 < size; i4++) {
                                    DisplayInfo displayInfo =
                                            (DisplayInfo)
                                                    HardwareManager.this.mDisplays.valueAt(i4);
                                    if (displayInfo.mType == i3) {
                                        intArray.add(displayInfo.mDisplayId);
                                    }
                                }
                                int size2 = intArray.size();
                                for (int i5 = 0; i5 < size2; i5++) {
                                    HardwareManager.m412$$Nest$mupdateExternalDisplayStatus(
                                            HardwareManager.this, false, intArray.get(i5));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStartLoadingScreen(boolean z) {
                        HardwareManager hardwareManager = HardwareManager.this;
                        hardwareManager.getClass();
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]HardwareManager", "setLowRefreshRate(), enter=" + z);
                        }
                        if (z) {
                            try {
                                hardwareManager.mRefreshRateToken =
                                        hardwareManager.mIDisplayManager.acquireLowRefreshRateToken(
                                                (IBinder) null, "[DMS]HardwareManager");
                                return;
                            } catch (RemoteException e) {
                                Log.e("[DMS]HardwareManager", "RemoteException is occurred", e);
                                return;
                            }
                        }
                        try {
                            IRefreshRateToken iRefreshRateToken = hardwareManager.mRefreshRateToken;
                            if (iRefreshRateToken != null) {
                                iRefreshRateToken.release();
                                hardwareManager.mRefreshRateToken = null;
                            }
                        } catch (RemoteException e2) {
                            Log.e("[DMS]HardwareManager", "LowRefreshRate() release failed", e2);
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStopLoadingScreen(boolean z) {
                        synchronized (HardwareManager.this.mLock) {
                            try {
                                if (!DesktopModeFeature.IS_TABLET && z) {
                                    HardwareManager hardwareManager = HardwareManager.this;
                                    if (hardwareManager.mIsExternalDisplayConnected) {
                                        hardwareManager.logConnectedAccessoryInformationLocked(
                                                ((StateManager) hardwareManager.mStateManager)
                                                        .getState());
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onStartLoadingScreen(boolean z) {
                        HardwareManager hardwareManager = HardwareManager.this;
                        hardwareManager.mPowerManagerInternal.setGoToSleepPrevention(true);
                        hardwareManager.mWindowManager.setEventDispatching(false);
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onStopLoadingScreen(boolean z) {
                        HardwareManager hardwareManager = HardwareManager.this;
                        hardwareManager.mPowerManagerInternal.setGoToSleepPrevention(false);
                        hardwareManager.mWindowManager.setEventDispatching(true);
                    }
                };
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("desktopmode_hw", -2);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mHandler = handler;
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(stateListener);
        this.mPowerManagerInternal = powerManagerInternal;
        this.mResolver = context.getContentResolver();
        this.mWindowManager = windowManagerService;
        this.mInputManager = inputManager;
        inputManager.registerInputDeviceListener(inputDeviceListener, handler);
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(displayListener, handler);
        this.mIDisplayManager = iDisplayManager;
        uEventObserver.startObserving("DEVPATH=/devices/virtual/sec/ccic");
        uEventObserver2.startObserving("DEVTYPE=usb_interface");
        this.mSettingsHelper = settingsHelper;
        if (DesktopModeFeature.SUPPORT_STANDALONE
                && context.getPackageManager().hasSystemFeature("com.sec.feature.cover")) {
            Intent registerReceiverAsUser =
                    context.registerReceiverAsUser(
                            new AnonymousClass6(this, 2),
                            UserHandle.ALL,
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "com.samsung.android.input.POGO_KEYBOARD_CHANGED"),
                            null,
                            null,
                            2);
            if (registerReceiverAsUser != null) {
                String action = registerReceiverAsUser.getAction();
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]HardwareManager", "sticky intent action=" + action);
                }
                if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(action)) {
                    m413$$Nest$mupdatePogoKeyboardStatus(
                            this,
                            registerReceiverAsUser.getBooleanExtra(
                                    Constants.JSON_CLIENT_DATA_STATUS, false));
                }
            }
        }
        context.registerReceiverAsUser(
                new AnonymousClass6(this, 0),
                UserHandle.ALL,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.device.action.ACL_DISCONNECTED"),
                null,
                null,
                2);
    }

    public static boolean isSupportedDisplayType(int i) {
        return i == 2 || i == 1000 || i == 1001;
    }

    public static int resolveDockType(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        Integer num = (Integer) ((ArrayMap) sSupportedDockUsbpdIds).get(str);
        if (num != null) {
            return num.intValue();
        }
        Iterator it = ((ArrayList) sSupportedDockUsbpdIdRanges).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Range) pair.first).contains((Range) str)) {
                return ((Integer) pair.second).intValue();
            }
        }
        return -1;
    }

    public final int command(PrintWriter printWriter, String str) {
        synchronized (this.mLock) {
            try {
                if ("on".equals(str)) {
                    setForcedInternalScreenModeLocked(printWriter, true);
                    return 0;
                }
                if ("off".equals(str)) {
                    setForcedInternalScreenModeLocked(printWriter, false);
                    return 0;
                }
                if (!"toggle".equals(str)) {
                    return -1;
                }
                setForcedInternalScreenModeLocked(
                        printWriter, !this.mForcedInternalScreenModeEnabled);
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Current HardwareManager state:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mConnectedDisplay=" + this.mConnectedDisplay);
            indentingPrintWriter.println("mConnectedMouse=" + this.mConnectedMouse);
            indentingPrintWriter.println("mDisplays=" + this.mDisplays);
            indentingPrintWriter.println("mDockState=" + this.mDockState);
            indentingPrintWriter.println(
                    "mForcedInternalScreenModeEnabled=" + this.mForcedInternalScreenModeEnabled);
            indentingPrintWriter.println("mIsBtMouseDeepSleep=" + this.mIsBtMouseDeepSleep);
            indentingPrintWriter.println(
                    "mIsExternalDisplayConnected=" + this.mIsExternalDisplayConnected);
            indentingPrintWriter.println("mIsMouseConnected=" + this.mIsMouseConnected);
            indentingPrintWriter.println(
                    "mIsPogoKeyboardConnected=" + this.mIsPogoKeyboardConnected);
            indentingPrintWriter.println(
                    "mRawDockState=" + Utils.dockStateToString(this.mRawDockState));
            indentingPrintWriter.println("mRawDockUsbpdIds=" + this.mRawDockUsbpdIds);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final void initializeExternalDisplayStatusLocked(Display[] displayArr) {
        DisplayInfo displayInfo = null;
        boolean z = false;
        for (Display display : displayArr) {
            DisplayInfo displayInfo2 = new DisplayInfo(display);
            this.mDisplays.put(displayInfo2.mDisplayId, displayInfo2);
            if (isSupportedDisplayType(displayInfo2.mType)) {
                z = true;
                displayInfo = displayInfo2;
            }
        }
        if (z) {
            this.mIsExternalDisplayConnected = true;
            this.mConnectedDisplay = displayInfo;
            ((StateManager) this.mStateManager).setExternalDisplayConnected(true, displayInfo);
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]HardwareManager",
                    "initializeExternalDisplayStatusLocked(), mDisplays=" + this.mDisplays);
        }
    }

    public final void logConnectedAccessoryInformationLocked(
            StateManager.InternalState internalState) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mRawDockUsbpdIds);
        sb.append("#");
        CoverState coverState = internalState.mCoverState;
        sb.append(coverState.attached ? coverState.type : -1);
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]HardwareManager", "Log extra data = " + ((Object) sb));
        }
        DesktopModeLogger.log(this.mContext, "LCAI", sb.toString());
    }

    public final void setForcedInternalScreenModeLocked(PrintWriter printWriter, boolean z) {
        boolean z2 = DesktopModeFeature.DEBUG;
        if (z2) {
            Log.d(
                    "[DMS]HardwareManager",
                    "ADB command received; setForcedInternalScreenModeLocked(), enabled=" + z);
        }
        if (printWriter != null) {
            if (z == this.mForcedInternalScreenModeEnabled) {
                printWriter.println(
                        "Internal screen DeX mode is already turned ".concat(z ? "on!" : "off!"));
                return;
            }
            printWriter.println(
                    (z ? "Entering" : "Exiting").concat(" internal screen DeX mode..."));
        }
        this.mForcedInternalScreenModeEnabled = z;
        StateManager stateManager = (StateManager) this.mStateManager;
        stateManager.getClass();
        if (z2) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "setForcedInternalScreenModeEnabled(enabled=", ")", "[DMS]StateManager", z);
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mForcedInternalScreenModeEnabled != z) {
                    stateManager.mInternalState.mForcedInternalScreenModeEnabled = z;
                    stateManager.mHandler.post(
                            new StateManager$$ExternalSyntheticLambda0(
                                    stateManager,
                                    stateManager.copyInternalStateLocked(
                                            stateManager.mInternalState),
                                    0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        updateDockStatusLocked();
    }

    public final void setRawDockStateLocked(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]HardwareManager",
                    "setRawDockStateLocked(), state=" + Utils.dockStateToString(i));
        }
        this.mRawDockState = i;
    }

    public final void setRawDockUsbpdIdsLocked(String str) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]HardwareManager", "setRawDockUsbpdIdsLocked(), usbpdIds=" + str);
        }
        this.mRawDockUsbpdIds = str;
    }

    public final boolean updateDockStatusLocked() {
        DisplayInfo displayInfo;
        int i;
        if (this.mRawDockState == -1) {
            setRawDockStateLocked(Utils.readFile(0, "/sys/class/sec/ccic/usbpd_type"));
            setRawDockUsbpdIdsLocked(Utils.readFile("/sys/class/sec/ccic/usbpd_ids", ""));
        }
        DockState dockState = this.mDockState;
        int resolveDockType = this.mRawDockState == 0 ? -1 : resolveDockType(this.mRawDockUsbpdIds);
        DockState dockState2 =
                resolveDockType != -1
                        ? (resolveDockType == 10001
                                        || resolveDockType == 10004
                                        || resolveDockType == 10006)
                                ? new DockState(this.mRawDockUsbpdIds, true, false, resolveDockType)
                                : new DockState(this.mRawDockUsbpdIds, true, true, resolveDockType)
                        : (!this.mIsExternalDisplayConnected
                                        || !(dockState.isUndocked() || dockState.mType == 10002)
                                        || (displayInfo = this.mConnectedDisplay) == null
                                        || displayInfo.mType == 1001)
                                ? new DockState()
                                : new DockState(this.mRawDockUsbpdIds, true, true, 10002);
        boolean z = DesktopModeFeature.DEBUG;
        if (z) {
            Log.d(
                    "[DMS]HardwareManager",
                    "updateDockStatusLocked(), new=" + dockState2 + ", old=" + dockState);
        }
        if (dockState.mType == dockState2.mType) {
            return false;
        }
        this.mDockState = dockState2;
        StateManager stateManager = (StateManager) this.mStateManager;
        stateManager.getClass();
        if (z) {
            Log.d("[DMS]StateManager", "setDockState(dockState=" + dockState2 + ")");
        }
        synchronized (stateManager.mLock) {
            stateManager.mInternalState.mPreviousDockState = stateManager.mInternalState.mDockState;
            stateManager.mInternalState.mDockState = dockState2;
            stateManager.mHandler.post(
                    new StateManager$$ExternalSyntheticLambda0(
                            stateManager,
                            stateManager.copyInternalStateLocked(stateManager.mInternalState),
                            10));
        }
        if (DesktopModeFeature.SUPPORT_SFC
                && (i = ((StateManager) this.mStateManager).getState().mCurrentUserId) != -10000) {
            this.mSettingsHelper.backupOrRestoreSuperFastCharging(i, dockState2.isDexPad());
        }
        if (dockState2.isUndocked()) {
            AnonymousClass6 anonymousClass6 = this.mBatteryChangedListener;
            if (anonymousClass6 != null) {
                anonymousClass6.this$0.mContext.unregisterReceiver(anonymousClass6);
                ((StateManager) anonymousClass6.this$0.mStateManager).setWiredCharging(false);
                this.mBatteryChangedListener = null;
            }
        } else {
            AnonymousClass6 anonymousClass62 = new AnonymousClass6(this, 1);
            this.mBatteryChangedListener = anonymousClass62;
            anonymousClass62.updateWiredChargingStatus(
                    ((StateManager) this.mStateManager).getState(),
                    this.mContext.registerReceiverAsUser(
                            anonymousClass62,
                            UserHandle.ALL,
                            new IntentFilter("android.intent.action.BATTERY_CHANGED"),
                            null,
                            null,
                            2));
        }
        DesktopModeSettings.setSettings(this.mResolver, "dock_usbpd_ids", dockState2.mType);
        return true;
    }

    public final void updateInputDeviceStatusLocked() {
        int[] inputDeviceIds = this.mInputManager.getInputDeviceIds();
        int length = inputDeviceIds.length;
        int i = 0;
        boolean z = false;
        while (true) {
            if (i >= length) {
                break;
            }
            InputDevice inputDevice = this.mInputManager.getInputDevice(inputDeviceIds[i]);
            if (inputDevice != null && inputDevice.isExternal()) {
                boolean z2 = (inputDevice.getSources() & 14) != 0;
                z |= z2;
                if (z2) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]HardwareManager", "Connected mouse=" + inputDevice);
                    }
                    this.mConnectedMouse = inputDevice;
                }
            }
            i++;
        }
        boolean z3 = this.mIsMouseConnected != z;
        this.mIsMouseConnected = z;
        if (this.mIsBtMouseDeepSleep) {
            this.mIsMouseConnected = true;
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]HardwareManager",
                        "Enter the BT mouse deep sleep routine in updateInputDeviceStatusLocked()");
            }
            this.mIsBtMouseDeepSleep = false;
        }
        if (z3) {
            StateManager stateManager = (StateManager) this.mStateManager;
            stateManager.getClass();
            if (DesktopModeFeature.DEBUG) {
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        "setMouseConnected(connected=", ")", "[DMS]StateManager", z);
            }
            synchronized (stateManager.mLock) {
                try {
                    if (stateManager.mInternalState.mIsMouseConnected != z) {
                        stateManager.mInternalState.mIsMouseConnected = z;
                        stateManager.mHandler.post(
                                new StateManager$$ExternalSyntheticLambda2(
                                        stateManager,
                                        stateManager.copyInternalStateLocked(
                                                stateManager.mInternalState),
                                        1));
                    }
                } finally {
                }
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]HardwareManager",
                    "updateInputDeviceStatusLocked(), mIsMouseConnected=" + this.mIsMouseConnected);
        }
    }
}
