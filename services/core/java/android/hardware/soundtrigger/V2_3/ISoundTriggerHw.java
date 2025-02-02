package android.hardware.soundtrigger.V2_3;

import android.hardware.soundtrigger.V2_0.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback;
import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;
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
public interface ISoundTriggerHw extends android.hardware.soundtrigger.V2_2.ISoundTriggerHw {

  public interface getParameterCallback {
    void onValues(int i, int i2);
  }

  public interface getProperties_2_3Callback {
    void onValues(int i, Properties properties);
  }

  public interface queryParameterCallback {
    void onValues(int i, OptionalModelParameterRange optionalModelParameterRange);
  }

  void getParameter(int i, int i2, getParameterCallback getparametercallback);

  void getProperties_2_3(getProperties_2_3Callback getproperties_2_3callback);

  @Override // android.hardware.soundtrigger.V2_2.ISoundTriggerHw,
            // android.hardware.soundtrigger.V2_1.ISoundTriggerHw,
            // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
  ArrayList interfaceChain();

  void queryParameter(int i, int i2, queryParameterCallback queryparametercallback);

  int setParameter(int i, int i2, int i3);

  int startRecognition_2_3(int i, RecognitionConfig recognitionConfig);

  static ISoundTriggerHw asInterface(IHwBinder iHwBinder) {
    if (iHwBinder == null) {
      return null;
    }
    IHwInterface queryLocalInterface =
        iHwBinder.queryLocalInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
    if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerHw)) {
      return (ISoundTriggerHw) queryLocalInterface;
    }
    Proxy proxy = new Proxy(iHwBinder);
    try {
      Iterator it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        if (((String) it.next()).equals("android.hardware.soundtrigger@2.3::ISoundTriggerHw")) {
          return proxy;
        }
      }
    } catch (RemoteException unused) {
    }
    return null;
  }

  public final class Proxy implements ISoundTriggerHw {
    public IHwBinder mRemote;

    public Proxy(IHwBinder iHwBinder) {
      Objects.requireNonNull(iHwBinder);
      this.mRemote = iHwBinder;
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException unused) {
        return "[class or subclass of android.hardware.soundtrigger@2.3::ISoundTriggerHw]@Proxy";
      }
    }

    public final boolean equals(Object obj) {
      return HidlSupport.interfacesEqual(this, obj);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public void getProperties(ISoundTriggerHw.getPropertiesCallback getpropertiescallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(1, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        ISoundTriggerHw.Properties properties = new ISoundTriggerHw.Properties();
        properties.readFromParcel(hwParcel2);
        getpropertiescallback.onValues(readInt32, properties);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public void loadSoundModel(
        ISoundTriggerHw.SoundModel soundModel,
        ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i,
        ISoundTriggerHw.loadSoundModelCallback loadsoundmodelcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      soundModel.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(2, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        loadsoundmodelcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public void loadPhraseSoundModel(
        ISoundTriggerHw.PhraseSoundModel phraseSoundModel,
        ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i,
        ISoundTriggerHw.loadPhraseSoundModelCallback loadphrasesoundmodelcallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      phraseSoundModel.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(3, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        loadphrasesoundmodelcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public int unloadSoundModel(int i) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      hwParcel.writeInt32(i);
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

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public int startRecognition(
        int i,
        ISoundTriggerHw.RecognitionConfig recognitionConfig,
        ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i2) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      recognitionConfig.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i2);
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

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public int stopRecognition(int i) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
      hwParcel.writeInt32(i);
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

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public void loadSoundModel_2_1(
        ISoundTriggerHw.SoundModel soundModel,
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i,
        ISoundTriggerHw.loadSoundModel_2_1Callback loadsoundmodel_2_1callback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
      soundModel.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(8, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        loadsoundmodel_2_1callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public void loadPhraseSoundModel_2_1(
        ISoundTriggerHw.PhraseSoundModel phraseSoundModel,
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i,
        ISoundTriggerHw.loadPhraseSoundModel_2_1Callback loadphrasesoundmodel_2_1callback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
      phraseSoundModel.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(9, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        loadphrasesoundmodel_2_1callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public int startRecognition_2_1(
        int i,
        ISoundTriggerHw.RecognitionConfig recognitionConfig,
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback iSoundTriggerHwCallback,
        int i2) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      recognitionConfig.writeToParcel(hwParcel);
      hwParcel.writeStrongBinder(
          iSoundTriggerHwCallback == null ? null : iSoundTriggerHwCallback.asBinder());
      hwParcel.writeInt32(i2);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(10, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_2.ISoundTriggerHw
    public int getModelState(int i) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.2::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(11, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw
    public void getProperties_2_3(getProperties_2_3Callback getproperties_2_3callback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(12, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        Properties properties = new Properties();
        properties.readFromParcel(hwParcel2);
        getproperties_2_3callback.onValues(readInt32, properties);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw
    public int startRecognition_2_3(int i, RecognitionConfig recognitionConfig) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      recognitionConfig.writeToParcel(hwParcel);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(13, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw
    public int setParameter(int i, int i2, int i3) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      hwParcel.writeInt32(i2);
      hwParcel.writeInt32(i3);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(14, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        return hwParcel2.readInt32();
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw
    public void getParameter(int i, int i2, getParameterCallback getparametercallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      hwParcel.writeInt32(i2);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(15, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        getparametercallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw
    public void queryParameter(int i, int i2, queryParameterCallback queryparametercallback) {
      HwParcel hwParcel = new HwParcel();
      hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
      hwParcel.writeInt32(i);
      hwParcel.writeInt32(i2);
      HwParcel hwParcel2 = new HwParcel();
      try {
        this.mRemote.transact(16, hwParcel, hwParcel2, 0);
        hwParcel2.verifySuccess();
        hwParcel.releaseTemporaryStorage();
        int readInt32 = hwParcel2.readInt32();
        OptionalModelParameterRange optionalModelParameterRange = new OptionalModelParameterRange();
        optionalModelParameterRange.readFromParcel(hwParcel2);
        queryparametercallback.onValues(readInt32, optionalModelParameterRange);
      } finally {
        hwParcel2.release();
      }
    }

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_2.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_1.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
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

  public abstract class Stub extends HwBinder implements ISoundTriggerHw {
    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    public IHwBinder asBinder() {
      return this;
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    public void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return "android.hardware.soundtrigger@2.3::ISoundTriggerHw";
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

    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_2.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_1.ISoundTriggerHw,
              // android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
      return new ArrayList(
          Arrays.asList(
              "android.hardware.soundtrigger@2.3::ISoundTriggerHw",
              "android.hardware.soundtrigger@2.2::ISoundTriggerHw",
              "android.hardware.soundtrigger@2.1::ISoundTriggerHw",
              "android.hardware.soundtrigger@2.0::ISoundTriggerHw",
              IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
      return new ArrayList(
          Arrays.asList(
              new byte[] {
                -77,
                Byte.MAX_VALUE,
                120,
                -29,
                -3,
                -57,
                -102,
                -8,
                -77,
                42,
                84,
                91,
                43,
                66,
                111,
                31,
                -47,
                53,
                91,
                53,
                -99,
                -98,
                120,
                53,
                -13,
                -65,
                30,
                -48,
                -86,
                69,
                24,
                -40
              },
              new byte[] {
                -50, 75, -104, 33, 25, 89, 68, -109, 97, 20, 109, 75, 30, 85, 84, -36, -124, 28,
                -21, 77, 69, 119, 21, 77, 123, 47, -74, -47, -21, 80, 79, 118
              },
              new byte[] {
                -76, -11, 7, -76, -36, -101, 92, -43, -16, -28, 68, 89, 38, -84, -73, -39, 69, 37,
                -82, 96, -36, 48, 123, 57, 81, 20, 34, -125, 99, 34, 7, -74
              },
              new byte[] {
                91, -17, -64, 25, -53, -23, 73, 83, 102, 30, 44, -37, -107, -29, -49, 100, -11, -27,
                101, -62, -108, 3, -31, -62, -38, -20, -62, -66, 68, -32, -91, 92
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
      if ("android.hardware.soundtrigger@2.3::ISoundTriggerHw".equals(str)) {
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
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          getProperties(
              new ISoundTriggerHw
                  .getPropertiesCallback() { // from class:
                                             // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.1
                @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback
                public void onValues(int i3, ISoundTriggerHw.Properties properties) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  properties.writeToParcel(hwParcel2);
                  hwParcel2.send();
                }
              });
          return;
        case 2:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          ISoundTriggerHw.SoundModel soundModel = new ISoundTriggerHw.SoundModel();
          soundModel.readFromParcel(hwParcel);
          loadSoundModel(
              soundModel,
              ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()),
              hwParcel.readInt32(),
              new ISoundTriggerHw
                  .loadSoundModelCallback() { // from class:
                                              // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.2
                @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 3:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          ISoundTriggerHw.PhraseSoundModel phraseSoundModel =
              new ISoundTriggerHw.PhraseSoundModel();
          phraseSoundModel.readFromParcel(hwParcel);
          loadPhraseSoundModel(
              phraseSoundModel,
              ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()),
              hwParcel.readInt32(),
              new ISoundTriggerHw
                  .loadPhraseSoundModelCallback() { // from class:
                                                    // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.3
                @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 4:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          int unloadSoundModel = unloadSoundModel(hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(unloadSoundModel);
          hwParcel2.send();
          return;
        case 5:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          int readInt32 = hwParcel.readInt32();
          ISoundTriggerHw.RecognitionConfig recognitionConfig =
              new ISoundTriggerHw.RecognitionConfig();
          recognitionConfig.readFromParcel(hwParcel);
          int startRecognition =
              startRecognition(
                  readInt32,
                  recognitionConfig,
                  ISoundTriggerHwCallback.asInterface(hwParcel.readStrongBinder()),
                  hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(startRecognition);
          hwParcel2.send();
          return;
        case 6:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          int stopRecognition = stopRecognition(hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(stopRecognition);
          hwParcel2.send();
          return;
        case 7:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
          int stopAllRecognitions = stopAllRecognitions();
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(stopAllRecognitions);
          hwParcel2.send();
          return;
        case 8:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
          ISoundTriggerHw.SoundModel soundModel2 = new ISoundTriggerHw.SoundModel();
          soundModel2.readFromParcel(hwParcel);
          loadSoundModel_2_1(
              soundModel2,
              android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(
                  hwParcel.readStrongBinder()),
              hwParcel.readInt32(),
              new ISoundTriggerHw
                  .loadSoundModel_2_1Callback() { // from class:
                                                  // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.4
                @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 9:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
          ISoundTriggerHw.PhraseSoundModel phraseSoundModel2 =
              new ISoundTriggerHw.PhraseSoundModel();
          phraseSoundModel2.readFromParcel(hwParcel);
          loadPhraseSoundModel_2_1(
              phraseSoundModel2,
              android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(
                  hwParcel.readStrongBinder()),
              hwParcel.readInt32(),
              new ISoundTriggerHw
                  .loadPhraseSoundModel_2_1Callback() { // from class:
                                                        // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.5
                @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 10:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
          int readInt322 = hwParcel.readInt32();
          ISoundTriggerHw.RecognitionConfig recognitionConfig2 =
              new ISoundTriggerHw.RecognitionConfig();
          recognitionConfig2.readFromParcel(hwParcel);
          int startRecognition_2_1 =
              startRecognition_2_1(
                  readInt322,
                  recognitionConfig2,
                  android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.asInterface(
                      hwParcel.readStrongBinder()),
                  hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(startRecognition_2_1);
          hwParcel2.send();
          return;
        case 11:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.2::ISoundTriggerHw");
          int modelState = getModelState(hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(modelState);
          hwParcel2.send();
          return;
        case 12:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
          getProperties_2_3(
              new getProperties_2_3Callback() { // from class:
                                                // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.6
                @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getProperties_2_3Callback
                public void onValues(int i3, Properties properties) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  properties.writeToParcel(hwParcel2);
                  hwParcel2.send();
                }
              });
          return;
        case 13:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
          int readInt323 = hwParcel.readInt32();
          RecognitionConfig recognitionConfig3 = new RecognitionConfig();
          recognitionConfig3.readFromParcel(hwParcel);
          int startRecognition_2_3 = startRecognition_2_3(readInt323, recognitionConfig3);
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(startRecognition_2_3);
          hwParcel2.send();
          return;
        case 14:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
          int parameter =
              setParameter(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
          hwParcel2.writeStatus(0);
          hwParcel2.writeInt32(parameter);
          hwParcel2.send();
          return;
        case 15:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
          getParameter(
              hwParcel.readInt32(),
              hwParcel.readInt32(),
              new getParameterCallback() { // from class:
                                           // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.7
                @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getParameterCallback
                public void onValues(int i3, int i4) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  hwParcel2.writeInt32(i4);
                  hwParcel2.send();
                }
              });
          return;
        case 16:
          hwParcel.enforceInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
          queryParameter(
              hwParcel.readInt32(),
              hwParcel.readInt32(),
              new queryParameterCallback() { // from class:
                                             // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.Stub.8
                @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.queryParameterCallback
                public void onValues(
                    int i3, OptionalModelParameterRange optionalModelParameterRange) {
                  hwParcel2.writeStatus(0);
                  hwParcel2.writeInt32(i3);
                  optionalModelParameterRange.writeToParcel(hwParcel2);
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
