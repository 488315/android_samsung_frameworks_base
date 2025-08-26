package com.android.systemui.statusbar.policy;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.location.ILocationManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda1;
import com.android.systemui.BasicRune;
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
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class LocationControllerImpl extends BroadcastReceiver implements LocationController, AppOpsController.Callback {
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
                boolean zIsLocationEnabled$1 = LocationControllerImpl.this.isLocationEnabled$1();
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    try {
                        int size = LocationControllerImpl.this.mSettingsChangeCallbacks.size();
                        while (i2 < size) {
                            ((LocationController.LocationChangeCallback) LocationControllerImpl.this.mSettingsChangeCallbacks.get(i2)).onLocationSettingsChanged(zIsLocationEnabled$1);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v22, types: [android.database.ContentObserver, com.android.systemui.statusbar.policy.LocationControllerImpl$1] */
    public LocationControllerImpl(Context context, AppOpsController appOpsController, DeviceConfigProxy deviceConfigProxy, Looper looper, Handler handler, BroadcastDispatcher broadcastDispatcher, BootCompleteCache bootCompleteCache, UserTracker userTracker, PackageManager packageManager, UiEventLogger uiEventLogger, SecureSettings secureSettings) throws NumberFormatException {
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
        this.mSupportChnNlpIcon = SystemProperties.getInt("ro.product.first_api_level", 0) >= 34 && ("CHN".equals(string) || "CHC".equals(string) || "CHU".equals(string) || "CTC".equals(string) || "CHM".equals(string));
        this.mShouldDisplayAllAccesses = this.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_small_enabled", false) || this.mSupportChnNlpIcon || BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION;
        this.mShowSystemAccessesFlag = this.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_show_system", false) || BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION;
        this.mShowSystemAccessesSetting = this.mSecureSettings.getIntForUser("locationShowSystemOps", 0, -2) == 1;
        ?? r2 = new ContentObserver(this.mBackgroundHandler) { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                int i2 = LocationControllerImpl.$r8$clinit;
                locationControllerImpl.mShowSystemAccessesSetting = locationControllerImpl.mSecureSettings.getIntForUser("locationShowSystemOps", 0, -2) == 1;
            }
        };
        this.mContentObserver = r2;
        this.mSecureSettings.registerContentObserverForUserSync("locationShowSystemOps", (ContentObserver) r2, -1);
        DeviceConfigProxy deviceConfigProxy2 = this.mDeviceConfigProxy;
        Objects.requireNonNull(handler);
        deviceConfigProxy2.addOnPropertiesChangedListener("privacy", new MobileStatusTracker$$ExternalSyntheticLambda1(handler), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LocationControllerImpl locationControllerImpl = this.f$0;
                int i2 = LocationControllerImpl.$r8$clinit;
                locationControllerImpl.mShouldDisplayAllAccesses = locationControllerImpl.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_small_enabled", false) || locationControllerImpl.mSupportChnNlpIcon || BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION;
                locationControllerImpl.mShowSystemAccessesFlag = locationControllerImpl.mDeviceConfigProxy.getBoolean("privacy", "location_indicators_show_system", false) || BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION;
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void areActiveLocationRequests() {
        boolean z;
        boolean z2;
        if (this.mShouldDisplayAllAccesses) {
            boolean z3 = this.mAreActiveLocationRequests;
            boolean z4 = true;
            boolean z5 = this.mShowSystemAccessesFlag || this.mShowSystemAccessesSetting;
            List activeAppOps = ((AppOpsControllerImpl) this.mAppOpsController).getActiveAppOps(false);
            List userProfiles = ((UserTrackerImpl) this.mUserTracker).getUserProfiles();
            ArrayList arrayList = (ArrayList) activeAppOps;
            int size = arrayList.size();
            int i = 0;
            boolean z6 = false;
            boolean z7 = false;
            boolean z8 = false;
            while (i < size) {
                if (((AppOpItem) arrayList.get(i)).mCode == z4 || ((AppOpItem) arrayList.get(i)).mCode == 0) {
                    AppOpItem appOpItem = (AppOpItem) arrayList.get(i);
                    String strOpToPermission = AppOpsManager.opToPermission(appOpItem.mCode);
                    int i2 = appOpItem.mUid;
                    UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i2);
                    int size2 = userProfiles.size();
                    int i3 = 0;
                    boolean z9 = false;
                    while (i3 < size2) {
                        boolean z10 = z5;
                        if (((UserInfo) userProfiles.get(i3)).getUserHandle().equals(userHandleForUid)) {
                            z9 = true;
                        }
                        i3++;
                        z5 = z10;
                    }
                    z = z5;
                    if (z9) {
                        PackageManager packageManager = this.mPackageManager;
                        String str = appOpItem.mPackageName;
                        int permissionFlags = packageManager.getPermissionFlags(strOpToPermission, str, userHandleForUid);
                        if (PermissionChecker.checkPermissionForPreflight(this.mContext, strOpToPermission, -1, i2, str) != 0 ? (permissionFlags & 512) != 0 : (permissionFlags & 256) != 0) {
                            z2 = false;
                        }
                        if (z2) {
                        }
                        if (z) {
                        }
                    } else {
                        z2 = true;
                        if (z2) {
                            z8 = true;
                        } else {
                            z7 = true;
                        }
                        z6 = (z && !z6 && z2) ? false : true;
                    }
                } else if ((this.mSupportChnNlpIcon || BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION) && ((AppOpItem) arrayList.get(i)).mCode == 42) {
                    z = z5;
                    z6 = z4;
                } else {
                    z = z5;
                }
                i++;
                z5 = z;
                z4 = true;
            }
            boolean zAreActiveHighPowerLocationRequests = areActiveHighPowerLocationRequests();
            this.mAreActiveLocationRequests = z6;
            if (z6 != z3) {
                this.mHandler.sendEmptyMessage(2);
            }
            if (z3) {
                return;
            }
            if (zAreActiveHighPowerLocationRequests || z7 || z8) {
                if (zAreActiveHighPowerLocationRequests) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER);
                }
                if (z7) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_SYSTEM_APP);
                }
                if (z8) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_NON_SYSTEM_APP);
                }
            }
        }
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0070 A[Catch: RemoteException -> 0x007d, TryCatch #0 {RemoteException -> 0x007d, blocks: (B:20:0x006c, B:22:0x0070, B:25:0x007f, B:27:0x0083, B:29:0x0089), top: B:33:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0083 A[Catch: RemoteException -> 0x007d, TryCatch #0 {RemoteException -> 0x007d, blocks: (B:20:0x006c, B:22:0x0070, B:25:0x007f, B:27:0x0083, B:29:0x0089), top: B:33:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0089 A[Catch: RemoteException -> 0x007d, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x007d, blocks: (B:20:0x006c, B:22:0x0070, B:25:0x007f, B:27:0x0083, B:29:0x0089), top: B:33:0x006c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateActiveLocationRequests() {
        ILocationManager iLocationManager;
        AppOpItem appOpItem = this.mActiveAppOpItem;
        try {
            if (!this.mShouldDisplayAllAccesses) {
                boolean z = this.mAreActiveLocationRequests;
                boolean zAreActiveHighPowerLocationRequests = areActiveHighPowerLocationRequests();
                this.mAreActiveLocationRequests = zAreActiveHighPowerLocationRequests;
                if (zAreActiveHighPowerLocationRequests != z) {
                    this.mHandler.sendEmptyMessage(2);
                    if (this.mAreActiveLocationRequests) {
                        this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("icon", this.mAreActiveLocationRequests);
                if (this.mActiveAppOpItem != null) {
                    if (appOpItem != null) {
                        bundle.putBoolean("onlyItemChanged", true);
                    }
                    bundle.putInt("activeAppOpUid", this.mActiveAppOpItem.mUid);
                    bundle.putString("activeAppOpPackageName", this.mActiveAppOpItem.mPackageName);
                }
                Message messageObtain = Message.obtain();
                messageObtain.what = 202;
                messageObtain.obj = bundle;
                if (this.mLocationManager == null) {
                    this.mLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
                }
                iLocationManager = this.mLocationManager;
                if (iLocationManager != null) {
                    Log.w("LocationControllerImpl", "Failed to get Location Manager");
                    return;
                } else {
                    iLocationManager.notifyNSFLP(messageObtain);
                    return;
                }
            }
            this.mBackgroundHandler.post(new LocationControllerImpl$$ExternalSyntheticLambda0(this, 0));
            if (this.mLocationManager == null) {
            }
            iLocationManager = this.mLocationManager;
            if (iLocationManager != null) {
            }
        } catch (RemoteException e) {
            Log.w("LocationControllerImpl", "Failed to send nsflp message, " + e.toString());
            return;
        }
        if (!this.mAreActiveLocationRequests || appOpItem == this.mActiveAppOpItem) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("icon", this.mAreActiveLocationRequests);
        if (this.mActiveAppOpItem != null) {
        }
        Message messageObtain2 = Message.obtain();
        messageObtain2.what = 202;
        messageObtain2.obj = bundle2;
    }
}
