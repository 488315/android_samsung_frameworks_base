package com.android.server;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ExtconUEventObserver;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class DockObserver extends SystemService {
    public int mActualDockState;
    public String mActualUsbpdIds;
    public final boolean mAllowTheaterModeWakeFromDock;
    public final AnonymousClass3 mCcicObserver;
    public final DeviceProvisionedObserver mDeviceProvisionedObserver;
    public final List mExtconStateConfigs;
    public final AnonymousClass2 mExtconUEventObserver;
    public final AnonymousClass1 mHandler;
    public final boolean mKeepDreamingWhenUnplugging;
    public int mLastUeventDevice;
    public final Object mLock;
    public final LogRecent mLogRecent;
    public final PowerManager mPowerManager;
    public int mPreviousDockState;
    public int mReportedDockState;
    public String mReportedUsbpdIds;
    public boolean mSystemReady;
    public boolean mUpdatesStopped;
    public String mUsbpdIds;
    public final PowerManager.WakeLock mWakeLock;

    public final class BinderService extends Binder {
        public BinderService() {
        }

        @Override // android.os.Binder
        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            String str;
            if (DumpUtils.checkDumpPermission(DockObserver.this.getContext(), "DockObserver", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (DockObserver.this.mLock) {
                        if (strArr != null) {
                            try {
                            } catch (NumberFormatException unused) {
                                printWriter.println("Bad value: " + str);
                            } finally {
                            }
                            if (strArr.length != 0 && !"-a".equals(strArr[0])) {
                                if (strArr.length == 3 && "set".equals(strArr[0])) {
                                    String str2 = strArr[1];
                                    str = strArr[2];
                                    if (LauncherConfigurationInternal.KEY_STATE_BOOLEAN.equals(str2)) {
                                        DockObserver dockObserver = DockObserver.this;
                                        dockObserver.mUpdatesStopped = true;
                                        dockObserver.setDockStateLocked(Integer.parseInt(str));
                                    } else {
                                        printWriter.println("Unknown set option: " + str2);
                                    }
                                } else if (strArr.length == 1 && "reset".equals(strArr[0])) {
                                    DockObserver dockObserver2 = DockObserver.this;
                                    dockObserver2.mUpdatesStopped = false;
                                    dockObserver2.setDockStateLocked(dockObserver2.mActualDockState);
                                } else {
                                    printWriter.println("Dump current dock state, or:");
                                    printWriter.println("  set state <value>");
                                    printWriter.println("  reset");
                                }
                            }
                        }
                        printWriter.println("Current Dock Observer Service state:");
                        if (DockObserver.this.mUpdatesStopped) {
                            printWriter.println("  (UPDATES STOPPED -- use 'reset' to restart)");
                        }
                        printWriter.println("  reported state: " + DockObserver.this.mReportedDockState);
                        printWriter.println("  previous state: " + DockObserver.this.mPreviousDockState);
                        printWriter.println("  actual state: " + DockObserver.this.mActualDockState);
                        printWriter.println(" last uevent device: " + DockObserver.this.mLastUeventDevice);
                        if (DockObserver.this.mLogRecent != null) {
                            printWriter.println(" == Latest states from Driver ==");
                            for (int i = 0; i < 10; i++) {
                                printWriter.println("   Time: " + DockObserver.this.mLogRecent.uEventTime[i] + " State: " + DockObserver.this.mLogRecent.uEventType[i] + " UsbpdIds: " + DockObserver.this.mLogRecent.uEventUsbpdIds[i]);
                            }
                            printWriter.println(" == Latest states to Apps ==");
                            for (int i2 = 0; i2 < 10; i2++) {
                                printWriter.println("  Time: " + DockObserver.this.mLogRecent.reportTime[i2] + " State: " + DockObserver.this.mLogRecent.report[i2] + " UsbpdIds: " + DockObserver.this.mLogRecent.reportUsbpdIds[i2]);
                            }
                        } else {
                            printWriter.println(" Cannot log latest events!");
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public final class DeviceProvisionedObserver extends ContentObserver {
        public boolean mRegistered;

        public DeviceProvisionedObserver(AnonymousClass1 anonymousClass1) {
            super(anonymousClass1);
        }

        public final boolean isDeviceProvisioned() {
            return Settings.Global.getInt(DockObserver.this.getContext().getContentResolver(), "device_provisioned", 0) != 0;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            synchronized (DockObserver.this.mLock) {
                try {
                    updateRegistration();
                    if (isDeviceProvisioned()) {
                        DockObserver dockObserver = DockObserver.this;
                        if (dockObserver.mReportedDockState != 0) {
                            dockObserver.mWakeLock.acquire();
                            dockObserver.mHandler.sendEmptyMessage(0);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateRegistration() {
            boolean z = !isDeviceProvisioned();
            if (z == this.mRegistered) {
                return;
            }
            ContentResolver contentResolver = DockObserver.this.getContext().getContentResolver();
            if (z) {
                contentResolver.registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this);
            } else {
                contentResolver.unregisterContentObserver(this);
            }
            this.mRegistered = z;
        }
    }

    public final class ExtconStateConfig {
        public final int extraStateValue;
        public final List keyValuePairs = new ArrayList();

        public ExtconStateConfig(int i) {
            this.extraStateValue = i;
        }
    }

    public final class ExtconStateProvider {
        public final Map mState;

        public ExtconStateProvider(Map map) {
            this.mState = map;
        }

        public static ExtconStateProvider fromString(String str) {
            HashMap hashMap = new HashMap();
            for (String str2 : str.split("\n")) {
                String[] split = str2.split("=");
                if (split.length == 2) {
                    hashMap.put(split[0], split[1]);
                } else {
                    Slog.e("DockObserver", "Invalid line: ".concat(str2));
                }
            }
            return new ExtconStateProvider(hashMap);
        }
    }

    public final class LogRecent {
        public int mCurrentReportLogIndex;
        public int mCurrentUeventLogIndex;
        public int[] report;
        public long[] reportTime;
        public String[] reportUsbpdIds;
        public long[] uEventTime;
        public int[] uEventType;
        public String[] uEventUsbpdIds;

        public final void log(int i, int i2, String str) {
            if (i == 0) {
                if (this.mCurrentUeventLogIndex % 10 == 0) {
                    this.mCurrentUeventLogIndex = 0;
                }
                this.uEventTime[this.mCurrentUeventLogIndex] = SystemClock.uptimeMillis();
                int i3 = this.mCurrentUeventLogIndex;
                this.uEventType[i3] = i2;
                this.uEventUsbpdIds[i3] = str;
                this.mCurrentUeventLogIndex = i3 + 1;
                return;
            }
            if (this.mCurrentReportLogIndex % 10 == 0) {
                this.mCurrentReportLogIndex = 0;
            }
            this.reportTime[this.mCurrentReportLogIndex] = SystemClock.uptimeMillis();
            int i4 = this.mCurrentReportLogIndex;
            this.report[i4] = i2;
            this.reportUsbpdIds[i4] = str;
            this.mCurrentReportLogIndex = i4 + 1;
        }
    }

    public DockObserver(Context context) {
        super(context);
        ArrayList arrayList;
        ExtconStateProvider extconStateProvider;
        this.mLock = new Object();
        this.mActualDockState = 0;
        this.mReportedDockState = 0;
        this.mPreviousDockState = 0;
        this.mLastUeventDevice = -1;
        ?? r1 = new Handler() { // from class: com.android.server.DockObserver.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                String str;
                if (message.what != 0) {
                    return;
                }
                DockObserver dockObserver = DockObserver.this;
                synchronized (dockObserver.mLock) {
                    try {
                        Slog.i("DockObserver", "Dock state changed from " + dockObserver.mPreviousDockState + " to " + dockObserver.mReportedDockState);
                        dockObserver.mPreviousDockState = dockObserver.mReportedDockState;
                        dockObserver.getContext().getContentResolver();
                        if (dockObserver.mDeviceProvisionedObserver.isDeviceProvisioned()) {
                            LogRecent logRecent = dockObserver.mLogRecent;
                            if (logRecent != null) {
                                logRecent.log(1, dockObserver.mReportedDockState, dockObserver.mReportedUsbpdIds);
                            }
                            Intent intent = new Intent("android.intent.action.DOCK_EVENT");
                            intent.addFlags(536870912);
                            intent.putExtra("android.intent.extra.DOCK_STATE", dockObserver.mReportedDockState);
                            if (dockObserver.mReportedDockState == 200 && (str = dockObserver.mReportedUsbpdIds) != null && !str.equals("")) {
                                intent.putExtra("com.sec.intent.extra.DOCK_ID", dockObserver.mReportedUsbpdIds);
                            }
                            dockObserver.getContext().sendStickyBroadcastAsUser(intent, UserHandle.ALL);
                        } else {
                            Slog.i("DockObserver", "Device not provisioned, skipping dock broadcast");
                        }
                    } finally {
                    }
                }
                DockObserver.this.mWakeLock.release();
            }
        };
        this.mHandler = r1;
        this.mExtconUEventObserver = new ExtconUEventObserver() { // from class: com.android.server.DockObserver.2
            @Override // com.android.server.ExtconUEventObserver
            public final void onUEvent(ExtconUEventObserver.ExtconInfo extconInfo, UEventObserver.UEvent uEvent) {
                Slog.v("DockObserver", "Dock UEVENT: " + uEvent.toString());
                DockObserver dockObserver = DockObserver.this;
                dockObserver.mLastUeventDevice = 0;
                synchronized (dockObserver.mLock) {
                    try {
                        String str = uEvent.get("STATE");
                        if (str != null) {
                            DockObserver.this.setDockStateFromProviderLocked(ExtconStateProvider.fromString(str));
                        } else {
                            Slog.e("DockObserver", "Extcon event missing STATE: " + uEvent);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mCcicObserver = new UEventObserver() { // from class: com.android.server.DockObserver.3
            public final void onUEvent(UEventObserver.UEvent uEvent) {
                Slog.i("DockObserver", "CCIC Dock UEVENT: " + uEvent.toString());
                DockObserver dockObserver = DockObserver.this;
                dockObserver.mLastUeventDevice = 1;
                try {
                    synchronized (dockObserver.mLock) {
                        DockObserver.this.mUsbpdIds = uEvent.get("USBPD_IDS");
                        DockObserver.this.setActualDockStateLocked(Integer.parseInt(uEvent.get("SWITCH_STATE")));
                    }
                } catch (NumberFormatException unused) {
                    Slog.e("DockObserver", "Could not parse switch state from event " + uEvent);
                }
            }
        };
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mWakeLock = powerManager.newWakeLock(1, "DockObserver");
        this.mAllowTheaterModeWakeFromDock = context.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromLidSwitch);
        this.mKeepDreamingWhenUnplugging = context.getResources().getBoolean(R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled);
        this.mDeviceProvisionedObserver = new DeviceProvisionedObserver(r1);
        LogRecent logRecent = new LogRecent();
        logRecent.mCurrentUeventLogIndex = 0;
        logRecent.mCurrentReportLogIndex = 0;
        logRecent.uEventTime = new long[10];
        logRecent.uEventType = new int[10];
        logRecent.reportTime = new long[10];
        logRecent.report = new int[10];
        logRecent.uEventUsbpdIds = new String[10];
        logRecent.reportUsbpdIds = new String[10];
        this.mLogRecent = logRecent;
        String[] stringArray = context.getResources().getStringArray(R.array.disallowed_apps_managed_profile);
        try {
            arrayList = new ArrayList();
            for (String str : stringArray) {
                String[] split = str.split(",");
                ExtconStateConfig extconStateConfig = new ExtconStateConfig(Integer.parseInt(split[0]));
                for (int i = 1; i < split.length; i++) {
                    String[] split2 = split[i].split("=");
                    if (split2.length != 2) {
                        throw new IllegalArgumentException("Invalid key-value: " + split[i]);
                    }
                    ((ArrayList) extconStateConfig.keyValuePairs).add(Pair.create(split2[0], split2[1]));
                }
                arrayList.add(extconStateConfig);
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Slog.e("DockObserver", "Could not parse extcon state config", e);
            arrayList = new ArrayList();
        }
        this.mExtconStateConfigs = arrayList;
        ArrayList arrayList2 = (ArrayList) ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(new String[]{"DOCK"});
        if (arrayList2.isEmpty()) {
            Slog.i("DockObserver", "No extcon dock device found in this kernel.");
        } else {
            ExtconUEventObserver.ExtconInfo extconInfo = (ExtconUEventObserver.ExtconInfo) arrayList2.get(0);
            StringBuilder sb = new StringBuilder("Found extcon info devPath: ");
            sb.append(extconInfo.getDevicePath());
            sb.append(", statePath: ");
            String str2 = extconInfo.mName;
            sb.append(TextUtils.formatSimple("/sys/class/extcon/%s/state", new Object[]{str2}));
            Slog.i("DockObserver", sb.toString());
            String formatSimple = TextUtils.formatSimple("/sys/class/extcon/%s/state", new Object[]{str2});
            char[] cArr = new char[1024];
            try {
            } catch (FileNotFoundException unused) {
                Slog.w("DockObserver", "No state file found at: " + formatSimple);
                extconStateProvider = new ExtconStateProvider(new HashMap());
            } catch (Exception e2) {
                Slog.e("DockObserver", "", e2);
                extconStateProvider = new ExtconStateProvider(new HashMap());
            }
            try {
                extconStateProvider = ExtconStateProvider.fromString(new String(cArr, 0, new FileReader(formatSimple).read(cArr, 0, 1024)).trim());
                setDockStateFromProviderLocked(extconStateProvider);
                this.mPreviousDockState = this.mActualDockState;
                AnonymousClass2 anonymousClass2 = this.mExtconUEventObserver;
                anonymousClass2.getClass();
                String devicePath = extconInfo.getDevicePath();
                if (devicePath == null) {
                    Slog.wtf("ExtconUEventObserver", "Unable to start observing  " + str2 + " because the device path is null. This probably means the selinux policies need to be changed.");
                } else {
                    ((ArrayMap) anonymousClass2.mExtconInfos).put(devicePath, extconInfo);
                    anonymousClass2.startObserving("DEVPATH=".concat(devicePath));
                }
            } catch (Throwable th) {
                try {
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (this.mActualDockState == 0) {
            try {
                char[] cArr2 = new char[1024];
                FileReader fileReader = new FileReader("/sys/class/sec/ccic/usbpd_ids");
                try {
                    this.mUsbpdIds = new String(cArr2, 0, fileReader.read(cArr2, 0, 1024)).trim();
                    fileReader.close();
                    char[] cArr3 = new char[1024];
                    fileReader = new FileReader("/sys/class/sec/ccic/usbpd_type");
                    try {
                        setActualDockStateLocked(Integer.valueOf(new String(cArr3, 0, fileReader.read(cArr3, 0, 1024)).trim()).intValue());
                        this.mPreviousDockState = this.mActualDockState;
                        fileReader.close();
                    } finally {
                    }
                } finally {
                }
            } catch (FileNotFoundException unused2) {
                Slog.w("DockObserver", "This kernel does not have ccic dock station support");
            } catch (Exception e3) {
                Slog.e("DockObserver", "", e3);
            }
        }
        startObserving("DEVPATH=/devices/virtual/sec/ccic");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 550) {
            synchronized (this.mLock) {
                this.mSystemReady = true;
                this.mDeviceProvisionedObserver.updateRegistration();
                if (this.mReportedDockState != 0) {
                    this.mWakeLock.acquire();
                    sendEmptyMessage(0);
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("DockObserver", new BinderService());
        FrameworkStatsLog.write(FrameworkStatsLog.DOCK_STATE_CHANGED, this.mReportedDockState);
    }

    public final void setActualDockStateLocked(int i) {
        this.mActualDockState = i;
        String str = this.mUsbpdIds;
        this.mActualUsbpdIds = str;
        LogRecent logRecent = this.mLogRecent;
        if (logRecent != null) {
            logRecent.log(0, i, str);
        }
        if (this.mUpdatesStopped) {
            return;
        }
        setDockStateLocked(i);
    }

    public void setDockStateFromProviderForTesting(ExtconStateProvider extconStateProvider) {
        synchronized (this.mLock) {
            setDockStateFromProviderLocked(extconStateProvider);
        }
    }

    public final void setDockStateFromProviderLocked(ExtconStateProvider extconStateProvider) {
        int i = 0;
        if ("1".equals((String) extconStateProvider.mState.get("DOCK"))) {
            Iterator it = this.mExtconStateConfigs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = 1;
                    break;
                }
                ExtconStateConfig extconStateConfig = (ExtconStateConfig) it.next();
                Iterator it2 = ((ArrayList) extconStateConfig.keyValuePairs).iterator();
                boolean z = true;
                while (it2.hasNext()) {
                    Pair pair = (Pair) it2.next();
                    z = z && ((String) pair.second).equals((String) extconStateProvider.mState.get((String) pair.first));
                    if (!z) {
                        break;
                    }
                }
                if (z) {
                    i = extconStateConfig.extraStateValue;
                    break;
                }
            }
        }
        setActualDockStateLocked(i);
    }

    public final void setDockStateLocked(int i) {
        if (i != this.mReportedDockState) {
            this.mReportedDockState = i;
            this.mReportedUsbpdIds = this.mActualUsbpdIds;
            if (this.mSystemReady) {
                if (!this.mKeepDreamingWhenUnplugging && (this.mAllowTheaterModeWakeFromDock || Settings.Global.getInt(getContext().getContentResolver(), "theater_mode_on", 0) == 0)) {
                    this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), "android.server:DOCK");
                }
                this.mWakeLock.acquire();
                sendEmptyMessage(0);
            }
        }
    }
}
