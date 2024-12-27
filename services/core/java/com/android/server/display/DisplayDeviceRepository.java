package com.android.server.display;

import android.os.PowerManager;
import android.os.Trace;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.Surface;

import com.android.server.display.utils.DebugUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public final class DisplayDeviceRepository implements DisplayAdapter.Listener {
    public static final boolean DEBUG = DebugUtils.isDebuggable("DisplayDeviceRepository");
    public final PersistentDataStore mPersistentDataStore;
    public final DisplayManagerService.SyncRoot mSyncRoot;
    public final List mDisplayDevices = new ArrayList();
    public final List mListeners = new ArrayList();
    public PowerManager.WakeLock mHDMIWakeLock = null;
    public boolean mNeedWakeLock = false;

    public interface Listener {}

    public DisplayDeviceRepository(
            DisplayManagerService.SyncRoot syncRoot, PersistentDataStore persistentDataStore) {
        this.mSyncRoot = syncRoot;
        this.mPersistentDataStore = persistentDataStore;
    }

    public static boolean isExternalDisplayDeviceForDexLocked(DisplayDeviceInfo displayDeviceInfo) {
        if (displayDeviceInfo.type != 2) {
            int i = displayDeviceInfo.flags;
            if ((134217728 & i) == 0 && (i & 67108864) == 0) {
                return false;
            }
        }
        return true;
    }

    public final void forEachLocked(Consumer consumer) {
        int size = ((ArrayList) this.mDisplayDevices).size();
        for (int i = 0; i < size; i++) {
            consumer.accept((DisplayDevice) ((ArrayList) this.mDisplayDevices).get(i));
        }
    }

    public final DisplayDevice getByAddressLocked(DisplayAddress displayAddress) {
        for (int size = ((ArrayList) this.mDisplayDevices).size() - 1; size >= 0; size--) {
            DisplayDevice displayDevice =
                    (DisplayDevice) ((ArrayList) this.mDisplayDevices).get(size);
            DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
            if (displayAddress.equals(displayDeviceInfoLocked.address)
                    || DisplayAddress.Physical.isPortMatch(
                            displayAddress, displayDeviceInfoLocked.address)) {
                return displayDevice;
            }
        }
        return null;
    }

    public final DisplayDevice getByUniqueIdLocked(String str) {
        for (int size = ((ArrayList) this.mDisplayDevices).size() - 1; size >= 0; size--) {
            DisplayDevice displayDevice =
                    (DisplayDevice) ((ArrayList) this.mDisplayDevices).get(size);
            if (displayDevice.mUniqueId.equals(str)) {
                return displayDevice;
            }
        }
        return null;
    }

    public final DisplayDevice getDexDisplayDeviceLocked() {
        Iterator it = ((ArrayList) this.mDisplayDevices).iterator();
        while (it.hasNext()) {
            DisplayDevice displayDevice = (DisplayDevice) it.next();
            if ((displayDevice.getDisplayDeviceInfoLocked().flags & 1048576) != 0) {
                return displayDevice;
            }
        }
        return null;
    }

    public final boolean isDexDisplayDeviceEnabledLocked() {
        DisplayDevice dexDisplayDeviceLocked = getDexDisplayDeviceLocked();
        return dexDisplayDeviceLocked != null && dexDisplayDeviceLocked.getDexEnabledStateLocked();
    }

    public final void onDisplayDeviceEvent(DisplayDevice displayDevice, int i) {
        String str;
        String str2;
        PowerManager.WakeLock wakeLock;
        PowerManager.WakeLock wakeLock2;
        boolean z = DEBUG;
        if (z) {
            str = "DisplayDeviceRepository#onDisplayDeviceEvent (event=" + i + ")";
            Trace.beginAsyncSection(str, 0);
        } else {
            str = null;
        }
        boolean z2 = true;
        if (i != 1) {
            if (i == 2) {
                synchronized (this.mSyncRoot) {
                    try {
                        DisplayDeviceInfo displayDeviceInfoLocked =
                                displayDevice.getDisplayDeviceInfoLocked();
                        if (((ArrayList) this.mDisplayDevices).contains(displayDevice)) {
                            str2 = str;
                            if (z) {
                                Trace.traceBegin(131072L, "handleDisplayDeviceChanged");
                            }
                            int diff =
                                    displayDevice.mDebugLastLoggedDeviceInfo.diff(
                                            displayDeviceInfoLocked);
                            if (diff == 2) {
                                Slog.i(
                                        "DisplayDeviceRepository",
                                        "Display device changed state: \""
                                                + displayDeviceInfoLocked.name
                                                + "\", "
                                                + Display.stateToString(
                                                        displayDeviceInfoLocked.state));
                            } else if (diff == 32) {
                                Slog.i(
                                        "DisplayDeviceRepository",
                                        "Display device rotated: \""
                                                + displayDeviceInfoLocked.name
                                                + "\", "
                                                + Surface.rotationToString(
                                                        displayDeviceInfoLocked.rotation));
                            } else if (diff == 192) {
                                Slog.i(
                                        "DisplayDeviceRepository",
                                        "Display device changed render timings: \""
                                                + displayDeviceInfoLocked.name
                                                + "\", renderFrameRate="
                                                + displayDeviceInfoLocked.renderFrameRate
                                                + ", presentationDeadlineNanos="
                                                + displayDeviceInfoLocked.presentationDeadlineNanos
                                                + ", appVsyncOffsetNanos="
                                                + displayDeviceInfoLocked.appVsyncOffsetNanos);
                            } else if (diff == 4) {
                                if (z) {
                                    Slog.i(
                                            "DisplayDeviceRepository",
                                            "Display device changed committed state: \""
                                                    + displayDeviceInfoLocked.name
                                                    + "\", "
                                                    + Display.stateToString(
                                                            displayDeviceInfoLocked
                                                                    .committedState));
                                }
                            } else if (diff != 16) {
                                Slog.i(
                                        "DisplayDeviceRepository",
                                        "Display device changed: " + displayDeviceInfoLocked);
                            }
                            if ((diff & 8) != 0) {
                                try {
                                    PersistentDataStore persistentDataStore =
                                            this.mPersistentDataStore;
                                    int i2 = displayDeviceInfoLocked.colorMode;
                                    persistentDataStore.getClass();
                                    if (displayDevice.hasStableUniqueId()) {
                                        PersistentDataStore.DisplayState displayState =
                                                persistentDataStore.getDisplayState(
                                                        displayDevice.mUniqueId, true);
                                        if (i2 != displayState.mColorMode) {
                                            displayState.mColorMode = i2;
                                            persistentDataStore.mDirty = true;
                                        }
                                    }
                                    this.mPersistentDataStore.saveIfNeeded();
                                } catch (Throwable th) {
                                    this.mPersistentDataStore.saveIfNeeded();
                                    throw th;
                                }
                            }
                            displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
                            displayDevice.applyPendingDisplayDeviceInfoChangesLocked();
                            sendChangedEventLocked(displayDevice, diff);
                            if (z) {
                                Trace.traceEnd(131072L);
                            }
                        } else {
                            Slog.w(
                                    "DisplayDeviceRepository",
                                    "Attempted to change non-existent display device: "
                                            + displayDeviceInfoLocked);
                        }
                    } finally {
                    }
                }
            } else if (i == 3) {
                synchronized (this.mSyncRoot) {
                    try {
                        DisplayDeviceInfo displayDeviceInfoLocked2 =
                                displayDevice.getDisplayDeviceInfoLocked();
                        if (((ArrayList) this.mDisplayDevices).remove(displayDevice)) {
                            if (displayDeviceInfoLocked2.type != 2) {
                                z2 = false;
                            }
                            Slog.i(
                                    "DisplayDeviceRepository",
                                    "Display device removed: " + displayDeviceInfoLocked2);
                            displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked2;
                            sendEventLocked(displayDevice, 3);
                            if (!this.mNeedWakeLock
                                    && z2
                                    && (wakeLock2 = this.mHDMIWakeLock) != null
                                    && wakeLock2.isHeld()) {
                                this.mHDMIWakeLock.release();
                            }
                        } else {
                            Slog.w(
                                    "DisplayDeviceRepository",
                                    "Attempted to remove non-existent display device: "
                                            + displayDeviceInfoLocked2);
                        }
                    } finally {
                    }
                }
            }
            str2 = str;
        } else {
            str2 = str;
            synchronized (this.mSyncRoot) {
                try {
                    DisplayDeviceInfo displayDeviceInfoLocked3 =
                            displayDevice.getDisplayDeviceInfoLocked();
                    if (((ArrayList) this.mDisplayDevices).contains(displayDevice)) {
                        Slog.w(
                                "DisplayDeviceRepository",
                                "Attempted to add already added display device: "
                                        + displayDeviceInfoLocked3);
                    } else {
                        boolean z3 = displayDeviceInfoLocked3.type == 2;
                        Slog.i(
                                "DisplayDeviceRepository",
                                "Display device added: " + displayDeviceInfoLocked3);
                        displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked3;
                        ((ArrayList) this.mDisplayDevices).add(displayDevice);
                        sendEventLocked(displayDevice, 1);
                        if (this.mNeedWakeLock
                                && z3
                                && (wakeLock = this.mHDMIWakeLock) != null
                                && !wakeLock.isHeld()) {
                            this.mHDMIWakeLock.acquire();
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            Trace.endAsyncSection(str2, 0);
        }
    }

    public final void sendChangedEventLocked(DisplayDevice displayDevice, int i) {
        int size = ((ArrayList) this.mListeners).size();
        for (int i2 = 0; i2 < size; i2++) {
            LogicalDisplayMapper logicalDisplayMapper =
                    (LogicalDisplayMapper) ((Listener) ((ArrayList) this.mListeners).get(i2));
            if (LogicalDisplayMapper.DEBUG) {
                logicalDisplayMapper.getClass();
                Slog.d(
                        "LogicalDisplayMapper",
                        "Display device changed: " + displayDevice.getDisplayDeviceInfoLocked());
            }
            logicalDisplayMapper.finishStateTransitionLocked(false);
            logicalDisplayMapper.updateLogicalDisplaysLocked(i, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendEventLocked(com.android.server.display.DisplayDevice r24, int r25) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayDeviceRepository.sendEventLocked(com.android.server.display.DisplayDevice,"
                    + " int):void");
    }
}
