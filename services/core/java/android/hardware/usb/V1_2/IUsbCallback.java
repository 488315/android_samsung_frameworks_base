package android.hardware.usb.V1_2;

import android.hardware.usb.V1_0.PortRole;
import android.hardware.usb.V1_1.PortStatus_1_1;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public interface IUsbCallback extends android.hardware.usb.V1_1.IUsbCallback {
  void notifyPortStatusChange_1_2(ArrayList arrayList, int i);

  public final class Proxy implements IUsbCallback {
    public IHwBinder mRemote;

    @Override // android.hardware.usb.V1_0.IUsbCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hardware.usb@1.2::IUsbCallback]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.usb.V1_0.IUsbCallback, android.hidl.base.V1_0.IBase
    public ArrayList interfaceChain() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readStringVector();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      hwParcel.writeNativeHandle(nativeHandle);
      hwParcel.writeStringVector(arrayList);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public String interfaceDescriptor() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readString();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public ArrayList getHashChain() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        ArrayList arrayList = new ArrayList();
        HwBlob readBuffer = hwParcel2.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer =
            hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
          byte[] bArr = new byte[32];
          readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
          arrayList.add(bArr);
        }
        return arrayList;
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public void setHALInstrumentation() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
      return this.mRemote.linkToDeath(deathRecipient, j);
    }

    @Override // android.hidl.base.V1_0.IBase
    public void ping() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public DebugInfo getDebugInfo() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        DebugInfo debugInfo = new DebugInfo();
        debugInfo.readFromParcel(hwParcel2);
        return debugInfo;
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public void notifySyspropsChanged() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.base.V1_0.IBase
    public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return this.mRemote.unlinkToDeath(deathRecipient);
    }
  }

  public abstract class Stub extends HwBinder implements IUsbCallback {
    @Override // android.hardware.usb.V1_0.IUsbCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.usb@1.2::IUsbCallback";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
      return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {}

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return true;
    }

    @Override // android.hardware.usb.V1_0.IUsbCallback, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList(
              "android.hardware.usb@1.2::IUsbCallback",
              "android.hardware.usb@1.1::IUsbCallback",
              "android.hardware.usb@1.0::IUsbCallback",
              IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                70, -103, 108, -46, -95, -58, 98, 97, -89, 90, 31, 110, -54, -38, 119, -18, -75,
                -122, 30, -78, 100, -6, 57, -71, -106, 84, -113, -32, -89, -14, 45, -45
              },
              new byte[] {
                19,
                -91,
                Byte.MIN_VALUE,
                -29,
                90,
                -16,
                18,
                112,
                -95,
                -23,
                119,
                65,
                119,
                -59,
                29,
                -75,
                29,
                -122,
                114,
                -26,
                19,
                -101,
                -96,
                8,
                81,
                -26,
                84,
                -26,
                -118,
                77,
                125,
                -1
              },
              new byte[] {
                75,
                -25,
                -120,
                30,
                65,
                27,
                -92,
                39,
                -124,
                -65,
                91,
                115,
                84,
                -63,
                74,
                -32,
                -49,
                22,
                HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
                4,
                -45,
                -108,
                51,
                -86,
                -20,
                -86,
                -80,
                -47,
                -98,
                -87,
                -109,
                84
              },
              new byte[] {
                -20,
                Byte.MAX_VALUE,
                -41,
                -98,
                -48,
                45,
                -6,
                -123,
                -68,
                73,
                -108,
                38,
                -83,
                -82,
                62,
                -66,
                35,
                -17,
                5,
                36,
                -13,
                -51,
                105,
                87,
                19,
                -109,
                36,
                -72,
                59,
                24,
                -54,
                76
              }));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
      DebugInfo debugInfo = new DebugInfo();
      debugInfo.pid = HidlSupport.getPidIfSharable();
      debugInfo.ptr = 0L;
      debugInfo.arch = 0;
      return debugInfo;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
      HwBinder.enableInstrumentation();
    }

    public IHwInterface queryLocalInterface(String str) {
      if ("android.hardware.usb@1.2::IUsbCallback".equals(str)) {
        return this;
      }
      return null;
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
      if (i == 1) {
        hwParcel.enforceInterface("android.hardware.usb@1.0::IUsbCallback");
        notifyPortStatusChange(
            android.hardware.usb.V1_0.PortStatus.readVectorFromParcel(hwParcel),
            hwParcel.readInt32());
        return;
      }
      if (i == 2) {
        hwParcel.enforceInterface("android.hardware.usb@1.0::IUsbCallback");
        String readString = hwParcel.readString();
        PortRole portRole = new PortRole();
        portRole.readFromParcel(hwParcel);
        notifyRoleSwitchStatus(readString, portRole, hwParcel.readInt32());
        return;
      }
      if (i == 3) {
        hwParcel.enforceInterface("android.hardware.usb@1.1::IUsbCallback");
        notifyPortStatusChange_1_1(
            PortStatus_1_1.readVectorFromParcel(hwParcel), hwParcel.readInt32());
        return;
      }
      if (i == 4) {
        hwParcel.enforceInterface("android.hardware.usb@1.2::IUsbCallback");
        notifyPortStatusChange_1_2(PortStatus.readVectorFromParcel(hwParcel), hwParcel.readInt32());
        return;
      }
      switch (i) {
        case 256067662:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          ArrayList interfaceChain = interfaceChain();
          hwParcel2.writeStatus(0);
          hwParcel2.writeStringVector(interfaceChain);
          hwParcel2.send();
          return;
        case 256131655:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 256136003:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          String interfaceDescriptor = interfaceDescriptor();
          hwParcel2.writeStatus(0);
          hwParcel2.writeString(interfaceDescriptor);
          hwParcel2.send();
          return;
        case 256398152:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          ArrayList hashChain = getHashChain();
          hwParcel2.writeStatus(0);
          HwBlob hwBlob = new HwBlob(16);
          int size = hashChain.size();
          hwBlob.putInt32(8L, size);
          hwBlob.putBool(12L, false);
          HwBlob hwBlob2 = new HwBlob(size * 32);
          for (int i3 = 0; i3 < size; i3++) {
            long j = i3 * 32;
            byte[] bArr = (byte[]) hashChain.get(i3);
            if (bArr == null || bArr.length != 32) {
              throw new IllegalArgumentException("Array element is not of the expected length");
            }
            hwBlob2.putInt8Array(j, bArr);
          }
          hwBlob.putBlob(0L, hwBlob2);
          hwParcel2.writeBuffer(hwBlob);
          hwParcel2.send();
          return;
        case 256462420:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          setHALInstrumentation();
          return;
        case 256921159:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          ping();
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 257049926:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          DebugInfo debugInfo = getDebugInfo();
          hwParcel2.writeStatus(0);
          debugInfo.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 257120595:
          hwParcel.enforceInterface(IBase.kInterfaceName);
          notifySyspropsChanged();
          return;
        default:
          return;
      }
    }
  }
}
