package android.hardware.weaver.V1_0;

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
public interface IWeaver extends IBase {

  public interface getConfigCallback {
    void onValues(int i, WeaverConfig weaverConfig);
  }

  public interface readCallback {
    void onValues(int i, WeaverReadResponse weaverReadResponse);
  }

  void getConfig(getConfigCallback getconfigcallback);

  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  @Override // android.hidl.base.V1_0.IBase
  boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j);

  void read(int i, ArrayList arrayList, readCallback readcallback);

  @Override // android.hidl.base.V1_0.IBase
  boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient);

  int write(int i, ArrayList arrayList, ArrayList arrayList2);

  static IWeaver asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.weaver@1.0::IWeaver");
    if (queryLocalInterface != null && (queryLocalInterface instanceof IWeaver)) {
      return (IWeaver) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.weaver@1.0::IWeaver")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  static IWeaver getService(String str, boolean z) {
    return asInterface(HwBinder.getService("android.hardware.weaver@1.0::IWeaver", str, z));
  }

  static IWeaver getService(boolean z) {
    return getService("default", z);
  }

  public final class Proxy implements IWeaver {
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
        return "[class or subclass of android.hardware.weaver@1.0::IWeaver]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.weaver.V1_0.IWeaver
    public void getConfig(getConfigCallback getconfigcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.weaver@1.0::IWeaver");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(1, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        WeaverConfig weaverConfig = new WeaverConfig();
        weaverConfig.readFromParcel(hwParcel2);
        getconfigcallback.onValues(readInt32, weaverConfig);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.weaver.V1_0.IWeaver
    public int write(int i, ArrayList arrayList, ArrayList arrayList2) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.weaver@1.0::IWeaver");
      hwParcel.writeInt32(i);
      hwParcel.writeInt8Vector(arrayList);
      hwParcel.writeInt8Vector(arrayList2);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(2, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.weaver.V1_0.IWeaver
    public void read(int i, ArrayList arrayList, readCallback readcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.weaver@1.0::IWeaver");
      hwParcel.writeInt32(i);
      hwParcel.writeInt8Vector(arrayList);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(3, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        WeaverReadResponse weaverReadResponse = new WeaverReadResponse();
        weaverReadResponse.readFromParcel(hwParcel2);
        readcallback.onValues(readInt32, weaverReadResponse);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
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

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
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

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
    public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return this.mRemote.unlinkToDeath(deathRecipient);
    }
  }

  public abstract class Stub extends HwBinder implements IWeaver {
    @Override // android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.weaver@1.0::IWeaver";
    }

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
      return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {}

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return true;
    }

    @Override // android.hardware.weaver.V1_0.IWeaver, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList("android.hardware.weaver@1.0::IWeaver", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -101, -60, 52, 19, -72, 12, -48, -59, -102, 2, 46, -109, -38, 20, 72, -36, -72, 45,
                -47, 12, 109, -45, 25, 50, -33, 70, 89, -28, -67, -53, 19, 104
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
      if ("android.hardware.weaver@1.0::IWeaver".equals(str)) {
        return this;
      }
      return null;
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) {
      if (i == 1) {
        hwParcel.enforceInterface("android.hardware.weaver@1.0::IWeaver");
        getConfig(
            new getConfigCallback() { // from class: android.hardware.weaver.V1_0.IWeaver.Stub.1
              @Override // android.hardware.weaver.V1_0.IWeaver.getConfigCallback
              public void onValues(int i3, WeaverConfig weaverConfig) {
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(i3);
                weaverConfig.writeToParcel(hwParcel2);
                hwParcel2.send();
              }
            });
        return;
      }
      if (i == 2) {
        hwParcel.enforceInterface("android.hardware.weaver@1.0::IWeaver");
        int write =
            write(hwParcel.readInt32(), hwParcel.readInt8Vector(), hwParcel.readInt8Vector());
        hwParcel2.writeStatus(0);
        hwParcel2.writeInt32(write);
        hwParcel2.send();
        return;
      }
      if (i == 3) {
        hwParcel.enforceInterface("android.hardware.weaver@1.0::IWeaver");
        read(
            hwParcel.readInt32(),
            hwParcel.readInt8Vector(),
            new readCallback() { // from class: android.hardware.weaver.V1_0.IWeaver.Stub.2
              @Override // android.hardware.weaver.V1_0.IWeaver.readCallback
              public void onValues(int i3, WeaverReadResponse weaverReadResponse) {
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(i3);
                weaverReadResponse.writeToParcel(hwParcel2);
                hwParcel2.send();
              }
            });
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
