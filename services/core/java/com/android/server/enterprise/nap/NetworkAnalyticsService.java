package com.android.server.enterprise.nap;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.firewall.DomainFilterNapCommon;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.nap.INetworkAnalytics;
import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NetworkAnalyticsService extends INetworkAnalytics.Stub
        implements EnterpriseServiceCallback {
    public static final boolean DBG = Debug.semIsProductDev();
    public static DeviceRootKeyServiceManager mDeviceRootKeyServiceManager;
    public static String mDeviceUniqueId;
    public final NetworkAnalyticsConfigStore mConfigStore;
    public final NetworkAnalyticsConnectionManager mConnectionManager;
    public final Context mContext;
    public final NetworkAnalyticsDataDelivery mDataDelivery;
    public final NetworkAnalyticsDriver mDriver;
    public EnterpriseDeviceManager mEDM = null;
    public final NapHandler mHandler;
    public final HandlerThread mHandlerThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AsyncHandlerObject {
        public String packageName;
        public String profileName;
        public int userId;

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.profileName);
            sb.append(",");
            sb.append(this.packageName);
            sb.append(",");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.userId, sb, "]");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NapHandler extends Handler {
        public NapHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:128:0x0286  */
        /* JADX WARN: Removed duplicated region for block: B:133:0x02a1  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r13) {
            /*
                Method dump skipped, instructions count: 1460
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.enterprise.nap.NetworkAnalyticsService.NapHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NapReceiver extends BroadcastReceiver {
        public NapReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            String action = intent.getAction();
            boolean z = NetworkAnalyticsService.DBG;
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "Nap Receiver : ", action, "NetworkAnalytics:Service");
            }
            if (isInitialStickyBroadcast()) {
                return;
            }
            new Bundle();
            if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
                NetworkAnalyticsService.this.sendMessageToHandler(3, 0, 0, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
                Uri data = intent.getData();
                schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                int i = intent.getExtras().getInt("android.intent.extra.UID", 0);
                if (z) {
                    NetworkScoreService$$ExternalSyntheticOutline0.m(
                            i,
                            "ACTION PACKAGE REMOVED packageName : ",
                            schemeSpecificPart,
                            " uid : ",
                            "NetworkAnalytics:Service");
                }
                int userId = UserHandle.getUserId(i);
                if (NetworkAnalyticsDataDelivery.DBG) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "updateHashCache Called");
                }
                Set set = NetworkAnalyticsDataDelivery.appset;
                synchronized (set) {
                    try {
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            NetworkAnalyticsDataDelivery.AppInfoSet appInfoSet =
                                    (NetworkAnalyticsDataDelivery.AppInfoSet) it.next();
                            if (appInfoSet.uid == i
                                    && appInfoSet.packageName.equalsIgnoreCase(
                                            schemeSpecificPart)) {
                                if (NetworkAnalyticsDataDelivery.DBG) {
                                    Log.d(
                                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                            "DataDelivery hash cache deletion uid:"
                                                    + appInfoSet.uid
                                                    + " pacName:"
                                                    + appInfoSet.packageName
                                                    + " procName:"
                                                    + appInfoSet.processName
                                                    + " trunProcName:"
                                                    + appInfoSet.truncatedProcessName
                                                    + " hash:"
                                                    + appInfoSet.hash);
                                }
                                it.remove();
                            }
                        }
                    } finally {
                    }
                }
                boolean isActivatedPackage =
                        NetworkAnalyticsService.this.mConfigStore.isActivatedPackage(
                                schemeSpecificPart);
                if (schemeSpecificPart == null || !isActivatedPackage) {
                    return;
                }
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "The package removal intent has been called before upgrade");
                    return;
                }
                NetworkAnalyticsService networkAnalyticsService = NetworkAnalyticsService.this;
                AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                asyncHandlerObject.packageName = schemeSpecificPart;
                asyncHandlerObject.userId = userId;
                networkAnalyticsService.sendMessageToHandler(6, 0, 0, asyncHandlerObject);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
                Uri data2 = intent.getData();
                schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                if (z) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "ACTION PACKAGE ADDED packageName : ",
                            schemeSpecificPart,
                            "NetworkAnalytics:Service");
                }
                int userId2 =
                        UserHandle.getUserId(
                                intent.getExtras().getInt("android.intent.extra.UID", 0));
                boolean isActivatedPackage2 =
                        NetworkAnalyticsService.this.mConfigStore.isActivatedPackage(
                                schemeSpecificPart);
                if (schemeSpecificPart == null || !isActivatedPackage2) {
                    return;
                }
                NetworkAnalyticsService networkAnalyticsService2 = NetworkAnalyticsService.this;
                AsyncHandlerObject asyncHandlerObject2 = new AsyncHandlerObject();
                asyncHandlerObject2.packageName = schemeSpecificPart;
                asyncHandlerObject2.userId = userId2;
                networkAnalyticsService2.sendMessageToHandler(7, 0, 0, asyncHandlerObject2);
                return;
            }
            if (!action.equals("android.intent.action.USER_REMOVED")) {
                if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                    if (z) {
                        Log.d("NetworkAnalytics:Service", "EMERGENCY_STATE_CHANGED received");
                    }
                    NetworkAnalyticsService.this.sendMessageToHandler(12, 0, 0, intent.getExtras());
                    return;
                }
                return;
            }
            Bundle extras = intent.getExtras();
            int i2 = extras.getInt("android.intent.extra.user_handle", -10000);
            if (i2 != -10000) {
                if (NetworkAnalyticsDataDelivery.DBG) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "updateHashCacheForUser Called");
                }
                Set set2 = NetworkAnalyticsDataDelivery.appset;
                synchronized (set2) {
                    try {
                        Iterator it2 = set2.iterator();
                        while (it2.hasNext()) {
                            NetworkAnalyticsDataDelivery.AppInfoSet appInfoSet2 =
                                    (NetworkAnalyticsDataDelivery.AppInfoSet) it2.next();
                            if (appInfoSet2.userId == i2) {
                                if (NetworkAnalyticsDataDelivery.DBG) {
                                    Log.d(
                                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                            "DataDelivery hash cache user deletion uid:"
                                                    + appInfoSet2.uid
                                                    + " pacName:"
                                                    + appInfoSet2.packageName
                                                    + " procName:"
                                                    + appInfoSet2.processName
                                                    + " trunProcName:"
                                                    + appInfoSet2.truncatedProcessName
                                                    + " hash:"
                                                    + appInfoSet2.hash);
                                }
                                it2.remove();
                            }
                        }
                    } finally {
                    }
                }
            }
            NetworkAnalyticsService.this.sendMessageToHandler(11, 0, 0, extras);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkAnalyticsServiceConnection implements ServiceConnection {
        public final AsyncHandlerObject handleObj;
        public CountDownLatch countDownLatch = null;
        public INetworkAnalyticsService napInterface = null;

        public NetworkAnalyticsServiceConnection(AsyncHandlerObject asyncHandlerObject) {
            this.handleObj = asyncHandlerObject;
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onBindingDied(ComponentName componentName) {
            AsyncHandlerObject asyncHandlerObject = this.handleObj;
            Log.d(
                    "NetworkAnalytics:Service",
                    "onBindingDied called by "
                            + NetworkAnalyticsService.getTransformedVendorName(
                                    asyncHandlerObject.userId, asyncHandlerObject.packageName));
            List allProfilesForUser =
                    NetworkAnalyticsService.this.mConfigStore.getAllProfilesForUser(
                            this.handleObj.userId);
            if (allProfilesForUser == null) {
                return;
            }
            Iterator it = ((ArrayList) allProfilesForUser).iterator();
            while (it.hasNext()) {
                NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile =
                        (NetworkAnalyticsConfigStore.NAPConfigProfile) it.next();
                if (nAPConfigProfile.packageName.equalsIgnoreCase(this.handleObj.packageName)
                        && nAPConfigProfile.userId == this.handleObj.userId) {
                    String transformedVendorName =
                            NetworkAnalyticsService.getTransformedVendorName(
                                    nAPConfigProfile.opUserId, nAPConfigProfile.profileName);
                    if (transformedVendorName != null) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "Trying to bind again after the onBindingDied for the profile "
                                        + transformedVendorName);
                        NetworkAnalyticsService.this._bindAndActivate(transformedVendorName, true);
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceConnected(
                ComponentName componentName, IBinder iBinder) {
            try {
                AsyncHandlerObject asyncHandlerObject = this.handleObj;
                String transformedVendorName =
                        NetworkAnalyticsService.getTransformedVendorName(
                                asyncHandlerObject.userId, asyncHandlerObject.packageName);
                Log.d(
                        "NetworkAnalytics:Service",
                        "onServiceconnected called by " + transformedVendorName);
                this.napInterface = INetworkAnalyticsService.Stub.asInterface(iBinder);
                AsyncHandlerObject asyncHandlerObject2 = this.handleObj;
                int i = asyncHandlerObject2.userId;
                List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForPackage =
                        i == 0
                                ? NetworkAnalyticsService.this.mConfigStore
                                        .getAllProfilesForPackage(asyncHandlerObject2.packageName)
                                : NetworkAnalyticsService.this.mConfigStore.getAllProfilesForUser(
                                        i);
                if (allProfilesForPackage == null) {
                    return;
                }
                for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile :
                        allProfilesForPackage) {
                    if (nAPConfigProfile.packageName.equalsIgnoreCase(this.handleObj.packageName)
                            && nAPConfigProfile.userId == this.handleObj.userId) {
                        if (NetworkAnalyticsService.this.mConfigStore.isProfileActivatedForUser(
                                        nAPConfigProfile.opUserId, nAPConfigProfile.profileName)
                                == 1) {
                            DataDeliveryHelper dataDeliveryHelper =
                                    new DataDeliveryHelper(
                                            nAPConfigProfile, this, nAPConfigProfile.opUserId);
                            Log.d(
                                    "NetworkAnalytics:Service",
                                    "Adding the profile to the delivery list onServiceconnected"
                                        + " callback "
                                            + nAPConfigProfile.profileName);
                            NetworkAnalyticsService.this.mDataDelivery.addNAPDataRecipient(
                                    dataDeliveryHelper);
                        }
                    }
                }
                NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager =
                        NetworkAnalyticsService.this.mConnectionManager;
                if (networkAnalyticsConnectionManager.binderMap.get(transformedVendorName)
                        != null) {
                    networkAnalyticsConnectionManager.binderMap.remove(transformedVendorName);
                }
                networkAnalyticsConnectionManager.binderMap.put(transformedVendorName, this);
                CountDownLatch countDownLatch = this.countDownLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceDisconnected(ComponentName componentName) {
            AsyncHandlerObject asyncHandlerObject = this.handleObj;
            String transformedVendorName =
                    NetworkAnalyticsService.getTransformedVendorName(
                            asyncHandlerObject.userId, asyncHandlerObject.packageName);
            Log.d(
                    "NetworkAnalytics:Service",
                    "onServiceDisconnected called by " + transformedVendorName);
            NetworkAnalyticsService.this.mConnectionManager.removeBinderForPackage(
                    transformedVendorName);
            NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery =
                    NetworkAnalyticsService.this.mDataDelivery;
            AsyncHandlerObject asyncHandlerObject2 = this.handleObj;
            networkAnalyticsDataDelivery.removeDataRecipientsForPackage(
                    asyncHandlerObject2.userId, asyncHandlerObject2.packageName);
        }
    }

    public NetworkAnalyticsService(Context context) {
        this.mContext = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mConfigStore = null;
        this.mConnectionManager = null;
        this.mDataDelivery = null;
        this.mDriver = null;
        Log.d("NetworkAnalytics:Service", "Adding network analytics service.");
        this.mContext = context;
        try {
            Log.d(
                    "NetworkAnalytics:Service",
                    "Network Platform analytics Service is going to be added to the ServiceManager"
                        + " list ");
            this.mConfigStore =
                    NetworkAnalyticsConfigStore.getInstance(
                            NetworkAnalyticsStorageHelper.getInstance(context));
            if (NetworkAnalyticsConnectionManager.mInstance == null) {
                NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager =
                        new NetworkAnalyticsConnectionManager();
                networkAnalyticsConnectionManager.binderMap = null;
                networkAnalyticsConnectionManager.profilesForPackage = null;
                networkAnalyticsConnectionManager.activatedProfileCounter = 0;
                networkAnalyticsConnectionManager.binderMap = new ConcurrentHashMap();
                networkAnalyticsConnectionManager.profilesForPackage = new ConcurrentHashMap();
                NetworkAnalyticsConnectionManager.mInstance = networkAnalyticsConnectionManager;
            }
            NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager2 =
                    NetworkAnalyticsConnectionManager.mInstance;
            this.mConnectionManager = networkAnalyticsConnectionManager2;
            NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery =
                    NetworkAnalyticsDataDelivery.getInstance();
            this.mDataDelivery = networkAnalyticsDataDelivery;
            this.mDriver =
                    NetworkAnalyticsDriver.getInstance(
                            networkAnalyticsConnectionManager2, networkAnalyticsDataDelivery);
            HandlerThread handlerThread = new HandlerThread("NapHandler", 10);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mHandler = new NapHandler(this.mHandlerThread.getLooper());
            try {
                setupIntentFilter();
            } catch (Exception unused) {
                Log.e(
                        "NetworkAnalytics:Service",
                        "Error occured while trying to ini the BroadcastReceiver");
            }
            if (this.mHandler == null || this.mHandlerThread == null) {
                return;
            }
            sendMessageToHandler(4, 0, 0, null);
        } catch (Throwable th) {
            Log.e(
                    "NetworkAnalytics:Service",
                    "Error occured while trying to start NPA as a system service",
                    th);
        }
    }

    public static int getCidFromTransformedName(String str) {
        return Integer.parseInt(str.substring(str.indexOf("__") + 2));
    }

    public static String getTransformedVendorName(int i, String str) {
        return VpnManagerService$$ExternalSyntheticOutline0.m(i, str, "__");
    }

    public static boolean isAllowedRecordingForUsers(
            NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile, int i) {
        if (i == 0) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "isAllowedRecordingForUsers: App is installed in User 0. Can record for any"
                        + " user.");
            return true;
        }
        boolean z = DBG;
        int i2 = nAPConfigProfile.opUserId;
        if (z) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    "NetworkAnalytics:Service",
                    new StringBuilder(
                            "isAllowedRecordingForUsers: App allowed to record profile? "),
                    i2 == i);
        }
        return i2 == i;
    }

    public static int versionCompare(String str, String str2) {
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int max = Math.max(split.length, split2.length);
            int i = 0;
            while (i < max) {
                int parseInt = i < split.length ? Integer.parseInt(split[i]) : 0;
                int parseInt2 = i < split2.length ? Integer.parseInt(split2[i]) : 0;
                if (parseInt > parseInt2) {
                    return 1;
                }
                if (parseInt2 > parseInt) {
                    return -1;
                }
                i++;
            }
            return 0;
        } catch (Exception e) {
            Log.e(
                    "NetworkAnalytics:Service",
                    "handleNAPClientCall: Exception in Comparing Versions",
                    e);
            return 0;
        }
    }

    public final boolean _activateNAPClient(AsyncHandlerObject asyncHandlerObject, int i, int i2) {
        INetworkAnalyticsService binderForPackage =
                this.mConnectionManager.getBinderForPackage(
                        getTransformedVendorName(
                                asyncHandlerObject.userId, asyncHandlerObject.packageName));
        NetworkAnalyticsConfigStore.NAPConfigProfile profileforName =
                this.mConfigStore.getProfileforName(asyncHandlerObject.profileName);
        String str = profileforName != null ? profileforName.jsonString : null;
        boolean z = DBG;
        if (z) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "_activateNAPClient: activation:", "NetworkAnalytics:Service");
        }
        if (binderForPackage == null || str == null) {
            if (z) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_activateNAPClient: Null values found. INetworkAnalyticsService = "
                                + binderForPackage
                                + " or json = "
                                + str);
            }
            return false;
        }
        int i3 = -1;
        try {
            if (1 == i) {
                String deviceUniqueKey = getDeviceUniqueKey();
                if (z) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_activateNAPClient: Device unique Key = " + deviceUniqueKey);
                }
                i3 = binderForPackage.onActivateProfile(str, i2, deviceUniqueKey);
                if (z) {
                    Log.d("NetworkAnalytics:Service", "onActivateProfile: returnValue = " + i3);
                }
            } else {
                i3 = binderForPackage.onDeactivateProfile(asyncHandlerObject.profileName, i2);
                if (z) {
                    Log.d("NetworkAnalytics:Service", "onDeactivateProfile: returnValue = " + i3);
                }
            }
            if (z) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_activateNAPClient: onActivate from client is " + i3);
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_activateNAPClient: Remote Exception", e);
        }
        return i3 == 0;
    }

    public final void _bindAndActivate(AsyncHandlerObject asyncHandlerObject, int i, int i2) {
        boolean await;
        try {
            if (1 != i2) {
                boolean z = DBG;
                if (z) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_NOT_ACTIVATED: Doing deactivate and unbind.");
                }
                boolean _activateNAPClient = _activateNAPClient(asyncHandlerObject, 0, i);
                if (!_activateNAPClient && z) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_NOT_ACTIVATED: deactivate did not succeed");
                }
                this.mConnectionManager.removeProfileForPackage(
                        getTransformedVendorName(
                                asyncHandlerObject.userId, asyncHandlerObject.packageName),
                        getTransformedVendorName(i, asyncHandlerObject.profileName));
                this.mDataDelivery.removeNAPDataRecipient(i, asyncHandlerObject.profileName);
                if (((List)
                                this.mConnectionManager.profilesForPackage.get(
                                        getTransformedVendorName(
                                                asyncHandlerObject.userId,
                                                asyncHandlerObject.packageName)))
                        != null) {
                    if (z) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "_bindAndActivate:PROFILE_NOT_ACTIVATED: Wont unbind.");
                        return;
                    }
                    return;
                }
                _unbindClient(asyncHandlerObject);
                if (this.mConnectionManager.activatedProfileCounter <= 0) {
                    if (z) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "_bindAndActivate:PROFILE_NOT_ACTIVATED: Will stop data"
                                    + " recording.");
                    }
                    this.mDriver.endDataRecording();
                    NetworkAnalyticsDataDelivery.clearHashCacheEntire();
                }
                if (z) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_NOT_ACTIVATED: deactivate complete: "
                                    + _activateNAPClient);
                    return;
                }
                return;
            }
            boolean z2 = DBG;
            if (z2) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_bindAndActivate:PROFILE_ACTIVATED: Doing bind and activate.");
            }
            NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection =
                    new NetworkAnalyticsServiceConnection(asyncHandlerObject);
            networkAnalyticsServiceConnection.countDownLatch = new CountDownLatch(1);
            int _bindToClient = _bindToClient(networkAnalyticsServiceConnection);
            asyncHandlerObject.getClass();
            if (-1 == _bindToClient) {
                if (z2) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED: _bindToClient did not succeed");
                    return;
                }
                return;
            }
            if (_bindToClient == 0) {
                if (this.mConnectionManager.isProfilePresentForPackage(
                        getTransformedVendorName(
                                asyncHandlerObject.userId, asyncHandlerObject.packageName),
                        getTransformedVendorName(i, asyncHandlerObject.profileName))) {
                    _activateNAPClient(asyncHandlerObject, 1, i);
                    return;
                }
                if (z2) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED: Honoring previous bind.");
                }
                networkAnalyticsServiceConnection =
                        this.mConnectionManager.getServiceConnectionForPackage(
                                getTransformedVendorName(
                                        asyncHandlerObject.userId, asyncHandlerObject.packageName));
                await = true;
            } else {
                await =
                        networkAnalyticsServiceConnection.countDownLatch.await(
                                3000L, TimeUnit.MILLISECONDS);
            }
            networkAnalyticsServiceConnection.countDownLatch = null;
            if (await && _bindToClient >= 0) {
                if (z2) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED: _bindToClient & countdown: "
                                    + asyncHandlerObject.toString());
                }
                if (!_activateNAPClient(asyncHandlerObject, 1, i)) {
                    return;
                }
                Log.d(
                        "NetworkAnalytics:Service",
                        "_bindAndActivate:PROFILE_ACTIVATED: Adding to data delivery");
                int i3 = asyncHandlerObject.userId;
                List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForPackage =
                        i3 == 0
                                ? this.mConfigStore.getAllProfilesForPackage(
                                        asyncHandlerObject.packageName)
                                : this.mConfigStore.getAllProfilesForUser(i3);
                if (allProfilesForPackage == null) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate: no profile found for user. Not adding profile to"
                                + " recipient list on NetworkAnalyticsDataDelivery");
                    return;
                }
                for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile :
                        allProfilesForPackage) {
                    String str = nAPConfigProfile.packageName;
                    int i4 = nAPConfigProfile.opUserId;
                    String str2 = nAPConfigProfile.profileName;
                    if (str.equalsIgnoreCase(asyncHandlerObject.packageName)
                            && nAPConfigProfile.userId == asyncHandlerObject.userId
                            && this.mConfigStore.isProfileActivatedForUser(i4, str2) == 1) {
                        DataDeliveryHelper dataDeliveryHelper =
                                new DataDeliveryHelper(
                                        nAPConfigProfile, networkAnalyticsServiceConnection, i4);
                        Log.d(
                                "NetworkAnalytics:Service",
                                "Adding the profile to the delivery list _bindAndActivate " + str2);
                        this.mDataDelivery.addNAPDataRecipient(dataDeliveryHelper);
                    }
                }
                if (EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() == null) {
                    Log.i(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device"
                                + " failed.");
                    return;
                }
                if (versionCompare(
                                EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion(),
                                EdmConstants.EnterpriseKnoxSdkVersion
                                        .KNOX_ENTERPRISE_SDK_VERSION_3_3
                                        .getInternalVersion())
                        < 0) {
                    if (this.mDriver.openCharDevice(0) < 0) {
                        Log.i(
                                "NetworkAnalytics:Service",
                                "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device"
                                    + " failed.");
                        return;
                    }
                } else if (this.mDriver.openCharDevice(1) < 0) {
                    Log.i(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device"
                                + " failed.");
                    return;
                }
                this.mConnectionManager.addProfileForPackage(
                        getTransformedVendorName(
                                asyncHandlerObject.userId, asyncHandlerObject.packageName),
                        getTransformedVendorName(i, asyncHandlerObject.profileName));
                NetworkAnalyticsConfigStore.NAPConfigProfile profileforName =
                        this.mConfigStore.getProfileforName(asyncHandlerObject.profileName);
                if (profileforName == null) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate: no profile found. Not begining to record data");
                    return;
                }
                int i5 = profileforName.opUserId;
                if (this.mDriver.checkNcmVersionMismatch() < 0) {
                    if (activateProfile(profileforName, 0) < 0) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "_bindAndActivate: version mismatch deactivation failed for profile"
                                    + " : "
                                        + asyncHandlerObject.profileName
                                        + " but data collection will not happen.");
                        return;
                    }
                    sendMessageToHandler(1, 0, i5, asyncHandlerObject);
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate:PROFILE_ACTIVATED:Version mismatch between user and"
                                + " kernel space. Deactivated this profile : "
                                    + asyncHandlerObject.profileName);
                    return;
                }
                NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName =
                        this.mConfigStore.getActiveStateForName(
                                getTransformedVendorName(i5, profileforName.profileName));
                if (activeStateForName == null) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate: no active profile found. Not begining to record"
                                + " data");
                    return;
                }
                int i6 = activeStateForName.activationFlowType;
                int i7 = activeStateForName.activationIntervalValue;
                if (i6 == 0
                        && i7 >= 60
                        && i7 <= 14400
                        && this.mDriver.setIntervalValueForFlow(i7) < 0) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindAndActivate: set interval value and deactivation failed for"
                                + " profile : "
                                    + asyncHandlerObject.profileName
                                    + " but data collection will not happen.");
                    return;
                }
                this.mDriver.beginDataRecording(i6);
            }
            if (DBG) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_bindAndActivate:PROFILE_ACTIVATED: bind and activate complete.");
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_bindAndActivate RemoteException", e);
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "_bindAndActivate Exception", e2);
        }
    }

    public final void _bindAndActivate(String str, boolean z) {
        int i;
        boolean z2 = DBG;
        if (z2) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "_bindAndActivate:ACTION_INITIALIZE_NAP: adding profile: ",
                    str,
                    "NetworkAnalytics:Service");
        }
        NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName =
                this.mConfigStore.getActiveStateForName(str);
        if (activeStateForName == null) {
            if (z2) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "_bindAndActivate: ERROR NAPActivationProfile is null for profile: ",
                        str,
                        "NetworkAnalytics:Service");
                return;
            }
            return;
        }
        if (activeStateForName.activationState == 0) {
            if (z2) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_bindAndActivate: ERROR NAPActivationProfile is not activated. No need to"
                            + " bind.");
                return;
            }
            return;
        }
        NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile = activeStateForName.profile;
        if (nAPConfigProfile == null) {
            if (z2) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "_bindAndActivate: ERROR NAPConfigProfile is null for profile: ",
                        str,
                        "NetworkAnalytics:Service");
                return;
            }
            return;
        }
        String str2 = nAPConfigProfile.packageName;
        if (z) {
            String str3 = nAPConfigProfile.packageSignature;
            int i2 = nAPConfigProfile.userId;
            if (z2) {
                Log.d("NetworkAnalytics:Service", "validateNAPClient");
            }
            try {
                i = isValidAppInstalled(i2, str2, str3);
                if (i < 0) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "validateNAPClient:Valid monitor application is not installed " + i);
                } else {
                    i = 0;
                }
            } catch (Exception e) {
                Log.e(
                        "NetworkAnalytics:Service",
                        "validateNAPClient: Add profile to database failed. Exception",
                        e);
                i = -1;
            }
            if (i < 0) {
                if (DBG) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "_bindAndActivate: ERROR Valid package has not been installed: ",
                            str2,
                            "NetworkAnalytics:Service");
                    return;
                }
                return;
            }
        }
        AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
        asyncHandlerObject.profileName = str.substring(0, str.indexOf("__"));
        asyncHandlerObject.packageName = str2;
        asyncHandlerObject.userId = nAPConfigProfile.userId;
        _bindAndActivate(asyncHandlerObject, getCidFromTransformedName(str), z ? 1 : 0);
    }

    public final int _bindToClient(
            NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection) {
        boolean z = false;
        long j = 0;
        try {
            try {
                AsyncHandlerObject asyncHandlerObject = networkAnalyticsServiceConnection.handleObj;
                boolean z2 = DBG;
                if (z2) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_bindToClient:handlerObj.profileName = "
                                    + asyncHandlerObject.toString());
                }
                INetworkAnalyticsService binderForPackage =
                        this.mConnectionManager.getBinderForPackage(
                                getTransformedVendorName(
                                        asyncHandlerObject.userId, asyncHandlerObject.packageName));
                IBinder asBinder = binderForPackage != null ? binderForPackage.asBinder() : null;
                if (binderForPackage != null) {
                    if (asBinder.isBinderAlive()) {
                        if (z2) {
                            Log.d(
                                    "NetworkAnalytics:Service",
                                    "_bindToClient:Binder is already available for package = "
                                            + asyncHandlerObject.packageName);
                        }
                        Binder.restoreCallingIdentity(0L);
                        return 0;
                    }
                    _unbindClient(asyncHandlerObject);
                }
                String str = asyncHandlerObject.packageName + "_namonitoraction";
                j = Binder.clearCallingIdentity();
                Intent intent = new Intent(str);
                List<ResolveInfo> queryIntentServicesAsUser =
                        this.mContext
                                .getPackageManager()
                                .queryIntentServicesAsUser(intent, 0, asyncHandlerObject.userId);
                if (queryIntentServicesAsUser.size() > 0) {
                    if (z2) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "_bindToClient:vendorServices.size() > 0");
                    }
                    for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
                        if (resolveInfo.serviceInfo.packageName.equalsIgnoreCase(
                                asyncHandlerObject.packageName)) {
                            boolean z3 = DBG;
                            if (z3) {
                                Log.d(
                                        "NetworkAnalytics:Service",
                                        "_bindToClient:resolveInfo.serviceInfo.packageName = "
                                                + resolveInfo.serviceInfo.packageName);
                            }
                            if (z3) {
                                Log.d(
                                        "NetworkAnalytics:Service",
                                        "_bindToClient:resolveInfo.serviceInfo.name = "
                                                + resolveInfo.serviceInfo.name);
                            }
                            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                            intent.setComponent(
                                    new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        }
                    }
                }
                intent.setPackage(asyncHandlerObject.packageName);
                z =
                        this.mContext.bindServiceAsUser(
                                intent,
                                networkAnalyticsServiceConnection,
                                1,
                                new UserHandle(asyncHandlerObject.userId));
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_bindToClient:bindSuccess = " + z);
                }
            } catch (Exception e) {
                Log.e("NetworkAnalytics:Service", "_bindToClient:Exception", e);
            }
            Binder.restoreCallingIdentity(j);
            return z ? 1 : -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(0L);
            throw th;
        }
    }

    public final void _cleanUpConnectionDetails(AsyncHandlerObject asyncHandlerObject) {
        String transformedVendorName =
                getTransformedVendorName(asyncHandlerObject.userId, asyncHandlerObject.packageName);
        if (DBG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "_cleanUpConnectionDetails tableKey = ",
                    transformedVendorName,
                    "NetworkAnalytics:Service");
        }
        NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager =
                this.mConnectionManager;
        boolean z = NetworkAnalyticsConnectionManager.DBG;
        if (z) {
            networkAnalyticsConnectionManager.getClass();
            Log.d(
                    "NetworkAnalytics:ConnectionManager",
                    "removeProfileForPackage completely for packageName = "
                            + transformedVendorName);
        }
        List list =
                (List)
                        networkAnalyticsConnectionManager.profilesForPackage.get(
                                transformedVendorName);
        if (list != null) {
            int size = networkAnalyticsConnectionManager.activatedProfileCounter - list.size();
            networkAnalyticsConnectionManager.activatedProfileCounter = size;
            if (size < 0) {
                networkAnalyticsConnectionManager.activatedProfileCounter = 0;
            }
            networkAnalyticsConnectionManager.profilesForPackage.remove(transformedVendorName);
            if (z) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "removeProfileForPackage completely for activatedProfileCounter ="
                                    + " "),
                        networkAnalyticsConnectionManager.activatedProfileCounter,
                        "NetworkAnalytics:ConnectionManager");
            }
        }
        this.mConnectionManager.removeBinderForPackage(transformedVendorName);
        this.mDataDelivery.removeDataRecipientsForPackage(
                asyncHandlerObject.userId, asyncHandlerObject.packageName);
    }

    public final void _unbindClient(AsyncHandlerObject asyncHandlerObject) {
        try {
            if (asyncHandlerObject.userId == -999) {
                if (DBG) {
                    Log.d(
                            "NetworkAnalytics:Service",
                            "_unbindClient: The profile was never activated. Returning.");
                    return;
                }
                return;
            }
            if (this.mConfigStore.getProfileforName(asyncHandlerObject.profileName) == null
                    && DBG) {
                Log.d("NetworkAnalytics:Service", "_unbindClient: profile object null");
            }
            NetworkAnalyticsServiceConnection serviceConnectionForPackage =
                    this.mConnectionManager.getServiceConnectionForPackage(
                            getTransformedVendorName(
                                    asyncHandlerObject.userId, asyncHandlerObject.packageName));
            if (serviceConnectionForPackage != null) {
                this.mContext.unbindService(serviceConnectionForPackage);
            }
            _cleanUpConnectionDetails(asyncHandlerObject);
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "_unbindClient: Done");
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_unbindClient: RemoteException", e);
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "_unbindClient: Exception", e2);
        }
    }

    public final int activateProfile(
            NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile, int i) {
        if (DBG) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Called activateProfile: "),
                    nAPConfigProfile.profileName,
                    "NetworkAnalytics:Service");
        }
        try {
            NetworkAnalyticsConfigStore networkAnalyticsConfigStore = this.mConfigStore;
            String str = nAPConfigProfile.profileName;
            int i2 = nAPConfigProfile.opUserId;
            if (networkAnalyticsConfigStore.isProfileActivatedForUser(i2, str) == i) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "activateProfile:Profile is already in the activated state for the user.");
                return -8;
            }
            int activateProfile =
                    this.mConfigStore.activateProfile(
                            nAPConfigProfile.adminUid, i2, i, nAPConfigProfile.profileName);
            if (activateProfile >= 0) {
                return activateProfile;
            }
            Log.d(
                    "NetworkAnalytics:Service",
                    "Activate profile config storage returned = "
                            + activateProfile
                            + " for state "
                            + i);
            return activateProfile;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Add profile to database falied. Exception", e);
            return -1;
        }
    }

    public final void constructObjectForBroadcast(int i, int i2, String str) {
        try {
            this.mConfigStore.getClass();
            String profileObjectFromJson =
                    NetworkAnalyticsConfigStore.getProfileObjectFromJson("profile_name", str);
            this.mConfigStore.getClass();
            String profileObjectFromJson2 =
                    NetworkAnalyticsConfigStore.getProfileObjectFromJson("package_name", str);
            this.mConfigStore.getClass();
            String profileObjectFromJson3 =
                    NetworkAnalyticsConfigStore.getProfileObjectFromJson("package_signature", str);
            Bundle bundle = new Bundle();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(profileObjectFromJson);
            arrayList.add(profileObjectFromJson2);
            arrayList.add(profileObjectFromJson3);
            arrayList.add(String.valueOf(i2));
            arrayList.add(String.valueOf(i));
            bundle.putStringArrayList("profileInfo", arrayList);
            if (this.mHandler == null || this.mHandlerThread == null) {
                return;
            }
            sendMessageToHandler(14, 0, 0, bundle);
        } catch (Exception unused) {
            Log.e(
                    "NetworkAnalytics:Service",
                    "Error occured while constructing the profile object for broadcast");
        }
    }

    public final ContextInfo enforceNetworkAnalyticsPermission(ContextInfo contextInfo) {
        Log.d(
                "NetworkAnalytics:Service",
                "enforceNetworkAnalyticsPermission: knox version above 3.0. Validating NPA"
                    + " Permission");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(
                contextInfo, "com.samsung.android.knox.permission.KNOX_NPA");
    }

    public final String getDeviceUniqueKey() {
        DeviceRootKeyServiceManager deviceRootKeyServiceManager;
        if (mDeviceUniqueId == null) {
            Context context = this.mContext;
            synchronized (NetworkAnalyticsService.class) {
                try {
                    if (mDeviceRootKeyServiceManager == null) {
                        mDeviceRootKeyServiceManager = new DeviceRootKeyServiceManager(context);
                    }
                    deviceRootKeyServiceManager = mDeviceRootKeyServiceManager;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (deviceRootKeyServiceManager != null) {
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "getDeviceUniqueKey: drkService not null");
                }
                mDeviceUniqueId = deviceRootKeyServiceManager.getDeviceRootKeyUID(1);
            }
        }
        return mDeviceUniqueId;
    }

    public final synchronized String getNPAVersion() {
        String str;
        try {
            if (this.mDriver.ncmVersion == null
                    && EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() != null) {
                String internalVersion =
                        EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3
                                .getInternalVersion();
                String internalVersion2 =
                        EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion();
                Log.d(
                        "TAG",
                        "supportedVersion = "
                                + internalVersion
                                + "currentVersion  = "
                                + internalVersion2);
                if (versionCompare(internalVersion2, internalVersion) < 0) {
                    int openCharDevice = this.mDriver.openCharDevice(0);
                    if (openCharDevice < 0) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "getNPAVersion: Failed to open char device : " + openCharDevice);
                        return Integer.toString(openCharDevice);
                    }
                } else {
                    int openCharDevice2 = this.mDriver.openCharDevice(1);
                    if (openCharDevice2 < 0) {
                        Log.d(
                                "NetworkAnalytics:Service",
                                "getNPAVersion: Failed to open char device : " + openCharDevice2);
                        return Integer.toString(openCharDevice2);
                    }
                }
            }
            str =
                    EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion()
                            + "_0_"
                            + this.mDriver.ncmVersion;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "getNPAVersion Exception", e);
            str = null;
        }
        return str;
    }

    public final List getNetworkMonitorProfiles(ContextInfo contextInfo) {
        boolean z = DBG;
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("getNetworkMonitorProfiles API caller is "),
                    contextInfo.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        ContextInfo enforceNetworkAnalyticsPermission =
                enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "getNetworkMonitorProfiles API caller with updated contextInfo is "),
                    enforceNetworkAnalyticsPermission.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        try {
            return this.mConfigStore.getClientProfiles(
                    enforceNetworkAnalyticsPermission.mCallerUid,
                    enforceNetworkAnalyticsPermission.mContainerId);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Get NVM client profiles. Exception", e);
            return null;
        }
    }

    public final String getPackageCertForPkgname(int i, String str) {
        boolean z;
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                z = DBG;
                if (z) {
                    Log.d("NetworkAnalytics:Service", "getPackageCertForPkgname: User id = " + i);
                }
                PackageManagerAdapter.getInstance(this.mContext).getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(64, i, str);
            } catch (NullPointerException e) {
                Log.e(
                        "NetworkAnalytics:Service",
                        "In getPackageCertForPkgname: NullPointerException",
                        e);
            } catch (Exception e2) {
                Log.e("NetworkAnalytics:Service", "In getPackageCertForPkgname: Exception", e2);
            }
            if (packageInfo == null) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "getPackageCertForPkgname: pkgInfo is null");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            int length = signatureArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Signature signature = signatureArr[i2];
                if (signature != null) {
                    str2 = signature.toCharsString();
                    break;
                }
                i2++;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return str2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final String getPackageNameForUid$5(int i) {
        String str = null;
        boolean z = false;
        for (String str2 : this.mContext.getPackageManager().getPackagesForUid(i)) {
            NetworkAnalyticsConfigStore networkAnalyticsConfigStore = this.mConfigStore;
            Set keySet = networkAnalyticsConfigStore.profileMap.keySet();
            if (keySet != null && keySet.size() > 0) {
                Iterator it = keySet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile =
                                (NetworkAnalyticsConfigStore.NAPConfigProfile)
                                        networkAnalyticsConfigStore.profileMap.get(
                                                (String) it.next());
                        if (nAPConfigProfile != null && nAPConfigProfile.packageName.equals(str2)) {
                            z = true;
                            str = str2;
                            break;
                        }
                    }
                }
            }
        }
        if (z && str != null) {
            return str;
        }
        Log.d(
                "NetworkAnalytics:Service",
                "start: Package name could not be found in activated list.");
        return null;
    }

    public final List getProfiles(ContextInfo contextInfo) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String packageNameForUid$5 = getPackageNameForUid$5(callingUid);
        if (packageNameForUid$5 == null) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "getProfiles: NAP client caller cannot be validated. PackageName null");
            return null;
        }
        try {
            return this.mConfigStore.getClientProfiles(userId, packageNameForUid$5);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Get NVM client profiles. Exception", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x031b A[Catch: all -> 0x0046, Exception -> 0x0049, TRY_LEAVE, TryCatch #1 {Exception -> 0x0049, blocks: (B:5:0x0010, B:10:0x0025, B:12:0x002b, B:16:0x004c, B:18:0x0052, B:20:0x0058, B:21:0x005c, B:23:0x0062, B:180:0x006a, B:25:0x008e, B:28:0x0098, B:38:0x00bd, B:170:0x00c3, B:42:0x00da, B:45:0x00de, B:137:0x00ee, B:51:0x00f8, B:55:0x0106, B:60:0x0117, B:67:0x0127, B:69:0x0131, B:72:0x0164, B:75:0x016e, B:77:0x0178, B:79:0x017e, B:80:0x0182, B:82:0x0188, B:85:0x0197, B:88:0x019c, B:90:0x01a0, B:93:0x01a4, B:104:0x01ae, B:106:0x01bc, B:108:0x01d0, B:117:0x01d9, B:110:0x022c, B:113:0x0234, B:120:0x01fe, B:123:0x0207, B:127:0x0259, B:132:0x0280, B:140:0x028a, B:142:0x0290, B:144:0x029c, B:151:0x0315, B:153:0x031b, B:158:0x0349, B:160:0x02cf, B:162:0x02db, B:164:0x02e7, B:190:0x0362), top: B:4:0x0010, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0349 A[Catch: all -> 0x0046, Exception -> 0x0049, TRY_ENTER, TryCatch #1 {Exception -> 0x0049, blocks: (B:5:0x0010, B:10:0x0025, B:12:0x002b, B:16:0x004c, B:18:0x0052, B:20:0x0058, B:21:0x005c, B:23:0x0062, B:180:0x006a, B:25:0x008e, B:28:0x0098, B:38:0x00bd, B:170:0x00c3, B:42:0x00da, B:45:0x00de, B:137:0x00ee, B:51:0x00f8, B:55:0x0106, B:60:0x0117, B:67:0x0127, B:69:0x0131, B:72:0x0164, B:75:0x016e, B:77:0x0178, B:79:0x017e, B:80:0x0182, B:82:0x0188, B:85:0x0197, B:88:0x019c, B:90:0x01a0, B:93:0x01a4, B:104:0x01ae, B:106:0x01bc, B:108:0x01d0, B:117:0x01d9, B:110:0x022c, B:113:0x0234, B:120:0x01fe, B:123:0x0207, B:127:0x0259, B:132:0x0280, B:140:0x028a, B:142:0x0290, B:144:0x029c, B:151:0x0315, B:153:0x031b, B:158:0x0349, B:160:0x02cf, B:162:0x02db, B:164:0x02e7, B:190:0x0362), top: B:4:0x0010, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int handleNAPClientCall(
            java.lang.String r17, android.os.Bundle r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 913
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.nap.NetworkAnalyticsService.handleNAPClientCall(java.lang.String,"
                    + " android.os.Bundle, boolean):int");
    }

    public final int isProfileActivatedForUser(ContextInfo contextInfo, String str) {
        boolean z = DBG;
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("isProfileActivatedForUser API caller is "),
                    contextInfo.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        ContextInfo enforceNetworkAnalyticsPermission =
                enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "isProfileActivatedForUser API caller with updated contextInfo is "),
                    enforceNetworkAnalyticsPermission.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        int i = enforceNetworkAnalyticsPermission.mCallerUid;
        if (i <= 0 || str == null) {
            Log.d("NetworkAnalytics:Service", "isProfileActivatedForUser: Invalid parameters");
            return -4;
        }
        int doesAdminOwnProfile = this.mConfigStore.doesAdminOwnProfile(i, str);
        if (doesAdminOwnProfile >= 0) {
            try {
                return this.mConfigStore.isProfileActivatedForUser(
                        enforceNetworkAnalyticsPermission.mContainerId, str);
            } catch (Exception e) {
                Log.e(
                        "NetworkAnalytics:Service",
                        "isProfileActivatedForUser: Remove profile to database falied. Exception",
                        e);
                return -1;
            }
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "isProfileActivatedForUser: Profile ",
                        str,
                        "is absent or does not belong to adminUid ");
        m.append(enforceNetworkAnalyticsPermission.mCallerUid);
        m.append(" return=");
        m.append(doesAdminOwnProfile);
        Log.d("NetworkAnalytics:Service", m.toString());
        return doesAdminOwnProfile;
    }

    public final int isValidAppInstalled(int i, String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (PackageManagerAdapter.getInstance(this.mContext) != null) {
            if (!PackageManagerAdapter.isApplicationInstalled(i, str)) {
                Log.d("NetworkAnalytics:Service", "isValidAppInstalled: Monitor app not installed");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            String packageCertForPkgname = getPackageCertForPkgname(i, str);
            if (packageCertForPkgname != null
                    && packageCertForPkgname.length() > 0
                    && !str2.equals(packageCertForPkgname)) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "isValidAppInstalled: Monitor app signature is not matched.");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 0;
    }

    public final List listOfProfilesForOperation(int i, String str, String str2) {
        if (str2 == null) {
            return null;
        }
        if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str2)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.mConfigStore.getProfileforName(str2));
            return arrayList;
        }
        if (i == 0) {
            return this.mConfigStore.getAllProfilesForPackage(str);
        }
        NetworkAnalyticsConfigStore networkAnalyticsConfigStore = this.mConfigStore;
        Set keySet = networkAnalyticsConfigStore.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile =
                    (NetworkAnalyticsConfigStore.NAPConfigProfile)
                            networkAnalyticsConfigStore.profileMap.get((String) it.next());
            if (nAPConfigProfile != null
                    && nAPConfigProfile.packageName.equals(str)
                    && nAPConfigProfile.opUserId == i) {
                arrayList2.add(nAPConfigProfile);
            }
        }
        return arrayList2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        if (this.mHandler == null || this.mHandlerThread == null) {
            return;
        }
        sendMessageToHandler(9, i, 0, null);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        if (this.mHandler == null || this.mHandlerThread == null) {
            return;
        }
        sendMessageToHandler(8, i, 0, null);
    }

    public final int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) {
        int i;
        JSONObject jSONObject;
        int validateJsonContent;
        List clientProfileNames;
        boolean z = DBG;
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("registerNetworkMonitorProfile API caller is "),
                    contextInfo.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        ContextInfo enforceNetworkAnalyticsPermission =
                enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "registerNetworkMonitorProfile API caller with updated contextInfo is"
                                + " "),
                    enforceNetworkAnalyticsPermission.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        if (enforceNetworkAnalyticsPermission == null || str == null) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile: Invalid parameters.");
            return -4;
        }
        if (z) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "registerNetworkMonitorProfile: allowUserId: "
                            + enforceNetworkAnalyticsPermission.mContainerId
                            + " profile:"
                            + str);
        }
        try {
            this.mConfigStore.getClass();
            jSONObject =
                    new JSONObject(str)
                            .getJSONObject("NETWORK_ANALYTICS_PARAMETERS")
                            .getJSONObject("profile_attribute");
            validateJsonContent = this.mConfigStore.validateJsonContent(jSONObject);
        } catch (JSONException e) {
            Log.e(
                    "NetworkAnalytics:Service",
                    "registerNetworkMonitorProfile: JSON Parsing Exception",
                    e);
            i = -2;
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "registerNetworkMonitorProfile: Exception", e2);
            i = -1;
        }
        if (validateJsonContent < 0) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "registerNetworkMonitorProfile: JSON validation failed:" + validateJsonContent);
            return validateJsonContent;
        }
        i =
                this.mConfigStore.addProfileToDatabase(
                        enforceNetworkAnalyticsPermission.mCallerUid,
                        jSONObject,
                        str,
                        enforceNetworkAnalyticsPermission.mContainerId);
        if (i < 0) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "registerNetworkMonitorProfile: Add profile to database failed:" + i);
            return i;
        }
        if (i == 0
                && (clientProfileNames =
                                this.mConfigStore.getClientProfileNames(
                                        enforceNetworkAnalyticsPermission.mCallerUid))
                        != null
                && clientProfileNames.size() == 1) {
            if (z) {
                Log.d("NetworkAnalytics:Service", "The Knox NAP feature has been enabled");
            }
            setFeatureProperty(true);
        }
        this.mConfigStore.getClass();
        NetworkAnalyticsConfigStore.getProfileObjectFromJson("profile_name", str);
        if (i == 0) {
            if (enforceNetworkAnalyticsPermission.mContainerId != 0) {
                constructObjectForBroadcast(0, 0, str);
            }
            constructObjectForBroadcast(0, enforceNetworkAnalyticsPermission.mContainerId, str);
        }
        return i;
    }

    public final void sendMessageToHandler(int i, int i2, int i3, Object obj) {
        NapHandler napHandler = this.mHandler;
        if (napHandler != null) {
            this.mHandler.sendMessage(Message.obtain(napHandler, i, i2, i3, obj));
        }
    }

    public final void setFeatureProperty(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                DomainFilterNapCommon domainFilterNapCommon =
                        DomainFilterNapCommon.getInstance(this.mContext);
                domainFilterNapCommon.getClass();
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        "setNapEnabled ", "DomainFilterNapCommon", z);
                domainFilterNapCommon.mNapEnabled = z;
                domainFilterNapCommon.setNapProperty(z);
                domainFilterNapCommon.updateEnforceDnsUidForAllNetworks();
                if (z) {
                    SystemProperties.set("net.redirect_socket_calls.hooked", "true");
                } else {
                    SystemProperties.set("net.redirect_socket_calls.hooked", "false");
                }
            } catch (Exception e) {
                Log.e("NetworkAnalytics:Service", "Unable to set NPA feature property", e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setupIntentFilter() {
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "setup intent filter is called");
        }
        NapReceiver napReceiver = new NapReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addCategory("android.intent.category.DEFAULT");
        intentFilter.addDataScheme("package");
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(napReceiver, userHandle, intentFilter, null, null);
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.BOOT_COMPLETED",
                        "android.intent.action.USER_STARTED",
                        "android.intent.action.USER_SWITCHED",
                        "android.intent.action.USER_REMOVED",
                        "android.net.conn.CONNECTIVITY_CHANGE");
        m.addAction("android.intent.action.AIRPLANE_MODE");
        this.mContext.registerReceiverAsUser(napReceiver, userHandle, m, null, null, 2);
        this.mContext.registerReceiverAsUser(
                napReceiver,
                userHandle,
                BatteryService$$ExternalSyntheticOutline0.m("enterprise.container.uninstalled"),
                "com.samsung.android.knox.permission.KNOX_CONTAINER",
                null,
                2);
        this.mContext.registerReceiverAsUser(
                napReceiver,
                userHandle,
                BatteryService$$ExternalSyntheticOutline0.m("enterprise.container.admin.changed"),
                "com.samsung.android.knox.permission.KNOX_CONTAINER",
                null,
                2);
        this.mContext.registerReceiverAsUser(
                napReceiver,
                userHandle,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.samsung.intent.action.EMERGENCY_STATE_CHANGED"),
                null,
                null,
                2);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    /* JADX WARN: Removed duplicated region for block: B:50:0x0108  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int unregisterNetworkMonitorProfile(int r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.nap.NetworkAnalyticsService.unregisterNetworkMonitorProfile(int,"
                    + " java.lang.String):int");
    }

    public final int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) {
        boolean z = DBG;
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("unregisterNetworkMonitorProfile API caller is "),
                    contextInfo.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        ContextInfo enforceNetworkAnalyticsPermission =
                enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "unregisterNetworkMonitorProfile API caller with updated contextInfo is"
                                + " "),
                    enforceNetworkAnalyticsPermission.mCallerUid,
                    "NetworkAnalytics:Service");
        }
        return unregisterNetworkMonitorProfile(enforceNetworkAnalyticsPermission.mCallerUid, str);
    }

    public final int validateNAPClient(int i, int i2, String str) {
        boolean validatePkgSignForSingleProfile;
        String packageNameForUid$5 = getPackageNameForUid$5(i);
        if (packageNameForUid$5 == null) {
            Log.d(
                    "NetworkAnalytics:Service",
                    "validateNAPClient: NAP client caller cannot be validated.");
            return -12;
        }
        String packageCertForPkgname = getPackageCertForPkgname(i2, packageNameForUid$5);
        if (packageCertForPkgname != null && packageCertForPkgname.length() > 0) {
            if ("ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                NetworkAnalyticsConfigStore networkAnalyticsConfigStore = this.mConfigStore;
                Iterator it = networkAnalyticsConfigStore.profileMap.keySet().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    boolean z = NetworkAnalyticsConfigStore.DBG;
                    if (hasNext) {
                        NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile =
                                (NetworkAnalyticsConfigStore.NAPConfigProfile)
                                        networkAnalyticsConfigStore.profileMap.get(
                                                (String) it.next());
                        if (nAPConfigProfile != null
                                && nAPConfigProfile.packageName.equals(packageNameForUid$5)) {
                            String str2 = nAPConfigProfile.packageSignature;
                            if (!str2.equals(packageCertForPkgname)) {
                                if (z) {
                                    Log.d(
                                            "NetworkAnalytics:ConfigStore",
                                            "validatePkgSignForAllProfiles: Signatures Dont"
                                                + " match!!! ");
                                    Log.d(
                                            "NetworkAnalytics:ConfigStore",
                                            "validatePkgSignForAllProfiles: Signatures in profile");
                                    Log.d("NetworkAnalytics:ConfigStore", str2);
                                    Log.d(
                                            "NetworkAnalytics:ConfigStore",
                                            "validatePkgSignForAllProfiles: Signatures in parameter"
                                                + " ");
                                    Log.d("NetworkAnalytics:ConfigStore", packageCertForPkgname);
                                }
                                validatePkgSignForSingleProfile = false;
                            }
                        }
                    } else {
                        if (z) {
                            Log.d(
                                    "NetworkAnalytics:ConfigStore",
                                    "validatePkgSignForAllProfiles: Valid package signature for "
                                            .concat(packageNameForUid$5));
                        }
                        validatePkgSignForSingleProfile = true;
                    }
                }
            } else {
                validatePkgSignForSingleProfile =
                        this.mConfigStore.validatePkgSignForSingleProfile(
                                packageNameForUid$5, packageCertForPkgname, str);
            }
            if (!validatePkgSignForSingleProfile) {
                Log.d(
                        "NetworkAnalytics:Service",
                        "_validateNAPClient: Package signature could not be matched for profile"
                            + " entry/entries");
                return -13;
            }
        }
        if (!DBG) {
            return 0;
        }
        Log.d("NetworkAnalytics:Service", "_validateNAPClient: Package validation complete.");
        return 0;
    }
}
