package com.android.server.display;

import android.os.IBinder;
import android.os.PowerManager;
import android.os.Trace;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAddress;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class DisplayDeviceRepository implements DisplayAdapter.Listener {
  public static final Boolean DEBUG = Boolean.FALSE;
  public final PersistentDataStore mPersistentDataStore;
  public final DisplayManagerService.SyncRoot mSyncRoot;
  public final List mDisplayDevices = new ArrayList();
  public final List mListeners = new ArrayList();
  public PowerManager.WakeLock mHDMIWakeLock = null;
  public boolean mNeedWakeLock = false;

  public interface Listener {
    void onDisplayDeviceChangedLocked(DisplayDevice displayDevice, int i);

    void onDisplayDeviceEventLocked(DisplayDevice displayDevice, int i);

    void onTraversalRequested();
  }

  public DisplayDeviceRepository(
      DisplayManagerService.SyncRoot syncRoot, PersistentDataStore persistentDataStore) {
    this.mSyncRoot = syncRoot;
    this.mPersistentDataStore = persistentDataStore;
  }

  public void addListener(Listener listener) {
    this.mListeners.add(listener);
  }

  @Override // com.android.server.display.DisplayAdapter.Listener
  public void onDisplayDeviceEvent(DisplayDevice displayDevice, int i) {
    String str;
    Boolean bool = DEBUG;
    if (bool.booleanValue()) {
      str = "DisplayDeviceRepository#onDisplayDeviceEvent (event=" + i + ")";
      Trace.beginAsyncSection(str, 0);
    } else {
      str = null;
    }
    if (i == 1) {
      handleDisplayDeviceAdded(displayDevice);
    } else if (i == 2) {
      handleDisplayDeviceChanged(displayDevice);
    } else if (i == 3) {
      handleDisplayDeviceRemoved(displayDevice);
    }
    if (bool.booleanValue()) {
      Trace.endAsyncSection(str, 0);
    }
  }

  @Override // com.android.server.display.DisplayAdapter.Listener
  public void onTraversalRequested() {
    int size = this.mListeners.size();
    for (int i = 0; i < size; i++) {
      ((Listener) this.mListeners.get(i)).onTraversalRequested();
    }
  }

  public boolean containsLocked(DisplayDevice displayDevice) {
    return this.mDisplayDevices.contains(displayDevice);
  }

  public int sizeLocked() {
    return this.mDisplayDevices.size();
  }

  public void forEachLocked(Consumer consumer) {
    int size = this.mDisplayDevices.size();
    for (int i = 0; i < size; i++) {
      consumer.accept((DisplayDevice) this.mDisplayDevices.get(i));
    }
  }

  public DisplayDevice getByAddressLocked(DisplayAddress displayAddress) {
    for (int size = this.mDisplayDevices.size() - 1; size >= 0; size--) {
      DisplayDevice displayDevice = (DisplayDevice) this.mDisplayDevices.get(size);
      if (displayAddress.equals(displayDevice.getDisplayDeviceInfoLocked().address)) {
        return displayDevice;
      }
    }
    return null;
  }

  public void releaseHDMIWake() {
    PowerManager.WakeLock wakeLock = this.mHDMIWakeLock;
    if (wakeLock == null || !wakeLock.isHeld()) {
      return;
    }
    this.mHDMIWakeLock.release();
  }

  public void acquireHDMIWake() {
    PowerManager.WakeLock wakeLock = this.mHDMIWakeLock;
    if (wakeLock == null || wakeLock.isHeld()) {
      return;
    }
    this.mHDMIWakeLock.acquire();
  }

  public DisplayDevice getByUniqueIdLocked(String str) {
    for (int size = this.mDisplayDevices.size() - 1; size >= 0; size--) {
      DisplayDevice displayDevice = (DisplayDevice) this.mDisplayDevices.get(size);
      if (displayDevice.getUniqueId().equals(str)) {
        return displayDevice;
      }
    }
    return null;
  }

  public final void handleDisplayDeviceAdded(DisplayDevice displayDevice) {
    synchronized (this.mSyncRoot) {
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      if (this.mDisplayDevices.contains(displayDevice)) {
        Slog.w(
            "DisplayDeviceRepository",
            "Attempted to add already added display device: " + displayDeviceInfoLocked);
        return;
      }
      boolean z = displayDeviceInfoLocked.type == 2;
      Slog.i("DisplayDeviceRepository", "Display device added: " + displayDeviceInfoLocked);
      displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
      this.mDisplayDevices.add(displayDevice);
      sendEventLocked(displayDevice, 1);
      if (this.mNeedWakeLock && z) {
        acquireHDMIWake();
      }
    }
  }

  public final void handleDisplayDeviceChanged(DisplayDevice displayDevice) {
    synchronized (this.mSyncRoot) {
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      if (!this.mDisplayDevices.contains(displayDevice)) {
        Slog.w(
            "DisplayDeviceRepository",
            "Attempted to change non-existent display device: " + displayDeviceInfoLocked);
        return;
      }
      Boolean bool = DEBUG;
      if (bool.booleanValue()) {
        Trace.traceBegin(131072L, "handleDisplayDeviceChanged");
      }
      int diff = displayDevice.mDebugLastLoggedDeviceInfo.diff(displayDeviceInfoLocked);
      if (diff == 1) {
        Slog.i(
            "DisplayDeviceRepository",
            "Display device changed state: \""
                + displayDeviceInfoLocked.name
                + "\", "
                + Display.stateToString(displayDeviceInfoLocked.state));
      } else if (diff != 8) {
        Slog.i("DisplayDeviceRepository", "Display device changed: " + displayDeviceInfoLocked);
      }
      if ((diff & 4) != 0) {
        try {
          this.mPersistentDataStore.setColorMode(displayDevice, displayDeviceInfoLocked.colorMode);
          this.mPersistentDataStore.saveIfNeeded();
        } catch (Throwable th) {
          this.mPersistentDataStore.saveIfNeeded();
          throw th;
        }
      }
      displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
      displayDevice.applyPendingDisplayDeviceInfoChangesLocked();
      sendChangedEventLocked(displayDevice, diff);
      if (bool.booleanValue()) {
        Trace.traceEnd(131072L);
      }
    }
  }

  public final void handleDisplayDeviceRemoved(DisplayDevice displayDevice) {
    synchronized (this.mSyncRoot) {
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      if (!this.mDisplayDevices.remove(displayDevice)) {
        Slog.w(
            "DisplayDeviceRepository",
            "Attempted to remove non-existent display device: " + displayDeviceInfoLocked);
        return;
      }
      boolean z = displayDeviceInfoLocked.type == 2;
      Slog.i("DisplayDeviceRepository", "Display device removed: " + displayDeviceInfoLocked);
      displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
      sendEventLocked(displayDevice, 3);
      if (this.mNeedWakeLock || !z) {
        return;
      }
      releaseHDMIWake();
    }
  }

  public boolean hasExternalDisplayDevice() {
    Iterator it = this.mDisplayDevices.iterator();
    while (it.hasNext()) {
      if (((DisplayDevice) it.next()).getDisplayDeviceInfoLocked().type == 2) {
        return true;
      }
    }
    return false;
  }

  public final void sendEventLocked(DisplayDevice displayDevice, int i) {
    int size = this.mListeners.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((Listener) this.mListeners.get(i2)).onDisplayDeviceEventLocked(displayDevice, i);
    }
  }

  public final void sendChangedEventLocked(DisplayDevice displayDevice, int i) {
    int size = this.mListeners.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((Listener) this.mListeners.get(i2)).onDisplayDeviceChangedLocked(displayDevice, i);
    }
  }

  public IBinder getDisplayTokenForCurrentLayerStackLocked(DisplayDevice displayDevice, int i) {
    for (int size = this.mDisplayDevices.size() - 1; size >= 0; size--) {
      DisplayDevice displayDevice2 = (DisplayDevice) this.mDisplayDevices.get(size);
      if (displayDevice2 != displayDevice && displayDevice2.getCurrentLayerStackLocked() == i) {
        return displayDevice2.getDisplayTokenLocked();
      }
    }
    return displayDevice.getDisplayTokenLocked();
  }

  public DisplayDevice getDexDisplayDeviceLocked() {
    for (DisplayDevice displayDevice : this.mDisplayDevices) {
      if ((displayDevice.getDisplayDeviceInfoLocked().flags & 1048576) != 0) {
        return displayDevice;
      }
    }
    return null;
  }

  public boolean isDexDisplayDeviceEnabledLocked() {
    DisplayDevice dexDisplayDeviceLocked = getDexDisplayDeviceLocked();
    return dexDisplayDeviceLocked != null && dexDisplayDeviceLocked.getDexEnabledStateLocked();
  }

  public boolean isExternalDisplayDeviceForDexLocked(DisplayDeviceInfo displayDeviceInfo) {
    if (displayDeviceInfo.type != 2) {
      int i = displayDeviceInfo.flags;
      if ((134217728 & i) == 0 && (i & 67108864) == 0) {
        return false;
      }
    }
    return true;
  }

  public boolean hasExternalDisplayDeviceForDexLocked() {
    Iterator it = this.mDisplayDevices.iterator();
    while (it.hasNext()) {
      DisplayDeviceInfo displayDeviceInfoLocked =
          ((DisplayDevice) it.next()).getDisplayDeviceInfoLocked();
      if (displayDeviceInfoLocked.type == 2) {
        return true;
      }
      int i = displayDeviceInfoLocked.flags;
      if ((134217728 & i) != 0 || (i & 67108864) != 0) {
        return true;
      }
    }
    return false;
  }

  public boolean hasDisplayDeviceForWirelessDexLocked() {
    Iterator it = this.mDisplayDevices.iterator();
    while (it.hasNext()) {
      if ((((DisplayDevice) it.next()).getDisplayDeviceInfoLocked().flags & 67108864) != 0) {
        return true;
      }
    }
    return false;
  }

  public DisplayDevice getPrimaryDisplayDeviceLocked() {
    for (DisplayDevice displayDevice : this.mDisplayDevices) {
      if (displayDevice.isFirstDisplay()) {
        return displayDevice;
      }
    }
    return null;
  }

  public boolean isDisplayDeviceForHiddenSpaceLocked(DisplayDeviceInfo displayDeviceInfo) {
    return (displayDeviceInfo.flags & 268435456) != 0
        || displayDeviceInfo.uniqueId.contains("com.google.android.gms")
        || (displayDeviceInfo.uniqueId.contains(
                KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME)
            && displayDeviceInfo.name.equals("TestVirtualDisplay"));
  }
}
