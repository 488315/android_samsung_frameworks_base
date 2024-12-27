package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.location.ILocationManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.feature.SemCarrierFeature;
import java.util.ArrayList;
import java.util.Objects;

public final class LocationControllerImpl extends BroadcastReceiver implements LocationController, AppOpsController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppOpsController mAppOpsController;
    public boolean mAreActiveLocationRequests;
    public final Handler mBackgroundHandler;
    public final AnonymousClass1 mContentObserver;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final H mHandler;
    public ILocationManager mLocationManager;
    public final PackageManager mPackageManager;
    public final SecureSettings mSecureSettings;
    public boolean mShouldDisplayAllAccesses;
    public boolean mShowSystemAccessesFlag;
    public boolean mShowSystemAccessesSetting;
    public final boolean mSupportChnNlpIcon;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public AppOpItem mActiveAppOpItem = null;
    public final ArrayList mSettingsChangeCallbacks = new ArrayList();

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            if (i == 1) {
                boolean isLocationEnabled$1 = LocationControllerImpl.this.isLocationEnabled$1();
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    try {
                        int size = LocationControllerImpl.this.mSettingsChangeCallbacks.size();
                        while (i2 < size) {
                            ((LocationController.LocationChangeCallback) LocationControllerImpl.this.mSettingsChangeCallbacks.get(i2)).onLocationSettingsChanged(isLocationEnabled$1);
                            i2++;
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i == 2) {
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    try {
                        int size2 = LocationControllerImpl.this.mSettingsChangeCallbacks.size();
                        while (i2 < size2) {
                            ((LocationController.LocationChangeCallback) LocationControllerImpl.this.mSettingsChangeCallbacks.get(i2)).onLocationActiveChanged(LocationControllerImpl.this.mAreActiveLocationRequests);
                            i2++;
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i == 3) {
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    LocationControllerImpl.this.mSettingsChangeCallbacks.add((LocationController.LocationChangeCallback) message.obj);
                }
            } else {
                if (i != 4) {
                    return;
                }
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    LocationControllerImpl.this.mSettingsChangeCallbacks.remove((LocationController.LocationChangeCallback) message.obj);
                }
            }
        }
    }

    enum LocationIndicatorEvent implements UiEventLogger.UiEventEnum {
        LOCATION_INDICATOR_MONITOR_HIGH_POWER(935),
        LOCATION_INDICATOR_SYSTEM_APP(936),
        LOCATION_INDICATOR_NON_SYSTEM_APP(937);

        private final int mId;

        LocationIndicatorEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public LocationControllerImpl(Context context, AppOpsController appOpsController, DeviceConfigProxy deviceConfigProxy, Looper looper, Handler handler, BroadcastDispatcher broadcastDispatcher, BootCompleteCache bootCompleteCache, UserTracker userTracker, PackageManager packageManager, UiEventLogger uiEventLogger, SecureSettings secureSettings) {
        int i;
        this.mContext = context;
        this.mAppOpsController = appOpsController;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mHandler = new H(looper);
        this.mUserTracker = userTracker;
        this.mUiEventLogger = uiEventLogger;
        this.mSecureSettings = secureSettings;
        this.mBackgroundHandler = handler;
        this.mPackageManager = packageManager;
        try {
            i = Integer.parseInt(SystemProperties.get("persist.sys.gps.dds.subId", "0"));
        } catch (NumberFormatException unused) {
            Log.w("LocationControllerImpl", "Sim slot property has wrong value, set 0");
            i = 0;
        }
        String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_GPS_ConfigAgpsSetting", "", false);
        string = (string == null || string.length() == 0) ? SystemProperties.get("ro.csc.sales_code") : string;
        boolean z = SystemProperties.getInt("ro.product.first_api_level", 0) >= 34 && ("CHN".equals(string) || "CHC".equals(string) || "CHU".equals(string) || "CTC".equals(string) || "CHM".equals(string));
        this.mSupportChnNlpIcon = z;
        this.mShouldDisplayAllAccesses = this.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_small_enabled", false) || this.mSupportChnNlpIcon;
        this.mShowSystemAccessesFlag = this.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_show_system", false) || z;
        this.mShowSystemAccessesSetting = this.mSecureSettings.getIntForUser("locationShowSystemOps", 0, -2) == 1;
        this.mSecureSettings.registerContentObserverForUserSync("locationShowSystemOps", new ContentObserver(this.mBackgroundHandler) { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                int i2 = LocationControllerImpl.$r8$clinit;
                locationControllerImpl.mShowSystemAccessesSetting = locationControllerImpl.mSecureSettings.getIntForUser("locationShowSystemOps", 0, -2) == 1;
            }
        }, -1);
        DeviceConfigProxy deviceConfigProxy2 = this.mDeviceConfigProxy;
        Objects.requireNonNull(handler);
        deviceConfigProxy2.addOnPropertiesChangedListener("privacy", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                locationControllerImpl.mShouldDisplayAllAccesses = locationControllerImpl.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_small_enabled", false) || locationControllerImpl.mSupportChnNlpIcon;
                locationControllerImpl.mShowSystemAccessesFlag = locationControllerImpl.mSecureSettings.getIntForUser("locationShowSystemOps", 0, -2) == 1;
                locationControllerImpl.updateActiveLocationRequests();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.location.HIGH_POWER_REQUEST_CHANGE");
        intentFilter.addAction("android.location.MODE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(this, intentFilter, this.mHandler, UserHandle.ALL);
        ((AppOpsControllerImpl) this.mAppOpsController).addCallback(new int[]{0, 1, 42}, this);
        handler.post(new LocationControllerImpl$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        LocationController.LocationChangeCallback locationChangeCallback = (LocationController.LocationChangeCallback) obj;
        synchronized (this.mSettingsChangeCallbacks) {
            this.mHandler.obtainMessage(3, locationChangeCallback).sendToTarget();
        }
        this.mHandler.sendEmptyMessage(1);
    }

    public boolean areActiveHighPowerLocationRequests() {
        ArrayList arrayList = (ArrayList) ((AppOpsControllerImpl) this.mAppOpsController).getActiveAppOps(false);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((AppOpItem) arrayList.get(i)).mCode == 42) {
                this.mActiveAppOpItem = (AppOpItem) arrayList.get(i);
                return true;
            }
        }
        this.mActiveAppOpItem = null;
        return false;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void areActiveLocationRequests() {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.LocationControllerImpl.areActiveLocationRequests():void");
    }

    public final boolean isLocationEnabled$1() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "location_mode", 0, ((UserTrackerImpl) this.mUserTracker).getUserId());
        return intForUser == 3 || intForUser == 1;
    }

    @Override // com.android.systemui.appops.AppOpsController.Callback
    public final void onActiveStateChanged(String str, int i, int i2, boolean z) {
        updateActiveLocationRequests();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("LocationControllerImpl", "onReceive() = " + action);
        if ("android.location.HIGH_POWER_REQUEST_CHANGE".equals(action)) {
            updateActiveLocationRequests();
        } else if ("android.location.MODE_CHANGED".equals(action)) {
            this.mHandler.sendEmptyMessage(1);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        LocationController.LocationChangeCallback locationChangeCallback = (LocationController.LocationChangeCallback) obj;
        synchronized (this.mSettingsChangeCallbacks) {
            this.mHandler.obtainMessage(4, locationChangeCallback).sendToTarget();
        }
    }

    public final boolean setLocationEnabled(boolean z) {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_share_location", UserHandle.of(userId))) {
            return false;
        }
        Context context = this.mContext;
        Settings.Secure.putIntForUser(context.getContentResolver(), "location_changer", 2, userId);
        ((LocationManager) context.getSystemService(LocationManager.class)).setLocationEnabledForUser(z, UserHandle.of(userId));
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateActiveLocationRequests() {
        /*
            r5 = this;
            com.android.systemui.appops.AppOpItem r0 = r5.mActiveAppOpItem
            boolean r1 = r5.mShouldDisplayAllAccesses
            if (r1 == 0) goto L12
            android.os.Handler r1 = r5.mBackgroundHandler
            com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda0 r2 = new com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda0
            r3 = 0
            r2.<init>(r5, r3)
            r1.post(r2)
            goto L2e
        L12:
            boolean r1 = r5.mAreActiveLocationRequests
            boolean r2 = r5.areActiveHighPowerLocationRequests()
            r5.mAreActiveLocationRequests = r2
            if (r2 == r1) goto L2e
            com.android.systemui.statusbar.policy.LocationControllerImpl$H r1 = r5.mHandler
            r2 = 2
            r1.sendEmptyMessage(r2)
            boolean r1 = r5.mAreActiveLocationRequests
            if (r1 == 0) goto L36
            com.android.internal.logging.UiEventLogger r1 = r5.mUiEventLogger
            com.android.systemui.statusbar.policy.LocationControllerImpl$LocationIndicatorEvent r2 = com.android.systemui.statusbar.policy.LocationControllerImpl.LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER
            r1.log(r2)
            goto L36
        L2e:
            boolean r1 = r5.mAreActiveLocationRequests
            if (r1 == 0) goto La3
            com.android.systemui.appops.AppOpItem r1 = r5.mActiveAppOpItem
            if (r0 == r1) goto La3
        L36:
            java.lang.String r1 = "LocationControllerImpl"
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            java.lang.String r3 = "icon"
            boolean r4 = r5.mAreActiveLocationRequests
            r2.putBoolean(r3, r4)
            com.android.systemui.appops.AppOpItem r3 = r5.mActiveAppOpItem
            if (r3 == 0) goto L63
            if (r0 == 0) goto L51
            java.lang.String r0 = "onlyItemChanged"
            r3 = 1
            r2.putBoolean(r0, r3)
        L51:
            com.android.systemui.appops.AppOpItem r0 = r5.mActiveAppOpItem
            int r0 = r0.mUid
            java.lang.String r3 = "activeAppOpUid"
            r2.putInt(r3, r0)
            com.android.systemui.appops.AppOpItem r0 = r5.mActiveAppOpItem
            java.lang.String r0 = r0.mPackageName
            java.lang.String r3 = "activeAppOpPackageName"
            r2.putString(r3, r0)
        L63:
            android.os.Message r0 = android.os.Message.obtain()
            r3 = 204(0xcc, float:2.86E-43)
            r0.what = r3
            r0.obj = r2
            android.location.ILocationManager r2 = r5.mLocationManager     // Catch: android.os.RemoteException -> L7e
            if (r2 != 0) goto L80
            java.lang.String r2 = "location"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: android.os.RemoteException -> L7e
            android.location.ILocationManager r2 = android.location.ILocationManager.Stub.asInterface(r2)     // Catch: android.os.RemoteException -> L7e
            r5.mLocationManager = r2     // Catch: android.os.RemoteException -> L7e
            goto L80
        L7e:
            r5 = move-exception
            goto L8e
        L80:
            android.location.ILocationManager r5 = r5.mLocationManager     // Catch: android.os.RemoteException -> L7e
            if (r5 != 0) goto L8a
            java.lang.String r5 = "Failed to get Location Manager"
            android.util.Log.w(r1, r5)     // Catch: android.os.RemoteException -> L7e
            goto La3
        L8a:
            r5.notifyNSFLP(r0)     // Catch: android.os.RemoteException -> L7e
            goto La3
        L8e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to send nsflp message, "
            r0.<init>(r2)
            java.lang.String r5 = r5.toString()
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            android.util.Log.w(r1, r5)
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.LocationControllerImpl.updateActiveLocationRequests():void");
    }
}
