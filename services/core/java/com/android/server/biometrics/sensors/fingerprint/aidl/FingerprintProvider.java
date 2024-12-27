package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.SynchronousUserSwitchObserver;
import android.app.TaskStackListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.TypedArray;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintServiceReceiver;
import android.hardware.fingerprint.HidlFingerprintSensorConfig;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.AuthenticationStatsBroadcastReceiver;
import com.android.server.biometrics.AuthenticationStatsCollector;
import com.android.server.biometrics.BiometricDanglingReceiver;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.Flags;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.BiometricScheduler$$ExternalSyntheticLambda1;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.InternalCleanupClient;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.RemovalClient;
import com.android.server.biometrics.sensors.SensorList;
import com.android.server.biometrics.sensors.face.aidl.FaceDetectClient;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import com.android.server.biometrics.sensors.fingerprint.PowerPressHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.fingerprint.SemFpProviderEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler;
import com.android.server.biometrics.sensors.fingerprint.ServiceProvider;
import com.android.server.biometrics.sensors.fingerprint.Udfps;
import com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass1;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.AnonymousClass1;
import com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl;
import com.android.server.input.KeyboardMetricsCollector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class FingerprintProvider implements IBinder.DeathRecipient, ServiceProvider {
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public AuthenticationStatsCollector mAuthenticationStatsCollector;
    public final BiometricContext mBiometricContext;
    public final BiometricHandlerProvider mBiometricHandlerProvider;
    public final BiometricStateCallback mBiometricStateCallback;
    public final SemFpCallbackDispatcher mCallbackDispatcher;
    public final Context mContext;
    public IFingerprint mDaemon;
    public final String mHalInstanceName;
    public String mHalInstanceNameCurrent;
    public final Handler mHandler;
    public final boolean mIsForHidl;
    public boolean mIsHalStarted;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public long mPowerSinglePressedTimeStamp;
    public final SemFpProviderEx mProviderEx;
    public ISehFingerprint mSehFingerprint;
    public final BiometricTaskStackListener mTaskStackListener;
    public boolean mTestHalEnabled;
    public boolean mTpaHalModeEnabled;
    public SemTpaTestHal mTpaTestHal;
    public final AtomicLong mRequestCounter = new AtomicLong(0);
    public final ArrayList mLifecycleListeners = new ArrayList();
    public final AnonymousClass1 mInternalCleanupClientCallback = new AnonymousClass1(this, 0);
    final SensorList mFingerprintSensors = new SensorList(ActivityManager.getService());

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientMonitorCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ FingerprintProvider this$0;

        public /* synthetic */ AnonymousClass1(FingerprintProvider fingerprintProvider, int i) {
            this.$r8$classId = i;
            this.this$0 = fingerprintProvider;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mHandler.post(
                            new FingerprintProvider$$ExternalSyntheticLambda1(
                                    2, this, baseClientMonitor));
                    break;
                case 1:
                    if (z) {
                        this.this$0.mProviderEx.updateCacheDataOfHAL(baseClientMonitor.mSensorId);
                        break;
                    }
                    break;
                default:
                    final SemFpCallbackDispatcher semFpCallbackDispatcher =
                            this.this$0.mCallbackDispatcher;
                    final int i = baseClientMonitor.mSensorId;
                    final int i2 = baseClientMonitor.mTargetUserId;
                    semFpCallbackDispatcher.getClass();
                    semFpCallbackDispatcher.mHandler.post(
                            new Runnable(
                                    i,
                                    i2) { // from class:
                                          // com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Iterator it =
                                            ((ArrayList)
                                                            SemFpCallbackDispatcher.this
                                                                    .mResetLockoutListeners)
                                                    .iterator();
                                    while (it.hasNext()) {
                                        Pair pair =
                                                ((SemFpResetLockoutDispatcher) it.next())
                                                        .mProviderPair;
                                        ((FingerprintProvider) ((ServiceProvider) pair.second))
                                                .semRequest(
                                                        ((Integer) pair.first).intValue(),
                                                        42,
                                                        0,
                                                        null,
                                                        null);
                                    }
                                }
                            });
                    break;
            }
        }
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$6, reason: invalid class name */
    public final class AnonymousClass6 implements ClientMonitorCallback {
        public final /* synthetic */ long val$requestId;
        public final /* synthetic */ int val$sensorId;
        public final /* synthetic */ int val$userId;

        public AnonymousClass6(int i, int i2, long j) {
            this.val$userId = i;
            this.val$sensorId = i2;
            this.val$requestId = j;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onBiometricAction() {
            FingerprintProvider fingerprintProvider = FingerprintProvider.this;
            fingerprintProvider.mBiometricStateCallback.onBiometricAction();
            fingerprintProvider.mCallbackDispatcher.getClass();
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, final boolean z) {
            FingerprintProvider fingerprintProvider = FingerprintProvider.this;
            fingerprintProvider.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
            Handler biometricCallbackHandler =
                    fingerprintProvider.mBiometricHandlerProvider.getBiometricCallbackHandler();
            final int i = this.val$sensorId;
            final long j = this.val$requestId;
            final int i2 = this.val$userId;
            biometricCallbackHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$6$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintProvider.AnonymousClass6 anonymousClass6 =
                                    FingerprintProvider.AnonymousClass6.this;
                            int i3 = i2;
                            int i4 = i;
                            FingerprintProvider.this.mAuthSessionCoordinator.authEndedFor(
                                    j, i3, Utils.getCurrentStrength(i4), i4, z);
                        }
                    });
            fingerprintProvider.mCallbackDispatcher.onClientFinished(baseClientMonitor, z);
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
            FingerprintProvider fingerprintProvider = FingerprintProvider.this;
            fingerprintProvider.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            Handler biometricCallbackHandler =
                    fingerprintProvider.mBiometricHandlerProvider.getBiometricCallbackHandler();
            final int i = this.val$userId;
            final int i2 = this.val$sensorId;
            final long j = this.val$requestId;
            biometricCallbackHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$6$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintProvider.AnonymousClass6 anonymousClass6 =
                                    FingerprintProvider.AnonymousClass6.this;
                            FingerprintProvider.this.mAuthSessionCoordinator.authStartedFor(
                                    i, i2, j);
                        }
                    });
            fingerprintProvider.mCallbackDispatcher.onClientStarted(baseClientMonitor);
        }
    }

    public final class BiometricTaskStackListener extends TaskStackListener
            implements ActivityManager.SemProcessListener {
        public BiometricTaskStackListener() {}

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            String tag$1 = FingerprintProvider.this.getTag$1();
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "onForegroundActivitiesChanged: pid = ",
                            ", uid = ",
                            ", foregroundActivities = ");
            m.append(z);
            Slog.d(tag$1, m.toString());
            onTaskStackChanged();
        }

        public final void onProcessDied(int i, int i2) {}

        public final void onTaskStackChanged() {
            FingerprintProvider.this.mHandler.post(
                    new FingerprintProvider$$ExternalSyntheticLambda4(5, this));
        }
    }

    public FingerprintProvider(
            Context context,
            BiometricStateCallback biometricStateCallback,
            AuthenticationStateListeners authenticationStateListeners,
            SensorProps[] sensorPropsArr,
            String str,
            LockoutResetDispatcher lockoutResetDispatcher,
            GestureAvailabilityDispatcher gestureAvailabilityDispatcher,
            BiometricContext biometricContext,
            IFingerprint iFingerprint,
            BiometricHandlerProvider biometricHandlerProvider,
            boolean z,
            boolean z2) {
        String str2;
        SensorProps[] sensorPropsArr2 = sensorPropsArr;
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mHalInstanceName = str;
        this.mHandler = biometricHandlerProvider.getFingerprintHandler();
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        ActivityTaskManager.getInstance();
        this.mTaskStackListener = new BiometricTaskStackListener();
        this.mBiometricContext = biometricContext;
        this.mAuthSessionCoordinator =
                ((BiometricContextProvider) biometricContext).mAuthSessionCoordinator;
        this.mDaemon = iFingerprint;
        this.mTestHalEnabled = z2;
        this.mBiometricHandlerProvider = biometricHandlerProvider;
        boolean z3 =
                "defaultHIDL".contentEquals(str)
                        || (sensorPropsArr2 instanceof HidlFingerprintSensorConfig[]);
        this.mIsForHidl = z3;
        this.mProviderEx = createSemFpProviderEx();
        this.mCallbackDispatcher = createSemFpCallbackCenter();
        new AuthenticationStatsBroadcastReceiver(
                context,
                1,
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FingerprintProvider fingerprintProvider = FingerprintProvider.this;
                        Slog.d(
                                fingerprintProvider.getTag$1(),
                                "Initializing AuthenticationStatsCollector");
                        fingerprintProvider.mAuthenticationStatsCollector =
                                (AuthenticationStatsCollector) obj;
                    }
                });
        new BiometricDanglingReceiver(context, 1);
        String str3 = "FingerprintProvider";
        if (sensorPropsArr2 == null) {
            Slog.w("FingerprintProvider", "initSensors: props is null");
            return;
        }
        String str4 = "Added: ";
        if (z3) {
            Slog.d(getTag$1(), "Adding HIDL configs");
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
                String str5 = str4;
                String str6 = str3;
                HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                        new HidlToAidlSensorAdapter(
                                this,
                                context2,
                                handler,
                                sensorProps,
                                lockoutResetDispatcher2,
                                biometricContext2,
                                z,
                                new FingerprintProvider$$ExternalSyntheticLambda9(this, i2, 2),
                                ((BiometricContextProvider) biometricContext2)
                                        .mAuthSessionCoordinator,
                                null,
                                null);
                hidlToAidlSensorAdapter.mLazySession =
                        new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(
                                hidlToAidlSensorAdapter, 0);
                hidlToAidlSensorAdapter.mScheduler =
                        new BiometricScheduler(
                                hidlToAidlSensorAdapter.mHandler,
                                hidlToAidlSensorAdapter.mSensorProperties.isAnyUdfpsType() ? 2 : 3,
                                gestureAvailabilityDispatcher,
                                new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(
                                        hidlToAidlSensorAdapter, 1),
                                hidlToAidlSensorAdapter.new AnonymousClass1());
                final Context context3 = hidlToAidlSensorAdapter.mContext;
                hidlToAidlSensorAdapter.mLockoutTracker =
                        new LockoutFrameworkImpl(
                                context3,
                                new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(
                                        hidlToAidlSensorAdapter),
                                new Function() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Integer num = (Integer) obj;
                                        return PendingIntent.getBroadcast(
                                                context3,
                                                num.intValue(),
                                                new Intent(
                                                                "com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET")
                                                        .putExtra("lockout_reset_user", num),
                                                201326592);
                                    }
                                },
                                hidlToAidlSensorAdapter.mHandler);
                final int i4 = 0;
                this.mFingerprintSensors.addSensor(
                        i2,
                        hidlToAidlSensorAdapter,
                        hidlToAidlSensorAdapter.mLazySession.get() == null
                                ? -10000
                                : ((AidlSession) hidlToAidlSensorAdapter.mLazySession.get())
                                        .mUserId,
                        new SynchronousUserSwitchObserver(
                                this) { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.2
                            public final /* synthetic */ FingerprintProvider this$0;

                            {
                                this.this$0 = this;
                            }

                            public final void onUserSwitching(int i5) {
                                switch (i4) {
                                    case 0:
                                        StringBuilder m =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i5, "onUserSwitching: ", ", ");
                                        m.append(this.this$0.mDaemon);
                                        Slog.d("FingerprintProvider", m.toString());
                                        FingerprintProvider fingerprintProvider = this.this$0;
                                        if (fingerprintProvider.mDaemon != null) {
                                            fingerprintProvider.scheduleInternalCleanup(
                                                    i2, i5, null);
                                            break;
                                        }
                                        break;
                                    default:
                                        StringBuilder m2 =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i5, "onUserSwitching: ", ", ");
                                        m2.append(this.this$0.mDaemon);
                                        Slog.d("FingerprintProvider", m2.toString());
                                        FingerprintProvider fingerprintProvider2 = this.this$0;
                                        if (fingerprintProvider2.mDaemon != null) {
                                            fingerprintProvider2.scheduleInternalCleanup(
                                                    i2, i5, null);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                Slog.d(
                        getTag$1(),
                        str5 + ((Sensor) this.mFingerprintSensors.mSensors.get(i2)).toString());
                i = i3 + 1;
                str4 = str5;
                str3 = str6;
                sensorPropsArr2 = sensorPropsArr;
            }
            str2 = str3;
        } else {
            str2 = "FingerprintProvider";
            Slog.d(getTag$1(), "Adding AIDL configs");
            ArrayList arrayList = new ArrayList();
            TypedArray obtainTypedArray = context.getResources().obtainTypedArray(17236317);
            for (int i5 = 0; i5 < obtainTypedArray.length(); i5++) {
                int resourceId = obtainTypedArray.getResourceId(i5, -1);
                if (resourceId > 0) {
                    TypedArray obtainTypedArray2 =
                            context.getResources().obtainTypedArray(resourceId);
                    SensorLocationInternal sensorLocationInternal = null;
                    if (obtainTypedArray2 != null) {
                        try {
                            sensorLocationInternal =
                                    new SensorLocationInternal(
                                            obtainTypedArray2.getString(0),
                                            obtainTypedArray2.getInt(1, 0),
                                            obtainTypedArray2.getInt(2, 0),
                                            obtainTypedArray2.getInt(3, 0));
                        } catch (Exception e) {
                            Slog.w(getTag$1(), "malformed sensor location", e);
                        }
                    }
                    if (sensorLocationInternal != null) {
                        arrayList.add(sensorLocationInternal);
                    }
                }
            }
            obtainTypedArray.recycle();
            for (SensorProps sensorProps2 : sensorPropsArr) {
                final int i6 = sensorProps2.commonProps.sensorId;
                final Sensor sensor =
                        new Sensor(
                                this,
                                this.mContext,
                                this.mHandler,
                                Sensor.getFingerprintSensorPropertiesInternal(
                                        sensorProps2, arrayList, z),
                                this.mBiometricContext);
                final int i7 = 1;
                sensor.mScheduler =
                        new BiometricScheduler(
                                sensor.mHandler,
                                sensor.mSensorProperties.isAnyUdfpsType() ? 2 : 3,
                                gestureAvailabilityDispatcher,
                                new Supplier() { // from class:
                                                 // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i8 = i7;
                                        Sensor sensor2 = sensor;
                                        switch (i8) {
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
                                sensor.new AnonymousClass1(this.mLockoutResetDispatcher));
                sensor.mLockoutTracker = new LockoutCache();
                final int i8 = 0;
                Supplier supplier =
                        new Supplier() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i82 = i8;
                                Sensor sensor2 = sensor;
                                switch (i82) {
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
                sensor.mLazySession = supplier;
                final int i9 = 1;
                this.mFingerprintSensors.addSensor(
                        i6,
                        sensor,
                        supplier.get() == null
                                ? -10000
                                : ((AidlSession) sensor.mLazySession.get()).mUserId,
                        new SynchronousUserSwitchObserver(
                                this) { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.2
                            public final /* synthetic */ FingerprintProvider this$0;

                            {
                                this.this$0 = this;
                            }

                            public final void onUserSwitching(int i52) {
                                switch (i9) {
                                    case 0:
                                        StringBuilder m =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i52, "onUserSwitching: ", ", ");
                                        m.append(this.this$0.mDaemon);
                                        Slog.d("FingerprintProvider", m.toString());
                                        FingerprintProvider fingerprintProvider = this.this$0;
                                        if (fingerprintProvider.mDaemon != null) {
                                            fingerprintProvider.scheduleInternalCleanup(
                                                    i6, i52, null);
                                            break;
                                        }
                                        break;
                                    default:
                                        StringBuilder m2 =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i52, "onUserSwitching: ", ", ");
                                        m2.append(this.this$0.mDaemon);
                                        Slog.d("FingerprintProvider", m2.toString());
                                        FingerprintProvider fingerprintProvider2 = this.this$0;
                                        if (fingerprintProvider2.mDaemon != null) {
                                            fingerprintProvider2.scheduleInternalCleanup(
                                                    i6, i52, null);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                this.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda4(3, this));
                Slog.d(
                        getTag$1(),
                        "Added: "
                                + ((Sensor) this.mFingerprintSensors.mSensors.get(i6)).toString());
            }
        }
        for (int i10 = 0; i10 < this.mFingerprintSensors.mSensors.size(); i10++) {
            Slog.d(
                    str2,
                    "init sensors: "
                            + FingerprintUtils.getInstance(
                                            this.mFingerprintSensors.mSensors.keyAt(i10))
                                    .getBiometricsForUser(this.mContext, 0)
                                    .size());
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e(getTag$1(), "HAL died");
        this.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda4(0, this));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final boolean containsSensor(int i) {
        return this.mFingerprintSensors.mSensors.contains(i);
    }

    public final BiometricLogger createLogger$1(
            int i, int i2, AuthenticationStatsCollector authenticationStatsCollector) {
        return new BiometricLogger(this.mContext, 1, i, i2, authenticationStatsCollector);
    }

    public SemFpCallbackDispatcher createSemFpCallbackCenter() {
        return new SemFpCallbackDispatcher(this, this.mHandler);
    }

    public SemFpProviderEx createSemFpProviderEx() {
        final int i = 0;
        final int i2 = 1;
        return new SemFpProviderEx(
                new BiFunction(
                        this) { // from class:
                                // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda5
                    public final /* synthetic */ FingerprintProvider f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                FingerprintProvider fingerprintProvider = this.f$0;
                                fingerprintProvider.getClass();
                                byte[] bArr = new byte[2048];
                                int semRequest =
                                        fingerprintProvider.semRequest(
                                                ((Integer) obj).intValue(),
                                                ((Integer) obj2).intValue(),
                                                0,
                                                null,
                                                bArr);
                                return TextUtils.emptyIfNull(
                                        semRequest > 0
                                                ? new String(
                                                        Arrays.copyOf(bArr, semRequest),
                                                        StandardCharsets.UTF_8)
                                                : null);
                            default:
                                return Integer.valueOf(
                                        this.f$0.semRequest(
                                                ((Integer) obj).intValue(),
                                                ((Integer) obj2).intValue(),
                                                0,
                                                null,
                                                null));
                        }
                    }
                },
                new BiFunction(
                        this) { // from class:
                                // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda5
                    public final /* synthetic */ FingerprintProvider f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                FingerprintProvider fingerprintProvider = this.f$0;
                                fingerprintProvider.getClass();
                                byte[] bArr = new byte[2048];
                                int semRequest =
                                        fingerprintProvider.semRequest(
                                                ((Integer) obj).intValue(),
                                                ((Integer) obj2).intValue(),
                                                0,
                                                null,
                                                bArr);
                                return TextUtils.emptyIfNull(
                                        semRequest > 0
                                                ? new String(
                                                        Arrays.copyOf(bArr, semRequest),
                                                        StandardCharsets.UTF_8)
                                                : null);
                            default:
                                return Integer.valueOf(
                                        this.f$0.semRequest(
                                                ((Integer) obj).intValue(),
                                                ((Integer) obj2).intValue(),
                                                0,
                                                null,
                                                null));
                        }
                    }
                },
                SemBioAnalyticsManager.get());
    }

    public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback) {
        Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.get(i);
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
            jSONObject.put("service", getTag$1());
            JSONArray jSONArray = new JSONArray();
            Iterator it = UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                int size =
                        FingerprintUtils.getInstance(i)
                                .getBiometricsForUser(this.mContext, identifier)
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
                        "quality",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mQuality
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
                        "qualityCrypto",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mQualityCrypto
                                : 0);
                jSONObject2.put(
                        "acquireCrypto",
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mAcquireCrypto
                                : 0);
                SparseArray sparseArray =
                        instanceForSensorId.mAllUsersInfo.contains(identifier)
                                ? ((PerformanceTracker.Info)
                                                instanceForSensorId.mAllUsersInfo.get(identifier))
                                        .mNoMatchReason
                                : null;
                if (sparseArray != null) {
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        int keyAt = sparseArray.keyAt(i2);
                        jSONObject2.put(String.valueOf(keyAt), sparseArray.get(keyAt));
                    }
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("prints", jSONArray);
        } catch (JSONException e) {
            Slog.e(getTag$1(), "dump formatting failure", e);
        }
        printWriter.println(jSONObject);
        printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.mHALDeathCount);
        printWriter.println("---AuthSessionCoordinator logs begin---");
        printWriter.println(
                ((BiometricContextProvider) this.mBiometricContext).mAuthSessionCoordinator);
        printWriter.println("---AuthSessionCoordinator logs end  ---");
        ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mScheduler.dump(printWriter);
        SemFpProviderEx semFpProviderEx = this.mProviderEx;
        semFpProviderEx.getClass();
        printWriter.println();
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter,
                        (String)
                                semFpProviderEx.mSensorInfos.get(
                                        i, KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG),
                        " SL : ",
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                (String)
                                        semFpProviderEx.mSdkVersions.get(
                                                i, KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG),
                                " sensor info : ",
                                new StringBuilder(" daemon version : ")));
        m.append(semFpProviderEx.mSecurityLevels.get(i, 0));
        printWriter.println(m.toString());
    }

    public final void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.get(i);
            sensor.getClass();
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, sensor.mSensorProperties.sensorId);
            protoOutputStream.write(1159641169922L, 1);
            if (sensor.mSensorProperties.isAnyUdfpsType()) {
                protoOutputStream.write(2259152797704L, 0);
            }
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
                        FingerprintUtils.getInstance(sensor.mSensorProperties.sensorId)
                                .getBiometricsForUser(sensor.mContext, identifier)
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
                        ((HashMap)
                                        ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                                                .mAuthenticatorIds)
                                .getOrDefault(Integer.valueOf(i2), 0L))
                .longValue();
    }

    public int getFirstVendorApiLevel() {
        return SystemProperties.getInt("ro.board.first_api_level", 33);
    }

    public synchronized IFingerprint getHalInstance() {
        if (this.mTestHalEnabled) {
            Flags.useVhalForTesting();
            return new TestHal();
        }
        String str = this.mHalInstanceNameCurrent;
        if (str == null) {
            this.mHalInstanceNameCurrent = this.mHalInstanceName;
        } else if (str.contains("virtual")
                && this.mHalInstanceNameCurrent != this.mHalInstanceName) {
            Slog.i(
                    getTag$1(),
                    "Switching fingerprint from virtual hal to " + this.mHalInstanceName);
            this.mHalInstanceNameCurrent = this.mHalInstanceName;
            this.mDaemon = null;
        }
        if (this.mTpaHalModeEnabled) {
            return this.mTpaTestHal;
        }
        IFingerprint iFingerprint = this.mDaemon;
        if (iFingerprint != null) {
            return iFingerprint;
        }
        Slog.d(getTag$1(), "Daemon was null, reconnecting");
        IFingerprint asInterface =
                IFingerprint.Stub.asInterface(
                        Binder.allowBlocking(
                                ServiceManager.waitForDeclaredService(
                                        IFingerprint.DESCRIPTOR
                                                + "/"
                                                + this.mHalInstanceNameCurrent)));
        this.mDaemon = asInterface;
        if (asInterface == null) {
            Slog.e(getTag$1(), "Unable to get daemon");
            return null;
        }
        try {
            asInterface.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
            Slog.e(getTag$1(), "Unable to linkToDeath", e);
        }
        for (int i = 0; i < this.mFingerprintSensors.mSensors.size(); i++) {
            int keyAt = this.mFingerprintSensors.mSensors.keyAt(i);
            Iterator it = UserManager.get(this.mContext).getAliveUsers().iterator();
            while (it.hasNext()) {
                scheduleLoadAuthenticatorIdsForUser(keyAt, ((UserInfo) it.next()).id);
            }
            scheduleInternalCleanup(
                    keyAt, ActivityManager.getCurrentUser(), this.mInternalCleanupClientCallback);
        }
        if (Build.isDebuggable()) {
            FingerprintUtils fingerprintUtils =
                    FingerprintUtils.getInstance(this.mFingerprintSensors.mSensors.keyAt(0));
            for (UserInfo userInfo : UserManager.get(this.mContext).getAliveUsers()) {
                List biometricsForUser =
                        fingerprintUtils.getBiometricsForUser(this.mContext, userInfo.id);
                Slog.d(
                        getTag$1(),
                        "Expecting enrollments for user "
                                + userInfo.id
                                + ": "
                                + biometricsForUser.stream()
                                        .map(new FingerprintProvider$$ExternalSyntheticLambda3())
                                        .toList());
            }
        }
        this.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda4(4, this));
        return this.mDaemon;
    }

    public final int getLockoutModeForUser(int i, int i2) {
        return ((Sensor) this.mFingerprintSensors.mSensors.get(i)).getLockoutModeForUser(i2);
    }

    public Sensor getSensor(int i) {
        if (this.mFingerprintSensors.mSensors.size() == 0) {
            return null;
        }
        return i == -1
                ? (Sensor) this.mFingerprintSensors.mSensors.valueAt(0)
                : (Sensor) this.mFingerprintSensors.mSensors.get(i);
    }

    public final SensorPropertiesInternal getSensorProperties(int i) {
        if (this.mFingerprintSensors.mSensors.size() == 0) {
            return null;
        }
        if (i == -1) {
            return ((Sensor) this.mFingerprintSensors.mSensors.valueAt(0)).mSensorProperties;
        }
        Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.get(i);
        if (sensor != null) {
            return sensor.mSensorProperties;
        }
        return null;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final List getSensorProperties() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mFingerprintSensors.mSensors.size(); i++) {
            arrayList.add(
                    ((Sensor) this.mFingerprintSensors.mSensors.valueAt(i)).mSensorProperties);
        }
        return arrayList;
    }

    public final String getTag$1() {
        return "FingerprintProvider/" + this.mHalInstanceName;
    }

    public TaskStackListener getTaskStackListener() {
        return this.mTaskStackListener;
    }

    public final int handleRequestCommandWithoutScheduler(
            int i, int i2, int i3, byte[] bArr, byte[] bArr2) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            return new SemFpBaseRequestClient(
                            this.mContext,
                            this.mBiometricContext,
                            ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mLazySession,
                            null,
                            null,
                            i,
                            0,
                            "FingerprintRequestClient",
                            false,
                            i2,
                            i3,
                            bArr,
                            bArr2)
                    .startWithoutScheduler();
        }
        return -1;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public final boolean hasEnrollments(int i, int i2) {
        return !FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2).isEmpty();
    }

    public final boolean isHardwareDetected(int i) {
        return ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                .isHardwareDetected(this.mHalInstanceName);
    }

    public final void onPointerDown(long j, int i, PointerContext pointerContext) {
        if (!SemBiometricFeature.FP_FEATURE_LOCAL_HBM) {
            semRequest(i, 22, 2, null, null);
        }
        BiometricScheduler biometricScheduler =
                ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mScheduler;
        FingerprintProvider$$ExternalSyntheticLambda15
                fingerprintProvider$$ExternalSyntheticLambda15 =
                        new FingerprintProvider$$ExternalSyntheticLambda15(this, pointerContext, 1);
        biometricScheduler.getClass();
        biometricScheduler.mHandler.post(
                new BiometricScheduler$$ExternalSyntheticLambda1(
                        biometricScheduler, j, fingerprintProvider$$ExternalSyntheticLambda15));
    }

    public final void onPointerUp(long j, int i, PointerContext pointerContext) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            if (!SemBiometricFeature.FP_FEATURE_LOCAL_HBM) {
                semRequest(i, 22, 1, null, null);
            }
            Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.get(i);
            if (sensor.mSensorProperties.sensorType == 3) {
                sensor.generateEvent();
            }
            BiometricScheduler biometricScheduler =
                    ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mScheduler;
            FingerprintProvider$$ExternalSyntheticLambda15
                    fingerprintProvider$$ExternalSyntheticLambda15 =
                            new FingerprintProvider$$ExternalSyntheticLambda15(
                                    this, pointerContext, 0);
            biometricScheduler.getClass();
            biometricScheduler.mHandler.post(
                    new BiometricScheduler$$ExternalSyntheticLambda1(
                            biometricScheduler, j, fingerprintProvider$$ExternalSyntheticLambda15));
        }
    }

    public final void onPowerPressed() {
        IBinder.DeathRecipient currentClient;
        for (int i = 0;
                i < this.mFingerprintSensors.mSensors.size()
                        && (currentClient =
                                        ((Sensor) this.mFingerprintSensors.mSensors.valueAt(i))
                                                .mScheduler.getCurrentClient())
                                != null;
                i++) {
            if (currentClient instanceof PowerPressHandler) {}
        }
    }

    public final void onUdfpsUiEvent(final int i, long j, int i2) {
        BiometricScheduler biometricScheduler =
                ((Sensor) this.mFingerprintSensors.mSensors.get(i2)).mScheduler;
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda26
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FingerprintProvider fingerprintProvider = FingerprintProvider.this;
                        int i3 = i;
                        fingerprintProvider.getClass();
                        if (obj instanceof Udfps) {
                            ((Udfps) obj).onUdfpsUiEvent(i3);
                            return;
                        }
                        Slog.e(
                                fingerprintProvider.getTag$1(),
                                "onUdfpsUiEvent received during client: " + obj);
                    }
                };
        biometricScheduler.getClass();
        biometricScheduler.mHandler.post(
                new BiometricScheduler$$ExternalSyntheticLambda1(biometricScheduler, j, consumer));
    }

    public final void onUserRemoved(int i) {
        for (int i2 = 0; i2 < this.mFingerprintSensors.mSensors.size(); i2++) {
            scheduleRemoveAll(
                    new Binder(),
                    this.mFingerprintSensors.mSensors.keyAt(i2),
                    i,
                    new FingerprintServiceReceiver(),
                    getTag$1());
        }
    }

    public final void scheduleForSensor$1(int i, BaseClientMonitor baseClientMonitor) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                    .mScheduler.scheduleClientMonitor(baseClientMonitor, null);
            return;
        }
        throw new IllegalStateException(
                "Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
    }

    public final void scheduleForSensor$1(
            int i,
            BaseClientMonitor baseClientMonitor,
            ClientMonitorCallback clientMonitorCallback) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                    .mScheduler.scheduleClientMonitor(baseClientMonitor, clientMonitorCallback);
            return;
        }
        throw new IllegalStateException(
                "Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
    }

    public final void scheduleInternalCleanup(
            final int i, final int i2, final ClientMonitorCallback clientMonitorCallback) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(
                i, i2, "scheduleInternalCleanup: ", ", ", "FingerprintProvider");
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda2
                        public final /* synthetic */ boolean f$3 = true;

                        @Override // java.lang.Runnable
                        public final void run() {
                            final FingerprintProvider fingerprintProvider =
                                    FingerprintProvider.this;
                            int i3 = i;
                            int i4 = i2;
                            boolean z = this.f$3;
                            ClientMonitorCallback clientMonitorCallback2 = clientMonitorCallback;
                            fingerprintProvider.getClass();
                            InternalCleanupClient internalCleanupClient =
                                    new InternalCleanupClient(
                                            fingerprintProvider.mContext,
                                            ((Sensor)
                                                            fingerprintProvider.mFingerprintSensors
                                                                    .mSensors.get(i3))
                                                    .mLazySession,
                                            i4,
                                            fingerprintProvider.mContext.getOpPackageName(),
                                            i3,
                                            fingerprintProvider.createLogger$1(
                                                    3,
                                                    0,
                                                    fingerprintProvider
                                                            .mAuthenticationStatsCollector),
                                            fingerprintProvider.mBiometricContext,
                                            FingerprintUtils.getInstance(i3),
                                            ((Sensor)
                                                            fingerprintProvider.mFingerprintSensors
                                                                    .mSensors.get(i3))
                                                    .mAuthenticatorIds) { // from class:
                                                                          // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.7
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
                                            return new FingerprintInternalEnumerateClient(
                                                    context,
                                                    supplier,
                                                    iBinder,
                                                    i5,
                                                    str,
                                                    list,
                                                    biometricUtils,
                                                    i6,
                                                    new BiometricLogger(
                                                            context,
                                                            biometricLogger.mStatsModality,
                                                            3,
                                                            biometricLogger.mStatsClient,
                                                            null),
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
                                            return new FingerprintRemovalClient(
                                                    context,
                                                    supplier,
                                                    iBinder,
                                                    null,
                                                    new int[] {i5},
                                                    i6,
                                                    str,
                                                    biometricUtils,
                                                    i7,
                                                    new BiometricLogger(
                                                            context,
                                                            biometricLogger.mStatsModality,
                                                            4,
                                                            biometricLogger.mStatsClient,
                                                            null),
                                                    biometricContext,
                                                    map);
                                        }

                                        @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                                        public final boolean interruptsPrecedingClients() {
                                            if (FingerprintProvider.this.mTestHalEnabled) {
                                                return true;
                                            }
                                            return this instanceof FaceDetectClient;
                                        }

                                        @Override // com.android.server.biometrics.sensors.InternalCleanupClient
                                        public final void onAddUnknownTemplate(
                                                BiometricAuthenticator.Identifier identifier) {
                                            FingerprintUtils.getInstance(this.mSensorId)
                                                    .addBiometricForUser(
                                                            this.mContext,
                                                            this.mTargetUserId,
                                                            (Fingerprint) identifier);
                                        }
                                    };
                            if (z) {
                                internalCleanupClient.mFavorHalEnrollments = true;
                            }
                            fingerprintProvider.scheduleForSensor$1(
                                    i3,
                                    internalCleanupClient,
                                    new ClientMonitorCompositeCallback(
                                            clientMonitorCallback2,
                                            fingerprintProvider.mBiometricStateCallback));
                        }
                    });
        }
    }

    public final void scheduleLoadAuthenticatorIdsForUser(int i, int i2) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(
                i, i2, "scheduleLoadAuthenticatorIdsForUser: ", ", ", "FingerprintProvider");
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            this.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda0(this, i, i2, 2));
        }
    }

    public final void scheduleRemoveAll(
            IBinder iBinder,
            int i,
            int i2,
            IFingerprintServiceReceiver iFingerprintServiceReceiver,
            String str) {
        List biometricsForUser =
                FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
        int[] iArr = new int[biometricsForUser.size()];
        for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
            iArr[i3] = ((Fingerprint) biometricsForUser.get(i3)).getBiometricId();
        }
        this.mHandler.post(
                new FingerprintProvider$$ExternalSyntheticLambda13(
                        this, i, iBinder, iFingerprintServiceReceiver, iArr, i2, str));
    }

    public final void scheduleWatchdog(int i) {
        Slog.d(getTag$1(), "Starting watchdog for fingerprint");
        BiometricScheduler biometricScheduler =
                ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mScheduler;
        if (biometricScheduler == null) {
            return;
        }
        biometricScheduler.startWatchdog();
    }

    public final void semAddAuthenticationListener(
            SemFpAuthenticationListener semFpAuthenticationListener) {
        SemFpCallbackDispatcher semFpCallbackDispatcher = this.mCallbackDispatcher;
        semFpCallbackDispatcher.getClass();
        semFpCallbackDispatcher.mHandler.post(
                new SemFpCallbackDispatcher$$ExternalSyntheticLambda0(
                        semFpCallbackDispatcher, semFpAuthenticationListener, 3));
    }

    public final BaseClientMonitor semGetCurrentClient() {
        if (this.mFingerprintSensors.mSensors.size() > 0) {
            return ((Sensor) this.mFingerprintSensors.mSensors.valueAt(0))
                    .mScheduler.getCurrentClient();
        }
        return null;
    }

    public final String semGetDaemonSdkVersion() {
        return this.mProviderEx.getDaemonSdkVersion(this.mFingerprintSensors.mSensors.keyAt(0));
    }

    public final int semGetRemainingLockoutTime(int i) {
        try {
            return ((Sensor) this.mFingerprintSensors.mSensors.valueAt(0))
                    .getRemainingLockoutTime(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            Slog.w(this.getTag$1(), "semGetRemainingLockoutTime: " + e.getMessage());
            return 0;
        }
    }

    public final int semGetSecurityLevel$1() {
        SemFpProviderEx semFpProviderEx = this.mProviderEx;
        return semFpProviderEx.mSecurityLevels.get(this.mFingerprintSensors.mSensors.keyAt(0), 1);
    }

    public final String semGetSensorInfo(int i) {
        String str;
        if (!this.mFingerprintSensors.mSensors.contains(i)) {
            if (Utils.DEBUG) {
                throw new IllegalStateException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unable to use sensor: "));
            }
            return "";
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        byte[] bArr = new byte[2048];
        SemFpBaseRequestClient semFpBaseRequestClient =
                new SemFpBaseRequestClient(
                        this.mContext,
                        this.mBiometricContext,
                        ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mLazySession,
                        null,
                        null,
                        i,
                        ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                                .getCurrentSessionUserId(),
                        "FingerprintRequestClient",
                        true,
                        5,
                        0,
                        null,
                        bArr);
        this.mHandler.post(
                new FingerprintProvider$$ExternalSyntheticLambda20(
                        this, i, semFpBaseRequestClient, countDownLatch, 0));
        try {
            countDownLatch.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Slog.w(getTag$1(), "Latch interrupted", e);
        }
        int i2 = semFpBaseRequestClient.mRequestResult;
        if (i2 > 0) {
            str = new String(Arrays.copyOf(bArr, i2), StandardCharsets.UTF_8);
            SemFpProviderEx semFpProviderEx = this.mProviderEx;
            semFpProviderEx.mSensorInfos.put(i, str.replace("\n", ", "));
        } else {
            str = "";
        }
        Slog.d("FingerprintProvider", "sensorInfo = ".concat(str));
        return str;
    }

    public final void semNotifyTspBlockStateToClient(boolean z) {
        for (int i = 0; i < this.mFingerprintSensors.mSensors.size(); i++) {
            Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.valueAt(i);
            if (sensor.mSensorProperties.isAnyUdfpsType()) {
                IBinder.DeathRecipient currentClient = sensor.mScheduler.getCurrentClient();
                if (currentClient instanceof SemFpTspBlockStatusHandler) {
                    SemFpTspBlockStatusHandler semFpTspBlockStatusHandler =
                            (SemFpTspBlockStatusHandler) currentClient;
                    if (z) {
                        semFpTspBlockStatusHandler.onTspBlocked();
                    } else {
                        semFpTspBlockStatusHandler.onTspUnBlocked();
                    }
                }
            }
        }
    }

    public final void semOpenTzSession() {
        if (this.mFingerprintSensors.mSensors.size() != 0) {
            this.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda4(2, this));
        } else if (Utils.DEBUG) {
            throw new IllegalStateException("Unable to use sensor");
        }
    }

    public final int semProcessFidoCommand(int i, byte[] bArr, byte[] bArr2) {
        if (!this.mFingerprintSensors.mSensors.contains(i)) {
            if (Utils.DEBUG) {
                throw new IllegalStateException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unable to use sensor: "));
            }
            return -1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SemFpBaseRequestClient semFpBaseRequestClient =
                new SemFpBaseRequestClient(
                        this.mContext,
                        this.mBiometricContext,
                        ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mLazySession,
                        null,
                        null,
                        i,
                        ((Sensor) this.mFingerprintSensors.mSensors.get(i))
                                .getCurrentSessionUserId(),
                        "FingerprintRequestClient",
                        true,
                        9,
                        0,
                        bArr,
                        bArr2);
        this.mHandler.post(
                new FingerprintProvider$$ExternalSyntheticLambda20(
                        this, i, semFpBaseRequestClient, countDownLatch, 1));
        try {
            countDownLatch.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Slog.w(getTag$1(), "Latch interrupted", e);
        }
        return semFpBaseRequestClient.mRequestResult;
    }

    public final int semRequest(int i, int i2, int i3, byte[] bArr, byte[] bArr2) {
        if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
            return 0;
        }
        return handleRequestCommandWithoutScheduler(i, i2, i3, bArr, bArr2);
    }

    public final void semScheduleSensorTest(
            int i, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter) {
        if (this.mFingerprintSensors.mSensors.contains(i)) {
            this.mHandler.post(
                    new FingerprintProvider$$ExternalSyntheticLambda20(
                            this, i, iBinder, clientMonitorCallbackConverter, 2));
        }
    }

    public final void semSetTpaHalEnabled(boolean z) {
        if (this.mTpaHalModeEnabled == z) {
            return;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "semSetTpaHalEnabled: start, ", "FingerprintProvider", z);
        this.mTpaHalModeEnabled = z;
        for (int i = 0; i < this.mFingerprintSensors.mSensors.size(); i++) {
            Sensor sensor = (Sensor) this.mFingerprintSensors.mSensors.valueAt(i);
            sensor.setTestHalEnabled(true);
            sensor.setTestHalEnabled(false);
            sensor.setTpaHalEnabled();
        }
        int intDb = Utils.getIntDb(this.mContext, 0, -2, "biometric_tpa_mode", true);
        int i2 = z ? intDb | 2 : intDb & (-3);
        Utils.putIntDb(i2, this.mContext, "biometric_tpa_mode", true);
        if (!this.mIsForHidl) {
            if (!this.mTpaHalModeEnabled) {
                SemTpaTestHal semTpaTestHal = this.mTpaTestHal;
                if (semTpaTestHal != null) {
                    ServiceThread serviceThread = semTpaTestHal.mThread;
                    if (serviceThread != null) {
                        serviceThread.quitSafely();
                        semTpaTestHal.mThread = null;
                    }
                    this.mTpaTestHal = null;
                }
            } else if (this.mTpaTestHal == null) {
                SemTpaTestHal semTpaTestHal2 = new SemTpaTestHal(this.mContext);
                this.mTpaTestHal = semTpaTestHal2;
                semTpaTestHal2.mTestHalHelper.initActions();
                SemTestSehFingerprint semTestSehFingerprint = semTpaTestHal2.mSehFingerprint;
                semTestSehFingerprint.mRequestActionTable.clear();
                semTestSehFingerprint.mRequestActionTable.put(6, 100040);
                ServiceThread serviceThread2 = semTpaTestHal2.mThread;
                if (serviceThread2 != null) {
                    serviceThread2.quitSafely();
                    semTpaTestHal2.mThread = null;
                }
                ServiceThread serviceThread3 =
                        new ServiceThread(-2, "fingerprint.aidl.SemTpaTestHal", true);
                semTpaTestHal2.mThread = serviceThread3;
                serviceThread3.start();
            }
        }
        for (int i3 = 0; i3 < this.mFingerprintSensors.mSensors.size(); i3++) {
            scheduleInternalCleanup(
                    this.mFingerprintSensors.mSensors.keyAt(i3),
                    ActivityManager.getCurrentUser(),
                    null);
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i2, "semSetTpaHalEnabled: done, ", "FingerprintProvider");
    }

    public final void semUpdateTpaAction() {
        SemTpaTestHal semTpaTestHal;
        ServiceThread serviceThread;
        if (!this.mTpaHalModeEnabled
                || (semTpaTestHal = this.mTpaTestHal) == null
                || (serviceThread = semTpaTestHal.mThread) == null) {
            return;
        }
        serviceThread
                .getThreadHandler()
                .post(new SemTpaTestHal$$ExternalSyntheticLambda0(semTpaTestHal));
    }

    public final void setIgnoreDisplayTouches(long j, int i, final boolean z) {
        BiometricScheduler biometricScheduler =
                ((Sensor) this.mFingerprintSensors.mSensors.get(i)).mScheduler;
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda32
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FingerprintProvider fingerprintProvider = FingerprintProvider.this;
                        boolean z2 = z;
                        fingerprintProvider.getClass();
                        if (obj instanceof Udfps) {
                            ((Udfps) obj).setIgnoreDisplayTouches(z2);
                            return;
                        }
                        Slog.e(
                                fingerprintProvider.getTag$1(),
                                "setIgnoreDisplayTouches received during client: " + obj);
                    }
                };
        biometricScheduler.getClass();
        biometricScheduler.mHandler.post(
                new BiometricScheduler$$ExternalSyntheticLambda1(biometricScheduler, j, consumer));
    }

    public final void simulateVhalFingerDown(int i, int i2) {
        Slog.d(getTag$1(), "Simulate virtual HAL finger down event");
        AidlSession sessionForUser =
                ((Sensor) this.mFingerprintSensors.mSensors.get(i2)).getSessionForUser(i);
        if (sessionForUser == null) {
            Slog.e(getTag$1(), "no existing hal session found - aborting");
            return;
        }
        PointerContext pointerContext = new PointerContext();
        try {
            sessionForUser.mSession.onPointerDownWithContext(pointerContext);
            sessionForUser.mSession.onUiReady();
            sessionForUser.mSession.onPointerUpWithContext(pointerContext);
        } catch (RemoteException e) {
            Slog.e(getTag$1(), "failed hal operation ", e);
        }
    }
}
