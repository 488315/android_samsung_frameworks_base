package com.android.server.biometrics.sensors.face.aidl;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.TaskStackListener;
import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.face.HidlFaceSensorConfig;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.AuthenticationStatsBroadcastReceiver;
import com.android.server.biometrics.AuthenticationStatsCollector;
import com.android.server.biometrics.BiometricDanglingReceiver;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.BiometricServiceProvider;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.InternalCleanupClient;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.RemovalClient;
import com.android.server.biometrics.sensors.SensorList;
import com.android.server.biometrics.sensors.UserSwitchProvider;
import com.android.server.biometrics.sensors.face.FaceUserState;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.LockoutHalImpl;
import com.android.server.biometrics.sensors.face.UsageStats;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.AnonymousClass2;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.AnonymousClass3;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.AnonymousClass4;
import com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass1;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FaceProvider implements IBinder.DeathRecipient, BiometricServiceProvider {
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public AuthenticationStatsCollector mAuthenticationStatsCollector;
    public final BiometricContext mBiometricContext;
    public final BiometricHandlerProvider mBiometricHandlerProvider;
    public final BiometricStateCallback mBiometricStateCallback;
    public final Context mContext;
    public IFace mDaemon;
    final SensorList mFaceSensors;
    public final String mHalInstanceName;
    public final Handler mHandler;
    public Boolean mIsFirstOnUserSwitching;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final AtomicLong mRequestCounter;
    public boolean mTestHalEnabled;
    public final UsageStats mUsageStats;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.FaceProvider$3, reason: invalid class name */
    public final class AnonymousClass3 implements ClientMonitorCallback {
        public final /* synthetic */ FaceAuthenticationClient val$client;
        public final /* synthetic */ long val$requestId;
        public final /* synthetic */ int val$sensorId;
        public final /* synthetic */ int val$userId;

        public AnonymousClass3(
                int i, int i2, long j, FaceAuthenticationClient faceAuthenticationClient) {
            this.val$userId = i;
            this.val$sensorId = i2;
            this.val$requestId = j;
            this.val$client = faceAuthenticationClient;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            Handler biometricCallbackHandler =
                    FaceProvider.this.mBiometricHandlerProvider.getBiometricCallbackHandler();
            final long j = this.val$requestId;
            final FaceAuthenticationClient faceAuthenticationClient = this.val$client;
            final int i = this.val$userId;
            final int i2 = this.val$sensorId;
            biometricCallbackHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.face.aidl.FaceProvider$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceProvider.AnonymousClass3 anonymousClass3 =
                                    FaceProvider.AnonymousClass3.this;
                            int i3 = i;
                            int i4 = i2;
                            FaceProvider.this.mAuthSessionCoordinator.authEndedFor(
                                    j,
                                    i3,
                                    Utils.getCurrentStrength(i4),
                                    i4,
                                    faceAuthenticationClient.mAuthSuccess);
                        }
                    });
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Handler biometricCallbackHandler =
                    FaceProvider.this.mBiometricHandlerProvider.getBiometricCallbackHandler();
            final int i = this.val$userId;
            final int i2 = this.val$sensorId;
            final long j = this.val$requestId;
            biometricCallbackHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.face.aidl.FaceProvider$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceProvider.AnonymousClass3 anonymousClass3 =
                                    FaceProvider.AnonymousClass3.this;
                            FaceProvider.this.mAuthSessionCoordinator.authStartedFor(i, i2, j);
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BiometricTaskStackListener extends TaskStackListener
            implements ActivityManager.SemProcessListener {
        public BiometricTaskStackListener() {}

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            String tag = FaceProvider.this.getTag();
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "onForegroundActivitiesChanged: pid = ",
                            ", uid = ",
                            ", foregroundActivities = ");
            m.append(z);
            Slog.d(tag, m.toString());
            onTaskStackChanged();
        }

        public final void onProcessDied(int i, int i2) {}

        public final void onTaskStackChanged() {
            FaceProvider.this.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(7, this));
        }
    }

    public FaceProvider(
            Context context,
            BiometricStateCallback biometricStateCallback,
            AuthenticationStateListeners authenticationStateListeners,
            SensorProps[] sensorPropsArr,
            String str,
            LockoutResetDispatcher lockoutResetDispatcher,
            BiometricContext biometricContext,
            IFace iFace,
            BiometricHandlerProvider biometricHandlerProvider,
            boolean z,
            boolean z2) {
        SensorProps[] sensorPropsArr2 = sensorPropsArr;
        this.mRequestCounter = new AtomicLong(0L);
        this.mIsFirstOnUserSwitching = Boolean.TRUE;
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mHalInstanceName = str;
        this.mFaceSensors = new SensorList(ActivityManager.getService());
        this.mHandler = biometricHandlerProvider.getFaceHandler();
        UsageStats usageStats = new UsageStats();
        usageStats.mAuthenticationEvents = new ArrayDeque();
        usageStats.mErrorFrequencyMap = new SparseIntArray();
        usageStats.mErrorLatencyMap = new SparseLongArray();
        usageStats.mContext = context;
        this.mUsageStats = usageStats;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        ActivityTaskManager.getInstance();
        new BiometricTaskStackListener();
        this.mBiometricContext = biometricContext;
        this.mAuthSessionCoordinator =
                ((BiometricContextProvider) biometricContext).mAuthSessionCoordinator;
        this.mDaemon = iFace;
        this.mTestHalEnabled = z2;
        this.mBiometricHandlerProvider = biometricHandlerProvider;
        boolean z3 =
                "defaultHIDL".contentEquals(str)
                        || (sensorPropsArr2 instanceof HidlFaceSensorConfig[]);
        new AuthenticationStatsBroadcastReceiver(
                context,
                4,
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda25
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceProvider faceProvider = FaceProvider.this;
                        Slog.d(faceProvider.getTag(), "Initializing AuthenticationStatsCollector");
                        faceProvider.mAuthenticationStatsCollector =
                                (AuthenticationStatsCollector) obj;
                    }
                });
        new BiometricDanglingReceiver(context, 4);
        String str2 = "Added: ";
        if (z3) {
            Slog.d(getTag(), "Adding HIDL configs");
            int length = sensorPropsArr2.length;
            int i = 0;
            while (i < length) {
                SensorProps sensorProps = sensorPropsArr2[i];
                final int i2 = sensorProps.commonProps.sensorId;
                Context context2 = this.mContext;
                Handler handler = this.mHandler;
                LockoutResetDispatcher lockoutResetDispatcher2 = this.mLockoutResetDispatcher;
                BiometricContext biometricContext2 = this.mBiometricContext;
                int i3 = i;
                int i4 = length;
                String str3 = str2;
                HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                        new HidlToAidlSensorAdapter(
                                this,
                                context2,
                                handler,
                                sensorProps,
                                lockoutResetDispatcher2,
                                biometricContext2,
                                z,
                                new FaceProvider$$ExternalSyntheticLambda0(this, i2, 1),
                                ((BiometricContextProvider) biometricContext2)
                                        .mAuthSessionCoordinator,
                                null,
                                null);
                hidlToAidlSensorAdapter.mScheduler =
                        new BiometricScheduler(
                                hidlToAidlSensorAdapter.mHandler,
                                1,
                                (GestureAvailabilityDispatcher) null,
                                new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(
                                        hidlToAidlSensorAdapter, 0),
                                (UserSwitchProvider) null);
                hidlToAidlSensorAdapter.mLazySession =
                        new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(
                                hidlToAidlSensorAdapter, 1);
                hidlToAidlSensorAdapter.mLockoutTracker = new LockoutHalImpl();
                int i5 =
                        hidlToAidlSensorAdapter.mLazySession.get() == null
                                ? -10000
                                : ((AidlSession) hidlToAidlSensorAdapter.mLazySession.get())
                                        .mUserId;
                createFaceServiceExImpl(this.mContext, i2, hidlToAidlSensorAdapter);
                final int i6 = 0;
                this.mFaceSensors.addSensor(
                        i2,
                        hidlToAidlSensorAdapter,
                        i5,
                        new SynchronousUserSwitchObserver(this) { // from class:
                            // com.android.server.biometrics.sensors.face.aidl.FaceProvider.1
                            public final /* synthetic */ FaceProvider this$0;

                            {
                                this.this$0 = this;
                            }

                            public final void onUserSwitching(int i7) {
                                switch (i6) {
                                    case 0:
                                        String tag = this.this$0.getTag();
                                        StringBuilder m =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i7, "onUserSwitching: ", ", ");
                                        m.append(this.this$0.mDaemon);
                                        Slog.d(tag, m.toString());
                                        SensorList sensorList = this.this$0.mFaceSensors;
                                        if (sensorList.mSensors.get(i2) != null) {
                                            if (this.this$0.mIsFirstOnUserSwitching
                                                    .booleanValue()) {
                                                FaceProvider faceProvider = this.this$0;
                                                faceProvider.mIsFirstOnUserSwitching =
                                                        Boolean.FALSE;
                                                if (i7 == 0) {
                                                    Slog.d(
                                                            faceProvider.getTag(),
                                                            "onUserSwitching HIDL: skip first event"
                                                                + " with user 0");
                                                    break;
                                                }
                                            }
                                            this.this$0.scheduleInternalCleanup(i2, i7, null);
                                            FaceProvider faceProvider2 = this.this$0;
                                            int i8 = i2;
                                            Binder binder = new Binder();
                                            this.this$0.mContext.getOpPackageName();
                                            faceProvider2.mHandler.post(
                                                    new FaceProvider$$ExternalSyntheticLambda9(
                                                            faceProvider2,
                                                            i8,
                                                            i7,
                                                            binder,
                                                            null,
                                                            1));
                                            break;
                                        } else {
                                            Slog.d(this.this$0.getTag(), "sensor is not added yet");
                                            break;
                                        }
                                    default:
                                        String tag2 = this.this$0.getTag();
                                        StringBuilder m2 =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i7, "onUserSwitching: ", ", ");
                                        m2.append(this.this$0.mDaemon);
                                        Slog.d(tag2, m2.toString());
                                        SensorList sensorList2 = this.this$0.mFaceSensors;
                                        if (sensorList2.mSensors.get(i2) != null) {
                                            if (this.this$0.mIsFirstOnUserSwitching
                                                    .booleanValue()) {
                                                FaceProvider faceProvider3 = this.this$0;
                                                faceProvider3.mIsFirstOnUserSwitching =
                                                        Boolean.FALSE;
                                                if (i7 == 0) {
                                                    Slog.d(
                                                            faceProvider3.getTag(),
                                                            "onUserSwitching: skip first event with"
                                                                + " user 0");
                                                    break;
                                                }
                                            }
                                            this.this$0.scheduleInternalCleanup(i2, i7, null);
                                            break;
                                        } else {
                                            Slog.d(this.this$0.getTag(), "sensor is not added yet");
                                            break;
                                        }
                                }
                            }
                        });
                Slog.d(getTag(), str3 + this.mFaceSensors.mSensors.get(i2));
                i = i3 + 1;
                str2 = str3;
                length = i4;
                sensorPropsArr2 = sensorPropsArr;
            }
        } else {
            Slog.d(getTag(), "Adding AIDL configs");
            for (SensorProps sensorProps2 : sensorPropsArr) {
                final int i7 = sensorProps2.commonProps.sensorId;
                final Sensor sensor =
                        new Sensor(
                                this,
                                this.mContext,
                                this.mHandler,
                                sensorProps2,
                                this.mBiometricContext,
                                z);
                final int i8 = 1;
                sensor.mScheduler =
                        new BiometricScheduler(
                                sensor.mHandler,
                                1,
                                (GestureAvailabilityDispatcher) null,
                                new Supplier() { // from class:
                                                 // com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i9 = i8;
                                        Sensor sensor2 = sensor;
                                        switch (i9) {
                                            case 0:
                                                AidlSession aidlSession = sensor2.mCurrentSession;
                                                if (aidlSession != null) {
                                                    return aidlSession;
                                                }
                                                return null;
                                            default:
                                                AidlSession aidlSession2 = sensor2.mCurrentSession;
                                                return Integer.valueOf(
                                                        aidlSession2 != null
                                                                ? aidlSession2.mUserId
                                                                : -10000);
                                        }
                                    }
                                },
                                sensor.new AnonymousClass1(this.mLockoutResetDispatcher, this));
                final int i9 = 0;
                sensor.mLazySession =
                        new Supplier() { // from class:
                                         // com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i92 = i9;
                                Sensor sensor2 = sensor;
                                switch (i92) {
                                    case 0:
                                        AidlSession aidlSession = sensor2.mCurrentSession;
                                        if (aidlSession != null) {
                                            return aidlSession;
                                        }
                                        return null;
                                    default:
                                        AidlSession aidlSession2 = sensor2.mCurrentSession;
                                        return Integer.valueOf(
                                                aidlSession2 != null
                                                        ? aidlSession2.mUserId
                                                        : -10000);
                                }
                            }
                        };
                sensor.mLockoutTracker = new LockoutCache();
                sensor.mLockoutHalImpl =
                        new SemFaceAidlLockoutHalImpl(
                                sensor.mContext,
                                SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT
                                        ? SemBiometricLockoutTracker.get()
                                        : null,
                                new Sensor$$ExternalSyntheticLambda1(sensor));
                int i10 =
                        sensor.mLazySession.get() == null
                                ? -10000
                                : ((AidlSession) sensor.mLazySession.get()).mUserId;
                createFaceServiceExImpl(this.mContext, i7, sensor);
                final int i11 = 1;
                this.mFaceSensors.addSensor(
                        i7,
                        sensor,
                        i10,
                        new SynchronousUserSwitchObserver(this) { // from class:
                            // com.android.server.biometrics.sensors.face.aidl.FaceProvider.1
                            public final /* synthetic */ FaceProvider this$0;

                            {
                                this.this$0 = this;
                            }

                            public final void onUserSwitching(int i72) {
                                switch (i11) {
                                    case 0:
                                        String tag = this.this$0.getTag();
                                        StringBuilder m =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i72, "onUserSwitching: ", ", ");
                                        m.append(this.this$0.mDaemon);
                                        Slog.d(tag, m.toString());
                                        SensorList sensorList = this.this$0.mFaceSensors;
                                        if (sensorList.mSensors.get(i7) != null) {
                                            if (this.this$0.mIsFirstOnUserSwitching
                                                    .booleanValue()) {
                                                FaceProvider faceProvider = this.this$0;
                                                faceProvider.mIsFirstOnUserSwitching =
                                                        Boolean.FALSE;
                                                if (i72 == 0) {
                                                    Slog.d(
                                                            faceProvider.getTag(),
                                                            "onUserSwitching HIDL: skip first event"
                                                                + " with user 0");
                                                    break;
                                                }
                                            }
                                            this.this$0.scheduleInternalCleanup(i7, i72, null);
                                            FaceProvider faceProvider2 = this.this$0;
                                            int i82 = i7;
                                            Binder binder = new Binder();
                                            this.this$0.mContext.getOpPackageName();
                                            faceProvider2.mHandler.post(
                                                    new FaceProvider$$ExternalSyntheticLambda9(
                                                            faceProvider2,
                                                            i82,
                                                            i72,
                                                            binder,
                                                            null,
                                                            1));
                                            break;
                                        } else {
                                            Slog.d(this.this$0.getTag(), "sensor is not added yet");
                                            break;
                                        }
                                    default:
                                        String tag2 = this.this$0.getTag();
                                        StringBuilder m2 =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i72, "onUserSwitching: ", ", ");
                                        m2.append(this.this$0.mDaemon);
                                        Slog.d(tag2, m2.toString());
                                        SensorList sensorList2 = this.this$0.mFaceSensors;
                                        if (sensorList2.mSensors.get(i7) != null) {
                                            if (this.this$0.mIsFirstOnUserSwitching
                                                    .booleanValue()) {
                                                FaceProvider faceProvider3 = this.this$0;
                                                faceProvider3.mIsFirstOnUserSwitching =
                                                        Boolean.FALSE;
                                                if (i72 == 0) {
                                                    Slog.d(
                                                            faceProvider3.getTag(),
                                                            "onUserSwitching: skip first event with"
                                                                + " user 0");
                                                    break;
                                                }
                                            }
                                            this.this$0.scheduleInternalCleanup(i7, i72, null);
                                            break;
                                        } else {
                                            Slog.d(this.this$0.getTag(), "sensor is not added yet");
                                            break;
                                        }
                                }
                            }
                        });
                Slog.d(getTag(), "Added: " + this.mFaceSensors.mSensors.get(i7));
            }
        }
        for (int i12 = 0; i12 < this.mFaceSensors.mSensors.size(); i12++) {
            Slog.d(
                    "FaceProvider",
                    "init sensors: "
                            + ((ArrayList)
                                            FaceUtils.getInstance(
                                                            this.mFaceSensors.mSensors.keyAt(i12),
                                                            null)
                                                    .getBiometricsForUser(this.mContext, 0))
                                    .size());
        }
        if (Utils.mDBCorrupted) {
            Utils.mDBCorrupted = false;
            Slog.d("FaceProvider", "init sensors: db does not exist !!!");
            this.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(6, this));
        }
    }

    public FaceProvider(
            Context context,
            BiometricStateCallback biometricStateCallback,
            AuthenticationStateListeners authenticationStateListeners,
            SensorProps[] sensorPropsArr,
            String str,
            LockoutResetDispatcher lockoutResetDispatcher,
            BiometricContextProvider biometricContextProvider,
            boolean z) {
        this(
                context,
                biometricStateCallback,
                authenticationStateListeners,
                sensorPropsArr,
                str,
                lockoutResetDispatcher,
                biometricContextProvider,
                null,
                BiometricHandlerProvider.sBiometricHandlerProvider,
                z,
                false);
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e(getTag(), "HAL died");
        this.mHandler.post(new FaceProvider$$ExternalSyntheticLambda2(0, this));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final boolean containsSensor(int i) {
        return this.mFaceSensors.mSensors.contains(i);
    }

    public void createFaceServiceExImpl(Context context, int i, Sensor sensor) {
        if (SemFaceServiceExImpl.mSemFaceServiceExImpl == null) {
            SemFaceServiceExImpl.mSemFaceServiceExImpl = new SemFaceServiceExImpl();
        }
        SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.mSemFaceServiceExImpl;
        semFaceServiceExImpl.mContext = context;
        semFaceServiceExImpl.mSensorId = i;
        semFaceServiceExImpl.mSensor = sensor;
        semFaceServiceExImpl.mScheduler = sensor.mScheduler;
        semFaceServiceExImpl.mProvider = this;
        semFaceServiceExImpl.mPowerManager =
                (PowerManager) context.getSystemService(PowerManager.class);
        semFaceServiceExImpl.mAppOpsManager =
                (AppOpsManager) semFaceServiceExImpl.mContext.getSystemService(AppOpsManager.class);
        synchronized (semFaceServiceExImpl) {
            semFaceServiceExImpl.mWakeLock =
                    semFaceServiceExImpl.mPowerManager.newWakeLock(268435466, "SemFace");
        }
        semFaceServiceExImpl.mOrientationEventListener =
                semFaceServiceExImpl.new AnonymousClass2(semFaceServiceExImpl.mContext);
        boolean z = Utils.DEBUG;
        if (SystemProperties.get("ro.build.characteristics", "").contains("tablet")) {
            semFaceServiceExImpl.mProximitySensorMgr =
                    semFaceServiceExImpl.new AnonymousClass3(semFaceServiceExImpl.mContext);
        } else {
            semFaceServiceExImpl.mProximitySensorMgr = null;
        }
        semFaceServiceExImpl.mFaceUtils =
                FaceUtils.getInstance(semFaceServiceExImpl.mSensorId, "settings_face.xml");
        semFaceServiceExImpl.mContext.registerReceiver(
                semFaceServiceExImpl.new AnonymousClass4(),
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.ACTION_SHUTDOWN"));
        int i2 = semFaceServiceExImpl.mSensor.mSensorProperties.sensorStrength;
        int i3 = 3;
        if (i2 != 0) {
            if (i2 == 1) {
                i3 = 2;
            } else if (i2 != 2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i2,
                        "propertyStrengthToOemStrength: Unknown security level ",
                        "BiometricUtils");
            } else {
                i3 = 1;
            }
        }
        semFaceServiceExImpl.mSecurityLevel = i3;
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                new StringBuilder("SL :"),
                semFaceServiceExImpl.mSecurityLevel,
                "(1:Strong)",
                "SemFace");
    }

    public final BiometricLogger createLogger(
            int i, int i2, AuthenticationStatsCollector authenticationStatsCollector) {
        return new BiometricLogger(this.mContext, 4, i, i2, authenticationStatsCollector);
    }

    public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback) {
        Sensor sensor = (Sensor) this.mFaceSensors.mSensors.get(i);
        sensor.getClass();
        return new BiometricTestSessionImpl(
                sensor.mContext,
                sensor.mSensorProperties.sensorId,
                iTestSessionCallback,
                sensor.mProvider,
                sensor);
    }

    public final void dumpInternal(int i, PrintWriter printWriter) {
        PerformanceTracker instanceForSensorId = PerformanceTracker.getInstanceForSensorId(i);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("service", getTag());
            JSONArray jSONArray = new JSONArray();
            Iterator it = UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                int size =
                        ((ArrayList)
                                        FaceUtils.getInstance(i, null)
                                                .getBiometricsForUser(this.mContext, identifier))
                                .size();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("id", identifier);
                jSONObject2.put("count", size);
                jSONObject2.put(
                        "accept",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mAccept
                                : 0);
                jSONObject2.put(
                        "reject",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mReject
                                : 0);
                jSONObject2.put(
                        "acquire",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mAcquire
                                : 0);
                jSONObject2.put(
                        "lockout",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mTimedLockout
                                : 0);
                jSONObject2.put(
                        "permanentLockout",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mPermanentLockout
                                : 0);
                jSONObject2.put(
                        "acceptCrypto",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mAcceptCrypto
                                : 0);
                jSONObject2.put(
                        "rejectCrypto",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mRejectCrypto
                                : 0);
                jSONObject2.put(
                        "acquireCrypto",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mAcquireCrypto
                                : 0);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("prints", jSONArray);
        } catch (JSONException e) {
            Slog.e(getTag(), "dump formatting failure", e);
        }
        printWriter.println(jSONObject);
        printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.mHALDeathCount);
        printWriter.println("---AuthSessionCoordinator logs begin---");
        printWriter.println(
                ((BiometricContextProvider) this.mBiometricContext).mAuthSessionCoordinator);
        printWriter.println("---AuthSessionCoordinator logs end  ---");
        ((Sensor) this.mFaceSensors.mSensors.get(i)).mScheduler.dump(printWriter);
        UsageStats usageStats = this.mUsageStats;
        printWriter.println(
                "Printing most recent events since last reboot("
                        + usageStats.mAuthenticationEvents.size()
                        + " events)");
        Iterator it2 = usageStats.mAuthenticationEvents.iterator();
        while (true) {
            String str = "";
            if (!it2.hasNext()) {
                break;
            }
            UsageStats.AuthenticationEvent authenticationEvent =
                    (UsageStats.AuthenticationEvent) it2.next();
            Context context = usageStats.mContext;
            StringBuilder sb = new StringBuilder("Start: ");
            sb.append(authenticationEvent.mStartTime);
            sb.append("\tLatency: ");
            sb.append(authenticationEvent.mLatency);
            sb.append("\tAuthenticated: ");
            sb.append(authenticationEvent.mAuthenticated);
            sb.append("\tError: ");
            int i2 = authenticationEvent.mError;
            sb.append(i2);
            sb.append("\tVendorCode: ");
            int i3 = authenticationEvent.mVendorError;
            sb.append(i3);
            sb.append("\tUser: ");
            sb.append(authenticationEvent.mUser);
            sb.append("\t");
            if (i2 != 0) {
                str = FaceManager.getErrorString(context, i2, i3);
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, str, printWriter);
        }
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "", "Accept Count: ");
        m$1.append(usageStats.mAcceptCount);
        m$1.append("\tLatency: ");
        m$1.append(usageStats.mAcceptLatency);
        m$1.append("\tAverage: ");
        int i4 = usageStats.mAcceptCount;
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m$1,
                        i4 > 0 ? usageStats.mAcceptLatency / i4 : 0L,
                        printWriter,
                        "Reject Count: ");
        m.append(usageStats.mRejectCount);
        m.append("\tLatency: ");
        m.append(usageStats.mRejectLatency);
        m.append("\tAverage: ");
        int i5 = usageStats.mRejectCount;
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m,
                        i5 > 0 ? usageStats.mRejectLatency / i5 : 0L,
                        printWriter,
                        "Total Error Count: ");
        m2.append(usageStats.mErrorCount);
        m2.append("\tLatency: ");
        m2.append(usageStats.mErrorLatency);
        m2.append("\tAverage: ");
        int i6 = usageStats.mErrorCount;
        StringBuilder m3 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m2,
                        i6 > 0 ? usageStats.mErrorLatency / i6 : 0L,
                        printWriter,
                        "Total Attempts: ");
        m3.append(usageStats.mAuthAttemptCount);
        printWriter.println(m3.toString());
        printWriter.println("");
        int i7 = 0;
        while (i7 < usageStats.mErrorFrequencyMap.size()) {
            int keyAt = usageStats.mErrorFrequencyMap.keyAt(i7);
            int i8 = usageStats.mErrorFrequencyMap.get(keyAt);
            StringBuilder m4 =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            keyAt, i8, "Error", "\tCount: ", "\tLatency: ");
            int i9 = i7;
            m4.append(usageStats.mErrorLatencyMap.get(keyAt, 0L));
            m4.append("\tAverage: ");
            m4.append(i8 > 0 ? usageStats.mErrorLatencyMap.get(keyAt, 0L) / i8 : 0L);
            m4.append("\t");
            m4.append(FaceManager.getErrorString(usageStats.mContext, keyAt, 0));
            printWriter.println(m4.toString());
            i7 = i9 + 1;
        }
        SemFaceServiceExImpl serviceExtImpl = getServiceExtImpl();
        serviceExtImpl.getClass();
        try {
            printWriter.println(" current User : " + serviceExtImpl.mUserId);
            StringBuilder sb2 = new StringBuilder(" SL : ");
            sb2.append(serviceExtImpl.mSecurityLevel);
            sb2.append(" , ");
            Slog.i("SemFace", "getSL : " + serviceExtImpl.mSecurityLevel);
            sb2.append(serviceExtImpl.mSecurityLevel);
            sb2.append(SemBiometricFeature.FEATURE_JDM_HAL ? " , J" : " , S");
            printWriter.println(sb2.toString());
            printWriter.println(
                    " FALI : "
                            + serviceExtImpl.mFALI
                            + ", FABK : "
                            + serviceExtImpl.mFABK
                            + ", FAMO : "
                            + serviceExtImpl.mFAMO
                            + ", FALQ : "
                            + serviceExtImpl.mFALQ
                            + ", FANM : "
                            + serviceExtImpl.mFANM);
            SemBioLoggingManager.get().faceDump(printWriter);
        } catch (Exception e2) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("dump: "), "SemFace");
        }
    }

    public final void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
        if (this.mFaceSensors.mSensors.contains(i)) {
            Sensor sensor = (Sensor) this.mFaceSensors.mSensors.get(i);
            sensor.getClass();
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, sensor.mSensorProperties.sensorId);
            protoOutputStream.write(1159641169922L, 2);
            protoOutputStream.write(
                    1120986464259L, Utils.getCurrentStrength(sensor.mSensorProperties.sensorId));
            protoOutputStream.write(1146756268036L, sensor.mScheduler.dumpProtoState(z));
            Iterator it = UserManager.get(sensor.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                long start2 = protoOutputStream.start(2246267895813L);
                protoOutputStream.write(1120986464257L, identifier);
                protoOutputStream.write(
                        1120986464258L,
                        ((ArrayList)
                                        FaceUtils.getInstance(
                                                        sensor.mSensorProperties.sensorId, null)
                                                .getBiometricsForUser(sensor.mContext, identifier))
                                .size());
                protoOutputStream.end(start2);
            }
            protoOutputStream.write(
                    1133871366150L, sensor.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
            protoOutputStream.write(
                    1133871366151L, sensor.mSensorProperties.resetLockoutRequiresChallenge);
            protoOutputStream.end(start);
        }
    }

    public final long getAuthenticatorId(int i, int i2) {
        return ((Long)
                        ((HashMap) ((Sensor) this.mFaceSensors.mSensors.get(i)).mAuthenticatorIds)
                                .getOrDefault(Integer.valueOf(i2), 0L))
                .longValue();
    }

    public synchronized IFace getHalInstance() {
        SehTestHal sehTestHal;
        try {
            if (getServiceExtImpl().isTpaSehTestHalEnabled()) {
                Context context = this.mContext;
                int i = getServiceExtImpl().mSensorId;
                SehTestHal sehTestHal2 = SehTestHal.mSehTestHal;
                synchronized (SehTestHal.class) {
                    try {
                        if (SehTestHal.mSehTestHal == null) {
                            SehTestHal.mSehTestHal = new SehTestHal(context, i);
                        }
                        sehTestHal = SehTestHal.mSehTestHal;
                    } finally {
                    }
                }
                return sehTestHal;
            }
            if (this.mTestHalEnabled) {
                return new TestHal();
            }
            if (this.mDaemon != null) {
                if (getServiceExtImpl().isUsingSehAPI()) {
                    SemFaceServiceExImpl serviceExtImpl = getServiceExtImpl();
                    if (serviceExtImpl.mISehSession == null || serviceExtImpl.mISession == null) {
                        Slog.d(getTag(), "HAL connection closed, reconnecting");
                        getServiceExtImpl().semConnectSession(this.mDaemon);
                    }
                }
                return this.mDaemon;
            }
            Slog.d(getTag(), "Daemon was null, reconnecting");
            IFace asInterface =
                    IFace.Stub.asInterface(
                            Binder.allowBlocking(
                                    ServiceManager.waitForDeclaredService(
                                            IFace.DESCRIPTOR + "/" + this.mHalInstanceName)));
            this.mDaemon = asInterface;
            if (asInterface == null) {
                Slog.e(getTag(), "Unable to get daemon");
                return null;
            }
            try {
                asInterface.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.e(getTag(), "Unable to linkToDeath", e);
            }
            if (getServiceExtImpl().isUsingSehAPI()) {
                getServiceExtImpl().semConnectSession(this.mDaemon);
                if (getServiceExtImpl().mIsResetting) {
                    return this.mDaemon;
                }
            }
            for (int i2 = 0; i2 < this.mFaceSensors.mSensors.size(); i2++) {
                int keyAt = this.mFaceSensors.mSensors.keyAt(i2);
                Iterator it = UserManager.get(this.mContext).getAliveUsers().iterator();
                while (it.hasNext()) {
                    scheduleLoadAuthenticatorIdsForUser(keyAt, ((UserInfo) it.next()).id);
                }
                scheduleInternalCleanup(keyAt, ActivityManager.getCurrentUser(), null);
            }
            if (Build.isDebuggable()) {
                FaceUtils faceUtils =
                        FaceUtils.getInstance(this.mFaceSensors.mSensors.keyAt(0), null);
                for (UserInfo userInfo : UserManager.get(this.mContext).getAliveUsers()) {
                    List biometricsForUser =
                            faceUtils.getBiometricsForUser(this.mContext, userInfo.id);
                    Slog.d(
                            getTag(),
                            "Expecting enrollments for user "
                                    + userInfo.id
                                    + ": "
                                    + biometricsForUser.stream()
                                            .map(new FaceProvider$$ExternalSyntheticLambda3())
                                            .toList());
                }
            }
            return this.mDaemon;
        } catch (Throwable th) {
            throw th;
        }
        throw th;
    }

    public final int getLockoutModeForUser(int i, int i2) {
        return ((Sensor) this.mFaceSensors.mSensors.get(i)).getLockoutModeForUser(i2);
    }

    public final SensorPropertiesInternal getSensorProperties(int i) {
        return ((Sensor) this.mFaceSensors.mSensors.get(i)).mSensorProperties;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final List getSensorProperties() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mFaceSensors.mSensors.size(); i++) {
            arrayList.add(((Sensor) this.mFaceSensors.mSensors.valueAt(i)).mSensorProperties);
        }
        return arrayList;
    }

    public SemFaceServiceExImpl getServiceExtImpl() {
        return SemFaceServiceExImpl.getInstance();
    }

    public final String getTag() {
        return "FaceProvider/" + this.mHalInstanceName;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final boolean hasEnrollments(int i, int i2) {
        return !FaceUtils.getInstance(i, null).getBiometricsForUser(this.mContext, i2).isEmpty();
    }

    public final boolean isHardwareDetected(int i) {
        return ((Sensor) this.mFaceSensors.mSensors.get(i))
                .isHardwareDetected(this.mHalInstanceName);
    }

    public final void scheduleForSensor(int i, BaseClientMonitor baseClientMonitor) {
        if (this.mFaceSensors.mSensors.contains(i)) {
            ((Sensor) this.mFaceSensors.mSensors.get(i))
                    .mScheduler.scheduleClientMonitor(baseClientMonitor, null);
            return;
        }
        throw new IllegalStateException(
                "Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
    }

    public final void scheduleForSensor(
            int i, HalClientMonitor halClientMonitor, ClientMonitorCallback clientMonitorCallback) {
        if (this.mFaceSensors.mSensors.contains(i)) {
            ((Sensor) this.mFaceSensors.mSensors.get(i))
                    .mScheduler.scheduleClientMonitor(halClientMonitor, clientMonitorCallback);
            return;
        }
        throw new IllegalStateException(
                "Unable to schedule client: " + halClientMonitor + " for sensor: " + i);
    }

    public final void scheduleInternalCleanup(
            final int i, final int i2, ClientMonitorCallback clientMonitorCallback) {
        final BiometricTestSessionImpl.AnonymousClass2 anonymousClass2 =
                (BiometricTestSessionImpl.AnonymousClass2) clientMonitorCallback;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda1
                    public final /* synthetic */ boolean f$3 = true;

                    @Override // java.lang.Runnable
                    public final void run() {
                        final FaceProvider faceProvider = FaceProvider.this;
                        int i3 = i;
                        int i4 = i2;
                        boolean z = this.f$3;
                        ClientMonitorCallback clientMonitorCallback2 = anonymousClass2;
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i3))
                                .scheduleFaceUpdateActiveUserClient(i4);
                        InternalCleanupClient internalCleanupClient =
                                new InternalCleanupClient(
                                        faceProvider.mContext,
                                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i3))
                                                .mLazySession,
                                        i4,
                                        faceProvider.mContext.getOpPackageName(),
                                        i3,
                                        faceProvider.createLogger(
                                                3, 0, faceProvider.mAuthenticationStatsCollector),
                                        faceProvider.mBiometricContext,
                                        FaceUtils.getInstance(i3, null),
                                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i3))
                                                .mAuthenticatorIds) { // from class:
                                                                      // com.android.server.biometrics.sensors.face.aidl.FaceProvider.4
                                    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
                                    public final InternalEnumerateClient getEnumerateClient(
                                            Context context,
                                            Supplier supplier,
                                            IBinder iBinder,
                                            int i5,
                                            String str,
                                            List list,
                                            BiometricUtils biometricUtils,
                                            int i6,
                                            BiometricLogger biometricLogger,
                                            BiometricContext biometricContext) {
                                        return new FaceInternalEnumerateClient(
                                                context,
                                                supplier,
                                                iBinder,
                                                i5,
                                                str,
                                                list,
                                                biometricUtils,
                                                i6,
                                                biometricLogger,
                                                biometricContext);
                                    }

                                    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
                                    public final RemovalClient getRemovalClient(
                                            Context context,
                                            Supplier supplier,
                                            IBinder iBinder,
                                            int i5,
                                            int i6,
                                            String str,
                                            BiometricUtils biometricUtils,
                                            int i7,
                                            BiometricLogger biometricLogger,
                                            BiometricContext biometricContext,
                                            Map map) {
                                        return new FaceRemovalClient(
                                                context,
                                                supplier,
                                                iBinder,
                                                null,
                                                new int[] {i5},
                                                i6,
                                                str,
                                                biometricUtils,
                                                i7,
                                                biometricLogger,
                                                biometricContext,
                                                map);
                                    }

                                    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                                    public final boolean interruptsPrecedingClients() {
                                        if (FaceProvider.this.mTestHalEnabled) {
                                            return true;
                                        }
                                        return this instanceof FaceDetectClient;
                                    }

                                    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
                                    public final void onAddUnknownTemplate(
                                            BiometricAuthenticator.Identifier identifier) {
                                        Face face = (Face) identifier;
                                        FaceUserState stateForUser =
                                                FaceUtils.getInstance(this.mSensorId, null)
                                                        .getStateForUser(
                                                                this.mContext, this.mTargetUserId);
                                        synchronized (stateForUser) {
                                            stateForUser.mBiometrics.add(face);
                                            AsyncTask.execute(stateForUser.mWriteStateRunnable);
                                        }
                                    }
                                };
                        if (z) {
                            internalCleanupClient.mFavorHalEnrollments = true;
                        }
                        faceProvider.scheduleForSensor(
                                i3,
                                internalCleanupClient,
                                new ClientMonitorCompositeCallback(
                                        clientMonitorCallback2,
                                        faceProvider.mBiometricStateCallback));
                    }
                });
    }

    public final void scheduleLoadAuthenticatorIdsForUser(int i, int i2) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(
                i, i2, "scheduleLoadAuthenticatorIdsForUser: ", ", ", "FaceProvider");
        if (this.mFaceSensors.mSensors.contains(i)) {
            this.mHandler.post(new FaceProvider$$ExternalSyntheticLambda15(this, i, i2, 1));
        }
    }

    public final void scheduleWatchdog(int i) {
        Slog.d(getTag(), "Starting watchdog for face");
        BiometricScheduler biometricScheduler =
                ((Sensor) this.mFaceSensors.mSensors.get(i)).mScheduler;
        if (biometricScheduler == null) {
            return;
        }
        biometricScheduler.startWatchdog();
    }

    public final int semGetRemainingLockoutTime(int i) {
        try {
            return ((Sensor) this.mFaceSensors.mSensors.valueAt(0)).getRemainingLockoutTime(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            Slog.w(this.getTag(), "semGetRemainingLockoutTime: " + e.getMessage());
            return 0;
        }
    }
}
