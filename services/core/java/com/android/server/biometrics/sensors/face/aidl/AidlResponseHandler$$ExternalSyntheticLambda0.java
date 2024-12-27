package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.face.BaseFrame;
import android.hardware.biometrics.face.Cell;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.face.Face;
import android.hardware.face.FaceDataFrame;
import android.hardware.face.FaceEnrollCell;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceServiceReceiver;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        int i2 = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i2) {
            case 0:
                ((AuthenticationConsumer) obj).onAuthenticated((Face) obj2, false, null);
                break;
            case 1:
                AidlResponseHandler aidlResponseHandler = (AidlResponseHandler) obj2;
                int i3 = aidlResponseHandler.mSensorId;
                int i4 = aidlResponseHandler.mUserId;
                LockoutTracker lockoutTracker = aidlResponseHandler.mLockoutTracker;
                LockoutResetDispatcher lockoutResetDispatcher =
                        aidlResponseHandler.mLockoutResetDispatcher;
                aidlResponseHandler.mAuthSessionCoordinator.resetLockoutFor(
                        i4, Utils.getCurrentStrength(i3), -1L);
                lockoutTracker.setLockoutModeForUser(i4, 0);
                lockoutResetDispatcher.notifyLockoutResetCallbacks(i3);
                break;
            case 2:
                byte[] bArr = (byte[]) obj2;
                FaceGetFeatureClient faceGetFeatureClient = (FaceGetFeatureClient) obj;
                faceGetFeatureClient.getClass();
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put(1, Boolean.FALSE);
                    int[] iArr = new int[hashMap.size()];
                    boolean[] zArr = new boolean[hashMap.size()];
                    for (byte b : bArr) {
                        hashMap.put(
                                Integer.valueOf(
                                        AidlConversionUtils.convertAidlToFrameworkFeature(b)),
                                Boolean.TRUE);
                    }
                    int i5 = 0;
                    for (Map.Entry entry : hashMap.entrySet()) {
                        iArr[i5] = ((Integer) entry.getKey()).intValue();
                        zArr[i5] = ((Boolean) entry.getValue()).booleanValue();
                        i5++;
                    }
                    boolean booleanValue = ((Boolean) hashMap.get(1)).booleanValue();
                    Slog.d(
                            "FaceGetFeatureClient",
                            "Updating attention value for user: "
                                    + faceGetFeatureClient.mUserId
                                    + " to value: "
                                    + booleanValue);
                    Settings.Secure.putIntForUser(
                            faceGetFeatureClient.mContext.getContentResolver(),
                            "face_unlock_attention_required",
                            booleanValue ? 1 : 0,
                            faceGetFeatureClient.mUserId);
                    IFaceServiceReceiver iFaceServiceReceiver =
                            faceGetFeatureClient.mListener.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onFeatureGet(true, iArr, zArr);
                    }
                    faceGetFeatureClient.mCallback.onClientFinished(faceGetFeatureClient, true);
                    break;
                } catch (RemoteException | IllegalArgumentException e) {
                    Slog.e("FaceGetFeatureClient", "exception", e);
                    faceGetFeatureClient.mCallback.onClientFinished(faceGetFeatureClient, false);
                    return;
                }
            default:
                EnrollmentFrame enrollmentFrame = (EnrollmentFrame) obj2;
                FaceEnrollClient faceEnrollClient = (FaceEnrollClient) obj;
                if (enrollmentFrame == null) {
                    Slog.e(
                            "AidlResponseHandler",
                            "Received null enrollment frame for face enroll client.");
                    break;
                } else {
                    Cell cell = enrollmentFrame.cell;
                    FaceEnrollCell faceEnrollCell =
                            cell == null ? null : new FaceEnrollCell(cell.x, cell.y, cell.z);
                    byte b2 = enrollmentFrame.stage;
                    switch (b2) {
                        case 1:
                            i = 1;
                            break;
                        case 2:
                            i = 2;
                            break;
                        case 3:
                            i = 3;
                            break;
                        case 4:
                            i = 4;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        default:
                            switch (b2) {
                                case 100:
                                    i = 100;
                                    break;
                                case 101:
                                    i = 101;
                                    break;
                                case 102:
                                    i = 102;
                                    break;
                                case 103:
                                    i = 103;
                                    break;
                                case 104:
                                    i = 104;
                                    break;
                                default:
                                    switch (b2) {
                                        case 110:
                                            i = 110;
                                            break;
                                        case 111:
                                            i = 111;
                                            break;
                                        case 112:
                                            i = 112;
                                            break;
                                        case 113:
                                            i = 113;
                                            break;
                                        case 114:
                                            i = 114;
                                            break;
                                        default:
                                            i = 0;
                                            break;
                                    }
                            }
                    }
                    BaseFrame baseFrame = enrollmentFrame.data;
                    FaceEnrollFrame faceEnrollFrame =
                            new FaceEnrollFrame(
                                    faceEnrollCell,
                                    i,
                                    new FaceDataFrame(
                                            AidlConversionUtils.toFrameworkAcquiredInfo(
                                                    baseFrame.acquiredInfo),
                                            baseFrame.vendorCode,
                                            baseFrame.pan,
                                            baseFrame.tilt,
                                            baseFrame.distance,
                                            baseFrame.isCancellable));
                    faceEnrollClient.getClass();
                    int acquiredInfo = faceEnrollFrame.getData().getAcquiredInfo();
                    int vendorCode = faceEnrollFrame.getData().getVendorCode();
                    faceEnrollClient.onAcquiredInternal(acquiredInfo, vendorCode, false);
                    if (acquiredInfo == 22) {
                        if (Utils.listContains(
                                vendorCode, faceEnrollClient.mEnrollIgnoreListVendor)) {}
                    } else if (Utils.listContains(
                            acquiredInfo, faceEnrollClient.mEnrollIgnoreList)) {
                    }
                    try {
                        faceEnrollClient.mAuthenticationStateListeners.onAuthenticationHelp(
                                new AuthenticationHelpInfo.Builder(
                                                BiometricSourceType.FACE,
                                                EnrollClient.getRequestReasonFromFaceEnrollReason(
                                                        faceEnrollClient.mEnrollReason),
                                                FaceManager.getEnrollHelpMessage(
                                                        faceEnrollClient.mContext,
                                                        acquiredInfo,
                                                        vendorCode),
                                                acquiredInfo == 22
                                                        ? vendorCode + 1000
                                                        : acquiredInfo)
                                        .build());
                        IFaceServiceReceiver iFaceServiceReceiver2 =
                                faceEnrollClient.mListener.mFaceServiceReceiver;
                        if (iFaceServiceReceiver2 != null) {
                            iFaceServiceReceiver2.onEnrollmentFrame(faceEnrollFrame);
                            break;
                        }
                    } catch (RemoteException e2) {
                        Slog.w("FaceEnrollClient", "Failed to send enrollment frame", e2);
                        faceEnrollClient.mCallback.onClientFinished(faceEnrollClient, false);
                    }
                }
                break;
        }
    }
}
