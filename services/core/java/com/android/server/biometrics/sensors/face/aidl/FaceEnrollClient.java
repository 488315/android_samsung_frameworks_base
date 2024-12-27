package com.android.server.biometrics.sensors.face.aidl;

import android.R;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricFaceConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.common.NativeHandle;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.FaceManager;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.view.Surface;

import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.CallbackWithProbe;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.face.FaceService;
import com.android.server.biometrics.sensors.face.FaceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;

public final class FaceEnrollClient extends EnrollClient {
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public ICancellationSignal mCancellationSignal;
    public final boolean mDebugConsent;
    public final int[] mDisabledFeatures;
    public final int[] mEnrollIgnoreList;
    public final int[] mEnrollIgnoreListVendor;
    public final int mEnrollReason;
    public NativeHandle mHwPreviewHandle;
    public final int mMaxTemplatesPerUser;
    public android.os.NativeHandle mOsPreviewHandle;
    public final AnonymousClass1 mPreviewHandleDeleterCallback;
    public final Surface mPreviewSurface;
    public final AnonymousClass1 mSemCancelDaemonCallback;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$1] */
    public FaceEnrollClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            byte[] bArr,
            String str,
            long j,
            FaceUtils faceUtils,
            int[] iArr,
            Surface surface,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            int i3,
            boolean z,
            FaceEnrollOptions faceEnrollOptions,
            AuthenticationStateListeners authenticationStateListeners) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                bArr,
                str,
                faceUtils,
                i2,
                false,
                biometricLogger,
                biometricContext,
                BiometricFaceConstants.reasonToMetric(faceEnrollOptions.getEnrollReason()));
        final int i4 = 0;
        this.mPreviewHandleDeleterCallback =
                new ClientMonitorCallback(this) { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.1
                    public final /* synthetic */ FaceEnrollClient this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onClientStarted$com$android$server$biometrics$sensors$face$aidl$FaceEnrollClient$1(
                                    BaseClientMonitor baseClientMonitor) {}

                    private final void
                            onClientStarted$com$android$server$biometrics$sensors$face$aidl$FaceEnrollClient$2(
                                    BaseClientMonitor baseClientMonitor) {}

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z2) {
                        switch (i4) {
                            case 0:
                                FaceEnrollClient faceEnrollClient = this.this$0;
                                if (faceEnrollClient.mPreviewSurface != null
                                        && faceEnrollClient.mHwPreviewHandle == null) {
                                    Slog.w(
                                            "FaceEnrollClient",
                                            "mHwPreviewHandle is null even though mPreviewSurface"
                                                + " is not null.");
                                }
                                if (faceEnrollClient.mHwPreviewHandle != null) {
                                    try {
                                        Slog.v("FaceEnrollClient", "Closing mHwPreviewHandle");
                                        NativeHandle nativeHandle =
                                                faceEnrollClient.mHwPreviewHandle;
                                        if (nativeHandle != null) {
                                            for (ParcelFileDescriptor parcelFileDescriptor :
                                                    nativeHandle.fds) {
                                                if (parcelFileDescriptor != null) {
                                                    parcelFileDescriptor.close();
                                                }
                                            }
                                        }
                                    } catch (IOException e) {
                                        Slog.e(
                                                "FaceEnrollClient",
                                                "Failed to close mPreviewSurface",
                                                e);
                                    }
                                    faceEnrollClient.mHwPreviewHandle = null;
                                }
                                if (faceEnrollClient.mOsPreviewHandle != null) {
                                    Slog.v("FaceEnrollClient", "Releasing mOsPreviewHandle");
                                    FaceService.releaseSurfaceHandle(
                                            faceEnrollClient.mOsPreviewHandle);
                                    faceEnrollClient.mOsPreviewHandle = null;
                                }
                                if (faceEnrollClient.mPreviewSurface != null) {
                                    Slog.v("FaceEnrollClient", "Releasing mPreviewSurface");
                                    faceEnrollClient.mPreviewSurface.release();
                                    break;
                                }
                                break;
                            default:
                                FaceEnrollClient faceEnrollClient2 = this.this$0;
                                faceEnrollClient2
                                        .getServiceExtImpl()
                                        .daemonCancel(faceEnrollClient2.mCancellationSignal, true);
                                break;
                        }
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
                        int i5 = i4;
                    }
                };
        final int i5 = 1;
        this.mSemCancelDaemonCallback =
                new ClientMonitorCallback(this) { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.1
                    public final /* synthetic */ FaceEnrollClient this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onClientStarted$com$android$server$biometrics$sensors$face$aidl$FaceEnrollClient$1(
                                    BaseClientMonitor baseClientMonitor) {}

                    private final void
                            onClientStarted$com$android$server$biometrics$sensors$face$aidl$FaceEnrollClient$2(
                                    BaseClientMonitor baseClientMonitor) {}

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z2) {
                        switch (i5) {
                            case 0:
                                FaceEnrollClient faceEnrollClient = this.this$0;
                                if (faceEnrollClient.mPreviewSurface != null
                                        && faceEnrollClient.mHwPreviewHandle == null) {
                                    Slog.w(
                                            "FaceEnrollClient",
                                            "mHwPreviewHandle is null even though mPreviewSurface"
                                                + " is not null.");
                                }
                                if (faceEnrollClient.mHwPreviewHandle != null) {
                                    try {
                                        Slog.v("FaceEnrollClient", "Closing mHwPreviewHandle");
                                        NativeHandle nativeHandle =
                                                faceEnrollClient.mHwPreviewHandle;
                                        if (nativeHandle != null) {
                                            for (ParcelFileDescriptor parcelFileDescriptor :
                                                    nativeHandle.fds) {
                                                if (parcelFileDescriptor != null) {
                                                    parcelFileDescriptor.close();
                                                }
                                            }
                                        }
                                    } catch (IOException e) {
                                        Slog.e(
                                                "FaceEnrollClient",
                                                "Failed to close mPreviewSurface",
                                                e);
                                    }
                                    faceEnrollClient.mHwPreviewHandle = null;
                                }
                                if (faceEnrollClient.mOsPreviewHandle != null) {
                                    Slog.v("FaceEnrollClient", "Releasing mOsPreviewHandle");
                                    FaceService.releaseSurfaceHandle(
                                            faceEnrollClient.mOsPreviewHandle);
                                    faceEnrollClient.mOsPreviewHandle = null;
                                }
                                if (faceEnrollClient.mPreviewSurface != null) {
                                    Slog.v("FaceEnrollClient", "Releasing mPreviewSurface");
                                    faceEnrollClient.mPreviewSurface.release();
                                    break;
                                }
                                break;
                            default:
                                FaceEnrollClient faceEnrollClient2 = this.this$0;
                                faceEnrollClient2
                                        .getServiceExtImpl()
                                        .daemonCancel(faceEnrollClient2.mCancellationSignal, true);
                                break;
                        }
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
                        int i52 = i5;
                    }
                };
        setRequestId(j);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mEnrollReason = faceEnrollOptions.getEnrollReason();
        this.mEnrollIgnoreList =
                context.getResources().getIntArray(R.array.no_ems_support_sim_operators);
        this.mEnrollIgnoreListVendor =
                context.getResources().getIntArray(R.array.policy_exempt_apps);
        this.mMaxTemplatesPerUser = i3;
        this.mDebugConsent = z;
        this.mDisabledFeatures = iArr;
        this.mPreviewSurface = surface;
        Slog.w(
                "FaceEnrollClient",
                "EnrollOptions "
                        + FaceEnrollOptions.enrollReasonToString(
                                faceEnrollOptions.getEnrollReason()));
    }

    public final void doEnroll(byte[] bArr) {
        AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
        HardwareAuthToken hardwareAuthToken =
                HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        aidlSession.getClass();
        this.mCancellationSignal =
                aidlSession.mSession.enroll(
                        hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle);
    }

    public SemFaceServiceExImpl getServiceExtImpl() {
        return SemFaceServiceExImpl.getInstance();
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public final boolean hasReachedEnrollmentLimit() {
        if (getServiceExtImpl().mIsHIDL) {
            return this.mBiometricUtils
                            .getBiometricsForUser(this.mContext, this.mTargetUserId)
                            .size()
                    >= (SemBiometricFeature.FEATURE_JDM_HAL ? 1 : 2);
        }
        return ((ArrayList)
                                FaceUtils.getInstance(this.mSensorId, null)
                                        .getBiometricsForUser(this.mContext, this.mTargetUserId))
                        .size()
                >= this.mMaxTemplatesPerUser;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void onAcquired(int i, int i2) {
        boolean z = false;
        if (i != 22
                ? !Utils.listContains(i, this.mEnrollIgnoreList)
                : !Utils.listContains(i2, this.mEnrollIgnoreListVendor)) {
            z = true;
        }
        if (z) {
            this.mAuthenticationStateListeners.onAuthenticationHelp(
                    new AuthenticationHelpInfo.Builder(
                                    BiometricSourceType.FACE,
                                    EnrollClient.getRequestReasonFromFaceEnrollReason(
                                            this.mEnrollReason),
                                    FaceManager.getEnrollHelpMessage(this.mContext, i, i2),
                                    i == 22 ? i2 + 1000 : i)
                            .build());
        }
        onAcquiredInternal(i, i2, z);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        AuthenticationStateListeners authenticationStateListeners =
                this.mAuthenticationStateListeners;
        BiometricSourceType biometricSourceType = BiometricSourceType.FACE;
        authenticationStateListeners.onAuthenticationError(
                new AuthenticationErrorInfo.Builder(
                                biometricSourceType,
                                EnrollClient.getRequestReasonFromFaceEnrollReason(
                                        this.mEnrollReason),
                                FaceManager.getErrorString(this.mContext, i, i2),
                                i)
                        .build());
        this.mLogger.logOnEnrolled(
                this.mTargetUserId,
                super.mEnrollReason,
                false,
                System.currentTimeMillis() - this.mEnrollmentStartTimeMs);
        onErrorInternal(i, i2, true);
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(
                                biometricSourceType,
                                EnrollClient.getRequestReasonFromFaceEnrollReason(
                                        this.mEnrollReason))
                        .build());
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        Context context = this.mContext;
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        UserHandle userHandle = UserHandle.CURRENT;
        notificationManager.cancelAsUser("FaceEnroll", 1, userHandle);
        ((NotificationManager) this.mContext.getSystemService(NotificationManager.class))
                .cancelAsUser("FaceReEnroll", 1, userHandle);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStarted(
                new AuthenticationStartedInfo.Builder(
                                BiometricSourceType.FACE,
                                EnrollClient.getRequestReasonFromFaceEnrollReason(
                                        this.mEnrollReason))
                        .build());
        Surface surface = this.mPreviewSurface;
        if (surface != null) {
            android.os.NativeHandle acquireSurfaceHandle =
                    FaceService.acquireSurfaceHandle(surface);
            this.mOsPreviewHandle = acquireSurfaceHandle;
            try {
                this.mHwPreviewHandle = AidlNativeHandleUtils.dup(acquireSurfaceHandle);
                Slog.v("FaceEnrollClient", "Obtained handles for the preview surface.");
            } catch (IOException e) {
                this.mHwPreviewHandle = null;
                Slog.e("FaceEnrollClient", "Failed to dup mOsPreviewHandle", e);
            }
        }
        try {
            ArrayList arrayList = new ArrayList();
            if (this.mDebugConsent) {
                arrayList.add((byte) 2);
            }
            boolean z = true;
            boolean z2 = true;
            for (int i : this.mDisabledFeatures) {
                if (AidlConversionUtils.convertFrameworkToAidlFeature(i) == 1) {
                    z2 = false;
                }
            }
            if (z2) {
                arrayList.add((byte) 1);
            }
            byte[] bArr = new byte[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                bArr[i2] = ((Byte) arrayList.get(i2)).byteValue();
            }
            getServiceExtImpl().startOperation(1);
            Slog.w("FaceEnrollClient", "enroll START");
            long currentTimeMillis = System.currentTimeMillis();
            if (getServiceExtImpl().isUsingSehAPI()) {
                AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
                SemFaceServiceExImpl serviceExtImpl = getServiceExtImpl();
                HardwareAuthToken hardwareAuthToken =
                        HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
                NativeHandle nativeHandle = this.mHwPreviewHandle;
                Surface surface2 = this.mPreviewSurface;
                if (aidlSession.mHalInterfaceVersion < 4) {
                    z = false;
                }
                this.mCancellationSignal =
                        serviceExtImpl.daemonEnroll(
                                hardwareAuthToken,
                                bArr,
                                nativeHandle,
                                surface2,
                                Boolean.valueOf(z));
            } else {
                doEnroll(bArr);
            }
            Slog.w(
                    "FaceEnrollClient",
                    "enroll FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms) RESULT: "
                            + this.mCancellationSignal);
        } catch (RemoteException | IllegalArgumentException e2) {
            Slog.e("FaceEnrollClient", "Exception when requesting enroll", e2);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(
                                BiometricSourceType.FACE,
                                EnrollClient.getRequestReasonFromFaceEnrollReason(
                                        this.mEnrollReason))
                        .build());
        unsubscribeBiometricContext();
        if (this.mCancellationSignal != null) {
            try {
                getServiceExtImpl().daemonCancel(this.mCancellationSignal, false);
            } catch (RemoteException e) {
                Slog.e("FaceEnrollClient", "Remote exception when requesting cancel", e);
                onError(1, 0);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final ClientMonitorCallback wrapCallbackForStart(
            ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(
                this.mPreviewHandleDeleterCallback,
                this.mSemCancelDaemonCallback,
                new CallbackWithProbe(this.mLogger.mALSProbe, true),
                clientMonitorCallback);
    }
}
