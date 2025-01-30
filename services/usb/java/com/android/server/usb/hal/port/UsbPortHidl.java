package com.android.server.usb.hal.port;

import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.UsbPort;
import android.hardware.usb.V1_0.IUsb;
import android.hardware.usb.V1_0.PortRole;
import android.hardware.usb.V1_0.PortStatus;
import android.hardware.usb.V1_1.PortStatus_1_1;
import android.hardware.usb.V1_2.IUsbCallback;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.RemoteException;
import android.util.sysfwutil.Slog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.usb.UsbPortManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class UsbPortHidl implements UsbPortHal {
  public static final String TAG = "UsbPortHidl";
  public static int sUsbDataStatus;
  public HALCallback mHALCallback;
  public final Object mLock = new Object();
  public UsbPortManager mPortManager;
  public IUsb mProxy;
  public IndentingPrintWriter mPw;
  public boolean mSystemReady;

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public int getUsbHalVersion() {
    int i;
    synchronized (this.mLock) {
      IUsb iUsb = this.mProxy;
      if (iUsb == null) {
        throw new RemoteException("IUsb not initialized yet");
      }
      if (android.hardware.usb.V1_3.IUsb.castFrom((IHwInterface) iUsb) != null) {
        i = 13;
      } else if (android.hardware.usb.V1_2.IUsb.castFrom((IHwInterface) this.mProxy) != null) {
        i = 12;
      } else {
        i = android.hardware.usb.V1_1.IUsb.castFrom((IHwInterface) this.mProxy) != null ? 11 : 10;
      }
      UsbPortManager.logAndPrint(4, null, "USB HAL HIDL version: " + i);
    }
    return i;
  }

  public final class DeathRecipient implements IHwBinder.DeathRecipient {

    /* renamed from: pw */
    public IndentingPrintWriter f1732pw;

    public DeathRecipient(IndentingPrintWriter indentingPrintWriter) {
      this.f1732pw = indentingPrintWriter;
    }

    public void serviceDied(long j) {
      if (j == 1000) {
        UsbPortManager.logAndPrint(6, this.f1732pw, "Usb hal service died cookie: " + j);
        synchronized (UsbPortHidl.this.mLock) {
          UsbPortHidl.this.mProxy = null;
        }
      }
    }
  }

  public final class ServiceNotification extends IServiceNotification.Stub {
    public ServiceNotification() {}

    @Override // android.hidl.manager.V1_0.IServiceNotification
    public void onRegistration(String str, String str2, boolean z) {
      UsbPortManager.logAndPrint(4, null, "Usb hal service started " + str + " " + str2);
      UsbPortHidl.this.connectToProxy(null);
    }
  }

  public final void connectToProxy(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      if (this.mProxy != null) {
        return;
      }
      try {
        IUsb service = IUsb.getService();
        this.mProxy = service;
        service.linkToDeath(new DeathRecipient(indentingPrintWriter), 1000L);
        this.mProxy.setCallback(this.mHALCallback);
        this.mProxy.queryPortStatus();
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(
            indentingPrintWriter, "connectToProxy: usb hal service not responding", e);
      } catch (NoSuchElementException e2) {
        UsbPortManager.logAndPrintException(
            indentingPrintWriter,
            "connectToProxy: usb hal service not found. Did the service fail to start?",
            e2);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void systemReady() {
    this.mSystemReady = true;
  }

  public static boolean isServicePresent(IndentingPrintWriter indentingPrintWriter) {
    try {
      IUsb.getService(true);
    } catch (RemoteException e) {
      UsbPortManager.logAndPrintException(
          indentingPrintWriter, "IUSB hal service present but failed to get service", e);
    } catch (NoSuchElementException e2) {
      UsbPortManager.logAndPrintException(
          indentingPrintWriter, "connectToProxy: usb hidl hal service not found.", e2);
      return false;
    }
    return true;
  }

  public UsbPortHidl(UsbPortManager usbPortManager, IndentingPrintWriter indentingPrintWriter) {
    Objects.requireNonNull(usbPortManager);
    this.mPortManager = usbPortManager;
    this.mPw = indentingPrintWriter;
    this.mHALCallback = new HALCallback(null, this.mPortManager, this);
    try {
      if (!IServiceManager.getService()
          .registerForNotifications(
              "android.hardware.usb@1.0::IUsb", "", new ServiceNotification())) {
        UsbPortManager.logAndPrint(6, null, "Failed to register service start notification");
      }
      connectToProxy(this.mPw);
    } catch (RemoteException e) {
      UsbPortManager.logAndPrintException(null, "Failed to register service start notification", e);
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void enableContaminantPresenceDetection(String str, boolean z, long j) {
    synchronized (this.mLock) {
      IUsb iUsb = this.mProxy;
      if (iUsb == null) {
        UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
        return;
      }
      try {
        android.hardware.usb.V1_2.IUsb.castFrom((IHwInterface) iUsb)
            .enableContaminantPresenceDetection(str, z);
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(this.mPw, "Failed to set contaminant detection", e);
      } catch (ClassCastException e2) {
        UsbPortManager.logAndPrintException(
            this.mPw, "Method only applicable to V1.2 or above implementation", e2);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void queryPortStatus(long j) {
    synchronized (this.mLock) {
      IUsb iUsb = this.mProxy;
      if (iUsb == null) {
        UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
        return;
      }
      try {
        iUsb.queryPortStatus();
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(null, "ServiceStart: Failed to query port status", e);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void switchMode(String str, int i, long j) {
    synchronized (this.mLock) {
      if (this.mProxy == null) {
        UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
        return;
      }
      PortRole portRole = new PortRole();
      portRole.type = 2;
      portRole.role = i;
      try {
        this.mProxy.switchRole(str, portRole);
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(
            this.mPw,
            "Failed to set the USB port mode: portId="
                + str
                + ", newMode="
                + UsbPort.modeToString(portRole.role),
            e);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void switchPowerRole(String str, int i, long j) {
    synchronized (this.mLock) {
      if (this.mProxy == null) {
        UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
        return;
      }
      PortRole portRole = new PortRole();
      portRole.type = 1;
      portRole.role = i;
      try {
        this.mProxy.switchRole(str, portRole);
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(
            this.mPw,
            "Failed to set the USB power role: portId="
                + str
                + ", newPowerRole="
                + UsbPort.powerRoleToString(portRole.role),
            e);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void enableLimitPowerTransfer(
      String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
    try {
      iUsbOperationInternal.onOperationComplete(2);
    } catch (RemoteException e) {
      UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void enableUsbDataWhileDocked(
      String str, long j, IUsbOperationInternal iUsbOperationInternal) {
    try {
      iUsbOperationInternal.onOperationComplete(2);
    } catch (RemoteException e) {
      UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void switchDataRole(String str, int i, long j) {
    synchronized (this.mLock) {
      if (this.mProxy == null) {
        UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
        return;
      }
      PortRole portRole = new PortRole();
      portRole.type = 0;
      portRole.role = i;
      try {
        this.mProxy.switchRole(str, portRole);
      } catch (RemoteException e) {
        UsbPortManager.logAndPrintException(
            this.mPw,
            "Failed to set the USB data role: portId="
                + str
                + ", newDataRole="
                + UsbPort.dataRoleToString(portRole.role),
            e);
      }
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public void resetUsbPort(String str, long j, IUsbOperationInternal iUsbOperationInternal) {
    try {
      iUsbOperationInternal.onOperationComplete(2);
    } catch (RemoteException e) {
      UsbPortManager.logAndPrintException(
          this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
    }
  }

  @Override // com.android.server.usb.hal.port.UsbPortHal
  public boolean enableUsbData(
      String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
    boolean enableUsbDataSignal;
    try {
      if (getUsbHalVersion() != 13) {
        try {
          iUsbOperationInternal.onOperationComplete(2);
        } catch (RemoteException e) {
          UsbPortManager.logAndPrintException(
              this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
        }
        return false;
      }
      synchronized (this.mLock) {
        try {
          enableUsbDataSignal =
              android.hardware.usb.V1_3.IUsb.castFrom((IHwInterface) this.mProxy)
                  .enableUsbDataSignal(z);
          Slog.d(
              TAG,
              "enableUsbData success["
                  + enableUsbDataSignal
                  + "] from USB HAL, while enable["
                  + z
                  + "]");
        } catch (RemoteException e2) {
          UsbPortManager.logAndPrintException(
              this.mPw, "Failed enableUsbData: opId:" + j + " portId=" + str, e2);
          try {
            iUsbOperationInternal.onOperationComplete(1);
          } catch (RemoteException e3) {
            UsbPortManager.logAndPrintException(
                this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e3);
          }
          return false;
        }
      }
      if (enableUsbDataSignal) {
        sUsbDataStatus = z ? 0 : 16;
      }
      try {
        iUsbOperationInternal.onOperationComplete(enableUsbDataSignal ? 0 : 1);
      } catch (RemoteException e4) {
        UsbPortManager.logAndPrintException(
            this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e4);
      }
      return false;
    } catch (RemoteException e5) {
      UsbPortManager.logAndPrintException(
          this.mPw, "Failed to query USB HAL version. opID:" + j + " portId:" + str, e5);
      return false;
    }
  }

  public class HALCallback extends IUsbCallback.Stub {
    public UsbPortManager mPortManager;
    public IndentingPrintWriter mPw;
    public UsbPortHidl mUsbPortHidl;
    public int preDataRole = 0;

    public HALCallback(
        IndentingPrintWriter indentingPrintWriter,
        UsbPortManager usbPortManager,
        UsbPortHidl usbPortHidl) {
      this.mPw = indentingPrintWriter;
      this.mPortManager = usbPortManager;
      this.mUsbPortHidl = usbPortHidl;
    }

    @Override // android.hardware.usb.V1_0.IUsbCallback
    public void notifyPortStatusChange(ArrayList arrayList, int i) {
      if (this.mUsbPortHidl.mSystemReady) {
        if (i != 0) {
          UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
          return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
          PortStatus portStatus = (PortStatus) it.next();
          arrayList2.add(
              new RawPortInfo(
                  portStatus.portName,
                  portStatus.supportedModes,
                  0,
                  portStatus.currentMode,
                  portStatus.canChangeMode,
                  portStatus.currentPowerRole,
                  portStatus.canChangePowerRole,
                  portStatus.currentDataRole,
                  portStatus.canChangeDataRole,
                  false,
                  0,
                  false,
                  0,
                  UsbPortHidl.sUsbDataStatus,
                  false,
                  0,
                  false,
                  new int[0],
                  0,
                  0,
                  null));
          UsbPortManager.logAndPrint(4, this.mPw, "ClientCallback V1_0: " + portStatus.portName);
        }
        this.mPortManager.updatePorts(arrayList2);
      }
    }

    @Override // android.hardware.usb.V1_1.IUsbCallback
    public void notifyPortStatusChange_1_1(ArrayList arrayList, int i) {
      int i2;
      if (this.mUsbPortHidl.mSystemReady) {
        if (i != 0) {
          UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
          return;
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
          PortStatus_1_1 portStatus_1_1 = (PortStatus_1_1) arrayList.get(i3);
          PortStatus portStatus = portStatus_1_1.status;
          int i4 = size;
          arrayList2.add(
              new RawPortInfo(
                  portStatus.portName,
                  portStatus_1_1.supportedModes,
                  0,
                  portStatus_1_1.currentMode,
                  portStatus.canChangeMode,
                  portStatus.currentPowerRole,
                  portStatus.canChangePowerRole,
                  portStatus.currentDataRole,
                  portStatus.canChangeDataRole,
                  false,
                  0,
                  false,
                  0,
                  UsbPortHidl.sUsbDataStatus,
                  false,
                  0,
                  false,
                  new int[0],
                  0,
                  0,
                  null));
          int i5 = portStatus_1_1.status.currentDataRole;
          if (i5 == 1 && ((i2 = this.preDataRole) == 0 || i2 == 2)) {
            this.mPortManager.startBoost();
          } else if (this.preDataRole == 1 && (i5 == 0 || i5 == 2)) {
            this.mPortManager.stopBoost();
          }
          this.preDataRole = i5;
          UsbPortManager.logAndPrint(
              4, this.mPw, "ClientCallback V1_1: " + portStatus_1_1.status.portName);
          i3++;
          size = i4;
        }
        this.mPortManager.updatePorts(arrayList2);
      }
    }

    @Override // android.hardware.usb.V1_2.IUsbCallback
    public void notifyPortStatusChange_1_2(ArrayList arrayList, int i) {
      int i2;
      if (this.mUsbPortHidl.mSystemReady) {
        if (i != 0) {
          UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
          return;
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
          android.hardware.usb.V1_2.PortStatus portStatus =
              (android.hardware.usb.V1_2.PortStatus) arrayList.get(i3);
          PortStatus_1_1 portStatus_1_1 = portStatus.status_1_1;
          PortStatus portStatus2 = portStatus_1_1.status;
          int i4 = size;
          arrayList2.add(
              new RawPortInfo(
                  portStatus2.portName,
                  portStatus_1_1.supportedModes,
                  portStatus.supportedContaminantProtectionModes,
                  portStatus_1_1.currentMode,
                  portStatus2.canChangeMode,
                  portStatus2.currentPowerRole,
                  portStatus2.canChangePowerRole,
                  portStatus2.currentDataRole,
                  portStatus2.canChangeDataRole,
                  portStatus.supportsEnableContaminantPresenceProtection,
                  portStatus.contaminantProtectionStatus,
                  portStatus.supportsEnableContaminantPresenceDetection,
                  portStatus.contaminantDetectionStatus,
                  UsbPortHidl.sUsbDataStatus,
                  false,
                  0,
                  false,
                  new int[0],
                  0,
                  0,
                  null));
          int i5 = portStatus.status_1_1.status.currentDataRole;
          if (i5 == 1 && ((i2 = this.preDataRole) == 0 || i2 == 2)) {
            this.mPortManager.startBoost();
          } else if (this.preDataRole == 1 && (i5 == 0 || i5 == 2)) {
            this.mPortManager.stopBoost();
          }
          this.preDataRole = i5;
          UsbPortManager.logAndPrint(
              4, this.mPw, "ClientCallback V1_2: " + portStatus.status_1_1.status.portName);
          i3++;
          size = i4;
        }
        this.mPortManager.updatePorts(arrayList2);
      }
    }

    @Override // android.hardware.usb.V1_0.IUsbCallback
    public void notifyRoleSwitchStatus(String str, PortRole portRole, int i) {
      if (i == 0) {
        UsbPortManager.logAndPrint(4, this.mPw, str + " role switch successful");
        return;
      }
      UsbPortManager.logAndPrint(6, this.mPw, str + " role switch failed");
    }
  }
}
