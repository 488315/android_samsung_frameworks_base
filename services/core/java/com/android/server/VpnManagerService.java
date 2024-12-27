package com.android.server;

import android.annotation.SystemApi;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.IVpnManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.net.UnderlyingNetworkInfo;
import android.net.Uri;
import android.net.VpnProfileState;
import android.net.util.NetdService;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.util.DumpUtils;
import com.android.net.module.util.PermissionUtils;
import com.android.server.connectivity.EnterpriseVpn;
import com.android.server.connectivity.Vpn;
import com.android.server.connectivity.VpnProfileStore;
import com.android.server.net.LockdownVpnTracker;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class VpnManagerService extends IVpnManager.Stub {
    public static final boolean DBG = Debug.semIsProductDev();
    public final Object knoxVpnLock;
    public final ConnectivityManager mCm;
    public final Context mContext;
    public NetworkCallback mDefaultNetworkCallback;
    public final Dependencies mDeps;
    public final SparseArray mEnterpriseVpnStoreObj;
    public final Handler mHandler;
    protected final HandlerThread mHandlerThread;
    public final AnonymousClass1 mIntentReceiver;
    public boolean mLockdownEnabled;
    public LockdownVpnTracker mLockdownTracker;
    public final int mMainUserId;
    public final INetworkManagementService mNMS;
    public final INetd mNetd;
    public SemPersonaManager mPersonaManager;
    public final UserManager mUserManager;
    public final AnonymousClass1 mUserPresentReceiver;
    public final VpnProfileStore mVpnProfileStore;
    protected final SparseArray mVpns = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public LinkProperties mLinkProperties;
        public Network mNetwork;

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            boolean z = VpnManagerService.DBG;
            Log.d("VpnManagerService", "onAvailable being called for netId " + network.getNetId());
            this.mNetwork = network;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            boolean z = VpnManagerService.DBG;
            Log.d(
                    "VpnManagerService",
                    "onLinkPropertiesChanged being called for netId "
                            + network.getNetId()
                            + " for interface "
                            + linkProperties.getInterfaceName());
            this.mNetwork = network;
            this.mLinkProperties = linkProperties;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            boolean z = VpnManagerService.DBG;
            Log.d("VpnManagerService", "onLost being called for netId " + network.getNetId());
            this.mNetwork = null;
            this.mLinkProperties = null;
        }
    }

    /* renamed from: -$$Nest$mensureRunningOnHandlerThread, reason: not valid java name */
    public static void m106$$Nest$mensureRunningOnHandlerThread(
            VpnManagerService vpnManagerService) {
        if (vpnManagerService.mHandler.getLooper().getThread() == Thread.currentThread()) {
            return;
        }
        throw new IllegalStateException(
                "Not running on VpnManagerService thread: " + Thread.currentThread().getName());
    }

    /* renamed from: -$$Nest$monPackageAdded, reason: not valid java name */
    public static void m107$$Nest$monPackageAdded(
            VpnManagerService vpnManagerService, String str, int i, boolean z) {
        vpnManagerService.getClass();
        if (TextUtils.isEmpty(str) || i < 0) {
            Log.wtf("VpnManagerService", "Invalid package in onPackageAdded: " + str + " | " + i);
            return;
        }
        int userId = UserHandle.getUserId(i);
        synchronized (vpnManagerService.mVpns) {
            try {
                Vpn vpn = (Vpn) vpnManagerService.mVpns.get(userId);
                if (vpn != null && !z) {
                    vpn.refreshPlatformVpnAppExclusionList();
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$monPackageRemoved, reason: not valid java name */
    public static void m108$$Nest$monPackageRemoved(
            VpnManagerService vpnManagerService, String str, int i, boolean z) {
        vpnManagerService.getClass();
        if (TextUtils.isEmpty(str) || i < 0) {
            Log.wtf("VpnManagerService", "Invalid package in onPackageRemoved: " + str + " | " + i);
            return;
        }
        int userId = UserHandle.getUserId(i);
        synchronized (vpnManagerService.mVpns) {
            try {
                Vpn vpn = (Vpn) vpnManagerService.mVpns.get(userId);
                if (vpn != null && !z) {
                    if (TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
                        Log.d(
                                "VpnManagerService",
                                "Removing always-on VPN package " + str + " for user " + userId);
                        vpn.setAlwaysOnPackage(null, null, false);
                    }
                    vpn.refreshPlatformVpnAppExclusionList();
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$monPackageReplaced, reason: not valid java name */
    public static void m109$$Nest$monPackageReplaced(
            VpnManagerService vpnManagerService, String str, int i) {
        vpnManagerService.getClass();
        if (TextUtils.isEmpty(str) || i < 0) {
            Log.wtf(
                    "VpnManagerService",
                    "Invalid package in onPackageReplaced: " + str + " | " + i);
            return;
        }
        int userId = UserHandle.getUserId(i);
        synchronized (vpnManagerService.mVpns) {
            try {
                Vpn vpn = (Vpn) vpnManagerService.mVpns.get(userId);
                if (vpn == null) {
                    return;
                }
                if (TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
                    Log.d(
                            "VpnManagerService",
                            "Restarting always-on VPN package " + str + " for user " + userId);
                    vpn.startAlwaysOnVpn();
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$monUserAdded, reason: not valid java name */
    public static void m110$$Nest$monUserAdded(VpnManagerService vpnManagerService, int i) {
        synchronized (vpnManagerService.mVpns) {
            try {
                int size = vpnManagerService.mVpns.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((Vpn) vpnManagerService.mVpns.valueAt(i2)).onUserAdded(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$monUserRemoved, reason: not valid java name */
    public static void m111$$Nest$monUserRemoved(VpnManagerService vpnManagerService, int i) {
        synchronized (vpnManagerService.mVpns) {
            try {
                int size = vpnManagerService.mVpns.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((Vpn) vpnManagerService.mVpns.valueAt(i2)).onUserRemoved(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$monUserUnlocked, reason: not valid java name */
    public static void m112$$Nest$monUserUnlocked(VpnManagerService vpnManagerService, int i) {
        synchronized (vpnManagerService.mVpns) {
            try {
                if (i == vpnManagerService.mMainUserId
                        && vpnManagerService.isLockdownVpnEnabled()) {
                    vpnManagerService.updateLockdownVpn();
                } else {
                    vpnManagerService.startAlwaysOnVpn(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public VpnManagerService(Context context, Dependencies dependencies) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(this) { // from class: com.android.server.VpnManagerService.1
                    public final /* synthetic */ VpnManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                VpnManagerService.m106$$Nest$mensureRunningOnHandlerThread(
                                        this.this$0);
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                Uri data = intent.getData();
                                String schemeSpecificPart =
                                        data != null ? data.getSchemeSpecificPart() : null;
                                if ("com.android.server.action.LOCKDOWN_RESET".equals(action)) {
                                    VpnManagerService vpnManagerService = this.this$0;
                                    synchronized (vpnManagerService.mVpns) {
                                        LockdownVpnTracker lockdownVpnTracker =
                                                vpnManagerService.mLockdownTracker;
                                        if (lockdownVpnTracker != null) {
                                            Log.d("LockdownVpnTracker", "reset()");
                                            synchronized (lockdownVpnTracker.mStateLock) {
                                                lockdownVpnTracker.shutdownLocked();
                                                lockdownVpnTracker.initLocked();
                                                lockdownVpnTracker.handleStateChangedLocked();
                                            }
                                        }
                                    }
                                    return;
                                }
                                if (intExtra == -10000) {
                                    return;
                                }
                                if ("android.intent.action.USER_STARTED".equals(action)) {
                                    VpnManagerService vpnManagerService2 = this.this$0;
                                    if (vpnManagerService2.mUserManager.getUserInfo(intExtra)
                                            == null) {
                                        VpnManagerService.logw(
                                                "Started user doesn't exist. UserId: " + intExtra);
                                        return;
                                    }
                                    synchronized (vpnManagerService2.mVpns) {
                                        try {
                                            if (((Vpn) vpnManagerService2.mVpns.get(intExtra))
                                                    != null) {
                                                Log.e(
                                                        "VpnManagerService",
                                                        "Starting user already has a VPN");
                                                return;
                                            }
                                            Dependencies dependencies2 = vpnManagerService2.mDeps;
                                            Looper looper = vpnManagerService2.mHandler.getLooper();
                                            Context context3 = vpnManagerService2.mContext;
                                            INetworkManagementService iNetworkManagementService =
                                                    vpnManagerService2.mNMS;
                                            INetd iNetd = vpnManagerService2.mNetd;
                                            dependencies2.getClass();
                                            vpnManagerService2.mVpns.put(
                                                    intExtra,
                                                    new Vpn(
                                                            looper,
                                                            context3,
                                                            new Vpn.Dependencies(),
                                                            iNetworkManagementService,
                                                            iNetd,
                                                            intExtra,
                                                            new VpnProfileStore(),
                                                            new Vpn.SystemServices(context3),
                                                            new Vpn.Ikev2SessionCreator()));
                                            if (intExtra == vpnManagerService2.mMainUserId
                                                    && vpnManagerService2.isLockdownVpnEnabled()) {
                                                vpnManagerService2.updateLockdownVpn();
                                            }
                                            return;
                                        } finally {
                                        }
                                    }
                                }
                                if ("android.intent.action.USER_STOPPED".equals(action)) {
                                    VpnManagerService vpnManagerService3 = this.this$0;
                                    synchronized (vpnManagerService3.mVpns) {
                                        try {
                                            Vpn vpn = (Vpn) vpnManagerService3.mVpns.get(intExtra);
                                            if (vpn == null) {
                                                Log.e(
                                                        "VpnManagerService",
                                                        "Stopped user has no VPN");
                                            } else {
                                                vpn.onUserStopped();
                                                vpnManagerService3.mVpns.delete(intExtra);
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if ("android.intent.action.USER_ADDED".equals(action)) {
                                    VpnManagerService.m110$$Nest$monUserAdded(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.USER_REMOVED".equals(action)) {
                                    VpnManagerService.m111$$Nest$monUserRemoved(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                                    VpnManagerService.m112$$Nest$monUserUnlocked(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
                                    VpnManagerService.m109$$Nest$monPackageReplaced(
                                            this.this$0, schemeSpecificPart, intExtra2);
                                    return;
                                }
                                if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                                    VpnManagerService.m108$$Nest$monPackageRemoved(
                                            this.this$0,
                                            schemeSpecificPart,
                                            intExtra2,
                                            intent.getBooleanExtra(
                                                    "android.intent.extra.REPLACING", false));
                                    return;
                                } else {
                                    if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                                        VpnManagerService.m107$$Nest$monPackageAdded(
                                                this.this$0,
                                                schemeSpecificPart,
                                                intExtra2,
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false));
                                        return;
                                    }
                                    Log.wtf(
                                            "VpnManagerService",
                                            "received unexpected intent: " + action);
                                    return;
                                }
                            default:
                                VpnManagerService.m106$$Nest$mensureRunningOnHandlerThread(
                                        this.this$0);
                                this.this$0.updateLockdownVpn();
                                context2.unregisterReceiver(this);
                                return;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(this) { // from class: com.android.server.VpnManagerService.1
                    public final /* synthetic */ VpnManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                VpnManagerService.m106$$Nest$mensureRunningOnHandlerThread(
                                        this.this$0);
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                Uri data = intent.getData();
                                String schemeSpecificPart =
                                        data != null ? data.getSchemeSpecificPart() : null;
                                if ("com.android.server.action.LOCKDOWN_RESET".equals(action)) {
                                    VpnManagerService vpnManagerService = this.this$0;
                                    synchronized (vpnManagerService.mVpns) {
                                        LockdownVpnTracker lockdownVpnTracker =
                                                vpnManagerService.mLockdownTracker;
                                        if (lockdownVpnTracker != null) {
                                            Log.d("LockdownVpnTracker", "reset()");
                                            synchronized (lockdownVpnTracker.mStateLock) {
                                                lockdownVpnTracker.shutdownLocked();
                                                lockdownVpnTracker.initLocked();
                                                lockdownVpnTracker.handleStateChangedLocked();
                                            }
                                        }
                                    }
                                    return;
                                }
                                if (intExtra == -10000) {
                                    return;
                                }
                                if ("android.intent.action.USER_STARTED".equals(action)) {
                                    VpnManagerService vpnManagerService2 = this.this$0;
                                    if (vpnManagerService2.mUserManager.getUserInfo(intExtra)
                                            == null) {
                                        VpnManagerService.logw(
                                                "Started user doesn't exist. UserId: " + intExtra);
                                        return;
                                    }
                                    synchronized (vpnManagerService2.mVpns) {
                                        try {
                                            if (((Vpn) vpnManagerService2.mVpns.get(intExtra))
                                                    != null) {
                                                Log.e(
                                                        "VpnManagerService",
                                                        "Starting user already has a VPN");
                                                return;
                                            }
                                            Dependencies dependencies2 = vpnManagerService2.mDeps;
                                            Looper looper = vpnManagerService2.mHandler.getLooper();
                                            Context context3 = vpnManagerService2.mContext;
                                            INetworkManagementService iNetworkManagementService =
                                                    vpnManagerService2.mNMS;
                                            INetd iNetd = vpnManagerService2.mNetd;
                                            dependencies2.getClass();
                                            vpnManagerService2.mVpns.put(
                                                    intExtra,
                                                    new Vpn(
                                                            looper,
                                                            context3,
                                                            new Vpn.Dependencies(),
                                                            iNetworkManagementService,
                                                            iNetd,
                                                            intExtra,
                                                            new VpnProfileStore(),
                                                            new Vpn.SystemServices(context3),
                                                            new Vpn.Ikev2SessionCreator()));
                                            if (intExtra == vpnManagerService2.mMainUserId
                                                    && vpnManagerService2.isLockdownVpnEnabled()) {
                                                vpnManagerService2.updateLockdownVpn();
                                            }
                                            return;
                                        } finally {
                                        }
                                    }
                                }
                                if ("android.intent.action.USER_STOPPED".equals(action)) {
                                    VpnManagerService vpnManagerService3 = this.this$0;
                                    synchronized (vpnManagerService3.mVpns) {
                                        try {
                                            Vpn vpn = (Vpn) vpnManagerService3.mVpns.get(intExtra);
                                            if (vpn == null) {
                                                Log.e(
                                                        "VpnManagerService",
                                                        "Stopped user has no VPN");
                                            } else {
                                                vpn.onUserStopped();
                                                vpnManagerService3.mVpns.delete(intExtra);
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if ("android.intent.action.USER_ADDED".equals(action)) {
                                    VpnManagerService.m110$$Nest$monUserAdded(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.USER_REMOVED".equals(action)) {
                                    VpnManagerService.m111$$Nest$monUserRemoved(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                                    VpnManagerService.m112$$Nest$monUserUnlocked(
                                            this.this$0, intExtra);
                                    return;
                                }
                                if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
                                    VpnManagerService.m109$$Nest$monPackageReplaced(
                                            this.this$0, schemeSpecificPart, intExtra2);
                                    return;
                                }
                                if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                                    VpnManagerService.m108$$Nest$monPackageRemoved(
                                            this.this$0,
                                            schemeSpecificPart,
                                            intExtra2,
                                            intent.getBooleanExtra(
                                                    "android.intent.extra.REPLACING", false));
                                    return;
                                } else {
                                    if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                                        VpnManagerService.m107$$Nest$monPackageAdded(
                                                this.this$0,
                                                schemeSpecificPart,
                                                intExtra2,
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false));
                                        return;
                                    }
                                    Log.wtf(
                                            "VpnManagerService",
                                            "received unexpected intent: " + action);
                                    return;
                                }
                            default:
                                VpnManagerService.m106$$Nest$mensureRunningOnHandlerThread(
                                        this.this$0);
                                this.this$0.updateLockdownVpn();
                                context2.unregisterReceiver(this);
                                return;
                        }
                    }
                };
        this.mEnterpriseVpnStoreObj = new SparseArray();
        this.knoxVpnLock = new Object();
        this.mPersonaManager = null;
        this.mDefaultNetworkCallback = null;
        Context createAttributionContext = context.createAttributionContext("VPN_MANAGER");
        this.mContext = createAttributionContext;
        this.mDeps = dependencies;
        HandlerThread handlerThread = new HandlerThread("VpnManagerService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler threadHandler = handlerThread.getThreadHandler();
        this.mHandler = threadHandler;
        this.mVpnProfileStore = new VpnProfileStore();
        Context createContextAsUser =
                createAttributionContext.createContextAsUser(UserHandle.ALL, 0);
        this.mCm =
                (ConnectivityManager)
                        createAttributionContext.getSystemService(ConnectivityManager.class);
        this.mNMS =
                INetworkManagementService.Stub.asInterface(
                        ServiceManager.getService("network_management"));
        this.mNetd = NetdService.getInstance();
        this.mUserManager =
                (UserManager) createAttributionContext.getSystemService(UserManager.class);
        int mainUserId =
                ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                        .getMainUserId();
        this.mMainUserId = mainUserId;
        createContextAsUser.registerReceiver(
                broadcastReceiver,
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.USER_STARTED",
                        "android.intent.action.USER_STOPPED",
                        "android.intent.action.USER_ADDED",
                        "android.intent.action.USER_REMOVED",
                        "android.intent.action.USER_UNLOCKED"),
                null,
                threadHandler);
        createAttributionContext
                .createContextAsUser(UserHandle.of(mainUserId), 0)
                .registerReceiver(
                        broadcastReceiver2,
                        new IntentFilter("android.intent.action.USER_PRESENT"),
                        null,
                        threadHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        createContextAsUser.registerReceiver(broadcastReceiver, intentFilter, null, threadHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.server.action.LOCKDOWN_RESET");
        createContextAsUser.registerReceiver(
                broadcastReceiver,
                intentFilter2,
                "android.permission.NETWORK_STACK",
                threadHandler,
                2);
        Log.d("VpnManagerService", "VpnManagerService starting up");
    }

    public static VpnManagerService create(Context context) {
        return new VpnManagerService(context, new Dependencies());
    }

    public static IKnoxVpnPolicy getService() {
        return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
    }

    public static void logw(String str) {
        Log.w("VpnManagerService", str);
    }

    public final boolean addVpnAddress(String str, int i) {
        boolean addAddress;
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            addAddress = ((Vpn) this.mVpns.get(userId)).addAddress(i, str);
        }
        return addAddress;
    }

    public final void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        vpn.applyBlockingRulesToUidRange(z);
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    enterpriseVpn.applyBlockingRulesToUidRange(z);
                }
            } finally {
            }
        }
    }

    public final boolean checkIfLocalProxyPortExists(int i) {
        try {
            return getService().checkIfLocalProxyPortExists(i);
        } catch (Exception unused) {
            Log.e(
                    "VpnManagerService",
                    "Exception occured while trying to checkIfLocalProxyPortExists");
            return false;
        }
    }

    public final boolean checkIfUidIsExempted(int i) {
        try {
            return getService().checkIfUidIsExempted(i);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Error while checking if the uid of the app which originated the"
                                + " download is exempted or not "),
                    "VpnManagerService");
            return false;
        }
    }

    public final void createEnterpriseVpnInstance(String str, String str2, int i, int i2) {
        if (Binder.getCallingUid() == 1000 && !str.equals("com.samsung.sVpn")) {
            synchronized (this.knoxVpnLock) {
                try {
                    HashMap hashMap = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
                    if (hashMap == null || !hashMap.containsKey(str2)) {
                        EnterpriseVpn enterpriseVpn =
                                new EnterpriseVpn(
                                        this.mHandler.getLooper(),
                                        this.mContext,
                                        this.mNMS,
                                        this.mNetd,
                                        i,
                                        str,
                                        i2);
                        HashMap hashMap2 = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
                        if (hashMap2 == null) {
                            hashMap2 = new HashMap();
                        }
                        hashMap2.put(str2, enterpriseVpn);
                        this.mEnterpriseVpnStoreObj.put(i, hashMap2);
                    } else {
                        Log.d(
                                "VpnManagerService",
                                "createEnterpriseVpnInstance failed for " + str2 + " in user " + i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void deleteVpnProfile(String str) {
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            ((Vpn) this.mVpns.get(userId)).deleteVpnProfile(str);
        }
    }

    public final boolean disconnectKnoxVpn(String str, int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException();
        }
        if (DBG) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "vpn disconnect :  Profile: ", str, " callingUid: ", "VpnManagerService");
        }
        synchronized (this.knoxVpnLock) {
            try {
                int userId = UserHandle.getUserId(i);
                List list = null;
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(userId) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(userId)).get(str)
                                : null;
                if (enterpriseVpn == null) {
                    return false;
                }
                NetworkInfo.State state = enterpriseVpn.mNetworkInfo.getState();
                if (state != NetworkInfo.State.DISCONNECTED
                        && state != NetworkInfo.State.DISCONNECTING) {
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                enterpriseVpn.showNotification(str, false);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                long clearCallingIdentity2 = Binder.clearCallingIdentity();
                List arrayList = new ArrayList();
                try {
                    arrayList = getService().getDomainsByProfileName(str);
                } catch (Exception unused) {
                }
                if (arrayList == null || arrayList.size() <= 0) {
                    if (this.mPersonaManager == null) {
                        this.mPersonaManager =
                                (SemPersonaManager) this.mContext.getSystemService("persona");
                    }
                    SemPersonaManager semPersonaManager = this.mPersonaManager;
                    if (semPersonaManager != null) {
                        if (semPersonaManager == null) {
                            this.mPersonaManager =
                                    (SemPersonaManager) this.mContext.getSystemService("persona");
                        }
                        list = this.mPersonaManager.getKnoxIds(false);
                    }
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(0);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        if (!hasInterfaceAsUser(intValue)) {
                            if (DBG) {
                                Log.d(
                                        "VpnManagerService",
                                        "The connected Vpn is not exists in user " + intValue);
                            }
                            enterpriseVpn.hideNotification(intValue);
                        }
                    }
                } else {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        if (!hasInterfaceAsUser(Integer.parseInt((String) arrayList.get(i2)))) {
                            if (DBG) {
                                Log.d(
                                        "VpnManagerService",
                                        "The connected Vpn is not exists in user "
                                                + ((String) arrayList.get(i2)));
                            }
                            enterpriseVpn.hideNotification(
                                    Integer.parseInt((String) arrayList.get(i2)));
                        }
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity2);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "VpnManagerService", printWriter)) {
            IndentingPrintWriter indentingPrintWriter =
                    new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("VPNs:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mVpns) {
                for (int i = 0; i < this.mVpns.size(); i++) {
                    try {
                        indentingPrintWriter.println(
                                this.mVpns.keyAt(i)
                                        + ": "
                                        + ((Vpn) this.mVpns.valueAt(i)).getPackage());
                        indentingPrintWriter.increaseIndent();
                        ((Vpn) this.mVpns.valueAt(i)).dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void enforceCrossUserPermission(int i) {
        if (i == UserHandle.getCallingUserId()) {
            return;
        }
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.INTERACT_ACROSS_USERS_FULL", "VpnManagerService");
    }

    public final void enforceSettingsPermission() {
        PermissionUtils.enforceAnyPermissionOf(
                this.mContext,
                new String[] {
                    "android.permission.NETWORK_SETTINGS",
                    "android.permission.MAINLINE_NETWORK_STACK"
                });
    }

    public final ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) {
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        int callingUid = Binder.getCallingUid();
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingUid);
        ParcelFileDescriptor parcelFileDescriptor = null;
        int i = 0;
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "establishVpn called by ",
                packagesForUid != null ? packagesForUid[0] : null,
                "VpnManagerService");
        String str = vpnConfig.session;
        try {
            boolean checkIfVendorCreatedKnoxProfile =
                    getService().checkIfVendorCreatedKnoxProfile(str, callingUid, userId);
            Log.d(
                    "VpnManagerService",
                    "checkKnoxVpnProfileType: profileCreatedByKnoxAdmin value is "
                            + checkIfVendorCreatedKnoxProfile);
            if (checkIfVendorCreatedKnoxProfile) {
                i = knoxVpnProfileType(str);
            }
        } catch (Exception unused) {
        }
        if (i == 1) {
            Log.d("VpnManagerService", "establishVpn called by knox per app vpn");
            Log.d(
                    "VpnManagerService",
                    "establishEnterpriseVpn : user = "
                            + vpnConfig.user
                            + " Profile: "
                            + vpnConfig.session);
            String str2 = SystemProperties.get("net.vpn.framework");
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "establishEnterpriseVpn: getProperty value is '",
                    str2,
                    "'",
                    "VpnManagerService");
            synchronized (this.knoxVpnLock) {
                try {
                    if (this.mEnterpriseVpnStoreObj.get(userId) != null) {
                        EnterpriseVpn enterpriseVpn =
                                (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(userId))
                                                .get(vpnConfig.session);
                        if (enterpriseVpn != null && str2.equals("2.0")) {
                            vpnConfig.routes = null;
                            parcelFileDescriptor = enterpriseVpn.establish(vpnConfig);
                        }
                        Log.d(
                                "VpnManagerService",
                                "establishEnterpriseVpn: knoxVpnFd value is "
                                        + parcelFileDescriptor);
                    } else {
                        Log.e(
                                "VpnManagerService",
                                "Unable to find enterpriseVpn object in hashmap : user = "
                                        + userId);
                    }
                } finally {
                }
            }
        } else {
            synchronized (this.mVpns) {
                Log.d("VpnManagerService", "establishVpn called by non-knox vpn");
                throwIfLockdownEnabled();
                parcelFileDescriptor = ((Vpn) this.mVpns.get(userId)).establish(vpnConfig);
            }
        }
        if (DBG) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("establishVpn: config.session value is "),
                    vpnConfig.session,
                    "VpnManagerService");
        }
        return parcelFileDescriptor;
    }

    public final void factoryReset() {
        enforceSettingsPermission();
        if (this.mUserManager.hasUserRestriction("no_network_reset")
                || this.mUserManager.hasUserRestriction("no_config_vpn")) {
            return;
        }
        int callingUserId = UserHandle.getCallingUserId();
        synchronized (this.mVpns) {
            try {
                String alwaysOnVpnPackage = getAlwaysOnVpnPackage(callingUserId);
                if (alwaysOnVpnPackage != null) {
                    setAlwaysOnVpnPackage(callingUserId, null, false, null);
                    setVpnPackageAuthorization(alwaysOnVpnPackage, callingUserId, -1);
                }
                if (this.mLockdownEnabled && callingUserId == this.mMainUserId) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mVpnProfileStore.remove("LOCKDOWN_VPN");
                        this.mLockdownEnabled = false;
                        setLockdownTracker(null);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                VpnConfig vpnConfig = getVpnConfig(callingUserId);
                if (vpnConfig != null) {
                    if (vpnConfig.legacy) {
                        prepareVpn("[Legacy VPN]", "[Legacy VPN]", callingUserId);
                    } else {
                        setVpnPackageAuthorization(vpnConfig.user, callingUserId, -1);
                        prepareVpn(null, "[Legacy VPN]", callingUserId);
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final String getActiveDefaultInterface() {
        LinkProperties linkProperties;
        NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        if (networkCallback == null || (linkProperties = networkCallback.mLinkProperties) == null) {
            return null;
        }
        return linkProperties.getInterfaceName();
    }

    public final Network getActiveDefaultNetwork() {
        NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        if (networkCallback != null) {
            return networkCallback.mNetwork;
        }
        return null;
    }

    public final String getAlwaysOnVpnPackage(int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.getAlwaysOnPackage();
                }
                logw("User " + i + " has no Vpn configuration");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getAppExclusionList(int i, String str) {
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.getAppExclusionList(str);
                }
                logw("User " + i + " has no Vpn configuration");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean getChainingEnabledForProfile(int i) {
        try {
            return getService().getChainingEnabledForProfile(i) == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:

       r6 = r4.getDnsServers().iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:

       if (r6.hasNext() == false) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:

       r7 = r6.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:

       if (com.android.server.VpnManagerService.DBG == false) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003e, code lost:

       android.util.Log.d("VpnManagerService", "The knox vpn dns server being added for usb tethering use-case is " + r7.getHostAddress());
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0058, code lost:

       r0.add(r7.getHostAddress());
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] getDnsServerListForInterface(java.lang.String r7) {
        /*
            r6 = this;
            java.util.ArrayList r0 = com.google.android.collect.Lists.newArrayList()
            android.net.ConnectivityManager r1 = r6.mCm     // Catch: java.lang.Exception -> L63
            android.net.Network[] r1 = r1.getAllNetworks()     // Catch: java.lang.Exception -> L63
            int r2 = r1.length     // Catch: java.lang.Exception -> L63
            r3 = 0
        Lc:
            if (r3 >= r2) goto L63
            r4 = r1[r3]     // Catch: java.lang.Exception -> L63
            android.net.ConnectivityManager r5 = r6.mCm     // Catch: java.lang.Exception -> L63
            android.net.LinkProperties r4 = r5.getLinkProperties(r4)     // Catch: java.lang.Exception -> L63
            java.lang.String r5 = r4.getInterfaceName()     // Catch: java.lang.Exception -> L63
            if (r5 == 0) goto L60
            java.lang.String r5 = r4.getInterfaceName()     // Catch: java.lang.Exception -> L63
            boolean r5 = r5.equals(r7)     // Catch: java.lang.Exception -> L63
            if (r5 == 0) goto L60
            java.util.List r6 = r4.getDnsServers()     // Catch: java.lang.Exception -> L63
            java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Exception -> L63
        L2e:
            boolean r7 = r6.hasNext()     // Catch: java.lang.Exception -> L63
            if (r7 == 0) goto L63
            java.lang.Object r7 = r6.next()     // Catch: java.lang.Exception -> L63
            java.net.InetAddress r7 = (java.net.InetAddress) r7     // Catch: java.lang.Exception -> L63
            boolean r1 = com.android.server.VpnManagerService.DBG     // Catch: java.lang.Exception -> L63
            if (r1 == 0) goto L58
            java.lang.String r1 = "VpnManagerService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L63
            r2.<init>()     // Catch: java.lang.Exception -> L63
            java.lang.String r3 = "The knox vpn dns server being added for usb tethering use-case is "
            r2.append(r3)     // Catch: java.lang.Exception -> L63
            java.lang.String r3 = r7.getHostAddress()     // Catch: java.lang.Exception -> L63
            r2.append(r3)     // Catch: java.lang.Exception -> L63
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> L63
            android.util.Log.d(r1, r2)     // Catch: java.lang.Exception -> L63
        L58:
            java.lang.String r7 = r7.getHostAddress()     // Catch: java.lang.Exception -> L63
            r0.add(r7)     // Catch: java.lang.Exception -> L63
            goto L2e
        L60:
            int r3 = r3 + 1
            goto Lc
        L63:
            int r6 = r0.size()
            java.lang.String[] r6 = new java.lang.String[r6]
            java.lang.Object[] r6 = r0.toArray(r6)
            java.lang.String[] r6 = (java.lang.String[]) r6
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.VpnManagerService.getDnsServerListForInterface(java.lang.String):java.lang.String[]");
    }

    public final byte[] getFromVpnProfileStore(String str) {
        return this.mVpnProfileStore.get(str);
    }

    public final int getKnoxNwFilterHttpProxyPort(int i, String str) {
        if (IKnoxNetworkFilterService.Stub.asInterface(
                        ServiceManager.getService("knox_nwFilterMgr_policy"))
                == null) {
            return -1;
        }
        try {
            return IKnoxNetworkFilterService.Stub.asInterface(
                            ServiceManager.getService("knox_nwFilterMgr_policy"))
                    .getKnoxNwFilterHttpProxyPort(i, str);
        } catch (Exception e) {
            Log.d("VpnManagerService", e.getMessage());
            return -1;
        }
    }

    public final int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) {
        int[] iArr = new int[2];
        String str2 = getProxyInfoForUid(i)[1];
        if (str2 != null) {
            try {
                iArr[0] = Integer.parseInt(str2);
            } catch (Exception unused) {
            }
        }
        iArr[1] = getKnoxNwFilterHttpProxyPort(UserHandle.getUserId(i), str);
        return iArr;
    }

    public final LegacyVpnInfo getLegacyVpnInfo(int i) {
        LegacyVpnInfo legacyVpnInfoPrivileged;
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                synchronized (vpn) {
                    vpn.enforceControlPermission();
                    legacyVpnInfoPrivileged = vpn.getLegacyVpnInfoPrivileged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return legacyVpnInfoPrivileged;
    }

    public final int getNetIdforActiveDefaultInterface() {
        NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        if (networkCallback != null) {
            return networkCallback.mNetwork.getNetId();
        }
        return 0;
    }

    public final VpnProfileState getProvisionedVpnProfileState(String str) {
        VpnProfileState makeVpnProfileStateLocked;
        int callingUid = Binder.getCallingUid();
        verifyCallingUidAndPackage(callingUid, str);
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(userId);
                synchronized (vpn) {
                    Objects.requireNonNull(str, "No package name provided");
                    vpn.enforceNotRestrictedUser();
                    makeVpnProfileStateLocked =
                            vpn.isCurrentIkev2VpnLocked(str)
                                    ? vpn.makeVpnProfileStateLocked()
                                    : null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return makeVpnProfileStateLocked;
    }

    public final String[] getProxyInfoForUid(int i) {
        String[] strArr = new String[2];
        try {
            return getService().getProxyInfoForUid(i);
        } catch (Exception unused) {
            return strArr;
        }
    }

    public final VpnConfig getVpnConfig(int i) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn == null) {
                    return null;
                }
                return vpn.getVpnConfig();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Vpn getVpnIfOwner() {
        this.mDeps.getClass();
        int callingUid = Binder.getCallingUid();
        Vpn vpn = (Vpn) this.mVpns.get(UserHandle.getUserId(callingUid));
        if (vpn == null) {
            return null;
        }
        UnderlyingNetworkInfo underlyingNetworkInfo = vpn.getUnderlyingNetworkInfo();
        if (underlyingNetworkInfo == null || underlyingNetworkInfo.getOwnerUid() != callingUid) {
            vpn = null;
        }
        return vpn;
    }

    public final List getVpnLockdownAllowlist(int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.getLockdownAllowlist();
                }
                logw("User " + i + " has no Vpn configuration");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasInterfaceAsUser(int i) {
        EnterpriseVpn enterpriseVpn;
        List arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                arrayList = getService().getProfilesByDomain(String.valueOf(i));
            } catch (Exception e) {
                Log.d("VpnManagerService", "Exception: " + Log.getStackTraceString(e));
            }
            if (DBG) {
                Log.d(
                        "VpnManagerService",
                        "hasInterfaceAsUser > profiles.size : " + arrayList.size());
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                for (int i3 = 0; i3 < this.mEnterpriseVpnStoreObj.size(); i3++) {
                    int keyAt = this.mEnterpriseVpnStoreObj.keyAt(i3);
                    boolean z = DBG;
                    if (z) {
                        VpnManagerService$$ExternalSyntheticOutline0.m(
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        keyAt,
                                        "hasInterfaceAsUser > vpn key : ",
                                        ", profileName : "),
                                (String) arrayList.get(i2),
                                "VpnManagerService");
                    }
                    if (this.mEnterpriseVpnStoreObj.get(keyAt) != null
                            && (enterpriseVpn =
                                            (EnterpriseVpn)
                                                    ((HashMap)
                                                                    this.mEnterpriseVpnStoreObj.get(
                                                                            keyAt))
                                                            .get(arrayList.get(i2)))
                                    != null) {
                        NetworkInfo.State state = enterpriseVpn.mNetworkInfo.getState();
                        if (enterpriseVpn.mInterface != null
                                && state == NetworkInfo.State.CONNECTED) {
                            if (!z) {
                                return true;
                            }
                            Log.d("VpnManagerService", "hasInterfaceAsUser : return true");
                            return true;
                        }
                    }
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAlwaysOnVpnPackageSupported(int i, String str) {
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.isAlwaysOnPackageSupported(str);
                }
                logw("User " + i + " has no Vpn configuration");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isCallerCurrentAlwaysOnVpnApp() {
        boolean z;
        synchronized (this.mVpns) {
            try {
                Vpn vpnIfOwner = getVpnIfOwner();
                z = vpnIfOwner != null && vpnIfOwner.getAlwaysOn();
            } finally {
            }
        }
        return z;
    }

    public final boolean isCallerCurrentAlwaysOnVpnLockdownApp() {
        boolean z;
        synchronized (this.mVpns) {
            try {
                Vpn vpnIfOwner = getVpnIfOwner();
                z = vpnIfOwner != null && vpnIfOwner.getLockdown();
            } finally {
            }
        }
        return z;
    }

    public final boolean isDoEnabled(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            z =
                    ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                            .isDeviceManaged();
            if (z && i == 0) {
                Log.e(
                        "VpnManagerService",
                        "prepare filtering failed since device owner is configured");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final void isKnoxAlwaysOnConfigured(String str, String str2) {
        if (str != null && str.equalsIgnoreCase("[Legacy VPN]")) {
            Log.d(
                    "VpnManagerService",
                    "isKnoxAlwaysOnConfigured check ignored for old package name");
            return;
        }
        if (str2 != null && str2.equalsIgnoreCase("[Legacy VPN]")) {
            Log.d(
                    "VpnManagerService",
                    "isKnoxAlwaysOnConfigured check ignored for new package name");
            return;
        }
        Vpn vpn = (Vpn) this.mVpns.get(0);
        if (vpn == null || !vpn.mKnoxAlwaysOn) {
            return;
        }
        Log.i("VpnManagerService", "isKnoxAlwaysOnConfigured returns true");
        throw new IllegalStateException("Unavailable in lockdown mode");
    }

    public final boolean isLockdownVpnEnabled() {
        return this.mVpnProfileStore.get("LOCKDOWN_VPN") != null;
    }

    public final boolean isProxyConfiguredForKnoxVpn(int i) {
        try {
            return getService().isProxyConfiguredForKnoxVpn(i);
        } catch (Exception unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    i,
                    "Exception occured while trying to get the getProxyInfoConfig for knoxvpn uid ",
                    "VpnManagerService");
            return false;
        }
    }

    @SystemApi
    public boolean isVpnConfigured(int i) {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(userId);
                return (vpn == null || vpn.getNetwork() == null || vpn.getNetwork().getNetId() != i)
                        ? false
                        : true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isVpnLockdownEnabled(int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.getLockdown();
                }
                logw("User " + i + " has no Vpn configuration");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int knoxVpnProfileType(String str) {
        try {
            return getService().getKnoxVpnProfileType(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public final String[] listFromVpnProfileStore(String str) {
        return this.mVpnProfileStore.list(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:

       if (getService().getUidPidEnabled(r2, r7) == 0) goto L14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:

       throw new java.lang.SecurityException("Unauthorized Call to enable meta data");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean prepareEnterpriseVpnExt(java.lang.String r7, boolean r8) {
        /*
            r6 = this;
            java.lang.String r0 = "No permission to call prepareEnterpriseVpnExt : "
            int r1 = android.os.Binder.getCallingUid()
            int r1 = android.os.UserHandle.getUserId(r1)
            int r2 = android.os.Binder.getCallingUid()
            r3 = 1
            int r4 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L99
            r5 = 1000(0x3e8, float:1.401E-42)
            if (r4 == r5) goto L41
            com.samsung.android.knox.net.vpn.IKnoxVpnPolicy r4 = getService()     // Catch: java.lang.Exception -> L99
            int r5 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L99
            boolean r4 = r4.checkIfCallerIsVpnVendor(r5)     // Catch: java.lang.Exception -> L99
            if (r4 == 0) goto L26
            goto L41
        L26:
            java.lang.String r6 = "VpnManagerService"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L99
            r7.<init>(r0)     // Catch: java.lang.Exception -> L99
            int r8 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L99
            r7.append(r8)     // Catch: java.lang.Exception -> L99
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> L99
            android.util.Log.e(r6, r7)     // Catch: java.lang.Exception -> L99
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.Exception -> L99
            r6.<init>()     // Catch: java.lang.Exception -> L99
            throw r6     // Catch: java.lang.Exception -> L99
        L41:
            if (r8 == 0) goto L56
            com.samsung.android.knox.net.vpn.IKnoxVpnPolicy r0 = getService()     // Catch: java.lang.Exception -> L99
            int r0 = r0.getUidPidEnabled(r2, r7)     // Catch: java.lang.Exception -> L99
            if (r0 == 0) goto L4e
            goto L56
        L4e:
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.Exception -> L99
            java.lang.String r7 = "Unauthorized Call to enable meta data"
            r6.<init>(r7)     // Catch: java.lang.Exception -> L99
            throw r6     // Catch: java.lang.Exception -> L99
        L56:
            com.samsung.android.knox.net.vpn.IKnoxVpnPolicy r0 = getService()     // Catch: java.lang.Exception -> L99
            boolean r0 = r0.checkIfVendorCreatedKnoxProfile(r7, r2, r1)     // Catch: java.lang.Exception -> L99
            if (r0 == 0) goto L99
            com.samsung.android.knox.net.vpn.IKnoxVpnPolicy r0 = getService()     // Catch: java.lang.Exception -> L99
            int r0 = r0.getKnoxVpnProfileType(r7)     // Catch: java.lang.Exception -> L99
            if (r0 != 0) goto L73
            java.lang.String r6 = "VpnManagerService"
            java.lang.String r7 = "Non knox mode is not supported"
            android.util.Log.e(r6, r7)     // Catch: java.lang.Exception -> L99
            r6 = 0
            return r6
        L73:
            if (r0 != r3) goto L99
            java.lang.Object r0 = r6.knoxVpnLock     // Catch: java.lang.Exception -> L99
            monitor-enter(r0)     // Catch: java.lang.Exception -> L99
            android.util.SparseArray r2 = r6.mEnterpriseVpnStoreObj     // Catch: java.lang.Throwable -> L93
            java.lang.Object r2 = r2.get(r1)     // Catch: java.lang.Throwable -> L93
            if (r2 == 0) goto L95
            android.util.SparseArray r6 = r6.mEnterpriseVpnStoreObj     // Catch: java.lang.Throwable -> L93
            java.lang.Object r6 = r6.get(r1)     // Catch: java.lang.Throwable -> L93
            java.util.HashMap r6 = (java.util.HashMap) r6     // Catch: java.lang.Throwable -> L93
            java.lang.Object r6 = r6.get(r7)     // Catch: java.lang.Throwable -> L93
            com.android.server.connectivity.EnterpriseVpn r6 = (com.android.server.connectivity.EnterpriseVpn) r6     // Catch: java.lang.Throwable -> L93
            if (r6 == 0) goto L95
            r6.isMetaDataEnabled = r8     // Catch: java.lang.Throwable -> L93
            goto L95
        L93:
            r6 = move-exception
            goto L97
        L95:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L93
            goto L99
        L97:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L93
            throw r6     // Catch: java.lang.Exception -> L99
        L99:
            java.lang.String r6 = "VpnManagerService"
            java.lang.String r7 = "The return value in prepareEnterpriseVpnExt true"
            android.util.Log.d(r6, r7)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.VpnManagerService.prepareEnterpriseVpnExt(java.lang.String,"
                    + " boolean):boolean");
    }

    public final boolean prepareVpn(String str, String str2, int i) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                throwIfLockdownEnabled();
                isKnoxAlwaysOnConfigured(str, str2);
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn == null) {
                    return false;
                }
                return vpn.prepare(1, str, str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean provisionVpnProfile(VpnProfile vpnProfile, String str) {
        boolean provisionVpnProfile;
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            provisionVpnProfile =
                    ((Vpn) this.mVpns.get(userId)).provisionVpnProfile(vpnProfile, str);
        }
        return provisionVpnProfile;
    }

    public final boolean putIntoVpnProfileStore(String str, byte[] bArr) {
        return this.mVpnProfileStore.put(str, bArr);
    }

    public final void registerSystemDefaultNetworkCallback() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        NetworkCallback networkCallback = new NetworkCallback();
        networkCallback.mNetwork = null;
        networkCallback.mLinkProperties = null;
        this.mDefaultNetworkCallback = networkCallback;
        try {
            try {
                this.mCm.registerSystemDefaultNetworkCallback(networkCallback, this.mHandler);
            } catch (RuntimeException e) {
                Log.e(
                        "VpnManagerService",
                        "Failed to register system default network callback "
                                + Log.getStackTraceString(e));
                this.mDefaultNetworkCallback = null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeEnterpriseVpnInstance(String str, String str2, int i) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        if (str.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    vpn.resetUidListInNetworkCapabilities();
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                HashMap hashMap = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
                if (hashMap != null && hashMap.containsKey(str2)) {
                    Log.d(
                            "VpnManagerService",
                            "removeEnterpriseVpnInstance: profile "
                                    + str2
                                    + " in user "
                                    + i
                                    + " is removed");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    ((EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str2))
                            .showNotification(str2, false);
                    ((EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str2))
                            .cleanupObjects();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).remove(str2);
                }
                HashMap hashMap2 = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
                if (hashMap2 == null || hashMap2.size() == 0 || hashMap2.isEmpty()) {
                    Log.d(
                            "VpnManagerService",
                            "removeEnterpriseVpnInstance all profiles in user "
                                    + i
                                    + " is removed");
                    this.mEnterpriseVpnStoreObj.delete(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean removeFromVpnProfileStore(String str) {
        return this.mVpnProfileStore.remove(str);
    }

    public final boolean removeVpnAddress(String str, int i) {
        boolean removeAddress;
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            removeAddress = ((Vpn) this.mVpns.get(userId)).removeAddress(i, str);
        }
        return removeAddress;
    }

    public final void resetUidListInNetworkCapabilities(String str, int i, String str2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        vpn.resetUidListInNetworkCapabilities();
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    enterpriseVpn.resetUidListInNetworkCapabilities();
                }
            } finally {
            }
        }
    }

    public final boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List list) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                if (isLockdownVpnEnabled()) {
                    return false;
                }
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return false;
                }
                if (!vpn.setAlwaysOnPackage(str, list, z)) {
                    return false;
                }
                if (startAlwaysOnVpn(i)) {
                    return true;
                }
                vpn.setAlwaysOnPackage(null, null, false);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setAppExclusionList(int i, String str, List list) {
        boolean z;
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    throw new IllegalStateException(
                            "VPN for user " + i + " not ready yet. Skipping setting the list");
                }
                synchronized (vpn) {
                    vpn.enforceNotRestrictedUser();
                    if (vpn.storeAppExclusionList(str, list)) {
                        vpn.updateAppExclusionList(list);
                        z = true;
                    } else {
                        z = false;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final void setLockdownTracker(LockdownVpnTracker lockdownVpnTracker) {
        LockdownVpnTracker lockdownVpnTracker2 = this.mLockdownTracker;
        this.mLockdownTracker = null;
        if (lockdownVpnTracker2 != null) {
            synchronized (lockdownVpnTracker2.mStateLock) {
                lockdownVpnTracker2.shutdownLocked();
            }
        }
        if (lockdownVpnTracker != null) {
            this.mLockdownTracker = lockdownVpnTracker;
            synchronized (lockdownVpnTracker.mStateLock) {
                lockdownVpnTracker.initLocked();
            }
        }
    }

    public final boolean setUnderlyingNetworksForVpn(Network[] networkArr) {
        boolean underlyingNetworks;
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mVpns) {
            underlyingNetworks = ((Vpn) this.mVpns.get(userId)).setUnderlyingNetworks(networkArr);
        }
        return underlyingNetworks;
    }

    public final void setVpnPackageAuthorization(String str, int i, int i2) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    vpn.setPackageAuthorization(i2, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean startAlwaysOnVpn(int i) {
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.startAlwaysOnVpn();
                }
                Log.wtf("VpnManagerService", "User " + i + " has no Vpn configuration");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startLegacyVpn(VpnProfile vpnProfile) {
        LinkProperties linkProperties;
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 31
                && VpnProfile.isLegacyType(vpnProfile.type)) {
            throw new UnsupportedOperationException("Legacy VPN is deprecated");
        }
        this.mDeps.getClass();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        if (knoxVpnProfileType(vpnProfile.name) == 1) {
            linkProperties = this.mDefaultNetworkCallback.mLinkProperties;
            if (linkProperties == null) {
                throw new IllegalStateException("Missing active default network");
            }
        } else {
            linkProperties = null;
        }
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            ((Vpn) this.mVpns.get(userId)).startLegacyVpn(vpnProfile, linkProperties);
        }
    }

    public final String startVpnProfile(String str) {
        String startVpnProfile;
        int callingUid = Binder.getCallingUid();
        verifyCallingUidAndPackage(callingUid, str);
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            startVpnProfile = ((Vpn) this.mVpns.get(userId)).startVpnProfile(str);
        }
        return startVpnProfile;
    }

    public final void stopVpnProfile(String str) {
        int callingUid = Binder.getCallingUid();
        verifyCallingUidAndPackage(callingUid, str);
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(userId);
                synchronized (vpn) {
                    Objects.requireNonNull(str, "No package name provided");
                    vpn.enforceNotRestrictedUser();
                    if (vpn.isCurrentIkev2VpnLocked(str)) {
                        vpn.stopVpnRunnerAndNotifyAppLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void throwIfLockdownEnabled() {
        if (this.mLockdownEnabled) {
            throw new IllegalStateException("Unavailable in lockdown mode");
        }
    }

    public final void unregisterSystemDefaultNetworkCallback() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NetworkCallback networkCallback = this.mDefaultNetworkCallback;
                if (networkCallback != null) {
                    this.mCm.unregisterNetworkCallback(networkCallback);
                }
                this.mDefaultNetworkCallback = null;
            } catch (RuntimeException e) {
                Log.e(
                        "VpnManagerService",
                        "Failed to unregister system default network callback "
                                + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateEnterpriseVpn(String str, int i, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException();
        }
        synchronized (this.knoxVpnLock) {
            EnterpriseVpn enterpriseVpn = null;
            for (int i2 = 0; i2 < this.mEnterpriseVpnStoreObj.size(); i2++) {
                try {
                    int keyAt = this.mEnterpriseVpnStoreObj.keyAt(i2);
                    if (this.mEnterpriseVpnStoreObj.get(keyAt) == null
                            || (enterpriseVpn =
                                            (EnterpriseVpn)
                                                    ((HashMap)
                                                                    this.mEnterpriseVpnStoreObj.get(
                                                                            keyAt))
                                                            .get(str))
                                    == null) {}
                } finally {
                }
            }
            if (enterpriseVpn != null) {
                NetworkInfo.State state = enterpriseVpn.mNetworkInfo.getState();
                if (enterpriseVpn.mInterface != null && state == NetworkInfo.State.CONNECTED) {
                    if (z) {
                        enterpriseVpn.refreshNotification(i, z);
                    } else if (!hasInterfaceAsUser(i)) {
                        enterpriseVpn.refreshNotification(i, z);
                        enterpriseVpn.hideNotification(i);
                    }
                }
            }
        }
    }

    public final void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        vpn.updateLocalProxyInfo(proxyInfo);
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    enterpriseVpn.mHttpProxyInfo = proxyInfo;
                    EnterpriseVpn.AnonymousClass1 anonymousClass1 = enterpriseVpn.mNetworkAgent;
                    if (anonymousClass1 != null && enterpriseVpn.mInterface != null) {
                        anonymousClass1.sendLinkProperties(enterpriseVpn.makeLinkProperties());
                    }
                }
            } finally {
            }
        }
    }

    public final boolean updateLockdownVpn() {
        this.mDeps.getClass();
        if (Binder.getCallingUid() != 1000) {
            this.mDeps.getClass();
            if (Binder.getCallingUid() != UserHandle.getUid(this.mMainUserId, 1000)
                    && Binder.getCallingPid() != Process.myPid()) {
                logw("Lockdown VPN only available to system process or AID_SYSTEM on main user");
                return false;
            }
        }
        synchronized (this.mVpns) {
            try {
                boolean isLockdownVpnEnabled = isLockdownVpnEnabled();
                this.mLockdownEnabled = isLockdownVpnEnabled;
                if (!isLockdownVpnEnabled) {
                    setLockdownTracker(null);
                    return true;
                }
                byte[] bArr = this.mVpnProfileStore.get("LOCKDOWN_VPN");
                if (bArr == null) {
                    Log.e(
                            "VpnManagerService",
                            "Lockdown VPN configured but cannot be read from keystore");
                    return false;
                }
                String str = new String(bArr);
                VpnProfile decode =
                        VpnProfile.decode(str, this.mVpnProfileStore.get("VPN_".concat(str)));
                if (decode == null) {
                    Log.e(
                            "VpnManagerService",
                            "Lockdown VPN configured invalid profile ".concat(str));
                    setLockdownTracker(null);
                    return true;
                }
                this.mDeps.getClass();
                int userId = UserHandle.getUserId(Binder.getCallingUid());
                Vpn vpn = (Vpn) this.mVpns.get(userId);
                if (vpn == null) {
                    logw("VPN for user " + userId + " not ready yet. Skipping lockdown");
                    return false;
                }
                Dependencies dependencies = this.mDeps;
                Context context = this.mContext;
                Handler handler = this.mHandler;
                dependencies.getClass();
                setLockdownTracker(new LockdownVpnTracker(context, handler, vpn, decode));
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateNotificationIcon(int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException();
        }
        synchronized (this.mVpns) {
            try {
                Vpn vpn = (Vpn) this.mVpns.get(i);
                if (vpn != null && vpn.getNetwork() != null) {
                    vpn.updateNotificationIconForKnoxStrongSwan(i);
                }
            } finally {
            }
        }
        synchronized (this.knoxVpnLock) {
            try {
                if (this.mEnterpriseVpnStoreObj.get(i) != null) {
                    for (EnterpriseVpn enterpriseVpn :
                            ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).values()) {
                        if (enterpriseVpn != null) {
                            NetworkInfo.State state = enterpriseVpn.mNetworkInfo.getState();
                            if (enterpriseVpn.mInterface != null
                                    && state == NetworkInfo.State.CONNECTED) {
                                if (DBG) {
                                    Log.d("VpnManagerService", "updateNotificationIcon is called");
                                }
                                enterpriseVpn.updateNotificationIcon(i);
                                return;
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void updateUidRangesToPerAppVpn(
            String str, int i, boolean z, int[] iArr, String str2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        int i2 = 0;
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        HashSet hashSet = new HashSet();
                        int length = iArr.length;
                        while (i2 < length) {
                            hashSet.add(Integer.valueOf(iArr[i2]));
                            i2++;
                        }
                        vpn.updateUidRangesToPerAppVpn(hashSet, z);
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    HashSet hashSet2 = new HashSet();
                    int length2 = iArr.length;
                    while (i2 < length2) {
                        hashSet2.add(Integer.valueOf(iArr[i2]));
                        i2++;
                    }
                    enterpriseVpn.updateUidRangesToPerAppVpn(hashSet2, z);
                }
            } finally {
            }
        }
    }

    public final void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        vpn.updateUidRangesToUserVpn(i2, z);
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    enterpriseVpn.updateUidRangesToUserVpn(i2, z);
                }
            } finally {
            }
        }
    }

    public final void updateUidRangesToUserVpnWithBlackList(
            String str, int i, int i2, int[] iArr, String str2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        int i3 = 0;
        if (str2.equals("com.samsung.sVpn")) {
            synchronized (this.mVpns) {
                try {
                    Vpn vpn = (Vpn) this.mVpns.get(i);
                    if (vpn != null) {
                        HashSet hashSet = new HashSet();
                        int length = iArr.length;
                        while (i3 < length) {
                            hashSet.add(Integer.valueOf(iArr[i3]));
                            i3++;
                        }
                        vpn.updateUidRangesToUserVpnWithBlackList(i2, hashSet);
                    }
                } finally {
                }
            }
            return;
        }
        synchronized (this.knoxVpnLock) {
            try {
                EnterpriseVpn enterpriseVpn =
                        this.mEnterpriseVpnStoreObj.get(i) != null
                                ? (EnterpriseVpn)
                                        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
                                : null;
                if (enterpriseVpn != null) {
                    HashSet hashSet2 = new HashSet();
                    int length2 = iArr.length;
                    while (i3 < length2) {
                        hashSet2.add(Integer.valueOf(iArr[i3]));
                        i3++;
                    }
                    enterpriseVpn.updateUidRangesToUserVpnWithBlackList(i2, hashSet2);
                }
            } finally {
            }
        }
    }

    public final void verifyCallingUidAndPackage(int i, String str) {
        int i2;
        int userId = UserHandle.getUserId(i);
        PackageManager packageManager = this.mContext.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            i2 = packageManager.getPackageUidAsUser(str, userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (PackageManager.NameNotFoundException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            i2 = -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (i2 != i) {
            throw new SecurityException(
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            i, str, " does not belong to uid "));
        }
    }
}
