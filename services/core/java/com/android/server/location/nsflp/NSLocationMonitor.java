package com.android.server.location.nsflp;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.INSLocationCallback;
import android.location.INSLocationManager;
import android.location.LocationConstants;
import android.location.LocationManagerInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ISemHqmManager;
import android.os.Message;
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
import com.android.internal.app.IBatteryStats;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.LocationPermissions;
import com.android.server.location.injector.DeviceIdleHelper;
import com.android.server.location.injector.DeviceStationaryHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPowerSaveModeHelper;
import com.android.server.location.nsflp.NSPermissionHelper;
import com.android.server.location.nsflp.NSUtLogger;
import com.samsung.android.hardware.context.SemContextDeviceActivityDetector;
import com.samsung.android.hardware.context.SemContextDeviceActivityDetectorAttribute;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class NSLocationMonitor extends INSLocationCallback.Stub {
    public static final Object MONITOR_SERVICE_LOCK = new Object();
    public final Context mContext;
    public int mDeviceActivityDuration;
    public int mDeviceActivityMode;
    public boolean mDeviceActivityRegistered;
    public final DeviceIdleHelper mDeviceIdleHelper;
    public final DeviceStationaryHelper mDeviceStationaryHelper;
    public boolean mEnableFeatureDeviceActivity;
    public ISemHqmManager mHqmManager;
    public final Injector mInjector;
    public boolean mIsStationary;
    public LocationManagerInternal mLocationManagerInternal;
    public final LocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
    public final NSConnectionHelper mNSConnectionHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public NSUtLogger mNsUtLogger;
    public PackageManager mPackageManager;
    public PhoneStateListener[] mPhoneStateListener;
    public boolean mRequestToUpdate;
    public SemContextManager mSemContextManager;
    public int mSimCount;
    public int[] mSimSubId;
    public IBatteryStats mBatteryStats = null;
    public ArrayList mConnectedHistory = new ArrayList();
    public ArrayList mDisconnectedHistory = new ArrayList();
    public INSLocationManager mMonitorService = null;
    public boolean mRegisteredNotificationListener = false;
    public final Map mForegroundNotificationList = new HashMap();
    public final Object mNetworkLock = new Object();
    public ALGORITHM_TYPE mSupportAlgorithm = ALGORITHM_TYPE.NOT_SUPPORT;
    public HQM_SUPPORT_STATE mSupportHqm = HQM_SUPPORT_STATE.UNKNOWN;
    public final LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener mLocationPowerSaveModeChangedListener = new LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener() { // from class: com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda0
        @Override // com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener
        public final void onLocationPowerSaveModeChanged(int i) {
            NSLocationMonitor.this.onLocationPowerSaveModeChanged(i);
        }
    };
    public final DeviceIdleInternal.StationaryListener mStationaryListener = new DeviceIdleInternal.StationaryListener() { // from class: com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda1
        public final void onDeviceStationaryChanged(boolean z) {
            NSLocationMonitor.this.onDeviceStationaryChanged(z);
        }
    };
    public final DeviceIdleHelper.DeviceIdleListener mDeviceIdleListener = new DeviceIdleHelper.DeviceIdleListener() { // from class: com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda2
        @Override // com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener
        public final void onDeviceIdleChanged(boolean z) {
            NSLocationMonitor.this.onDeviceIdleChanged(z);
        }
    };
    public int mCrashCount = 0;
    public long mCrashTime = 0;
    public ServiceConnection mMonitorServiceConnection = new ServiceConnection() { // from class: com.android.server.location.nsflp.NSLocationMonitor.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("NSLocationMonitor", "MonitorService is connected, " + componentName);
            synchronized (NSLocationMonitor.MONITOR_SERVICE_LOCK) {
                NSLocationMonitor.this.mMonitorService = INSLocationManager.Stub.asInterface(iBinder);
                if (NSLocationMonitor.this.mMonitorService == null) {
                    Log.e("NSLocationMonitor", "Failed to stub interface to MonitorService");
                    return;
                }
                try {
                    NSLocationMonitor.this.mMonitorService.setCallback(NSLocationMonitor.this);
                } catch (Exception unused) {
                    Log.w("NSLocationMonitor", "Failed to set callback");
                }
                NSLocationMonitor.this.mNSConnectionHelper.onServiceConnected(NSLocationMonitor.this.mMonitorService);
                NSLocationMonitor.this.sendConnectionInfo();
                NSLocationMonitor.this.mNSConnectionHelper.sendSupportedBdmsgFormat();
                synchronized (NSLocationMonitor.this.mNotificationListener) {
                    if (!NSLocationMonitor.this.mRegisteredNotificationListener) {
                        try {
                            NSLocationMonitor.this.mNotificationListener.registerAsSystemService(NSLocationMonitor.this.mContext, new ComponentName(NSLocationMonitor.this.mContext.getPackageName(), NSLocationMonitor.this.mContext.getClass().getCanonicalName()), ActivityManager.getCurrentUser());
                            NSLocationMonitor.this.mRegisteredNotificationListener = true;
                        } catch (RemoteException e) {
                            Log.e("NSLocationMonitor", "Failed to register notification listener, " + e.toString());
                        }
                    }
                }
                NSLocationMonitor.this.initNetworkStateListener();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("NSLocationMonitor", "MonitorService has unexpectedly disconnected, " + componentName);
            synchronized (NSLocationMonitor.MONITOR_SERVICE_LOCK) {
                NSLocationMonitor.this.mMonitorService = null;
                NSLocationMonitor.this.mNSConnectionHelper.onServiceDisconnected();
            }
            NSLocationMonitor.this.mDisconnectedHistory.add(Long.valueOf(SystemClock.elapsedRealtime()));
            NSLocationMonitor.this.noteGpsOp(3, 0);
            synchronized (NSLocationMonitor.this.mNotificationListener) {
                if (NSLocationMonitor.this.mRegisteredNotificationListener) {
                    try {
                        NSLocationMonitor.this.mNotificationListener.unregisterAsSystemService();
                        NSLocationMonitor.this.mRegisteredNotificationListener = false;
                    } catch (RemoteException e) {
                        Log.e("NSLocationMonitor", "Failed to unregister notification listener, " + e.toString());
                    }
                }
            }
            NSLocationMonitor.this.deinitNetworkStateListener();
            NSLocationMonitor.this.disableDeviceActivity(DEVICE_ACTIVITY_ERROR_CODE.SEVICE_DISCONNECT);
            NSLocationMonitor.this.mNSLocationProviderHelper.notifyMotionPowerSaveModeChanged(false);
            if (NSLocationMonitor.this.mCrashCount >= 3) {
                Log.e("NSLocationMonitor", "" + NSLocationMonitor.this.mCrashCount + " times disconnected, so stop " + componentName);
                NSLocationMonitor.this.mContext.unbindService(this);
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Log.d("NSLocationMonitor", "onNullBinding is called, " + componentName);
            NSLocationMonitor.this.mContext.unbindService(this);
        }
    };
    public final SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.location.nsflp.NSLocationMonitor.2
        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            Log.i("NSLocationMonitor", "onSubscriptionsChanged");
            synchronized (NSLocationMonitor.this.mNetworkLock) {
                boolean z = false;
                for (int i = 0; i < NSLocationMonitor.this.mSimCount; i++) {
                    int[] subId = SubscriptionManager.getSubId(i);
                    if (subId != null && subId.length != 0) {
                        int i2 = subId[0];
                        if (NSLocationMonitor.this.mSimSubId != null) {
                            if (NSLocationMonitor.this.mSimSubId.length == NSLocationMonitor.this.mSimCount && i2 == NSLocationMonitor.this.mSimSubId[i]) {
                                Log.w("NSLocationMonitor", "SubId[" + i2 + "] for sim[" + i + "] was not changed");
                            } else {
                                Log.w("NSLocationMonitor", "SubId was changed for sim[" + i + "]=" + i2);
                                NSLocationMonitor.this.mSimSubId[i] = i2;
                            }
                        }
                        TelephonyManager telephonyManager = (TelephonyManager) NSLocationMonitor.this.mContext.getSystemService("phone");
                        if (telephonyManager != null) {
                            if (NSLocationMonitor.this.mPhoneStateListener[i] != null) {
                                Log.i("NSLocationMonitor", "unregister previous PhoneStateListener SimSlot=" + i);
                                telephonyManager.listen(NSLocationMonitor.this.mPhoneStateListener[i], 0);
                            }
                            NSLocationMonitor.this.mPhoneStateListener[i] = new NetworkStateListener(i2);
                            telephonyManager.listen(NSLocationMonitor.this.mPhoneStateListener[i], 1);
                            z = true;
                        }
                    }
                    Log.w("NSLocationMonitor", "Failed to load subId for sim[" + i + "]");
                }
                if (z) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isInitialized", true);
                    NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SIM_STATE_CHANGED, bundle);
                }
            }
        }
    };
    public final NotificationListenerService mNotificationListener = new NotificationListenerService() { // from class: com.android.server.location.nsflp.NSLocationMonitor.3
        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(StatusBarNotification statusBarNotification) {
            Notification notification;
            if (statusBarNotification == null || (notification = statusBarNotification.getNotification()) == null || !NSLocationMonitor.this.isForegroundService(notification)) {
                return;
            }
            String packageName = statusBarNotification.getPackageName();
            if (NSLocationMonitor.this.addForegroundNotification(packageName, statusBarNotification.getKey())) {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", packageName);
                NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.NOTIFICATION_POSTED, bundle);
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
            Notification notification;
            if (statusBarNotification == null || (notification = statusBarNotification.getNotification()) == null || !NSLocationMonitor.this.isForegroundService(notification)) {
                return;
            }
            String packageName = statusBarNotification.getPackageName();
            if (NSLocationMonitor.this.removeForegroundNotification(packageName, statusBarNotification.getKey())) {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", packageName);
                NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.NOTIFICATION_REMOVED, bundle);
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public void onListenerConnected() {
            synchronized (NSLocationMonitor.this.mNotificationListener) {
                if (!NSLocationMonitor.this.mRegisteredNotificationListener) {
                    Log.d("NSLocationMonitor", "Notification listener is disconnected so do not set the trim");
                    return;
                }
                setOnNotificationPostedTrim(1);
                super.onListenerConnected();
                Log.v("NSLocationMonitor", "onListenerConnected for NotificationListener");
                NSLocationMonitor.this.mForegroundNotificationList.clear();
                NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.NOTIFICATION_LISTENER_CONNECTED, null);
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public void onListenerDisconnected() {
            Log.w("NSLocationMonitor", "onListenerDisconnected for NotificationListener");
            NSLocationMonitor.this.mForegroundNotificationList.clear();
            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.NOTIFICATION_LISTENER_DISCONNECTED, null);
        }
    };
    public final SemContextListener mSemContextListener = new SemContextListener() { // from class: com.android.server.location.nsflp.NSLocationMonitor.4
        public void onSemContextChanged(SemContextEvent semContextEvent) {
            if (semContextEvent.semContext.getType() == 54) {
                SemContextDeviceActivityDetector deviceActivityDetectorContext = semContextEvent.getDeviceActivityDetectorContext();
                int activity = deviceActivityDetectorContext.getActivity();
                int result = deviceActivityDetectorContext.getResult();
                ALGORITHM_TYPE algorithm_type = NSLocationMonitor.this.mSupportAlgorithm;
                ALGORITHM_TYPE algorithm_type2 = ALGORITHM_TYPE.NEW;
                int duration = algorithm_type == algorithm_type2 ? deviceActivityDetectorContext.getDuration() : -1;
                Log.d("NSLocationMonitor", "onSemContextChanged, activity : " + activity + " result : " + result + " movement : " + duration);
                if (NSLocationMonitor.this.mSupportAlgorithm == ALGORITHM_TYPE.NOT_SUPPORT) {
                    NSLocationMonitor.this.checkSupportAlgorithm(result);
                    return;
                }
                if (result == 4 || result == 5) {
                    return;
                }
                if (result == 3) {
                    Log.e("NSLocationMonitor", "SENSOR OUT");
                    NSLocationMonitor.this.disableDeviceActivity(DEVICE_ACTIVITY_ERROR_CODE.SENSOR_OUT);
                } else if (NSLocationMonitor.this.mSupportAlgorithm == algorithm_type2) {
                    NSLocationMonitor.this.sendStationaryInfo(activity, result, duration);
                } else if (NSLocationMonitor.this.mSupportAlgorithm == ALGORITHM_TYPE.OLD) {
                    NSLocationMonitor.this.checkActivityResult(activity, result);
                } else {
                    Log.e("NSLocationMonitor", "onSemContextChanged, algorithm is not supported");
                }
            }
        }
    };
    public final long mSystemRunningTime = SystemClock.elapsedRealtime();

    enum ALGORITHM_TYPE {
        NOT_SUPPORT,
        OLD,
        NEW
    }

    enum DEVICE_ACTIVITY_ERROR_CODE {
        SEVICE_DISCONNECT,
        NOT_SUPPORT,
        SENSOR_OUT
    }

    enum HQM_SUPPORT_STATE {
        UNKNOWN,
        NOT_SUPPORT,
        SUPPORT
    }

    public NSLocationMonitor(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mNSConnectionHelper = injector.getNSConnectionHelper();
        this.mNSLocationProviderHelper = injector.getNSLocationProviderHelper();
        this.mLocationPowerSaveModeHelper = injector.getLocationPowerSaveModeHelper();
        this.mDeviceIdleHelper = injector.getDeviceIdleHelper();
        this.mDeviceStationaryHelper = injector.getDeviceStationaryHelper();
    }

    public void onSystemReady() {
        PackageManager packageManager = this.mContext.getPackageManager();
        this.mPackageManager = packageManager;
        if (packageManager.hasSystemFeature("com.sec.feature.nsflp")) {
            int semGetSystemFeatureLevel = this.mPackageManager.semGetSystemFeatureLevel("com.sec.feature.nsflp");
            this.mNSConnectionHelper.setFeature(true);
            Log.i("NSLocationMonitor", "NS-FLP Feature available, nsFlpFeatureLevel = " + semGetSystemFeatureLevel);
            Log.i("NSLocationMonitor", "Try to bind NSMonitorService");
            this.mContext.bindService(new Intent().setComponent(new ComponentName("com.sec.location.nsflp2", "com.sec.location.nsflp2.nsmonitor.NSMonitorService")), this.mMonitorServiceConnection, 67108865);
            this.mLocationPowerSaveModeHelper.addListener(this.mLocationPowerSaveModeChangedListener);
            this.mDeviceIdleHelper.addListener(this.mDeviceIdleListener);
            this.mDeviceStationaryHelper.addListener(this.mStationaryListener);
            return;
        }
        Log.e("NSLocationMonitor", "Not binding the MonitorService");
        this.mNSConnectionHelper.setFeature(false);
    }

    public void updateBackgroundThrottlingAllowlist(List list) {
        this.mInjector.getSettingsHelper().updateBackgroundThrottlingAllowlist(list);
    }

    public Bundle getActiveRequests(String str) {
        return this.mInjector.getNSLocationProviderHelper().getActiveRequests(str);
    }

    public boolean isLocationEnabled(int i) {
        return this.mInjector.getSettingsHelper().isLocationEnabled(i);
    }

    public void noteGpsOp(int i, int i2) {
        if (this.mBatteryStats == null) {
            IBatteryStats asInterface = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
            this.mBatteryStats = asInterface;
            if (asInterface == null) {
                Log.w("NSLocationMonitor", "Failed to get batterystats");
                return;
            }
        }
        Log.i("NSLocationMonitor", "noteGpsOp, mode=" + i + " (" + i2 + ")");
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
            Log.e("NSLocationMonitor", "Failed to call noteResetGps");
        }
    }

    public Bundle getUidState(int i, int i2) {
        NSPermissionHelper.UidState uidState = this.mInjector.getNSPermissionHelper().getUidState(i);
        if (uidState == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("procState", uidState.getProcState());
        bundle.putBoolean("hasLocationCapability", uidState.hasLocationCapability());
        bundle.putInt("permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
        return bundle;
    }

    public Map getGPSUsingApps() {
        Log.i("NSLocationMonitor", "getGPSUsingApps() called");
        INSLocationManager iNSLocationManager = this.mMonitorService;
        if (iNSLocationManager == null) {
            Log.w("NSLocationMonitor", "NSMonitorService is not connected, return null");
            return null;
        }
        try {
            return iNSLocationManager.getGPSUsingApps();
        } catch (Exception e) {
            Log.e("NSLocationMonitor", "failed to getGPSUsingApps due to  " + e.toString() + ", return null");
            return null;
        }
    }

    public final void initNetworkStateListener() {
        synchronized (this.mNetworkLock) {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            boolean z = false;
            if (telephonyManager != null) {
                int phoneCount = telephonyManager.getPhoneCount();
                this.mSimCount = phoneCount;
                if (phoneCount > 0) {
                    Log.v("NSLocationMonitor", "Initialize NetworkStateListener, count=" + this.mSimCount);
                    int i = this.mSimCount;
                    this.mSimSubId = new int[i];
                    this.mPhoneStateListener = new PhoneStateListener[i];
                    boolean z2 = false;
                    for (int i2 = 0; i2 < this.mSimCount; i2++) {
                        int[] subId = SubscriptionManager.getSubId(i2);
                        if (subId == null) {
                            Log.e("NSLocationMonitor", "subId is null from simSlot[" + i2 + "]");
                        } else if (subId.length == 0) {
                            Log.e("NSLocationMonitor", "subId is empty from simSlot[" + i2 + "]");
                        } else {
                            Log.i("NSLocationMonitor", "subIdTemp, " + Arrays.toString(subId));
                            int i3 = subId[0];
                            this.mSimSubId[i2] = i3;
                            this.mPhoneStateListener[i2] = new NetworkStateListener(i3);
                            telephonyManager.listen(this.mPhoneStateListener[i2], 1);
                            Log.v("NSLocationMonitor", "registerPhoneStateListener SimSlot=" + i2 + ", subId=" + i3);
                            z2 = true;
                        }
                    }
                    z = z2;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("isInitialized", z);
            this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SIM_STATE_CHANGED, bundle);
            SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
            if (subscriptionManager != null) {
                Log.v("NSLocationMonitor", "Register SubscriptionChangedListener");
                subscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
            }
        }
    }

    public final void deinitNetworkStateListener() {
        SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener;
        synchronized (this.mNetworkLock) {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager != null && this.mPhoneStateListener != null) {
                for (int i = 0; i < this.mSimCount; i++) {
                    PhoneStateListener phoneStateListener = this.mPhoneStateListener[i];
                    if (phoneStateListener != null) {
                        telephonyManager.listen(phoneStateListener, 0);
                    }
                }
                this.mSimCount = 0;
                this.mPhoneStateListener = null;
            }
            SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
            if (subscriptionManager != null && (onSubscriptionsChangedListener = this.mSubscriptionListener) != null) {
                subscriptionManager.removeOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
                this.mSimSubId = null;
                Log.w("NSLocationMonitor", "Unregister SubscriptionChangedListener");
            }
        }
    }

    public void sendLogToHqm(String str, String str2, String str3) {
        if (this.mSupportHqm == HQM_SUPPORT_STATE.UNKNOWN && this.mHqmManager == null) {
            ISemHqmManager asInterface = ISemHqmManager.Stub.asInterface(ServiceManager.getService("HqmManagerService"));
            this.mHqmManager = asInterface;
            this.mSupportHqm = asInterface != null ? HQM_SUPPORT_STATE.SUPPORT : HQM_SUPPORT_STATE.NOT_SUPPORT;
        }
        if (this.mSupportHqm == HQM_SUPPORT_STATE.NOT_SUPPORT) {
            Log.i("NSLocationMonitor", "HQM not supported device");
            return;
        }
        try {
            this.mHqmManager.sendHWParamToHQM(0, "GNSS", str, "ph", "0.0", "sec", str2, str3, "");
        } catch (RemoteException e) {
            Log.w("NSLocationMonitor", "Failed logging big data for GNSS" + e.toString());
        }
    }

    public final void sendConnectionInfo() {
        this.mConnectedHistory.add(Long.valueOf(SystemClock.elapsedRealtime()));
        Bundle bundle = new Bundle();
        bundle.putLong("systemRunning", this.mSystemRunningTime);
        bundle.putSerializable("connectedHistory", this.mConnectedHistory);
        bundle.putSerializable("disconnectedHistory", this.mDisconnectedHistory);
        String suplAddress = this.mInjector.getNSLocationProviderHelper().getSuplAddress();
        if (suplAddress != null) {
            bundle.putString("supl_hostname", suplAddress);
        }
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.MONITOR_SERVICE_CONNECTED, bundle);
    }

    public final boolean isForegroundService(Notification notification) {
        return (notification.flags & 64) != 0;
    }

    public static /* synthetic */ Set lambda$addForegroundNotification$0(String str) {
        return new HashSet();
    }

    public final boolean addForegroundNotification(String str, String str2) {
        Set set = (Set) this.mForegroundNotificationList.computeIfAbsent(str, new Function() { // from class: com.android.server.location.nsflp.NSLocationMonitor$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Set lambda$addForegroundNotification$0;
                lambda$addForegroundNotification$0 = NSLocationMonitor.lambda$addForegroundNotification$0((String) obj);
                return lambda$addForegroundNotification$0;
            }
        });
        return set.add(str2) && set.size() == 1;
    }

    public final boolean removeForegroundNotification(String str, String str2) {
        Set set = (Set) this.mForegroundNotificationList.get(str);
        return set != null && set.remove(str2) && set.isEmpty();
    }

    public final class NetworkStateListener extends PhoneStateListener {
        public NetworkStateListener(int i) {
            Log.v("NSLocationMonitor", "Constructor, NetworkStateListener subId=" + i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            int i;
            String str;
            if (serviceState == null) {
                Log.e("NSLocationMonitor", "onServiceStateChanged null");
                return;
            }
            int state = serviceState.getState();
            int channelNumber = serviceState.getChannelNumber();
            Log.w("NSLocationMonitor", "onServiceStateChanged, state=" + state + " / channel=" + channelNumber);
            boolean z = true;
            NetworkRegistrationInfo networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
            if (networkRegistrationInfo != null) {
                i = networkRegistrationInfo.getAccessNetworkTechnology();
                str = TelephonyManager.getNetworkTypeName(i);
                r3 = networkRegistrationInfo.getNrState() == 3;
                Log.i("NSLocationMonitor", "networkType[" + i + "]=" + str + " / nrState=" + r3);
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
            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SERVICE_STATE_CHANGED, bundle);
        }
    }

    public void registerDeviceActivityDetector(int i, int i2, boolean z) {
        if (!this.mEnableFeatureDeviceActivity) {
            Log.w("NSLocationMonitor", "Failed registerDeviceActivityDetector");
            return;
        }
        if (this.mDeviceActivityRegistered) {
            if (this.mDeviceActivityDuration == i2 && this.mDeviceActivityMode == i && this.mRequestToUpdate == z) {
                Log.w("NSLocationMonitor", "Already registered same attribute, mode : " + i + ", duration : " + i2 + ", requestToUpdate : " + z);
                return;
            }
            this.mSemContextManager.unregisterListener(this.mSemContextListener, 54);
        }
        try {
            if (this.mSemContextManager.registerListener(this.mSemContextListener, 54)) {
                Log.d("NSLocationMonitor", "Success registerDeviceActivityDetector, mode : " + i + ", duration : " + i2 + ", requestToUpdate : " + z);
                this.mSemContextManager.changeParameters(this.mSemContextListener, 54, new SemContextDeviceActivityDetectorAttribute(i, i2, z));
                this.mDeviceActivityRegistered = true;
                this.mDeviceActivityMode = i;
                this.mDeviceActivityDuration = i2;
                this.mRequestToUpdate = z;
            }
        } catch (RuntimeException e) {
            Log.e("NSLocationMonitor", "Exception is occurred while registerDeviceActivityDetector, " + e.toString());
        }
    }

    public void unregisterDeviceActivityDetector() {
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
            Log.e("NSLocationMonitor", "Exception is occurred while unregisterDeviceActivityDetector, " + e.toString());
        }
        this.mIsStationary = false;
        this.mDeviceActivityRegistered = false;
        this.mDeviceActivityMode = 0;
        this.mDeviceActivityDuration = 0;
        this.mRequestToUpdate = false;
    }

    public void requestToUpdateDeviceActivityDetector() {
        if (!this.mEnableFeatureDeviceActivity) {
            Log.w("NSLocationMonitor", "Failed updateDeviceActivityDetector");
            return;
        }
        try {
            this.mSemContextManager.requestToUpdate(this.mSemContextListener, 54);
        } catch (RuntimeException e) {
            Log.e("NSLocationMonitor", "Exception is occurred while updateDeviceActivityDetector, " + e.toString());
        }
    }

    public boolean setFeatureDeviceActivity(boolean z) {
        if (z) {
            if (this.mSemContextManager == null) {
                SemContextManager semContextManager = (SemContextManager) this.mContext.getSystemService("scontext");
                this.mSemContextManager = semContextManager;
                if (semContextManager == null) {
                    Log.w("NSLocationMonitor", "semContextManager is null");
                    this.mEnableFeatureDeviceActivity = false;
                    return false;
                }
            }
            this.mEnableFeatureDeviceActivity = this.mSemContextManager.isAvailableService(54);
            Log.i("NSLocationMonitor", "setFeatureDeviceActivity, isAvailable, " + this.mEnableFeatureDeviceActivity);
            return this.mEnableFeatureDeviceActivity;
        }
        if (this.mIsStationary) {
            this.mIsStationary = false;
            sendStationaryInfo(false);
        }
        unregisterDeviceActivityDetector();
        Log.w("NSLocationMonitor", "setFeatureDeviceActivity, false");
        this.mEnableFeatureDeviceActivity = false;
        return true;
    }

    public void notifyCrash(long j) {
        this.mCrashCount++;
        this.mCrashTime = j;
    }

    public int getCrashCount() {
        return this.mCrashCount;
    }

    public long getCrashTime() {
        return this.mCrashTime;
    }

    public final void checkSupportAlgorithm(int i) {
        if (i == 4) {
            Log.i("NSLocationMonitor", "Support device_activity_detector old algorithm");
            this.mSupportAlgorithm = ALGORITHM_TYPE.OLD;
        } else if (i == 5) {
            Log.i("NSLocationMonitor", "Support device_activity_detector new algorithm");
            this.mSupportAlgorithm = ALGORITHM_TYPE.NEW;
        } else {
            Log.e("NSLocationMonitor", "Not support device_activity_detector algorithm");
            this.mSupportAlgorithm = ALGORITHM_TYPE.NOT_SUPPORT;
            disableDeviceActivity(DEVICE_ACTIVITY_ERROR_CODE.NOT_SUPPORT);
            return;
        }
        sendSupportAlgorithmType(this.mSupportAlgorithm);
    }

    public final void checkActivityResult(int i, int i2) {
        if (i != 1) {
            if (i2 == 1) {
                if (this.mIsStationary) {
                    sendStationaryInfo(false);
                }
                this.mIsStationary = false;
                return;
            }
            return;
        }
        if (i2 == 1) {
            if (!this.mIsStationary) {
                sendStationaryInfo(true);
            }
            this.mIsStationary = true;
        } else if (i2 == 2) {
            if (this.mIsStationary) {
                sendStationaryInfo(false);
            }
            this.mIsStationary = false;
        }
    }

    public final void sendStationaryInfo(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("stationary", z);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.MOTION_STATE_CHANGED, bundle);
    }

    public final void sendStationaryInfo(int i, int i2, int i3) {
        Bundle bundle = new Bundle();
        bundle.putInt("activity", i);
        bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, i2);
        bundle.putInt("movement", i3);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.MOTION_STATE_CHANGED, bundle);
    }

    public final void disableDeviceActivity(DEVICE_ACTIVITY_ERROR_CODE device_activity_error_code) {
        setFeatureDeviceActivity(false);
        Bundle bundle = new Bundle();
        bundle.putString("code", device_activity_error_code.name());
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.DEVICE_ACTIVITY_ERROR, bundle);
    }

    public final void sendSupportAlgorithmType(ALGORITHM_TYPE algorithm_type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", algorithm_type.ordinal());
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SUPPORT_ALGORITHM_TYPE, bundle);
    }

    public void setMotionPowerSaveMode(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("NSLocationMonitor", "setMotionPowerSaveMode, " + z);
            this.mInjector.getNSLocationProviderHelper().notifyMotionPowerSaveModeChanged(z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setLocationEnabled(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt("pid", Binder.getCallingPid());
        bundle.putInt("uid", Binder.getCallingUid());
        bundle.putBoolean("enabled", z);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SET_LOCATION_ENABLED, bundle);
    }

    public void sendExtraCommandInfo(String str, String str2) {
        Bundle bundle = new Bundle();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        bundle.putString("provider", str);
        bundle.putString(KnoxVpnFirewallHelper.CMD, str2);
        bundle.putInt("pid", callingPid);
        bundle.putInt("uid", callingUid);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SEND_EXTRA_COMMAND, bundle);
    }

    public void updateTestProvider(boolean z, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRegister", z);
        bundle.putString("provider", str);
        bundle.putString("packageName", str2);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.MOCK_PROVIDER_CHANGED, bundle);
    }

    public void onMessageUpdated(Message message) {
        if (message == null) {
            Log.w("NSLocationMonitor", "onMessageUpdated, message is null");
        } else if (message.what == 200) {
            this.mInjector.getNSLocationProviderHelper().onGmsApiRequest(message);
        } else {
            this.mNSConnectionHelper.onMessageUpdated(message);
        }
    }

    public final void onLocationPowerSaveModeChanged(int i) {
        boolean z = true;
        if (i != 1 && i != 2 && i != 4) {
            z = false;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("powerSaveMode", z);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.LOCATION_POWER_SAVE_CHANGED, bundle);
    }

    public final void onDeviceStationaryChanged(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("stationary", z);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.DEVICE_STATIONARY_CHANGED, bundle);
    }

    public final void onDeviceIdleChanged(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("deviceIdle", z);
        this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.DEVICE_IDLE_CHANGED, bundle);
    }

    public boolean isProviderEnabled(String str, int i) {
        if (this.mLocationManagerInternal == null) {
            this.mLocationManagerInternal = (LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class);
        }
        LocationManagerInternal locationManagerInternal = this.mLocationManagerInternal;
        return locationManagerInternal != null && locationManagerInternal.isProviderEnabledForUser(str, i);
    }

    public void writeUtLog(int i, String str, String str2) {
        if (this.mNsUtLogger == null) {
            HandlerThread handlerThread = new HandlerThread("NS_UT_LOGGER_THREAD");
            handlerThread.start();
            this.mNsUtLogger = new NSUtLogger(handlerThread.getLooper());
        }
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.obj = new NSUtLogger.LogInfo(i, str, str2);
        this.mNsUtLogger.sendMessage(obtain);
    }
}
