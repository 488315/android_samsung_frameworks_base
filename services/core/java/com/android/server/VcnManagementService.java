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
import android.net.NetworkRequest;
import android.net.vcn.IVcnManagementService;
import android.net.vcn.IVcnStatusCallback;
import android.net.vcn.IVcnUnderlyingNetworkPolicyListener;
import android.net.vcn.VcnConfig;
import android.net.vcn.VcnUnderlyingNetworkPolicy;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
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
import com.android.server.VcnManagementService;
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
import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public class VcnManagementService extends IVcnManagementService.Stub {
    static final long CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS;
    public static final long DUMP_TIMEOUT_MILLIS;
    public static final LocalLog LOCAL_LOG;
    public static final Set RESTRICTED_TRANSPORTS_DEFAULT;
    public static final String TAG = VcnManagementService.class.getSimpleName();
    static final String VCN_CONFIG_FILE;
    public final PersistableBundleUtils.LockingReadWriteHelper mConfigDiskRwHelper;
    public final Context mContext;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public final Looper mLooper;
    public final VcnNetworkProvider mNetworkProvider;
    public final TelephonySubscriptionTracker mTelephonySubscriptionTracker;
    public final TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback mTelephonySubscriptionTrackerCb;
    public final BroadcastReceiver mVcnBroadcastReceiver;
    public final TrackingNetworkCallback mTrackingNetworkCallback = new TrackingNetworkCallback();
    public final Map mConfigs = new ArrayMap();
    public final Map mVcns = new ArrayMap();
    public TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot = TelephonySubscriptionTracker.TelephonySubscriptionSnapshot.EMPTY_SNAPSHOT;
    public final Object mLock = new Object();
    public final Map mRegisteredPolicyListeners = new ArrayMap();
    public final Map mRegisteredStatusCallbacks = new ArrayMap();

    public interface VcnCallback {
        void onGatewayConnectionError(String str, int i, String str2, String str3);

        void onSafeModeStatusChanged(boolean z);
    }

    public final void logVdbg(String str) {
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        DUMP_TIMEOUT_MILLIS = timeUnit.toMillis(5L);
        RESTRICTED_TRANSPORTS_DEFAULT = Collections.singleton(1);
        LOCAL_LOG = new LocalLog(512);
        VCN_CONFIG_FILE = new File(Environment.getDataSystemDirectory(), "vcn/configs.xml").getPath();
        CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS = timeUnit.toMillis(30L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public VcnManagementService(Context context, Dependencies dependencies) {
        Objects.requireNonNull(context, "Missing context");
        Context createAttributionContext = context.createAttributionContext("VCN");
        this.mContext = createAttributionContext;
        Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDeps = dependencies;
        Looper looper = dependencies.getLooper();
        this.mLooper = looper;
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mNetworkProvider = new VcnNetworkProvider(createAttributionContext, looper);
        VcnSubscriptionTrackerCallback vcnSubscriptionTrackerCallback = new VcnSubscriptionTrackerCallback();
        this.mTelephonySubscriptionTrackerCb = vcnSubscriptionTrackerCallback;
        this.mTelephonySubscriptionTracker = dependencies.newTelephonySubscriptionTracker(createAttributionContext, looper, vcnSubscriptionTrackerCallback);
        this.mConfigDiskRwHelper = dependencies.newPersistableBundleLockingReadWriteHelper(VCN_CONFIG_FILE);
        VcnBroadcastReceiver vcnBroadcastReceiver = new VcnBroadcastReceiver();
        this.mVcnBroadcastReceiver = vcnBroadcastReceiver;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        createAttributionContext.registerReceiver(vcnBroadcastReceiver, intentFilter, null, handler);
        handler.post(new Runnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                VcnManagementService.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        PersistableBundle readFromDisk;
        try {
            readFromDisk = this.mConfigDiskRwHelper.readFromDisk();
        } catch (IOException e) {
            logErr("Failed to read configs from disk; retrying", e);
            try {
                readFromDisk = this.mConfigDiskRwHelper.readFromDisk();
            } catch (IOException e2) {
                logWtf("Failed to read configs from disk", e2);
                return;
            }
        }
        if (readFromDisk != null) {
            LinkedHashMap map = PersistableBundleUtils.toMap(readFromDisk, new PersistableBundleUtils.Deserializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda8
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
                public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
                    return PersistableBundleUtils.toParcelUuid(persistableBundle);
                }
            }, new PersistableBundleUtils.Deserializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda9
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
                public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
                    return new VcnConfig(persistableBundle);
                }
            });
            synchronized (this.mLock) {
                for (Map.Entry entry : map.entrySet()) {
                    if (!this.mConfigs.containsKey(entry.getKey())) {
                        this.mConfigs.put((ParcelUuid) entry.getKey(), (VcnConfig) entry.getValue());
                    }
                }
                this.mTelephonySubscriptionTrackerCb.onNewSnapshot(this.mLastSnapshot);
            }
        }
    }

    public static VcnManagementService create(Context context) {
        return new VcnManagementService(context, new Dependencies());
    }

    public class Dependencies {
        public HandlerThread mHandlerThread;

        public Looper getLooper() {
            if (this.mHandlerThread == null) {
                synchronized (this) {
                    if (this.mHandlerThread == null) {
                        HandlerThread handlerThread = new HandlerThread(VcnManagementService.TAG);
                        this.mHandlerThread = handlerThread;
                        handlerThread.start();
                    }
                }
            }
            return this.mHandlerThread.getLooper();
        }

        public TelephonySubscriptionTracker newTelephonySubscriptionTracker(Context context, Looper looper, TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback telephonySubscriptionTrackerCallback) {
            return new TelephonySubscriptionTracker(context, new Handler(looper), telephonySubscriptionTrackerCallback);
        }

        public int getBinderCallingUid() {
            return Binder.getCallingUid();
        }

        public PersistableBundleUtils.LockingReadWriteHelper newPersistableBundleLockingReadWriteHelper(String str) {
            return new PersistableBundleUtils.LockingReadWriteHelper(str);
        }

        public VcnContext newVcnContext(Context context, Looper looper, VcnNetworkProvider vcnNetworkProvider, boolean z) {
            return new VcnContext(context, looper, vcnNetworkProvider, z);
        }

        public Vcn newVcn(VcnContext vcnContext, ParcelUuid parcelUuid, VcnConfig vcnConfig, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, VcnCallback vcnCallback) {
            return new Vcn(vcnContext, parcelUuid, vcnConfig, telephonySubscriptionSnapshot, vcnCallback);
        }

        public Set getRestrictedTransportsFromCarrierConfig(ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
            if (!Build.IS_ENG && !Build.IS_USERDEBUG) {
                return VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            }
            PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
            if (carrierConfigForSubGrp == null) {
                return VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            }
            int[] intArray = carrierConfigForSubGrp.getIntArray("vcn_restricted_transports", VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT.stream().mapToInt(new ToIntFunction() { // from class: com.android.server.VcnManagementService$Dependencies$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int intValue;
                    intValue = ((Integer) obj).intValue();
                    return intValue;
                }
            }).toArray());
            ArraySet arraySet = new ArraySet();
            for (int i : intArray) {
                arraySet.add(Integer.valueOf(i));
            }
            return arraySet;
        }

        public Set getRestrictedTransports(ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, VcnConfig vcnConfig) {
            ArraySet arraySet = new ArraySet();
            arraySet.addAll(vcnConfig.getRestrictedUnderlyingNetworkTransports());
            arraySet.addAll(getRestrictedTransportsFromCarrierConfig(parcelUuid, telephonySubscriptionSnapshot));
            return arraySet;
        }
    }

    public void systemReady() {
        this.mNetworkProvider.register();
        ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class)).registerNetworkCallback(new NetworkRequest.Builder().clearCapabilities().build(), this.mTrackingNetworkCallback);
        this.mTelephonySubscriptionTracker.register();
    }

    public final void enforcePrimaryUser() {
        int binderCallingUid = this.mDeps.getBinderCallingUid();
        if (binderCallingUid == 1000) {
            throw new IllegalStateException("Calling identity was System Server. Was Binder calling identity cleared?");
        }
        if (!UserHandle.getUserHandleForUid(binderCallingUid).isSystem()) {
            throw new SecurityException("VcnManagementService can only be used by callers running as the primary user");
        }
    }

    public final void enforceCallingUserAndCarrierPrivilege(final ParcelUuid parcelUuid, String str) {
        enforcePrimaryUser();
        final SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        final ArrayList<SubscriptionInfo> arrayList = new ArrayList();
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                VcnManagementService.lambda$enforceCallingUserAndCarrierPrivilege$1(arrayList, subscriptionManager, parcelUuid);
            }
        });
        for (SubscriptionInfo subscriptionInfo : arrayList) {
            TelephonyManager createForSubscriptionId = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId());
            if (SubscriptionManager.isValidSlotIndex(subscriptionInfo.getSimSlotIndex()) && createForSubscriptionId.checkCarrierPrivilegesForPackage(str) == 1) {
                return;
            }
        }
        throw new SecurityException("Carrier privilege required for subscription group to set VCN Config");
    }

    public static /* synthetic */ void lambda$enforceCallingUserAndCarrierPrivilege$1(List list, SubscriptionManager subscriptionManager, ParcelUuid parcelUuid) {
        list.addAll(subscriptionManager.getSubscriptionsInGroup(parcelUuid));
    }

    public final void enforceManageTestNetworksForTestMode(VcnConfig vcnConfig) {
        if (vcnConfig.isTestModeProfile()) {
            this.mContext.enforceCallingPermission("android.permission.MANAGE_TEST_NETWORKS", "Test-mode require the MANAGE_TEST_NETWORKS permission");
        }
    }

    public final boolean isActiveSubGroup(ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        if (parcelUuid == null || telephonySubscriptionSnapshot == null) {
            return false;
        }
        return parcelUuid.equals(telephonySubscriptionSnapshot.getActiveDataSubscriptionGroup());
    }

    public class VcnBroadcastReceiver extends BroadcastReceiver {
        public VcnBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.intent.action.PACKAGE_REPLACED":
                case "android.intent.action.PACKAGE_REMOVED":
                case "android.intent.action.PACKAGE_ADDED":
                    VcnManagementService.this.mTelephonySubscriptionTracker.handleSubscriptionsChanged();
                    return;
                case "android.intent.action.PACKAGE_DATA_CLEARED":
                case "android.intent.action.PACKAGE_FULLY_REMOVED":
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (schemeSpecificPart == null || schemeSpecificPart.isEmpty()) {
                        VcnManagementService.this.logWtf("Package name was empty or null for intent with action" + action);
                        return;
                    }
                    synchronized (VcnManagementService.this.mLock) {
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry entry : VcnManagementService.this.mConfigs.entrySet()) {
                            if (schemeSpecificPart.equals(((VcnConfig) entry.getValue()).getProvisioningPackageName())) {
                                arrayList.add((ParcelUuid) entry.getKey());
                            }
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            VcnManagementService.this.stopAndClearVcnConfigInternalLocked((ParcelUuid) it.next());
                        }
                        if (!arrayList.isEmpty()) {
                            VcnManagementService.this.writeConfigsToDiskLocked();
                        }
                    }
                    return;
                default:
                    Slog.wtf(VcnManagementService.TAG, "received unexpected intent: " + action);
                    return;
            }
        }
    }

    public class VcnSubscriptionTrackerCallback implements TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback {
        public VcnSubscriptionTrackerCallback() {
        }

        @Override // com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback
        public void onNewSnapshot(TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
            synchronized (VcnManagementService.this.mLock) {
                TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot2 = VcnManagementService.this.mLastSnapshot;
                VcnManagementService.this.mLastSnapshot = telephonySubscriptionSnapshot;
                VcnManagementService.this.logInfo("new snapshot: " + VcnManagementService.this.mLastSnapshot);
                for (Map.Entry entry : VcnManagementService.this.mConfigs.entrySet()) {
                    ParcelUuid parcelUuid = (ParcelUuid) entry.getKey();
                    if (telephonySubscriptionSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, ((VcnConfig) entry.getValue()).getProvisioningPackageName()) && VcnManagementService.this.isActiveSubGroup(parcelUuid, telephonySubscriptionSnapshot)) {
                        if (!VcnManagementService.this.mVcns.containsKey(parcelUuid)) {
                            VcnManagementService.this.startVcnLocked(parcelUuid, (VcnConfig) entry.getValue());
                        }
                        VcnManagementService.this.mHandler.removeCallbacksAndMessages(VcnManagementService.this.mVcns.get(parcelUuid));
                    }
                }
                boolean z = false;
                for (Map.Entry entry2 : VcnManagementService.this.mVcns.entrySet()) {
                    final ParcelUuid parcelUuid2 = (ParcelUuid) entry2.getKey();
                    VcnConfig vcnConfig = (VcnConfig) VcnManagementService.this.mConfigs.get(parcelUuid2);
                    boolean isActiveSubGroup = VcnManagementService.this.isActiveSubGroup(parcelUuid2, telephonySubscriptionSnapshot);
                    boolean z2 = SubscriptionManager.isValidSubscriptionId(telephonySubscriptionSnapshot.getActiveDataSubscriptionId()) && !VcnManagementService.this.isActiveSubGroup(parcelUuid2, telephonySubscriptionSnapshot);
                    if (vcnConfig != null && telephonySubscriptionSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid2, vcnConfig.getProvisioningPackageName()) && isActiveSubGroup) {
                        ((Vcn) entry2.getValue()).updateSubscriptionSnapshot(VcnManagementService.this.mLastSnapshot);
                        z |= !Objects.equals(telephonySubscriptionSnapshot2.getCarrierConfigForSubGrp(parcelUuid2), VcnManagementService.this.mLastSnapshot.getCarrierConfigForSubGrp(parcelUuid2));
                    }
                    final Vcn vcn = (Vcn) entry2.getValue();
                    VcnManagementService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.VcnManagementService$VcnSubscriptionTrackerCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VcnManagementService.VcnSubscriptionTrackerCallback.this.lambda$onNewSnapshot$0(parcelUuid2, vcn);
                        }
                    }, vcn, z2 ? 0L : VcnManagementService.CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS);
                }
                Map subGroupToSubIdMappings = VcnManagementService.this.getSubGroupToSubIdMappings(telephonySubscriptionSnapshot2);
                VcnManagementService vcnManagementService = VcnManagementService.this;
                if (!vcnManagementService.getSubGroupToSubIdMappings(vcnManagementService.mLastSnapshot).equals(subGroupToSubIdMappings)) {
                    VcnManagementService.this.garbageCollectAndWriteVcnConfigsLocked();
                    z = true;
                }
                if (z) {
                    VcnManagementService.this.notifyAllPolicyListenersLocked();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNewSnapshot$0(ParcelUuid parcelUuid, Vcn vcn) {
            synchronized (VcnManagementService.this.mLock) {
                if (VcnManagementService.this.mVcns.get(parcelUuid) == vcn) {
                    VcnManagementService.this.stopVcnLocked(parcelUuid);
                    VcnManagementService.this.notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 1);
                }
            }
        }
    }

    public final Map getSubGroupToSubIdMappings(TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        ArrayMap arrayMap = new ArrayMap();
        for (ParcelUuid parcelUuid : this.mVcns.keySet()) {
            arrayMap.put(parcelUuid, telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid));
        }
        return arrayMap;
    }

    public final void stopVcnLocked(ParcelUuid parcelUuid) {
        logInfo("Stopping VCN config for subGrp: " + parcelUuid);
        Vcn vcn = (Vcn) this.mVcns.get(parcelUuid);
        if (vcn == null) {
            return;
        }
        vcn.teardownAsynchronously();
        this.mVcns.remove(parcelUuid);
        notifyAllPolicyListenersLocked();
    }

    public final void notifyAllPolicyListenersLocked() {
        for (final PolicyListenerBinderDeath policyListenerBinderDeath : this.mRegisteredPolicyListeners.values()) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda13
                public final void runOrThrow() {
                    VcnManagementService.this.lambda$notifyAllPolicyListenersLocked$2(policyListenerBinderDeath);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllPolicyListenersLocked$2(PolicyListenerBinderDeath policyListenerBinderDeath) {
        try {
            policyListenerBinderDeath.mListener.onPolicyChanged();
        } catch (RemoteException e) {
            logDbg("VcnStatusCallback threw on VCN status change", e);
        }
    }

    public final void notifyAllPermissionedStatusCallbacksLocked(ParcelUuid parcelUuid, final int i) {
        for (final VcnStatusCallbackInfo vcnStatusCallbackInfo : this.mRegisteredStatusCallbacks.values()) {
            if (isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda7
                    public final void runOrThrow() {
                        VcnManagementService.this.lambda$notifyAllPermissionedStatusCallbacksLocked$3(vcnStatusCallbackInfo, i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllPermissionedStatusCallbacksLocked$3(VcnStatusCallbackInfo vcnStatusCallbackInfo, int i) {
        try {
            vcnStatusCallbackInfo.mCallback.onVcnStatusChanged(i);
        } catch (RemoteException e) {
            logDbg("VcnStatusCallback threw on VCN status change", e);
        }
    }

    public final void startVcnLocked(ParcelUuid parcelUuid, VcnConfig vcnConfig) {
        logInfo("Starting VCN config for subGrp: " + parcelUuid);
        if (!this.mVcns.isEmpty()) {
            Iterator it = this.mVcns.keySet().iterator();
            while (it.hasNext()) {
                stopVcnLocked((ParcelUuid) it.next());
            }
        }
        this.mVcns.put(parcelUuid, this.mDeps.newVcn(this.mDeps.newVcnContext(this.mContext, this.mLooper, this.mNetworkProvider, vcnConfig.isTestModeProfile()), parcelUuid, vcnConfig, this.mLastSnapshot, new VcnCallbackImpl(parcelUuid)));
        notifyAllPolicyListenersLocked();
        notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 2);
    }

    public final void startOrUpdateVcnLocked(ParcelUuid parcelUuid, VcnConfig vcnConfig) {
        logDbg("Starting or updating VCN config for subGrp: " + parcelUuid);
        if (this.mVcns.containsKey(parcelUuid)) {
            ((Vcn) this.mVcns.get(parcelUuid)).updateConfig(vcnConfig);
            notifyAllPolicyListenersLocked();
        } else if (isActiveSubGroup(parcelUuid, this.mLastSnapshot)) {
            startVcnLocked(parcelUuid, vcnConfig);
        }
    }

    public void setVcnConfig(final ParcelUuid parcelUuid, final VcnConfig vcnConfig, String str) {
        Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        Objects.requireNonNull(vcnConfig, "config was null");
        Objects.requireNonNull(str, "opPkgName was null");
        if (!vcnConfig.getProvisioningPackageName().equals(str)) {
            throw new IllegalArgumentException("Mismatched caller and VcnConfig creator");
        }
        logInfo("VCN config updated for subGrp: " + parcelUuid);
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), vcnConfig.getProvisioningPackageName());
        enforceManageTestNetworksForTestMode(vcnConfig);
        enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                VcnManagementService.this.lambda$setVcnConfig$4(parcelUuid, vcnConfig);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVcnConfig$4(ParcelUuid parcelUuid, VcnConfig vcnConfig) {
        synchronized (this.mLock) {
            this.mConfigs.put(parcelUuid, vcnConfig);
            startOrUpdateVcnLocked(parcelUuid, vcnConfig);
            writeConfigsToDiskLocked();
        }
    }

    public final void enforceCarrierPrivilegeOrProvisioningPackage(ParcelUuid parcelUuid, String str) {
        enforcePrimaryUser();
        if (isProvisioningPackageForConfig(parcelUuid, str)) {
            return;
        }
        enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
    }

    public final boolean isProvisioningPackageForConfig(ParcelUuid parcelUuid, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                VcnConfig vcnConfig = (VcnConfig) this.mConfigs.get(parcelUuid);
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

    public void clearVcnConfig(final ParcelUuid parcelUuid, String str) {
        Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        Objects.requireNonNull(str, "opPkgName was null");
        logInfo("VCN config cleared for subGrp: " + parcelUuid);
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), str);
        enforceCarrierPrivilegeOrProvisioningPackage(parcelUuid, str);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                VcnManagementService.this.lambda$clearVcnConfig$5(parcelUuid);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearVcnConfig$5(ParcelUuid parcelUuid) {
        synchronized (this.mLock) {
            stopAndClearVcnConfigInternalLocked(parcelUuid);
            writeConfigsToDiskLocked();
        }
    }

    public final void stopAndClearVcnConfigInternalLocked(ParcelUuid parcelUuid) {
        this.mConfigs.remove(parcelUuid);
        boolean containsKey = this.mVcns.containsKey(parcelUuid);
        stopVcnLocked(parcelUuid);
        if (containsKey) {
            notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 0);
        }
    }

    public final void garbageCollectAndWriteVcnConfigsLocked() {
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        Iterator it = this.mConfigs.keySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            List<SubscriptionInfo> subscriptionsInGroup = subscriptionManager.getSubscriptionsInGroup((ParcelUuid) it.next());
            if (subscriptionsInGroup == null || subscriptionsInGroup.isEmpty()) {
                it.remove();
                z = true;
            }
        }
        if (z) {
            writeConfigsToDiskLocked();
        }
    }

    public List getConfiguredSubscriptionGroups(String str) {
        Objects.requireNonNull(str, "opPkgName was null");
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), str);
        enforcePrimaryUser();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (ParcelUuid parcelUuid : this.mConfigs.keySet()) {
                if (this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, str) || isProvisioningPackageForConfig(parcelUuid, str)) {
                    arrayList.add(parcelUuid);
                }
            }
        }
        return arrayList;
    }

    public final void writeConfigsToDiskLocked() {
        try {
            this.mConfigDiskRwHelper.writeToDisk(PersistableBundleUtils.fromMap(this.mConfigs, new PersistableBundleUtils.Serializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda10
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
                public final PersistableBundle toPersistableBundle(Object obj) {
                    return PersistableBundleUtils.fromParcelUuid((ParcelUuid) obj);
                }
            }, new PersistableBundleUtils.Serializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda11
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
                public final PersistableBundle toPersistableBundle(Object obj) {
                    return ((VcnConfig) obj).toPersistableBundle();
                }
            }));
        } catch (IOException e) {
            logErr("Failed to save configs to disk", e);
            throw new ServiceSpecificException(0, "Failed to save configs");
        }
    }

    public Map getConfigs() {
        Map unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(this.mConfigs);
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

    public Map getAllStatusCallbacks() {
        Map unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(this.mRegisteredStatusCallbacks);
        }
        return unmodifiableMap;
    }

    public class PolicyListenerBinderDeath implements IBinder.DeathRecipient {
        public final IVcnUnderlyingNetworkPolicyListener mListener;

        public PolicyListenerBinderDeath(IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
            this.mListener = iVcnUnderlyingNetworkPolicyListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e(VcnManagementService.TAG, "app died without removing VcnUnderlyingNetworkPolicyListener");
            VcnManagementService.this.removeVcnUnderlyingNetworkPolicyListener(this.mListener);
        }
    }

    public void addVcnUnderlyingNetworkPolicyListener(final IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        PermissionUtils.enforceAnyPermissionOf(this.mContext, new String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                VcnManagementService.this.lambda$addVcnUnderlyingNetworkPolicyListener$6(iVcnUnderlyingNetworkPolicyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addVcnUnderlyingNetworkPolicyListener$6(IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        PolicyListenerBinderDeath policyListenerBinderDeath = new PolicyListenerBinderDeath(iVcnUnderlyingNetworkPolicyListener);
        synchronized (this.mLock) {
            this.mRegisteredPolicyListeners.put(iVcnUnderlyingNetworkPolicyListener.asBinder(), policyListenerBinderDeath);
            try {
                iVcnUnderlyingNetworkPolicyListener.asBinder().linkToDeath(policyListenerBinderDeath, 0);
            } catch (RemoteException unused) {
                policyListenerBinderDeath.binderDied();
            }
        }
    }

    public void removeVcnUnderlyingNetworkPolicyListener(final IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        PermissionUtils.enforceAnyPermissionOf(this.mContext, new String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                VcnManagementService.this.lambda$removeVcnUnderlyingNetworkPolicyListener$7(iVcnUnderlyingNetworkPolicyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeVcnUnderlyingNetworkPolicyListener$7(IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        synchronized (this.mLock) {
            PolicyListenerBinderDeath policyListenerBinderDeath = (PolicyListenerBinderDeath) this.mRegisteredPolicyListeners.remove(iVcnUnderlyingNetworkPolicyListener.asBinder());
            if (policyListenerBinderDeath != null) {
                iVcnUnderlyingNetworkPolicyListener.asBinder().unlinkToDeath(policyListenerBinderDeath, 0);
            }
        }
    }

    public final ParcelUuid getSubGroupForNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot;
        synchronized (this.mLock) {
            telephonySubscriptionSnapshot = this.mLastSnapshot;
        }
        Iterator it = networkCapabilities.getSubscriptionIds().iterator();
        ParcelUuid parcelUuid = null;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (parcelUuid != null && !parcelUuid.equals(telephonySubscriptionSnapshot.getGroupForSubId(intValue))) {
                logWtf("Got multiple subscription groups for a single network");
            }
            parcelUuid = telephonySubscriptionSnapshot.getGroupForSubId(intValue);
        }
        return parcelUuid;
    }

    public VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(final NetworkCapabilities networkCapabilities, final LinkProperties linkProperties) {
        Objects.requireNonNull(networkCapabilities, "networkCapabilities was null");
        Objects.requireNonNull(linkProperties, "linkProperties was null");
        PermissionUtils.enforceAnyPermissionOf(this.mContext, new String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        if ((this.mContext.checkCallingOrSelfPermission("android.permission.NETWORK_FACTORY") != 0) && !networkCapabilities.hasTransport(7)) {
            throw new IllegalStateException("NetworkCapabilities must be for Test Network if using permission MANAGE_TEST_NETWORKS");
        }
        return (VcnUnderlyingNetworkPolicy) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda1
            public final Object getOrThrow() {
                VcnUnderlyingNetworkPolicy lambda$getUnderlyingNetworkPolicy$8;
                lambda$getUnderlyingNetworkPolicy$8 = VcnManagementService.this.lambda$getUnderlyingNetworkPolicy$8(networkCapabilities, linkProperties);
                return lambda$getUnderlyingNetworkPolicy$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ VcnUnderlyingNetworkPolicy lambda$getUnderlyingNetworkPolicy$8(NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        boolean z;
        boolean z2;
        NetworkCapabilities networkCapabilities2 = new NetworkCapabilities(networkCapabilities);
        ParcelUuid subGroupForNetworkCapabilities = getSubGroupForNetworkCapabilities(networkCapabilities2);
        synchronized (this.mLock) {
            Vcn vcn = (Vcn) this.mVcns.get(subGroupForNetworkCapabilities);
            VcnConfig vcnConfig = (VcnConfig) this.mConfigs.get(subGroupForNetworkCapabilities);
            z = false;
            if (vcn != null) {
                if (vcnConfig == null) {
                    logWtf("Vcn instance exists but VcnConfig does not for " + subGroupForNetworkCapabilities);
                }
                boolean z3 = vcn.getStatus() == 2;
                Iterator it = this.mDeps.getRestrictedTransports(subGroupForNetworkCapabilities, this.mLastSnapshot, vcnConfig).iterator();
                z2 = false;
                while (true) {
                    if (!it.hasNext()) {
                        z = z3;
                        break;
                    }
                    int intValue = ((Integer) it.next()).intValue();
                    if (networkCapabilities2.hasTransport(intValue)) {
                        if (intValue != 0 && intValue != 7) {
                            z = z3;
                            z2 = true;
                            break;
                        }
                        z2 |= vcn.getStatus() == 2;
                    }
                }
            } else {
                z2 = false;
            }
        }
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder(networkCapabilities2);
        if (z) {
            builder.removeCapability(28);
        } else {
            builder.addCapability(28);
        }
        if (z2) {
            builder.removeCapability(13);
        }
        NetworkCapabilities build = builder.build();
        VcnUnderlyingNetworkPolicy vcnUnderlyingNetworkPolicy = new VcnUnderlyingNetworkPolicy(this.mTrackingNetworkCallback.requiresRestartForImmutableCapabilityChanges(build, linkProperties), build);
        logVdbg("getUnderlyingNetworkPolicy() called for caps: " + networkCapabilities + "; and lp: " + linkProperties + "; result = " + vcnUnderlyingNetworkPolicy);
        return vcnUnderlyingNetworkPolicy;
    }

    class VcnStatusCallbackInfo implements IBinder.DeathRecipient {
        public final IVcnStatusCallback mCallback;
        public final String mPkgName;
        public final ParcelUuid mSubGroup;
        public final int mUid;

        public VcnStatusCallbackInfo(ParcelUuid parcelUuid, IVcnStatusCallback iVcnStatusCallback, String str, int i) {
            this.mSubGroup = parcelUuid;
            this.mCallback = iVcnStatusCallback;
            this.mPkgName = str;
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e(VcnManagementService.TAG, "app died without unregistering VcnStatusCallback");
            VcnManagementService.this.unregisterVcnStatusCallback(this.mCallback);
        }
    }

    public final boolean isCallbackPermissioned(VcnStatusCallbackInfo vcnStatusCallbackInfo, ParcelUuid parcelUuid) {
        return parcelUuid.equals(vcnStatusCallbackInfo.mSubGroup) && this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, vcnStatusCallbackInfo.mPkgName);
    }

    public void registerVcnStatusCallback(ParcelUuid parcelUuid, IVcnStatusCallback iVcnStatusCallback, String str) {
        int binderCallingUid = this.mDeps.getBinderCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Objects.requireNonNull(parcelUuid, "subGroup must not be null");
            Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            Objects.requireNonNull(str, "opPkgName must not be null");
            ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(binderCallingUid, str);
            IBinder asBinder = iVcnStatusCallback.asBinder();
            VcnStatusCallbackInfo vcnStatusCallbackInfo = new VcnStatusCallbackInfo(parcelUuid, iVcnStatusCallback, str, binderCallingUid);
            int i = 0;
            try {
                asBinder.linkToDeath(vcnStatusCallbackInfo, 0);
                synchronized (this.mLock) {
                    if (this.mRegisteredStatusCallbacks.containsKey(asBinder)) {
                        throw new IllegalStateException("Attempting to register a callback that is already in use");
                    }
                    this.mRegisteredStatusCallbacks.put(asBinder, vcnStatusCallbackInfo);
                    VcnConfig vcnConfig = (VcnConfig) this.mConfigs.get(parcelUuid);
                    Vcn vcn = (Vcn) this.mVcns.get(parcelUuid);
                    int status = vcn == null ? 0 : vcn.getStatus();
                    if (vcnConfig != null && isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                        if (vcn == null) {
                            i = 1;
                        } else {
                            if (status != 2 && status != 3) {
                                logWtf("Unknown VCN status: " + status);
                            }
                            i = status;
                        }
                    }
                    try {
                        vcnStatusCallbackInfo.mCallback.onVcnStatusChanged(i);
                    } catch (RemoteException e) {
                        logDbg("VcnStatusCallback threw on VCN status change", e);
                    }
                }
            } catch (RemoteException unused) {
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterVcnStatusCallback(IVcnStatusCallback iVcnStatusCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            IBinder asBinder = iVcnStatusCallback.asBinder();
            synchronized (this.mLock) {
                VcnStatusCallbackInfo vcnStatusCallbackInfo = (VcnStatusCallbackInfo) this.mRegisteredStatusCallbacks.remove(asBinder);
                if (vcnStatusCallbackInfo != null) {
                    asBinder.unlinkToDeath(vcnStatusCallbackInfo, 0);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setLastSnapshot(TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        Objects.requireNonNull(telephonySubscriptionSnapshot);
        this.mLastSnapshot = telephonySubscriptionSnapshot;
    }

    public final void logDbg(String str) {
        Slog.d(TAG, str);
    }

    public final void logDbg(String str, Throwable th) {
        Slog.d(TAG, str, th);
    }

    public final void logInfo(String str) {
        String str2 = TAG;
        Slog.i(str2, str);
        LOCAL_LOG.log("[INFO] [" + str2 + "] " + str);
    }

    public final void logErr(String str, Throwable th) {
        String str2 = TAG;
        Slog.e(str2, str, th);
        LOCAL_LOG.log("[ERR ] [" + str2 + "] " + str + th);
    }

    public final void logWtf(String str) {
        String str2 = TAG;
        Slog.wtf(str2, str);
        LOCAL_LOG.log("[WTF] [" + str2 + "] " + str);
    }

    public final void logWtf(String str, Throwable th) {
        String str2 = TAG;
        Slog.wtf(str2, str, th);
        LOCAL_LOG.log("[WTF ] [" + str2 + "] " + str + th);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", TAG);
        final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "| ");
        this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VcnManagementService.this.lambda$dump$9(indentingPrintWriter);
            }
        }, DUMP_TIMEOUT_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$9(IndentingPrintWriter indentingPrintWriter) {
        this.mNetworkProvider.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mTrackingNetworkCallback.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        synchronized (this.mLock) {
            this.mLastSnapshot.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.println("mConfigs:");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : this.mConfigs.entrySet()) {
                indentingPrintWriter.println(entry.getKey() + ": " + ((VcnConfig) entry.getValue()).getProvisioningPackageName());
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("mVcns:");
            indentingPrintWriter.increaseIndent();
            Iterator it = this.mVcns.values().iterator();
            while (it.hasNext()) {
                ((Vcn) it.next()).dump(indentingPrintWriter);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("Local log:");
        indentingPrintWriter.increaseIndent();
        LOCAL_LOG.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public class TrackingNetworkCallback extends ConnectivityManager.NetworkCallback {
        public final Map mCaps;
        public final Map mLinkProperties;
        public final Object mLockObject;

        public TrackingNetworkCallback() {
            this.mLockObject = new Object();
            this.mCaps = new ArrayMap();
            this.mLinkProperties = new ArrayMap();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            synchronized (this.mLockObject) {
                this.mCaps.put(network, networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            synchronized (this.mLockObject) {
                this.mLinkProperties.put(network, linkProperties);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            synchronized (this.mLockObject) {
                this.mCaps.remove(network);
                this.mLinkProperties.remove(network);
            }
        }

        public final boolean requiresRestartForImmutableCapabilityChanges(NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
            if (networkCapabilities.getSubscriptionIds() == null) {
                return false;
            }
            synchronized (this.mLockObject) {
                for (Map.Entry entry : this.mLinkProperties.entrySet()) {
                    if (linkProperties.getInterfaceName() != null && !linkProperties.getInterfaceName().isEmpty() && Objects.equals(linkProperties.getInterfaceName(), ((LinkProperties) entry.getValue()).getInterfaceName())) {
                        return ((NetworkCapabilities) this.mCaps.get(entry.getKey())).hasCapability(13) != networkCapabilities.hasCapability(13);
                    }
                }
                return false;
            }
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("TrackingNetworkCallback:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mCaps:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mCaps) {
                for (Map.Entry entry : this.mCaps.entrySet()) {
                    indentingPrintWriter.println(entry.getKey() + ": " + entry.getValue());
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public class VcnCallbackImpl implements VcnCallback {
        public final ParcelUuid mSubGroup;

        public VcnCallbackImpl(ParcelUuid parcelUuid) {
            Objects.requireNonNull(parcelUuid, "Missing subGroup");
            this.mSubGroup = parcelUuid;
        }

        @Override // com.android.server.VcnManagementService.VcnCallback
        public void onSafeModeStatusChanged(boolean z) {
            synchronized (VcnManagementService.this.mLock) {
                if (VcnManagementService.this.mVcns.containsKey(this.mSubGroup)) {
                    int i = z ? 3 : 2;
                    VcnManagementService.this.notifyAllPolicyListenersLocked();
                    VcnManagementService.this.notifyAllPermissionedStatusCallbacksLocked(this.mSubGroup, i);
                }
            }
        }

        @Override // com.android.server.VcnManagementService.VcnCallback
        public void onGatewayConnectionError(final String str, final int i, final String str2, final String str3) {
            synchronized (VcnManagementService.this.mLock) {
                if (VcnManagementService.this.mVcns.containsKey(this.mSubGroup)) {
                    for (final VcnStatusCallbackInfo vcnStatusCallbackInfo : VcnManagementService.this.mRegisteredStatusCallbacks.values()) {
                        if (VcnManagementService.this.isCallbackPermissioned(vcnStatusCallbackInfo, this.mSubGroup)) {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$VcnCallbackImpl$$ExternalSyntheticLambda0
                                public final void runOrThrow() {
                                    VcnManagementService.VcnCallbackImpl.this.lambda$onGatewayConnectionError$0(vcnStatusCallbackInfo, str, i, str2, str3);
                                }
                            });
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGatewayConnectionError$0(VcnStatusCallbackInfo vcnStatusCallbackInfo, String str, int i, String str2, String str3) {
            try {
                vcnStatusCallbackInfo.mCallback.onGatewayConnectionError(str, i, str2, str3);
            } catch (RemoteException e) {
                VcnManagementService.this.logDbg("VcnStatusCallback threw on VCN status change", e);
            }
        }
    }
}
