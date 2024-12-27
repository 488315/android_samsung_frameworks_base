package com.android.server.location.nsflp;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.INSLocationCallback;
import android.location.INSLocationManager;
import android.location.LocationConstants;
import android.location.LocationManagerInternal;
import android.location.LocationRequest;
import android.location.util.identity.CallerIdentity;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ISemHqmManager;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;

import com.android.internal.app.IBatteryStats;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleInternal;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationPermissions;
import com.android.server.location.injector.DeviceIdleHelper$DeviceIdleListener;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener;
import com.android.server.location.injector.SettingsHelper$UserSettingChangedListener;
import com.android.server.location.injector.SystemDeviceIdleHelper;
import com.android.server.location.injector.SystemDeviceStationaryHelper;
import com.android.server.location.injector.SystemLocationPowerSaveModeHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.provider.LocationProviderManager;

import com.samsung.android.hardware.context.SemContextDeviceActivityDetector;
import com.samsung.android.hardware.context.SemContextDeviceActivityDetectorAttribute;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public final class NSLocationMonitor extends INSLocationCallback.Stub {
    public static final Object MONITOR_SERVICE_LOCK = new Object();
    public final Context mContext;
    public int mDeviceActivityDuration;
    public int mDeviceActivityMode;
    public boolean mDeviceActivityRegistered;
    public final SystemDeviceIdleHelper mDeviceIdleHelper;
    public final SystemDeviceStationaryHelper mDeviceStationaryHelper;
    public boolean mEnableFeatureDeviceActivity;
    public final Executor mExecutor;
    public final Handler mHandler;
    public ISemHqmManager mHqmManager;
    public final Injector mInjector;
    public boolean mIsStationary;
    public LocationManagerInternal mLocationManagerInternal;
    public final SystemLocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
    public final Looper mLooper;
    public final NSConnectionHelper mNSConnectionHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public NSUtLogger mNsUtLogger;
    public PackageManager mPackageManager;
    public PhoneStateListener[] mPhoneStateListener;
    public boolean mRequestToUpdate;
    public SemContextManager mSemContextManager;
    public int mSimCount;
    public int[] mSimSubId;
    public final long mSystemRunningTime;
    public IBatteryStats mBatteryStats = null;
    public final ArrayList mConnectedHistory = new ArrayList();
    public final ArrayList mDisconnectedHistory = new ArrayList();
    public INSLocationManager mMonitorService = null;
    public boolean mRegisteredNotificationListener = false;
    public final Map mForegroundNotificationList = new HashMap();
    public final Object mNetworkLock = new Object();
    public ALGORITHM_TYPE mSupportAlgorithm = ALGORITHM_TYPE.NOT_SUPPORT;
    public HQM_SUPPORT_STATE mSupportHqm = HQM_SUPPORT_STATE.UNKNOWN;
    public final NSLocationMonitor$$ExternalSyntheticLambda0 mLocationPowerSaveModeChangedListener =
            new LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener() { // from class:
                                                                                     // com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda0
                @Override // com.android.server.location.injector.LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener
                public final void onLocationPowerSaveModeChanged(int i) {
                    NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                    nSLocationMonitor.getClass();
                    boolean z = true;
                    if (i != 1 && i != 2 && i != 4) {
                        z = false;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("powerSaveMode", z);
                    nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                            LocationConstants.STATE_TYPE.LOCATION_POWER_SAVE_CHANGED, bundle);
                }
            };
    public final NSLocationMonitor$$ExternalSyntheticLambda1 mStationaryListener =
            new DeviceIdleInternal
                    .StationaryListener() { // from class:
                                            // com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda1
                public final void onDeviceStationaryChanged(boolean z) {
                    NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                    nSLocationMonitor.getClass();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("stationary", z);
                    nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                            LocationConstants.STATE_TYPE.DEVICE_STATIONARY_CHANGED, bundle);
                }
            };
    public final NSLocationMonitor$$ExternalSyntheticLambda2 mDeviceIdleListener =
            new DeviceIdleHelper$DeviceIdleListener() { // from class:
                                                        // com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda2
                @Override // com.android.server.location.injector.DeviceIdleHelper$DeviceIdleListener
                public final void onDeviceIdleChanged(boolean z) {
                    NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                    nSLocationMonitor.getClass();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("deviceIdle", z);
                    nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                            LocationConstants.STATE_TYPE.DEVICE_IDLE_CHANGED, bundle);
                }
            };
    public int mCrashCount = 0;
    public long mCrashTime = 0;
    public final AnonymousClass2 mMonitorServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.location.nsflp.NSLocationMonitor.2
                @Override // android.content.ServiceConnection
                public final void onNullBinding(ComponentName componentName) {
                    Log.d("NSLocationMonitor", "onNullBinding is called, " + componentName);
                    NSLocationMonitor.this.mContext.unbindService(this);
                }

                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Handler handler;
                    Log.i("NSLocationMonitor", "MonitorService is connected, " + componentName);
                    synchronized (NSLocationMonitor.MONITOR_SERVICE_LOCK) {
                        NSLocationMonitor.this.mMonitorService =
                                INSLocationManager.Stub.asInterface(iBinder);
                        NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                        INSLocationManager iNSLocationManager = nSLocationMonitor.mMonitorService;
                        if (iNSLocationManager == null) {
                            Log.e(
                                    "NSLocationMonitor",
                                    "Failed to stub interface to MonitorService");
                            return;
                        }
                        try {
                            iNSLocationManager.setCallback(nSLocationMonitor);
                        } catch (Exception unused) {
                            Log.w("NSLocationMonitor", "Failed to set callback");
                        }
                        NSLocationMonitor nSLocationMonitor2 = NSLocationMonitor.this;
                        NSConnectionHelper nSConnectionHelper =
                                nSLocationMonitor2.mNSConnectionHelper;
                        INSLocationManager iNSLocationManager2 = nSLocationMonitor2.mMonitorService;
                        if (nSConnectionHelper.mHandler == null) {
                            NSLocationThread nSLocationThread = NSLocationThread.sInstance;
                            synchronized (NSLocationThread.class) {
                                NSLocationThread.ensureThreadLocked();
                                handler = NSLocationThread.sHandler;
                            }
                            nSConnectionHelper.mHandler = handler;
                        }
                        nSConnectionHelper.mMonitorService = iNSLocationManager2;
                        NSLocationMonitor nSLocationMonitor3 = NSLocationMonitor.this;
                        nSLocationMonitor3.mConnectedHistory.add(
                                Long.valueOf(SystemClock.elapsedRealtime()));
                        Bundle bundle = new Bundle();
                        bundle.putLong("systemRunning", nSLocationMonitor3.mSystemRunningTime);
                        bundle.putSerializable(
                                "connectedHistory", nSLocationMonitor3.mConnectedHistory);
                        bundle.putSerializable(
                                "disconnectedHistory", nSLocationMonitor3.mDisconnectedHistory);
                        String str =
                                ((LocationManagerService.SystemInjector)
                                                nSLocationMonitor3.mInjector)
                                        .mNSLocationProviderHelper
                                        .mSuplAddress;
                        if (str != null) {
                            bundle.putString("supl_hostname", str);
                        }
                        nSLocationMonitor3.mNSConnectionHelper.onStateUpdated(
                                LocationConstants.STATE_TYPE.MONITOR_SERVICE_CONNECTED, bundle);
                        NSConnectionHelper nSConnectionHelper2 =
                                NSLocationMonitor.this.mNSConnectionHelper;
                        String str2 = nSConnectionHelper2.mBdmsgFormatMessage;
                        if (str2 != null) {
                            nSConnectionHelper2.onGnssEventUpdated(str2);
                        }
                        synchronized (NSLocationMonitor.this.mNotificationListener) {
                            NSLocationMonitor nSLocationMonitor4 = NSLocationMonitor.this;
                            if (!nSLocationMonitor4.mRegisteredNotificationListener) {
                                try {
                                    nSLocationMonitor4.mNotificationListener
                                            .registerAsSystemService(
                                                    nSLocationMonitor4.mContext,
                                                    new ComponentName(
                                                            NSLocationMonitor.this.mContext
                                                                    .getPackageName(),
                                                            NSLocationMonitor.this
                                                                    .mContext
                                                                    .getClass()
                                                                    .getCanonicalName()),
                                                    ActivityManager.getCurrentUser());
                                    NSLocationMonitor.this.mRegisteredNotificationListener = true;
                                } catch (RemoteException e) {
                                    Log.e(
                                            "NSLocationMonitor",
                                            "Failed to register notification listener, "
                                                    + e.toString());
                                }
                            }
                        }
                        NSLocationMonitor nSLocationMonitor5 = NSLocationMonitor.this;
                        synchronized (nSLocationMonitor5.mNetworkLock) {
                            try {
                                TelephonyManager telephonyManager =
                                        (TelephonyManager)
                                                nSLocationMonitor5.mContext.getSystemService(
                                                        "phone");
                                boolean z = false;
                                if (telephonyManager != null) {
                                    int phoneCount = telephonyManager.getPhoneCount();
                                    nSLocationMonitor5.mSimCount = phoneCount;
                                    if (phoneCount > 0) {
                                        Log.v(
                                                "NSLocationMonitor",
                                                "Initialize NetworkStateListener, count="
                                                        + nSLocationMonitor5.mSimCount);
                                        int i = nSLocationMonitor5.mSimCount;
                                        nSLocationMonitor5.mSimSubId = new int[i];
                                        nSLocationMonitor5.mPhoneStateListener =
                                                new PhoneStateListener[i];
                                        boolean z2 = false;
                                        for (int i2 = 0; i2 < nSLocationMonitor5.mSimCount; i2++) {
                                            int[] subId = SubscriptionManager.getSubId(i2);
                                            if (subId == null) {
                                                Log.e(
                                                        "NSLocationMonitor",
                                                        "subId is null from simSlot[" + i2 + "]");
                                            } else if (subId.length == 0) {
                                                Log.e(
                                                        "NSLocationMonitor",
                                                        "subId is empty from simSlot[" + i2 + "]");
                                            } else {
                                                Log.i(
                                                        "NSLocationMonitor",
                                                        "subIdTemp, " + Arrays.toString(subId));
                                                int i3 = subId[0];
                                                nSLocationMonitor5.mSimSubId[i2] = i3;
                                                nSLocationMonitor5.mPhoneStateListener[i2] =
                                                        nSLocationMonitor5
                                                        .new NetworkStateListener(i3);
                                                telephonyManager.listen(
                                                        nSLocationMonitor5.mPhoneStateListener[i2],
                                                        1);
                                                Log.v(
                                                        "NSLocationMonitor",
                                                        "registerPhoneStateListener SimSlot="
                                                                + i2
                                                                + ", subId="
                                                                + i3);
                                                z2 = true;
                                            }
                                        }
                                        z = z2;
                                    }
                                }
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("isInitialized", z);
                                nSLocationMonitor5.mNSConnectionHelper.onStateUpdated(
                                        LocationConstants.STATE_TYPE.SIM_STATE_CHANGED, bundle2);
                                SubscriptionManager subscriptionManager =
                                        (SubscriptionManager)
                                                nSLocationMonitor5.mContext.getSystemService(
                                                        "telephony_subscription_service");
                                if (subscriptionManager != null) {
                                    Log.v(
                                            "NSLocationMonitor",
                                            "Register SubscriptionChangedListener");
                                    subscriptionManager.addOnSubscriptionsChangedListener(
                                            nSLocationMonitor5.mExecutor,
                                            nSLocationMonitor5.mSubscriptionListener);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    NSLocationMonitor nSLocationMonitor;
                    AnonymousClass3 anonymousClass3;
                    Log.d(
                            "NSLocationMonitor",
                            "MonitorService has unexpectedly disconnected, " + componentName);
                    synchronized (NSLocationMonitor.MONITOR_SERVICE_LOCK) {
                        nSLocationMonitor = NSLocationMonitor.this;
                        nSLocationMonitor.mMonitorService = null;
                        NSConnectionHelper nSConnectionHelper =
                                nSLocationMonitor.mNSConnectionHelper;
                        nSConnectionHelper.mMonitorService = null;
                        nSConnectionHelper.mHandler = null;
                    }
                    nSLocationMonitor.mDisconnectedHistory.add(
                            Long.valueOf(SystemClock.elapsedRealtime()));
                    NSLocationMonitor.this.noteGpsOp(3, 0);
                    synchronized (NSLocationMonitor.this.mNotificationListener) {
                        NSLocationMonitor nSLocationMonitor2 = NSLocationMonitor.this;
                        if (nSLocationMonitor2.mRegisteredNotificationListener) {
                            try {
                                nSLocationMonitor2.mNotificationListener
                                        .unregisterAsSystemService();
                                NSLocationMonitor.this.mRegisteredNotificationListener = false;
                            } catch (RemoteException e) {
                                Log.e(
                                        "NSLocationMonitor",
                                        "Failed to unregister notification listener, "
                                                + e.toString());
                            }
                        }
                    }
                    NSLocationMonitor nSLocationMonitor3 = NSLocationMonitor.this;
                    synchronized (nSLocationMonitor3.mNetworkLock) {
                        try {
                            TelephonyManager telephonyManager =
                                    (TelephonyManager)
                                            nSLocationMonitor3.mContext.getSystemService("phone");
                            if (telephonyManager != null
                                    && nSLocationMonitor3.mPhoneStateListener != null) {
                                for (int i = 0; i < nSLocationMonitor3.mSimCount; i++) {
                                    PhoneStateListener phoneStateListener =
                                            nSLocationMonitor3.mPhoneStateListener[i];
                                    if (phoneStateListener != null) {
                                        telephonyManager.listen(phoneStateListener, 0);
                                    }
                                }
                                nSLocationMonitor3.mSimCount = 0;
                                nSLocationMonitor3.mPhoneStateListener = null;
                            }
                            SubscriptionManager subscriptionManager =
                                    (SubscriptionManager)
                                            nSLocationMonitor3.mContext.getSystemService(
                                                    "telephony_subscription_service");
                            if (subscriptionManager != null
                                    && (anonymousClass3 = nSLocationMonitor3.mSubscriptionListener)
                                            != null) {
                                subscriptionManager.removeOnSubscriptionsChangedListener(
                                        anonymousClass3);
                                nSLocationMonitor3.mSimSubId = null;
                                Log.w(
                                        "NSLocationMonitor",
                                        "Unregister SubscriptionChangedListener");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    NSLocationMonitor.this.disableDeviceActivity(
                            DEVICE_ACTIVITY_ERROR_CODE.SEVICE_DISCONNECT);
                    NSLocationMonitor.this.mNSLocationProviderHelper
                            .notifyMotionPowerSaveModeChanged(false);
                    if (NSLocationMonitor.this.mCrashCount >= 3) {
                        Log.e(
                                "NSLocationMonitor",
                                ""
                                        + NSLocationMonitor.this.mCrashCount
                                        + " times disconnected, so stop "
                                        + componentName);
                        NSLocationMonitor.this.mContext.unbindService(this);
                    }
                }
            };
    public final AnonymousClass3 mSubscriptionListener =
            new SubscriptionManager.OnSubscriptionsChangedListener() { // from class:
                // com.android.server.location.nsflp.NSLocationMonitor.3
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public final void onSubscriptionsChanged() {
                    Log.i("NSLocationMonitor", "onSubscriptionsChanged");
                    synchronized (NSLocationMonitor.this.mNetworkLock) {
                        boolean z = false;
                        for (int i = 0; i < NSLocationMonitor.this.mSimCount; i++) {
                            try {
                                int[] subId = SubscriptionManager.getSubId(i);
                                if (subId != null && subId.length != 0) {
                                    int i2 = subId[0];
                                    NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                                    int[] iArr = nSLocationMonitor.mSimSubId;
                                    if (iArr != null) {
                                        if (iArr.length == nSLocationMonitor.mSimCount
                                                && i2 == iArr[i]) {
                                            Log.w(
                                                    "NSLocationMonitor",
                                                    "SubId["
                                                            + i2
                                                            + "] for sim["
                                                            + i
                                                            + "] was not changed");
                                        } else {
                                            Log.w(
                                                    "NSLocationMonitor",
                                                    "SubId was changed for sim[" + i + "]=" + i2);
                                            NSLocationMonitor.this.mSimSubId[i] = i2;
                                        }
                                    }
                                    TelephonyManager telephonyManager =
                                            (TelephonyManager)
                                                    NSLocationMonitor.this.mContext
                                                            .getSystemService("phone");
                                    if (telephonyManager != null) {
                                        if (NSLocationMonitor.this.mPhoneStateListener[i] != null) {
                                            Log.i(
                                                    "NSLocationMonitor",
                                                    "unregister previous PhoneStateListener"
                                                        + " SimSlot="
                                                            + i);
                                            telephonyManager.listen(
                                                    NSLocationMonitor.this.mPhoneStateListener[i],
                                                    0);
                                        }
                                        NSLocationMonitor.this.mPhoneStateListener[i] =
                                                NSLocationMonitor.this.new NetworkStateListener(i2);
                                        telephonyManager.listen(
                                                NSLocationMonitor.this.mPhoneStateListener[i], 1);
                                        z = true;
                                    }
                                }
                                Log.w(
                                        "NSLocationMonitor",
                                        "Failed to load subId for sim[" + i + "]");
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isInitialized", true);
                            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                                    LocationConstants.STATE_TYPE.SIM_STATE_CHANGED, bundle);
                        }
                    }
                }
            };
    public final AnonymousClass4 mNotificationListener = new AnonymousClass4();
    public final AnonymousClass5 mSemContextListener =
            new SemContextListener() { // from class:
                                       // com.android.server.location.nsflp.NSLocationMonitor.5
                public final void onSemContextChanged(SemContextEvent semContextEvent) {
                    if (semContextEvent.semContext.getType() == 54) {
                        SemContextDeviceActivityDetector deviceActivityDetectorContext =
                                semContextEvent.getDeviceActivityDetectorContext();
                        int activity = deviceActivityDetectorContext.getActivity();
                        int result = deviceActivityDetectorContext.getResult();
                        ALGORITHM_TYPE algorithm_type = NSLocationMonitor.this.mSupportAlgorithm;
                        ALGORITHM_TYPE algorithm_type2 = ALGORITHM_TYPE.NEW;
                        int duration =
                                algorithm_type == algorithm_type2
                                        ? deviceActivityDetectorContext.getDuration()
                                        : -1;
                        GestureWakeup$$ExternalSyntheticOutline0.m(
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        activity,
                                        result,
                                        "onSemContextChanged, activity : ",
                                        " result : ",
                                        " movement : "),
                                duration,
                                "NSLocationMonitor");
                        NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                        ALGORITHM_TYPE algorithm_type3 = nSLocationMonitor.mSupportAlgorithm;
                        ALGORITHM_TYPE algorithm_type4 = ALGORITHM_TYPE.NOT_SUPPORT;
                        ALGORITHM_TYPE algorithm_type5 = ALGORITHM_TYPE.OLD;
                        if (algorithm_type3 == algorithm_type4) {
                            nSLocationMonitor.getClass();
                            if (result == 4) {
                                Log.i(
                                        "NSLocationMonitor",
                                        "Support device_activity_detector old algorithm");
                                nSLocationMonitor.mSupportAlgorithm = algorithm_type5;
                            } else {
                                if (result != 5) {
                                    Log.e(
                                            "NSLocationMonitor",
                                            "Not support device_activity_detector algorithm");
                                    nSLocationMonitor.mSupportAlgorithm = algorithm_type4;
                                    nSLocationMonitor.disableDeviceActivity(
                                            DEVICE_ACTIVITY_ERROR_CODE.NOT_SUPPORT);
                                    return;
                                }
                                Log.i(
                                        "NSLocationMonitor",
                                        "Support device_activity_detector new algorithm");
                                nSLocationMonitor.mSupportAlgorithm = algorithm_type2;
                            }
                            ALGORITHM_TYPE algorithm_type6 = nSLocationMonitor.mSupportAlgorithm;
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", algorithm_type6.ordinal());
                            nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                                    LocationConstants.STATE_TYPE.SUPPORT_ALGORITHM_TYPE, bundle);
                            return;
                        }
                        if (result == 4 || result == 5) {
                            return;
                        }
                        if (result == 3) {
                            Log.e("NSLocationMonitor", "SENSOR OUT");
                            NSLocationMonitor.this.disableDeviceActivity(
                                    DEVICE_ACTIVITY_ERROR_CODE.SENSOR_OUT);
                            return;
                        }
                        if (algorithm_type3 == algorithm_type2) {
                            nSLocationMonitor.getClass();
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("activity", activity);
                            bundle2.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, result);
                            bundle2.putInt("movement", duration);
                            nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                                    LocationConstants.STATE_TYPE.MOTION_STATE_CHANGED, bundle2);
                            return;
                        }
                        if (algorithm_type3 != algorithm_type5) {
                            Log.e(
                                    "NSLocationMonitor",
                                    "onSemContextChanged, algorithm is not supported");
                            return;
                        }
                        nSLocationMonitor.getClass();
                        if (activity != 1) {
                            if (result == 1) {
                                if (nSLocationMonitor.mIsStationary) {
                                    nSLocationMonitor.sendStationaryInfo(false);
                                }
                                nSLocationMonitor.mIsStationary = false;
                                return;
                            }
                            return;
                        }
                        if (result == 1) {
                            if (!nSLocationMonitor.mIsStationary) {
                                nSLocationMonitor.sendStationaryInfo(true);
                            }
                            nSLocationMonitor.mIsStationary = true;
                        } else if (result == 2) {
                            if (nSLocationMonitor.mIsStationary) {
                                nSLocationMonitor.sendStationaryInfo(false);
                            }
                            nSLocationMonitor.mIsStationary = false;
                        }
                    }
                }
            };

    /* renamed from: com.android.server.location.nsflp.NSLocationMonitor$4, reason: invalid class name */
    public final class AnonymousClass4 extends NotificationListenerService {
        public static /* synthetic */ void $r8$lambda$UqTBjMb3xqJQs7mYPjsLobcSlD8(
                AnonymousClass4 anonymousClass4) {
            synchronized (NSLocationMonitor.this.mNotificationListener) {
                try {
                    if (!NSLocationMonitor.this.mRegisteredNotificationListener) {
                        Log.d(
                                "NSLocationMonitor",
                                "Notification listener is disconnected so do not set the trim");
                        return;
                    }
                    anonymousClass4.setOnNotificationPostedTrim(1);
                    super.onListenerConnected();
                    Log.v("NSLocationMonitor", "onListenerConnected for NotificationListener");
                    ((HashMap) NSLocationMonitor.this.mForegroundNotificationList).clear();
                    NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                            LocationConstants.STATE_TYPE.NOTIFICATION_LISTENER_CONNECTED, null);
                } finally {
                }
            }
        }

        public AnonymousClass4() {}

        @Override // android.service.notification.NotificationListenerService
        public final void onListenerConnected() {
            NSLocationMonitor.this.mHandler.post(
                    new NSLocationMonitor$4$$ExternalSyntheticLambda1(this, 1));
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onListenerDisconnected() {
            NSLocationMonitor.this.mHandler.post(
                    new NSLocationMonitor$4$$ExternalSyntheticLambda1(this, 0));
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
            NSLocationMonitor.this.mHandler.post(
                    new NSLocationMonitor$4$$ExternalSyntheticLambda0(
                            this, statusBarNotification, 1));
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
            NSLocationMonitor.this.mHandler.post(
                    new NSLocationMonitor$4$$ExternalSyntheticLambda0(
                            this, statusBarNotification, 0));
        }
    }

    final class ALGORITHM_TYPE {
        public static final /* synthetic */ ALGORITHM_TYPE[] $VALUES;
        public static final ALGORITHM_TYPE NEW;
        public static final ALGORITHM_TYPE NOT_SUPPORT;
        public static final ALGORITHM_TYPE OLD;

        static {
            ALGORITHM_TYPE algorithm_type = new ALGORITHM_TYPE("NOT_SUPPORT", 0);
            NOT_SUPPORT = algorithm_type;
            ALGORITHM_TYPE algorithm_type2 = new ALGORITHM_TYPE("OLD", 1);
            OLD = algorithm_type2;
            ALGORITHM_TYPE algorithm_type3 = new ALGORITHM_TYPE("NEW", 2);
            NEW = algorithm_type3;
            $VALUES = new ALGORITHM_TYPE[] {algorithm_type, algorithm_type2, algorithm_type3};
        }

        public static ALGORITHM_TYPE valueOf(String str) {
            return (ALGORITHM_TYPE) Enum.valueOf(ALGORITHM_TYPE.class, str);
        }

        public static ALGORITHM_TYPE[] values() {
            return (ALGORITHM_TYPE[]) $VALUES.clone();
        }
    }

    final class DEVICE_ACTIVITY_ERROR_CODE {
        public static final /* synthetic */ DEVICE_ACTIVITY_ERROR_CODE[] $VALUES;
        public static final DEVICE_ACTIVITY_ERROR_CODE NOT_SUPPORT;
        public static final DEVICE_ACTIVITY_ERROR_CODE SENSOR_OUT;
        public static final DEVICE_ACTIVITY_ERROR_CODE SEVICE_DISCONNECT;

        static {
            DEVICE_ACTIVITY_ERROR_CODE device_activity_error_code =
                    new DEVICE_ACTIVITY_ERROR_CODE("SEVICE_DISCONNECT", 0);
            SEVICE_DISCONNECT = device_activity_error_code;
            DEVICE_ACTIVITY_ERROR_CODE device_activity_error_code2 =
                    new DEVICE_ACTIVITY_ERROR_CODE("NOT_SUPPORT", 1);
            NOT_SUPPORT = device_activity_error_code2;
            DEVICE_ACTIVITY_ERROR_CODE device_activity_error_code3 =
                    new DEVICE_ACTIVITY_ERROR_CODE("SENSOR_OUT", 2);
            SENSOR_OUT = device_activity_error_code3;
            $VALUES =
                    new DEVICE_ACTIVITY_ERROR_CODE[] {
                        device_activity_error_code,
                        device_activity_error_code2,
                        device_activity_error_code3
                    };
        }

        public static DEVICE_ACTIVITY_ERROR_CODE valueOf(String str) {
            return (DEVICE_ACTIVITY_ERROR_CODE) Enum.valueOf(DEVICE_ACTIVITY_ERROR_CODE.class, str);
        }

        public static DEVICE_ACTIVITY_ERROR_CODE[] values() {
            return (DEVICE_ACTIVITY_ERROR_CODE[]) $VALUES.clone();
        }
    }

    final class HQM_SUPPORT_STATE {
        public static final /* synthetic */ HQM_SUPPORT_STATE[] $VALUES;
        public static final HQM_SUPPORT_STATE NOT_SUPPORT;
        public static final HQM_SUPPORT_STATE SUPPORT;
        public static final HQM_SUPPORT_STATE UNKNOWN;

        static {
            HQM_SUPPORT_STATE hqm_support_state = new HQM_SUPPORT_STATE("UNKNOWN", 0);
            UNKNOWN = hqm_support_state;
            HQM_SUPPORT_STATE hqm_support_state2 = new HQM_SUPPORT_STATE("NOT_SUPPORT", 1);
            NOT_SUPPORT = hqm_support_state2;
            HQM_SUPPORT_STATE hqm_support_state3 = new HQM_SUPPORT_STATE("SUPPORT", 2);
            SUPPORT = hqm_support_state3;
            $VALUES =
                    new HQM_SUPPORT_STATE[] {
                        hqm_support_state, hqm_support_state2, hqm_support_state3
                    };
        }

        public static HQM_SUPPORT_STATE valueOf(String str) {
            return (HQM_SUPPORT_STATE) Enum.valueOf(HQM_SUPPORT_STATE.class, str);
        }

        public static HQM_SUPPORT_STATE[] values() {
            return (HQM_SUPPORT_STATE[]) $VALUES.clone();
        }
    }

    public final class NetworkStateListener extends PhoneStateListener {
        public NetworkStateListener(int i) {
            super(NSLocationMonitor.this.mExecutor);
            Log.v("NSLocationMonitor", "Constructor, NetworkStateListener subId=" + i);
        }

        @Override // android.telephony.PhoneStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            int i;
            String str;
            if (serviceState == null) {
                Log.e("NSLocationMonitor", "onServiceStateChanged null");
                return;
            }
            int state = serviceState.getState();
            int channelNumber = serviceState.getChannelNumber();
            Log.w(
                    "NSLocationMonitor",
                    "onServiceStateChanged, state=" + state + " / channel=" + channelNumber);
            boolean z = true;
            NetworkRegistrationInfo networkRegistrationInfo =
                    serviceState.getNetworkRegistrationInfo(2, 1);
            if (networkRegistrationInfo != null) {
                i = networkRegistrationInfo.getAccessNetworkTechnology();
                str = TelephonyManager.getNetworkTypeName(i);
                r3 = networkRegistrationInfo.getNrState() == 3;
                FlashNotificationsController$$ExternalSyntheticOutline0.m(
                        "NSLocationMonitor",
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "networkType[", "]=", str, " / nrState="),
                        r3);
            } else {
                Log.w("NSLocationMonitor", "Failed to get registration info from serviceState");
                i = -1;
                str = "unknown";
                z = false;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("serviceState", state);
            bundle.putBoolean("isRegistered", z);
            bundle.putBoolean("nrState", r3);
            bundle.putInt("channelNumber", channelNumber);
            bundle.putInt("networkType", i);
            bundle.putString("networkTypeName", str);
            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.SERVICE_STATE_CHANGED, bundle);
        }
    }

    public NSLocationMonitor(
            Context context, LocationManagerService.SystemInjector systemInjector) {
        Handler handler;
        HandlerExecutor handlerExecutor;
        this.mContext = context;
        this.mInjector = systemInjector;
        NSLocationThread nSLocationThread = NSLocationThread.sInstance;
        synchronized (NSLocationThread.class) {
            NSLocationThread.ensureThreadLocked();
            handler = NSLocationThread.sHandler;
        }
        this.mHandler = handler;
        this.mLooper = handler.getLooper();
        synchronized (NSLocationThread.class) {
            NSLocationThread.ensureThreadLocked();
            handlerExecutor = NSLocationThread.sHandlerExecutor;
        }
        this.mExecutor = handlerExecutor;
        this.mNSConnectionHelper = systemInjector.mNSConnectionHelper;
        this.mNSLocationProviderHelper = systemInjector.mNSLocationProviderHelper;
        this.mSystemRunningTime = SystemClock.elapsedRealtime();
        this.mLocationPowerSaveModeHelper = systemInjector.mLocationPowerSaveModeHelper;
        this.mDeviceIdleHelper = systemInjector.mDeviceIdleHelper;
        this.mDeviceStationaryHelper = systemInjector.mDeviceStationaryHelper;
    }

    public static boolean isCallerNsflp() {
        return Binder.getCallingUid() == 5013;
    }

    public final void disableDeviceActivity(DEVICE_ACTIVITY_ERROR_CODE device_activity_error_code) {
        setFeatureDeviceActivity(false);
        Bundle bundle = new Bundle();
        bundle.putString("code", device_activity_error_code.name());
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.DEVICE_ACTIVITY_ERROR, bundle);
    }

    public final Bundle getActiveRequests(String str) {
        Bundle bundle;
        Bundle bundle2 = null;
        if (!isCallerNsflp()) {
            return null;
        }
        NSLocationProviderHelper nSLocationProviderHelper =
                ((LocationManagerService.SystemInjector) this.mInjector).mNSLocationProviderHelper;
        nSLocationProviderHelper.getClass();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        Set set = (Set) ((ConcurrentHashMap) nSLocationProviderHelper.mRegistrationMap).get(str);
        if (set == null || set.isEmpty()) {
            bundle = null;
        } else {
            for (Iterator it = set.iterator(); it.hasNext(); it = it) {
                LocationProviderManager.Registration registration =
                        (LocationProviderManager.Registration) it.next();
                CallerIdentity callerIdentity = registration.mIdentity;
                LocationRequest request = registration.getRequest();
                int quality = request.getQuality();
                String packageName = callerIdentity.getPackageName();
                int uid = callerIdentity.getUid();
                int pid = callerIdentity.getPid();
                boolean z = quality == 100;
                boolean isForeground = registration.isForeground();
                String str2 = registration.mListenerId;
                ArrayList<? extends Parcelable> arrayList2 = arrayList;
                long intervalMillis = request.getIntervalMillis();
                long minUpdateIntervalMillis = request.getMinUpdateIntervalMillis();
                long intervalMillis2 = request.getIntervalMillis();
                boolean z2 = registration.isListenerType;
                boolean z3 = z;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                boolean isPermitted = registration.isPermitted();
                if (str == null || str2 == null || packageName == null) {
                    StringBuilder m =
                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                    "New requestInfo mandatory fields are null,PackageName=",
                                    packageName,
                                    "/ListenerId=",
                                    str2,
                                    "/Provider=");
                    m.append(str);
                    m.append("/isAllowed=");
                    m.append(isPermitted);
                    throw new NullPointerException(m.toString());
                }
                LocationRequestInfo locationRequestInfo = new LocationRequestInfo();
                locationRequestInfo.isPassive = false;
                locationRequestInfo.lastUpdateTime = 0L;
                locationRequestInfo.packageName = packageName;
                locationRequestInfo.versionName = null;
                locationRequestInfo.listenerId = str2;
                locationRequestInfo.provider = str;
                locationRequestInfo.quality = quality;
                locationRequestInfo.requestTime = elapsedRealtime;
                locationRequestInfo.backgroundTime = 0L;
                locationRequestInfo.interval = intervalMillis;
                locationRequestInfo.minUpdateInterval = minUpdateIntervalMillis;
                locationRequestInfo.maxWaitTime = intervalMillis2;
                locationRequestInfo.requester = 0;
                locationRequestInfo.isForeground = isForeground;
                locationRequestInfo.numUpdates = Integer.MAX_VALUE;
                locationRequestInfo.uid = uid;
                locationRequestInfo.pid = pid;
                locationRequestInfo.isListenerType = z2;
                locationRequestInfo.isAllowed = isPermitted;
                locationRequestInfo.isHighPowerRequest = z3;
                Log.i(
                        "NSLocationProviderHelper",
                        "onActiveRequestSync, added " + locationRequestInfo);
                arrayList = arrayList2;
                arrayList.add(locationRequestInfo);
                bundle2 = null;
            }
            bundle = bundle2;
            Log.i("NSLocationProviderHelper", "onActiveRequestSync, size=" + arrayList.size());
            if (!arrayList.isEmpty()) {
                Bundle bundle3 = new Bundle();
                bundle3.putString("provider", str);
                bundle3.putParcelableArrayList("activeRequests", arrayList);
                return bundle3;
            }
        }
        return bundle;
    }

    public final int getCrashCount() {
        return this.mCrashCount;
    }

    public final long getCrashTime() {
        return this.mCrashTime;
    }

    public final Bundle getUidState(int i, int i2) {
        NSPermissionHelper.UidState uidState;
        if (!isCallerNsflp()
                || (uidState =
                                (NSPermissionHelper.UidState)
                                        ((LocationManagerService.SystemInjector) this.mInjector)
                                                .mNSPermissionHelper.mUidObserver.mUidState.get(
                                                        Integer.valueOf(i)))
                        == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("procState", uidState.state);
        bundle.putBoolean("hasLocationCapability", (uidState.capability & 1) == 1);
        bundle.putInt(
                "permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
        return bundle;
    }

    public final boolean isLocationEnabled(int i) {
        if (isCallerNsflp()) {
            return ((LocationManagerService.SystemInjector) this.mInjector)
                    .mSettingsHelper.isLocationEnabled(i);
        }
        return false;
    }

    public final boolean isProviderEnabled(String str, int i) {
        if (!isCallerNsflp()) {
            return false;
        }
        if (this.mLocationManagerInternal == null) {
            this.mLocationManagerInternal =
                    (LocationManagerInternal)
                            LocalServices.getService(LocationManagerInternal.class);
        }
        LocationManagerInternal locationManagerInternal = this.mLocationManagerInternal;
        return locationManagerInternal != null
                && locationManagerInternal.isProviderEnabledForUser(str, i);
    }

    public final void noteGpsOp(int i, int i2) {
        if (isCallerNsflp()) {
            if (this.mBatteryStats == null) {
                IBatteryStats asInterface =
                        IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
                this.mBatteryStats = asInterface;
                if (asInterface == null) {
                    Log.w("NSLocationMonitor", "Failed to get batterystats");
                    return;
                }
            }
            Log.i(
                    "NSLocationMonitor",
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            i, i2, "noteGpsOp, mode=", " (", ")"));
            try {
                if (i == 1) {
                    this.mBatteryStats.noteStartGps(i2);
                } else if (i == 2) {
                    this.mBatteryStats.noteStopGps(i2);
                } else if (i != 3) {
                } else {
                    this.mBatteryStats.noteResetGps();
                }
            } catch (RemoteException unused) {
                Log.e("NSLocationMonitor", "Failed to call noteGpsOp");
            }
        }
    }

    public final void notifyCrash(long j) {
        this.mCrashCount++;
        this.mCrashTime = j;
    }

    public final void registerDeviceActivityDetector(int i, int i2, boolean z) {
        if (!this.mEnableFeatureDeviceActivity) {
            Log.w("NSLocationMonitor", "Failed registerDeviceActivityDetector");
            return;
        }
        if (this.mDeviceActivityRegistered) {
            if (this.mDeviceActivityDuration == i2
                    && this.mDeviceActivityMode == i
                    && this.mRequestToUpdate == z) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i,
                                i2,
                                "Already registered same attribute, mode : ",
                                ", duration : ",
                                ", requestToUpdate : ");
                m.append(z);
                Log.w("NSLocationMonitor", m.toString());
                return;
            }
            this.mSemContextManager.unregisterListener(this.mSemContextListener, 54);
        }
        try {
            if (this.mSemContextManager.registerListener(
                    this.mSemContextListener, 54, this.mLooper)) {
                Log.d(
                        "NSLocationMonitor",
                        "Success registerDeviceActivityDetector, mode : "
                                + i
                                + ", duration : "
                                + i2
                                + ", requestToUpdate : "
                                + z);
                this.mSemContextManager.changeParameters(
                        this.mSemContextListener,
                        54,
                        new SemContextDeviceActivityDetectorAttribute(i, i2, z));
                this.mDeviceActivityRegistered = true;
                this.mDeviceActivityMode = i;
                this.mDeviceActivityDuration = i2;
                this.mRequestToUpdate = z;
            }
        } catch (RuntimeException e) {
            Log.e(
                    "NSLocationMonitor",
                    "Exception is occurred while registerDeviceActivityDetector, " + e.toString());
        }
    }

    public final void requestToUpdateDeviceActivityDetector() {
        if (!this.mEnableFeatureDeviceActivity) {
            Log.w("NSLocationMonitor", "Failed updateDeviceActivityDetector");
            return;
        }
        try {
            this.mSemContextManager.requestToUpdate(this.mSemContextListener, 54);
        } catch (RuntimeException e) {
            Log.e(
                    "NSLocationMonitor",
                    "Exception is occurred while updateDeviceActivityDetector, " + e.toString());
        }
    }

    public final void sendLogToHqm(String str, String str2, String str3) {
        if (isCallerNsflp()) {
            HQM_SUPPORT_STATE hqm_support_state = this.mSupportHqm;
            HQM_SUPPORT_STATE hqm_support_state2 = HQM_SUPPORT_STATE.UNKNOWN;
            HQM_SUPPORT_STATE hqm_support_state3 = HQM_SUPPORT_STATE.NOT_SUPPORT;
            if (hqm_support_state == hqm_support_state2 && this.mHqmManager == null) {
                ISemHqmManager asInterface =
                        ISemHqmManager.Stub.asInterface(
                                ServiceManager.getService("HqmManagerService"));
                this.mHqmManager = asInterface;
                this.mSupportHqm =
                        asInterface != null ? HQM_SUPPORT_STATE.SUPPORT : hqm_support_state3;
            }
            if (this.mSupportHqm == hqm_support_state3) {
                Log.i("NSLocationMonitor", "HQM not supported device");
                return;
            }
            try {
                this.mHqmManager.sendHWParamToHQM(
                        0, "GNSS", str, "ph", "0.0", "sec", str2, str3, "");
            } catch (RemoteException e) {
                Log.w("NSLocationMonitor", "Failed logging big data for GNSS" + e.toString());
            }
        }
    }

    public final void sendStationaryInfo(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("stationary", z);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.MOTION_STATE_CHANGED, bundle);
    }

    public final boolean setFeatureDeviceActivity(boolean z) {
        if (!z) {
            if (this.mIsStationary) {
                this.mIsStationary = false;
                sendStationaryInfo(false);
            }
            unregisterDeviceActivityDetector();
            Log.w("NSLocationMonitor", "setFeatureDeviceActivity, false");
            this.mEnableFeatureDeviceActivity = false;
            return true;
        }
        if (this.mSemContextManager == null) {
            SemContextManager semContextManager =
                    (SemContextManager) this.mContext.getSystemService("scontext");
            this.mSemContextManager = semContextManager;
            if (semContextManager == null) {
                Log.w("NSLocationMonitor", "semContextManager is null");
                this.mEnableFeatureDeviceActivity = false;
                return false;
            }
        }
        this.mEnableFeatureDeviceActivity = this.mSemContextManager.isAvailableService(54);
        FlashNotificationsController$$ExternalSyntheticOutline0.m(
                "NSLocationMonitor",
                new StringBuilder("setFeatureDeviceActivity, isAvailable, "),
                this.mEnableFeatureDeviceActivity);
        return this.mEnableFeatureDeviceActivity;
    }

    public final void setMotionPowerSaveMode(boolean z) {
        if (isCallerNsflp()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("NSLocationMonitor", "setMotionPowerSaveMode, " + z);
                ((LocationManagerService.SystemInjector) this.mInjector)
                        .mNSLocationProviderHelper.notifyMotionPowerSaveModeChanged(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void unregisterDeviceActivityDetector() {
        if (!this.mEnableFeatureDeviceActivity) {
            Log.w("NSLocationMonitor", "Failed unregisterDeviceActivityDetector");
            return;
        }
        if (!this.mDeviceActivityRegistered) {
            Log.w("NSLocationMonitor", "Already unregistered DeviceActivityDetector");
            return;
        }
        try {
            this.mSemContextManager.unregisterListener(this.mSemContextListener, 54);
            Log.d("NSLocationMonitor", "Success unregisterDeviceActivityDetector");
        } catch (RuntimeException e) {
            Log.e(
                    "NSLocationMonitor",
                    "Exception is occurred while unregisterDeviceActivityDetector, "
                            + e.toString());
        }
        this.mIsStationary = false;
        this.mDeviceActivityRegistered = false;
        this.mDeviceActivityMode = 0;
        this.mDeviceActivityDuration = 0;
        this.mRequestToUpdate = false;
    }

    public final void updateBackgroundThrottlingAllowlist(List list) {
        if (isCallerNsflp()) {
            SystemSettingsHelper systemSettingsHelper =
                    ((LocationManagerService.SystemInjector) this.mInjector).mSettingsHelper;
            systemSettingsHelper.mBackgroundThrottlePackageAllowlistByNsflp.clear();
            if (list == null) {
                Log.w("LocationManagerService", "Null package is updated, so just clear");
            } else {
                systemSettingsHelper.mBackgroundThrottlePackageAllowlistByNsflp.addAll(list);
                SystemSettingsHelper.StringSetCachedGlobalSetting stringSetCachedGlobalSetting =
                        systemSettingsHelper.mBackgroundThrottlePackageWhitelist;
                stringSetCachedGlobalSetting.getClass();
                Log.i("LocationManagerService", "location setting is changed by nsflp");
                Iterator it = stringSetCachedGlobalSetting.mListeners.iterator();
                while (it.hasNext()) {
                    ((SettingsHelper$UserSettingChangedListener) it.next()).onSettingChanged(-1);
                }
            }
            boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            FreecessController freecessController =
                    FreecessController.FreecessControllerHolder.INSTANCE;
            freecessController.getClass();
            Slog.e("FreecessController", "setGPSAllowList");
            freecessController.mGpsDefaultAllowList = list;
        }
    }

    public final void writeUtLog(int i, String str, String str2) {
        if (isCallerNsflp()) {
            if (this.mNsUtLogger == null) {
                this.mNsUtLogger =
                        new NSUtLogger(
                                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                                "NS_UT_LOGGER_THREAD")
                                        .getLooper());
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            NSUtLogger.LogInfo logInfo = new NSUtLogger.LogInfo();
            logInfo.type = i;
            logInfo.path = str;
            logInfo.data = str2;
            obtain.obj = logInfo;
            this.mNsUtLogger.sendMessage(obtain);
        }
    }
}
