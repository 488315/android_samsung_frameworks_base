package android.hardware.tv.cec.V1_1;

import android.hardware.tv.cec.V1_0.CecMessage;
import android.hardware.tv.cec.V1_0.HotplugEvent;
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
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes.dex */
public interface IHdmiCecCallback extends android.hardware.tv.cec.V1_0.IHdmiCecCallback {
  @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
  IHwBinder asBinder();

  @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  void onCecMessage_1_1(CecMessage cecMessage);

  static IHdmiCecCallback asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.tv.cec@1.1::IHdmiCecCallback");
    if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCecCallback)) {
      return (IHdmiCecCallback) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.tv.cec@1.1::IHdmiCecCallback")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  public final class Proxy implements IHdmiCecCallback {
    public IHwBinder mRemote;

    public Proxy(IHwBinder iHwBinder) {
      Objects.requireNonNull(iHwBinder);
      this.mRemote = iHwBinder;
    }

    @Override // android.hardware.tv.cec.V1_1.IHdmiCecCallback,
              // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hardware.tv.cec@1.1::IHdmiCecCallback]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.tv.cec.V1_1.IHdmiCecCallback,
              // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements IHdmiCecCallback {
    @Override // android.hardware.tv.cec.V1_1.IHdmiCecCallback,
              // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.tv.cec@1.1::IHdmiCecCallback";
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

    @Override // android.hardware.tv.cec.V1_1.IHdmiCecCallback,
              // android.hardware.tv.cec.V1_0.IHdmiCecCallback, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList(
              "android.hardware.tv.cec@1.1::IHdmiCecCallback",
              "android.hardware.tv.cec@1.0::IHdmiCecCallback",
              IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -71, 104, 37, -121, 103, 124, -23, -56, 114, -32, 79, 14, -97, -42, -55, -57, -118,
                86, -82, 121, 92, 7, -53, -8, -59, 1, 0, -32, 53, 29, 76, 68
              },
              new byte[] {
                -25,
                91,
                110,
                -22,
                113,
                29,
                54,
                -6,
                -58,
                120,
                -68,
                -32,
                114,
                -77,
                -50,
                -58,
                84,
                75,
                39,
                -6,
                -97,
                76,
                -39,
                3,
                -103,
                -108,
                4,
                -27,
                -63,
                HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
                -54,
                HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED
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
      if ("android.hardware.tv.cec@1.1::IHdmiCecCallback".equals(str)) {
        return this;
      }
      return null;
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
      if (i == 1) {
        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
        CecMessage cecMessage = new CecMessage();
        cecMessage.readFromParcel(hwParcel);
        onCecMessage(cecMessage);
        return;
      }
      if (i == 2) {
        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
        HotplugEvent hotplugEvent = new HotplugEvent();
        hotplugEvent.readFromParcel(hwParcel);
        onHotplugEvent(hotplugEvent);
        return;
      }
      if (i == 3) {
        hwParcel.enforceInterface("android.hardware.tv.cec@1.1::IHdmiCecCallback");
        CecMessage cecMessage2 = new CecMessage();
        cecMessage2.readFromParcel(hwParcel);
        onCecMessage_1_1(cecMessage2);
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
