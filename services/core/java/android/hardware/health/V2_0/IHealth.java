package android.hardware.health.V2_0;

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
import com.att.iqi.lib.metrics.p020mm.MM05;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes.dex */
public interface IHealth extends IBase {

  public interface getCapacityCallback {
    void onValues(int i, int i2);
  }

  public interface getChargeCounterCallback {
    void onValues(int i, int i2);
  }

  public interface getChargeStatusCallback {
    void onValues(int i, int i2);
  }

  public interface getCurrentAverageCallback {
    void onValues(int i, int i2);
  }

  public interface getCurrentNowCallback {
    void onValues(int i, int i2);
  }

  public interface getDiskStatsCallback {}

  public interface getEnergyCounterCallback {
    void onValues(int i, long j);
  }

  public interface getHealthInfoCallback {
    void onValues(int i, HealthInfo healthInfo);
  }

  public interface getStorageInfoCallback {}

  void getCapacity(getCapacityCallback getcapacitycallback);

  void getChargeCounter(getChargeCounterCallback getchargecountercallback);

  void getChargeStatus(getChargeStatusCallback getchargestatuscallback);

  void getCurrentAverage(getCurrentAverageCallback getcurrentaveragecallback);

  void getCurrentNow(getCurrentNowCallback getcurrentnowcallback);

  void getDiskStats(getDiskStatsCallback getdiskstatscallback);

  void getEnergyCounter(getEnergyCounterCallback getenergycountercallback);

  void getHealthInfo(getHealthInfoCallback gethealthinfocallback);

  void getStorageInfo(getStorageInfoCallback getstorageinfocallback);

  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  int registerCallback(IHealthInfoCallback iHealthInfoCallback);

  int unregisterCallback(IHealthInfoCallback iHealthInfoCallback);

  int update();

  static IHealth asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.health@2.0::IHealth");
    if (queryLocalInterface != null && (queryLocalInterface instanceof IHealth)) {
      return (IHealth) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.health@2.0::IHealth")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  static IHealth getService(String str, boolean z) {
    return asInterface(HwBinder.getService("android.hardware.health@2.0::IHealth", str, z));
  }

  public final class Proxy implements IHealth {
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
        return "[class or subclass of android.hardware.health@2.0::IHealth]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.health.V2_0.IHealth
    public int registerCallback(IHealthInfoCallback iHealthInfoCallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      hwParcel.writeStrongBinder(
          iHealthInfoCallback == null ? null : iHealthInfoCallback.asBinder());
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

    @Override // android.hardware.health.V2_0.IHealth
    public int unregisterCallback(IHealthInfoCallback iHealthInfoCallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      hwParcel.writeStrongBinder(
          iHealthInfoCallback == null ? null : iHealthInfoCallback.asBinder());
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

    @Override // android.hardware.health.V2_0.IHealth
    public int update() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
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

    @Override // android.hardware.health.V2_0.IHealth
    public void getChargeCounter(getChargeCounterCallback getchargecountercallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(4, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getchargecountercallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getCurrentNow(getCurrentNowCallback getcurrentnowcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(5, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getcurrentnowcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getCurrentAverage(getCurrentAverageCallback getcurrentaveragecallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(6, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getcurrentaveragecallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getCapacity(getCapacityCallback getcapacitycallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(7, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getcapacitycallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getEnergyCounter(getEnergyCounterCallback getenergycountercallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(8, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getenergycountercallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt64());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getChargeStatus(getChargeStatusCallback getchargestatuscallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(9, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getchargestatuscallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth
    public void getHealthInfo(getHealthInfoCallback gethealthinfocallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.health@2.0::IHealth");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(12, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.readFromParcel(hwParcel2);
        gethealthinfocallback.onValues(readInt32, healthInfo);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.health.V2_0.IHealth, android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements IHealth {
    @Override // android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.health@2.0::IHealth";
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

    @Override // android.hardware.health.V2_0.IHealth, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList("android.hardware.health@2.0::IHealth", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                103,
                86,
                -126,
                -35,
                48,
                7,
                Byte.MIN_VALUE,
                92,
                -104,
                94,
                -86,
                -20,
                -111,
                97,
                42,
                -68,
                -120,
                -12,
                -62,
                91,
                52,
                49,
                -5,
                -124,
                7,
                MM05.IQ_SIP_CALL_STATE_DISCONNECTING,
                117,
                -124,
                -95,
                -89,
                65,
                -5
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
      if ("android.hardware.health@2.0::IHealth".equals(str)) {
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
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          int registerCallback =
              registerCallback(IHealthInfoCallback.asInterface(hwParcel.readStrongBinder()));
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(registerCallback);
          hwParcel2.send();
          return;
        case 2:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          int unregisterCallback =
              unregisterCallback(IHealthInfoCallback.asInterface(hwParcel.readStrongBinder()));
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(unregisterCallback);
          hwParcel2.send();
          return;
        case 3:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          int update = update();
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(update);
          hwParcel2.send();
          return;
        case 4:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getChargeCounter(
              new getChargeCounterCallback() { // from class:
                                               // android.hardware.health.V2_0.IHealth.Stub.1
                @Override // android.hardware.health.V2_0.IHealth.getChargeCounterCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 5:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getCurrentNow(
              new getCurrentNowCallback() { // from class:
                                            // android.hardware.health.V2_0.IHealth.Stub.2
                @Override // android.hardware.health.V2_0.IHealth.getCurrentNowCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 6:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getCurrentAverage(
              new getCurrentAverageCallback() { // from class:
                                                // android.hardware.health.V2_0.IHealth.Stub.3
                @Override // android.hardware.health.V2_0.IHealth.getCurrentAverageCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 7:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getCapacity(
              new getCapacityCallback() { // from class: android.hardware.health.V2_0.IHealth.Stub.4
                @Override // android.hardware.health.V2_0.IHealth.getCapacityCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 8:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getEnergyCounter(
              new getEnergyCounterCallback() { // from class:
                                               // android.hardware.health.V2_0.IHealth.Stub.5
                @Override // android.hardware.health.V2_0.IHealth.getEnergyCounterCallback
                public void onValues(int i3, long j) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt64(j);
                  hwParcel2.send();
                }
              });
          return;
        case 9:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getChargeStatus(
              new getChargeStatusCallback() { // from class:
                                              // android.hardware.health.V2_0.IHealth.Stub.6
                @Override // android.hardware.health.V2_0.IHealth.getChargeStatusCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 10:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getStorageInfo(
              new getStorageInfoCallback() { // from class:
                                             // android.hardware.health.V2_0.IHealth.Stub.7
              });
          return;
        case 11:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getDiskStats(
              new getDiskStatsCallback() { // from class:
                                           // android.hardware.health.V2_0.IHealth.Stub.8
              });
          return;
        case 12:
          hwParcel.enforceInterface("android.hardware.health@2.0::IHealth");
          getHealthInfo(
              new getHealthInfoCallback() { // from class:
                                            // android.hardware.health.V2_0.IHealth.Stub.9
                @Override // android.hardware.health.V2_0.IHealth.getHealthInfoCallback
                public void onValues(int i3, HealthInfo healthInfo) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  healthInfo.writeToParcel(hwParcel2);
                  hwParcel2.send();
                }
              });
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
