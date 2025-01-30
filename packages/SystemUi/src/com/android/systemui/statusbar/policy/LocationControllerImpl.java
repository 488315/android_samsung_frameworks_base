package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
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
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.feature.SemCarrierFeature;
import com.sec.ims.configuration.DATA;
import com.sec.ims.gls.GlsIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LocationControllerImpl extends BroadcastReceiver implements LocationController, AppOpsController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppOpsController mAppOpsController;
    public boolean mAreActiveLocationRequests;
    public final Handler mBackgroundHandler;
    public final C34111 mContentObserver;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final HandlerC3412H mHandler;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.LocationControllerImpl$H */
    public final class HandlerC3412H extends Handler {
        public HandlerC3412H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                final boolean isLocationEnabled = LocationControllerImpl.this.isLocationEnabled();
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    Utils.safeForeach(LocationControllerImpl.this.mSettingsChangeCallbacks, new Consumer() { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl$H$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((LocationController.LocationChangeCallback) obj).onLocationSettingsChanged();
                        }
                    });
                }
                return;
            }
            if (i == 2) {
                synchronized (LocationControllerImpl.this.mSettingsChangeCallbacks) {
                    Utils.safeForeach(LocationControllerImpl.this.mSettingsChangeCallbacks, new Consumer() { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl$H$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            boolean z = LocationControllerImpl.this.mAreActiveLocationRequests;
                            ((LocationController.LocationChangeCallback) obj).onLocationActiveChanged();
                        }
                    });
                }
            } else if (i == 3) {
                LocationControllerImpl.this.mSettingsChangeCallbacks.add((LocationController.LocationChangeCallback) message.obj);
            } else {
                if (i != 4) {
                    return;
                }
                LocationControllerImpl.this.mSettingsChangeCallbacks.remove((LocationController.LocationChangeCallback) message.obj);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    /* JADX WARN: Type inference failed for: r2v19, types: [android.database.ContentObserver, com.android.systemui.statusbar.policy.LocationControllerImpl$1] */
    public LocationControllerImpl(Context context, AppOpsController appOpsController, DeviceConfigProxy deviceConfigProxy, Looper looper, Handler handler, BroadcastDispatcher broadcastDispatcher, BootCompleteCache bootCompleteCache, UserTracker userTracker, PackageManager packageManager, UiEventLogger uiEventLogger, SecureSettings secureSettings) {
        int i;
        this.mContext = context;
        this.mAppOpsController = appOpsController;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mHandler = new HandlerC3412H(looper);
        this.mUserTracker = userTracker;
        this.mUiEventLogger = uiEventLogger;
        this.mSecureSettings = secureSettings;
        this.mBackgroundHandler = handler;
        this.mPackageManager = packageManager;
        try {
            i = Integer.parseInt(SystemProperties.get("persist.sys.gps.dds.subId", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
        } catch (NumberFormatException unused) {
            Log.w("LocationControllerImpl", "Sim slot property has wrong value, set 0");
            i = 0;
        }
        String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_GPS_ConfigAgpsSetting", "", false);
        string = (string == null || string.length() == 0) ? SystemProperties.get("ro.csc.sales_code") : string;
        boolean z = SystemProperties.getInt("ro.product.first_api_level", 0) >= 34 && ("CHN".equals(string) || "CHC".equals(string) || "CHU".equals(string) || "CTC".equals(string) || "CHM".equals(string));
        this.mSupportChnNlpIcon = z;
        this.mDeviceConfigProxy.getClass();
        this.mShouldDisplayAllAccesses = DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false) || z;
        this.mDeviceConfigProxy.getClass();
        this.mShowSystemAccessesFlag = DeviceConfig.getBoolean("privacy", "location_indicators_show_system", false);
        this.mShowSystemAccessesSetting = this.mSecureSettings.getIntForUser(0, -2, "locationShowSystemOps") == 1;
        ?? r2 = new ContentObserver(this.mBackgroundHandler) { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                int i2 = LocationControllerImpl.$r8$clinit;
                locationControllerImpl.mShowSystemAccessesSetting = locationControllerImpl.mSecureSettings.getIntForUser(0, -2, "locationShowSystemOps") == 1;
            }
        };
        this.mContentObserver = r2;
        this.mSecureSettings.registerContentObserverForUser("locationShowSystemOps", r2, -1);
        DeviceConfigProxy deviceConfigProxy2 = this.mDeviceConfigProxy;
        Objects.requireNonNull(handler);
        MediaRoute2Provider$$ExternalSyntheticLambda0 mediaRoute2Provider$$ExternalSyntheticLambda0 = new MediaRoute2Provider$$ExternalSyntheticLambda0(handler);
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                locationControllerImpl.mDeviceConfigProxy.getClass();
                locationControllerImpl.mShouldDisplayAllAccesses = DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false) || locationControllerImpl.mSupportChnNlpIcon;
                locationControllerImpl.mShowSystemAccessesFlag = locationControllerImpl.mSecureSettings.getIntForUser(0, -2, "locationShowSystemOps") == 1;
                locationControllerImpl.updateActiveLocationRequests();
            }
        };
        deviceConfigProxy2.getClass();
        DeviceConfig.addOnPropertiesChangedListener("privacy", mediaRoute2Provider$$ExternalSyntheticLambda0, onPropertiesChangedListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.location.HIGH_POWER_REQUEST_CHANGE");
        intentFilter.addAction("android.location.MODE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(this, intentFilter, this.mHandler, UserHandle.ALL);
        ((AppOpsControllerImpl) this.mAppOpsController).addCallback(new int[]{0, 1, 42}, this);
        handler.post(new LocationControllerImpl$$ExternalSyntheticLambda1(this, 0));
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void areActiveLocationRequests() {
        boolean z;
        if (this.mShouldDisplayAllAccesses) {
            boolean z2 = this.mAreActiveLocationRequests;
            int i = 1;
            int i2 = 0;
            boolean z3 = this.mShowSystemAccessesFlag || this.mShowSystemAccessesSetting;
            List activeAppOps = ((AppOpsControllerImpl) this.mAppOpsController).getActiveAppOps(false);
            List userProfiles = ((UserTrackerImpl) this.mUserTracker).getUserProfiles();
            ArrayList arrayList = (ArrayList) activeAppOps;
            int size = arrayList.size();
            int i3 = 0;
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            while (i3 < size) {
                if (((AppOpItem) arrayList.get(i3)).mCode == i || ((AppOpItem) arrayList.get(i3)).mCode == 0) {
                    AppOpItem appOpItem = (AppOpItem) arrayList.get(i3);
                    String opToPermission = AppOpsManager.opToPermission(appOpItem.mCode);
                    int i4 = appOpItem.mUid;
                    UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i4);
                    int size2 = userProfiles.size();
                    int i5 = i2;
                    while (i2 < size2) {
                        int i6 = size2;
                        if (((UserInfo) userProfiles.get(i2)).getUserHandle().equals(userHandleForUid)) {
                            i5 = 1;
                        }
                        i2++;
                        size2 = i6;
                    }
                    if (i5 != 0) {
                        PackageManager packageManager = this.mPackageManager;
                        String str = appOpItem.mPackageName;
                        int permissionFlags = packageManager.getPermissionFlags(opToPermission, str, userHandleForUid);
                        if (PermissionChecker.checkPermissionForPreflight(this.mContext, opToPermission, -1, i4, str) != 0 ? (permissionFlags & 512) != 0 : (permissionFlags & 256) != 0) {
                            z = false;
                            if (z) {
                                z6 = true;
                            } else {
                                z5 = true;
                            }
                            z4 = (z3 && !z4 && z) ? false : true;
                        }
                    }
                    z = true;
                    if (z) {
                    }
                    if (z3) {
                    }
                }
                i3++;
                i = 1;
                i2 = 0;
            }
            boolean areActiveHighPowerLocationRequests = areActiveHighPowerLocationRequests();
            this.mAreActiveLocationRequests = z4;
            if (z4 != z2) {
                this.mHandler.sendEmptyMessage(2);
            }
            if (z2) {
                return;
            }
            if (areActiveHighPowerLocationRequests || z5 || z6) {
                if (areActiveHighPowerLocationRequests) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER);
                }
                if (z5) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_SYSTEM_APP);
                }
                if (z6) {
                    this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_NON_SYSTEM_APP);
                }
            }
        }
    }

    public final boolean isLocationEnabled() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "location_mode", 0, ActivityManager.getCurrentUser());
        return intForUser == 3 || intForUser == 1;
    }

    @Override // com.android.systemui.appops.AppOpsController.Callback
    public final void onActiveStateChanged(boolean z, String str, int i, int i2) {
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0074 A[Catch: RemoteException -> 0x008e, TryCatch #0 {RemoteException -> 0x008e, blocks: (B:20:0x0070, B:22:0x0074, B:23:0x0080, B:25:0x0084, B:27:0x008a), top: B:19:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084 A[Catch: RemoteException -> 0x008e, TryCatch #0 {RemoteException -> 0x008e, blocks: (B:20:0x0070, B:22:0x0074, B:23:0x0080, B:25:0x0084, B:27:0x008a), top: B:19:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a A[Catch: RemoteException -> 0x008e, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x008e, blocks: (B:20:0x0070, B:22:0x0074, B:23:0x0080, B:25:0x0084, B:27:0x008a), top: B:19:0x0070 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateActiveLocationRequests() {
        boolean z;
        ILocationManager iLocationManager;
        AppOpItem appOpItem = this.mActiveAppOpItem;
        try {
            if (this.mShouldDisplayAllAccesses) {
                this.mBackgroundHandler.post(new LocationControllerImpl$$ExternalSyntheticLambda1(this, 1));
            } else {
                boolean z2 = this.mAreActiveLocationRequests;
                boolean areActiveHighPowerLocationRequests = areActiveHighPowerLocationRequests();
                this.mAreActiveLocationRequests = areActiveHighPowerLocationRequests;
                if (areActiveHighPowerLocationRequests != z2) {
                    this.mHandler.sendEmptyMessage(2);
                    if (this.mAreActiveLocationRequests) {
                        this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER);
                    }
                    z = true;
                    if (!z || (this.mAreActiveLocationRequests && appOpItem != this.mActiveAppOpItem)) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("icon", this.mAreActiveLocationRequests);
                        if (this.mActiveAppOpItem != null) {
                            if (appOpItem != null) {
                                bundle.putBoolean("onlyItemChanged", true);
                            }
                            bundle.putInt("activeAppOpUid", this.mActiveAppOpItem.mUid);
                            bundle.putString("activeAppOpPackageName", this.mActiveAppOpItem.mPackageName);
                        }
                        Message obtain = Message.obtain();
                        obtain.what = 204;
                        obtain.obj = bundle;
                        if (this.mLocationManager == null) {
                            this.mLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService(GlsIntent.Extras.EXTRA_LOCATION));
                        }
                        iLocationManager = this.mLocationManager;
                        if (iLocationManager != null) {
                            Log.w("LocationControllerImpl", "Failed to get Location Manager");
                            return;
                        } else {
                            iLocationManager.notifyNSFLP(obtain);
                            return;
                        }
                    }
                    return;
                }
            }
            if (this.mLocationManager == null) {
            }
            iLocationManager = this.mLocationManager;
            if (iLocationManager != null) {
            }
        } catch (RemoteException e) {
            Log.w("LocationControllerImpl", "Failed to send nsflp message, " + e.toString());
            return;
        }
        z = false;
        if (z) {
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("icon", this.mAreActiveLocationRequests);
        if (this.mActiveAppOpItem != null) {
        }
        Message obtain2 = Message.obtain();
        obtain2.what = 204;
        obtain2.obj = bundle2;
    }
}
