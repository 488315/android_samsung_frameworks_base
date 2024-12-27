package android.hardware.soundtrigger.V2_3;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy$$ExternalSyntheticOutline0;
import android.hardware.soundtrigger.V2_0.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_2.ISoundTriggerHw;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.hidl.safe_union.V1_0.Monostate;
import android.os.HidlMemory;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.NativeHandle;
import android.os.RemoteException;

import com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat;
import com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda1;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ISoundTriggerHw$Proxy implements ISoundTriggerHw {
    public IHwBinder mRemote;

    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        IBase.kInterfaceName, nativeHandle, arrayList);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256131655, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
        } finally {
            hwParcel.release();
        }
    }

    public final boolean equals(Object obj) {
        return HidlSupport.interfacesEqual(this, obj);
    }

    @Override // android.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(257049926, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.readFromParcel(hwParcel);
            return debugInfo;
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256398152, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            ArrayList arrayList = new ArrayList();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer =
                    hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                byte[] bArr = new byte[32];
                readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                arrayList.add(bArr);
            }
            return arrayList;
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_2.ISoundTriggerHw
    public final int getModelState(int i) {
        HwParcel m =
                ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                        i, "android.hardware.soundtrigger@2.2::ISoundTriggerHw");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(11, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readInt32();
        } finally {
            hwParcel.release();
        }
    }

    public final void getParameter(
            int i,
            int i2,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
        hwParcel.writeInt32(i);
        hwParcel.writeInt32(i2);
        HwParcel hwParcel2 = new HwParcel();
        try {
            this.mRemote.transact(15, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            int readInt322 = hwParcel2.readInt32();
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicInteger atomicInteger2 =
                    (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicInteger2.set(readInt322);
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final void getProperties(
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(1, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            ISoundTriggerHw.Properties properties = new ISoundTriggerHw.Properties();
            properties.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L));
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicReference atomicReference =
                    (AtomicReference) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicReference.set(properties);
        } finally {
            hwParcel.release();
        }
    }

    public final void getProperties_2_3(
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.soundtrigger@2.3::ISoundTriggerHw");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(12, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            Properties properties = new Properties();
            HwBlob readBuffer = hwParcel.readBuffer(112L);
            properties.base.readEmbeddedFromParcel(hwParcel, readBuffer);
            properties.supportedModelArch = readBuffer.getString(88L);
            hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, readBuffer.handle(), 88L, false);
            properties.audioCapabilities = readBuffer.getInt32(104L);
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicReference atomicReference =
                    (AtomicReference) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicReference.set(properties);
        } finally {
            hwParcel.release();
        }
    }

    public final int hashCode() {
        return this.mRemote.hashCode();
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256067662, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readStringVector();
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256136003, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readString();
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return this.mRemote.linkToDeath(deathRecipient, j);
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final void loadPhraseSoundModel(
            ISoundTriggerHw.PhraseSoundModel phraseSoundModel,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        phraseSoundModel.writeToParcel(hwParcel);
        hwParcel.writeStrongBinder(modelCallbackWrapper);
        hwParcel.writeInt32(0);
        HwParcel hwParcel2 = new HwParcel();
        try {
            this.mRemote.transact(3, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            int readInt322 = hwParcel2.readInt32();
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicInteger atomicInteger2 =
                    (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicInteger2.set(readInt322);
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public final void loadPhraseSoundModel_2_1(
            ISoundTriggerHw.SoundModel soundModel,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
        soundModel.writeToParcel(hwParcel);
        hwParcel.writeStrongBinder(modelCallbackWrapper);
        hwParcel.writeInt32(0);
        HwParcel hwParcel2 = new HwParcel();
        try {
            this.mRemote.transact(9, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            int readInt322 = hwParcel2.readInt32();
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicInteger atomicInteger2 =
                    (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicInteger2.set(readInt322);
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final void loadSoundModel(
            ISoundTriggerHw.SoundModel soundModel,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        soundModel.writeToParcel(hwParcel);
        hwParcel.writeStrongBinder(modelCallbackWrapper);
        hwParcel.writeInt32(0);
        HwParcel hwParcel2 = new HwParcel();
        try {
            this.mRemote.transact(2, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            int readInt322 = hwParcel2.readInt32();
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicInteger atomicInteger2 =
                    (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicInteger2.set(readInt322);
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public final void loadSoundModel_2_1(
            ISoundTriggerHw.SoundModel soundModel,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.soundtrigger@2.1::ISoundTriggerHw");
        HwBlob hwBlob = new HwBlob(96);
        ((ISoundTriggerHw.SoundModel) soundModel.header).writeEmbeddedToBlob(hwBlob);
        hwBlob.putHidlMemory(56L, (HidlMemory) soundModel.data);
        m.writeBuffer(hwBlob);
        m.writeStrongBinder(modelCallbackWrapper);
        m.writeInt32(0);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(8, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            int readInt322 = hwParcel.readInt32();
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicInteger atomicInteger2 =
                    (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicInteger2.set(readInt322);
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(257120595, m, hwParcel, 1);
            m.releaseTemporaryStorage();
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256921159, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
        } finally {
            hwParcel.release();
        }
    }

    public final void queryParameter(
            int i,
            int i2,
            SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                    soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
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
            OptionalModelParameterRange optionalModelParameterRange =
                    new OptionalModelParameterRange();
            optionalModelParameterRange.hidl_d = (byte) 0;
            optionalModelParameterRange.hidl_o = new Monostate();
            optionalModelParameterRange.readFromParcel(hwParcel2);
            AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
            AtomicReference atomicReference =
                    (AtomicReference) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
            atomicInteger.set(readInt32);
            atomicReference.set(optionalModelParameterRange);
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {
        HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(256462420, m, hwParcel, 1);
            m.releaseTemporaryStorage();
        } finally {
            hwParcel.release();
        }
    }

    public final int setParameter(int i, int i2, int i3) {
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

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final int startRecognition(
            int i,
            ISoundTriggerHw.RecognitionConfig recognitionConfig,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        hwParcel.writeInt32(i);
        recognitionConfig.writeToParcel(hwParcel);
        hwParcel.writeStrongBinder(modelCallbackWrapper);
        hwParcel.writeInt32(0);
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

    @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw
    public final int startRecognition_2_1(
            int i,
            ISoundTriggerHw.SoundModel soundModel,
            SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
        hwParcel.writeInt32(i);
        soundModel.getClass();
        HwBlob hwBlob = new HwBlob(88);
        ((ISoundTriggerHw.RecognitionConfig) soundModel.header).writeEmbeddedToBlob(hwBlob);
        hwBlob.putHidlMemory(48L, (HidlMemory) soundModel.data);
        hwParcel.writeBuffer(hwBlob);
        hwParcel.writeStrongBinder(modelCallbackWrapper);
        hwParcel.writeInt32(0);
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

    public final int startRecognition_2_3(int i, RecognitionConfig recognitionConfig) {
        HwParcel m =
                ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                        i, "android.hardware.soundtrigger@2.3::ISoundTriggerHw");
        HwBlob hwBlob = new HwBlob(96);
        ISoundTriggerHw.SoundModel soundModel = recognitionConfig.base;
        ((ISoundTriggerHw.RecognitionConfig) soundModel.header).writeEmbeddedToBlob(hwBlob);
        hwBlob.putHidlMemory(48L, (HidlMemory) soundModel.data);
        hwBlob.putInt32(88L, recognitionConfig.audioCapabilities);
        m.writeBuffer(hwBlob);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(13, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readInt32();
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final int stopRecognition(int i) {
        HwParcel m =
                ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                        i, "android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(6, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readInt32();
        } finally {
            hwParcel.release();
        }
    }

    public final String toString() {
        try {
            return interfaceDescriptor() + "@Proxy";
        } catch (RemoteException unused) {
            return "[class or subclass of"
                       + " android.hardware.soundtrigger@2.3::ISoundTriggerHw]@Proxy";
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return this.mRemote.unlinkToDeath(deathRecipient);
    }

    @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
    public final int unloadSoundModel(int i) {
        HwParcel m =
                ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                        i, "android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(4, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readInt32();
        } finally {
            hwParcel.release();
        }
    }
}
