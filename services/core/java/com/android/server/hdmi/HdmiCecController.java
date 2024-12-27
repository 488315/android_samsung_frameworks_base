package com.android.server.hdmi;

import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy$$ExternalSyntheticOutline0;
import android.hardware.hdmi.HdmiHotplugEvent;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.tv.cec.V1_0.IHdmiCec;
import android.hardware.tv.cec.V1_1.IHdmiCec;
import android.hardware.tv.hdmi.cec.CecMessage;
import android.hardware.tv.hdmi.cec.IHdmiCec;
import android.hardware.tv.hdmi.cec.IHdmiCecCallback;
import android.hardware.tv.hdmi.connection.IHdmiConnection;
import android.hardware.tv.hdmi.connection.IHdmiConnectionCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.icu.util.IllformedLocaleException;
import android.icu.util.ULocale;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Handler;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.IInterface;
import android.os.Looper;
import android.os.NativeHandle;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.hdmi.HdmiCecController;
import com.android.server.hdmi.HdmiCecLocalDevice;
import com.android.server.hdmi.HdmiControlService;
import com.android.server.location.gnss.hal.GnssNative;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HdmiCecController {
    public static final byte[] EMPTY_BODY = EmptyArray.BYTE;
    public Handler mControlHandler;
    public final HdmiCecAtomWriter mHdmiCecAtomWriter;
    public Handler mIoHandler;
    public final NativeWrapper mNativeWrapperImpl;
    public final HdmiControlService mService;
    public final AnonymousClass1 mRemoteDeviceAddressPredicate = new Predicate() { // from class: com.android.server.hdmi.HdmiCecController.1
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return !HdmiCecController.this.mService.mHdmiCecNetwork.isAllocatedLocalDeviceAddress(((Integer) obj).intValue());
        }
    };
    public final AnonymousClass2 mSystemAudioAddressPredicate = new AnonymousClass2();
    public ArrayBlockingQueue mMessageHistory = new ArrayBlockingQueue(FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE);
    public final Object mMessageHistoryLock = new Object();
    public long mLogicalAddressAllocationDelay = 0;
    public long mPollDevicesDelay = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.HdmiCecController$2, reason: invalid class name */
    public final class AnonymousClass2 implements Predicate {
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return HdmiUtils.isEligibleAddressForDevice(5, ((Integer) obj).intValue());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.HdmiCecController$7, reason: invalid class name */
    public final class AnonymousClass7 implements Runnable {
        public final /* synthetic */ HdmiControlService.SendMessageCallback val$callback;
        public final /* synthetic */ HdmiCecMessage val$cecMessage;
        public final /* synthetic */ List val$sendResults;

        public AnonymousClass7(HdmiCecMessage hdmiCecMessage, List list, HdmiControlService.SendMessageCallback sendMessageCallback) {
            this.val$cecMessage = hdmiCecMessage;
            this.val$sendResults = list;
            this.val$callback = sendMessageCallback;
        }

        @Override // java.lang.Runnable
        public final void run() {
            final int nativeSendCecCommand;
            int i = 0;
            HdmiLogger.debug("[S]:" + this.val$cecMessage, new Object[0]);
            HdmiCecMessage hdmiCecMessage = this.val$cecMessage;
            int i2 = hdmiCecMessage.mOpcode;
            byte[] bArr = hdmiCecMessage.mParams;
            byte[] bArr2 = new byte[bArr.length + 1];
            bArr2[0] = (byte) i2;
            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
            while (true) {
                NativeWrapper nativeWrapper = HdmiCecController.this.mNativeWrapperImpl;
                HdmiCecMessage hdmiCecMessage2 = this.val$cecMessage;
                nativeSendCecCommand = nativeWrapper.nativeSendCecCommand(hdmiCecMessage2.mSource, hdmiCecMessage2.mDestination, bArr2);
                if (nativeSendCecCommand == 0) {
                    this.val$sendResults.add("ACK");
                } else if (nativeSendCecCommand == 1) {
                    this.val$sendResults.add("NACK");
                } else if (nativeSendCecCommand == 2) {
                    this.val$sendResults.add("BUSY");
                } else if (nativeSendCecCommand == 3) {
                    this.val$sendResults.add("FAIL");
                }
                if (nativeSendCecCommand == 0) {
                    break;
                }
                int i3 = i + 1;
                if (i >= 1) {
                    break;
                } else {
                    i = i3;
                }
            }
            if (nativeSendCecCommand != 0) {
                Slog.w("HdmiCecController", "Failed to send " + this.val$cecMessage + " with errorCode=" + nativeSendCecCommand);
            }
            HdmiCecController.this.runOnServiceThread(new Runnable() { // from class: com.android.server.hdmi.HdmiCecController.7.1
                @Override // java.lang.Runnable
                public final void run() {
                    AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                    HdmiCecAtomWriter hdmiCecAtomWriter = HdmiCecController.this.mHdmiCecAtomWriter;
                    HdmiCecMessage hdmiCecMessage3 = anonymousClass7.val$cecMessage;
                    int callingWorkSourceUid = Binder.getCallingWorkSourceUid();
                    if (callingWorkSourceUid == -1) {
                        callingWorkSourceUid = Binder.getCallingUid();
                    }
                    hdmiCecAtomWriter.messageReported(hdmiCecMessage3, 2, callingWorkSourceUid, nativeSendCecCommand);
                    HdmiControlService.SendMessageCallback sendMessageCallback = AnonymousClass7.this.val$callback;
                    if (sendMessageCallback != null) {
                        sendMessageCallback.onSendCompleted(nativeSendCecCommand);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Dumpable {
        public final long mTime = System.currentTimeMillis();

        public abstract void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdmiCecCallback {
        public HdmiCecCallback() {
        }

        public void onCecMessage(final int i, final int i2, final byte[] bArr) {
            HdmiCecController.this.runOnServiceThread(new Runnable() { // from class: com.android.server.hdmi.HdmiCecController$HdmiCecCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    HdmiCecController.HdmiCecCallback hdmiCecCallback = HdmiCecController.HdmiCecCallback.this;
                    int i3 = i;
                    int i4 = i2;
                    byte[] bArr2 = bArr;
                    HdmiCecController hdmiCecController = HdmiCecController.this;
                    hdmiCecController.assertRunOnServiceThread();
                    if (bArr2.length == 0) {
                        Slog.e("HdmiCecController", "Message with empty body received.");
                        return;
                    }
                    boolean z = false;
                    int i5 = 1;
                    HdmiCecMessage build = HdmiCecMessage.build(i3, i4, bArr2[0], Arrays.copyOfRange(bArr2, 1, bArr2.length));
                    if (build.mValidationResult != 0) {
                        Slog.e("HdmiCecController", "Invalid message received: " + build);
                    }
                    HdmiLogger.debug("[R]:" + build, new Object[0]);
                    hdmiCecController.assertRunOnServiceThread();
                    hdmiCecController.addEventToHistory(new HdmiCecController.MessageHistoryRecord(true, build, null));
                    boolean z2 = i4 == 15;
                    Iterator it = ((ArrayList) hdmiCecController.mService.mHdmiCecNetwork.getLocalDeviceList()).iterator();
                    while (it.hasNext()) {
                        int logicalAddress = ((HdmiCecLocalDevice) it.next()).getDeviceInfo().getLogicalAddress();
                        if (logicalAddress == i3) {
                            z = true;
                        }
                        if (logicalAddress == i4) {
                            z2 = true;
                        }
                    }
                    if (!z && z2) {
                        i5 = 3;
                    } else if (z && z2) {
                        i5 = 4;
                    }
                    int callingWorkSourceUid = Binder.getCallingWorkSourceUid();
                    if (callingWorkSourceUid == -1) {
                        callingWorkSourceUid = Binder.getCallingUid();
                    }
                    hdmiCecController.mHdmiCecAtomWriter.messageReported(build, i5, callingWorkSourceUid, -1);
                    hdmiCecController.onReceiveCommand(build);
                }
            });
        }

        public void onHotplugEvent(final int i, final boolean z) {
            HdmiCecController.this.runOnServiceThread(new Runnable() { // from class: com.android.server.hdmi.HdmiCecController$HdmiCecCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HdmiCecController.HdmiCecCallback hdmiCecCallback = HdmiCecController.HdmiCecCallback.this;
                    int i2 = i;
                    boolean z2 = z;
                    HdmiCecController hdmiCecController = HdmiCecController.this;
                    hdmiCecController.assertRunOnServiceThread();
                    HdmiLogger.debug("Hotplug event:[port:%d, connected:%b]", Integer.valueOf(i2), Boolean.valueOf(z2));
                    hdmiCecController.assertRunOnServiceThread();
                    hdmiCecController.addEventToHistory(new HdmiCecController.HotplugHistoryRecord(i2, z2));
                    HdmiControlService hdmiControlService = hdmiCecController.mService;
                    hdmiControlService.assertRunOnServiceThread();
                    hdmiControlService.mHdmiCecNetwork.initPortInfo();
                    HdmiPortInfo portInfo = hdmiControlService.mHdmiCecNetwork.getPortInfo(i2);
                    if (z2 && !hdmiControlService.isTvDevice() && portInfo != null && portInfo.getType() == 1) {
                        ArrayList arrayList = new ArrayList();
                        Iterator it = ((ArrayList) hdmiControlService.getCecLocalDeviceTypes()).iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) it.next()).intValue();
                            HdmiCecLocalDevice localDevice = hdmiControlService.mHdmiCecNetwork.getLocalDevice(intValue);
                            if (localDevice == null) {
                                localDevice = HdmiCecLocalDevice.create(hdmiControlService, intValue);
                                localDevice.assertRunOnServiceThread();
                                localDevice.mPreferredAddress = localDevice.getPreferredAddress();
                                HdmiCecLocalDevice.AnonymousClass1 anonymousClass1 = localDevice.mHandler;
                                if (anonymousClass1.hasMessages(1)) {
                                    anonymousClass1.removeMessages(1);
                                    localDevice.handleDisableDeviceTimeout();
                                }
                                localDevice.mPendingActionClearedCallback = null;
                            }
                            arrayList.add(localDevice);
                        }
                        hdmiControlService.allocateLogicalAddress(arrayList, 4);
                    }
                    Iterator it2 = ((ArrayList) hdmiControlService.mHdmiCecNetwork.getLocalDeviceList()).iterator();
                    while (it2.hasNext()) {
                        ((HdmiCecLocalDevice) it2.next()).onHotplug(i2, z2);
                    }
                    HdmiHotplugEvent hdmiHotplugEvent = new HdmiHotplugEvent(i2, z2);
                    synchronized (hdmiControlService.mLock) {
                        try {
                            Iterator it3 = hdmiControlService.mHotplugEventListenerRecords.iterator();
                            while (it3.hasNext()) {
                                try {
                                    ((HdmiControlService.HotplugEventListenerRecord) it3.next()).mListener.onReceived(hdmiHotplugEvent);
                                } catch (RemoteException e) {
                                    Slog.e("HdmiControlService", "Failed to report hotplug event:" + hdmiHotplugEvent.toString(), e);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdmiCecCallback10 extends HwBinder implements IBase {
        public final /* synthetic */ int $r8$classId;
        public final HdmiCecCallback mHdmiCecCallback;

        public /* synthetic */ HdmiCecCallback10(HdmiCecCallback hdmiCecCallback, int i) {
            this.$r8$classId = i;
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        private final void debug$com$android$server$hdmi$HdmiCecController$HdmiCecCallback10(NativeHandle nativeHandle, ArrayList arrayList) {
        }

        private final void debug$com$android$server$hdmi$HdmiCecController$HdmiCecCallback11(NativeHandle nativeHandle, ArrayList arrayList) {
        }

        private final void ping$com$android$server$hdmi$HdmiCecController$HdmiCecCallback10() {
        }

        private final void ping$com$android$server$hdmi$HdmiCecController$HdmiCecCallback11() {
        }

        private final void setHALInstrumentation$com$android$server$hdmi$HdmiCecController$HdmiCecCallback10() {
        }

        private final void setHALInstrumentation$com$android$server$hdmi$HdmiCecController$HdmiCecCallback11() {
        }

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
                    return new ArrayList(Arrays.asList(new byte[]{-25, 91, 110, -22, 113, 29, 54, -6, -58, 120, -68, -32, 114, -77, -50, -58, 84, 75, 39, -6, -97, 76, -39, 3, -103, -108, 4, -27, -63, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -54, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
                default:
                    return new ArrayList(Arrays.asList(new byte[]{-71, 104, 37, -121, 103, 124, -23, -56, 114, -32, 79, 14, -97, -42, -55, -57, -118, 86, -82, 121, 92, 7, -53, -8, -59, 1, 0, -32, 53, 29, 76, 68}, new byte[]{-25, 91, 110, -22, 113, 29, 54, -6, -58, 120, -68, -32, 114, -77, -50, -58, 84, 75, 39, -6, -97, 76, -39, 3, -103, -108, 4, -27, -63, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -54, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            switch (this.$r8$classId) {
                case 0:
                    return new ArrayList(Arrays.asList("android.hardware.tv.cec@1.0::IHdmiCecCallback", IBase.kInterfaceName));
                default:
                    return new ArrayList(Arrays.asList("android.hardware.tv.cec@1.1::IHdmiCecCallback", "android.hardware.tv.cec@1.0::IHdmiCecCallback", IBase.kInterfaceName));
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            switch (this.$r8$classId) {
                case 0:
                    return "android.hardware.tv.cec@1.0::IHdmiCecCallback";
                default:
                    return "android.hardware.tv.cec@1.1::IHdmiCecCallback";
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
                    int i3 = 0;
                    if (i == 1) {
                        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
                        ArrayList arrayList = new ArrayList();
                        HwBlob readBuffer = hwParcel.readBuffer(24L);
                        int int32 = readBuffer.getInt32(0L);
                        int int322 = readBuffer.getInt32(4L);
                        int int323 = readBuffer.getInt32(16L);
                        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int323, readBuffer.handle(), 8L, true);
                        arrayList.clear();
                        for (int i4 = 0; i4 < int323; i4++) {
                            arrayList.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i4)));
                        }
                        byte[] bArr = new byte[arrayList.size()];
                        while (i3 < arrayList.size()) {
                            bArr[i3] = ((Byte) arrayList.get(i3)).byteValue();
                            i3++;
                        }
                        this.mHdmiCecCallback.onCecMessage(int32, int322, bArr);
                        return;
                    }
                    if (i == 2) {
                        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
                        HwBlob readBuffer2 = hwParcel.readBuffer(8L);
                        this.mHdmiCecCallback.onHotplugEvent(readBuffer2.getInt32(4L), readBuffer2.getBool(0L));
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
                            hwParcel2.writeString("android.hardware.tv.cec@1.0::IHdmiCecCallback");
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
                            while (i3 < size) {
                                long j = i3 * 32;
                                byte[] bArr2 = (byte[]) hashChain.get(i3);
                                if (bArr2 == null || bArr2.length != 32) {
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                hwBlob2.putInt8Array(j, bArr2);
                                i3++;
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
                    int i5 = 0;
                    if (i == 1) {
                        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
                        ArrayList arrayList2 = new ArrayList();
                        HwBlob readBuffer3 = hwParcel.readBuffer(24L);
                        int int324 = readBuffer3.getInt32(0L);
                        int int325 = readBuffer3.getInt32(4L);
                        int int326 = readBuffer3.getInt32(16L);
                        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int326, readBuffer3.handle(), 8L, true);
                        arrayList2.clear();
                        for (int i6 = 0; i6 < int326; i6++) {
                            arrayList2.add(Byte.valueOf(readEmbeddedBuffer2.getInt8(i6)));
                        }
                        byte[] bArr3 = new byte[arrayList2.size()];
                        while (i5 < arrayList2.size()) {
                            bArr3[i5] = ((Byte) arrayList2.get(i5)).byteValue();
                            i5++;
                        }
                        this.mHdmiCecCallback.onCecMessage(int324, int325, bArr3);
                        return;
                    }
                    if (i == 2) {
                        hwParcel.enforceInterface("android.hardware.tv.cec@1.0::IHdmiCecCallback");
                        HwBlob readBuffer4 = hwParcel.readBuffer(8L);
                        this.mHdmiCecCallback.onHotplugEvent(readBuffer4.getInt32(4L), readBuffer4.getBool(0L));
                        return;
                    }
                    if (i == 3) {
                        hwParcel.enforceInterface("android.hardware.tv.cec@1.1::IHdmiCecCallback");
                        ArrayList arrayList3 = new ArrayList();
                        HwBlob readBuffer5 = hwParcel.readBuffer(24L);
                        int int327 = readBuffer5.getInt32(0L);
                        int int328 = readBuffer5.getInt32(4L);
                        int int329 = readBuffer5.getInt32(16L);
                        HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int329, readBuffer5.handle(), 8L, true);
                        arrayList3.clear();
                        for (int i7 = 0; i7 < int329; i7++) {
                            arrayList3.add(Byte.valueOf(readEmbeddedBuffer3.getInt8(i7)));
                        }
                        byte[] bArr4 = new byte[arrayList3.size()];
                        while (i5 < arrayList3.size()) {
                            bArr4[i5] = ((Byte) arrayList3.get(i5)).byteValue();
                            i5++;
                        }
                        this.mHdmiCecCallback.onCecMessage(int327, int328, bArr4);
                        return;
                    }
                    switch (i) {
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
                            hwParcel2.writeString("android.hardware.tv.cec@1.1::IHdmiCecCallback");
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
                            while (i5 < size2) {
                                long j2 = i5 * 32;
                                byte[] bArr5 = (byte[]) hashChain2.get(i5);
                                if (bArr5 == null || bArr5.length != 32) {
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                hwBlob4.putInt8Array(j2, bArr5);
                                i5++;
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
                    if ("android.hardware.tv.cec@1.0::IHdmiCecCallback".equals(str)) {
                    }
                    break;
                default:
                    if ("android.hardware.tv.cec@1.1::IHdmiCecCallback".equals(str)) {
                    }
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
                    return "android.hardware.tv.cec@1.0::IHdmiCecCallback@Stub";
                default:
                    return "android.hardware.tv.cec@1.1::IHdmiCecCallback@Stub";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            switch (this.$r8$classId) {
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdmiCecCallbackAidl extends Binder implements IHdmiCecCallback {
        public final HdmiCecCallback mHdmiCecCallback;

        public HdmiCecCallbackAidl(HdmiCecCallback hdmiCecCallback) {
            markVintfStability();
            attachInterface(this, IHdmiCecCallback.DESCRIPTOR);
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IHdmiCecCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                synchronized (this) {
                }
                parcel2.writeString("cd956e3a0c2e6ade71693c85e9f0aeffa221ea26");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            CecMessage cecMessage = (CecMessage) parcel.readTypedObject(CecMessage.CREATOR);
            parcel.enforceNoDataAvail();
            this.mHdmiCecCallback.onCecMessage(cecMessage.initiator, cecMessage.destination, cecMessage.body);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdmiConnectionCallbackAidl extends Binder implements IHdmiConnectionCallback {
        public final HdmiCecCallback mHdmiCecCallback;

        public HdmiConnectionCallbackAidl(HdmiCecCallback hdmiCecCallback) {
            markVintfStability();
            attachInterface(this, IHdmiConnectionCallback.DESCRIPTOR);
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IHdmiConnectionCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                synchronized (this) {
                }
                parcel2.writeString("85c26fa47f3c3062aa93ffc8bb0897a85c8cb118");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            this.mHdmiCecCallback.onHotplugEvent(readInt, readBoolean);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HotplugHistoryRecord extends Dumpable {
        public final boolean mConnected;
        public final int mPort;

        public HotplugHistoryRecord(int i, boolean z) {
            this.mPort = i;
            this.mConnected = z;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("[H]");
            indentingPrintWriter.print(" time=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mTime)));
            indentingPrintWriter.print(" hotplug port=");
            indentingPrintWriter.print(this.mPort);
            indentingPrintWriter.print(" connected=");
            indentingPrintWriter.println(this.mConnected);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageHistoryRecord extends Dumpable {
        public final boolean mIsReceived;
        public final HdmiCecMessage mMessage;
        public final List mSendResults;

        public MessageHistoryRecord(boolean z, HdmiCecMessage hdmiCecMessage, List list) {
            this.mIsReceived = z;
            this.mMessage = hdmiCecMessage;
            this.mSendResults = list;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            boolean z = this.mIsReceived;
            indentingPrintWriter.print(z ? "[R]" : "[S]");
            indentingPrintWriter.print(" time=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mTime)));
            indentingPrintWriter.print(" message=");
            indentingPrintWriter.print(this.mMessage);
            StringBuilder sb = new StringBuilder();
            if (!z && this.mSendResults != null) {
                sb.append(" (");
                sb.append(String.join(", ", this.mSendResults));
                sb.append(")");
            }
            indentingPrintWriter.println(sb);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NativeWrapper {
        void enableCec(boolean z);

        void enableSystemCecControl(boolean z);

        void enableWakeupByOtp(boolean z);

        int nativeAddLogicalAddress(int i);

        void nativeClearLogicalAddress();

        void nativeEnableAudioReturnChannel(int i, boolean z);

        int nativeGetHpdSignalType(int i);

        int nativeGetPhysicalAddress();

        HdmiPortInfo[] nativeGetPortInfos();

        int nativeGetVendorId();

        int nativeGetVersion();

        String nativeInit();

        boolean nativeIsConnected(int i);

        int nativeSendCecCommand(int i, int i2, byte[] bArr);

        void nativeSetHpdSignalType(int i, int i2);

        void nativeSetLanguage(String str);

        void setCallback(HdmiCecCallback hdmiCecCallback);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NativeWrapperImpl implements NativeWrapper, IHwBinder.DeathRecipient, IHdmiCec.getPhysicalAddressCallback {
        public HdmiCecCallback mCallback;
        public IHdmiCec mHdmiCec;
        public final Object mLock = new Object();
        public int mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;

        private void nativeSetOption(int i, boolean z) {
            try {
                this.mHdmiCec.setOption(i, z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to set option : ", new Object[0]);
            }
        }

        public final boolean connectToHal() {
            try {
                IHdmiCec service = IHdmiCec.getService();
                this.mHdmiCec = service;
                try {
                    service.linkToDeath(this, 353L);
                    return true;
                } catch (RemoteException e) {
                    HdmiLogger.error(e, "Couldn't link to death : ", new Object[0]);
                    return true;
                }
            } catch (RemoteException | NoSuchElementException e2) {
                HdmiLogger.error(e2, "Couldn't connect to cec@1.0", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableCec(boolean z) {
            nativeSetOption(2, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableSystemCecControl(boolean z) {
            nativeSetOption(3, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableWakeupByOtp(boolean z) {
            nativeSetOption(1, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeAddLogicalAddress(int i) {
            try {
                return this.mHdmiCec.addLogicalAddress(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to add a logical address : ", new Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeClearLogicalAddress() {
            try {
                this.mHdmiCec.clearLogicalAddress();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to clear logical address : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                this.mHdmiCec.enableAudioReturnChannel(i, z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to enable/disable ARC : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetHpdSignalType(int i) {
            HdmiLogger.error("Failed to get HPD signal type: not supported by HAL.", new Object[0]);
            return 0;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetPhysicalAddress() {
            try {
                this.mHdmiCec.getPhysicalAddress(this);
                return this.mPhysicalAddress;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get physical address : ", new Object[0]);
                return GnssNative.GNSS_AIDING_TYPE_ALL;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final HdmiPortInfo[] nativeGetPortInfos() {
            try {
                ArrayList portInfo = this.mHdmiCec.getPortInfo();
                HdmiPortInfo[] hdmiPortInfoArr = new HdmiPortInfo[portInfo.size()];
                Iterator it = portInfo.iterator();
                int i = 0;
                while (it.hasNext()) {
                    android.hardware.tv.cec.V1_0.HdmiPortInfo hdmiPortInfo = (android.hardware.tv.cec.V1_0.HdmiPortInfo) it.next();
                    hdmiPortInfoArr[i] = new HdmiPortInfo.Builder(hdmiPortInfo.portId, hdmiPortInfo.type, Short.toUnsignedInt(hdmiPortInfo.physicalAddress)).setCecSupported(hdmiPortInfo.cecSupported).setMhlSupported(false).setArcSupported(hdmiPortInfo.arcSupported).setEarcSupported(false).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get port information : ", new Object[0]);
                return null;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVendorId() {
            try {
                return this.mHdmiCec.getVendorId();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get vendor id : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVersion() {
            try {
                return this.mHdmiCec.getCecVersion();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get cec version : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final String nativeInit() {
            if (connectToHal()) {
                return this.mHdmiCec.toString();
            }
            return null;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final boolean nativeIsConnected(int i) {
            try {
                return this.mHdmiCec.isConnected(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get connection info : ", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            android.hardware.tv.cec.V1_0.CecMessage cecMessage = new android.hardware.tv.cec.V1_0.CecMessage();
            cecMessage.initiator = 0;
            cecMessage.destination = 0;
            cecMessage.body = new ArrayList();
            cecMessage.initiator = i;
            cecMessage.destination = i2;
            cecMessage.body = new ArrayList(bArr.length);
            for (byte b : bArr) {
                cecMessage.body.add(Byte.valueOf(b));
            }
            try {
                return this.mHdmiCec.sendMessage(cecMessage);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to send CEC message : ", new Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetHpdSignalType(int i, int i2) {
            HdmiLogger.error("Failed to set HPD signal type: not supported by HAL.", new Object[0]);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetLanguage(String str) {
            try {
                this.mHdmiCec.setLanguage(str);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to set language : ", new Object[0]);
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback
        public final void onValues(int i, short s) {
            if (i == 0) {
                synchronized (this.mLock) {
                    this.mPhysicalAddress = new Short(s).intValue();
                }
            }
        }

        public final void serviceDied(long j) {
            if (j == 353) {
                HdmiLogger.error("Service died cookie : " + j + "; reconnecting", new Object[0]);
                connectToHal();
                HdmiCecCallback hdmiCecCallback = this.mCallback;
                if (hdmiCecCallback != null) {
                    setCallback(hdmiCecCallback);
                }
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void setCallback(HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                this.mHdmiCec.setCallback(new HdmiCecCallback10(hdmiCecCallback, 0));
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Couldn't initialise tv.cec callback : ", new Object[0]);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NativeWrapperImpl11 implements NativeWrapper, IHwBinder.DeathRecipient, IHdmiCec.getPhysicalAddressCallback {
        public HdmiCecCallback mCallback;
        public android.hardware.tv.cec.V1_1.IHdmiCec mHdmiCec;
        public final Object mLock = new Object();
        public int mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;

        public final boolean connectToHal() {
            try {
                android.hardware.tv.cec.V1_1.IHdmiCec service = android.hardware.tv.cec.V1_1.IHdmiCec.getService();
                this.mHdmiCec = service;
                try {
                    ((IHdmiCec.Proxy) service).linkToDeath(this, 353L);
                    return true;
                } catch (RemoteException e) {
                    HdmiLogger.error(e, "Couldn't link to death : ", new Object[0]);
                    return true;
                }
            } catch (RemoteException | NoSuchElementException e2) {
                HdmiLogger.error(e2, "Couldn't connect to cec@1.1", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableCec(boolean z) {
            nativeSetOption(2, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableSystemCecControl(boolean z) {
            nativeSetOption(3, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableWakeupByOtp(boolean z) {
            nativeSetOption(1, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeAddLogicalAddress(int i) {
            try {
                IHdmiCec.Proxy proxy = (IHdmiCec.Proxy) this.mHdmiCec;
                proxy.getClass();
                HwParcel m = ITunerSession$Proxy$$ExternalSyntheticOutline0.m(i, "android.hardware.tv.cec@1.1::IHdmiCec");
                HwParcel hwParcel = new HwParcel();
                try {
                    proxy.mRemote.transact(13, m, hwParcel, 0);
                    hwParcel.verifySuccess();
                    m.releaseTemporaryStorage();
                    return hwParcel.readInt32();
                } finally {
                    hwParcel.release();
                }
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to add a logical address : ", new Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeClearLogicalAddress() {
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).clearLogicalAddress();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to clear logical address : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).enableAudioReturnChannel(i, z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to enable/disable ARC : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetHpdSignalType(int i) {
            HdmiLogger.error("Failed to get HPD signal type: not supported by HAL.", new Object[0]);
            return 0;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetPhysicalAddress() {
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).getPhysicalAddress(this);
                return this.mPhysicalAddress;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get physical address : ", new Object[0]);
                return GnssNative.GNSS_AIDING_TYPE_ALL;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final HdmiPortInfo[] nativeGetPortInfos() {
            try {
                ArrayList portInfo = ((IHdmiCec.Proxy) this.mHdmiCec).getPortInfo();
                HdmiPortInfo[] hdmiPortInfoArr = new HdmiPortInfo[portInfo.size()];
                Iterator it = portInfo.iterator();
                int i = 0;
                while (it.hasNext()) {
                    android.hardware.tv.cec.V1_0.HdmiPortInfo hdmiPortInfo = (android.hardware.tv.cec.V1_0.HdmiPortInfo) it.next();
                    hdmiPortInfoArr[i] = new HdmiPortInfo.Builder(hdmiPortInfo.portId, hdmiPortInfo.type, Short.toUnsignedInt(hdmiPortInfo.physicalAddress)).setCecSupported(hdmiPortInfo.cecSupported).setMhlSupported(false).setArcSupported(hdmiPortInfo.arcSupported).setEarcSupported(false).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get port information : ", new Object[0]);
                return null;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVendorId() {
            try {
                return ((IHdmiCec.Proxy) this.mHdmiCec).getVendorId();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get vendor id : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVersion() {
            try {
                return ((IHdmiCec.Proxy) this.mHdmiCec).getCecVersion();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get cec version : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final String nativeInit() {
            if (connectToHal()) {
                return this.mHdmiCec.toString();
            }
            return null;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final boolean nativeIsConnected(int i) {
            try {
                return ((IHdmiCec.Proxy) this.mHdmiCec).isConnected(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get connection info : ", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            android.hardware.tv.cec.V1_1.CecMessage cecMessage = new android.hardware.tv.cec.V1_1.CecMessage();
            cecMessage.initiator = 0;
            cecMessage.destination = 0;
            cecMessage.body = new ArrayList();
            cecMessage.initiator = i;
            cecMessage.destination = i2;
            cecMessage.body = new ArrayList(bArr.length);
            for (byte b : bArr) {
                cecMessage.body.add(Byte.valueOf(b));
            }
            try {
                return ((IHdmiCec.Proxy) this.mHdmiCec).sendMessage_1_1(cecMessage);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to send CEC message : ", new Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetHpdSignalType(int i, int i2) {
            HdmiLogger.error("Failed to set HPD signal type: not supported by HAL.", new Object[0]);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetLanguage(String str) {
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).setLanguage(str);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to set language : ", new Object[0]);
            }
        }

        public final void nativeSetOption(int i, boolean z) {
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).setOption(i, z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to set option : ", new Object[0]);
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback
        public final void onValues(int i, short s) {
            if (i == 0) {
                synchronized (this.mLock) {
                    this.mPhysicalAddress = new Short(s).intValue();
                }
            }
        }

        public final void serviceDied(long j) {
            if (j == 353) {
                HdmiLogger.error("Service died cookie : " + j + "; reconnecting", new Object[0]);
                connectToHal();
                HdmiCecCallback hdmiCecCallback = this.mCallback;
                if (hdmiCecCallback != null) {
                    setCallback(hdmiCecCallback);
                }
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void setCallback(HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                ((IHdmiCec.Proxy) this.mHdmiCec).setCallback_1_1(new HdmiCecCallback10(hdmiCecCallback, 1));
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Couldn't initialise tv.cec callback : ", new Object[0]);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NativeWrapperImplAidl implements NativeWrapper, IBinder.DeathRecipient {
        public HdmiCecCallback mCallback;
        public android.hardware.tv.hdmi.cec.IHdmiCec mHdmiCec;
        public IHdmiConnection mHdmiConnection;
        public final Object mLock = new Object();

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            ((IHdmiCec.Stub.Proxy) this.mHdmiCec).mRemote.unlinkToDeath(this, 0);
            ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).mRemote.unlinkToDeath(this, 0);
            HdmiLogger.error("HDMI Connection or CEC service died, reconnecting", new Object[0]);
            connectToHal();
            HdmiCecCallback hdmiCecCallback = this.mCallback;
            if (hdmiCecCallback != null) {
                setCallback(hdmiCecCallback);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v3, types: [android.hardware.tv.hdmi.cec.IHdmiCec] */
        public final boolean connectToHal() {
            IHdmiCec.Stub.Proxy proxy;
            StringBuilder sb = new StringBuilder();
            String str = android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR;
            sb.append(str);
            sb.append("/default");
            IBinder service = ServiceManager.getService(sb.toString());
            int i = IHdmiCec.Stub.$r8$clinit;
            IHdmiConnection iHdmiConnection = null;
            if (service == null) {
                proxy = null;
            } else {
                IInterface queryLocalInterface = service.queryLocalInterface(str);
                if (queryLocalInterface == null || !(queryLocalInterface instanceof android.hardware.tv.hdmi.cec.IHdmiCec)) {
                    IHdmiCec.Stub.Proxy proxy2 = new IHdmiCec.Stub.Proxy();
                    proxy2.mRemote = service;
                    proxy = proxy2;
                } else {
                    proxy = (android.hardware.tv.hdmi.cec.IHdmiCec) queryLocalInterface;
                }
            }
            this.mHdmiCec = proxy;
            if (proxy == null) {
                HdmiLogger.error("Could not initialize HDMI CEC AIDL HAL", new Object[0]);
                return false;
            }
            try {
                proxy.mRemote.linkToDeath(this, 0);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Couldn't link to death : ", new Object[0]);
            }
            StringBuilder sb2 = new StringBuilder();
            String str2 = IHdmiConnection.DESCRIPTOR;
            sb2.append(str2);
            sb2.append("/default");
            IBinder service2 = ServiceManager.getService(sb2.toString());
            int i2 = IHdmiConnection.Stub.$r8$clinit;
            if (service2 != null) {
                IInterface queryLocalInterface2 = service2.queryLocalInterface(str2);
                if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IHdmiConnection)) {
                    IHdmiConnection.Stub.Proxy proxy3 = new IHdmiConnection.Stub.Proxy();
                    proxy3.mRemote = service2;
                    iHdmiConnection = proxy3;
                } else {
                    iHdmiConnection = (IHdmiConnection) queryLocalInterface2;
                }
            }
            this.mHdmiConnection = iHdmiConnection;
            if (iHdmiConnection == null) {
                HdmiLogger.error("Could not initialize HDMI Connection AIDL HAL", new Object[0]);
                return false;
            }
            try {
                ((IHdmiConnection.Stub.Proxy) iHdmiConnection).mRemote.linkToDeath(this, 0);
                return true;
            } catch (RemoteException e2) {
                HdmiLogger.error(e2, "Couldn't link to death : ", new Object[0]);
                return true;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableCec(boolean z) {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).enableCec(z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed call to enableCec : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableSystemCecControl(boolean z) {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).enableSystemCecControl(z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed call to enableSystemCecControl : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void enableWakeupByOtp(boolean z) {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).enableWakeupByOtp(z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed call to enableWakeupByOtp : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeAddLogicalAddress(int i) {
            try {
                return ((IHdmiCec.Stub.Proxy) this.mHdmiCec).addLogicalAddress((byte) i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to add a logical address : ", new Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeClearLogicalAddress() {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).clearLogicalAddress();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to clear logical address : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).enableAudioReturnChannel(i, z);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to enable/disable ARC : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetHpdSignalType(int i) {
            try {
                return ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).getHpdSignal(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Could not get HPD signal type for portId ", ". Exception: "), new Object[0]);
                return 0;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetPhysicalAddress() {
            try {
                return ((IHdmiCec.Stub.Proxy) this.mHdmiCec).getPhysicalAddress();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get physical address : ", new Object[0]);
                return GnssNative.GNSS_AIDING_TYPE_ALL;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final HdmiPortInfo[] nativeGetPortInfos() {
            try {
                android.hardware.tv.hdmi.connection.HdmiPortInfo[] portInfo = ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).getPortInfo();
                HdmiPortInfo[] hdmiPortInfoArr = new HdmiPortInfo[portInfo.length];
                int i = 0;
                for (android.hardware.tv.hdmi.connection.HdmiPortInfo hdmiPortInfo : portInfo) {
                    hdmiPortInfoArr[i] = new HdmiPortInfo.Builder(hdmiPortInfo.portId, hdmiPortInfo.type, hdmiPortInfo.physicalAddress).setCecSupported(hdmiPortInfo.cecSupported).setMhlSupported(false).setArcSupported(hdmiPortInfo.arcSupported).setEarcSupported(hdmiPortInfo.eArcSupported).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get port information : ", new Object[0]);
                return null;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVendorId() {
            try {
                return ((IHdmiCec.Stub.Proxy) this.mHdmiCec).getVendorId();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get vendor id : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeGetVersion() {
            try {
                return ((IHdmiCec.Stub.Proxy) this.mHdmiCec).getCecVersion();
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get cec version : ", new Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final String nativeInit() {
            if (!connectToHal()) {
                return null;
            }
            return this.mHdmiCec.toString() + " " + this.mHdmiConnection.toString();
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final boolean nativeIsConnected(int i) {
            try {
                return ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).isConnected(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to get connection info : ", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            CecMessage cecMessage = new CecMessage();
            cecMessage.initiator = (byte) (i & 15);
            cecMessage.destination = (byte) (i2 & 15);
            cecMessage.body = bArr;
            try {
                return ((IHdmiCec.Stub.Proxy) this.mHdmiCec).sendMessage(cecMessage);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to send CEC message : ", new Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetHpdSignalType(int i, int i2) {
            try {
                ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).setHpdSignal((byte) i, i2);
            } catch (RemoteException e) {
                HdmiLogger.error(e, DualAppManagerService$$ExternalSyntheticOutline0.m(i2, i, "Could not set HPD signal type for portId ", " to ", ". Exception: "), new Object[0]);
            } catch (ServiceSpecificException e2) {
                HdmiLogger.error(DualAppManagerService$$ExternalSyntheticOutline0.m(i2, i, "Could not set HPD signal type for portId ", " to ", ". Error: "), Integer.valueOf(e2.errorCode));
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void nativeSetLanguage(String str) {
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).setLanguage(str);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Failed to set language : ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public final void setCallback(HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                ((IHdmiCec.Stub.Proxy) this.mHdmiCec).setCallback(new HdmiCecCallbackAidl(hdmiCecCallback));
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Couldn't initialise tv.cec callback : ", new Object[0]);
            }
            try {
                ((IHdmiConnection.Stub.Proxy) this.mHdmiConnection).setCallback(new HdmiConnectionCallbackAidl(hdmiCecCallback));
            } catch (RemoteException e2) {
                HdmiLogger.error(e2, "Couldn't initialise tv.hdmi callback : ", new Object[0]);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.hdmi.HdmiCecController$1] */
    public HdmiCecController(HdmiControlService hdmiControlService, NativeWrapper nativeWrapper, HdmiCecAtomWriter hdmiCecAtomWriter) {
        this.mService = hdmiControlService;
        this.mNativeWrapperImpl = nativeWrapper;
        this.mHdmiCecAtomWriter = hdmiCecAtomWriter;
    }

    public static HdmiCecController createWithNativeWrapper(HdmiControlService hdmiControlService, NativeWrapper nativeWrapper, HdmiCecAtomWriter hdmiCecAtomWriter) {
        HdmiCecController hdmiCecController = new HdmiCecController(hdmiControlService, nativeWrapper, hdmiCecAtomWriter);
        if (nativeWrapper.nativeInit() == null) {
            HdmiLogger.warning("Couldn't get tv.cec service.", new Object[0]);
            return null;
        }
        HdmiControlService hdmiControlService2 = hdmiCecController.mService;
        hdmiCecController.mIoHandler = new Handler(hdmiControlService2.getIoLooper());
        hdmiCecController.mControlHandler = new Handler(hdmiControlService2.mHandler.getLooper());
        nativeWrapper.setCallback(hdmiCecController.new HdmiCecCallback());
        return hdmiCecController;
    }

    public static boolean isLanguage(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                new ULocale.Builder().setLanguage(str);
                return true;
            } catch (IllformedLocaleException unused) {
            }
        }
        return false;
    }

    public final void addEventToHistory(Dumpable dumpable) {
        synchronized (this.mMessageHistoryLock) {
            try {
                if (!this.mMessageHistory.offer(dumpable)) {
                    this.mMessageHistory.poll();
                    this.mMessageHistory.offer(dumpable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void assertRunOnServiceThread() {
        if (Looper.myLooper() != this.mControlHandler.getLooper()) {
            throw new IllegalStateException("Should run on service thread.");
        }
    }

    public final void enableCec(boolean z) {
        assertRunOnServiceThread();
        HdmiLogger.debug("enableCec: %b", Boolean.valueOf(z));
        this.mNativeWrapperImpl.enableCec(z);
    }

    public final void enableSystemCecControl(boolean z) {
        assertRunOnServiceThread();
        HdmiLogger.debug("enableSystemCecControl: %b", Boolean.valueOf(z));
        this.mNativeWrapperImpl.enableSystemCecControl(z);
    }

    public final void maySendFeatureAbortCommand(int i, HdmiCecMessage hdmiCecMessage) {
        int i2;
        int i3;
        assertRunOnServiceThread();
        int i4 = hdmiCecMessage.mDestination;
        if (i4 == 15 || (i2 = hdmiCecMessage.mSource) == 15 || (i3 = hdmiCecMessage.mOpcode) == 0) {
            return;
        }
        HdmiCecMessage build = HdmiCecMessage.build(i4, i2, 0, new byte[]{(byte) (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT), (byte) (i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)});
        assertRunOnServiceThread();
        assertRunOnServiceThread();
        ArrayList arrayList = new ArrayList();
        runOnIoThread(new AnonymousClass7(build, arrayList, null));
        assertRunOnServiceThread();
        addEventToHistory(new MessageHistoryRecord(false, build, arrayList));
    }

    public void onReceiveCommand(HdmiCecMessage hdmiCecMessage) {
        int i;
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (!hdmiControlService.isCecControlEnabled() && (i = hdmiCecMessage.mOpcode) != 167 && i != 168 && i != 248) {
            HdmiLogger.warning("Message " + hdmiCecMessage + " received when cec disabled", new Object[0]);
        }
        if (hdmiControlService.mAddressAllocated) {
            int i2 = hdmiCecMessage.mDestination;
            if (!(i2 == 15 ? true : hdmiControlService.mHdmiCecNetwork.isAllocatedLocalDeviceAddress(i2))) {
                return;
            }
        }
        int handleCecCommand = hdmiControlService.handleCecCommand(hdmiCecMessage);
        if (handleCecCommand == -2) {
            maySendFeatureAbortCommand(0, hdmiCecMessage);
        } else if (handleCecCommand != -1) {
            maySendFeatureAbortCommand(handleCecCommand, hdmiCecMessage);
        }
    }

    public final void runDevicePolling(final int i, final List list, final int i2, final HdmiControlService.DevicePollingCallback devicePollingCallback, final List list2, final long j, boolean z) {
        assertRunOnServiceThread();
        if (!list.isEmpty()) {
            final Integer num = (Integer) list.remove(0);
            this.mIoHandler.postDelayed(new Runnable() { // from class: com.android.server.hdmi.HdmiCecController.5
                @Override // java.lang.Runnable
                public final void run() {
                    if (HdmiCecController.this.sendPollMessage(i, num.intValue(), i2)) {
                        list2.add(num);
                    }
                    HdmiCecController.this.runOnServiceThread(new Runnable() { // from class: com.android.server.hdmi.HdmiCecController.5.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                            HdmiCecController hdmiCecController = HdmiCecController.this;
                            int i3 = i;
                            List list3 = list;
                            int i4 = i2;
                            HdmiControlService.DevicePollingCallback devicePollingCallback2 = devicePollingCallback;
                            List list4 = list2;
                            long j2 = j;
                            byte[] bArr = HdmiCecController.EMPTY_BODY;
                            hdmiCecController.runDevicePolling(i3, list3, i4, devicePollingCallback2, list4, j2, true);
                        }
                    });
                }
            }, z ? j : 0L);
        } else if (devicePollingCallback != null) {
            HdmiLogger.debug("[P]:AllocatedAddress=%s", list2.toString());
            devicePollingCallback.onPollingFinished(list2);
        }
    }

    public void runOnIoThread(Runnable runnable) {
        this.mIoHandler.post(new WorkSourceUidPreservingRunnable(runnable));
    }

    public void runOnServiceThread(Runnable runnable) {
        this.mControlHandler.post(new WorkSourceUidPreservingRunnable(runnable));
    }

    public final boolean sendPollMessage(int i, int i2, int i3) {
        if (Looper.myLooper() != this.mIoHandler.getLooper()) {
            throw new IllegalStateException("Should run on io thread.");
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int nativeSendCecCommand = this.mNativeWrapperImpl.nativeSendCecCommand(i, i2, EMPTY_BODY);
            if (nativeSendCecCommand == 0) {
                return true;
            }
            if (nativeSendCecCommand != 1) {
                HdmiLogger.warning("Failed to send a polling message(%d->%d) with return code %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(nativeSendCecCommand));
            }
        }
        return false;
    }

    public void setLogicalAddressAllocationDelay(long j) {
        this.mLogicalAddressAllocationDelay = j;
    }

    public void setPollDevicesDelay(long j) {
        this.mPollDevicesDelay = j;
    }
}
