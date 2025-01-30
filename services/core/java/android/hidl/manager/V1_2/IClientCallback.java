package android.hidl.manager.V1_2;

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
public interface IClientCallback extends IBase {
  public static final String kInterfaceName = "android.hidl.manager@1.2::IClientCallback";

  @Override // android.hidl.base.V1_0.IBase
  IHwBinder asBinder();

  @Override // android.hidl.base.V1_0.IBase
  void debug(NativeHandle nativeHandle, ArrayList arrayList);

  @Override // android.hidl.base.V1_0.IBase
  DebugInfo getDebugInfo();

  @Override // android.hidl.base.V1_0.IBase
  ArrayList getHashChain();

  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  @Override // android.hidl.base.V1_0.IBase
  String interfaceDescriptor();

  @Override // android.hidl.base.V1_0.IBase
  boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j);

  @Override // android.hidl.base.V1_0.IBase
  void notifySyspropsChanged();

  void onClients(IBase iBase, boolean z);

  @Override // android.hidl.base.V1_0.IBase
  void ping();

  @Override // android.hidl.base.V1_0.IBase
  void setHALInstrumentation();

  @Override // android.hidl.base.V1_0.IBase
  boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient);

  static IClientCallback asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
    if (queryLocalInterface != null && (queryLocalInterface instanceof IClientCallback)) {
      return (IClientCallback) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals(kInterfaceName)) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  static IClientCallback castFrom(IHwInterface iHwInterface) {
    if (iHwInterface == null) {
      return null;
    }
    return asInterface(iHwInterface.asBinder());
  }

  static IClientCallback getService(String str, boolean z) {
    return asInterface(HwBinder.getService(kInterfaceName, str, z));
  }

  static IClientCallback getService(boolean z) {
    return getService("default", z);
  }

  @Deprecated
  static IClientCallback getService(String str) {
    return asInterface(HwBinder.getService(kInterfaceName, str));
  }

  @Deprecated
  static IClientCallback getService() {
    return getService("default");
  }

  public final class Proxy implements IClientCallback {
    private IHwBinder mRemote;

    public Proxy(IHwBinder iHwBinder) {
      Objects.requireNonNull(iHwBinder);
      this.mRemote = iHwBinder;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hidl.manager@1.2::IClientCallback]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hidl.manager.V1_2.IClientCallback
    public void onClients(IBase iBase, boolean z) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken(IClientCallback.kInterfaceName);
      hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
      hwParcel.writeBool(z);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(1, hwParcel, hwParcel2, 1);
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
      return this.mRemote.linkToDeath(deathRecipient, j);
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return this.mRemote.unlinkToDeath(deathRecipient);
    }
  }

  public abstract class Stub extends HwBinder implements IClientCallback {
    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return IClientCallback.kInterfaceName;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
      return true;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final void ping() {}

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
      return true;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(Arrays.asList(IClientCallback.kInterfaceName, IBase.kInterfaceName));
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -91,
                112,
                -3,
                67,
                106,
                -19,
                94,
                -18,
                -22,
                36,
                -107,
                -71,
                38,
                126,
                Byte.MAX_VALUE,
                -100,
                72,
                8,
                -43,
                30,
                -3,
                76,
                -101,
                -96,
                -111,
                59,
                -111,
                2,
                -21,
                -109,
                -82,
                -19
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

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
      DebugInfo debugInfo = new DebugInfo();
      debugInfo.pid = HidlSupport.getPidIfSharable();
      debugInfo.ptr = 0L;
      debugInfo.arch = 0;
      return debugInfo;
    }

    @Override // android.hidl.manager.V1_2.IClientCallback, android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
      HwBinder.enableInstrumentation();
    }

    public IHwInterface queryLocalInterface(String str) {
      if (IClientCallback.kInterfaceName.equals(str)) {
        return this;
      }
      return null;
    }

    public void registerAsService(String str) {
      registerService(str);
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
      switch (i) {
        case 1:
          hwParcel.enforceInterface(IClientCallback.kInterfaceName);
          onClients(IBase.asInterface(hwParcel.readStrongBinder()), hwParcel.readBool());
          return;
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
