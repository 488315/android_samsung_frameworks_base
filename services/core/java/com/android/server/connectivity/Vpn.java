package com.android.server.connectivity;

import android.R;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.DnsResolver;
import android.net.INetd;
import android.net.INetworkManagementEventObserver;
import android.net.Ikev2VpnProfile;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.IpSecManager;
import android.net.IpSecTransform;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkAgentConfig;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkProvider;
import android.net.NetworkRequest;
import android.net.NetworkScore;
import android.net.NetworkSpecifier;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.TelephonyNetworkSpecifier;
import android.net.UidRangeParcel;
import android.net.UnderlyingNetworkInfo;
import android.net.Uri;
import android.net.VpnProfileState;
import android.net.VpnTransportInfo;
import android.net.ipsec.ike.ChildSessionCallback;
import android.net.ipsec.ike.ChildSessionConfiguration;
import android.net.ipsec.ike.ChildSessionParams;
import android.net.ipsec.ike.IkeSession;
import android.net.ipsec.ike.IkeSessionCallback;
import android.net.ipsec.ike.IkeSessionConfiguration;
import android.net.ipsec.ike.IkeSessionConnectionInfo;
import android.net.ipsec.ike.IkeSessionParams;
import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.net.ipsec.ike.exceptions.IkeIOException;
import android.net.ipsec.ike.exceptions.IkeNetworkLostException;
import android.net.ipsec.ike.exceptions.IkeNonProtocolException;
import android.net.ipsec.ike.exceptions.IkeProtocolException;
import android.net.ipsec.ike.exceptions.IkeTimeoutException;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.SystemService;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.Credentials;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.system.keystore2.KeyDescriptor;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Base64;
import android.util.IndentingPrintWriter;
import android.util.LocalLog;
import android.util.Log;
import android.util.Range;
import android.util.SparseArray;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.build.SdkLevel;
import com.android.net.module.util.BinderUtils;
import com.android.net.module.util.LinkPropertiesUtils;
import com.android.net.module.util.NetdUtils;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.connectivity.Vpn;
import com.android.server.connectivity.VpnIkev2Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.net.BaseNetworkObserver;
import com.android.server.vcn.util.MtuUtils;
import com.android.server.vcn.util.PersistableBundleUtils;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.net.vpn.VpnPolicy;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.ucmRetParcelable;
import com.samsung.android.knoxguard.KnoxGuardManager;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class Vpn {
    public static final int AUTOMATIC_KEEPALIVE_DELAY_SECONDS = 30;
    static final int DEFAULT_LONG_LIVED_TCP_CONNS_EXPENSIVE_TIMEOUT_SEC = 60;
    static final int DEFAULT_UDP_PORT_4500_NAT_TIMEOUT_SEC_INT = 300;
    static final int MAX_VPN_PROFILE_SIZE_BYTES = 131072;
    public static final int PREFERRED_IKE_PROTOCOL_AUTO = 0;
    public static final int PREFERRED_IKE_PROTOCOL_IPV4_UDP = 40;
    public static final int PREFERRED_IKE_PROTOCOL_IPV6_ESP = 61;
    public static final int PREFERRED_IKE_PROTOCOL_IPV6_UDP = 60;
    static final String VPN_APP_EXCLUDED = "VPNAPPEXCLUDED_";
    public final int KNOXVPN_CONTAINER_ENABLED;
    public int KNOXVPN_FEATURE;
    public final int KNOXVPN_MDM_ENABLED;
    public final Set knoxVpnUidRanges;
    public String mAddress;
    protected boolean mAlwaysOn;
    public final AppOpsManager mAppOpsManager;
    public final Set mBlockedUidsAsToldToConnectivity;
    public final SparseArray mCachedCarrierConfigInfoPerSubId;
    public final CarrierConfigManager mCarrierConfigManager;
    protected VpnConfig mConfig;
    public Connection mConnection;
    public final ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public boolean mDataStallSuspected;
    final Dependencies mDeps;
    public EnterpriseDeviceManager mEDM;
    public String mEgressIface;
    public volatile boolean mEnableTeardown;
    public final LocalLog mEventChanges;
    public ProxyInfo mHttpProxyInfo;
    public final Ikev2SessionCreator mIkev2SessionCreator;
    protected String mInterface;
    public boolean mIsPackageTargetingAtLeastQ;
    public String mLegacyAddress;
    public int mLegacyState;
    protected boolean mLockdown;
    public List mLockdownAllowlist;
    public final Looper mLooper;
    public NetworkAgent mMockNetworkAgent;
    public final INetd mNetd;
    protected NetworkAgent mNetworkAgent;
    protected NetworkCapabilities mNetworkCapabilities;
    public final NetworkInfo mNetworkInfo;
    public final NetworkProvider mNetworkProvider;
    public final INetworkManagementService mNms;
    public NotificationManager mNotificationManager;
    public INetworkManagementEventObserver mObserver;
    public int mOwnerUID;
    protected String mPackage;
    public String mProfileName;
    public PendingIntent mStatusIntent;
    public final SubscriptionManager mSubscriptionManager;
    public VpnConfig mSwifiConfig;
    public final SystemServices mSystemServices;
    public final TelephonyManager mTelephonyManager;
    public final int mUserId;
    public final Context mUserIdContext;
    public final UserManager mUserManager;
    public String mV6Address;
    public ConnectivityManager.NetworkCallback mVpnNetworkCallback;
    public VpnPolicy mVpnPolicy;
    public final VpnProfileStore mVpnProfileStore;
    public VpnRules mVpnRules;
    protected VpnRunner mVpnRunner;
    public static final long[] IKEV2_VPN_RETRY_DELAYS_MS = {1000, 2000, 5000, 30000, 60000, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, 900000};
    public static final long[] DATA_STALL_RESET_DELAYS_SEC = {30, 60, 120, 240, 480, 960};
    public static final boolean DBG = Debug.semIsProductDev();

    public interface IkeV2VpnRunnerCallback {
        void onChildMigrated(int i, IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2);

        void onChildOpened(int i, ChildSessionConfiguration childSessionConfiguration);

        void onChildTransformCreated(int i, IpSecTransform ipSecTransform, int i2);

        void onDefaultNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities);

        void onDefaultNetworkChanged(Network network);

        void onDefaultNetworkLinkPropertiesChanged(LinkProperties linkProperties);

        void onDefaultNetworkLost(Network network);

        void onIkeConnectionInfoChanged(int i, IkeSessionConnectionInfo ikeSessionConnectionInfo);

        void onIkeOpened(int i, IkeSessionConfiguration ikeSessionConfiguration);

        void onSessionLost(int i, Exception exc);
    }

    public interface RetryScheduler {
        void checkInterruptAndDelay(boolean z);
    }

    interface ValidationStatusCallback {
        void onValidationStatus(int i);
    }

    public static boolean areLongLivedTcpConnectionsExpensive(int i) {
        return i < 60;
    }

    private native boolean jniAddAddress(String str, String str2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCheck(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCreate(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCreateSecureWifi(int i);

    private native boolean jniDelAddress(String str, String str2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native String jniGetName(int i);

    private native void jniReset(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniSetAddresses(String str, String str2);

    public final void dismissConnectingNotification() {
    }

    public final String getVpnManagerEventClassName(int i) {
        return i != 1 ? i != 2 ? "UNKNOWN_CLASS" : "ERROR_CLASS_RECOVERABLE" : "ERROR_CLASS_NOT_RECOVERABLE";
    }

    public final String getVpnManagerEventErrorName(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN_ERROR" : "ERROR_CODE_NETWORK_IO" : "ERROR_CODE_NETWORK_LOST" : "ERROR_CODE_NETWORK_PROTOCOL_TIMEOUT" : "ERROR_CODE_NETWORK_UNKNOWN_HOST";
    }

    public final String getVpnTypeString(int i) {
        switch (i) {
            case 0:
                return "PPTP";
            case 1:
                return "L2TP_IPSEC_PSK";
            case 2:
                return "L2TP_IPSEC_RSA";
            case 3:
                return "IPSEC_XAUTH_PSK";
            case 4:
                return "IPSEC_XAUTH_RSA";
            case 5:
                return "IPSEC_HYBRID_RSA";
            case 6:
            default:
                return "";
            case 7:
                return "IPSEC_IKEV2_PSK";
            case 8:
                return "IPSEC_IKEV2_RSA";
        }
    }

    public VpnProfileStore getVpnProfileStore() {
        return this.mVpnProfileStore;
    }

    public final boolean isSecureWifiPackage() {
        if (!"com.samsung.android.fast".equals(this.mPackage)) {
            return false;
        }
        if (this.mContext.getPackageManager().checkSignatures("android", "com.samsung.android.fast") == 0) {
            return true;
        }
        Log.e("Vpn", "Secure Wi-Fi signature mismatched");
        return false;
    }

    public final void broadcastVpnState(NetworkInfo.DetailedState detailedState, String str) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.vpn.action.VPN_STATE_CHANGED");
        intent.setPackage("com.android.settings");
        intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, detailedState.name());
        intent.putExtra("reason", str);
        this.mContext.sendBroadcast(intent, "android.permission.NETWORK_SETTINGS");
        Log.d("Vpn", "send state=" + detailedState + ", reason=" + str);
    }

    public class CarrierConfigInfo {
        public final int encapType;
        public final int ipVersion;
        public final int keepaliveDelaySec;
        public final String mccMnc;

        public CarrierConfigInfo(String str, int i, int i2, int i3) {
            this.mccMnc = str;
            this.keepaliveDelaySec = i;
            this.encapType = i2;
            this.ipVersion = i3;
        }

        public String toString() {
            return "CarrierConfigInfo(" + this.mccMnc + ") [keepaliveDelaySec=" + this.keepaliveDelaySec + ", encapType=" + this.encapType + ", ipVersion=" + this.ipVersion + "]";
        }
    }

    public class Dependencies {
        public boolean isCallerSystem() {
            return Binder.getCallingUid() == 1000;
        }

        public void startService(String str) {
            SystemService.start(str);
        }

        public void stopService(String str) {
            SystemService.stop(str);
        }

        public boolean isServiceRunning(String str) {
            return SystemService.isRunning(str);
        }

        public boolean isServiceStopped(String str) {
            return SystemService.isStopped(str);
        }

        public File getStateFile() {
            return new File("/data/misc/vpn/state");
        }

        public DeviceIdleInternal getDeviceIdleInternal() {
            return (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
        }

        public PendingIntent getIntentForStatusPanel(Context context) {
            return VpnConfig.getIntentForStatusPanel(context);
        }

        public void sendArgumentsToDaemon(String str, LocalSocket localSocket, String[] strArr, RetryScheduler retryScheduler) {
            LocalSocketAddress localSocketAddress = new LocalSocketAddress(str, LocalSocketAddress.Namespace.RESERVED);
            while (true) {
                try {
                    localSocket.connect(localSocketAddress);
                    break;
                } catch (Exception unused) {
                    retryScheduler.checkInterruptAndDelay(true);
                }
            }
            localSocket.setSoTimeout(500);
            OutputStream outputStream = localSocket.getOutputStream();
            for (String str2 : strArr) {
                byte[] bytes = str2.getBytes(StandardCharsets.UTF_8);
                if (bytes.length >= 65535) {
                    throw new IllegalArgumentException("Argument is too large");
                }
                outputStream.write(bytes.length >> 8);
                outputStream.write(bytes.length);
                outputStream.write(bytes);
                retryScheduler.checkInterruptAndDelay(false);
            }
            outputStream.write(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            outputStream.write(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            if ("charon".equals(str)) {
                return;
            }
            InputStream inputStream = localSocket.getInputStream();
            while (inputStream.read() != -1) {
                retryScheduler.checkInterruptAndDelay(true);
            }
        }

        public InetAddress resolve(final String str) {
            try {
                return InetAddresses.parseNumericAddress(str);
            } catch (IllegalArgumentException unused) {
                CancellationSignal cancellationSignal = new CancellationSignal();
                try {
                    DnsResolver dnsResolver = DnsResolver.getInstance();
                    final CompletableFuture completableFuture = new CompletableFuture();
                    dnsResolver.query(null, str, 0, new Executor() { // from class: com.android.server.connectivity.Vpn$Dependencies$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Executor
                        public final void execute(Runnable runnable) {
                            runnable.run();
                        }
                    }, cancellationSignal, new DnsResolver.Callback() { // from class: com.android.server.connectivity.Vpn.Dependencies.1
                        @Override // android.net.DnsResolver.Callback
                        public void onAnswer(List list, int i) {
                            if (list.size() > 0) {
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    if (list.get(i2) instanceof Inet4Address) {
                                        Log.i("Vpn", "Resolved address: " + str + " > " + list.get(i2));
                                        completableFuture.complete((InetAddress) list.get(i2));
                                        return;
                                    }
                                }
                                completableFuture.complete((InetAddress) list.get(0));
                                return;
                            }
                            completableFuture.completeExceptionally(new UnknownHostException(str));
                        }

                        @Override // android.net.DnsResolver.Callback
                        public void onError(DnsResolver.DnsException dnsException) {
                            Log.e("Vpn", "Async dns resolver error : " + dnsException);
                            completableFuture.completeExceptionally(new UnknownHostException(str));
                        }
                    });
                    return (InetAddress) completableFuture.get();
                } catch (InterruptedException e) {
                    Log.e("Vpn", "Legacy VPN was interrupted while resolving the endpoint", e);
                    cancellationSignal.cancel();
                    throw e;
                } catch (ExecutionException e2) {
                    Log.e("Vpn", "Cannot resolve VPN endpoint : " + str + ".", e2);
                    throw e2;
                }
            }
        }

        public boolean isInterfacePresent(Vpn vpn, String str) {
            return vpn.jniCheck(str) != 0;
        }

        public ParcelFileDescriptor adoptFd(Vpn vpn, int i) {
            return ParcelFileDescriptor.adoptFd(jniCreate(vpn, i));
        }

        public int jniCreate(Vpn vpn, int i) {
            if (vpn.isSecureWifiPackage()) {
                return vpn.jniCreateSecureWifi(i);
            }
            return vpn.jniCreate(i);
        }

        public String jniGetName(Vpn vpn, int i) {
            return vpn.jniGetName(i);
        }

        public int jniSetAddresses(Vpn vpn, String str, String str2) {
            return vpn.jniSetAddresses(str, str2);
        }

        public void setBlocking(FileDescriptor fileDescriptor, boolean z) {
            try {
                IoUtils.setBlocking(fileDescriptor, z);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot set tunnel's fd as blocking=" + z, e);
            }
        }

        public long getNextRetryDelayMs(int i) {
            if (i >= Vpn.IKEV2_VPN_RETRY_DELAYS_MS.length) {
                return Vpn.IKEV2_VPN_RETRY_DELAYS_MS[Vpn.IKEV2_VPN_RETRY_DELAYS_MS.length - 1];
            }
            return Vpn.IKEV2_VPN_RETRY_DELAYS_MS[i];
        }

        public ScheduledThreadPoolExecutor newScheduledThreadPoolExecutor() {
            return new ScheduledThreadPoolExecutor(1);
        }

        public NetworkAgent newNetworkAgent(Context context, Looper looper, String str, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, NetworkScore networkScore, NetworkAgentConfig networkAgentConfig, NetworkProvider networkProvider, ValidationStatusCallback validationStatusCallback) {
            return new VpnNetworkAgentWrapper(context, looper, str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider, validationStatusCallback);
        }

        public long getDataStallResetSessionSeconds(int i) {
            if (i >= Vpn.DATA_STALL_RESET_DELAYS_SEC.length) {
                return Vpn.DATA_STALL_RESET_DELAYS_SEC[Vpn.DATA_STALL_RESET_DELAYS_SEC.length - 1];
            }
            return Vpn.DATA_STALL_RESET_DELAYS_SEC[i];
        }

        public int getJavaNetworkInterfaceMtu(String str, int i) {
            NetworkInterface byName;
            return (str == null || (byName = NetworkInterface.getByName(str)) == null) ? i : byName.getMTU();
        }

        public int calculateVpnMtu(List list, int i, int i2, boolean z) {
            return MtuUtils.getMtu(list, i, i2, z);
        }

        public void verifyCallingUidAndPackage(Context context, String str, int i) {
            int callingUid = Binder.getCallingUid();
            if (Vpn.getAppUid(context, str, i) == callingUid) {
                return;
            }
            throw new SecurityException(str + " does not belong to uid " + callingUid);
        }
    }

    public Vpn(Looper looper, Context context, INetworkManagementService iNetworkManagementService, INetd iNetd, int i, VpnProfileStore vpnProfileStore) {
        this(looper, context, new Dependencies(), iNetworkManagementService, iNetd, i, vpnProfileStore, new SystemServices(context), new Ikev2SessionCreator());
    }

    public Vpn(Looper looper, Context context, Dependencies dependencies, INetworkManagementService iNetworkManagementService, INetd iNetd, int i, VpnProfileStore vpnProfileStore) {
        this(looper, context, dependencies, iNetworkManagementService, iNetd, i, vpnProfileStore, new SystemServices(context), new Ikev2SessionCreator());
    }

    public Vpn(Looper looper, Context context, Dependencies dependencies, INetworkManagementService iNetworkManagementService, INetd iNetd, int i, VpnProfileStore vpnProfileStore, SystemServices systemServices, Ikev2SessionCreator ikev2SessionCreator) {
        this.mEnableTeardown = true;
        this.mProfileName = null;
        this.mAddress = null;
        this.mV6Address = null;
        this.KNOXVPN_FEATURE = -1;
        this.KNOXVPN_MDM_ENABLED = 1;
        this.KNOXVPN_CONTAINER_ENABLED = 2;
        this.mHttpProxyInfo = null;
        this.mDataStallSuspected = false;
        this.mEventChanges = new LocalLog(100);
        this.mCachedCarrierConfigInfoPerSubId = new SparseArray();
        this.mAlwaysOn = false;
        this.mLockdown = false;
        this.mLockdownAllowlist = Collections.emptyList();
        this.mBlockedUidsAsToldToConnectivity = new ArraySet();
        this.mObserver = new BaseNetworkObserver() { // from class: com.android.server.connectivity.Vpn.2
            public void interfaceStatusChanged(String str, boolean z) {
                synchronized (Vpn.this) {
                    if (!z) {
                        VpnRunner vpnRunner = Vpn.this.mVpnRunner;
                        if (vpnRunner != null && (vpnRunner instanceof LegacyVpnRunner)) {
                            ((LegacyVpnRunner) vpnRunner).exitIfOuterInterfaceIs(str);
                        }
                    }
                }
            }

            public void interfaceRemoved(String str) {
                synchronized (Vpn.this) {
                    if (str.equals(Vpn.this.mInterface) && Vpn.this.jniCheck(str) == 0) {
                        if (Vpn.this.mConnection != null) {
                            Vpn.this.mAppOpsManager.finishOp("android:establish_vpn_service", Vpn.this.mOwnerUID, Vpn.this.mPackage, null);
                            Vpn.this.mContext.unbindService(Vpn.this.mConnection);
                            Vpn.this.cleanupVpnStateLocked();
                        } else {
                            Vpn vpn = Vpn.this;
                            if (vpn.mVpnRunner != null) {
                                if (!"[Legacy VPN]".equals(vpn.mPackage)) {
                                    Vpn.this.mAppOpsManager.finishOp("android:establish_vpn_manager", Vpn.this.mOwnerUID, Vpn.this.mPackage, null);
                                }
                                Vpn.this.mVpnRunner.exit();
                            }
                        }
                    }
                }
            }
        };
        this.mNotificationManager = null;
        this.knoxVpnUidRanges = new ArraySet();
        this.mVpnProfileStore = vpnProfileStore;
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mUserIdContext = context.createContextAsUser(UserHandle.of(i), 0);
        this.mConnectivityDiagnosticsManager = (ConnectivityDiagnosticsManager) context.getSystemService(ConnectivityDiagnosticsManager.class);
        this.mCarrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mSubscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mDeps = dependencies;
        this.mNms = iNetworkManagementService;
        this.mNetd = iNetd;
        this.mUserId = i;
        this.mLooper = looper;
        this.mSystemServices = systemServices;
        this.mIkev2SessionCreator = ikev2SessionCreator;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mPackage = "[Legacy VPN]";
        this.mOwnerUID = getAppUid(context, "[Legacy VPN]", i);
        this.mIsPackageTargetingAtLeastQ = doesPackageTargetAtLeastQ(this.mPackage);
        this.mVpnRules = new VpnRules();
        try {
            iNetworkManagementService.registerObserver(this.mObserver);
        } catch (RemoteException e) {
            Log.wtf("Vpn", "Problem registering observer", e);
        }
        NetworkProvider networkProvider = new NetworkProvider(context, looper, "VpnNetworkProvider:" + this.mUserId);
        this.mNetworkProvider = networkProvider;
        this.mConnectivityManager.registerNetworkProvider(networkProvider);
        this.mLegacyState = 0;
        this.mNetworkInfo = new NetworkInfo(17, 0, "VPN", "");
        this.mNetworkCapabilities = new NetworkCapabilities.Builder().addTransportType(4).removeCapability(15).addCapability(28).setTransportInfo(new VpnTransportInfo(-1, (String) null, false, false)).build();
        loadAlwaysOnPackage();
    }

    public void setEnableTeardown(boolean z) {
        this.mEnableTeardown = z;
    }

    public boolean getEnableTeardown() {
        return this.mEnableTeardown;
    }

    public void updateState(NetworkInfo.DetailedState detailedState, String str) {
        Log.d("Vpn", "setting state=" + detailedState + ", reason=" + str);
        this.mLegacyState = LegacyVpnInfo.stateFromNetworkInfo(detailedState);
        this.mNetworkInfo.setDetailedState(detailedState, str, null);
        int i = AbstractC10504.$SwitchMap$android$net$NetworkInfo$DetailedState[detailedState.ordinal()];
        if (i == 1) {
            NetworkAgent networkAgent = this.mNetworkAgent;
            if (networkAgent != null) {
                networkAgent.markConnected();
            }
        } else if (i == 2 || i == 3) {
            NetworkAgent networkAgent2 = this.mNetworkAgent;
            if (networkAgent2 != null) {
                networkAgent2.unregister();
                this.mNetworkAgent = null;
            }
            unregisterMockNetworkAgent();
        } else if (i == 4) {
            if (this.mNetworkAgent != null) {
                throw new IllegalStateException("VPN can only go to CONNECTING state when the agent is null.");
            }
        } else {
            throw new IllegalArgumentException("Illegal state argument " + detailedState);
        }
        updateAlwaysOnNotification(detailedState);
    }

    /* renamed from: com.android.server.connectivity.Vpn$4 */
    public abstract /* synthetic */ class AbstractC10504 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState;

        static {
            int[] iArr = new int[NetworkInfo.DetailedState.values().length];
            $SwitchMap$android$net$NetworkInfo$DetailedState = iArr;
            try {
                iArr[NetworkInfo.DetailedState.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CONNECTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public final void resetNetworkCapabilities() {
        this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).setTransportInfo(new VpnTransportInfo(-1, (String) null, false, false)).build();
    }

    public synchronized void setLockdown(boolean z) {
        enforceControlPermissionOrInternalCaller();
        setVpnForcedLocked(z);
        this.mLockdown = z;
        if (this.mAlwaysOn) {
            saveAlwaysOnPackage();
        }
    }

    public synchronized String getPackage() {
        return this.mPackage;
    }

    public synchronized boolean getLockdown() {
        return this.mLockdown;
    }

    public synchronized boolean getAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isAlwaysOnPackageSupported(String str) {
        ApplicationInfo applicationInfo;
        enforceSettingsPermission();
        if (str == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (getVpnProfilePrivileged(str) != null) {
                return true;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfoAsUser(str, 0, this.mUserId);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("Vpn", "Can't find \"" + str + "\" when checking always-on support");
                applicationInfo = null;
            }
            if (applicationInfo != null && applicationInfo.targetSdkVersion >= 24) {
                Intent intent = new Intent("android.net.VpnService");
                intent.setPackage(str);
                List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, this.mUserId);
                if (queryIntentServicesAsUser != null && queryIntentServicesAsUser.size() != 0) {
                    Iterator it = queryIntentServicesAsUser.iterator();
                    while (it.hasNext()) {
                        Bundle bundle = ((ResolveInfo) it.next()).serviceInfo.metaData;
                        if (bundle != null && !bundle.getBoolean("android.net.VpnService.SUPPORTS_ALWAYS_ON", true)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Intent buildVpnManagerEventIntent(String str, int i, int i2, String str2, String str3, VpnProfileState vpnProfileState, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        Log.d("Vpn", "buildVpnManagerEventIntent: sessionKey = " + str3);
        Intent intent = new Intent("android.net.action.VPN_MANAGER_EVENT");
        intent.setPackage(str2);
        intent.addCategory(str);
        intent.putExtra("android.net.extra.VPN_PROFILE_STATE", vpnProfileState);
        intent.putExtra("android.net.extra.SESSION_KEY", str3);
        intent.putExtra("android.net.extra.UNDERLYING_NETWORK", network);
        intent.putExtra("android.net.extra.UNDERLYING_NETWORK_CAPABILITIES", networkCapabilities);
        intent.putExtra("android.net.extra.UNDERLYING_LINK_PROPERTIES", linkProperties);
        intent.putExtra("android.net.extra.TIMESTAMP_MILLIS", System.currentTimeMillis());
        if (!"android.net.category.EVENT_DEACTIVATED_BY_USER".equals(str) || !"android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED".equals(str)) {
            intent.putExtra("android.net.extra.ERROR_CLASS", i);
            intent.putExtra("android.net.extra.ERROR_CODE", i2);
        }
        return intent;
    }

    public final boolean sendEventToVpnManagerApp(String str, int i, int i2, String str2, String str3, VpnProfileState vpnProfileState, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        this.mEventChanges.log("[VMEvent] Event class=" + getVpnManagerEventClassName(i) + ", err=" + getVpnManagerEventErrorName(i2) + " for " + str2 + " on session " + str3);
        return sendEventToVpnManagerApp(buildVpnManagerEventIntent(str, i, i2, str2, str3, vpnProfileState, network, networkCapabilities, linkProperties), str2);
    }

    public final boolean sendEventToVpnManagerApp(Intent intent, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeps.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(Process.myUid(), str, 30000L, this.mUserId, false, 309, "VpnManager event");
            return this.mUserIdContext.startService(intent) != null;
        } catch (RuntimeException e) {
            Log.e("Vpn", "Service of VpnManager app " + intent + " failed to start", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isVpnApp(String str) {
        return (str == null || "[Legacy VPN]".equals(str)) ? false : true;
    }

    public synchronized boolean setAlwaysOnPackage(String str, boolean z, List list) {
        VpnProfileState makeVpnProfileStateLocked;
        enforceControlPermissionOrInternalCaller();
        String str2 = this.mPackage;
        boolean z2 = !Objects.equals(str, str2);
        boolean z3 = isVpnApp(str2) && this.mAlwaysOn && (z != this.mLockdown || z2);
        boolean z4 = isVpnApp(str) && z2;
        if (KnoxGuardManager.getInstance().isVpnExceptionRequired()) {
            if (list == null) {
                list = new ArrayList();
            }
            Log.i("Vpn", "add KG to exempted list for AlwaysOnVpn");
            list.add(KnoxCustomManagerService.KG_PKG_NAME);
        }
        if (!setAlwaysOnPackageInternal(str, z, list)) {
            return false;
        }
        saveAlwaysOnPackage();
        if (!SdkLevel.isAtLeastT()) {
            return true;
        }
        if (z3) {
            if (z2) {
                makeVpnProfileStateLocked = makeDisconnectedVpnProfileState();
            } else {
                makeVpnProfileStateLocked = makeVpnProfileStateLocked();
            }
            sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str2, null, makeVpnProfileStateLocked, null, null, null);
        }
        if (z4) {
            sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null);
        }
        return true;
    }

    public final boolean setAlwaysOnPackageInternal(String str, boolean z, List list) {
        List emptyList;
        boolean z2 = false;
        if ("[Legacy VPN]".equals(str)) {
            Log.w("Vpn", "Not setting legacy VPN \"" + str + "\" as always-on.");
            return false;
        }
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2.contains(",")) {
                    Log.w("Vpn", "Not setting always-on vpn, invalid allowed package: " + str2);
                    return false;
                }
            }
        }
        if (str != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!setPackageAuthorization(str, getVpnProfilePrivileged(str) == null ? 1 : 2)) {
                    return false;
                }
                this.mAlwaysOn = true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            this.mAlwaysOn = false;
            str = "[Legacy VPN]";
        }
        boolean z3 = this.mLockdown;
        if (this.mAlwaysOn && z) {
            z2 = true;
        }
        this.mLockdown = z2;
        if (z2 && list != null) {
            emptyList = Collections.unmodifiableList(new ArrayList(list));
        } else {
            emptyList = Collections.emptyList();
        }
        this.mLockdownAllowlist = emptyList;
        this.mEventChanges.log("[LockdownAlwaysOn] Mode changed: lockdown=" + this.mLockdown + " alwaysOn=" + this.mAlwaysOn + " calling from " + Binder.getCallingUid());
        if (isCurrentPreparedPackage(str)) {
            updateAlwaysOnNotification(this.mNetworkInfo.getDetailedState());
            setVpnForcedLocked(this.mLockdown);
            NetworkAgent networkAgent = this.mNetworkAgent;
            if (networkAgent != null && z3 != this.mLockdown) {
                startNewNetworkAgent(networkAgent, "Lockdown mode changed");
            }
        } else {
            prepareInternal(str);
        }
        return true;
    }

    public static boolean isNullOrLegacyVpn(String str) {
        return str == null || "[Legacy VPN]".equals(str);
    }

    public synchronized String getAlwaysOnPackage() {
        enforceControlPermissionOrInternalCaller();
        return this.mAlwaysOn ? this.mPackage : null;
    }

    public synchronized List getLockdownAllowlist() {
        return this.mLockdown ? this.mLockdownAllowlist : null;
    }

    public final void saveAlwaysOnPackage() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSystemServices.settingsSecurePutStringForUser("always_on_vpn_app", getAlwaysOnPackage(), this.mUserId);
            this.mSystemServices.settingsSecurePutIntForUser("always_on_vpn_lockdown", (this.mAlwaysOn && this.mLockdown) ? 1 : 0, this.mUserId);
            this.mSystemServices.settingsSecurePutStringForUser("always_on_vpn_lockdown_whitelist", String.join(",", this.mLockdownAllowlist), this.mUserId);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void loadAlwaysOnPackage() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String str = this.mSystemServices.settingsSecureGetStringForUser("always_on_vpn_app", this.mUserId);
            boolean z = this.mSystemServices.settingsSecureGetIntForUser("always_on_vpn_lockdown", 0, this.mUserId) != 0;
            String str2 = this.mSystemServices.settingsSecureGetStringForUser("always_on_vpn_lockdown_whitelist", this.mUserId);
            setAlwaysOnPackageInternal(str, z, TextUtils.isEmpty(str2) ? Collections.emptyList() : Arrays.asList(str2.split(",")));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean startAlwaysOnVpn() {
        synchronized (this) {
            String alwaysOnPackage = getAlwaysOnPackage();
            if (alwaysOnPackage == null) {
                return true;
            }
            if (!isAlwaysOnPackageSupported(alwaysOnPackage)) {
                setAlwaysOnPackage(null, false, null);
                return false;
            }
            if (getNetworkInfo().isConnected()) {
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(alwaysOnPackage);
                if (vpnProfilePrivileged != null) {
                    startVpnProfilePrivileged(vpnProfilePrivileged, alwaysOnPackage);
                    return true;
                }
                this.mDeps.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(Process.myUid(), alwaysOnPackage, 60000L, this.mUserId, false, 309, "vpn");
                Intent intent = new Intent("android.net.VpnService");
                intent.setPackage(alwaysOnPackage);
                try {
                    return this.mUserIdContext.startService(intent) != null;
                } catch (RuntimeException e) {
                    Log.e("Vpn", "VpnService " + intent + " failed to start", e);
                    return false;
                }
            } catch (Exception e2) {
                Log.e("Vpn", "Error starting always-on VPN", e2);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public synchronized boolean prepare(String str, String str2, int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CONTROL_VPN") != 0) {
            if (str != null) {
                verifyCallingUidAndPackage(str);
            }
            if (str2 != null) {
                verifyCallingUidAndPackage(str2);
            }
        }
        if (str != null) {
            if (this.mAlwaysOn && !isCurrentPreparedPackage(str)) {
                return false;
            }
            if (!isCurrentPreparedPackage(str)) {
                if (str.equals("[Legacy VPN]") || !isVpnPreConsented(this.mContext, str, i)) {
                    return false;
                }
                prepareInternal(str);
                return true;
            }
            if (!str.equals("[Legacy VPN]") && !isVpnPreConsented(this.mContext, str, i)) {
                prepareInternal("[Legacy VPN]");
                return false;
            }
        }
        if (str2 != null && (str2.equals("[Legacy VPN]") || !isCurrentPreparedPackage(str2))) {
            if (str != null) {
                if (str.equalsIgnoreCase("[Legacy VPN]") && str2.equalsIgnoreCase("[Legacy VPN]")) {
                    if (Binder.getCallingUid() == 1000 && Binder.getCallingPid() == Process.myPid()) {
                        enforceControlPermissionOrInternalCaller();
                    } else {
                        enforceControlPermission();
                    }
                } else {
                    enforceControlPermission();
                }
            } else {
                enforceControlPermission();
            }
            if (this.mAlwaysOn && !isCurrentPreparedPackage(str2)) {
                return false;
            }
            prepareInternal(str2);
            return true;
        }
        return true;
    }

    public final boolean isCurrentPreparedPackage(String str) {
        return getAppUid(this.mContext, str, this.mUserId) == this.mOwnerUID && this.mPackage.equals(str);
    }

    public final void prepareInternal(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mInterface != null) {
                this.mStatusIntent = null;
                agentDisconnect();
                jniReset(this.mInterface);
                this.mInterface = null;
                resetNetworkCapabilities();
            }
            Connection connection = this.mConnection;
            if (connection != null) {
                try {
                    connection.mService.transact(16777215, Parcel.obtain(), null, 1);
                } catch (Exception unused) {
                }
                this.mAppOpsManager.finishOp("android:establish_vpn_service", this.mOwnerUID, this.mPackage, null);
                this.mContext.unbindService(this.mConnection);
                cleanupVpnStateLocked();
            } else if (this.mVpnRunner != null) {
                stopVpnRunnerAndNotifyAppLocked();
            }
            try {
                this.mNms.denyProtect(this.mOwnerUID);
            } catch (Exception e) {
                Log.wtf("Vpn", "Failed to disallow UID " + this.mOwnerUID + " to call protect() " + e);
            }
            Log.i("Vpn", "Switched from " + this.mPackage + " to " + str);
            this.mPackage = str;
            this.mOwnerUID = getAppUid(this.mContext, str, this.mUserId);
            this.mIsPackageTargetingAtLeastQ = doesPackageTargetAtLeastQ(str);
            try {
                this.mNms.allowProtect(this.mOwnerUID);
            } catch (Exception e2) {
                Log.wtf("Vpn", "Failed to allow UID " + this.mOwnerUID + " to call protect() " + e2);
            }
            this.mConfig = null;
            updateState(NetworkInfo.DetailedState.DISCONNECTED, "prepare");
            setVpnForcedLocked(this.mLockdown);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setPackageAuthorization(String str, int i) {
        String[] strArr;
        enforceControlPermissionOrInternalCaller();
        int appUid = getAppUid(this.mContext, str, this.mUserId);
        if (appUid == -1 || "[Legacy VPN]".equals(str)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (i == -1) {
                strArr = new String[]{"android:activate_vpn", "android:activate_platform_vpn"};
            } else if (i == 1) {
                strArr = new String[]{"android:activate_vpn"};
            } else {
                if (i != 2) {
                    if (i == 3) {
                        return false;
                    }
                    Log.wtf("Vpn", "Unrecognized VPN type while granting authorization");
                    return false;
                }
                strArr = new String[]{"android:activate_platform_vpn"};
            }
            for (String str2 : strArr) {
                this.mAppOpsManager.setMode(str2, appUid, str, i == -1 ? 1 : 0);
            }
            return true;
        } catch (Exception e) {
            Log.wtf("Vpn", "Failed to set app ops for package " + str + ", uid " + appUid, e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isVpnPreConsented(Context context, String str, int i) {
        if (i == 1) {
            return isVpnServicePreConsented(context, str);
        }
        if (i == 2) {
            return isVpnProfilePreConsented(context, str);
        }
        if (i != 3) {
            return false;
        }
        return "[Legacy VPN]".equals(str);
    }

    public static boolean doesPackageHaveAppop(Context context, String str, String str2) {
        return ((AppOpsManager) context.getSystemService("appops")).noteOpNoThrow(str2, Binder.getCallingUid(), str, null, null) == 0;
    }

    public static boolean isVpnServicePreConsented(Context context, String str) {
        return doesPackageHaveAppop(context, str, "android:activate_vpn");
    }

    public static boolean isVpnProfilePreConsented(Context context, String str) {
        return doesPackageHaveAppop(context, str, "android:activate_platform_vpn") || isVpnServicePreConsented(context, str);
    }

    public static int getAppUid(Context context, String str, int i) {
        if ("[Legacy VPN]".equals(str)) {
            return Process.myUid();
        }
        PackageManager packageManager = context.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return packageManager.getPackageUidAsUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        } catch (SecurityException unused2) {
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean doesPackageTargetAtLeastQ(String str) {
        if ("[Legacy VPN]".equals(str)) {
            return true;
        }
        try {
            return this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, this.mUserId).targetSdkVersion >= 29;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("Vpn", "Can't find \"" + str + "\"");
            return false;
        }
    }

    public NetworkInfo getNetworkInfo() {
        return this.mNetworkInfo;
    }

    public synchronized Network getNetwork() {
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent == null) {
            return null;
        }
        Network network = networkAgent.getNetwork();
        if (network == null) {
            return null;
        }
        return network;
    }

    public final LinkProperties makeLinkProperties() {
        boolean z = isIkev2VpnRunner() && this.mConfig.mtu < 1280;
        VpnConfig vpnConfig = this.mConfig;
        boolean z2 = vpnConfig.allowIPv4;
        boolean z3 = vpnConfig.allowIPv6;
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName(this.mInterface);
        List<LinkAddress> list = this.mConfig.addresses;
        if (list != null) {
            for (LinkAddress linkAddress : list) {
                if (!z || !linkAddress.isIpv6()) {
                    linkProperties.addLinkAddress(linkAddress);
                    z2 |= linkAddress.getAddress() instanceof Inet4Address;
                    z3 |= linkAddress.getAddress() instanceof Inet6Address;
                }
            }
        }
        List<RouteInfo> list2 = this.mConfig.routes;
        if (list2 != null) {
            for (RouteInfo routeInfo : list2) {
                InetAddress address = routeInfo.getDestination().getAddress();
                if (!z || !(address instanceof Inet6Address)) {
                    linkProperties.addRoute(routeInfo);
                    if (routeInfo.getType() == 1) {
                        z2 |= address instanceof Inet4Address;
                        z3 |= address instanceof Inet6Address;
                    }
                }
            }
        }
        List list3 = this.mConfig.dnsServers;
        if (list3 != null) {
            Iterator it = list3.iterator();
            while (it.hasNext()) {
                InetAddress parseNumericAddress = InetAddresses.parseNumericAddress((String) it.next());
                if (!z || !(parseNumericAddress instanceof Inet6Address)) {
                    linkProperties.addDnsServer(parseNumericAddress);
                    z2 |= parseNumericAddress instanceof Inet4Address;
                    z3 |= parseNumericAddress instanceof Inet6Address;
                }
            }
        }
        linkProperties.setHttpProxy(this.mConfig.proxyInfo);
        if (!z2) {
            linkProperties.addRoute(new RouteInfo(new IpPrefix(NetworkStackConstants.IPV4_ADDR_ANY, 0), null, null, 7));
        }
        if (!z3 || z) {
            linkProperties.addRoute(new RouteInfo(new IpPrefix(NetworkStackConstants.IPV6_ADDR_ANY, 0), null, null, 7));
        }
        StringBuilder sb = new StringBuilder();
        List list4 = this.mConfig.searchDomains;
        if (list4 != null) {
            Iterator it2 = list4.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append(' ');
            }
        }
        linkProperties.setDomains(sb.toString().trim());
        int i = this.mConfig.mtu;
        if (i > 0) {
            linkProperties.setMtu(i);
        }
        return linkProperties;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
    
        r3[2] = r1.getHostAddress().toUpperCase();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object[] getWifiInterfaceInfo() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return null;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.getName().startsWith("wlan")) {
                    Object[] objArr = new Object[3];
                    objArr[0] = Integer.valueOf(nextElement.getIndex());
                    objArr[1] = nextElement.getName();
                    Iterator<InterfaceAddress> it = nextElement.getInterfaceAddresses().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        InetAddress address = it.next().getAddress();
                        if (!address.isLoopbackAddress() && (address instanceof Inet4Address)) {
                            break;
                        }
                    }
                    return objArr;
                }
            }
            return null;
        } catch (Exception e) {
            Log.e("Vpn", "Failed to get wifi interface info: " + e);
            return null;
        }
    }

    public final void updatePortBypassConfigs() {
        String[] split;
        VpnConfig vpnConfig = this.mConfig;
        vpnConfig.allowPortBypass = false;
        String str = vpnConfig.session;
        if (str != null && (split = str.split("_")) != null && split.length > 1) {
            try {
                this.mConfig.allowPortBypass = Integer.parseInt(split[0]) != 0;
                this.mConfig.session = split[1];
            } catch (NumberFormatException unused) {
            }
        }
        if (this.mConfig.allowPortBypass) {
            Object[] wifiInterfaceInfo = getWifiInterfaceInfo();
            if (wifiInterfaceInfo == null) {
                Log.e("Vpn", "Failed to update port bypass configs due to no wifi interface info");
                this.mConfig.allowPortBypass = false;
                return;
            }
            VpnConfig vpnConfig2 = this.mConfig;
            vpnConfig2.dport = 443;
            vpnConfig2.fwmark = 1546;
            vpnConfig2.priority = 11500;
            vpnConfig2.netTableId = ((Integer) wifiInterfaceInfo[0]).intValue() + 1000;
            VpnConfig vpnConfig3 = this.mConfig;
            vpnConfig3.netIfaceName = (String) wifiInterfaceInfo[1];
            vpnConfig3.netIfaceAddress = (String) wifiInterfaceInfo[2];
        }
    }

    public final void applyPortBypassRule() {
        if (this.mConfig.allowPortBypass) {
            Log.i("Vpn", "Apply port bypass rules for Secure Wi-Fi");
            VpnRules vpnRules = this.mVpnRules;
            VpnConfig vpnConfig = this.mConfig;
            int tcpPortBypassRule = vpnRules.setTcpPortBypassRule(true, vpnConfig.dport, vpnConfig.fwmark, vpnConfig.priority, vpnConfig.netTableId, vpnConfig.netIfaceName, vpnConfig.netIfaceAddress);
            VpnConfig vpnConfig2 = this.mConfig;
            if (vpnConfig2.fwmark != tcpPortBypassRule) {
                vpnConfig2.fwmark = tcpPortBypassRule;
                if (tcpPortBypassRule > 0) {
                    Log.i("Vpn", "Applied port bypass rules with new fwmark = " + tcpPortBypassRule);
                }
            }
            this.mSwifiConfig = this.mConfig;
        }
    }

    public final void cleanPortBypassRule() {
        VpnConfig vpnConfig = this.mSwifiConfig;
        if (vpnConfig != null) {
            if (vpnConfig.allowPortBypass && vpnConfig.fwmark > 0) {
                Log.i("Vpn", "Clean port bypass rules for Secure Wi-Fi");
                VpnRules vpnRules = this.mVpnRules;
                VpnConfig vpnConfig2 = this.mSwifiConfig;
                vpnRules.setTcpPortBypassRule(false, vpnConfig2.dport, vpnConfig2.fwmark, vpnConfig2.priority, vpnConfig2.netTableId, vpnConfig2.netIfaceName, vpnConfig2.netIfaceAddress);
            }
            this.mSwifiConfig = null;
        }
    }

    public final boolean updateLinkPropertiesInPlaceIfPossible(NetworkAgent networkAgent, VpnConfig vpnConfig) {
        boolean z = vpnConfig.allowBypass;
        VpnConfig vpnConfig2 = this.mConfig;
        if (z != vpnConfig2.allowBypass) {
            Log.i("Vpn", "Handover not possible due to changes to allowBypass");
            return false;
        }
        if (!Objects.equals(vpnConfig.allowedApplications, vpnConfig2.allowedApplications) || !Objects.equals(vpnConfig.disallowedApplications, this.mConfig.disallowedApplications)) {
            Log.i("Vpn", "Handover not possible due to changes to allowed/denied apps");
            return false;
        }
        if (isSecureWifiPackage()) {
            if (SemPersonaManager.getSecureFolderId(this.mContext) > 0) {
                if (!Objects.equals(vpnConfig.allowedSecureFolderApps, this.mConfig.allowedSecureFolderApps) || !Objects.equals(vpnConfig.disallowedSecureFolderApps, this.mConfig.disallowedSecureFolderApps)) {
                    Log.i("Vpn", "secureFolder's config is changed. return false");
                    return false;
                }
                Log.i("Vpn", "secureFolder's config is not changed");
            }
            boolean z2 = vpnConfig.allowPortBypass;
            VpnConfig vpnConfig3 = this.mConfig;
            if (z2 != vpnConfig3.allowPortBypass || vpnConfig.dport != vpnConfig3.dport || vpnConfig.fwmark != vpnConfig3.fwmark || vpnConfig.priority != vpnConfig3.priority || vpnConfig.netTableId != vpnConfig3.netTableId || !Objects.equals(vpnConfig.netIfaceName, vpnConfig3.netIfaceName) || !Objects.equals(vpnConfig.netIfaceAddress, this.mConfig.netIfaceAddress)) {
                Log.i("Vpn", "Handover not possible due to changes to port bypass configs");
                return false;
            }
        }
        networkAgent.sendLinkProperties(makeLinkProperties());
        return true;
    }

    public final LinkProperties makeMockLinkProperties() {
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName("mock_" + this.mInterface);
        return linkProperties;
    }

    public final void registerMockNetworkAgent() {
        this.mMockNetworkAgent = this.mDeps.newNetworkAgent(this.mContext, this.mLooper, "VPN", new NetworkCapabilities.Builder().addTransportType(9).addCapability(11).addCapability(28).setUnderlyingNetworks((List) null).build(), makeMockLinkProperties(), new NetworkScore.Builder().setLegacyInt(101).build(), new NetworkAgentConfig.Builder().setLegacyType(17).setLegacyTypeName("VPN").setBypassableVpn(true).setVpnRequiresValidation(false).setLocalRoutesExcludedForVpn(true).build(), this.mNetworkProvider, null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mMockNetworkAgent.register();
                Log.i("Vpn", "register MockNetworkAgent success");
                this.mVpnNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.connectivity.Vpn.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network) {
                        Log.i("Vpn", "mVpnNetworkCallback onAvailable");
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                        Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged: " + linkProperties.toString());
                        if (Vpn.this.mInterface.equals(linkProperties.getInterfaceName())) {
                            Vpn.this.mConnectivityManager.unregisterNetworkCallback(this);
                            LinkProperties makeMockLinkProperties = Vpn.this.makeMockLinkProperties();
                            List<LinkAddress> list = Vpn.this.mConfig.addresses;
                            if (list != null) {
                                for (LinkAddress linkAddress : list) {
                                    makeMockLinkProperties.addLinkAddress(linkAddress);
                                    Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged addLinkAddress: " + linkAddress);
                                }
                            }
                            Vpn.this.mMockNetworkAgent.sendLinkProperties(makeMockLinkProperties);
                            Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged sendLinkProperties");
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onLost(Network network) {
                        Log.i("Vpn", "mVpnNetworkCallback onLost");
                    }
                };
                this.mConnectivityManager.registerNetworkCallback(new NetworkRequest.Builder().clearCapabilities().addTransportType(4).build(), this.mVpnNetworkCallback, new Handler(this.mLooper));
            } catch (Exception e) {
                this.mMockNetworkAgent = null;
                Log.i("Vpn", "registerMockNetworkAgent failed: " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterMockNetworkAgent() {
        NetworkAgent networkAgent = this.mMockNetworkAgent;
        if (networkAgent != null) {
            networkAgent.unregister();
            this.mMockNetworkAgent = null;
        }
        ConnectivityManager.NetworkCallback networkCallback = this.mVpnNetworkCallback;
        if (networkCallback != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback);
            this.mVpnNetworkCallback = null;
        }
        Log.i("Vpn", "unregisterMockNetworkAgent");
    }

    public final void agentConnect() {
        agentConnect(null);
    }

    public final void agentConnect(ValidationStatusCallback validationStatusCallback) {
        IkeSessionWrapper ikeSessionWrapper;
        LinkProperties makeLinkProperties = makeLinkProperties();
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder(this.mNetworkCapabilities);
        builder.addCapability(12);
        this.mLegacyState = 2;
        updateState(NetworkInfo.DetailedState.CONNECTING, "agentConnect");
        boolean z = this.mConfig.allowBypass && !this.mLockdown;
        NetworkAgentConfig build = new NetworkAgentConfig.Builder().setLegacyType(17).setLegacyTypeName("VPN").setBypassableVpn(z).setVpnRequiresValidation(this.mConfig.requiresInternetValidation).setLocalRoutesExcludedForVpn(this.mConfig.excludeLocalRoutes).build();
        builder.setOwnerUid(this.mOwnerUID);
        builder.setAdministratorUids(new int[]{this.mOwnerUID});
        int i = this.mUserId;
        VpnConfig vpnConfig = this.mConfig;
        builder.setUids(createUserAndRestrictedProfilesRanges(i, vpnConfig.allowedApplications, vpnConfig.disallowedApplications));
        if (isSecureWifiPackage()) {
            int secureFolderId = SemPersonaManager.getSecureFolderId(this.mContext);
            if (secureFolderId > 0) {
                Log.i("Vpn", "Apply Secure Wi-Fi to Secure Folder");
                int i2 = this.mUserId;
                VpnConfig vpnConfig2 = this.mConfig;
                Set createUserAndRestrictedProfilesRanges = createUserAndRestrictedProfilesRanges(i2, vpnConfig2.allowedApplications, vpnConfig2.disallowedApplications);
                VpnConfig vpnConfig3 = this.mConfig;
                addUserToRanges(createUserAndRestrictedProfilesRanges, secureFolderId, vpnConfig3.allowedSecureFolderApps, vpnConfig3.disallowedSecureFolderApps);
                builder.setUids(createUserAndRestrictedProfilesRanges);
            }
            cleanPortBypassRule();
            applyPortBypassRule();
        }
        builder.setTransportInfo(new VpnTransportInfo(getActiveVpnType(), this.mConfig.session, z, areLongLivedTcpConnectionsExpensive(this.mVpnRunner)));
        if (this.mIsPackageTargetingAtLeastQ && this.mConfig.isMetered) {
            builder.removeCapability(11);
        } else {
            builder.addCapability(11);
        }
        Network[] networkArr = this.mConfig.underlyingNetworks;
        builder.setUnderlyingNetworks(networkArr != null ? Arrays.asList(networkArr) : null);
        NetworkCapabilities build2 = builder.build();
        this.mNetworkCapabilities = build2;
        logUnderlyNetworkChanges(build2.getUnderlyingNetworks());
        this.mNetworkAgent = this.mDeps.newNetworkAgent(this.mContext, this.mLooper, "VPN", this.mNetworkCapabilities, makeLinkProperties, new NetworkScore.Builder().setLegacyInt(101).build(), build, this.mNetworkProvider, validationStatusCallback);
        if ((this.mVpnRunner instanceof LegacyVpnRunner) || isSecureWifiPackage()) {
            registerMockNetworkAgent();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkAgent.register();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                INetworkManagementService iNetworkManagementService = this.mNms;
                if (iNetworkManagementService != null) {
                    try {
                        iNetworkManagementService.setNetworkInfo(getNetId(), false, this.mOwnerUID);
                    } catch (Exception unused) {
                    }
                }
                updateState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
                if (!isIkev2VpnRunner() || (ikeSessionWrapper = ((IkeV2VpnRunner) this.mVpnRunner).mSession) == null) {
                    return;
                }
                ikeSessionWrapper.setUnderpinnedNetwork(this.mNetworkAgent.getNetwork());
            } catch (Exception e) {
                this.mNetworkAgent = null;
                unregisterMockNetworkAgent();
                throw e;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean areLongLivedTcpConnectionsExpensive(VpnRunner vpnRunner) {
        if (vpnRunner instanceof IkeV2VpnRunner) {
            return areLongLivedTcpConnectionsExpensive(((IkeV2VpnRunner) vpnRunner).getOrGuessKeepaliveDelaySeconds());
        }
        return false;
    }

    public final boolean canHaveRestrictedProfile(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((UserManager) this.mContext.createContextAsUser(UserHandle.of(i), 0).getSystemService(UserManager.class)).canHaveRestrictedProfile();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void logUnderlyNetworkChanges(List list) {
        LocalLog localLog = this.mEventChanges;
        StringBuilder sb = new StringBuilder();
        sb.append("[UnderlyingNW] Switch to ");
        sb.append(list != null ? TextUtils.join(", ", list) : "null");
        localLog.log(sb.toString());
    }

    public final void agentDisconnect(NetworkAgent networkAgent) {
        if (networkAgent != null) {
            networkAgent.unregister();
        }
    }

    public final void agentDisconnect() {
        cleanPortBypassRule();
        updateState(NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
        if (getKnoxVpnFeature() < 1 || getknoxVpnTypeForStrongswanProfile()) {
            return;
        }
        hideNotification(0);
    }

    public final void startNewNetworkAgent(NetworkAgent networkAgent, String str) {
        this.mNetworkAgent = null;
        updateState(NetworkInfo.DetailedState.CONNECTING, str);
        agentConnect();
        agentDisconnect(networkAgent);
    }

    public synchronized ParcelFileDescriptor establish(VpnConfig vpnConfig) {
        if (Binder.getCallingUid() != this.mOwnerUID) {
            return null;
        }
        if (!isVpnServicePreConsented(this.mContext, this.mPackage)) {
            return null;
        }
        Intent intent = new Intent("android.net.VpnService");
        intent.setClassName(this.mPackage, vpnConfig.user);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            enforceNotRestrictedUser();
            PackageManager packageManager = this.mUserIdContext.getPackageManager();
            if (packageManager == null) {
                throw new IllegalStateException("Cannot get PackageManager.");
            }
            ResolveInfo resolveService = packageManager.resolveService(intent, 0);
            if (resolveService == null) {
                throw new SecurityException("Cannot find " + vpnConfig.user);
            }
            if (!"android.permission.BIND_VPN_SERVICE".equals(resolveService.serviceInfo.permission)) {
                throw new SecurityException(vpnConfig.user + " does not require android.permission.BIND_VPN_SERVICE");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            VpnConfig vpnConfig2 = this.mConfig;
            String str = this.mInterface;
            Connection connection = this.mConnection;
            NetworkAgent networkAgent = this.mNetworkAgent;
            Set uids = this.mNetworkCapabilities.getUids();
            ParcelFileDescriptor adoptFd = this.mDeps.adoptFd(this, vpnConfig.mtu);
            try {
                String jniGetName = this.mDeps.jniGetName(this, adoptFd.getFd());
                StringBuilder sb = new StringBuilder();
                for (LinkAddress linkAddress : vpnConfig.addresses) {
                    sb.append(" ");
                    sb.append(linkAddress);
                }
                if (this.mDeps.jniSetAddresses(this, jniGetName, sb.toString()) < 1) {
                    throw new IllegalArgumentException("At least one address must be specified");
                }
                Connection connection2 = new Connection();
                if (!this.mContext.bindServiceAsUser(intent, connection2, 67108865, new UserHandle(this.mUserId))) {
                    throw new IllegalStateException("Cannot bind " + vpnConfig.user);
                }
                this.mConnection = connection2;
                this.mInterface = jniGetName;
                vpnConfig.user = this.mPackage;
                vpnConfig.interfaze = jniGetName;
                vpnConfig.startTime = SystemClock.elapsedRealtime();
                this.mConfig = vpnConfig;
                if (isSecureWifiPackage()) {
                    updatePortBypassConfigs();
                }
                if (vpnConfig2 != null && updateLinkPropertiesInPlaceIfPossible(this.mNetworkAgent, vpnConfig2)) {
                    if (!Arrays.equals(vpnConfig2.underlyingNetworks, vpnConfig.underlyingNetworks)) {
                        setUnderlyingNetworks(vpnConfig.underlyingNetworks);
                    }
                } else {
                    startNewNetworkAgent(networkAgent, "establish");
                }
                if (connection != null) {
                    this.mContext.unbindService(connection);
                }
                if (str != null && !str.equals(jniGetName)) {
                    jniReset(str);
                }
                this.mDeps.setBlocking(adoptFd.getFileDescriptor(), vpnConfig.blocking);
                if (networkAgent != this.mNetworkAgent) {
                    this.mAppOpsManager.startOp("android:establish_vpn_service", this.mOwnerUID, this.mPackage, null, null);
                }
                Log.i("Vpn", "Established by " + vpnConfig.user + " on " + this.mInterface);
                return adoptFd;
            } catch (RuntimeException e) {
                IoUtils.closeQuietly(adoptFd);
                if (networkAgent != this.mNetworkAgent) {
                    agentDisconnect();
                }
                this.mConfig = vpnConfig2;
                this.mConnection = connection;
                this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                this.mNetworkAgent = networkAgent;
                this.mInterface = str;
                throw e;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isRunningLocked() {
        return (this.mNetworkAgent == null || this.mInterface == null) ? false : true;
    }

    public boolean isCallerEstablishedOwnerLocked() {
        return isRunningLocked() && Binder.getCallingUid() == this.mOwnerUID;
    }

    public final SortedSet getAppsUids(List list, int i) {
        TreeSet treeSet = new TreeSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int appUid = getAppUid(this.mContext, (String) it.next(), i);
            if (appUid != -1) {
                treeSet.add(Integer.valueOf(appUid));
            }
            if (Process.isApplicationUid(appUid) && SdkLevel.isAtLeastT()) {
                treeSet.add(Integer.valueOf(Process.toSdkSandboxUid(appUid)));
            }
        }
        return treeSet;
    }

    public Set createUserAndRestrictedProfilesRanges(int i, List list, List list2) {
        ArraySet arraySet = new ArraySet();
        addUserToRanges(arraySet, i, list, list2);
        if (isDualAppEnabled() && isFullTunneling(list, list2) && i == 0) {
            Log.d("Vpn", "Add uid on dualAppProfile");
            addUserToRanges(arraySet, SemDualAppManager.getDualAppProfileId(), list, list2);
        }
        if (canHaveRestrictedProfile(i)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List<UserInfo> aliveUsers = this.mUserManager.getAliveUsers();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                for (UserInfo userInfo : aliveUsers) {
                    if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == i) {
                        addUserToRanges(arraySet, userInfo.id, list, list2);
                    }
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return arraySet;
    }

    public final boolean isDualAppEnabled() {
        return SemDualAppManager.getDualAppProfileId() != -10000;
    }

    public final boolean isFullTunneling(List list, List list2) {
        return CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(list2);
    }

    public void addUserToRanges(Set set, int i, List list, List list2) {
        if (list != null) {
            Iterator it = getAppsUids(list, i).iterator();
            int i2 = -1;
            int i3 = -1;
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (i2 != -1) {
                    if (intValue != i3 + 1) {
                        set.add(new Range(Integer.valueOf(i2), Integer.valueOf(i3)));
                    } else {
                        i3 = intValue;
                    }
                }
                i2 = intValue;
                i3 = intValue;
            }
            if (i2 != -1) {
                set.add(new Range(Integer.valueOf(i2), Integer.valueOf(i3)));
                return;
            }
            return;
        }
        if (list2 != null) {
            Range createUidRangeForUser = createUidRangeForUser(i);
            int intValue2 = ((Integer) createUidRangeForUser.getLower()).intValue();
            Iterator it2 = getAppsUids(list2, i).iterator();
            while (it2.hasNext()) {
                int intValue3 = ((Integer) it2.next()).intValue();
                if (intValue3 == intValue2) {
                    intValue2++;
                } else {
                    set.add(new Range(Integer.valueOf(intValue2), Integer.valueOf(intValue3 - 1)));
                    intValue2 = intValue3 + 1;
                }
            }
            if (intValue2 <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
                set.add(new Range(Integer.valueOf(intValue2), (Integer) createUidRangeForUser.getUpper()));
                return;
            }
            return;
        }
        set.add(createUidRangeForUser(i));
    }

    public static List uidRangesForUser(int i, Set set) {
        Range createUidRangeForUser = createUidRangeForUser(i);
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Range range = (Range) it.next();
            if (createUidRangeForUser.contains(range)) {
                arrayList.add(range);
            }
        }
        return arrayList;
    }

    public void onUserAdded(int i) {
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == this.mUserId) {
            synchronized (this) {
                Set uids = this.mNetworkCapabilities.getUids();
                if (uids != null) {
                    try {
                        VpnConfig vpnConfig = this.mConfig;
                        addUserToRanges(uids, i, vpnConfig.allowedApplications, vpnConfig.disallowedApplications);
                        this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    } catch (Exception e) {
                        Log.wtf("Vpn", "Failed to add restricted user to owner", e);
                    }
                    NetworkAgent networkAgent = this.mNetworkAgent;
                    if (networkAgent != null) {
                        doSendNetworkCapabilities(networkAgent, this.mNetworkCapabilities);
                    }
                }
                setVpnForcedLocked(this.mLockdown);
            }
        }
    }

    public void onUserRemoved(int i) {
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == this.mUserId) {
            synchronized (this) {
                Set uids = this.mNetworkCapabilities.getUids();
                if (uids != null) {
                    try {
                        uids.removeAll(uidRangesForUser(i, uids));
                        this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    } catch (Exception e) {
                        Log.wtf("Vpn", "Failed to remove restricted user to owner", e);
                    }
                    NetworkAgent networkAgent = this.mNetworkAgent;
                    if (networkAgent != null) {
                        doSendNetworkCapabilities(networkAgent, this.mNetworkCapabilities);
                    }
                }
                setVpnForcedLocked(this.mLockdown);
            }
        }
    }

    public synchronized void onUserStopped() {
        setVpnForcedLocked(false);
        this.mAlwaysOn = false;
        agentDisconnect();
        this.mConnectivityManager.unregisterNetworkProvider(this.mNetworkProvider);
    }

    public final void setVpnForcedLocked(boolean z) {
        List arrayList;
        Set emptySet;
        if (isNullOrLegacyVpn(this.mPackage)) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.mLockdownAllowlist);
            arrayList.add(this.mPackage);
        }
        ArraySet arraySet = new ArraySet(this.mBlockedUidsAsToldToConnectivity);
        if (z) {
            Set<Range> createUserAndRestrictedProfilesRanges = createUserAndRestrictedProfilesRanges(this.mUserId, null, arrayList);
            emptySet = new ArraySet();
            for (Range range : createUserAndRestrictedProfilesRanges) {
                if (((Integer) range.getLower()).intValue() == 0 && ((Integer) range.getUpper()).intValue() != 0) {
                    if (MdfUtils.isMdfEnforced()) {
                        emptySet.add(new UidRangeParcel(1, 1015));
                        emptySet.add(new UidRangeParcel(1017, ((Integer) range.getUpper()).intValue()));
                    } else {
                        emptySet.add(new UidRangeParcel(1, ((Integer) range.getUpper()).intValue()));
                    }
                } else if (((Integer) range.getLower()).intValue() != 0) {
                    emptySet.add(new UidRangeParcel(((Integer) range.getLower()).intValue(), ((Integer) range.getUpper()).intValue()));
                }
            }
            arraySet.removeAll(emptySet);
            emptySet.removeAll(this.mBlockedUidsAsToldToConnectivity);
        } else {
            emptySet = Collections.emptySet();
        }
        setAllowOnlyVpnForUids(false, arraySet);
        setAllowOnlyVpnForUids(true, emptySet);
    }

    public final boolean setAllowOnlyVpnForUids(boolean z, Collection collection) {
        if (collection.size() == 0) {
            return true;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            UidRangeParcel uidRangeParcel = (UidRangeParcel) it.next();
            arrayList.add(new Range(Integer.valueOf(uidRangeParcel.start), Integer.valueOf(uidRangeParcel.stop)));
        }
        try {
            this.mConnectivityManager.setRequireVpnForUids(z, arrayList);
            if (z) {
                this.mBlockedUidsAsToldToConnectivity.addAll(collection);
            } else {
                this.mBlockedUidsAsToldToConnectivity.removeAll(collection);
            }
            return true;
        } catch (RuntimeException e) {
            Log.e("Vpn", "Updating blocked=" + z + " for UIDs " + Arrays.toString(collection.toArray()) + " failed", e);
            return false;
        }
    }

    public synchronized VpnConfig getVpnConfig() {
        enforceControlPermission();
        return this.mConfig;
    }

    public final void cleanupVpnStateLocked() {
        this.mStatusIntent = null;
        resetNetworkCapabilities();
        this.mConfig = null;
        this.mInterface = null;
        this.mVpnRunner = null;
        this.mConnection = null;
        agentDisconnect();
    }

    public final void enforceControlPermission() {
        this.mContext.enforceCallingPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
    }

    public final void enforceControlPermissionOrInternalCaller() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
    }

    public final void enforceSettingsPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.NETWORK_SETTINGS", "Unauthorized Caller");
    }

    public class Connection implements ServiceConnection {
        public IBinder mService;

        public Connection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.mService = iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.mService = null;
        }
    }

    public final void prepareStatusIntent() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mStatusIntent = this.mDeps.getIntentForStatusPanel(this.mContext);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized boolean addAddress(String str, int i) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniAddAddress = jniAddAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniAddAddress;
    }

    public synchronized boolean removeAddress(String str, int i) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniDelAddress = jniDelAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniDelAddress;
    }

    public synchronized boolean setUnderlyingNetworks(Network[] networkArr) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        this.mConfig.underlyingNetworks = networkArr != null ? (Network[]) Arrays.copyOf(networkArr, networkArr.length) : null;
        NetworkAgent networkAgent = this.mNetworkAgent;
        Network[] networkArr2 = this.mConfig.underlyingNetworks;
        doSetUnderlyingNetworks(networkAgent, networkArr2 != null ? Arrays.asList(networkArr2) : null);
        return true;
    }

    public synchronized UnderlyingNetworkInfo getUnderlyingNetworkInfo() {
        if (!isRunningLocked()) {
            return null;
        }
        return new UnderlyingNetworkInfo(this.mOwnerUID, this.mInterface, new ArrayList());
    }

    public synchronized int getActiveVpnType() {
        if (!this.mNetworkInfo.isConnectedOrConnecting()) {
            return -1;
        }
        if (this.mVpnRunner == null) {
            return 1;
        }
        return isIkev2VpnRunner() ? 2 : 3;
    }

    public final void updateAlwaysOnNotification(NetworkInfo.DetailedState detailedState) {
        boolean z = this.mAlwaysOn && detailedState != NetworkInfo.DetailedState.CONNECTED;
        UserHandle of = UserHandle.of(this.mUserId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            NotificationManager notificationManager = (NotificationManager) this.mUserIdContext.getSystemService(NotificationManager.class);
            if (!z) {
                notificationManager.cancel("Vpn", 17);
                return;
            }
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString(this.mContext.getString(R.string.create_contact_using)));
            intent.putExtra("lockdown", this.mLockdown);
            intent.addFlags(268435456);
            notificationManager.notify("Vpn", 17, new Notification.Builder(this.mContext, "VPN").setSmallIcon(17304614).setContentTitle(this.mContext.getString(17043246)).setContentText(this.mContext.getString(17043243)).setContentIntent(this.mSystemServices.pendingIntentGetActivityAsUser(intent, 201326592, of)).setCategory("sys").setVisibility(1).setOngoing(true).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).build());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class SystemServices {
        public final Context mContext;

        public SystemServices(Context context) {
            this.mContext = context;
        }

        public PendingIntent pendingIntentGetActivityAsUser(Intent intent, int i, UserHandle userHandle) {
            return PendingIntent.getActivity(this.mContext.createContextAsUser(userHandle, 0), 0, intent, i);
        }

        public void settingsSecurePutStringForUser(String str, String str2, int i) {
            Settings.Secure.putString(getContentResolverAsUser(i), str, str2);
        }

        public void settingsSecurePutIntForUser(String str, int i, int i2) {
            Settings.Secure.putInt(getContentResolverAsUser(i2), str, i);
        }

        public String settingsSecureGetStringForUser(String str, int i) {
            return Settings.Secure.getString(getContentResolverAsUser(i), str);
        }

        public int settingsSecureGetIntForUser(String str, int i, int i2) {
            return Settings.Secure.getInt(getContentResolverAsUser(i2), str, i);
        }

        public final ContentResolver getContentResolverAsUser(int i) {
            return this.mContext.createContextAsUser(UserHandle.of(i), 0).getContentResolver();
        }
    }

    public static RouteInfo findIPv4DefaultRoute(LinkProperties linkProperties) {
        for (RouteInfo routeInfo : linkProperties.getAllRoutes()) {
            if (routeInfo.isDefaultRoute() && (routeInfo.getGateway() instanceof Inet4Address)) {
                return routeInfo;
            }
        }
        throw new IllegalStateException("Unable to find IPv4 default gateway");
    }

    public final void enforceNotRestrictedUser() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mUserManager.getUserInfo(this.mUserId).isRestricted()) {
                throw new SecurityException("Restricted users cannot configure VPNs");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startLegacyVpn(VpnProfile vpnProfile, Network network, LinkProperties linkProperties) {
        enforceControlPermission();
        String interfaceName = linkProperties.getInterfaceName();
        if (isSettingsVpnLocked() && interfaceName != null && (interfaceName.startsWith(INetd.IPSEC_INTERFACE_PREFIX) || interfaceName.startsWith("ppp") || interfaceName.startsWith("tun"))) {
            stopVpnRunnerPrivileged();
            ConnectivityManager connectivityManager = this.mConnectivityManager;
            linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
            if (linkProperties == null) {
                throw new IllegalStateException("Missing active default network");
            }
            Log.d("Vpn", "startLegacyVpn changed egress: " + interfaceName + " > " + linkProperties.getInterfaceName());
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            startLegacyVpnPrivileged(vpnProfile, network, linkProperties);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isVpnConnectionSecure(String[] strArr) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        this.mEDM = enterpriseDeviceManager;
        if (enterpriseDeviceManager != null) {
            this.mVpnPolicy = enterpriseDeviceManager.getVpnPolicy();
        }
        VpnPolicy vpnPolicy = this.mVpnPolicy;
        boolean checkRacoonSecurity = vpnPolicy != null ? vpnPolicy.checkRacoonSecurity(strArr) : true;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return checkRacoonSecurity;
    }

    public final String makeKeystoreEngineGrantString(String str) {
        if (str == null) {
            return null;
        }
        KeyStore2 keyStore2 = KeyStore2.getInstance();
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        try {
            return KeyStore2.makeKeystoreEngineGrantString(keyStore2.grant(keyDescriptor, 1016, 260).nspace);
        } catch (KeyStoreException e) {
            Log.e("Vpn", "Failed to get grant for keystore key.", e);
            throw new IllegalStateException("Failed to get grant for keystore key.", e);
        }
    }

    public final String getCaCertificateFromKeystoreAsPem(KeyStore keyStore, String str) {
        if (keyStore.isCertificateEntry(str)) {
            Certificate certificate = keyStore.getCertificate(str);
            if (certificate == null) {
                return null;
            }
            return new String(Credentials.convertToPem(new Certificate[]{certificate}), StandardCharsets.UTF_8);
        }
        Certificate[] certificateChain = keyStore.getCertificateChain(str);
        if (certificateChain == null || certificateChain.length <= 1) {
            return null;
        }
        return new String(Credentials.convertToPem((Certificate[]) Arrays.copyOfRange(certificateChain, 1, certificateChain.length)), StandardCharsets.UTF_8);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startLegacyVpnPrivileged(VpnProfile vpnProfile, Network network, LinkProperties linkProperties) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        int i;
        char c;
        char c2;
        char c3;
        String[] strArr;
        int i2;
        boolean z;
        String[] strArr2;
        if (this.mUserManager.getUserInfo(this.mUserId).isRestricted() || this.mUserManager.hasUserRestriction("no_config_vpn", new UserHandle(this.mUserId))) {
            throw new SecurityException("Restricted users cannot establish VPNs");
        }
        RouteInfo findIPv4DefaultRoute = findIPv4DefaultRoute(linkProperties);
        String hostAddress = findIPv4DefaultRoute.getGateway().getHostAddress();
        String str6 = findIPv4DefaultRoute.getInterface();
        boolean isUcmEnabled = isUcmEnabled();
        Log.d("Vpn", "startLegacyVpnPrivileged: isUcmEnabled=" + isUcmEnabled);
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (vpnProfile.ipsecUserCert.isEmpty()) {
                str = "";
                str2 = str;
            } else {
                String str7 = vpnProfile.ipsecUserCert;
                Certificate certificate = keyStore.getCertificate(str7);
                String str8 = certificate == null ? null : new String(Credentials.convertToPem(new Certificate[]{certificate}), StandardCharsets.UTF_8);
                if (str8 == null && isUcmEnabled) {
                    str8 = getUcmCertificate("USRCERT_", vpnProfile.ipsecUserCert);
                } else {
                    str7 = makeKeystoreEngineGrantString(str7);
                }
                str = str7;
                str2 = str8;
            }
            if (vpnProfile.ipsecCaCert.isEmpty()) {
                str3 = "";
            } else {
                String caCertificateFromKeystoreAsPem = getCaCertificateFromKeystoreAsPem(keyStore, vpnProfile.ipsecCaCert);
                if (caCertificateFromKeystoreAsPem == null && isUcmEnabled) {
                    caCertificateFromKeystoreAsPem = getUcmCertificate("CACERT_", vpnProfile.ipsecCaCert);
                }
                str3 = caCertificateFromKeystoreAsPem;
            }
            if (vpnProfile.ipsecServerCert.isEmpty()) {
                str4 = "";
            } else {
                Certificate certificate2 = keyStore.getCertificate(vpnProfile.ipsecServerCert);
                String str9 = certificate2 == null ? null : new String(Credentials.convertToPem(new Certificate[]{certificate2}), StandardCharsets.UTF_8);
                if (str9 == null && isUcmEnabled) {
                    str9 = getUcmCertificate("USRCERT_", vpnProfile.ipsecServerCert);
                }
                str4 = str9;
            }
            if (str2 == null || str3 == null || str4 == null) {
                throw new IllegalStateException("Cannot load credentials");
            }
            this.mEgressIface = str6;
            Log.d("Vpn", "Egress Iface (" + this.mEgressIface + ") activated");
            try {
                Iterator it = Collections.list(NetworkInterface.getByName(this.mEgressIface).getInetAddresses()).iterator();
                str5 = "";
                while (it.hasNext()) {
                    try {
                        str5 = ((InetAddress) it.next()).getHostAddress();
                    } catch (Exception unused) {
                        Log.d("Vpn", "startLegacyVpnPrivileged Egress Iface (" + this.mEgressIface + ") doesn't exist");
                        String str10 = str5;
                        VpnProfile.decrypt(vpnProfile);
                        i = vpnProfile.type;
                        if (i != 1) {
                        }
                        if (!MdfUtils.isMdfEnforced()) {
                        }
                        switch (vpnProfile.type) {
                        }
                        if (isVpnConnectionSecure(strArr)) {
                        }
                    }
                }
            } catch (Exception unused2) {
                str5 = "";
            }
            String str102 = str5;
            VpnProfile.decrypt(vpnProfile);
            i = vpnProfile.type;
            if (i != 1) {
                c = 1;
                c2 = 5;
                c3 = '\n';
                strArr = new String[]{str6, vpnProfile.server, "udppsk", vpnProfile.ipsecIdentifier, vpnProfile.ipsecSecret, "1701"};
            } else if (i == 2) {
                c = 1;
                c2 = 5;
                c3 = '\n';
                strArr = new String[]{str6, vpnProfile.server, "udprsa", str, str2, str3, str4, "1701"};
            } else if (i == 5) {
                c = 1;
                c2 = 5;
                c3 = '\n';
                strArr = new String[]{str6, vpnProfile.server, "hybridrsa", str3, str4, vpnProfile.username, vpnProfile.password, "", hostAddress};
            } else {
                if (i == 10) {
                    startVpnProfilePrivileged(vpnProfile, "[Legacy VPN]");
                    return;
                }
                c2 = 5;
                c3 = '\n';
                strArr = null;
                c = 1;
            }
            if (!MdfUtils.isMdfEnforced()) {
                vpnProfile.ocspServerUrl = "";
            }
            switch (vpnProfile.type) {
                case 3:
                    i2 = 2;
                    strArr = new String[9];
                    strArr[0] = vpnProfile.server;
                    strArr[1] = "xauthpsk";
                    strArr[2] = vpnProfile.ipsecIdentifier;
                    strArr[3] = vpnProfile.ipsecSecret;
                    strArr[4] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr[c2] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr[6] = str102;
                    strArr[7] = vpnProfile.username;
                    strArr[8] = vpnProfile.password;
                    break;
                case 4:
                    i2 = 2;
                    String[] strArr3 = new String[12];
                    strArr3[0] = vpnProfile.server;
                    strArr3[1] = "xauthrsa";
                    strArr3[2] = str;
                    strArr3[3] = str2;
                    strArr3[4] = str3;
                    strArr3[c2] = str4;
                    strArr3[6] = vpnProfile.ocspServerUrl;
                    strArr3[7] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr3[8] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr3[9] = str102;
                    strArr3[c3] = vpnProfile.username;
                    strArr3[11] = vpnProfile.password;
                    strArr = strArr3;
                    break;
                case 5:
                default:
                    i2 = 2;
                    break;
                case 6:
                    i2 = 2;
                    strArr = new String[9];
                    strArr[0] = vpnProfile.server;
                    strArr[1] = "ikev2eap";
                    strArr[2] = vpnProfile.ipsecIdentifier;
                    strArr[3] = str3;
                    strArr[4] = vpnProfile.username;
                    strArr[c2] = vpnProfile.password;
                    strArr[6] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr[7] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr[8] = str102;
                    break;
                case 7:
                    i2 = 2;
                    strArr = new String[7];
                    strArr[0] = vpnProfile.server;
                    strArr[1] = "ikev2psk";
                    strArr[2] = vpnProfile.ipsecIdentifier;
                    strArr[3] = vpnProfile.ipsecSecret;
                    strArr[4] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr[c2] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr[6] = str102;
                    break;
                case 8:
                    i2 = 2;
                    strArr = new String[11];
                    strArr[0] = vpnProfile.server;
                    strArr[1] = "ikev2rsa";
                    strArr[2] = vpnProfile.ipsecIdentifier;
                    strArr[3] = str;
                    strArr[4] = str2;
                    strArr[c2] = str3;
                    strArr[6] = str4;
                    strArr[7] = vpnProfile.ocspServerUrl;
                    strArr[8] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr[9] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr[c3] = str102;
                    break;
                case 9:
                    strArr = new String[11];
                    strArr[0] = vpnProfile.server;
                    strArr[c] = "ikev2eaptls";
                    i2 = 2;
                    strArr[2] = vpnProfile.ipsecIdentifier;
                    strArr[3] = str;
                    strArr[4] = str2;
                    strArr[c2] = str3;
                    strArr[6] = str4;
                    strArr[7] = vpnProfile.ocspServerUrl;
                    strArr[8] = MdfUtils.isMdfEnforced() ? "Enforcing" : "None";
                    strArr[9] = vpnProfile.isPFS ? "+pfs" : "nonpfs";
                    strArr[c3] = str102;
                    break;
            }
            if (isVpnConnectionSecure(strArr)) {
                AuditLog.logAsUser(5, 5, false, Process.myPid(), "Vpn", String.format("Connecting to VPN network %s to server address : %s from type %s failed - unsecure", vpnProfile.name, vpnProfile.server, getVpnTypeString(vpnProfile.type)), "", UserHandle.getUserId(Binder.getCallingUid()));
                return;
            }
            int i3 = vpnProfile.type;
            if (i3 == 0) {
                z = true;
                String[] strArr4 = new String[20];
                strArr4[0] = str6;
                strArr4[1] = "pptp";
                strArr4[i2] = vpnProfile.server;
                strArr4[3] = "1723";
                strArr4[4] = "name";
                strArr4[c2] = vpnProfile.username;
                strArr4[6] = "password";
                strArr4[7] = vpnProfile.password;
                strArr4[8] = "linkname";
                strArr4[9] = "vpn";
                strArr4[c3] = "refuse-eap";
                strArr4[11] = "nodefaultroute";
                strArr4[12] = "usepeerdns";
                strArr4[13] = "idle";
                strArr4[14] = "1800";
                strArr4[15] = "mtu";
                strArr4[16] = "1350";
                strArr4[17] = "mru";
                strArr4[18] = "1350";
                boolean z2 = vpnProfile.mppe;
                strArr4[19] = z2 ? "+mppe" : "nomppe";
                if (z2) {
                    strArr2 = (String[]) Arrays.copyOf(strArr4, 21);
                    strArr2[strArr2.length - 1] = "-pap";
                } else {
                    strArr2 = strArr4;
                }
            } else if (i3 == 1 || i3 == i2) {
                z = true;
                strArr2 = new String[]{str6, "l2tp", vpnProfile.server, "1701", vpnProfile.l2tpSecret, "name", vpnProfile.username, "password", vpnProfile.password, "linkname", "vpn", "refuse-eap", "nodefaultroute", "usepeerdns", "idle", "1800", "mtu", "1300", "mru", "1300"};
            } else {
                z = true;
                strArr2 = null;
            }
            VpnConfig vpnConfig = new VpnConfig();
            vpnConfig.legacy = z;
            vpnConfig.user = vpnProfile.key;
            vpnConfig.interfaze = str6;
            String str11 = vpnProfile.name;
            vpnConfig.session = str11;
            vpnConfig.isMetered = false;
            vpnConfig.proxyInfo = vpnProfile.proxy;
            if (network != null) {
                vpnConfig.underlyingNetworks = new Network[]{network};
            }
            this.mProfileName = str11;
            vpnConfig.addLegacyRoutes(vpnProfile.routes);
            if (!vpnProfile.dnsServers.isEmpty()) {
                vpnConfig.dnsServers = Arrays.asList(vpnProfile.dnsServers.split(" +"));
            }
            if (!vpnProfile.searchDomains.isEmpty()) {
                vpnConfig.searchDomains = Arrays.asList(vpnProfile.searchDomains.split(" +"));
            }
            startLegacyVpn(vpnConfig, strArr, strArr2, vpnProfile);
        } catch (IOException | java.security.KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new IllegalStateException("Failed to load credentials from AndroidKeyStore", e);
        }
    }

    public final synchronized void startLegacyVpn(VpnConfig vpnConfig, String[] strArr, String[] strArr2, VpnProfile vpnProfile) {
        stopVpnRunnerPrivileged();
        prepareInternal("[Legacy VPN]");
        updateState(NetworkInfo.DetailedState.CONNECTING, "startLegacyVpn");
        this.mVpnRunner = new LegacyVpnRunner(vpnConfig, strArr, strArr2, vpnProfile);
        startLegacyVpnRunner();
    }

    public void startLegacyVpnRunner() {
        this.mVpnRunner.start();
    }

    public final boolean isSettingsVpnLocked() {
        return this.mVpnRunner != null && "[Legacy VPN]".equals(this.mPackage);
    }

    public synchronized void stopVpnRunnerPrivileged() {
        if (isSettingsVpnLocked()) {
            VpnRunner vpnRunner = this.mVpnRunner;
            boolean z = vpnRunner instanceof LegacyVpnRunner;
            vpnRunner.exit();
            this.mVpnRunner = null;
            if (z) {
                synchronized ("LegacyVpnRunner") {
                }
            }
        }
    }

    public synchronized LegacyVpnInfo getLegacyVpnInfo() {
        enforceControlPermission();
        return getLegacyVpnInfoPrivileged();
    }

    public final synchronized LegacyVpnInfo getLegacyVpnInfoPrivileged() {
        if (!isSettingsVpnLocked()) {
            return null;
        }
        LegacyVpnInfo legacyVpnInfo = new LegacyVpnInfo();
        legacyVpnInfo.key = this.mConfig.user;
        legacyVpnInfo.state = this.mLegacyState;
        if (this.mNetworkInfo.isConnected()) {
            legacyVpnInfo.intent = this.mStatusIntent;
        }
        return legacyVpnInfo;
    }

    public synchronized VpnConfig getLegacyVpnConfig() {
        if (!isSettingsVpnLocked()) {
            return null;
        }
        return this.mConfig;
    }

    public final synchronized NetworkCapabilities getRedactedNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        return this.mConnectivityManager.getRedactedNetworkCapabilitiesForPackage(networkCapabilities, this.mOwnerUID, this.mPackage);
    }

    public final synchronized LinkProperties getRedactedLinkProperties(LinkProperties linkProperties) {
        if (linkProperties == null) {
            return null;
        }
        return this.mConnectivityManager.getRedactedLinkPropertiesForPackage(linkProperties, this.mOwnerUID, this.mPackage);
    }

    abstract class VpnRunner extends Thread {
        public abstract void exitVpnRunner();

        public VpnRunner(String str) {
            super(str);
        }

        public final void exit() {
            synchronized (Vpn.this) {
                exitVpnRunner();
                Vpn.this.cleanupVpnStateLocked();
            }
        }
    }

    public static boolean isIPv6Only(List list) {
        Iterator it = list.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            LinkAddress linkAddress = (LinkAddress) it.next();
            z |= linkAddress.isIpv6();
            z2 |= linkAddress.isIpv4();
        }
        return z && !z2;
    }

    public final void setVpnNetworkPreference(final String str, final Set set) {
        BinderUtils.withCleanCallingIdentity(new BinderUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda0
            public final void run() {
                Vpn.this.lambda$setVpnNetworkPreference$0(str, set);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVpnNetworkPreference$0(String str, Set set) {
        this.mConnectivityManager.setVpnDefaultForUids(str, set);
    }

    public final void clearVpnNetworkPreference(final String str) {
        BinderUtils.withCleanCallingIdentity(new BinderUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda2
            public final void run() {
                Vpn.this.lambda$clearVpnNetworkPreference$1(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearVpnNetworkPreference$1(String str) {
        this.mConnectivityManager.setVpnDefaultForUids(str, Collections.EMPTY_LIST);
    }

    public class IkeV2VpnRunner extends VpnRunner implements IkeV2VpnRunnerCallback {
        public Network mActiveNetwork;
        public CarrierConfigManager.CarrierConfigChangeListener mCarrierConfigChangeListener;
        public int mCurrentToken;
        public int mDataStallRetryCount;
        public VpnConnectivityDiagnosticsCallback mDiagnosticsCallback;
        public final ScheduledThreadPoolExecutor mExecutor;
        public IkeSessionConnectionInfo mIkeConnectionInfo;
        public final IpSecManager mIpSecManager;
        public boolean mIsRunning;
        public boolean mMobikeEnabled;
        public final ConnectivityManager.NetworkCallback mNetworkCallback;
        public final Ikev2VpnProfile mProfile;
        public int mRetryCount;
        public ScheduledFuture mScheduledHandleDataStallFuture;
        public ScheduledFuture mScheduledHandleNetworkLostFuture;
        public ScheduledFuture mScheduledHandleRetryIkeSessionFuture;
        public IkeSessionWrapper mSession;
        public final String mSessionKey;
        public IpSecManager.IpSecTunnelInterface mTunnelIface;
        public LinkProperties mUnderlyingLinkProperties;
        public NetworkCapabilities mUnderlyingNetworkCapabilities;

        public IkeV2VpnRunner(Ikev2VpnProfile ikev2VpnProfile, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            super("IkeV2VpnRunner");
            this.mIsRunning = true;
            this.mCurrentToken = -1;
            this.mMobikeEnabled = false;
            this.mDataStallRetryCount = 0;
            this.mRetryCount = 0;
            this.mCarrierConfigChangeListener = new CarrierConfigManager.CarrierConfigChangeListener() { // from class: com.android.server.connectivity.Vpn.IkeV2VpnRunner.1
                @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
                public void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                    Vpn.this.mEventChanges.log("[CarrierConfig] Changed on slot " + i + " subId=" + i2 + " carrerId=" + i3 + " specificCarrierId=" + i4);
                    synchronized (Vpn.this) {
                        Vpn.this.mCachedCarrierConfigInfoPerSubId.remove(i2);
                        IkeV2VpnRunner ikeV2VpnRunner = IkeV2VpnRunner.this;
                        if (Vpn.this.mVpnRunner != ikeV2VpnRunner) {
                            return;
                        }
                        ikeV2VpnRunner.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(ikeV2VpnRunner.mActiveNetwork);
                    }
                }
            };
            this.mProfile = ikev2VpnProfile;
            this.mExecutor = scheduledThreadPoolExecutor;
            this.mIpSecManager = (IpSecManager) Vpn.this.mContext.getSystemService(INetd.IPSEC_INTERFACE_PREFIX);
            this.mNetworkCallback = new VpnIkev2Utils.Ikev2VpnNetworkCallback("IkeV2VpnRunner", this, scheduledThreadPoolExecutor);
            String uuid = UUID.randomUUID().toString();
            this.mSessionKey = uuid;
            Log.d("IkeV2VpnRunner", "Generate session key = " + uuid);
            scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
            scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            scheduledThreadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.RejectedExecutionHandler
                public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                    Vpn.IkeV2VpnRunner.lambda$new$0(runnable, threadPoolExecutor);
                }
            });
            int i = Vpn.this.mUserId;
            VpnConfig vpnConfig = Vpn.this.mConfig;
            Vpn.this.setVpnNetworkPreference(uuid, Vpn.this.createUserAndRestrictedProfilesRanges(i, vpnConfig.allowedApplications, vpnConfig.disallowedApplications));
            Vpn.this.mCarrierConfigManager.registerCarrierConfigChangeListener(scheduledThreadPoolExecutor, this.mCarrierConfigChangeListener);
        }

        public static /* synthetic */ void lambda$new$0(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            Log.d("IkeV2VpnRunner", "Runnable " + runnable + " rejected by the mExecutor");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            if (this.mProfile.isRestrictedToTestNetworks()) {
                Vpn.this.mConnectivityManager.requestNetwork(new NetworkRequest.Builder().clearCapabilities().addTransportType(7).addCapability(15).build(), this.mNetworkCallback);
            } else {
                Vpn.this.mConnectivityManager.registerSystemDefaultNetworkCallback(this.mNetworkCallback, new Handler(Vpn.this.mLooper));
            }
            NetworkRequest build = new NetworkRequest.Builder().addTransportType(4).removeCapability(15).build();
            this.mDiagnosticsCallback = new VpnConnectivityDiagnosticsCallback();
            Vpn.this.mConnectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(build, this.mExecutor, this.mDiagnosticsCallback);
        }

        public final boolean isActiveNetwork(Network network) {
            return Objects.equals(this.mActiveNetwork, network) && this.mIsRunning;
        }

        public final boolean isActiveToken(int i) {
            return this.mCurrentToken == i && this.mIsRunning;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onIkeOpened(int i, IkeSessionConfiguration ikeSessionConfiguration) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeOpened obsolete token=" + i);
                StringBuilder sb = new StringBuilder();
                sb.append("onIkeOpened called for obsolete token ");
                sb.append(i);
                Log.d("IkeV2VpnRunner", sb.toString());
                return;
            }
            this.mMobikeEnabled = ikeSessionConfiguration.isIkeExtensionEnabled(2);
            IkeSessionConnectionInfo ikeSessionConnectionInfo = ikeSessionConfiguration.getIkeSessionConnectionInfo();
            Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeOpened token=" + i + ", localAddr=" + ikeSessionConnectionInfo.getLocalAddress() + ", network=" + ikeSessionConnectionInfo.getNetwork() + ", mobikeEnabled= " + this.mMobikeEnabled);
            onIkeConnectionInfoChanged(i, ikeSessionConnectionInfo);
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onIkeConnectionInfoChanged(int i, IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeConnectionInfoChanged obsolete token=" + i);
                StringBuilder sb = new StringBuilder();
                sb.append("onIkeConnectionInfoChanged called for obsolete token ");
                sb.append(i);
                Log.d("IkeV2VpnRunner", sb.toString());
                return;
            }
            Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeConnectionInfoChanged token=" + i + ", localAddr=" + ikeSessionConnectionInfo.getLocalAddress() + ", network=" + ikeSessionConnectionInfo.getNetwork());
            this.mIkeConnectionInfo = ikeSessionConnectionInfo;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildOpened(int i, ChildSessionConfiguration childSessionConfiguration) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildOpened obsolete token=" + i);
                StringBuilder sb = new StringBuilder();
                sb.append("onChildOpened called for obsolete token ");
                sb.append(i);
                Log.d("IkeV2VpnRunner", sb.toString());
                return;
            }
            Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildOpened token=" + i + ", addr=" + TextUtils.join(", ", childSessionConfiguration.getInternalAddresses()) + " dns=" + TextUtils.join(", ", childSessionConfiguration.getInternalDnsServers()));
            try {
                String interfaceName = this.mTunnelIface.getInterfaceName();
                List<LinkAddress> internalAddresses = childSessionConfiguration.getInternalAddresses();
                ArrayList arrayList = new ArrayList();
                int calculateVpnMtu = calculateVpnMtu();
                if (Vpn.isIPv6Only(internalAddresses) && calculateVpnMtu < 1280) {
                    onSessionLost(i, new IkeIOException(new IOException("No valid addresses for MTU < 1280")));
                    return;
                }
                Collection routesFromTrafficSelectors = VpnIkev2Utils.getRoutesFromTrafficSelectors(childSessionConfiguration.getOutboundTrafficSelectors());
                for (LinkAddress linkAddress : internalAddresses) {
                    this.mTunnelIface.addAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                }
                Iterator it = childSessionConfiguration.getInternalDnsServers().iterator();
                while (it.hasNext()) {
                    arrayList.add(((InetAddress) it.next()).getHostAddress());
                }
                Network network = this.mIkeConnectionInfo.getNetwork();
                synchronized (Vpn.this) {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    }
                    vpn.mInterface = interfaceName;
                    VpnConfig vpnConfig = vpn.mConfig;
                    vpnConfig.mtu = calculateVpnMtu;
                    vpnConfig.interfaze = interfaceName;
                    vpnConfig.addresses.clear();
                    Vpn.this.mConfig.addresses.addAll(internalAddresses);
                    Vpn.this.mConfig.routes.clear();
                    Vpn.this.mConfig.routes.addAll(routesFromTrafficSelectors);
                    VpnConfig vpnConfig2 = Vpn.this.mConfig;
                    if (vpnConfig2.dnsServers == null) {
                        vpnConfig2.dnsServers = new ArrayList();
                    }
                    Vpn.this.mConfig.dnsServers.clear();
                    Vpn.this.mConfig.dnsServers.addAll(arrayList);
                    Vpn vpn2 = Vpn.this;
                    vpn2.mConfig.underlyingNetworks = new Network[]{network};
                    NetworkAgent networkAgent = vpn2.mNetworkAgent;
                    if (networkAgent == null) {
                        if (vpn2.isSettingsVpnLocked()) {
                            Vpn.this.prepareStatusIntent();
                        }
                        Vpn.this.agentConnect(new ValidationStatusCallback() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4
                            @Override // com.android.server.connectivity.Vpn.ValidationStatusCallback
                            public final void onValidationStatus(int i2) {
                                Vpn.IkeV2VpnRunner.this.onValidationStatus(i2);
                            }
                        });
                    } else {
                        vpn2.doSetUnderlyingNetworks(networkAgent, Collections.singletonList(network));
                        Vpn.this.mNetworkCapabilities = new NetworkCapabilities.Builder(Vpn.this.mNetworkCapabilities).setUnderlyingNetworks(Collections.singletonList(network)).build();
                        Vpn.doSendLinkProperties(networkAgent, Vpn.this.makeLinkProperties());
                        this.mRetryCount = 0;
                    }
                }
            } catch (Exception e) {
                Log.d("IkeV2VpnRunner", "Error in ChildOpened for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildTransformCreated(int i, IpSecTransform ipSecTransform, int i2) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildTransformCreated obsolete token=" + i);
                StringBuilder sb = new StringBuilder();
                sb.append("ChildTransformCreated for obsolete token ");
                sb.append(i);
                Log.d("IkeV2VpnRunner", sb.toString());
                return;
            }
            Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildTransformCreated token=" + i + ", direction=" + i2 + ", transform=" + ipSecTransform);
            try {
                this.mTunnelIface.setUnderlyingNetwork(this.mIkeConnectionInfo.getNetwork());
                this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, i2, ipSecTransform);
            } catch (IOException e) {
                Log.d("IkeV2VpnRunner", "Transform application failed for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildMigrated(int i, IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildMigrated obsolete token=" + i);
                StringBuilder sb = new StringBuilder();
                sb.append("onChildMigrated for obsolete token ");
                sb.append(i);
                Log.d("IkeV2VpnRunner", sb.toString());
                return;
            }
            Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildMigrated token=" + i + ", in=" + ipSecTransform + ", out=" + ipSecTransform2);
            Network network = this.mIkeConnectionInfo.getNetwork();
            try {
                synchronized (Vpn.this) {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    }
                    LinkProperties makeLinkProperties = vpn.makeLinkProperties();
                    boolean z = !Arrays.equals(Vpn.this.mConfig.underlyingNetworks, new Network[]{network});
                    VpnConfig vpnConfig = Vpn.this.mConfig;
                    vpnConfig.underlyingNetworks = new Network[]{network};
                    vpnConfig.mtu = calculateVpnMtu();
                    LinkProperties makeLinkProperties2 = Vpn.this.makeLinkProperties();
                    if (makeLinkProperties2.getLinkAddresses().isEmpty()) {
                        onSessionLost(i, new IkeIOException(new IOException("No valid addresses for MTU < 1280")));
                        return;
                    }
                    HashSet<LinkAddress> hashSet = new HashSet(makeLinkProperties.getLinkAddresses());
                    hashSet.removeAll(makeLinkProperties2.getLinkAddresses());
                    if (!hashSet.isEmpty()) {
                        Vpn vpn2 = Vpn.this;
                        vpn2.startNewNetworkAgent(vpn2.mNetworkAgent, "MTU too low for IPv6; restarting network agent");
                        for (LinkAddress linkAddress : hashSet) {
                            this.mTunnelIface.removeAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                        }
                    } else {
                        if (!makeLinkProperties2.equals(makeLinkProperties)) {
                            Vpn.doSendLinkProperties(Vpn.this.mNetworkAgent, makeLinkProperties2);
                        }
                        if (z) {
                            Vpn.this.mNetworkCapabilities = new NetworkCapabilities.Builder(Vpn.this.mNetworkCapabilities).setUnderlyingNetworks(Collections.singletonList(network)).build();
                            Vpn vpn3 = Vpn.this;
                            vpn3.doSetUnderlyingNetworks(vpn3.mNetworkAgent, Collections.singletonList(network));
                        }
                    }
                    this.mTunnelIface.setUnderlyingNetwork(network);
                    this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, 0, ipSecTransform);
                    this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, 1, ipSecTransform2);
                }
            } catch (IOException e) {
                Log.d("IkeV2VpnRunner", "Transform application failed for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkChanged(Network network) {
            Vpn.this.mEventChanges.log("[UnderlyingNW] Default network changed to " + network);
            Log.d("IkeV2VpnRunner", "onDefaultNetworkChanged: " + network);
            cancelRetryNewIkeSessionFuture();
            cancelHandleNetworkLostTimeout();
            if (!this.mIsRunning) {
                Log.d("IkeV2VpnRunner", "onDefaultNetworkChanged after exit");
                return;
            }
            this.mActiveNetwork = network;
            this.mUnderlyingLinkProperties = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mRetryCount = 0;
        }

        public final IkeSessionParams getIkeSessionParams(Network network) {
            IkeSessionParams.Builder makeIkeSessionParamsBuilder;
            IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                makeIkeSessionParamsBuilder = new IkeSessionParams.Builder(ikeTunnelConnectionParams.getIkeSessionParams()).setNetwork(network);
            } else {
                makeIkeSessionParamsBuilder = VpnIkev2Utils.makeIkeSessionParamsBuilder(Vpn.this.mContext, this.mProfile, network);
            }
            if (this.mProfile.isAutomaticNattKeepaliveTimerEnabled()) {
                makeIkeSessionParamsBuilder.setNattKeepAliveDelaySeconds(guessNattKeepaliveTimerForNetwork());
            }
            if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                makeIkeSessionParamsBuilder.setIpVersion(guessEspIpVersionForNetwork());
                makeIkeSessionParamsBuilder.setEncapType(guessEspEncapTypeForNetwork());
            }
            return makeIkeSessionParamsBuilder.build();
        }

        public final ChildSessionParams getChildSessionParams() {
            IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                return ikeTunnelConnectionParams.getTunnelModeChildSessionParams();
            }
            return VpnIkev2Utils.buildChildSessionParams(this.mProfile.getAllowedAlgorithms());
        }

        public final int calculateVpnMtu() {
            Network network = this.mIkeConnectionInfo.getNetwork();
            LinkProperties linkProperties = Vpn.this.mConnectivityManager.getLinkProperties(network);
            if (network == null || linkProperties == null) {
                return this.mProfile.getMaxMtu();
            }
            int mtu = linkProperties.getMtu();
            if (mtu == 0) {
                try {
                    mtu = Vpn.this.mDeps.getJavaNetworkInterfaceMtu(linkProperties.getInterfaceName(), this.mProfile.getMaxMtu());
                } catch (SocketException e) {
                    Log.d("IkeV2VpnRunner", "Got a SocketException when getting MTU from kernel: " + e);
                    return this.mProfile.getMaxMtu();
                }
            }
            return Vpn.this.mDeps.calculateVpnMtu(getChildSessionParams().getSaProposals(), this.mProfile.getMaxMtu(), mtu, this.mIkeConnectionInfo.getLocalAddress() instanceof Inet4Address);
        }

        public final void startOrMigrateIkeSession(Network network) {
            if (network == null) {
                Log.d("IkeV2VpnRunner", "There is no active network for starting an IKE session");
            } else {
                if (maybeMigrateIkeSessionAndUpdateVpnTransportInfo(network)) {
                    return;
                }
                startIkeSession(network);
            }
        }

        public final int guessEspIpVersionForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof VcnTransportInfo) {
                Log.d("IkeV2VpnRunner", "Running over VCN, esp IP version is auto");
                return 0;
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.ipVersion : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                Log.d("IkeV2VpnRunner", "Get customized IP version (" + i + ") on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        public final int guessEspEncapTypeForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof VcnTransportInfo) {
                Log.d("IkeV2VpnRunner", "Running over VCN, encap type is auto");
                return 0;
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.encapType : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                Log.d("IkeV2VpnRunner", "Get customized encap type (" + i + ") on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        public final int guessNattKeepaliveTimerForNetwork() {
            VcnTransportInfo transportInfo = this.mUnderlyingNetworkCapabilities.getTransportInfo();
            if (transportInfo instanceof VcnTransportInfo) {
                int minUdpPort4500NatTimeoutSeconds = transportInfo.getMinUdpPort4500NatTimeoutSeconds();
                Log.d("IkeV2VpnRunner", "Running over VCN, keepalive timer : " + minUdpPort4500NatTimeoutSeconds + "s");
                if (-1 != minUdpPort4500NatTimeoutSeconds) {
                    return minUdpPort4500NatTimeoutSeconds;
                }
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.keepaliveDelaySec : 30;
            if (carrierConfigForUnderlyingNetwork != null) {
                Log.d("IkeV2VpnRunner", "Get customized keepalive (" + i + "s) on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        public final CarrierConfigInfo getCarrierConfigForUnderlyingNetwork() {
            int cellSubIdForNetworkCapabilities = Vpn.getCellSubIdForNetworkCapabilities(this.mUnderlyingNetworkCapabilities);
            if (cellSubIdForNetworkCapabilities == -1) {
                Log.d("IkeV2VpnRunner", "Underlying network is not a cellular network");
                return null;
            }
            synchronized (Vpn.this) {
                if (Vpn.this.mCachedCarrierConfigInfoPerSubId.contains(cellSubIdForNetworkCapabilities)) {
                    Log.d("IkeV2VpnRunner", "Get cached config");
                    return (CarrierConfigInfo) Vpn.this.mCachedCarrierConfigInfoPerSubId.get(cellSubIdForNetworkCapabilities);
                }
                TelephonyManager createForSubscriptionId = Vpn.this.mTelephonyManager.createForSubscriptionId(cellSubIdForNetworkCapabilities);
                if (createForSubscriptionId.getSimApplicationState() != 10) {
                    Log.d("IkeV2VpnRunner", "SIM card is not ready on sub " + cellSubIdForNetworkCapabilities);
                    return null;
                }
                PersistableBundle configForSubId = Vpn.this.mCarrierConfigManager.getConfigForSubId(cellSubIdForNetworkCapabilities);
                if (!CarrierConfigManager.isConfigForIdentifiedCarrier(configForSubId)) {
                    return null;
                }
                CarrierConfigInfo buildCarrierConfigInfo = buildCarrierConfigInfo(createForSubscriptionId.getSimOperator(cellSubIdForNetworkCapabilities), configForSubId.getInt("min_udp_port_4500_nat_timeout_sec_int"), configForSubId.getInt("preferred_ike_protocol_int", -1));
                synchronized (Vpn.this) {
                    Vpn.this.mCachedCarrierConfigInfoPerSubId.put(cellSubIdForNetworkCapabilities, buildCarrierConfigInfo);
                }
                return buildCarrierConfigInfo;
            }
        }

        public final CarrierConfigInfo buildCarrierConfigInfo(String str, int i, int i2) {
            int i3;
            int i4;
            if (i2 != 0) {
                i3 = 4;
                if (i2 != 40) {
                    if (i2 != 60) {
                        i4 = i2 == 61 ? -1 : 17;
                    }
                    i3 = 6;
                }
            } else {
                i3 = 0;
                i4 = 0;
            }
            return new CarrierConfigInfo(str, i, i4, i3);
        }

        public final int getOrGuessKeepaliveDelaySeconds() {
            if (this.mProfile.isAutomaticNattKeepaliveTimerEnabled()) {
                return guessNattKeepaliveTimerForNetwork();
            }
            if (this.mProfile.getIkeTunnelConnectionParams() != null) {
                return this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getNattKeepAliveDelaySeconds();
            }
            return 300;
        }

        public boolean maybeMigrateIkeSessionAndUpdateVpnTransportInfo(Network network) {
            int orGuessKeepaliveDelaySeconds = getOrGuessKeepaliveDelaySeconds();
            boolean maybeMigrateIkeSession = maybeMigrateIkeSession(network, orGuessKeepaliveDelaySeconds);
            if (maybeMigrateIkeSession) {
                updateVpnTransportInfoAndNetCap(orGuessKeepaliveDelaySeconds);
            }
            return maybeMigrateIkeSession;
        }

        public void updateVpnTransportInfoAndNetCap(int i) {
            int activeVpnType = Vpn.this.getActiveVpnType();
            Vpn vpn = Vpn.this;
            VpnConfig vpnConfig = vpn.mConfig;
            VpnTransportInfo vpnTransportInfo = new VpnTransportInfo(activeVpnType, vpnConfig.session, vpnConfig.allowBypass && !vpn.mLockdown, Vpn.areLongLivedTcpConnectionsExpensive(i));
            if (!vpnTransportInfo.equals(Vpn.this.mNetworkCapabilities.getTransportInfo())) {
                Vpn.this.mNetworkCapabilities = new NetworkCapabilities.Builder(Vpn.this.mNetworkCapabilities).setTransportInfo(vpnTransportInfo).build();
                Vpn.this.mEventChanges.log("[VPNRunner] Update agent caps " + Vpn.this.mNetworkCapabilities);
                Vpn vpn2 = Vpn.this;
                Vpn.doSendNetworkCapabilities(vpn2.mNetworkAgent, vpn2.mNetworkCapabilities);
            }
        }

        public final boolean maybeMigrateIkeSession(Network network, int i) {
            int i2;
            int i3 = 0;
            if (this.mSession == null || !this.mMobikeEnabled) {
                return false;
            }
            Log.d("IkeV2VpnRunner", "Migrate IKE Session with token " + this.mCurrentToken + " to network " + network);
            if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                i3 = guessEspIpVersionForNetwork();
                i2 = guessEspEncapTypeForNetwork();
            } else if (this.mProfile.getIkeTunnelConnectionParams() != null) {
                i3 = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getIpVersion();
                i2 = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getEncapType();
            } else {
                i2 = 0;
            }
            this.mSession.setNetwork(network, i3, i2, i);
            return true;
        }

        public final void startIkeSession(Network network) {
            Log.d("IkeV2VpnRunner", "Start new IKE session on network " + network);
            Vpn.this.mEventChanges.log("[IKE] Start IKE session over " + network);
            try {
                synchronized (Vpn.this) {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    }
                    vpn.mInterface = null;
                    resetIkeState();
                    InetAddress localHost = InetAddress.getLocalHost();
                    this.mTunnelIface = this.mIpSecManager.createIpSecTunnelInterface(localHost, localHost, network);
                    NetdUtils.setInterfaceUp(Vpn.this.mNetd, this.mTunnelIface.getInterfaceName());
                    int i = this.mCurrentToken + 1;
                    this.mCurrentToken = i;
                    this.mSession = Vpn.this.mIkev2SessionCreator.createIkeSession(Vpn.this.mContext, getIkeSessionParams(network), getChildSessionParams(), this.mExecutor, new VpnIkev2Utils.IkeSessionCallbackImpl("IkeV2VpnRunner", this, i), new VpnIkev2Utils.ChildSessionCallbackImpl("IkeV2VpnRunner", this, i));
                    Log.d("IkeV2VpnRunner", "IKE session started for token " + i);
                }
            } catch (Exception e) {
                Log.i("IkeV2VpnRunner", "Setup failed for token " + this.mCurrentToken + ". Aborting", e);
                onSessionLost(this.mCurrentToken, e);
            }
        }

        public final void scheduleStartIkeSession(long j) {
            if (this.mScheduledHandleRetryIkeSessionFuture != null) {
                Log.d("IkeV2VpnRunner", "There is a pending retrying task, skip the new retrying task");
                return;
            }
            if (-1 == j) {
                Dependencies dependencies = Vpn.this.mDeps;
                int i = this.mRetryCount;
                this.mRetryCount = i + 1;
                j = dependencies.getNextRetryDelayMs(i);
            }
            Log.d("IkeV2VpnRunner", "Retry new IKE session after " + j + " milliseconds.");
            this.mScheduledHandleRetryIkeSessionFuture = this.mExecutor.schedule(new Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Vpn.IkeV2VpnRunner.this.lambda$scheduleStartIkeSession$1();
                }
            }, j, TimeUnit.MILLISECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleStartIkeSession$1() {
            startOrMigrateIkeSession(this.mActiveNetwork);
            this.mScheduledHandleRetryIkeSessionFuture = null;
        }

        public final boolean significantCapsChange(NetworkCapabilities networkCapabilities, NetworkCapabilities networkCapabilities2) {
            if (networkCapabilities == networkCapabilities2) {
                return false;
            }
            return (networkCapabilities != null && networkCapabilities2 != null && Arrays.equals(networkCapabilities.getTransportTypes(), networkCapabilities2.getTransportTypes()) && Arrays.equals(networkCapabilities.getCapabilities(), networkCapabilities2.getCapabilities()) && Arrays.equals(networkCapabilities.getEnterpriseIds(), networkCapabilities2.getEnterpriseIds()) && Objects.equals(networkCapabilities.getTransportInfo(), networkCapabilities2.getTransportInfo()) && Objects.equals(networkCapabilities.getAllowedUids(), networkCapabilities2.getAllowedUids()) && Objects.equals(networkCapabilities.getUnderlyingNetworks(), networkCapabilities2.getUnderlyingNetworks()) && Objects.equals(networkCapabilities.getNetworkSpecifier(), networkCapabilities2.getNetworkSpecifier())) ? false : true;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities) {
            if (significantCapsChange(this.mUnderlyingNetworkCapabilities, networkCapabilities)) {
                Vpn.this.mEventChanges.log("[UnderlyingNW] Cap changed from " + this.mUnderlyingNetworkCapabilities + " to " + networkCapabilities);
            }
            NetworkCapabilities networkCapabilities2 = this.mUnderlyingNetworkCapabilities;
            this.mUnderlyingNetworkCapabilities = networkCapabilities;
            if (networkCapabilities2 == null || !networkCapabilities.getSubscriptionIds().equals(networkCapabilities2.getSubscriptionIds())) {
                scheduleStartIkeSession(300L);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkLinkPropertiesChanged(LinkProperties linkProperties) {
            LinkProperties linkProperties2 = this.mUnderlyingLinkProperties;
            Vpn.this.mEventChanges.log("[UnderlyingNW] Lp changed from " + linkProperties2 + " to " + linkProperties);
            this.mUnderlyingLinkProperties = linkProperties;
            if (linkProperties2 == null || !LinkPropertiesUtils.isIdenticalAllLinkAddresses(linkProperties2, linkProperties)) {
                scheduleStartIkeSession(300L);
            }
        }

        public class VpnConnectivityDiagnosticsCallback extends ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback {
            public VpnConnectivityDiagnosticsCallback() {
            }

            @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
            public void onDataStallSuspected(ConnectivityDiagnosticsManager.DataStallReport dataStallReport) {
                synchronized (Vpn.this) {
                    IkeV2VpnRunner ikeV2VpnRunner = IkeV2VpnRunner.this;
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != ikeV2VpnRunner) {
                        return;
                    }
                    NetworkAgent networkAgent = vpn.mNetworkAgent;
                    if (networkAgent != null && networkAgent.getNetwork().equals(dataStallReport.getNetwork()) && !Vpn.this.mDataStallSuspected) {
                        Log.d("IkeV2VpnRunner", "Data stall suspected");
                        IkeV2VpnRunner ikeV2VpnRunner2 = IkeV2VpnRunner.this;
                        ikeV2VpnRunner2.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(ikeV2VpnRunner2.mActiveNetwork);
                        Vpn.this.mDataStallSuspected = true;
                    }
                }
            }
        }

        public void onValidationStatus(int i) {
            Vpn.this.mEventChanges.log("[Validation] validation status " + i);
            if (i == 1) {
                this.mExecutor.execute(new Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        Vpn.IkeV2VpnRunner.this.lambda$onValidationStatus$2();
                    }
                });
                return;
            }
            if (this.mScheduledHandleDataStallFuture != null) {
                return;
            }
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
            Runnable runnable = new Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Vpn.IkeV2VpnRunner.this.lambda$onValidationStatus$3();
                }
            };
            Dependencies dependencies = Vpn.this.mDeps;
            int i2 = this.mDataStallRetryCount;
            this.mDataStallRetryCount = i2 + 1;
            this.mScheduledHandleDataStallFuture = scheduledThreadPoolExecutor.schedule(runnable, dependencies.getDataStallResetSessionSeconds(i2), TimeUnit.SECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onValidationStatus$2() {
            Vpn.this.mDataStallSuspected = false;
            this.mDataStallRetryCount = 0;
            if (this.mScheduledHandleDataStallFuture != null) {
                Log.d("IkeV2VpnRunner", "Recovered from stall. Cancel pending reset action.");
                this.mScheduledHandleDataStallFuture.cancel(false);
                this.mScheduledHandleDataStallFuture = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onValidationStatus$3() {
            if (Vpn.this.mDataStallSuspected) {
                Log.d("IkeV2VpnRunner", "Reset session to recover stalled network");
                startIkeSession(this.mActiveNetwork);
            }
            this.mScheduledHandleDataStallFuture = null;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkLost(final Network network) {
            Vpn.this.mEventChanges.log("[UnderlyingNW] Network lost " + network);
            cancelRetryNewIkeSessionFuture();
            if (!isActiveNetwork(network)) {
                Log.d("IkeV2VpnRunner", "onDefaultNetworkLost called for obsolete network " + network);
                return;
            }
            this.mActiveNetwork = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mUnderlyingLinkProperties = null;
            if (this.mScheduledHandleNetworkLostFuture != null) {
                IllegalStateException illegalStateException = new IllegalStateException("Found a pending mScheduledHandleNetworkLostFuture");
                Log.i("IkeV2VpnRunner", "Unexpected error in onDefaultNetworkLost. Tear down session", illegalStateException);
                handleSessionLost(illegalStateException, network);
                return;
            }
            Log.d("IkeV2VpnRunner", "Schedule a delay handleSessionLost for losing network " + network + " on session with token " + this.mCurrentToken);
            final int i = this.mCurrentToken;
            this.mScheduledHandleNetworkLostFuture = this.mExecutor.schedule(new Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Vpn.IkeV2VpnRunner.this.lambda$onDefaultNetworkLost$4(i, network);
                }
            }, 5000L, TimeUnit.MILLISECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDefaultNetworkLost$4(int i, Network network) {
            if (isActiveToken(i)) {
                handleSessionLost(new IkeNetworkLostException(network), network);
                synchronized (Vpn.this) {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    } else {
                        vpn.updateState(NetworkInfo.DetailedState.DISCONNECTED, "Network lost");
                    }
                }
            } else {
                Log.d("IkeV2VpnRunner", "Scheduled handleSessionLost fired for obsolete token " + i);
            }
            this.mScheduledHandleNetworkLostFuture = null;
        }

        public final void cancelHandleNetworkLostTimeout() {
            if (this.mScheduledHandleNetworkLostFuture != null) {
                Log.d("IkeV2VpnRunner", "Cancel the task for handling network lost timeout");
                this.mScheduledHandleNetworkLostFuture.cancel(false);
                this.mScheduledHandleNetworkLostFuture = null;
            }
        }

        public final void cancelRetryNewIkeSessionFuture() {
            if (this.mScheduledHandleRetryIkeSessionFuture != null) {
                Log.d("IkeV2VpnRunner", "Cancel the task for handling new ike session timeout");
                this.mScheduledHandleRetryIkeSessionFuture.cancel(false);
                this.mScheduledHandleRetryIkeSessionFuture = null;
            }
        }

        public final void markFailedAndDisconnect(Exception exc) {
            synchronized (Vpn.this) {
                Vpn vpn = Vpn.this;
                if (vpn.mVpnRunner != this) {
                    return;
                }
                vpn.updateState(NetworkInfo.DetailedState.FAILED, exc.getMessage());
                lambda$exitVpnRunner$5();
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onSessionLost(int i, Exception exc) {
            String str;
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder();
            sb.append("[IKE] Session lost on network ");
            sb.append(this.mActiveNetwork);
            if (exc == null) {
                str = "";
            } else {
                str = " reason " + exc.getMessage();
            }
            sb.append(str);
            localLog.log(sb.toString());
            Log.d("IkeV2VpnRunner", "onSessionLost() called for token " + i);
            if (!isActiveToken(i)) {
                Log.d("IkeV2VpnRunner", "onSessionLost() called for obsolete token " + i);
                return;
            }
            handleSessionLost(exc, this.mActiveNetwork);
        }

        public final void handleSessionLost(Exception exc, Network network) {
            String str;
            int i;
            cancelHandleNetworkLostTimeout();
            if (exc instanceof IllegalArgumentException) {
                markFailedAndDisconnect(exc);
                return;
            }
            if (exc instanceof IkeProtocolException) {
                IkeProtocolException ikeProtocolException = (IkeProtocolException) exc;
                int errorType = ikeProtocolException.getErrorType();
                int errorType2 = ikeProtocolException.getErrorType();
                r4 = (errorType2 == 14 || errorType2 == 17 || errorType2 == 24 || errorType2 == 34 || errorType2 == 37 || errorType2 == 38) ? 1 : 2;
                str = "android.net.category.EVENT_IKE_ERROR";
                i = errorType;
            } else if (exc instanceof IkeNetworkLostException) {
                i = 2;
                str = "android.net.category.EVENT_NETWORK_ERROR";
            } else {
                int i2 = -1;
                if (exc instanceof IkeNonProtocolException) {
                    if (exc.getCause() instanceof UnknownHostException) {
                        i2 = 0;
                    } else if (exc.getCause() instanceof IkeTimeoutException) {
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                        i = 1;
                    } else if (exc.getCause() instanceof IOException) {
                        i2 = 3;
                    }
                    str = "android.net.category.EVENT_NETWORK_ERROR";
                    i = i2;
                } else {
                    if (exc != null) {
                        Log.wtf("IkeV2VpnRunner", "onSessionLost: exception = " + exc);
                    }
                    str = null;
                    r4 = -1;
                    i = -1;
                }
            }
            synchronized (Vpn.this) {
                if (Vpn.this.mVpnRunner != this) {
                    return;
                }
                if (SdkLevel.isAtLeastT() && str != null && Vpn.isVpnApp(Vpn.this.mPackage)) {
                    Vpn vpn = Vpn.this;
                    vpn.sendEventToVpnManagerApp(str, r4, i, vpn.getPackage(), this.mSessionKey, Vpn.this.makeVpnProfileStateLocked(), this.mActiveNetwork, Vpn.this.getRedactedNetworkCapabilities(this.mUnderlyingNetworkCapabilities), Vpn.this.getRedactedLinkProperties(this.mUnderlyingLinkProperties));
                }
                if (r4 == 1) {
                    markFailedAndDisconnect(exc);
                    return;
                }
                scheduleStartIkeSession(-1L);
                Log.d("IkeV2VpnRunner", "Resetting state for token: " + this.mCurrentToken);
                synchronized (Vpn.this) {
                    Vpn vpn2 = Vpn.this;
                    if (vpn2.mVpnRunner != this) {
                        return;
                    }
                    vpn2.mInterface = null;
                    VpnConfig vpnConfig = vpn2.mConfig;
                    if (vpnConfig != null) {
                        vpnConfig.interfaze = null;
                        if (vpnConfig.routes != null) {
                            ArrayList arrayList = new ArrayList(Vpn.this.mConfig.routes);
                            Vpn.this.mConfig.routes.clear();
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                Vpn.this.mConfig.routes.add(new RouteInfo(((RouteInfo) it.next()).getDestination(), null, null, 7));
                            }
                            Vpn vpn3 = Vpn.this;
                            NetworkAgent networkAgent = vpn3.mNetworkAgent;
                            if (networkAgent != null) {
                                Vpn.doSendLinkProperties(networkAgent, vpn3.makeLinkProperties());
                            }
                        }
                    }
                    resetIkeState();
                }
            }
        }

        public final void resetIkeState() {
            IpSecManager.IpSecTunnelInterface ipSecTunnelInterface = this.mTunnelIface;
            if (ipSecTunnelInterface != null) {
                ipSecTunnelInterface.close();
                this.mTunnelIface = null;
            }
            IkeSessionWrapper ikeSessionWrapper = this.mSession;
            if (ikeSessionWrapper != null) {
                ikeSessionWrapper.kill();
                this.mSession = null;
            }
            this.mIkeConnectionInfo = null;
            this.mMobikeEnabled = false;
        }

        /* renamed from: disconnectVpnRunner, reason: merged with bridge method [inline-methods] */
        public final void lambda$exitVpnRunner$5() {
            Vpn.this.mEventChanges.log("[VPNRunner] Disconnect runner, underlying network" + this.mActiveNetwork);
            this.mActiveNetwork = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mUnderlyingLinkProperties = null;
            this.mIsRunning = false;
            resetIkeState();
            Vpn.this.mCarrierConfigManager.unregisterCarrierConfigChangeListener(this.mCarrierConfigChangeListener);
            Vpn.this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            Vpn.this.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(this.mDiagnosticsCallback);
            Vpn.this.clearVpnNetworkPreference(this.mSessionKey);
            this.mExecutor.shutdown();
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner
        public void exitVpnRunner() {
            try {
                this.mExecutor.execute(new Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Vpn.IkeV2VpnRunner.this.lambda$exitVpnRunner$5();
                    }
                });
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    public class LegacyVpnRunner extends VpnRunner {
        public List ipAddresses;
        public final String[][] mArguments;
        public long mBringupStartTime;
        public final BroadcastReceiver mBroadcastReceiver;
        public final String[] mDaemons;
        public final AtomicInteger mOuterConnection;
        public final String mOuterInterface;
        public final VpnProfile mProfile;
        public String mServerIP;
        public final LocalSocket[] mSockets;
        public final BroadcastReceiver mTetheringChangedReceiver;

        public LegacyVpnRunner(VpnConfig vpnConfig, String[] strArr, String[] strArr2, VpnProfile vpnProfile) {
            super("LegacyVpnRunner");
            NetworkInfo networkInfo;
            this.mOuterConnection = new AtomicInteger(-1);
            this.mBringupStartTime = -1L;
            this.ipAddresses = null;
            this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.connectivity.Vpn.LegacyVpnRunner.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    NetworkInfo networkInfo2;
                    if (Vpn.this.mEnableTeardown && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && intent.getIntExtra("networkType", -1) == LegacyVpnRunner.this.mOuterConnection.get() && (networkInfo2 = (NetworkInfo) intent.getExtra("networkInfo")) != null && !networkInfo2.isConnectedOrConnecting()) {
                        try {
                            Vpn.this.mObserver.interfaceStatusChanged(LegacyVpnRunner.this.mOuterInterface, false);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            };
            this.mTetheringChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.connectivity.Vpn.LegacyVpnRunner.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction() != null && "android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("wifi_state", 14);
                        if (intExtra != 11) {
                            if (intExtra == 13) {
                                if (Vpn.this.mNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                                    LegacyVpnRunner legacyVpnRunner = LegacyVpnRunner.this;
                                    VpnConfig vpnConfig2 = Vpn.this.mConfig;
                                    if (vpnConfig2 == null || vpnConfig2.addresses == null) {
                                        return;
                                    }
                                    if (legacyVpnRunner.ipAddresses == null) {
                                        LegacyVpnRunner.this.ipAddresses = new ArrayList();
                                    }
                                    for (LinkAddress linkAddress : Vpn.this.mConfig.addresses) {
                                        Log.d("LegacyVpnRunner", "addVpnRuleForTethering :" + linkAddress.toString());
                                        Vpn.this.mVpnRules.addVpnRuleForTethering(linkAddress.toString());
                                        LegacyVpnRunner.this.ipAddresses.add(linkAddress.toString());
                                    }
                                    return;
                                }
                                return;
                            }
                            if (intExtra != 14) {
                                return;
                            }
                        }
                        LegacyVpnRunner.this.ipAddresses = null;
                        Log.d("LegacyVpnRunner", "WIFI_AP_STATE_DISABLED or WIFI_AP_STATE_FAILED");
                    }
                }
            };
            if (strArr == null && strArr2 == null) {
                throw new IllegalArgumentException("Arguments to racoon and mtpd must not both be null");
            }
            Vpn.this.mConfig = vpnConfig;
            if (strArr2 == null && !strArr[2].equals("hybridrsa")) {
                this.mDaemons = new String[]{"charon", "mtpd"};
            } else {
                this.mDaemons = new String[]{"racoon", "mtpd"};
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Daemon[0]: ");
            int i = 0;
            sb.append(this.mDaemons[0]);
            Log.i("LegacyVpnRunner", sb.toString());
            this.mArguments = new String[][]{strArr, strArr2};
            this.mSockets = new LocalSocket[this.mDaemons.length];
            String str = Vpn.this.mConfig.interfaze;
            this.mOuterInterface = str;
            this.mProfile = vpnProfile;
            if (!TextUtils.isEmpty(str)) {
                Network[] allNetworks = Vpn.this.mConnectivityManager.getAllNetworks();
                int length = allNetworks.length;
                while (true) {
                    if (i < length) {
                        Network network = allNetworks[i];
                        LinkProperties linkProperties = Vpn.this.mConnectivityManager.getLinkProperties(network);
                        if (linkProperties != null && linkProperties.getAllInterfaceNames().contains(this.mOuterInterface) && (networkInfo = Vpn.this.mConnectivityManager.getNetworkInfo(network)) != null) {
                            this.mOuterConnection.set(networkInfo.getType());
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            Vpn.this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
            Vpn.this.mContext.registerReceiver(this.mTetheringChangedReceiver, intentFilter2);
        }

        public void exitIfOuterInterfaceIs(String str) {
            if (str.equals(this.mOuterInterface)) {
                Log.i("LegacyVpnRunner", "Legacy VPN is going down with " + str);
                exitVpnRunner();
            }
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner
        public void exitVpnRunner() {
            interrupt();
            Vpn.this.agentDisconnect();
            try {
                Vpn.this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                Vpn.this.mContext.unregisterReceiver(this.mTetheringChangedReceiver);
            } catch (IllegalArgumentException unused) {
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(24:3|4|5|(2:6|7)|(3:172|173|(21:177|178|179|11|12|13|(1:15)|(1:17)|18|(4:20|(2:23|21)|24|25)|26|(1:32)|33|(2:35|36)|37|38|39|(3:41|(2:43|(4:47|48|50|51))(1:56)|54)|57|58|59)(1:175))(1:9)|10|11|12|13|(0)|(0)|18|(0)|26|(3:28|30|32)|33|(0)|37|38|39|(0)|57|58|59) */
        /* JADX WARN: Code restructure failed: missing block: B:101:0x028c, code lost:
        
            r7 = r0[r14];
         */
        /* JADX WARN: Code restructure failed: missing block: B:102:0x0294, code lost:
        
            if ("charon".equals(r7) != false) goto L128;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x0296, code lost:
        
            r8 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:108:0x029f, code lost:
        
            java.lang.Thread.sleep(200);
         */
        /* JADX WARN: Code restructure failed: missing block: B:111:0x02a2, code lost:
        
            r8 = r8 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:114:0x02a5, code lost:
        
            r24.this$0.mDeps.stopService(r7);
            r14 = r14 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:121:0x0116, code lost:
        
            r0 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:125:0x0120, code lost:
        
            r11.setupIpRulesForCcMode(r24.mServerIP, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:127:0x0128, code lost:
        
            android.util.Log.d("LegacyVpnRunner", "delete vpn rules");
            r24.this$0.mVpnRules.deleteVpnPostroutingChain();
         */
        /* JADX WARN: Code restructure failed: missing block: B:130:0x013c, code lost:
        
            r11 = r11.iterator();
         */
        /* JADX WARN: Code restructure failed: missing block: B:133:0x0146, code lost:
        
            r12 = (java.lang.String) r11.next();
            android.util.Log.d("LegacyVpnRunner", "deleteTetheringRule" + r12);
            r24.this$0.mVpnRules.deleteTetheringRule(r12);
         */
        /* JADX WARN: Code restructure failed: missing block: B:135:0x016c, code lost:
        
            r24.ipAddresses = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:145:0x01ae, code lost:
        
            libcore.io.IoUtils.closeQuietly(r8[r14]);
         */
        /* JADX WARN: Code restructure failed: missing block: B:146:0x01b3, code lost:
        
            r14 = r14 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:151:0x01bf, code lost:
        
            r8 = r6[r14];
         */
        /* JADX WARN: Code restructure failed: missing block: B:152:0x01c7, code lost:
        
            if ("charon".equals(r8) != false) goto L90;
         */
        /* JADX WARN: Code restructure failed: missing block: B:153:0x01c9, code lost:
        
            r9 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:158:0x01d2, code lost:
        
            java.lang.Thread.sleep(200);
         */
        /* JADX WARN: Code restructure failed: missing block: B:161:0x01d5, code lost:
        
            r9 = r9 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:164:0x01d8, code lost:
        
            r24.this$0.mDeps.stopService(r8);
            r14 = r14 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x01ea, code lost:
        
            r0.setupIpRulesForCcMode(r24.mServerIP, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x01f4, code lost:
        
            android.util.Log.d("LegacyVpnRunner", "delete vpn rules");
            r24.this$0.mVpnRules.deleteVpnPostroutingChain();
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x0208, code lost:
        
            r0 = r0.iterator();
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0212, code lost:
        
            r11 = (java.lang.String) r0.next();
            android.util.Log.d("LegacyVpnRunner", "deleteTetheringRule" + r11);
            r24.this$0.mVpnRules.deleteTetheringRule(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x0239, code lost:
        
            r24.ipAddresses = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x027b, code lost:
        
            libcore.io.IoUtils.closeQuietly(r0[r14]);
         */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x0280, code lost:
        
            r14 = r14 + 1;
         */
        /* JADX WARN: Removed duplicated region for block: B:101:0x028c A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:120:0x01f1  */
        /* JADX WARN: Removed duplicated region for block: B:125:0x0120 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:127:0x0128 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:130:0x013c A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:145:0x01ae A[Catch: all -> 0x02ca, TRY_LEAVE, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:151:0x01bf A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0054 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x005b A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x006f A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00e2 A[Catch: all -> 0x02ca, TRY_LEAVE, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00f3 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x02bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:75:0x01ea A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x01f4 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0208 A[Catch: all -> 0x02ca, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x027b A[Catch: all -> 0x02ca, TRY_LEAVE, TryCatch #7 {, blocks: (B:4:0x000c, B:13:0x004e, B:15:0x0054, B:17:0x005b, B:18:0x006b, B:20:0x006f, B:21:0x0073, B:23:0x0079, B:25:0x00a0, B:26:0x00a2, B:28:0x00ae, B:30:0x00b2, B:32:0x00b5, B:33:0x00b9, B:35:0x00e2, B:38:0x00ea, B:39:0x00ed, B:41:0x00f3, B:45:0x0100, B:48:0x0106, B:54:0x010c, B:58:0x02af, B:59:0x02b9, B:123:0x011a, B:125:0x0120, B:127:0x0128, B:128:0x0138, B:130:0x013c, B:131:0x0140, B:133:0x0146, B:135:0x016c, B:136:0x016e, B:138:0x017a, B:140:0x017e, B:142:0x0181, B:143:0x0185, B:145:0x01ae, B:148:0x01b6, B:149:0x01b9, B:151:0x01bf, B:155:0x01cc, B:158:0x01d2, B:164:0x01d8, B:168:0x01e2, B:73:0x01e4, B:75:0x01ea, B:77:0x01f4, B:78:0x0204, B:80:0x0208, B:81:0x020c, B:83:0x0212, B:85:0x0239, B:86:0x023b, B:88:0x0247, B:90:0x024b, B:92:0x024e, B:93:0x0252, B:95:0x027b, B:98:0x0283, B:99:0x0286, B:101:0x028c, B:105:0x0299, B:108:0x029f, B:114:0x02a5), top: B:3:0x000c }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            boolean z;
            String[] strArr;
            String[] strArr2;
            Vpn vpn;
            List<String> list;
            String str;
            String str2;
            String[][] strArr3;
            String[] strArr4;
            Log.v("LegacyVpnRunner", "Waiting");
            synchronized ("LegacyVpnRunner") {
                Log.v("LegacyVpnRunner", "Executing");
                try {
                } catch (InterruptedException unused) {
                    z = false;
                } catch (Throwable th) {
                    th = th;
                    z = false;
                }
                if (Vpn.this.getKnoxVpnFeature() >= 1) {
                    try {
                    } catch (InterruptedException unused2) {
                        z = false;
                    } catch (Throwable th2) {
                        th = th2;
                        z = false;
                    }
                    if (!Vpn.this.getknoxVpnTypeForStrongswanProfile()) {
                        try {
                            bringup(true);
                            z = true;
                            waitForDaemonsToStop();
                            Thread.interrupted();
                            vpn = Vpn.this;
                            if (!vpn.mLockdown) {
                                vpn.setupIpRulesForCcMode(this.mServerIP, false);
                            }
                            if (!z) {
                                Log.d("LegacyVpnRunner", "delete vpn rules");
                                Vpn.this.mVpnRules.deleteVpnPostroutingChain();
                            }
                            list = this.ipAddresses;
                            if (list != null) {
                                for (String str3 : list) {
                                    Log.d("LegacyVpnRunner", "deleteTetheringRule" + str3);
                                    Vpn.this.mVpnRules.deleteTetheringRule(str3);
                                }
                                this.ipAddresses = null;
                            }
                            stopLegacyDaemons();
                            str = "";
                            str2 = "";
                            strArr3 = this.mArguments;
                            if (strArr3.length > 1 && (strArr4 = strArr3[1]) != null && strArr4.length > 2) {
                                str = strArr4[1];
                                str2 = strArr4[2];
                            }
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "Vpn", String.format("Disconnecting from VPN network to server address %s from type %s succeeded", str2, str), "", UserHandle.getUserId(Binder.getCallingUid()));
                            for (LocalSocket localSocket : this.mSockets) {
                                IoUtils.closeQuietly(localSocket);
                            }
                            Thread.sleep(50L);
                            for (String str4 : this.mDaemons) {
                                if ("charon".equals(str4)) {
                                    for (int i = 0; i < 20 && SystemService.isRunning(str4); i++) {
                                        try {
                                            Thread.sleep(200L);
                                        } catch (Exception unused3) {
                                        }
                                    }
                                }
                                Vpn.this.mDeps.stopService(str4);
                            }
                        } catch (InterruptedException unused4) {
                            z = true;
                            Vpn vpn2 = Vpn.this;
                            if (vpn2.mLockdown) {
                            }
                            if (!z) {
                            }
                            List list2 = this.ipAddresses;
                            if (list2 != null) {
                            }
                            stopLegacyDaemons();
                            String str5 = "";
                            String str6 = "";
                            String[][] strArr5 = this.mArguments;
                            if (strArr5.length > 1) {
                                str5 = strArr2[1];
                                str6 = strArr2[2];
                            }
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "Vpn", String.format("Disconnecting from VPN network to server address %s from type %s succeeded", str6, str5), "", UserHandle.getUserId(Binder.getCallingUid()));
                            LocalSocket[] localSocketArr = this.mSockets;
                            int length = localSocketArr.length;
                            int i2 = 0;
                            while (i2 < length) {
                            }
                            try {
                                Thread.sleep(50L);
                            } catch (InterruptedException unused5) {
                            }
                            String[] strArr6 = this.mDaemons;
                            int length2 = strArr6.length;
                            int i3 = 0;
                            while (i3 < length2) {
                            }
                            Vpn.this.agentDisconnect();
                            Vpn.this.hideNotification();
                            if (z) {
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            z = true;
                            Vpn vpn3 = Vpn.this;
                            if (!vpn3.mLockdown) {
                            }
                            if (!z) {
                            }
                            List list3 = this.ipAddresses;
                            if (list3 != null) {
                            }
                            stopLegacyDaemons();
                            String str7 = "";
                            String str8 = "";
                            String[][] strArr7 = this.mArguments;
                            if (strArr7.length > 1) {
                                str7 = strArr[1];
                                str8 = strArr[2];
                            }
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "Vpn", String.format("Disconnecting from VPN network to server address %s from type %s succeeded", str8, str7), "", UserHandle.getUserId(Binder.getCallingUid()));
                            LocalSocket[] localSocketArr2 = this.mSockets;
                            int length3 = localSocketArr2.length;
                            int i4 = 0;
                            while (i4 < length3) {
                            }
                            try {
                                Thread.sleep(50L);
                            } catch (InterruptedException unused6) {
                            }
                            String[] strArr8 = this.mDaemons;
                            int length4 = strArr8.length;
                            int i5 = 0;
                            while (i5 < length4) {
                            }
                            throw th;
                        }
                        Vpn.this.agentDisconnect();
                        Vpn.this.hideNotification();
                    } else {
                        bringup(false);
                    }
                } else {
                    bringup(false);
                }
                z = false;
                waitForDaemonsToStop();
                Thread.interrupted();
                vpn = Vpn.this;
                if (!vpn.mLockdown) {
                }
                if (!z) {
                }
                list = this.ipAddresses;
                if (list != null) {
                }
                stopLegacyDaemons();
                str = "";
                str2 = "";
                strArr3 = this.mArguments;
                if (strArr3.length > 1) {
                    str = strArr4[1];
                    str2 = strArr4[2];
                }
                AuditLog.logAsUser(5, 5, true, Process.myPid(), "Vpn", String.format("Disconnecting from VPN network to server address %s from type %s succeeded", str2, str), "", UserHandle.getUserId(Binder.getCallingUid()));
                while (r9 < r8) {
                }
                Thread.sleep(50L);
                while (r7 < r6) {
                }
                Vpn.this.agentDisconnect();
                Vpn.this.hideNotification();
            }
            if (z) {
                try {
                    Vpn.getKnoxVpnService().removeVpnUidRanges(Vpn.this.mProfileName);
                } catch (Exception unused7) {
                }
            }
        }

        public final void checkInterruptAndDelay(boolean z) {
            if (SystemClock.elapsedRealtime() - this.mBringupStartTime <= 60000) {
                Thread.sleep(z ? 200L : 1L);
            } else {
                Vpn.this.updateState(NetworkInfo.DetailedState.FAILED, "checkpoint");
                throw new IllegalStateException("VPN bringup took too long");
            }
        }

        public final void checkAndFixupArguments(InetAddress inetAddress) {
            String hostAddress = inetAddress.getHostAddress();
            if ("charon".equals(this.mDaemons[0])) {
                String[] strArr = this.mArguments[0];
                if (strArr != null) {
                    if (!this.mProfile.server.equals(strArr[0])) {
                        throw new IllegalStateException("Invalid server argument for charon");
                    }
                    this.mArguments[0][0] = hostAddress;
                    return;
                }
                return;
            }
            if (!"racoon".equals(this.mDaemons[0]) || !"mtpd".equals(this.mDaemons[1])) {
                throw new IllegalStateException("Unexpected daemons order");
            }
            String[] strArr2 = this.mArguments[0];
            if (strArr2 != null) {
                if (!this.mProfile.server.equals(strArr2[1])) {
                    throw new IllegalStateException("Invalid server argument for racoon");
                }
                this.mArguments[0][1] = hostAddress;
            }
            String[] strArr3 = this.mArguments[1];
            if (strArr3 != null) {
                if (!this.mProfile.server.equals(strArr3[2])) {
                    throw new IllegalStateException("Invalid server argument for mtpd");
                }
                this.mArguments[1][2] = hostAddress;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:66:0x011d, code lost:
        
            checkInterruptAndDelay(true);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void bringup(boolean z) {
            InetAddress resolve;
            List list;
            String[] strArr;
            String str = "";
            String str2 = "";
            String[][] strArr2 = this.mArguments;
            if (strArr2.length > 1 && (strArr = strArr2[1]) != null && strArr.length > 2) {
                str = strArr[1];
                str2 = strArr[2];
            }
            String str3 = str2;
            String str4 = str;
            if (z) {
                resolve = null;
            } else {
                try {
                    resolve = Vpn.this.mDeps.resolve(this.mProfile.server);
                    checkAndFixupArguments(resolve);
                    this.mServerIP = resolve.getHostAddress();
                } catch (Exception e) {
                    Log.i("LegacyVpnRunner", "Aborting", e);
                    AuditLog.logAsUser(5, 5, false, Process.myPid(), "Vpn", String.format("Connecting to VPN network to server address %s from type %s failed", str3, str4), "", UserHandle.getUserId(Binder.getCallingUid()));
                    Vpn vpn = Vpn.this;
                    if (!vpn.mLockdown) {
                        vpn.setupIpRulesForCcMode(this.mServerIP, false);
                    }
                    stopLegacyDaemons();
                    Vpn.this.updateState(NetworkInfo.DetailedState.FAILED, e.getMessage());
                    exitVpnRunner();
                    if (!z) {
                        Vpn.this.broadcastVpnState(NetworkInfo.DetailedState.FAILED, e.getMessage());
                    }
                    if (z) {
                        Log.d("LegacyVpnRunner", "Sending message to clear connecting notification due to some connection error");
                        Vpn.this.sendBroadcastToClearConnectingNotification(2);
                        return;
                    }
                    return;
                }
            }
            this.mBringupStartTime = SystemClock.elapsedRealtime();
            for (String str5 : this.mDaemons) {
                while (!Vpn.this.mDeps.isServiceStopped(str5)) {
                    checkInterruptAndDelay(true);
                }
            }
            File stateFile = Vpn.this.mDeps.getStateFile();
            stateFile.delete();
            if (stateFile.exists()) {
                throw new IllegalStateException("Cannot delete the state");
            }
            File file = new File("/data/misc/vpn/abort");
            file.delete();
            if (file.exists()) {
                throw new IllegalStateException("Cannot delete the abort");
            }
            Vpn.this.updateState(NetworkInfo.DetailedState.CONNECTING, "execute");
            int i = 0;
            while (true) {
                String[] strArr3 = this.mDaemons;
                if (i >= strArr3.length) {
                    break;
                }
                String[] strArr4 = this.mArguments[i];
                if (strArr4 != null) {
                    String str6 = strArr3[i];
                    Vpn.this.mDeps.startService(str6);
                    while (!Vpn.this.mDeps.isServiceRunning(str6)) {
                        checkInterruptAndDelay(true);
                    }
                    this.mSockets[i] = new LocalSocket();
                    Vpn.this.mDeps.sendArgumentsToDaemon(str6, this.mSockets[i], strArr4, new RetryScheduler() { // from class: com.android.server.connectivity.Vpn$LegacyVpnRunner$$ExternalSyntheticLambda0
                        @Override // com.android.server.connectivity.Vpn.RetryScheduler
                        public final void checkInterruptAndDelay(boolean z2) {
                            Vpn.LegacyVpnRunner.this.checkInterruptAndDelay(z2);
                        }
                    });
                }
                i++;
            }
            while (!stateFile.exists()) {
                int i2 = 0;
                while (true) {
                    String[] strArr5 = this.mDaemons;
                    if (i2 < strArr5.length) {
                        String str7 = strArr5[i2];
                        if (this.mArguments[i2] != null && !Vpn.this.mDeps.isServiceRunning(str7)) {
                            throw new IllegalStateException(str7 + " is dead");
                        }
                        if ("charon".equals(str7) && file.exists()) {
                            throw new IllegalStateException(str7 + " is shutdown");
                        }
                        i2++;
                    }
                }
            }
            String[] split = FileUtils.readTextFile(stateFile, 0, null).split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, -1);
            if (split.length != 7) {
                throw new IllegalStateException("Cannot parse the state: '" + String.join("', '", split) + "'");
            }
            Vpn.this.mConfig.interfaze = split[0].trim();
            Vpn.this.mConfig.addLegacyAddresses(split[1]);
            Vpn.this.mLegacyAddress = split[1].split("/")[0];
            List list2 = Vpn.this.mConfig.routes;
            if (list2 == null || list2.isEmpty()) {
                Vpn.this.mConfig.addLegacyRoutes(split[2]);
            }
            if (!z && ((list = Vpn.this.mConfig.routes) == null || list.isEmpty())) {
                Vpn.this.mConfig.addLegacyRoutes(split[2]);
            }
            List list3 = Vpn.this.mConfig.dnsServers;
            if (list3 == null || list3.size() == 0) {
                String trim = split[3].trim();
                if (!trim.isEmpty()) {
                    Vpn.this.mConfig.dnsServers = Arrays.asList(trim.split(" "));
                }
            }
            List list4 = Vpn.this.mConfig.searchDomains;
            if (list4 == null || list4.size() == 0) {
                String trim2 = split[4].trim();
                if (!trim2.isEmpty()) {
                    Vpn.this.mConfig.searchDomains = Arrays.asList(trim2.split(" "));
                }
            }
            if (!z) {
                if (resolve instanceof Inet4Address) {
                    Vpn.this.mConfig.routes.add(new RouteInfo(new IpPrefix(resolve, 32), null, null, 9));
                } else if (resolve instanceof Inet6Address) {
                    Vpn.this.mConfig.routes.add(new RouteInfo(new IpPrefix(resolve, 128), null, null, 9));
                } else {
                    Log.e("LegacyVpnRunner", "Unknown IP address family for VPN endpoint: " + resolve);
                }
                Vpn vpn2 = Vpn.this;
                if (!vpn2.mLockdown) {
                    vpn2.setupIpRulesForCcMode(this.mServerIP, true);
                }
            }
            synchronized (Vpn.this) {
                Vpn.this.mConfig.startTime = SystemClock.elapsedRealtime();
                checkInterruptAndDelay(false);
                Vpn vpn3 = Vpn.this;
                if (!vpn3.mDeps.isInterfacePresent(vpn3, vpn3.mConfig.interfaze)) {
                    throw new IllegalStateException(Vpn.this.mConfig.interfaze + " is gone");
                }
                Vpn vpn4 = Vpn.this;
                vpn4.mInterface = vpn4.mConfig.interfaze;
                vpn4.prepareStatusIntent();
                if (z) {
                    Vpn.this.agentConnectForKnoxInterface();
                } else {
                    Vpn.this.agentConnect();
                }
                Log.i("LegacyVpnRunner", "Connected!");
                if (!z) {
                    Vpn.this.showNotification();
                    Log.d("LegacyVpnRunner", "add vpn rules");
                    Vpn.this.mVpnRules.createVpnPostroutingChain();
                    Vpn.this.mVpnRules.addTcpmssClampRule();
                    Vpn.this.broadcastVpnState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
                }
                if (isWifiApMode(Vpn.this.mContext)) {
                    if (this.ipAddresses == null) {
                        this.ipAddresses = new ArrayList();
                    }
                    for (LinkAddress linkAddress : Vpn.this.mConfig.addresses) {
                        Log.d("LegacyVpnRunner", "addVpnRuleForTethering :" + linkAddress.toString());
                        Vpn.this.mVpnRules.addVpnRuleForTethering(linkAddress.toString());
                        this.ipAddresses.add(linkAddress.toString());
                    }
                }
            }
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "Vpn", String.format("Connecting to VPN network to server address %s from type %s succeeded", str3, str4), "", UserHandle.getUserId(Binder.getCallingUid()));
        }

        public final void waitForDaemonsToStop() {
            if (!Vpn.this.mNetworkInfo.isConnected()) {
                return;
            }
            while (true) {
                Thread.sleep(2000L);
                int i = 0;
                if (this.mDaemons[0].compareTo("racoon") == 0) {
                    if (!isEgressActivated()) {
                        return;
                    }
                } else if (this.mDaemons[0].compareTo("charon") == 0 && (!isCharonActivated() || !isEgressActivated())) {
                    return;
                }
                while (true) {
                    String[] strArr = this.mDaemons;
                    if (i < strArr.length) {
                        if (this.mArguments[i] != null && Vpn.this.mDeps.isServiceStopped(strArr[i])) {
                            return;
                        } else {
                            i++;
                        }
                    }
                }
            }
        }

        public final boolean isWifiApMode(Context context) {
            try {
                return ((WifiManager) context.getSystemService("wifi")).getWifiApState() == 13;
            } catch (Exception e) {
                Log.d("LegacyVpnRunner", "Failed to get hotspot status, assume hotpost disabled");
                e.printStackTrace();
                return false;
            }
        }

        public final boolean isCharonActivated() {
            try {
                NetworkInterface.getByName("ipsec0").getInetAddresses();
                return true;
            } catch (Exception unused) {
                Log.d("LegacyVpnRunner", "charon Iface doesn't exist");
                return false;
            }
        }

        public final void stopLegacyDaemons() {
            Log.v("LegacyVpnRunner", "stopLegacyDaemons: begin");
            int i = 0;
            while (true) {
                String[] strArr = this.mDaemons;
                if (i >= strArr.length) {
                    return;
                }
                String str = strArr[i];
                if (str.compareTo("charon") == 0) {
                    IoUtils.closeQuietly(this.mSockets[i]);
                    for (int i2 = 0; i2 < 20 && SystemService.isRunning(str); i2++) {
                        try {
                            Thread.sleep(200L);
                        } catch (Exception unused) {
                        }
                    }
                } else {
                    IoUtils.closeQuietly(this.mSockets[i]);
                }
                SystemService.stop(str);
                i++;
            }
        }

        public final boolean isEgressActivated() {
            try {
                Iterator it = Collections.list(NetworkInterface.getByName(Vpn.this.mEgressIface).getInetAddresses()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    z = true;
                }
                if (z) {
                    return true;
                }
                Log.d("LegacyVpnRunner", "Egress Iface (" + Vpn.this.mEgressIface + ") deactivated");
                return false;
            } catch (Exception unused) {
                Log.d("LegacyVpnRunner", "Egress Iface (" + Vpn.this.mEgressIface + ") doesn't exist");
                return false;
            }
        }
    }

    public final void setupIpRulesForCcMode(String str, boolean z) {
        if (MdfUtils.isMdfEnforced()) {
            Log.i("Vpn", "setupIpRulesForCcMode: " + str + ", " + this.mLegacyAddress + ", " + z);
            try {
                this.mVpnRules.setFirewallEnabled(z);
                this.mVpnRules.setFirewallEgressDestRule(str, 500, z);
                this.mVpnRules.setFirewallEgressDestRule(str, 4500, z);
                this.mVpnRules.setFirewallEgressDestRule(str, 1701, z);
                this.mVpnRules.setFirewallInterfaceRule("ipsec0", z);
                this.mVpnRules.setFirewallEgressSourceRule(this.mLegacyAddress, z);
                Log.i("Vpn", "setupIpRulesForCcMode: v4=" + str);
                String clatIpv4to6 = clatIpv4to6(str);
                this.mVpnRules.setFirewallEgressDestRule(clatIpv4to6, 500, z);
                this.mVpnRules.setFirewallEgressDestRule(clatIpv4to6, 4500, z);
                this.mVpnRules.setFirewallEgressDestRule(clatIpv4to6, 1701, z);
                this.mVpnRules.setFirewallEgressSourceRule("fe80::/64", z);
                this.mVpnRules.setFirewallEgressSourceRule("2000::/4", z);
                Log.i("Vpn", "setupIpRulesForCcMode: v6=" + clatIpv4to6);
            } catch (Exception e) {
                Log.e("Vpn", "Failed to set firewall rule: " + e);
            }
        }
    }

    public String clatIpv4to6(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer("64:ff9b::");
        String[] split = str.split("\\.");
        for (int i = 0; i < 4; i++) {
            stringBuffer.append(Integer.toHexString(Integer.parseInt(split[i])));
            if (i == 1) {
                stringBuffer.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            }
        }
        return stringBuffer.toString();
    }

    public final void verifyCallingUidAndPackage(String str) {
        this.mDeps.verifyCallingUidAndPackage(this.mContext, str, this.mUserId);
    }

    public String getProfileNameForPackage(String str) {
        return "PLATFORM_VPN_" + this.mUserId + "_" + str;
    }

    public void validateRequiredFeatures(VpnProfile vpnProfile) {
        int i = vpnProfile.type;
        if ((i == 6 || i == 7 || i == 8 || i == 10) && !this.mContext.getPackageManager().hasSystemFeature("android.software.ipsec_tunnels")) {
            throw new UnsupportedOperationException("Ikev2VpnProfile(s) requires PackageManager.FEATURE_IPSEC_TUNNELS");
        }
    }

    public synchronized boolean provisionVpnProfile(String str, VpnProfile vpnProfile) {
        Objects.requireNonNull(str, "No package name provided");
        Objects.requireNonNull(vpnProfile, "No profile provided");
        verifyCallingUidAndPackage(str);
        enforceNotRestrictedUser();
        validateRequiredFeatures(vpnProfile);
        if (vpnProfile.isRestrictedToTestNetworks) {
            this.mContext.enforceCallingPermission("android.permission.MANAGE_TEST_NETWORKS", "Test-mode profiles require the MANAGE_TEST_NETWORKS permission");
        }
        byte[] encode = vpnProfile.encode();
        if (encode.length > 131072) {
            throw new IllegalArgumentException("Profile too big");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getVpnProfileStore().put(getProfileNameForPackage(str), encode);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        return isVpnProfilePreConsented(this.mContext, str);
    }

    public final boolean isCurrentIkev2VpnLocked(String str) {
        return isCurrentPreparedPackage(str) && isIkev2VpnRunner();
    }

    public synchronized void deleteVpnProfile(String str) {
        Objects.requireNonNull(str, "No package name provided");
        verifyCallingUidAndPackage(str);
        enforceNotRestrictedUser();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isCurrentIkev2VpnLocked(str)) {
                if (this.mAlwaysOn) {
                    setAlwaysOnPackage(null, false, null);
                } else {
                    prepareInternal("[Legacy VPN]");
                }
            }
            getVpnProfileStore().remove(getProfileNameForPackage(str));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public VpnProfile getVpnProfilePrivileged(String str) {
        if (!this.mDeps.isCallerSystem()) {
            Log.wtf("Vpn", "getVpnProfilePrivileged called as non-System UID ");
            return null;
        }
        byte[] bArr = getVpnProfileStore().get(getProfileNameForPackage(str));
        if (bArr == null) {
            return null;
        }
        return VpnProfile.decode("", bArr);
    }

    public final boolean isIkev2VpnRunner() {
        return this.mVpnRunner instanceof IkeV2VpnRunner;
    }

    public final String getSessionKeyLocked() {
        boolean isIkev2VpnRunner = isIkev2VpnRunner();
        String str = isIkev2VpnRunner ? ((IkeV2VpnRunner) this.mVpnRunner).mSessionKey : null;
        Log.d("Vpn", "getSessionKeyLocked: isIkev2VpnRunner = " + isIkev2VpnRunner + ", sessionKey = " + str);
        return str;
    }

    public synchronized String startVpnProfile(String str) {
        Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        if (!prepare(str, null, 2)) {
            throw new SecurityException("User consent not granted for package " + str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(str);
            if (vpnProfilePrivileged == null) {
                throw new IllegalArgumentException("No profile found for " + str);
            }
            startVpnProfilePrivileged(vpnProfilePrivileged, str);
            if (!isIkev2VpnRunner()) {
                throw new IllegalStateException("mVpnRunner shouldn't be null and should also be an instance of Ikev2VpnRunner");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return getSessionKeyLocked();
    }

    public final boolean getknoxVpnTypeForStrongswanProfile() {
        boolean z = true;
        if (getKnoxVpnFeature() >= 1) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                if (getKnoxVpnService() != null && this.mProfileName != null) {
                    boolean z2 = DBG;
                    if (z2) {
                        Log.d("Vpn", "getknoxVpnTypeForStrongswanProfile: profile name is " + this.mProfileName);
                    }
                    String vendorNameForProfile = getKnoxVpnService().getVendorNameForProfile(this.mProfileName);
                    if (z2) {
                        Log.d("Vpn", "getknoxVpnTypeForStrongswanProfile vendorOwningProfile value is " + vendorNameForProfile);
                    }
                    if (vendorNameForProfile != null) {
                        int packageUidAsUser = packageManager.getPackageUidAsUser("com.android.vpndialogs", 0);
                        if (vendorNameForProfile.equalsIgnoreCase("com.samsung.sVpn") || packageUidAsUser == UserHandle.getAppId(Binder.getCallingUid())) {
                            if (z2) {
                                Log.d("Vpn", "Caller is either the strongswan proxy or the vpn dialog app");
                            }
                            int knoxVpnProfileType = getKnoxVpnService().getKnoxVpnProfileType(this.mProfileName);
                            if (knoxVpnProfileType != 0 && knoxVpnProfileType == 1) {
                                z = false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.e("Vpn", "Exception at checkIfStrongswanProfileIsKnoxBased " + Log.getStackTraceString(e));
                }
            }
        }
        if (DBG) {
            Log.d("Vpn", "getknoxVpnTypeForStrongswanProfile profileType value is " + z);
        }
        return z;
    }

    public final int getKnoxVpnFeature() {
        this.KNOXVPN_FEATURE = 2;
        return 2;
    }

    public static IKnoxVpnPolicy getKnoxVpnService() {
        return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
    }

    public final void agentConnectForKnoxInterface() {
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName(this.mInterface);
        List<LinkAddress> list = this.mConfig.addresses;
        if (list != null) {
            for (LinkAddress linkAddress : list) {
                if (linkAddress.getAddress() instanceof Inet4Address) {
                    this.mAddress = linkAddress.getAddress().getHostAddress();
                    Log.d("Vpn", "The ipv4 address added to the knox vpn interface is " + this.mAddress);
                }
                if (linkAddress.getAddress() instanceof Inet6Address) {
                    this.mV6Address = linkAddress.getAddress().getHostAddress();
                    Log.d("Vpn", "The ipv6 address added to the knox vpn interface is " + this.mV6Address);
                }
                linkProperties.addLinkAddress(linkAddress);
            }
        }
        List list2 = this.mConfig.dnsServers;
        if (list2 != null) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                linkProperties.addDnsServer(InetAddresses.parseNumericAddress((String) it.next()));
            }
        }
        ProxyInfo proxyInfo = this.mHttpProxyInfo;
        if (proxyInfo != null) {
            linkProperties.setHttpProxy(proxyInfo);
        }
        StringBuilder sb = new StringBuilder();
        List list3 = this.mConfig.searchDomains;
        if (list3 != null) {
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append(' ');
            }
        }
        linkProperties.setDomains(sb.toString().trim());
        int i = this.mConfig.mtu;
        if (i > 0) {
            linkProperties.setMtu(i);
        }
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder(this.mNetworkCapabilities);
        builder.addCapability(12);
        this.mLegacyState = 2;
        updateState(NetworkInfo.DetailedState.CONNECTING, "agentConnect");
        NetworkAgentConfig build = new NetworkAgentConfig.Builder().setBypassableVpn(true).build();
        builder.setOwnerUid(Binder.getCallingUid());
        builder.setAdministratorUids(new int[]{Binder.getCallingUid()});
        builder.setTransportInfo(new VpnTransportInfo(4, (String) null));
        this.mNetworkCapabilities = builder.build();
        this.mNetworkAgent = new NetworkAgent(this.mContext, this.mLooper, "VPN", this.mNetworkCapabilities, linkProperties, new NetworkScore.Builder().setLegacyInt(101).build(), build, this.mNetworkProvider) { // from class: com.android.server.connectivity.Vpn.3
            public void onNetworkUnwanted() {
            }

            public void onNetworkCreated() {
                Log.d("Vpn", "knox vpn network is created");
                Vpn.this.onKnoxVpnConnected();
            }

            public void onNetworkDestroyed() {
                Log.d("Vpn", "knox vpn network is destroyed");
            }
        };
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                Vpn.this.lambda$agentConnectForKnoxInterface$2();
            }
        });
        NetworkAgent networkAgent = this.mNetworkAgent;
        Network[] networkArr = this.mConfig.underlyingNetworks;
        networkAgent.setUnderlyingNetworks(networkArr != null ? Arrays.asList(networkArr) : null);
        updateState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
        sendStrongSwanInterfaceToKnoxVpn(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$agentConnectForKnoxInterface$2() {
        try {
            this.mNetworkAgent.register();
        } catch (Exception e) {
            this.mNetworkAgent = null;
            throw e;
        }
    }

    public void updateLocalProxyInfo(ProxyInfo proxyInfo) {
        this.mHttpProxyInfo = proxyInfo;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent == null || this.mInterface == null) {
            return;
        }
        networkAgent.sendLinkProperties(makeLinkProperties());
    }

    public final void sendStrongSwanInterfaceToKnoxVpn(int i) {
        if (getKnoxVpnFeature() >= 1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
                    intent.setPackage("com.samsung.sVpn");
                    intent.putExtra("com.samsung.android.knox.intent.extra.ACTION_INTERNAL", "tun_info");
                    String str = this.mProfileName;
                    if (str != null) {
                        intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", str);
                    }
                    String str2 = this.mInterface;
                    if (str2 != null) {
                        intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ID_INTERNAL", str2);
                    }
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", i);
                    intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ADDRESS_INTERNAL", this.mAddress);
                    intent.putExtra("com.samsung.android.knox.intent.extra.TUNV6_ADDRESS_INTERNAL", this.mV6Address);
                    intent.putExtra("com.samsung.android.knox.intent.extra.VPN_CLIENT_TYPE_INTERNAL", 1);
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(0), "com.samsung.android.knox.permission.KNOX_VPN_SOLUTION");
                } catch (Exception unused) {
                    Log.e("Vpn", "unable to send interface details");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void sendBroadcastToClearConnectingNotification(int i) {
        if (getKnoxVpnFeature() >= 1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (this.mProfileName != null) {
                        Intent intent = new Intent();
                        intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
                        intent.setPackage("com.samsung.sVpn");
                        intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", this.mProfileName);
                        intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ID_INTERNAL", "ipsec0");
                        intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", i);
                        Log.d("Vpn", "send message to clear connecting notificiation due to some error while connecting to server");
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(0), "com.samsung.android.knox.permission.KNOX_VPN_SOLUTION");
                    }
                } catch (Exception unused) {
                    Log.e("Vpn", "unable to send message to clear connecting notification");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void showNotificationForKnoxStrongSwan(String str, Bitmap bitmap, int i) {
        dismissConnectingNotification();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getKnoxVpnFeature() >= 1) {
                    this.mStatusIntent = VpnConfig.getIntentForStatusPanelAsUser(this.mContext, 0);
                    NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                    if (notificationManager != null) {
                        Notification createNotification = createNotification(str, bitmap, this.mStatusIntent);
                        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
                            notificationManager.notifyAsUser("Knox_SS_Notification", 100000, createNotification, new UserHandle(i));
                        } else {
                            notificationManager.notifyAsUser(null, 17304614, createNotification, new UserHandle(i));
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Notification createNotification(String str, Bitmap bitmap, PendingIntent pendingIntent) {
        String string;
        if (str == null) {
            string = this.mContext.getString(17043250);
        } else {
            string = this.mContext.getString(17043251, str);
        }
        String str2 = this.mConfig.session;
        Notification build = new Notification.Builder(this.mContext, SystemNotificationChannels.VPN).setSmallIcon(17304614).setLargeIcon(bitmap).setContentTitle(string).setContentText(str2 == null ? this.mContext.getString(17043248) : this.mContext.getString(17043249, str2)).setContentIntent(pendingIntent).setDefaults(0).setOngoing(true).build();
        build.flags |= 98;
        return build;
    }

    public final void hideNotification(int i) {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
                        notificationManager.cancelAsUser("Knox_SS_Notification", 100000, new UserHandle(i));
                    } else {
                        notificationManager.cancelAsUser(null, 17304614, new UserHandle(i));
                    }
                } catch (Exception e) {
                    Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void onKnoxVpnConnected() {
        try {
            getKnoxVpnService().addVpnUidRanges(this.mProfileName, getNetId(), this.mInterface, this.mAddress, this.mV6Address);
            updateUidListInNetworkCapabilities();
            showNotificationForKnoxStrongSwan(null, null, 0);
        } catch (Exception e) {
            Log.e("Vpn", "Error occured " + Log.getStackTraceString(e));
        }
    }

    public final synchronized int getNetId() {
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent == null) {
            return 0;
        }
        Network network = networkAgent.getNetwork();
        if (network == null) {
            return 0;
        }
        return network.getNetId();
    }

    public void updateUidRangesToPerAppVpn(boolean z, Set set) {
        Log.d("Vpn", "updateUidRangesToPerAppVpn " + z);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (z) {
                this.knoxVpnUidRanges.add(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue)));
            } else {
                this.knoxVpnUidRanges.remove(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue)));
            }
        }
        updateUidListInNetworkCapabilities();
    }

    public void updateUidRangesToUserVpn(boolean z, int i) {
        Log.d("Vpn", "updateUidRangesToUserVpn " + z + " " + i);
        int i2 = (i * 100000) + 1;
        int i3 = i2 + (-1) + 99999;
        if (z) {
            this.knoxVpnUidRanges.add(new Range(Integer.valueOf(i2), Integer.valueOf(i3)));
        } else {
            this.knoxVpnUidRanges.remove(new Range(Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        updateUidListInNetworkCapabilities();
    }

    public void updateUidRangesToUserVpnWithBlackList(int i, Set set) {
        Log.d("Vpn", "updateUidRangesToUserVpnWithBlackList " + i);
        if (set.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList);
        Range createUidRangeForUser = createUidRangeForUser(i);
        int intValue = ((Integer) createUidRangeForUser.getLower()).intValue() + 1;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue2 = ((Integer) it.next()).intValue();
            if (intValue2 == intValue) {
                intValue++;
            } else {
                this.knoxVpnUidRanges.add(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue2 - 1)));
                intValue = intValue2 + 1;
            }
        }
        if (intValue <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
            this.knoxVpnUidRanges.add(new Range(Integer.valueOf(intValue), (Integer) createUidRangeForUser.getUpper()));
        }
        updateUidListInNetworkCapabilities();
    }

    public final void updateUidListInNetworkCapabilities() {
        for (Range range : this.knoxVpnUidRanges) {
            Log.d("Vpn", "uidRange going to be updated is " + range.getLower() + " " + range.getUpper() + " for profile " + this.mProfileName);
        }
        NetworkCapabilities build = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(this.knoxVpnUidRanges).build();
        this.mNetworkCapabilities = build;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent != null) {
            networkAgent.sendNetworkCapabilities(build);
        }
    }

    public void resetUidListInNetworkCapabilities() {
        applyBlockingRulesToUidRange(false);
        this.knoxVpnUidRanges.clear();
        NetworkCapabilities build = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).build();
        this.mNetworkCapabilities = build;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent != null) {
            networkAgent.sendNetworkCapabilities(build);
        }
    }

    public void applyBlockingRulesToUidRange(boolean z) {
        try {
            this.mConnectivityManager.setRequireVpnForUids(z, this.knoxVpnUidRanges);
        } catch (RuntimeException e) {
            Log.e("Vpn", "Updating blocked=" + z + " for UIDs " + Arrays.toString(this.knoxVpnUidRanges.toArray()) + " failed", e);
        }
    }

    public void cleanupObjects() {
        resetUidListInNetworkCapabilities();
    }

    public final synchronized void startVpnProfilePrivileged(VpnProfile vpnProfile, String str) {
        prepareInternal(str);
        updateState(NetworkInfo.DetailedState.CONNECTING, "startPlatformVpn");
        try {
            this.mConfig = new VpnConfig();
            if ("[Legacy VPN]".equals(str)) {
                VpnConfig vpnConfig = this.mConfig;
                vpnConfig.legacy = true;
                vpnConfig.session = vpnProfile.name;
                vpnConfig.user = vpnProfile.key;
                vpnConfig.isMetered = true;
            } else {
                VpnConfig vpnConfig2 = this.mConfig;
                vpnConfig2.user = str;
                vpnConfig2.isMetered = vpnProfile.isMetered;
            }
            this.mConfig.startTime = SystemClock.elapsedRealtime();
            VpnConfig vpnConfig3 = this.mConfig;
            vpnConfig3.proxyInfo = vpnProfile.proxy;
            vpnConfig3.requiresInternetValidation = vpnProfile.requiresInternetValidation;
            vpnConfig3.excludeLocalRoutes = vpnProfile.excludeLocalRoutes;
            vpnConfig3.allowBypass = vpnProfile.isBypassable;
            vpnConfig3.disallowedApplications = getAppExclusionList(this.mPackage);
            int i = vpnProfile.type;
            if (i == 6 || i == 7 || i == 8 || i == 10) {
                IkeV2VpnRunner ikeV2VpnRunner = new IkeV2VpnRunner(Ikev2VpnProfile.fromVpnProfile(vpnProfile), this.mDeps.newScheduledThreadPoolExecutor());
                this.mVpnRunner = ikeV2VpnRunner;
                ikeV2VpnRunner.start();
            } else {
                updateState(NetworkInfo.DetailedState.FAILED, "Invalid platform VPN type");
                Log.d("Vpn", "Unknown VPN profile type: " + vpnProfile.type);
            }
            if (!"[Legacy VPN]".equals(str)) {
                this.mAppOpsManager.startOp("android:establish_vpn_manager", this.mOwnerUID, this.mPackage, null, null);
            }
        } catch (GeneralSecurityException e) {
            this.mConfig = null;
            updateState(NetworkInfo.DetailedState.FAILED, "VPN startup failed");
            throw new IllegalArgumentException("VPN startup failed", e);
        }
    }

    public final void stopVpnRunnerAndNotifyAppLocked() {
        int i = this.mOwnerUID;
        Intent buildVpnManagerEventIntent = (SdkLevel.isAtLeastT() && isVpnApp(this.mPackage)) ? buildVpnManagerEventIntent("android.net.category.EVENT_DEACTIVATED_BY_USER", -1, -1, this.mPackage, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null) : null;
        this.mVpnRunner.exit();
        if (buildVpnManagerEventIntent == null || !isVpnApp(this.mPackage)) {
            return;
        }
        notifyVpnManagerVpnStopped(this.mPackage, i, buildVpnManagerEventIntent);
    }

    public synchronized void stopVpnProfile(String str) {
        Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        if (isCurrentIkev2VpnLocked(str)) {
            stopVpnRunnerAndNotifyAppLocked();
        }
    }

    public final synchronized void notifyVpnManagerVpnStopped(String str, int i, Intent intent) {
        this.mAppOpsManager.finishOp("android:establish_vpn_manager", i, str, null);
        if (SdkLevel.isAtLeastT()) {
            this.mEventChanges.log("[VMEvent] " + str + " stopped");
            sendEventToVpnManagerApp(intent, str);
        }
    }

    public final boolean storeAppExclusionList(String str, List list) {
        try {
            byte[] diskStableBytes = PersistableBundleUtils.toDiskStableBytes(PersistableBundleUtils.fromList(list, PersistableBundleUtils.STRING_SERIALIZER));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                getVpnProfileStore().put(getVpnAppExcludedForPackage(str), diskStableBytes);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (IOException e) {
            Log.e("Vpn", "problem writing into stream", e);
            return false;
        }
    }

    public String getVpnAppExcludedForPackage(String str) {
        return VPN_APP_EXCLUDED + this.mUserId + "_" + str;
    }

    public synchronized boolean setAppExclusionList(String str, List list) {
        enforceNotRestrictedUser();
        if (!storeAppExclusionList(str, list)) {
            return false;
        }
        updateAppExclusionList(list);
        return true;
    }

    public synchronized void refreshPlatformVpnAppExclusionList() {
        updateAppExclusionList(getAppExclusionList(this.mPackage));
    }

    public final synchronized void updateAppExclusionList(List list) {
        if (this.mNetworkAgent != null && isIkev2VpnRunner()) {
            this.mConfig.disallowedApplications = List.copyOf(list);
            this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(createUserAndRestrictedProfilesRanges(this.mUserId, null, list)).build();
            String sessionKeyLocked = getSessionKeyLocked();
            int i = this.mUserId;
            VpnConfig vpnConfig = this.mConfig;
            setVpnNetworkPreference(sessionKeyLocked, createUserAndRestrictedProfilesRanges(i, vpnConfig.allowedApplications, vpnConfig.disallowedApplications));
            doSendNetworkCapabilities(this.mNetworkAgent, this.mNetworkCapabilities);
        }
    }

    public synchronized List getAppExclusionList(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                byte[] bArr = getVpnProfileStore().get(getVpnAppExcludedForPackage(str));
                if (bArr != null && bArr.length != 0) {
                    return PersistableBundleUtils.toList(PersistableBundleUtils.fromDiskStableBytes(bArr), PersistableBundleUtils.STRING_DESERIALIZER);
                }
                return new ArrayList();
            } catch (IOException e) {
                Log.e("Vpn", "problem reading from stream", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return new ArrayList();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getStateFromLegacyState(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 5) {
            return 3;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        Log.wtf("Vpn", "Unhandled state " + i + ", treat it as STATE_DISCONNECTED");
        return 0;
    }

    public final VpnProfileState makeVpnProfileStateLocked() {
        return new VpnProfileState(getStateFromLegacyState(this.mLegacyState), isIkev2VpnRunner() ? getSessionKeyLocked() : null, this.mAlwaysOn, this.mLockdown);
    }

    public final VpnProfileState makeDisconnectedVpnProfileState() {
        return new VpnProfileState(0, null, false, false);
    }

    public synchronized VpnProfileState getProvisionedVpnProfileState(String str) {
        Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        return isCurrentIkev2VpnLocked(str) ? makeVpnProfileStateLocked() : null;
    }

    public static void doSendLinkProperties(NetworkAgent networkAgent, LinkProperties linkProperties) {
        if (networkAgent instanceof VpnNetworkAgentWrapper) {
            ((VpnNetworkAgentWrapper) networkAgent).doSendLinkProperties(linkProperties);
        } else {
            networkAgent.sendLinkProperties(linkProperties);
        }
    }

    public static void doSendNetworkCapabilities(NetworkAgent networkAgent, NetworkCapabilities networkCapabilities) {
        if (networkAgent instanceof VpnNetworkAgentWrapper) {
            ((VpnNetworkAgentWrapper) networkAgent).doSendNetworkCapabilities(networkCapabilities);
        } else {
            networkAgent.sendNetworkCapabilities(networkCapabilities);
        }
    }

    public final void doSetUnderlyingNetworks(NetworkAgent networkAgent, List list) {
        logUnderlyNetworkChanges(list);
        if (networkAgent instanceof VpnNetworkAgentWrapper) {
            ((VpnNetworkAgentWrapper) networkAgent).doSetUnderlyingNetworks(list);
        } else {
            networkAgent.setUnderlyingNetworks(list);
        }
    }

    public class VpnNetworkAgentWrapper extends NetworkAgent {
        public final ValidationStatusCallback mCallback;

        public void onNetworkUnwanted() {
        }

        public VpnNetworkAgentWrapper(Context context, Looper looper, String str, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, NetworkScore networkScore, NetworkAgentConfig networkAgentConfig, NetworkProvider networkProvider, ValidationStatusCallback validationStatusCallback) {
            super(context, looper, str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider);
            this.mCallback = validationStatusCallback;
        }

        public void doSendLinkProperties(LinkProperties linkProperties) {
            sendLinkProperties(linkProperties);
        }

        public void doSendNetworkCapabilities(NetworkCapabilities networkCapabilities) {
            sendNetworkCapabilities(networkCapabilities);
        }

        public void doSetUnderlyingNetworks(List list) {
            setUnderlyingNetworks(list);
        }

        public void onValidationStatus(int i, Uri uri) {
            ValidationStatusCallback validationStatusCallback = this.mCallback;
            if (validationStatusCallback != null) {
                validationStatusCallback.onValidationStatus(i);
            }
        }
    }

    public class IkeSessionWrapper {
        public final IkeSession mImpl;

        public IkeSessionWrapper(IkeSession ikeSession) {
            this.mImpl = ikeSession;
        }

        public void setNetwork(Network network, int i, int i2, int i3) {
            this.mImpl.setNetwork(network, i, i2, i3);
        }

        public void setUnderpinnedNetwork(Network network) {
            this.mImpl.setUnderpinnedNetwork(network);
        }

        public void kill() {
            this.mImpl.kill();
        }
    }

    public class Ikev2SessionCreator {
        public IkeSessionWrapper createIkeSession(Context context, IkeSessionParams ikeSessionParams, ChildSessionParams childSessionParams, Executor executor, IkeSessionCallback ikeSessionCallback, ChildSessionCallback childSessionCallback) {
            return new IkeSessionWrapper(new IkeSession(context, ikeSessionParams, childSessionParams, executor, ikeSessionCallback, childSessionCallback));
        }
    }

    public static Range createUidRangeForUser(int i) {
        return new Range(Integer.valueOf(i * 100000), Integer.valueOf(((i + 1) * 100000) - 1));
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this) {
            indentingPrintWriter.println("Active package name: " + this.mPackage);
            indentingPrintWriter.println("Active vpn type: " + getActiveVpnType());
            indentingPrintWriter.println("NetworkCapabilities: " + this.mNetworkCapabilities);
            if (isIkev2VpnRunner()) {
                IkeV2VpnRunner ikeV2VpnRunner = (IkeV2VpnRunner) this.mVpnRunner;
                indentingPrintWriter.println("SessionKey: " + ikeV2VpnRunner.mSessionKey);
                StringBuilder sb = new StringBuilder();
                sb.append("MOBIKE ");
                sb.append(ikeV2VpnRunner.mMobikeEnabled ? "enabled" : "disabled");
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.println("Profile: " + ikeV2VpnRunner.mProfile);
                indentingPrintWriter.println("Token: " + ikeV2VpnRunner.mCurrentToken);
                if (this.mDataStallSuspected) {
                    indentingPrintWriter.println("Data stall suspected");
                }
                if (ikeV2VpnRunner.mScheduledHandleDataStallFuture != null) {
                    indentingPrintWriter.println("Reset session scheduled");
                }
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("mCachedCarrierConfigInfoPerSubId=" + this.mCachedCarrierConfigInfoPerSubId);
            indentingPrintWriter.println("mEventChanges (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mEventChanges.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static int getCellSubIdForNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null || !networkCapabilities.hasTransport(0)) {
            return -1;
        }
        NetworkSpecifier networkSpecifier = networkCapabilities.getNetworkSpecifier();
        if (networkSpecifier instanceof TelephonyNetworkSpecifier) {
            return ((TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId();
        }
        return -1;
    }

    public final void showNotification() {
        if (this.mLockdown) {
            return;
        }
        String str = this.mConfig.session;
        NotificationManager.from(this.mContext).notify(null, 20, new Notification.Builder(this.mContext, SystemNotificationChannels.VPN).setWhen(0L).setSmallIcon(17304614).setContentTitle(this.mContext.getString(17043250)).setContentText(str == null ? this.mContext.getString(17043248) : this.mContext.getString(17043249, str)).setContentIntent(this.mStatusIntent).setOngoing(true).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).build());
    }

    public final void hideNotification() {
        NotificationManager.from(this.mContext).cancel(null, 20);
    }

    public final boolean isUcmEnabled() {
        return SystemProperties.getBoolean("security.ucmcrypto", false) || SystemProperties.getBoolean("persist.security.ucmcrypto", false);
    }

    public final String formatPemCert(String str) {
        int length = str.length();
        String str2 = "";
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 64) {
            int i3 = length - i;
            if (i3 >= 64) {
                i3 = 64;
            }
            i += i3;
            str2 = str2 + str.substring(i2, i3 + i2) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        }
        return "-----BEGIN CERTIFICATE-----\n" + str2 + "-----END CERTIFICATE-----";
    }

    public final String convertToPemCert(X509Certificate x509Certificate) {
        try {
            return formatPemCert(Base64.encodeToString(x509Certificate.getEncoded(), 2));
        } catch (Exception e) {
            Log.e("Vpn", "Failed to convertToPemCert: " + e);
            return null;
        }
    }

    public final String getUcmCertificate(String str, String str2) {
        X509Certificate uCMCertificateForAlias;
        if (str2 != null && (uCMCertificateForAlias = getUCMCertificateForAlias(str2)) != null) {
            try {
                if (uCMCertificateForAlias.getBasicConstraints() > 0) {
                    if (str.equals("CACERT_")) {
                        return convertToPemCert(uCMCertificateForAlias);
                    }
                } else if (str.equals("USRCERT_")) {
                    return convertToPemCert(uCMCertificateForAlias);
                }
            } catch (Exception e) {
                Log.e("Vpn", "Failed to convertToPemCert, " + e);
            }
        }
        Log.e("Vpn", "Failed to getCcmCertificate");
        return null;
    }

    public final X509Certificate getUCMCertificateForAlias(String str) {
        ucmRetParcelable certificateChain;
        if (str != null && str.length() > 0) {
            try {
                IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
                if (asInterface == null || (certificateChain = asInterface.getCertificateChain(str)) == null) {
                    return null;
                }
                return toCertificate(certificateChain.mData);
            } catch (Exception e) {
                Log.w("Vpn", "Failed to getUCMCertificate", e);
            }
        }
        return null;
    }

    public final X509Certificate toCertificate(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w("Vpn", "Failed to toCertificate", e);
            return null;
        }
    }

    public void setNotificationDismissibleFlagForKnoxStrongSwan(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getKnoxVpnFeature() >= 1 && !getknoxVpnTypeForStrongswanProfile()) {
                    this.mStatusIntent = VpnConfig.getIntentForStatusPanelAsUser(this.mContext, 0);
                    NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                    if (notificationManager != null) {
                        Notification createNotification = createNotification(null, null, this.mStatusIntent);
                        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
                            notificationManager.cancelAsUser(null, 17304614, new UserHandle(i));
                            notificationManager.notifyAsUser("Knox_SS_Notification", 100000, createNotification, new UserHandle(i));
                        } else {
                            notificationManager.cancelAsUser("Knox_SS_Notification", 100000, new UserHandle(i));
                            notificationManager.notifyAsUser(null, 17304614, createNotification, new UserHandle(i));
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
