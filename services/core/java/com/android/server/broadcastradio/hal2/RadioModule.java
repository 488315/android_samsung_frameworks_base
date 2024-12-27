package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.Announcement;
import android.hardware.broadcastradio.V2_0.IBroadcastRadio;
import android.hardware.broadcastradio.V2_0.ICloseHandle$Proxy;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy;
import android.hardware.broadcastradio.V2_0.ProgramIdentifier;
import android.hardware.broadcastradio.V2_0.ProgramInfo;
import android.hardware.broadcastradio.V2_0.ProgramListChunk;
import android.hardware.broadcastradio.V2_0.ProgramSelector;
import android.hardware.broadcastradio.V2_0.VendorKeyValue;
import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.Looper;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.MutableInt;

import com.android.server.broadcastradio.RadioEventLogger;
import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import com.att.iqi.lib.metrics.hw.HwConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class RadioModule {
    public final RadioEventLogger mEventLogger;
    public ITunerSession$Proxy mHalTunerSession;
    public final Handler mHandler;
    public final RadioManager.ModuleProperties mProperties;
    public final IBroadcastRadio mService;
    public final Object mLock = new Object();
    public Boolean mAntennaConnected = null;
    public RadioManager.ProgramInfo mCurrentProgramInfo = null;
    public final ProgramInfoCache mProgramInfoCache = new ProgramInfoCache(null);
    public ProgramList.Filter mUnionOfAidlProgramFilters = null;
    public final AnonymousClass1 mHalTunerCallback = new AnonymousClass1(0, this);
    public final Set mAidlTunerSessions = new ArraySet();

    /* renamed from: com.android.server.broadcastradio.hal2.RadioModule$1, reason: invalid class name */
    public final class AnonymousClass1 extends HwBinder implements IBase {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        private final void debug$com$android$server$broadcastradio$hal2$RadioModule$1(
                NativeHandle nativeHandle, ArrayList arrayList) {}

        private final void debug$com$android$server$broadcastradio$hal2$RadioModule$2(
                NativeHandle nativeHandle, ArrayList arrayList) {}

        private final void ping$com$android$server$broadcastradio$hal2$RadioModule$1() {}

        private final void ping$com$android$server$broadcastradio$hal2$RadioModule$2() {}

        private final void
                setHALInstrumentation$com$android$server$broadcastradio$hal2$RadioModule$1() {}

        private final void
                setHALInstrumentation$com$android$server$broadcastradio$hal2$RadioModule$2() {}

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            int i = this.$r8$classId;
            return this;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            int i = this.$r8$classId;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            switch (this.$r8$classId) {
                case 0:
                    DebugInfo debugInfo = new DebugInfo();
                    debugInfo.pid = HidlSupport.getPidIfSharable();
                    debugInfo.ptr = 0L;
                    debugInfo.arch = 0;
                    return debugInfo;
                default:
                    DebugInfo debugInfo2 = new DebugInfo();
                    debugInfo2.pid = HidlSupport.getPidIfSharable();
                    debugInfo2.ptr = 0L;
                    debugInfo2.arch = 0;
                    return debugInfo2;
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            switch (this.$r8$classId) {
                case 0:
                    return new ArrayList(
                            Arrays.asList(
                                    new byte[] {
                                        -81, 36, -72, 124, -88, -72, -16, 47, -51, -30, 5, -28, 125,
                                        -74, -87, 96, -97, -57, -23, -41, 125, 115, -26, -108, -20,
                                        -113, -100, 80, -116, -95, -107, 117
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
                default:
                    return new ArrayList(
                            Arrays.asList(
                                    new byte[] {
                                        61,
                                        -114,
                                        -42,
                                        125,
                                        Byte.MIN_VALUE,
                                        126,
                                        -97,
                                        21,
                                        -48,
                                        112,
                                        -125,
                                        -112,
                                        -92,
                                        22,
                                        -66,
                                        -32,
                                        9,
                                        HwConstants.IQ_CONFIG_POS_WIFI_ENABLED,
                                        -10,
                                        -94,
                                        33,
                                        -106,
                                        -63,
                                        4,
                                        -52,
                                        -98,
                                        68,
                                        60,
                                        -115,
                                        33,
                                        125,
                                        -8
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
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            switch (this.$r8$classId) {
                case 0:
                    return new ArrayList(
                            Arrays.asList(
                                    "android.hardware.broadcastradio@2.0::ITunerCallback",
                                    IBase.kInterfaceName));
                default:
                    return new ArrayList(
                            Arrays.asList(
                                    "android.hardware.broadcastradio@2.0::IAnnouncementListener",
                                    IBase.kInterfaceName));
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            switch (this.$r8$classId) {
                case 0:
                    return "android.hardware.broadcastradio@2.0::ITunerCallback";
                default:
                    return "android.hardware.broadcastradio@2.0::IAnnouncementListener";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            switch (this.$r8$classId) {
            }
            return true;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            switch (this.$r8$classId) {
                case 0:
                    HwBinder.enableInstrumentation();
                    break;
                default:
                    HwBinder.enableInstrumentation();
                    break;
            }
        }

        public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
            switch (this.$r8$classId) {
                case 0:
                    if (i == 1) {
                        hwParcel.enforceInterface(
                                "android.hardware.broadcastradio@2.0::ITunerCallback");
                        final int readInt32 = hwParcel.readInt32();
                        final ProgramSelector programSelector = new ProgramSelector();
                        programSelector.readEmbeddedFromParcel(
                                hwParcel, hwParcel.readBuffer(32L), 0L);
                        ((RadioModule) this.this$0)
                                .fireLater(
                                        new Runnable() { // from class:
                                                         // com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                final int i3;
                                                RadioModule.AnonymousClass1 anonymousClass1 =
                                                        RadioModule.AnonymousClass1.this;
                                                ProgramSelector programSelector2 = programSelector;
                                                int i4 = readInt32;
                                                anonymousClass1.getClass();
                                                final android.hardware.radio.ProgramSelector
                                                        programSelectorFromHal =
                                                                Convert.programSelectorFromHal(
                                                                        programSelector2);
                                                if (i4 != 0) {
                                                    i3 = 2;
                                                    if (i4 != 2) {
                                                        int i5 = 3;
                                                        if (i4 != 3) {
                                                            i3 = 4;
                                                            if (i4 != 4) {
                                                                i5 = 5;
                                                                if (i4 != 5) {
                                                                    if (i4 != 6) {
                                                                        i3 = 7;
                                                                    }
                                                                }
                                                            }
                                                            i3 = i5;
                                                        }
                                                    } else {
                                                        i3 = 1;
                                                    }
                                                } else {
                                                    i3 = 0;
                                                }
                                                synchronized (
                                                        ((RadioModule) anonymousClass1.this$0)
                                                                .mLock) {
                                                    ((RadioModule) anonymousClass1.this$0)
                                                            .fanoutAidlCallbackLocked(
                                                                    new RadioModule
                                                                            .AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda5
                                                                        @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                                                                        public final void run(
                                                                                ITunerCallback
                                                                                        iTunerCallback) {
                                                                            iTunerCallback
                                                                                    .onTuneFailed(
                                                                                            i3,
                                                                                            programSelectorFromHal);
                                                                        }
                                                                    });
                                                }
                                            }
                                        });
                        return;
                    }
                    if (i == 2) {
                        hwParcel.enforceInterface(
                                "android.hardware.broadcastradio@2.0::ITunerCallback");
                        ProgramInfo programInfo = new ProgramInfo();
                        programInfo.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
                        ((RadioModule) this.this$0)
                                .fireLater(
                                        new RadioModule$$ExternalSyntheticLambda3(
                                                1, this, programInfo));
                        return;
                    }
                    if (i == 3) {
                        hwParcel.enforceInterface(
                                "android.hardware.broadcastradio@2.0::ITunerCallback");
                        ProgramListChunk programListChunk = new ProgramListChunk();
                        programListChunk.purge = false;
                        programListChunk.complete = false;
                        ArrayList arrayList = new ArrayList();
                        programListChunk.modified = arrayList;
                        programListChunk.removed = new ArrayList();
                        HwBlob readBuffer = hwParcel.readBuffer(40L);
                        programListChunk.purge = readBuffer.getBool(0L);
                        programListChunk.complete = readBuffer.getBool(1L);
                        int int32 = readBuffer.getInt32(16L);
                        HwBlob readEmbeddedBuffer =
                                hwParcel.readEmbeddedBuffer(
                                        int32 * 120, readBuffer.handle(), 8L, true);
                        arrayList.clear();
                        for (int i3 = 0; i3 < int32; i3++) {
                            ProgramInfo programInfo2 = new ProgramInfo();
                            programInfo2.readEmbeddedFromParcel(
                                    hwParcel, readEmbeddedBuffer, i3 * 120);
                            programListChunk.modified.add(programInfo2);
                        }
                        int int322 = readBuffer.getInt32(32L);
                        HwBlob readEmbeddedBuffer2 =
                                hwParcel.readEmbeddedBuffer(
                                        int322 * 16, readBuffer.handle(), 24L, true);
                        programListChunk.removed.clear();
                        for (int i4 = 0; i4 < int322; i4++) {
                            ProgramIdentifier programIdentifier = new ProgramIdentifier();
                            programIdentifier.readEmbeddedFromParcel(readEmbeddedBuffer2, i4 * 16);
                            programListChunk.removed.add(programIdentifier);
                        }
                        ((RadioModule) this.this$0)
                                .fireLater(
                                        new RadioModule$$ExternalSyntheticLambda3(
                                                3, this, programListChunk));
                        return;
                    }
                    if (i == 4) {
                        hwParcel.enforceInterface(
                                "android.hardware.broadcastradio@2.0::ITunerCallback");
                        final boolean readBool = hwParcel.readBool();
                        ((RadioModule) this.this$0)
                                .fireLater(
                                        new Runnable() { // from class:
                                                         // com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                RadioModule.AnonymousClass1 anonymousClass1 =
                                                        RadioModule.AnonymousClass1.this;
                                                final boolean z = readBool;
                                                synchronized (
                                                        ((RadioModule) anonymousClass1.this$0)
                                                                .mLock) {
                                                    ((RadioModule) anonymousClass1.this$0)
                                                                    .mAntennaConnected =
                                                            Boolean.valueOf(z);
                                                    ((RadioModule) anonymousClass1.this$0)
                                                            .fanoutAidlCallbackLocked(
                                                                    new RadioModule
                                                                            .AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda7
                                                                        @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                                                                        public final void run(
                                                                                ITunerCallback
                                                                                        iTunerCallback) {
                                                                            iTunerCallback
                                                                                    .onAntennaState(
                                                                                            z);
                                                                        }
                                                                    });
                                                }
                                            }
                                        });
                        return;
                    }
                    if (i == 5) {
                        hwParcel.enforceInterface(
                                "android.hardware.broadcastradio@2.0::ITunerCallback");
                        ((RadioModule) this.this$0)
                                .fireLater(
                                        new RadioModule$$ExternalSyntheticLambda3(
                                                2,
                                                this,
                                                VendorKeyValue.readVectorFromParcel(hwParcel)));
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
                            hwParcel.readNativeHandle();
                            hwParcel.readStringVector();
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(
                                    "android.hardware.broadcastradio@2.0::ITunerCallback");
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
                            for (int i5 = 0; i5 < size; i5++) {
                                long j = i5 * 32;
                                byte[] bArr = (byte[]) hashChain.get(i5);
                                if (bArr == null || bArr.length != 32) {
                                    throw new IllegalArgumentException(
                                            "Array element is not of the expected length");
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
                default:
                    byte b = 0;
                    switch (i) {
                        case 1:
                            hwParcel.enforceInterface(
                                    "android.hardware.broadcastradio@2.0::IAnnouncementListener");
                            ArrayList arrayList2 = new ArrayList();
                            HwBlob readBuffer2 = hwParcel.readBuffer(16L);
                            int int323 = readBuffer2.getInt32(8L);
                            HwBlob readEmbeddedBuffer3 =
                                    hwParcel.readEmbeddedBuffer(
                                            int323 * 56, readBuffer2.handle(), 0L, true);
                            arrayList2.clear();
                            int i6 = 0;
                            while (i6 < int323) {
                                Announcement announcement = new Announcement();
                                ProgramSelector programSelector2 = new ProgramSelector();
                                announcement.selector = programSelector2;
                                announcement.type = b;
                                ArrayList arrayList3 = new ArrayList();
                                announcement.vendorInfo = arrayList3;
                                long j2 = i6 * 56;
                                programSelector2.readEmbeddedFromParcel(
                                        hwParcel, readEmbeddedBuffer3, j2);
                                announcement.type = readEmbeddedBuffer3.getInt8(32 + j2);
                                int int324 = readEmbeddedBuffer3.getInt32(j2 + 48);
                                HwBlob readEmbeddedBuffer4 =
                                        hwParcel.readEmbeddedBuffer(
                                                int324 * 32,
                                                readEmbeddedBuffer3.handle(),
                                                j2 + 40,
                                                true);
                                arrayList3.clear();
                                for (int i7 = 0; i7 < int324; i7++) {
                                    VendorKeyValue vendorKeyValue = new VendorKeyValue();
                                    vendorKeyValue.readEmbeddedFromParcel(
                                            hwParcel, readEmbeddedBuffer4, i7 * 32);
                                    announcement.vendorInfo.add(vendorKeyValue);
                                }
                                arrayList2.add(announcement);
                                i6++;
                                b = 0;
                            }
                            ((IAnnouncementListener) this.this$0)
                                    .onListUpdated(
                                            (List)
                                                    arrayList2.stream()
                                                            .map(
                                                                    new RadioModule$2$$ExternalSyntheticLambda0())
                                                            .collect(Collectors.toList()));
                            return;
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList interfaceChain2 = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(interfaceChain2);
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
                            hwParcel2.writeString(
                                    "android.hardware.broadcastradio@2.0::IAnnouncementListener");
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList hashChain2 = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob3 = new HwBlob(16);
                            int size2 = hashChain2.size();
                            hwBlob3.putInt32(8L, size2);
                            hwBlob3.putBool(12L, false);
                            HwBlob hwBlob4 = new HwBlob(size2 * 32);
                            for (int i8 = 0; i8 < size2; i8++) {
                                long j3 = i8 * 32;
                                byte[] bArr2 = (byte[]) hashChain2.get(i8);
                                if (bArr2 == null || bArr2.length != 32) {
                                    throw new IllegalArgumentException(
                                            "Array element is not of the expected length");
                                }
                                hwBlob4.putInt8Array(j3, bArr2);
                            }
                            hwBlob3.putBlob(0L, hwBlob4);
                            hwParcel2.writeBuffer(hwBlob3);
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
                            DebugInfo debugInfo2 = getDebugInfo();
                            hwParcel2.writeStatus(0);
                            debugInfo2.writeToParcel(hwParcel2);
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

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
            int i = this.$r8$classId;
        }

        public final IHwInterface queryLocalInterface(String str) {
            switch (this.$r8$classId) {
                case 0:
                    if ("android.hardware.broadcastradio@2.0::ITunerCallback".equals(str)) {}
                    break;
                default:
                    if ("android.hardware.broadcastradio@2.0::IAnnouncementListener".equals(str)) {}
                    break;
            }
            return this;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
            int i = this.$r8$classId;
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    return "android.hardware.broadcastradio@2.0::ITunerCallback@Stub";
                default:
                    return "android.hardware.broadcastradio@2.0::IAnnouncementListener@Stub";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            switch (this.$r8$classId) {
            }
            return true;
        }
    }

    public interface AidlCallbackRunnable {
        void run(ITunerCallback iTunerCallback);
    }

    public RadioModule(
            IBroadcastRadio iBroadcastRadio, RadioManager.ModuleProperties moduleProperties) {
        Objects.requireNonNull(moduleProperties);
        this.mProperties = moduleProperties;
        Objects.requireNonNull(iBroadcastRadio);
        this.mService = iBroadcastRadio;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mEventLogger = new RadioEventLogger("BcRadio2Srv.module");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.broadcastradio.hal2.RadioModule tryLoadingModule(
            int r10, java.lang.String r11) {
        /*
            java.lang.String r0 = "android.hardware.broadcastradio@2.0::IBroadcastRadio"
            java.lang.String r1 = "BcRadio2Srv.module"
            java.lang.String r2 = "Try loading module for idx "
            r3 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L6b
            r4.<init>(r2)     // Catch: android.os.RemoteException -> L6b
            r4.append(r10)     // Catch: android.os.RemoteException -> L6b
            java.lang.String r2 = ", fqName "
            r4.append(r2)     // Catch: android.os.RemoteException -> L6b
            r4.append(r11)     // Catch: android.os.RemoteException -> L6b
            java.lang.String r2 = r4.toString()     // Catch: android.os.RemoteException -> L6b
            com.android.server.utils.Slogf.i(r1, r2)     // Catch: android.os.RemoteException -> L6b
            android.os.IHwBinder r2 = android.os.HwBinder.getService(r0, r11)     // Catch: android.os.RemoteException -> L6b
            if (r2 != 0) goto L26
        L24:
            r4 = r3
            goto L54
        L26:
            android.os.IHwInterface r4 = r2.queryLocalInterface(r0)     // Catch: android.os.RemoteException -> L6b
            if (r4 == 0) goto L33
            boolean r5 = r4 instanceof android.hardware.broadcastradio.V2_0.IBroadcastRadio     // Catch: android.os.RemoteException -> L6b
            if (r5 == 0) goto L33
            android.hardware.broadcastradio.V2_0.IBroadcastRadio r4 = (android.hardware.broadcastradio.V2_0.IBroadcastRadio) r4     // Catch: android.os.RemoteException -> L6b
            goto L54
        L33:
            android.hardware.broadcastradio.V2_0.IBroadcastRadio$Proxy r4 = new android.hardware.broadcastradio.V2_0.IBroadcastRadio$Proxy     // Catch: android.os.RemoteException -> L6b
            r4.<init>()     // Catch: android.os.RemoteException -> L6b
            r4.mRemote = r2     // Catch: android.os.RemoteException -> L6b
            java.util.ArrayList r2 = r4.interfaceChain()     // Catch: android.os.RemoteException -> L24
            java.util.Iterator r2 = r2.iterator()     // Catch: android.os.RemoteException -> L24
        L42:
            boolean r5 = r2.hasNext()     // Catch: android.os.RemoteException -> L24
            if (r5 == 0) goto L24
            java.lang.Object r5 = r2.next()     // Catch: android.os.RemoteException -> L24
            java.lang.String r5 = (java.lang.String) r5     // Catch: android.os.RemoteException -> L24
            boolean r5 = r5.equals(r0)     // Catch: android.os.RemoteException -> L24
            if (r5 == 0) goto L42
        L54:
            if (r4 != 0) goto L6d
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L6b
            r10.<init>()     // Catch: android.os.RemoteException -> L6b
            java.lang.String r0 = "No service found for fqName "
            r10.append(r0)     // Catch: android.os.RemoteException -> L6b
            r10.append(r11)     // Catch: android.os.RemoteException -> L6b
            java.lang.String r10 = r10.toString()     // Catch: android.os.RemoteException -> L6b
            com.android.server.utils.Slogf.w(r1, r10)     // Catch: android.os.RemoteException -> L6b
            return r3
        L6b:
            r10 = move-exception
            goto Lbe
        L6d:
            com.android.server.broadcastradio.hal2.Mutable r2 = new com.android.server.broadcastradio.hal2.Mutable     // Catch: android.os.RemoteException -> L6b
            r2.<init>()     // Catch: android.os.RemoteException -> L6b
            com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda2 r5 = new com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda2     // Catch: android.os.RemoteException -> L6b
            r6 = 0
            r5.<init>(r6, r2)     // Catch: android.os.RemoteException -> L6b
            r6 = r4
            android.hardware.broadcastradio.V2_0.IBroadcastRadio$Proxy r6 = (android.hardware.broadcastradio.V2_0.IBroadcastRadio.Proxy) r6     // Catch: android.os.RemoteException -> L6b
            r6.getAmFmRegionConfig(r5)     // Catch: android.os.RemoteException -> L6b
            android.os.HwParcel r5 = new android.os.HwParcel     // Catch: android.os.RemoteException -> L6b
            r5.<init>()     // Catch: android.os.RemoteException -> L6b
            r5.writeInterfaceToken(r0)     // Catch: android.os.RemoteException -> L6b
            android.os.HwParcel r0 = new android.os.HwParcel     // Catch: android.os.RemoteException -> L6b
            r0.<init>()     // Catch: android.os.RemoteException -> L6b
            android.os.IHwBinder r7 = r6.mRemote     // Catch: java.lang.Throwable -> Lb9
            r8 = 3
            r9 = 0
            r7.transact(r8, r5, r0, r9)     // Catch: java.lang.Throwable -> Lb9
            r0.verifySuccess()     // Catch: java.lang.Throwable -> Lb9
            r5.releaseTemporaryStorage()     // Catch: java.lang.Throwable -> Lb9
            int r5 = r0.readInt32()     // Catch: java.lang.Throwable -> Lb9
            java.util.ArrayList r7 = android.hardware.broadcastradio.V2_0.DabTableEntry.readVectorFromParcel(r0)     // Catch: java.lang.Throwable -> Lb9
            if (r5 != 0) goto La3
            goto La4
        La3:
            r7 = r3
        La4:
            r0.release()     // Catch: android.os.RemoteException -> L6b
            android.hardware.broadcastradio.V2_0.Properties r0 = r6.getProperties()     // Catch: android.os.RemoteException -> L6b
            java.lang.Object r2 = r2.value     // Catch: android.os.RemoteException -> L6b
            android.hardware.broadcastradio.V2_0.AmFmRegionConfig r2 = (android.hardware.broadcastradio.V2_0.AmFmRegionConfig) r2     // Catch: android.os.RemoteException -> L6b
            android.hardware.radio.RadioManager$ModuleProperties r10 = com.android.server.broadcastradio.hal2.Convert.propertiesFromHal(r10, r11, r0, r2, r7)     // Catch: android.os.RemoteException -> L6b
            com.android.server.broadcastradio.hal2.RadioModule r0 = new com.android.server.broadcastradio.hal2.RadioModule     // Catch: android.os.RemoteException -> L6b
            r0.<init>(r4, r10)     // Catch: android.os.RemoteException -> L6b
            return r0
        Lb9:
            r10 = move-exception
            r0.release()     // Catch: android.os.RemoteException -> L6b
            throw r10     // Catch: android.os.RemoteException -> L6b
        Lbe:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to load module "
            r0.<init>(r2)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            com.android.server.utils.Slogf.e(r1, r11, r10)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.broadcastradio.hal2.RadioModule.tryLoadingModule(int,"
                    + " java.lang.String):com.android.server.broadcastradio.hal2.RadioModule");
    }

    public final AnonymousClass3 addAnnouncementListener(
            int[] iArr, AnnouncementAggregator.ModuleWatcher moduleWatcher) {
        this.mEventLogger.logRadioEvent("Add AnnouncementListener", new Object[0]);
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            arrayList.add(Byte.valueOf((byte) i));
        }
        MutableInt mutableInt = new MutableInt(1);
        final Mutable mutable = new Mutable();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(1, moduleWatcher);
        IBroadcastRadio.Proxy proxy = (IBroadcastRadio.Proxy) this.mService;
        proxy.getClass();
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::IBroadcastRadio");
        hwParcel.writeInt8Vector(arrayList);
        hwParcel.writeStrongBinder(anonymousClass1);
        HwParcel hwParcel2 = new HwParcel();
        try {
            proxy.mRemote.transact(6, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            IHwBinder readStrongBinder = hwParcel2.readStrongBinder();
            ICloseHandle$Proxy iCloseHandle$Proxy = null;
            if (readStrongBinder != null) {
                IHwInterface queryLocalInterface =
                        readStrongBinder.queryLocalInterface(
                                "android.hardware.broadcastradio@2.0::ICloseHandle");
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof ICloseHandle$Proxy)) {
                    ICloseHandle$Proxy iCloseHandle$Proxy2 = new ICloseHandle$Proxy();
                    iCloseHandle$Proxy2.mRemote = readStrongBinder;
                    try {
                        Iterator it = iCloseHandle$Proxy2.interfaceChain().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (((String) it.next())
                                    .equals("android.hardware.broadcastradio@2.0::ICloseHandle")) {
                                iCloseHandle$Proxy = iCloseHandle$Proxy2;
                                break;
                            }
                        }
                    } catch (RemoteException unused) {
                    }
                } else {
                    iCloseHandle$Proxy = (ICloseHandle$Proxy) queryLocalInterface;
                }
            }
            mutableInt.value = readInt32;
            mutable.value = iCloseHandle$Proxy;
            hwParcel2.release();
            Convert.throwOnError(mutableInt.value, "addAnnouncementListener");
            return new ICloseHandle
                    .Stub() { // from class: com.android.server.broadcastradio.hal2.RadioModule.3
                public final void close() {
                    try {
                        ICloseHandle$Proxy iCloseHandle$Proxy3 =
                                (ICloseHandle$Proxy) Mutable.this.value;
                        iCloseHandle$Proxy3.getClass();
                        HwParcel hwParcel3 = new HwParcel();
                        hwParcel3.writeInterfaceToken(
                                "android.hardware.broadcastradio@2.0::ICloseHandle");
                        HwParcel hwParcel4 = new HwParcel();
                        try {
                            iCloseHandle$Proxy3.mRemote.transact(1, hwParcel3, hwParcel4, 0);
                            hwParcel4.verifySuccess();
                            hwParcel3.releaseTemporaryStorage();
                            hwParcel4.release();
                        } catch (Throwable th) {
                            hwParcel4.release();
                            throw th;
                        }
                    } catch (RemoteException e) {
                        Slogf.e("BcRadio2Srv.module", "Failed closing announcement listener", e);
                    }
                    Mutable.this.value = null;
                }
            };
        } catch (Throwable th) {
            hwParcel2.release();
            throw th;
        }
    }

    public final void closeSessions() {
        int size;
        TunerSession[] tunerSessionArr;
        this.mEventLogger.logRadioEvent("Close TunerSessions", new Object[0]);
        synchronized (this.mLock) {
            size = ((ArraySet) this.mAidlTunerSessions).size();
            tunerSessionArr = new TunerSession[size];
            ((ArraySet) this.mAidlTunerSessions).toArray(tunerSessionArr);
            ((ArraySet) this.mAidlTunerSessions).clear();
        }
        for (int i = 0; i < size; i++) {
            tunerSessionArr[i].close(0);
        }
    }

    public final void dumpInfo(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("RadioModule\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("BroadcastRadioService: %s\n", new Object[] {this.mService});
        indentingPrintWriter.printf("Properties: %s\n", new Object[] {this.mProperties});
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf(
                        "HIDL 2.0 HAL TunerSession: %s\n", new Object[] {this.mHalTunerSession});
                indentingPrintWriter.printf("Is antenna connected? ", new Object[0]);
                Boolean bool = this.mAntennaConnected;
                if (bool == null) {
                    indentingPrintWriter.printf("null\n", new Object[0]);
                } else {
                    indentingPrintWriter.printf(
                            "%s\n", new Object[] {bool.booleanValue() ? "Yes" : "No"});
                }
                indentingPrintWriter.printf(
                        "Current ProgramInfo: %s\n", new Object[] {this.mCurrentProgramInfo});
                indentingPrintWriter.printf(
                        "ProgramInfoCache: %s\n", new Object[] {this.mProgramInfoCache});
                indentingPrintWriter.printf(
                        "Union of AIDL ProgramFilters: %s\n",
                        new Object[] {this.mUnionOfAidlProgramFilters});
                indentingPrintWriter.printf("AIDL TunerSessions:\n", new Object[0]);
                indentingPrintWriter.increaseIndent();
                Iterator it = ((ArraySet) this.mAidlTunerSessions).iterator();
                while (it.hasNext()) {
                    ((TunerSession) it.next()).dumpInfo(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Radio module events:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mEventLogger.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final void fanoutAidlCallbackLocked(AidlCallbackRunnable aidlCallbackRunnable) {
        int currentUser = RadioServiceUserController.getCurrentUser();
        Iterator it = ((ArraySet) this.mAidlTunerSessions).iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            TunerSession tunerSession = (TunerSession) it.next();
            int i = tunerSession.mUserId;
            if (i == currentUser || i == 0) {
                try {
                    aidlCallbackRunnable.run(tunerSession.mCallback);
                } catch (DeadObjectException unused) {
                    Slogf.e("BcRadio2Srv.module", "Removing dead TunerSession");
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(tunerSession);
                } catch (RemoteException e) {
                    Slogf.e("BcRadio2Srv.module", "Failed to invoke ITunerCallback: ", e);
                }
            }
        }
        if (arrayList != null) {
            onTunerSessionsClosedLocked((TunerSession[]) arrayList.toArray(new TunerSession[0]));
        }
    }

    public final void fireLater(final Runnable runnable) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        runnable.run();
                    }
                });
    }

    public final void onTunerSessionProgramListFilterChangedLocked(TunerSession tunerSession) {
        ProgramList.Filter filter;
        Iterator it = ((ArraySet) this.mAidlTunerSessions).iterator();
        boolean z = true;
        boolean z2 = false;
        ArraySet arraySet = null;
        ArraySet arraySet2 = null;
        while (it.hasNext()) {
            TunerSession tunerSession2 = (TunerSession) it.next();
            synchronized (tunerSession2.mLock) {
                ProgramInfoCache programInfoCache = tunerSession2.mProgramInfoCache;
                filter = programInfoCache == null ? null : programInfoCache.mFilter;
            }
            if (filter != null) {
                if (arraySet == null) {
                    arraySet = new ArraySet(filter.getIdentifierTypes());
                    arraySet2 = new ArraySet(filter.getIdentifiers());
                    z2 = filter.areCategoriesIncluded();
                    z = filter.areModificationsExcluded();
                } else {
                    if (!arraySet.isEmpty()) {
                        if (filter.getIdentifierTypes().isEmpty()) {
                            arraySet.clear();
                        } else {
                            arraySet.addAll(filter.getIdentifierTypes());
                        }
                    }
                    if (!arraySet2.isEmpty()) {
                        if (filter.getIdentifiers().isEmpty()) {
                            arraySet2.clear();
                        } else {
                            arraySet2.addAll(filter.getIdentifiers());
                        }
                    }
                    z2 |= filter.areCategoriesIncluded();
                    z &= filter.areModificationsExcluded();
                }
            }
        }
        ProgramList.Filter filter2 =
                arraySet == null ? null : new ProgramList.Filter(arraySet, arraySet2, z2, z);
        if (filter2 != null) {
            if (!filter2.equals(this.mUnionOfAidlProgramFilters)) {
                this.mUnionOfAidlProgramFilters = filter2;
                try {
                    Convert.throwOnError(
                            this.mHalTunerSession.startProgramListUpdates(
                                    Convert.programFilterToHal(filter2)),
                            "startProgramListUpdates");
                    return;
                } catch (RemoteException e) {
                    Slogf.e(
                            "BcRadio2Srv.module",
                            "mHalTunerSession.startProgramListUpdates() failed: ",
                            e);
                    return;
                }
            }
            if (tunerSession != null) {
                ProgramInfoCache programInfoCache2 = this.mProgramInfoCache;
                synchronized (tunerSession.mLock) {
                    try {
                        ProgramInfoCache programInfoCache3 = tunerSession.mProgramInfoCache;
                        if (programInfoCache3 != null) {
                            tunerSession.dispatchClientUpdateChunks(
                                    programInfoCache3.filterAndUpdateFromInternal(
                                            programInfoCache2, true, 100, 500));
                        }
                    } finally {
                    }
                }
                return;
            }
            return;
        }
        if (this.mUnionOfAidlProgramFilters == null) {
            return;
        }
        this.mUnionOfAidlProgramFilters = null;
        try {
            ITunerSession$Proxy iTunerSession$Proxy = this.mHalTunerSession;
            iTunerSession$Proxy.getClass();
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
            HwParcel hwParcel2 = new HwParcel();
            try {
                iTunerSession$Proxy.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                hwParcel2.release();
            } catch (Throwable th) {
                hwParcel2.release();
                throw th;
            }
        } catch (RemoteException e2) {
            Slogf.e("BcRadio2Srv.module", "mHalTunerSession.stopProgramListUpdates() failed: ", e2);
        }
    }

    public final void onTunerSessionsClosedLocked(TunerSession... tunerSessionArr) {
        for (TunerSession tunerSession : tunerSessionArr) {
            ((ArraySet) this.mAidlTunerSessions).remove(tunerSession);
        }
        synchronized (this.mLock) {
            onTunerSessionProgramListFilterChangedLocked(null);
        }
        if (!((ArraySet) this.mAidlTunerSessions).isEmpty() || this.mHalTunerSession == null) {
            return;
        }
        this.mEventLogger.logRadioEvent("Closing HAL tuner session", new Object[0]);
        try {
            ITunerSession$Proxy iTunerSession$Proxy = this.mHalTunerSession;
            iTunerSession$Proxy.getClass();
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
            HwParcel hwParcel2 = new HwParcel();
            try {
                iTunerSession$Proxy.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                hwParcel2.release();
            } catch (Throwable th) {
                hwParcel2.release();
                throw th;
            }
        } catch (RemoteException e) {
            Slogf.e("BcRadio2Srv.module", "mHalTunerSession.close() failed: ", e);
        }
        this.mHalTunerSession = null;
    }
}
