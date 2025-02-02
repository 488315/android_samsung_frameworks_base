package android.hardware.configstore.V1_1;

import android.hardware.configstore.V1_0.OptionalBool;
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
public interface ISurfaceFlingerConfigs
    extends android.hardware.configstore.V1_0.ISurfaceFlingerConfigs {
  @Override // android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  OptionalDisplayOrientation primaryDisplayOrientation();

  static ISurfaceFlingerConfigs asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.configstore@1.1::ISurfaceFlingerConfigs");
    if (queryLocalInterface != null && (queryLocalInterface instanceof ISurfaceFlingerConfigs)) {
      return (ISurfaceFlingerConfigs) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next())
            .equals("android.hardware.configstore@1.1::ISurfaceFlingerConfigs")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  static ISurfaceFlingerConfigs getService(String str) {
    return asInterface(
        HwBinder.getService("android.hardware.configstore@1.1::ISurfaceFlingerConfigs", str));
  }

  static ISurfaceFlingerConfigs getService() {
    return getService("default");
  }

  public final class Proxy implements ISurfaceFlingerConfigs {
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
        return "[class or subclass of"
                   + " android.hardware.configstore@1.1::ISurfaceFlingerConfigs]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
    public OptionalBool hasWideColorDisplay() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(4, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        OptionalBool optionalBool = new OptionalBool();
        optionalBool.readFromParcel(hwParcel2);
        return optionalBool;
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
    public OptionalBool hasHDRDisplay() {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(5, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        OptionalBool optionalBool = new OptionalBool();
        optionalBool.readFromParcel(hwParcel2);
        return optionalBool;
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.configstore.V1_1.ISurfaceFlingerConfigs,
              // android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements ISurfaceFlingerConfigs {
    @Override // android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.configstore@1.1::ISurfaceFlingerConfigs";
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

    @Override // android.hardware.configstore.V1_1.ISurfaceFlingerConfigs,
              // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList(
              "android.hardware.configstore@1.1::ISurfaceFlingerConfigs",
              "android.hardware.configstore@1.0::ISurfaceFlingerConfigs",
              IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -8,
                88,
                9,
                27,
                HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
                -9,
                -43,
                -110,
                123,
                -26,
                5,
                115,
                -64,
                109,
                -12,
                Byte.MIN_VALUE,
                82,
                117,
                -45,
                114,
                38,
                -69,
                -76,
                26,
                115,
                33,
                -112,
                -65,
                -72,
                20,
                87,
                -20
              },
              new byte[] {
                -38, 51, 35, 68, 3, -1, 93, 96, -13, 71, 55, 17, -111, 123, -103, 72, -26, 72, 74,
                66, 96, -75, 36, 122, -51, -81, -79, 17, 25, 58, -99, -30
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
      if ("android.hardware.configstore@1.1::ISurfaceFlingerConfigs".equals(str)) {
        return this;
      }
      return null;
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
      switch (i) {
        case 1:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          vsyncEventPhaseOffsetNs();
          hwParcel2.writeStatus(0);
          throw null;
        case 2:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          vsyncSfEventPhaseOffsetNs();
          hwParcel2.writeStatus(0);
          throw null;
        case 3:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool useContextPriority = useContextPriority();
          hwParcel2.writeStatus(0);
          useContextPriority.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 4:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool hasWideColorDisplay = hasWideColorDisplay();
          hwParcel2.writeStatus(0);
          hasWideColorDisplay.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 5:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool hasHDRDisplay = hasHDRDisplay();
          hwParcel2.writeStatus(0);
          hasHDRDisplay.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 6:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          presentTimeOffsetFromVSyncNs();
          hwParcel2.writeStatus(0);
          throw null;
        case 7:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool useHwcForRGBtoYUV = useHwcForRGBtoYUV();
          hwParcel2.writeStatus(0);
          useHwcForRGBtoYUV.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 8:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          maxVirtualDisplaySize();
          hwParcel2.writeStatus(0);
          throw null;
        case 9:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool hasSyncFramework = hasSyncFramework();
          hwParcel2.writeStatus(0);
          hasSyncFramework.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 10:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool useVrFlinger = useVrFlinger();
          hwParcel2.writeStatus(0);
          useVrFlinger.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 11:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          maxFrameBufferAcquiredBuffers();
          hwParcel2.writeStatus(0);
          throw null;
        case 12:
          hwParcel.enforceInterface("android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
          OptionalBool startGraphicsAllocatorService = startGraphicsAllocatorService();
          hwParcel2.writeStatus(0);
          startGraphicsAllocatorService.writeToParcel(hwParcel2);
          hwParcel2.send();
          return;
        case 13:
          hwParcel.enforceInterface("android.hardware.configstore@1.1::ISurfaceFlingerConfigs");
          primaryDisplayOrientation();
          hwParcel2.writeStatus(0);
          throw null;
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
