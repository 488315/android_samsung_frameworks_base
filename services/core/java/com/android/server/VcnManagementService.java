package com.android.server;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkProvider;
import android.net.NetworkRequest;
import android.net.NetworkScore;
import android.net.vcn.Flags;
import android.net.vcn.IVcnManagementService;
import android.net.vcn.IVcnStatusCallback;
import android.net.vcn.IVcnUnderlyingNetworkPolicyListener;
import android.net.vcn.VcnConfig;
import android.net.vcn.VcnGatewayConnectionConfig;
import android.net.vcn.VcnUnderlyingNetworkPolicy;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.net.module.util.PermissionUtils;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.Vcn;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.VcnNetworkProvider;
import com.android.server.vcn.util.PersistableBundleUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VcnManagementService extends IVcnManagementService.Stub {
    static final long CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS;
    public static final long DUMP_TIMEOUT_MILLIS;
    public static final LocalLog LOCAL_LOG;
    public static final Set RESTRICTED_TRANSPORTS_DEFAULT;
    static final String VCN_CONFIG_FILE;
    public final PersistableBundleUtils.LockingReadWriteHelper mConfigDiskRwHelper;
    public final Context mContext;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public final Looper mLooper;
    public final VcnNetworkProvider mNetworkProvider;
    public final TelephonySubscriptionTracker mTelephonySubscriptionTracker;
    public final VcnSubscriptionTrackerCallback mTelephonySubscriptionTrackerCb;
    public final VcnBroadcastReceiver mVcnBroadcastReceiver;
    public final TrackingNetworkCallback mTrackingNetworkCallback = new TrackingNetworkCallback();
    public final Map mConfigs = new ArrayMap();
    public final Map mVcns = new ArrayMap();
    public TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot =
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot.EMPTY_SNAPSHOT;
    public final Object mLock = new Object();
    public final Map mRegisteredPolicyListeners = new ArrayMap();
    public final Map mRegisteredStatusCallbacks = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {
        public HandlerThread mHandlerThread;

        public Set getRestrictedTransportsFromCarrierConfig(
                ParcelUuid parcelUuid,
                TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                        telephonySubscriptionSnapshot) {
            PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp;
            if ((Build.IS_ENG || Build.IS_USERDEBUG)
                    && (carrierConfigForSubGrp =
                                    telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(
                                            parcelUuid))
                            != null) {
                int[] array =
                        VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT.stream()
                                .mapToInt(
                                        new VcnManagementService$Dependencies$$ExternalSyntheticLambda0())
                                .toArray();
                int[] intArray =
                        carrierConfigForSubGrp.mBundle.getIntArray("vcn_restricted_transports");
                if (intArray != null) {
                    array = intArray;
                }
                ArraySet arraySet = new ArraySet();
                for (int i : array) {
                    arraySet.add(Integer.valueOf(i));
                }
                return arraySet;
            }
            return VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyListenerBinderDeath implements IBinder.DeathRecipient {
        public final IVcnUnderlyingNetworkPolicyListener mListener;

        public PolicyListenerBinderDeath(
                IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
            this.mListener = iVcnUnderlyingNetworkPolicyListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Set set = VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            Log.e(
                    "VcnManagementService",
                    "app died without removing VcnUnderlyingNetworkPolicyListener");
            VcnManagementService.this.removeVcnUnderlyingNetworkPolicyListener(this.mListener);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrackingNetworkCallback extends ConnectivityManager.NetworkCallback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Object mLockObject = new Object();
        public final Map mCaps = new ArrayMap();
        public final Map mLinkProperties = new ArrayMap();

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            synchronized (this.mLockObject) {
                ((ArrayMap) this.mCaps).put(network, networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            synchronized (this.mLockObject) {
                ((ArrayMap) this.mLinkProperties).put(network, linkProperties);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            synchronized (this.mLockObject) {
                ((ArrayMap) this.mCaps).remove(network);
                ((ArrayMap) this.mLinkProperties).remove(network);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnBroadcastReceiver extends BroadcastReceiver {
        public VcnBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action;
            action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.PACKAGE_REPLACED":
                case "android.intent.action.PACKAGE_REMOVED":
                case "android.intent.action.PACKAGE_ADDED":
                    VcnManagementService.this.mTelephonySubscriptionTracker
                            .handleSubscriptionsChanged();
                    return;
                case "android.intent.action.PACKAGE_DATA_CLEARED":
                case "android.intent.action.PACKAGE_FULLY_REMOVED":
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (schemeSpecificPart == null || schemeSpecificPart.isEmpty()) {
                        VcnManagementService vcnManagementService = VcnManagementService.this;
                        String concat =
                                "Package name was empty or null for intent with action"
                                        .concat(action);
                        vcnManagementService.getClass();
                        VcnManagementService.logWtf(concat);
                        return;
                    }
                    synchronized (VcnManagementService.this.mLock) {
                        try {
                            ArrayList arrayList = new ArrayList();
                            for (Map.Entry entry :
                                    ((ArrayMap) VcnManagementService.this.mConfigs).entrySet()) {
                                if (schemeSpecificPart.equals(
                                        ((VcnConfig) entry.getValue())
                                                .getProvisioningPackageName())) {
                                    arrayList.add((ParcelUuid) entry.getKey());
                                }
                            }
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                ParcelUuid parcelUuid = (ParcelUuid) it.next();
                                VcnManagementService vcnManagementService2 =
                                        VcnManagementService.this;
                                ((ArrayMap) vcnManagementService2.mConfigs).remove(parcelUuid);
                                boolean containsKey =
                                        ((ArrayMap) vcnManagementService2.mVcns)
                                                .containsKey(parcelUuid);
                                vcnManagementService2.stopVcnLocked(parcelUuid);
                                if (containsKey) {
                                    vcnManagementService2
                                            .notifyAllPermissionedStatusCallbacksLocked(
                                                    parcelUuid, 0);
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                VcnManagementService.this.writeConfigsToDiskLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    Set set = VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
                    Slog.wtf("VcnManagementService", "received unexpected intent: ".concat(action));
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface VcnCallback {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnCallbackImpl implements VcnCallback {
        public final ParcelUuid mSubGroup;

        public VcnCallbackImpl(ParcelUuid parcelUuid) {
            Objects.requireNonNull(parcelUuid, "Missing subGroup");
            this.mSubGroup = parcelUuid;
        }

        public final void onGatewayConnectionError(
                final int i, final String str, final String str2, final String str3) {
            synchronized (VcnManagementService.this.mLock) {
                try {
                    if (((ArrayMap) VcnManagementService.this.mVcns).containsKey(this.mSubGroup)) {
                        for (final VcnStatusCallbackInfo vcnStatusCallbackInfo :
                                ((ArrayMap) VcnManagementService.this.mRegisteredStatusCallbacks)
                                        .values()) {
                            if (VcnManagementService.this.isCallbackPermissioned(
                                    vcnStatusCallbackInfo, this.mSubGroup)) {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils.ThrowingRunnable() { // from class:
                                            // com.android.server.VcnManagementService$VcnCallbackImpl$$ExternalSyntheticLambda0
                                            public final void runOrThrow() {
                                                VcnManagementService.VcnCallbackImpl
                                                        vcnCallbackImpl =
                                                                VcnManagementService.VcnCallbackImpl
                                                                        .this;
                                                VcnManagementService.VcnStatusCallbackInfo
                                                        vcnStatusCallbackInfo2 =
                                                                vcnStatusCallbackInfo;
                                                String str4 = str;
                                                int i2 = i;
                                                String str5 = str2;
                                                String str6 = str3;
                                                vcnCallbackImpl.getClass();
                                                try {
                                                    vcnStatusCallbackInfo2.mCallback
                                                            .onGatewayConnectionError(
                                                                    str4, i2, str5, str6);
                                                } catch (RemoteException e) {
                                                    VcnManagementService.this.getClass();
                                                    Slog.d(
                                                            "VcnManagementService",
                                                            "VcnStatusCallback threw on VCN status"
                                                                + " change",
                                                            e);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class VcnStatusCallbackInfo implements IBinder.DeathRecipient {
        public final IVcnStatusCallback mCallback;
        public final String mPkgName;
        public final ParcelUuid mSubGroup;

        public VcnStatusCallbackInfo(
                ParcelUuid parcelUuid, IVcnStatusCallback iVcnStatusCallback, String str) {
            this.mSubGroup = parcelUuid;
            this.mCallback = iVcnStatusCallback;
            this.mPkgName = str;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Set set = VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            Log.e("VcnManagementService", "app died without unregistering VcnStatusCallback");
            VcnManagementService.this.unregisterVcnStatusCallback(this.mCallback);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnSubscriptionTrackerCallback
            implements TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback {
        public VcnSubscriptionTrackerCallback() {}

        /* JADX WARN: Removed duplicated region for block: B:38:0x0119  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x011c A[Catch: all -> 0x0074, TryCatch #0 {all -> 0x0074, blocks: (B:4:0x0008, B:5:0x002f, B:7:0x0035, B:10:0x0051, B:13:0x005c, B:15:0x0068, B:16:0x0077, B:22:0x0087, B:23:0x0097, B:25:0x009e, B:27:0x00c7, B:31:0x00d7, B:44:0x00e4, B:36:0x0111, B:39:0x011e, B:42:0x011c, B:50:0x012c, B:52:0x0142, B:54:0x014a, B:55:0x014f), top: B:3:0x0008 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onNewSnapshot(
                com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                        r12) {
            /*
                Method dump skipped, instructions count: 339
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.VcnManagementService.VcnSubscriptionTrackerCallback.onNewSnapshot(com.android.server.vcn.TelephonySubscriptionTracker$TelephonySubscriptionSnapshot):void");
        }
    }

    /* renamed from: -$$Nest$mgarbageCollectAndWriteVcnConfigsLocked, reason: not valid java name */
    public static void m104$$Nest$mgarbageCollectAndWriteVcnConfigsLocked(
            VcnManagementService vcnManagementService) {
        SubscriptionManager subscriptionManager =
                (SubscriptionManager)
                        vcnManagementService.mContext.getSystemService(SubscriptionManager.class);
        Iterator it = ((ArrayMap) vcnManagementService.mConfigs).keySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            List<SubscriptionInfo> subscriptionsInGroup =
                    subscriptionManager.getSubscriptionsInGroup((ParcelUuid) it.next());
            if (subscriptionsInGroup == null || subscriptionsInGroup.isEmpty()) {
                it.remove();
                z = true;
            }
        }
        if (z) {
            vcnManagementService.writeConfigsToDiskLocked();
        }
    }

    /* renamed from: -$$Nest$mgetSubGroupToSubIdMappings, reason: not valid java name */
    public static Map m105$$Nest$mgetSubGroupToSubIdMappings(
            VcnManagementService vcnManagementService,
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot) {
        vcnManagementService.getClass();
        ArrayMap arrayMap = new ArrayMap();
        for (ParcelUuid parcelUuid : ((ArrayMap) vcnManagementService.mVcns).keySet()) {
            arrayMap.put(parcelUuid, telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid));
        }
        return arrayMap;
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        DUMP_TIMEOUT_MILLIS = timeUnit.toMillis(5L);
        RESTRICTED_TRANSPORTS_DEFAULT = Collections.singleton(1);
        LOCAL_LOG = new LocalLog(512);
        VCN_CONFIG_FILE =
                new File(Environment.getDataSystemDirectory(), "vcn/configs.xml").getPath();
        CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS = timeUnit.toMillis(30L);
    }

    public VcnManagementService(Context context, Dependencies dependencies) {
        Objects.requireNonNull(context, "Missing context");
        Context createAttributionContext = context.createAttributionContext("VCN");
        this.mContext = createAttributionContext;
        Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDeps = dependencies;
        if (dependencies.mHandlerThread == null) {
            synchronized (dependencies) {
                try {
                    if (dependencies.mHandlerThread == null) {
                        HandlerThread handlerThread = new HandlerThread("VcnManagementService");
                        dependencies.mHandlerThread = handlerThread;
                        handlerThread.start();
                    }
                } finally {
                }
            }
        }
        Looper looper = dependencies.mHandlerThread.getLooper();
        this.mLooper = looper;
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mNetworkProvider =
                new VcnNetworkProvider(
                        createAttributionContext, looper, new VcnNetworkProvider.Dependencies());
        VcnSubscriptionTrackerCallback vcnSubscriptionTrackerCallback =
                new VcnSubscriptionTrackerCallback();
        this.mTelephonySubscriptionTrackerCb = vcnSubscriptionTrackerCallback;
        this.mTelephonySubscriptionTracker =
                new TelephonySubscriptionTracker(
                        createAttributionContext,
                        new Handler(looper),
                        vcnSubscriptionTrackerCallback,
                        new TelephonySubscriptionTracker.Dependencies());
        this.mConfigDiskRwHelper =
                new PersistableBundleUtils.LockingReadWriteHelper(VCN_CONFIG_FILE);
        VcnBroadcastReceiver vcnBroadcastReceiver = new VcnBroadcastReceiver();
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.PACKAGE_ADDED",
                        "android.intent.action.PACKAGE_REPLACED",
                        "android.intent.action.PACKAGE_REMOVED",
                        "android.intent.action.PACKAGE_DATA_CLEARED",
                        "android.intent.action.PACKAGE_FULLY_REMOVED");
        m.addDataScheme("package");
        createAttributionContext.registerReceiver(vcnBroadcastReceiver, m, null, handler);
        handler.post(
                new Runnable() { // from class:
                    // com.android.server.VcnManagementService$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PersistableBundle readFromDisk;
                        VcnManagementService vcnManagementService = VcnManagementService.this;
                        vcnManagementService.getClass();
                        try {
                            readFromDisk = vcnManagementService.mConfigDiskRwHelper.readFromDisk();
                        } catch (IOException e) {
                            Slog.e(
                                    "VcnManagementService",
                                    "Failed to read configs from disk; retrying",
                                    e);
                            VcnManagementService.LOCAL_LOG.log(
                                    "[ERR ] [VcnManagementService] Failed to read configs from"
                                        + " disk; retrying"
                                            + e);
                            try {
                                readFromDisk =
                                        vcnManagementService.mConfigDiskRwHelper.readFromDisk();
                            } catch (IOException e2) {
                                Slog.wtf(
                                        "VcnManagementService",
                                        "Failed to read configs from disk",
                                        e2);
                                VcnManagementService.LOCAL_LOG.log(
                                        "[WTF ] [VcnManagementService] Failed to read configs from"
                                            + " disk"
                                                + e2);
                                return;
                            }
                        }
                        if (readFromDisk != null) {
                            int i = readFromDisk.getInt("COLLECTION_LENGTH");
                            LinkedHashMap linkedHashMap = new LinkedHashMap(i);
                            for (int i2 = 0; i2 < i; i2++) {
                                linkedHashMap.put(
                                        ParcelUuid.fromString(
                                                readFromDisk
                                                        .getPersistableBundle(
                                                                String.format(
                                                                        "MAP_KEY_%d",
                                                                        Integer.valueOf(i2)))
                                                        .getString("PARCEL_UUID")),
                                        new VcnConfig(
                                                readFromDisk.getPersistableBundle(
                                                        String.format(
                                                                "MAP_VALUE_%d",
                                                                Integer.valueOf(i2)))));
                            }
                            synchronized (vcnManagementService.mLock) {
                                try {
                                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                                        if (!((ArrayMap) vcnManagementService.mConfigs)
                                                .containsKey(entry.getKey())) {
                                            ((ArrayMap) vcnManagementService.mConfigs)
                                                    .put(
                                                            (ParcelUuid) entry.getKey(),
                                                            (VcnConfig) entry.getValue());
                                        }
                                    }
                                    vcnManagementService.mTelephonySubscriptionTrackerCb
                                            .onNewSnapshot(vcnManagementService.mLastSnapshot);
                                } finally {
                                }
                            }
                        }
                    }
                });
    }

    public static VcnManagementService create(Context context) {
        return new VcnManagementService(context, new Dependencies());
    }

    public static boolean isActiveSubGroup(
            ParcelUuid parcelUuid,
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot) {
        if (parcelUuid == null || telephonySubscriptionSnapshot == null) {
            return false;
        }
        SubscriptionInfo subscriptionInfo =
                (SubscriptionInfo)
                        telephonySubscriptionSnapshot.mSubIdToInfoMap.get(
                                Integer.valueOf(telephonySubscriptionSnapshot.mActiveDataSubId));
        return parcelUuid.equals(subscriptionInfo == null ? null : subscriptionInfo.getGroupUuid());
    }

    public static void logInfo(String str) {
        Slog.i("VcnManagementService", str);
        LOCAL_LOG.log("[INFO] [VcnManagementService] " + str);
    }

    public static void logWtf(String str) {
        Slog.wtf("VcnManagementService", str);
        LOCAL_LOG.log("[WTF] [VcnManagementService] " + str);
    }

    public final void addVcnUnderlyingNetworkPolicyListener(
            IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        PermissionUtils.enforceAnyPermissionOf(
                this.mContext,
                new String[] {
                    "android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"
                });
        Binder.withCleanCallingIdentity(
                new VcnManagementService$$ExternalSyntheticLambda1(
                        this, iVcnUnderlyingNetworkPolicyListener, 1));
    }

    public final void clearVcnConfig(ParcelUuid parcelUuid, String str) {
        Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        Objects.requireNonNull(str, "opPkgName was null");
        logInfo("VCN config cleared for subGrp: " + parcelUuid);
        AppOpsManager appOpsManager =
                (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mDeps.getClass();
        appOpsManager.checkPackage(Binder.getCallingUid(), str);
        enforcePrimaryUser();
        if (!isProvisioningPackageForConfig(parcelUuid, str)) {
            enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
        }
        Binder.withCleanCallingIdentity(
                new VcnManagementService$$ExternalSyntheticLambda2(this, parcelUuid, 0));
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.DUMP", "VcnManagementService");
        final IndentingPrintWriter indentingPrintWriter =
                new IndentingPrintWriter(printWriter, "| ");
        this.mHandler.runWithScissors(
                new Runnable() { // from class:
                                 // com.android.server.VcnManagementService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        VcnManagementService vcnManagementService = VcnManagementService.this;
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        VcnNetworkProvider vcnNetworkProvider =
                                vcnManagementService.mNetworkProvider;
                        vcnNetworkProvider.getClass();
                        indentingPrintWriter2.println("VcnNetworkProvider:");
                        indentingPrintWriter2.increaseIndent();
                        indentingPrintWriter2.println("mListeners:");
                        indentingPrintWriter2.increaseIndent();
                        Iterator it = ((ArraySet) vcnNetworkProvider.mListeners).iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter2.println(
                                    (VcnNetworkProvider.NetworkRequestListener) it.next());
                        }
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("mRequests:");
                        indentingPrintWriter2.increaseIndent();
                        Iterator it2 = ((ArraySet) vcnNetworkProvider.mRequests).iterator();
                        while (it2.hasNext()) {
                            indentingPrintWriter2.println((NetworkRequest) it2.next());
                        }
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                        VcnManagementService.TrackingNetworkCallback trackingNetworkCallback =
                                vcnManagementService.mTrackingNetworkCallback;
                        trackingNetworkCallback.getClass();
                        indentingPrintWriter2.println("TrackingNetworkCallback:");
                        indentingPrintWriter2.increaseIndent();
                        indentingPrintWriter2.println("mCaps:");
                        indentingPrintWriter2.increaseIndent();
                        synchronized (trackingNetworkCallback.mCaps) {
                            try {
                                for (Map.Entry entry :
                                        ((ArrayMap) trackingNetworkCallback.mCaps).entrySet()) {
                                    indentingPrintWriter2.println(
                                            entry.getKey() + ": " + entry.getValue());
                                }
                            } finally {
                            }
                        }
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                        synchronized (vcnManagementService.mLock) {
                            try {
                                vcnManagementService.mLastSnapshot.dump(indentingPrintWriter2);
                                indentingPrintWriter2.println();
                                indentingPrintWriter2.println("mConfigs:");
                                indentingPrintWriter2.increaseIndent();
                                for (Map.Entry entry2 :
                                        ((ArrayMap) vcnManagementService.mConfigs).entrySet()) {
                                    indentingPrintWriter2.println(
                                            entry2.getKey()
                                                    + ": "
                                                    + ((VcnConfig) entry2.getValue())
                                                            .getProvisioningPackageName());
                                }
                                indentingPrintWriter2.decreaseIndent();
                                indentingPrintWriter2.println();
                                indentingPrintWriter2.println("mVcns:");
                                indentingPrintWriter2.increaseIndent();
                                Iterator it3 =
                                        ((ArrayMap) vcnManagementService.mVcns).values().iterator();
                                while (it3.hasNext()) {
                                    ((Vcn) it3.next()).dump(indentingPrintWriter2);
                                }
                                indentingPrintWriter2.decreaseIndent();
                                indentingPrintWriter2.println();
                            } finally {
                            }
                        }
                        indentingPrintWriter2.println("Local log:");
                        indentingPrintWriter2.increaseIndent();
                        VcnManagementService.LOCAL_LOG.dump(indentingPrintWriter2);
                        indentingPrintWriter2.decreaseIndent();
                        indentingPrintWriter2.println();
                    }
                },
                DUMP_TIMEOUT_MILLIS);
    }

    public final void enforceCallingUserAndCarrierPrivilege(
            final ParcelUuid parcelUuid, String str) {
        enforcePrimaryUser();
        final SubscriptionManager subscriptionManager =
                (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        final ArrayList arrayList = new ArrayList();
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.VcnManagementService$$ExternalSyntheticLambda12
                    public final void runOrThrow() {
                        VcnManagementService vcnManagementService = VcnManagementService.this;
                        SubscriptionManager subscriptionManager2 = subscriptionManager;
                        ParcelUuid parcelUuid2 = parcelUuid;
                        List list = arrayList;
                        vcnManagementService.getClass();
                        List<SubscriptionInfo> subscriptionsInGroup =
                                subscriptionManager2.getSubscriptionsInGroup(parcelUuid2);
                        if (subscriptionsInGroup == null) {
                            VcnManagementService.logWtf(
                                    "Received null from getSubscriptionsInGroup");
                            subscriptionsInGroup = Collections.emptyList();
                        }
                        list.addAll(subscriptionsInGroup);
                    }
                });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            TelephonyManager createForSubscriptionId =
                    ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                            .createForSubscriptionId(subscriptionInfo.getSubscriptionId());
            if (SubscriptionManager.isValidSlotIndex(subscriptionInfo.getSimSlotIndex())
                    && createForSubscriptionId.checkCarrierPrivilegesForPackage(str) == 1) {
                return;
            }
        }
        throw new SecurityException(
                "Carrier privilege required for subscription group to set VCN Config");
    }

    public final void enforcePrimaryUser() {
        this.mDeps.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000) {
            throw new IllegalStateException(
                    "Calling identity was System Server. Was Binder calling identity cleared?");
        }
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(callingUid);
        if (Flags.enforceMainUser()) {
            Binder.withCleanCallingIdentity(
                    new VcnManagementService$$ExternalSyntheticLambda2(
                            (UserManager) this.mContext.getSystemService(UserManager.class),
                            userHandleForUid));
        } else if (!userHandleForUid.isSystem()) {
            throw new SecurityException(
                    "VcnManagementService can only be used by callers running as the primary user");
        }
    }

    public Map getAllStatusCallbacks() {
        Map unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(this.mRegisteredStatusCallbacks);
        }
        return unmodifiableMap;
    }

    public Map getAllVcns() {
        Map unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(this.mVcns);
        }
        return unmodifiableMap;
    }

    public Map getConfigs() {
        Map unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(this.mConfigs);
        }
        return unmodifiableMap;
    }

    public final List getConfiguredSubscriptionGroups(String str) {
        Objects.requireNonNull(str, "opPkgName was null");
        AppOpsManager appOpsManager =
                (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mDeps.getClass();
        appOpsManager.checkPackage(Binder.getCallingUid(), str);
        enforcePrimaryUser();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                for (ParcelUuid parcelUuid : ((ArrayMap) this.mConfigs).keySet()) {
                    if (!this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(
                                    parcelUuid, str)
                            && !isProvisioningPackageForConfig(parcelUuid, str)) {}
                    arrayList.add(parcelUuid);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(
            final NetworkCapabilities networkCapabilities, final LinkProperties linkProperties) {
        Objects.requireNonNull(networkCapabilities, "networkCapabilities was null");
        Objects.requireNonNull(linkProperties, "linkProperties was null");
        PermissionUtils.enforceAnyPermissionOf(
                this.mContext,
                new String[] {
                    "android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"
                });
        if (this.mContext.checkCallingOrSelfPermission("android.permission.NETWORK_FACTORY") == 0
                || networkCapabilities.hasTransport(7)) {
            return (VcnUnderlyingNetworkPolicy)
                    Binder.withCleanCallingIdentity(
                            new FunctionalUtils.ThrowingSupplier() { // from class:
                                // com.android.server.VcnManagementService$$ExternalSyntheticLambda7
                                public final Object getOrThrow() {
                                    TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                                            telephonySubscriptionSnapshot;
                                    ParcelUuid groupUuid;
                                    boolean z;
                                    boolean z2;
                                    boolean z3;
                                    boolean z4;
                                    VcnManagementService vcnManagementService =
                                            VcnManagementService.this;
                                    NetworkCapabilities networkCapabilities2 = networkCapabilities;
                                    LinkProperties linkProperties2 = linkProperties;
                                    vcnManagementService.getClass();
                                    NetworkCapabilities networkCapabilities3 =
                                            new NetworkCapabilities(networkCapabilities2);
                                    synchronized (vcnManagementService.mLock) {
                                        telephonySubscriptionSnapshot =
                                                vcnManagementService.mLastSnapshot;
                                    }
                                    loop0:
                                    while (true) {
                                        for (Integer num :
                                                networkCapabilities3.getSubscriptionIds()) {
                                            num.intValue();
                                            if (groupUuid != null) {
                                                if (!groupUuid.equals(
                                                        telephonySubscriptionSnapshot
                                                                        .mSubIdToInfoMap
                                                                        .containsKey(num)
                                                                ? ((SubscriptionInfo)
                                                                                telephonySubscriptionSnapshot
                                                                                        .mSubIdToInfoMap
                                                                                        .get(num))
                                                                        .getGroupUuid()
                                                                : null)) {
                                                    VcnManagementService.logWtf(
                                                            "Got multiple subscription groups for a"
                                                                + " single network");
                                                }
                                            }
                                            groupUuid =
                                                    telephonySubscriptionSnapshot.mSubIdToInfoMap
                                                                    .containsKey(num)
                                                            ? ((SubscriptionInfo)
                                                                            telephonySubscriptionSnapshot
                                                                                    .mSubIdToInfoMap
                                                                                    .get(num))
                                                                    .getGroupUuid()
                                                            : null;
                                        }
                                    }
                                    synchronized (vcnManagementService.mLock) {
                                        try {
                                            Vcn vcn =
                                                    (Vcn)
                                                            ((ArrayMap) vcnManagementService.mVcns)
                                                                    .get(groupUuid);
                                            VcnConfig vcnConfig =
                                                    (VcnConfig)
                                                            ((ArrayMap)
                                                                            vcnManagementService
                                                                                    .mConfigs)
                                                                    .get(groupUuid);
                                            z = true;
                                            z2 = false;
                                            if (vcn != null) {
                                                if (vcnConfig == null) {
                                                    VcnManagementService.logWtf(
                                                            "Vcn instance exists but VcnConfig does"
                                                                + " not for "
                                                                    + groupUuid);
                                                }
                                                z4 = vcn.mCurrentStatus == 2;
                                                VcnManagementService.Dependencies dependencies =
                                                        vcnManagementService.mDeps;
                                                TelephonySubscriptionTracker
                                                                .TelephonySubscriptionSnapshot
                                                        telephonySubscriptionSnapshot2 =
                                                                vcnManagementService.mLastSnapshot;
                                                dependencies.getClass();
                                                ArraySet arraySet = new ArraySet();
                                                arraySet.addAll(
                                                        vcnConfig
                                                                .getRestrictedUnderlyingNetworkTransports());
                                                arraySet.addAll(
                                                        dependencies
                                                                .getRestrictedTransportsFromCarrierConfig(
                                                                        groupUuid,
                                                                        telephonySubscriptionSnapshot2));
                                                Iterator it = arraySet.iterator();
                                                z3 = false;
                                                while (true) {
                                                    if (!it.hasNext()) {
                                                        break;
                                                    }
                                                    int intValue = ((Integer) it.next()).intValue();
                                                    if (networkCapabilities3.hasTransport(
                                                            intValue)) {
                                                        if (intValue != 0 && intValue != 7) {
                                                            z3 = true;
                                                            break;
                                                        }
                                                        z3 |= vcn.mCurrentStatus == 2;
                                                    }
                                                }
                                            } else {
                                                z3 = false;
                                                z4 = false;
                                            }
                                        } finally {
                                        }
                                    }
                                    NetworkCapabilities.Builder builder =
                                            new NetworkCapabilities.Builder(networkCapabilities3);
                                    if (z4) {
                                        builder.removeCapability(28);
                                    } else {
                                        builder.addCapability(28);
                                    }
                                    if (z3) {
                                        builder.removeCapability(13);
                                    }
                                    NetworkCapabilities build = builder.build();
                                    VcnManagementService.TrackingNetworkCallback
                                            trackingNetworkCallback =
                                                    vcnManagementService.mTrackingNetworkCallback;
                                    int i = VcnManagementService.TrackingNetworkCallback.$r8$clinit;
                                    trackingNetworkCallback.getClass();
                                    if (build.getSubscriptionIds() != null) {
                                        synchronized (trackingNetworkCallback.mLockObject) {
                                            try {
                                                Iterator it2 =
                                                        ((ArrayMap)
                                                                        trackingNetworkCallback
                                                                                .mLinkProperties)
                                                                .entrySet()
                                                                .iterator();
                                                while (true) {
                                                    if (!it2.hasNext()) {
                                                        break;
                                                    }
                                                    Map.Entry entry = (Map.Entry) it2.next();
                                                    if (linkProperties2.getInterfaceName() != null
                                                            && !linkProperties2
                                                                    .getInterfaceName()
                                                                    .isEmpty()
                                                            && Objects.equals(
                                                                    linkProperties2
                                                                            .getInterfaceName(),
                                                                    ((LinkProperties)
                                                                                    entry
                                                                                            .getValue())
                                                                            .getInterfaceName())) {
                                                        if (((NetworkCapabilities)
                                                                                ((ArrayMap)
                                                                                                trackingNetworkCallback
                                                                                                        .mCaps)
                                                                                        .get(
                                                                                                entry
                                                                                                        .getKey()))
                                                                        .hasCapability(13)
                                                                == build.hasCapability(13)) {
                                                            z = false;
                                                        }
                                                        z2 = z;
                                                    }
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                    VcnUnderlyingNetworkPolicy vcnUnderlyingNetworkPolicy =
                                            new VcnUnderlyingNetworkPolicy(z2, build);
                                    Objects.toString(networkCapabilities2);
                                    Objects.toString(linkProperties2);
                                    vcnUnderlyingNetworkPolicy.toString();
                                    return vcnUnderlyingNetworkPolicy;
                                }
                            });
        }
        throw new IllegalStateException(
                "NetworkCapabilities must be for Test Network if using permission"
                    + " MANAGE_TEST_NETWORKS");
    }

    public final boolean isCallbackPermissioned(
            VcnStatusCallbackInfo vcnStatusCallbackInfo, ParcelUuid parcelUuid) {
        return parcelUuid.equals(vcnStatusCallbackInfo.mSubGroup)
                && this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(
                        parcelUuid, vcnStatusCallbackInfo.mPkgName);
    }

    public final boolean isProvisioningPackageForConfig(ParcelUuid parcelUuid, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                VcnConfig vcnConfig = (VcnConfig) ((ArrayMap) this.mConfigs).get(parcelUuid);
                if (vcnConfig == null || !str.equals(vcnConfig.getProvisioningPackageName())) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void notifyAllPermissionedStatusCallbacksLocked(
            ParcelUuid parcelUuid, final int i) {
        for (final VcnStatusCallbackInfo vcnStatusCallbackInfo :
                ((ArrayMap) this.mRegisteredStatusCallbacks).values()) {
            if (isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils
                                .ThrowingRunnable() { // from class:
                                                      // com.android.server.VcnManagementService$$ExternalSyntheticLambda13
                            public final void runOrThrow() {
                                VcnManagementService vcnManagementService =
                                        VcnManagementService.this;
                                VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo2 =
                                        vcnStatusCallbackInfo;
                                int i2 = i;
                                vcnManagementService.getClass();
                                try {
                                    vcnStatusCallbackInfo2.mCallback.onVcnStatusChanged(i2);
                                } catch (RemoteException e) {
                                    Slog.d(
                                            "VcnManagementService",
                                            "VcnStatusCallback threw on VCN status change",
                                            e);
                                }
                            }
                        });
            }
        }
    }

    public final void notifyAllPolicyListenersLocked() {
        Iterator it = ((ArrayMap) this.mRegisteredPolicyListeners).values().iterator();
        while (it.hasNext()) {
            Binder.withCleanCallingIdentity(
                    new VcnManagementService$$ExternalSyntheticLambda2(
                            this, (PolicyListenerBinderDeath) it.next(), 2));
        }
    }

    public final void registerVcnStatusCallback(
            ParcelUuid parcelUuid, IVcnStatusCallback iVcnStatusCallback, String str) {
        this.mDeps.getClass();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Objects.requireNonNull(parcelUuid, "subGroup must not be null");
            Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            Objects.requireNonNull(str, "opPkgName must not be null");
            ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                    .checkPackage(callingUid, str);
            IBinder asBinder = iVcnStatusCallback.asBinder();
            VcnStatusCallbackInfo vcnStatusCallbackInfo =
                    new VcnStatusCallbackInfo(parcelUuid, iVcnStatusCallback, str);
            int i = 0;
            try {
                asBinder.linkToDeath(vcnStatusCallbackInfo, 0);
                synchronized (this.mLock) {
                    if (((ArrayMap) this.mRegisteredStatusCallbacks).containsKey(asBinder)) {
                        throw new IllegalStateException(
                                "Attempting to register a callback that is already in use");
                    }
                    ((ArrayMap) this.mRegisteredStatusCallbacks)
                            .put(asBinder, vcnStatusCallbackInfo);
                    VcnConfig vcnConfig = (VcnConfig) ((ArrayMap) this.mConfigs).get(parcelUuid);
                    Vcn vcn = (Vcn) ((ArrayMap) this.mVcns).get(parcelUuid);
                    int i2 = vcn == null ? 0 : vcn.mCurrentStatus;
                    if (vcnConfig != null
                            && isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                        if (vcn == null) {
                            i = 1;
                        } else {
                            if (i2 != 2 && i2 != 3) {
                                logWtf("Unknown VCN status: " + i2);
                            }
                            i = i2;
                        }
                    }
                    try {
                        iVcnStatusCallback.onVcnStatusChanged(i);
                    } catch (RemoteException e) {
                        Slog.d(
                                "VcnManagementService",
                                "VcnStatusCallback threw on VCN status change",
                                e);
                    }
                }
            } catch (RemoteException unused) {
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeVcnUnderlyingNetworkPolicyListener(
            IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        PermissionUtils.enforceAnyPermissionOf(
                this.mContext,
                new String[] {
                    "android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"
                });
        Binder.withCleanCallingIdentity(
                new VcnManagementService$$ExternalSyntheticLambda1(
                        this, iVcnUnderlyingNetworkPolicyListener, 0));
    }

    public void setLastSnapshot(
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot) {
        Objects.requireNonNull(telephonySubscriptionSnapshot);
        this.mLastSnapshot = telephonySubscriptionSnapshot;
    }

    public final void setVcnConfig(
            final ParcelUuid parcelUuid, final VcnConfig vcnConfig, String str) {
        Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        Objects.requireNonNull(vcnConfig, "config was null");
        Objects.requireNonNull(str, "opPkgName was null");
        if (!vcnConfig.getProvisioningPackageName().equals(str)) {
            throw new IllegalArgumentException("Mismatched caller and VcnConfig creator");
        }
        logInfo("VCN config updated for subGrp: " + parcelUuid);
        AppOpsManager appOpsManager =
                (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mDeps.getClass();
        appOpsManager.checkPackage(Binder.getCallingUid(), vcnConfig.getProvisioningPackageName());
        if (vcnConfig.isTestModeProfile()) {
            this.mContext.enforceCallingPermission(
                    "android.permission.MANAGE_TEST_NETWORKS",
                    "Test-mode require the MANAGE_TEST_NETWORKS permission");
        }
        enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.VcnManagementService$$ExternalSyntheticLambda5
                    public final void runOrThrow() {
                        VcnManagementService vcnManagementService = VcnManagementService.this;
                        ParcelUuid parcelUuid2 = parcelUuid;
                        VcnConfig vcnConfig2 = vcnConfig;
                        synchronized (vcnManagementService.mLock) {
                            ((ArrayMap) vcnManagementService.mConfigs).put(parcelUuid2, vcnConfig2);
                            vcnManagementService.startOrUpdateVcnLocked(parcelUuid2, vcnConfig2);
                            vcnManagementService.writeConfigsToDiskLocked();
                        }
                    }
                });
    }

    public final void startOrUpdateVcnLocked(ParcelUuid parcelUuid, VcnConfig vcnConfig) {
        Slog.d("VcnManagementService", "Starting or updating VCN config for subGrp: " + parcelUuid);
        if (!((ArrayMap) this.mVcns).containsKey(parcelUuid)) {
            if (isActiveSubGroup(parcelUuid, this.mLastSnapshot)) {
                startVcnLocked(parcelUuid, vcnConfig);
            }
        } else {
            Vcn vcn = (Vcn) ((ArrayMap) this.mVcns).get(parcelUuid);
            vcn.getClass();
            Objects.requireNonNull(vcnConfig, "Missing config");
            vcn.sendMessage(vcn.obtainMessage(0, vcnConfig));
            notifyAllPolicyListenersLocked();
        }
    }

    public final void startVcnLocked(ParcelUuid parcelUuid, VcnConfig vcnConfig) {
        logInfo("Starting VCN config for subGrp: " + parcelUuid);
        if (!((ArrayMap) this.mVcns).isEmpty()) {
            Iterator it = ((ArrayMap) this.mVcns).keySet().iterator();
            while (it.hasNext()) {
                stopVcnLocked((ParcelUuid) it.next());
            }
        }
        VcnCallbackImpl vcnCallbackImpl = new VcnCallbackImpl(parcelUuid);
        Dependencies dependencies = this.mDeps;
        Context context = this.mContext;
        Looper looper = this.mLooper;
        VcnNetworkProvider vcnNetworkProvider = this.mNetworkProvider;
        boolean isTestModeProfile = vcnConfig.isTestModeProfile();
        dependencies.getClass();
        VcnContext vcnContext =
                new VcnContext(context, looper, vcnNetworkProvider, isTestModeProfile);
        Dependencies dependencies2 = this.mDeps;
        TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot =
                this.mLastSnapshot;
        dependencies2.getClass();
        ((ArrayMap) this.mVcns)
                .put(
                        parcelUuid,
                        new Vcn(
                                vcnContext,
                                parcelUuid,
                                vcnConfig,
                                telephonySubscriptionSnapshot,
                                vcnCallbackImpl,
                                new Vcn.Dependencies()));
        notifyAllPolicyListenersLocked();
        notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 2);
    }

    public final void stopVcnLocked(ParcelUuid parcelUuid) {
        logInfo("Stopping VCN config for subGrp: " + parcelUuid);
        Vcn vcn = (Vcn) ((ArrayMap) this.mVcns).get(parcelUuid);
        if (vcn == null) {
            return;
        }
        vcn.sendMessageAtFrontOfQueue(vcn.obtainMessage(100));
        ((ArrayMap) this.mVcns).remove(parcelUuid);
        notifyAllPolicyListenersLocked();
    }

    public final void systemReady() {
        VcnNetworkProvider vcnNetworkProvider = this.mNetworkProvider;
        ((ConnectivityManager)
                        vcnNetworkProvider.mContext.getSystemService(ConnectivityManager.class))
                .registerNetworkProvider(vcnNetworkProvider);
        VcnNetworkProvider.Dependencies dependencies = vcnNetworkProvider.mDeps;
        List list = Vcn.CAPS_REQUIRING_MOBILE_DATA;
        NetworkScore build =
                new NetworkScore.Builder().setLegacyInt(52).setTransportPrimary(true).build();
        NetworkCapabilities.Builder addCapability =
                new NetworkCapabilities.Builder()
                        .addTransportType(0)
                        .addCapability(14)
                        .addCapability(13)
                        .addCapability(15)
                        .addCapability(28);
        Iterator it = VcnGatewayConnectionConfig.ALLOWED_CAPABILITIES.iterator();
        while (it.hasNext()) {
            addCapability.addCapability(((Integer) it.next()).intValue());
        }
        NetworkCapabilities build2 = addCapability.build();
        HandlerExecutor handlerExecutor = new HandlerExecutor(vcnNetworkProvider.mHandler);
        VcnNetworkProvider.AnonymousClass1 anonymousClass1 =
                new NetworkProvider
                        .NetworkOfferCallback() { // from class:
                                                  // com.android.server.vcn.VcnNetworkProvider.1
                    public AnonymousClass1() {}

                    public final void onNetworkNeeded(NetworkRequest networkRequest) {
                        VcnNetworkProvider vcnNetworkProvider2 = VcnNetworkProvider.this;
                        ((ArraySet) vcnNetworkProvider2.mRequests).add(networkRequest);
                        Iterator it2 = ((ArraySet) vcnNetworkProvider2.mListeners).iterator();
                        while (it2.hasNext()) {
                            Vcn.VcnNetworkRequestListener vcnNetworkRequestListener =
                                    (Vcn.VcnNetworkRequestListener)
                                            ((NetworkRequestListener) it2.next());
                            vcnNetworkRequestListener.getClass();
                            Objects.requireNonNull(networkRequest, "Missing request");
                            Vcn vcn = Vcn.this;
                            vcn.sendMessage(vcn.obtainMessage(1, networkRequest));
                        }
                    }

                    public final void onNetworkUnneeded(NetworkRequest networkRequest) {
                        ((ArraySet) VcnNetworkProvider.this.mRequests).remove(networkRequest);
                    }
                };
        dependencies.getClass();
        vcnNetworkProvider.registerNetworkOffer(build, build2, handlerExecutor, anonymousClass1);
        ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
                .registerNetworkCallback(
                        new NetworkRequest.Builder().clearCapabilities().build(),
                        this.mTrackingNetworkCallback);
        TelephonySubscriptionTracker telephonySubscriptionTracker =
                this.mTelephonySubscriptionTracker;
        telephonySubscriptionTracker.getClass();
        Executor handlerExecutor2 = new HandlerExecutor(telephonySubscriptionTracker.mHandler);
        telephonySubscriptionTracker.mContext.registerReceiver(
                telephonySubscriptionTracker,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.telephony.action.MULTI_SIM_CONFIG_CHANGED"),
                null,
                telephonySubscriptionTracker.mHandler);
        telephonySubscriptionTracker.mSubscriptionManager.addOnSubscriptionsChangedListener(
                handlerExecutor2, telephonySubscriptionTracker.mSubscriptionChangedListener);
        telephonySubscriptionTracker.mTelephonyManager.registerTelephonyCallback(
                handlerExecutor2, telephonySubscriptionTracker.mActiveDataSubIdListener);
        CarrierConfigManager carrierConfigManager =
                telephonySubscriptionTracker.mCarrierConfigManager;
        if (carrierConfigManager != null) {
            carrierConfigManager.registerCarrierConfigChangeListener(
                    handlerExecutor2, telephonySubscriptionTracker.mCarrierConfigChangeListener);
        }
        telephonySubscriptionTracker.registerCarrierPrivilegesCallbacks();
    }

    public final void unregisterVcnStatusCallback(IVcnStatusCallback iVcnStatusCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            IBinder asBinder = iVcnStatusCallback.asBinder();
            synchronized (this.mLock) {
                try {
                    VcnStatusCallbackInfo vcnStatusCallbackInfo =
                            (VcnStatusCallbackInfo)
                                    ((ArrayMap) this.mRegisteredStatusCallbacks).remove(asBinder);
                    if (vcnStatusCallbackInfo != null) {
                        asBinder.unlinkToDeath(vcnStatusCallbackInfo, 0);
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void writeConfigsToDiskLocked() {
        try {
            this.mConfigDiskRwHelper.writeToDisk(
                    PersistableBundleUtils.fromMap(
                            this.mConfigs,
                            new VcnManagementService$$ExternalSyntheticLambda10(),
                            new VcnManagementService$$ExternalSyntheticLambda10()));
        } catch (IOException e) {
            Slog.e("VcnManagementService", "Failed to save configs to disk", e);
            LOCAL_LOG.log("[ERR ] [VcnManagementService] Failed to save configs to disk" + e);
            throw new ServiceSpecificException(0, "Failed to save configs");
        }
    }
}
