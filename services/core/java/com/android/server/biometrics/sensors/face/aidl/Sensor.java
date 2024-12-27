package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.common.ComponentInfo;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;
import com.android.server.biometrics.sensors.UserSwitchProvider;
import com.android.server.biometrics.sensors.face.FaceUserState;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class Sensor {
    public final Map mAuthenticatorIds;
    public final BiometricContext mBiometricContext;
    public final Context mContext;
    public AidlSession mCurrentSession;
    public final Handler mHandler;
    public Supplier mLazySession;
    public SemFaceAidlLockoutHalImpl mLockoutHalImpl;
    public LockoutCache mLockoutTracker;
    public final FaceProvider mProvider;
    public BiometricScheduler mScheduler;
    public final FaceSensorPropertiesInternal mSensorProperties;
    public boolean mTestHalEnabled;
    public final IBinder mToken;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.Sensor$1, reason: invalid class name */
    public final class AnonymousClass1 implements UserSwitchProvider {
        public final /* synthetic */ LockoutResetDispatcher val$lockoutResetDispatcher;
        public final /* synthetic */ FaceProvider val$provider;

        public AnonymousClass1(LockoutResetDispatcher lockoutResetDispatcher, FaceProvider faceProvider) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
            this.val$provider = faceProvider;
        }

        /* JADX WARN: Type inference failed for: r12v0, types: [com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda3] */
        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public final StartUserClient getStartUserClient(final int i) {
            final Sensor sensor = Sensor.this;
            final int i2 = sensor.mSensorProperties.sensorId;
            final AidlResponseHandler aidlResponseHandler = new AidlResponseHandler(sensor.mContext, sensor.mScheduler, i2, i, sensor.mLockoutTracker, this.val$lockoutResetDispatcher, ((BiometricContextProvider) sensor.mBiometricContext).mAuthSessionCoordinator, new AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor.1.1
                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public final void onEnrollSuccess() {
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    FaceProvider faceProvider = Sensor.this.mProvider;
                    int i3 = i2;
                    int i4 = i;
                    faceProvider.scheduleLoadAuthenticatorIdsForUser(i3, i4);
                    if (Utils.isStrongBiometric(i3)) {
                        FaceProvider faceProvider2 = Sensor.this.mProvider;
                        faceProvider2.mHandler.post(new FaceProvider$$ExternalSyntheticLambda15(faceProvider2, i4, i3, 2));
                    }
                }

                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public final void onHardwareUnavailable() {
                    Slog.e("Sensor", "Face sensor hardware unavailable.");
                    Sensor.this.mCurrentSession = null;
                }
            });
            aidlResponseHandler.mLockoutHalImpl = sensor.mLockoutHalImpl;
            Slog.w("Sensor", "UserAwareBiometricScheduler.UserSwitchCallback getStartUserClient(" + i + ")");
            final FaceProvider faceProvider = this.val$provider;
            ?? r12 = new StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda3
                @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
                public final void onUserStarted(int i3, int i4, Object obj) {
                    boolean z;
                    Sensor sensor2 = Sensor.this;
                    sensor2.getClass();
                    Slog.d("Sensor", "New face session created for user: " + i3 + " with hal version: " + i4);
                    sensor2.mCurrentSession = new AidlSession(i4, (ISession) obj, i3, aidlResponseHandler);
                    int i5 = i2;
                    FaceUserState stateForUser = FaceUtils.getInstance(i5, "settings_face.xml").getStateForUser(sensor2.mContext, i3);
                    synchronized (stateForUser) {
                        z = stateForUser.mInvalidationInProgress;
                    }
                    if (z) {
                        PendingIntentController$$ExternalSyntheticOutline0.m(i5, i3, "Scheduling unfinished invalidation request for face sensor: ", ", user: ", "Sensor");
                        FaceProvider faceProvider2 = faceProvider;
                        faceProvider2.mHandler.post(new FaceProvider$$ExternalSyntheticLambda15(faceProvider2, i3, i5, 2));
                    }
                }
            };
            Context context = sensor.mContext;
            Objects.requireNonNull(faceProvider);
            return new FaceStartUserClient(context, new Sensor$$ExternalSyntheticLambda4(0, faceProvider), sensor.mToken, i, sensor.mSensorProperties.sensorId, BiometricLogger.ofUnknown(sensor.mContext), sensor.mBiometricContext, aidlResponseHandler, r12);
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public final StopUserClient getStopUserClient(int i) {
            Slog.w("Sensor", "UserAwareBiometricScheduler.UserSwitchCallback getStopUserClient(" + i + ")");
            Sensor sensor = Sensor.this;
            Context context = sensor.mContext;
            return new FaceStopUserClient(context, new Sensor$$ExternalSyntheticLambda4(1, this), sensor.mToken, i, sensor.mSensorProperties.sensorId, BiometricLogger.ofUnknown(context), sensor.mBiometricContext, new Sensor$$ExternalSyntheticLambda1(this));
        }
    }

    public Sensor(FaceProvider faceProvider, Context context, Handler handler, SensorProps sensorProps, BiometricContext biometricContext, boolean z) {
        ArrayList arrayList = new ArrayList();
        ComponentInfo[] componentInfoArr = sensorProps.commonProps.componentInfo;
        if (componentInfoArr != null) {
            for (ComponentInfo componentInfo : componentInfoArr) {
                arrayList.add(new ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
            }
        }
        CommonProps commonProps = sensorProps.commonProps;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal = new FaceSensorPropertiesInternal(commonProps.sensorId, commonProps.sensorStrength, commonProps.maxEnrollmentsPerUser, arrayList, sensorProps.sensorType, sensorProps.supportsDetectInteraction, sensorProps.halControlsPreview, z);
        this.mProvider = faceProvider;
        this.mContext = context;
        this.mToken = new Binder();
        this.mHandler = handler;
        this.mSensorProperties = faceSensorPropertiesInternal;
        this.mBiometricContext = biometricContext;
        this.mAuthenticatorIds = new HashMap();
    }

    public int getLockoutModeForUser(int i) {
        int lockoutState;
        AuthSessionCoordinator authSessionCoordinator = ((BiometricContextProvider) this.mBiometricContext).mAuthSessionCoordinator;
        int currentStrength = Utils.getCurrentStrength(this.mSensorProperties.sensorId);
        synchronized (authSessionCoordinator) {
            lockoutState = authSessionCoordinator.mMultiBiometricLockoutState.getLockoutState(i, currentStrength);
        }
        return lockoutState;
    }

    public LockoutTracker getLockoutTracker(boolean z) {
        if (z) {
            return null;
        }
        return this.mLockoutTracker;
    }

    public final int getRemainingLockoutTime(int i) {
        SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl = this.mLockoutHalImpl;
        int i2 = 0;
        if (semFaceAidlLockoutHalImpl == null) {
            return 0;
        }
        semFaceAidlLockoutHalImpl.getClass();
        try {
            int lockoutModeForUser = semFaceAidlLockoutHalImpl.getLockoutModeForUser(i);
            if (lockoutModeForUser == 2) {
                return -1;
            }
            if (lockoutModeForUser == 0 || lockoutModeForUser != 1) {
                return 0;
            }
            int elapsedRealtime = (int) ((semFaceAidlLockoutHalImpl.mRemainingLockoutTime.get(i, 0L) - SystemClock.elapsedRealtime()) / 1000);
            if (elapsedRealtime >= 0) {
                return elapsedRealtime;
            }
            try {
                Slog.d("FaceService.lockout", "remaining lockout = " + elapsedRealtime);
                return 0;
            } catch (Exception e) {
                e = e;
                i2 = elapsedRealtime;
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("getRemainingLockoutTime: "), "FaceService.lockout");
                return i2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public AidlSession getSessionForUser(int i) {
        Slog.d("Sensor", "getSessionForUser: mCurrentSession: " + this.mCurrentSession);
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.mUserId != i) {
            return null;
        }
        return aidlSession;
    }

    public boolean isHardwareDetected(String str) {
        if (this.mTestHalEnabled) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(IFace.DESCRIPTOR);
        sb.append("/");
        sb.append(str);
        return ServiceManager.checkService(sb.toString()) != null;
    }

    public void scheduleFaceUpdateActiveUserClient(int i) {
    }

    public final void setTestHalEnabled(boolean z) {
        Slog.w("Sensor", "Face setTestHalEnabled: " + z);
        if (z != this.mTestHalEnabled) {
            try {
                if (this.mCurrentSession != null) {
                    Slog.d("Sensor", "Closing old face session");
                    this.mCurrentSession.mSession.close();
                }
            } catch (RemoteException e) {
                Slog.e("Sensor", "RemoteException", e);
            }
            this.mCurrentSession = null;
        }
        this.mTestHalEnabled = z;
    }
}
