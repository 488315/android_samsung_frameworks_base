package android.hardware.soundtrigger.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.Uuid;
import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy$$ExternalSyntheticOutline0;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat;
import com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface ISoundTriggerHw extends IBase {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Phrase {
        public int id;
        public String locale;
        public int recognitionModes;
        public String text;
        public ArrayList users;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != Phrase.class) {
                return false;
            }
            Phrase phrase = (Phrase) obj;
            return this.id == phrase.id && this.recognitionModes == phrase.recognitionModes && HidlSupport.deepEquals(this.users, phrase.users) && HidlSupport.deepEquals(this.locale, phrase.locale) && HidlSupport.deepEquals(this.text, phrase.text);
        }

        public final int hashCode() {
            return Objects.hash(AudioConfig$$ExternalSyntheticOutline0.m(this.id), AudioConfig$$ExternalSyntheticOutline0.m(this.recognitionModes), Integer.valueOf(HidlSupport.deepHashCode(this.users)), Integer.valueOf(HidlSupport.deepHashCode(this.locale)), Integer.valueOf(HidlSupport.deepHashCode(this.text)));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{.id = ");
            sb.append(this.id);
            sb.append(", .recognitionModes = ");
            sb.append(this.recognitionModes);
            sb.append(", .users = ");
            sb.append(this.users);
            sb.append(", .locale = ");
            sb.append(this.locale);
            sb.append(", .text = ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.text, "}");
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt32(j, this.id);
            hwBlob.putInt32(4 + j, this.recognitionModes);
            int size = this.users.size();
            long j2 = 8 + j;
            hwBlob.putInt32(16 + j, size);
            hwBlob.putBool(20 + j, false);
            HwBlob hwBlob2 = new HwBlob(size * 4);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt32(i * 4, ((Integer) this.users.get(i)).intValue());
            }
            hwBlob.putBlob(j2, hwBlob2);
            hwBlob.putString(24 + j, this.locale);
            hwBlob.putString(j + 40, this.text);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhraseSoundModel {
        public SoundModel common;
        public ArrayList phrases;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != PhraseSoundModel.class) {
                return false;
            }
            PhraseSoundModel phraseSoundModel = (PhraseSoundModel) obj;
            return HidlSupport.deepEquals(this.common, phraseSoundModel.common) && HidlSupport.deepEquals(this.phrases, phraseSoundModel.phrases);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.common)), Integer.valueOf(HidlSupport.deepHashCode(this.phrases)));
        }

        public final String toString() {
            return "{.common = " + this.common + ", .phrases = " + this.phrases + "}";
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(72);
            this.common.writeEmbeddedToBlob(hwBlob);
            int size = this.phrases.size();
            hwBlob.putInt32(64L, size);
            hwBlob.putBool(68L, false);
            HwBlob hwBlob2 = new HwBlob(size * 56);
            for (int i = 0; i < size; i++) {
                ((Phrase) this.phrases.get(i)).writeEmbeddedToBlob(hwBlob2, i * 56);
            }
            hwBlob.putBlob(56L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Properties {
        public String implementor = new String();
        public String description = new String();
        public int version = 0;
        public final Uuid uuid = new Uuid();
        public int maxSoundModels = 0;
        public int maxKeyPhrases = 0;
        public int maxUsers = 0;
        public int recognitionModes = 0;
        public boolean captureTransition = false;
        public int maxBufferMs = 0;
        public boolean concurrentCapture = false;
        public boolean triggerInEvent = false;
        public int powerConsumptionMw = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != Properties.class) {
                return false;
            }
            Properties properties = (Properties) obj;
            return HidlSupport.deepEquals(this.implementor, properties.implementor) && HidlSupport.deepEquals(this.description, properties.description) && this.version == properties.version && HidlSupport.deepEquals(this.uuid, properties.uuid) && this.maxSoundModels == properties.maxSoundModels && this.maxKeyPhrases == properties.maxKeyPhrases && this.maxUsers == properties.maxUsers && this.recognitionModes == properties.recognitionModes && this.captureTransition == properties.captureTransition && this.maxBufferMs == properties.maxBufferMs && this.concurrentCapture == properties.concurrentCapture && this.triggerInEvent == properties.triggerInEvent && this.powerConsumptionMw == properties.powerConsumptionMw;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.implementor)), Integer.valueOf(HidlSupport.deepHashCode(this.description)), AudioConfig$$ExternalSyntheticOutline0.m(this.version), Integer.valueOf(HidlSupport.deepHashCode(this.uuid)), AudioConfig$$ExternalSyntheticOutline0.m(this.maxSoundModels), AudioConfig$$ExternalSyntheticOutline0.m(this.maxKeyPhrases), AudioConfig$$ExternalSyntheticOutline0.m(this.maxUsers), AudioConfig$$ExternalSyntheticOutline0.m(this.recognitionModes), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.captureTransition), AudioConfig$$ExternalSyntheticOutline0.m(this.maxBufferMs), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.concurrentCapture), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.triggerInEvent), AudioConfig$$ExternalSyntheticOutline0.m(this.powerConsumptionMw));
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob) {
            this.implementor = hwBlob.getString(0L);
            hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), 0L, false);
            this.description = hwBlob.getString(16L);
            hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), 16L, false);
            this.version = hwBlob.getInt32(32L);
            Uuid uuid = this.uuid;
            uuid.getClass();
            uuid.timeLow = hwBlob.getInt32(36L);
            uuid.timeMid = hwBlob.getInt16(40L);
            uuid.versionAndTimeHigh = hwBlob.getInt16(42L);
            uuid.variantAndClockSeqHigh = hwBlob.getInt16(44L);
            hwBlob.copyToInt8Array(46L, uuid.node, 6);
            this.maxSoundModels = hwBlob.getInt32(52L);
            this.maxKeyPhrases = hwBlob.getInt32(56L);
            this.maxUsers = hwBlob.getInt32(60L);
            this.recognitionModes = hwBlob.getInt32(64L);
            this.captureTransition = hwBlob.getBool(68L);
            this.maxBufferMs = hwBlob.getInt32(72L);
            this.concurrentCapture = hwBlob.getBool(76L);
            this.triggerInEvent = hwBlob.getBool(77L);
            this.powerConsumptionMw = hwBlob.getInt32(80L);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{.implementor = ");
            sb.append(this.implementor);
            sb.append(", .description = ");
            sb.append(this.description);
            sb.append(", .version = ");
            sb.append(this.version);
            sb.append(", .uuid = ");
            sb.append(this.uuid);
            sb.append(", .maxSoundModels = ");
            sb.append(this.maxSoundModels);
            sb.append(", .maxKeyPhrases = ");
            sb.append(this.maxKeyPhrases);
            sb.append(", .maxUsers = ");
            sb.append(this.maxUsers);
            sb.append(", .recognitionModes = ");
            sb.append(this.recognitionModes);
            sb.append(", .captureTransition = ");
            sb.append(this.captureTransition);
            sb.append(", .maxBufferMs = ");
            sb.append(this.maxBufferMs);
            sb.append(", .concurrentCapture = ");
            sb.append(this.concurrentCapture);
            sb.append(", .triggerInEvent = ");
            sb.append(this.triggerInEvent);
            sb.append(", .powerConsumptionMw = ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.powerConsumptionMw, sb, "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy implements ISoundTriggerHw {
        public IHwBinder mRemote;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName, nativeHandle, arrayList);
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
                HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
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

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public final void getProperties(SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                Properties properties = new Properties();
                properties.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L));
                AtomicInteger atomicInteger = soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$0;
                AtomicReference atomicReference = (AtomicReference) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
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
        public final void loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper, SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
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
                AtomicInteger atomicInteger2 = (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
                atomicInteger.set(readInt32);
                atomicInteger2.set(readInt322);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public final void loadSoundModel(SoundModel soundModel, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper, SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1) {
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
                AtomicInteger atomicInteger2 = (AtomicInteger) soundTriggerHw2Compat$$ExternalSyntheticLambda1.f$1;
                atomicInteger.set(readInt32);
                atomicInteger2.set(readInt322);
            } finally {
                hwParcel2.release();
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

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public final int startRecognition(int i, RecognitionConfig recognitionConfig, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper) {
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

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public final int stopRecognition(int i) {
            HwParcel m = ITunerSession$Proxy$$ExternalSyntheticOutline0.m(i, "android.hardware.soundtrigger@2.0::ISoundTriggerHw");
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
                return "[class or subclass of android.hardware.soundtrigger@2.0::ISoundTriggerHw]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw
        public final int unloadSoundModel(int i) {
            HwParcel m = ITunerSession$Proxy$$ExternalSyntheticOutline0.m(i, "android.hardware.soundtrigger@2.0::ISoundTriggerHw");
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RecognitionConfig {
        public int captureDevice;
        public int captureHandle;
        public boolean captureRequested;
        public ArrayList data;
        public ArrayList phrases;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != RecognitionConfig.class) {
                return false;
            }
            RecognitionConfig recognitionConfig = (RecognitionConfig) obj;
            return this.captureHandle == recognitionConfig.captureHandle && this.captureDevice == recognitionConfig.captureDevice && this.captureRequested == recognitionConfig.captureRequested && HidlSupport.deepEquals(this.phrases, recognitionConfig.phrases) && HidlSupport.deepEquals(this.data, recognitionConfig.data);
        }

        public final int hashCode() {
            return Objects.hash(AudioConfig$$ExternalSyntheticOutline0.m(this.captureHandle), AudioConfig$$ExternalSyntheticOutline0.m(this.captureDevice), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.captureRequested), Integer.valueOf(HidlSupport.deepHashCode(this.phrases)), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{.captureHandle = ");
            sb.append(this.captureHandle);
            sb.append(", .captureDevice = ");
            int i = this.captureDevice;
            sb.append(i == 0 ? "NONE" : i == Integer.MIN_VALUE ? "BIT_IN" : i == 1073741824 ? "BIT_DEFAULT" : i == 1 ? "OUT_EARPIECE" : i == 2 ? "OUT_SPEAKER" : i == 4 ? "OUT_WIRED_HEADSET" : i == 8 ? "OUT_WIRED_HEADPHONE" : i == 16 ? "OUT_BLUETOOTH_SCO" : i == 32 ? "OUT_BLUETOOTH_SCO_HEADSET" : i == 64 ? "OUT_BLUETOOTH_SCO_CARKIT" : i == 128 ? "OUT_BLUETOOTH_A2DP" : i == 256 ? "OUT_BLUETOOTH_A2DP_HEADPHONES" : i == 512 ? "OUT_BLUETOOTH_A2DP_SPEAKER" : i == 1024 ? "OUT_AUX_DIGITAL" : i == 1024 ? "OUT_HDMI" : i == 2048 ? "OUT_ANLG_DOCK_HEADSET" : i == 4096 ? "OUT_DGTL_DOCK_HEADSET" : i == 8192 ? "OUT_USB_ACCESSORY" : i == 16384 ? "OUT_USB_DEVICE" : i == 32768 ? "OUT_REMOTE_SUBMIX" : i == 65536 ? "OUT_TELEPHONY_TX" : i == 131072 ? "OUT_LINE" : i == 262144 ? "OUT_HDMI_ARC" : i == 524288 ? "OUT_SPDIF" : i == 1048576 ? "OUT_FM" : i == 2097152 ? "OUT_AUX_LINE" : i == 4194304 ? "OUT_SPEAKER_SAFE" : i == 8388608 ? "OUT_IP" : i == 16777216 ? "OUT_BUS" : i == 33554432 ? "OUT_PROXY" : i == 67108864 ? "OUT_USB_HEADSET" : i == 1073741824 ? "OUT_DEFAULT" : i == 1207959551 ? "OUT_ALL" : i == 896 ? "OUT_ALL_A2DP" : i == 112 ? "OUT_ALL_SCO" : i == 67133440 ? "OUT_ALL_USB" : i == -2147483647 ? "IN_COMMUNICATION" : i == -2147483646 ? "IN_AMBIENT" : i == -2147483644 ? "IN_BUILTIN_MIC" : i == -2147483640 ? "IN_BLUETOOTH_SCO_HEADSET" : i == -2147483632 ? "IN_WIRED_HEADSET" : i == -2147483616 ? "IN_AUX_DIGITAL" : i == -2147483616 ? "IN_HDMI" : i == -2147483584 ? "IN_VOICE_CALL" : i == -2147483584 ? "IN_TELEPHONY_RX" : i == -2147483520 ? "IN_BACK_MIC" : i == -2147483392 ? "IN_REMOTE_SUBMIX" : i == -2147483136 ? "IN_ANLG_DOCK_HEADSET" : i == -2147482624 ? "IN_DGTL_DOCK_HEADSET" : i == -2147481600 ? "IN_USB_ACCESSORY" : i == -2147479552 ? "IN_USB_DEVICE" : i == -2147475456 ? "IN_FM_TUNER" : i == -2147467264 ? "IN_TV_TUNER" : i == -2147450880 ? "IN_LINE" : i == -2147418112 ? "IN_SPDIF" : i == -2147352576 ? "IN_BLUETOOTH_A2DP" : i == -2147221504 ? "IN_LOOPBACK" : i == -2146959360 ? "IN_IP" : i == -2146435072 ? "IN_BUS" : i == -2130706432 ? "IN_PROXY" : i == -2113929216 ? "IN_USB_HEADSET" : i == -1073741824 ? "IN_DEFAULT" : i == -1021313025 ? "IN_ALL" : i == -2147483640 ? "IN_ALL_SCO" : i == -2113923072 ? "IN_ALL_USB" : AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("0x"), i));
            sb.append(", .captureRequested = ");
            sb.append(this.captureRequested);
            sb.append(", .phrases = ");
            sb.append(this.phrases);
            sb.append(", .data = ");
            sb.append(this.data);
            sb.append("}");
            return sb.toString();
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob) {
            hwBlob.putInt32(0L, this.captureHandle);
            long j = 4;
            hwBlob.putInt32(4L, this.captureDevice);
            long j2 = 8;
            hwBlob.putBool(8L, this.captureRequested);
            int size = this.phrases.size();
            hwBlob.putInt32(24L, size);
            hwBlob.putBool(28L, false);
            HwBlob hwBlob2 = new HwBlob(size * 32);
            int i = 0;
            while (i < size) {
                PhraseRecognitionExtra phraseRecognitionExtra = (PhraseRecognitionExtra) this.phrases.get(i);
                long j3 = i * 32;
                hwBlob2.putInt32(j3, phraseRecognitionExtra.id);
                hwBlob2.putInt32(j3 + j, phraseRecognitionExtra.recognitionModes);
                hwBlob2.putInt32(j3 + j2, phraseRecognitionExtra.confidenceLevel);
                int size2 = phraseRecognitionExtra.levels.size();
                long j4 = 16 + j3;
                hwBlob2.putInt32(j3 + 24, size2);
                hwBlob2.putBool(j3 + 28, false);
                HwBlob hwBlob3 = new HwBlob(size2 * 8);
                for (int i2 = 0; i2 < size2; i2++) {
                    ConfidenceLevel confidenceLevel = (ConfidenceLevel) phraseRecognitionExtra.levels.get(i2);
                    long j5 = i2 * 8;
                    hwBlob3.putInt32(j5, confidenceLevel.userId);
                    hwBlob3.putInt32(j5 + 4, confidenceLevel.levelPercent);
                }
                hwBlob2.putBlob(j4, hwBlob3);
                i++;
                j = 4;
                j2 = 8;
            }
            hwBlob.putBlob(16L, hwBlob2);
            int size3 = this.data.size();
            hwBlob.putInt32(40L, size3);
            hwBlob.putBool(44L, false);
            HwBlob hwBlob4 = new HwBlob(size3);
            for (int i3 = 0; i3 < size3; i3++) {
                hwBlob4.putInt8(i3, ((Byte) this.data.get(i3)).byteValue());
            }
            hwBlob.putBlob(32L, hwBlob4);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(48);
            writeEmbeddedToBlob(hwBlob);
            hwParcel.writeBuffer(hwBlob);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundModel {
        public int type = 0;
        public Uuid uuid = new Uuid();
        public Uuid vendorUuid = new Uuid();
        public ArrayList data = new ArrayList();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != SoundModel.class) {
                return false;
            }
            SoundModel soundModel = (SoundModel) obj;
            return this.type == soundModel.type && HidlSupport.deepEquals(this.uuid, soundModel.uuid) && HidlSupport.deepEquals(this.vendorUuid, soundModel.vendorUuid) && HidlSupport.deepEquals(this.data, soundModel.data);
        }

        public final int hashCode() {
            return Objects.hash(AudioConfig$$ExternalSyntheticOutline0.m(this.type), Integer.valueOf(HidlSupport.deepHashCode(this.uuid)), Integer.valueOf(HidlSupport.deepHashCode(this.vendorUuid)), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
        }

        public final String toString() {
            return "{.type = " + SoundModelType.toString(this.type) + ", .uuid = " + this.uuid + ", .vendorUuid = " + this.vendorUuid + ", .data = " + this.data + "}";
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob) {
            hwBlob.putInt32(0L, this.type);
            this.uuid.writeEmbeddedToBlob(hwBlob, 4L);
            this.vendorUuid.writeEmbeddedToBlob(hwBlob, 20L);
            int size = this.data.size();
            hwBlob.putInt32(48L, size);
            hwBlob.putBool(52L, false);
            HwBlob hwBlob2 = new HwBlob(size);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt8(i, ((Byte) this.data.get(i)).byteValue());
            }
            hwBlob.putBlob(40L, hwBlob2);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(56);
            writeEmbeddedToBlob(hwBlob);
            hwParcel.writeBuffer(hwBlob);
        }
    }

    static ISoundTriggerHw asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHw");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerHw)) {
            return (ISoundTriggerHw) queryLocalInterface;
        }
        Proxy proxy = new Proxy();
        proxy.mRemote = iHwBinder;
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("android.hardware.soundtrigger@2.0::ISoundTriggerHw")) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    void getProperties(SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1);

    void loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper, SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1);

    void loadSoundModel(SoundModel soundModel, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper, SoundTriggerHw2Compat$$ExternalSyntheticLambda1 soundTriggerHw2Compat$$ExternalSyntheticLambda1);

    int startRecognition(int i, RecognitionConfig recognitionConfig, SoundTriggerHw2Compat.ModelCallbackWrapper modelCallbackWrapper);

    int stopRecognition(int i);

    int unloadSoundModel(int i);
}
