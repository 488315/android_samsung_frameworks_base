package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.biometrics.face.V1_0.IBiometricsFace;
import android.os.Environment;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.LockoutHalImpl;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler;
import com.android.server.biometrics.sensors.face.aidl.AidlSession;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider;
import com.android.server.biometrics.sensors.face.aidl.Sensor;

import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public final class HidlToAidlSensorAdapter extends Sensor implements IHwBinder.DeathRecipient {
    public final AidlResponseHandler.AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public int mCurrentUserId;
    public IBiometricsFace mDaemon;
    public final FaceProvider mFaceProvider;
    public final Runnable mInternalCleanupAndGetFeatureRunnable;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public LockoutHalImpl mLockoutTracker;
    public AidlSession mSession;
    public final HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 mUserStartedCallback;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3] */
    public HidlToAidlSensorAdapter(
            FaceProvider faceProvider,
            Context context,
            Handler handler,
            SensorProps sensorProps,
            LockoutResetDispatcher lockoutResetDispatcher,
            BiometricContext biometricContext,
            boolean z,
            Runnable runnable,
            AuthSessionCoordinator authSessionCoordinator,
            IBiometricsFace iBiometricsFace,
            AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback) {
        super(faceProvider, context, handler, sensorProps, biometricContext, z);
        this.mCurrentUserId = -10000;
        this.mUserStartedCallback =
                new StartUserClient
                        .UserStartedCallback() { // from class:
                                                 // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3
                    @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
                    public final void onUserStarted(int i, int i2, Object obj) {
                        HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                                HidlToAidlSensorAdapter.this;
                        if (i != hidlToAidlSensorAdapter.mCurrentUserId) {
                            hidlToAidlSensorAdapter.handleUserChanged(i);
                        }
                    }
                };
        this.mInternalCleanupAndGetFeatureRunnable = runnable;
        this.mFaceProvider = faceProvider;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mDaemon = iBiometricsFace;
        this.mAidlResponseHandlerCallback =
                aidlResponseHandlerCallback == null
                        ? new AidlResponseHandler
                                .AidlResponseHandlerCallback() { // from class:
                                                                 // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.1
                            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                            public final void onEnrollSuccess() {
                                HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                                        HidlToAidlSensorAdapter.this;
                                hidlToAidlSensorAdapter.scheduleFaceUpdateActiveUserClient(
                                        hidlToAidlSensorAdapter.mCurrentUserId);
                            }

                            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                            public final void onHardwareUnavailable() {
                                HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                                        HidlToAidlSensorAdapter.this;
                                hidlToAidlSensorAdapter.mDaemon = null;
                                hidlToAidlSensorAdapter.mCurrentUserId = -10000;
                            }
                        }
                        : aidlResponseHandlerCallback;
    }

    public final AidlResponseHandler getAidlResponseHandler() {
        return new AidlResponseHandler(
                this.mContext,
                this.mScheduler,
                this.mSensorProperties.sensorId,
                this.mCurrentUserId,
                this.mLockoutTracker,
                this.mLockoutResetDispatcher,
                this.mAuthSessionCoordinator,
                this.mAidlResponseHandlerCallback);
    }

    public final IBiometricsFace getIBiometricsFace() {
        SehTestHal sehTestHal;
        if (this.mFaceProvider.getServiceExtImpl().isTpaSehTestHalEnabled()) {
            Context context = this.mContext;
            int i = this.mSensorProperties.sensorId;
            SehTestHal sehTestHal2 = SehTestHal.mSehTestHal;
            synchronized (SehTestHal.class) {
                try {
                    if (SehTestHal.mSehTestHal == null) {
                        SehTestHal.mSehTestHal = new SehTestHal(context, i);
                    }
                    sehTestHal = SehTestHal.mSehTestHal;
                } catch (Throwable th) {
                    throw th;
                }
            }
            sehTestHal.setCallback(new HidlToAidlCallbackConverter(getAidlResponseHandler()));
            return sehTestHal;
        }
        if (this.mFaceProvider.mTestHalEnabled) {
            TestHal testHal = new TestHal(this.mContext, this.mSensorProperties.sensorId);
            testHal.setCallback(new HidlToAidlCallbackConverter(getAidlResponseHandler()));
            return testHal;
        }
        IBiometricsFace iBiometricsFace = this.mDaemon;
        if (iBiometricsFace != null) {
            return iBiometricsFace;
        }
        Slog.d(
                "HidlToAidlSensorAdapter",
                "Face daemon was null, reconnecting, current operation: "
                        + this.mScheduler.getCurrentClient());
        try {
            if (SemBiometricFeature.FEATURE_JDM_HAL) {
                this.mDaemon = ISehBiometricsFace.getService();
            } else {
                this.mDaemon =
                        vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
                                .getService();
            }
        } catch (RemoteException e) {
            Slog.e("HidlToAidlSensorAdapter", "Failed to get face HAL", e);
        } catch (NoSuchElementException e2) {
            Slog.w("HidlToAidlSensorAdapter", "NoSuchElementException", e2);
        }
        IBiometricsFace iBiometricsFace2 = this.mDaemon;
        if (iBiometricsFace2 == null) {
            Slog.w("HidlToAidlSensorAdapter", "Face HAL not available");
            return null;
        }
        iBiometricsFace2.asBinder().linkToDeath(this, 0L);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                                HidlToAidlSensorAdapter.this;
                        Iterator it =
                                UserManager.get(hidlToAidlSensorAdapter.mContext)
                                        .getAliveUsers()
                                        .iterator();
                        while (it.hasNext()) {
                            int i2 = ((UserInfo) it.next()).id;
                            if (!((HashMap) hidlToAidlSensorAdapter.mAuthenticatorIds)
                                    .containsKey(Integer.valueOf(i2))) {
                                hidlToAidlSensorAdapter.scheduleFaceUpdateActiveUserClient(i2);
                            }
                        }
                    }
                });
        this.mInternalCleanupAndGetFeatureRunnable.run();
        return this.mDaemon;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public final int getLockoutModeForUser(int i) {
        return this.mLockoutTracker.mCurrentUserLockoutMode;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public final LockoutTracker getLockoutTracker(boolean z) {
        return this.mLockoutTracker;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public AidlSession getSessionForUser(int i) {
        AidlSession aidlSession = this.mSession;
        if (aidlSession != null && aidlSession.mUserId == i) {
            return aidlSession;
        }
        AidlSession aidlSession2 =
                new AidlSession(
                        this.mContext,
                        new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(this, 2),
                        i,
                        getAidlResponseHandler());
        this.mSession = aidlSession2;
        return aidlSession2;
    }

    public void handleUserChanged(int i) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "User changed. Current user for face sensor is ", "HidlToAidlSensorAdapter");
        this.mSession = null;
        this.mCurrentUserId = i;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public final boolean isHardwareDetected(String str) {
        return getIBiometricsFace() != null;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public final void scheduleFaceUpdateActiveUserClient(int i) {
        BiometricScheduler biometricScheduler = this.mScheduler;
        Context context = this.mContext;
        biometricScheduler.scheduleClientMonitor(
                new StartUserClient(
                        context,
                        new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(this, 2),
                        this.mUserStartedCallback,
                        i,
                        this.mSensorProperties.sensorId,
                        BiometricLogger.ofUnknown(context),
                        this.mBiometricContext,
                        !((ArrayList)
                                        FaceUtils.getInstance(this.mSensorProperties.sensorId, null)
                                                .getBiometricsForUser(this.mContext, i))
                                .isEmpty(),
                        this.mAuthenticatorIds,
                        i) { // from class:
                             // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.2
                    public final Map mAuthenticatorIds;
                    public final boolean mHasEnrolledBiometrics;
                    public final /* synthetic */ int val$userId;

                    {
                        this.val$userId = i;
                        this.mHasEnrolledBiometrics = r19;
                        this.mAuthenticatorIds = r20;
                    }

                    public final void daemonSetActiveUser() {
                        int i2;
                        long j = 0;
                        try {
                            File file =
                                    new File(
                                            Environment.getDataVendorDeDirectory(this.val$userId),
                                            "facedata");
                            j = System.currentTimeMillis();
                            i2 =
                                    HidlToAidlSensorAdapter.this.mDaemon.setActiveUser(
                                            this.val$userId, file.getAbsolutePath());
                        } catch (RemoteException e) {
                            Slog.e("HidlToAidlSensorAdapter", "daemonSetActiveUser : " + e);
                            i2 = -1;
                        }
                        Slog.w(
                                "HidlToAidlSensorAdapter",
                                "daemonSetActiveUser FINISH ("
                                        + (System.currentTimeMillis() - j)
                                        + "ms) RESULT: "
                                        + i2);
                    }

                    @Override // com.android.server.biometrics.sensors.StartUserClient,
                              // com.android.server.biometrics.sensors.BaseClientMonitor
                    public final int getProtoEnum() {
                        return 1;
                    }

                    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                    public final void start(ClientMonitorCallback clientMonitorCallback) {
                        super.start(clientMonitorCallback);
                        startHalOperation();
                    }

                    @Override // com.android.server.biometrics.sensors.HalClientMonitor
                    public final void startHalOperation() {
                        String str;
                        if (!new File(
                                        Environment.getDataVendorDeDirectory(this.mTargetUserId),
                                        "facedata")
                                .exists()) {
                            Slog.e(
                                    "FaceUpdateActiveUserClient",
                                    "vold has not created the directory?");
                            this.mCallback.onClientFinished(this, false);
                            return;
                        }
                        try {
                            daemonSetActiveUser();
                            IBiometricsFace iBiometricsFace =
                                    (IBiometricsFace) this.mLazyDaemon.get();
                            long currentTimeMillis = System.currentTimeMillis();
                            long j = iBiometricsFace.getAuthenticatorId().value;
                            this.mAuthenticatorIds.put(
                                    Integer.valueOf(this.mTargetUserId),
                                    Long.valueOf(this.mHasEnrolledBiometrics ? j : 0L));
                            StringBuilder sb = new StringBuilder("getAuthenticatorId FINISH (");
                            sb.append(System.currentTimeMillis() - currentTimeMillis);
                            sb.append("ms)  user = ");
                            sb.append(this.mTargetUserId);
                            if (Utils.DEBUG) {
                                str = ", id = " + j;
                            } else {
                                str = "";
                            }
                            sb.append(str);
                            Slog.w("FaceUpdateActiveUserClient", sb.toString());
                            this.mUserStartedCallback.onUserStarted(this.mTargetUserId, 0, null);
                            this.mCallback.onClientFinished(this, true);
                        } catch (RemoteException e) {
                            Slog.e("FaceUpdateActiveUserClient", "Failed to setActiveUser: " + e);
                            this.mCallback.onClientFinished(this, false);
                        }
                    }

                    @Override // com.android.server.biometrics.sensors.HalClientMonitor
                    public final void unableToStart() {}
                },
                null);
    }

    public final void serviceDied(long j) {
        Slog.d("HidlToAidlSensorAdapter", "Face HAL died.");
        this.mDaemon = null;
    }
}
