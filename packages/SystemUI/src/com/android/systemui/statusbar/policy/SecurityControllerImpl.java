package com.android.systemui.statusbar.policy;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.VpnManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.KeyChain;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.supervision.shared.DeprecateDpmSupervisionApis;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class SecurityControllerImpl implements SecurityController {
    public static final boolean DEBUG = Log.isLoggable("SecurityController", 3);
    public static final NetworkRequest REQUEST = new NetworkRequest.Builder().clearCapabilities().addTransportType(4).build();
    public final Executor mBgExecutor;
    public final AnonymousClass3 mBroadcastReceiver;
    public final Context mContext;
    public int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final Executor mMainExecutor;
    public final AnonymousClass2 mNetworkCallback;
    public final PackageManager mPackageManager;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final VpnManager mVpnManager;
    public int mVpnUserId;
    public final ArrayList mCallbacks = new ArrayList();
    public SparseArray mCurrentVpns = new SparseArray();
    public final SparseArray mNetworkProperties = new SparseArray();
    public final ArrayMap mHasCACerts = new ArrayMap();

    public class NetworkProperties {
        public String interfaceName;
        public boolean validated;

        public NetworkProperties(String str, boolean z) {
            this.interfaceName = str;
            this.validated = z;
        }
    }

    /* renamed from: -$$Nest$mupdateState, reason: not valid java name */
    public static void m3107$$Nest$mupdateState(SecurityControllerImpl securityControllerImpl) {
        LegacyVpnInfo legacyVpnInfo;
        securityControllerImpl.getClass();
        SparseArray sparseArray = new SparseArray();
        for (UserInfo userInfo : securityControllerImpl.mUserManager.getUsers()) {
            VpnConfig vpnConfig = securityControllerImpl.mVpnManager.getVpnConfig(userInfo.id);
            if (vpnConfig != null && (!vpnConfig.legacy || ((legacyVpnInfo = securityControllerImpl.mVpnManager.getLegacyVpnInfo(userInfo.id)) != null && legacyVpnInfo.state == 3))) {
                sparseArray.put(userInfo.id, vpnConfig);
            }
        }
        securityControllerImpl.mCurrentVpns = sparseArray;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.net.ConnectivityManager$NetworkCallback, com.android.systemui.statusbar.policy.SecurityControllerImpl$2] */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.policy.SecurityControllerImpl$3] */
    public SecurityControllerImpl(Context context, UserTracker userTracker, Handler handler, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, DumpManager dumpManager, Provider provider) {
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                SecurityControllerImpl securityControllerImpl = SecurityControllerImpl.this;
                securityControllerImpl.mCurrentUserId = i;
                UserInfo userInfo = securityControllerImpl.mUserManager.getUserInfo(i);
                if (userInfo.isRestricted()) {
                    securityControllerImpl.mVpnUserId = userInfo.restrictedProfileParentId;
                } else {
                    securityControllerImpl.mVpnUserId = securityControllerImpl.mCurrentUserId;
                }
                securityControllerImpl.fireCallbacks();
            }
        };
        this.mUserChangedCallback = callback;
        ?? r0 = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onAvailable " + network.getNetId());
                }
                SecurityControllerImpl.m3107$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkProperties networkProperties;
                boolean zHasCapability;
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onCapabilitiesChanged " + network.getNetId());
                }
                synchronized (SecurityControllerImpl.this.mNetworkProperties) {
                    networkProperties = (NetworkProperties) SecurityControllerImpl.this.mNetworkProperties.get(network.getNetId());
                }
                if (networkProperties == null || networkProperties.validated == (zHasCapability = networkCapabilities.hasCapability(16))) {
                    return;
                }
                networkProperties.validated = zHasCapability;
                SecurityControllerImpl.this.fireCallbacks();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onLinkPropertiesChanged " + network.getNetId());
                }
                String interfaceName = linkProperties.getInterfaceName();
                if (interfaceName == null) {
                    Log.w("SecurityController", "onLinkPropertiesChanged event with null interface");
                    return;
                }
                synchronized (SecurityControllerImpl.this.mNetworkProperties) {
                    try {
                        NetworkProperties networkProperties = (NetworkProperties) SecurityControllerImpl.this.mNetworkProperties.get(network.getNetId());
                        if (networkProperties == null) {
                            SecurityControllerImpl.this.mNetworkProperties.put(network.getNetId(), new NetworkProperties(interfaceName, false));
                        } else {
                            networkProperties.interfaceName = interfaceName;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onLost " + network.getNetId());
                }
                synchronized (SecurityControllerImpl.this.mNetworkProperties) {
                    SecurityControllerImpl.this.mNetworkProperties.delete(network.getNetId());
                }
                SecurityControllerImpl.m3107$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }
        };
        this.mNetworkCallback = r0;
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                final int intExtra;
                if ("android.security.action.TRUST_STORE_CHANGED".equals(intent.getAction())) {
                    final SecurityControllerImpl securityControllerImpl = SecurityControllerImpl.this;
                    final int sendingUserId = getSendingUserId();
                    securityControllerImpl.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x00ab  */
                        /* JADX WARN: Type inference failed for: r1v7, types: [android.util.ArrayMap] */
                        /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Integer, java.lang.Object] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() throws Throwable {
                            Pair pair;
                            boolean z;
                            String str;
                            Object obj;
                            SecurityControllerImpl securityControllerImpl2 = securityControllerImpl;
                            int i = sendingUserId;
                            boolean z2 = SecurityControllerImpl.DEBUG;
                            securityControllerImpl2.getClass();
                            String str2 = "Refreshing CA Certs ";
                            boolean z3 = SecurityControllerImpl.DEBUG;
                            Pair pair2 = null;
                            try {
                                try {
                                    try {
                                        KeyChain.KeyChainConnection keyChainConnectionBindAsUser = KeyChain.bindAsUser(securityControllerImpl2.mContext, UserHandle.of(i));
                                        try {
                                            pair = new Pair(Integer.valueOf(i), Boolean.valueOf(!keyChainConnectionBindAsUser.getService().getUserCaAliases().getList().isEmpty()));
                                            try {
                                                keyChainConnectionBindAsUser.close();
                                                if (z3) {
                                                    Log.d("SecurityController", "Refreshing CA Certs " + pair);
                                                }
                                                Object obj2 = pair.second;
                                                str2 = str2;
                                                z3 = z3;
                                                if (obj2 != null) {
                                                    securityControllerImpl2.mHasCACerts.put((Integer) pair.first, (Boolean) obj2);
                                                    securityControllerImpl2.fireCallbacks();
                                                }
                                            } catch (RemoteException | AssertionError | IllegalStateException | InterruptedException e) {
                                                e = e;
                                                Log.i("SecurityController", "failed to get CA certs", e);
                                                Pair pair3 = new Pair(Integer.valueOf(i), null);
                                                if (z3) {
                                                    Log.d("SecurityController", "Refreshing CA Certs " + pair3);
                                                }
                                                Object obj3 = pair3.second;
                                                str2 = str2;
                                                z3 = z3;
                                                if (obj3 != null) {
                                                    ?? r12 = securityControllerImpl2.mHasCACerts;
                                                    ?? r2 = (Integer) pair3.first;
                                                    r12.put(r2, (Boolean) obj3);
                                                    securityControllerImpl2.fireCallbacks();
                                                    str2 = r12;
                                                    z3 = r2;
                                                }
                                            }
                                        } finally {
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        pair2 = pair;
                                        str = str2;
                                        z = z3;
                                        if (z) {
                                            Log.d("SecurityController", str + pair2);
                                        }
                                        if (pair2 != null && (obj = pair2.second) != null) {
                                            securityControllerImpl2.mHasCACerts.put((Integer) pair2.first, (Boolean) obj);
                                            securityControllerImpl2.fireCallbacks();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    str = str2;
                                    z = z3;
                                    if (z) {
                                    }
                                    if (pair2 != null) {
                                        securityControllerImpl2.mHasCACerts.put((Integer) pair2.first, (Boolean) obj);
                                        securityControllerImpl2.fireCallbacks();
                                    }
                                    throw th;
                                }
                            } catch (RemoteException | AssertionError | IllegalStateException | InterruptedException e2) {
                                e = e2;
                                pair = null;
                            }
                        }
                    });
                } else {
                    if (!"android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000) {
                        return;
                    }
                    final SecurityControllerImpl securityControllerImpl2 = SecurityControllerImpl.this;
                    securityControllerImpl2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x00ab  */
                        /* JADX WARN: Type inference failed for: r1v7, types: [android.util.ArrayMap] */
                        /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Integer, java.lang.Object] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() throws Throwable {
                            Pair pair;
                            boolean z;
                            String str;
                            Object obj;
                            SecurityControllerImpl securityControllerImpl22 = securityControllerImpl2;
                            int i = intExtra;
                            boolean z2 = SecurityControllerImpl.DEBUG;
                            securityControllerImpl22.getClass();
                            String str2 = "Refreshing CA Certs ";
                            boolean z3 = SecurityControllerImpl.DEBUG;
                            Pair pair2 = null;
                            try {
                                try {
                                    try {
                                        KeyChain.KeyChainConnection keyChainConnectionBindAsUser = KeyChain.bindAsUser(securityControllerImpl22.mContext, UserHandle.of(i));
                                        try {
                                            pair = new Pair(Integer.valueOf(i), Boolean.valueOf(!keyChainConnectionBindAsUser.getService().getUserCaAliases().getList().isEmpty()));
                                            try {
                                                keyChainConnectionBindAsUser.close();
                                                if (z3) {
                                                    Log.d("SecurityController", "Refreshing CA Certs " + pair);
                                                }
                                                Object obj2 = pair.second;
                                                str2 = str2;
                                                z3 = z3;
                                                if (obj2 != null) {
                                                    securityControllerImpl22.mHasCACerts.put((Integer) pair.first, (Boolean) obj2);
                                                    securityControllerImpl22.fireCallbacks();
                                                }
                                            } catch (RemoteException | AssertionError | IllegalStateException | InterruptedException e) {
                                                e = e;
                                                Log.i("SecurityController", "failed to get CA certs", e);
                                                Pair pair3 = new Pair(Integer.valueOf(i), null);
                                                if (z3) {
                                                    Log.d("SecurityController", "Refreshing CA Certs " + pair3);
                                                }
                                                Object obj3 = pair3.second;
                                                str2 = str2;
                                                z3 = z3;
                                                if (obj3 != null) {
                                                    ?? r12 = securityControllerImpl22.mHasCACerts;
                                                    ?? r2 = (Integer) pair3.first;
                                                    r12.put(r2, (Boolean) obj3);
                                                    securityControllerImpl22.fireCallbacks();
                                                    str2 = r12;
                                                    z3 = r2;
                                                }
                                            }
                                        } finally {
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        pair2 = pair;
                                        str = str2;
                                        z = z3;
                                        if (z) {
                                            Log.d("SecurityController", str + pair2);
                                        }
                                        if (pair2 != null && (obj = pair2.second) != null) {
                                            securityControllerImpl22.mHasCACerts.put((Integer) pair2.first, (Boolean) obj);
                                            securityControllerImpl22.fireCallbacks();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    str = str2;
                                    z = z3;
                                    if (z) {
                                    }
                                    if (pair2 != null) {
                                        securityControllerImpl22.mHasCACerts.put((Integer) pair2.first, (Boolean) obj);
                                        securityControllerImpl22.fireCallbacks();
                                    }
                                    throw th;
                                }
                            } catch (RemoteException | AssertionError | IllegalStateException | InterruptedException e2) {
                                e = e2;
                                pair = null;
                            }
                        }
                    });
                }
            }
        };
        this.mBroadcastReceiver = r1;
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mVpnManager = (VpnManager) context.getSystemService(VpnManager.class);
        this.mPackageManager = context.getPackageManager();
        int i = DeprecateDpmSupervisionApis.$r8$clinit;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.security.action.TRUST_STORE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        broadcastDispatcher.registerReceiverWithHandler(r1, intentFilter, handler, UserHandle.ALL);
        connectivityManager.registerNetworkCallback(REQUEST, (ConnectivityManager.NetworkCallback) r0);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        int userId = userTrackerImpl.getUserId();
        this.mCurrentUserId = userId;
        UserInfo userInfo = userManager.getUserInfo(userId);
        if (userInfo.isRestricted()) {
            this.mVpnUserId = userInfo.restrictedProfileParentId;
        } else {
            this.mVpnUserId = this.mCurrentUserId;
        }
        fireCallbacks();
        userTrackerImpl.addCallback(callback, executor);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        SecurityController.SecurityControllerCallback securityControllerCallback = (SecurityController.SecurityControllerCallback) obj;
        synchronized (this.mCallbacks) {
            if (securityControllerCallback != null) {
                try {
                    if (!this.mCallbacks.contains(securityControllerCallback)) {
                        if (DEBUG) {
                            Log.d("SecurityController", "addCallback " + securityControllerCallback);
                        }
                        this.mCallbacks.add(securityControllerCallback);
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SecurityController state:");
        printWriter.print("  mCurrentVpns={");
        for (int i = 0; i < this.mCurrentVpns.size(); i++) {
            if (i > 0) {
                printWriter.print(", ");
            }
            printWriter.print(this.mCurrentVpns.keyAt(i));
            printWriter.print('=');
            printWriter.print(((VpnConfig) this.mCurrentVpns.valueAt(i)).user);
        }
        printWriter.println("}");
        printWriter.print("  mNetworkProperties={");
        synchronized (this.mNetworkProperties) {
            for (int i2 = 0; i2 < this.mNetworkProperties.size(); i2++) {
                try {
                    if (i2 > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(this.mNetworkProperties.keyAt(i2));
                    printWriter.print("={");
                    printWriter.print(((NetworkProperties) this.mNetworkProperties.valueAt(i2)).interfaceName);
                    printWriter.print(", ");
                    printWriter.print(((NetworkProperties) this.mNetworkProperties.valueAt(i2)).validated);
                    printWriter.print("}");
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        printWriter.println("}");
    }

    public final void fireCallbacks() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SecurityController.SecurityControllerCallback) obj).onStateChanged();
        }
    }

    public final DeviceAdminInfo getDeviceAdminInfo() {
        ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(new UserHandle(this.mCurrentUserId));
        try {
            ResolveInfo resolveInfo = new ResolveInfo();
            resolveInfo.activityInfo = this.mPackageManager.getReceiverInfo(profileOwnerOrDeviceOwnerSupervisionComponent, 128);
            return new DeviceAdminInfo(this.mContext, resolveInfo);
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException unused) {
            return null;
        }
    }

    public final String getNameForVpnConfig(VpnConfig vpnConfig, UserHandle userHandle) {
        if (vpnConfig.legacy) {
            return this.mContext.getString(R.string.legacy_vpn_name);
        }
        String str = vpnConfig.user;
        try {
            Context context = this.mContext;
            return VpnConfig.getVpnLabel(context.createPackageContextAsUser(context.getPackageName(), 0, userHandle), str).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SecurityController", "Package " + str + " is not present", e);
            return null;
        }
    }

    public final String getPrimaryVpnName() {
        VpnConfig vpnConfig = (VpnConfig) this.mCurrentVpns.get(this.mVpnUserId);
        if (vpnConfig != null) {
            return getNameForVpnConfig(vpnConfig, new UserHandle(this.mVpnUserId));
        }
        return null;
    }

    public final boolean getVpnValidationStatus(VpnConfig vpnConfig) {
        synchronized (this.mNetworkProperties) {
            for (int i = 0; i < this.mNetworkProperties.size(); i++) {
                try {
                    if (((NetworkProperties) this.mNetworkProperties.valueAt(i)).interfaceName.equals(vpnConfig.interfaze)) {
                        return ((NetworkProperties) this.mNetworkProperties.valueAt(i)).validated;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    public final int getWorkProfileUserId$1(int i) {
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    public final String getWorkProfileVpnName() {
        VpnConfig vpnConfig;
        int workProfileUserId$1 = getWorkProfileUserId$1(this.mVpnUserId);
        if (workProfileUserId$1 == -10000 || (vpnConfig = (VpnConfig) this.mCurrentVpns.get(workProfileUserId$1)) == null) {
            return null;
        }
        return getNameForVpnConfig(vpnConfig, UserHandle.of(workProfileUserId$1));
    }

    public final boolean hasCACertInCurrentUser() {
        Boolean bool = (Boolean) this.mHasCACerts.get(Integer.valueOf(this.mCurrentUserId));
        return bool != null && bool.booleanValue();
    }

    public final boolean hasCACertInWorkProfile() {
        Boolean bool;
        int workProfileUserId$1 = getWorkProfileUserId$1(this.mCurrentUserId);
        return (workProfileUserId$1 == -10000 || (bool = (Boolean) this.mHasCACerts.get(Integer.valueOf(workProfileUserId$1))) == null || !bool.booleanValue()) ? false : true;
    }

    public final boolean hasWorkProfile$1() {
        return getWorkProfileUserId$1(this.mCurrentUserId) != -10000;
    }

    public final boolean isParentalControlsEnabled() {
        int i = DeprecateDpmSupervisionApis.$r8$clinit;
        return this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(new UserHandle(this.mCurrentUserId)) != null;
    }

    public final boolean isSecureWifiEnabled() {
        String str;
        VpnConfig vpnConfig = (VpnConfig) this.mCurrentVpns.get(this.mVpnUserId);
        return vpnConfig != null && (str = vpnConfig.user) != null && str.equals("com.samsung.android.fast") && this.mContext.getPackageManager().checkSignatures("android", vpnConfig.user) == 0;
    }

    public final boolean isVpnBranded() throws PackageManager.NameNotFoundException {
        VpnConfig vpnConfig = (VpnConfig) this.mCurrentVpns.get(this.mVpnUserId);
        if (vpnConfig == null) {
            return false;
        }
        String str = vpnConfig.legacy ? null : vpnConfig.user;
        if (str == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 128);
            if (applicationInfo != null && applicationInfo.metaData != null && applicationInfo.isSystemApp()) {
                return applicationInfo.metaData.getBoolean("com.android.systemui.IS_BRANDED", false);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        SecurityController.SecurityControllerCallback securityControllerCallback = (SecurityController.SecurityControllerCallback) obj;
        synchronized (this.mCallbacks) {
            try {
                if (securityControllerCallback == null) {
                    return;
                }
                if (DEBUG) {
                    Log.d("SecurityController", "removeCallback " + securityControllerCallback);
                }
                this.mCallbacks.remove(securityControllerCallback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
