package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger.V2_0.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback$RecognitionEvent;
import android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra;
import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_2.ISoundTriggerHw;
import android.hardware.soundtrigger.V2_3.ISoundTriggerHw$Proxy;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.media.soundtrigger.ConfidenceLevel;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.HidlMemory;
import android.os.HidlMemoryUtil;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.soundtrigger_middleware.ISoundTriggerHal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SoundTriggerHw2Compat implements ISoundTriggerHal {
    public final IHwBinder mBinder;
    public final Properties mProperties;
    public final Runnable mRebootRunnable;
    public final ISoundTriggerHw mUnderlying_2_0;
    public final android.hardware.soundtrigger.V2_1.ISoundTriggerHw mUnderlying_2_1;
    public final android.hardware.soundtrigger.V2_2.ISoundTriggerHw mUnderlying_2_2;
    public final ISoundTriggerHw$Proxy mUnderlying_2_3;
    public final ConcurrentMap mModelCallbacks = new ConcurrentHashMap();
    public final Map mDeathRecipientMap = new HashMap();

    public final class ModelCallbackWrapper extends HwBinder implements IBase {
        public final ISoundTriggerHal.ModelCallback mDelegate;

        public ModelCallbackWrapper(ISoundTriggerHal.ModelCallback modelCallback) {
            Objects.requireNonNull(modelCallback);
            this.mDelegate = modelCallback;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
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
        public final ArrayList getHashChain() {
            return new ArrayList(Arrays.asList(new byte[]{-24, -56, 108, 105, -60, 56, -38, -115, 21, 73, -123, 108, 27, -77, -30, -47, -72, -38, 82, 114, 47, -126, 53, -1, 73, -93, 15, 44, -50, -111, 116, 44}, new byte[]{26, 110, 43, -46, -119, -14, 41, 49, -59, 38, -78, 25, 22, -111, 15, 29, 76, 67, 107, 122, -53, -107, 86, -28, 36, 61, -28, -50, -114, 108, -62, -28}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            return new ArrayList(Arrays.asList("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback", "android.hardware.soundtrigger@2.0::ISoundTriggerHwCallback", IBase.kInterfaceName));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return "android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHwCallback");
                    ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent = new ISoundTriggerHwCallback$RecognitionEvent();
                    iSoundTriggerHwCallback$RecognitionEvent.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L));
                    hwParcel.readInt32();
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent(0);
                    iSoundTriggerHwCallback$RecognitionEvent2.header = iSoundTriggerHwCallback$RecognitionEvent;
                    iSoundTriggerHwCallback$RecognitionEvent2.data = HidlMemoryUtil.byteListToHidlMemory(iSoundTriggerHwCallback$RecognitionEvent.data, "SoundTrigger RecognitionEvent");
                    ((ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent2.header).data = new ArrayList();
                    RecognitionEventSys recognitionEventSys = new RecognitionEventSys();
                    recognitionEventSys.recognitionEvent = ConversionUtil.hidl2aidlRecognitionEvent(iSoundTriggerHwCallback$RecognitionEvent2);
                    recognitionEventSys.halEventReceivedMillis = SystemClock.elapsedRealtime();
                    this.mDelegate.recognitionCallback(((ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent2.header).model, recognitionEventSys);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHwCallback");
                    ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent3 = new ISoundTriggerHwCallback$RecognitionEvent();
                    ArrayList arrayList = new ArrayList();
                    HwBlob readBuffer = hwParcel.readBuffer(136L);
                    iSoundTriggerHwCallback$RecognitionEvent3.readEmbeddedFromParcel(hwParcel, readBuffer);
                    int int32 = readBuffer.getInt32(128L);
                    HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 120L, true);
                    arrayList.clear();
                    for (int i3 = 0; i3 < int32; i3++) {
                        PhraseRecognitionExtra phraseRecognitionExtra = new PhraseRecognitionExtra();
                        phraseRecognitionExtra.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i3 * 32);
                        arrayList.add(phraseRecognitionExtra);
                    }
                    hwParcel.readInt32();
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent4 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent(1);
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent5 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent(0);
                    iSoundTriggerHwCallback$RecognitionEvent5.header = iSoundTriggerHwCallback$RecognitionEvent3;
                    iSoundTriggerHwCallback$RecognitionEvent5.data = HidlMemoryUtil.byteListToHidlMemory(iSoundTriggerHwCallback$RecognitionEvent3.data, "SoundTrigger RecognitionEvent");
                    ((ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent5.header).data = new ArrayList();
                    iSoundTriggerHwCallback$RecognitionEvent4.header = iSoundTriggerHwCallback$RecognitionEvent5;
                    iSoundTriggerHwCallback$RecognitionEvent4.data = arrayList;
                    phraseRecognitionCallback_2_1(iSoundTriggerHwCallback$RecognitionEvent4);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.0::ISoundTriggerHwCallback");
                    ArrayList arrayList2 = new ArrayList();
                    HwBlob readBuffer2 = hwParcel.readBuffer(24L);
                    readBuffer2.getInt32(0L);
                    readBuffer2.getInt32(4L);
                    int int322 = readBuffer2.getInt32(16L);
                    HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322, readBuffer2.handle(), 8L, true);
                    arrayList2.clear();
                    for (int i4 = 0; i4 < int322; i4++) {
                        arrayList2.add(Byte.valueOf(readEmbeddedBuffer2.getInt8(i4)));
                    }
                    hwParcel.readInt32();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback");
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent6 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent(0);
                    iSoundTriggerHwCallback$RecognitionEvent6.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(160L));
                    hwParcel.readInt32();
                    RecognitionEventSys recognitionEventSys2 = new RecognitionEventSys();
                    recognitionEventSys2.recognitionEvent = ConversionUtil.hidl2aidlRecognitionEvent(iSoundTriggerHwCallback$RecognitionEvent6);
                    recognitionEventSys2.halEventReceivedMillis = SystemClock.elapsedRealtime();
                    this.mDelegate.recognitionCallback(((ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent6.header).model, recognitionEventSys2);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback");
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent7 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent(1);
                    HwBlob readBuffer3 = hwParcel.readBuffer(176L);
                    ((android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent7.header).readEmbeddedFromParcel(hwParcel, readBuffer3);
                    int int323 = readBuffer3.getInt32(168L);
                    HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 32, readBuffer3.handle(), 160L, true);
                    ((ArrayList) iSoundTriggerHwCallback$RecognitionEvent7.data).clear();
                    for (int i5 = 0; i5 < int323; i5++) {
                        PhraseRecognitionExtra phraseRecognitionExtra2 = new PhraseRecognitionExtra();
                        phraseRecognitionExtra2.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i5 * 32);
                        ((ArrayList) iSoundTriggerHwCallback$RecognitionEvent7.data).add(phraseRecognitionExtra2);
                    }
                    hwParcel.readInt32();
                    phraseRecognitionCallback_2_1(iSoundTriggerHwCallback$RecognitionEvent7);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback");
                    ArrayList arrayList3 = new ArrayList();
                    HwBlob readBuffer4 = hwParcel.readBuffer(64L);
                    readBuffer4.getInt32(0L);
                    readBuffer4.getInt32(4L);
                    int int324 = readBuffer4.getInt32(16L);
                    HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324, readBuffer4.handle(), 8L, true);
                    arrayList3.clear();
                    for (int i6 = 0; i6 < int324; i6++) {
                        arrayList3.add(Byte.valueOf(readEmbeddedBuffer4.getInt8(i6)));
                    }
                    try {
                        hwParcel.readEmbeddedHidlMemory(readBuffer4.getFieldHandle(24L), readBuffer4.handle(), 24L).dup();
                        hwParcel.readInt32();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                            hwParcel.readNativeHandle();
                            hwParcel.readStringVector();
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback");
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
                            for (int i7 = 0; i7 < size; i7++) {
                                long j = i7 * 32;
                                byte[] bArr = (byte[]) hashChain.get(i7);
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
                            return;
                        case 256921159:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
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
                            HwBinder.enableInstrumentation();
                            return;
                        default:
                            return;
                    }
            }
        }

        public final void phraseRecognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent iSoundTriggerHwCallback$RecognitionEvent) {
            PhraseRecognitionEventSys phraseRecognitionEventSys = new PhraseRecognitionEventSys();
            PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
            phraseRecognitionEvent.common = ConversionUtil.hidl2aidlRecognitionEvent((android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent.header);
            phraseRecognitionEvent.phraseExtras = new android.media.soundtrigger.PhraseRecognitionExtra[((ArrayList) iSoundTriggerHwCallback$RecognitionEvent.data).size()];
            for (int i = 0; i < ((ArrayList) iSoundTriggerHwCallback$RecognitionEvent.data).size(); i++) {
                android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr = phraseRecognitionEvent.phraseExtras;
                PhraseRecognitionExtra phraseRecognitionExtra = (PhraseRecognitionExtra) ((ArrayList) iSoundTriggerHwCallback$RecognitionEvent.data).get(i);
                android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra2 = new android.media.soundtrigger.PhraseRecognitionExtra();
                phraseRecognitionExtra2.id = phraseRecognitionExtra.id;
                phraseRecognitionExtra2.recognitionModes = ConversionUtil.hidl2aidlRecognitionModes(phraseRecognitionExtra.recognitionModes);
                phraseRecognitionExtra2.confidenceLevel = phraseRecognitionExtra.confidenceLevel;
                phraseRecognitionExtra2.levels = new ConfidenceLevel[phraseRecognitionExtra.levels.size()];
                for (int i2 = 0; i2 < phraseRecognitionExtra.levels.size(); i2++) {
                    ConfidenceLevel[] confidenceLevelArr = phraseRecognitionExtra2.levels;
                    android.hardware.soundtrigger.V2_0.ConfidenceLevel confidenceLevel = (android.hardware.soundtrigger.V2_0.ConfidenceLevel) phraseRecognitionExtra.levels.get(i2);
                    ConfidenceLevel confidenceLevel2 = new ConfidenceLevel();
                    confidenceLevel2.userId = confidenceLevel.userId;
                    confidenceLevel2.levelPercent = confidenceLevel.levelPercent;
                    confidenceLevelArr[i2] = confidenceLevel2;
                }
                phraseRecognitionExtraArr[i] = phraseRecognitionExtra2;
            }
            phraseRecognitionEventSys.phraseRecognitionEvent = phraseRecognitionEvent;
            phraseRecognitionEventSys.halEventReceivedMillis = SystemClock.elapsedRealtime();
            this.mDelegate.phraseRecognitionCallback(((ISoundTriggerHwCallback$RecognitionEvent) ((android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback$RecognitionEvent) iSoundTriggerHwCallback$RecognitionEvent.header).header).model, phraseRecognitionEventSys);
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        public final IHwInterface queryLocalInterface(String str) {
            if ("android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback".equals(str)) {
                return this;
            }
            return null;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        public final String toString() {
            return "android.hardware.soundtrigger@2.1::ISoundTriggerHwCallback@Stub";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }
    }

    class NotSupported extends Exception {
        public NotSupported(String str) {
            super(str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [android.hardware.soundtrigger.V2_1.ISoundTriggerHw$Proxy, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13, types: [android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hardware.soundtrigger.V2_1.ISoundTriggerHw] */
    /* JADX WARN: Type inference failed for: r0v14, types: [android.hardware.soundtrigger.V2_1.ISoundTriggerHw] */
    /* JADX WARN: Type inference failed for: r0v15, types: [android.hardware.soundtrigger.V2_2.ISoundTriggerHw] */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.hardware.soundtrigger.V2_2.ISoundTriggerHw$Proxy, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.hardware.soundtrigger.V2_0.ISoundTriggerHw, android.hardware.soundtrigger.V2_1.ISoundTriggerHw, android.hardware.soundtrigger.V2_2.ISoundTriggerHw] */
    public SoundTriggerHw2Compat(IHwBinder iHwBinder, Runnable runnable) {
        ISoundTriggerHw$Proxy iSoundTriggerHw$Proxy;
        ?? proxy;
        ?? proxy2;
        Properties properties_2_0;
        this.mRebootRunnable = runnable;
        Objects.requireNonNull(iHwBinder);
        this.mBinder = iHwBinder;
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface("android.hardware.soundtrigger@2.3::ISoundTriggerHw");
        if (queryLocalInterface == null || !(queryLocalInterface instanceof ISoundTriggerHw$Proxy)) {
            iSoundTriggerHw$Proxy = new ISoundTriggerHw$Proxy();
            iSoundTriggerHw$Proxy.mRemote = iHwBinder;
            try {
                Iterator it = iSoundTriggerHw$Proxy.interfaceChain().iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals("android.hardware.soundtrigger@2.3::ISoundTriggerHw")) {
                        break;
                    }
                }
            } catch (RemoteException unused) {
            }
            iSoundTriggerHw$Proxy = null;
        } else {
            iSoundTriggerHw$Proxy = (ISoundTriggerHw$Proxy) queryLocalInterface;
        }
        if (iSoundTriggerHw$Proxy != null) {
            this.mUnderlying_2_3 = iSoundTriggerHw$Proxy;
            this.mUnderlying_2_2 = iSoundTriggerHw$Proxy;
            this.mUnderlying_2_1 = iSoundTriggerHw$Proxy;
            this.mUnderlying_2_0 = iSoundTriggerHw$Proxy;
        } else {
            IHwInterface queryLocalInterface2 = iHwBinder.queryLocalInterface("android.hardware.soundtrigger@2.2::ISoundTriggerHw");
            if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof android.hardware.soundtrigger.V2_2.ISoundTriggerHw)) {
                proxy = new ISoundTriggerHw.Proxy();
                proxy.mRemote = iHwBinder;
                try {
                    Iterator it2 = proxy.interfaceChain().iterator();
                    while (it2.hasNext()) {
                        if (((String) it2.next()).equals("android.hardware.soundtrigger@2.2::ISoundTriggerHw")) {
                            break;
                        }
                    }
                } catch (RemoteException unused2) {
                }
                proxy = 0;
            } else {
                proxy = (android.hardware.soundtrigger.V2_2.ISoundTriggerHw) queryLocalInterface2;
            }
            if (proxy != 0) {
                this.mUnderlying_2_2 = proxy;
                this.mUnderlying_2_1 = proxy;
                this.mUnderlying_2_0 = proxy;
                this.mUnderlying_2_3 = null;
            } else {
                IHwInterface queryLocalInterface3 = iHwBinder.queryLocalInterface("android.hardware.soundtrigger@2.1::ISoundTriggerHw");
                if (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof android.hardware.soundtrigger.V2_1.ISoundTriggerHw)) {
                    proxy2 = new ISoundTriggerHw.Proxy();
                    proxy2.mRemote = iHwBinder;
                    try {
                        Iterator it3 = proxy2.interfaceChain().iterator();
                        while (it3.hasNext()) {
                            if (((String) it3.next()).equals("android.hardware.soundtrigger@2.1::ISoundTriggerHw")) {
                                break;
                            }
                        }
                    } catch (RemoteException unused3) {
                    }
                    proxy2 = 0;
                } else {
                    proxy2 = (android.hardware.soundtrigger.V2_1.ISoundTriggerHw) queryLocalInterface3;
                }
                if (proxy2 != 0) {
                    this.mUnderlying_2_1 = proxy2;
                    this.mUnderlying_2_0 = proxy2;
                    this.mUnderlying_2_3 = null;
                    this.mUnderlying_2_2 = null;
                } else {
                    android.hardware.soundtrigger.V2_0.ISoundTriggerHw asInterface = android.hardware.soundtrigger.V2_0.ISoundTriggerHw.asInterface(iHwBinder);
                    if (asInterface == null) {
                        throw new RuntimeException("Binder doesn't support ISoundTriggerHw@2.0");
                    }
                    this.mUnderlying_2_0 = asInterface;
                    this.mUnderlying_2_3 = null;
                    this.mUnderlying_2_2 = null;
                    this.mUnderlying_2_1 = null;
                }
            }
        }
        try {
            AtomicInteger atomicInteger = new AtomicInteger(-1);
            AtomicReference atomicReference = new AtomicReference();
            try {
                as2_3().getProperties_2_3(new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicReference));
                handleHalStatus(atomicInteger.get(), "getProperties_2_3");
                properties_2_0 = ConversionUtil.hidl2aidlProperties((android.hardware.soundtrigger.V2_3.Properties) atomicReference.get());
            } catch (NotSupported unused4) {
                properties_2_0 = getProperties_2_0();
            }
            this.mProperties = properties_2_0;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public static ISoundTriggerHal create(android.hardware.soundtrigger.V2_0.ISoundTriggerHw iSoundTriggerHw, Runnable runnable, ICaptureStateNotifier iCaptureStateNotifier) {
        SoundTriggerHw2Compat soundTriggerHw2Compat = new SoundTriggerHw2Compat(iSoundTriggerHw.asBinder(), runnable);
        Properties properties = soundTriggerHw2Compat.mProperties;
        SoundTriggerHalMaxModelLimiter soundTriggerHalMaxModelLimiter = new SoundTriggerHalMaxModelLimiter(soundTriggerHw2Compat, properties.maxSoundModels);
        return !properties.concurrentCapture ? new SoundTriggerHalConcurrentCaptureHandler(soundTriggerHalMaxModelLimiter, iCaptureStateNotifier) : soundTriggerHalMaxModelLimiter;
    }

    public static void handleHalStatus(int i, String str) {
        if (i != 0) {
            throw new HalException(i, str);
        }
    }

    public final ISoundTriggerHw$Proxy as2_3() {
        ISoundTriggerHw$Proxy iSoundTriggerHw$Proxy = this.mUnderlying_2_3;
        if (iSoundTriggerHw$Proxy != null) {
            return iSoundTriggerHw$Proxy;
        }
        throw new NotSupported("Underlying driver version < 2.3");
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientAttached(IBinder iBinder) {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientDetached(IBinder iBinder) {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void detach() {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        try {
            android.hardware.soundtrigger.V2_2.ISoundTriggerHw iSoundTriggerHw = this.mUnderlying_2_2;
            if (iSoundTriggerHw == null) {
                throw new NotSupported("Underlying driver version < 2.2");
            }
            handleHalStatus(iSoundTriggerHw.getModelState(i), "getModelState");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (NotSupported e2) {
            throw new RecoverableException(2, e2.getMessage());
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        try {
            as2_3().getParameter(i, i2, new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicInteger2));
            handleHalStatus(atomicInteger.get(), "getParameter");
            return atomicInteger2.get();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (NotSupported e2) {
            throw new RecoverableException(2, e2.getMessage());
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        return this.mProperties;
    }

    public final Properties getProperties_2_0() {
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicReference atomicReference = new AtomicReference();
        this.mUnderlying_2_0.getProperties(new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicReference));
        handleHalStatus(atomicInteger.get(), "getProperties");
        ISoundTriggerHw.Properties properties = (ISoundTriggerHw.Properties) atomicReference.get();
        android.hardware.soundtrigger.V2_3.Properties properties2 = new android.hardware.soundtrigger.V2_3.Properties();
        properties2.base = properties;
        return ConversionUtil.hidl2aidlProperties(properties2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(final IBinder.DeathRecipient deathRecipient) {
        IHwBinder.DeathRecipient deathRecipient2 = new IHwBinder.DeathRecipient() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda0
            public final void serviceDied(long j) {
                deathRecipient.binderDied();
            }
        };
        ((HashMap) this.mDeathRecipientMap).put(deathRecipient, deathRecipient2);
        this.mBinder.linkToDeath(deathRecipient2, 0L);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        ISoundTriggerHw.SoundModel soundModel = new ISoundTriggerHw.SoundModel(1);
        soundModel.header = new ISoundTriggerHw.SoundModel();
        soundModel.data = new ArrayList();
        soundModel.header = ConversionUtil.aidl2hidlSoundModel(phraseSoundModel.common);
        Phrase[] phraseArr = phraseSoundModel.phrases;
        int length = phraseArr.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            try {
                Phrase phrase = phraseArr[i2];
                ArrayList arrayList = (ArrayList) soundModel.data;
                ISoundTriggerHw.Phrase phrase2 = new ISoundTriggerHw.Phrase();
                phrase2.id = i;
                phrase2.recognitionModes = i;
                phrase2.users = new ArrayList();
                phrase2.locale = new String();
                phrase2.text = new String();
                phrase2.id = phrase.id;
                phrase2.recognitionModes = ConversionUtil.aidl2hidlRecognitionModes(phrase.recognitionModes);
                int[] iArr = phrase.users;
                int length2 = iArr.length;
                for (int i3 = i; i3 < length2; i3++) {
                    phrase2.users.add(Integer.valueOf(iArr[i3]));
                }
                phrase2.locale = phrase.locale;
                phrase2.text = phrase.text;
                arrayList.add(phrase2);
                i2++;
                i = 0;
            } finally {
            }
        }
        try {
            AtomicInteger atomicInteger = new AtomicInteger(-1);
            AtomicInteger atomicInteger2 = new AtomicInteger(0);
            try {
                android.hardware.soundtrigger.V2_1.ISoundTriggerHw iSoundTriggerHw = this.mUnderlying_2_1;
                if (iSoundTriggerHw == null) {
                    throw new NotSupported("Underlying driver version < 2.1");
                }
                iSoundTriggerHw.loadPhraseSoundModel_2_1(soundModel, new ModelCallbackWrapper(modelCallback), new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicInteger2));
                handleHalStatus(atomicInteger.get(), "loadPhraseSoundModel_2_1");
                ((ConcurrentHashMap) this.mModelCallbacks).put(Integer.valueOf(atomicInteger2.get()), modelCallback);
                int i4 = atomicInteger2.get();
                HidlMemory hidlMemory = (HidlMemory) ((ISoundTriggerHw.SoundModel) soundModel.header).data;
                if (hidlMemory != null) {
                    try {
                        hidlMemory.close();
                    } catch (IOException e) {
                        Slog.e("SoundTriggerHw2Compat", "Failed to close file", e);
                    }
                }
                return i4;
            } catch (NotSupported unused) {
                int loadPhraseSoundModel_2_0 = loadPhraseSoundModel_2_0(soundModel, modelCallback);
                HidlMemory hidlMemory2 = (HidlMemory) ((ISoundTriggerHw.SoundModel) soundModel.header).data;
                if (hidlMemory2 != null) {
                    try {
                        hidlMemory2.close();
                    } catch (IOException e2) {
                        Slog.e("SoundTriggerHw2Compat", "Failed to close file", e2);
                    }
                }
                return loadPhraseSoundModel_2_0;
            }
        } catch (RemoteException e3) {
            throw e3.rethrowAsRuntimeException();
        }
    }

    public final int loadPhraseSoundModel_2_0(ISoundTriggerHw.SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        ISoundTriggerHw.PhraseSoundModel phraseSoundModel = new ISoundTriggerHw.PhraseSoundModel();
        phraseSoundModel.common = new ISoundTriggerHw.SoundModel();
        phraseSoundModel.phrases = new ArrayList();
        ISoundTriggerHw.SoundModel soundModel2 = (ISoundTriggerHw.SoundModel) soundModel.header;
        ISoundTriggerHw.SoundModel soundModel3 = (ISoundTriggerHw.SoundModel) soundModel2.header;
        soundModel3.data = HidlMemoryUtil.hidlMemoryToByteList((HidlMemory) soundModel2.data);
        phraseSoundModel.common = soundModel3;
        phraseSoundModel.phrases = (ArrayList) soundModel.data;
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        this.mUnderlying_2_0.loadPhraseSoundModel(phraseSoundModel, new ModelCallbackWrapper(modelCallback), new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicInteger2));
        handleHalStatus(atomicInteger.get(), "loadSoundModel");
        ((ConcurrentHashMap) this.mModelCallbacks).put(Integer.valueOf(atomicInteger2.get()), modelCallback);
        return atomicInteger2.get();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        ISoundTriggerHw.SoundModel aidl2hidlSoundModel = ConversionUtil.aidl2hidlSoundModel(soundModel);
        try {
            try {
                AtomicInteger atomicInteger = new AtomicInteger(-1);
                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                try {
                    android.hardware.soundtrigger.V2_1.ISoundTriggerHw iSoundTriggerHw = this.mUnderlying_2_1;
                    if (iSoundTriggerHw == null) {
                        throw new NotSupported("Underlying driver version < 2.1");
                    }
                    iSoundTriggerHw.loadSoundModel_2_1(aidl2hidlSoundModel, new ModelCallbackWrapper(modelCallback), new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicInteger2));
                    handleHalStatus(atomicInteger.get(), "loadSoundModel_2_1");
                    ((ConcurrentHashMap) this.mModelCallbacks).put(Integer.valueOf(atomicInteger2.get()), modelCallback);
                    return atomicInteger2.get();
                } catch (NotSupported unused) {
                    int loadSoundModel_2_0 = loadSoundModel_2_0(aidl2hidlSoundModel, modelCallback);
                    HidlMemory hidlMemory = (HidlMemory) aidl2hidlSoundModel.data;
                    if (hidlMemory != null) {
                        try {
                            hidlMemory.close();
                        } catch (IOException e) {
                            Slog.e("SoundTriggerHw2Compat", "Failed to close file", e);
                        }
                    }
                    return loadSoundModel_2_0;
                }
            } catch (RemoteException e2) {
                throw e2.rethrowAsRuntimeException();
            }
        } finally {
            HidlMemory hidlMemory2 = (HidlMemory) aidl2hidlSoundModel.data;
            if (hidlMemory2 != null) {
                try {
                    hidlMemory2.close();
                } catch (IOException e3) {
                    Slog.e("SoundTriggerHw2Compat", "Failed to close file", e3);
                }
            }
        }
    }

    public final int loadSoundModel_2_0(ISoundTriggerHw.SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        ISoundTriggerHw.SoundModel soundModel2 = (ISoundTriggerHw.SoundModel) soundModel.header;
        soundModel2.data = HidlMemoryUtil.hidlMemoryToByteList((HidlMemory) soundModel.data);
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        this.mUnderlying_2_0.loadSoundModel(soundModel2, new ModelCallbackWrapper(modelCallback), new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicInteger2));
        handleHalStatus(atomicInteger.get(), "loadSoundModel");
        ((ConcurrentHashMap) this.mModelCallbacks).put(Integer.valueOf(atomicInteger2.get()), modelCallback);
        return atomicInteger2.get();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        android.hardware.soundtrigger.V2_3.ModelParameterRange range;
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicReference atomicReference = new AtomicReference();
        try {
            as2_3().queryParameter(i, i2, new SoundTriggerHw2Compat$$ExternalSyntheticLambda1(atomicInteger, atomicReference));
            handleHalStatus(atomicInteger.get(), "queryParameter");
            if (((OptionalModelParameterRange) atomicReference.get()).hidl_d != 1 || (range = ((OptionalModelParameterRange) atomicReference.get()).range()) == null) {
                return null;
            }
            ModelParameterRange modelParameterRange = new ModelParameterRange();
            modelParameterRange.minInclusive = range.start;
            modelParameterRange.maxInclusive = range.end;
            return modelParameterRange;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (NotSupported unused) {
            return null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mRebootRunnable.run();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        try {
            handleHalStatus(as2_3().setParameter(i, i2, i3), "setParameter");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (NotSupported e2) {
            throw new RecoverableException(2, e2.getMessage());
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig2 = new android.hardware.soundtrigger.V2_3.RecognitionConfig();
        ISoundTriggerHw.SoundModel soundModel = new ISoundTriggerHw.SoundModel(2);
        ISoundTriggerHw.RecognitionConfig recognitionConfig3 = new ISoundTriggerHw.RecognitionConfig();
        recognitionConfig3.captureHandle = 0;
        recognitionConfig3.captureDevice = 0;
        recognitionConfig3.captureRequested = false;
        recognitionConfig3.phrases = new ArrayList();
        recognitionConfig3.data = new ArrayList();
        soundModel.header = recognitionConfig3;
        soundModel.data = null;
        recognitionConfig2.base = soundModel;
        recognitionConfig3.captureDevice = i2;
        recognitionConfig3.captureHandle = i3;
        recognitionConfig3.captureRequested = recognitionConfig.captureRequested;
        for (android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra : recognitionConfig.phraseRecognitionExtras) {
            ArrayList arrayList = ((ISoundTriggerHw.RecognitionConfig) recognitionConfig2.base.header).phrases;
            PhraseRecognitionExtra phraseRecognitionExtra2 = new PhraseRecognitionExtra();
            phraseRecognitionExtra2.id = phraseRecognitionExtra.id;
            phraseRecognitionExtra2.recognitionModes = ConversionUtil.aidl2hidlRecognitionModes(phraseRecognitionExtra.recognitionModes);
            phraseRecognitionExtra2.confidenceLevel = phraseRecognitionExtra.confidenceLevel;
            phraseRecognitionExtra2.levels.ensureCapacity(phraseRecognitionExtra.levels.length);
            for (ConfidenceLevel confidenceLevel : phraseRecognitionExtra.levels) {
                ArrayList arrayList2 = phraseRecognitionExtra2.levels;
                android.hardware.soundtrigger.V2_0.ConfidenceLevel confidenceLevel2 = new android.hardware.soundtrigger.V2_0.ConfidenceLevel();
                confidenceLevel2.userId = confidenceLevel.userId;
                confidenceLevel2.levelPercent = confidenceLevel.levelPercent;
                arrayList2.add(confidenceLevel2);
            }
            arrayList.add(phraseRecognitionExtra2);
        }
        recognitionConfig2.base.data = HidlMemoryUtil.byteArrayToHidlMemory(recognitionConfig.data, "SoundTrigger RecognitionConfig");
        recognitionConfig2.audioCapabilities = recognitionConfig.audioCapabilities;
        try {
            try {
                handleHalStatus(as2_3().startRecognition_2_3(i, recognitionConfig2), "startRecognition_2_3");
            } catch (NotSupported unused) {
                startRecognition_2_1(i, recognitionConfig2);
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public final void startRecognition_2_0(int i, android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) {
        ISoundTriggerHw.SoundModel soundModel = recognitionConfig.base;
        ISoundTriggerHw.RecognitionConfig recognitionConfig2 = (ISoundTriggerHw.RecognitionConfig) soundModel.header;
        recognitionConfig2.data = HidlMemoryUtil.hidlMemoryToByteList((HidlMemory) soundModel.data);
        handleHalStatus(this.mUnderlying_2_0.startRecognition(i, recognitionConfig2, new ModelCallbackWrapper((ISoundTriggerHal.ModelCallback) ((ConcurrentHashMap) this.mModelCallbacks).get(Integer.valueOf(i)))), "startRecognition");
    }

    public final void startRecognition_2_1(int i, android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) {
        try {
            try {
                ISoundTriggerHw.SoundModel soundModel = recognitionConfig.base;
                android.hardware.soundtrigger.V2_1.ISoundTriggerHw iSoundTriggerHw = this.mUnderlying_2_1;
                if (iSoundTriggerHw == null) {
                    throw new NotSupported("Underlying driver version < 2.1");
                }
                handleHalStatus(iSoundTriggerHw.startRecognition_2_1(i, soundModel, new ModelCallbackWrapper((ISoundTriggerHal.ModelCallback) ((ConcurrentHashMap) this.mModelCallbacks).get(Integer.valueOf(i)))), "startRecognition_2_1");
            } catch (NotSupported unused) {
                startRecognition_2_0(i, recognitionConfig);
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        try {
            handleHalStatus(this.mUnderlying_2_0.stopRecognition(i), "stopRecognition");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        try {
            ((ConcurrentHashMap) this.mModelCallbacks).remove(Integer.valueOf(i));
            handleHalStatus(this.mUnderlying_2_0.unloadSoundModel(i), "unloadSoundModel");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
