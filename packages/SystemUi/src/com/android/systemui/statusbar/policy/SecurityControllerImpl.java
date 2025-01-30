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
import android.net.Network;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecurityControllerImpl implements SecurityController {
    public static final boolean DEBUG = Log.isLoggable("SecurityController", 3);
    public static final NetworkRequest REQUEST = new NetworkRequest.Builder().clearCapabilities().build();
    public final Executor mBgExecutor;
    public final C34373 mBroadcastReceiver;
    public final Context mContext;
    public int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final Executor mMainExecutor;
    public final C34362 mNetworkCallback;
    public final PackageManager mPackageManager;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public final VpnManager mVpnManager;
    public int mVpnUserId;
    public final ArrayList mCallbacks = new ArrayList();
    public SparseArray mCurrentVpns = new SparseArray();
    public final ArrayMap mHasCACerts = new ArrayMap();

    /* renamed from: -$$Nest$mupdateState, reason: not valid java name */
    public static void m1728$$Nest$mupdateState(SecurityControllerImpl securityControllerImpl) {
        LegacyVpnInfo legacyVpnInfo;
        securityControllerImpl.getClass();
        SparseArray sparseArray = new SparseArray();
        for (UserInfo userInfo : securityControllerImpl.mUserManager.getUsers()) {
            int i = userInfo.id;
            VpnManager vpnManager = securityControllerImpl.mVpnManager;
            VpnConfig vpnConfig = vpnManager.getVpnConfig(i);
            if (vpnConfig != null && (!vpnConfig.legacy || ((legacyVpnInfo = vpnManager.getLegacyVpnInfo(userInfo.id)) != null && legacyVpnInfo.state == 3))) {
                sparseArray.put(userInfo.id, vpnConfig);
            }
        }
        securityControllerImpl.mCurrentVpns = sparseArray;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.net.ConnectivityManager$NetworkCallback, com.android.systemui.statusbar.policy.SecurityControllerImpl$2] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.policy.SecurityControllerImpl$3] */
    public SecurityControllerImpl(Context context, UserTracker userTracker, Handler handler, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, DumpManager dumpManager) {
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
        ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onAvailable " + network.getNetId());
                }
                SecurityControllerImpl.m1728$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onLost " + network.getNetId());
                }
                SecurityControllerImpl.m1728$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }
        };
        this.mNetworkCallback = r1;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                final int intExtra;
                if ("android.security.action.TRUST_STORE_CHANGED".equals(intent.getAction())) {
                    Log.d("SecurityController", "ACTION_TRUST_STORE_CHANGED intent: refreshCACerts()");
                    final SecurityControllerImpl securityControllerImpl = SecurityControllerImpl.this;
                    final int sendingUserId = getSendingUserId();
                    boolean z = SecurityControllerImpl.DEBUG;
                    securityControllerImpl.getClass();
                    securityControllerImpl.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0
                        /* JADX WARN: Not initialized variable reg: 9, insn: 0x00c1: MOVE (r6 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:194), block:B:41:0x00c1 */
                        /* JADX WARN: Removed duplicated region for block: B:43:0x00c4  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            Pair pair;
                            Integer num;
                            Object obj;
                            KeyChain.KeyChainConnection bindAsUser;
                            SecurityControllerImpl securityControllerImpl2 = SecurityControllerImpl.this;
                            int i = sendingUserId;
                            ArrayMap arrayMap = securityControllerImpl2.mHasCACerts;
                            boolean z2 = SecurityControllerImpl.DEBUG;
                            Pair pair2 = null;
                            try {
                                try {
                                    try {
                                        bindAsUser = KeyChain.bindAsUser(securityControllerImpl2.mContext, UserHandle.of(i));
                                    } catch (Throwable th) {
                                        th = th;
                                        pair2 = pair;
                                        if (z2) {
                                            Log.d("SecurityController", "Refreshing CA Certs " + pair2);
                                        }
                                        if (pair2 != null && pair2.second != null) {
                                            Log.d("SecurityController", "mHasCACerts updated: " + pair2.second);
                                            arrayMap.put((Integer) pair2.first, (Boolean) pair2.second);
                                            securityControllerImpl2.fireCallbacks();
                                        }
                                        throw th;
                                    }
                                } catch (RemoteException | AssertionError | InterruptedException e) {
                                    e = e;
                                }
                                try {
                                    Pair pair3 = new Pair(Integer.valueOf(i), Boolean.valueOf(!bindAsUser.getService().getUserCaAliases().getList().isEmpty()));
                                    try {
                                        bindAsUser.close();
                                        if (z2) {
                                            Log.d("SecurityController", "Refreshing CA Certs " + pair3);
                                        }
                                    } catch (RemoteException | AssertionError | InterruptedException e2) {
                                        e = e2;
                                        Log.i("SecurityController", "failed to get CA certs", e);
                                        Pair pair4 = new Pair(Integer.valueOf(i), null);
                                        if (z2) {
                                            Log.d("SecurityController", "Refreshing CA Certs " + pair4);
                                        }
                                        if (pair4.second != null) {
                                            Log.d("SecurityController", "mHasCACerts updated: " + pair4.second);
                                            num = (Integer) pair4.first;
                                            obj = pair4.second;
                                            arrayMap.put(num, (Boolean) obj);
                                            securityControllerImpl2.fireCallbacks();
                                        }
                                        return;
                                    }
                                    if (pair3.second != null) {
                                        Log.d("SecurityController", "mHasCACerts updated: " + pair3.second);
                                        num = (Integer) pair3.first;
                                        obj = pair3.second;
                                        arrayMap.put(num, (Boolean) obj);
                                        securityControllerImpl2.fireCallbacks();
                                    }
                                } catch (Throwable th2) {
                                    if (bindAsUser != null) {
                                        try {
                                            bindAsUser.close();
                                        } catch (Throwable th3) {
                                            th2.addSuppressed(th3);
                                        }
                                    }
                                    throw th2;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                if (z2) {
                                }
                                if (pair2 != null) {
                                    Log.d("SecurityController", "mHasCACerts updated: " + pair2.second);
                                    arrayMap.put((Integer) pair2.first, (Boolean) pair2.second);
                                    securityControllerImpl2.fireCallbacks();
                                }
                                throw th;
                            }
                        }
                    });
                    return;
                }
                if (!"android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000) {
                    return;
                }
                Log.d("SecurityController", "ACTION_USER_UNLOCKED intent: refreshCACerts()");
                final SecurityControllerImpl securityControllerImpl2 = SecurityControllerImpl.this;
                boolean z2 = SecurityControllerImpl.DEBUG;
                securityControllerImpl2.getClass();
                securityControllerImpl2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0
                    /* JADX WARN: Not initialized variable reg: 9, insn: 0x00c1: MOVE (r6 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:194), block:B:41:0x00c1 */
                    /* JADX WARN: Removed duplicated region for block: B:43:0x00c4  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        Pair pair;
                        Integer num;
                        Object obj;
                        KeyChain.KeyChainConnection bindAsUser;
                        SecurityControllerImpl securityControllerImpl22 = SecurityControllerImpl.this;
                        int i = intExtra;
                        ArrayMap arrayMap = securityControllerImpl22.mHasCACerts;
                        boolean z22 = SecurityControllerImpl.DEBUG;
                        Pair pair2 = null;
                        try {
                            try {
                                try {
                                    bindAsUser = KeyChain.bindAsUser(securityControllerImpl22.mContext, UserHandle.of(i));
                                } catch (Throwable th) {
                                    th = th;
                                    pair2 = pair;
                                    if (z22) {
                                        Log.d("SecurityController", "Refreshing CA Certs " + pair2);
                                    }
                                    if (pair2 != null && pair2.second != null) {
                                        Log.d("SecurityController", "mHasCACerts updated: " + pair2.second);
                                        arrayMap.put((Integer) pair2.first, (Boolean) pair2.second);
                                        securityControllerImpl22.fireCallbacks();
                                    }
                                    throw th;
                                }
                            } catch (RemoteException | AssertionError | InterruptedException e) {
                                e = e;
                            }
                            try {
                                Pair pair3 = new Pair(Integer.valueOf(i), Boolean.valueOf(!bindAsUser.getService().getUserCaAliases().getList().isEmpty()));
                                try {
                                    bindAsUser.close();
                                    if (z22) {
                                        Log.d("SecurityController", "Refreshing CA Certs " + pair3);
                                    }
                                } catch (RemoteException | AssertionError | InterruptedException e2) {
                                    e = e2;
                                    Log.i("SecurityController", "failed to get CA certs", e);
                                    Pair pair4 = new Pair(Integer.valueOf(i), null);
                                    if (z22) {
                                        Log.d("SecurityController", "Refreshing CA Certs " + pair4);
                                    }
                                    if (pair4.second != null) {
                                        Log.d("SecurityController", "mHasCACerts updated: " + pair4.second);
                                        num = (Integer) pair4.first;
                                        obj = pair4.second;
                                        arrayMap.put(num, (Boolean) obj);
                                        securityControllerImpl22.fireCallbacks();
                                    }
                                    return;
                                }
                                if (pair3.second != null) {
                                    Log.d("SecurityController", "mHasCACerts updated: " + pair3.second);
                                    num = (Integer) pair3.first;
                                    obj = pair3.second;
                                    arrayMap.put(num, (Boolean) obj);
                                    securityControllerImpl22.fireCallbacks();
                                }
                            } catch (Throwable th2) {
                                if (bindAsUser != null) {
                                    try {
                                        bindAsUser.close();
                                    } catch (Throwable th3) {
                                        th2.addSuppressed(th3);
                                    }
                                }
                                throw th2;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            if (z22) {
                            }
                            if (pair2 != null) {
                                Log.d("SecurityController", "mHasCACerts updated: " + pair2.second);
                                arrayMap.put((Integer) pair2.first, (Boolean) pair2.second);
                                securityControllerImpl22.fireCallbacks();
                            }
                            throw th;
                        }
                    }
                });
            }
        };
        this.mBroadcastReceiver = r2;
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mVpnManager = (VpnManager) context.getSystemService(VpnManager.class);
        this.mPackageManager = context.getPackageManager();
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "SecurityControllerImpl", this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.security.action.TRUST_STORE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        broadcastDispatcher.registerReceiverWithHandler(r2, intentFilter, handler, UserHandle.ALL);
        connectivityManager.registerNetworkCallback(REQUEST, (ConnectivityManager.NetworkCallback) r1);
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
                if (!this.mCallbacks.contains(securityControllerCallback)) {
                    if (DEBUG) {
                        Log.d("SecurityController", "addCallback " + securityControllerCallback);
                    }
                    this.mCallbacks.add(securityControllerCallback);
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
    }

    public final void fireCallbacks() {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((SecurityController.SecurityControllerCallback) it.next()).onStateChanged();
            }
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
        boolean z = vpnConfig.legacy;
        Context context = this.mContext;
        if (z) {
            return context.getString(R.string.legacy_vpn_name);
        }
        String str = vpnConfig.user;
        try {
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

    public final int getWorkProfileUserId(int i) {
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    public final String getWorkProfileVpnName() {
        VpnConfig vpnConfig;
        int workProfileUserId = getWorkProfileUserId(this.mVpnUserId);
        if (workProfileUserId == -10000 || (vpnConfig = (VpnConfig) this.mCurrentVpns.get(workProfileUserId)) == null) {
            return null;
        }
        return getNameForVpnConfig(vpnConfig, UserHandle.of(workProfileUserId));
    }

    public final boolean hasCACertInCurrentUser() {
        Boolean bool = (Boolean) this.mHasCACerts.get(Integer.valueOf(this.mCurrentUserId));
        return bool != null && bool.booleanValue();
    }

    public final boolean hasCACertInWorkProfile() {
        Boolean bool;
        int workProfileUserId = getWorkProfileUserId(this.mCurrentUserId);
        return (workProfileUserId == -10000 || (bool = (Boolean) this.mHasCACerts.get(Integer.valueOf(workProfileUserId))) == null || !bool.booleanValue()) ? false : true;
    }

    public final boolean hasWorkProfile() {
        return getWorkProfileUserId(this.mCurrentUserId) != -10000;
    }

    public final boolean isDeviceManaged() {
        return this.mDevicePolicyManager.isDeviceManaged();
    }

    public final boolean isParentalControlsEnabled() {
        return this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(new UserHandle(this.mCurrentUserId)) != null;
    }

    public final boolean isSecureWifiEnabled() {
        String str;
        VpnConfig vpnConfig = (VpnConfig) this.mCurrentVpns.get(this.mVpnUserId);
        return vpnConfig != null && (str = vpnConfig.user) != null && str.equals("com.samsung.android.fast") && this.mContext.getPackageManager().checkSignatures("android", vpnConfig.user) == 0;
    }

    public final boolean isVpnBranded() {
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
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isVpnEnabled() {
        for (int i : this.mUserManager.getProfileIdsWithDisabled(this.mVpnUserId)) {
            if (this.mCurrentVpns.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        SecurityController.SecurityControllerCallback securityControllerCallback = (SecurityController.SecurityControllerCallback) obj;
        synchronized (this.mCallbacks) {
            if (securityControllerCallback == null) {
                return;
            }
            if (DEBUG) {
                Log.d("SecurityController", "removeCallback " + securityControllerCallback);
            }
            this.mCallbacks.remove(securityControllerCallback);
        }
    }
}
