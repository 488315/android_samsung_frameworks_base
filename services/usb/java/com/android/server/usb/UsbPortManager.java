package com.android.server.usb;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.usb.DisplayPortAltModeInfo;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.sysfwutil.Slog;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.usb.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.FgThread;
import com.android.server.usb.hal.port.RawPortInfo;
import com.android.server.usb.hal.port.UsbPortHal;
import com.android.server.usb.hal.port.UsbPortHalInstance;
import com.samsung.android.os.SemDvfsManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes3.dex */
public class UsbPortManager implements IBinder.DeathRecipient {
  public final Context mContext;
  public int mIsPortContaminatedNotificationId;
  public NotificationManager mNotificationManager;
  public boolean mSystemReady;
  public long mTransactionId;
  public UsbPortHal mUsbPortHal;
  public static final int COMBO_SOURCE_HOST = UsbPort.combineRolesAsBit(1, 1);
  public static final int COMBO_SOURCE_DEVICE = UsbPort.combineRolesAsBit(1, 2);
  public static final int COMBO_SINK_HOST = UsbPort.combineRolesAsBit(2, 1);
  public static final int COMBO_SINK_DEVICE = UsbPort.combineRolesAsBit(2, 2);
  public final Object mLock = new Object();
  public final ArrayMap mPorts = new ArrayMap();
  public final ArrayMap mSimulatedPorts = new ArrayMap();
  public final ArrayMap mConnected = new ArrayMap();
  public final ArrayMap mContaminantStatus = new ArrayMap();
  public final Object mDisplayPortListenerLock = new Object();
  public final ArrayMap mDisplayPortListeners = new ArrayMap();
  public SemDvfsManager usbPortBooster = null;
  public final Handler mHandler =
      new Handler(
          FgThread.get().getLooper()) { // from class: com.android.server.usb.UsbPortManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          int i = message.what;
          if (i != 1) {
            if (i != 2) {
              return;
            }
            UsbPortManager usbPortManager = UsbPortManager.this;
            usbPortManager.mNotificationManager =
                (NotificationManager) usbPortManager.mContext.getSystemService("notification");
            return;
          }
          ArrayList parcelableArrayList =
              message.getData().getParcelableArrayList("port_info", RawPortInfo.class);
          synchronized (UsbPortManager.this.mLock) {
            UsbPortManager.this.updatePortsLocked(null, parcelableArrayList);
          }
        }
      };

  public static int convertContaminantDetectionStatusToProto(int i) {
    if (i == 0) {
      return 1;
    }
    if (i == 1) {
      return 2;
    }
    if (i != 2) {
      return i != 3 ? 0 : 4;
    }
    return 3;
  }

  public final int toHalUsbDataRole(int i) {
    return i == 2 ? 2 : 1;
  }

  public final int toHalUsbMode(int i) {
    return i == 1 ? 1 : 2;
  }

  public final int toHalUsbPowerRole(int i) {
    return i == 2 ? 2 : 1;
  }

  public UsbPortManager(Context context) {
    this.mContext = context;
    initBoost();
    this.mUsbPortHal = UsbPortHalInstance.getInstance(this, null);
    logAndPrint(3, null, "getInstance done");
  }

  public void systemReady() {
    this.mSystemReady = true;
    UsbPortHal usbPortHal = this.mUsbPortHal;
    if (usbPortHal != null) {
      usbPortHal.systemReady();
      try {
        UsbPortHal usbPortHal2 = this.mUsbPortHal;
        long j = this.mTransactionId + 1;
        this.mTransactionId = j;
        usbPortHal2.queryPortStatus(j);
      } catch (Exception e) {
        logAndPrintException(null, "ServiceStart: Failed to query port status", e);
      }
    }
    this.mHandler.sendEmptyMessage(2);
  }

  public final void updateContaminantNotification() {
    PortInfo portInfo;
    int i;
    int i2;
    Resources resources = this.mContext.getResources();
    int i3 = 2;
    for (PortInfo portInfo2 : this.mPorts.values()) {
      int contaminantDetectionStatus = portInfo2.mUsbPortStatus.getContaminantDetectionStatus();
      if (contaminantDetectionStatus == 3 || contaminantDetectionStatus == 1) {
        portInfo = portInfo2;
        i3 = contaminantDetectionStatus;
        break;
      }
      i3 = contaminantDetectionStatus;
    }
    portInfo = null;
    if (i3 != 3 || (i2 = this.mIsPortContaminatedNotificationId) == 52) {
      if (i3 == 3 || (i = this.mIsPortContaminatedNotificationId) != 52) {
        return;
      }
      this.mNotificationManager.cancelAsUser(null, i, UserHandle.ALL);
      this.mIsPortContaminatedNotificationId = 0;
      if (i3 == 2) {
        this.mIsPortContaminatedNotificationId = 53;
        CharSequence text = resources.getText(17043119);
        String str = SystemNotificationChannels.ALERTS;
        CharSequence text2 = resources.getText(17043118);
        this.mNotificationManager.notifyAsUser(
            null,
            this.mIsPortContaminatedNotificationId,
            new Notification.Builder(this.mContext, str)
                .setSmallIcon(R.drawable.ic_sysbar_quicksettings)
                .setTicker(text)
                .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
                .setContentTitle(text)
                .setContentText(text2)
                .setVisibility(1)
                .setStyle(new Notification.BigTextStyle().bigText(text2))
                .build(),
            UserHandle.ALL);
        return;
      }
      return;
    }
    if (i2 == 53) {
      this.mNotificationManager.cancelAsUser(null, i2, UserHandle.ALL);
    }
    this.mIsPortContaminatedNotificationId = 52;
    CharSequence text3 = resources.getText(17043117);
    String str2 = SystemNotificationChannels.ALERTS;
    CharSequence text4 = resources.getText(17043116);
    Intent intent = new Intent();
    intent.addFlags(268435456);
    intent.setComponent(
        ComponentName.unflattenFromString(
            resources.getString(R.string.ext_media_status_unsupported)));
    intent.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo.mUsbPort));
    intent.putExtra("portStatus", (Parcelable) portInfo.mUsbPortStatus);
    this.mNotificationManager.notifyAsUser(
        null,
        this.mIsPortContaminatedNotificationId,
        new Notification.Builder(this.mContext, str2)
            .setOngoing(true)
            .setTicker(text3)
            .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
            .setContentIntent(
                PendingIntent.getActivityAsUser(
                    this.mContext, 0, intent, 67108864, null, UserHandle.CURRENT))
            .setContentTitle(text3)
            .setContentText(text4)
            .setVisibility(1)
            .setSmallIcon(R.drawable.stat_sys_warning)
            .setStyle(new Notification.BigTextStyle().bigText(text4))
            .build(),
        UserHandle.ALL);
  }

  public UsbPort[] getPorts() {
    UsbPort[] usbPortArr;
    synchronized (this.mLock) {
      int size = this.mPorts.size();
      usbPortArr = new UsbPort[size];
      for (int i = 0; i < size; i++) {
        usbPortArr[i] = ((PortInfo) this.mPorts.valueAt(i)).mUsbPort;
      }
    }
    return usbPortArr;
  }

  public UsbPortStatus getPortStatus(String str) {
    UsbPortStatus usbPortStatus;
    synchronized (this.mLock) {
      PortInfo portInfo = (PortInfo) this.mPorts.get(str);
      usbPortStatus = portInfo != null ? portInfo.mUsbPortStatus : null;
    }
    return usbPortStatus;
  }

  public void enableContaminantDetection(
      String str, boolean z, IndentingPrintWriter indentingPrintWriter) {
    PortInfo portInfo = (PortInfo) this.mPorts.get(str);
    if (portInfo == null) {
      if (indentingPrintWriter != null) {
        indentingPrintWriter.println("No such USB port: " + str);
        return;
      }
      return;
    }
    if (portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
      if (!z || portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
        if ((z || portInfo.mUsbPortStatus.getContaminantDetectionStatus() != 1)
            && portInfo.mUsbPortStatus.getContaminantDetectionStatus() != 0) {
          try {
            UsbPortHal usbPortHal = this.mUsbPortHal;
            long j = this.mTransactionId + 1;
            this.mTransactionId = j;
            usbPortHal.enableContaminantPresenceDetection(str, z, j);
          } catch (Exception e) {
            logAndPrintException(indentingPrintWriter, "Failed to set contaminant detection", e);
          }
        }
      }
    }
  }

  public void enableLimitPowerTransfer(
      String str,
      boolean z,
      long j,
      IUsbOperationInternal iUsbOperationInternal,
      IndentingPrintWriter indentingPrintWriter) {
    Objects.requireNonNull(str);
    if (((PortInfo) this.mPorts.get(str)) == null) {
      logAndPrint(
          6, indentingPrintWriter, "enableLimitPowerTransfer: No such port: " + str + " opId:" + j);
      if (iUsbOperationInternal != null) {
        try {
          iUsbOperationInternal.onOperationComplete(3);
          return;
        } catch (RemoteException e) {
          logAndPrintException(
              indentingPrintWriter,
              "enableLimitPowerTransfer: Failed to call OperationComplete. opId:" + j,
              e);
          return;
        }
      }
      return;
    }
    try {
      this.mUsbPortHal.enableLimitPowerTransfer(str, z, j, iUsbOperationInternal);
    } catch (Exception e2) {
      try {
        logAndPrintException(
            indentingPrintWriter,
            "enableLimitPowerTransfer: Failed to limit power transfer. opId:" + j,
            e2);
        if (iUsbOperationInternal != null) {
          iUsbOperationInternal.onOperationComplete(1);
        }
      } catch (RemoteException e3) {
        logAndPrintException(
            indentingPrintWriter,
            "enableLimitPowerTransfer:Failed to call onOperationComplete. opId:" + j,
            e3);
      }
    }
  }

  public void enableUsbDataWhileDocked(
      String str,
      long j,
      IUsbOperationInternal iUsbOperationInternal,
      IndentingPrintWriter indentingPrintWriter) {
    Objects.requireNonNull(str);
    if (((PortInfo) this.mPorts.get(str)) == null) {
      logAndPrint(
          6, indentingPrintWriter, "enableUsbDataWhileDocked: No such port: " + str + " opId:" + j);
      if (iUsbOperationInternal != null) {
        try {
          iUsbOperationInternal.onOperationComplete(3);
          return;
        } catch (RemoteException e) {
          logAndPrintException(
              indentingPrintWriter,
              "enableUsbDataWhileDocked: Failed to call OperationComplete. opId:" + j,
              e);
          return;
        }
      }
      return;
    }
    try {
      this.mUsbPortHal.enableUsbDataWhileDocked(str, j, iUsbOperationInternal);
    } catch (Exception e2) {
      try {
        logAndPrintException(
            indentingPrintWriter,
            "enableUsbDataWhileDocked: Failed to limit power transfer. opId:" + j,
            e2);
        if (iUsbOperationInternal != null) {
          iUsbOperationInternal.onOperationComplete(1);
        }
      } catch (RemoteException e3) {
        logAndPrintException(
            indentingPrintWriter,
            "enableUsbDataWhileDocked:Failed to call onOperationComplete. opId:" + j,
            e3);
      }
    }
  }

  public boolean enableUsbData(
      String str,
      boolean z,
      int i,
      IUsbOperationInternal iUsbOperationInternal,
      IndentingPrintWriter indentingPrintWriter) {
    Objects.requireNonNull(iUsbOperationInternal);
    Objects.requireNonNull(str);
    if (((PortInfo) this.mPorts.get(str)) == null) {
      logAndPrint(6, indentingPrintWriter, "enableUsbData: No such port: " + str + " opId:" + i);
      try {
        iUsbOperationInternal.onOperationComplete(3);
      } catch (RemoteException e) {
        logAndPrintException(
            indentingPrintWriter, "enableUsbData: Failed to call OperationComplete. opId:" + i, e);
      }
      return false;
    }
    try {
      return this.mUsbPortHal.enableUsbData(str, z, i, iUsbOperationInternal);
    } catch (Exception e2) {
      try {
        logAndPrintException(
            indentingPrintWriter, "enableUsbData: Failed to invoke enableUsbData. opId:" + i, e2);
        iUsbOperationInternal.onOperationComplete(1);
      } catch (RemoteException e3) {
        logAndPrintException(
            indentingPrintWriter,
            "enableUsbData: Failed to call onOperationComplete. opId:" + i,
            e3);
      }
      return false;
    }
  }

  public int getUsbHalVersion() {
    UsbPortHal usbPortHal = this.mUsbPortHal;
    if (usbPortHal != null) {
      try {
        return usbPortHal.getUsbHalVersion();
      } catch (RemoteException unused) {
      }
    }
    return -2;
  }

  public void resetUsbPort(
      String str,
      int i,
      IUsbOperationInternal iUsbOperationInternal,
      IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      Objects.requireNonNull(iUsbOperationInternal);
      Objects.requireNonNull(str);
      if (((PortInfo) this.mPorts.get(str)) == null) {
        logAndPrint(6, indentingPrintWriter, "resetUsbPort: No such port: " + str + " opId:" + i);
        try {
          iUsbOperationInternal.onOperationComplete(3);
        } catch (RemoteException e) {
          logAndPrintException(
              indentingPrintWriter, "resetUsbPort: Failed to call OperationComplete. opId:" + i, e);
        }
      }
      try {
        this.mUsbPortHal.resetUsbPort(str, i, iUsbOperationInternal);
      } catch (Exception e2) {
        try {
          logAndPrintException(
              indentingPrintWriter, "reseetUsbPort: Failed to resetUsbPort. opId:" + i, e2);
          iUsbOperationInternal.onOperationComplete(1);
        } catch (RemoteException e3) {
          logAndPrintException(
              indentingPrintWriter,
              "resetUsbPort: Failed to call onOperationComplete. opId:" + i,
              e3);
        }
      }
    }
  }

  public void setPortRoles(
      String str, int i, int i2, IndentingPrintWriter indentingPrintWriter, Runnable runnable) {
    int i3;
    synchronized (this.mLock) {
      PortInfo portInfo = (PortInfo) this.mPorts.get(str);
      if (portInfo == null) {
        if (indentingPrintWriter != null) {
          indentingPrintWriter.println("No such USB port: " + str);
        }
        return;
      }
      if (!portInfo.mUsbPortStatus.isRoleCombinationSupported(i, i2)) {
        logAndPrint(
            6,
            indentingPrintWriter,
            "Attempted to set USB port into unsupported role combination: portId="
                + str
                + ", newPowerRole="
                + UsbPort.powerRoleToString(i)
                + ", newDataRole="
                + UsbPort.dataRoleToString(i2));
        return;
      }
      int currentDataRole = portInfo.mUsbPortStatus.getCurrentDataRole();
      int currentPowerRole = portInfo.mUsbPortStatus.getCurrentPowerRole();
      if (currentDataRole == i2 && currentPowerRole == i) {
        if (indentingPrintWriter != null) {
          indentingPrintWriter.println("No change.");
        }
        return;
      }
      boolean z = portInfo.mCanChangeMode;
      boolean z2 = portInfo.mCanChangePowerRole;
      boolean z3 = portInfo.mCanChangeDataRole;
      int currentMode = portInfo.mUsbPortStatus.getCurrentMode();
      if ((z2 || currentPowerRole == i) && (z3 || currentDataRole == i2)) {
        i3 = currentMode;
      } else {
        i3 = 2;
        if (!z || i != 1 || i2 != 1) {
          if (!z || i != 2 || i2 != 2) {
            logAndPrint(
                6,
                indentingPrintWriter,
                "Found mismatch in supported USB role combinations while attempting to change role:"
                    + " "
                    + portInfo
                    + ", newPowerRole="
                    + UsbPort.powerRoleToString(i)
                    + ", newDataRole="
                    + UsbPort.dataRoleToString(i2));
            return;
          }
          i3 = 1;
        }
      }
      logAndPrint(
          4,
          indentingPrintWriter,
          "Setting USB port mode and role: portId="
              + str
              + ", currentMode="
              + UsbPort.modeToString(currentMode)
              + ", currentPowerRole="
              + UsbPort.powerRoleToString(currentPowerRole)
              + ", currentDataRole="
              + UsbPort.dataRoleToString(currentDataRole)
              + ", newMode="
              + UsbPort.modeToString(i3)
              + ", newPowerRole="
              + UsbPort.powerRoleToString(i)
              + ", newDataRole="
              + UsbPort.dataRoleToString(i2));
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo != null) {
        rawPortInfo.currentMode = i3;
        rawPortInfo.currentPowerRole = i;
        rawPortInfo.currentDataRole = i2;
        updatePortsLocked(indentingPrintWriter, null);
      } else {
        UsbPortHal usbPortHal = this.mUsbPortHal;
        if (usbPortHal != null) {
          if (currentMode != i3) {
            logAndPrint(
                6,
                indentingPrintWriter,
                "Trying to set the USB port mode: portId="
                    + str
                    + ", newMode="
                    + UsbPort.modeToString(i3));
            if (runnable != null) {
              try {
                runnable.run();
              } catch (Exception e) {
                logAndPrintException(
                    indentingPrintWriter,
                    "Failed to set the USB port mode: portId="
                        + str
                        + ", newMode="
                        + UsbPort.modeToString(i3),
                    e);
              }
            }
            UsbPortHal usbPortHal2 = this.mUsbPortHal;
            int halUsbMode = toHalUsbMode(i3);
            long j = this.mTransactionId + 1;
            this.mTransactionId = j;
            usbPortHal2.switchMode(str, halUsbMode, j);
          } else {
            if (currentPowerRole != i) {
              try {
                int halUsbPowerRole = toHalUsbPowerRole(i);
                long j2 = this.mTransactionId + 1;
                this.mTransactionId = j2;
                usbPortHal.switchPowerRole(str, halUsbPowerRole, j2);
              } catch (Exception e2) {
                logAndPrintException(
                    indentingPrintWriter,
                    "Failed to set the USB port power role: portId="
                        + str
                        + ", newPowerRole="
                        + UsbPort.powerRoleToString(i),
                    e2);
                return;
              }
            }
            if (currentDataRole != i2) {
              if (runnable != null) {
                try {
                  runnable.run();
                } catch (Exception e3) {
                  logAndPrintException(
                      indentingPrintWriter,
                      "Failed to set the USB port data role: portId="
                          + str
                          + ", newDataRole="
                          + UsbPort.dataRoleToString(i2),
                      e3);
                }
              }
              UsbPortHal usbPortHal3 = this.mUsbPortHal;
              int halUsbDataRole = toHalUsbDataRole(i2);
              long j3 = this.mTransactionId + 1;
              this.mTransactionId = j3;
              usbPortHal3.switchDataRole(str, halUsbDataRole, j3);
            }
          }
        }
      }
    }
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    Slog.wtf("UsbPortManager", "binderDied() called unexpectedly");
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied(IBinder iBinder) {
    synchronized (this.mDisplayPortListenerLock) {
      this.mDisplayPortListeners.remove(iBinder);
      Slog.d("UsbPortManager", "DisplayPortEventDispatcherListener died at " + iBinder);
    }
  }

  public boolean registerForDisplayPortEvents(
      IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
    synchronized (this.mDisplayPortListenerLock) {
      if (this.mDisplayPortListeners.containsKey(iDisplayPortAltModeInfoListener.asBinder())) {
        return false;
      }
      try {
        iDisplayPortAltModeInfoListener.asBinder().linkToDeath(this, 0);
        this.mDisplayPortListeners.put(
            iDisplayPortAltModeInfoListener.asBinder(), iDisplayPortAltModeInfoListener);
        return true;
      } catch (RemoteException e) {
        logAndPrintException(null, "Caught RemoteException in registerForDisplayPortEvents: ", e);
        return false;
      }
    }
  }

  public void unregisterForDisplayPortEvents(
      IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
    synchronized (this.mDisplayPortListenerLock) {
      if (this.mDisplayPortListeners.remove(iDisplayPortAltModeInfoListener.asBinder()) != null) {
        iDisplayPortAltModeInfoListener.asBinder().unlinkToDeath(this, 0);
      }
    }
  }

  public void updatePorts(ArrayList arrayList) {
    Message obtainMessage = this.mHandler.obtainMessage();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList("port_info", arrayList);
    obtainMessage.what = 1;
    obtainMessage.setData(bundle);
    this.mHandler.sendMessage(obtainMessage);
  }

  public void addSimulatedPort(
      String str, int i, boolean z, boolean z2, IndentingPrintWriter indentingPrintWriter) {
    DisplayPortAltModeInfo displayPortAltModeInfo = z2 ? new DisplayPortAltModeInfo() : null;
    synchronized (this.mLock) {
      try {
        try {
          if (this.mSimulatedPorts.containsKey(str)) {
            indentingPrintWriter.println(
                "Port with same name already exists.  Please remove it first.");
            return;
          }
          indentingPrintWriter.println(
              "Adding simulated port: portId="
                  + str
                  + ", supportedModes="
                  + UsbPort.modeToString(i));
          this.mSimulatedPorts.put(
              str,
              new RawPortInfo(
                  str,
                  i,
                  0,
                  0,
                  false,
                  0,
                  false,
                  0,
                  false,
                  false,
                  0,
                  false,
                  0,
                  0,
                  false,
                  0,
                  z,
                  new int[0],
                  0,
                  z2 ? 1 : 0,
                  displayPortAltModeInfo));
          updatePortsLocked(indentingPrintWriter, null);
        } catch (Throwable th) {
          th = th;
          throw th;
        }
      } catch (Throwable th2) {
        th = th2;
        throw th;
      }
    }
  }

  public void connectSimulatedPort(
      String str,
      int i,
      boolean z,
      int i2,
      boolean z2,
      int i3,
      boolean z3,
      IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo == null) {
        indentingPrintWriter.println("Cannot connect simulated port which does not exist.");
        return;
      }
      if (i != 0 && i2 != 0 && i3 != 0) {
        if ((rawPortInfo.supportedModes & i) == 0) {
          indentingPrintWriter.println(
              "Simulated port does not support mode: " + UsbPort.modeToString(i));
          return;
        }
        indentingPrintWriter.println(
            "Connecting simulated port: portId="
                + str
                + ", mode="
                + UsbPort.modeToString(i)
                + ", canChangeMode="
                + z
                + ", powerRole="
                + UsbPort.powerRoleToString(i2)
                + ", canChangePowerRole="
                + z2
                + ", dataRole="
                + UsbPort.dataRoleToString(i3)
                + ", canChangeDataRole="
                + z3);
        rawPortInfo.currentMode = i;
        rawPortInfo.canChangeMode = z;
        rawPortInfo.currentPowerRole = i2;
        rawPortInfo.canChangePowerRole = z2;
        rawPortInfo.currentDataRole = i3;
        rawPortInfo.canChangeDataRole = z3;
        updatePortsLocked(indentingPrintWriter, null);
        return;
      }
      indentingPrintWriter.println(
          "Cannot connect simulated port in null mode, power role, or data role.");
    }
  }

  public void simulateContaminantStatus(
      String str, boolean z, IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo == null) {
        indentingPrintWriter.println("Simulated port not found.");
        return;
      }
      indentingPrintWriter.println("Simulating wet port: portId=" + str + ", wet=" + z);
      rawPortInfo.contaminantDetectionStatus = z ? 3 : 2;
      updatePortsLocked(indentingPrintWriter, null);
    }
  }

  public void simulateComplianceWarnings(
      String str, String str2, IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo == null) {
        indentingPrintWriter.println("Simulated port not found");
        return;
      }
      IntArray intArray = new IntArray();
      for (String str3 : str2.split("[, ]")) {
        if (str3.length() > 0) {
          intArray.add(Integer.parseInt(str3));
        }
      }
      indentingPrintWriter.println(
          "Simulating Compliance Warnings: portId=" + str + " Warnings=" + str2);
      rawPortInfo.complianceWarnings = intArray.toArray();
      updatePortsLocked(indentingPrintWriter, null);
    }
  }

  public void simulateDisplayPortAltModeInfo(
      String str,
      int i,
      int i2,
      int i3,
      boolean z,
      int i4,
      IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo == null) {
        indentingPrintWriter.println("Simulated port not found");
        return;
      }
      DisplayPortAltModeInfo displayPortAltModeInfo = new DisplayPortAltModeInfo(i, i2, i3, z, i4);
      rawPortInfo.displayPortAltModeInfo = displayPortAltModeInfo;
      indentingPrintWriter.println("Simulating DisplayPort Info: " + displayPortAltModeInfo);
      updatePortsLocked(indentingPrintWriter, null);
    }
  }

  public void disconnectSimulatedPort(String str, IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo == null) {
        indentingPrintWriter.println("Cannot disconnect simulated port which does not exist.");
        return;
      }
      indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
      rawPortInfo.currentMode = 0;
      rawPortInfo.canChangeMode = false;
      rawPortInfo.currentPowerRole = 0;
      rawPortInfo.canChangePowerRole = false;
      rawPortInfo.currentDataRole = 0;
      rawPortInfo.canChangeDataRole = false;
      updatePortsLocked(indentingPrintWriter, null);
    }
  }

  public void removeSimulatedPort(String str, IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      int indexOfKey = this.mSimulatedPorts.indexOfKey(str);
      if (indexOfKey < 0) {
        indentingPrintWriter.println("Cannot remove simulated port which does not exist.");
        return;
      }
      indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
      this.mSimulatedPorts.removeAt(indexOfKey);
      updatePortsLocked(indentingPrintWriter, null);
    }
  }

  public void resetSimulation(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println("Removing all simulated ports and ending simulation.");
      if (!this.mSimulatedPorts.isEmpty()) {
        this.mSimulatedPorts.clear();
        updatePortsLocked(indentingPrintWriter, null);
      }
    }
  }

  public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
    long start = dualDumpOutputStream.start(str, j);
    synchronized (this.mLock) {
      dualDumpOutputStream.write(
          "is_simulation_active", 1133871366145L, !this.mSimulatedPorts.isEmpty());
      Iterator it = this.mPorts.values().iterator();
      while (it.hasNext()) {
        ((PortInfo) it.next()).dump(dualDumpOutputStream, "usb_ports", 2246267895810L);
      }
      dualDumpOutputStream.write("usb_hal_version", 1159641169924L, getUsbHalVersion());
    }
    dualDumpOutputStream.end(start);
  }

  public final void updatePortsLocked(
      IndentingPrintWriter indentingPrintWriter, ArrayList arrayList) {
    String str;
    IndentingPrintWriter indentingPrintWriter2;
    UsbPortManager usbPortManager = this;
    String str2 = "UsbPortManager";
    Slog.d("UsbPortManager", "updatePortsLocked()++");
    int size = usbPortManager.mPorts.size();
    while (true) {
      int i = size - 1;
      if (size <= 0) {
        break;
      }
      ((PortInfo) usbPortManager.mPorts.valueAt(i)).mDisposition = 3;
      size = i;
    }
    if (!usbPortManager.mSimulatedPorts.isEmpty()) {
      int size2 = usbPortManager.mSimulatedPorts.size();
      int i2 = 0;
      while (i2 < size2) {
        RawPortInfo rawPortInfo = (RawPortInfo) usbPortManager.mSimulatedPorts.valueAt(i2);
        usbPortManager = this;
        usbPortManager.addOrUpdatePortLocked(
            rawPortInfo.portId,
            rawPortInfo.supportedModes,
            rawPortInfo.supportedContaminantProtectionModes,
            rawPortInfo.currentMode,
            rawPortInfo.canChangeMode,
            rawPortInfo.currentPowerRole,
            rawPortInfo.canChangePowerRole,
            rawPortInfo.currentDataRole,
            rawPortInfo.canChangeDataRole,
            rawPortInfo.supportsEnableContaminantPresenceProtection,
            rawPortInfo.contaminantProtectionStatus,
            rawPortInfo.supportsEnableContaminantPresenceDetection,
            rawPortInfo.contaminantDetectionStatus,
            rawPortInfo.usbDataStatus,
            rawPortInfo.powerTransferLimited,
            rawPortInfo.powerBrickConnectionStatus,
            rawPortInfo.supportsComplianceWarnings,
            rawPortInfo.complianceWarnings,
            rawPortInfo.plugState,
            rawPortInfo.supportedAltModes,
            rawPortInfo.displayPortAltModeInfo,
            indentingPrintWriter);
        i2++;
        size2 = size2;
        str2 = str2;
      }
      str = str2;
    } else {
      str = "UsbPortManager";
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        RawPortInfo rawPortInfo2 = (RawPortInfo) it.next();
        addOrUpdatePortLocked(
            rawPortInfo2.portId,
            rawPortInfo2.supportedModes,
            rawPortInfo2.supportedContaminantProtectionModes,
            rawPortInfo2.currentMode,
            rawPortInfo2.canChangeMode,
            rawPortInfo2.currentPowerRole,
            rawPortInfo2.canChangePowerRole,
            rawPortInfo2.currentDataRole,
            rawPortInfo2.canChangeDataRole,
            rawPortInfo2.supportsEnableContaminantPresenceProtection,
            rawPortInfo2.contaminantProtectionStatus,
            rawPortInfo2.supportsEnableContaminantPresenceDetection,
            rawPortInfo2.contaminantDetectionStatus,
            rawPortInfo2.usbDataStatus,
            rawPortInfo2.powerTransferLimited,
            rawPortInfo2.powerBrickConnectionStatus,
            rawPortInfo2.supportsComplianceWarnings,
            rawPortInfo2.complianceWarnings,
            rawPortInfo2.plugState,
            rawPortInfo2.supportedAltModes,
            rawPortInfo2.displayPortAltModeInfo,
            indentingPrintWriter);
      }
    }
    String str3 = str;
    Slog.d(str3, "mPorts size=" + this.mPorts.size());
    int size3 = this.mPorts.size();
    while (true) {
      int i3 = size3 - 1;
      if (size3 > 0) {
        PortInfo portInfo = (PortInfo) this.mPorts.valueAt(i3);
        int i4 = portInfo.mDisposition;
        if (i4 == 0) {
          indentingPrintWriter2 = indentingPrintWriter;
          handlePortAddedLocked(portInfo, indentingPrintWriter2);
          portInfo.mDisposition = 2;
          Slog.d(str3, "mPorts(" + i3 + ") DISPOSITION_ADDED -> DISPOSITION_READY");
        } else if (i4 == 1) {
          indentingPrintWriter2 = indentingPrintWriter;
          handlePortChangedLocked(portInfo, indentingPrintWriter2);
          portInfo.mDisposition = 2;
          Slog.d(str3, "mPorts(" + i3 + ") DISPOSITION_CHANGED -> DISPOSITION_READY");
        } else if (i4 == 3) {
          Slog.d(str3, "mPorts(" + i3 + ") DISPOSITION_REMOVED size=" + this.mPorts.size());
          this.mPorts.removeAt(i3);
          portInfo.mUsbPortStatus = null;
          indentingPrintWriter2 = indentingPrintWriter;
          handlePortRemovedLocked(portInfo, indentingPrintWriter2);
        } else {
          Slog.d(str3, "mPorts(" + i3 + ") default: mDisposition=" + portInfo.mDisposition);
          indentingPrintWriter2 = indentingPrintWriter;
        }
        if (portInfo.mComplianceWarningChange == 1) {
          handlePortComplianceWarningLocked(portInfo, indentingPrintWriter2);
        }
        if (portInfo.mDisplayPortAltModeChange == 1) {
          handleDpAltModeLocked(portInfo, indentingPrintWriter2);
        }
        size3 = i3;
      } else {
        Slog.d(str3, "updatePortsLocked()--");
        return;
      }
    }
  }

  public final void addOrUpdatePortLocked(
      String str,
      int i,
      int i2,
      int i3,
      boolean z,
      int i4,
      boolean z2,
      int i5,
      boolean z3,
      boolean z4,
      int i6,
      boolean z5,
      int i7,
      int i8,
      boolean z6,
      int i9,
      boolean z7,
      int[] iArr,
      int i10,
      int i11,
      DisplayPortAltModeInfo displayPortAltModeInfo,
      IndentingPrintWriter indentingPrintWriter) {
    int i12;
    boolean z8;
    String str2;
    int i13;
    int i14;
    int combineRolesAsBit;
    Slog.d(
        "UsbPortManager",
        "addOrUpdatePortLocked()++ : portId="
            + str
            + " supportedModes="
            + UsbPort.modeToString(i)
            + " currentMode="
            + UsbPort.modeToString(i3)
            + " canChangeMode="
            + z
            + " currentPowerRole="
            + UsbPort.powerRoleToString(i4)
            + " canChangePowerRole="
            + z2
            + " currentDataRole="
            + UsbPort.dataRoleToString(i5)
            + " canChangeDataRole="
            + z3);
    if ((i & 3) == 3) {
      i12 = i3;
      z8 = z;
    } else if (i3 == 0 || i3 == i) {
      i12 = i3;
      z8 = false;
    } else {
      logAndPrint(
          5,
          indentingPrintWriter,
          "Ignoring inconsistent current mode from USB port driver: supportedModes="
              + UsbPort.modeToString(i)
              + ", currentMode="
              + UsbPort.modeToString(i3));
      i12 = 0;
      z8 = false;
    }
    int combineRolesAsBit2 = UsbPort.combineRolesAsBit(i4, i5);
    if (i12 != 0 && i4 != 0 && i5 != 0) {
      if (z2 && z3) {
        i13 = COMBO_SOURCE_HOST | COMBO_SOURCE_DEVICE | COMBO_SINK_HOST;
        i14 = COMBO_SINK_DEVICE;
      } else {
        if (z2) {
          combineRolesAsBit2 |= UsbPort.combineRolesAsBit(1, i5);
          combineRolesAsBit = UsbPort.combineRolesAsBit(2, i5);
        } else if (z3) {
          combineRolesAsBit2 |= UsbPort.combineRolesAsBit(i4, 1);
          combineRolesAsBit = UsbPort.combineRolesAsBit(i4, 2);
        } else if (z8) {
          i13 = COMBO_SOURCE_HOST;
          i14 = COMBO_SINK_DEVICE;
        }
        combineRolesAsBit2 |= combineRolesAsBit;
      }
      combineRolesAsBit = i13 | i14;
      combineRolesAsBit2 |= combineRolesAsBit;
    }
    int i15 = combineRolesAsBit2;
    Slog.d("UsbPortManager", "supportedRoleCombinations=" + i15);
    PortInfo portInfo = (PortInfo) this.mPorts.get(str);
    if (portInfo == null) {
      PortInfo portInfo2 =
          new PortInfo(
              (UsbManager) this.mContext.getSystemService(UsbManager.class),
              str,
              i,
              i2,
              z4,
              z5,
              z7,
              i11);
      portInfo2.setStatus(
          i12, z8, i4, z2, i5, z3, i15, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo);
      this.mPorts.put(str, portInfo2);
      Slog.d("UsbPortManager", "mPorts(" + str + ") put size=" + this.mPorts.size());
      str2 = "UsbPortManager";
    } else {
      if (i != portInfo.mUsbPort.getSupportedModes()) {
        logAndPrint(
            5,
            indentingPrintWriter,
            "Ignoring inconsistent list of supported modes from USB port driver (should be"
                + " immutable): previous="
                + UsbPort.modeToString(portInfo.mUsbPort.getSupportedModes())
                + ", current="
                + UsbPort.modeToString(i));
      }
      Slog.d("UsbPortManager", "supportedModes=" + UsbPort.modeToString(i));
      if (z4 != portInfo.mUsbPort.supportsEnableContaminantPresenceProtection()) {
        logAndPrint(
            5,
            indentingPrintWriter,
            "Ignoring inconsistent supportsEnableContaminantPresenceProtectionUSB port driver"
                + " (should be immutable): previous="
                + portInfo.mUsbPort.supportsEnableContaminantPresenceProtection()
                + ", current="
                + z4);
      }
      if (z5 != portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
        logAndPrint(
            5,
            indentingPrintWriter,
            "Ignoring inconsistent supportsEnableContaminantPresenceDetection USB port driver"
                + " (should be immutable): previous="
                + portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()
                + ", current="
                + z5);
      }
      if (portInfo.setStatus(
          i12, z8, i4, z2, i5, z3, i15, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo)) {
        portInfo.mDisposition = 1;
        str2 = "UsbPortManager";
        Slog.d(str2, "addOrUpdatePortLocked() mPorts DISPOSITION_REMOVED -> DISPOSITION_CHANGED");
      } else {
        str2 = "UsbPortManager";
        portInfo.mDisposition = 2;
      }
    }
    Slog.d(str2, "addOrUpdatePortLocked()--");
  }

  public final void handlePortLocked(PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    sendPortChangedBroadcastLocked(portInfo);
    logToStatsd(portInfo, indentingPrintWriter);
    updateContaminantNotification();
  }

  public final void handlePortAddedLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    logAndPrint(4, indentingPrintWriter, "USB port added: " + portInfo);
    handlePortLocked(portInfo, indentingPrintWriter);
  }

  public final void handlePortChangedLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    logAndPrint(4, indentingPrintWriter, "USB port changed: " + portInfo);
    enableContaminantDetectionIfNeeded(portInfo, indentingPrintWriter);
    disableLimitPowerTransferIfNeeded(portInfo, indentingPrintWriter);
    handlePortLocked(portInfo, indentingPrintWriter);
  }

  public final void handlePortComplianceWarningLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    logAndPrint(4, indentingPrintWriter, "USB port compliance warning changed: " + portInfo);
    logToStatsdComplianceWarnings(portInfo);
    sendComplianceWarningBroadcastLocked(portInfo);
  }

  public final void handleDpAltModeLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    logAndPrint(
        4, indentingPrintWriter, "USB port DisplayPort Alt Mode Status Changed: " + portInfo);
    sendDpAltModeCallbackLocked(portInfo, indentingPrintWriter);
  }

  public final void handlePortRemovedLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    logAndPrint(4, indentingPrintWriter, "USB port removed: " + portInfo);
    handlePortLocked(portInfo, indentingPrintWriter);
  }

  public static int[] toStatsLogConstant(int[] iArr) {
    IntArray intArray = new IntArray();
    for (int i : iArr) {
      if (i == 1) {
        intArray.add(4);
      } else if (i == 2) {
        intArray.add(1);
      } else if (i == 3) {
        intArray.add(2);
      } else if (i == 4) {
        intArray.add(3);
      }
    }
    return intArray.toArray();
  }

  public final void sendPortChangedBroadcastLocked(PortInfo portInfo) {
    final Intent intent = new Intent("android.hardware.usb.action.USB_PORT_CHANGED");
    intent.addFlags(285212672);
    intent.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo.mUsbPort));
    intent.putExtra("portStatus", (Parcelable) portInfo.mUsbPortStatus);
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.usb.UsbPortManager$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            UsbPortManager.this.lambda$sendPortChangedBroadcastLocked$0(intent);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendPortChangedBroadcastLocked$0(Intent intent) {
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.MANAGE_USB");
  }

  public final void sendComplianceWarningBroadcastLocked(PortInfo portInfo) {
    if (portInfo.mComplianceWarningChange == 0) {
      return;
    }
    final Intent intent = new Intent("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
    intent.addFlags(285212672);
    intent.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo.mUsbPort));
    intent.putExtra("portStatus", (Parcelable) portInfo.mUsbPortStatus);
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.usb.UsbPortManager$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            UsbPortManager.this.lambda$sendComplianceWarningBroadcastLocked$1(intent);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendComplianceWarningBroadcastLocked$1(Intent intent) {
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.MANAGE_USB");
  }

  public final void sendDpAltModeCallbackLocked(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    String id = portInfo.mUsbPort.getId();
    synchronized (this.mDisplayPortListenerLock) {
      Iterator it = this.mDisplayPortListeners.values().iterator();
      while (it.hasNext()) {
        try {
          ((IDisplayPortAltModeInfoListener) it.next())
              .onDisplayPortAltModeInfoChanged(
                  id, portInfo.mUsbPortStatus.getDisplayPortAltModeInfo());
        } catch (RemoteException e) {
          logAndPrintException(
              indentingPrintWriter, "Caught RemoteException at sendDpAltModeCallbackLocked", e);
        }
      }
    }
  }

  public final void enableContaminantDetectionIfNeeded(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    if (this.mConnected.containsKey(portInfo.mUsbPort.getId())
        && ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue()
        && !portInfo.mUsbPortStatus.isConnected()
        && portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
      enableContaminantDetection(portInfo.mUsbPort.getId(), true, indentingPrintWriter);
    }
  }

  public final void disableLimitPowerTransferIfNeeded(
      PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    if (this.mConnected.containsKey(portInfo.mUsbPort.getId())
        && ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue()
        && !portInfo.mUsbPortStatus.isConnected()
        && portInfo.mUsbPortStatus.isPowerTransferLimited()) {
      String id = portInfo.mUsbPort.getId();
      long j = this.mTransactionId + 1;
      this.mTransactionId = j;
      enableLimitPowerTransfer(id, false, j, null, indentingPrintWriter);
    }
  }

  public final void logToStatsd(PortInfo portInfo, IndentingPrintWriter indentingPrintWriter) {
    if (portInfo.mUsbPortStatus == null) {
      if (this.mConnected.containsKey(portInfo.mUsbPort.getId())) {
        if (((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue()) {
          FrameworkStatsLog.write(
              70, 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
        }
        this.mConnected.remove(portInfo.mUsbPort.getId());
      }
      if (this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId())) {
        if (((Integer) this.mContaminantStatus.get(portInfo.mUsbPort.getId())).intValue() == 3) {
          FrameworkStatsLog.write(
              146, portInfo.mUsbPort.getId(), convertContaminantDetectionStatusToProto(2));
        }
        this.mContaminantStatus.remove(portInfo.mUsbPort.getId());
        return;
      }
      return;
    }
    if (!this.mConnected.containsKey(portInfo.mUsbPort.getId())
        || ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue()
            != portInfo.mUsbPortStatus.isConnected()) {
      this.mConnected.put(
          portInfo.mUsbPort.getId(), Boolean.valueOf(portInfo.mUsbPortStatus.isConnected()));
      boolean isConnected = portInfo.mUsbPortStatus.isConnected();
      FrameworkStatsLog.write(
          70, isConnected ? 1 : 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
    }
    if (this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId())
        && ((Integer) this.mContaminantStatus.get(portInfo.mUsbPort.getId())).intValue()
            == portInfo.mUsbPortStatus.getContaminantDetectionStatus()) {
      return;
    }
    this.mContaminantStatus.put(
        portInfo.mUsbPort.getId(),
        Integer.valueOf(portInfo.mUsbPortStatus.getContaminantDetectionStatus()));
    FrameworkStatsLog.write(
        146,
        portInfo.mUsbPort.getId(),
        convertContaminantDetectionStatusToProto(
            portInfo.mUsbPortStatus.getContaminantDetectionStatus()));
  }

  public final void logToStatsdComplianceWarnings(PortInfo portInfo) {
    UsbPortStatus usbPortStatus = portInfo.mUsbPortStatus;
    if (usbPortStatus == null || usbPortStatus.getComplianceWarnings().length == 0) {
      return;
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.USB_COMPLIANCE_WARNINGS_REPORTED,
        portInfo.mUsbPort.getId(),
        toStatsLogConstant(portInfo.mUsbPortStatus.getComplianceWarnings()));
  }

  public static void logAndPrint(int i, IndentingPrintWriter indentingPrintWriter, String str) {
    Slog.println(i, "UsbPortManager", str);
    if (indentingPrintWriter != null) {
      indentingPrintWriter.println(str);
    }
  }

  public static void logAndPrintException(
      IndentingPrintWriter indentingPrintWriter, String str, Exception exc) {
    Slog.e("UsbPortManager", str, exc);
    if (indentingPrintWriter != null) {
      indentingPrintWriter.println(str + exc);
    }
  }

  public final class PortInfo {
    public boolean mCanChangeDataRole;
    public boolean mCanChangeMode;
    public boolean mCanChangePowerRole;
    public long mConnectedAtMillis;
    public int mDisposition;
    public long mLastConnectDurationMillis;
    public final UsbPort mUsbPort;
    public UsbPortStatus mUsbPortStatus;
    public int mComplianceWarningChange = 0;
    public int mDisplayPortAltModeChange = 0;

    public PortInfo(
        UsbManager usbManager,
        String str,
        int i,
        int i2,
        boolean z,
        boolean z2,
        boolean z3,
        int i3) {
      this.mUsbPort = new UsbPort(usbManager, str, i, i2, z, z2, z3, i3);
    }

    public boolean complianceWarningsChanged(int[] iArr) {
      if (Arrays.equals(iArr, this.mUsbPortStatus.getComplianceWarnings())) {
        this.mComplianceWarningChange = 0;
        return false;
      }
      this.mComplianceWarningChange = 1;
      return true;
    }

    public boolean displayPortAltModeChanged(DisplayPortAltModeInfo displayPortAltModeInfo) {
      DisplayPortAltModeInfo displayPortAltModeInfo2 =
          this.mUsbPortStatus.getDisplayPortAltModeInfo();
      this.mDisplayPortAltModeChange = 0;
      if (displayPortAltModeInfo == null && displayPortAltModeInfo2 != null) {
        this.mDisplayPortAltModeChange = 1;
        return true;
      }
      if (displayPortAltModeInfo2 == null) {
        if (displayPortAltModeInfo == null) {
          return false;
        }
        this.mDisplayPortAltModeChange = 1;
        return true;
      }
      if (displayPortAltModeInfo2.equals(displayPortAltModeInfo)) {
        return false;
      }
      this.mDisplayPortAltModeChange = 1;
      return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean setStatus(
        int i,
        boolean z,
        int i2,
        boolean z2,
        int i3,
        boolean z3,
        int i4,
        int i5,
        int i6,
        int i7,
        boolean z4,
        int i8,
        int[] iArr,
        int i9,
        DisplayPortAltModeInfo displayPortAltModeInfo) {
      boolean z5;
      boolean z6;
      boolean z7;
      boolean z8;
      if (this.mUsbPortStatus != null) {
        z6 = complianceWarningsChanged(iArr);
        z7 = displayPortAltModeChanged(displayPortAltModeInfo);
        z5 = z;
      } else {
        z5 = z;
        z6 = false;
        z7 = false;
      }
      this.mCanChangeMode = z5;
      this.mCanChangePowerRole = z2;
      this.mCanChangeDataRole = z3;
      UsbPortStatus usbPortStatus = this.mUsbPortStatus;
      if (usbPortStatus != null && usbPortStatus.getCurrentMode() == i) {
        if (this.mUsbPortStatus.getCurrentPowerRole() == i2) {
          if (this.mUsbPortStatus.getCurrentDataRole() == i3) {
            if (this.mUsbPortStatus.getSupportedRoleCombinations() == i4) {
              if (this.mUsbPortStatus.getContaminantProtectionStatus() == i5) {
                if (this.mUsbPortStatus.getContaminantDetectionStatus() == i6) {
                  if (this.mUsbPortStatus.getUsbDataStatus() == i7) {
                    if (this.mUsbPortStatus.isPowerTransferLimited() == z4) {
                      if (this.mUsbPortStatus.getPowerBrickConnectionStatus() == i8) {
                        if (this.mUsbPortStatus.getPlugState() == i9) {
                          if (z6 || z7) {
                            this.mUsbPortStatus =
                                new UsbPortStatus(
                                    i,
                                    i2,
                                    i3,
                                    i4,
                                    i5,
                                    i6,
                                    i7,
                                    z4,
                                    i8,
                                    iArr,
                                    i9,
                                    displayPortAltModeInfo);
                          }
                          z8 = false;
                          if (!this.mUsbPortStatus.isConnected() && this.mConnectedAtMillis == 0) {
                            this.mConnectedAtMillis = SystemClock.elapsedRealtime();
                            this.mLastConnectDurationMillis = 0L;
                          } else if (!this.mUsbPortStatus.isConnected()
                              && this.mConnectedAtMillis != 0) {
                            this.mLastConnectDurationMillis =
                                SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                            this.mConnectedAtMillis = 0L;
                          }
                          return z8;
                        }
                        if (this.mUsbPortStatus == null && iArr.length > 0) {
                          this.mComplianceWarningChange = 1;
                        }
                        this.mUsbPortStatus =
                            new UsbPortStatus(
                                i,
                                i2,
                                i3,
                                i4,
                                i5,
                                i6,
                                i7,
                                z4,
                                i8,
                                iArr,
                                i9,
                                displayPortAltModeInfo);
                        z8 = true;
                        if (!this.mUsbPortStatus.isConnected()) {}
                        if (!this.mUsbPortStatus.isConnected()) {
                          this.mLastConnectDurationMillis =
                              SystemClock.elapsedRealtime() - this.mConnectedAtMillis;
                          this.mConnectedAtMillis = 0L;
                        }
                        return z8;
                      }
                      if (this.mUsbPortStatus == null) {
                        this.mComplianceWarningChange = 1;
                      }
                      this.mUsbPortStatus =
                          new UsbPortStatus(
                              i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
                      z8 = true;
                      if (!this.mUsbPortStatus.isConnected()) {}
                      if (!this.mUsbPortStatus.isConnected()) {}
                      return z8;
                    }
                    if (this.mUsbPortStatus == null) {}
                    this.mUsbPortStatus =
                        new UsbPortStatus(
                            i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
                    z8 = true;
                    if (!this.mUsbPortStatus.isConnected()) {}
                    if (!this.mUsbPortStatus.isConnected()) {}
                    return z8;
                  }
                  if (this.mUsbPortStatus == null) {}
                  this.mUsbPortStatus =
                      new UsbPortStatus(
                          i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
                  z8 = true;
                  if (!this.mUsbPortStatus.isConnected()) {}
                  if (!this.mUsbPortStatus.isConnected()) {}
                  return z8;
                }
                if (this.mUsbPortStatus == null) {}
                this.mUsbPortStatus =
                    new UsbPortStatus(
                        i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
                z8 = true;
                if (!this.mUsbPortStatus.isConnected()) {}
                if (!this.mUsbPortStatus.isConnected()) {}
                return z8;
              }
              if (this.mUsbPortStatus == null) {}
              this.mUsbPortStatus =
                  new UsbPortStatus(
                      i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
              z8 = true;
              if (!this.mUsbPortStatus.isConnected()) {}
              if (!this.mUsbPortStatus.isConnected()) {}
              return z8;
            }
            if (this.mUsbPortStatus == null) {}
            this.mUsbPortStatus =
                new UsbPortStatus(
                    i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
            z8 = true;
            if (!this.mUsbPortStatus.isConnected()) {}
            if (!this.mUsbPortStatus.isConnected()) {}
            return z8;
          }
          if (this.mUsbPortStatus == null) {}
          this.mUsbPortStatus =
              new UsbPortStatus(
                  i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
          z8 = true;
          if (!this.mUsbPortStatus.isConnected()) {}
          if (!this.mUsbPortStatus.isConnected()) {}
          return z8;
        }
        if (this.mUsbPortStatus == null) {}
        this.mUsbPortStatus =
            new UsbPortStatus(i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
        z8 = true;
        if (!this.mUsbPortStatus.isConnected()) {}
        if (!this.mUsbPortStatus.isConnected()) {}
        return z8;
      }
      if (this.mUsbPortStatus == null) {}
      this.mUsbPortStatus =
          new UsbPortStatus(i, i2, i3, i4, i5, i6, i7, z4, i8, iArr, i9, displayPortAltModeInfo);
      z8 = true;
      if (!this.mUsbPortStatus.isConnected()) {}
      if (!this.mUsbPortStatus.isConnected()) {}
      return z8;
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
      long start = dualDumpOutputStream.start(str, j);
      DumpUtils.writePort(dualDumpOutputStream, "port", 1146756268033L, this.mUsbPort);
      DumpUtils.writePortStatus(
          dualDumpOutputStream, "status", 1146756268034L, this.mUsbPortStatus);
      dualDumpOutputStream.write("can_change_mode", 1133871366147L, this.mCanChangeMode);
      dualDumpOutputStream.write("can_change_power_role", 1133871366148L, this.mCanChangePowerRole);
      dualDumpOutputStream.write("can_change_data_role", 1133871366149L, this.mCanChangeDataRole);
      dualDumpOutputStream.write("connected_at_millis", 1112396529670L, this.mConnectedAtMillis);
      dualDumpOutputStream.write(
          "last_connect_duration_millis", 1112396529671L, this.mLastConnectDurationMillis);
      dualDumpOutputStream.end(start);
    }

    public String toString() {
      return "port="
          + this.mUsbPort
          + ", status="
          + this.mUsbPortStatus
          + ", canChangeMode="
          + this.mCanChangeMode
          + ", canChangePowerRole="
          + this.mCanChangePowerRole
          + ", canChangeDataRole="
          + this.mCanChangeDataRole
          + ", connectedAtMillis="
          + this.mConnectedAtMillis
          + ", lastConnectDurationMillis="
          + this.mLastConnectDurationMillis;
    }
  }

  public void setPortRolesLegacy(
      String str, int i, int i2, IndentingPrintWriter indentingPrintWriter) {
    int i3;
    synchronized (this.mLock) {
      PortInfo portInfo = (PortInfo) this.mPorts.get(str);
      if (portInfo == null) {
        if (indentingPrintWriter != null) {
          indentingPrintWriter.println("No such USB port: " + str);
        }
        return;
      }
      if (!portInfo.mUsbPortStatus.isRoleCombinationSupported(i, i2)) {
        logAndPrint(
            6,
            indentingPrintWriter,
            "Attempted to set USB port into unsupported role combination: portId="
                + str
                + ", newPowerRole="
                + UsbPort.powerRoleToString(i)
                + ", newDataRole="
                + UsbPort.dataRoleToString(i2));
        return;
      }
      int currentDataRole = portInfo.mUsbPortStatus.getCurrentDataRole();
      int currentPowerRole = portInfo.mUsbPortStatus.getCurrentPowerRole();
      if (currentDataRole == i2 && currentPowerRole == i) {
        if (indentingPrintWriter != null) {
          indentingPrintWriter.println("No change.");
        }
        return;
      }
      boolean z = portInfo.mCanChangeMode;
      boolean z2 = portInfo.mCanChangePowerRole;
      boolean z3 = portInfo.mCanChangeDataRole;
      int currentMode = portInfo.mUsbPortStatus.getCurrentMode();
      if ((z2 || currentPowerRole == i) && (z3 || currentDataRole == i2)) {
        i3 = currentMode;
      } else {
        i3 = 2;
        if (!z || i != 1 || i2 != 1) {
          if (!z || i != 2 || i2 != 2) {
            logAndPrint(
                6,
                indentingPrintWriter,
                "Found mismatch in supported USB role combinations while attempting to change role:"
                    + " "
                    + portInfo
                    + ", newPowerRole="
                    + UsbPort.powerRoleToString(i)
                    + ", newDataRole="
                    + UsbPort.dataRoleToString(i2));
            return;
          }
          i3 = 1;
        }
      }
      logAndPrint(
          4,
          indentingPrintWriter,
          "Setting USB port mode and role: portId="
              + str
              + ", currentMode="
              + UsbPort.modeToString(currentMode)
              + ", currentPowerRole="
              + UsbPort.powerRoleToString(currentPowerRole)
              + ", currentDataRole="
              + UsbPort.dataRoleToString(currentDataRole)
              + ", newMode="
              + UsbPort.modeToString(i3)
              + ", newPowerRole="
              + UsbPort.powerRoleToString(i)
              + ", newDataRole="
              + UsbPort.dataRoleToString(i2));
      RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
      if (rawPortInfo != null) {
        rawPortInfo.currentMode = i3;
        rawPortInfo.currentPowerRole = i;
        rawPortInfo.currentDataRole = i2;
        updatePortsLocked(indentingPrintWriter, null);
      } else {
        UsbPortHal usbPortHal = this.mUsbPortHal;
        if (usbPortHal != null) {
          if (currentMode != i3) {
            logAndPrint(
                6,
                indentingPrintWriter,
                "Trying to set the USB port mode: portId="
                    + str
                    + ", newMode="
                    + UsbPort.modeToString(i3));
            try {
              UsbPortHal usbPortHal2 = this.mUsbPortHal;
              int halUsbMode = toHalUsbMode(i3);
              long j = this.mTransactionId + 1;
              this.mTransactionId = j;
              usbPortHal2.switchMode(str, halUsbMode, j);
            } catch (Exception e) {
              logAndPrintException(
                  indentingPrintWriter,
                  "Failed to set the USB port mode: portId="
                      + str
                      + ", newMode="
                      + UsbPort.modeToString(i3),
                  e);
            }
          } else if (currentDataRole != i2) {
            try {
              int halUsbDataRole = toHalUsbDataRole(i2);
              long j2 = this.mTransactionId + 1;
              this.mTransactionId = j2;
              usbPortHal.switchDataRole(str, halUsbDataRole, j2);
            } catch (Exception e2) {
              logAndPrintException(
                  indentingPrintWriter,
                  "Failed to set the USB port data role: portId="
                      + str
                      + ", newDataRole="
                      + UsbPort.dataRoleToString(i2),
                  e2);
            }
          }
        }
      }
    }
  }

  public int semGetPowerRoleStatus() {
    int i;
    Slog.d("UsbPortManager", "semGetPowerRoleStatus++");
    UsbPort[] ports = getPorts();
    int length = ports.length;
    int i2 = 0;
    while (true) {
      if (i2 >= length) {
        i = -1;
        break;
      }
      UsbPortStatus portStatus = getPortStatus(ports[i2].getId());
      Slog.d("UsbPortManager", "semGetPowerRoleStatus status of ports[" + i2 + "] = " + portStatus);
      if (portStatus != null && portStatus.isConnected()) {
        i = portStatus.getCurrentPowerRole();
        Slog.d(
            "UsbPortManager",
            "semGetPowerRoleStatus status.isConnected() = " + portStatus.isConnected());
        break;
      }
      i2++;
    }
    Slog.d("UsbPortManager", "semGetPowerRoleStatus-- with ret[" + i + "]");
    return i;
  }

  public int semGetDataRoleStatus() {
    int i;
    Slog.d("UsbPortManager", "semGetDataRoleStatus++");
    UsbPort[] ports = getPorts();
    int length = ports.length;
    int i2 = 0;
    while (true) {
      if (i2 >= length) {
        i = -1;
        break;
      }
      UsbPortStatus portStatus = getPortStatus(ports[i2].getId());
      Slog.d("UsbPortManager", "semGetDataRoleStatus status of ports[" + i2 + "] = " + portStatus);
      if (portStatus != null && portStatus.isConnected()) {
        i = portStatus.getCurrentDataRole();
        Slog.d(
            "UsbPortManager",
            "semGetDataRoleStatus status.isConnected() = " + portStatus.isConnected());
        break;
      }
      i2++;
    }
    Slog.d("UsbPortManager", "semGetDataRoleStatus-- with ret[" + i + "]");
    return i;
  }

  public final void initBoost() {
    if (this.usbPortBooster == null) {
      this.usbPortBooster =
          SemDvfsManager.createInstance(this.mContext, "USB_PORT_MANAGER_BOOSTER");
    }
    if (this.usbPortBooster != null) {
      Slog.d("UsbPortManager", "To boost, setHint");
      this.usbPortBooster.setHint(1150);
    }
  }

  public void startBoost() {
    if (this.usbPortBooster != null) {
      Slog.d("UsbPortManager", "To boost, Acquire");
      this.usbPortBooster.acquire();
    }
  }

  public void stopBoost() {
    if (this.usbPortBooster != null) {
      Slog.d("UsbPortManager", "To boost, Release");
      this.usbPortBooster.release();
    }
  }
}
