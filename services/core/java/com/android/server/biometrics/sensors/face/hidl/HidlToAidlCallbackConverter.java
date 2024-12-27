package com.android.server.biometrics.sensors.face.hidl;

import android.hardware.face.IFaceServiceReceiver;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.Bundle;
import android.os.HidlMemory;
import android.os.HidlMemoryUtil;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.MemoryFile;
import android.os.NativeHandle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFaceClientCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HidlToAidlCallbackConverter extends HwBinder implements ISehBiometricsFaceClientCallback {
    public final AidlResponseHandler mAidlResponseHandler;

    public HidlToAidlCallbackConverter(AidlResponseHandler aidlResponseHandler) {
        this.mAidlResponseHandler = aidlResponseHandler;
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
        return new ArrayList(Arrays.asList(new byte[]{-109, 91, -14, 67, 67, 94, 108, -16, -118, 36, 28, -61, -21, -27, -22, -106, -25, -94, -39, 104, -50, 98, 26, -78, 71, 104, -80, -92, 47, 74, 81, -73}, new byte[]{7, -105, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -41, -109, 10, -37, -16, 7, 84, -68, -123, -102, 31, -43, -64, 113, -104, 15, -96, -117, 53, 40, -51, -29, -57, -101, -68, -11, -115, -14, 74}, new byte[]{-74, -27, 93, 119, -107, -69, -81, -48, 17, -5, -107, -93, -74, -45, -107, 75, -10, 108, 52, -98, 20, -49, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, Byte.MAX_VALUE, 59, 114, 3, 44, -29, -50, -76, 72}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
        return new ArrayList(Arrays.asList("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback", "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback", "android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        return "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwBinder.enableInstrumentation();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onAcquired(int i, final int i2, final int i3, long j) {
        AidlResponseHandler aidlResponseHandler = this.mAidlResponseHandler;
        aidlResponseHandler.getClass();
        Slog.d("AidlResponseHandler", "onAcquired");
        aidlResponseHandler.handleResponse(AcquisitionClient.class, new Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AcquisitionClient) obj).onAcquired(i2, i3);
            }
        }, null);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onAuthenticated(long j, int i, int i2, ArrayList arrayList) {
        boolean z = i != 0;
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            bArr[i3] = ((Byte) arrayList.get(i3)).byteValue();
        }
        if (!z) {
            this.mAidlResponseHandler.onAuthenticationFailed();
        } else if (size > 1) {
            this.mAidlResponseHandler.onAuthenticationSucceeded(i, HardwareAuthTokenUtils.toHardwareAuthToken(bArr));
        } else {
            this.mAidlResponseHandler.onAuthenticationSucceeded(i, null);
        }
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onEnrollResult(int i, int i2, int i3, long j) {
        this.mAidlResponseHandler.onEnrollmentProgress(i, i3);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onEnumerate(int i, long j, ArrayList arrayList) {
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        this.mAidlResponseHandler.onEnrollmentsEnumerated(iArr);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onError(int i, int i2, int i3, long j) {
        this.mAidlResponseHandler.onError(i2, i3);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public final void onRemoved(int i, long j, ArrayList arrayList) {
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        this.mAidlResponseHandler.onEnrollmentsRemoved(iArr);
    }

    public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
        int i3 = 0;
        switch (i) {
            case 1:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onEnrollResult(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt64());
                return;
            case 2:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onAuthenticated(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt8Vector());
                return;
            case 3:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onAcquired(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt64());
                return;
            case 4:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onError(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt64());
                return;
            case 5:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onRemoved(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32Vector());
                return;
            case 6:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                onEnumerate(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32Vector());
                return;
            case 7:
                hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                final long readInt64 = hwParcel.readInt64();
                final AidlResponseHandler aidlResponseHandler = this.mAidlResponseHandler;
                aidlResponseHandler.getClass();
                Slog.d("AidlResponseHandler", "onLockoutChanged");
                aidlResponseHandler.mScheduler.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda23
                    @Override // java.lang.Runnable
                    public final void run() {
                        AidlResponseHandler aidlResponseHandler2 = AidlResponseHandler.this;
                        long j = readInt64;
                        aidlResponseHandler2.getClass();
                        aidlResponseHandler2.mLockoutTracker.setLockoutModeForUser(aidlResponseHandler2.mUserId, j == 0 ? 0 : (j == -1 || j == Long.MAX_VALUE) ? 2 : 1);
                        if (j == 0) {
                            aidlResponseHandler2.mLockoutResetDispatcher.notifyLockoutResetCallbacks(aidlResponseHandler2.mSensorId);
                        }
                    }
                });
                return;
            case 8:
                hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                ArrayList readInt8Vector = hwParcel.readInt8Vector();
                final int readInt32 = hwParcel.readInt32();
                final int readInt322 = hwParcel.readInt32();
                final int readInt323 = hwParcel.readInt32();
                final int readInt324 = hwParcel.readInt32();
                this.mAidlResponseHandler.getClass();
                final SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.getInstance();
                semFaceServiceExImpl.getClass();
                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(readInt32, readInt322, "sehOnPreviewUpdated: ", ",", ",");
                m.append(readInt323);
                m.append(",");
                m.append(readInt324);
                Slog.d("SemFace", m.toString());
                if (!semFaceServiceExImpl.mIsOperationStarted) {
                    Slog.d("SemFace", "sehOnPreviewUpdated: skip after stop");
                } else if (readInt8Vector == null || readInt8Vector.size() == 0) {
                    Slog.d("SemFace", "sehOnPreviewUpdated: preview data is null or size is 0");
                } else if (semFaceServiceExImpl.mHIDLpreviewImage != null) {
                    Slog.d("SemFace", "sehOnPreviewUpdated: previous preview is not processed yet");
                } else {
                    semFaceServiceExImpl.mHIDLpreviewImage = new byte[readInt8Vector.size()];
                    for (int i4 = 0; i4 < readInt8Vector.size(); i4++) {
                        semFaceServiceExImpl.mHIDLpreviewImage[i4] = ((Byte) readInt8Vector.get(i4)).byteValue();
                    }
                    semFaceServiceExImpl.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFaceServiceExImpl semFaceServiceExImpl2 = SemFaceServiceExImpl.this;
                            int i5 = readInt32;
                            int i6 = readInt322;
                            int i7 = readInt324;
                            int i8 = readInt323;
                            BaseClientMonitor currentClient = semFaceServiceExImpl2.mScheduler.getCurrentClient();
                            if (currentClient == null) {
                                Slog.d("SemFace", "sendImageProcessed: client is null");
                                return;
                            }
                            if (!(currentClient instanceof FaceEnrollClient) && (!(currentClient instanceof FaceAuthenticationClient) || !semFaceServiceExImpl2.mIsAuthenticationExtOperation)) {
                                Slog.e("SemFace", "sendImageProcessed : Wrong Client : " + Utils.getClientName(currentClient) + ", Proto=" + currentClient.getProtoEnum() + ", ext=" + semFaceServiceExImpl2.mIsAuthenticationExtOperation);
                                semFaceServiceExImpl2.mHIDLpreviewImage = null;
                                return;
                            }
                            Bundle bundle = new Bundle();
                            try {
                                if (semFaceServiceExImpl2.mHIDLmemoryFileForPreview == null) {
                                    semFaceServiceExImpl2.mHIDLmemoryFileForPreview = new MemoryFile("face_enroll_preview", semFaceServiceExImpl2.mHIDLpreviewImage.length);
                                }
                                MemoryFile memoryFile = semFaceServiceExImpl2.mHIDLmemoryFileForPreview;
                                byte[] bArr = semFaceServiceExImpl2.mHIDLpreviewImage;
                                memoryFile.writeBytes(bArr, 0, 0, bArr.length);
                                Class[] clsArr = new Class[0];
                                bundle.putParcelable("memoryfile_descriptor", ParcelFileDescriptor.dup((FileDescriptor) MemoryFile.class.getDeclaredMethod("getFileDescriptor", null).invoke(semFaceServiceExImpl2.mHIDLmemoryFileForPreview, null)));
                                semFaceServiceExImpl2.mHIDLpreviewImage = new byte[1];
                            } catch (Exception e) {
                                Log.e("SemFace", "sendImageProcessed MemoryFile: ", e);
                            }
                            try {
                                if (semFaceServiceExImpl2.mIsAuthenticationExtOperation) {
                                    byte[] bArr2 = semFaceServiceExImpl2.mHIDLpreviewImage;
                                    IFaceServiceReceiver iFaceServiceReceiver = ((FaceAuthenticationClient) currentClient).mListener.mFaceServiceReceiver;
                                    if (iFaceServiceReceiver != null) {
                                        iFaceServiceReceiver.onSemImageProcessed(bArr2, i5, i6, i7, i8, bundle);
                                    }
                                } else {
                                    byte[] bArr3 = semFaceServiceExImpl2.mHIDLpreviewImage;
                                    IFaceServiceReceiver iFaceServiceReceiver2 = ((FaceEnrollClient) currentClient).mListener.mFaceServiceReceiver;
                                    if (iFaceServiceReceiver2 != null) {
                                        iFaceServiceReceiver2.onSemImageProcessed(bArr3, i5, i6, i7, i8, bundle);
                                    }
                                }
                            } catch (Exception e2) {
                                Log.e("SemFace", "sendImageProcessed onImageProcessed: ", e2);
                            }
                            semFaceServiceExImpl2.mHIDLpreviewImage = null;
                        }
                    });
                }
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            case 9:
                hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                long readInt642 = hwParcel.readInt64();
                int readInt325 = hwParcel.readInt32();
                int readInt326 = hwParcel.readInt32();
                ArrayList readInt8Vector2 = hwParcel.readInt8Vector();
                ArrayList readInt8Vector3 = hwParcel.readInt8Vector();
                StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(readInt326, readInt325, "sehOnAuthenticated: [", "] ", ",");
                m2.append(readInt642);
                m2.append(",");
                m2.append(readInt8Vector3.size());
                Slog.d("", m2.toString());
                if (readInt8Vector3.size() > 0) {
                    SemFaceUtils.mFidoResultData = new byte[readInt8Vector3.size()];
                    while (i3 < readInt8Vector3.size()) {
                        SemFaceUtils.mFidoResultData[i3] = ((Byte) readInt8Vector3.get(i3)).byteValue();
                        i3++;
                    }
                    if (Utils.DEBUG) {
                        Log.i("SemFace", "fidoResultData(" + readInt8Vector3.size() + ") = " + Arrays.toString(SemFaceUtils.mFidoResultData));
                    }
                }
                onAuthenticated(readInt642, readInt325, readInt326, readInt8Vector2);
                return;
            case 10:
                hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                try {
                    HidlMemory dup = hwParcel.readHidlMemory().dup();
                    final int readInt327 = hwParcel.readInt32();
                    final int readInt328 = hwParcel.readInt32();
                    final int readInt329 = hwParcel.readInt32();
                    final int readInt3210 = hwParcel.readInt32();
                    this.mAidlResponseHandler.getClass();
                    final SemFaceServiceExImpl semFaceServiceExImpl2 = SemFaceServiceExImpl.getInstance();
                    semFaceServiceExImpl2.getClass();
                    StringBuilder m3 = ArrayUtils$$ExternalSyntheticOutline0.m(readInt327, readInt328, "sehOnPreviewFrame: ", ",", ",");
                    m3.append(readInt329);
                    m3.append(",");
                    m3.append(readInt3210);
                    Slog.d("SemFace", m3.toString());
                    if (!semFaceServiceExImpl2.mIsOperationStarted) {
                        Slog.d("SemFace", "sehOnPreviewUpdated: skip after stop");
                    } else if (dup == null) {
                        Slog.d("SemFace", "sehOnPreviewFrame: preview data is null");
                    } else if (semFaceServiceExImpl2.mHIDLpreviewImage != null) {
                        Slog.d("SemFace", "sehOnPreviewFrame: previous preview is not processed yet");
                    } else {
                        semFaceServiceExImpl2.mHIDLpreviewImage = HidlMemoryUtil.hidlMemoryToByteArray(dup);
                        semFaceServiceExImpl2.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemFaceServiceExImpl semFaceServiceExImpl22 = SemFaceServiceExImpl.this;
                                int i5 = readInt327;
                                int i6 = readInt328;
                                int i7 = readInt3210;
                                int i8 = readInt329;
                                BaseClientMonitor currentClient = semFaceServiceExImpl22.mScheduler.getCurrentClient();
                                if (currentClient == null) {
                                    Slog.d("SemFace", "sendImageProcessed: client is null");
                                    return;
                                }
                                if (!(currentClient instanceof FaceEnrollClient) && (!(currentClient instanceof FaceAuthenticationClient) || !semFaceServiceExImpl22.mIsAuthenticationExtOperation)) {
                                    Slog.e("SemFace", "sendImageProcessed : Wrong Client : " + Utils.getClientName(currentClient) + ", Proto=" + currentClient.getProtoEnum() + ", ext=" + semFaceServiceExImpl22.mIsAuthenticationExtOperation);
                                    semFaceServiceExImpl22.mHIDLpreviewImage = null;
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                try {
                                    if (semFaceServiceExImpl22.mHIDLmemoryFileForPreview == null) {
                                        semFaceServiceExImpl22.mHIDLmemoryFileForPreview = new MemoryFile("face_enroll_preview", semFaceServiceExImpl22.mHIDLpreviewImage.length);
                                    }
                                    MemoryFile memoryFile = semFaceServiceExImpl22.mHIDLmemoryFileForPreview;
                                    byte[] bArr = semFaceServiceExImpl22.mHIDLpreviewImage;
                                    memoryFile.writeBytes(bArr, 0, 0, bArr.length);
                                    Class[] clsArr = new Class[0];
                                    bundle.putParcelable("memoryfile_descriptor", ParcelFileDescriptor.dup((FileDescriptor) MemoryFile.class.getDeclaredMethod("getFileDescriptor", null).invoke(semFaceServiceExImpl22.mHIDLmemoryFileForPreview, null)));
                                    semFaceServiceExImpl22.mHIDLpreviewImage = new byte[1];
                                } catch (Exception e) {
                                    Log.e("SemFace", "sendImageProcessed MemoryFile: ", e);
                                }
                                try {
                                    if (semFaceServiceExImpl22.mIsAuthenticationExtOperation) {
                                        byte[] bArr2 = semFaceServiceExImpl22.mHIDLpreviewImage;
                                        IFaceServiceReceiver iFaceServiceReceiver = ((FaceAuthenticationClient) currentClient).mListener.mFaceServiceReceiver;
                                        if (iFaceServiceReceiver != null) {
                                            iFaceServiceReceiver.onSemImageProcessed(bArr2, i5, i6, i7, i8, bundle);
                                        }
                                    } else {
                                        byte[] bArr3 = semFaceServiceExImpl22.mHIDLpreviewImage;
                                        IFaceServiceReceiver iFaceServiceReceiver2 = ((FaceEnrollClient) currentClient).mListener.mFaceServiceReceiver;
                                        if (iFaceServiceReceiver2 != null) {
                                            iFaceServiceReceiver2.onSemImageProcessed(bArr3, i5, i6, i7, i8, bundle);
                                        }
                                    }
                                } catch (Exception e2) {
                                    Log.e("SemFace", "sendImageProcessed onImageProcessed: ", e2);
                                }
                                semFaceServiceExImpl22.mHIDLpreviewImage = null;
                            }
                        });
                    }
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case 11:
                hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback");
                long readInt643 = hwParcel.readInt64();
                final int readInt3211 = hwParcel.readInt32();
                int readInt3212 = hwParcel.readInt32();
                hwParcel.readInt8Vector();
                try {
                    final HidlMemory dup2 = hwParcel.readHidlMemory().dup();
                    this.mAidlResponseHandler.getClass();
                    final SemFaceServiceExImpl semFaceServiceExImpl3 = SemFaceServiceExImpl.getInstance();
                    semFaceServiceExImpl3.getClass();
                    BatteryService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(readInt3212, readInt3211, "sehOnAuthenticatedFromMemory: [", "] ", ","), readInt643, "SemFace");
                    if (!semFaceServiceExImpl3.mIsAuthenticationExtOperation) {
                        Slog.d("SemFace", "sehOnAuthenticatedFromMemory: auth preview mode is off");
                        return;
                    } else if (dup2 == null) {
                        Slog.d("SemFace", "sehOnAuthenticatedFromMemory: result data is null");
                        return;
                    } else {
                        semFaceServiceExImpl3.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemFaceServiceExImpl semFaceServiceExImpl4 = SemFaceServiceExImpl.this;
                                HidlMemory hidlMemory = dup2;
                                int i5 = readInt3211;
                                semFaceServiceExImpl4.getClass();
                                Bundle bundle = new Bundle();
                                byte[] hidlMemoryToByteArray = HidlMemoryUtil.hidlMemoryToByteArray(hidlMemory);
                                if (hidlMemoryToByteArray == null || hidlMemoryToByteArray.length <= 0) {
                                    Slog.i("SemFace", "sehOnAuthenticatedFromMemory data is null or 0");
                                } else {
                                    SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("sehOnAuthenticatedFromMemory read "), hidlMemoryToByteArray.length, "SemFace");
                                    if (Utils.DEBUG) {
                                        Slog.i("SemFace", "data = " + Arrays.toString(Arrays.copyOf(hidlMemoryToByteArray, hidlMemoryToByteArray.length <= 128 ? hidlMemoryToByteArray.length : 128)));
                                    }
                                    try {
                                        if (semFaceServiceExImpl4.mMemoryFileForAuthPreviewResult == null) {
                                            semFaceServiceExImpl4.mMemoryFileForAuthPreviewResult = new MemoryFile("auth_preview", hidlMemoryToByteArray.length);
                                        }
                                        semFaceServiceExImpl4.mMemoryFileForAuthPreviewResult.writeBytes(hidlMemoryToByteArray, 0, 0, hidlMemoryToByteArray.length);
                                        Class[] clsArr = new Class[0];
                                        bundle.putParcelable("memoryfile_descriptor", ParcelFileDescriptor.dup((FileDescriptor) MemoryFile.class.getDeclaredMethod("getFileDescriptor", null).invoke(semFaceServiceExImpl4.mMemoryFileForAuthPreviewResult, null)));
                                    } catch (Exception e2) {
                                        Log.w("SemFace", "Unable to write statistics stream", e2);
                                    }
                                    Slog.i("SemFace", "sehOnAuthenticatedFromMemory save");
                                }
                                if (i5 > 0) {
                                    semFaceServiceExImpl4.sendSucceeded(bundle);
                                } else if (i5 == 0) {
                                    semFaceServiceExImpl4.sendFailed();
                                } else {
                                    Slog.d("SemFace", "sehOnAuthenticated: faceId is less than 0");
                                }
                            }
                        });
                        return;
                    }
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
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
                        hwParcel2.writeString("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback");
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
                            byte[] bArr = (byte[]) hashChain.get(i3);
                            if (bArr == null || bArr.length != 32) {
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j, bArr);
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
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {
    }

    public final IHwInterface queryLocalInterface(String str) {
        if ("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback".equals(str)) {
            return this;
        }
        return null;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {
    }

    public final String toString() {
        return "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback@Stub";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return true;
    }
}
