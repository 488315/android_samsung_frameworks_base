package android.hardware.broadcastradio.V2_0;

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
public interface ITunerSession extends IBase {

  public interface isConfigFlagSetCallback {
    void onValues(int i, boolean z);
  }

  @Override // android.hidl.base.V1_0.IBase
  IHwBinder asBinder();

  void cancel();

  void close();

  ArrayList getParameters(ArrayList arrayList);

  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  void isConfigFlagSet(int i, isConfigFlagSetCallback isconfigflagsetcallback);

  int scan(boolean z, boolean z2);

  int setConfigFlag(int i, boolean z);

  ArrayList setParameters(ArrayList arrayList);

  int startProgramListUpdates(ProgramFilter programFilter);

  int step(boolean z);

  void stopProgramListUpdates();

  int tune(ProgramSelector programSelector);

  static ITunerSession asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.broadcastradio@2.0::ITunerSession");
    if (queryLocalInterface != null && (queryLocalInterface instanceof ITunerSession)) {
      return (ITunerSession) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.broadcastradio@2.0::ITunerSession")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  public final class Proxy implements ITunerSession {
    public IHwBinder mRemote;

    public Proxy(IHwBinder iHwBinder) {
      Objects.requireNonNull(iHwBinder);
      this.mRemote = iHwBinder;
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hardware.broadcastradio@2.0::ITunerSession]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public int tune(ProgramSelector programSelector) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      programSelector.writeToParcel(hwParcel);
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

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public int scan(boolean z, boolean z2) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      hwParcel.writeBool(z);
      hwParcel.writeBool(z2);
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

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public int step(boolean z) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      hwParcel.writeBool(z);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(3, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public void cancel() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(4, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public int startProgramListUpdates(ProgramFilter programFilter) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      programFilter.writeToParcel(hwParcel);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(5, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public void stopProgramListUpdates() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(6, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public void isConfigFlagSet(int i, isConfigFlagSetCallback isconfigflagsetcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(7, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        isconfigflagsetcallback.onValues(hwParcel2.readInt32(), hwParcel2.readBool());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public int setConfigFlag(int i, boolean z) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      hwParcel.writeInt32(i);
      hwParcel.writeBool(z);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(8, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public ArrayList setParameters(ArrayList arrayList) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      VendorKeyValue.writeVectorToParcel(hwParcel, arrayList);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(9, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return VendorKeyValue.readVectorFromParcel(hwParcel2);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public ArrayList getParameters(ArrayList arrayList) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      hwParcel.writeStringVector(arrayList);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(10, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return VendorKeyValue.readVectorFromParcel(hwParcel2);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession
    public void close() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(11, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession, android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements ITunerSession {
    @Override // android.hardware.broadcastradio.V2_0.ITunerSession, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.broadcastradio@2.0::ITunerSession";
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

    @Override // android.hardware.broadcastradio.V2_0.ITunerSession, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList(
              "android.hardware.broadcastradio@2.0::ITunerSession", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -41,
                4,
                100,
                -59,
                23,
                -92,
                -95,
                -75,
                22,
                119,
                48,
                -124,
                55,
                117,
                -87,
                Byte.MAX_VALUE,
                69,
                81,
                2,
                -111,
                -98,
                -108,
                91,
                4,
                -9,
                23,
                -71,
                -38,
                57,
                12,
                15,
                57
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
      if ("android.hardware.broadcastradio@2.0::ITunerSession".equals(str)) {
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
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          ProgramSelector programSelector = new ProgramSelector();
          programSelector.readFromParcel(hwParcel);
          int tune = tune(programSelector);
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(tune);
          hwParcel2.send();
          return;
        case 2:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          int scan = scan(hwParcel.readBool(), hwParcel.readBool());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(scan);
          hwParcel2.send();
          return;
        case 3:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          int step = step(hwParcel.readBool());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(step);
          hwParcel2.send();
          return;
        case 4:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          cancel();
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 5:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          ProgramFilter programFilter = new ProgramFilter();
          programFilter.readFromParcel(hwParcel);
          int startProgramListUpdates = startProgramListUpdates(programFilter);
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(startProgramListUpdates);
          hwParcel2.send();
          return;
        case 6:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          stopProgramListUpdates();
          hwParcel2.writeStatus(0);
          hwParcel2.send();
          return;
        case 7:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          isConfigFlagSet(
              hwParcel.readInt32(),
              new isConfigFlagSetCallback() { // from class:
                                              // android.hardware.broadcastradio.V2_0.ITunerSession.Stub.1
                @Override // android.hardware.broadcastradio.V2_0.ITunerSession.isConfigFlagSetCallback
                public void onValues(int i3, boolean z) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeBool(z);
                  hwParcel2.send();
                }
              });
          return;
        case 8:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          int configFlag = setConfigFlag(hwParcel.readInt32(), hwParcel.readBool());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(configFlag);
          hwParcel2.send();
          return;
        case 9:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          ArrayList parameters = setParameters(VendorKeyValue.readVectorFromParcel(hwParcel));
          hwParcel2.writeStatus(0);
          VendorKeyValue.writeVectorToParcel(hwParcel2, parameters);
          hwParcel2.send();
          return;
        case 10:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          ArrayList parameters2 = getParameters(hwParcel.readStringVector());
          hwParcel2.writeStatus(0);
          VendorKeyValue.writeVectorToParcel(hwParcel2, parameters2);
          hwParcel2.send();
          return;
        case 11:
          hwParcel.enforceInterface("android.hardware.broadcastradio@2.0::ITunerSession");
          close();
          hwParcel2.writeStatus(0);
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
