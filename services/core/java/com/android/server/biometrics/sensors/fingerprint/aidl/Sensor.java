package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.common.ComponentInfo;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricLockoutTracker;
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
import com.android.server.biometrics.sensors.fingerprint.FingerprintUserState;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class Sensor {
    public final BiometricContext mBiometricContext;
    public final Context mContext;
    public final Handler mHandler;
    public Supplier mLazySession;
    public final SemFpAidlLockoutHalImpl mLockoutHalImpl;
    public LockoutCache mLockoutTracker;
    public final FingerprintProvider mProvider;
    public BiometricScheduler mScheduler;
    public final FingerprintSensorPropertiesInternal mSensorProperties;
    public boolean mTestHalEnabled;
    public final IBinder mToken = new Binder();
    public final Map mAuthenticatorIds = new HashMap();
    public AidlSession mCurrentSession = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1, reason: invalid class name */
    public final class AnonymousClass1 implements UserSwitchProvider {
        public final /* synthetic */ LockoutResetDispatcher val$lockoutResetDispatcher;

        public AnonymousClass1(LockoutResetDispatcher lockoutResetDispatcher) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
        }

        /* JADX WARN: Type inference failed for: r12v1, types: [com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda4] */
        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public final StartUserClient getStartUserClient(final int i) {
            final Sensor sensor = Sensor.this;
            final int i2 = sensor.mSensorProperties.sensorId;
            Context context = sensor.mContext;
            BiometricScheduler biometricScheduler = sensor.mScheduler;
            LockoutCache lockoutCache = sensor.mLockoutTracker;
            AuthSessionCoordinator authSessionCoordinator = ((BiometricContextProvider) sensor.mBiometricContext).mAuthSessionCoordinator;
            AidlResponseHandler$AidlResponseHandlerCallback aidlResponseHandler$AidlResponseHandlerCallback = new AidlResponseHandler$AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.1.1
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$AidlResponseHandlerCallback
                public final void onEnrollSuccess() {
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    FingerprintProvider fingerprintProvider = Sensor.this.mProvider;
                    int i3 = i2;
                    int i4 = i;
                    fingerprintProvider.scheduleLoadAuthenticatorIdsForUser(i3, i4);
                    FingerprintProvider fingerprintProvider2 = Sensor.this.mProvider;
                    fingerprintProvider2.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda0(fingerprintProvider2, i4, i3, 0));
                }

                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$AidlResponseHandlerCallback
                public final void onHardwareUnavailable() {
                    Slog.e("Sensor", "Fingerprint sensor hardware unavailable.");
                    Sensor.this.mCurrentSession = null;
                }
            };
            FingerprintProvider fingerprintProvider = sensor.mProvider;
            final SemFpAidlResponseHandler semFpAidlResponseHandler = new SemFpAidlResponseHandler(context, biometricScheduler, i2, i, lockoutCache, this.val$lockoutResetDispatcher, authSessionCoordinator, aidlResponseHandler$AidlResponseHandlerCallback, fingerprintProvider.mCallbackDispatcher, sensor.mLockoutHalImpl, sensor.mSensorProperties);
            ?? r12 = new StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda4
                @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
                public final void onUserStarted(int i3, int i4, Object obj) {
                    boolean z;
                    Sensor sensor2 = Sensor.this;
                    sensor2.getClass();
                    Slog.d("Sensor", "New fingerprint session created for user: " + i3 + " with hal version: " + i4);
                    AidlSession aidlSession = new AidlSession(i4, (ISession) obj, i3, semFpAidlResponseHandler);
                    sensor2.mCurrentSession = aidlSession;
                    final FingerprintProvider fingerprintProvider2 = sensor2.mProvider;
                    Objects.requireNonNull(fingerprintProvider2);
                    aidlSession.mLazySehFingerprint = new Function() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            SemTpaTestHal semTpaTestHal;
                            FingerprintProvider fingerprintProvider3 = FingerprintProvider.this;
                            int intValue = ((Integer) obj2).intValue();
                            synchronized (fingerprintProvider3) {
                                if (fingerprintProvider3.mTestHalEnabled) {
                                    SemTestSehFingerprint semTestSehFingerprint = new SemTestSehFingerprint();
                                    semTestSehFingerprint.mRequestActionTable.clear();
                                    semTestSehFingerprint.mRequestActionTable.put(6, 100040);
                                    return semTestSehFingerprint;
                                }
                                if (fingerprintProvider3.mTpaHalModeEnabled && (semTpaTestHal = fingerprintProvider3.mTpaTestHal) != null) {
                                    return semTpaTestHal.mSehFingerprint;
                                }
                                if (fingerprintProvider3.mFingerprintSensors.mSensors.contains(intValue)) {
                                    if (!fingerprintProvider3.mIsForHidl) {
                                        if (fingerprintProvider3.mSehFingerprint == null) {
                                            try {
                                                IFingerprint halInstance = fingerprintProvider3.getHalInstance();
                                                if (halInstance != null) {
                                                    fingerprintProvider3.mSehFingerprint = SemTestSehFingerprint.asInterface(halInstance.asBinder().getExtension());
                                                }
                                            } catch (Exception e) {
                                                Slog.w(fingerprintProvider3.getTag$1(), "getSehFingerprint: " + e.getMessage());
                                            }
                                        }
                                        return fingerprintProvider3.mSehFingerprint;
                                    }
                                    Slog.e("FingerprintProvider", "FingerprintProvider#getSehFingerprint unsupported in HIDL");
                                }
                                return null;
                            }
                        }
                    };
                    int i5 = i2;
                    FingerprintUserState stateForUser = FingerprintUtils.getInstance(i5).getStateForUser(sensor2.mContext, i3);
                    synchronized (stateForUser) {
                        z = stateForUser.mInvalidationInProgress;
                    }
                    if (z) {
                        PendingIntentController$$ExternalSyntheticOutline0.m(i5, i3, "Scheduling unfinished invalidation request for fingerprint sensor: ", ", user: ", "Sensor");
                        fingerprintProvider2.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda0(fingerprintProvider2, i3, i5, 0));
                    }
                }
            };
            Context context2 = sensor.mContext;
            Objects.requireNonNull(fingerprintProvider);
            return new FingerprintStartUserClient(context2, new Sensor$$ExternalSyntheticLambda5(0, fingerprintProvider), sensor.mToken, i, sensor.mSensorProperties.sensorId, BiometricLogger.ofUnknown(sensor.mContext), sensor.mBiometricContext, semFpAidlResponseHandler, r12);
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public final StopUserClient getStopUserClient(int i) {
            Sensor sensor = Sensor.this;
            Context context = sensor.mContext;
            return new FingerprintStopUserClient(context, new Sensor$$ExternalSyntheticLambda5(1, this), sensor.mToken, i, sensor.mSensorProperties.sensorId, BiometricLogger.ofUnknown(context), sensor.mBiometricContext, new Sensor$$ExternalSyntheticLambda0(this));
        }
    }

    public Sensor(FingerprintProvider fingerprintProvider, Context context, Handler handler, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, BiometricContext biometricContext) {
        this.mProvider = fingerprintProvider;
        this.mContext = context;
        this.mHandler = handler;
        this.mSensorProperties = fingerprintSensorPropertiesInternal;
        this.mBiometricContext = biometricContext;
        this.mLockoutHalImpl = new SemFpAidlLockoutHalImpl(context, SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT ? SemBiometricLockoutTracker.get() : null, new Sensor$$ExternalSyntheticLambda0(this));
    }

    public static FingerprintSensorPropertiesInternal getFingerprintSensorPropertiesInternal(SensorProps sensorProps, List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        ComponentInfo[] componentInfoArr = sensorProps.commonProps.componentInfo;
        if (componentInfoArr != null) {
            for (ComponentInfo componentInfo : componentInfoArr) {
                arrayList.add(new ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
            }
        }
        CommonProps commonProps = sensorProps.commonProps;
        int i = commonProps.sensorId;
        byte b = commonProps.sensorStrength;
        int i2 = commonProps.maxEnrollmentsPerUser;
        byte b2 = sensorProps.sensorType;
        boolean z2 = sensorProps.halControlsIllumination;
        if (((ArrayList) list).isEmpty()) {
            list = (List) Arrays.stream(sensorProps.sensorLocations).map(new Sensor$$ExternalSyntheticLambda2()).collect(Collectors.toList());
        }
        return new FingerprintSensorPropertiesInternal(i, b, i2, arrayList, b2, z2, z, list);
    }

    public void generateEvent() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            aidlSession.mAidlResponseHandler.onAcquired((byte) 7, 70001);
        }
    }

    public int getCurrentSessionUserId() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession.mUserId;
        }
        return 0;
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
        SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl = this.mLockoutHalImpl;
        semFpAidlLockoutHalImpl.getClass();
        int i2 = 0;
        try {
            int lockoutModeForUser = semFpAidlLockoutHalImpl.getLockoutModeForUser(i);
            if (lockoutModeForUser == 2) {
                return -1;
            }
            if (lockoutModeForUser == 0 || lockoutModeForUser != 1) {
                return 0;
            }
            int elapsedRealtime = (int) ((semFpAidlLockoutHalImpl.mRemainingLockoutTime.get(i, 0L) - SystemClock.elapsedRealtime()) / 1000);
            if (elapsedRealtime >= 0) {
                return elapsedRealtime;
            }
            try {
                Slog.d("FingerprintService", "remaining lockout = " + elapsedRealtime);
                return 0;
            } catch (Exception e) {
                e = e;
                i2 = elapsedRealtime;
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("getRemainingLockoutTime: "), "FingerprintService");
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

    public void handleOnLockoutReset(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.mUserId != i) {
            return;
        }
        aidlSession.mAidlResponseHandler.onLockoutCleared();
    }

    public boolean isHardwareDetected(String str) {
        if (this.mTestHalEnabled) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(IFingerprint.DESCRIPTOR);
        sb.append("/");
        sb.append(str);
        return ServiceManager.checkService(sb.toString()) != null;
    }

    public void setTestHalEnabled(boolean z) {
        Slog.w("Sensor", "Fingerprint setTestHalEnabled: " + z);
        if (z != this.mTestHalEnabled) {
            try {
                if (this.mCurrentSession != null) {
                    Slog.d("Sensor", "Closing old fingerprint session");
                    this.mCurrentSession.mSession.close();
                }
            } catch (RemoteException e) {
                Slog.e("Sensor", "RemoteException", e);
            }
            this.mCurrentSession = null;
        }
        this.mTestHalEnabled = z;
    }

    public void setTpaHalEnabled() {
    }
}
