package com.android.server.location.gnss;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.location.GpsNetInitiatedHandler;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.sec.CarrierConfig;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class GnssVisibilityControl {
    public static final String[] NO_LOCATION_ENABLED_PROXY_APPS = new String[0];
    public static boolean mIsNfwLocationAccessProxyAppsUpdated;
    public final AppOpsManager mAppOps;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsGpsEnabled;
    public final GpsNetInitiatedHandler mNiHandler;
    public final PackageManager mPackageManager;
    public final PowerManager.WakeLock mWakeLock;
    public final ArrayMap mProxyAppsState = new ArrayMap(5);
    public final GnssVisibilityControl$$ExternalSyntheticLambda3 mOnPermissionsChangedListener =
            new PackageManager.OnPermissionsChangedListener() { // from class:
                // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda3
                public final void onPermissionsChanged(final int i) {
                    final GnssVisibilityControl gnssVisibilityControl = GnssVisibilityControl.this;
                    gnssVisibilityControl.getClass();
                    gnssVisibilityControl.runOnHandler(
                            new Runnable() { // from class:
                                // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GnssVisibilityControl gnssVisibilityControl2 =
                                            GnssVisibilityControl.this;
                                    int i2 = i;
                                    if (gnssVisibilityControl2.mProxyAppsState.isEmpty()) {
                                        return;
                                    }
                                    for (Map.Entry entry :
                                            gnssVisibilityControl2.mProxyAppsState.entrySet()) {
                                        String str = (String) entry.getKey();
                                        ApplicationInfo proxyAppInfo =
                                                gnssVisibilityControl2.getProxyAppInfo(str);
                                        if (proxyAppInfo != null && proxyAppInfo.uid == i2) {
                                            boolean shouldEnableLocationPermissionInGnssHal =
                                                    gnssVisibilityControl2
                                                            .shouldEnableLocationPermissionInGnssHal(
                                                                    str);
                                            GnssVisibilityControl.ProxyAppState proxyAppState =
                                                    (GnssVisibilityControl.ProxyAppState)
                                                            entry.getValue();
                                            if (shouldEnableLocationPermissionInGnssHal
                                                    != proxyAppState.mHasLocationPermission) {
                                                Log.i(
                                                        "GnssVisibilityControl",
                                                        "Proxy app "
                                                                + str
                                                                + " location permission changed."
                                                                + " IsLocationPermissionEnabled: "
                                                                + shouldEnableLocationPermissionInGnssHal);
                                                proxyAppState.mHasLocationPermission =
                                                        shouldEnableLocationPermissionInGnssHal;
                                                gnssVisibilityControl2
                                                        .updateNfwLocationAccessProxyAppsInGnssHal();
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                }
                            });
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NfwNotification {
        public final boolean mInEmergencyMode;
        public final boolean mIsCachedLocation;
        public final String mOtherProtocolStackName;
        public final byte mProtocolStack;
        public final String mProxyAppPackageName;
        public final byte mRequestor;
        public final String mRequestorId;
        public final byte mResponseType;

        public NfwNotification(
                String str,
                byte b,
                String str2,
                byte b2,
                String str3,
                byte b3,
                boolean z,
                boolean z2) {
            this.mProxyAppPackageName = str;
            this.mProtocolStack = b;
            this.mOtherProtocolStackName = str2;
            this.mRequestor = b2;
            this.mRequestorId = str3;
            this.mResponseType = b3;
            this.mInEmergencyMode = z;
            this.mIsCachedLocation = z2;
        }

        public final String toString() {
            Byte valueOf = Byte.valueOf(this.mProtocolStack);
            Byte valueOf2 = Byte.valueOf(this.mRequestor);
            byte b = this.mResponseType;
            return String.format(
                    "{proxyAppPackageName: %s, protocolStack: %d, otherProtocolStackName: %s,"
                        + " requestor: %d, requestorId: %s, responseType: %s, inEmergencyMode: %b,"
                        + " isCachedLocation: %b}",
                    this.mProxyAppPackageName,
                    valueOf,
                    this.mOtherProtocolStackName,
                    valueOf2,
                    this.mRequestorId,
                    b != 0
                            ? b != 1
                                    ? b != 2 ? "<Unknown>" : "ACCEPTED_LOCATION_PROVIDED"
                                    : "ACCEPTED_NO_LOCATION_PROVIDED"
                            : "REJECTED",
                    Boolean.valueOf(this.mInEmergencyMode),
                    Boolean.valueOf(this.mIsCachedLocation));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProxyAppState {
        public boolean mHasLocationPermission;
        public boolean mIsLocationIconOn;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda3] */
    public GnssVisibilityControl(
            Context context, Looper looper, GpsNetInitiatedHandler gpsNetInitiatedHandler) {
        this.mContext = context;
        this.mWakeLock =
                ((PowerManager) context.getSystemService("power"))
                        .newWakeLock(1, "GnssVisibilityControl");
        this.mHandler = new Handler(looper);
        this.mNiHandler = gpsNetInitiatedHandler;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mPackageManager = context.getPackageManager();
        runOnHandler(
                new Runnable() { // from class:
                    // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        final GnssVisibilityControl gnssVisibilityControl =
                                GnssVisibilityControl.this;
                        gnssVisibilityControl.getClass();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                        intentFilter.addDataScheme("package");
                        gnssVisibilityControl.mContext.registerReceiverAsUser(
                                new BroadcastReceiver() { // from class:
                                    // com.android.server.location.gnss.GnssVisibilityControl.1
                                    @Override // android.content.BroadcastReceiver
                                    public final void onReceive(Context context2, Intent intent) {
                                        String action = intent.getAction();
                                        if (action == null) {}
                                        switch (action) {
                                            case "android.intent.action.PACKAGE_REPLACED":
                                            case "android.intent.action.PACKAGE_CHANGED":
                                            case "android.intent.action.PACKAGE_REMOVED":
                                            case "android.intent.action.PACKAGE_ADDED":
                                                String encodedSchemeSpecificPart =
                                                        intent.getData()
                                                                .getEncodedSchemeSpecificPart();
                                                GnssVisibilityControl gnssVisibilityControl2 =
                                                        GnssVisibilityControl.this;
                                                ProxyAppState proxyAppState =
                                                        (ProxyAppState)
                                                                gnssVisibilityControl2
                                                                        .mProxyAppsState.get(
                                                                        encodedSchemeSpecificPart);
                                                if (proxyAppState != null) {
                                                    Log.d(
                                                            "GnssVisibilityControl",
                                                            "Proxy app "
                                                                    + encodedSchemeSpecificPart
                                                                    + " package changed: "
                                                                    + action);
                                                    boolean
                                                            shouldEnableLocationPermissionInGnssHal =
                                                                    gnssVisibilityControl2
                                                                            .shouldEnableLocationPermissionInGnssHal(
                                                                                    encodedSchemeSpecificPart);
                                                    if (proxyAppState.mHasLocationPermission
                                                            != shouldEnableLocationPermissionInGnssHal) {
                                                        Log.i(
                                                                "GnssVisibilityControl",
                                                                "Proxy app "
                                                                        + encodedSchemeSpecificPart
                                                                        + " location permission"
                                                                        + " changed."
                                                                        + " IsLocationPermissionEnabled:"
                                                                        + " "
                                                                        + shouldEnableLocationPermissionInGnssHal);
                                                        proxyAppState.mHasLocationPermission =
                                                                shouldEnableLocationPermissionInGnssHal;
                                                        gnssVisibilityControl2
                                                                .updateNfwLocationAccessProxyAppsInGnssHal();
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                    }
                                },
                                UserHandle.ALL,
                                intentFilter,
                                null,
                                gnssVisibilityControl.mHandler);
                    }
                });
    }

    private native boolean native_enable_nfw_location_access(String[] strArr);

    public final void clearLocationIcon(ProxyAppState proxyAppState, int i, String str) {
        updateLocationIcon(i, str, false);
        if (proxyAppState != null) {
            proxyAppState.mIsLocationIconOn = false;
        }
        Log.d(
                "GnssVisibilityControl",
                "Location icon off. Uid: " + i + ", proxyAppPkgName: " + str);
    }

    public final String[] getLocationPermissionEnabledProxyApps() {
        Iterator it = this.mProxyAppsState.values().iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            if (((ProxyAppState) it.next()).mHasLocationPermission) {
                i2++;
            }
        }
        String[] strArr = new String[i2];
        for (Map.Entry entry : this.mProxyAppsState.entrySet()) {
            String str = (String) entry.getKey();
            if (((ProxyAppState) entry.getValue()).mHasLocationPermission) {
                strArr[i] = str;
                i++;
            }
        }
        return strArr;
    }

    public final ApplicationInfo getProxyAppInfo(String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Proxy app ", str, " is not found.", "GnssVisibilityControl");
            return null;
        }
    }

    public final void resetProxyAppsState() {
        for (Map.Entry entry : this.mProxyAppsState.entrySet()) {
            ProxyAppState proxyAppState = (ProxyAppState) entry.getValue();
            if (proxyAppState.mIsLocationIconOn) {
                this.mHandler.removeCallbacksAndMessages(proxyAppState);
                ApplicationInfo proxyAppInfo = getProxyAppInfo((String) entry.getKey());
                if (proxyAppInfo != null) {
                    clearLocationIcon(proxyAppState, proxyAppInfo.uid, (String) entry.getKey());
                }
            }
        }
        this.mProxyAppsState.clear();
    }

    public final void runOnHandler(Runnable runnable) {
        this.mWakeLock.acquire(60000L);
        if (this.mHandler.post(
                new GnssVisibilityControl$$ExternalSyntheticLambda1(this, runnable, 1))) {
            return;
        }
        this.mWakeLock.release();
    }

    public final void setNfwLocationAccessProxyAppsInGnssHal(String[] strArr) {
        String arrays = Arrays.toString(strArr);
        Log.i(
                "GnssVisibilityControl",
                "Updating non-framework location access proxy apps in the GNSS HAL to: " + arrays);
        if (!CarrierConfig.getInstance().isKoreaMarket()) {
            Uri uri = GnssLocationProviderSec.PREFERAPN_NO_UPDATE_URI_USING_SUBID;
            if (SystemProperties.getInt("ro.product.first_api_level", 0) <= 28
                    && this.mIsGpsEnabled) {
                strArr = new String[] {"com.sec.location.nfwlocationprivacy"};
            }
        } else if (strArr.length == 0) {
            Log.d(
                    "GnssVisibilityControl",
                    "GnssVisibilityControl KOR exception policy. Force set proxyapp packageName");
            strArr = new String[] {"com.sec.location.nfwlocationprivacy"};
        }
        boolean native_enable_nfw_location_access = native_enable_nfw_location_access(strArr);
        if (!native_enable_nfw_location_access) {
            StorageManagerService$$ExternalSyntheticOutline0.m(
                    "Failed to update non-framework location access proxy apps in the GNSS HAL to:"
                        + " ",
                    arrays,
                    "GnssVisibilityControl");
        }
        if (mIsNfwLocationAccessProxyAppsUpdated || !native_enable_nfw_location_access) {
            return;
        }
        mIsNfwLocationAccessProxyAppsUpdated = true;
    }

    public final boolean shouldEnableLocationPermissionInGnssHal(String str) {
        ApplicationInfo proxyAppInfo = getProxyAppInfo(str);
        return proxyAppInfo != null
                && proxyAppInfo.enabled
                && this.mPackageManager.checkPermission(
                                "android.permission.ACCESS_FINE_LOCATION", str)
                        == 0;
    }

    public final boolean updateLocationIcon(int i, String str, boolean z) {
        if (!z) {
            this.mAppOps.finishOp(41, i, str);
            this.mAppOps.finishOp(42, i, str);
            return true;
        }
        if (this.mAppOps.startOpNoThrow(41, i, str) != 0) {
            return false;
        }
        if (this.mAppOps.startOpNoThrow(42, i, str) == 0) {
            return true;
        }
        this.mAppOps.finishOp(41, i, str);
        return false;
    }

    public final void updateNfwLocationAccessProxyAppsInGnssHal() {
        if (this.mIsGpsEnabled) {
            setNfwLocationAccessProxyAppsInGnssHal(getLocationPermissionEnabledProxyApps());
        } else {
            if (mIsNfwLocationAccessProxyAppsUpdated) {
                return;
            }
            setNfwLocationAccessProxyAppsInGnssHal(NO_LOCATION_ENABLED_PROXY_APPS);
        }
    }
}
