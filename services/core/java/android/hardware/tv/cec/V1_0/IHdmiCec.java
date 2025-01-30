package android.hardware.tv.cec.V1_0;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes.dex */
public interface IHdmiCec extends IBase {

  public interface getPhysicalAddressCallback {
    void onValues(int i, short s);
  }

  int addLogicalAddress(int i);

  void clearLogicalAddress();

  void enableAudioReturnChannel(int i, boolean z);

  int getCecVersion();

  void getPhysicalAddress(getPhysicalAddressCallback getphysicaladdresscallback);

  ArrayList getPortInfo();

  int getVendorId();

  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  boolean isConnected(int i);

  @Override // android.hidl.base.V1_0.IBase
  boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j);

  int sendMessage(CecMessage cecMessage);

  void setCallback(IHdmiCecCallback iHdmiCecCallback);

  void setLanguage(String str);

  void setOption(int i, boolean z);

  static IHdmiCec asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.tv.cec@1.0::IHdmiCec");
    if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCec)) {
      return (IHdmiCec) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.tv.cec@1.0::IHdmiCec")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  static IHdmiCec getService(String str, boolean z) {
    return asInterface(HwBinder.getService("android.hardware.tv.cec@1.0::IHdmiCec", str, z));
  }

  static IHdmiCec getService(boolean z) {
    return getService("default", z);
  }

  public final class Proxy implements IHdmiCec {
    public IHwBinder mRemote;

    public Proxy(IHwBinder iHwBinder) {
      Objects.requireNonNull(iHwBinder);
      this.mRemote = iHwBinder;
    }

    @Override // android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hardware.tv.cec@1.0::IHdmiCec]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public int addLogicalAddress(int i) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(1, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void clearLogicalAddress() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(2, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void getPhysicalAddress(getPhysicalAddressCallback getphysicaladdresscallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(3, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getphysicaladdresscallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt16());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public int sendMessage(CecMessage cecMessage) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      cecMessage.writeToParcel(hwParcel);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(4, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void setCallback(IHdmiCecCallback iHdmiCecCallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeStrongBinder(iHdmiCecCallback == null ? null : iHdmiCecCallback.asBinder());
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(5, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public int getCecVersion() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(6, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public int getVendorId() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(7, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public ArrayList getPortInfo() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(8, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return HdmiPortInfo.readVectorFromParcel(hwParcel2);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void setOption(int i, boolean z) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeInt32(i);
      hwParcel.writeBool(z);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(9, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void setLanguage(String str) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeString(str);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(10, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public void enableAudioReturnChannel(int i, boolean z) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeInt32(i);
      hwParcel.writeBool(z);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(11, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec
    public boolean isConnected(int i) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(12, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readBool();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec, android.hidl.base.V1_0.IBase
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

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec, android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements IHdmiCec {
    @Override // android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.tv.cec@1.0::IHdmiCec";
    }

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec, android.hidl.base.V1_0.IBase
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

    @Override // android.hardware.tv.cec.V1_0.IHdmiCec, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList("android.hardware.tv.cec@1.0::IHdmiCec", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -109, -120, 80, 98, 28, 60, 94, -12, 38, -92, -72, -114, 117, 43, -87, -101, 53, 89,
                3, 126, 120, 42, 61, -109, -122, 4, -13, -82, -11, -52, 15, 27
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
      if ("android.hardware.tv.cec@1.0::IHdmiCec".equals(str)) {
        return this;
      }
      return null;
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) {
      switch (i) {
        case 1:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          int addLogicalAddress = addLogicalAddress(hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(addLogicalAddress);
          hwParcel2.send();
          return;
        case 2:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          clearLogicalAddress();
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 3:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          getPhysicalAddress(
              new getPhysicalAddressCallback() { // from class:
                                                 // android.hardware.tv.cec.V1_0.IHdmiCec.Stub.1
                @Override // android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback
                public void onValues(int i3, short s) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt16(s);
                  hwParcel2.send();
                }
              });
          return;
        case 4:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          CecMessage cecMessage = new CecMessage();
          cecMessage.readFromParcel(hwParcel);
          int sendMessage = sendMessage(cecMessage);
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(sendMessage);
          hwParcel2.send();
          return;
        case 5:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          setCallback(IHdmiCecCallback.asInterface(hwParcel.readStrongBinder()));
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 6:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          int cecVersion = getCecVersion();
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(cecVersion);
          hwParcel2.send();
          return;
        case 7:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          int vendorId = getVendorId();
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(vendorId);
          hwParcel2.send();
          return;
        case 8:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          ArrayList portInfo = getPortInfo();
          hwParcel2.writeStatus(0);
          HdmiPortInfo.writeVectorToParcel(hwParcel2, portInfo);
          hwParcel2.send();
          return;
        case 9:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          setOption(hwParcel.readInt32(), hwParcel.readBool());
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 10:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          setLanguage(hwParcel.readString());
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 11:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          enableAudioReturnChannel(hwParcel.readInt32(), hwParcel.readBool());
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 12:
          hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCec");
          boolean isConnected = isConnected(hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeBool(isConnected);
          hwParcel2.send();
          return;
        default:
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
}
