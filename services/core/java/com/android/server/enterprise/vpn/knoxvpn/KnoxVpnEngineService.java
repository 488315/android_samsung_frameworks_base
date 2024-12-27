package com.android.server.enterprise.vpn.knoxvpn;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.net.IVpnManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.UidRangeParcel;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.LegacyVpnProfileStore;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.internal.net.VpnProfile;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.net.vpn.EnterpriseResponseData;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.net.vpn.IVpnInfoPolicy;
import com.samsung.android.knox.net.vpn.KnoxVpnContext;
import com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnEngineService extends IKnoxVpnPolicy.Stub implements EnterpriseServiceCallback {
    public static INetd mNetdService;
    public static IOemNetd mOemNetdService;
    public boolean isDeviceBootCompleted;
    public ActivityManagerService mAMS;
    public final Object mCaptiveExemptLock;
    public ChainingStateMachine mChainingStateMachine;
    public ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public KnoxAnalyticsData mData;
    public EnterpriseDeviceManager mEDM;
    public IEnterpriseDeviceManager mEnterpriseDeviceManagerService;
    public KnoxVpnFirewallHelper mFirewallHelper;
    public final KnoxVpnHandler mHandler;
    public final HandlerThread mHandlerThread;
    public final ConcurrentHashMap mIgnoreConnectCheckForChaining;
    public final Injector mInjector;
    public final KnoxVpnApiValidation mKnoxVpnApiValidation;
    public KnoxVpnHelper mKnoxVpnHelper;
    public KnoxVpnPacProcessor mKnoxVpnPacProcessor;
    public KnoxVpnTetherAuthentication mKnoxVpnTetherAuthentication;
    public final AnonymousClass1 mNetworkCallback;
    public INetworkManagementService mNetworkManagementService;
    public NotificationManager mNotificationManager;
    public final ConcurrentHashMap mNotificationMap;
    public final KnoxVpnProcessManager mProcessManager;
    public final List mVpnClientStatus;
    public final HashMap mVpnConnectionList;
    public IVpnInfoPolicy mVpnInfoPolicy;
    public final KnoxVpnStorageProvider mVpnStorageProvider;
    public final ConcurrentHashMap notificationFlagState;
    public VpnReceiver receiver;
    public final VpnProfileConfig vpnConfig;
    public final ConcurrentHashMap vpnInterfaceMap;
    public static final boolean DBG = Debug.semIsProductDev();
    public static volatile boolean mIsCaptiveExempt = false;
    public static final Object NULL_OBJECT = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChainingStateMachine extends Thread {
        public final String profileName;
        public long threadStartTime = -1;
        public final long idleStateSleepTime = 5000;
        public final long connectingStateSleepTime = 5000;

        public ChainingStateMachine(String str) {
            this.profileName = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                this.threadStartTime = SystemClock.elapsedRealtime();
                boolean z = KnoxVpnEngineService.DBG;
                if (z) {
                    Log.d("KnoxVpnEngineService", "ChainingStateMachine: thread start time is " + this.threadStartTime);
                }
                IKnoxVpnService binderInterfaceForProfile = KnoxVpnEngineService.this.getBinderInterfaceForProfile(this.profileName);
                if (binderInterfaceForProfile == null) {
                    if (z) {
                        Log.d("KnoxVpnEngineService", "Error occured while ChainingStateMachine: The error code is 110");
                        return;
                    }
                    return;
                }
                int state = binderInterfaceForProfile.getState(this.profileName);
                if (z) {
                    Log.d("KnoxVpnEngineService", "ChainingStateMachine: currentState of the profile is " + state);
                }
                if (state == -1 || state == 5 || state == 1) {
                    startChainedConnection(this.idleStateSleepTime, this.profileName);
                } else if (state == 2 || state == 3) {
                    startChainedConnection(this.connectingStateSleepTime, this.profileName);
                }
                if (z) {
                    Log.d("KnoxVpnEngineService", "ChainingStateMachine: thread stop time is " + SystemClock.elapsedRealtime());
                }
            } catch (Exception e) {
                if (KnoxVpnEngineService.DBG) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at ChainingStateMachine API "), "KnoxVpnEngineService");
                }
            }
        }

        public final void startChainedConnection(long j, String str) {
            boolean z = KnoxVpnEngineService.DBG;
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("ChainingStateMachine: startChainedConnection is being called for profile ", str, "KnoxVpnEngineService");
            }
            try {
                Thread.sleep(j);
                if (z) {
                    Log.d("KnoxVpnEngineService", "ChainingStateMachine: The thread running time after delay is  " + SystemClock.elapsedRealtime());
                }
                IKnoxVpnService binderInterfaceForProfile = KnoxVpnEngineService.this.getBinderInterfaceForProfile(str);
                if (binderInterfaceForProfile != null) {
                    if (z) {
                        Log.d("KnoxVpnEngineService", "ChainingStateMachine: state of the profile after delay is " + binderInterfaceForProfile.getState(str));
                    }
                    int state = binderInterfaceForProfile.getState(str);
                    if (state == -1 || state == 5 || state == 1) {
                        VpnProfileInfo profileEntry = KnoxVpnEngineService.this.vpnConfig.getProfileEntry(str);
                        if (profileEntry != null) {
                            KnoxVpnEngineService.this.mIgnoreConnectCheckForChaining.put(Integer.valueOf(profileEntry.personaId), Boolean.TRUE);
                            int startVpnProfile = KnoxVpnEngineService.this.startVpnProfile(str);
                            if (z) {
                                Log.d("KnoxVpnEngineService", "ChainingStateMachine: the profile is going to be started after the delay and the result is " + startVpnProfile);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (state == 2 || state == 3) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (z) {
                            Log.d("KnoxVpnEngineService", "ChainingStateMachine: currentTime after the thread has started is  " + elapsedRealtime);
                        }
                        if (elapsedRealtime - this.threadStartTime <= 90000) {
                            if (z) {
                                Log.d("KnoxVpnEngineService", "ChainingStateMachine: the profile is going to be delayed again " + str);
                            }
                            startChainedConnection(this.connectingStateSleepTime, str);
                            return;
                        }
                        if (z) {
                            Log.d("KnoxVpnEngineService", "ChainingStateMachine: time out has happened and going to exit " + str);
                        }
                    }
                }
            } catch (Exception e) {
                if (KnoxVpnEngineService.DBG) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at startChainedConnection API "), "KnoxVpnEngineService");
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KnoxVpnHandler extends Handler {
        public KnoxVpnHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:112:0x01d0 A[Catch: all -> 0x0384, TryCatch #2 {all -> 0x0384, blocks: (B:85:0x0158, B:89:0x0165, B:91:0x0173, B:92:0x0180, B:94:0x0186, B:95:0x0198, B:97:0x019e, B:102:0x01b1, B:109:0x01c3, B:110:0x01c7, B:112:0x01d0, B:114:0x01fa, B:115:0x0207, B:116:0x020d, B:118:0x0213, B:120:0x0200, B:122:0x0229, B:123:0x0233, B:125:0x0239, B:127:0x0255, B:129:0x025e, B:132:0x0268, B:134:0x0277, B:137:0x0281, B:138:0x02a6, B:140:0x02ac, B:141:0x02be, B:143:0x02c4, B:148:0x02d7, B:151:0x02e6, B:153:0x02eb, B:155:0x0318, B:157:0x0322, B:158:0x0328, B:159:0x032f, B:160:0x0335, B:162:0x033b, B:172:0x035e, B:173:0x0362, B:175:0x0368), top: B:84:0x0158 }] */
        /* JADX WARN: Removed duplicated region for block: B:121:0x0229 A[SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r20) {
            /*
                Method dump skipped, instructions count: 1636
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.KnoxVpnHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VpnReceiver extends BroadcastReceiver {
        public VpnReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            String action = intent.getAction();
            Log.d("KnoxVpnEngineService", "Vpn Receiver : " + action);
            if (isInitialStickyBroadcast()) {
                return;
            }
            Bundle bundle = new Bundle();
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
                Uri data = intent.getData();
                schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                boolean z = KnoxVpnEngineService.DBG;
                if (z) {
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m("Vpn Receiver : The extra value is ", "KnoxVpnEngineService", booleanExtra);
                }
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra > -1) {
                    if (z) {
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("Vpn Receiver : Package Added = ", schemeSpecificPart, "KnoxVpnEngineService");
                    }
                    bundle.putInt("uid", intExtra);
                    bundle.putString("package", schemeSpecificPart);
                    bundle.putBoolean("new_install_or_update", booleanExtra);
                    KnoxVpnEngineService.this.sendMessageToHandler$1(2, bundle);
                    return;
                }
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
                Uri data2 = intent.getData();
                schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                boolean booleanExtra2 = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (KnoxVpnEngineService.DBG) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("Vpn Receiver : Package Removed = ", schemeSpecificPart, "KnoxVpnEngineService");
                }
                bundle.putInt("uid", intExtra2);
                bundle.putString("package", schemeSpecificPart);
                bundle.putBoolean("new_install_or_update", booleanExtra2);
                KnoxVpnEngineService.this.sendMessageToHandler$1(3, bundle);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.AIRPLANE_MODE")) {
                Log.i("KnoxVpnEngineService", "Airplane Event received.");
                if (intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
                    KnoxVpnEngineService.this.sendMessageToHandler$1(20, null);
                    return;
                }
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.USER_PRESENT")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(21, null);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(15, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(14, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.USER_STARTED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(9, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.USER_SWITCHED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(13, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo == null) {
                    Log.d("KnoxVpnEngineService", "networkInfo is null");
                    return;
                }
                Log.d("KnoxVpnEngineService", "change in connectivity has occured  for the network type " + networkInfo.getType() + networkInfo.getDetailedState() + networkInfo.getState());
                if (networkInfo.getType() == 1) {
                    Bundle extras = intent.getExtras();
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        extras.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(25, extras);
                    } else {
                        if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
                            return;
                        }
                        extras.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(25, extras);
                    }
                } else if (networkInfo.getType() == 0) {
                    Bundle extras2 = intent.getExtras();
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        extras2.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(26, extras2);
                    } else {
                        if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
                            return;
                        }
                        extras2.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(26, extras2);
                    }
                } else if (networkInfo.getType() == 9) {
                    Bundle extras3 = intent.getExtras();
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        extras3.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(27, extras3);
                    } else {
                        if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
                            return;
                        }
                        extras3.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
                        KnoxVpnEngineService.this.sendMessageToHandler$1(27, extras3);
                    }
                }
                if (networkInfo.getType() == 17 || networkInfo.getType() == 7) {
                    return;
                }
                KnoxVpnEngineService.this.sendMessageToHandler$1(4, intent.getExtras());
                return;
            }
            if (action.equals("android.intent.action.USER_REMOVED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(6, intent.getExtras());
                return;
            }
            if (action.equals("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(1, intent.getExtras());
                return;
            }
            if (action.equals("enterprise.container.admin.changed")) {
                bundle.putInt("android.intent.extra.user_handle", intent.getIntExtra("containerid", -1));
                bundle.putInt("android.intent.extra.UID", intent.getIntExtra("android.intent.extra.UID", -1));
                KnoxVpnEngineService.this.sendMessageToHandler$1(12, bundle);
                return;
            }
            if (action.equals("com.samsung.android.knox.intent.action.VPN_PROXY_BROADCAST_INTERNAL")) {
                bundle.putInt("uid", intent.getIntExtra("com.samsung.android.knox.intent.extra.caller", -1));
                KnoxVpnEngineService.this.sendMessageToHandler$1(18, bundle);
                return;
            }
            if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(22, intent.getExtras());
                return;
            }
            if (action.equals("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE")) {
                KnoxVpnEngineService.this.sendMessageToHandler$1(28, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.net.wifi.STATE_CHANGE")) {
                Bundle extras4 = intent.getExtras();
                NetworkInfo networkInfo2 = (NetworkInfo) extras4.getParcelable("networkInfo");
                if (networkInfo2.getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
                    extras4.putInt("captive", 1);
                    KnoxVpnEngineService.this.sendMessageToHandler$1(29, extras4);
                    return;
                } else {
                    if (networkInfo2.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                        extras4.putInt("captive", 0);
                        KnoxVpnEngineService.this.sendMessageToHandler$1(29, extras4);
                        return;
                    }
                    return;
                }
            }
            if (!action.equals("android.hardware.usb.action.USB_STATE")) {
                if (action.equalsIgnoreCase("android.intent.action.PACKAGE_DATA_CLEARED")) {
                    KnoxVpnEngineService.this.sendMessageToHandler$1(34, intent.getExtras());
                    return;
                }
                return;
            }
            boolean booleanExtra3 = intent.getBooleanExtra("connected", false);
            boolean booleanExtra4 = intent.getBooleanExtra("configured", false);
            boolean booleanExtra5 = intent.getBooleanExtra("rndis", false);
            RCPManagerService$$ExternalSyntheticOutline0.m("KnoxVpnEngineService", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("usb_tethering status: usbConnected ", booleanExtra3, " usbConfigured ", booleanExtra4, " rndisEnabled "), booleanExtra5);
            if (!booleanExtra3) {
                bundle.putInt("bundle_usb_status", 1);
                KnoxVpnEngineService.this.sendMessageToHandler$1(33, bundle);
                return;
            }
            if (booleanExtra3 && booleanExtra4 && booleanExtra5) {
                bundle.putInt("bundle_usb_status", 0);
                KnoxVpnEngineService.this.sendMessageToHandler$1(33, bundle);
            } else if ((booleanExtra3 || booleanExtra4) && !booleanExtra5) {
                bundle.putInt("bundle_usb_status", 1);
                KnoxVpnEngineService.this.sendMessageToHandler$1(33, bundle);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VpnServiceConnection implements ServiceConnection {
        public final int adminUid;
        public final int containerId;

        public VpnServiceConnection(int i, int i2) {
            this.containerId = i;
            this.adminUid = i2;
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onBindingDied(ComponentName componentName) {
            String packageName = componentName.getPackageName();
            KnoxVpnHelper knoxVpnHelper = KnoxVpnEngineService.this.mKnoxVpnHelper;
            int i = this.containerId;
            knoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i, packageName);
            Log.d("KnoxVpnEngineService", "onBindingDied has been called for the vpn client " + personifiedName);
            ((ArrayList) KnoxVpnEngineService.this.mVpnClientStatus).add("onBindingDied callback has been recieved for the vpn client " + personifiedName + " at " + System.currentTimeMillis());
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                KnoxVpnEngineService.this.mInjector.getClass();
                IKnoxVpnService asInterface = IKnoxVpnService.Stub.asInterface(iBinder);
                KnoxVpnEngineService.m559$$Nest$mlogVpnVendor(KnoxVpnEngineService.this, componentName);
                String packageName = componentName.getPackageName();
                KnoxVpnHelper knoxVpnHelper = KnoxVpnEngineService.this.mKnoxVpnHelper;
                int i = this.containerId;
                knoxVpnHelper.getClass();
                String personifiedName = KnoxVpnHelper.getPersonifiedName(i, packageName);
                Log.d("KnoxVpnEngineService", "Vendor VPN service connected: pkgName = " + personifiedName);
                KnoxVpnEngineService.m561$$Nest$msetVpnInterface(KnoxVpnEngineService.this, personifiedName, asInterface);
                ((ArrayList) KnoxVpnEngineService.this.mVpnClientStatus).add("onServiceConnected callback has been recieved for the vpn client " + personifiedName + " at " + System.currentTimeMillis());
                KnoxVpnEngineService.m566$$Nest$mvalidateProfilesForVendor(KnoxVpnEngineService.this, personifiedName, asInterface);
                if (KnoxVpnEngineService.this.isNetworkConnected()) {
                    KnoxVpnEngineService.this.startVpnConnectionForBindedClient(asInterface, personifiedName);
                }
                KnoxVpnEngineService.this.sendBindSuccessIntent(this.adminUid, personifiedName);
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceDisconnected(ComponentName componentName) {
            String packageName = componentName.getPackageName();
            KnoxVpnHelper knoxVpnHelper = KnoxVpnEngineService.this.mKnoxVpnHelper;
            int i = this.containerId;
            knoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i, packageName);
            Log.d("KnoxVpnEngineService", "Vendor VPN service disconnected : vendorName = " + personifiedName);
            KnoxVpnEngineService.m564$$Nest$mstopStrongwanProxyConnection(KnoxVpnEngineService.this, packageName, personifiedName, this.containerId);
            KnoxVpnEngineService.m561$$Nest$msetVpnInterface(KnoxVpnEngineService.this, personifiedName, null);
            ((ArrayList) KnoxVpnEngineService.this.mVpnClientStatus).add("onServiceDisconnected callback has been recieved for the vpn client " + personifiedName + " at " + System.currentTimeMillis());
            StringBuilder sb = new StringBuilder("Trying to bind again.. Vendor: ");
            sb.append(personifiedName);
            Log.d("KnoxVpnEngineService", sb.toString());
            KnoxVpnEngineService.this.bindKnoxVpnInterface(this.adminUid, personifiedName);
        }
    }

    /* renamed from: -$$Nest$mhandleActionAdminChanged, reason: not valid java name */
    public static void m548$$Nest$mhandleActionAdminChanged(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        knoxVpnEngineService.getClass();
        int i = bundle.getInt("android.intent.extra.user_handle", -1);
        int i2 = bundle.getInt("android.intent.extra.UID", -1);
        if (i == -1 || i2 == -1) {
            return;
        }
        knoxVpnEngineService.stopVpnConnectionAfterAdminRemoval(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0068 A[Catch: all -> 0x004b, TryCatch #0 {all -> 0x004b, blocks: (B:4:0x0004, B:6:0x0017, B:8:0x001b, B:10:0x0027, B:11:0x0033, B:13:0x003a, B:15:0x0044, B:18:0x0064, B:20:0x0068, B:22:0x0072, B:24:0x004e, B:26:0x008f, B:27:0x009b, B:29:0x00a1, B:32:0x00ab, B:33:0x00b0, B:34:0x00bd, B:36:0x00c3, B:37:0x00d5, B:39:0x00db, B:44:0x00ee), top: B:3:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0072 A[SYNTHETIC] */
    /* renamed from: -$$Nest$mhandleActionLockBootCompleted, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m549$$Nest$mhandleActionLockBootCompleted(com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService r6, int r7) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.m549$$Nest$mhandleActionLockBootCompleted(com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService, int):void");
    }

    /* renamed from: -$$Nest$mhandleActionMobileChanged, reason: not valid java name */
    public static void m550$$Nest$mhandleActionMobileChanged(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        Collection<VpnProfileInfo> values;
        synchronized (knoxVpnEngineService) {
            try {
                values = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values();
            } catch (Throwable unused) {
                Log.e("KnoxVpnEngineService", "Exception occured while trying to apply iptable rule for user 0 during connectivity change of mobile network");
            }
            if (values == null) {
                return;
            }
            if (knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values().size() > 0) {
                boolean z = false;
                for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                    int i = vpnProfileInfo.activateState;
                    Iterator it = vpnProfileInfo.mPackageMap.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
                        knoxVpnEngineService.mKnoxVpnHelper.getClass();
                        if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo) && i == 1) {
                            KnoxVpnHelper knoxVpnHelper = knoxVpnEngineService.mKnoxVpnHelper;
                            String packageName = vpnPackageInfo.getPackageName();
                            knoxVpnHelper.getClass();
                            if (KnoxVpnHelper.getContainerIdFromPackageName(packageName) == 0) {
                                z = true;
                                break;
                            }
                        }
                    }
                    if (z) {
                        break;
                    }
                }
                for (VpnProfileInfo vpnProfileInfo2 : values) {
                    knoxVpnEngineService.mFirewallHelper.removeInputFilterDropRulesForInterface(1, knoxVpnEngineService.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo2.mProfileName));
                    knoxVpnEngineService.mFirewallHelper.removeInputFilterDropRulesForInterface(0, knoxVpnEngineService.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo2.mProfileName));
                    if (knoxVpnEngineService.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo2.mProfileName)) {
                        knoxVpnEngineService.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
                    } else {
                        knoxVpnEngineService.mFirewallHelper.removeIpRulesForExemptedUid(vpnProfileInfo2.mVendorUid, 3);
                    }
                    Iterator it2 = vpnProfileInfo2.mExemptPackageList.iterator();
                    while (it2.hasNext()) {
                        knoxVpnEngineService.updateRulesToExemptUid(0, vpnProfileInfo2.mProfileName, null, null, 2, ((Integer) it2.next()).intValue(), 3);
                        vpnProfileInfo2 = vpnProfileInfo2;
                    }
                }
                HashSet hashSet = knoxVpnEngineService.mKnoxVpnHelper.getuserIdForExemptedUids();
                Iterator it3 = hashSet.iterator();
                while (it3.hasNext()) {
                    int intValue = ((Integer) it3.next()).intValue();
                    knoxVpnEngineService.mKnoxVpnHelper.getClass();
                    knoxVpnEngineService.mFirewallHelper.removeExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(intValue, "com.android.providers.downloads"));
                }
                if (bundle.getBoolean("noConnectivity")) {
                    Log.d("KnoxVpnEngineService", "Default Mobile Network lost connectivity");
                    return;
                }
                if (bundle.getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN).equalsIgnoreCase("DISCONNECTED")) {
                    Log.d("KnoxVpnEngineService", "Default Mobile Network is disconnected");
                    return;
                }
                knoxVpnEngineService.getKnoxVpnHelperInstance().getClass();
                String activeNetworkInterface = KnoxVpnHelper.getActiveNetworkInterface();
                Log.d("KnoxVpnEngineService", "Default Mobile Network interface Name is " + activeNetworkInterface);
                for (VpnProfileInfo vpnProfileInfo3 : values) {
                    int i2 = vpnProfileInfo3.activateState;
                    Iterator it4 = vpnProfileInfo3.mPackageMap.values().iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            break;
                        }
                        VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it4.next();
                        knoxVpnEngineService.mKnoxVpnHelper.getClass();
                        if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo2) && i2 == 1) {
                            KnoxVpnHelper knoxVpnHelper2 = knoxVpnEngineService.mKnoxVpnHelper;
                            String packageName2 = vpnPackageInfo2.getPackageName();
                            knoxVpnHelper2.getClass();
                            if (KnoxVpnHelper.getContainerIdFromPackageName(packageName2) == 0) {
                                knoxVpnEngineService.blockIncomingICMPPackets(true);
                                break;
                            }
                        }
                    }
                    if (i2 == 1) {
                        KnoxVpnHelper knoxVpnHelper3 = knoxVpnEngineService.mKnoxVpnHelper;
                        String str = vpnProfileInfo3.mVendorPkgName;
                        knoxVpnHelper3.getClass();
                        String regularPackageName = KnoxVpnHelper.getRegularPackageName(str);
                        knoxVpnEngineService.mFirewallHelper.addInputFilterDropRulesForInterface(regularPackageName, activeNetworkInterface, 1, knoxVpnEngineService.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo3.mProfileName));
                        knoxVpnEngineService.mFirewallHelper.addInputFilterDropRulesForInterface(regularPackageName, activeNetworkInterface, 0, knoxVpnEngineService.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo3.mProfileName));
                        if (knoxVpnEngineService.getChainingEnabledForProfile(vpnProfileInfo3.mVendorUid) != 1) {
                            if (knoxVpnEngineService.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo3.mProfileName)) {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(1016, 3, activeNetworkInterface);
                            } else {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(vpnProfileInfo3.mVendorUid, 3, activeNetworkInterface);
                            }
                        }
                        Iterator it5 = vpnProfileInfo3.mExemptPackageList.iterator();
                        while (it5.hasNext()) {
                            knoxVpnEngineService.updateRulesToExemptUid(1, vpnProfileInfo3.mProfileName, null, activeNetworkInterface, 2, ((Integer) it5.next()).intValue(), 3);
                            vpnProfileInfo3 = vpnProfileInfo3;
                        }
                    }
                }
                Iterator it6 = hashSet.iterator();
                while (it6.hasNext()) {
                    int intValue2 = ((Integer) it6.next()).intValue();
                    knoxVpnEngineService.mKnoxVpnHelper.getClass();
                    knoxVpnEngineService.mFirewallHelper.addExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(intValue2, "com.android.providers.downloads"), activeNetworkInterface);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionUserRemoved, reason: not valid java name */
    public static void m551$$Nest$mhandleActionUserRemoved(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        synchronized (knoxVpnEngineService) {
            int i = bundle.getInt("android.intent.extra.user_handle", -10000);
            Log.d("KnoxVpnEngineService", "handleActionUserRemoved : Got USERREMOVED intent : " + i);
            try {
                knoxVpnEngineService.mKnoxVpnHelper.getClass();
                knoxVpnEngineService.mFirewallHelper.removeExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(i, "com.android.providers.downloads"));
            } catch (Exception unused) {
            }
            knoxVpnEngineService.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(UserHandle.getUid(i, 1002), null, false);
            ConcurrentHashMap concurrentHashMap = knoxVpnEngineService.mIgnoreConnectCheckForChaining;
            if (concurrentHashMap != null && concurrentHashMap.containsKey(Integer.valueOf(i))) {
                knoxVpnEngineService.mIgnoreConnectCheckForChaining.remove(Integer.valueOf(i));
            }
            try {
                KnoxVpnStorageProvider knoxVpnStorageProvider = knoxVpnEngineService.mVpnStorageProvider;
                String[] strArr = {Integer.toString(i)};
                knoxVpnStorageProvider.getClass();
                ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"personaId"}, strArr, null);
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "handleActionUserRemoved : #1 cvList.size() : " + dataByFields.size());
                }
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    contentValues.getAsInteger("adminUid").getClass();
                    String asString = contentValues.getAsString("profileName");
                    if (DBG) {
                        Log.d("KnoxVpnEngineService", "handleActionUserRemoved : profileName = " + asString);
                    }
                    VpnProfileInfo profileEntry = knoxVpnEngineService.vpnConfig.getProfileEntry(asString);
                    if (profileEntry != null && UserHandle.getUserId(profileEntry.mVendorUid) == i) {
                        knoxVpnEngineService.removeProfileFromKeyStore(profileEntry.mVendorUid, asString, profileEntry.mVendorPkgName);
                        knoxVpnEngineService.removeProfileFromHashMapAndDB(asString);
                    }
                }
            } catch (Exception e) {
                Log.d("KnoxVpnEngineService", "handleActionUserRemoved : Exception : " + Log.getStackTraceString(e));
            }
            try {
                KnoxVpnStorageProvider knoxVpnStorageProvider2 = knoxVpnEngineService.mVpnStorageProvider;
                String[] strArr2 = {Integer.toString(i)};
                knoxVpnStorageProvider2.getClass();
                ArrayList dataByFields2 = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageCid"}, strArr2, null);
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "handleActionUserRemoved : #3 cvList.size() : " + dataByFields2.size());
                }
                Iterator it2 = dataByFields2.iterator();
                while (it2.hasNext()) {
                    ContentValues contentValues2 = (ContentValues) it2.next();
                    String asString2 = contentValues2.getAsString("profileName");
                    String asString3 = contentValues2.getAsString("packageName");
                    VpnProfileInfo profileEntry2 = knoxVpnEngineService.vpnConfig.getProfileEntry(asString2);
                    if (profileEntry2 != null) {
                        int i2 = profileEntry2.admin_id;
                        boolean z = DBG;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "handleActionUserRemoved : profileName = " + asString2 + " adminId = " + i2 + " transformedPackageName = " + asString3);
                        }
                        if (z) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("handleActionUserRemoved : package = ");
                            KnoxVpnHelper knoxVpnHelper = knoxVpnEngineService.mKnoxVpnHelper;
                            String str = profileEntry2.mVendorPkgName;
                            knoxVpnHelper.getClass();
                            sb.append(KnoxVpnHelper.getRegularPackageName(str));
                            Log.d("KnoxVpnEngineService", sb.toString());
                        }
                        KnoxVpnHelper knoxVpnHelper2 = knoxVpnEngineService.mKnoxVpnHelper;
                        String str2 = profileEntry2.mVendorPkgName;
                        knoxVpnHelper2.getClass();
                        KnoxVpnContext knoxVpnContext = new KnoxVpnContext(i2, i, KnoxVpnHelper.getRegularPackageName(str2));
                        knoxVpnEngineService.getKnoxVpnHelperInstance().getClass();
                        if (asString3.equalsIgnoreCase(KnoxVpnHelper.getPersonifiedName(KnoxVpnHelper.getContainerIdFromPackageName(asString3), "ADD_ALL_PACKAGES"))) {
                            knoxVpnEngineService.removeAllPackagesFromVpn(knoxVpnContext, asString2);
                        } else {
                            knoxVpnEngineService.mKnoxVpnHelper.getClass();
                            knoxVpnEngineService.removePackagesFromVpn(knoxVpnContext, new String[]{KnoxVpnHelper.getRegularPackageName(asString3)}, asString2);
                        }
                    }
                }
            } catch (Exception e2) {
                Log.d("KnoxVpnEngineService", "handleActionUserRemoved : Exception : " + Log.getStackTraceString(e2));
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionWifiChanged, reason: not valid java name */
    public static void m552$$Nest$mhandleActionWifiChanged(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        Collection<VpnProfileInfo> values;
        synchronized (knoxVpnEngineService) {
            try {
                values = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values();
            } catch (Throwable unused) {
                Log.e("KnoxVpnEngineService", "Exception occured while trying to apply iptable rule for user 0 during connectivity change of wifi network");
            }
            if (values == null) {
                return;
            }
            if (values.size() > 0) {
                boolean z = false;
                for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                    int i = vpnProfileInfo.activateState;
                    Iterator it = vpnProfileInfo.mPackageMap.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
                        knoxVpnEngineService.mKnoxVpnHelper.getClass();
                        if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo) && i == 1) {
                            KnoxVpnHelper knoxVpnHelper = knoxVpnEngineService.mKnoxVpnHelper;
                            String packageName = vpnPackageInfo.getPackageName();
                            knoxVpnHelper.getClass();
                            if (KnoxVpnHelper.getContainerIdFromPackageName(packageName) == 0) {
                                z = true;
                                break;
                            }
                        }
                    }
                    if (z) {
                        break;
                    }
                }
                for (VpnProfileInfo vpnProfileInfo2 : values) {
                    knoxVpnEngineService.mFirewallHelper.removeInputFilterDropRulesForInterface(1, knoxVpnEngineService.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo2.mProfileName));
                    knoxVpnEngineService.mFirewallHelper.removeInputFilterDropRulesForInterface(0, knoxVpnEngineService.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo2.mProfileName));
                    if (knoxVpnEngineService.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo2.mProfileName)) {
                        knoxVpnEngineService.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
                    } else {
                        knoxVpnEngineService.mFirewallHelper.removeIpRulesForExemptedUid(vpnProfileInfo2.mVendorUid, 3);
                    }
                    Iterator it2 = vpnProfileInfo2.mExemptPackageList.iterator();
                    while (it2.hasNext()) {
                        knoxVpnEngineService.updateRulesToExemptUid(0, vpnProfileInfo2.mProfileName, null, null, 2, ((Integer) it2.next()).intValue(), 3);
                        vpnProfileInfo2 = vpnProfileInfo2;
                    }
                }
                HashSet hashSet = knoxVpnEngineService.mKnoxVpnHelper.getuserIdForExemptedUids();
                Iterator it3 = hashSet.iterator();
                while (it3.hasNext()) {
                    int intValue = ((Integer) it3.next()).intValue();
                    knoxVpnEngineService.mKnoxVpnHelper.getClass();
                    knoxVpnEngineService.mFirewallHelper.removeExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(intValue, "com.android.providers.downloads"));
                }
                if (bundle.getBoolean("noConnectivity")) {
                    Log.d("KnoxVpnEngineService", "Default Wifi Network lost connectivity");
                    return;
                }
                if (bundle.getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN).equalsIgnoreCase("DISCONNECTED")) {
                    Log.d("KnoxVpnEngineService", "Default Wifi Network is disconnected");
                    return;
                }
                knoxVpnEngineService.getKnoxVpnHelperInstance().getClass();
                String activeNetworkInterface = KnoxVpnHelper.getActiveNetworkInterface();
                Log.d("KnoxVpnEngineService", "Default Wifi Network interface Name is " + activeNetworkInterface);
                for (VpnProfileInfo vpnProfileInfo3 : values) {
                    int i2 = vpnProfileInfo3.activateState;
                    Iterator it4 = vpnProfileInfo3.mPackageMap.values().iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            break;
                        }
                        VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it4.next();
                        knoxVpnEngineService.mKnoxVpnHelper.getClass();
                        if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo2) && i2 == 1) {
                            KnoxVpnHelper knoxVpnHelper2 = knoxVpnEngineService.mKnoxVpnHelper;
                            String packageName2 = vpnPackageInfo2.getPackageName();
                            knoxVpnHelper2.getClass();
                            if (KnoxVpnHelper.getContainerIdFromPackageName(packageName2) == 0) {
                                knoxVpnEngineService.blockIncomingICMPPackets(true);
                                break;
                            }
                        }
                    }
                    if (i2 == 1) {
                        KnoxVpnHelper knoxVpnHelper3 = knoxVpnEngineService.mKnoxVpnHelper;
                        String str = vpnProfileInfo3.mVendorPkgName;
                        knoxVpnHelper3.getClass();
                        String regularPackageName = KnoxVpnHelper.getRegularPackageName(str);
                        knoxVpnEngineService.mFirewallHelper.addInputFilterDropRulesForInterface(regularPackageName, activeNetworkInterface, 1, knoxVpnEngineService.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo3.mProfileName));
                        knoxVpnEngineService.mFirewallHelper.addInputFilterDropRulesForInterface(regularPackageName, activeNetworkInterface, 0, knoxVpnEngineService.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo3.mProfileName));
                        if (knoxVpnEngineService.getChainingEnabledForProfile(vpnProfileInfo3.mVendorUid) != 1) {
                            if (knoxVpnEngineService.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo3.mProfileName)) {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(1016, 3, activeNetworkInterface);
                            } else {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(vpnProfileInfo3.mVendorUid, 3, activeNetworkInterface);
                            }
                        }
                        Iterator it5 = vpnProfileInfo3.mExemptPackageList.iterator();
                        while (it5.hasNext()) {
                            knoxVpnEngineService.updateRulesToExemptUid(1, vpnProfileInfo3.mProfileName, null, activeNetworkInterface, 2, ((Integer) it5.next()).intValue(), 3);
                            vpnProfileInfo3 = vpnProfileInfo3;
                        }
                    }
                }
                Iterator it6 = hashSet.iterator();
                while (it6.hasNext()) {
                    int intValue2 = ((Integer) it6.next()).intValue();
                    knoxVpnEngineService.mKnoxVpnHelper.getClass();
                    knoxVpnEngineService.mFirewallHelper.addExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(intValue2, "com.android.providers.downloads"), activeNetworkInterface);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleCaptivePortal, reason: not valid java name */
    public static void m553$$Nest$mhandleCaptivePortal(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        Collection values;
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    values = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values();
                } catch (Exception unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while handling captive portal firewall rules");
                }
                if (values == null) {
                    return;
                }
                if (values.size() > 0) {
                    knoxVpnEngineService.mKnoxVpnHelper.getClass();
                    int uIDForPackage = KnoxVpnHelper.getUIDForPackage(0, "com.google.android.captiveportallogin");
                    if (uIDForPackage != -1 ? knoxVpnEngineService.mKnoxVpnHelper.checKIfUidIsExempted(uIDForPackage) : false) {
                        if (mIsCaptiveExempt && bundle.getInt("captive") == 0) {
                            Log.i("KnoxVpnEngineService", "Removing iptables captive rules");
                            knoxVpnEngineService.mFirewallHelper.removeRulesToDropIpv6SystemQueries(uIDForPackage);
                            knoxVpnEngineService.mFirewallHelper.removeRulesToDropIpv6SystemQueries(1000);
                            knoxVpnEngineService.mFirewallHelper.removeRulesToDropIpv6SystemQueries(1073);
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1000, 1000, 4);
                            knoxVpnFirewallHelper.deleteRulesToExemptCaptivePortalQueries(4, 1000);
                            knoxVpnFirewallHelper.deleteRulesToExemptCaptivePortalQueries(6, 1000);
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper2 = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper2.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(uIDForPackage, uIDForPackage, 4);
                            knoxVpnFirewallHelper2.deleteRulesToExemptCaptivePortalQueries(4, uIDForPackage);
                            knoxVpnFirewallHelper2.deleteRulesToExemptCaptivePortalQueries(6, uIDForPackage);
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper3 = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper3.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1073, 1073, 4);
                            knoxVpnFirewallHelper3.deleteRulesToExemptCaptivePortalQueries(4, 1073);
                            knoxVpnFirewallHelper3.deleteRulesToExemptCaptivePortalQueries(6, 1073);
                            knoxVpnEngineService.mKnoxVpnHelper.getClass();
                            String activeNetworkInterface = KnoxVpnHelper.getActiveNetworkInterface();
                            Integer[] numArr = KnoxVpnConstants.AID_EXEMPT_LIST;
                            for (int i = 0; i < 3; i++) {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(numArr[i].intValue(), 3, activeNetworkInterface);
                            }
                            synchronized (knoxVpnEngineService.mCaptiveExemptLock) {
                                mIsCaptiveExempt = false;
                            }
                        } else if (!mIsCaptiveExempt && bundle.getInt("captive") == 1) {
                            Log.i("KnoxVpnEngineService", "Adding iptables captive rules");
                            knoxVpnEngineService.mFirewallHelper.insertRulesToDropIpv6SystemQueries(uIDForPackage);
                            knoxVpnEngineService.mFirewallHelper.insertRulesToDropIpv6SystemQueries(1000);
                            knoxVpnEngineService.mFirewallHelper.insertRulesToDropIpv6SystemQueries(1073);
                            Integer[] numArr2 = KnoxVpnConstants.AID_EXEMPT_LIST;
                            for (int i2 = 0; i2 < 3; i2++) {
                                knoxVpnEngineService.mFirewallHelper.addIpRulesForExemptedUid(numArr2[i2].intValue(), 3, "wlan0");
                            }
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper4 = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper4.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1000, 1000, 3);
                            knoxVpnFirewallHelper4.insertRulesToExemptCaptivePortalQueries(4, 1000);
                            knoxVpnFirewallHelper4.insertRulesToExemptCaptivePortalQueries(6, 1000);
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper5 = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper5.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(uIDForPackage, uIDForPackage, 3);
                            knoxVpnFirewallHelper5.insertRulesToExemptCaptivePortalQueries(4, uIDForPackage);
                            knoxVpnFirewallHelper5.insertRulesToExemptCaptivePortalQueries(6, uIDForPackage);
                            KnoxVpnFirewallHelper knoxVpnFirewallHelper6 = knoxVpnEngineService.mFirewallHelper;
                            knoxVpnFirewallHelper6.getClass();
                            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1073, 1073, 3);
                            knoxVpnFirewallHelper6.insertRulesToExemptCaptivePortalQueries(4, 1073);
                            knoxVpnFirewallHelper6.insertRulesToExemptCaptivePortalQueries(6, 1073);
                            synchronized (knoxVpnEngineService.mCaptiveExemptLock) {
                                mIsCaptiveExempt = true;
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleConnectivityAction, reason: not valid java name */
    public static void m554$$Nest$mhandleConnectivityAction(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        synchronized (knoxVpnEngineService) {
            boolean z = bundle.getBoolean("noConnectivity");
            Log.d("KnoxVpnEngineService", "vpn handle : connectivity action : Handle MSG CONNECTIVITY_ACTION. networkDown = " + z);
            if (!z) {
                knoxVpnEngineService.runAllVpnService();
            }
        }
    }

    /* renamed from: -$$Nest$mhandleVpnInterfaceState, reason: not valid java name */
    public static void m555$$Nest$mhandleVpnInterfaceState(KnoxVpnEngineService knoxVpnEngineService, Bundle bundle) {
        Collection values;
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    String string = bundle.getString("com.samsung.android.knox.intent.extra.ACTION_INTERNAL");
                    if (string != null && string.equals("pac_info")) {
                        String string2 = bundle.getString("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL");
                        VpnProfileInfo profileEntry = knoxVpnEngineService.vpnConfig.getProfileEntry(string2);
                        if (profileEntry == null) {
                            return;
                        }
                        if (profileEntry.routeType == 0) {
                            return;
                        }
                        int i = bundle.getInt("com.samsung.android.knox.intent.extra.STATE_INTERNAL");
                        if (i == 0) {
                            Log.e("KnoxVpnEngineService", "The PAC Service has been disconnected for unknown reason,removing rules");
                            knoxVpnEngineService.updateProxyRules(0, string2, knoxVpnEngineService.getKnoxVpnHelperInstance().getListOfUid(string2));
                        } else if (i == 1) {
                            Log.e("KnoxVpnEngineService", "The PAC Service has been connected successfully,adding rules");
                            knoxVpnEngineService.updateProxyRules(1, string2, knoxVpnEngineService.getKnoxVpnHelperInstance().getListOfUid(string2));
                        }
                    } else if (string != null && string.equals("VpnUtils_info")) {
                        Iterator it = ((ArrayList) knoxVpnEngineService.mKnoxVpnHelper.profileListForClient()).iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            knoxVpnEngineService.removeProfileFromHashMapAndDB(str);
                            IKnoxVpnService binderInterfaceForProfile = knoxVpnEngineService.getBinderInterfaceForProfile(str);
                            if (binderInterfaceForProfile != null) {
                                binderInterfaceForProfile.removeConnection(str);
                            }
                        }
                    } else if (string != null && string.equals("tethering_info") && (values = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) != null && values.size() > 0) {
                        Iterator it2 = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it2.next();
                            if (vpnProfileInfo.mUsbTethering == 1) {
                                int i2 = vpnProfileInfo.activateState;
                                String str2 = vpnProfileInfo.mInterfaceName;
                                String interfaceNameForUsbtethering = knoxVpnEngineService.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
                                int i3 = vpnProfileInfo.isUsbTetheringAuthEnabled;
                                if (interfaceNameForUsbtethering != null) {
                                    if (i2 != 1 || str2 == null || str2.isEmpty()) {
                                        if (i2 == 1) {
                                            Log.d("KnoxVpnEngineService", "Applying rules to drop tether packets since vpn is not connected, but still in activated state when the usb is plugged");
                                            knoxVpnEngineService.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                                        }
                                    } else if (i3 == 1) {
                                        Log.d("KnoxVpnTetherAuthentication", "usb tether auth process is started after usb is plugged");
                                        knoxVpnEngineService.mKnoxVpnTetherAuthentication.startTetherAuthProcess(vpnProfileInfo.personaId, interfaceNameForUsbtethering, knoxVpnEngineService.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
                                    } else {
                                        String[] dnsServerListForInterface = knoxVpnEngineService.getVpnManagerService().getDnsServerListForInterface(str2);
                                        knoxVpnEngineService.mKnoxVpnHelper.getClass();
                                        knoxVpnEngineService.mFirewallHelper.addRulesForUsbTethering(vpnProfileInfo.mInterface_type, str2, interfaceNameForUsbtethering, KnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering), dnsServerListForInterface);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("KnoxVpnEngineService", "handleVpnInterfaceState : Exception : " + Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$minitializeVpnVendorForUserAfterBootComplete, reason: not valid java name */
    public static void m556$$Nest$minitializeVpnVendorForUserAfterBootComplete(KnoxVpnEngineService knoxVpnEngineService, int i) {
        int i2;
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete " + i);
                    Iterator it = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
                        if (vpnProfileInfo.isUsbTetheringAuthEnabled == 1 && (i2 = vpnProfileInfo.personaId) == i) {
                            String str = vpnProfileInfo.mProfileName;
                            knoxVpnEngineService.mKnoxVpnTetherAuthentication.bindTetherAuthService(i2, str, knoxVpnEngineService.mKnoxVpnHelper.getTetherAuthDetailsFromDatabase(str));
                            break;
                        }
                    }
                    ArrayList arrayList = new ArrayList();
                    for (VpnProfileInfo vpnProfileInfo2 : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                        String str2 = vpnProfileInfo2.mProfileName;
                        int i3 = vpnProfileInfo2.personaId;
                        boolean z = DBG;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete : ProfileName: " + str2 + " , cid = " + i3);
                        }
                        String str3 = vpnProfileInfo2.mVendorPkgName;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete : Checking vendor : " + str3);
                        }
                        if (i3 == i && str3 != null && !arrayList.contains(str3)) {
                            if (z) {
                                Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete : Adding vendor : " + str3);
                            }
                            arrayList.add(str3);
                        }
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        String str4 = (String) it2.next();
                        if (knoxVpnEngineService.mVpnConnectionList.containsKey(str4)) {
                            knoxVpnEngineService.startVpnConnectionForBindedClient(knoxVpnEngineService.getVpnInterface(str4), str4);
                        } else {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete : Binding to vendor : " + str4);
                            knoxVpnEngineService.bindKnoxVpnInterface(knoxVpnEngineService.mKnoxVpnHelper.getAdminIdFromPackageName(str4), str4);
                        }
                    }
                } catch (Exception e) {
                    Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete : Exception: " + Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$minitializeVpnVendorForUserAfterLockBootComplete, reason: not valid java name */
    public static void m557$$Nest$minitializeVpnVendorForUserAfterLockBootComplete(KnoxVpnEngineService knoxVpnEngineService, int i) {
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete " + i);
                    ArrayList arrayList = new ArrayList();
                    for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                        String str = vpnProfileInfo.mProfileName;
                        int i2 = vpnProfileInfo.personaId;
                        boolean z = DBG;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete : ProfileName: " + str + " , cid = " + i2);
                        }
                        String str2 = vpnProfileInfo.mVendorPkgName;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete : Checking vendor : " + str2);
                        }
                        if (i2 == i && str2 != null && !arrayList.contains(str2)) {
                            if (z) {
                                Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete : Adding vendor : " + str2);
                            }
                            arrayList.add(str2);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        String str3 = (String) it.next();
                        if (knoxVpnEngineService.mVpnConnectionList.containsKey(str3)) {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete: skip binding for " + str3);
                        } else {
                            Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete : Binding to vendor : " + str3);
                            knoxVpnEngineService.bindKnoxVpnInterface(knoxVpnEngineService.mKnoxVpnHelper.getAdminIdFromPackageName(str3), str3);
                        }
                    }
                } catch (Exception e) {
                    Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete : Exception: " + Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mkillRunningProcessToApplyProxy, reason: not valid java name */
    public static void m558$$Nest$mkillRunningProcessToApplyProxy(KnoxVpnEngineService knoxVpnEngineService, String str, HashMap hashMap, HashSet hashSet) {
        int i;
        HashMap hashMap2 = hashMap;
        knoxVpnEngineService.getClass();
        if (hashMap2 == null) {
            if (DBG) {
                Log.d("KnoxVpnEngineService", "Proxy config has been applied : null");
                return;
            }
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int i2 = 0;
                boolean z = false;
                for (Integer num : hashMap.keySet()) {
                    boolean booleanValue = ((Boolean) hashMap2.get(num)).booleanValue();
                    String[] strArr = KnoxVpnConstants.APPS_TO_RESTART_PROXY;
                    if (booleanValue) {
                        int i3 = i2;
                        while (i3 < 2) {
                            String str2 = strArr[i3];
                            KnoxVpnHelper knoxVpnHelperInstance = knoxVpnEngineService.getKnoxVpnHelperInstance();
                            int intValue = num.intValue();
                            knoxVpnHelperInstance.getClass();
                            int uIDForPackage = KnoxVpnHelper.getUIDForPackage(intValue, str2);
                            if (hashSet.contains(Integer.valueOf(uIDForPackage))) {
                                Log.d("KnoxVpnEngineService", "The following app uid " + uIDForPackage + "is not going to be restarted since it is added to exempt list");
                            } else {
                                ApplicationInfo applicationInfo = knoxVpnEngineService.mContext.getPackageManager().getApplicationInfo(str2, i2);
                                if (knoxVpnEngineService.getAMSInstance().checkIfProcessIsRunning(uIDForPackage, applicationInfo.processName)) {
                                    Log.d("KnoxVpnEngineService", "Proxy config has been applied for the entire user, going to restart the app " + str2 + "whose uid is " + uIDForPackage);
                                    knoxVpnEngineService.getAMSInstance().killApplicationProcess(applicationInfo.processName, uIDForPackage);
                                    z = true;
                                }
                                knoxVpnEngineService.getAMSInstance().killBackgroundProcesses(str2, num.intValue());
                            }
                            i3++;
                            i2 = 0;
                        }
                        i = i2;
                    } else {
                        KnoxVpnHelper knoxVpnHelperInstance2 = knoxVpnEngineService.getKnoxVpnHelperInstance();
                        int intValue2 = num.intValue();
                        knoxVpnHelperInstance2.getClass();
                        String packageNameForUid = KnoxVpnHelper.getPackageNameForUid(intValue2);
                        if (Arrays.asList(strArr).contains(packageNameForUid)) {
                            i = 0;
                            ApplicationInfo applicationInfo2 = knoxVpnEngineService.mContext.getPackageManager().getApplicationInfo(packageNameForUid, 0);
                            if (knoxVpnEngineService.getAMSInstance().checkIfProcessIsRunning(num.intValue(), applicationInfo2.processName)) {
                                Log.d("KnoxVpnEngineService", "Proxy config has been applied, going to restart the app " + packageNameForUid + "whose uid is " + num);
                                knoxVpnEngineService.getAMSInstance().killApplicationProcess(applicationInfo2.processName, num.intValue());
                                z = true;
                            }
                            knoxVpnEngineService.getAMSInstance().killBackgroundProcesses(packageNameForUid, UserHandle.getUserId(num.intValue()));
                        } else {
                            i = 0;
                        }
                    }
                    hashMap2 = hashMap;
                    i2 = i;
                }
                if (z) {
                    knoxVpnEngineService.createProcessKillNotification(str);
                }
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "Exception occured while trying to kill application process to enable proxy " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mlogVpnVendor, reason: not valid java name */
    public static void m559$$Nest$mlogVpnVendor(KnoxVpnEngineService knoxVpnEngineService, ComponentName componentName) {
        knoxVpnEngineService.getClass();
        if (componentName == null) {
            return;
        }
        try {
            String packageName = componentName.getPackageName();
            String className = componentName.getClassName();
            if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "VPN service name " + className + " pkgname " + packageName);
                }
                int i = "com.samsung.android.knox.net.vpn.KnoxONSVpnService".equals(className) ? 2 : 1;
                String[] strArr = {"packageName"};
                String[] strArr2 = {packageName};
                knoxVpnEngineService.mVpnStorageProvider.getClass();
                ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnVendor", strArr, strArr2, null);
                if (dataByFields.isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("packageName", packageName);
                    contentValues.put("namespace", Integer.valueOf(i));
                    knoxVpnEngineService.mVpnStorageProvider.getClass();
                    KnoxVpnStorageProvider.putDataByFields("vpnVendor", null, null, contentValues);
                } else {
                    int i2 = 0;
                    ContentValues contentValues2 = (ContentValues) dataByFields.get(0);
                    try {
                        i2 = contentValues2.getAsInteger("namespace").intValue();
                    } catch (NumberFormatException unused) {
                    }
                    if ((i2 & i) != 0) {
                        return;
                    }
                    contentValues2.put("namespace", Integer.valueOf(i2 | i));
                    knoxVpnEngineService.mVpnStorageProvider.getClass();
                    KnoxVpnStorageProvider.putDataByFields("vpnVendor", strArr, strArr2, contentValues2);
                }
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:logVpnVendor");
                knoxVpnEngineService.mData = knoxAnalyticsData;
                knoxAnalyticsData.setProperty("vndrPkgN", packageName);
                knoxVpnEngineService.mData.setProperty("NS", (i & 1) != 0 ? "Old" : "New");
                KnoxAnalytics.log(knoxVpnEngineService.mData);
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("failed to log vendor namespace "), "KnoxVpnEngineService");
        }
    }

    /* renamed from: -$$Nest$mregisterNetworkCallback, reason: not valid java name */
    public static void m560$$Nest$mregisterNetworkCallback(final KnoxVpnEngineService knoxVpnEngineService) {
        knoxVpnEngineService.getClass();
        Log.d("KnoxVpnEngineService", "Registering network callback");
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                KnoxVpnEngineService knoxVpnEngineService2 = KnoxVpnEngineService.this;
                knoxVpnEngineService2.getClass();
                NetworkRequest.Builder addCapability = new NetworkRequest.Builder().addTransportType(1).addCapability(16);
                if (knoxVpnEngineService2.mConnectivityManager == null) {
                    knoxVpnEngineService2.mConnectivityManager = (ConnectivityManager) knoxVpnEngineService2.mContext.getSystemService(ConnectivityManager.class);
                }
                knoxVpnEngineService2.mConnectivityManager.registerNetworkCallback(addCapability.build(), knoxVpnEngineService2.mNetworkCallback);
            }
        });
    }

    /* renamed from: -$$Nest$msetVpnInterface, reason: not valid java name */
    public static void m561$$Nest$msetVpnInterface(KnoxVpnEngineService knoxVpnEngineService, String str, Object obj) {
        knoxVpnEngineService.getClass();
        if (DBG) {
            Log.d("KnoxVpnEngineService", "setVpnInterface: vendorName value is " + str + "vpnInterface value is " + obj);
        }
        if (obj == null) {
            obj = NULL_OBJECT;
        }
        knoxVpnEngineService.vpnInterfaceMap.put(str, obj);
    }

    /* renamed from: -$$Nest$mstartVpnConnectionForAllClients, reason: not valid java name */
    public static void m562$$Nest$mstartVpnConnectionForAllClients(KnoxVpnEngineService knoxVpnEngineService) {
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    HashSet hashSet = new HashSet();
                    for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                        String str = vpnProfileInfo.mProfileName;
                        if (knoxVpnEngineService.getBinderInterfaceForProfile(str) != null) {
                            int vPNTransitionState = knoxVpnEngineService.getVPNTransitionState(str);
                            if (vpnProfileInfo.routeType != 0 && (vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)) {
                                Log.d("KnoxVpnEngineService", "starting the vpn connection for the profile after UPSM is switched off " + str + "state:" + vPNTransitionState);
                                knoxVpnEngineService.startVpnProfile(str);
                            }
                        } else if (knoxVpnEngineService.mVpnConnectionList.containsKey(vpnProfileInfo.mVendorPkgName)) {
                            Log.d("KnoxVpnEngineService", "startVpnConnectionForAllClients: skip binding for " + vpnProfileInfo.mVendorPkgName);
                        } else {
                            hashSet.add(vpnProfileInfo.mVendorPkgName);
                        }
                    }
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        Log.d("KnoxVpnEngineService", "The client binder object was set to null due to app getting killed when UPSM was switched on and trying to bind again");
                        knoxVpnEngineService.bindKnoxVpnInterface(knoxVpnEngineService.mKnoxVpnHelper.getAdminIdFromPackageName(str2), str2);
                    }
                } catch (Exception e) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to start the vpn connection after UPSM is switched off" + Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mstartVpnConnectionForClient, reason: not valid java name */
    public static void m563$$Nest$mstartVpnConnectionForClient(KnoxVpnEngineService knoxVpnEngineService, int i) {
        synchronized (knoxVpnEngineService) {
            try {
                try {
                    HashSet hashSet = new HashSet();
                    for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                        if (vpnProfileInfo.mVendorUid == i) {
                            String str = vpnProfileInfo.mProfileName;
                            if (knoxVpnEngineService.getBinderInterfaceForProfile(str) != null) {
                                int vPNTransitionState = knoxVpnEngineService.getVPNTransitionState(str);
                                if (vpnProfileInfo.routeType != 0 && (vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)) {
                                    Log.d("KnoxVpnEngineService", "starting the vpn connection for the profile after system restriction is removed " + str + "state:" + vPNTransitionState + "vpnClient:" + i);
                                    knoxVpnEngineService.startVpnProfile(str);
                                }
                            } else if (knoxVpnEngineService.mVpnConnectionList.containsKey(vpnProfileInfo.mVendorPkgName)) {
                                Log.d("KnoxVpnEngineService", "startVpnConnectionForClient: skip binding for " + vpnProfileInfo.mVendorPkgName);
                            } else {
                                hashSet.add(vpnProfileInfo.mVendorPkgName);
                            }
                        }
                    }
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        Log.d("KnoxVpnEngineService", "The client binder object was set to null due to app getting killed during idle or power mode and trying to bind again");
                        knoxVpnEngineService.bindKnoxVpnInterface(knoxVpnEngineService.mKnoxVpnHelper.getAdminIdFromPackageName(str2), str2);
                    }
                } catch (Exception e) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to start the vpn connection after the system restriction is removed" + Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mstopStrongwanProxyConnection, reason: not valid java name */
    public static void m564$$Nest$mstopStrongwanProxyConnection(KnoxVpnEngineService knoxVpnEngineService, String str, String str2, int i) {
        knoxVpnEngineService.getClass();
        try {
            if (str.equalsIgnoreCase("com.samsung.sVpn")) {
                knoxVpnEngineService.getActiveProfilesForVendor(str2);
                if (((ArrayList) knoxVpnEngineService.getActiveProfilesForVendor(str2)).size() > 0) {
                    knoxVpnEngineService.getVpnManagerService().prepareVpn("[Legacy VPN]", "[Legacy VPN]", i);
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured at stopStrongwanProxyConnection API "), "KnoxVpnEngineService");
        }
    }

    /* renamed from: -$$Nest$mstopVpnConnectionForAirplaneMode, reason: not valid java name */
    public static void m565$$Nest$mstopVpnConnectionForAirplaneMode(KnoxVpnEngineService knoxVpnEngineService) {
        knoxVpnEngineService.getClass();
        int i = 0;
        try {
            for (VpnProfileInfo vpnProfileInfo : knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values()) {
                i = vpnProfileInfo.personaId;
                if (vpnProfileInfo.activateState == 1) {
                    String str = vpnProfileInfo.mProfileName;
                    Log.i("KnoxVpnEngineService", "Airplane Mode: Stopping connection for " + str);
                    AuditLog.logAsUser(5, 5, false, Process.myPid(), "KnoxVpnEngineService", "Airplane Mode: Stopping connection for profile " + str, i);
                    IKnoxVpnService binderInterfaceForProfile = knoxVpnEngineService.getBinderInterfaceForProfile(str);
                    if ((binderInterfaceForProfile != null ? binderInterfaceForProfile.stopConnection(str) : -1) != 0) {
                        Log.i("KnoxVpnEngineService", "Airplane Mode: Stopping connection for " + str + " failed!!");
                    }
                }
            }
        } catch (Exception e) {
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception stopping vpn connection for airplane mode", i);
            Log.i("KnoxVpnEngineService", "Airplane Mode: ACTION_AIRPLANE_MODE_CHANGED Exception", e);
        }
    }

    /* renamed from: -$$Nest$mvalidateProfilesForVendor, reason: not valid java name */
    public static void m566$$Nest$mvalidateProfilesForVendor(KnoxVpnEngineService knoxVpnEngineService, String str, IKnoxVpnService iKnoxVpnService) {
        knoxVpnEngineService.getClass();
        if (DBG) {
            Log.d("KnoxVpnEngineService", "validateProfilesForVendor - vendorNameWithCid = " + str + " vpnInterface = " + iKnoxVpnService);
        }
        try {
            knoxVpnEngineService.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"vendorName"}, new String[]{str}, null);
            if (iKnoxVpnService != null) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("profileName");
                    String asString2 = contentValues.getAsString("profileJson");
                    int intValue = contentValues.getAsInteger("adminUid").intValue();
                    int intValue2 = contentValues.getAsInteger("activateState").intValue();
                    if (DBG) {
                        Log.d("KnoxVpnEngineService", "validateProfilesForVendor : profileName = " + asString);
                    }
                    if (iKnoxVpnService.getConnection(asString) == null) {
                        VpnProfileInfo profileEntry = knoxVpnEngineService.vpnConfig.getProfileEntry(asString);
                        if (profileEntry != null) {
                            knoxVpnEngineService.removeProfileFromKeyStore(profileEntry.mVendorUid, asString, profileEntry.mVendorPkgName);
                        }
                        iKnoxVpnService.createConnection(asString2);
                        if (iKnoxVpnService.getConnection(asString) == null) {
                            if (intValue2 == 1) {
                                knoxVpnEngineService.sendVpnConnectionFailIntent(intValue, str, asString);
                            }
                            Log.d("KnoxVpnEngineService", "JsonProfile null and recreate connection fail");
                            knoxVpnEngineService.removeProfileFromHashMapAndDB(asString);
                        }
                    }
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("validateProfilesForVendor : Failure at "), "KnoxVpnEngineService");
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService$1] */
    public KnoxVpnEngineService(Context context) {
        KnoxVpnApiValidation knoxVpnApiValidation;
        KnoxVpnProcessManager knoxVpnProcessManager;
        String str;
        SQLiteDatabase writableDatabase;
        Injector injector = new Injector(context);
        this.mContext = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mNetworkManagementService = null;
        this.mEnterpriseDeviceManagerService = null;
        this.mNotificationMap = null;
        this.mKnoxVpnApiValidation = null;
        this.mKnoxVpnPacProcessor = null;
        this.mProcessManager = null;
        this.mChainingStateMachine = null;
        this.mAMS = null;
        this.mNotificationManager = null;
        this.mEDM = null;
        this.receiver = null;
        this.mIgnoreConnectCheckForChaining = null;
        this.mVpnConnectionList = new HashMap();
        this.mVpnClientStatus = new ArrayList();
        this.mData = null;
        this.mInjector = null;
        this.mKnoxVpnTetherAuthentication = null;
        this.mCaptiveExemptLock = new Object();
        this.notificationFlagState = null;
        this.isDeviceBootCompleted = false;
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                if (KnoxVpnEngineService.mIsCaptiveExempt && networkCapabilities.hasCapability(16)) {
                    KnoxVpnEngineService.this.sendMessageToHandler$1(29, SystemUpdateManagerService$$ExternalSyntheticOutline0.m(0, "captive"));
                }
            }
        };
        Log.d("KnoxVpnEngineService", "Initializing in constructor");
        this.mInjector = injector;
        Objects.requireNonNull(context);
        this.mContext = context;
        this.vpnInterfaceMap = new ConcurrentHashMap();
        this.mNotificationMap = new ConcurrentHashMap();
        this.mIgnoreConnectCheckForChaining = new ConcurrentHashMap();
        this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(context);
        this.vpnConfig = VpnProfileConfig.getInstance();
        this.mVpnStorageProvider = KnoxVpnStorageProvider.getInstance(context);
        KnoxVpnApiValidation knoxVpnApiValidation2 = KnoxVpnApiValidation.mKnoxVpnApiValidation;
        synchronized (KnoxVpnApiValidation.class) {
            try {
                if (KnoxVpnApiValidation.mKnoxVpnApiValidation == null) {
                    KnoxVpnApiValidation knoxVpnApiValidation3 = new KnoxVpnApiValidation();
                    knoxVpnApiValidation3.mKnoxVpnHelper = null;
                    knoxVpnApiValidation3.mUserMgr = null;
                    KnoxVpnApiValidation.mContext = context;
                    knoxVpnApiValidation3.vpnConfig = VpnProfileConfig.getInstance();
                    KnoxVpnApiValidation.mEdmStorageProvider = new EdmStorageProvider(context);
                    knoxVpnApiValidation3.mKnoxVpnHelper = KnoxVpnHelper.getInstance(context);
                    KnoxVpnApiValidation.mKnoxVpnApiValidation = knoxVpnApiValidation3;
                }
                knoxVpnApiValidation = KnoxVpnApiValidation.mKnoxVpnApiValidation;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mKnoxVpnApiValidation = knoxVpnApiValidation;
        this.mKnoxVpnPacProcessor = KnoxVpnPacProcessor.getInstance(context);
        boolean z = KnoxVpnProcessManager.DBG;
        synchronized (KnoxVpnProcessManager.class) {
            try {
                if (KnoxVpnProcessManager.instance == null) {
                    KnoxVpnProcessManager.instance = new KnoxVpnProcessManager(this);
                }
                knoxVpnProcessManager = KnoxVpnProcessManager.instance;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.mProcessManager = knoxVpnProcessManager;
        this.notificationFlagState = new ConcurrentHashMap();
        HandlerThread handlerThread = new HandlerThread("KnoxVpnHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KnoxVpnHandler(this.mHandlerThread.getLooper());
        try {
            String str2 = SystemProperties.get("ro.build.date", "NONE");
            if (!str2.equals(SystemProperties.get("persist.sys.knoxvpn.date", "NONE"))) {
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "handleKnoxVpnBuildUpdate - updated");
                }
                SystemProperties.set("persist.sys.knoxvpn.date", str2);
                checkToUpdateDb();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mVpnStorageProvider.getClass();
        EdmStorageProvider edmStorageProvider = KnoxVpnStorageProvider.mEDM;
        if (edmStorageProvider != null && (writableDatabase = edmStorageProvider.mEdmDbHelper.getWritableDatabase()) != null) {
            try {
                writableDatabase.execSQL("SELECT 1 FROM VpnNotificationTable WHERE 1=0");
                try {
                    Iterator it = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnNotificationTable", null, null, null).iterator();
                    while (it.hasNext()) {
                        KnoxVpnStorageProvider.putDataByFields("VpnNotificationFlagTable", null, null, (ContentValues) it.next());
                    }
                    writableDatabase.execSQL("DROP TABLE VpnNotificationTable;");
                } catch (Exception e2) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception = "), "KnoxVpnStorageProvider");
                }
            } catch (Exception unused) {
            }
        }
        synchronized (this) {
            if (DBG) {
                Log.d("KnoxVpnEngineService", "initializeVpnDataAndVendors");
            }
            try {
                initializeHashtable();
                if (!this.mKnoxVpnHelper.checkIfProfileListEmpty()) {
                    setupIntentFilter$1();
                    this.mInjector.getClass();
                    this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                    getVpnManagerService().registerSystemDefaultNetworkCallback();
                    addBlockingRulesForPackages();
                    this.mKnoxVpnTetherAuthentication = KnoxVpnTetherAuthentication.getInstance(this.mContext);
                }
            } catch (Exception unused2) {
                Log.e("KnoxVpnEngineService", "Error occured while trying to ini the BroadcastReceiver");
            }
        }
        Log.d("KnoxVpnVersion", "writeVersionInProperties : 2.3.0");
        switch (KnoxVpnVersion$1.$SwitchMap$com$samsung$android$knox$EdmConstants$EnterpriseKnoxSdkVersion[EdmConstants.getEnterpriseKnoxSdkVersion().ordinal()]) {
            case 13:
                str = "2.2.2";
                break;
            case 14:
                str = "2.2.3";
                break;
            case 15:
                str = "2.2.4";
                break;
            case 16:
            case 17:
                str = "2.3.0";
                break;
            case 18:
                str = "2.4.0";
                break;
            default:
                str = "2.2.0";
                break;
        }
        SystemProperties.set("net.knoxvpn.version", str);
    }

    public static IOemNetd getOemNetdService$1() {
        boolean z;
        INetd asInterface;
        IOemNetd iOemNetd = mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (mNetdService == null) {
            try {
                asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
                mNetdService = asInterface;
            } catch (RemoteException unused) {
                mNetdService = null;
                z = false;
            }
            if (asInterface != null) {
                z = asInterface.isAlive();
                if (!z && DBG) {
                    Log.e("KnoxVpnEngineService", "Can't connect to NativeNetdService netd");
                }
            }
        }
        INetd iNetd = mNetdService;
        if (iNetd != null) {
            try {
                mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to get OemNetd listener "), "KnoxVpnEngineService");
            }
        }
        return mOemNetdService;
    }

    public static ArrayList retrieveVpnListFromStorage$1() {
        VpnProfile decode;
        ArrayList arrayList = new ArrayList();
        String[] list = LegacyVpnProfileStore.list("VPN_");
        if (list != null && list.length > 0) {
            for (String str : list) {
                if (!TextUtils.isEmpty(str)) {
                    byte[] bArr = LegacyVpnProfileStore.get("VPN_" + str);
                    if (bArr != null && (decode = VpnProfile.decode(str, bArr)) != null) {
                        arrayList.add(decode);
                    }
                }
            }
        }
        return arrayList;
    }

    public static String toJSONObject(ContentValues contentValues) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("errorType", String.valueOf(contentValues.getAsInteger("errorType")));
            jSONObject.put("profileName", contentValues.getAsString("profileName"));
            jSONObject.put("vendorName", contentValues.getAsString("vendorName"));
            jSONObject.put("userID", contentValues.getAsString("containerID"));
            jSONObject.put("packageList", contentValues.getAsString("packageList"));
        } catch (JSONException unused) {
            Log.e("KnoxVpnEngineService", "toJSONObject(): JSONException");
        }
        return jSONObject.toString();
    }

    public static UidRangeParcel[] toUidRangeStableParcels(Set set) {
        ArraySet arraySet = (ArraySet) set;
        UidRangeParcel[] uidRangeParcelArr = new UidRangeParcel[arraySet.size()];
        Iterator it = arraySet.iterator();
        int i = 0;
        while (it.hasNext()) {
            UidRangeParcel uidRangeParcel = (UidRangeParcel) it.next();
            uidRangeParcelArr[i] = new UidRangeParcel(uidRangeParcel.start, uidRangeParcel.stop);
            i++;
        }
        return uidRangeParcelArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean updateIfNonLegacyUserAndCheckIfVendorAllowed(com.samsung.android.knox.net.vpn.KnoxVpnContext r7, int r8) {
        /*
            int r0 = r7.personaId
            r1 = 1
            r2 = 0
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r0)     // Catch: java.lang.Exception -> L12
            if (r3 == 0) goto L12
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r0)     // Catch: java.lang.Exception -> L12
            if (r3 == 0) goto L12
            r3 = r1
            goto L13
        L12:
            r3 = r2
        L13:
            boolean r4 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG
            java.lang.String r5 = "KnoxVpnEngineService"
            if (r4 == 0) goto L33
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "check to see userId type "
            r4.<init>(r6)
            r4.append(r0)
            java.lang.String r0 = " "
            r4.append(r0)
            r4.append(r3)
            java.lang.String r0 = r4.toString()
            android.util.Log.d(r5, r0)
        L33:
            if (r3 != 0) goto L37
            r7.adminId = r8
        L37:
            java.lang.String r8 = "com.android.settings"
            java.lang.String r7 = r7.vendorName
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L76
            java.lang.Throwable r7 = new java.lang.Throwable
            r7.<init>()
            java.lang.StackTraceElement[] r7 = r7.getStackTrace()
            if (r7 == 0) goto L6f
            int r8 = r7.length
            r0 = 2
            if (r8 <= r0) goto L6f
            r7 = r7[r0]
            java.lang.String r7 = r7.getMethodName()
            java.lang.String[] r8 = com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants.ALLOWED_METHODS_VENDOR_SETTINGS
            int r0 = r8.length
            r3 = r2
        L5b:
            if (r3 >= r0) goto L6f
            r4 = r8[r3]
            boolean r4 = r4.equalsIgnoreCase(r7)
            if (r4 == 0) goto L6c
            java.lang.String r7 = "checkIfVendorAllowed: settings vendor allowed"
            android.util.Log.d(r5, r7)
            goto L76
        L6c:
            int r3 = r3 + 1
            goto L5b
        L6f:
            java.lang.String r7 = "checkIfVendorAllowed: settings vendor not allowed"
            android.util.Log.d(r5, r7)
            r1 = r2
        L76:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.updateIfNonLegacyUserAndCheckIfVendorAllowed(com.samsung.android.knox.net.vpn.KnoxVpnContext, int):boolean");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:30|(1:31)|(4:145|146|(1:148)|(3:150|151|152)(4:153|154|155|(4:157|158|159|160)(29:161|(1:165)|166|(4:169|170|171|167)|175|176|177|178|179|(2:181|(1:183)(1:258))(1:259)|184|(4:187|188|189|185)|190|191|192|193|194|195|196|(4:199|(5:211|212|(3:214|(1:216)|217)|218|(4:222|223|224|225)(2:220|221))(3:201|202|(3:208|209|210)(3:204|205|206))|207|197)|226|227|(2:229|(3:231|232|233)(1:234))(1:254)|(2:236|(1:238)(2:239|(1:241)(1:(3:243|244|245))))|246|247|248|249|250)))(2:33|(4:46|47|(1:49)|(8:51|52|53|54|55|56|57|58)(24:65|(4:68|69|70|66)|71|72|(5:75|76|77|78|73)|81|82|(2:83|(3:85|(3:91|92|(4:96|97|(1:99)|100)(2:94|95))(3:87|88|89)|90)(2:140|141))|101|102|103|104|(1:137)|108|109|(3:118|(1:120)|(3:122|(3:124|125|126)(3:129|130|131)|(1:128))(3:132|133|134))|113|114|115|36|37|(1:39)|41|42)))|35|36|37|(0)|41|42) */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0653, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0655, code lost:
    
        android.util.Log.e("KnoxVpnEngineService", "Exception = " + android.util.Log.getStackTraceString(r0));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0634 A[Catch: all -> 0x0059, Exception -> 0x0653, TRY_LEAVE, TryCatch #4 {Exception -> 0x0653, blocks: (B:37:0x0628, B:39:0x0634), top: B:36:0x0628, outer: #9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized com.samsung.android.knox.net.vpn.EnterpriseResponseData activateVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext r25, java.lang.String r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 1651
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.activateVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String, boolean):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData addAllContainerPackagesToVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext r13, int r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.addAllContainerPackagesToVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext, int, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final synchronized EnterpriseResponseData addAllPackages(KnoxVpnContext knoxVpnContext, String str) {
        VpnProfileInfo profileEntry;
        Log.d("KnoxVpnEngineService", "addAllPackages : vpnContext.personaId value is " + knoxVpnContext.personaId + " profileName value is " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        try {
            profileEntry = this.vpnConfig.getProfileEntry(str);
        } catch (Exception e) {
            Log.d("KnoxVpnEngineService", "Error while adding all the packages to vpn: exception occured: The error code is -1" + Log.getStackTraceString(e));
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while adding all the packages to vpn for profile " + str, knoxVpnContext.personaId);
        }
        if (profileEntry == null) {
            Log.d("KnoxVpnEngineService", "Error while adding all the packages to vpn: The error code is -1");
            return enterpriseResponseData;
        }
        KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
        int i = knoxVpnContext.personaId;
        knoxVpnHelper.getClass();
        if (writeAddAllPackageToDB(knoxVpnContext, KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES"), str) == -1) {
            enterpriseResponseData.setData(126);
            Log.d("KnoxVpnEngineService", "Error while adding all the packages to vpn: The error code is 126");
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error while adding all packages to vpn for profile " + str, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
        if (profileEntry.activateState == 1) {
            if (knoxVpnContext.personaId == 0) {
                blockIncomingICMPPackets(true);
                this.mKnoxVpnHelper.getClass();
                KnoxVpnHelper.getActiveNetworkInterface();
                Integer[] numArr = KnoxVpnConstants.AID_EXEMPT_LIST;
                for (int i2 = 0; i2 < 3; i2++) {
                    this.mFirewallHelper.addExemptRulesForUid(numArr[i2].intValue());
                }
            }
            getOemNetdService$1().knoxVpnBlockUserWideDnsQuery(true, knoxVpnContext.personaId);
        }
        String defaultNetworkInterface = this.mKnoxVpnHelper.getDefaultNetworkInterface(str);
        refreshDomainInHashMap(str);
        int vPNTransitionState = getVPNTransitionState(str);
        if (vPNTransitionState != -1 && vPNTransitionState != 1) {
            if (vPNTransitionState == 2) {
                this.mFirewallHelper.addMiscRulesRange(knoxVpnContext.personaId, getVirtualInterfaceType(str), null);
                this.mFirewallHelper.addRangeRulesForFilteredPackages(knoxVpnContext.personaId, profileEntry.mVendorPkgName, profileEntry.mIpChainName, defaultNetworkInterface);
                KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                int i3 = knoxVpnContext.personaId;
                knoxVpnHelperInstance.getClass();
                updateProxyRules(3, str, KnoxVpnHelper.updateProxyList(i3, true));
            } else if (vPNTransitionState == 3) {
                this.mFirewallHelper.addMiscRulesRange(knoxVpnContext.personaId, getVirtualInterfaceType(str), null);
                this.mFirewallHelper.addRangeRulesForFilteredPackages(knoxVpnContext.personaId, profileEntry.mVendorPkgName, profileEntry.mIpChainName, defaultNetworkInterface);
            } else if (vPNTransitionState == 4) {
                String str2 = profileEntry.mInterfaceName;
                int i4 = profileEntry.mNetId;
                Log.d("KnoxVpnEngineService", "addAllPackages : profileName = " + str + "interfaceValue = " + str2);
                if (str2 != null) {
                    this.mFirewallHelper.addMiscRulesRange(knoxVpnContext.personaId, getVirtualInterfaceType(str), str2);
                    this.mFirewallHelper.addRangeRulesForFilteredPackages(knoxVpnContext.personaId, profileEntry.mVendorPkgName, profileEntry.mIpChainName, defaultNetworkInterface);
                    KnoxVpnHelper knoxVpnHelper2 = this.mKnoxVpnHelper;
                    int i5 = knoxVpnContext.personaId;
                    knoxVpnHelper2.getClass();
                    int startUid = KnoxVpnHelper.startUid(i5);
                    KnoxVpnHelper knoxVpnHelper3 = this.mKnoxVpnHelper;
                    int i6 = knoxVpnContext.personaId;
                    knoxVpnHelper3.getClass();
                    setDnsSystemProperty(startUid, KnoxVpnHelper.stopUid(i6), i4, str, str2);
                    KnoxVpnHelper knoxVpnHelperInstance2 = getKnoxVpnHelperInstance();
                    int i7 = knoxVpnContext.personaId;
                    knoxVpnHelperInstance2.getClass();
                    updateProxyRules(3, str, KnoxVpnHelper.updateProxyList(i7, true));
                }
            } else if (vPNTransitionState != 5) {
                this.mFirewallHelper.addRangeRulesForFilteredPackages(knoxVpnContext.personaId, profileEntry.mVendorPkgName, profileEntry.mIpChainName, defaultNetworkInterface);
                Log.e("KnoxVpnEngineService", "addAllPackages : VPN State not valid");
            }
            updateNotification(knoxVpnContext.personaId, str, true);
            this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
            Log.d("KnoxVpnEngineService", "success while adding all the packages to vpn: The error code is 0");
            enterpriseResponseData.setData(0);
            enterpriseResponseData.setStatus(0, 0);
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Success while adding all the packages to vpn for profile " + str, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
        this.mFirewallHelper.addMiscRulesRange(knoxVpnContext.personaId, getVirtualInterfaceType(str), null);
        this.mFirewallHelper.addRangeRulesForFilteredPackages(knoxVpnContext.personaId, profileEntry.mVendorPkgName, profileEntry.mIpChainName, defaultNetworkInterface);
        KnoxVpnHelper knoxVpnHelperInstance3 = getKnoxVpnHelperInstance();
        int i8 = knoxVpnContext.personaId;
        knoxVpnHelperInstance3.getClass();
        updateProxyRules(3, str, KnoxVpnHelper.updateProxyList(i8, true));
        startVpnProfile(str);
        Log.d("KnoxVpnEngineService", "addAllPackages : start connection called. profileName = " + str);
        updateNotification(knoxVpnContext.personaId, str, true);
        this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
        Log.d("KnoxVpnEngineService", "success while adding all the packages to vpn: The error code is 0");
        enterpriseResponseData.setData(0);
        enterpriseResponseData.setStatus(0, 0);
        AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Success while adding all the packages to vpn for profile " + str, knoxVpnContext.personaId);
        return enterpriseResponseData;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ff  */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1, types: [int] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData addAllPackagesToVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.addAllPackagesToVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final synchronized void addBlockingRulesForPackages() {
        Collection values;
        char c;
        try {
            Log.d("KnoxVpnEngineService", "addBlockingRulesForPackages");
            try {
                values = this.vpnConfig.vpnProfileInfoMap.values();
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "addBlockingRulesForPackages : Exception when reading package DB:" + Log.getStackTraceString(e));
            }
            if (values != null && values.size() > 0) {
                if (values.size() > 0) {
                    KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
                    knoxVpnFirewallHelper.getClass();
                    Log.d(KnoxVpnFirewallHelper.TAG, "deleting blocking rules");
                    knoxVpnFirewallHelper.runSingleCommand("ip rule del blackhole fwmark 60 prio 50 ;ip -6 rule del blackhole fwmark 60 prio 50 ;");
                    Log.d(KnoxVpnFirewallHelper.TAG, "Adding blocking rules");
                    knoxVpnFirewallHelper.runSingleCommand("ip rule add blackhole fwmark 60 prio 50 ;ip -6 rule add blackhole fwmark 60 prio 50 ;");
                    this.mFirewallHelper.addRulesForNoUidPackets(3, "block_traffic", "block_traffic");
                    allowAppsToMakeDnsQueryForNetId();
                }
                Iterator it = values.iterator();
                while (it.hasNext()) {
                    VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
                    String str = vpnProfileInfo.mProfileName;
                    String str2 = vpnProfileInfo.mInterfaceName;
                    int i = vpnProfileInfo.mVendorUid;
                    int i2 = vpnProfileInfo.activateState;
                    String str3 = vpnProfileInfo.mIpChainName;
                    String str4 = vpnProfileInfo.mVendorPkgName;
                    int i3 = vpnProfileInfo.personaId;
                    this.mKnoxVpnHelper.getClass();
                    String regularPackageName = KnoxVpnHelper.getRegularPackageName(str4);
                    int i4 = vpnProfileInfo.chainingEnabled;
                    ArrayList arrayList = new ArrayList();
                    getVpnManagerService().createEnterpriseVpnInstance(regularPackageName, str, i3, i4);
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                    KnoxVpnFirewallHelper knoxVpnFirewallHelper2 = this.mFirewallHelper;
                    knoxVpnFirewallHelper2.getClass();
                    if (str3 != null) {
                        knoxVpnFirewallHelper2.insertRules(46, "*mangle", Arrays.asList(str3.concat("_uidlist"), str3.concat("_act")), null, false);
                    }
                    this.mFirewallHelper.addRulesInOutputChain(str3);
                    addExemptRulesForUid(i, str);
                    Iterator it2 = vpnProfileInfo.mExemptPackageList.iterator();
                    while (it2.hasNext()) {
                        updateRulesToExemptUid(1, str, null, null, 1, ((Integer) it2.next()).intValue(), 0);
                        str4 = str4;
                        arrayList = arrayList;
                        it = it;
                    }
                    Iterator it3 = it;
                    ArrayList arrayList2 = arrayList;
                    String str5 = str4;
                    for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                        this.mKnoxVpnHelper.getClass();
                        if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                            KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                            String packageName = vpnPackageInfo.getPackageName();
                            knoxVpnHelper.getClass();
                            int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(packageName);
                            if (i2 == 1) {
                                getOemNetdService$1().knoxVpnBlockUserWideDnsQuery(true, containerIdFromPackageName);
                                this.mFirewallHelper.addMiscRulesRange(containerIdFromPackageName, getVirtualInterfaceType(str), str2);
                            }
                            this.mFirewallHelper.addRangeRulesForFilteredPackages(containerIdFromPackageName, str5, str3, this.mKnoxVpnHelper.getDefaultNetworkInterface(str));
                        } else {
                            arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
                        }
                    }
                    if (!arrayList2.isEmpty()) {
                        if (i2 == 1) {
                            this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), str2, arrayList2);
                        }
                        this.mFirewallHelper.addRulesForFilteredPackages(str5, str3, arrayList2, this.mKnoxVpnHelper.getDefaultNetworkInterface(str));
                    }
                    if (i2 == 1) {
                        c = 3;
                        this.mFirewallHelper.addMarkingRulesForFilteredPackages(3, "block_traffic", str3);
                    } else {
                        c = 3;
                    }
                    it = it3;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "addContainerPackagesToVpn : containerId value is " + i + " profileName value is " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        int addContainerPackagesToVpnValidation = this.mKnoxVpnApiValidation.addContainerPackagesToVpnValidation(knoxVpnContext, i, strArr, str);
        if (addContainerPackagesToVpnValidation != 100) {
            enterpriseResponseData.setData(Integer.valueOf(addContainerPackagesToVpnValidation));
            Log.d("KnoxVpnEngineService", "Error occured while adding container packages to vpn: The error code is " + addContainerPackagesToVpnValidation);
            if (addContainerPackagesToVpnValidation == 113) {
                enterpriseResponseData.setStatus(1, 11);
            }
            return enterpriseResponseData;
        }
        knoxVpnContext.personaId = i;
        EnterpriseResponseData addPackages = addPackages(knoxVpnContext, strArr, str);
        try {
            if (((Integer) addPackages.getData()).intValue() == 0) {
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:addContainerPackagesToVpn");
                this.mData = knoxAnalyticsData;
                setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                Arrays.sort(strArr);
                StringBuffer stringBuffer = new StringBuffer(strArr[0]);
                for (int i2 = 1; i2 < strArr.length; i2++) {
                    stringBuffer.append("," + strArr[i2]);
                }
                this.mData.setProperty("pkgLst", stringBuffer.toString());
                int i3 = knoxVpnContext.personaId;
                if (i3 != 0) {
                    this.mData.setProperty("cid", i3);
                }
                KnoxAnalytics.log(this.mData);
            }
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
        }
        return addPackages;
    }

    public final void addExemptRulesForUid(int i, String str) {
        Log.d("KnoxVpnEngineService", "addExemptRulesForUid : vendor = " + i + " for profile " + str);
        if (getChainingEnabledForProfile(i) != 1) {
            Log.d("KnoxVpnEngineService", "addExemptRulesForUid : uid = " + i);
            if (UserHandle.getAppId(i) != 1000) {
                this.mFirewallHelper.addExemptRulesForUid(i);
                return;
            }
            KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
            knoxVpnFirewallHelper.getClass();
            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1016, 1016, 3);
            KnoxVpnFirewallHelper.IpRestoreActionType ipRestoreActionType = KnoxVpnFirewallHelper.IpRestoreActionType.INSERT;
            knoxVpnFirewallHelper.insertRule(true, "*mangle", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_EXEMPT", " -m owner --gid-owner 1016", "ACCEPT", "", ipRestoreActionType), 46);
            knoxVpnFirewallHelper.insertRule(true, "*mangle", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_EXEMPT", " -m owner --uid-owner 1016", "ACCEPT", "", ipRestoreActionType), 46);
        }
    }

    public final void addMiscRulesForProfile(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("addMiscRulesForProfile : profileName =  ", str, "KnoxVpnEngineService");
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        ArrayList arrayList = new ArrayList();
        if (profileEntry == null || profileEntry.activateState == 0) {
            return;
        }
        String str2 = profileEntry.mInterfaceName;
        for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
            this.mKnoxVpnHelper.getClass();
            if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                String packageName = vpnPackageInfo.getPackageName();
                knoxVpnHelper.getClass();
                this.mFirewallHelper.addMiscRulesRange(KnoxVpnHelper.getContainerIdFromPackageName(packageName), getVirtualInterfaceType(str), str2);
            } else {
                arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), str2, arrayList);
    }

    public final EnterpriseResponseData addPackages(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
        String str2;
        IPackageManager iPackageManager;
        int i;
        String[] strArr2 = strArr;
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(2);
        enterpriseResponseData.setStatus(1, -1);
        if (knoxVpnContext == null) {
            return enterpriseResponseData;
        }
        int i2 = knoxVpnContext.personaId;
        ArrayList arrayList = new ArrayList();
        VpnProfileInfo profileEntry = this.mKnoxVpnHelper.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Error occured while trying to fetch the profile list for the vpn client ", str, "FW-KnoxVpnHelper");
            str2 = null;
        } else {
            str2 = profileEntry.mIpChainName;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i2, "addPackages : profileName value is ", str, "personaId value is", "KnoxVpnEngineService");
        try {
            int addPackagesToVpnValidation = this.mKnoxVpnApiValidation.addPackagesToVpnValidation(knoxVpnContext, strArr2, str);
            if (addPackagesToVpnValidation != 100) {
                enterpriseResponseData.setData(Integer.valueOf(addPackagesToVpnValidation));
                Log.d("KnoxVpnEngineService", "Error occured while adding packages to vpn(addPackagesToVpnValidation): The error code is " + addPackagesToVpnValidation);
                return enterpriseResponseData;
            }
            IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            StringBuilder sb = new StringBuilder();
            this.mKnoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i2, "ADD_ALL_PACKAGES");
            int length = strArr2.length;
            int i3 = 0;
            while (i3 < length) {
                String str3 = strArr2[i3];
                this.mKnoxVpnHelper.getClass();
                int uIDForPackage = KnoxVpnHelper.getUIDForPackage(i2, str3);
                if (uIDForPackage > 0) {
                    i = length;
                    String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
                    if (profileOwningTheUid == null || !profileOwningTheUid.equalsIgnoreCase(str)) {
                        String profileOwningThePackage = this.mKnoxVpnHelper.getProfileOwningThePackage(personifiedName);
                        if (profileOwningThePackage != null && profileOwningThePackage.equalsIgnoreCase(str) && i2 == UserHandle.getUserId(uIDForPackage)) {
                            Log.d("KnoxVpnEngineService", "addPackages:userId for the appId already added to the profile, skipping it " + uIDForPackage);
                        } else if (asInterface == null) {
                            iPackageManager = asInterface;
                        } else if (asInterface.checkUidPermission("android.permission.INTERNET", uIDForPackage) != 0) {
                            Log.d("KnoxVpnEngineService", "addPackages:uid is not granted internet permission " + uIDForPackage + str3);
                            writePackagestoPermissionCheckDb(i2, uIDForPackage, str, str3);
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            iPackageManager = asInterface;
                            sb2.append("addPackages:uid is granted internet permission ");
                            sb2.append(uIDForPackage);
                            sb2.append(str3);
                            Log.d("KnoxVpnEngineService", sb2.toString());
                            arrayList.add(Integer.valueOf(uIDForPackage));
                        }
                    } else {
                        Log.d("KnoxVpnEngineService", "addPackages:uid already added to the profile, skipping it " + uIDForPackage + str3);
                    }
                    iPackageManager = asInterface;
                    i3++;
                    strArr2 = strArr;
                    length = i;
                    asInterface = iPackageManager;
                } else {
                    iPackageManager = asInterface;
                    i = length;
                }
                int writePackageToDB = writePackageToDB(uIDForPackage, i2, str, str3);
                if (writePackageToDB == -1) {
                    Log.d("KnoxVpnEngineService", "Error occured while adding packages to vpn(writePackageToDB): The error code is " + writePackageToDB);
                    enterpriseResponseData.setData(1);
                    if (sb.length() > 0) {
                        AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", sb.subSequence(0, sb.length() - 2).toString() + " added to vpn for profile " + str, knoxVpnContext.personaId);
                    }
                    AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred while adding package " + str3 + " to vpn for profile " + str, knoxVpnContext.personaId);
                    return enterpriseResponseData;
                }
                sb.append(str3);
                sb.append(", ");
                i3++;
                strArr2 = strArr;
                length = i;
                asInterface = iPackageManager;
            }
            this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
            if (sb.length() > 0) {
                AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", sb.subSequence(0, sb.length() - 2).toString() + " added to vpn for profile " + str, knoxVpnContext.personaId);
            }
            this.mFirewallHelper.addRulesForFilteredPackages(knoxVpnContext.getVendorName(), str2, arrayList, this.mKnoxVpnHelper.getDefaultNetworkInterface(str));
            refreshDomainInHashMap(str);
            updateNotification(i2, str, true);
            Log.d("KnoxVpnEngineService", "Error occured while adding packages to vpn: The error code is 0");
            enterpriseResponseData.setData(0);
            enterpriseResponseData.setStatus(0, 0);
            return enterpriseResponseData;
        } catch (Exception e) {
            Log.d("KnoxVpnEngineService", "Error occured while adding packages to vpn: exception occured: The error code is -1" + Log.getStackTraceString(e));
            enterpriseResponseData.setData(-1);
            enterpriseResponseData.setStatus(1, 2);
            return enterpriseResponseData;
        }
    }

    public final synchronized EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
        try {
            ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
            EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
            enterpriseResponseData.setData((Object) null);
            enterpriseResponseData.setStatus(1, -1);
            if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
                return enterpriseResponseData;
            }
            EnterpriseResponseData addPackages = addPackages(knoxVpnContext, strArr, str);
            try {
                if (((Integer) addPackages.getData()).intValue() == 0) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:addPackagesToVpn");
                    this.mData = knoxAnalyticsData;
                    setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                    Arrays.sort(strArr);
                    StringBuffer stringBuffer = new StringBuffer(strArr[0]);
                    for (int i = 1; i < strArr.length; i++) {
                        stringBuffer.append("," + strArr[i]);
                    }
                    this.mData.setProperty("pkgLst", stringBuffer.toString());
                    int i2 = knoxVpnContext.personaId;
                    if (i2 != 0) {
                        this.mData.setProperty("cid", i2);
                    }
                    KnoxAnalytics.log(this.mData);
                }
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
            }
            return addPackages;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void addUidToExemptList(int i, int i2, String str, String str2) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        int i3 = profileEntry.mNetId;
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "The following app added to the interface ", str2, "will not go through vpn since it was blacklisted ", "KnoxVpnEngineService");
        if (getNetworkManagementService() != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new UidRangeParcel(i, i2));
                    getOemNetdService$1().knoxVpnExemptUidFromKnoxVpn(i3, (UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
                } catch (Exception unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to add Uid From Vpn List ");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final synchronized void addVpnUidRanges(String str, int i, String str2, String str3, String str4) {
        try {
            if (Binder.getCallingUid() != 1000) {
                return;
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return;
            }
            if (profileEntry.routeType == 0) {
                return;
            }
            int i2 = profileEntry.activateState;
            int size = profileEntry.mPackageMap.size();
            int i3 = 1;
            if (i2 == 0 || (size <= 0 && i2 == 1)) {
                Log.d("KnoxVpnEngineService", "Applying firewall rules after vpn is up is stopped for the profile " + str);
                IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                if (binderInterfaceForProfile != null) {
                    try {
                        binderInterfaceForProfile.stopConnection(str);
                    } catch (RemoteException unused) {
                    }
                    throw new IllegalStateException();
                }
            }
            this.mKnoxVpnHelper.getClass();
            String activeNetworkInterface = KnoxVpnHelper.getActiveNetworkInterface();
            if (activeNetworkInterface == null) {
                return;
            }
            profileEntry.mDefaultInterface = activeNetworkInterface;
            profileEntry.mInterfaceName = str2;
            profileEntry.mInterfaceAddress = str3;
            profileEntry.mInterfaceV6Address = str4;
            profileEntry.mNetId = i;
            profileEntry.mVpnStartTimeToConnect = System.currentTimeMillis();
            int i4 = (str3 == null || str4 == null) ? (str3 == null && str4 != null) ? 2 : 1 : 3;
            profileEntry.mInterface_type = i4;
            String str5 = profileEntry.mIpChainName;
            int i5 = profileEntry.mVendorUid;
            String str6 = profileEntry.mVendorPkgName;
            Log.d("KnoxVpnEngineService", "start applying exempt rules for knox vpn profile " + str);
            Iterator it = profileEntry.mExemptPackageList.iterator();
            while (it.hasNext()) {
                int i6 = i4;
                updateRulesToExemptUid(1, str, str2, activeNetworkInterface, 3, ((Integer) it.next()).intValue(), i6);
                i3 = i3;
                i5 = i5;
                i4 = i6;
                str5 = str5;
                activeNetworkInterface = activeNetworkInterface;
            }
            String str7 = str5;
            int i7 = i4;
            String str8 = activeNetworkInterface;
            int i8 = i3;
            if (UserHandle.getAppId(i5) == 1000 && str6.contains("com.samsung.sVpn")) {
                profileEntry.mVpnClientType = i8;
            } else {
                profileEntry.mVpnClientType = 0;
            }
            if (profileEntry.mVpnClientType == i8) {
                try {
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                } catch (RemoteException unused2) {
                }
            }
            Log.d("KnoxVpnEngineService", "start applying dns rules for knox vpn profile " + str);
            for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                    KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                    String packageName = vpnPackageInfo.getPackageName();
                    knoxVpnHelper.getClass();
                    int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(packageName);
                    this.mKnoxVpnHelper.getClass();
                    int startUid = KnoxVpnHelper.startUid(containerIdFromPackageName);
                    this.mKnoxVpnHelper.getClass();
                    setDnsSystemProperty(startUid, KnoxVpnHelper.stopUid(containerIdFromPackageName), i, str, str2);
                } else {
                    int uid = vpnPackageInfo.getUid();
                    setDnsSystemProperty(uid, uid, i, str, str2);
                }
            }
            Log.d("KnoxVpnEngineService", "addVpnUidRanges:defaultInterface value to which the virual tunnel is added is " + str8 + " for the profile " + str + " with interface type " + i7 + " netId " + i);
            KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
            knoxVpnFirewallHelper.getClass();
            boolean checknterface = KnoxVpnFirewallHelper.checknterface(str2);
            String str9 = KnoxVpnFirewallHelper.TAG;
            if (checknterface) {
                String str10 = str2 == null ? "block_traffic" : str2;
                if (i7 == 1) {
                    knoxVpnFirewallHelper.insertNatRules(4, str10);
                } else if (i7 == 2) {
                    knoxVpnFirewallHelper.insertNatRules(6, str10);
                } else if (i7 != 3) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i7, "unknown interface type has been recieved ", str9);
                } else {
                    knoxVpnFirewallHelper.insertNatRules(46, str10);
                }
            } else {
                DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str2, str9);
            }
            this.mFirewallHelper.addIpRouteAndPolicyRules(i7, str2);
            this.mFirewallHelper.addRulesForNoUidPackets(i7, str7, str2);
            this.mFirewallHelper.addMarkingRulesForFilteredPackages(i7, str2, str7);
            addMiscRulesForProfile(str);
            this.mFirewallHelper.addRulesToAcceptIncomingPackets(i7, str2);
            this.mFirewallHelper.updateDropRulesForNoUidPackets(0, str2, str3, str8, str4);
            if (profileEntry.mUsbTethering == i8) {
                try {
                    String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
                    int i9 = profileEntry.isUsbTetheringAuthEnabled;
                    if (interfaceNameForUsbtethering != null) {
                        if (i9 != i8) {
                            String[] dnsServerListForInterface = getVpnManagerService().getDnsServerListForInterface(str2);
                            this.mKnoxVpnHelper.getClass();
                            this.mFirewallHelper.addRulesForUsbTethering(getVirtualInterfaceType(str), str2, interfaceNameForUsbtethering, KnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering), dnsServerListForInterface);
                        } else if (this.mKnoxVpnTetherAuthentication.isTetherAuthSuccessful) {
                            Log.d("KnoxVpnTetherAuthentication", "vpn is up and and if mutual authentication is already validated");
                            String[] dnsServerListForInterface2 = getVpnManagerService().getDnsServerListForInterface(str2);
                            this.mKnoxVpnHelper.getClass();
                            this.mFirewallHelper.addRulesForUsbTethering(getVirtualInterfaceType(str), str2, interfaceNameForUsbtethering, KnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering), dnsServerListForInterface2);
                        } else {
                            Log.d("KnoxVpnTetherAuthentication", "vpn is up and if mutual authentication is going to be validated");
                            this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(profileEntry.personaId, interfaceNameForUsbtethering, this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
                        }
                    }
                } catch (RemoteException unused3) {
                }
            }
            if (profileEntry.mProxyServer != null || profileEntry.mPacurl != Uri.EMPTY) {
                int uid2 = UserHandle.getUid(profileEntry.personaId, 1002);
                KnoxVpnFirewallHelper knoxVpnFirewallHelper2 = this.mFirewallHelper;
                knoxVpnFirewallHelper2.getClass();
                if (str2 != null && !str2.contains("block_traffic")) {
                    if (i7 == i8) {
                        knoxVpnFirewallHelper2.insertRulesToAcceptProxyPackets(4, uid2, str2);
                    } else if (i7 == 2) {
                        knoxVpnFirewallHelper2.insertRulesToAcceptProxyPackets(6, uid2, str2);
                    } else if (i7 != 3) {
                        Log.e(KnoxVpnFirewallHelper.TAG, "unknown interface type has been recieved " + i7);
                    } else {
                        knoxVpnFirewallHelper2.insertRulesToAcceptProxyPackets(46, uid2, str2);
                    }
                }
                if (profileEntry.getProxyInfo() == null) {
                    getKnoxVpnPacProcessor().bindProxyService(str, str2, profileEntry.mPacurl, i);
                } else {
                    getKnoxVpnPacProcessor().setCurrentProxyScript(str, str2, profileEntry.mPacurl);
                    getKnoxVpnPacProcessor().setMiscValueForPacProfile(i, str, str2);
                    this.mFirewallHelper.removeRulesToAllowAccessToLocalHostWithValidMark(profileEntry.getProxyInfo().getPort(), i7, null);
                    this.mFirewallHelper.addRulesToAllowAccessToLocalHostWithValidMark(profileEntry.getProxyInfo().getPort(), i7, str2);
                }
            }
            handleChainingScenarioForStartConnection(str);
            if (DBG) {
                Log.d("KnoxVpnEngineService", "Applying Knox VPN Analytics rules after vpn is up");
            }
            System.currentTimeMillis();
            long currentTimeMillis = System.currentTimeMillis() - profileEntry.mVpnStartTimeToConnect;
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", i8, "API:addVpnUidRanges");
            this.mData = knoxAnalyticsData;
            knoxAnalyticsData.setProperty("vpnEt", currentTimeMillis);
            this.mData.setProperty("vndrPkgN", profileEntry.mVendorPkgName);
            KnoxAnalytics.log(this.mData);
            Log.d("KnoxVpnEngineService", "Applying firewall rules after vpn is up is finished");
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void allowAppsToMakeDnsQueryForNetId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mKnoxVpnHelper.getClass();
                getOemNetdService$1().knoxVpnInsertUidForDnsAuthorization(new int[]{KnoxVpnHelper.getUIDForPackage(0, "com.android.providers.downloads")});
                getOemNetdService$1().knoxVpnInsertUidForDnsAuthorization(new int[]{1002});
                getOemNetdService$1().knoxVpnInsertUidForDnsAuthorization(new int[]{1000});
            } catch (Exception unused) {
                Log.e("KnoxVpnEngineService", "Error at allowAppsToMakeDnsQueryForNetId");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) {
        try {
            if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
                return 140;
            }
            int allowUsbTetheringValidation = this.mKnoxVpnApiValidation.allowUsbTetheringValidation(knoxVpnContext, str, bundle);
            if (allowUsbTetheringValidation != 100) {
                return allowUsbTetheringValidation;
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            try {
            } catch (Exception unused) {
                allowUsbTetheringValidation = 101;
            }
            if (profileEntry.mUsbTethering == 1) {
                return allowUsbTetheringValidation;
            }
            this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(1, str);
            profileEntry.mUsbTethering = 1;
            this.mKnoxVpnHelper.registerNetdTetherEventListener(true);
            this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(true);
            profileEntry.isUsbTetheringAuthEnabled = 1;
            this.mKnoxVpnHelper.updateUsbTetherAuthDetails(str, bundle, true);
            this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(str, "com.samsung.knox.vpn.tether.auth", true);
            this.mKnoxVpnTetherAuthentication.bindTetherAuthService(knoxVpnContext.getPersonaId(), str, bundle);
            String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
            String str2 = profileEntry.mInterfaceName;
            int i = profileEntry.activateState;
            if (interfaceNameForUsbtethering != null) {
                if (str2 == null && i == 1) {
                    Log.d("KnoxVpnTetherAuthentication", "Applying rules to drop tether packets after allowAuthUsbTetheringOverVpn API is called and before vpn comes up");
                    this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                } else if (str2 != null && i == 1) {
                    Log.d("KnoxVpnTetherAuthentication", "start tether auth process after allowAuthUsbTetheringOverVpn API is called");
                    this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(profileEntry.personaId, interfaceNameForUsbtethering, this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
                }
            }
            return allowUsbTetheringValidation;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
        try {
            ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
            try {
                if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
                    return 141;
                }
                if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
                    return 140;
                }
                int allowUsbTetheringValidation = this.mKnoxVpnApiValidation.allowUsbTetheringValidation(knoxVpnContext, str, null);
                if (allowUsbTetheringValidation != 100) {
                    return allowUsbTetheringValidation;
                }
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry == null) {
                    return 108;
                }
                try {
                } catch (Exception unused) {
                    allowUsbTetheringValidation = 101;
                }
                if (profileEntry.mUsbTethering == 1) {
                    return allowUsbTetheringValidation;
                }
                this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(1, str);
                profileEntry.mUsbTethering = 1;
                this.mKnoxVpnHelper.registerNetdTetherEventListener(true);
                this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(true);
                String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
                String str2 = profileEntry.mInterfaceName;
                int i = profileEntry.activateState;
                if (interfaceNameForUsbtethering != null) {
                    if (str2 == null && i == 1) {
                        Log.d("KnoxVpnEngineService", "Applying rules to drop tether packets after enableUsbTethering API is called and before vpn comes up");
                        this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                    } else if (str2 != null && i == 1) {
                        String[] dnsServerListForInterface = getVpnManagerService().getDnsServerListForInterface(str2);
                        this.mKnoxVpnHelper.getClass();
                        this.mFirewallHelper.addRulesForUsbTethering(getVirtualInterfaceType(str), str2, interfaceNameForUsbtethering, KnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering), dnsServerListForInterface);
                    }
                }
                return allowUsbTetheringValidation;
            } catch (RemoteException unused2) {
                return 141;
            } catch (SecurityException unused3) {
                return 141;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean bindKnoxVpnInterface(int i, String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("Bind to Vpn Vendor Service : vendorName = ", str, "KnoxVpnEngineService");
        this.mKnoxVpnHelper.getClass();
        String regularPackageName = KnoxVpnHelper.getRegularPackageName(str);
        this.mKnoxVpnHelper.getClass();
        int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(str);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "bindKnoxVpnInterface is being called by the admin ", "KnoxVpnEngineService");
        this.mKnoxVpnHelper.getClass();
        if (KnoxVpnHelper.getUIDForPackage(containerIdFromPackageName, regularPackageName) < 0) {
            Log.d("KnoxVpnEngineService", "Bind to VPN Vendor is fail ");
            AuditLog.logEventAsUser(containerIdFromPackageName, 17, new Object[]{regularPackageName});
            return false;
        }
        if (DBG) {
            Log.d("KnoxVpnEngineService", "bindKnoxVpnInterface : user > " + containerIdFromPackageName + ", package name > " + regularPackageName);
        }
        if (getVpnInterface(str) != null) {
            sendBindSuccessIntent(i, str);
            return true;
        }
        if (this.mVpnConnectionList.containsKey(str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("unbinding previous service before binding again for the vpn client ", str, "KnoxVpnEngineService");
            try {
                this.mContext.unbindService((ServiceConnection) this.mVpnConnectionList.get(str));
            } catch (Exception unused) {
                Log.e("KnoxVpnEngineService", "unbinding failed since the service is already unbinded or not existing");
            }
            this.mVpnConnectionList.remove(str);
        }
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(regularPackageName, ".BIND_SERVICE_NEW");
        Log.d("KnoxVpnEngineService", "Bind to Vpn Vendor Service : vendorAction = " + m$1 + " container ID : " + containerIdFromPackageName);
        VpnServiceConnection vpnServiceConnection = new VpnServiceConnection(containerIdFromPackageName, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent(m$1);
            List<ResolveInfo> queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 0, containerIdFromPackageName);
            if (queryIntentServicesAsUser.size() > 0) {
                for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
                    if (resolveInfo.serviceInfo.packageName.equalsIgnoreCase(regularPackageName)) {
                        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                        intent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                    }
                }
            }
            intent.setPackage(regularPackageName);
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(intent, vpnServiceConnection, 83886081, new UserHandle(containerIdFromPackageName));
            if (bindServiceAsUser) {
                this.mVpnConnectionList.put(str, vpnServiceConnection);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.d("KnoxVpnEngineService", "Bind to Vpn Vendor Service : bindSuccess = " + bindServiceAsUser);
            if (bindServiceAsUser) {
                AuditLog.logEventAsUser(containerIdFromPackageName, 18, new Object[]{regularPackageName});
            } else {
                AuditLog.logEventAsUser(containerIdFromPackageName, 19, new Object[]{regularPackageName});
            }
            return bindServiceAsUser;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) {
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
            return false;
        }
        KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
        int personaId = knoxVpnContext.getPersonaId();
        String vendorName = knoxVpnContext.getVendorName();
        knoxVpnHelper.getClass();
        return bindKnoxVpnInterface(knoxVpnContext.adminId, KnoxVpnHelper.getPersonifiedName(personaId, vendorName));
    }

    public final void blockIncomingICMPPackets(boolean z) {
        this.mKnoxVpnHelper.getClass();
        String activeNetworkInterface = KnoxVpnHelper.getActiveNetworkInterface();
        if (activeNetworkInterface != null) {
            KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
            knoxVpnFirewallHelper.getClass();
            if (z) {
                String concat = " -p ICMP --icmp-type 8 -i ".concat(activeNetworkInterface);
                KnoxVpnFirewallHelper.IpRestoreActionType ipRestoreActionType = KnoxVpnFirewallHelper.IpRestoreActionType.INSERT;
                knoxVpnFirewallHelper.insertRule(true, "*filter", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_filter_input_drop", concat, "DROP", "", ipRestoreActionType), 4);
                knoxVpnFirewallHelper.insertRule(true, "*filter", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_filter_input_drop", " -p ICMPV6 --icmpv6-type 8 -i ".concat(activeNetworkInterface), "DROP", "", ipRestoreActionType), 6);
                return;
            }
            String concat2 = " -p ICMP --icmp-type 8 -i ".concat(activeNetworkInterface);
            KnoxVpnFirewallHelper.IpRestoreActionType ipRestoreActionType2 = KnoxVpnFirewallHelper.IpRestoreActionType.DELETE;
            knoxVpnFirewallHelper.insertRule(false, "*filter", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_filter_input_drop", concat2, "DROP", "", ipRestoreActionType2), 4);
            knoxVpnFirewallHelper.insertRule(false, "*filter", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_filter_input_drop", " -p ICMPV6 --icmpv6-type 8 -i ".concat(activeNetworkInterface), "DROP", "", ipRestoreActionType2), 6);
        }
    }

    public final ContextInfo checkCallingUidPermission(KnoxVpnContext knoxVpnContext) {
        ArrayList arrayList = new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_VPN_GENERIC"));
        ContextInfo contextInfo = new ContextInfo(knoxVpnContext.adminId, knoxVpnContext.personaId);
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            return enterpriseDeviceManager.enforceActiveAdminPermissionByContext(contextInfo, arrayList);
        }
        throw new SecurityException("Admin cannot be verified");
    }

    public final int checkChainingStatus(String str) {
        IKnoxVpnService binderInterfaceForProfile;
        int i = 0;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                String str2 = profileEntry.mVendorPkgName;
                Log.d("KnoxVpnEngineService", "checkChainingStatus: vendorName value is " + str2);
                String profileOwningThePackage = this.mKnoxVpnHelper.getProfileOwningThePackage(str2);
                Log.d("KnoxVpnEngineService", "checkChainingStatus: profile_added_vendor_name value is " + profileOwningThePackage);
                if (profileOwningThePackage != null && (binderInterfaceForProfile = getBinderInterfaceForProfile(profileOwningThePackage)) != null) {
                    int state = binderInterfaceForProfile.getState(profileOwningThePackage);
                    Log.d("KnoxVpnEngineService", "checkChainingStatus: state value is " + state);
                    if (state != 4) {
                        if (((Boolean) this.mIgnoreConnectCheckForChaining.get(Integer.valueOf(profileEntry.personaId))).booleanValue()) {
                        }
                    }
                    i = 1;
                    this.mIgnoreConnectCheckForChaining.put(Integer.valueOf(profileEntry.personaId), Boolean.FALSE);
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkChainingStatus API "), "KnoxVpnEngineService");
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "checkChainingStatus: chaining_status value is ", "KnoxVpnEngineService");
        return i;
    }

    public final void checkExistsEmailPackageInProfiles(int i, HashMap hashMap, HashSet hashSet) {
        String[] strArr;
        KnoxVpnEngineService knoxVpnEngineService = this;
        ArrayList arrayList = new ArrayList();
        HashMap hashMap2 = new HashMap();
        int i2 = 1;
        boolean z = i % 2 != 0;
        Iterator it = knoxVpnEngineService.vpnConfig.vpnProfileInfoMap.values().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            strArr = KnoxVpnConstants.EMAIL_PACKAGE_LIST;
            if (!hasNext) {
                break;
            }
            VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
            if (vpnProfileInfo != null) {
                for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                    if (vpnPackageInfo.getUid() == -2) {
                        KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                        String packageName = vpnPackageInfo.getPackageName();
                        knoxVpnHelperInstance.getClass();
                        int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(packageName);
                        if (vpnProfileInfo.activateState == i2) {
                            Iterator it2 = hashSet.iterator();
                            boolean z2 = false;
                            while (it2.hasNext()) {
                                String[] packagesForUid = knoxVpnEngineService.mContext.getPackageManager().getPackagesForUid(((Integer) it2.next()).intValue());
                                if (packagesForUid != null) {
                                    int length = packagesForUid.length;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= length) {
                                            break;
                                        }
                                        String str = packagesForUid[i3];
                                        if (Arrays.asList(strArr).contains(str)) {
                                            DualAppManagerService$$ExternalSyntheticOutline0.m("checkExistsEmailPackageInProfiles :: Email package is matched but exempted: ", str, "KnoxVpnEngineService");
                                            z2 = true;
                                            break;
                                        }
                                        i3++;
                                    }
                                    if (z2) {
                                        break;
                                    } else {
                                        knoxVpnEngineService = this;
                                    }
                                }
                            }
                            if (!z2) {
                                arrayList.add(Integer.valueOf(containerIdFromPackageName));
                            }
                        }
                        getKnoxVpnHelperInstance().getClass();
                        KnoxVpnHelper.setProxyFlagForEmail(containerIdFromPackageName, false);
                    } else {
                        if (vpnProfileInfo.activateState == 1) {
                            hashMap2.put(Integer.valueOf(vpnPackageInfo.getUid()), vpnPackageInfo.getPackageName());
                        }
                        KnoxVpnHelper knoxVpnHelperInstance2 = getKnoxVpnHelperInstance();
                        int userId = UserHandle.getUserId(vpnPackageInfo.getUid());
                        knoxVpnHelperInstance2.getClass();
                        KnoxVpnHelper.setProxyFlagForEmail(userId, false);
                    }
                    knoxVpnEngineService = this;
                    i2 = 1;
                }
            }
            knoxVpnEngineService = this;
            i2 = 1;
        }
        if (!z) {
            for (Integer num : hashMap.keySet()) {
                int intValue = num.intValue();
                hashMap2.remove(num);
                if (((Boolean) hashMap.get(num)).booleanValue()) {
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        if (((Integer) arrayList.get(i4)).intValue() == intValue) {
                            arrayList.remove(i4);
                        }
                    }
                }
            }
        }
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            KnoxVpnHelper knoxVpnHelperInstance3 = getKnoxVpnHelperInstance();
            int intValue2 = ((Integer) arrayList.get(i5)).intValue();
            knoxVpnHelperInstance3.getClass();
            KnoxVpnHelper.setProxyFlagForEmail(intValue2, true);
        }
        for (Integer num2 : hashMap2.keySet()) {
            int intValue3 = num2.intValue();
            List asList = Arrays.asList(strArr);
            KnoxVpnHelper knoxVpnHelperInstance4 = getKnoxVpnHelperInstance();
            String str2 = (String) hashMap2.get(num2);
            knoxVpnHelperInstance4.getClass();
            if (asList.contains(KnoxVpnHelper.getRegularPackageName(str2))) {
                Log.d("KnoxVpnEngineService", "checkExistsEmailPackageInProfiles :: Email package is matched : " + ((String) hashMap2.get(num2)));
                KnoxVpnHelper knoxVpnHelperInstance5 = getKnoxVpnHelperInstance();
                int userId2 = UserHandle.getUserId(intValue3);
                knoxVpnHelperInstance5.getClass();
                KnoxVpnHelper.setProxyFlagForEmail(userId2, true);
            }
        }
    }

    public final boolean checkIfCallerIsVpnVendor(int i) {
        String str;
        try {
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            if (packagesForUid != null) {
                str = packagesForUid[0];
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "Packagename of uid " + i + " is " + str);
                }
            } else {
                str = null;
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfCallerIsVpnVendor "), "KnoxVpnEngineService");
        }
        return implementsVpnService(UserHandle.getUserId(i), str);
    }

    public final boolean checkIfLocalProxyPortExists(int i) {
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.getProxyInfo().getPort() == i) {
                    Log.d("KnoxVpnEngineService", "The local proxy port is currently owned by the vpn profile " + vpnProfileInfo.mProfileName);
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            Log.e("KnoxVpnEngineService", "Exception occured while trying to check if local proxy port exists for vpn profile");
            return false;
        }
    }

    public final boolean checkIfUidIsExempted(int i) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            if (vpnProfileInfo.activateState == 1) {
                for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                    if (vpnPackageInfo.getUid() == -2) {
                        int userId = UserHandle.getUserId(i);
                        KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                        String packageName = vpnPackageInfo.getPackageName();
                        knoxVpnHelperInstance.getClass();
                        if (userId == KnoxVpnHelper.getContainerIdFromPackageName(packageName)) {
                            Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
                            while (it.hasNext()) {
                                if (i == ((Integer) it.next()).intValue()) {
                                    return true;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "checkIfUidIsExempted returned false for uid ", "KnoxVpnEngineService");
        return false;
    }

    public final boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) {
        boolean z = false;
        try {
            boolean z2 = DBG;
            if (z2) {
                Log.d("KnoxVpnEngineService", "checkIfVendorCreatedKnoxProfile: profile name and the vendor uid is " + str + i);
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                String str2 = profileEntry.mVendorPkgName;
                knoxVpnHelper.getClass();
                String regularPackageName = KnoxVpnHelper.getRegularPackageName(str2);
                if (z2) {
                    Log.d("KnoxVpnEngineService", "The vendor name who created the profile is " + regularPackageName);
                }
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.getUIDForPackage(i2, regularPackageName) == i) {
                    if (z2) {
                        Log.d("KnoxVpnEngineService", "The vendor name who created the profile and the caller has the same uid");
                    }
                    z = true;
                }
            }
        } catch (Exception e) {
            if (DBG) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("checkIfVendorCreatedKnoxProfile Exception :"), "KnoxVpnEngineService");
            }
        }
        if (DBG) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("checkIfVendorCreatedKnoxProfile: profilecreatedByKnoxVendor ", "KnoxVpnEngineService", z);
        }
        return z;
    }

    public final void checkToUpdateDb() {
        Log.d("KnoxVpnEngineService", "checkToUpdateDb");
        try {
            this.mVpnStorageProvider.getClass();
            int profileId = KnoxVpnStorageProvider.getProfileId();
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", null, null, null);
            if (profileId != 0 || dataByFields.size() <= 0) {
                return;
            }
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("profileName");
                if (asString != null) {
                    contentValues.put("profileId", Integer.valueOf(profileId));
                    this.mVpnStorageProvider.getClass();
                    KnoxVpnStorageProvider.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{asString}, contentValues);
                    String[] strArr = {String.valueOf(profileId)};
                    profileId++;
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("profileCount", Integer.valueOf(profileId));
                    this.mVpnStorageProvider.getClass();
                    KnoxVpnStorageProvider.putDataByFields("VpnAnalyticsTable", new String[]{"profileCount"}, strArr, contentValues2);
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("exception - "), "KnoxVpnEngineService");
        }
    }

    public final void createProcessKillNotification(String str) {
        try {
            String string = this.mContext.getString(R.string.permdesc_observeCompanionDevicePresence);
            String string2 = this.mContext.getString(R.string.permdesc_nfcTransactionEvent);
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                int userId = UserHandle.getUserId(profileEntry.mVendorUid);
                getNotificationManager().notifyAsUser(null, str.hashCode(), new Notification.Builder(this.mContext, SystemNotificationChannels.VPN).setSmallIcon(R.drawable.ic_dialog_alert).setContentTitle(string).setContentText(string2).setDefaults(0).setPriority(2).setAutoCancel(true).setStyle(new Notification.BigTextStyle().bigText(string2)).build(), new UserHandle(userId));
            }
        } catch (Exception unused) {
            if (DBG) {
                Log.e("KnoxVpnEngineService", "Exception occured while trying to create a notification to inform user about process restart ");
            }
        }
    }

    public final synchronized EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
        String personifiedName;
        IKnoxVpnService vpnInterface;
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        this.mKnoxVpnHelper.getClass();
        if (KnoxVpnHelper.isUsbTetheringConfigured(str)) {
            try {
                if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
                    enterpriseResponseData.setData(141);
                    return enterpriseResponseData;
                }
            } catch (RemoteException unused) {
                enterpriseResponseData.setData(141);
                return enterpriseResponseData;
            } catch (SecurityException unused2) {
                enterpriseResponseData.setData(141);
                return enterpriseResponseData;
            }
        }
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        String str2 = knoxVpnContext.vendorName;
        Log.d("KnoxVpnEngineService", "createVpnProfile: vendorName = " + str2 + " : personaId = " + knoxVpnContext.personaId);
        int createVpnProfileValidation = this.mKnoxVpnApiValidation.createVpnProfileValidation(knoxVpnContext, str);
        if (createVpnProfileValidation != 100) {
            enterpriseResponseData.setData(Integer.valueOf(createVpnProfileValidation));
            Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is " + createVpnProfileValidation);
            AuditLog.logEventAsUser(knoxVpnContext.personaId, 20, new Object[]{str2});
            return enterpriseResponseData;
        }
        try {
            KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
            int i = knoxVpnContext.personaId;
            try {
                knoxVpnHelper.getClass();
                personifiedName = KnoxVpnHelper.getPersonifiedName(i, str2);
                vpnInterface = getVpnInterface(personifiedName);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (vpnInterface == null) {
            enterpriseResponseData.setData(110);
            Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 110");
            return enterpriseResponseData;
        }
        this.mKnoxVpnHelper.getClass();
        String profileNameFromJsonString = KnoxVpnHelper.getProfileNameFromJsonString(str);
        syncVpnProfile(vpnInterface, profileNameFromJsonString);
        int createConnection = vpnInterface.createConnection(str);
        if (createConnection != 0) {
            enterpriseResponseData.setData(102);
            Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 102");
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error received from vendor while creating vpn connection for profile " + profileNameFromJsonString, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
        this.mVpnStorageProvider.getClass();
        int profileId = KnoxVpnStorageProvider.getProfileId();
        if (!this.mKnoxVpnHelper.addVpnProfileToDatabase(knoxVpnContext, str, profileId)) {
            boolean z = DBG;
            if (z) {
                Log.d("KnoxVpnEngineService", "knox vpn profile creation failed : failure to add the entry to db");
            }
            Bundle bundle = new Bundle();
            bundle.putString("profileName", profileNameFromJsonString);
            sendMessageToHandler$1(16, bundle);
            int removeConnection = vpnInterface.removeConnection(profileNameFromJsonString);
            if (z) {
                Log.d("KnoxVpnEngineService", "knox vpn profile creation failed : removeStatus value is " + removeConnection);
            }
            enterpriseResponseData.setData(126);
            Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 126");
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred while adding profile " + profileNameFromJsonString + " into database", knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
        if (this.mKnoxVpnHelper.addVpnProfileToMap(knoxVpnContext, str, profileId)) {
            try {
                if (this.vpnConfig.vpnProfileInfoMap.values().size() == 1) {
                    setupIntentFilter$1();
                    try {
                        this.mInjector.getClass();
                        KnoxVpnFirewallHelper knoxVpnFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                        this.mFirewallHelper = knoxVpnFirewallHelper;
                        try {
                            knoxVpnFirewallHelper.getClass();
                            Log.d(KnoxVpnFirewallHelper.TAG, "deleting blocking rules");
                            knoxVpnFirewallHelper.runSingleCommand("ip rule del blackhole fwmark 60 prio 50 ;ip -6 rule del blackhole fwmark 60 prio 50 ;");
                            Log.d(KnoxVpnFirewallHelper.TAG, "Adding blocking rules");
                            knoxVpnFirewallHelper.runSingleCommand("ip rule add blackhole fwmark 60 prio 50 ;ip -6 rule add blackhole fwmark 60 prio 50 ;");
                            this.mFirewallHelper.addRulesForNoUidPackets(3, "block_traffic", "block_traffic");
                            Log.d("KnoxVpnEngineService", "Setting the system property to confirm Generic vpn policy has been configured");
                            SystemProperties.set("net.vpn.framework", "2.0");
                            allowAppsToMakeDnsQueryForNetId();
                            this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(profileNameFromJsonString, "com.android.vpndialogs", true);
                            getVpnManagerService().registerSystemDefaultNetworkCallback();
                            this.mKnoxVpnTetherAuthentication = KnoxVpnTetherAuthentication.getInstance(this.mContext);
                        } catch (Exception e3) {
                            e = e3;
                        }
                    } catch (Exception e4) {
                        e = e4;
                    }
                }
                if (this.mKnoxVpnHelper.getConnectionType(profileNameFromJsonString) == 1 && !this.mProcessManager.isProcessObserverRegistered()) {
                    this.mProcessManager.registerProcessObserver();
                }
                this.mKnoxVpnHelper.getClass();
                addExemptRulesForUid(KnoxVpnHelper.getUidForPackageName(personifiedName), profileNameFromJsonString);
                this.mKnoxVpnHelper.addOrRemoveSystemAppFromBatteryOptimization(profileNameFromJsonString, true);
                this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(profileNameFromJsonString, str2, true);
                this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(UserHandle.getUid(knoxVpnContext.personaId, 1002), profileNameFromJsonString, true);
                String randomIpChainName = this.mKnoxVpnHelper.setRandomIpChainName(profileNameFromJsonString);
                Log.d("KnoxVpnEngineService", "The IP Chain Name obtained for the profile " + profileNameFromJsonString + " is " + randomIpChainName);
                KnoxVpnFirewallHelper knoxVpnFirewallHelper2 = this.mFirewallHelper;
                try {
                    knoxVpnFirewallHelper2.getClass();
                    if (randomIpChainName != null) {
                        knoxVpnFirewallHelper2.insertRules(46, "*mangle", Arrays.asList(randomIpChainName.concat("_uidlist"), randomIpChainName.concat("_act")), null, false);
                    }
                    this.mFirewallHelper.addRulesInOutputChain(randomIpChainName);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        getVpnManagerService().createEnterpriseVpnInstance(str2, profileNameFromJsonString, knoxVpnContext.personaId, this.mKnoxVpnHelper.getChainingValueForProfile(profileNameFromJsonString));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Log.d("KnoxVpnEngineService", "profileid : " + profileId);
                        String[] strArr = {"profileCount"};
                        String[] strArr2 = {String.valueOf(profileId)};
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("profileCount", Integer.valueOf(profileId + 1));
                        this.mVpnStorageProvider.getClass();
                        KnoxVpnStorageProvider.putDataByFields("VpnAnalyticsTable", strArr, strArr2, contentValues);
                        Log.d("KnoxVpnEngineService", "knox vpn profile creation success: The error code is " + createConnection);
                        AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Vpn profile " + profileNameFromJsonString + " successfully created", knoxVpnContext.personaId);
                        try {
                            KnoxVpnStorageProvider knoxVpnStorageProvider = this.mVpnStorageProvider;
                            String[] strArr3 = {profileNameFromJsonString, String.valueOf(checkCallingUidPermission.mCallerUid)};
                            knoxVpnStorageProvider.getClass();
                            KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnConnectionFail", new String[]{"profileName", "adminUid"}, strArr3);
                        } catch (Exception e5) {
                            Log.e("KnoxVpnEngineService", "Exception at removeProfileByEnterpriseVpnConnectionFailTable " + Log.getStackTraceString(e5));
                        }
                        enterpriseResponseData.setData(Integer.valueOf(createConnection));
                        enterpriseResponseData.setStatus(0, 0);
                        try {
                            if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
                                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:createVpnProfile");
                                this.mData = knoxAnalyticsData;
                                setCommonProperties(knoxAnalyticsData, knoxVpnContext, profileNameFromJsonString, -1);
                                setPropertiesWithLocalEntry(this.mData, profileNameFromJsonString);
                                KnoxAnalytics.log(this.mData);
                            }
                        } catch (Exception e6) {
                            Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e6));
                        }
                        return enterpriseResponseData;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Exception e7) {
                    e = e7;
                }
            } catch (Exception e8) {
                e = e8;
            }
        } else {
            boolean z2 = DBG;
            if (z2) {
                Log.d("KnoxVpnEngineService", "knox vpn profile creation failed : failure to add the entry to local entry");
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("profileName", profileNameFromJsonString);
            sendMessageToHandler$1(16, bundle2);
            int removeConnection2 = vpnInterface.removeConnection(profileNameFromJsonString);
            if (z2) {
                Log.d("KnoxVpnEngineService", "knox vpn profile creation failed : removeStatus value is " + removeConnection2);
            }
            String[] strArr4 = {"profileName"};
            String[] strArr5 = {profileNameFromJsonString};
            try {
                this.mVpnStorageProvider.getClass();
                KnoxVpnStorageProvider.mEDM.deleteDataByFields("VpnProfileInfo", strArr4, strArr5);
                enterpriseResponseData.setData(127);
                Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 127");
                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred while adding vpn profile " + profileNameFromJsonString + " in vpn map", knoxVpnContext.personaId);
                return enterpriseResponseData;
            } catch (Exception e9) {
                e = e9;
            }
        }
        Exception exc = e;
        enterpriseResponseData.setData(-1);
        Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: exception occured: The error code is -1" + Log.getStackTraceString(exc));
        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception occurred while creating vpn profile for vendor " + str2, knoxVpnContext.personaId);
        return enterpriseResponseData;
    }

    public final synchronized int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                if (profileEntry.mUsbTethering == 0) {
                    return 100;
                }
                if (profileEntry.isUsbTetheringAuthEnabled == 0) {
                    if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
                        return 141;
                    }
                }
            }
            if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
                return 140;
            }
            int disallowUsbTetheringValidation = this.mKnoxVpnApiValidation.disallowUsbTetheringValidation(knoxVpnContext, str);
            if (disallowUsbTetheringValidation != 100) {
                return disallowUsbTetheringValidation;
            }
            VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(str);
            if (profileEntry2 == null) {
                return 108;
            }
            try {
                this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(0, str);
                profileEntry2.mUsbTethering = 0;
                this.mKnoxVpnHelper.registerNetdTetherEventListener(false);
                this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(false);
                this.mFirewallHelper.removeRulesForUsbTethering(profileEntry2.mInterface_type, profileEntry2.mInterfaceName);
                if (profileEntry2.isUsbTetheringAuthEnabled == 1) {
                    this.mKnoxVpnHelper.updateUsbTetherAuthDetails(str, null, false);
                    profileEntry2.isUsbTetheringAuthEnabled = 0;
                    this.mKnoxVpnTetherAuthentication.unbindTetherAuthService();
                    this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(str, "com.samsung.knox.vpn.tether.auth", false);
                }
            } catch (Exception unused) {
                disallowUsbTetheringValidation = 101;
            }
            return disallowUsbTetheringValidation;
        } catch (RemoteException unused2) {
            return 141;
        } catch (SecurityException unused3) {
            return 141;
        }
    }

    public final synchronized void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        try {
        } catch (Exception unused) {
            Log.e("KnoxVpnEngineService", "knoxvpnprofileinfo: error occured while trying to print the profile state");
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("knoxvpnprofileinfo: Permission Denial: can't dump PersonaManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        printWriter.print("knoxvpnprofileinfo: The profile info being printed at time " + System.currentTimeMillis() + "\n");
        Iterator it = ((ArrayList) this.mVpnClientStatus).iterator();
        while (it.hasNext()) {
            printWriter.print("knoxvpnprofileinfo:" + ((String) it.next()) + "\n");
        }
        Iterator it2 = ((ArrayList) this.mKnoxVpnPacProcessor.getKnoxVpnProxyClientStatus()).iterator();
        while (it2.hasNext()) {
            printWriter.print("knoxvpnprofileinfo:" + ((String) it2.next()) + "\n");
        }
        KnoxVpnTetherAuthentication knoxVpnTetherAuthentication = this.mKnoxVpnTetherAuthentication;
        if (knoxVpnTetherAuthentication != null) {
            Iterator it3 = ((ArrayList) knoxVpnTetherAuthentication.getKnoxVpnTetherAuthClientStatus()).iterator();
            while (it3.hasNext()) {
                printWriter.print("knoxvpnprofileinfo:" + ((String) it3.next()) + "\n");
            }
        }
        List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = getAMSInstance().getProcessesInErrorState();
        if (processesInErrorState != null) {
            for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                String str = processErrorStateInfo.processName;
                if (str != null && str.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error condition is " + processErrorStateInfo.condition + "\n");
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error longMsg is " + processErrorStateInfo.longMsg + "\n");
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error shortMsg is " + processErrorStateInfo.shortMsg + "\n");
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error stackTrace is " + processErrorStateInfo.stackTrace + "\n");
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error pid is " + processErrorStateInfo.pid + "\n");
                    printWriter.print("knoxvpnprofileinfo: knox vpn proxy apk error uid is " + processErrorStateInfo.uid + "\n");
                }
            }
        }
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            printWriter.print("knoxvpnprofileinfo: profile name is " + vpnProfileInfo.getProfileName() + "\n");
            printWriter.print("knoxvpnprofileinfo: The default interface to which the profile is connected to is " + vpnProfileInfo.mDefaultInterface + "\n");
            printWriter.print("knoxvpnprofileinfo: The vitual interface to which the profile is connected to is " + vpnProfileInfo.mInterfaceName + "\n");
            printWriter.print("knoxvpnprofileinfo: The vitual interface v4 address to which the profile is connected to is " + vpnProfileInfo.mInterfaceAddress + "\n");
            printWriter.print("knoxvpnprofileinfo: The vitual interface v6 address to which the profile is connected to is " + vpnProfileInfo.mInterfaceV6Address + "\n");
            printWriter.print("knoxvpnprofileinfo: The profile is triggered by the vpn client " + vpnProfileInfo.mVendorPkgName + "\n");
            printWriter.print("knoxvpnprofileinfo: The admin id of the profile is " + vpnProfileInfo.admin_id + "\n");
            printWriter.print("knoxvpnprofileinfo: The profile has been created under the user " + vpnProfileInfo.personaId + "\n");
            printWriter.print("knoxvpnprofileinfo: The activation state of the vpn profile " + vpnProfileInfo.activateState + "\n");
            printWriter.print("knoxvpnprofileinfo: is chaining enabled for the profile ? " + vpnProfileInfo.chainingEnabled + "\n");
            printWriter.print("knoxvpnprofileinfo: is uid tracking enabled for the profile ? " + vpnProfileInfo.uidPidSearchEnabled + "\n");
            printWriter.print("knoxvpnprofileinfo: The profile is triggered by the vpn client whose uid is " + vpnProfileInfo.mVendorUid + "\n");
            printWriter.print("knoxvpnprofileinfo: The ipChainValue for the profile configured is " + vpnProfileInfo.mIpChainName + "\n");
            printWriter.print("knoxvpnprofileinfo: The usb tethering for the profile configured is " + vpnProfileInfo.mUsbTethering + "\n");
            printWriter.print("knoxvpnprofileinfo: usb interface name, if active is " + this.mKnoxVpnHelper.getInterfaceNameForUsbtethering() + "\n");
            int i = vpnProfileInfo.isUsbTetheringAuthEnabled;
            printWriter.print("knoxvpnprofileinfo: is usb tethering configured with auth " + i + "\n");
            if (i == 1) {
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig login page configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherLoginpage") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig response page configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherResponsePage") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig client cert issuer CN configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherClientCertIssuerCN") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig client cert issued CN configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherClientCertIssuedCN") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig captive cert configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(1, vpnProfileInfo.getProfileName(), "tetherCaptivePortalCert") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig captive alias configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherCaptivePortalAlias") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig ca cert configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(1, vpnProfileInfo.getProfileName(), "tetherCACert") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig ca alias configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherCAlias") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig user cert configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(1, vpnProfileInfo.getProfileName(), "tetherServerCert") + "\n");
                printWriter.print("knoxvpnprofileinfo: usbTetherAuthConfig user alias configured ? " + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(0, vpnProfileInfo.getProfileName(), "tetherServerAlias") + "\n");
            }
            printWriter.print("knoxvpnprofileinfo: connection type is " + vpnProfileInfo.vpnConnectionType + "\n");
            for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                if (vpnPackageInfo.getUid() == -2) {
                    KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                    String packageName = vpnPackageInfo.getPackageName();
                    knoxVpnHelperInstance.getClass();
                    printWriter.print("knoxvpnprofileinfo: The following user has been added to the profile " + KnoxVpnHelper.getContainerIdFromPackageName(packageName) + "\n");
                } else {
                    printWriter.print("knoxvpnprofileinfo: The following application with uid has been added to the profile " + vpnPackageInfo.getUid() + " whose package name is " + vpnPackageInfo.getPackageName() + "\n");
                }
            }
            Iterator it4 = vpnProfileInfo.mExemptPackageList.iterator();
            while (it4.hasNext()) {
                printWriter.print("knoxvpnprofileinfo: The following application with uid has been exempted from vpn connection " + ((Integer) it4.next()).intValue() + "\n");
            }
            Iterator it5 = this.mKnoxVpnHelper.getUninsalledAppsFromExemptedList(vpnProfileInfo.getProfileName()).iterator();
            while (it5.hasNext()) {
                printWriter.print("knoxvpnprofileinfo:Exempted app is either uninstalled by end-user or not yet installed " + ((String) it5.next()) + "\n");
            }
            if (vpnProfileInfo.mProxyServer != null || vpnProfileInfo.mPacurl != Uri.EMPTY) {
                String profileName = vpnProfileInfo.getProfileName();
                if (vpnProfileInfo.getProxyInfo() != null) {
                    int port = vpnProfileInfo.getProxyInfo().getPort();
                    String host = vpnProfileInfo.getProxyInfo().getHost();
                    printWriter.print("knoxvpnprofileinfo: The profile has been configured with proxy configuration whose local port as set in f/w is " + port + "\n");
                    printWriter.print("knoxvpnprofileinfo: The profile has been configured with proxy configuration whose local host as set in f/w is " + host + "\n");
                }
                printWriter.print("knoxvpnprofileinfo: is the proxy credentials predefined for the profile? " + vpnProfileInfo.credentialsPredefined + "\n");
                printWriter.print("knoxvpnprofileinfo: is proxy auth required for the profile " + vpnProfileInfo.proxyAuthRequried + "\n");
                printWriter.print("knoxvpnprofileinfo: The profile has been configured with the pac url " + vpnProfileInfo.mPacurl + "\n");
                printWriter.print("knoxvpnprofileinfo: The profile has been configured with the Static Proxy Server " + vpnProfileInfo.mProxyServer + "\n");
                printWriter.print("knoxvpnprofileinfo: The profile has been configured with the Static Proxy port " + vpnProfileInfo.mProxyPort + "\n");
                printWriter.print("knoxvpnprofileinfo: The proxy port retrieved from the apk is " + this.mKnoxVpnPacProcessor.getProxyPortForProfile(profileName) + "\n");
                printWriter.print("knoxvpnprofileinfo: check to see if proxy thread is running or not in the apk is " + this.mKnoxVpnPacProcessor.isProxyThreadRunning(profileName) + "\n");
                printWriter.print("knoxvpnprofileinfo: current state of the proxy thread in the apk is " + this.mKnoxVpnPacProcessor.getProxythreadStatus(profileName) + "\n");
                printWriter.print("knoxvpnprofileinfo: check to see if proxy thread is alive or not in the apk is " + this.mKnoxVpnPacProcessor.isProxyThreadAlive(profileName) + "\n");
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(vpnProfileInfo.getProfileName());
            if (binderInterfaceForProfile != null) {
                printWriter.print("knoxvpnprofileinfo: The state of the profile in client is " + binderInterfaceForProfile.getState(vpnProfileInfo.getProfileName()) + "\n");
            }
        }
    }

    public final ActivityManagerService getAMSInstance() {
        if (this.mAMS == null) {
            this.mAMS = (ActivityManagerService) ServiceManager.getService("activity");
        }
        return this.mAMS;
    }

    public final List getActiveProfilesForVendor(String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            try {
                for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                    String str2 = vpnProfileInfo.mVendorPkgName;
                    int i = vpnProfileInfo.activateState;
                    if (str2.equals(str) && i == 1 && !this.mKnoxVpnHelper.checkIfVpnProfileTableIsEmpty(str2)) {
                        String str3 = vpnProfileInfo.mProfileName;
                        Log.d("KnoxVpnEngineService", "profileName = " + str3);
                        arrayList.add(str3);
                    }
                }
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception = "), "KnoxVpnEngineService");
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00d0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00d1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData getAllContainerPackagesInVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext r9, int r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getAllContainerPackagesInVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext, int, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final EnterpriseResponseData getAllPackages(KnoxVpnContext knoxVpnContext, String str) {
        boolean z = DBG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("get all packages : Profile : ", str, "KnoxVpnEngineService");
        }
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        int i = -1;
        enterpriseResponseData.setStatus(1, -1);
        enterpriseResponseData.setData((Object) null);
        try {
            int personaId = knoxVpnContext.getPersonaId();
            ArrayList arrayList = new ArrayList();
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"profileName", "packageCid"}, new String[]{str, Integer.toString(personaId)}, new String[]{"packageUid", "packageName"});
            if (z) {
                printProfileVpnMap();
            }
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("packageName");
                    Log.d("KnoxVpnEngineService", "getAllPackages_personifiedPackageName::" + asString);
                    int intValue = contentValues.getAsInteger("packageUid").intValue();
                    Log.d("KnoxVpnEngineService", "getAllPackages_uid::" + intValue);
                    this.mKnoxVpnHelper.getClass();
                    if (!asString.equalsIgnoreCase(KnoxVpnHelper.getPersonifiedName(personaId, "ADD_ALL_PACKAGES"))) {
                        if (intValue == i) {
                            this.mKnoxVpnHelper.getClass();
                            arrayList.add(KnoxVpnHelper.getRegularPackageName(asString));
                        } else {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(intValue);
                            Log.d("KnoxVpnEngineService", "getAllPackages_packagesforUid[]::" + this.mContext.getPackageManager().getPackagesForUid(intValue) + " uid::" + intValue);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            if (packagesForUid != null) {
                                for (String str2 : packagesForUid) {
                                    arrayList.add(str2);
                                }
                            }
                        }
                        i = -1;
                    }
                }
            }
            Iterator it2 = ((ArrayList) getPackageListNoInternetPermission(personaId, str)).iterator();
            while (it2.hasNext()) {
                arrayList.add((String) it2.next());
            }
            enterpriseResponseData.setData((String[]) arrayList.toArray(new String[0]));
            enterpriseResponseData.setStatus(0, 0);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("get all packages : Exceptionin notify: "), "KnoxVpnEngineService");
        }
        return enterpriseResponseData;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x008f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData getAllPackagesInVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext r8, java.lang.String r9) {
        /*
            r7 = this;
            com.samsung.android.knox.net.vpn.EnterpriseResponseData r0 = new com.samsung.android.knox.net.vpn.EnterpriseResponseData
            r0.<init>()
            r1 = 0
            r0.setData(r1)
            r2 = 1
            r3 = -1
            r0.setStatus(r2, r3)
            if (r8 != 0) goto L11
            return r0
        L11:
            com.samsung.android.knox.ContextInfo r2 = r7.checkCallingUidPermission(r8)
            int r2 = r2.mCallerUid
            boolean r2 = updateIfNonLegacyUserAndCheckIfVendorAllowed(r8, r2)
            if (r2 != 0) goto L1e
            return r0
        L1e:
            java.lang.String r2 = "getAllPackagesInVpnProfile: profileName value is "
            java.lang.String r3 = "vpnContext.personaId value is "
            java.lang.StringBuilder r2 = com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0.m(r2, r9, r3)
            int r3 = r8.personaId
            java.lang.String r4 = "KnoxVpnEngineService"
            com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0.m(r2, r3, r4)
            com.android.server.enterprise.vpn.knoxvpn.KnoxVpnApiValidation r2 = r7.mKnoxVpnApiValidation
            r2.getClass()
            java.lang.String r3 = "KnoxVpnApiValidation"
            if (r9 == 0) goto L7c
            java.lang.String r5 = r8.vendorName     // Catch: java.lang.Exception -> L4c
            if (r5 != 0) goto L3d
            goto L7c
        L3d:
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r2 = r2.vpnConfig     // Catch: java.lang.Exception -> L4c
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r2 = r2.getProfileEntry(r9)     // Catch: java.lang.Exception -> L4c
            if (r2 != 0) goto L4e
            java.lang.String r2 = "getAllPackagesInVpnProfileValidation: profileInfo value is null"
            android.util.Log.d(r3, r2)     // Catch: java.lang.Exception -> L4c
            goto L8d
        L4c:
            r2 = move-exception
            goto L83
        L4e:
            int r5 = r2.admin_id     // Catch: java.lang.Exception -> L4c
            int r6 = r8.adminId     // Catch: java.lang.Exception -> L4c
            if (r5 == r6) goto L5b
            java.lang.String r2 = "getAllPackagesInVpnProfileValidation: Not the same Admin"
            android.util.Log.d(r3, r2)     // Catch: java.lang.Exception -> L4c
            goto L8d
        L5b:
            int r5 = r2.admin_id     // Catch: java.lang.Exception -> L4c
            int r5 = android.os.UserHandle.getUserId(r5)     // Catch: java.lang.Exception -> L4c
            int r6 = r8.getPersonaId()     // Catch: java.lang.Exception -> L4c
            if (r5 == 0) goto L6f
            if (r6 != 0) goto L6f
            java.lang.String r2 = "EMM present in work profile trying to configure vpn for user 0"
            android.util.Log.e(r3, r2)     // Catch: java.lang.Exception -> L4c
            goto L8d
        L6f:
            int r2 = r2.routeType     // Catch: java.lang.Exception -> L4c
            if (r2 != 0) goto L7a
            java.lang.String r2 = "getAllPackagesInVpnProfileValidation: profile is of system type"
            android.util.Log.d(r3, r2)     // Catch: java.lang.Exception -> L4c
            goto L8d
        L7a:
            r1 = r9
            goto L8d
        L7c:
            java.lang.String r2 = "getAllPackagesInVpnProfileValidation: profileName value is null"
            android.util.Log.d(r3, r2)     // Catch: java.lang.Exception -> L4c
            goto L8d
        L83:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Exception at getAllPackagesInVpnProfileValidation API "
            r5.<init>(r6)
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r2, r5, r3)
        L8d:
            if (r1 != 0) goto L90
            return r0
        L90:
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r1 = r7.vpnConfig
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r1 = r1.getProfileEntry(r9)
            if (r1 != 0) goto L9e
            java.lang.String r7 = "Error occured while getting all packages in vpn profile: The error code is 108"
            android.util.Log.d(r4, r7)
            return r0
        L9e:
            java.util.concurrent.ConcurrentHashMap r0 = r1.mPackageMap
            java.util.Collection r0 = r0.values()
            java.util.Iterator r0 = r0.iterator()
        La8:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto Lcc
            java.lang.Object r1 = r0.next()
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo r1 = (com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo) r1
            int r2 = r1.getCid()
            com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper r3 = r7.mKnoxVpnHelper
            r3.getClass()
            boolean r1 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.isPackageForAddAllPackages(r1)
            if (r1 == 0) goto La8
            int r1 = r8.personaId
            if (r1 != r2) goto La8
            com.samsung.android.knox.net.vpn.EnterpriseResponseData r7 = r7.getAllRangedPackages(r8, r9)
            return r7
        Lcc:
            com.samsung.android.knox.net.vpn.EnterpriseResponseData r7 = r7.getAllPackages(r8, r9)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getAllPackagesInVpnProfile(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final EnterpriseResponseData getAllRangedPackages(KnoxVpnContext knoxVpnContext, String str) {
        if (DBG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("get all ranged packages : Profile : ", str, "KnoxVpnEngineService");
        }
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setStatus(1, -1);
        enterpriseResponseData.setData((Object) null);
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                    this.mKnoxVpnHelper.getClass();
                    if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                        KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                        String packageName = vpnPackageInfo.getPackageName();
                        knoxVpnHelper.getClass();
                        int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(packageName);
                        if (DBG) {
                            Log.d("KnoxVpnEngineService", "get all ranged packages : containerId " + containerIdFromPackageName);
                        }
                        if (containerIdFromPackageName == knoxVpnContext.personaId) {
                            enterpriseResponseData.setData(this.mKnoxVpnHelper.getUserPackageListForProfile(containerIdFromPackageName, str));
                            enterpriseResponseData.setStatus(0, 0);
                            return enterpriseResponseData;
                        }
                    }
                }
            }
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error occured while fetching all packages of a profile: exception occured: The error code is -1"), "KnoxVpnEngineService");
        }
        return enterpriseResponseData;
    }

    public final synchronized EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) {
        IKnoxVpnService vpnInterface;
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData((Object) null);
        enterpriseResponseData.setStatus(1, -1);
        if (knoxVpnContext == null) {
            return enterpriseResponseData;
        }
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        int i = knoxVpnContext.adminId;
        String str = knoxVpnContext.vendorName;
        int i2 = knoxVpnContext.personaId;
        this.mKnoxVpnHelper.getClass();
        String personifiedName = KnoxVpnHelper.getPersonifiedName(i2, str);
        Log.d("KnoxVpnEngineService", "getAllVpnProfiles: vendorNameWithCid value is " + personifiedName);
        if (KnoxCustomManagerService.SETTING_PKG_NAME.equals(str)) {
            if (this.mVpnInfoPolicy == null) {
                this.mInjector.getClass();
                this.mVpnInfoPolicy = IVpnInfoPolicy.Stub.asInterface(ServiceManager.getService("vpn_policy"));
            }
            IVpnInfoPolicy iVpnInfoPolicy = this.mVpnInfoPolicy;
            if (iVpnInfoPolicy != null) {
                try {
                    List allVpnSettingsProfiles = iVpnInfoPolicy.getAllVpnSettingsProfiles(checkCallingUidPermission);
                    if (allVpnSettingsProfiles != null) {
                        enterpriseResponseData.setData(allVpnSettingsProfiles);
                        enterpriseResponseData.setStatus(0, 0);
                    }
                } catch (RemoteException unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while getting vpn settings profiles");
                }
                return enterpriseResponseData;
            }
        }
        try {
            vpnInterface = getVpnInterface(personifiedName);
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "getAllVpnProfiles exception result is " + Log.getStackTraceString(e));
        }
        if (vpnInterface == null) {
            Log.d("KnoxVpnEngineService", "getAllVpnProfiles: interface returned null");
            enterpriseResponseData.setData((Object) null);
            return enterpriseResponseData;
        }
        List<String> allConnections = vpnInterface.getAllConnections();
        if (allConnections != null) {
            r2 = allConnections.size() > 0 ? new ArrayList() : null;
            for (String str2 : allConnections) {
                this.mKnoxVpnHelper.getClass();
                String profileNameFromJsonString = KnoxVpnHelper.getProfileNameFromJsonString(str2);
                boolean z = DBG;
                if (z) {
                    Log.d("KnoxVpnEngineService", "getAllVpnProfiles: profileName > " + profileNameFromJsonString);
                }
                if (z) {
                    Log.d("KnoxVpnEngineService", "getAllVpnProfiles: profile > " + str2);
                }
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(profileNameFromJsonString);
                if (profileEntry != null && (i == profileEntry.admin_id || UserHandle.getAppId(i) == 1000)) {
                    if (i2 == profileEntry.personaId) {
                        r2.add(str2);
                    }
                }
            }
        }
        enterpriseResponseData.setData(r2);
        enterpriseResponseData.setStatus(0, 0);
        return enterpriseResponseData;
    }

    public final IKnoxVpnService getBinderInterfaceForProfile(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
            return getVpnInterface(profileEntry.mVendorPkgName);
        }
        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", XmlUtils$$ExternalSyntheticOutline0.m("Error getting binder for profile ", str, ". Vendor service might not be running"), -1);
        StorageManagerService$$ExternalSyntheticOutline0.m("get binder for profile : Profile does not exist : ", str, "KnoxVpnEngineService");
        return null;
    }

    public final EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData((Object) null);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        try {
            if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                return enterpriseResponseData;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.e("KnoxVpnEngineService", "getting CACertificate : Service is not started");
                return null;
            }
            enterpriseResponseData.setData(binderInterfaceForProfile.getCACertificate(str));
            enterpriseResponseData.setStatus(0, 0);
            return enterpriseResponseData;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("getting CACertificate : Failure at "), "KnoxVpnEngineService");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0054, code lost:
    
        if (r3 == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0056, code lost:
    
        android.util.Log.d("KnoxVpnEngineService", "getChainingEnabledForProfile:chaining enabled for profile");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005c, code lost:
    
        r0 = r2.chainingEnabled;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getChainingEnabledForProfile(int r7) {
        /*
            r6 = this;
            boolean r0 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG
            java.lang.String r1 = "KnoxVpnEngineService"
            if (r0 == 0) goto Lc
            java.lang.String r0 = "getChainingEnabledForProfile: uid value is "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r7, r0, r1)
        Lc:
            r0 = -1
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r6 = r6.vpnConfig     // Catch: java.lang.Exception -> L41
            java.util.concurrent.ConcurrentHashMap r6 = r6.vpnProfileInfoMap     // Catch: java.lang.Exception -> L41
            java.util.Collection r6 = r6.values()     // Catch: java.lang.Exception -> L41
            java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Exception -> L41
        L19:
            boolean r2 = r6.hasNext()     // Catch: java.lang.Exception -> L41
            if (r2 == 0) goto L69
            java.lang.Object r2 = r6.next()     // Catch: java.lang.Exception -> L41
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r2 = (com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo) r2     // Catch: java.lang.Exception -> L41
            boolean r3 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG     // Catch: java.lang.Exception -> L41
            if (r3 == 0) goto L43
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L41
            r4.<init>()     // Catch: java.lang.Exception -> L41
            java.lang.String r5 = "getChainingEnabledForProfile: uidOfVendor value is "
            r4.append(r5)     // Catch: java.lang.Exception -> L41
            int r5 = r2.mVendorUid     // Catch: java.lang.Exception -> L41
            r4.append(r5)     // Catch: java.lang.Exception -> L41
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> L41
            android.util.Log.d(r1, r4)     // Catch: java.lang.Exception -> L41
            goto L43
        L41:
            r6 = move-exception
            goto L5f
        L43:
            int r4 = r2.mVendorUid     // Catch: java.lang.Exception -> L41
            if (r4 != r7) goto L19
            if (r3 == 0) goto L4f
            java.lang.String r4 = "getChainingEnabledForProfile: vendorName is same"
            android.util.Log.d(r1, r4)     // Catch: java.lang.Exception -> L41
        L4f:
            int r4 = r2.chainingEnabled     // Catch: java.lang.Exception -> L41
            r5 = 1
            if (r4 != r5) goto L19
            if (r3 == 0) goto L5c
            java.lang.String r6 = "getChainingEnabledForProfile:chaining enabled for profile"
            android.util.Log.d(r1, r6)     // Catch: java.lang.Exception -> L41
        L5c:
            int r0 = r2.chainingEnabled     // Catch: java.lang.Exception -> L41
            goto L69
        L5f:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r2 = "Exception = "
            r7.<init>(r2)
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r6, r7, r1)
        L69:
            boolean r6 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG
            if (r6 == 0) goto L73
            java.lang.String r6 = "getChainingEnabledForProfile: chainingEnabled value is "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r0, r6, r1)
        L73:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getChainingEnabledForProfile(int):int");
    }

    public final List getDomainsByProfileName(String str) {
        refreshDomainInHashMap(str);
        return (List) this.mNotificationMap.get(str);
    }

    public final IEnterpriseDeviceManager getEnterpriseDeviceManagerService() {
        IBinder service = ServiceManager.getService("enterprise_policy");
        if (service != null && this.mEnterpriseDeviceManagerService == null) {
            this.mEnterpriseDeviceManagerService = IEnterpriseDeviceManager.Stub.asInterface(service);
            if (DBG) {
                Log.d("KnoxVpnEngineService", "getEnterpriseDeviceManagerService value is" + this.mEnterpriseDeviceManagerService);
            }
        }
        return this.mEnterpriseDeviceManagerService;
    }

    public final EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "getErrorString API called for profileName = " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData((Object) null);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        try {
        } catch (Exception e) {
            if (DBG) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("getting vpn error string : Failure at "), "KnoxVpnEngineService");
            }
        }
        if (this.mKnoxVpnApiValidation.getErrorStringValidation(knoxVpnContext, str) != null) {
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.d("KnoxVpnEngineService", "getting error string for the profile failed: Error occured since the service is not started");
                return enterpriseResponseData;
            }
            enterpriseResponseData.setData(binderInterfaceForProfile.getErrorString(str));
            enterpriseResponseData.setStatus(0, 0);
            return enterpriseResponseData;
        }
        Log.d("KnoxVpnEngineService", "getting error string for the profile failed: Error occured while validating the profile");
        this.mVpnStorageProvider.getClass();
        ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnConnectionFail", new String[]{"profileName"}, new String[]{str}, new String[]{"profileName", "adminUid", "errorType", "vendorName", "containerID", "packageList"});
        if (dataByFields.size() > 0 && ((ContentValues) dataByFields.get(0)).getAsInteger("adminUid").intValue() == checkCallingUidPermission.mCallerUid) {
            Log.d("KnoxVpnEngineService", "Error occured while try to recreate the profile");
            enterpriseResponseData.setData(toJSONObject((ContentValues) dataByFields.get(0)));
            enterpriseResponseData.setStatus(2, -1);
            try {
                this.mVpnStorageProvider.getClass();
                KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnConnectionFail", new String[]{"profileName"}, new String[]{str});
            } catch (Exception e2) {
                Log.e("KnoxVpnEngineService", "Exception at removeProfileByEnterpriseVpnConnectionFailTable " + Log.getStackTraceString(e2));
            }
        }
        return enterpriseResponseData;
    }

    public final String getInterfaceNameForUid(int i) {
        String str;
        try {
        } catch (Exception unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Exception occured while trying to fetch interfacename for the uid ", "KnoxVpnEngineService");
        }
        if (Binder.getCallingUid() != 1000) {
            return null;
        }
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            if (vpnProfileInfo.activateState == 1) {
                if (vpnProfileInfo.mVendorUid == i && vpnProfileInfo.chainingEnabled != 1) {
                    return null;
                }
                for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                    if (vpnPackageInfo.getUid() == i) {
                        return vpnProfileInfo.mInterfaceName;
                    }
                    if (vpnPackageInfo.getUid() == -2) {
                        int userId = UserHandle.getUserId(i);
                        KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                        String packageName = vpnPackageInfo.getPackageName();
                        knoxVpnHelperInstance.getClass();
                        if (userId == KnoxVpnHelper.getContainerIdFromPackageName(packageName)) {
                            try {
                                for (VpnProfileInfo vpnProfileInfo2 : this.vpnConfig.vpnProfileInfoMap.values()) {
                                    if (vpnProfileInfo2.activateState == 1 && vpnProfileInfo2.mVendorUid == i) {
                                        str = vpnProfileInfo2.mProfileName;
                                        break;
                                    }
                                }
                            } catch (Exception unused2) {
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Exception occured while trying to fetch profile name for the uid ", "KnoxVpnEngineService");
                            }
                            str = null;
                            if (str == null) {
                                Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
                                while (it.hasNext()) {
                                    if (i == ((Integer) it.next()).intValue()) {
                                        return null;
                                    }
                                }
                                if (i == 0) {
                                    return null;
                                }
                                return vpnProfileInfo.mInterfaceName;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return null;
    }

    public final KnoxVpnHelper getKnoxVpnHelperInstance() {
        if (this.mKnoxVpnHelper == null) {
            this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(this.mContext);
        }
        return this.mKnoxVpnHelper;
    }

    public final KnoxVpnPacProcessor getKnoxVpnPacProcessor() {
        if (this.mKnoxVpnPacProcessor == null) {
            this.mKnoxVpnPacProcessor = KnoxVpnPacProcessor.getInstance(this.mContext);
        }
        return this.mKnoxVpnPacProcessor;
    }

    public final int getKnoxVpnProfileType(String str) {
        if (str == null) {
            return -1;
        }
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                return profileEntry.routeType;
            }
            return -1;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception = "), "KnoxVpnEngineService");
            return -1;
        }
    }

    public final INetworkManagementService getNetworkManagementService() {
        boolean z = DBG;
        if (z) {
            Log.d("KnoxVpnEngineService", "getNetworkManagementService is reached initially");
        }
        IBinder service = ServiceManager.getService("network_management");
        if (z) {
            Log.d("KnoxVpnEngineService", "getNetworkManagementService binder value is" + service);
        }
        if (service != null && this.mNetworkManagementService == null) {
            this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
            if (z) {
                Log.d("KnoxVpnEngineService", "getNetworkManagementService mNetworkManagementService value is" + this.mNetworkManagementService);
            }
        }
        return this.mNetworkManagementService;
    }

    public final int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) {
        checkCallingUidPermission(knoxVpnContext);
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        try {
            if (AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", this.mContext.getPackageManager().getNameForUid(callingUid), userId) == 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return getNotificationDismissibleFlagInternal(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d("KnoxVpnEngineService", "Only Knox Internal package can get notification flag");
        return -1;
    }

    public final int getNotificationDismissibleFlagInternal(int i) {
        ConcurrentHashMap concurrentHashMap;
        if (Binder.getCallingUid() == 1000 && (concurrentHashMap = this.notificationFlagState) != null && concurrentHashMap.containsKey(Integer.valueOf(i))) {
            return ((Integer) this.notificationFlagState.get(Integer.valueOf(i))).intValue();
        }
        return 1;
    }

    public NotificationManager getNotificationManager() {
        if (this.mNotificationManager == null) {
            this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        }
        return this.mNotificationManager;
    }

    public final List getPackageListNoInternetPermission(int i, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    int intValue = ((ContentValues) it.next()).getAsInteger("packageUid").intValue();
                    if (intValue != -1 && UserHandle.getUserId(intValue) == i) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(intValue);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        for (String str2 : packagesForUid) {
                            arrayList.add(str2);
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public final int getPortForProfileName(String str) {
        int proxyPortForProfile = getKnoxVpnPacProcessor().getProxyPortForProfile(str);
        if (proxyPortForProfile == -1) {
            throw new IllegalArgumentException();
        }
        KnoxVpnPacProcessor knoxVpnPacProcessor = getKnoxVpnPacProcessor();
        getKnoxVpnHelperInstance().getListOfUid(str);
        knoxVpnPacProcessor.updatePermissionForAppsToaccessLocalHost(0, proxyPortForProfile, str);
        return proxyPortForProfile;
    }

    public final List getProfilesByDomain(String str) {
        ArrayList arrayList = new ArrayList();
        if (Binder.getCallingUid() != 1000) {
            return arrayList;
        }
        for (String str2 : this.mNotificationMap.keySet()) {
            ArrayList arrayList2 = (ArrayList) this.mNotificationMap.get(str2);
            int i = 0;
            while (true) {
                if (i >= arrayList2.size()) {
                    break;
                }
                if (((String) arrayList2.get(i)).equals(str)) {
                    arrayList.add(str2);
                    break;
                }
                i++;
            }
        }
        return arrayList;
    }

    public final String[] getProxyInfoForUid(int i) {
        String[] strArr = new String[2];
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.activateState == 1) {
                    if (vpnProfileInfo.getProxyInfo() == null) {
                        return strArr;
                    }
                    for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                        if (vpnPackageInfo.getUid() == i) {
                            strArr[0] = vpnProfileInfo.getProxyInfo().getHost();
                            strArr[1] = Integer.toString(vpnProfileInfo.getProxyInfo().getPort());
                            Log.d("KnoxVpnEngineService", "proxy information is requested by the uid belonging to per-app domain " + i);
                            return strArr;
                        }
                        if (vpnPackageInfo.getUid() == -2) {
                            int userId = UserHandle.getUserId(i);
                            KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                            String packageName = vpnPackageInfo.getPackageName();
                            knoxVpnHelperInstance.getClass();
                            if (userId == KnoxVpnHelper.getContainerIdFromPackageName(packageName)) {
                                Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
                                while (it.hasNext()) {
                                    if (i == ((Integer) it.next()).intValue()) {
                                        Log.d("KnoxVpnEngineService", "proxy information is requested by the uid belonging to exempt list in user domain " + i);
                                        return strArr;
                                    }
                                }
                                if (i == vpnProfileInfo.mVendorUid) {
                                    Log.d("KnoxVpnEngineService", "proxy information is requested by the uid belonging to vpn client " + i);
                                    return strArr;
                                }
                                strArr[0] = vpnProfileInfo.getProxyInfo().getHost();
                                strArr[1] = Integer.toString(vpnProfileInfo.getProxyInfo().getPort());
                                Log.d("KnoxVpnEngineService", "proxy information is requested by the uid belonging to user domain " + i);
                                return strArr;
                            }
                        }
                    }
                }
            }
        } catch (Exception unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Exception occured while trying to fetch the proxy information for the uid ", "KnoxVpnEngineService");
        }
        return strArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] getSharedUidPackges(int r14, java.lang.String[] r15) {
        /*
            r13 = this;
            int r0 = r15.length
            r1 = 0
            if (r0 <= 0) goto L7b
            java.lang.String r0 = "package"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            android.content.pm.IPackageManager r0 = android.content.pm.IPackageManager.Stub.asInterface(r0)
            if (r0 != 0) goto L12
            return r1
        L12:
            android.content.Context r13 = r13.mContext
            android.content.pm.PackageManager r13 = r13.getPackageManager()
            r2 = 0
            java.util.List r13 = r13.getInstalledPackagesAsUser(r2, r14)
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            int r4 = r15.length
            r5 = r2
        L24:
            if (r5 >= r4) goto L72
            r6 = r15[r5]
            if (r6 == 0) goto L69
            java.util.HashSet r7 = new java.util.HashSet
            r7.<init>()
            r7.add(r6)
            r8 = 0
            android.content.pm.ApplicationInfo r8 = r0.getApplicationInfo(r6, r8, r14)     // Catch: java.lang.Exception -> L61
            if (r8 != 0) goto L3b
            goto L6a
        L3b:
            java.util.Iterator r9 = r13.iterator()     // Catch: java.lang.Exception -> L61
        L3f:
            boolean r10 = r9.hasNext()     // Catch: java.lang.Exception -> L61
            if (r10 == 0) goto L6a
            java.lang.Object r10 = r9.next()     // Catch: java.lang.Exception -> L61
            android.content.pm.PackageInfo r10 = (android.content.pm.PackageInfo) r10     // Catch: java.lang.Exception -> L61
            android.content.pm.ApplicationInfo r11 = r10.applicationInfo     // Catch: java.lang.Exception -> L61
            int r11 = r11.uid     // Catch: java.lang.Exception -> L61
            int r12 = r8.uid     // Catch: java.lang.Exception -> L61
            if (r11 != r12) goto L3f
            java.lang.String r11 = r10.packageName     // Catch: java.lang.Exception -> L61
            boolean r11 = r11.equals(r6)     // Catch: java.lang.Exception -> L61
            if (r11 != 0) goto L3f
            java.lang.String r10 = r10.packageName     // Catch: java.lang.Exception -> L61
            r7.add(r10)     // Catch: java.lang.Exception -> L61
            goto L3f
        L61:
            r6 = move-exception
            java.lang.String r7 = "KnoxVpnEngineService"
            java.lang.String r8 = "_getSharedUidPackges: Exception"
            android.util.Log.e(r7, r8, r6)
        L69:
            r7 = r1
        L6a:
            if (r7 == 0) goto L6f
            r3.addAll(r7)
        L6f:
            int r5 = r5 + 1
            goto L24
        L72:
            java.lang.String[] r13 = new java.lang.String[r2]
            java.lang.Object[] r13 = r3.toArray(r13)
            java.lang.String[] r13 = (java.lang.String[]) r13
            return r13
        L7b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getSharedUidPackges(int, java.lang.String[]):java.lang.String[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007d A[Catch: Exception -> 0x0094, TryCatch #0 {Exception -> 0x0094, blocks: (B:7:0x003e, B:12:0x007d, B:14:0x0096, B:17:0x009f, B:19:0x00a4, B:21:0x00af, B:23:0x00b5, B:25:0x00c5, B:38:0x0070, B:41:0x0064, B:28:0x0047, B:31:0x004c, B:34:0x0057), top: B:6:0x003e, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0096 A[Catch: Exception -> 0x0094, TryCatch #0 {Exception -> 0x0094, blocks: (B:7:0x003e, B:12:0x007d, B:14:0x0096, B:17:0x009f, B:19:0x00a4, B:21:0x00af, B:23:0x00b5, B:25:0x00c5, B:38:0x0070, B:41:0x0064, B:28:0x0047, B:31:0x004c, B:34:0x0057), top: B:6:0x003e, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData getState(com.samsung.android.knox.net.vpn.KnoxVpnContext r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r0 = "knox vpn profile current state request failed: The error code is "
            com.samsung.android.knox.ContextInfo r1 = r6.checkCallingUidPermission(r7)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "getState API called for profileName = "
            r2.<init>(r3)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "KnoxVpnEngineService"
            android.util.Log.d(r3, r2)
            com.samsung.android.knox.net.vpn.EnterpriseResponseData r2 = new com.samsung.android.knox.net.vpn.EnterpriseResponseData
            r2.<init>()
            r4 = -1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r2.setData(r5)
            r5 = 1
            r2.setStatus(r5, r4)
            int r1 = r1.mCallerUid
            boolean r1 = updateIfNonLegacyUserAndCheckIfVendorAllowed(r7, r1)
            if (r1 != 0) goto L3e
            r6 = 140(0x8c, float:1.96E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r2.setData(r6)
            return r2
        L3e:
            com.android.server.enterprise.vpn.knoxvpn.KnoxVpnApiValidation r1 = r6.mKnoxVpnApiValidation     // Catch: java.lang.Exception -> L94
            r1.getClass()     // Catch: java.lang.Exception -> L94
            java.lang.String r4 = "KnoxVpnApiValidation"
            if (r8 == 0) goto L77
            java.lang.String r5 = r7.vendorName     // Catch: java.lang.Exception -> L63
            if (r5 != 0) goto L4c
            goto L77
        L4c:
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r1 = r1.vpnConfig     // Catch: java.lang.Exception -> L63
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r1 = r1.getProfileEntry(r8)     // Catch: java.lang.Exception -> L63
            if (r1 != 0) goto L57
            r7 = 108(0x6c, float:1.51E-43)
            goto L79
        L57:
            int r1 = r1.admin_id     // Catch: java.lang.Exception -> L63
            int r7 = r7.adminId     // Catch: java.lang.Exception -> L63
            if (r1 == r7) goto L60
            r7 = 112(0x70, float:1.57E-43)
            goto L79
        L60:
            r7 = 100
            goto L70
        L63:
            r7 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L94
            java.lang.String r5 = "Exception at getStateValidation API "
            r1.<init>(r5)     // Catch: java.lang.Exception -> L94
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r7, r1, r4)     // Catch: java.lang.Exception -> L94
            r7 = 101(0x65, float:1.42E-43)
        L70:
            java.lang.String r1 = "getStateValidation : validationResult value is "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r7, r1, r4)     // Catch: java.lang.Exception -> L94
            goto L79
        L77:
            r7 = 104(0x68, float:1.46E-43)
        L79:
            r1 = 100
            if (r7 == r1) goto L96
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Exception -> L94
            r2.setData(r6)     // Catch: java.lang.Exception -> L94
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L94
            r6.<init>(r0)     // Catch: java.lang.Exception -> L94
            r6.append(r7)     // Catch: java.lang.Exception -> L94
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L94
            android.util.Log.d(r3, r6)     // Catch: java.lang.Exception -> L94
            return r2
        L94:
            r6 = move-exception
            goto Ld4
        L96:
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r7 = r6.vpnConfig     // Catch: java.lang.Exception -> L94
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r7 = r7.getProfileEntry(r8)     // Catch: java.lang.Exception -> L94
            if (r7 != 0) goto L9f
            return r2
        L9f:
            int r7 = r7.activateState     // Catch: java.lang.Exception -> L94
            r0 = 0
            if (r7 != 0) goto Laf
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L94
            r2.setData(r6)     // Catch: java.lang.Exception -> L94
            r2.setStatus(r0, r0)     // Catch: java.lang.Exception -> L94
            return r2
        Laf:
            com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService r6 = r6.getBinderInterfaceForProfile(r8)     // Catch: java.lang.Exception -> L94
            if (r6 != 0) goto Lc5
            r6 = 110(0x6e, float:1.54E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L94
            r2.setData(r6)     // Catch: java.lang.Exception -> L94
            java.lang.String r6 = "knox vpn profile current state request failed: The error code is 110"
            android.util.Log.d(r3, r6)     // Catch: java.lang.Exception -> L94
            return r2
        Lc5:
            int r6 = r6.getState(r8)     // Catch: java.lang.Exception -> L94
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L94
            r2.setData(r6)     // Catch: java.lang.Exception -> L94
            r2.setStatus(r0, r0)     // Catch: java.lang.Exception -> L94
            goto Le3
        Ld4:
            boolean r7 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG
            if (r7 == 0) goto Le3
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "getting vpn state : Failure at "
            r7.<init>(r8)
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r6, r7, r3)
        Le3:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getState(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final int getUidPidEnabled(int i, String str) {
        int i2 = 0;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                if (DBG) {
                    Log.d("KnoxVpnEngineService", "The packageName stored in database is " + profileEntry.mVendorPkgName + "callerUid is " + i);
                }
                if (profileEntry.mVendorUid == i) {
                    if (profileEntry.routeType == 0) {
                        i2 = profileEntry.uidPidSearchEnabled;
                    } else if (profileEntry.routeType == 1) {
                        Log.d("KnoxVpnEngineService", "activated state of the profile " + profileEntry.activateState);
                        i2 = profileEntry.uidPidSearchEnabled;
                    }
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception = "), "KnoxVpnEngineService");
        }
        if (DBG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "getUidPidSearchEnabledForProfile: uidPidSearchEnabled value is ", "KnoxVpnEngineService");
        }
        return i2;
    }

    public final synchronized EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData((Object) null);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        try {
            if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                return enterpriseResponseData;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.e("KnoxVpnEngineService", "getting user certificate : VPN Service not started");
                return enterpriseResponseData;
            }
            enterpriseResponseData.setData(binderInterfaceForProfile.getUserCertificate(str));
            enterpriseResponseData.setStatus(0, 0);
            return enterpriseResponseData;
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "getting user certificate : Failure at " + Log.getStackTraceString(e));
            return null;
        }
    }

    public final int getVPNState(String str) {
        try {
            if (this.vpnConfig.getProfileEntry(str) == null) {
                return -1;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.e("KnoxVpnEngineService", "getVPNState : Interface null for profile : " + str);
                return -1;
            }
            int state = binderInterfaceForProfile.getState(str);
            Log.d("KnoxVpnEngineService", "State of vpn profile received from vpn vendor for profileName is = " + str + " state = " + state);
            return state;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("getVPNState : Failure at "), "KnoxVpnEngineService");
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002a A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int getVPNTransitionState(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "getVPNTransitionState : VpnState : "
            monitor-enter(r4)
            int r1 = r4.getVPNState(r5)     // Catch: java.lang.Throwable -> L46
            r2 = -1
            if (r1 >= 0) goto Ld
            monitor-exit(r4)
            return r2
        Ld:
            r3 = 5
            if (r3 >= r1) goto L12
            monitor-exit(r4)
            return r2
        L12:
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r3 = r4.vpnConfig     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L46
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r5 = r3.getProfileEntry(r5)     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L46
            if (r5 != 0) goto L1c
        L1a:
            r5 = r2
            goto L28
        L1c:
            int r5 = r5.activateState     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L46
            goto L28
        L1f:
            java.lang.String r5 = "KnoxVpnEngineService"
            java.lang.String r3 = "getActivate error"
            android.util.Log.e(r5, r3)     // Catch: java.lang.Throwable -> L46
            goto L1a
        L28:
            if (r5 >= 0) goto L2c
            monitor-exit(r4)
            return r2
        L2c:
            if (r5 != 0) goto L30
            int r1 = r1 + 100
        L30:
            boolean r5 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG     // Catch: java.lang.Throwable -> L46
            if (r5 == 0) goto L48
            java.lang.String r5 = "KnoxVpnEngineService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L46
            r2.append(r1)     // Catch: java.lang.Throwable -> L46
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L46
            android.util.Log.d(r5, r0)     // Catch: java.lang.Throwable -> L46
            goto L48
        L46:
            r5 = move-exception
            goto L4a
        L48:
            monitor-exit(r4)
            return r1
        L4a:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.getVPNTransitionState(java.lang.String):int");
    }

    public final String getVendorNameForProfile(String str) {
        String str2 = null;
        try {
            boolean z = DBG;
            if (z) {
                Log.d("KnoxVpnEngineService", "getVendorNameForProfile: profile name  " + str);
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                String str3 = profileEntry.mVendorPkgName;
                knoxVpnHelper.getClass();
                str2 = KnoxVpnHelper.getRegularPackageName(str3);
                if (z) {
                    Log.d("KnoxVpnEngineService", "getVendorNameForProfile: vendorName  " + str2);
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getVendorNameForProfile API "), "KnoxVpnEngineService");
        }
        return str2;
    }

    public final int getVirtualInterfaceType(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
            return profileEntry.mInterface_type;
        }
        return 0;
    }

    public final IKnoxVpnService getVpnInterface(String str) {
        if (str == null) {
            return null;
        }
        try {
            Object obj = this.vpnInterfaceMap.get(str);
            if (obj != NULL_OBJECT) {
                return (IKnoxVpnService) obj;
            }
            return null;
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception when retrieving Binder interface : "), "KnoxVpnEngineService");
            return null;
        }
    }

    public final IVpnManager getVpnManagerService() {
        this.mInjector.getClass();
        return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
    }

    public final synchronized EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) {
        int vpnModeOfOperationValidation;
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        try {
            vpnModeOfOperationValidation = this.mKnoxVpnApiValidation.getVpnModeOfOperationValidation(knoxVpnContext, str);
        } catch (Exception e) {
            if (DBG) {
                Log.e("KnoxVpnEngineService", "getting vpn mode : Failure at " + Log.getStackTraceString(e));
            }
        }
        if (vpnModeOfOperationValidation != 100) {
            enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperationValidation));
            Log.d("KnoxVpnEngineService", "getting the mode of operation for the profile has failed:The error code is " + vpnModeOfOperationValidation);
            return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
            enterpriseResponseData.setData(110);
            Log.d("KnoxVpnEngineService", "getting the mode of operation for the profile has failed: The error code is 110");
            return enterpriseResponseData;
        }
        int vpnModeOfOperation = binderInterfaceForProfile.getVpnModeOfOperation(str);
        Log.d("KnoxVpnEngineService", "getVpnModeOfOperation : profileName = " + str + " :currentMode = " + vpnModeOfOperation);
        enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperation));
        enterpriseResponseData.setStatus(0, 0);
        return enterpriseResponseData;
    }

    public final synchronized EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "profile info is going to be fetched for the proifle " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData((Object) null);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        try {
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "getVpnProfile exception result is " + Log.getStackTraceString(e));
        }
        if (this.mKnoxVpnApiValidation.getVpnProfileValidation(knoxVpnContext, str) == null) {
            Log.d("KnoxVpnEngineService", "getting vpn profile Info failed: Error occured while validating the profile");
            return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
            Log.d("KnoxVpnEngineService", "getting vpn profile Info failed: Error occured due to invalid interface");
            return enterpriseResponseData;
        }
        String connection = binderInterfaceForProfile.getConnection(str);
        if (DBG) {
            Log.d("KnoxVpnEngineService", "getVpnProfile: jsonProfile value is " + connection);
        }
        enterpriseResponseData.setData(connection);
        enterpriseResponseData.setStatus(0, 0);
        return enterpriseResponseData;
    }

    public final synchronized void handleActionPackageAdded$1(Bundle bundle) {
        int i;
        String string;
        int userId;
        boolean z;
        String personifiedName;
        boolean z2;
        String profileOwningTheUid;
        Log.d("KnoxVpnEngineService", "vpn handle : Handle MSG package add");
        try {
            i = bundle.getInt("uid");
            string = bundle.getString("package");
            userId = UserHandle.getUserId(i);
            z = bundle.getBoolean("new_install_or_update");
            this.mKnoxVpnHelper.getClass();
            personifiedName = KnoxVpnHelper.getPersonifiedName(userId, string);
            z2 = DBG;
            if (z2) {
                Log.d("KnoxVpnEngineService", "vpn handle : package added : package =  " + personifiedName + " : uid = " + i);
            }
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "vpn handle : pakcage add : Exception:" + Log.getStackTraceString(e));
        }
        if (personifiedName == null) {
            return;
        }
        if (this.vpnInterfaceMap.containsKey(personifiedName)) {
            if (z2) {
                Log.d("KnoxVpnEngineService", "vpn handle : package added : calling bind package =  ".concat(personifiedName));
            }
            bindKnoxVpnInterface(this.mKnoxVpnHelper.getAdminIdFromPackageName(personifiedName), personifiedName);
            return;
        }
        if (isProxyServicePackage(personifiedName)) {
            Log.d("KnoxVpnEngineService", "Knox vpn proxy support package has been added in user " + userId);
            if (z) {
                Log.d("KnoxVpnEngineService", "Knox vpn proxy support package has been updated in user " + userId);
                for (String str : this.vpnConfig.getProfileNames()) {
                    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                    if (profileEntry != null) {
                        Uri uri = profileEntry.mPacurl;
                        String str2 = profileEntry.mProxyServer;
                        String str3 = profileEntry.mInterfaceName;
                        int i2 = profileEntry.mNetId;
                        if (uri != Uri.EMPTY || str2 != null) {
                            if (str3 != null) {
                                Log.d("KnoxVpnEngineService", "Binding again to pac service after package update");
                                getKnoxVpnPacProcessor().bindProxyService(str, str3, uri, i2);
                            }
                        }
                    }
                }
            }
            return;
        }
        if (string.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
            Log.d("KnoxVpnEngineService", "Knox vpn proxy support package has been added on profile users " + userId);
            return;
        }
        if (string.equalsIgnoreCase("com.samsung.knox.vpn.tether.auth")) {
            KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
            int userId2 = UserHandle.getUserId(i);
            knoxVpnHelper.getClass();
            if (KnoxVpnHelper.checkIfPlatformSigned(userId2)) {
                if (this.mKnoxVpnTetherAuthentication.isTetherAuthSuccessful) {
                    Log.d("KnoxVpnTetherAuthentication", "usb tether auth application is installed and mutual authentication not needed");
                    return;
                }
                Iterator it = this.vpnConfig.vpnProfileInfoMap.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
                    if (vpnProfileInfo.isUsbTetheringAuthEnabled == 1 && UserHandle.getUserId(i) == vpnProfileInfo.personaId) {
                        String profileName = vpnProfileInfo.getProfileName();
                        this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(profileName, "com.samsung.knox.vpn.tether.auth", true);
                        Log.d("KnoxVpnTetherAuthentication", "usb tether auth application is installed, start bind to usb auth service");
                        this.mKnoxVpnTetherAuthentication.bindTetherAuthService(UserHandle.getUserId(i), profileName);
                        String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
                        String str4 = vpnProfileInfo.mInterfaceName;
                        int i3 = vpnProfileInfo.activateState;
                        if (interfaceNameForUsbtethering != null) {
                            if (str4 == null && i3 == 1) {
                                Log.d("KnoxVpnTetherAuthentication", "usb tether auth application is installed, adding blocking rules since vpn is not up");
                                this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                            } else if (str4 != null && i3 == 1) {
                                Log.d("KnoxVpnTetherAuthentication", "usb tether auth application is installed, start mutual authentication");
                                this.mFirewallHelper.removeRulesForDroppingTethePackets();
                                this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(vpnProfileInfo.personaId, interfaceNameForUsbtethering, this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
                            }
                        }
                    }
                }
            }
        }
        if (z) {
            Log.d("KnoxVpnEngineService", "vpn handle : package added: package being updated");
            int checkIfUidHasInternetPermission = this.mKnoxVpnHelper.checkIfUidHasInternetPermission(i);
            if (checkIfUidHasInternetPermission == 0) {
                String profileNameForPermissionUpdatedApp = this.mKnoxVpnHelper.getProfileNameForPermissionUpdatedApp(i);
                if (profileNameForPermissionUpdatedApp != null) {
                    Log.d("KnoxVpnEngineService", "The app is being upgraded with internet permission, adding to vpn profile " + profileNameForPermissionUpdatedApp);
                    VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(profileNameForPermissionUpdatedApp);
                    if (profileEntry2 != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(Integer.valueOf(i));
                        this.mFirewallHelper.addRulesForFilteredPackages(profileEntry2.mVendorPkgName, profileEntry2.mIpChainName, arrayList, this.mKnoxVpnHelper.getDefaultNetworkInterface(profileNameForPermissionUpdatedApp));
                        this.mKnoxVpnHelper.updateUidsToVpnUidRange(profileNameForPermissionUpdatedApp);
                    }
                    removePackagesFromPermissionCheckDb(i);
                    writePackageToDB(i, userId, profileNameForPermissionUpdatedApp, string);
                    refreshDomainInHashMap(profileNameForPermissionUpdatedApp);
                }
            } else if (checkIfUidHasInternetPermission == 1 && (profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(i)) != null) {
                Log.d("KnoxVpnEngineService", "The app is being downgraded without internet permission, removing from vpn profile " + profileOwningTheUid);
                removePackageListByUid(i, profileOwningTheUid, string);
                writePackagestoPermissionCheckDb(userId, i, profileOwningTheUid, string);
            }
            return;
        }
        if (this.mKnoxVpnHelper.isWideVpnExists(userId)) {
            Log.d("KnoxVpnEngineService", "No action needed for package added use-case, since user-wide vpn is configured ");
            KnoxVpnHelper knoxVpnHelper2 = this.mKnoxVpnHelper;
            knoxVpnHelper2.getClass();
            String profileOwningThePackage = knoxVpnHelper2.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(userId, "ADD_ALL_PACKAGES"));
            VpnProfileInfo profileEntry3 = this.vpnConfig.getProfileEntry(profileOwningThePackage);
            if (profileEntry3 != null && profileEntry3.activateState == 1) {
                if (!this.mKnoxVpnHelper.updateExemptedListToDatabase(i, personifiedName)) {
                    return;
                }
                if (!this.mKnoxVpnHelper.getuserIdForExemptedUids().contains(Integer.valueOf(userId))) {
                    this.mKnoxVpnHelper.getClass();
                    int uIDForPackage = KnoxVpnHelper.getUIDForPackage(userId, "com.android.providers.downloads");
                    KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
                    this.mKnoxVpnHelper.getClass();
                    knoxVpnFirewallHelper.addExemptRulesForDownloadManagerUid(uIDForPackage, KnoxVpnHelper.getActiveNetworkInterface());
                }
                profileEntry3.mExemptPackageList.add(Integer.valueOf(i));
                updateRulesToExemptUid(1, profileOwningThePackage, null, null, 1, i, 0);
                updateRulesToExemptUid(1, profileOwningThePackage, profileEntry3.mInterfaceName, profileEntry3.mDefaultInterface, 2, i, profileEntry3.mInterface_type);
                updateRulesToExemptUid(1, profileOwningThePackage, profileEntry3.mInterfaceName, profileEntry3.mDefaultInterface, 3, i, profileEntry3.mInterface_type);
                this.mKnoxVpnHelper.updateUidsToVpnUidRange(profileOwningThePackage);
            }
            return;
        }
        String profileOwningThePackage2 = this.mKnoxVpnHelper.getProfileOwningThePackage(personifiedName);
        if (profileOwningThePackage2 != null) {
            VpnProfileInfo profileEntry4 = this.vpnConfig.getProfileEntry(profileOwningThePackage2);
            String str5 = null;
            VpnPackageInfo vpnPackageInfo = profileEntry4 != null ? profileEntry4.getPackage(personifiedName) : null;
            int i4 = -1;
            if (vpnPackageInfo == null) {
                Log.d("KnoxVpnEngineService", "The application added might be an update to an existing one to the profile");
            } else {
                if (vpnPackageInfo.getUid() != -1) {
                    Log.d("KnoxVpnEngineService", "The application added is already added to a vpn profile, so cancelling further calls");
                    return;
                }
                Log.d("KnoxVpnEngineService", "The application added by admin but not present in the device till now has been installed");
            }
            String profileOwningTheUid2 = this.mKnoxVpnHelper.getProfileOwningTheUid(i);
            if (profileOwningTheUid2 != null && !profileOwningTheUid2.equalsIgnoreCase(profileOwningThePackage2)) {
                Log.d("KnoxVpnEngineService", "Multiple profile exists with same uid, so cancelling adding further attempts");
                return;
            }
            if (DBG) {
                Log.d("KnoxVpnEngineService", "vpn handle : package added : checking update package =  " + personifiedName);
            }
            if (updatePackageData(i, personifiedName)) {
                Log.d("KnoxVpnEngineService", "vpn handle : package add : Package found in DB with rule proceed with logic");
                this.mVpnStorageProvider.getClass();
                ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{personifiedName}, new String[]{"profileName", "packageUid"});
                try {
                    Log.d("KnoxVpnEngineService", "getting vpn object from database : Cursor not null");
                    Iterator it2 = dataByFields.iterator();
                    while (it2.hasNext()) {
                        ContentValues contentValues = (ContentValues) it2.next();
                        str5 = contentValues.getAsString("profileName");
                        i4 = contentValues.getAsInteger("packageUid").intValue();
                    }
                } catch (Exception e2) {
                    Log.e("KnoxVpnEngineService", "getting vpn object from database : Exception: " + Log.getStackTraceString(e2));
                }
                VpnProfileInfo profileEntry5 = this.vpnConfig.getProfileEntry(str5);
                if (profileEntry5 != null) {
                    profileEntry5.addPackageEntry(i4, userId, personifiedName);
                }
                if (profileEntry5.activateState == 1 && getVPNTransitionState(str5) == 4 && this.mKnoxVpnHelper.getpackageCountByUserId(userId, str5) == 1) {
                    refreshDomainInHashMap(str5);
                    updateNotification(userId, str5, true);
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Integer.valueOf(i4));
                this.mKnoxVpnHelper.updateUidsToVpnUidRange(str5);
                this.mFirewallHelper.addRulesForFilteredPackages(profileEntry5.mVendorPkgName, profileEntry5.mIpChainName, arrayList2, this.mKnoxVpnHelper.getDefaultNetworkInterface(str5));
                startVpnForPerApplication(str5, arrayList2, true);
                if (DBG) {
                    printProfileVpnMap();
                }
            }
        }
    }

    public final synchronized void handleActionPackageRemoved$1(Bundle bundle) {
        String interfaceNameForUsbtethering;
        Log.d("KnoxVpnEngineService", "handleActionPackageRemoved");
        int i = bundle.getInt("uid");
        String string = bundle.getString("package");
        int userId = UserHandle.getUserId(i);
        this.mKnoxVpnHelper.getClass();
        String personifiedName = KnoxVpnHelper.getPersonifiedName(userId, string);
        boolean z = bundle.getBoolean("new_install_or_update");
        Log.d("KnoxVpnEngineService", "handleActionPackageRemoved : packageName = " + personifiedName + " : replacing = " + z);
        if (this.vpnInterfaceMap.containsKey(personifiedName)) {
            if (z) {
                Log.d("KnoxVpnEngineService", "Package is being reinstalled. Skip remove profile");
                return;
            }
            Log.d("KnoxVpnEngineService", "handleActionPackageRemoved : packageName is Vpn Vendor");
            try {
                Log.d("KnoxVpnEngineService", "handleActionPackageRemoved : Getting profile list for vendor from DB");
                this.mVpnStorageProvider.getClass();
                Iterator it = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"vendorName"}, new String[]{personifiedName}, null).iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("profileName");
                    int intValue = contentValues.getAsInteger("activateState").intValue();
                    int intValue2 = contentValues.getAsInteger("adminUid").intValue();
                    String asString2 = contentValues.getAsString("vendorName");
                    if (intValue == 1) {
                        sendVpnConnectionFailIntent(intValue2, asString2, asString);
                    }
                    Log.d("KnoxVpnEngineService", "handleActionPackageRemoved : Remvoing profile = " + asString);
                    removeProfileFromKeyStore(i, asString, personifiedName);
                    removeProfileFromHashMapAndDB(asString);
                }
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "handleActionPackageRemoved : Failure at " + Log.getStackTraceString(e));
            }
            EnterpriseDeviceManager.getInstance(this.mContext);
        }
        if (z) {
            Log.d("KnoxVpnEngineService", "vpn handle : package removed: package being updated");
            return;
        }
        if (string.equalsIgnoreCase("com.samsung.knox.vpn.tether.auth")) {
            Iterator it2 = this.vpnConfig.vpnProfileInfoMap.values().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it2.next();
                if (vpnProfileInfo.isUsbTetheringAuthEnabled == 1 && UserHandle.getUserId(i) == vpnProfileInfo.personaId) {
                    Log.d("KnoxVpnTetherAuthentication", "unbind from usb tethering service after usb tether app is uninstalled");
                    try {
                        this.mKnoxVpnTetherAuthentication.unbindTetherAuthService();
                    } catch (RemoteException unused) {
                    }
                    if (vpnProfileInfo.activateState == 1 && (interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering()) != null) {
                        Log.d("KnoxVpnTetherAuthentication", "Adding tethering blocking rules after usb tether app is uninstalled");
                        this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                    }
                }
            }
        }
        String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(i);
        if (profileOwningTheUid != null) {
            removePackageListByUid(i, profileOwningTheUid, string);
        }
        String profileNameForExemptedUid = this.mKnoxVpnHelper.getProfileNameForExemptedUid(i);
        if (profileNameForExemptedUid != null) {
            removeExemptedUidDetailsAfterUninstall(i, profileNameForExemptedUid, personifiedName);
        }
        removePackagesFromPermissionCheckDb(i);
    }

    public final void handleChainingScenarioForStartConnection(String str) {
        try {
            int checkIfProfileHasChainingFeature = this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str);
            Log.d("KnoxVpnEngineService", "handleChainingScenarioForStartConnection: chaining_enabled value is " + checkIfProfileHasChainingFeature);
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                Log.d("KnoxVpnEngineService", "handleChainingScenarioForStartConnection: profile in outerProfile is " + profileEntry);
                return;
            }
            int i = profileEntry.personaId;
            Log.d("KnoxVpnEngineService", "handleChainingScenarioForStartConnection: userIdOfOuterProfile value is " + i);
            if (checkIfProfileHasChainingFeature == 0) {
                for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                    if (vpnProfileInfo.chainingEnabled == 1 && vpnProfileInfo.personaId == i) {
                        Log.d("KnoxVpnEngineService", "handleChainingScenarioForStartConnection: chained profile is going to be started");
                        String str2 = vpnProfileInfo.mProfileName;
                        try {
                            ChainingStateMachine chainingStateMachine = this.mChainingStateMachine;
                            if (chainingStateMachine != null) {
                                chainingStateMachine.interrupt();
                                this.mChainingStateMachine = null;
                            }
                            ChainingStateMachine chainingStateMachine2 = new ChainingStateMachine(str2);
                            this.mChainingStateMachine = chainingStateMachine2;
                            chainingStateMachine2.start();
                        } catch (Exception e) {
                            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at startChainedProfile API "), "KnoxVpnEngineService");
                        }
                    }
                }
            }
        } catch (Exception e2) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception at handleChainingScenarioForStartConnection API "), "KnoxVpnEngineService");
        }
    }

    public final boolean implementsVpnService(int i, String str) {
        boolean z = false;
        try {
            List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent(str + ".BIND_SERVICE_NEW"), 0, i);
            if (queryIntentServicesAsUser.size() > 0) {
                Iterator it = queryIntentServicesAsUser.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (((ResolveInfo) it.next()).serviceInfo.packageName.equalsIgnoreCase(str)) {
                        z = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at implementsVpnService "), "KnoxVpnEngineService");
        }
        if (DBG) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("implementsVpnService : supportsKnox value is ", "KnoxVpnEngineService", z);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0277 A[Catch: all -> 0x002c, Exception -> 0x0030, TryCatch #0 {Exception -> 0x0030, blocks: (B:7:0x000c, B:9:0x001f, B:11:0x0023, B:12:0x0034, B:14:0x003e, B:15:0x0046, B:16:0x004b, B:18:0x0051, B:21:0x00ae, B:23:0x0131, B:25:0x0177, B:27:0x0182, B:28:0x018e, B:31:0x0197, B:36:0x019b, B:37:0x019c, B:40:0x019d, B:43:0x01a7, B:48:0x01b3, B:49:0x01bb, B:51:0x0204, B:53:0x0212, B:55:0x0218, B:57:0x021c, B:58:0x021f, B:60:0x0226, B:62:0x022c, B:65:0x0233, B:67:0x026e, B:69:0x0277, B:70:0x0288, B:72:0x0282, B:73:0x023a, B:74:0x020b, B:80:0x0093), top: B:6:0x000c, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0282 A[Catch: all -> 0x002c, Exception -> 0x0030, TryCatch #0 {Exception -> 0x0030, blocks: (B:7:0x000c, B:9:0x001f, B:11:0x0023, B:12:0x0034, B:14:0x003e, B:15:0x0046, B:16:0x004b, B:18:0x0051, B:21:0x00ae, B:23:0x0131, B:25:0x0177, B:27:0x0182, B:28:0x018e, B:31:0x0197, B:36:0x019b, B:37:0x019c, B:40:0x019d, B:43:0x01a7, B:48:0x01b3, B:49:0x01bb, B:51:0x0204, B:53:0x0212, B:55:0x0218, B:57:0x021c, B:58:0x021f, B:60:0x0226, B:62:0x022c, B:65:0x0233, B:67:0x026e, B:69:0x0277, B:70:0x0288, B:72:0x0282, B:73:0x023a, B:74:0x020b, B:80:0x0093), top: B:6:0x000c, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void initializeHashtable() {
        /*
            Method dump skipped, instructions count: 1055
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.initializeHashtable():void");
    }

    public final boolean isNetworkConnected() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (getVpnManagerService().getActiveDefaultNetwork() == null) {
                Log.d("KnoxVpnEngineService", "check network connection : returns false");
                return false;
            }
            Log.d("KnoxVpnEngineService", "check network connection : returns true");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Exception e) {
            Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isProxyConfiguredForKnoxVpn(int i) {
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.mProxyServer != null || vpnProfileInfo.mPacurl != Uri.EMPTY) {
                    int i2 = vpnProfileInfo.activateState;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                                if (vpnPackageInfo.getUid() == i) {
                                    return true;
                                }
                                if (vpnPackageInfo.getUid() == -2) {
                                    if (i == vpnProfileInfo.mVendorUid) {
                                        Log.d("KnoxVpnEngineService", "knox vpn proxy setting skipping for vendor entry " + i);
                                        return false;
                                    }
                                    int userId = UserHandle.getUserId(i);
                                    KnoxVpnHelper knoxVpnHelperInstance = getKnoxVpnHelperInstance();
                                    String packageName = vpnPackageInfo.getPackageName();
                                    knoxVpnHelperInstance.getClass();
                                    if (userId == KnoxVpnHelper.getContainerIdFromPackageName(packageName)) {
                                        Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
                                        while (it.hasNext()) {
                                            if (i == ((Integer) it.next()).intValue()) {
                                                Log.d("KnoxVpnEngineService", "knox vpn proxy setting skipping for exempted entry " + i);
                                                return false;
                                            }
                                        }
                                        return true;
                                    }
                                }
                            }
                        } else if (DBG) {
                            Log.d("KnoxVpnEngineService", "knox vpn proxy settings is being queried by CS for unknown state vpn profile " + vpnProfileInfo.mProfileName);
                        }
                    } else if (DBG) {
                        Log.d("KnoxVpnEngineService", "knox vpn proxy settings is being queried by CS for the de-activated vpn profile " + vpnProfileInfo.mProfileName);
                    }
                }
            }
        } catch (Exception unused) {
            if (DBG) {
                Log.e("KnoxVpnEngineService", "Exception occured while retrieving the profile info object, might be that the proxy info has not been configured yet ");
            }
        }
        return false;
    }

    public final boolean isProxyServicePackage(String str) {
        this.mKnoxVpnHelper.getClass();
        return str.equalsIgnoreCase(KnoxVpnHelper.getPersonifiedName(0, "com.knox.vpn.proxyhandler"));
    }

    public final synchronized int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
            return 140;
        }
        int isUsbTetheringOverVpnEnabledValidation = this.mKnoxVpnApiValidation.isUsbTetheringOverVpnEnabledValidation(knoxVpnContext, str);
        if (isUsbTetheringOverVpnEnabledValidation != 100) {
            return isUsbTetheringOverVpnEnabledValidation;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        try {
            i = profileEntry.mUsbTethering;
        } catch (Exception unused) {
            i = 101;
        }
        return i;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
        Log.d("KnoxVpnEngineService", "[onAdminAdded]");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        Log.d("KnoxVpnEngineService", "[onAdminRemoved]");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        Log.d("KnoxVpnEngineService", "[onPreAdminRemoval]");
        if (DBG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Admin has VPN Permission : Pre admin remove ", "KnoxVpnEngineService");
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("uid", i);
            sendMessageToHandler$1(19, bundle);
        } catch (Exception unused) {
        }
    }

    public final void printProfileVpnMap() {
        if (DBG) {
            Log.v("KnoxVpnEngineService", "********************Printing profile map ********************");
            try {
                Log.v("KnoxVpnEngineService", "No of profiles: " + this.vpnConfig.vpnProfileInfoMap.size());
                for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                    if (vpnProfileInfo != null) {
                        String str = vpnProfileInfo.mProfileName;
                        String str2 = vpnProfileInfo.mInterfaceName;
                        int vPNTransitionState = getVPNTransitionState(str);
                        Log.v("KnoxVpnEngineService", "{ProfileName = " + str + ": [");
                        Log.v("KnoxVpnEngineService", "iface:" + str2 + " ; personaId: " + vpnProfileInfo.personaId + " ; adminId: " + vpnProfileInfo.admin_id);
                        StringBuilder sb = new StringBuilder();
                        sb.append("state: ");
                        sb.append(vPNTransitionState);
                        Log.v("KnoxVpnEngineService", sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("activated:");
                        sb2.append(vpnProfileInfo.activateState == 0 ? "false " : "true ");
                        Log.v("KnoxVpnEngineService", sb2.toString());
                        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                            Log.v("KnoxVpnEngineService", "{ uid:" + vpnPackageInfo.getUid() + ", packageName:" + vpnPackageInfo.getPackageName() + ", profileName:" + str + "}},");
                        }
                    } else {
                        Log.v("KnoxVpnEngineService", "VpnProfileInfo contains NULL object.");
                    }
                    Log.v("KnoxVpnEngineService", "]},");
                }
                Log.v("KnoxVpnEngineService", "] ");
            } catch (Exception e) {
                Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
            }
            Log.v("KnoxVpnEngineService", "-----------------Printing profile map --------------------------");
        }
    }

    public final void refreshDomainInHashMap(String str) {
        ArrayList arrayList;
        this.mVpnStorageProvider.getClass();
        if (KnoxVpnStorageProvider.mEDM != null) {
            arrayList = new ArrayList();
            Cursor query = KnoxVpnStorageProvider.mEDM.mEdmDbHelper.getReadableDatabase().query("VpnPackageInfo", new String[]{"packageCid", "packageUid"}, "profileName=?", new String[]{str}, null, null, null);
            if (query != null) {
                Log.d("KnoxVpnStorageProvider", "getDomainsByProfileName : cursor.size : " + query.getCount());
                try {
                    query.moveToFirst();
                    if (query.getCount() > 0) {
                        do {
                            int i = query.getInt(0);
                            Log.d("KnoxVpnStorageProvider", "getDomainsByProfileName : cid : " + i);
                            int i2 = query.getInt(1);
                            Log.d("KnoxVpnStorageProvider", "getDomainsByProfileName : uid : " + i2);
                            if (i2 != -1 && i >= 0) {
                                arrayList.add(String.valueOf(i));
                            }
                        } while (query.moveToNext());
                    }
                    query.close();
                } finally {
                    query.close();
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.mNotificationMap.put(str, arrayList);
        }
        if (DBG) {
            Log.v("KnoxVpnEngineService", "#################### Printing domain map ####################");
            try {
                Log.v("KnoxVpnEngineService", "Domain Count : " + this.mNotificationMap.size() + " [ ");
                for (String str2 : this.mNotificationMap.keySet()) {
                    ArrayList arrayList2 = (ArrayList) this.mNotificationMap.get(str2);
                    String str3 = "";
                    String str4 = "";
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        str4 = str4 + "," + ((String) arrayList2.get(i3));
                    }
                    Log.v("KnoxVpnEngineService", "{ProfileName = " + str2 + ": [");
                    StringBuilder sb = new StringBuilder();
                    sb.append("    domains : ");
                    if (!str4.equals("")) {
                        str3 = str4.substring(1);
                    }
                    sb.append(str3);
                    Log.v("KnoxVpnEngineService", sb.toString());
                    Log.v("KnoxVpnEngineService", "]},");
                }
                Log.v("KnoxVpnEngineService", "] ");
            } catch (Exception e) {
                Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
            }
            Log.v("KnoxVpnEngineService", "#################### Printing domain map ####################");
        }
    }

    public final void refreshFlagValueInHashMap(int i) {
        try {
            String[] strArr = {String.valueOf(i)};
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnNotificationFlagTable", new String[]{"notificationUserId"}, strArr, null);
            int i2 = 1;
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                int i3 = 1;
                while (it.hasNext() && (i3 = ((ContentValues) it.next()).getAsInteger("dismissFlag").intValue()) != 1) {
                }
                i2 = i3;
            }
            this.notificationFlagState.put(Integer.valueOf(i), Integer.valueOf(i2));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception when updating Notification Map:"), "KnoxVpnEngineService");
        }
    }

    public final void removeAddAllPackageInfofromDatabase(int i, String str) {
        int i2 = -1;
        try {
            this.mKnoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES");
            if (DBG) {
                Log.d("KnoxVpnEngineService", "removeAddAllPackageInfofromDatabase: removePackage value is " + personifiedName);
            }
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.deleteDataByFields("VpnPackageInfo", new String[]{"packageName", "profileName"}, new String[]{personifiedName, str})) {
                i2 = 0;
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removeAddAllPackageInfofromDatabase API "), "KnoxVpnEngineService");
        }
        if (DBG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "removeAddAllPackageInfofromDatabase: status value is ", "KnoxVpnEngineService");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ce, code lost:
    
        if (r8.personaId != r14) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x010b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.vpn.EnterpriseResponseData removeAllContainerPackagesFromVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext r13, int r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.removeAllContainerPackagesFromVpn(com.samsung.android.knox.net.vpn.KnoxVpnContext, int, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:100:0x02c4
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final synchronized com.samsung.android.knox.net.vpn.EnterpriseResponseData removeAllPackages(com.samsung.android.knox.net.vpn.KnoxVpnContext r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 867
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.removeAllPackages(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final synchronized EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) {
        try {
            ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
            if (DBG) {
                Log.d("KnoxVpnEngineService", "removeAllPackagesFromVpn: vpnContext.personaId value is " + knoxVpnContext.personaId + "profileName value is " + str);
            }
            EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
            enterpriseResponseData.setData(-1);
            enterpriseResponseData.setStatus(1, -1);
            if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
                enterpriseResponseData.setData(140);
                return enterpriseResponseData;
            }
            int removeAllPackagesFromVpnValidation = this.mKnoxVpnApiValidation.removeAllPackagesFromVpnValidation(knoxVpnContext, str);
            if (removeAllPackagesFromVpnValidation != 100) {
                enterpriseResponseData.setData(Integer.valueOf(removeAllPackagesFromVpnValidation));
                Log.d("KnoxVpnEngineService", "removing all the packages from vpn failed: The error code is " + removeAllPackagesFromVpnValidation);
                return enterpriseResponseData;
            }
            EnterpriseResponseData removeAllPackages = removeAllPackages(knoxVpnContext, str);
            try {
                if (((Integer) removeAllPackages.getData()).intValue() == 0) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeAllPackagesFromVpn");
                    this.mData = knoxAnalyticsData;
                    setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                    int i = knoxVpnContext.personaId;
                    if (i != 0) {
                        this.mData.setProperty("cid", i);
                    }
                    KnoxAnalytics.log(this.mData);
                }
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
            }
            return removeAllPackages;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
        String str2;
        int i2;
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "removeContainerPackagesFromVpn : containerId value is " + i + " profileName value is " + str);
        this.mKnoxVpnHelper.getClass();
        String personifiedName = KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES");
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                enterpriseResponseData.setData(108);
                Log.d("KnoxVpnEngineService", "Error while removing the container packages from vpn: The error code is 108");
                return enterpriseResponseData;
            }
            if (profileEntry.activateState == 1 && this.mKnoxVpnHelper.isWideVpnExists(i)) {
                Log.d("KnoxVpnEngineService", "removeContainerPackagesFromVpn: removing previously added rules before updating");
                this.mFirewallHelper.removeMiscRulesRange(i, getVirtualInterfaceType(str), profileEntry.mInterfaceName);
            }
            int removeContainerPackagesFromVpnValidation = this.mKnoxVpnApiValidation.removeContainerPackagesFromVpnValidation(knoxVpnContext, i, strArr, str);
            if (removeContainerPackagesFromVpnValidation != 100) {
                enterpriseResponseData.setData(Integer.valueOf(removeContainerPackagesFromVpnValidation));
                Log.d("KnoxVpnEngineService", "Error occured while removing the container packages from vpn: The error code is " + removeContainerPackagesFromVpnValidation);
                if (removeContainerPackagesFromVpnValidation == 113) {
                    enterpriseResponseData.setStatus(1, 11);
                }
                return enterpriseResponseData;
            }
            if (profileEntry.activateState == 1 && this.mKnoxVpnHelper.isWideVpnExists(i)) {
                Log.d("KnoxVpnEngineService", "removeContainerPackagesFromVpn: adding rules for newly added rules");
                this.mFirewallHelper.addMiscRulesRange(i, getVirtualInterfaceType(str), profileEntry.mInterfaceName);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int i3 = 0;
            for (int length = strArr.length; i3 < length; length = i2) {
                String str3 = strArr[i3];
                if (!str3.equalsIgnoreCase(personifiedName)) {
                    this.mKnoxVpnHelper.getClass();
                    String personifiedName2 = KnoxVpnHelper.getPersonifiedName(i, str3);
                    ArrayList arrayList3 = null;
                    String[] strArr2 = null;
                    if (removePackagesFromDatabase(str, personifiedName2)) {
                        str2 = personifiedName;
                        i2 = length;
                    } else {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            strArr2 = getSharedUidPackges(i, new String[]{str3});
                        } catch (Exception unused) {
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        String[] strArr3 = strArr2;
                        if (strArr3 != null && strArr3.length > 0) {
                            ArrayList arrayList4 = new ArrayList();
                            str2 = personifiedName;
                            int length2 = strArr3.length;
                            i2 = length;
                            int i4 = 0;
                            while (i4 < length2) {
                                int i5 = length2;
                                String str4 = strArr3[i4];
                                String[] strArr4 = strArr3;
                                this.mKnoxVpnHelper.getClass();
                                String personifiedName3 = KnoxVpnHelper.getPersonifiedName(i, str4);
                                if (removePackagesFromDatabase(str, personifiedName3)) {
                                    arrayList4.add(personifiedName3);
                                }
                                i4++;
                                length2 = i5;
                                strArr3 = strArr4;
                            }
                            arrayList3 = arrayList4;
                        }
                    }
                    if (arrayList3 != null && arrayList3.size() > 0) {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            String str5 = (String) it.next();
                            VpnPackageInfo vpnPackageInfo = profileEntry.getPackage(str5);
                            if (vpnPackageInfo != null && vpnPackageInfo.getCid() == i) {
                                arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
                                if (profileEntry.activateState == 0) {
                                    profileEntry.removePackageEntry(str5);
                                } else {
                                    int uid = vpnPackageInfo.getUid();
                                    arrayList.add(Integer.valueOf(uid));
                                    removePackagesFromPermissionCheckDb(uid);
                                    unsetDnsSystemProperty(uid, uid, str);
                                    getKnoxVpnHelperInstance().getClass();
                                    updateProxyRules(2, str, KnoxVpnHelper.updateProxyList(uid, false));
                                    profileEntry.removePackageEntry(str5);
                                }
                            }
                        }
                        i3++;
                        personifiedName = str2;
                    }
                    VpnPackageInfo vpnPackageInfo2 = profileEntry.getPackage(personifiedName2);
                    if (vpnPackageInfo2 != null && vpnPackageInfo2.getCid() == i) {
                        arrayList2.add(Integer.valueOf(vpnPackageInfo2.getUid()));
                        if (profileEntry.activateState == 0) {
                            profileEntry.removePackageEntry(str3);
                        } else {
                            int uid2 = vpnPackageInfo2.getUid();
                            arrayList.add(Integer.valueOf(uid2));
                            removePackagesFromPermissionCheckDb(uid2);
                            unsetDnsSystemProperty(uid2, uid2, str);
                            getKnoxVpnHelperInstance().getClass();
                            updateProxyRules(2, str, KnoxVpnHelper.updateProxyList(uid2, false));
                            profileEntry.removePackageEntry(personifiedName2);
                        }
                    }
                    i3++;
                    personifiedName = str2;
                }
                str2 = personifiedName;
                i2 = length;
                i3++;
                personifiedName = str2;
            }
            this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
            this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, arrayList);
            this.mFirewallHelper.removeRulesForFilteredPackages(profileEntry.mVendorPkgName, profileEntry.mIpChainName, arrayList2);
            refreshDomainInHashMap(str);
            updateNotification(i, str, false);
            int size = profileEntry.mPackageMap.size();
            boolean z = profileEntry.vpnConnectionType == 1 && !this.mProcessManager.processRunCheck(profileEntry);
            if ((size <= 0 || z) && profileEntry.activateState == 1) {
                try {
                    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                    if (binderInterfaceForProfile != null) {
                        int stopConnection = binderInterfaceForProfile.stopConnection(str);
                        if (stopConnection != 0) {
                            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred trying to stop vpn connection from profile " + str, knoxVpnContext.personaId);
                        } else {
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Connection with vpn vendor service stopped for profile " + str, knoxVpnContext.personaId);
                        }
                        Log.d("KnoxVpnEngineService", "stopping the vpn connection status for on-demand configuration after removing all the container packages " + stopConnection);
                    }
                } catch (Exception e) {
                    AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception stopping connection for profile " + str + " after removing all container packages", knoxVpnContext.personaId);
                    StringBuilder sb = new StringBuilder();
                    sb.append("stopping the vpn connection failed for on-demand configuration after removing all the container packages ");
                    sb.append(Log.getStackTraceString(e));
                    Log.d("KnoxVpnEngineService", sb.toString());
                }
            }
            Log.d("KnoxVpnEngineService", "removing all container packages from vpn is a success: The error code is 0");
            enterpriseResponseData.setData(0);
            enterpriseResponseData.setStatus(0, 0);
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Success while removing all the packages from vpn for profile " + str, knoxVpnContext.personaId);
            try {
                if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeContainerPackagesFromVpn");
                    this.mData = knoxAnalyticsData;
                    setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                    Arrays.sort(strArr);
                    StringBuffer stringBuffer = new StringBuffer(strArr[0]);
                    for (int i6 = 1; i6 < strArr.length; i6++) {
                        stringBuffer.append("," + strArr[i6]);
                    }
                    this.mData.setProperty("pkgLst", stringBuffer.toString());
                    this.mData.setProperty("cid", i);
                    KnoxAnalytics.log(this.mData);
                }
            } catch (Exception e2) {
                Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
            }
            return enterpriseResponseData;
        } catch (Exception e3) {
            Log.d("KnoxVpnEngineService", "Error occured while removing all container packages from vpn: exception occured: The error code is -1" + Log.getStackTraceString(e3));
            enterpriseResponseData.setData(-1);
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing all container packages from vpn for profile " + str, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
    }

    public final void removeExemptRulesForUid(int i, String str) {
        Log.d("KnoxVpnEngineService", "removeExemptRulesForUid : vendor = " + i + " for profile " + str);
        if (getChainingEnabledForProfile(i) != 1) {
            Log.d("KnoxVpnEngineService", "removeExemptRulesForUid : vendorUid = " + i);
            if (UserHandle.getAppId(i) != 1000) {
                this.mFirewallHelper.removeExemptRulesForUid(i);
                return;
            }
            KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
            knoxVpnFirewallHelper.getClass();
            KnoxVpnFirewallHelper.applyBlockingRulesForDns(1016, 1016, 4);
            KnoxVpnFirewallHelper.IpRestoreActionType ipRestoreActionType = KnoxVpnFirewallHelper.IpRestoreActionType.DELETE;
            knoxVpnFirewallHelper.insertRule(false, "*mangle", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_EXEMPT", " -m owner --gid-owner 1016", "ACCEPT", "", ipRestoreActionType), 46);
            knoxVpnFirewallHelper.insertRule(false, "*mangle", null, new KnoxVpnFirewallHelper.IpRestoreParam("knox_vpn_EXEMPT", " -m owner --uid-owner 1016", "ACCEPT", "", ipRestoreActionType), 46);
        }
    }

    public final void removeExemptedUidDetailsAfterUninstall(int i, String str, String str2) {
        String str3;
        String str4;
        int i2 = i;
        String str5 = "KnoxVpnEngineService";
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        int userId = UserHandle.getUserId(i);
        try {
            try {
                if (this.mContext.getPackageManager().getPackagesForUid(i2) == null) {
                    try {
                        Iterator it = profileEntry.mExemptPackageList.iterator();
                        while (it.hasNext()) {
                            Integer num = (Integer) it.next();
                            int intValue = num.intValue();
                            if (intValue == i2) {
                                boolean updateExemptedListToDatabase = this.mKnoxVpnHelper.updateExemptedListToDatabase(-1, str2);
                                Log.d(str5, "removeExemptedUidDetailsAfterUninstall: removing from exempeted list " + updateExemptedListToDatabase);
                                if (updateExemptedListToDatabase) {
                                    str4 = str5;
                                    updateRulesToExemptUid(0, str, null, null, 1, intValue, 0);
                                    updateRulesToExemptUid(0, str, profileEntry.mInterfaceName, profileEntry.mDefaultInterface, 2, intValue, profileEntry.mInterface_type);
                                    updateRulesToExemptUid(0, str, profileEntry.mInterfaceName, profileEntry.mDefaultInterface, 3, intValue, profileEntry.mInterface_type);
                                    it.remove();
                                    AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", String.format("Success removing uid = %d from exempted list after uninstallation for profile %s", num, str), userId);
                                    str5 = str4;
                                }
                            }
                            str4 = str5;
                            str5 = str4;
                        }
                        str3 = str5;
                    } catch (Exception e) {
                        e = e;
                        String str6 = str5;
                        str3 = str6;
                        Log.e(str3, "removePackagesAfterUninstall exception " + Log.getStackTraceString(e));
                        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing packages exempted from vpn after uninstallation for profile ".concat(str), userId);
                        return;
                    }
                } else {
                    String str7 = "KnoxVpnEngineService";
                    Iterator it2 = profileEntry.mExemptPackageList.iterator();
                    while (it2.hasNext()) {
                        Integer num2 = (Integer) it2.next();
                        int intValue2 = num2.intValue();
                        if (intValue2 == i2) {
                            boolean removeExemptedListToDatabase = this.mKnoxVpnHelper.removeExemptedListToDatabase(i2);
                            String str8 = str7;
                            try {
                                Log.d(str8, "removeExemptedUidDetailsAfterUninstall: removing from exempeted list " + removeExemptedListToDatabase);
                                if (removeExemptedListToDatabase) {
                                    str3 = str8;
                                    try {
                                        updateRulesToExemptUid(0, str, null, null, 1, intValue2, 0);
                                        updateRulesToExemptUid(0, str, profileEntry.mInterfaceName, profileEntry.mDefaultInterface, 2, intValue2, profileEntry.mInterface_type);
                                        updateRulesToExemptUid(0, str, profileEntry.mInterfaceName, profileEntry.mDefaultInterface, 3, intValue2, profileEntry.mInterface_type);
                                        it2.remove();
                                        AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", String.format("Success removing uid = %d from exempted list after uninstallation for profile %s", num2, str), userId);
                                    } catch (Exception e2) {
                                        e = e2;
                                        Log.e(str3, "removePackagesAfterUninstall exception " + Log.getStackTraceString(e));
                                        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing packages exempted from vpn after uninstallation for profile ".concat(str), userId);
                                        return;
                                    }
                                } else {
                                    str3 = str8;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                str3 = str8;
                                Log.e(str3, "removePackagesAfterUninstall exception " + Log.getStackTraceString(e));
                                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing packages exempted from vpn after uninstallation for profile ".concat(str), userId);
                                return;
                            }
                        } else {
                            str3 = str7;
                        }
                        i2 = i;
                        str7 = str3;
                    }
                    str3 = str7;
                }
                if (this.mKnoxVpnHelper.getuserIdForExemptedUids().contains(Integer.valueOf(userId))) {
                    return;
                }
                this.mKnoxVpnHelper.getClass();
                this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(KnoxVpnHelper.getUIDForPackage(userId, "com.android.providers.downloads"));
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Exception e5) {
            e = e5;
            str3 = "KnoxVpnEngineService";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0083 A[Catch: Exception -> 0x0046, TryCatch #1 {Exception -> 0x0046, blocks: (B:7:0x001c, B:9:0x0021, B:11:0x002e, B:13:0x003f, B:16:0x006a, B:18:0x0083, B:20:0x0090, B:21:0x009f, B:22:0x0098, B:23:0x00a4, B:24:0x00aa, B:26:0x00b0, B:28:0x00eb, B:30:0x0145, B:31:0x0158, B:33:0x015c, B:34:0x0171, B:37:0x017e, B:38:0x0183, B:40:0x0186, B:41:0x019b, B:43:0x01a9, B:44:0x01e5, B:46:0x01eb, B:48:0x01fd, B:51:0x0208, B:52:0x0217, B:53:0x0231, B:55:0x0237, B:57:0x024b, B:59:0x0269, B:61:0x0277, B:64:0x0287, B:65:0x0290, B:69:0x028c, B:70:0x028f, B:75:0x0298, B:76:0x029b, B:80:0x0190, B:82:0x019a, B:85:0x029d, B:86:0x029e, B:89:0x02a0, B:90:0x02a3, B:91:0x0049, B:97:0x0026, B:63:0x0280, B:78:0x0188, B:81:0x0192, B:50:0x0201, B:72:0x020f, B:36:0x0175), top: B:6:0x001c, inners: #2, #3, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b0 A[Catch: Exception -> 0x0046, LOOP:0: B:24:0x00aa->B:26:0x00b0, LOOP_END, TryCatch #1 {Exception -> 0x0046, blocks: (B:7:0x001c, B:9:0x0021, B:11:0x002e, B:13:0x003f, B:16:0x006a, B:18:0x0083, B:20:0x0090, B:21:0x009f, B:22:0x0098, B:23:0x00a4, B:24:0x00aa, B:26:0x00b0, B:28:0x00eb, B:30:0x0145, B:31:0x0158, B:33:0x015c, B:34:0x0171, B:37:0x017e, B:38:0x0183, B:40:0x0186, B:41:0x019b, B:43:0x01a9, B:44:0x01e5, B:46:0x01eb, B:48:0x01fd, B:51:0x0208, B:52:0x0217, B:53:0x0231, B:55:0x0237, B:57:0x024b, B:59:0x0269, B:61:0x0277, B:64:0x0287, B:65:0x0290, B:69:0x028c, B:70:0x028f, B:75:0x0298, B:76:0x029b, B:80:0x0190, B:82:0x019a, B:85:0x029d, B:86:0x029e, B:89:0x02a0, B:90:0x02a3, B:91:0x0049, B:97:0x0026, B:63:0x0280, B:78:0x0188, B:81:0x0192, B:50:0x0201, B:72:0x020f, B:36:0x0175), top: B:6:0x001c, inners: #2, #3, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0145 A[Catch: Exception -> 0x0046, TryCatch #1 {Exception -> 0x0046, blocks: (B:7:0x001c, B:9:0x0021, B:11:0x002e, B:13:0x003f, B:16:0x006a, B:18:0x0083, B:20:0x0090, B:21:0x009f, B:22:0x0098, B:23:0x00a4, B:24:0x00aa, B:26:0x00b0, B:28:0x00eb, B:30:0x0145, B:31:0x0158, B:33:0x015c, B:34:0x0171, B:37:0x017e, B:38:0x0183, B:40:0x0186, B:41:0x019b, B:43:0x01a9, B:44:0x01e5, B:46:0x01eb, B:48:0x01fd, B:51:0x0208, B:52:0x0217, B:53:0x0231, B:55:0x0237, B:57:0x024b, B:59:0x0269, B:61:0x0277, B:64:0x0287, B:65:0x0290, B:69:0x028c, B:70:0x028f, B:75:0x0298, B:76:0x029b, B:80:0x0190, B:82:0x019a, B:85:0x029d, B:86:0x029e, B:89:0x02a0, B:90:0x02a3, B:91:0x0049, B:97:0x0026, B:63:0x0280, B:78:0x0188, B:81:0x0192, B:50:0x0201, B:72:0x020f, B:36:0x0175), top: B:6:0x001c, inners: #2, #3, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x015c A[Catch: Exception -> 0x0046, TryCatch #1 {Exception -> 0x0046, blocks: (B:7:0x001c, B:9:0x0021, B:11:0x002e, B:13:0x003f, B:16:0x006a, B:18:0x0083, B:20:0x0090, B:21:0x009f, B:22:0x0098, B:23:0x00a4, B:24:0x00aa, B:26:0x00b0, B:28:0x00eb, B:30:0x0145, B:31:0x0158, B:33:0x015c, B:34:0x0171, B:37:0x017e, B:38:0x0183, B:40:0x0186, B:41:0x019b, B:43:0x01a9, B:44:0x01e5, B:46:0x01eb, B:48:0x01fd, B:51:0x0208, B:52:0x0217, B:53:0x0231, B:55:0x0237, B:57:0x024b, B:59:0x0269, B:61:0x0277, B:64:0x0287, B:65:0x0290, B:69:0x028c, B:70:0x028f, B:75:0x0298, B:76:0x029b, B:80:0x0190, B:82:0x019a, B:85:0x029d, B:86:0x029e, B:89:0x02a0, B:90:0x02a3, B:91:0x0049, B:97:0x0026, B:63:0x0280, B:78:0x0188, B:81:0x0192, B:50:0x0201, B:72:0x020f, B:36:0x0175), top: B:6:0x001c, inners: #2, #3, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0184  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeFromHashMapByProfileName(java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.removeFromHashMapByProfileName(java.lang.String):void");
    }

    public final void removeMiscRulesForProfile(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("removeMiscRulesForProfile : profileName =  ", str, "KnoxVpnEngineService");
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        ArrayList arrayList = new ArrayList();
        if (profileEntry != null) {
            String str2 = profileEntry.mInterfaceName;
            for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                    KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                    String packageName = vpnPackageInfo.getPackageName();
                    knoxVpnHelper.getClass();
                    this.mFirewallHelper.removeMiscRulesRange(KnoxVpnHelper.getContainerIdFromPackageName(packageName), getVirtualInterfaceType(str), str2);
                } else {
                    arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, arrayList);
        }
    }

    public final void removePackageListByUid(int i, String str, String str2) {
        boolean z;
        int i2;
        int i3;
        String[] strArr;
        String str3;
        boolean z2;
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        int userId = UserHandle.getUserId(i);
        ArrayList arrayList = new ArrayList();
        String str4 = profileEntry.mIpChainName;
        try {
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            int i4 = 2;
            String str5 = " removed from vpn for profile ";
            boolean z3 = false;
            if (packagesForUid == null) {
                this.mKnoxVpnHelper.getClass();
                String personifiedName = KnoxVpnHelper.getPersonifiedName(userId, str2);
                if (profileEntry.getPackage(personifiedName) == null) {
                    return;
                }
                Log.d("KnoxVpnEngineService", "Inside deleteUIDFromPackageData");
                if (!updatePackageData(-1, personifiedName)) {
                    return;
                }
                arrayList.add(Integer.valueOf(i));
                if (profileEntry.activateState == 0) {
                    profileEntry.removePackageEntry(personifiedName);
                    AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", personifiedName + " removed from vpn for profile " + str, userId);
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Integer.valueOf(i));
                this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, arrayList2);
                unsetDnsSystemProperty(i, i, str);
                getKnoxVpnHelperInstance().getClass();
                updateProxyRules(2, str, KnoxVpnHelper.updateProxyList(i, false));
                profileEntry.removePackageEntry(personifiedName);
                this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                z = false;
                AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", personifiedName + " removed from vpn for profile " + str, userId);
            } else {
                int length = packagesForUid.length;
                int i5 = 0;
                while (i5 < length) {
                    String str6 = packagesForUid[i5];
                    this.mKnoxVpnHelper.getClass();
                    String personifiedName2 = KnoxVpnHelper.getPersonifiedName(userId, str6);
                    if (profileEntry.getPackage(personifiedName2) != null && removePackagesFromDatabase(str, personifiedName2)) {
                        arrayList.add(Integer.valueOf(i));
                        if (profileEntry.activateState == 0) {
                            profileEntry.removePackageEntry(personifiedName2);
                            i2 = i5;
                            i3 = length;
                            strArr = packagesForUid;
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", personifiedName2 + str5 + str, userId);
                            str3 = str5;
                            z2 = false;
                        } else {
                            i2 = i5;
                            i3 = length;
                            String str7 = str5;
                            strArr = packagesForUid;
                            ArrayList arrayList3 = new ArrayList();
                            arrayList3.add(Integer.valueOf(i));
                            this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, arrayList3);
                            unsetDnsSystemProperty(i, i, str);
                            getKnoxVpnHelperInstance().getClass();
                            updateProxyRules(i4, str, KnoxVpnHelper.updateProxyList(i, false));
                            profileEntry.removePackageEntry(personifiedName2);
                            this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                            str3 = str7;
                            z2 = false;
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", personifiedName2 + str7 + str, userId);
                        }
                        i5 = i2 + 1;
                        z3 = z2;
                        packagesForUid = strArr;
                        str5 = str3;
                        length = i3;
                        i4 = 2;
                    }
                    i2 = i5;
                    i3 = length;
                    str3 = str5;
                    strArr = packagesForUid;
                    z2 = z3;
                    i5 = i2 + 1;
                    z3 = z2;
                    packagesForUid = strArr;
                    str5 = str3;
                    length = i3;
                    i4 = 2;
                }
                z = z3;
            }
            this.mFirewallHelper.removeRulesForFilteredPackages(profileEntry.mVendorPkgName, str4, arrayList);
            refreshDomainInHashMap(str);
            updateNotification(userId, str, z);
            int size = profileEntry.mPackageMap.size();
            if (profileEntry.vpnConnectionType == 1 && !this.mProcessManager.processRunCheck(profileEntry)) {
                z = true;
            }
            if ((size <= 0 || z) && profileEntry.activateState == 1) {
                try {
                    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                    if (binderInterfaceForProfile != null) {
                        int stopConnection = binderInterfaceForProfile.stopConnection(str);
                        if (stopConnection != 0) {
                            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred trying to stop vpn connection from profile " + str, userId);
                        } else {
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Connection with vpn vendor service stopped for profile " + str, userId);
                        }
                        Log.d("KnoxVpnEngineService", "removePackagesAfterUninstall: stopping the vpn connection status for on-demand configuration after removing all the packages " + stopConnection);
                    }
                } catch (Exception e) {
                    AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception stopping connection for profile " + str + " after removing package list by uid", userId);
                    StringBuilder sb = new StringBuilder();
                    sb.append("removePackagesAfterUninstall: stopping the vpn connection failed for on-demand configuration after removing all the packages ");
                    sb.append(Log.getStackTraceString(e));
                    Log.d("KnoxVpnEngineService", sb.toString());
                }
            }
        } catch (Exception e2) {
            Log.e("KnoxVpnEngineService", "removePackagesAfterUninstall exception " + Log.getStackTraceString(e2));
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing packages from vpn after uninstallation for profile ".concat(str), userId);
        }
    }

    public final boolean removePackagesFromDatabase(String str, String str2) {
        try {
            this.mVpnStorageProvider.getClass();
            return KnoxVpnStorageProvider.mEDM.deleteDataByFields("VpnPackageInfo", new String[]{"packageName", "profileName"}, new String[]{str2, str});
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removePackagesFromDatabase API "), "KnoxVpnEngineService");
            return false;
        }
    }

    public final void removePackagesFromPermissionCheckDb(int i) {
        try {
            String num = Integer.toString(i);
            this.mVpnStorageProvider.getClass();
            KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{num});
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removePackagestoPermissionCheckDb API "), "KnoxVpnEngineService");
        }
    }

    public final synchronized EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
        String str2;
        int i;
        String[] strArr2 = strArr;
        synchronized (this) {
            ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
            Log.d("KnoxVpnEngineService", "package is going to be removed from vpn for the profile " + str);
            int personaId = knoxVpnContext.getPersonaId();
            this.mKnoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(personaId, "ADD_ALL_PACKAGES");
            StringBuilder sb = new StringBuilder();
            EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
            enterpriseResponseData.setData(2);
            enterpriseResponseData.setStatus(1, -1);
            if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
                enterpriseResponseData.setData(140);
                return enterpriseResponseData;
            }
            try {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry == null) {
                    enterpriseResponseData.setData(108);
                    Log.d("KnoxVpnEngineService", "Error while removing the packages from vpn: The error code is 108");
                    return enterpriseResponseData;
                }
                if (profileEntry.activateState == 1 && this.mKnoxVpnHelper.isWideVpnExists(personaId)) {
                    Log.d("KnoxVpnEngineService", "removePackagesFromVpn: removing previously added rules before updating");
                    this.mFirewallHelper.removeMiscRulesRange(personaId, getVirtualInterfaceType(str), profileEntry.mInterfaceName);
                }
                int removePackagesFromVpnValidation = this.mKnoxVpnApiValidation.removePackagesFromVpnValidation(knoxVpnContext, strArr2, str);
                if (removePackagesFromVpnValidation != 100) {
                    enterpriseResponseData.setData(Integer.valueOf(removePackagesFromVpnValidation));
                    Log.d("KnoxVpnEngineService", "Error occured while removing packages from vpn: The error code is " + removePackagesFromVpnValidation);
                    return enterpriseResponseData;
                }
                if (profileEntry.activateState == 1 && this.mKnoxVpnHelper.isWideVpnExists(personaId)) {
                    Log.d("KnoxVpnEngineService", "removePackagesFromVpn: adding rules for newly added rules");
                    this.mFirewallHelper.addMiscRulesRange(personaId, getVirtualInterfaceType(str), profileEntry.mInterfaceName);
                }
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                int length = strArr2.length;
                int i2 = 0;
                while (i2 < length) {
                    String str3 = strArr2[i2];
                    if (!str3.equalsIgnoreCase(personifiedName)) {
                        this.mKnoxVpnHelper.getClass();
                        String personifiedName2 = KnoxVpnHelper.getPersonifiedName(personaId, str3);
                        ArrayList arrayList3 = null;
                        String[] strArr3 = null;
                        if (removePackagesFromDatabase(str, personifiedName2)) {
                            str2 = personifiedName;
                            i = length;
                        } else {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                strArr3 = getSharedUidPackges(personaId, new String[]{str3});
                            } catch (Exception unused) {
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            String[] strArr4 = strArr3;
                            if (strArr4 != null) {
                                str2 = personifiedName;
                                if (strArr4.length <= 0) {
                                    i = length;
                                    i2++;
                                    strArr2 = strArr;
                                    personifiedName = str2;
                                    length = i;
                                } else {
                                    ArrayList arrayList4 = new ArrayList();
                                    i = length;
                                    int length2 = strArr4.length;
                                    int i3 = 0;
                                    while (i3 < length2) {
                                        int i4 = length2;
                                        String str4 = strArr4[i3];
                                        String[] strArr5 = strArr4;
                                        this.mKnoxVpnHelper.getClass();
                                        String personifiedName3 = KnoxVpnHelper.getPersonifiedName(personaId, str4);
                                        if (removePackagesFromDatabase(str, personifiedName3)) {
                                            arrayList4.add(personifiedName3);
                                        }
                                        i3++;
                                        length2 = i4;
                                        strArr4 = strArr5;
                                    }
                                    arrayList3 = arrayList4;
                                }
                            }
                        }
                        if (arrayList3 != null && arrayList3.size() > 0) {
                            Iterator it = arrayList3.iterator();
                            while (it.hasNext()) {
                                String str5 = (String) it.next();
                                VpnPackageInfo vpnPackageInfo = profileEntry.getPackage(str5);
                                if (vpnPackageInfo != null && vpnPackageInfo.getCid() == personaId) {
                                    arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
                                    if (profileEntry.activateState == 0) {
                                        profileEntry.removePackageEntry(str5);
                                        sb.append(str5);
                                        sb.append(", ");
                                    } else {
                                        int uid = vpnPackageInfo.getUid();
                                        arrayList.add(Integer.valueOf(uid));
                                        removePackagesFromPermissionCheckDb(uid);
                                        unsetDnsSystemProperty(uid, uid, str);
                                        getKnoxVpnHelperInstance().getClass();
                                        updateProxyRules(2, str, KnoxVpnHelper.updateProxyList(uid, false));
                                        profileEntry.removePackageEntry(str5);
                                        sb.append(str5);
                                        sb.append(", ");
                                    }
                                }
                            }
                            i2++;
                            strArr2 = strArr;
                            personifiedName = str2;
                            length = i;
                        }
                        VpnPackageInfo vpnPackageInfo2 = profileEntry.getPackage(personifiedName2);
                        if (vpnPackageInfo2 != null && vpnPackageInfo2.getCid() == personaId) {
                            arrayList2.add(Integer.valueOf(vpnPackageInfo2.getUid()));
                            if (profileEntry.activateState == 0) {
                                profileEntry.removePackageEntry(personifiedName2);
                                sb.append(personifiedName2);
                                sb.append(", ");
                            } else {
                                int uid2 = vpnPackageInfo2.getUid();
                                arrayList.add(Integer.valueOf(uid2));
                                removePackagesFromPermissionCheckDb(uid2);
                                unsetDnsSystemProperty(uid2, uid2, str);
                                getKnoxVpnHelperInstance().getClass();
                                updateProxyRules(2, str, KnoxVpnHelper.updateProxyList(uid2, false));
                                profileEntry.removePackageEntry(personifiedName2);
                                sb.append(personifiedName2);
                                sb.append(", ");
                            }
                        }
                        i2++;
                        strArr2 = strArr;
                        personifiedName = str2;
                        length = i;
                    }
                    str2 = personifiedName;
                    i = length;
                    i2++;
                    strArr2 = strArr;
                    personifiedName = str2;
                    length = i;
                }
                this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, arrayList);
                this.mFirewallHelper.removeRulesForFilteredPackages(profileEntry.mVendorPkgName, profileEntry.mIpChainName, arrayList2);
                refreshDomainInHashMap(str);
                updateNotification(personaId, str, false);
                int size = profileEntry.mPackageMap.size();
                boolean z = profileEntry.vpnConnectionType == 1 && !this.mProcessManager.processRunCheck(profileEntry);
                if ((size <= 0 || z) && profileEntry.activateState == 1) {
                    try {
                        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                        if (binderInterfaceForProfile != null) {
                            int stopConnection = binderInterfaceForProfile.stopConnection(str);
                            if (stopConnection != 0) {
                                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error stopping connection for vpn profile " + str + ". Vendor service might not be running", knoxVpnContext.personaId);
                            } else {
                                AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Connection with vpn vendor service stopped for profile " + str + " after removing all the packages", knoxVpnContext.personaId);
                            }
                            Log.d("KnoxVpnEngineService", "stopping the vpn connection status for on-demand configuration after removing all the packages " + stopConnection);
                        }
                    } catch (Exception e) {
                        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception stopping connection for profile " + str + " after removing packages from vpn", knoxVpnContext.personaId);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("stopping the vpn connection failed for on-demand configuration after removing all the packages ");
                        sb2.append(Log.getStackTraceString(e));
                        Log.d("KnoxVpnEngineService", sb2.toString());
                    }
                }
                Log.d("KnoxVpnEngineService", "removing packages from vpn is a success: The error code is 0");
                enterpriseResponseData.setData(0);
                enterpriseResponseData.setStatus(0, 0);
                try {
                    if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
                        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:removePackagesFromVpn");
                        this.mData = knoxAnalyticsData;
                        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                        Arrays.sort(strArr);
                        StringBuffer stringBuffer = new StringBuffer(strArr[0]);
                        for (int i5 = 1; i5 < strArr.length; i5++) {
                            stringBuffer.append("," + strArr[i5]);
                        }
                        this.mData.setProperty("pkgLst", stringBuffer.toString());
                        KnoxAnalytics.log(this.mData);
                    }
                } catch (Exception e2) {
                    Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
                }
                if (sb.length() > 0) {
                    AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", sb.subSequence(0, sb.length() - 2).toString() + " removed from vpn for profile " + str, knoxVpnContext.personaId);
                }
                return enterpriseResponseData;
            } catch (Exception e3) {
                Log.d("KnoxVpnEngineService", "Error occured while removing packages from vpn: exception occured: The error code is -1" + Log.getStackTraceString(e3));
                enterpriseResponseData.setData(-1);
                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception while removing packages from vpn for profile " + str, knoxVpnContext.personaId);
                return enterpriseResponseData;
            }
        }
    }

    public final void removeProcessKillNotification(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry != null) {
                    getNotificationManager().cancelAsUser(null, str.hashCode(), new UserHandle(UserHandle.getUserId(profileEntry.mVendorUid)));
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to remove a notification which informed user about process restart " + Log.getStackTraceString(e));
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeProfileFromHashMapAndDB(String str) {
        Log.d("KnoxVpnEngineService", "removeProfileFromHashMapAndDB : removeProfileFromHashMapAndDB beginning");
        try {
            this.mVpnStorageProvider.getClass();
            boolean deleteDataByFields = KnoxVpnStorageProvider.mEDM.deleteDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str});
            if (deleteDataByFields) {
                removeFromHashMapByProfileName(str);
            }
            Log.d("KnoxVpnEngineService", "remove vpn connection for per app : success : " + deleteDataByFields);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("remove vpn connection for perapp : Error in handling remove connection for per app vpn Feature"), "KnoxVpnEngineService");
        }
    }

    public final void removeProfileFromKeyStore(int i, String str, String str2) {
        int userId = UserHandle.getUserId(i);
        int appId = UserHandle.getAppId(i);
        this.mKnoxVpnHelper.getClass();
        String regularPackageName = KnoxVpnHelper.getRegularPackageName(str2);
        if (appId == 1000) {
            try {
                if (regularPackageName.equalsIgnoreCase("com.samsung.sVpn") && userId == 0) {
                    Iterator it = retrieveVpnListFromStorage$1().iterator();
                    while (it.hasNext()) {
                        VpnProfile vpnProfile = (VpnProfile) it.next();
                        if (vpnProfile.name.equals(str)) {
                            if (DBG) {
                                Log.d("KnoxVpnEngineService", "The profileInfo present in the keystore belongs to knox, so going to delete it");
                            }
                            LegacyVpnProfileStore.remove("VPN_" + vpnProfile.key);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured at removeProfileFromKeyStore API "), "KnoxVpnEngineService");
            }
        }
    }

    public final void removeUidFromExemptList(int i, int i2, String str, String str2) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        int i3 = profileEntry.mNetId;
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "The following app removed from the interface ", str2, "will not go through vpn since it was blacklisted ", "KnoxVpnEngineService");
        if (getNetworkManagementService() != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new UidRangeParcel(i, i2));
                    getOemNetdService$1().knoxVpnRemoveExemptUidFromKnoxVpn(i3, (UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
                } catch (Exception unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to remove Uid From Vpn List ");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final synchronized EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "knox vpn profile is going to be removed : profileName value is " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        if (KnoxCustomManagerService.SETTING_PKG_NAME.equals(knoxVpnContext.vendorName)) {
            if (this.mVpnInfoPolicy == null) {
                this.mInjector.getClass();
                this.mVpnInfoPolicy = IVpnInfoPolicy.Stub.asInterface(ServiceManager.getService("vpn_policy"));
            }
            IVpnInfoPolicy iVpnInfoPolicy = this.mVpnInfoPolicy;
            if (iVpnInfoPolicy != null) {
                try {
                    if (iVpnInfoPolicy.deleteProfile(checkCallingUidPermission, str)) {
                        enterpriseResponseData.setData(0);
                        enterpriseResponseData.setStatus(0, 0);
                    }
                } catch (RemoteException unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while removing vpn settings profile");
                }
                return enterpriseResponseData;
            }
        }
        try {
            int removeVpnProfileValidation = this.mKnoxVpnApiValidation.removeVpnProfileValidation(knoxVpnContext, str);
            if (removeVpnProfileValidation != 100) {
                enterpriseResponseData.setData(Integer.valueOf(removeVpnProfileValidation));
                Log.d("KnoxVpnEngineService", "Error occured while removing the vpn profile: The error code is " + removeVpnProfileValidation);
                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error validating information from profile " + str + " before removing", knoxVpnContext.personaId);
                return enterpriseResponseData;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                enterpriseResponseData.setData(110);
                Log.d("KnoxVpnEngineService", "Error occured while removing the vpn profile: The error code is 110");
                return enterpriseResponseData;
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry.activateState == 1) {
                int state = binderInterfaceForProfile.getState(str);
                Log.d("KnoxVpnEngineService", "knox vpn profile is going to be removed: currentStateOfProfile value is " + state);
                if (state != 1 || state != 5) {
                    if (binderInterfaceForProfile.stopConnection(str) != 0) {
                        AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred trying to stop vpn connection from profile " + str, knoxVpnContext.personaId);
                    } else {
                        AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Connection with vpn vendor service stopped for profile " + str, knoxVpnContext.personaId);
                    }
                    Log.d("KnoxVpnEngineService", "knox vpn profile is going to be removed: stopConnectionStatus value is " + state);
                }
                int state2 = binderInterfaceForProfile.getState(str);
                Log.d("KnoxVpnEngineService", "knox vpn profile is going to be removed: currentStateOfProfile after stopping the connection is " + state2);
                if (state2 != 1 && state2 != 5) {
                    enterpriseResponseData.setData(306);
                    Log.d("KnoxVpnEngineService", "Error occured while removing the vpn profile: The error code is 306");
                    AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred while removing vpn profile " + str + ". Stop vpn connection before removing profile", knoxVpnContext.personaId);
                    return enterpriseResponseData;
                }
            }
            int i = profileEntry.profileId;
            int removeConnection = binderInterfaceForProfile.removeConnection(str);
            if (removeConnection != 0) {
                enterpriseResponseData.setData(102);
                Log.d("KnoxVpnEngineService", "Error occured while removing the vpn profile: The error code is 102");
                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error received from vendor while removing vpn connection for profile " + str, knoxVpnContext.personaId);
                return enterpriseResponseData;
            }
            removeProfileFromHashMapAndDB(str);
            Log.d("KnoxVpnEngineService", "knox vpn profile removal is a success: The error code is " + removeConnection);
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Vpn profile " + str + " successfully removed", knoxVpnContext.personaId);
            enterpriseResponseData.setData(Integer.valueOf(removeConnection));
            enterpriseResponseData.setStatus(0, 0);
            try {
                if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeVpnProfile");
                    this.mData = knoxAnalyticsData;
                    setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, i);
                    KnoxAnalytics.log(this.mData);
                }
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
            }
            return enterpriseResponseData;
        } catch (Exception e2) {
            Log.d("KnoxVpnEngineService", "Error occured while removing the vpn profile: exception occured: The error code is -1" + Log.getStackTraceString(e2));
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception occurred while removing vpn profile " + str, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
    }

    public final synchronized void removeVpnUidRanges(String str) {
        try {
            if (Binder.getCallingUid() != 1000) {
                return;
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return;
            }
            if (profileEntry.routeType == 0) {
                return;
            }
            Log.d("KnoxVpnEngineService", "unsetting dns rules after vpn is down");
            for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                    KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                    String packageName = vpnPackageInfo.getPackageName();
                    knoxVpnHelper.getClass();
                    int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(packageName);
                    this.mKnoxVpnHelper.getClass();
                    int startUid = KnoxVpnHelper.startUid(containerIdFromPackageName);
                    this.mKnoxVpnHelper.getClass();
                    unsetDnsSystemProperty(startUid, KnoxVpnHelper.stopUid(containerIdFromPackageName), str);
                } else {
                    int uid = vpnPackageInfo.getUid();
                    unsetDnsSystemProperty(uid, uid, str);
                }
            }
            Log.d("KnoxVpnEngineService", "updating firewall rules after vpn is down");
            System.currentTimeMillis();
            Log.d("KnoxVpnEngineService", "removeVpnUidRanges:defaultInterface value to which the virual tunnel was added is " + profileEntry.mDefaultInterface + " for the profile " + str);
            this.mFirewallHelper.removeNatRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName);
            this.mFirewallHelper.removeIpRouteAndPolicyRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName);
            this.mFirewallHelper.removeRulesForNoUidPackets(profileEntry.mInterface_type, profileEntry.mInterfaceName, profileEntry.mIpChainName);
            this.mFirewallHelper.removeRulesToAcceptIncomingPackets(profileEntry.mInterface_type, profileEntry.mInterfaceName);
            this.mFirewallHelper.updateDropRulesForNoUidPackets(1, profileEntry.mInterfaceName, profileEntry.mInterfaceAddress, profileEntry.mDefaultInterface, profileEntry.mInterfaceV6Address);
            if (profileEntry.mProxyServer != null || profileEntry.mPacurl != Uri.EMPTY) {
                this.mFirewallHelper.removeRulesToAcceptProxyPackets(profileEntry.mInterface_type, UserHandle.getUid(profileEntry.personaId, 1002), profileEntry.mInterfaceName);
                if (profileEntry.getProxyInfo() != null) {
                    this.mFirewallHelper.removeRulesToAllowAccessToLocalHostWithValidMark(profileEntry.getProxyInfo().getPort(), profileEntry.mInterface_type, profileEntry.mInterfaceName);
                }
                if (profileEntry.activateState == 1) {
                    KnoxVpnPacProcessor knoxVpnPacProcessor = getKnoxVpnPacProcessor();
                    knoxVpnPacProcessor.getClass();
                    try {
                        KnoxVpnPacProcessor.getProxyService(knoxVpnPacProcessor.getConfiguredUser(str)).resetInterface(str);
                    } catch (Exception unused) {
                        Log.e("KnoxVpnPacProcessor", "error occured while trying to reset interface name");
                    }
                }
            }
            if (profileEntry.mUsbTethering == 1) {
                if (profileEntry.activateState == 1) {
                    Log.d("KnoxVpnEngineService", "Applying rules to drop tether packets since vpn is down, but still in activated state");
                    String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
                    if (interfaceNameForUsbtethering != null) {
                        this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                    }
                }
                this.mFirewallHelper.removeRulesForUsbTethering(profileEntry.mInterface_type, profileEntry.mInterfaceName);
            }
            int i = profileEntry.activateState;
            if (i == 0) {
                KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
                String str2 = profileEntry.mIpChainName;
                knoxVpnFirewallHelper.getClass();
                knoxVpnFirewallHelper.insertRule(false, "*mangle", str2 + "_act", null, 46);
                removeMiscRulesForProfile(str);
            } else if (i == 1) {
                this.mFirewallHelper.addMarkingRulesForFilteredPackages(3, "block_traffic", profileEntry.mIpChainName);
                updateBlockingRules(str);
                if (profileEntry.mVpnClientType == 1) {
                    try {
                        this.mKnoxVpnHelper.applyBlockingRulesToUidRange(str, true);
                    } catch (RemoteException unused2) {
                    }
                }
            }
            VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(str);
            if (profileEntry2 != null) {
                profileEntry2.mInterfaceName = null;
                profileEntry2.mInterfaceAddress = null;
                profileEntry2.mInterfaceV6Address = null;
                profileEntry2.mDefaultInterface = null;
                profileEntry2.mInterface_type = 0;
                profileEntry2.mNetId = 0;
            }
            Log.d("KnoxVpnEngineService", "updating firewall rules after vpn down is finished");
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0075, code lost:
    
        android.util.Log.d("KnoxVpnEngineService", "run all vpn : startVpnProfile : profileName " + r3 + " state : " + r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runAllVpnService() {
        /*
            r9 = this;
            java.lang.String r0 = "run all vpn : runAllVpnService beginning"
            java.lang.String r1 = "KnoxVpnEngineService"
            android.util.Log.d(r1, r0)
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r0 = r9.vpnConfig     // Catch: java.lang.Exception -> L3e
            java.util.concurrent.ConcurrentHashMap r0 = r0.vpnProfileInfoMap     // Catch: java.lang.Exception -> L3e
            java.util.Collection r0 = r0.values()     // Catch: java.lang.Exception -> L3e
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Exception -> L3e
        L14:
            boolean r2 = r0.hasNext()     // Catch: java.lang.Exception -> L3e
            if (r2 == 0) goto L9e
            java.lang.Object r2 = r0.next()     // Catch: java.lang.Exception -> L3e
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r2 = (com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo) r2     // Catch: java.lang.Exception -> L3e
            java.lang.String r3 = r2.mProfileName     // Catch: java.lang.Exception -> L3e
            com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService r4 = r9.getBinderInterfaceForProfile(r3)     // Catch: java.lang.Exception -> L3e
            if (r4 != 0) goto L40
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3e
            r2.<init>()     // Catch: java.lang.Exception -> L3e
            java.lang.String r4 = "runAllVpnService:binder value is null for the profile "
            r2.append(r4)     // Catch: java.lang.Exception -> L3e
            r2.append(r3)     // Catch: java.lang.Exception -> L3e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> L3e
            android.util.Log.d(r1, r2)     // Catch: java.lang.Exception -> L3e
            goto L14
        L3e:
            r9 = move-exception
            goto L94
        L40:
            int r4 = r9.getVPNTransitionState(r3)     // Catch: java.lang.Exception -> L3e
            boolean r5 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG     // Catch: java.lang.Exception -> L3e
            java.lang.String r6 = " state : "
            if (r5 == 0) goto L65
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3e
            r7.<init>()     // Catch: java.lang.Exception -> L3e
            java.lang.String r8 = "run all vpn : profileName "
            r7.append(r8)     // Catch: java.lang.Exception -> L3e
            r7.append(r3)     // Catch: java.lang.Exception -> L3e
            r7.append(r6)     // Catch: java.lang.Exception -> L3e
            r7.append(r4)     // Catch: java.lang.Exception -> L3e
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> L3e
            android.util.Log.d(r1, r7)     // Catch: java.lang.Exception -> L3e
        L65:
            int r2 = r2.routeType     // Catch: java.lang.Exception -> L3e
            if (r2 != 0) goto L6a
            goto L14
        L6a:
            r2 = 1
            if (r4 == r2) goto L73
            r2 = 5
            if (r4 == r2) goto L73
            r2 = -1
            if (r4 != r2) goto L14
        L73:
            if (r5 == 0) goto L90
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3e
            r2.<init>()     // Catch: java.lang.Exception -> L3e
            java.lang.String r5 = "run all vpn : startVpnProfile : profileName "
            r2.append(r5)     // Catch: java.lang.Exception -> L3e
            r2.append(r3)     // Catch: java.lang.Exception -> L3e
            r2.append(r6)     // Catch: java.lang.Exception -> L3e
            r2.append(r4)     // Catch: java.lang.Exception -> L3e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> L3e
            android.util.Log.d(r1, r2)     // Catch: java.lang.Exception -> L3e
        L90:
            r9.startVpnProfile(r3)     // Catch: java.lang.Exception -> L3e
            goto L14
        L94:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Exception occured while doing runAllVpnService "
            r0.<init>(r2)
            com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticOutline0.m(r9, r0, r1)
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.runAllVpnService():void");
    }

    public final void sendBindSuccessIntent(int i, String str) {
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mKnoxVpnHelper.getClass();
            String regularPackageName = KnoxVpnHelper.getRegularPackageName(str);
            this.mKnoxVpnHelper.getClass();
            int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(str);
            if (packagesForUid != null && i != -1) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.knox.intent.action.VPN_BIND_RESULT");
                intent.putExtra("com.samsung.android.knox.intent.extra.VPN_BIND_VENDOR", regularPackageName);
                intent.putExtra("com.samsung.android.knox.intent.extra.VPN_BIND_CID", containerIdFromPackageName);
                intent.putExtra("com.samsung.android.knox.intent.extra.VPN_BIND_STATUS", true);
                Log.d("KnoxVpnEngineService", "Sending bind success intent to User " + containerIdFromPackageName);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_VPN_GENERIC");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendMessageToHandler$1(int i, Bundle bundle) {
        KnoxVpnHandler knoxVpnHandler = this.mHandler;
        if (knoxVpnHandler != null) {
            this.mHandler.sendMessage(Message.obtain(knoxVpnHandler, i, 0, 0, bundle));
        }
    }

    public final void sendVpnConnectionFailIntent(int i, String str, String str2) {
        String[] strArr;
        int i2;
        this.mKnoxVpnHelper.getClass();
        int containerIdFromPackageName = KnoxVpnHelper.getContainerIdFromPackageName(str);
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str2);
        if (profileEntry != null) {
            strArr = new String[profileEntry.mPackageMap.values().size()];
            int i3 = 0;
            for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                    strArr = this.mKnoxVpnHelper.getUserPackageListForProfile(containerIdFromPackageName, str2);
                } else if (vpnPackageInfo.getPackageName() != null) {
                    if (!vpnPackageInfo.getPackageName().contains("_") || vpnPackageInfo.getPackageName().split("_", 2).length < 1) {
                        i2 = i3 + 1;
                        strArr[i3] = vpnPackageInfo.getPackageName();
                    } else {
                        i2 = i3 + 1;
                        strArr[i3] = vpnPackageInfo.getPackageName().split("_", 2)[1];
                    }
                    i3 = i2;
                }
            }
        } else {
            strArr = new String[0];
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.knox.intent.action.ACTION_RECREATE_VPN_PROFILE_FAIL");
        if (!str.contains("_") || str.split("_", 2).length < 1) {
            intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str);
        } else {
            intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str.split("_", 2)[1]);
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str.split("_", 2)[1]);
        intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_PROFILE_NAME", str2);
        intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_USER_ID", containerIdFromPackageName);
        intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_PACKAGE_LIST", strArr);
        Log.v("KnoxVpnEngineService", "Sending Vpn Connection Fail intent - profile: " + str2);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(i)), "com.samsung.android.knox.permission.KNOX_VPN_GENERIC");
        if (this.vpnConfig.getProfileEntry(str2) != null) {
            ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("profileName", str2);
            Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i, m, "adminUid", FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1, "errorType");
            if (!str.contains("_") || str.split("_", 2).length < 1) {
                m.put("vendorName", str);
            } else {
                m.put("vendorName", str.split("_", 2)[1]);
            }
            m.put("containerID", Integer.valueOf(containerIdFromPackageName));
            StringBuffer stringBuffer = new StringBuffer("[");
            if (strArr != null) {
                for (int i4 = 0; i4 < strArr.length; i4++) {
                    if (i4 > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(strArr[i4]);
                }
            }
            stringBuffer.append("]");
            m.put("packageList", stringBuffer.toString());
            this.mVpnStorageProvider.getClass();
            boolean putDataByFields = KnoxVpnStorageProvider.mEDM.putDataByFields("vpnConnectionFail", null, null, m);
            if (DBG) {
                Log.d("KnoxVpnEngineService", "add profile in database (vpnConnectionFail): status is " + putDataByFields + "profile Name is" + str2);
            }
        }
    }

    public final boolean setActivate(int i, String str) {
        VpnProfileInfo profileEntry;
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setActivate: profileName value is ", str, "activateState value is ", "KnoxVpnEngineService");
        boolean z = false;
        try {
            profileEntry = this.vpnConfig.getProfileEntry(str);
        } catch (Exception e) {
            if (DBG) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured while storing activateState info in db "), "KnoxVpnEngineService");
            }
        }
        if (profileEntry == null) {
            return false;
        }
        int i2 = profileEntry.activateState;
        ContentValues contentValues = new ContentValues();
        contentValues.put("activateState", Integer.valueOf(i));
        this.mVpnStorageProvider.getClass();
        if (KnoxVpnStorageProvider.mEDM.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues)) {
            profileEntry.activateState = i;
            z = true;
        } else {
            profileEntry.activateState = i2;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("setActivate: isActivateInfoSaved value is ", "KnoxVpnEngineService", z);
        return z;
    }

    public final EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) {
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(Boolean.FALSE);
        enterpriseResponseData.setStatus(1, -1);
        if (knoxVpnContext == null) {
            if (DBG) {
                Log.e("KnoxVpnEngineService", "setAutoRetryOnConnectionError : vpnContext is null");
            }
            return enterpriseResponseData;
        }
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
            return enterpriseResponseData;
        }
        if (str != null) {
            try {
                if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                    return enterpriseResponseData;
                }
                IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                if (binderInterfaceForProfile == null) {
                    Log.e("KnoxVpnEngineService", "setAutoRetryOnConnectionError : VPN Service not started");
                    return enterpriseResponseData;
                }
                boolean autoRetryOnConnectionError = binderInterfaceForProfile.setAutoRetryOnConnectionError(str, z);
                Log.d("KnoxVpnEngineService", "setAutoRetryOnConnectionError : success = " + autoRetryOnConnectionError);
                enterpriseResponseData.setData(Boolean.valueOf(autoRetryOnConnectionError));
                enterpriseResponseData.setStatus(0, 0);
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("setAutoRetryOnConnectionError : Failure at "), "KnoxVpnEngineService");
                return null;
            }
        }
        try {
            if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:setAutoRetryOnConnectionError");
                this.mData = knoxAnalyticsData;
                setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                this.mData.setProperty("enb", z);
                KnoxAnalytics.log(this.mData);
            }
        } catch (Exception e2) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception = "), "KnoxVpnEngineService");
        }
        return enterpriseResponseData;
    }

    public final EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(Boolean.FALSE);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        try {
            if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                return enterpriseResponseData;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.e("KnoxVpnEngineService", "setting CACertificate : Service is not started");
                return enterpriseResponseData;
            }
            enterpriseResponseData.setData(Boolean.valueOf(binderInterfaceForProfile.setCACertificate(str, bArr)));
            enterpriseResponseData.setStatus(0, 0);
            try {
                if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:setCACertificate");
                    this.mData = knoxAnalyticsData;
                    setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                    KnoxAnalytics.log(this.mData);
                }
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception = "), "KnoxVpnEngineService");
            }
            return enterpriseResponseData;
        } catch (Exception e2) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("setting CACertificate : Failure at "), "KnoxVpnEngineService");
            return null;
        }
    }

    public final void setCommonProperties(KnoxAnalyticsData knoxAnalyticsData, KnoxVpnContext knoxVpnContext, String str, int i) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (knoxVpnContext != null) {
            knoxAnalyticsData.setProperty("admUid", knoxVpnContext.adminId);
            knoxAnalyticsData.setProperty("vndrCid", knoxVpnContext.personaId);
            knoxAnalyticsData.setProperty("vndrPkgN", knoxVpnContext.vendorName);
        }
        if (profileEntry != null) {
            i = profileEntry.profileId;
        }
        knoxAnalyticsData.setProperty("prfid", i);
    }

    public final boolean setDismissibleFlagDB(int i, int i2, int i3) {
        boolean putDataByFields;
        try {
            String[] strArr = {"notificationUserId", "adminUid"};
            String[] strArr2 = {String.valueOf(i2), String.valueOf(i)};
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnNotificationFlagTable", strArr, strArr2, null);
            if (dataByFields.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("dismissFlag", Integer.valueOf(i3));
                contentValues.put("notificationUserId", Integer.valueOf(i2));
                contentValues.put("adminUid", Integer.valueOf(i));
                this.mVpnStorageProvider.getClass();
                putDataByFields = KnoxVpnStorageProvider.mEDM.putDataByFields("VpnNotificationFlagTable", null, null, contentValues);
            } else {
                ContentValues contentValues2 = (ContentValues) dataByFields.get(0);
                contentValues2.put("dismissFlag", Integer.valueOf(i3));
                this.mVpnStorageProvider.getClass();
                putDataByFields = KnoxVpnStorageProvider.mEDM.putDataByFields("VpnNotificationFlagTable", strArr, strArr2, contentValues2);
            }
            return putDataByFields;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured while storing notificationFlagState info in db "), "KnoxVpnEngineService");
            return false;
        }
    }

    public final void setDnsSystemProperty(int i, int i2, int i3, String str, String str2) {
        if (str != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (this.vpnConfig.getProfileEntry(str) != null) {
                        if (DBG) {
                            Log.d("KnoxVpnEngineService", "setDnsSystemProperty is reached : whose profileName is " + str + "whose start uid is " + i + " whose stop uid is " + i2 + " whose interface " + str2 + " whose netId is " + i3);
                        }
                        ArraySet arraySet = new ArraySet();
                        arraySet.add(new UidRangeParcel(i, i2));
                        getOemNetdService$1().networkAddUidRanges(i3, toUidRangeStableParcels(arraySet));
                    }
                } catch (Exception unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to set the dns entry for the profile ".concat(str));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setNotificationDismissibleFlag(com.samsung.android.knox.net.vpn.KnoxVpnContext r8, java.lang.String r9, int r10, int r11) {
        /*
            r7 = this;
            java.lang.String r0 = "setNotificationDismissibleFlag : Exception : "
            r7.checkCallingUidPermission(r8)
            int r1 = android.os.Binder.getCallingUid()
            int r2 = android.os.UserHandle.getUserId(r1)
            android.content.Context r3 = r7.mContext
            android.content.pm.PackageManager r3 = r3.getPackageManager()
            java.lang.String r1 = r3.getNameForUid(r1)
            android.content.pm.IPackageManager r3 = android.app.AppGlobals.getPackageManager()
            r4 = 1
            java.lang.String r5 = "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION"
            int r1 = r3.checkPermission(r5, r1, r2)     // Catch: android.os.RemoteException -> L28
            if (r1 != 0) goto L2c
            r1 = r4
            goto L2d
        L28:
            r1 = move-exception
            r1.printStackTrace()
        L2c:
            r1 = 0
        L2d:
            r2 = -1
            java.lang.String r3 = "KnoxVpnEngineService"
            if (r1 != 0) goto L38
            java.lang.String r7 = "Only Knox Internal package can set notification flag"
            android.util.Log.d(r3, r7)
            return r2
        L38:
            boolean r1 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.DBG
            if (r1 == 0) goto L44
            java.lang.String r1 = "setNotificationDismissibleFlag userId : "
            java.lang.String r5 = " , flag : "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r10, r11, r1, r5, r3)
        L44:
            long r5 = android.os.Binder.clearCallingIdentity()
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig r1 = r7.vpnConfig     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo r9 = r1.getProfileEntry(r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            if (r9 != 0) goto L56
            android.os.Binder.restoreCallingIdentity(r5)
            r7 = 108(0x6c, float:1.51E-43)
            return r7
        L56:
            int r8 = r8.adminId     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            boolean r8 = r7.setDismissibleFlagDB(r8, r10, r11)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            if (r8 != 0) goto L6d
            java.lang.String r7 = "setNotificationDismissibleFlag : Notification DB failure"
            android.util.Log.e(r3, r7)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            android.os.Binder.restoreCallingIdentity(r5)
            r7 = -2
            return r7
        L69:
            r7 = move-exception
            goto L9e
        L6b:
            r7 = move-exception
            goto L87
        L6d:
            int r8 = r7.getNotificationDismissibleFlagInternal(r10)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            if (r8 == r11) goto L83
            r7.refreshFlagValueInHashMap(r10)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            java.lang.String r8 = "setNotificationDismissibleFlag : Profile is connected to Knox Vpn"
            android.util.Log.d(r3, r8)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            android.net.IVpnManager r7 = r7.getVpnManagerService()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r7.updateNotificationIcon(r10)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
        L83:
            android.os.Binder.restoreCallingIdentity(r5)
            return r4
        L87:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L69
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L69
            java.lang.String r7 = android.util.Log.getStackTraceString(r7)     // Catch: java.lang.Throwable -> L69
            r8.append(r7)     // Catch: java.lang.Throwable -> L69
            java.lang.String r7 = r8.toString()     // Catch: java.lang.Throwable -> L69
            android.util.Log.e(r3, r7)     // Catch: java.lang.Throwable -> L69
            android.os.Binder.restoreCallingIdentity(r5)
            return r2
        L9e:
            android.os.Binder.restoreCallingIdentity(r5)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.setNotificationDismissibleFlag(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String, int, int):int");
    }

    public final void setPropertiesWithLocalEntry(KnoxAnalyticsData knoxAnalyticsData, String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
            String str2 = profileEntry.protocolType;
            if (str2 != null) {
                knoxAnalyticsData.setProperty("prtTp", str2);
            }
            int i = profileEntry.routeType;
            if (i != -1) {
                knoxAnalyticsData.setProperty("rtTp", i);
            }
            knoxAnalyticsData.setProperty("connTp", profileEntry.vpnConnectionType);
            knoxAnalyticsData.setProperty("prxAuth", profileEntry.proxyAuthRequried);
            knoxAnalyticsData.setProperty("chn", profileEntry.chainingEnabled);
        }
    }

    public final EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(Boolean.FALSE);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        if (str != null) {
            try {
                if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                    return enterpriseResponseData;
                }
                IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                if (binderInterfaceForProfile == null) {
                    Log.e("KnoxVpnEngineService", "setting server cert validation : VPN Service not started");
                    return enterpriseResponseData;
                }
                enterpriseResponseData.setData(Boolean.valueOf(binderInterfaceForProfile.setServerCertValidationUserAcceptanceCriteria(str, z, list, i)));
                enterpriseResponseData.setStatus(0, 0);
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("setting server cert validation : Failure at "), "KnoxVpnEngineService");
                return null;
            }
        }
        try {
            if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:setServerCertValidationUserAcceptanceCriteria");
                this.mData = knoxAnalyticsData;
                setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                this.mData.setProperty("enb", z);
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(list.get(0)));
                for (int i2 = 1; i2 < list.size(); i2++) {
                    stringBuffer.append("," + String.valueOf(list.get(i2)));
                }
                this.mData.setProperty("cndt", stringBuffer.toString());
                this.mData.setProperty("fq", i);
                KnoxAnalytics.log(this.mData);
            }
        } catch (Exception e2) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception = "), "KnoxVpnEngineService");
        }
        return enterpriseResponseData;
    }

    public final synchronized EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(Boolean.FALSE);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            return enterpriseResponseData;
        }
        if (str != null) {
            try {
                if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                    return enterpriseResponseData;
                }
                IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
                if (binderInterfaceForProfile == null) {
                    Log.e("KnoxVpnEngineService", "setting user certificate : VPN Service not started");
                    return enterpriseResponseData;
                }
                enterpriseResponseData.setData(Boolean.valueOf(binderInterfaceForProfile.setUserCertificate(str, bArr, str2)));
                enterpriseResponseData.setStatus(0, 0);
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "setting user certificate : Failure at " + Log.getStackTraceString(e));
                return null;
            }
        }
        try {
            if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:setUserCertificate");
                this.mData = knoxAnalyticsData;
                setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                KnoxAnalytics.log(this.mData);
            }
        } catch (Exception e2) {
            Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
        }
        return enterpriseResponseData;
    }

    public final synchronized EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) {
        int vpnModeOfOperationValidation;
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        try {
            vpnModeOfOperationValidation = this.mKnoxVpnApiValidation.setVpnModeOfOperationValidation(knoxVpnContext, str, i);
        } catch (Exception e) {
            if (DBG) {
                Log.e("KnoxVpnEngineService", "setting vpn mode : Failure at " + Log.getStackTraceString(e));
            }
        }
        if (vpnModeOfOperationValidation != 100) {
            enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperationValidation));
            Log.d("KnoxVpnEngineService", "setting the mode of operation for the profile has failed:The error code is " + vpnModeOfOperationValidation);
            return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
            enterpriseResponseData.setData(110);
            Log.d("KnoxVpnEngineService", "setting the mode of operation for the profile has failed: The error code is 110");
            return enterpriseResponseData;
        }
        int vpnModeOfOperation = binderInterfaceForProfile.setVpnModeOfOperation(str, i);
        Log.d("KnoxVpnEngineService", "setVpnModeOfOperation : profileName = " + str + " :setMode = " + vpnModeOfOperation);
        enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperation));
        enterpriseResponseData.setStatus(0, 0);
        try {
            if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
                KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_VPN", 1, "API:setVpnModeOfOperation");
                this.mData = knoxAnalyticsData;
                setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
                this.mData.setProperty("vpnMd", i);
                KnoxAnalytics.log(this.mData);
            }
        } catch (Exception e2) {
            Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
        }
        return enterpriseResponseData;
    }

    public final void setupIntentFilter$1() {
        Log.d("KnoxVpnEngineService", "setup intent filter is called");
        if (this.receiver != null) {
            Log.d("KnoxVpnEngineService", "VpnReceiver already registered");
            return;
        }
        Log.d("KnoxVpnEngineService", "Registering VpnReceiver is successful");
        this.receiver = new VpnReceiver();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            intentFilter.addDataScheme("package");
            Context context = this.mContext;
            VpnReceiver vpnReceiver = this.receiver;
            UserHandle userHandle = UserHandle.ALL;
            context.registerReceiverAsUser(vpnReceiver, userHandle, intentFilter, null, null);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.USER_STARTED");
            intentFilter2.addAction("android.intent.action.USER_SWITCHED");
            intentFilter2.addAction("android.intent.action.USER_REMOVED");
            intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter2.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter2.addAction("android.intent.action.USER_PRESENT");
            intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
            intentFilter2.addAction("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE");
            intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
            intentFilter2.addAction("android.hardware.usb.action.USB_STATE");
            this.mContext.registerReceiverAsUser(this.receiver, userHandle, intentFilter2, null, null, 2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("enterprise.container.uninstalled");
            intentFilter3.addAction("enterprise.container.admin.changed");
            this.mContext.registerReceiverAsUser(this.receiver, userHandle, intentFilter3, "com.samsung.android.knox.permission.KNOX_CONTAINER", null, 2);
            IntentFilter intentFilter4 = new IntentFilter();
            intentFilter4.addAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
            intentFilter4.addAction("com.samsung.android.knox.intent.action.VPN_PROXY_BROADCAST_INTERNAL");
            this.mContext.registerReceiverAsUser(this.receiver, userHandle, intentFilter4, "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL", null, 2);
            IntentFilter intentFilter5 = new IntentFilter();
            intentFilter5.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter5.addDataScheme("package");
            this.mContext.registerReceiverAsUser(this.receiver, userHandle, intentFilter5, null, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showToastVpnEULA() {
        Log.d("KnoxVpnEngineService", "Show EULA Toast for ONS- START");
        String string = this.mContext.getString(R.string.permdesc_nfc);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("message", string);
        message.setData(bundle);
        message.what = 32;
        this.mHandler.sendMessage(message);
        Log.d("KnoxVpnEngineService", "Show EULA Toast for ONS - END");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00de A[Catch: all -> 0x0058, Exception -> 0x00d8, TRY_LEAVE, TryCatch #0 {Exception -> 0x00d8, blocks: (B:59:0x00ce, B:43:0x00de, B:46:0x0123, B:48:0x0127, B:54:0x0144, B:56:0x0155, B:57:0x0175), top: B:58:0x00ce }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0123 A[Catch: all -> 0x0058, Exception -> 0x00d8, TRY_ENTER, TryCatch #0 {Exception -> 0x00d8, blocks: (B:59:0x00ce, B:43:0x00de, B:46:0x0123, B:48:0x0127, B:54:0x0144, B:56:0x0155, B:57:0x0175), top: B:58:0x00ce }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized com.samsung.android.knox.net.vpn.EnterpriseResponseData startConnection(com.samsung.android.knox.net.vpn.KnoxVpnContext r29, java.lang.String r30) {
        /*
            Method dump skipped, instructions count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.startConnection(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):com.samsung.android.knox.net.vpn.EnterpriseResponseData");
    }

    public final void startVpnConnectionForBindedClient(IKnoxVpnService iKnoxVpnService, String str) {
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.mVendorPkgName.equalsIgnoreCase(str) && vpnProfileInfo.routeType != 0 && vpnProfileInfo.activateState != 0) {
                    String str2 = vpnProfileInfo.mProfileName;
                    int state = iKnoxVpnService.getState(str2);
                    Log.d("KnoxVpnEngineService", "Start the vpn connection after binding successfully for the profile " + str2 + " with the vpn client " + vpnProfileInfo.mVendorPkgName + " whose current state is " + state);
                    if (state == 1 || state == 5 || state == -1) {
                        startVpnProfile(str2);
                    }
                }
            }
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured while doing runAllVpnService "), "KnoxVpnEngineService");
        }
    }

    public final int startVpnForPerApplication(String str, List list, boolean z) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("startVpnForPerApplication: profileName value is ", str, "KnoxVpnEngineService");
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str) == 1 && checkChainingStatus(str) == 0) {
                return EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
            }
            if (profileEntry.activateState == 0) {
                return 301;
            }
            int vPNTransitionState = getVPNTransitionState(str);
            if (vPNTransitionState != -1 && vPNTransitionState != 1) {
                if (vPNTransitionState == 2) {
                    this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), null, list);
                } else if (vPNTransitionState == 3) {
                    this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), null, list);
                } else if (vPNTransitionState == 4) {
                    String str2 = profileEntry.mInterfaceName;
                    int i = profileEntry.mNetId;
                    if (DBG) {
                        Log.d("KnoxVpnEngineService", "startVpnForPackage: for connected state profileName value is " + str + "interfaceValue value is " + str2);
                    }
                    if (str2 == null) {
                        return -1;
                    }
                    this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), profileEntry.mInterfaceName, list);
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        setDnsSystemProperty(intValue, intValue, i, str, str2);
                        getKnoxVpnHelperInstance().getClass();
                        updateProxyRules(3, str, KnoxVpnHelper.updateProxyList(intValue, false));
                    }
                } else if (vPNTransitionState != 5) {
                    Log.e("KnoxVpnEngineService", "startVpnForPackage : VPN State not valid");
                    return -1;
                }
                return 0;
            }
            if (DBG) {
                Log.d("KnoxVpnEngineService", "startVpnForPackage: profileName : " + str);
            }
            this.mFirewallHelper.addMiscRules(getVirtualInterfaceType(str), null, list);
            if (z) {
                startVpnProfile(str);
            }
            return 0;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("startVpnForPackage: "), "KnoxVpnEngineService");
            return -1;
        }
    }

    public final int startVpnForUserwideVpn(int i, String str) {
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "startVpnForPackage: profileName value is ", str, " container id value is ", "KnoxVpnEngineService");
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str) == 1 && checkChainingStatus(str) == 0) {
                return EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
            }
            if (profileEntry.activateState == 0) {
                return 301;
            }
            int vPNTransitionState = getVPNTransitionState(str);
            if (vPNTransitionState != -1 && vPNTransitionState != 1) {
                if (vPNTransitionState == 2) {
                    this.mFirewallHelper.addMiscRulesRange(i, getVirtualInterfaceType(str), null);
                } else if (vPNTransitionState == 3) {
                    this.mFirewallHelper.addMiscRulesRange(i, getVirtualInterfaceType(str), null);
                } else if (vPNTransitionState == 4) {
                    String str2 = profileEntry.mInterfaceName;
                    int i2 = profileEntry.mNetId;
                    if (DBG) {
                        Log.d("KnoxVpnEngineService", "startVpnForPackage: for connected state profileName value is " + str + "interfaceValue value is " + str2);
                    }
                    if (str2 == null) {
                        return -1;
                    }
                    this.mFirewallHelper.addMiscRulesRange(i, getVirtualInterfaceType(str), str2);
                    this.mKnoxVpnHelper.getClass();
                    int startUid = KnoxVpnHelper.startUid(i);
                    this.mKnoxVpnHelper.getClass();
                    setDnsSystemProperty(startUid, KnoxVpnHelper.stopUid(i), i2, str, str2);
                    getKnoxVpnHelperInstance().getClass();
                    updateProxyRules(3, str, KnoxVpnHelper.updateProxyList(i, true));
                } else if (vPNTransitionState != 5) {
                    Log.e("KnoxVpnEngineService", "startVpnForPackage : VPN State not valid");
                    return -1;
                }
                return 0;
            }
            if (DBG) {
                Log.d("KnoxVpnEngineService", "startVpnForPackage: profileName : " + str);
            }
            this.mFirewallHelper.addMiscRulesRange(i, getVirtualInterfaceType(str), null);
            return 0;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("startVpnForPackage: "), "KnoxVpnEngineService");
            return -1;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:40|41|(1:96)(1:45)|(9:95|48|(1:50)(1:93)|51|(3:87|88|89)(1:53)|(1:86)(1:56)|(1:85)|59|(3:61|62|63)(6:(1:65)|(1:(3:70|71|72))|73|74|75|(3:77|78|79)(3:80|71|72)))|47|48|(0)(0)|51|(0)(0)|(0)|86|(0)|85|59|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(6:(1:65)|(1:(3:70|71|72))|73|74|75|(3:77|78|79)(3:80|71|72)) */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0112, code lost:
    
        if (r10 == (-1)) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01ca, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0167, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x014a A[Catch: all -> 0x0036, Exception -> 0x0167, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0036, blocks: (B:4:0x0020, B:6:0x0024, B:7:0x0039, B:13:0x0045, B:16:0x0049, B:19:0x0079, B:21:0x007f, B:24:0x00a6, B:26:0x00af, B:28:0x00b5, B:31:0x00cd, B:33:0x00d5, B:36:0x00f7, B:38:0x00fe, B:40:0x0104, B:41:0x0108, B:88:0x0126, B:61:0x014a, B:65:0x016c, B:73:0x0190, B:75:0x0194, B:77:0x01a7, B:80:0x01cc, B:82:0x01f3), top: B:3:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0126 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int startVpnProfile(java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 554
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.startVpnProfile(java.lang.String):int");
    }

    public final synchronized EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) {
        ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
        Log.d("KnoxVpnEngineService", "knox vpn profile is going to be stopped for the profile " + str);
        EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
        enterpriseResponseData.setData(-1);
        enterpriseResponseData.setStatus(1, -1);
        if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
            enterpriseResponseData.setData(140);
            return enterpriseResponseData;
        }
        try {
            if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
                return enterpriseResponseData;
            }
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            if (binderInterfaceForProfile == null) {
                Log.e("KnoxVpnEngineService", "stopping vpn connection : Service is not started");
                return enterpriseResponseData;
            }
            int stopConnection = binderInterfaceForProfile.stopConnection(str);
            enterpriseResponseData.setData(Integer.valueOf(stopConnection));
            enterpriseResponseData.setStatus(0, 0);
            if (stopConnection != 0) {
                AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Error occurred trying to stop vpn connection from profile " + str, knoxVpnContext.personaId);
            } else {
                AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnEngineService", "Connection with vpn vendor service stopped for profile " + str, knoxVpnContext.personaId);
            }
            return enterpriseResponseData;
        } catch (Exception e) {
            Log.e("KnoxVpnEngineService", "stopping vpn connection : Failure at " + Log.getStackTraceString(e));
            AuditLog.logAsUser(3, 5, false, Process.myPid(), "KnoxVpnEngineService", "Exception stopping connection for profile " + str, knoxVpnContext.personaId);
            return enterpriseResponseData;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00e2 A[Catch: all -> 0x012a, Exception -> 0x0179, TRY_LEAVE, TryCatch #0 {Exception -> 0x0179, blocks: (B:6:0x000c, B:8:0x003b, B:9:0x0040, B:11:0x0046, B:14:0x007e, B:28:0x00e2, B:39:0x0166, B:40:0x017c, B:16:0x019f, B:18:0x01a7, B:19:0x01ac, B:43:0x00d6, B:48:0x01b4, B:51:0x01bc, B:53:0x01cc, B:55:0x01d6, B:56:0x01df, B:58:0x01f2, B:60:0x01f6, B:62:0x0200), top: B:5:0x000c, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void stopVpnConnectionAfterAdminRemoval(int r14) {
        /*
            Method dump skipped, instructions count: 556
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.stopVpnConnectionAfterAdminRemoval(int):void");
    }

    public final void syncVpnProfile(IKnoxVpnService iKnoxVpnService, String str) {
        Log.d("KnoxVpnEngineService", "Start syncProfile between VPNDB and Vendor's");
        try {
            String connection = iKnoxVpnService.getConnection(str);
            if (connection != null) {
                this.mKnoxVpnHelper.getClass();
                Log.d("KnoxVpnEngineService", "Profile in VendorDB is removed successfullyStatus of remove: " + iKnoxVpnService.removeConnection(KnoxVpnHelper.getProfileNameFromJsonString(connection)));
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Sync the profile : Failure at "), "KnoxVpnEngineService");
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        Log.d("KnoxVpnEngineService", "system ready is being called");
    }

    public final void unregisterFilterList() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (this.receiver != null) {
                    Log.d("KnoxVpnEngineService", "unregistering VpnReceiver is successful");
                    this.mContext.unregisterReceiver(this.receiver);
                }
                this.receiver = null;
            } catch (Exception unused) {
                Log.e("KnoxVpnEngineService", "Error occured while trying to unregister the reciever");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void unsetDnsSystemProperty(int i, int i2, String str) {
        if (str != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                    if (profileEntry != null && profileEntry.mInterfaceName != null) {
                        boolean z = DBG;
                        if (z) {
                            Log.d("KnoxVpnEngineService", "unsetDnsSystemProperty: interface name is not null");
                        }
                        String str2 = profileEntry.mInterfaceName;
                        int i3 = profileEntry.mNetId;
                        if (i3 == 0) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        }
                        if (z) {
                            Log.d("KnoxVpnEngineService", "unsetDnsSystemProperty is reached : whose profileName is " + str + "whose start uid is " + i + " whose stop uid is " + i2 + " whose interface " + str2 + " whose netId is " + i3);
                        }
                        ArraySet arraySet = new ArraySet();
                        arraySet.add(new UidRangeParcel(i, i2));
                        getOemNetdService$1().networkRemoveUidRanges(i3, toUidRangeStableParcels(arraySet));
                    }
                } catch (Exception unused) {
                    Log.e("KnoxVpnEngineService", "Exception occured while trying to unset the dns entry for the profile ".concat(str));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void updateBlockingRules(String str) {
        ArrayList arrayList = new ArrayList();
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        String str2 = profileEntry.mInterfaceName;
        for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
            this.mKnoxVpnHelper.getClass();
            if (KnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                String packageName = vpnPackageInfo.getPackageName();
                knoxVpnHelper.getClass();
                this.mFirewallHelper.removeMiscRulesRange(KnoxVpnHelper.getContainerIdFromPackageName(packageName), getVirtualInterfaceType(str), str2);
            } else {
                arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
            }
        }
        if (!arrayList.isEmpty()) {
            this.mFirewallHelper.removeMiscRules(getVirtualInterfaceType(str), str2, arrayList);
        }
        profileEntry.mInterfaceName = null;
        profileEntry.mNetId = 0;
        addMiscRulesForProfile(str);
    }

    public final void updateNotification(int i, String str, boolean z) {
        VpnProfileInfo profileEntry;
        if (DBG) {
            Log.d("KnoxVpnEngineService", "updateNotification profileName : " + str + " , flag : " + z);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                profileEntry = this.vpnConfig.getProfileEntry(str);
            } catch (Exception e) {
                Log.e("KnoxVpnEngineService", "updateNotification : Exception : " + Log.getStackTraceString(e));
            }
            if (profileEntry == null) {
                return;
            }
            if (profileEntry.activateState == 1) {
                getVpnManagerService().updateEnterpriseVpn(str, i, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updatePackageData(int i, String str) {
        if (str != null) {
            try {
                Log.d("KnoxVpnEngineService", "Inside updatePackageData : packageName = ".concat(str));
                this.mVpnStorageProvider.getClass();
                if (KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{str}, new String[]{"profileName", "packageUid"}).size() > 0) {
                    Log.d("KnoxVpnEngineService", "update to package : Cursor not null and data present, so update packageData UID in DB");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("packageUid", Integer.valueOf(i));
                    String[] strArr = {"packageName"};
                    String[] strArr2 = {str};
                    Log.d("KnoxVpnEngineService", "update to package : update to package before DB insert:" + strArr2[0] + strArr[0]);
                    this.mVpnStorageProvider.getClass();
                    return KnoxVpnStorageProvider.mEDM.putDataByFields("VpnPackageInfo", strArr, strArr2, contentValues);
                }
            } catch (Exception e) {
                EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("update to package : Exception:"), "KnoxVpnEngineService");
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:147:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateProxyRules(final int r16, final java.lang.String r17, final java.util.HashMap r18) {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.updateProxyRules(int, java.lang.String, java.util.HashMap):void");
    }

    public final void updateRulesToExemptUid(int i, String str, String str2, String str3, int i2, int i3, int i4) {
        this.mFirewallHelper.getClass();
        List defaultRouteAppUidList = KnoxVpnFirewallHelper.getDefaultRouteAppUidList();
        try {
            if (i != 0) {
                if (i != 1) {
                    return;
                }
                if (i2 == 1) {
                    this.mFirewallHelper.addExemptRulesForUid(i3);
                } else if (i2 == 2) {
                    if (((ArrayList) defaultRouteAppUidList).contains(Integer.valueOf(i3))) {
                        Log.d("KnoxVpnEngineService", "Ignore applying exempt rule for uid " + i3);
                    } else {
                        this.mFirewallHelper.addIpRulesForExemptedUid(i3, i4, str3);
                    }
                } else if (i2 != 3) {
                } else {
                    addUidToExemptList(i3, i3, str, str2);
                }
            } else if (i2 == 1) {
                this.mFirewallHelper.removeExemptRulesForUid(i3);
            } else if (i2 == 2) {
                if (((ArrayList) defaultRouteAppUidList).contains(Integer.valueOf(i3))) {
                    Log.d("KnoxVpnEngineService", "Ignore removing exempt rule for uid " + i3);
                } else {
                    this.mFirewallHelper.removeIpRulesForExemptedUid(i3, i4);
                }
            } else if (i2 != 3) {
            } else {
                removeUidFromExemptList(i3, i3, str, str2);
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Error at updateRulesToExemtUid ", "KnoxVpnEngineService");
        }
    }

    public final int validateAdminAndVendorForProfile(KnoxVpnContext knoxVpnContext, String str, EnterpriseResponseData enterpriseResponseData) {
        int i = knoxVpnContext.adminId;
        String str2 = knoxVpnContext.vendorName;
        int i2 = knoxVpnContext.personaId;
        this.mKnoxVpnHelper.getClass();
        KnoxVpnHelper.getPersonifiedName(i2, str2);
        if (str == null || str2 == null) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "Admin check null for profile : ", str, " :admin = ", "KnoxVpnEngineService");
            enterpriseResponseData.setStatus(1, 7);
            return -1;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            Log.d(VpnProfileConfig.TAG, "KnoxVpn: Profile does not exist. profile : ".concat(str));
        } else {
            if (i == profileEntry.admin_id || UserHandle.getAppId(i) == 1000) {
                if (VpnProfileConfig.DBG) {
                    Log.d(VpnProfileConfig.TAG, "KnoxVpn: Profile admin validation success. profile : ".concat(str));
                }
                return 0;
            }
            Log.d(VpnProfileConfig.TAG, "KnoxVpn: Admin does not have permissions for this profile : ".concat(str));
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "Admin check failed for profile : ", str, " :admin = ", "KnoxVpnEngineService");
        enterpriseResponseData.setStatus(1, 8);
        return 1;
    }

    public final int writeAddAllPackageToDB(KnoxVpnContext knoxVpnContext, String str, String str2) {
        if (DBG) {
            Log.d("KnoxVpnEngineService", "writeAddAllPackageToDB:profileName = " + str2 + " :packageName = " + str);
        }
        int i = knoxVpnContext.adminId;
        int i2 = knoxVpnContext.personaId;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str2);
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageName", str);
            contentValues.put("profileName", str2);
            contentValues.put("packageUid", (Integer) (-2));
            contentValues.put("packageCid", Integer.valueOf(i2));
            this.mVpnStorageProvider.getClass();
            boolean putDataByFields = KnoxVpnStorageProvider.mEDM.putDataByFields("VpnPackageInfo", null, null, contentValues);
            Log.d("KnoxVpnEngineService", "writeAddAllPackageToDB: status value is" + putDataByFields);
            if (!putDataByFields) {
                return -1;
            }
            if (profileEntry == null) {
                return 0;
            }
            profileEntry.addPackageEntry(-2, i2, str);
            return 0;
        } catch (Exception e) {
            if (DBG) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at writeAddAllPackageToDB API"), "KnoxVpnEngineService");
            }
            return -1;
        }
    }

    public final int writePackageToDB(int i, int i2, String str, String str2) {
        int i3;
        boolean z = DBG;
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("write package DB : profileName = ", str, " :packageName = ", str2, " :cid = "), i2, "KnoxVpnEngineService");
        }
        try {
            this.mKnoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i2, str2);
            if (z) {
                Log.d("KnoxVpnEngineService", "write package DB : Transformed UID: " + i);
            }
            if (z) {
                Log.d("KnoxVpnEngineService", "write package DB : Transformed packagename before adding to db : " + personifiedName);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageName", personifiedName);
            contentValues.put("profileName", str);
            contentValues.put("packageUid", Integer.valueOf(i));
            contentValues.put("packageCid", Integer.valueOf(i2));
            this.mVpnStorageProvider.getClass();
            if (!KnoxVpnStorageProvider.mEDM.putDataByFields("VpnPackageInfo", null, null, contentValues)) {
                return 126;
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                profileEntry.addPackageEntry(i, i2, personifiedName);
            }
            if (i != -1) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(Integer.valueOf(i));
                i3 = startVpnForPerApplication(str, arrayList, true);
            } else {
                i3 = 0;
            }
            if (z) {
                printProfileVpnMap();
            }
            return i3;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("write package DB : Exception occured for adding vpn, package map."), "KnoxVpnEngineService");
            return -1;
        }
    }

    public final void writePackagestoPermissionCheckDb(int i, int i2, String str, String str2) {
        try {
            this.mKnoxVpnHelper.getClass();
            String personifiedName = KnoxVpnHelper.getPersonifiedName(i, str2);
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i2)}, new String[]{"packageUid"}).size() > 0) {
                Log.e("KnoxVpnEngineService", "Error adding the package to permission check db since it is already added");
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("profileName", str);
            contentValues.put("packageName", personifiedName);
            contentValues.put("packageUid", Integer.valueOf(i2));
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.putDataByFields("vpnNoInternetPermission", null, null, contentValues)) {
                return;
            }
            Log.e("KnoxVpnEngineService", "Error adding the package info to permission exempt list in db");
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at writePackagestoPermissionCheckDb API "), "KnoxVpnEngineService");
        }
    }
}
