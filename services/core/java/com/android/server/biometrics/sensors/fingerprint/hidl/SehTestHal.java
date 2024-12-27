package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.Handler;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;

import com.att.iqi.lib.metrics.mm.MM05;

import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SehTestHal extends HwBinder implements ISehBiometricsFingerprint {
    public final SparseLongArray mAuthenticatorID;
    public IBiometricsFingerprintClientCallback mCallback;
    public final Context mContext;
    public CountDownLatch mCountDownLatch;
    public int mCurrentEnrollmentId;
    public int mCurrentUserID;
    public final long mDeviceId;
    public final Set mEnrollmentIds;
    public final Handler mH;
    public final boolean mIsTpaMode;
    public final Random mRandom;
    public final SparseIntArray mRequestActionTable;
    public final TestHal mTestHal;
    public final SemTestHalHelper mTestHalHelper;
    public final AtomicBoolean mIsCancellation = new AtomicBoolean(false);
    public final ArrayList mSehRequestOutput = new ArrayList();

    public SehTestHal(Context context, int i, boolean z) {
        this.mContext = context;
        this.mIsTpaMode = z;
        this.mTestHal = new TestHal(context, i);
        Random random = new Random();
        this.mRandom = random;
        this.mAuthenticatorID = new SparseLongArray();
        this.mDeviceId = random.nextLong();
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mRequestActionTable = sparseIntArray;
        this.mEnrollmentIds = new HashSet();
        if (z) {
            this.mTestHalHelper =
                    new SemTestHalHelper(
                            2,
                            new SemTestHalHelper.Callback() { // from class:
                                // com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal.1
                                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                                public final void deliverAcquiredEvent(int i2, int i3) {
                                    SehTestHal sehTestHal = SehTestHal.this;
                                    if (sehTestHal.mIsTpaMode) {
                                        try {
                                            sehTestHal.mCallback.onAcquired(
                                                    i2, i3, sehTestHal.mDeviceId);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                                public final void deliverAuthenticationResult(int i2) {
                                    SehTestHal sehTestHal = SehTestHal.this;
                                    if (sehTestHal.mIsTpaMode) {
                                        try {
                                            if (((HashSet) sehTestHal.mEnrollmentIds).isEmpty()) {
                                                sehTestHal.deliverErrorEvent(8, 1004);
                                                return;
                                            }
                                            if (i2 != 0) {
                                                i2 =
                                                        ((Integer)
                                                                        ((HashSet)
                                                                                        sehTestHal
                                                                                                .mEnrollmentIds)
                                                                                .iterator()
                                                                                .next())
                                                                .intValue();
                                            }
                                            sehTestHal.mCallback.onAuthenticated(
                                                    sehTestHal.mDeviceId,
                                                    i2,
                                                    sehTestHal.mCurrentUserID,
                                                    new ArrayList(
                                                            Collections.nCopies(69, (byte) 0)));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                                public final void deliverEnrollResult(int i2) {
                                    SehTestHal sehTestHal = SehTestHal.this;
                                    if (sehTestHal.mIsTpaMode) {
                                        try {
                                            sehTestHal.mCallback.onEnrollResult(
                                                    sehTestHal.mCurrentEnrollmentId,
                                                    sehTestHal.mCurrentUserID,
                                                    i2,
                                                    sehTestHal.mDeviceId);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                                public final void deliverErrorEvent(int i2, int i3) {
                                    SehTestHal.this.deliverErrorEvent(i2, i3);
                                }

                                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                                public final void deliverTspEvent(int i2) {
                                    SehTestHal sehTestHal = SehTestHal.this;
                                    sehTestHal.getClass();
                                    Intent intent =
                                            new Intent(
                                                    "com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
                                    if (i2 == 2) {
                                        intent.putExtra("info", 15);
                                    } else if (i2 == 1) {
                                        intent.putExtra("info", 16);
                                    }
                                    try {
                                        sehTestHal.mContext.sendBroadcast(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
            ServiceThread serviceThread =
                    new ServiceThread(-2, "fingerprint.hidl.SehTestHal", true);
            serviceThread.start();
            Handler threadHandler = serviceThread.getThreadHandler();
            this.mH = threadHandler;
            threadHandler.post(new SehTestHal$$ExternalSyntheticLambda1(this, 1));
        } else {
            sparseIntArray.clear();
            sparseIntArray.put(6, 100040);
        }
        if (Utils.DEBUG) {
            Slog.d("fingerprint.hidl.SehTestHal", "SehTestHal: constructed, " + i + ", " + z);
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int authenticate(final int i, long j) {
        if (!this.mIsTpaMode) {
            this.mTestHal.authenticate(i, j);
            return 0;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(i, "authenticateTPA: ", ", "),
                this.mCurrentUserID,
                "fingerprint.hidl.SehTestHal");
        if (this.mCurrentUserID != i) {
            return -1;
        }
        long j2 = this.mTestHalHelper.mDelayAuthAction;
        Handler handler = this.mH;
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SehTestHal sehTestHal = SehTestHal.this;
                        int i2 = i;
                        if (((HashSet) sehTestHal.mEnrollmentIds).isEmpty()) {
                            sehTestHal.deliverErrorEvent(8, 1004);
                            return;
                        }
                        List list = sehTestHal.mTestHalHelper.mAuthActionList;
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i2, "start authenticateTPA: ", ", action size = ");
                        ArrayList arrayList = (ArrayList) list;
                        m.append(arrayList.size());
                        Slog.d("fingerprint.hidl.SehTestHal", m.toString());
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            SemTestHalHelper.Action action = (SemTestHalHelper.Action) it.next();
                            if (sehTestHal.mIsCancellation.get()) {
                                return;
                            }
                            try {
                                Thread.sleep(action.delay);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            action.run();
                            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
                                    && action.callbackType == SemTestHalHelper.CallbackType.TSP_FOD
                                    && action.value == 2) {
                                CountDownLatch countDownLatch = new CountDownLatch(1);
                                sehTestHal.mCountDownLatch = countDownLatch;
                                try {
                                    countDownLatch.await(10000L, TimeUnit.MICROSECONDS);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                };
        if (j2 == 0) {
            j2 = 600;
        }
        handler.postDelayed(runnable, j2);
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int cancel() {
        if (!this.mIsTpaMode) {
            this.mTestHal.cancel();
            return 0;
        }
        this.mIsCancellation.set(true);
        this.mH.post(new SehTestHal$$ExternalSyntheticLambda1(this, 2));
        return 0;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    public final void deliverErrorEvent(int i, int i2) {
        if (this.mIsTpaMode) {
            try {
                this.mCallback.onError(i, i2, this.mDeviceId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int enroll(int i, int i2, byte[] bArr) {
        if (!this.mIsTpaMode) {
            this.mTestHal.enroll(i, i2, bArr);
            return 0;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(i, "enrollTPA: ", ", "),
                this.mCurrentUserID,
                "fingerprint.hidl.SehTestHal");
        if (this.mCurrentUserID != i) {
            return -1;
        }
        this.mH.postDelayed(new SehTestHal$$ExternalSyntheticLambda1(this, 0), 600L);
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int enumerate() {
        this.mTestHal.enumerate();
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long getAuthenticatorId() {
        if (!this.mIsTpaMode) {
            this.mTestHal.getClass();
            return 0L;
        }
        if (((HashSet) this.mEnrollmentIds).isEmpty()) {
            return 0L;
        }
        return this.mAuthenticatorID.get(this.mCurrentUserID);
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
        return new ArrayList(
                Arrays.asList(
                        new byte[] {
                            33,
                            53,
                            87,
                            -37,
                            82,
                            -4,
                            -14,
                            -53,
                            -86,
                            25,
                            -63,
                            -101,
                            -69,
                            -81,
                            -124,
                            -26,
                            -7,
                            97,
                            -47,
                            118,
                            -50,
                            84,
                            15,
                            -74,
                            -75,
                            74,
                            -36,
                            -79,
                            55,
                            82,
                            MM05.IQ_SIP_CALL_STATE_DISCONNECTING,
                            35
                        },
                        new byte[] {
                            31, -67, -63, -8, 82, -8, -67, 46, 74, 108, 92, -77, 10, -62, -73, -122,
                            104, -55, -115, -50, 17, -118, 97, 118, 45, 64, 52, -82, -123, -97, 67,
                            -40
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
    public final ArrayList interfaceChain() {
        return new ArrayList(
                Arrays.asList(
                        "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint",
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint",
                        IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        return "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwBinder.enableInstrumentation();
    }

    public final void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) {
        switch (i) {
            case 1:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                setNotify(
                        IBiometricsFingerprintClientCallback.asInterface(
                                hwParcel.readStrongBinder()));
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(1L);
                hwParcel2.send();
                return;
            case 2:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                long preEnroll = preEnroll();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(preEnroll);
                hwParcel2.send();
                return;
            case 3:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                byte[] bArr = new byte[69];
                hwParcel.readBuffer(69L).copyToInt8Array(0L, bArr, 69);
                int enroll = enroll(hwParcel.readInt32(), hwParcel.readInt32(), bArr);
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(enroll);
                hwParcel2.send();
                return;
            case 4:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                postEnroll();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 5:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                long authenticatorId = getAuthenticatorId();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(authenticatorId);
                hwParcel2.send();
                return;
            case 6:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                cancel();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 7:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                enumerate();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 8:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                remove(hwParcel.readInt32(), hwParcel.readInt32());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 9:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                setActiveGroup(hwParcel.readInt32(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 10:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                int authenticate = authenticate(hwParcel.readInt32(), hwParcel.readInt64());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(authenticate);
                hwParcel2.send();
                return;
            case 11:
                hwParcel.enforceInterface(
                        "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint");
                sehRequest(
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt8Vector(),
                        new ISehBiometricsFingerprint
                                .sehRequestCallback() { // from class:
                                                        // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint$Stub$1
                            @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint.sehRequestCallback
                            public final void onValues(ArrayList arrayList, int i3) {
                                hwParcel2.writeStatus(0);
                                hwParcel2.writeInt32(i3);
                                hwParcel2.writeInt8Vector(arrayList);
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
                        hwParcel.readNativeHandle();
                        hwParcel.readStringVector();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 256136003:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeString(
                                "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint");
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
                            byte[] bArr2 = (byte[]) hashChain.get(i3);
                            if (bArr2 == null || bArr2.length != 32) {
                                throw new IllegalArgumentException(
                                        "Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j, bArr2);
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

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {}

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int postEnroll() {
        if (this.mIsTpaMode) {
            return 0;
        }
        this.mTestHal.getClass();
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long preEnroll() {
        if (this.mIsTpaMode) {
            return this.mRandom.nextLong();
        }
        this.mTestHal.getClass();
        return 0L;
    }

    public final IHwInterface queryLocalInterface(String str) {
        if ("vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint"
                .equals(str)) {
            return this;
        }
        return null;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int remove(int i, int i2) {
        this.mTestHal.remove(i, i2);
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint
    public final void sehRequest(
            int i,
            int i2,
            ArrayList arrayList,
            ISehBiometricsFingerprint.sehRequestCallback sehrequestcallback) {
        CountDownLatch countDownLatch;
        PendingIntentController$$ExternalSyntheticOutline0.m(
                i, i2, "sehRequest: ", ", ", "fingerprint.hidl.SehTestHal");
        sehrequestcallback.onValues(this.mSehRequestOutput, this.mRequestActionTable.get(i, 0));
        this.mSehRequestOutput.clear();
        if (i == 22 && i2 == 2 && (countDownLatch = this.mCountDownLatch) != null) {
            countDownLatch.countDown();
        }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int setActiveGroup(int i, String str) {
        this.mCurrentUserID = i;
        this.mTestHal.getClass();
        return 0;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long setNotify(
            IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback) {
        this.mCallback = iBiometricsFingerprintClientCallback;
        this.mTestHal.mCallback = iBiometricsFingerprintClientCallback;
        return 1L;
    }

    public final String toString() {
        return "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint@Stub";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return true;
    }
}
