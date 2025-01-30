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
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ExtconUEventObserver;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class DockObserver extends SystemService {
    public int mActualDockState;
    public String mActualUsbpdIds;
    public final boolean mAllowTheaterModeWakeFromDock;
    public final UEventObserver mCcicObserver;
    public DeviceProvisionedObserver mDeviceProvisionedObserver;
    public final List mExtconStateConfigs;
    public final ExtconUEventObserver mExtconUEventObserver;
    public final Handler mHandler;
    public final boolean mKeepDreamingWhenUnplugging;
    public int mLastUeventDevice;
    public final Object mLock;
    public LogRecent mLogRecent;
    public final PowerManager mPowerManager;
    public int mPreviousDockState;
    public int mReportedDockState;
    public String mReportedUsbpdIds;
    public boolean mSystemReady;
    public boolean mUpdatesStopped;
    public String mUsbpdIds;
    public final PowerManager.WakeLock mWakeLock;

    public class LogRecent {
        public int mCurrentUeventLogIndex = 0;
        public int mCurrentReportLogIndex = 0;
        public long[] uEventTime = new long[10];
        public int[] uEventType = new int[10];
        public long[] reportTime = new long[10];
        public int[] report = new int[10];
        public String[] uEventUsbpdIds = new String[10];
        public String[] reportUsbpdIds = new String[10];

        public void log(int i, int i2, String str) {
            if (i == 0) {
                if (this.mCurrentUeventLogIndex % 10 == 0) {
                    this.mCurrentUeventLogIndex = 0;
                }
                this.uEventTime[this.mCurrentUeventLogIndex] = SystemClock.uptimeMillis();
                int[] iArr = this.uEventType;
                int i3 = this.mCurrentUeventLogIndex;
                iArr[i3] = i2;
                this.uEventUsbpdIds[i3] = str;
                this.mCurrentUeventLogIndex = i3 + 1;
                return;
            }
            if (this.mCurrentReportLogIndex % 10 == 0) {
                this.mCurrentReportLogIndex = 0;
            }
            this.reportTime[this.mCurrentReportLogIndex] = SystemClock.uptimeMillis();
            int[] iArr2 = this.report;
            int i4 = this.mCurrentReportLogIndex;
            iArr2[i4] = i2;
            this.reportUsbpdIds[i4] = str;
            this.mCurrentReportLogIndex = i4 + 1;
        }

        public LogRecent() {
        }
    }

    public final class ExtconStateProvider {
        public final Map mState;

        public ExtconStateProvider(Map map) {
            this.mState = map;
        }

        public String getValue(String str) {
            return (String) this.mState.get(str);
        }

        public static ExtconStateProvider fromString(String str) {
            HashMap hashMap = new HashMap();
            for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE)) {
                String[] split = str2.split("=");
                if (split.length == 2) {
                    hashMap.put(split[0], split[1]);
                } else {
                    Slog.e("DockObserver", "Invalid line: " + str2);
                }
            }
            return new ExtconStateProvider(hashMap);
        }

        public static ExtconStateProvider fromFile(String str) {
            char[] cArr = new char[1024];
            try {
                FileReader fileReader = new FileReader(str);
                try {
                    ExtconStateProvider fromString = fromString(new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim());
                    fileReader.close();
                    return fromString;
                } finally {
                }
            } catch (FileNotFoundException unused) {
                Slog.w("DockObserver", "No state file found at: " + str);
                return new ExtconStateProvider(new HashMap());
            } catch (Exception e) {
                Slog.e("DockObserver", "", e);
                return new ExtconStateProvider(new HashMap());
            }
        }
    }

    public final class ExtconStateConfig {
        public final int extraStateValue;
        public final List keyValuePairs = new ArrayList();

        public ExtconStateConfig(int i) {
            this.extraStateValue = i;
        }
    }

    public static List loadExtconStateConfigs(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.device_state_notification_names);
        try {
            ArrayList arrayList = new ArrayList();
            for (String str : stringArray) {
                String[] split = str.split(",");
                ExtconStateConfig extconStateConfig = new ExtconStateConfig(Integer.parseInt(split[0]));
                for (int i = 1; i < split.length; i++) {
                    String[] split2 = split[i].split("=");
                    if (split2.length != 2) {
                        throw new IllegalArgumentException("Invalid key-value: " + split[i]);
                    }
                    extconStateConfig.keyValuePairs.add(Pair.create(split2[0], split2[1]));
                }
                arrayList.add(extconStateConfig);
            }
            return arrayList;
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Slog.e("DockObserver", "Could not parse extcon state config", e);
            return new ArrayList();
        }
    }

    public DockObserver(Context context) {
        super(context);
        this.mLock = new Object();
        this.mActualDockState = 0;
        this.mReportedDockState = 0;
        this.mPreviousDockState = 0;
        this.mLastUeventDevice = -1;
        Handler handler = new Handler(true) { // from class: com.android.server.DockObserver.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 0) {
                    return;
                }
                DockObserver.this.handleDockStateChange();
                DockObserver.this.mWakeLock.release();
            }
        };
        this.mHandler = handler;
        ExtconUEventObserver extconUEventObserver = new ExtconUEventObserver() { // from class: com.android.server.DockObserver.2
            @Override // com.android.server.ExtconUEventObserver
            public void onUEvent(ExtconUEventObserver.ExtconInfo extconInfo, UEventObserver.UEvent uEvent) {
                Slog.v("DockObserver", "Dock UEVENT: " + uEvent.toString());
                DockObserver.this.mLastUeventDevice = 0;
                synchronized (DockObserver.this.mLock) {
                    String str = uEvent.get("STATE");
                    if (str != null) {
                        DockObserver.this.setDockStateFromProviderLocked(ExtconStateProvider.fromString(str));
                    } else {
                        Slog.e("DockObserver", "Extcon event missing STATE: " + uEvent);
                    }
                }
            }
        };
        this.mExtconUEventObserver = extconUEventObserver;
        this.mCcicObserver = new UEventObserver() { // from class: com.android.server.DockObserver.3
            public void onUEvent(UEventObserver.UEvent uEvent) {
                Slog.i("DockObserver", "CCIC Dock UEVENT: " + uEvent.toString());
                DockObserver.this.mLastUeventDevice = 1;
                try {
                    synchronized (DockObserver.this.mLock) {
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
        this.mAllowTheaterModeWakeFromDock = context.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromDock);
        this.mKeepDreamingWhenUnplugging = context.getResources().getBoolean(R.bool.config_goToSleepOnButtonPressTheaterMode);
        this.mDeviceProvisionedObserver = new DeviceProvisionedObserver(handler);
        this.mLogRecent = new LogRecent();
        this.mExtconStateConfigs = loadExtconStateConfigs(context);
        List extconInfoForTypes = ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(new String[]{"DOCK"});
        if (!extconInfoForTypes.isEmpty()) {
            ExtconUEventObserver.ExtconInfo extconInfo = (ExtconUEventObserver.ExtconInfo) extconInfoForTypes.get(0);
            Slog.i("DockObserver", "Found extcon info devPath: " + extconInfo.getDevicePath() + ", statePath: " + extconInfo.getStatePath());
            setDockStateFromProviderLocked(ExtconStateProvider.fromFile(extconInfo.getStatePath()));
            this.mPreviousDockState = this.mActualDockState;
            extconUEventObserver.startObserving(extconInfo);
        } else {
            Slog.i("DockObserver", "No extcon dock device found in this kernel.");
        }
        if (this.mActualDockState == 0) {
            try {
                char[] cArr = new char[1024];
                FileReader fileReader = new FileReader("/sys/class/sec/ccic/usbpd_ids");
                try {
                    this.mUsbpdIds = new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim();
                    fileReader.close();
                    char[] cArr2 = new char[1024];
                    fileReader = new FileReader("/sys/class/sec/ccic/usbpd_type");
                    try {
                        setActualDockStateLocked(Integer.valueOf(new String(cArr2, 0, fileReader.read(cArr2, 0, 1024)).trim()).intValue());
                        this.mPreviousDockState = this.mActualDockState;
                        fileReader.close();
                    } finally {
                    }
                } finally {
                }
            } catch (FileNotFoundException unused) {
                Slog.w("DockObserver", "This kernel does not have ccic dock station support");
            } catch (Exception e) {
                Slog.e("DockObserver", "", e);
            }
        }
        this.mCcicObserver.startObserving("DEVPATH=/devices/virtual/sec/ccic");
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("DockObserver", new BinderService());
        FrameworkStatsLog.write(FrameworkStatsLog.DOCK_STATE_CHANGED, this.mReportedDockState);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            synchronized (this.mLock) {
                this.mSystemReady = true;
                this.mDeviceProvisionedObserver.onSystemReady();
                updateIfDockedLocked();
            }
        }
    }

    public final void updateIfDockedLocked() {
        if (this.mReportedDockState != 0) {
            updateLocked();
        }
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

    public final void setDockStateLocked(int i) {
        if (i != this.mReportedDockState) {
            this.mReportedDockState = i;
            this.mReportedUsbpdIds = this.mActualUsbpdIds;
            if (this.mSystemReady) {
                if (allowWakeFromDock()) {
                    this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), "android.server:DOCK");
                }
                updateLocked();
            }
        }
    }

    public final boolean allowWakeFromDock() {
        if (this.mKeepDreamingWhenUnplugging) {
            return false;
        }
        return this.mAllowTheaterModeWakeFromDock || Settings.Global.getInt(getContext().getContentResolver(), "theater_mode_on", 0) == 0;
    }

    public final void updateLocked() {
        this.mWakeLock.acquire();
        this.mHandler.sendEmptyMessage(0);
    }

    public final void handleDockStateChange() {
        String str;
        synchronized (this.mLock) {
            Slog.i("DockObserver", "Dock state changed from " + this.mPreviousDockState + " to " + this.mReportedDockState);
            this.mPreviousDockState = this.mReportedDockState;
            getContext().getContentResolver();
            if (!this.mDeviceProvisionedObserver.isDeviceProvisioned()) {
                Slog.i("DockObserver", "Device not provisioned, skipping dock broadcast");
                return;
            }
            LogRecent logRecent = this.mLogRecent;
            if (logRecent != null) {
                logRecent.log(1, this.mReportedDockState, this.mReportedUsbpdIds);
            }
            Intent intent = new Intent("android.intent.action.DOCK_EVENT");
            intent.addFlags(536870912);
            intent.putExtra("android.intent.extra.DOCK_STATE", this.mReportedDockState);
            if (this.mReportedDockState == 200 && (str = this.mReportedUsbpdIds) != null && !str.equals("")) {
                intent.putExtra("com.sec.intent.extra.DOCK_ID", this.mReportedUsbpdIds);
            }
            getContext().sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        }
    }

    public final int getDockedStateExtraValue(ExtconStateProvider extconStateProvider) {
        for (ExtconStateConfig extconStateConfig : this.mExtconStateConfigs) {
            boolean z = true;
            for (Pair pair : extconStateConfig.keyValuePairs) {
                z = z && ((String) pair.second).equals(extconStateProvider.getValue((String) pair.first));
                if (!z) {
                    break;
                }
            }
            if (z) {
                return extconStateConfig.extraStateValue;
            }
        }
        return 1;
    }

    public void setDockStateFromProviderForTesting(ExtconStateProvider extconStateProvider) {
        synchronized (this.mLock) {
            setDockStateFromProviderLocked(extconStateProvider);
        }
    }

    public final void setDockStateFromProviderLocked(ExtconStateProvider extconStateProvider) {
        setActualDockStateLocked("1".equals(extconStateProvider.getValue("DOCK")) ? getDockedStateExtraValue(extconStateProvider) : 0);
    }

    public final class BinderService extends Binder {
        public BinderService() {
        }

        @Override // android.os.Binder
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(DockObserver.this.getContext(), "DockObserver", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (DockObserver.this.mLock) {
                        if (strArr != null) {
                            if (strArr.length != 0 && !"-a".equals(strArr[0])) {
                                if (strArr.length == 3 && "set".equals(strArr[0])) {
                                    String str = strArr[1];
                                    String str2 = strArr[2];
                                    try {
                                        if (LauncherConfigurationInternal.KEY_STATE_BOOLEAN.equals(str)) {
                                            DockObserver.this.mUpdatesStopped = true;
                                            DockObserver.this.setDockStateLocked(Integer.parseInt(str2));
                                        } else {
                                            printWriter.println("Unknown set option: " + str);
                                        }
                                    } catch (NumberFormatException unused) {
                                        printWriter.println("Bad value: " + str2);
                                    }
                                } else if (strArr.length == 1 && "reset".equals(strArr[0])) {
                                    DockObserver.this.mUpdatesStopped = false;
                                    DockObserver dockObserver = DockObserver.this;
                                    dockObserver.setDockStateLocked(dockObserver.mActualDockState);
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

        public DeviceProvisionedObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            synchronized (DockObserver.this.mLock) {
                updateRegistration();
                if (isDeviceProvisioned()) {
                    DockObserver.this.updateIfDockedLocked();
                }
            }
        }

        public void onSystemReady() {
            updateRegistration();
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

        public boolean isDeviceProvisioned() {
            return Settings.Global.getInt(DockObserver.this.getContext().getContentResolver(), "device_provisioned", 0) != 0;
        }
    }
}
