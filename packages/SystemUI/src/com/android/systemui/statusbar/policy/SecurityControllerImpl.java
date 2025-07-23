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
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NetworkProperties {
        public String interfaceName;
        public boolean validated;

        public NetworkProperties(String str, boolean z) {
            this.interfaceName = str;
            this.validated = z;
        }
    }

    /* renamed from: -$$Nest$mupdateState, reason: not valid java name */
    public static void m3090$$Nest$mupdateState(SecurityControllerImpl securityControllerImpl) {
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
                SecurityControllerImpl.m3090$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkProperties networkProperties;
                boolean hasCapability;
                if (SecurityControllerImpl.DEBUG) {
                    Log.d("SecurityController", "onCapabilitiesChanged " + network.getNetId());
                }
                synchronized (SecurityControllerImpl.this.mNetworkProperties) {
                    networkProperties = (NetworkProperties) SecurityControllerImpl.this.mNetworkProperties.get(network.getNetId());
                }
                if (networkProperties == null || networkProperties.validated == (hasCapability = networkCapabilities.hasCapability(16))) {
                    return;
                }
                networkProperties.validated = hasCapability;
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
                SecurityControllerImpl.m3090$$Nest$mupdateState(SecurityControllerImpl.this);
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
                        /* JADX WARN: Removed duplicated region for block: B:39:0x00ab  */
                        /* JADX WARN: Type inference failed for: r1v7, types: [android.util.ArrayMap] */
                        /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Integer, java.lang.Object] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                r9 = this;
                                com.android.systemui.statusbar.policy.SecurityControllerImpl r0 = com.android.systemui.statusbar.policy.SecurityControllerImpl.this
                                int r9 = r2
                                boolean r1 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
                                r0.getClass()
                                java.lang.String r1 = "Refreshing CA Certs "
                                boolean r2 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
                                java.lang.String r3 = "SecurityController"
                                r4 = 0
                                android.content.Context r5 = r0.mContext     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.os.UserHandle r6 = android.os.UserHandle.of(r9)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.security.KeyChain$KeyChainConnection r5 = android.security.KeyChain.bindAsUser(r5, r6)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.security.IKeyChainService r6 = r5.getService()     // Catch: java.lang.Throwable -> L65
                                android.content.pm.StringParceledListSlice r6 = r6.getUserCaAliases()     // Catch: java.lang.Throwable -> L65
                                java.util.List r6 = r6.getList()     // Catch: java.lang.Throwable -> L65
                                boolean r6 = r6.isEmpty()     // Catch: java.lang.Throwable -> L65
                                r6 = r6 ^ 1
                                android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L65
                                java.lang.Integer r8 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L65
                                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch: java.lang.Throwable -> L65
                                r7.<init>(r8, r6)     // Catch: java.lang.Throwable -> L65
                                r5.close()     // Catch: java.lang.Throwable -> L60 java.lang.Throwable -> L63
                                if (r2 == 0) goto L4d
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                r9.<init>(r1)
                                r9.append(r7)
                                java.lang.String r9 = r9.toString()
                                android.util.Log.d(r3, r9)
                            L4d:
                                java.lang.Object r9 = r7.second
                                if (r9 == 0) goto La8
                                android.util.ArrayMap r1 = r0.mHasCACerts
                                java.lang.Object r2 = r7.first
                                java.lang.Integer r2 = (java.lang.Integer) r2
                                java.lang.Boolean r9 = (java.lang.Boolean) r9
                                r1.put(r2, r9)
                                r0.fireCallbacks()
                                return
                            L60:
                                r9 = move-exception
                                r4 = r7
                                goto La9
                            L63:
                                r5 = move-exception
                                goto L77
                            L65:
                                r6 = move-exception
                                if (r5 == 0) goto L76
                                r5.close()     // Catch: java.lang.Throwable -> L6c
                                goto L76
                            L6c:
                                r5 = move-exception
                                r6.addSuppressed(r5)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73
                                goto L76
                            L71:
                                r9 = move-exception
                                goto La9
                            L73:
                                r5 = move-exception
                                r7 = r4
                                goto L77
                            L76:
                                throw r6     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73
                            L77:
                                java.lang.String r6 = "failed to get CA certs"
                                android.util.Log.i(r3, r6, r5)     // Catch: java.lang.Throwable -> L60
                                android.util.Pair r5 = new android.util.Pair     // Catch: java.lang.Throwable -> L60
                                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L60
                                r5.<init>(r9, r4)     // Catch: java.lang.Throwable -> L60
                                if (r2 == 0) goto L96
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                r9.<init>(r1)
                                r9.append(r5)
                                java.lang.String r9 = r9.toString()
                                android.util.Log.d(r3, r9)
                            L96:
                                java.lang.Object r9 = r5.second
                                if (r9 == 0) goto La8
                                android.util.ArrayMap r1 = r0.mHasCACerts
                                java.lang.Object r2 = r5.first
                                java.lang.Integer r2 = (java.lang.Integer) r2
                                java.lang.Boolean r9 = (java.lang.Boolean) r9
                                r1.put(r2, r9)
                                r0.fireCallbacks()
                            La8:
                                return
                            La9:
                                if (r2 == 0) goto Lba
                                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                r2.<init>(r1)
                                r2.append(r4)
                                java.lang.String r1 = r2.toString()
                                android.util.Log.d(r3, r1)
                            Lba:
                                if (r4 == 0) goto Lce
                                java.lang.Object r1 = r4.second
                                if (r1 == 0) goto Lce
                                android.util.ArrayMap r2 = r0.mHasCACerts
                                java.lang.Object r3 = r4.first
                                java.lang.Integer r3 = (java.lang.Integer) r3
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                r2.put(r3, r1)
                                r0.fireCallbacks()
                            Lce:
                                throw r9
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0.run():void");
                        }
                    });
                } else {
                    if (!"android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000) {
                        return;
                    }
                    final SecurityControllerImpl securityControllerImpl2 = SecurityControllerImpl.this;
                    securityControllerImpl2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            /*
                                this = this;
                                com.android.systemui.statusbar.policy.SecurityControllerImpl r0 = com.android.systemui.statusbar.policy.SecurityControllerImpl.this
                                int r9 = r2
                                boolean r1 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
                                r0.getClass()
                                java.lang.String r1 = "Refreshing CA Certs "
                                boolean r2 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
                                java.lang.String r3 = "SecurityController"
                                r4 = 0
                                android.content.Context r5 = r0.mContext     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.os.UserHandle r6 = android.os.UserHandle.of(r9)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.security.KeyChain$KeyChainConnection r5 = android.security.KeyChain.bindAsUser(r5, r6)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73
                                android.security.IKeyChainService r6 = r5.getService()     // Catch: java.lang.Throwable -> L65
                                android.content.pm.StringParceledListSlice r6 = r6.getUserCaAliases()     // Catch: java.lang.Throwable -> L65
                                java.util.List r6 = r6.getList()     // Catch: java.lang.Throwable -> L65
                                boolean r6 = r6.isEmpty()     // Catch: java.lang.Throwable -> L65
                                r6 = r6 ^ 1
                                android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L65
                                java.lang.Integer r8 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L65
                                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch: java.lang.Throwable -> L65
                                r7.<init>(r8, r6)     // Catch: java.lang.Throwable -> L65
                                r5.close()     // Catch: java.lang.Throwable -> L60 java.lang.Throwable -> L63
                                if (r2 == 0) goto L4d
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                r9.<init>(r1)
                                r9.append(r7)
                                java.lang.String r9 = r9.toString()
                                android.util.Log.d(r3, r9)
                            L4d:
                                java.lang.Object r9 = r7.second
                                if (r9 == 0) goto La8
                                android.util.ArrayMap r1 = r0.mHasCACerts
                                java.lang.Object r2 = r7.first
                                java.lang.Integer r2 = (java.lang.Integer) r2
                                java.lang.Boolean r9 = (java.lang.Boolean) r9
                                r1.put(r2, r9)
                                r0.fireCallbacks()
                                return
                            L60:
                                r9 = move-exception
                                r4 = r7
                                goto La9
                            L63:
                                r5 = move-exception
                                goto L77
                            L65:
                                r6 = move-exception
                                if (r5 == 0) goto L76
                                r5.close()     // Catch: java.lang.Throwable -> L6c
                                goto L76
                            L6c:
                                r5 = move-exception
                                r6.addSuppressed(r5)     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73
                                goto L76
                            L71:
                                r9 = move-exception
                                goto La9
                            L73:
                                r5 = move-exception
                                r7 = r4
                                goto L77
                            L76:
                                throw r6     // Catch: java.lang.Throwable -> L71 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73 java.lang.Throwable -> L73
                            L77:
                                java.lang.String r6 = "failed to get CA certs"
                                android.util.Log.i(r3, r6, r5)     // Catch: java.lang.Throwable -> L60
                                android.util.Pair r5 = new android.util.Pair     // Catch: java.lang.Throwable -> L60
                                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L60
                                r5.<init>(r9, r4)     // Catch: java.lang.Throwable -> L60
                                if (r2 == 0) goto L96
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                r9.<init>(r1)
                                r9.append(r5)
                                java.lang.String r9 = r9.toString()
                                android.util.Log.d(r3, r9)
                            L96:
                                java.lang.Object r9 = r5.second
                                if (r9 == 0) goto La8
                                android.util.ArrayMap r1 = r0.mHasCACerts
                                java.lang.Object r2 = r5.first
                                java.lang.Integer r2 = (java.lang.Integer) r2
                                java.lang.Boolean r9 = (java.lang.Boolean) r9
                                r1.put(r2, r9)
                                r0.fireCallbacks()
                            La8:
                                return
                            La9:
                                if (r2 == 0) goto Lba
                                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                r2.<init>(r1)
                                r2.append(r4)
                                java.lang.String r1 = r2.toString()
                                android.util.Log.d(r3, r1)
                            Lba:
                                if (r4 == 0) goto Lce
                                java.lang.Object r1 = r4.second
                                if (r1 == 0) goto Lce
                                android.util.ArrayMap r2 = r0.mHasCACerts
                                java.lang.Object r3 = r4.first
                                java.lang.Integer r3 = (java.lang.Integer) r3
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                r2.put(r3, r1)
                                r0.fireCallbacks()
                            Lce:
                                throw r9
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0.run():void");
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
