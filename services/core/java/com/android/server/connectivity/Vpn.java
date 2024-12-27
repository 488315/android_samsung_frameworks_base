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
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.DnsResolver;
import android.net.INetd;
import android.net.Ikev2VpnProfile;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.IpSecManager;
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
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.UidRangeParcel;
import android.net.UnderlyingNetworkInfo;
import android.net.Uri;
import android.net.VpnProfileState;
import android.net.VpnTransportInfo;
import android.net.eap.EapSessionConfig;
import android.net.ipsec.ike.ChildSaProposal;
import android.net.ipsec.ike.ChildSessionParams;
import android.net.ipsec.ike.IkeSaProposal;
import android.net.ipsec.ike.IkeSession;
import android.net.ipsec.ike.IkeSessionConnectionInfo;
import android.net.ipsec.ike.IkeSessionParams;
import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.net.ipsec.ike.TunnelModeChildSessionParams;
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
import android.system.OsConstants;
import android.system.keystore2.KeyDescriptor;
import android.telephony.CarrierConfigManager;
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
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.net.module.util.BinderUtils;
import com.android.net.module.util.NetdUtils;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleInternal;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.connectivity.VpnIkev2Utils;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.net.BaseNetworkObserver;
import com.android.server.vcn.util.MtuUtils;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knoxguard.KnoxGuardManager;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
    public final Set knoxVpnUidRanges;
    public String mAddress;
    protected boolean mAlwaysOn;
    public final AppOpsManager mAppOpsManager;
    public final Set mBlockedUidsAsToldToConnectivity;
    public final SparseArray mCachedCarrierConfigInfoPerSubId;
    public final CarrierConfigManager mCarrierConfigManager;
    protected VpnConfig mConfig;
    public Connection mConnection;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    final Dependencies mDeps;
    public String mEgressIface;
    public volatile boolean mEnableTeardown;
    public final LocalLog mEventChanges;
    public ProxyInfo mHttpProxyInfo;
    public final Ikev2SessionCreator mIkev2SessionCreator;
    protected String mInterface;
    public boolean mIsPackageTargetingAtLeastQ;
    public boolean mKnoxAlwaysOn;
    public String mLegacyAddress;
    public int mLegacyState;
    protected boolean mLockdown;
    public List mLockdownAllowlist;
    public final Looper mLooper;
    public VpnNetworkAgentWrapper mMockNetworkAgent;
    public final INetd mNetd;
    protected NetworkAgent mNetworkAgent;
    protected NetworkCapabilities mNetworkCapabilities;
    public final NetworkInfo mNetworkInfo;
    public final NetworkProvider mNetworkProvider;
    public final INetworkManagementService mNms;
    public final AnonymousClass2 mObserver;
    public int mOwnerUID;
    protected String mPackage;
    public String mProfileName;
    public PendingIntent mStatusIntent;
    public VpnConfig mSwifiConfig;
    public final SystemServices mSystemServices;
    public final TelephonyManager mTelephonyManager;
    public final int mUserId;
    public final Context mUserIdContext;
    public final UserManager mUserManager;
    public String mV6Address;
    public AnonymousClass1 mVpnNetworkCallback;
    public final VpnProfileStore mVpnProfileStore;
    public final VpnRules mVpnRules;
    protected VpnRunner mVpnRunner;
    public static final long[] IKEV2_VPN_RETRY_DELAYS_MS = {1000, 2000, 5000, 30000, 60000, 300000, 900000};
    public static final long[] DATA_STALL_RECOVERY_DELAYS_MS = {1000, 5000, 30000, 60000, 120000, 240000, 480000, 960000};
    public static final boolean DBG = Debug.semIsProductDev();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.connectivity.Vpn$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseNetworkObserver {
        public AnonymousClass2() {
        }

        public final void interfaceRemoved(String str) {
            synchronized (Vpn.this) {
                try {
                    if (str.equals(Vpn.this.mInterface) && Vpn.this.jniCheck(str) == 0) {
                        Vpn vpn = Vpn.this;
                        if (vpn.mConnection != null) {
                            vpn.mAppOpsManager.finishOp("android:establish_vpn_service", vpn.mOwnerUID, vpn.mPackage, null);
                            Vpn vpn2 = Vpn.this;
                            vpn2.mContext.unbindService(vpn2.mConnection);
                            Vpn.this.cleanupVpnStateLocked();
                        } else if (vpn.mVpnRunner != null) {
                            if (!"[Legacy VPN]".equals(vpn.mPackage)) {
                                Vpn vpn3 = Vpn.this;
                                vpn3.mAppOpsManager.finishOp("android:establish_vpn_manager", vpn3.mOwnerUID, vpn3.mPackage, null);
                            }
                            Vpn.this.mVpnRunner.exit();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void interfaceStatusChanged(String str, boolean z) {
            synchronized (Vpn.this) {
                if (!z) {
                    try {
                        VpnRunner vpnRunner = Vpn.this.mVpnRunner;
                        if (vpnRunner != null && (vpnRunner instanceof LegacyVpnRunner)) {
                            LegacyVpnRunner legacyVpnRunner = (LegacyVpnRunner) vpnRunner;
                            if (str.equals(legacyVpnRunner.mOuterInterface)) {
                                Log.i("LegacyVpnRunner", "Legacy VPN is going down with ".concat(str));
                                legacyVpnRunner.exitVpnRunner();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.connectivity.Vpn$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CarrierConfigInfo {
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

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierConfigInfo(");
            sb.append(this.mccMnc);
            sb.append(") [keepaliveDelaySec=");
            sb.append(this.keepaliveDelaySec);
            sb.append(", encapType=");
            sb.append(this.encapType);
            sb.append(", ipVersion=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.ipVersion, sb, "]");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Connection implements ServiceConnection {
        public IBinder mService;

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.mService = iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            this.mService = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {
        public static InetAddress resolve(final String str) {
            try {
                return InetAddresses.parseNumericAddress(str);
            } catch (IllegalArgumentException unused) {
                CancellationSignal cancellationSignal = new CancellationSignal();
                try {
                    DnsResolver dnsResolver = DnsResolver.getInstance();
                    final CompletableFuture completableFuture = new CompletableFuture();
                    dnsResolver.query(null, str, 0, new Vpn$Dependencies$$ExternalSyntheticLambda0(), cancellationSignal, new DnsResolver.Callback() { // from class: com.android.server.connectivity.Vpn.Dependencies.1
                        @Override // android.net.DnsResolver.Callback
                        public final void onAnswer(Object obj, int i) {
                            List list = (List) obj;
                            if (list.size() > 0) {
                                completableFuture.complete((InetAddress) list.get(0));
                            } else {
                                completableFuture.completeExceptionally(new UnknownHostException(str));
                            }
                        }

                        @Override // android.net.DnsResolver.Callback
                        public final void onError(DnsResolver.DnsException dnsException) {
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

        public static void sendArgumentsToDaemon(LocalSocket localSocket, String[] strArr, Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4 vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4) {
            LegacyVpnRunner legacyVpnRunner = (LegacyVpnRunner) vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4.f$0;
            LocalSocketAddress localSocketAddress = new LocalSocketAddress("charon", LocalSocketAddress.Namespace.RESERVED);
            while (true) {
                try {
                    localSocket.connect(localSocketAddress);
                    break;
                } catch (Exception unused) {
                    legacyVpnRunner.checkInterruptAndDelay(true);
                }
            }
            localSocket.setSoTimeout(500);
            OutputStream outputStream = localSocket.getOutputStream();
            for (String str : strArr) {
                byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                if (bytes.length >= 65535) {
                    throw new IllegalArgumentException("Argument is too large");
                }
                outputStream.write(bytes.length >> 8);
                outputStream.write(bytes.length);
                outputStream.write(bytes);
                legacyVpnRunner.checkInterruptAndDelay(false);
            }
            outputStream.write(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            outputStream.write(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        }

        public static void setBlocking(FileDescriptor fileDescriptor, boolean z) {
            try {
                IoUtils.setBlocking(fileDescriptor, z);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot set tunnel's fd as blocking=" + z, e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class IkeSessionWrapper {
        public final IkeSession mImpl;

        public IkeSessionWrapper(IkeSession ikeSession) {
            this.mImpl = ikeSession;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IkeV2VpnRunner extends VpnRunner implements IkeV2VpnRunnerCallback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Network mActiveNetwork;
        public final AnonymousClass1 mCarrierConfigChangeListener;
        public int mCurrentToken;
        public final ScheduledThreadPoolExecutor mExecutor;
        public IkeSessionConnectionInfo mIkeConnectionInfo;
        public final IpSecManager mIpSecManager;
        public boolean mIsRunning;
        public boolean mMobikeEnabled;
        public final VpnIkev2Utils.Ikev2VpnNetworkCallback mNetworkCallback;
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
        int mValidationFailRetryCount;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v0, types: [android.telephony.CarrierConfigManager$CarrierConfigChangeListener, com.android.server.connectivity.Vpn$IkeV2VpnRunner$1] */
        public IkeV2VpnRunner(Ikev2VpnProfile ikev2VpnProfile, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            super("IkeV2VpnRunner");
            this.mIsRunning = true;
            this.mCurrentToken = -1;
            this.mMobikeEnabled = false;
            this.mValidationFailRetryCount = 0;
            this.mRetryCount = 0;
            ?? r3 = new CarrierConfigManager.CarrierConfigChangeListener() { // from class: com.android.server.connectivity.Vpn.IkeV2VpnRunner.1
                @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
                public final void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                    LocalLog localLog = Vpn.this.mEventChanges;
                    StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "[CarrierConfig] Changed on slot ", " subId=", " carrerId=");
                    m.append(i3);
                    m.append(" specificCarrierId=");
                    m.append(i4);
                    localLog.log(m.toString());
                    synchronized (Vpn.this) {
                        try {
                            Vpn.this.mCachedCarrierConfigInfoPerSubId.remove(i2);
                            IkeV2VpnRunner ikeV2VpnRunner = IkeV2VpnRunner.this;
                            if (Vpn.this.mVpnRunner != ikeV2VpnRunner) {
                                return;
                            }
                            ikeV2VpnRunner.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(ikeV2VpnRunner.mActiveNetwork);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            this.mCarrierConfigChangeListener = r3;
            this.mProfile = ikev2VpnProfile;
            this.mExecutor = scheduledThreadPoolExecutor;
            this.mIpSecManager = (IpSecManager) Vpn.this.mContext.getSystemService(INetd.IPSEC_INTERFACE_PREFIX);
            this.mNetworkCallback = new VpnIkev2Utils.Ikev2VpnNetworkCallback(this, scheduledThreadPoolExecutor);
            String uuid = UUID.randomUUID().toString();
            this.mSessionKey = uuid;
            Log.d("IkeV2VpnRunner", "Generate session key = " + uuid);
            scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
            scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            scheduledThreadPoolExecutor.setRejectedExecutionHandler(new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda0());
            VpnConfig vpnConfig = Vpn.this.mConfig;
            BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda0(Vpn.this, uuid, Vpn.this.createUserAndRestrictedProfilesRanges(Vpn.this.mUserId, vpnConfig.allowedApplications, vpnConfig.disallowedApplications)));
            Vpn.this.mCarrierConfigManager.registerCarrierConfigChangeListener(scheduledThreadPoolExecutor, r3);
        }

        public final int calculateVpnMtu() {
            NetworkInterface byName;
            Network network = this.mIkeConnectionInfo.getNetwork();
            LinkProperties linkProperties = Vpn.this.mConnectivityManager.getLinkProperties(network);
            if (network == null || linkProperties == null) {
                return this.mProfile.getMaxMtu();
            }
            int mtu = linkProperties.getMtu();
            if (mtu == 0) {
                try {
                    Dependencies dependencies = Vpn.this.mDeps;
                    String interfaceName = linkProperties.getInterfaceName();
                    int maxMtu = this.mProfile.getMaxMtu();
                    dependencies.getClass();
                    if (interfaceName != null && (byName = NetworkInterface.getByName(interfaceName)) != null) {
                        maxMtu = byName.getMTU();
                    }
                    mtu = maxMtu;
                } catch (SocketException e) {
                    Log.d("IkeV2VpnRunner", "Got a SocketException when getting MTU from kernel: " + e);
                    return this.mProfile.getMaxMtu();
                }
            }
            Dependencies dependencies2 = Vpn.this.mDeps;
            List saProposals = getChildSessionParams().getSaProposals();
            int maxMtu2 = this.mProfile.getMaxMtu();
            boolean z = this.mIkeConnectionInfo.getLocalAddress() instanceof Inet4Address;
            dependencies2.getClass();
            return MtuUtils.getMtu(saProposals, maxMtu2, z, mtu);
        }

        public final void disconnectVpnRunner() {
            Vpn.this.mEventChanges.log("[VPNRunner] Disconnect runner, underlying net " + this.mActiveNetwork);
            this.mActiveNetwork = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mUnderlyingLinkProperties = null;
            this.mIsRunning = false;
            resetIkeState();
            Vpn.this.mCarrierConfigManager.unregisterCarrierConfigChangeListener(this.mCarrierConfigChangeListener);
            Vpn.this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mExecutor.shutdown();
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner
        public final void exitVpnRunner() {
            Vpn vpn = Vpn.this;
            String str = this.mSessionKey;
            long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
            vpn.getClass();
            BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda1(vpn, str));
            try {
                this.mExecutor.execute(new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(this, 3));
            } catch (RejectedExecutionException unused) {
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0022  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.android.server.connectivity.Vpn.CarrierConfigInfo getCarrierConfigForUnderlyingNetwork() {
            /*
                r9 = this;
                android.net.NetworkCapabilities r0 = r9.mUnderlyingNetworkCapabilities
                long[] r1 = com.android.server.connectivity.Vpn.IKEV2_VPN_RETRY_DELAYS_MS
                r1 = 0
                r2 = -1
                if (r0 != 0) goto La
            L8:
                r0 = r2
                goto L1f
            La:
                boolean r3 = r0.hasTransport(r1)
                if (r3 != 0) goto L11
                goto L8
            L11:
                android.net.NetworkSpecifier r0 = r0.getNetworkSpecifier()
                boolean r3 = r0 instanceof android.net.TelephonyNetworkSpecifier
                if (r3 == 0) goto L8
                android.net.TelephonyNetworkSpecifier r0 = (android.net.TelephonyNetworkSpecifier) r0
                int r0 = r0.getSubscriptionId()
            L1f:
                r3 = 0
                if (r0 != r2) goto L2a
                java.lang.String r9 = "IkeV2VpnRunner"
                java.lang.String r0 = "Underlying network is not a cellular network"
                android.util.Log.d(r9, r0)
                return r3
            L2a:
                com.android.server.connectivity.Vpn r4 = com.android.server.connectivity.Vpn.this
                monitor-enter(r4)
                com.android.server.connectivity.Vpn r5 = com.android.server.connectivity.Vpn.this     // Catch: java.lang.Throwable -> L4a
                android.util.SparseArray r5 = r5.mCachedCarrierConfigInfoPerSubId     // Catch: java.lang.Throwable -> L4a
                boolean r5 = r5.contains(r0)     // Catch: java.lang.Throwable -> L4a
                if (r5 == 0) goto L4c
                java.lang.String r1 = "IkeV2VpnRunner"
                java.lang.String r2 = "Get cached config"
                android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L4a
                com.android.server.connectivity.Vpn r9 = com.android.server.connectivity.Vpn.this     // Catch: java.lang.Throwable -> L4a
                android.util.SparseArray r9 = r9.mCachedCarrierConfigInfoPerSubId     // Catch: java.lang.Throwable -> L4a
                java.lang.Object r9 = r9.get(r0)     // Catch: java.lang.Throwable -> L4a
                com.android.server.connectivity.Vpn$CarrierConfigInfo r9 = (com.android.server.connectivity.Vpn.CarrierConfigInfo) r9     // Catch: java.lang.Throwable -> L4a
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4a
                return r9
            L4a:
                r9 = move-exception
                goto Lb4
            L4c:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4a
                com.android.server.connectivity.Vpn r4 = com.android.server.connectivity.Vpn.this
                android.telephony.TelephonyManager r4 = r4.mTelephonyManager
                android.telephony.TelephonyManager r4 = r4.createForSubscriptionId(r0)
                int r5 = r4.getSimApplicationState()
                r6 = 10
                if (r5 == r6) goto L65
                java.lang.String r9 = "IkeV2VpnRunner"
                java.lang.String r1 = "SIM card is not ready on sub "
                com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r0, r1, r9)
                return r3
            L65:
                com.android.server.connectivity.Vpn r5 = com.android.server.connectivity.Vpn.this
                android.telephony.CarrierConfigManager r5 = r5.mCarrierConfigManager
                android.os.PersistableBundle r5 = r5.getConfigForSubId(r0)
                boolean r6 = android.telephony.CarrierConfigManager.isConfigForIdentifiedCarrier(r5)
                if (r6 != 0) goto L74
                return r3
            L74:
                java.lang.String r3 = "min_udp_port_4500_nat_timeout_sec_int"
                int r3 = r5.getInt(r3)
                java.lang.String r6 = "preferred_ike_protocol_int"
                int r5 = r5.getInt(r6, r2)
                java.lang.String r4 = r4.getSimOperator(r0)
                if (r5 == 0) goto L9f
                r1 = 40
                r6 = 4
                r7 = 17
                if (r5 == r1) goto L98
                r1 = 60
                r8 = 6
                if (r5 == r1) goto L9d
                r1 = 61
                if (r5 == r1) goto L9a
            L98:
                r1 = r7
                goto La0
            L9a:
                r1 = r2
            L9b:
                r6 = r8
                goto La0
            L9d:
                r1 = r7
                goto L9b
            L9f:
                r6 = r1
            La0:
                com.android.server.connectivity.Vpn$CarrierConfigInfo r2 = new com.android.server.connectivity.Vpn$CarrierConfigInfo
                r2.<init>(r4, r3, r1, r6)
                com.android.server.connectivity.Vpn r1 = com.android.server.connectivity.Vpn.this
                monitor-enter(r1)
                com.android.server.connectivity.Vpn r9 = com.android.server.connectivity.Vpn.this     // Catch: java.lang.Throwable -> Lb1
                android.util.SparseArray r9 = r9.mCachedCarrierConfigInfoPerSubId     // Catch: java.lang.Throwable -> Lb1
                r9.put(r0, r2)     // Catch: java.lang.Throwable -> Lb1
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lb1
                return r2
            Lb1:
                r9 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lb1
                throw r9
            Lb4:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4a
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.IkeV2VpnRunner.getCarrierConfigForUnderlyingNetwork():com.android.server.connectivity.Vpn$CarrierConfigInfo");
        }

        public final ChildSessionParams getChildSessionParams() {
            IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                return ikeTunnelConnectionParams.getTunnelModeChildSessionParams();
            }
            List<String> allowedAlgorithms = this.mProfile.getAllowedAlgorithms();
            TunnelModeChildSessionParams.Builder builder = new TunnelModeChildSessionParams.Builder();
            ArrayList arrayList = new ArrayList();
            List asList = Arrays.asList(256, 192, 128);
            if (Ikev2VpnProfile.hasNormalModeAlgorithms(allowedAlgorithms)) {
                ChildSaProposal.Builder builder2 = new ChildSaProposal.Builder();
                Iterator it = asList.iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (allowedAlgorithms.contains("rfc3686(ctr(aes))")) {
                        builder2.addEncryptionAlgorithm(13, intValue);
                    }
                    if (allowedAlgorithms.contains("cbc(aes)")) {
                        builder2.addEncryptionAlgorithm(12, intValue);
                    }
                }
                if (allowedAlgorithms.contains("hmac(sha512)")) {
                    builder2.addIntegrityAlgorithm(14);
                }
                if (allowedAlgorithms.contains("hmac(sha384)")) {
                    builder2.addIntegrityAlgorithm(13);
                }
                if (allowedAlgorithms.contains("hmac(sha256)")) {
                    builder2.addIntegrityAlgorithm(12);
                }
                if (allowedAlgorithms.contains("xcbc(aes)")) {
                    builder2.addIntegrityAlgorithm(5);
                }
                if (allowedAlgorithms.contains("cmac(aes)")) {
                    builder2.addIntegrityAlgorithm(8);
                }
                if (builder2.build().getIntegrityAlgorithms().isEmpty()) {
                    Log.wtf("VpnIkev2Utils", "Missing integrity algorithm when buildling Child SA proposal");
                } else {
                    arrayList.add(builder2.build());
                }
            }
            if (Ikev2VpnProfile.hasAeadAlgorithms(allowedAlgorithms)) {
                ChildSaProposal.Builder builder3 = new ChildSaProposal.Builder();
                if (allowedAlgorithms.contains("rfc7539esp(chacha20,poly1305)")) {
                    builder3.addEncryptionAlgorithm(28, 0);
                }
                if (allowedAlgorithms.contains("rfc4106(gcm(aes))")) {
                    builder3.addEncryptionAlgorithm(20, 256);
                    builder3.addEncryptionAlgorithm(19, 256);
                    builder3.addEncryptionAlgorithm(18, 256);
                    builder3.addEncryptionAlgorithm(20, 192);
                    builder3.addEncryptionAlgorithm(19, 192);
                    builder3.addEncryptionAlgorithm(18, 192);
                    builder3.addEncryptionAlgorithm(20, 128);
                    builder3.addEncryptionAlgorithm(19, 128);
                    builder3.addEncryptionAlgorithm(18, 128);
                }
                arrayList.add(builder3.build());
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                builder.addSaProposal((ChildSaProposal) it2.next());
            }
            int i = OsConstants.AF_INET;
            builder.addInternalAddressRequest(i);
            int i2 = OsConstants.AF_INET6;
            builder.addInternalAddressRequest(i2);
            builder.addInternalDnsServerRequest(i);
            builder.addInternalDnsServerRequest(i2);
            return builder.build();
        }

        public final IkeSessionParams getIkeSessionParams(Network network) {
            IkeSessionParams.Builder remoteIdentification;
            IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                remoteIdentification = new IkeSessionParams.Builder(ikeTunnelConnectionParams.getIkeSessionParams()).setNetwork(network);
            } else {
                Context context = Vpn.this.mContext;
                Ikev2VpnProfile ikev2VpnProfile = this.mProfile;
                remoteIdentification = new IkeSessionParams.Builder(context).setServerHostname(ikev2VpnProfile.getServerAddr()).setNetwork(network).addIkeOption(2).setLocalIdentification(VpnIkev2Utils.parseIkeIdentification(ikev2VpnProfile.getUserIdentity())).setRemoteIdentification(VpnIkev2Utils.parseIkeIdentification(ikev2VpnProfile.getServerAddr()));
                int type = ikev2VpnProfile.getType();
                if (type == 6) {
                    remoteIdentification.setAuthEap(ikev2VpnProfile.getServerRootCaCert(), new EapSessionConfig.Builder().setEapMsChapV2Config(ikev2VpnProfile.getUsername(), ikev2VpnProfile.getPassword()).build());
                } else if (type == 7) {
                    remoteIdentification.setAuthPsk(ikev2VpnProfile.getPresharedKey());
                } else {
                    if (type != 8) {
                        throw new IllegalArgumentException("Unknown auth method set");
                    }
                    remoteIdentification.setAuthDigitalSignature(ikev2VpnProfile.getServerRootCaCert(), ikev2VpnProfile.getUserCert(), ikev2VpnProfile.getRsaPrivateKey());
                }
                ArrayList arrayList = new ArrayList();
                IkeSaProposal.Builder builder = new IkeSaProposal.Builder();
                builder.addEncryptionAlgorithm(13, 256);
                builder.addEncryptionAlgorithm(12, 256);
                builder.addEncryptionAlgorithm(13, 192);
                builder.addEncryptionAlgorithm(12, 192);
                builder.addEncryptionAlgorithm(13, 128);
                builder.addEncryptionAlgorithm(12, 128);
                builder.addIntegrityAlgorithm(14);
                builder.addIntegrityAlgorithm(13);
                builder.addIntegrityAlgorithm(12);
                builder.addIntegrityAlgorithm(5);
                builder.addIntegrityAlgorithm(8);
                IkeSaProposal.Builder builder2 = new IkeSaProposal.Builder();
                builder2.addEncryptionAlgorithm(28, 0);
                builder2.addEncryptionAlgorithm(20, 256);
                builder2.addEncryptionAlgorithm(19, 256);
                builder2.addEncryptionAlgorithm(18, 256);
                builder2.addEncryptionAlgorithm(20, 192);
                builder2.addEncryptionAlgorithm(19, 192);
                builder2.addEncryptionAlgorithm(18, 192);
                builder2.addEncryptionAlgorithm(20, 128);
                builder2.addEncryptionAlgorithm(19, 128);
                builder2.addEncryptionAlgorithm(18, 128);
                for (IkeSaProposal.Builder builder3 : Arrays.asList(builder, builder2)) {
                    builder3.addDhGroup(16);
                    builder3.addDhGroup(31);
                    builder3.addDhGroup(15);
                    builder3.addDhGroup(14);
                    builder3.addPseudorandomFunction(7);
                    builder3.addPseudorandomFunction(6);
                    builder3.addPseudorandomFunction(5);
                    builder3.addPseudorandomFunction(4);
                    builder3.addPseudorandomFunction(8);
                    builder3.addPseudorandomFunction(2);
                }
                arrayList.add(builder.build());
                arrayList.add(builder2.build());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    remoteIdentification.addSaProposal((IkeSaProposal) it.next());
                }
            }
            if (this.mProfile.isAutomaticNattKeepaliveTimerEnabled()) {
                remoteIdentification.setNattKeepAliveDelaySeconds(guessNattKeepaliveTimerForNetwork());
            }
            if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                remoteIdentification.setIpVersion(guessEspIpVersionForNetwork());
                remoteIdentification.setEncapType(guessEspEncapTypeForNetwork());
            }
            return remoteIdentification.build();
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

        public final int guessEspEncapTypeForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof VcnTransportInfo) {
                Log.d("IkeV2VpnRunner", "Running over VCN, encap type is auto");
                return 0;
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.encapType : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                RCPManagerService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "Get customized encap type (", ") on SIM (mccmnc="), carrierConfigForUnderlyingNetwork.mccMnc, ")", "IkeV2VpnRunner");
            }
            return i;
        }

        public final int guessEspIpVersionForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof VcnTransportInfo) {
                Log.d("IkeV2VpnRunner", "Running over VCN, esp IP version is auto");
                return 0;
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.ipVersion : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                RCPManagerService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "Get customized IP version (", ") on SIM (mccmnc="), carrierConfigForUnderlyingNetwork.mccMnc, ")", "IkeV2VpnRunner");
            }
            return i;
        }

        public final int guessNattKeepaliveTimerForNetwork() {
            VcnTransportInfo transportInfo = this.mUnderlyingNetworkCapabilities.getTransportInfo();
            if (transportInfo instanceof VcnTransportInfo) {
                int minUdpPort4500NatTimeoutSeconds = transportInfo.getMinUdpPort4500NatTimeoutSeconds();
                NetworkScoreService$$ExternalSyntheticOutline0.m(minUdpPort4500NatTimeoutSeconds, "Running over VCN, keepalive timer : ", "s", "IkeV2VpnRunner");
                if (-1 != minUdpPort4500NatTimeoutSeconds) {
                    return minUdpPort4500NatTimeoutSeconds;
                }
            }
            CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.keepaliveDelaySec : 30;
            if (carrierConfigForUnderlyingNetwork != null) {
                RCPManagerService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "Get customized keepalive (", "s) on SIM (mccmnc="), carrierConfigForUnderlyingNetwork.mccMnc, ")", "IkeV2VpnRunner");
            }
            return i;
        }

        public final void handleSessionLost(Exception exc) {
            String str;
            int i;
            LinkProperties redactedLinkPropertiesForPackage;
            NetworkCapabilities networkCapabilities = null;
            int i2 = 0;
            if (this.mScheduledHandleNetworkLostFuture != null) {
                Log.d("IkeV2VpnRunner", "Cancel the task for handling network lost timeout");
                this.mScheduledHandleNetworkLostFuture.cancel(false);
                this.mScheduledHandleNetworkLostFuture = null;
            }
            if (exc instanceof IllegalArgumentException) {
                markFailedAndDisconnect(exc);
                return;
            }
            if (exc instanceof IkeProtocolException) {
                IkeProtocolException ikeProtocolException = (IkeProtocolException) exc;
                int errorType = ikeProtocolException.getErrorType();
                int errorType2 = ikeProtocolException.getErrorType();
                i = (errorType2 == 14 || errorType2 == 17 || errorType2 == 24 || errorType2 == 34 || errorType2 == 37 || errorType2 == 38) ? 1 : 2;
                str = "android.net.category.EVENT_IKE_ERROR";
                i2 = errorType;
            } else if (exc instanceof IkeNetworkLostException) {
                str = "android.net.category.EVENT_NETWORK_ERROR";
                i = 2;
                i2 = 2;
            } else if (exc instanceof IkeNonProtocolException) {
                if (!(exc.getCause() instanceof UnknownHostException)) {
                    if (exc.getCause() instanceof IkeTimeoutException) {
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                        i = 2;
                        i2 = 1;
                    } else if (exc.getCause() instanceof IOException) {
                        i2 = 3;
                    } else {
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                        i2 = -1;
                        i = 2;
                    }
                }
                str = "android.net.category.EVENT_NETWORK_ERROR";
                i = 2;
            } else {
                if (exc != null) {
                    Log.wtf("IkeV2VpnRunner", "onSessionLost: exception = " + exc);
                }
                str = null;
                i = -1;
                i2 = -1;
            }
            synchronized (Vpn.this) {
                try {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    }
                    if (str != null && Vpn.isVpnApp(vpn.mPackage)) {
                        Vpn vpn2 = Vpn.this;
                        String str2 = vpn2.getPackage();
                        String str3 = this.mSessionKey;
                        VpnProfileState makeVpnProfileStateLocked = Vpn.this.makeVpnProfileStateLocked();
                        Network network = this.mActiveNetwork;
                        Vpn vpn3 = Vpn.this;
                        NetworkCapabilities networkCapabilities2 = this.mUnderlyingNetworkCapabilities;
                        synchronized (vpn3) {
                            if (networkCapabilities2 != null) {
                                networkCapabilities = vpn3.mConnectivityManager.getRedactedNetworkCapabilitiesForPackage(networkCapabilities2, vpn3.mOwnerUID, vpn3.mPackage);
                            }
                        }
                        NetworkCapabilities networkCapabilities3 = networkCapabilities;
                        Vpn vpn4 = Vpn.this;
                        LinkProperties linkProperties = this.mUnderlyingLinkProperties;
                        synchronized (vpn4) {
                            if (linkProperties == null) {
                                redactedLinkPropertiesForPackage = null;
                            } else {
                                redactedLinkPropertiesForPackage = vpn4.mConnectivityManager.getRedactedLinkPropertiesForPackage(linkProperties, vpn4.mOwnerUID, vpn4.mPackage);
                            }
                        }
                        vpn2.sendEventToVpnManagerApp(str, i, i2, str2, str3, makeVpnProfileStateLocked, network, networkCapabilities3, redactedLinkPropertiesForPackage);
                    }
                    if (i == 1) {
                        markFailedAndDisconnect(exc);
                        return;
                    }
                    scheduleStartIkeSession(-1L);
                    GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Resetting state for token: "), this.mCurrentToken, "IkeV2VpnRunner");
                    synchronized (Vpn.this) {
                        try {
                            Vpn vpn5 = Vpn.this;
                            if (vpn5.mVpnRunner != this) {
                                return;
                            }
                            vpn5.mInterface = null;
                            VpnConfig vpnConfig = vpn5.mConfig;
                            if (vpnConfig != null) {
                                vpnConfig.interfaze = null;
                                if (vpnConfig.routes != null) {
                                    ArrayList arrayList = new ArrayList(Vpn.this.mConfig.routes);
                                    Vpn.this.mConfig.routes.clear();
                                    Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        Vpn.this.mConfig.routes.add(new RouteInfo(((RouteInfo) it.next()).getDestination(), null, null, 7));
                                    }
                                    Vpn vpn6 = Vpn.this;
                                    NetworkAgent networkAgent = vpn6.mNetworkAgent;
                                    if (networkAgent != null) {
                                        Vpn.doSendLinkProperties(networkAgent, vpn6.makeLinkProperties());
                                    }
                                }
                            }
                            resetIkeState();
                            if (i2 != 2) {
                                Dependencies dependencies = Vpn.this.mDeps;
                                int i3 = this.mRetryCount - 1;
                                dependencies.getClass();
                                long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
                                if ((i3 >= 7 ? jArr[6] : jArr[i3]) > 5000) {
                                    Vpn vpn7 = Vpn.this;
                                    String str4 = this.mSessionKey;
                                    vpn7.getClass();
                                    BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda1(vpn7, str4));
                                }
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }

        public final boolean isActiveToken(int i) {
            return this.mCurrentToken == i && this.mIsRunning;
        }

        public final void markFailedAndDisconnect(Exception exc) {
            synchronized (Vpn.this) {
                try {
                    Vpn vpn = Vpn.this;
                    if (vpn.mVpnRunner != this) {
                        return;
                    }
                    vpn.updateState(NetworkInfo.DetailedState.FAILED, exc.getMessage());
                    Vpn vpn2 = Vpn.this;
                    String str = this.mSessionKey;
                    vpn2.getClass();
                    BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda1(vpn2, str));
                    disconnectVpnRunner();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean maybeMigrateIkeSessionAndUpdateVpnTransportInfo(Network network) {
            boolean z;
            VpnTransportInfo vpnTransportInfo;
            int i;
            int i2;
            int orGuessKeepaliveDelaySeconds = getOrGuessKeepaliveDelaySeconds();
            if (this.mSession == null || !this.mMobikeEnabled) {
                z = false;
            } else {
                Log.d("IkeV2VpnRunner", "Migrate IKE Session with token " + this.mCurrentToken + " to network " + network);
                if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                    i = guessEspIpVersionForNetwork();
                    i2 = guessEspEncapTypeForNetwork();
                } else if (this.mProfile.getIkeTunnelConnectionParams() != null) {
                    i = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getIpVersion();
                    i2 = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getEncapType();
                } else {
                    i = 0;
                    i2 = 0;
                }
                this.mSession.mImpl.setNetwork(network, i, i2, orGuessKeepaliveDelaySeconds);
                z = true;
            }
            if (z) {
                synchronized (Vpn.this) {
                    try {
                        int activeVpnType = Vpn.this.getActiveVpnType();
                        Vpn vpn = Vpn.this;
                        VpnConfig vpnConfig = vpn.mConfig;
                        vpnTransportInfo = new VpnTransportInfo(activeVpnType, vpnConfig.session, vpnConfig.allowBypass && !vpn.mLockdown, orGuessKeepaliveDelaySeconds < 60);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!vpnTransportInfo.equals(Vpn.this.mNetworkCapabilities.getTransportInfo())) {
                    Vpn.this.mNetworkCapabilities = new NetworkCapabilities.Builder(Vpn.this.mNetworkCapabilities).setTransportInfo(vpnTransportInfo).build();
                    Vpn.this.mEventChanges.log("[VPNRunner] Update agent caps " + Vpn.this.mNetworkCapabilities);
                    Vpn vpn2 = Vpn.this;
                    Vpn.doSendNetworkCapabilities(vpn2.mNetworkAgent, vpn2.mNetworkCapabilities);
                }
            }
            return z;
        }

        public final void onIkeConnectionInfoChanged(int i, IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            if (!isActiveToken(i)) {
                Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeConnectionInfoChanged obsolete token=" + i);
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "onIkeConnectionInfoChanged called for obsolete token ", "IkeV2VpnRunner");
                return;
            }
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKEEvent-");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, this.mSessionKey, "] onIkeConnectionInfoChanged token=", ", localAddr=", sb);
            sb.append(ikeSessionConnectionInfo.getLocalAddress());
            sb.append(", network=");
            sb.append(ikeSessionConnectionInfo.getNetwork());
            localLog.log(sb.toString());
            this.mIkeConnectionInfo = ikeSessionConnectionInfo;
        }

        public final void onSessionLost(int i, Exception exc) {
            String str;
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKE] Session lost on network ");
            sb.append(this.mActiveNetwork);
            if (exc == null) {
                str = "";
            } else {
                str = " reason " + exc.getMessage();
            }
            sb.append(str);
            localLog.log(sb.toString());
            Log.d("IkeV2VpnRunner", "onSessionLost() called for token " + i);
            if (isActiveToken(i)) {
                handleSessionLost(exc);
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "onSessionLost() called for obsolete token ", "IkeV2VpnRunner");
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
                ikeSessionWrapper.mImpl.kill();
                this.mSession = null;
            }
            this.mIkeConnectionInfo = null;
            this.mMobikeEnabled = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            if (!this.mProfile.isRestrictedToTestNetworks()) {
                Vpn.this.mConnectivityManager.registerSystemDefaultNetworkCallback(this.mNetworkCallback, new Handler(Vpn.this.mLooper));
            } else {
                Vpn.this.mConnectivityManager.requestNetwork(new NetworkRequest.Builder().clearCapabilities().addTransportType(7).addCapability(15).build(), this.mNetworkCallback);
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
                dependencies.getClass();
                long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
                j = i >= 7 ? jArr[6] : jArr[i];
            }
            Log.d("IkeV2VpnRunner", "Retry new IKE session after " + j + " milliseconds.");
            this.mScheduledHandleRetryIkeSessionFuture = this.mExecutor.schedule(new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(this, 0), j, TimeUnit.MILLISECONDS);
        }

        public final void startIkeSession(Network network) {
            Log.d("IkeV2VpnRunner", "Start new IKE session on network " + network);
            Vpn.this.mEventChanges.log("[IKE] Start IKE session over " + network);
            try {
                synchronized (Vpn.this) {
                    try {
                        Vpn vpn = Vpn.this;
                        if (vpn.mVpnRunner != this) {
                            return;
                        }
                        vpn.mInterface = null;
                        resetIkeState();
                        InetAddress localHost = InetAddress.getLocalHost();
                        IpSecManager.IpSecTunnelInterface createIpSecTunnelInterface = this.mIpSecManager.createIpSecTunnelInterface(localHost, localHost, network);
                        this.mTunnelIface = createIpSecTunnelInterface;
                        NetdUtils.setInterfaceUp(Vpn.this.mNetd, createIpSecTunnelInterface.getInterfaceName());
                        int i = this.mCurrentToken + 1;
                        this.mCurrentToken = i;
                        Vpn vpn2 = Vpn.this;
                        Ikev2SessionCreator ikev2SessionCreator = vpn2.mIkev2SessionCreator;
                        Context context = vpn2.mContext;
                        IkeSessionParams ikeSessionParams = getIkeSessionParams(network);
                        ChildSessionParams childSessionParams = getChildSessionParams();
                        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
                        VpnIkev2Utils.IkeSessionCallbackImpl ikeSessionCallbackImpl = new VpnIkev2Utils.IkeSessionCallbackImpl(this, i);
                        VpnIkev2Utils.ChildSessionCallbackImpl childSessionCallbackImpl = new VpnIkev2Utils.ChildSessionCallbackImpl(this, i);
                        ikev2SessionCreator.getClass();
                        this.mSession = new IkeSessionWrapper(new IkeSession(context, ikeSessionParams, childSessionParams, scheduledThreadPoolExecutor, ikeSessionCallbackImpl, childSessionCallbackImpl));
                        Log.d("IkeV2VpnRunner", "IKE session started for token " + i);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                Log.i("IkeV2VpnRunner", "Setup failed for token " + this.mCurrentToken + ". Aborting", e);
                onSessionLost(this.mCurrentToken, e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IkeV2VpnRunnerCallback {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Ikev2SessionCreator {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LegacyVpnRunner extends VpnRunner {
        public List ipAddresses;
        public final String[] mArguments;
        public long mBringupStartTime;
        public final AnonymousClass2 mBroadcastReceiver;
        public final AtomicInteger mOuterConnection;
        public final String mOuterInterface;
        public final VpnProfile mProfile;
        public String mServerIP;
        public final LocalSocket mSockets;
        public final AnonymousClass2 mTetheringChangedReceiver;

        /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.connectivity.Vpn$LegacyVpnRunner$2] */
        /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.connectivity.Vpn$LegacyVpnRunner$2] */
        public LegacyVpnRunner(VpnConfig vpnConfig, String[] strArr, VpnProfile vpnProfile) {
            super("LegacyVpnRunner");
            NetworkInfo networkInfo;
            this.mOuterConnection = new AtomicInteger(-1);
            this.mBringupStartTime = -1L;
            this.ipAddresses = null;
            final int i = 1;
            this.mBroadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.connectivity.Vpn.LegacyVpnRunner.2
                public final /* synthetic */ LegacyVpnRunner this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    NetworkInfo networkInfo2;
                    switch (i) {
                        case 0:
                            if (intent.getAction() != null && "android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
                                int intExtra = intent.getIntExtra("wifi_state", 14);
                                if (intExtra != 11) {
                                    if (intExtra == 13) {
                                        if (Vpn.this.mNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                                            LegacyVpnRunner legacyVpnRunner = this.this$1;
                                            VpnConfig vpnConfig2 = Vpn.this.mConfig;
                                            if (vpnConfig2 != null && vpnConfig2.addresses != null) {
                                                if (legacyVpnRunner.ipAddresses == null) {
                                                    legacyVpnRunner.ipAddresses = new ArrayList();
                                                }
                                                for (LinkAddress linkAddress : Vpn.this.mConfig.addresses) {
                                                    Log.d("LegacyVpnRunner", "addVpnRuleForTethering :" + linkAddress.toString());
                                                    Vpn.this.mVpnRules.addVpnRuleForTethering(linkAddress.toString());
                                                    this.this$1.ipAddresses.add(linkAddress.toString());
                                                }
                                                break;
                                            }
                                        }
                                    } else if (intExtra != 14) {
                                    }
                                }
                                this.this$1.ipAddresses = null;
                                Log.d("LegacyVpnRunner", "WIFI_AP_STATE_DISABLED or WIFI_AP_STATE_FAILED");
                                break;
                            }
                            break;
                        default:
                            if (Vpn.this.mEnableTeardown && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && intent.getIntExtra("networkType", -1) == this.this$1.mOuterConnection.get() && (networkInfo2 = (NetworkInfo) intent.getExtra("networkInfo")) != null && !networkInfo2.isConnectedOrConnecting()) {
                                try {
                                    LegacyVpnRunner legacyVpnRunner2 = this.this$1;
                                    Vpn.this.mObserver.interfaceStatusChanged(legacyVpnRunner2.mOuterInterface, false);
                                    break;
                                } catch (RemoteException unused) {
                                    return;
                                }
                            }
                            break;
                    }
                }
            };
            final int i2 = 0;
            this.mTetheringChangedReceiver = new BroadcastReceiver(this) { // from class: com.android.server.connectivity.Vpn.LegacyVpnRunner.2
                public final /* synthetic */ LegacyVpnRunner this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    NetworkInfo networkInfo2;
                    switch (i2) {
                        case 0:
                            if (intent.getAction() != null && "android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
                                int intExtra = intent.getIntExtra("wifi_state", 14);
                                if (intExtra != 11) {
                                    if (intExtra == 13) {
                                        if (Vpn.this.mNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                                            LegacyVpnRunner legacyVpnRunner = this.this$1;
                                            VpnConfig vpnConfig2 = Vpn.this.mConfig;
                                            if (vpnConfig2 != null && vpnConfig2.addresses != null) {
                                                if (legacyVpnRunner.ipAddresses == null) {
                                                    legacyVpnRunner.ipAddresses = new ArrayList();
                                                }
                                                for (LinkAddress linkAddress : Vpn.this.mConfig.addresses) {
                                                    Log.d("LegacyVpnRunner", "addVpnRuleForTethering :" + linkAddress.toString());
                                                    Vpn.this.mVpnRules.addVpnRuleForTethering(linkAddress.toString());
                                                    this.this$1.ipAddresses.add(linkAddress.toString());
                                                }
                                                break;
                                            }
                                        }
                                    } else if (intExtra != 14) {
                                    }
                                }
                                this.this$1.ipAddresses = null;
                                Log.d("LegacyVpnRunner", "WIFI_AP_STATE_DISABLED or WIFI_AP_STATE_FAILED");
                                break;
                            }
                            break;
                        default:
                            if (Vpn.this.mEnableTeardown && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && intent.getIntExtra("networkType", -1) == this.this$1.mOuterConnection.get() && (networkInfo2 = (NetworkInfo) intent.getExtra("networkInfo")) != null && !networkInfo2.isConnectedOrConnecting()) {
                                try {
                                    LegacyVpnRunner legacyVpnRunner2 = this.this$1;
                                    Vpn.this.mObserver.interfaceStatusChanged(legacyVpnRunner2.mOuterInterface, false);
                                    break;
                                } catch (RemoteException unused) {
                                    return;
                                }
                            }
                            break;
                    }
                }
            };
            if (strArr == null) {
                throw new IllegalArgumentException("Arguments must not be null");
            }
            Vpn.this.mConfig = vpnConfig;
            this.mArguments = strArr;
            this.mSockets = new LocalSocket();
            String str = Vpn.this.mConfig.interfaze;
            this.mOuterInterface = str;
            this.mProfile = vpnProfile;
            if (!TextUtils.isEmpty(str)) {
                Network[] allNetworks = Vpn.this.mConnectivityManager.getAllNetworks();
                int length = allNetworks.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        Network network = allNetworks[i3];
                        LinkProperties linkProperties = Vpn.this.mConnectivityManager.getLinkProperties(network);
                        if (linkProperties != null && linkProperties.getAllInterfaceNames().contains(this.mOuterInterface) && (networkInfo = Vpn.this.mConnectivityManager.getNetworkInfo(network)) != null) {
                            this.mOuterConnection.set(networkInfo.getType());
                            break;
                        }
                        i3++;
                    } else {
                        break;
                    }
                }
            }
            Vpn.this.mContext.registerReceiver(this.mBroadcastReceiver, BatteryService$$ExternalSyntheticOutline0.m("android.net.conn.CONNECTIVITY_CHANGE"));
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
            Vpn.this.mContext.registerReceiver(this.mTetheringChangedReceiver, intentFilter);
        }

        public final void bringup(boolean z) {
            InetAddress resolve;
            List list;
            String m373$$Nest$mgetVpnTypeString = Vpn.m373$$Nest$mgetVpnTypeString(Vpn.this, this.mProfile.type);
            String str = this.mProfile.server;
            if (z) {
                resolve = null;
            } else {
                try {
                    Vpn.this.mDeps.getClass();
                    resolve = Dependencies.resolve(str);
                    String hostAddress = resolve.getHostAddress();
                    String str2 = this.mArguments[0];
                    if (str2 != null) {
                        if (!this.mProfile.server.equals(str2)) {
                            throw new IllegalStateException("Invalid server argument for charon");
                        }
                        this.mArguments[0] = hostAddress;
                    }
                    this.mServerIP = resolve.getHostAddress();
                } catch (Exception e) {
                    Log.i("LegacyVpnRunner", "Aborting", e);
                    AuditLog.logEventAsUser(UserHandle.getUserId(Binder.getCallingUid()), 16, new Object[]{str, m373$$Nest$mgetVpnTypeString});
                    Vpn vpn = Vpn.this;
                    if (!vpn.mLockdown) {
                        Vpn.m376$$Nest$msetupIpRulesForCcMode(vpn, this.mServerIP, false);
                    }
                    stopLegacyDaemons();
                    Vpn vpn2 = Vpn.this;
                    NetworkInfo.DetailedState detailedState = NetworkInfo.DetailedState.FAILED;
                    vpn2.updateState(detailedState, e.getMessage());
                    exitVpnRunner();
                    if (!z) {
                        Vpn.m372$$Nest$mbroadcastVpnState(Vpn.this, detailedState, e.getMessage());
                    }
                    if (z) {
                        Log.d("LegacyVpnRunner", "Sending message to clear connecting notification due to some connection error");
                        Vpn vpn3 = Vpn.this;
                        vpn3.getClass();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                if (vpn3.mProfileName != null) {
                                    Intent intent = new Intent();
                                    intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
                                    intent.setPackage("com.samsung.sVpn");
                                    intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", vpn3.mProfileName);
                                    intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ID_INTERNAL", "ipsec0");
                                    intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", 2);
                                    Log.d("Vpn", "send message to clear connecting notificiation due to some error while connecting to server");
                                    vpn3.mContext.sendBroadcastAsUser(intent, new UserHandle(vpn3.mUserId), "com.samsung.android.knox.permission.KNOX_VPN_SOLUTION");
                                }
                            } catch (Exception unused) {
                                Log.e("Vpn", "unable to send message to clear connecting notification");
                            }
                            return;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    return;
                }
            }
            this.mBringupStartTime = SystemClock.elapsedRealtime();
            while (true) {
                Vpn.this.mDeps.getClass();
                if (SystemService.isStopped("charon")) {
                    break;
                } else {
                    checkInterruptAndDelay(true);
                }
            }
            Vpn.this.mDeps.getClass();
            File file = new File("/data/misc/vpn/state");
            file.delete();
            if (file.exists()) {
                throw new IllegalStateException("Cannot delete the state");
            }
            File file2 = new File("/data/misc/vpn/abort");
            file2.delete();
            if (file2.exists()) {
                throw new IllegalStateException("Cannot delete the abort");
            }
            Vpn.this.updateState(NetworkInfo.DetailedState.CONNECTING, "execute");
            Vpn.this.mDeps.getClass();
            SystemService.start("charon");
            while (true) {
                Vpn.this.mDeps.getClass();
                if (SystemService.isRunning("charon")) {
                    break;
                } else {
                    checkInterruptAndDelay(true);
                }
            }
            Dependencies dependencies = Vpn.this.mDeps;
            LocalSocket localSocket = this.mSockets;
            String[] strArr = this.mArguments;
            Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4 vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4 = new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4(this);
            dependencies.getClass();
            Dependencies.sendArgumentsToDaemon(localSocket, strArr, vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4);
            while (!file.exists()) {
                Vpn.this.mDeps.getClass();
                if (!SystemService.isRunning("charon")) {
                    throw new IllegalStateException("charon is dead");
                }
                if (file2.exists()) {
                    throw new IllegalStateException("charon is shutdown");
                }
                checkInterruptAndDelay(true);
            }
            String[] split = FileUtils.readTextFile(file, 0, null).split("\n", -1);
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
            List list3 = Vpn.this.mConfig.routes;
            if (list3 == null || list3.isEmpty()) {
                Vpn.this.mConfig.addLegacyRoutes(split[2]);
            }
            if (!z && ((list = Vpn.this.mConfig.routes) == null || list.isEmpty())) {
                Vpn.this.mConfig.addLegacyRoutes(split[2]);
            }
            List list4 = Vpn.this.mConfig.dnsServers;
            if (list4 == null || list4.size() == 0) {
                String trim = split[3].trim();
                if (!trim.isEmpty()) {
                    Vpn.this.mConfig.dnsServers = Arrays.asList(trim.split(" "));
                }
            }
            List list5 = Vpn.this.mConfig.searchDomains;
            if (list5 == null || list5.size() == 0) {
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
                Vpn vpn4 = Vpn.this;
                if (!vpn4.mLockdown) {
                    Vpn.m376$$Nest$msetupIpRulesForCcMode(vpn4, this.mServerIP, true);
                }
            }
            synchronized (Vpn.this) {
                try {
                    Vpn.this.mConfig.startTime = SystemClock.elapsedRealtime();
                    checkInterruptAndDelay(false);
                    Vpn vpn5 = Vpn.this;
                    Dependencies dependencies2 = vpn5.mDeps;
                    String str3 = vpn5.mConfig.interfaze;
                    dependencies2.getClass();
                    if (vpn5.jniCheck(str3) == 0) {
                        throw new IllegalStateException(Vpn.this.mConfig.interfaze + " is gone");
                    }
                    Vpn vpn6 = Vpn.this;
                    vpn6.mInterface = vpn6.mConfig.interfaze;
                    Vpn.m375$$Nest$mprepareStatusIntent(vpn6);
                    if (z) {
                        Vpn.m371$$Nest$magentConnectForKnoxInterface(Vpn.this);
                    } else {
                        Vpn.this.agentConnect(null);
                    }
                    Log.i("LegacyVpnRunner", "Connected!");
                    if (!z) {
                        Log.d("LegacyVpnRunner", "add vpn rules");
                        VpnRules vpnRules = Vpn.this.mVpnRules;
                        vpnRules.getClass();
                        Log.d("VpnRules", "createVpnPostroutingChain");
                        vpnRules.runVpnRulesCommand(4, "*mangle\n-N vpn_POSTROUTING\nCOMMIT\n");
                        vpnRules.runVpnRulesCommand(4, "*mangle\n-D POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
                        vpnRules.runVpnRulesCommand(4, "*mangle\n-A POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
                        VpnRules vpnRules2 = Vpn.this.mVpnRules;
                        vpnRules2.getClass();
                        Log.d("VpnRules", "addTcpmssClampRule");
                        vpnRules2.runVpnRulesCommand(4, "*mangle\n-A vpn_POSTROUTING -p tcp --tcp-flag SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu\nCOMMIT\n");
                        Vpn.m372$$Nest$mbroadcastVpnState(Vpn.this, NetworkInfo.DetailedState.CONNECTED, "agentConnect");
                    }
                    try {
                        if (((WifiManager) Vpn.this.mContext.getSystemService("wifi")).getWifiApState() == 13) {
                            if (this.ipAddresses == null) {
                                this.ipAddresses = new ArrayList();
                            }
                            for (LinkAddress linkAddress : Vpn.this.mConfig.addresses) {
                                Log.d("LegacyVpnRunner", "addVpnRuleForTethering :" + linkAddress.toString());
                                Vpn.this.mVpnRules.addVpnRuleForTethering(linkAddress.toString());
                                this.ipAddresses.add(linkAddress.toString());
                            }
                        }
                    } catch (Exception e2) {
                        Log.d("LegacyVpnRunner", "Failed to get hotspot status, assume hotpost disabled");
                        e2.printStackTrace();
                    }
                } finally {
                }
            }
            AuditLog.logEventAsUser(UserHandle.getUserId(Binder.getCallingUid()), 15, new Object[]{str, m373$$Nest$mgetVpnTypeString});
        }

        public final void checkInterruptAndDelay(boolean z) {
            if (SystemClock.elapsedRealtime() - this.mBringupStartTime <= 60000) {
                Thread.sleep(z ? 200L : 1L);
            } else {
                Vpn.this.updateState(NetworkInfo.DetailedState.FAILED, "checkpoint");
                throw new IllegalStateException("VPN bringup took too long");
            }
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner
        public final void exitVpnRunner() {
            interrupt();
            Vpn vpn = Vpn.this;
            long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
            vpn.agentDisconnect$1();
            try {
                Vpn.this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                Vpn.this.mContext.unregisterReceiver(this.mTetheringChangedReceiver);
            } catch (IllegalArgumentException unused) {
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x021d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0184 A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x018c A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x019f A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:93:0x00ed A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x00f5 A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:98:0x0108 A[Catch: all -> 0x004f, TryCatch #7 {all -> 0x004f, blocks: (B:4:0x000d, B:13:0x0043, B:15:0x0049, B:17:0x0054, B:18:0x0063, B:20:0x0067, B:21:0x006b, B:23:0x0071, B:25:0x0097, B:26:0x0099, B:28:0x00bc, B:31:0x00c2, B:34:0x00cb, B:40:0x00d0, B:41:0x00da, B:42:0x0213, B:43:0x021a, B:91:0x00e7, B:93:0x00ed, B:95:0x00f5, B:96:0x0104, B:98:0x0108, B:99:0x010c, B:101:0x0112, B:103:0x0137, B:104:0x0139, B:106:0x015c, B:109:0x0162, B:112:0x016b, B:118:0x0170, B:119:0x017d, B:58:0x017e, B:60:0x0184, B:62:0x018c, B:63:0x019b, B:65:0x019f, B:66:0x01a3, B:68:0x01a9, B:70:0x01ce, B:71:0x01d0, B:73:0x01f3, B:76:0x01f9, B:79:0x0202, B:85:0x0207), top: B:3:0x000d }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 560
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.LegacyVpnRunner.run():void");
        }

        public final void stopLegacyDaemons() {
            Log.v("LegacyVpnRunner", "stopLegacyDaemons: begin");
            IoUtils.closeQuietly(this.mSockets);
            for (int i = 0; i < 20 && SystemService.isRunning("charon"); i++) {
                try {
                    Thread.sleep(200L);
                } catch (Exception unused) {
                }
            }
            SystemService.stop("charon");
        }

        public final void waitForDaemonsToStop() {
            if (Vpn.this.mNetworkInfo.isConnected()) {
                do {
                    Thread.sleep(2000L);
                    try {
                        NetworkInterface.getByName("ipsec0").getInetAddresses();
                        try {
                            Iterator it = Collections.list(NetworkInterface.getByName(Vpn.this.mEgressIface).getInetAddresses()).iterator();
                            boolean z = false;
                            while (it.hasNext()) {
                                z = true;
                            }
                            if (!z) {
                                Log.d("LegacyVpnRunner", "Egress Iface (" + Vpn.this.mEgressIface + ") deactivated");
                                return;
                            }
                            Vpn.this.mDeps.getClass();
                        } catch (Exception unused) {
                            RCPManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Egress Iface ("), Vpn.this.mEgressIface, ") doesn't exist", "LegacyVpnRunner");
                            return;
                        }
                    } catch (Exception unused2) {
                        Log.d("LegacyVpnRunner", "charon Iface doesn't exist");
                        return;
                    }
                } while (!SystemService.isStopped("charon"));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class SystemServices {
        public final Context mContext;

        public SystemServices(Context context) {
            this.mContext = context;
        }

        public final ContentResolver getContentResolverAsUser(int i) {
            return this.mContext.createContextAsUser(UserHandle.of(i), 0).getContentResolver();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface ValidationStatusCallback {
        void onValidationStatus(int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class VpnNetworkAgentWrapper extends NetworkAgent {
        public final ValidationStatusCallback mCallback;

        public VpnNetworkAgentWrapper(Context context, Looper looper, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, NetworkScore networkScore, NetworkAgentConfig networkAgentConfig, NetworkProvider networkProvider, Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4 vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4) {
            super(context, looper, "VPN", networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider);
            this.mCallback = vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4;
        }

        public final void onNetworkUnwanted() {
        }

        public final void onValidationStatus(int i, Uri uri) {
            ValidationStatusCallback validationStatusCallback = this.mCallback;
            if (validationStatusCallback != null) {
                validationStatusCallback.onValidationStatus(i);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    abstract class VpnRunner extends Thread {
        public VpnRunner(String str) {
            super(str);
        }

        public final void exit() {
            synchronized (Vpn.this) {
                exitVpnRunner();
                Vpn vpn = Vpn.this;
                long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
                vpn.cleanupVpnStateLocked();
            }
        }

        public abstract void exitVpnRunner();
    }

    /* renamed from: -$$Nest$magentConnectForKnoxInterface, reason: not valid java name */
    public static void m371$$Nest$magentConnectForKnoxInterface(final Vpn vpn) {
        vpn.getClass();
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName(vpn.mInterface);
        List<LinkAddress> list = vpn.mConfig.addresses;
        if (list != null) {
            for (LinkAddress linkAddress : list) {
                if (linkAddress.getAddress() instanceof Inet4Address) {
                    vpn.mAddress = linkAddress.getAddress().getHostAddress();
                    VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("The ipv4 address added to the knox vpn interface is "), vpn.mAddress, "Vpn");
                }
                if (linkAddress.getAddress() instanceof Inet6Address) {
                    vpn.mV6Address = linkAddress.getAddress().getHostAddress();
                    VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("The ipv6 address added to the knox vpn interface is "), vpn.mV6Address, "Vpn");
                }
                linkProperties.addLinkAddress(linkAddress);
            }
        }
        List list2 = vpn.mConfig.dnsServers;
        if (list2 != null) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                linkProperties.addDnsServer(InetAddresses.parseNumericAddress((String) it.next()));
            }
        }
        ProxyInfo proxyInfo = vpn.mHttpProxyInfo;
        if (proxyInfo != null) {
            linkProperties.setHttpProxy(proxyInfo);
        }
        StringBuilder sb = new StringBuilder();
        List list3 = vpn.mConfig.searchDomains;
        if (list3 != null) {
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append(' ');
            }
        }
        linkProperties.setDomains(sb.toString().trim());
        int i = vpn.mConfig.mtu;
        if (i > 0) {
            linkProperties.setMtu(i);
        }
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder(vpn.mNetworkCapabilities);
        builder.addCapability(12);
        vpn.mLegacyState = 2;
        vpn.updateState(NetworkInfo.DetailedState.CONNECTING, "agentConnect");
        NetworkAgentConfig build = new NetworkAgentConfig.Builder().setBypassableVpn(true).build();
        builder.setOwnerUid(Binder.getCallingUid());
        builder.setAdministratorUids(new int[]{Binder.getCallingUid()});
        builder.setTransportInfo(new VpnTransportInfo(4, (String) null));
        vpn.mNetworkCapabilities = builder.build();
        vpn.mNetworkAgent = new NetworkAgent(vpn.mContext, vpn.mLooper, vpn.mNetworkCapabilities, linkProperties, new NetworkScore.Builder().setLegacyInt(101).build(), build, vpn.mNetworkProvider) { // from class: com.android.server.connectivity.Vpn.3
            public final void onNetworkCreated() {
                int netId;
                Log.d("Vpn", "knox vpn network is created");
                Vpn vpn2 = Vpn.this;
                long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
                vpn2.getClass();
                try {
                    synchronized (vpn2) {
                        NetworkAgent networkAgent = vpn2.mNetworkAgent;
                        if (networkAgent != null) {
                            Network network = networkAgent.getNetwork();
                            if (network != null) {
                                netId = network.getNetId();
                            }
                        }
                        netId = 0;
                    }
                    Vpn.getKnoxVpnService().addVpnUidRanges(vpn2.mProfileName, netId, vpn2.mInterface, vpn2.mAddress, vpn2.mV6Address);
                    vpn2.updateUidListInNetworkCapabilities();
                    vpn2.applyBlockingRulesToUidRange(false);
                    vpn2.showNotificationForKnoxStrongSwan(vpn2.mUserId);
                } catch (Exception e) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error occured "), "Vpn");
                }
            }

            public final void onNetworkDestroyed() {
                Log.d("Vpn", "knox vpn network is destroyed");
            }

            public final void onNetworkUnwanted() {
            }
        };
        vpn.registerMockNetworkAgent();
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                Vpn vpn2 = Vpn.this;
                long[] jArr = Vpn.IKEV2_VPN_RETRY_DELAYS_MS;
                vpn2.getClass();
                try {
                    vpn2.mNetworkAgent.register();
                } catch (Exception e) {
                    vpn2.mNetworkAgent = null;
                    vpn2.unregisterMockNetworkAgent();
                    throw e;
                }
            }
        });
        NetworkAgent networkAgent = vpn.mNetworkAgent;
        Network[] networkArr = vpn.mConfig.underlyingNetworks;
        networkAgent.setUnderlyingNetworks(networkArr != null ? Arrays.asList(networkArr) : null);
        vpn.updateState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
                intent.setPackage("com.samsung.sVpn");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACTION_INTERNAL", "tun_info");
                String str = vpn.mProfileName;
                if (str != null) {
                    intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", str);
                }
                String str2 = vpn.mInterface;
                if (str2 != null) {
                    intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ID_INTERNAL", str2);
                }
                intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", 1);
                intent.putExtra("com.samsung.android.knox.intent.extra.TUN_ADDRESS_INTERNAL", vpn.mAddress);
                intent.putExtra("com.samsung.android.knox.intent.extra.TUNV6_ADDRESS_INTERNAL", vpn.mV6Address);
                intent.putExtra("com.samsung.android.knox.intent.extra.VPN_CLIENT_TYPE_INTERNAL", 1);
                vpn.mContext.sendBroadcastAsUser(intent, new UserHandle(vpn.mUserId), "com.samsung.android.knox.permission.KNOX_VPN_SOLUTION");
            } catch (Exception unused) {
                Log.e("Vpn", "unable to send interface details");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mbroadcastVpnState, reason: not valid java name */
    public static void m372$$Nest$mbroadcastVpnState(Vpn vpn, NetworkInfo.DetailedState detailedState, String str) {
        vpn.getClass();
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.vpn.action.VPN_STATE_CHANGED");
        intent.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
        intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, detailedState.name());
        intent.putExtra("reason", str);
        vpn.mContext.sendBroadcast(intent, "android.permission.NETWORK_SETTINGS");
        StringBuilder sb = new StringBuilder("send state=");
        sb.append(detailedState);
        RCPManagerService$$ExternalSyntheticOutline0.m(sb, ", reason=", str, "Vpn");
    }

    /* renamed from: -$$Nest$mgetVpnTypeString, reason: not valid java name */
    public static String m373$$Nest$mgetVpnTypeString(Vpn vpn, int i) {
        vpn.getClass();
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
                return "IKEV2_IPSEC_USER_PASS";
            case 7:
                return "IKEV2_IPSEC_PSK";
            case 8:
                return "IKEV2_IPSEC_RSA";
            case 9:
                return "IKEV2_IPSEC_EAP_TLS";
            case 10:
                return "IKEV2_FROM_IKE_TUN_CONN_PARAMS";
            default:
                return "";
        }
    }

    /* renamed from: -$$Nest$mprepareStatusIntent, reason: not valid java name */
    public static void m375$$Nest$mprepareStatusIntent(Vpn vpn) {
        vpn.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Dependencies dependencies = vpn.mDeps;
            Context context = vpn.mContext;
            dependencies.getClass();
            vpn.mStatusIntent = VpnConfig.getIntentForStatusPanel(context);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: -$$Nest$msetupIpRulesForCcMode, reason: not valid java name */
    public static void m376$$Nest$msetupIpRulesForCcMode(Vpn vpn, String str, boolean z) {
        String stringBuffer;
        VpnRules vpnRules = vpn.mVpnRules;
        if (MdfUtils.isMdfEnforced()) {
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("setupIpRulesForCcMode: ", str, ", ");
            m.append(vpn.mLegacyAddress);
            m.append(", ");
            m.append(z);
            Log.i("Vpn", m.toString());
            try {
                vpnRules.setFirewallEnabled(z);
                vpnRules.setFirewallEgressDestRule(500, str, z);
                vpnRules.setFirewallEgressDestRule(4500, str, z);
                vpnRules.setFirewallEgressDestRule(1701, str, z);
                vpnRules.setFirewallInterfaceRule(z);
                vpnRules.setFirewallEgressSourceRule(vpn.mLegacyAddress, z);
                Log.i("Vpn", "setupIpRulesForCcMode: v4=" + str);
                if (str == null) {
                    stringBuffer = null;
                } else {
                    StringBuffer stringBuffer2 = new StringBuffer("64:ff9b::");
                    String[] split = str.split("\\.");
                    for (int i = 0; i < 4; i++) {
                        stringBuffer2.append(Integer.toHexString(Integer.parseInt(split[i])));
                        if (i == 1) {
                            stringBuffer2.append(":");
                        }
                    }
                    stringBuffer = stringBuffer2.toString();
                }
                vpnRules.setFirewallEgressDestRule(500, stringBuffer, z);
                vpnRules.setFirewallEgressDestRule(4500, stringBuffer, z);
                vpnRules.setFirewallEgressDestRule(1701, stringBuffer, z);
                vpnRules.setFirewallEgressSourceRule("fe80::/64", z);
                vpnRules.setFirewallEgressSourceRule("2000::/4", z);
                Log.i("Vpn", "setupIpRulesForCcMode: v6=" + stringBuffer);
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Failed to set firewall rule: ", "Vpn");
            }
        }
    }

    public Vpn(Looper looper, Context context, Dependencies dependencies, INetworkManagementService iNetworkManagementService, INetd iNetd, int i, VpnProfileStore vpnProfileStore) {
        this(looper, context, dependencies, iNetworkManagementService, iNetd, i, vpnProfileStore, new SystemServices(context), new Ikev2SessionCreator());
    }

    public Vpn(Looper looper, Context context, Dependencies dependencies, INetworkManagementService iNetworkManagementService, INetd iNetd, int i, VpnProfileStore vpnProfileStore, SystemServices systemServices, Ikev2SessionCreator ikev2SessionCreator) {
        boolean z = true;
        this.mEnableTeardown = true;
        this.mProfileName = null;
        this.mAddress = null;
        this.mV6Address = null;
        this.mHttpProxyInfo = null;
        this.mKnoxAlwaysOn = false;
        this.mEventChanges = new LocalLog(100);
        this.mCachedCarrierConfigInfoPerSubId = new SparseArray();
        this.mAlwaysOn = false;
        this.mLockdown = false;
        this.mLockdownAllowlist = Collections.emptyList();
        this.mBlockedUidsAsToldToConnectivity = new ArraySet();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mObserver = anonymousClass2;
        this.knoxVpnUidRanges = new ArraySet();
        this.mVpnProfileStore = vpnProfileStore;
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mUserIdContext = context.createContextAsUser(UserHandle.of(i), 0);
        this.mCarrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
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
        VpnRules vpnRules = new VpnRules();
        if (vpnRules.mNetdService == null) {
            vpnRules.bindNetdNativeService();
        }
        this.mVpnRules = vpnRules;
        try {
            iNetworkManagementService.registerObserver(anonymousClass2);
        } catch (RemoteException e) {
            Log.wtf("Vpn", "Problem registering observer", e);
        }
        NetworkProvider networkProvider = new NetworkProvider(context, looper, "VpnNetworkProvider:" + this.mUserId);
        this.mNetworkProvider = networkProvider;
        this.mConnectivityManager.registerNetworkProvider(networkProvider);
        this.mLegacyState = 0;
        this.mNetworkInfo = new NetworkInfo(17, 0, "VPN", "");
        this.mNetworkCapabilities = new NetworkCapabilities.Builder().addTransportType(4).removeCapability(15).addCapability(28).setTransportInfo(new VpnTransportInfo(-1, (String) null, false, false)).build();
        int i2 = this.mUserId;
        SystemServices systemServices2 = this.mSystemServices;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String string = Settings.Secure.getString(systemServices2.getContentResolverAsUser(i2), "always_on_vpn_app");
            if (Settings.Secure.getInt(systemServices2.getContentResolverAsUser(i2), "always_on_vpn_lockdown", 0) == 0) {
                z = false;
            }
            String string2 = Settings.Secure.getString(systemServices2.getContentResolverAsUser(i2), "always_on_vpn_lockdown_whitelist");
            setAlwaysOnPackageInternal(string, TextUtils.isEmpty(string2) ? Collections.emptyList() : Arrays.asList(string2.split(",")), z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void buildCertificatePathFromRoot(X509Certificate x509Certificate, KeyStore keyStore, List list) {
        if (x509Certificate == null) {
            return;
        }
        ((ArrayList) list).add(x509Certificate);
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            Certificate certificate = keyStore.getCertificate(aliases.nextElement());
            if (certificate instanceof X509Certificate) {
                X509Certificate x509Certificate2 = (X509Certificate) certificate;
                if (!x509Certificate2.equals(x509Certificate) && x509Certificate2.getIssuerX500Principal().equals(x509Certificate.getSubjectX500Principal())) {
                    try {
                        x509Certificate2.verify(x509Certificate.getPublicKey());
                        buildCertificatePathFromRoot(x509Certificate2, keyStore, list);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    public static Intent buildVpnManagerEventIntent(String str, int i, int i2, String str2, String str3, VpnProfileState vpnProfileState, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
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

    public static String convertToPemCert(X509Certificate x509Certificate) {
        try {
            String encodeToString = Base64.encodeToString(x509Certificate.getEncoded(), 2);
            int length = encodeToString.length();
            String str = "";
            int i = 0;
            for (int i2 = 0; i2 < length; i2 += 64) {
                int i3 = length - i;
                if (i3 >= 64) {
                    i3 = 64;
                }
                i += i3;
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                m.append(encodeToString.substring(i2, i3 + i2));
                m.append("\n");
                str = m.toString();
            }
            return XmlUtils$$ExternalSyntheticOutline0.m("-----BEGIN CERTIFICATE-----\n", str, "-----END CERTIFICATE-----");
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Failed to convertToPemCert: ", "Vpn");
            return null;
        }
    }

    public static Range createUidRangeForUser(int i) {
        return new Range(Integer.valueOf(i * 100000), Integer.valueOf(((i + 1) * 100000) - 1));
    }

    public static void doSendLinkProperties(NetworkAgent networkAgent, LinkProperties linkProperties) {
        if (networkAgent instanceof VpnNetworkAgentWrapper) {
            ((VpnNetworkAgentWrapper) networkAgent).sendLinkProperties(linkProperties);
        } else {
            networkAgent.sendLinkProperties(linkProperties);
        }
    }

    public static void doSendNetworkCapabilities(NetworkAgent networkAgent, NetworkCapabilities networkCapabilities) {
        if (networkAgent instanceof VpnNetworkAgentWrapper) {
            ((VpnNetworkAgentWrapper) networkAgent).sendNetworkCapabilities(networkCapabilities);
        } else {
            networkAgent.sendNetworkCapabilities(networkCapabilities);
        }
    }

    public static boolean doesPackageHaveAppop(Context context, String str, String str2) {
        return ((AppOpsManager) context.getSystemService("appops")).noteOpNoThrow(str2, Binder.getCallingUid(), str, null, null) == 0;
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

    public static String getCaCertificateFromKeystoreAsPem(KeyStore keyStore, String str) {
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

    public static IKnoxVpnPolicy getKnoxVpnService() {
        return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getUcmCertificate(java.lang.String r4, java.lang.String r5) {
        /*
            r0 = 0
            java.lang.String r1 = "Vpn"
            if (r5 == 0) goto L6e
            int r2 = r5.length()
            if (r2 > 0) goto Ld
        Lb:
            r5 = r0
            goto L45
        Ld:
            java.lang.String r2 = "com.samsung.ucs.ucsservice"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Exception -> L3e
            com.samsung.android.knox.ucm.core.IUcmService r2 = com.samsung.android.knox.ucm.core.IUcmService.Stub.asInterface(r2)     // Catch: java.lang.Exception -> L3e
            if (r2 == 0) goto Lb
            com.samsung.android.knox.ucm.core.ucmRetParcelable r5 = r2.getCertificateChain(r5)     // Catch: java.lang.Exception -> L3e
            if (r5 == 0) goto Lb
            byte[] r5 = r5.mData     // Catch: java.lang.Exception -> L3e
            if (r5 != 0) goto L25
            goto Lb
        L25:
            java.lang.String r2 = "X.509"
            java.security.cert.CertificateFactory r2 = java.security.cert.CertificateFactory.getInstance(r2)     // Catch: java.security.cert.CertificateException -> L37 java.lang.Exception -> L3e
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch: java.security.cert.CertificateException -> L37 java.lang.Exception -> L3e
            r3.<init>(r5)     // Catch: java.security.cert.CertificateException -> L37 java.lang.Exception -> L3e
            java.security.cert.Certificate r5 = r2.generateCertificate(r3)     // Catch: java.security.cert.CertificateException -> L37 java.lang.Exception -> L3e
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch: java.security.cert.CertificateException -> L37 java.lang.Exception -> L3e
            goto L45
        L37:
            r5 = move-exception
            java.lang.String r2 = "Failed to toCertificate"
            android.util.Log.w(r1, r2, r5)     // Catch: java.lang.Exception -> L3e
            goto Lb
        L3e:
            r5 = move-exception
            java.lang.String r2 = "Failed to getUCMCertificate"
            android.util.Log.w(r1, r2, r5)
            goto Lb
        L45:
            if (r5 == 0) goto L6e
            int r2 = r5.getBasicConstraints()     // Catch: java.lang.Exception -> L5a
            if (r2 <= 0) goto L5c
            java.lang.String r2 = "CACERT_"
            boolean r4 = r4.equals(r2)     // Catch: java.lang.Exception -> L5a
            if (r4 == 0) goto L6e
            java.lang.String r4 = convertToPemCert(r5)     // Catch: java.lang.Exception -> L5a
            return r4
        L5a:
            r4 = move-exception
            goto L69
        L5c:
            java.lang.String r2 = "USRCERT_"
            boolean r4 = r4.equals(r2)     // Catch: java.lang.Exception -> L5a
            if (r4 == 0) goto L6e
            java.lang.String r4 = convertToPemCert(r5)     // Catch: java.lang.Exception -> L5a
            return r4
        L69:
            java.lang.String r5 = "Failed to convertToPemCert, "
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r4, r5, r1)
        L6e:
            java.lang.String r4 = "Failed to getCcmCertificate"
            android.util.Log.e(r1, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.getUcmCertificate(java.lang.String, java.lang.String):java.lang.String");
    }

    public static boolean isVpnApp(String str) {
        return (str == null || "[Legacy VPN]".equals(str)) ? false : true;
    }

    private native boolean jniAddAddress(String str, String str2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCheck(String str);

    private native int jniCreate(int i);

    private native int jniCreateSecureWifi(int i);

    private native boolean jniDelAddress(String str, String str2, int i);

    private native String jniGetName(int i);

    private native void jniReset(String str);

    private native int jniSetAddresses(String str, String str2);

    public static String makeKeystoreEngineGrantString(String str) {
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

    public final synchronized boolean addAddress(int i, String str) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniAddAddress = jniAddAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniAddAddress;
    }

    public void addUserToRanges(Set set, int i, List list, List list2) {
        if (list != null) {
            Iterator it = ((TreeSet) getAppsUids(i, list)).iterator();
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
        if (list2 == null) {
            set.add(createUidRangeForUser(i));
            return;
        }
        Range createUidRangeForUser = createUidRangeForUser(i);
        int intValue2 = ((Integer) createUidRangeForUser.getLower()).intValue();
        Iterator it2 = ((TreeSet) getAppsUids(i, list2)).iterator();
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
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x00de, code lost:
    
        if (((com.android.server.connectivity.Vpn.IkeV2VpnRunner) r4).getOrGuessKeepaliveDelaySeconds() < 60) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void agentConnect(com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4 r22) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.agentConnect(com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4):void");
    }

    public final void agentDisconnect$1() {
        cleanPortBypassRule();
        updateState(NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
        if (getknoxVpnTypeForStrongswanProfile()) {
            return;
        }
        int i = this.mUserId;
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
                        notificationManager.cancelAsUser("Knox_SS_Notification", 100000, new UserHandle(i));
                    } else {
                        notificationManager.cancelAsUser(null, 17304844, new UserHandle(i));
                    }
                } catch (Exception e) {
                    Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void applyBlockingRulesToUidRange(boolean z) {
        try {
            this.mConnectivityManager.setRequireVpnForUids(z, this.knoxVpnUidRanges);
        } catch (RuntimeException e) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Updating blocked=", " for UIDs ", z);
            m.append(Arrays.toString(((ArraySet) this.knoxVpnUidRanges).toArray()));
            m.append(" failed");
            Log.e("Vpn", m.toString(), e);
        }
    }

    public final void cleanPortBypassRule() {
        VpnConfig vpnConfig = this.mSwifiConfig;
        if (vpnConfig != null) {
            if (vpnConfig.allowPortBypass && vpnConfig.fwmark > 0) {
                Log.i("Vpn", "Clean port bypass rules for Secure Wi-Fi");
                VpnConfig vpnConfig2 = this.mSwifiConfig;
                this.mVpnRules.setTcpPortBypassRule(vpnConfig2.dport, vpnConfig2.fwmark, vpnConfig2.priority, vpnConfig2.netTableId, vpnConfig2.netIfaceName, vpnConfig2.netIfaceAddress, false);
            }
            this.mSwifiConfig = null;
        }
    }

    public final void cleanupVpnStateLocked() {
        this.mStatusIntent = null;
        this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).setTransportInfo(new VpnTransportInfo(-1, (String) null, false, false)).build();
        this.mConfig = null;
        this.mInterface = null;
        this.mVpnRunner = null;
        this.mConnection = null;
        agentDisconnect$1();
    }

    public final Notification createNotification(PendingIntent pendingIntent, boolean z) {
        String string = this.mContext.getString(17043472);
        String str = this.mConfig.session;
        Notification build = new Notification.Builder(this.mContext, SystemNotificationChannels.VPN).setSmallIcon(17304844).setLargeIcon((Bitmap) null).setContentTitle(string).setContentText(str == null ? this.mContext.getString(17043470) : this.mContext.getString(17043471, str)).setContentIntent(pendingIntent).setDefaults(0).setOngoing(z).build();
        build.flags |= 98;
        return build;
    }

    public Set createUserAndRestrictedProfilesRanges(int i, List list, List list2) {
        ArraySet arraySet = new ArraySet();
        addUserToRanges(arraySet, i, list, list2);
        if (SemDualAppManager.getDualAppProfileId() != -10000 && CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(list2) && i == 0) {
            Log.d("Vpn", "Add uid on dualAppProfile");
            addUserToRanges(arraySet, SemDualAppManager.getDualAppProfileId(), list, list2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (((UserManager) this.mContext.createContextAsUser(UserHandle.of(i), 0).getSystemService(UserManager.class)).canHaveRestrictedProfile()) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List<UserInfo> aliveUsers = this.mUserManager.getAliveUsers();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    for (UserInfo userInfo : aliveUsers) {
                        if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == i) {
                            addUserToRanges(arraySet, userInfo.id, list, list2);
                        }
                    }
                } finally {
                }
            }
            return arraySet;
        } finally {
        }
    }

    public final synchronized void deleteVpnProfile(String str) {
        Objects.requireNonNull(str, "No package name provided");
        verifyCallingUidAndPackage(str);
        enforceNotRestrictedUser();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isCurrentIkev2VpnLocked(str)) {
                if (this.mAlwaysOn) {
                    setAlwaysOnPackage(null, null, false);
                } else {
                    prepareInternal("[Legacy VPN]");
                }
            }
            getVpnProfileStore().remove(getProfileNameForPackage(str));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
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

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this) {
            try {
                indentingPrintWriter.println("Active package name: " + this.mPackage);
                indentingPrintWriter.println("Active vpn type: " + getActiveVpnType());
                indentingPrintWriter.println("NetworkCapabilities: " + this.mNetworkCapabilities);
                VpnRunner vpnRunner = this.mVpnRunner;
                if (vpnRunner instanceof IkeV2VpnRunner) {
                    IkeV2VpnRunner ikeV2VpnRunner = (IkeV2VpnRunner) vpnRunner;
                    indentingPrintWriter.println("SessionKey: " + ikeV2VpnRunner.mSessionKey);
                    indentingPrintWriter.println("MOBIKE ".concat(ikeV2VpnRunner.mMobikeEnabled ? "enabled" : "disabled"));
                    indentingPrintWriter.println("Profile: " + ikeV2VpnRunner.mProfile);
                    indentingPrintWriter.println("Token: " + ikeV2VpnRunner.mCurrentToken);
                    indentingPrintWriter.println("Validation failed retry count:" + ikeV2VpnRunner.mValidationFailRetryCount);
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enforceControlPermission() {
        if (isSecureWifiCalling()) {
            Log.i("Vpn", "enforceControlPermission for fast");
        } else {
            this.mContext.enforceCallingPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
        }
    }

    public final void enforceControlPermissionOrInternalCaller() {
        if (isSecureWifiCalling()) {
            Log.i("Vpn", "enforceControlPermissionOrInternalCaller for fast");
        } else {
            this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
        }
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

    public final synchronized ParcelFileDescriptor establish(VpnConfig vpnConfig) {
        try {
            if (Binder.getCallingUid() != this.mOwnerUID) {
                return null;
            }
            if (!doesPackageHaveAppop(this.mContext, this.mPackage, "android:activate_vpn")) {
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
                Dependencies dependencies = this.mDeps;
                int i = vpnConfig.mtu;
                dependencies.getClass();
                ParcelFileDescriptor adoptFd = ParcelFileDescriptor.adoptFd(isSecureWifiPackage() ? jniCreateSecureWifi(i) : jniCreate(i));
                try {
                    Dependencies dependencies2 = this.mDeps;
                    int fd = adoptFd.getFd();
                    dependencies2.getClass();
                    String jniGetName = jniGetName(fd);
                    StringBuilder sb = new StringBuilder();
                    for (LinkAddress linkAddress : vpnConfig.addresses) {
                        sb.append(" ");
                        sb.append(linkAddress);
                    }
                    Dependencies dependencies3 = this.mDeps;
                    String sb2 = sb.toString();
                    dependencies3.getClass();
                    if (jniSetAddresses(jniGetName, sb2) < 1) {
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
                    if (vpnConfig2 == null || !updateLinkPropertiesInPlaceIfPossible(this.mNetworkAgent, vpnConfig2)) {
                        startNewNetworkAgent(networkAgent, "establish");
                    } else if (!Arrays.equals(vpnConfig2.underlyingNetworks, vpnConfig.underlyingNetworks)) {
                        setUnderlyingNetworks(vpnConfig.underlyingNetworks);
                    }
                    if (connection != null) {
                        this.mContext.unbindService(connection);
                    }
                    if (str != null && !str.equals(jniGetName)) {
                        jniReset(str);
                    }
                    Dependencies dependencies4 = this.mDeps;
                    FileDescriptor fileDescriptor = adoptFd.getFileDescriptor();
                    boolean z = vpnConfig.blocking;
                    dependencies4.getClass();
                    Dependencies.setBlocking(fileDescriptor, z);
                    if (networkAgent != this.mNetworkAgent) {
                        this.mAppOpsManager.startOp("android:establish_vpn_service", this.mOwnerUID, this.mPackage, null, null);
                    }
                    Log.i("Vpn", "Established by " + vpnConfig.user + " on " + this.mInterface);
                    return adoptFd;
                } catch (RuntimeException e) {
                    IoUtils.closeQuietly(adoptFd);
                    if (networkAgent != this.mNetworkAgent) {
                        agentDisconnect$1();
                    }
                    this.mConfig = vpnConfig2;
                    this.mConnection = connection;
                    this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    this.mNetworkAgent = networkAgent;
                    this.mInterface = str;
                    throw e;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized int getActiveVpnType() {
        if (!this.mNetworkInfo.isConnectedOrConnecting()) {
            return -1;
        }
        if (isSecureWifiPackage()) {
            return 4;
        }
        VpnRunner vpnRunner = this.mVpnRunner;
        if (vpnRunner == null) {
            return 1;
        }
        return vpnRunner instanceof IkeV2VpnRunner ? 2 : 3;
    }

    public final synchronized boolean getAlwaysOn() {
        return this.mAlwaysOn;
    }

    public final synchronized String getAlwaysOnPackage() {
        enforceControlPermissionOrInternalCaller();
        return this.mAlwaysOn ? this.mPackage : null;
    }

    /* JADX WARN: Not initialized variable reg: 0, insn: 0x0078: INVOKE (r0 I:long) STATIC call: android.os.Binder.restoreCallingIdentity(long):void A[Catch: all -> 0x0056, MD:(long):void (c), TRY_ENTER], block:B:30:0x0078 */
    public final synchronized List getAppExclusionList(String str) {
        long restoreCallingIdentity;
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                byte[] bArr = getVpnProfileStore().get(getVpnAppExcludedForPackage(str));
                if (bArr != null && bArr.length != 0) {
                    PersistableBundle readFromStream = PersistableBundle.readFromStream(new ByteArrayInputStream(bArr));
                    int i = readFromStream.getInt("COLLECTION_LENGTH");
                    ArrayList arrayList = new ArrayList(i);
                    for (int i2 = 0; i2 < i; i2++) {
                        PersistableBundle persistableBundle = readFromStream.getPersistableBundle(String.format("LIST_ITEM_%d", Integer.valueOf(i2)));
                        Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
                        arrayList.add(persistableBundle.getString("STRING_KEY"));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return arrayList;
                }
                ArrayList arrayList2 = new ArrayList();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return arrayList2;
            } catch (IOException e) {
                Log.e("Vpn", "problem reading from stream", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return new ArrayList();
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(restoreCallingIdentity);
            throw th;
        }
    }

    public final SortedSet getAppsUids(int i, List list) {
        TreeSet treeSet = new TreeSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int appUid = getAppUid(this.mContext, (String) it.next(), i);
            if (appUid != -1) {
                treeSet.add(Integer.valueOf(appUid));
            }
            if (Process.isApplicationUid(appUid)) {
                treeSet.add(Integer.valueOf(Process.toSdkSandboxUid(appUid)));
            }
        }
        return treeSet;
    }

    public boolean getEnableTeardown() {
        return this.mEnableTeardown;
    }

    public final synchronized VpnConfig getLegacyVpnConfig() {
        if (!isSettingsVpnLocked()) {
            return null;
        }
        return this.mConfig;
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

    public final synchronized boolean getLockdown() {
        return this.mLockdown;
    }

    public final synchronized List getLockdownAllowlist() {
        return this.mLockdown ? this.mLockdownAllowlist : null;
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

    public final synchronized String getPackage() {
        return this.mPackage;
    }

    public String getProfileNameForPackage(String str) {
        return "PLATFORM_VPN_" + this.mUserId + "_" + str;
    }

    public final String getSessionKeyLocked() {
        VpnRunner vpnRunner = this.mVpnRunner;
        boolean z = vpnRunner instanceof IkeV2VpnRunner;
        String str = z ? ((IkeV2VpnRunner) vpnRunner).mSessionKey : null;
        Log.d("Vpn", "getSessionKeyLocked: isIkev2VpnRunner = " + z + ", sessionKey = " + str);
        return str;
    }

    public final synchronized UnderlyingNetworkInfo getUnderlyingNetworkInfo() {
        if (this.mNetworkAgent == null || this.mInterface == null) {
            return null;
        }
        return new UnderlyingNetworkInfo(this.mOwnerUID, this.mInterface, new ArrayList());
    }

    public String getVpnAppExcludedForPackage(String str) {
        return VPN_APP_EXCLUDED + this.mUserId + "_" + str;
    }

    public final synchronized VpnConfig getVpnConfig() {
        enforceControlPermission();
        if (this.mConfig == null) {
            return null;
        }
        return new VpnConfig(this.mConfig);
    }

    public VpnProfile getVpnProfilePrivileged(String str) {
        this.mDeps.getClass();
        if (Binder.getCallingUid() != 1000) {
            Log.wtf("Vpn", "getVpnProfilePrivileged called as non-System UID ");
            return null;
        }
        byte[] bArr = getVpnProfileStore().get(getProfileNameForPackage(str));
        if (bArr == null) {
            return null;
        }
        return VpnProfile.decode("", bArr);
    }

    public VpnProfileStore getVpnProfileStore() {
        return this.mVpnProfileStore;
    }

    public final boolean getknoxVpnTypeForStrongswanProfile() {
        boolean z = DBG;
        PackageManager packageManager = this.mContext.getPackageManager();
        boolean z2 = true;
        try {
            if (getKnoxVpnService() != null && this.mProfileName != null) {
                if (z) {
                    Log.d("Vpn", "getknoxVpnTypeForStrongswanProfile: profile name is " + this.mProfileName);
                }
                String vendorNameForProfile = getKnoxVpnService().getVendorNameForProfile(this.mProfileName);
                if (z) {
                    Log.d("Vpn", "getknoxVpnTypeForStrongswanProfile vendorOwningProfile value is " + vendorNameForProfile);
                }
                if (vendorNameForProfile != null) {
                    int packageUidAsUser = packageManager.getPackageUidAsUser("com.android.vpndialogs", this.mUserId);
                    if (vendorNameForProfile.equalsIgnoreCase("com.samsung.sVpn") || packageUidAsUser == UserHandle.getAppId(Binder.getCallingUid())) {
                        if (z) {
                            Log.d("Vpn", "Caller is either the strongswan proxy or the vpn dialog app");
                        }
                        int knoxVpnProfileType = getKnoxVpnService().getKnoxVpnProfileType(this.mProfileName);
                        if (knoxVpnProfileType != 0 && knoxVpnProfileType == 1) {
                            z2 = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (z) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfStrongswanProfileIsKnoxBased "), "Vpn");
            }
        }
        if (z) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("getknoxVpnTypeForStrongswanProfile profileType value is ", "Vpn", z2);
        }
        return z2;
    }

    public final boolean isAlwaysOnPackageSupported(String str) {
        ApplicationInfo applicationInfo;
        int i = this.mUserId;
        this.mContext.enforceCallingOrSelfPermission("android.permission.NETWORK_SETTINGS", "Unauthorized Caller");
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
                applicationInfo = packageManager.getApplicationInfoAsUser(str, 0, i);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("Vpn", "Can't find \"" + str + "\" when checking always-on support");
                applicationInfo = null;
            }
            if (applicationInfo != null && applicationInfo.targetSdkVersion >= 24) {
                Intent intent = new Intent("android.net.VpnService");
                intent.setPackage(str);
                List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, i);
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

    public boolean isCallerEstablishedOwnerLocked() {
        return (this.mNetworkAgent == null || this.mInterface == null || Binder.getCallingUid() != this.mOwnerUID) ? false : true;
    }

    public final boolean isCurrentIkev2VpnLocked(String str) {
        return isCurrentPreparedPackage(str) && (this.mVpnRunner instanceof IkeV2VpnRunner);
    }

    public final boolean isCurrentPreparedPackage(String str) {
        return getAppUid(this.mContext, str, this.mUserId) == this.mOwnerUID && this.mPackage.equals(str);
    }

    public final boolean isSecureWifiCalling() {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            String[] packagesForUid = packageManager.getPackagesForUid(Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0 && "com.samsung.android.fast".equals(packagesForUid[0])) {
                if (packageManager.checkSignatures("android", "com.samsung.android.fast") == 0) {
                    return true;
                }
                Log.e("Vpn", "isSecureWifiCalling: signature mismatched");
            }
        } catch (Exception unused) {
        }
        return false;
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

    public final boolean isSettingsVpnLocked() {
        return this.mVpnRunner != null && "[Legacy VPN]".equals(this.mPackage);
    }

    public final void logUnderlyNetworkChanges(List list) {
        LocalLog localLog = this.mEventChanges;
        StringBuilder sb = new StringBuilder("[UnderlyingNW] Switch to ");
        sb.append(list != null ? TextUtils.join(", ", list) : "null");
        localLog.log(sb.toString());
    }

    public final LinkProperties makeLinkProperties() {
        boolean z = (this.mVpnRunner instanceof IkeV2VpnRunner) && this.mConfig.mtu < 1280;
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

    public final VpnProfileState makeVpnProfileStateLocked() {
        int i = this.mLegacyState;
        int i2 = 0;
        if (i != 0) {
            if (i == 5) {
                i2 = 3;
            } else if (i == 2) {
                i2 = 1;
            } else if (i != 3) {
                Log.wtf("Vpn", "Unhandled state " + i + ", treat it as STATE_DISCONNECTED");
            } else {
                i2 = 2;
            }
        }
        return new VpnProfileState(i2, this.mVpnRunner instanceof IkeV2VpnRunner ? getSessionKeyLocked() : null, this.mAlwaysOn, this.mLockdown);
    }

    public final void onUserAdded(int i) {
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

    public final void onUserRemoved(int i) {
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

    public final synchronized void onUserStopped() {
        setVpnForcedLocked(false);
        this.mAlwaysOn = false;
        agentDisconnect$1();
        this.mConnectivityManager.unregisterNetworkProvider(this.mNetworkProvider);
    }

    public final synchronized boolean prepare(int i, String str, String str2) {
        boolean doesPackageHaveAppop;
        boolean doesPackageHaveAppop2;
        try {
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
                    if (!str.equals("[Legacy VPN]")) {
                        Context context = this.mContext;
                        if (i == 1) {
                            doesPackageHaveAppop2 = doesPackageHaveAppop(context, str, "android:activate_vpn");
                        } else if (i != 2) {
                            if (i == 3) {
                                doesPackageHaveAppop2 = "[Legacy VPN]".equals(str);
                            }
                            doesPackageHaveAppop2 = false;
                        } else {
                            if (doesPackageHaveAppop(context, str, "android:activate_platform_vpn") || doesPackageHaveAppop(context, str, "android:activate_vpn")) {
                                doesPackageHaveAppop2 = true;
                            }
                            doesPackageHaveAppop2 = false;
                        }
                        if (doesPackageHaveAppop2) {
                            prepareInternal(str);
                            return true;
                        }
                    }
                    return false;
                }
                if (!str.equals("[Legacy VPN]")) {
                    Context context2 = this.mContext;
                    if (i == 1) {
                        doesPackageHaveAppop = doesPackageHaveAppop(context2, str, "android:activate_vpn");
                    } else if (i != 2) {
                        if (i == 3) {
                            doesPackageHaveAppop = "[Legacy VPN]".equals(str);
                        }
                        doesPackageHaveAppop = false;
                    } else {
                        if (doesPackageHaveAppop(context2, str, "android:activate_platform_vpn") || doesPackageHaveAppop(context2, str, "android:activate_vpn")) {
                            doesPackageHaveAppop = true;
                        }
                        doesPackageHaveAppop = false;
                    }
                    if (!doesPackageHaveAppop) {
                        prepareInternal("[Legacy VPN]");
                        return false;
                    }
                }
            }
            if (str2 != null && (str2.equals("[Legacy VPN]") || !isCurrentPreparedPackage(str2))) {
                if (str == null) {
                    enforceControlPermissionOrInternalCaller();
                } else if (!str.equalsIgnoreCase("[Legacy VPN]") || !str2.equalsIgnoreCase("[Legacy VPN]")) {
                    enforceControlPermission();
                } else if (Binder.getCallingUid() == 1000 && Binder.getCallingPid() == Process.myPid()) {
                    enforceControlPermissionOrInternalCaller();
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
        } finally {
        }
    }

    public final void prepareInternal(String str) {
        INetd iNetd = this.mNetd;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mInterface != null) {
                this.mStatusIntent = null;
                agentDisconnect$1();
                jniReset(this.mInterface);
                this.mInterface = null;
                this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).setTransportInfo(new VpnTransportInfo(-1, (String) null, false, false)).build();
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
                iNetd.networkSetProtectDeny(this.mOwnerUID);
            } catch (Exception e) {
                Log.wtf("Vpn", "Failed to disallow UID " + this.mOwnerUID + " to call protect() " + e);
            }
            Log.i("Vpn", "Switched from " + this.mPackage + " to " + str);
            this.mPackage = str;
            this.mOwnerUID = getAppUid(this.mContext, str, this.mUserId);
            this.mIsPackageTargetingAtLeastQ = doesPackageTargetAtLeastQ(str);
            try {
                iNetd.networkSetProtectAllow(this.mOwnerUID);
            } catch (Exception e2) {
                Log.wtf("Vpn", "Failed to allow UID " + this.mOwnerUID + " to call protect() " + e2);
            }
            this.mConfig = null;
            updateState(NetworkInfo.DetailedState.DISCONNECTED, "prepare");
            setVpnForcedLocked(this.mLockdown);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final synchronized boolean provisionVpnProfile(VpnProfile vpnProfile, String str) {
        Context context;
        try {
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
                context = this.mContext;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return doesPackageHaveAppop(context, str, "android:activate_platform_vpn") || doesPackageHaveAppop(context, str, "android:activate_vpn");
    }

    public final synchronized void refreshPlatformVpnAppExclusionList() {
        updateAppExclusionList(getAppExclusionList(this.mPackage));
    }

    /* JADX WARN: Type inference failed for: r2v16, types: [com.android.server.connectivity.Vpn$1] */
    public final void registerMockNetworkAgent() {
        NetworkCapabilities build = new NetworkCapabilities.Builder().addTransportType(9).addCapability(11).addCapability(28).setUnderlyingNetworks((List) null).build();
        NetworkAgentConfig build2 = new NetworkAgentConfig.Builder().setLegacyType(17).setLegacyTypeName("VPN").setBypassableVpn(true).setVpnRequiresValidation(false).setLocalRoutesExcludedForVpn(true).build();
        Dependencies dependencies = this.mDeps;
        Context context = this.mContext;
        Looper looper = this.mLooper;
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName("mock_" + this.mInterface);
        NetworkScore build3 = new NetworkScore.Builder().setLegacyInt(101).build();
        NetworkProvider networkProvider = this.mNetworkProvider;
        dependencies.getClass();
        this.mMockNetworkAgent = new VpnNetworkAgentWrapper(context, looper, build, linkProperties, build3, build2, networkProvider, null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mMockNetworkAgent.register();
                Log.i("Vpn", "register MockNetworkAgent success");
                this.mVpnNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.connectivity.Vpn.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        Log.i("Vpn", "mVpnNetworkCallback onAvailable");
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties2) {
                        Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged: " + linkProperties2.toString());
                        if (Vpn.this.mInterface.equals(linkProperties2.getInterfaceName())) {
                            Vpn.this.mConnectivityManager.unregisterNetworkCallback(this);
                            Vpn vpn = Vpn.this;
                            vpn.getClass();
                            LinkProperties linkProperties3 = new LinkProperties();
                            linkProperties3.setInterfaceName("mock_" + vpn.mInterface);
                            List<LinkAddress> list = Vpn.this.mConfig.addresses;
                            if (list != null) {
                                for (LinkAddress linkAddress : list) {
                                    linkProperties3.addLinkAddress(linkAddress);
                                    Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged addLinkAddress: " + linkAddress);
                                }
                            }
                            Vpn.this.mMockNetworkAgent.sendLinkProperties(linkProperties3);
                            Log.i("Vpn", "mVpnNetworkCallback onLinkPropertiesChanged sendLinkProperties");
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
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

    public final synchronized boolean removeAddress(int i, String str) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniDelAddress = jniDelAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniDelAddress;
    }

    public final void resetUidListInNetworkCapabilities() {
        applyBlockingRulesToUidRange(false);
        ((ArraySet) this.knoxVpnUidRanges).clear();
        NetworkCapabilities build = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).build();
        this.mNetworkCapabilities = build;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent != null) {
            networkAgent.sendNetworkCapabilities(build);
        }
    }

    public final void saveAlwaysOnPackage() {
        int i = this.mUserId;
        SystemServices systemServices = this.mSystemServices;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putString(systemServices.getContentResolverAsUser(i), "always_on_vpn_app", getAlwaysOnPackage());
            Settings.Secure.putInt(systemServices.getContentResolverAsUser(i), "always_on_vpn_lockdown", (this.mAlwaysOn && this.mLockdown) ? 1 : 0);
            Settings.Secure.putString(systemServices.getContentResolverAsUser(i), "always_on_vpn_lockdown_whitelist", String.join(",", this.mLockdownAllowlist));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean sendEventToVpnManagerApp(Intent intent, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeps.getClass();
            ((DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class)).addPowerSaveTempWhitelistApp(Process.myUid(), str, 30000L, this.mUserId, false, 309, "VpnManager event");
            return this.mUserIdContext.startService(intent) != null;
        } catch (RuntimeException e) {
            Log.e("Vpn", "Service of VpnManager app " + intent + " failed to start", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendEventToVpnManagerApp(String str, int i, int i2, String str2, String str3, VpnProfileState vpnProfileState, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        LocalLog localLog = this.mEventChanges;
        StringBuilder sb = new StringBuilder("[VMEvent] Event class=");
        sb.append(i != 1 ? i != 2 ? "UNKNOWN_CLASS" : "ERROR_CLASS_RECOVERABLE" : "ERROR_CLASS_NOT_RECOVERABLE");
        sb.append(", err=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "UNKNOWN_ERROR" : "ERROR_CODE_NETWORK_IO" : "ERROR_CODE_NETWORK_LOST" : "ERROR_CODE_NETWORK_PROTOCOL_TIMEOUT" : "ERROR_CODE_NETWORK_UNKNOWN_HOST", " for ", str2, " on session ");
        sb.append(str3);
        localLog.log(sb.toString());
        return sendEventToVpnManagerApp(buildVpnManagerEventIntent(str, i, i2, str2, str3, vpnProfileState, network, networkCapabilities, linkProperties), str2);
    }

    public final void setAllowOnlyVpnForUids(boolean z, Collection collection) {
        if (collection.size() == 0) {
            return;
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
                ((ArraySet) this.mBlockedUidsAsToldToConnectivity).addAll(collection);
            } else {
                ((ArraySet) this.mBlockedUidsAsToldToConnectivity).removeAll((Collection<?>) collection);
            }
        } catch (RuntimeException e) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Updating blocked=", " for UIDs ", z);
            m.append(Arrays.toString(collection.toArray()));
            m.append(" failed");
            Log.e("Vpn", m.toString(), e);
        }
    }

    public final synchronized boolean setAlwaysOnPackage(String str, List list, boolean z) {
        try {
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
                list.add("com.samsung.android.kgclient");
            }
            if (!setAlwaysOnPackageInternal(str, list, z)) {
                return false;
            }
            saveAlwaysOnPackage();
            if (z3) {
                sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str2, null, z2 ? new VpnProfileState(0, null, false, false) : makeVpnProfileStateLocked(), null, null, null);
            }
            if (z4) {
                sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null);
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean setAlwaysOnPackageInternal(String str, List list, boolean z) {
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
                    Log.w("Vpn", "Not setting always-on vpn, invalid allowed package: ".concat(str2));
                    return false;
                }
            }
        }
        if (str != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!setPackageAuthorization(getVpnProfilePrivileged(str) == null ? 1 : 2, str)) {
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
        this.mLockdownAllowlist = (!z2 || list == null) ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(list));
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

    public final synchronized void setLockdown(boolean z) {
        enforceControlPermissionOrInternalCaller();
        setVpnForcedLocked(z);
        this.mLockdown = z;
        if (this.mAlwaysOn) {
            saveAlwaysOnPackage();
        }
    }

    public final boolean setPackageAuthorization(int i, String str) {
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

    public final synchronized boolean setUnderlyingNetworks(Network[] networkArr) {
        try {
            if (!isCallerEstablishedOwnerLocked()) {
                return false;
            }
            this.mConfig.underlyingNetworks = networkArr != null ? (Network[]) Arrays.copyOf(networkArr, networkArr.length) : null;
            NetworkAgent networkAgent = this.mNetworkAgent;
            Network[] networkArr2 = this.mConfig.underlyingNetworks;
            List asList = networkArr2 != null ? Arrays.asList(networkArr2) : null;
            logUnderlyNetworkChanges(asList);
            if (networkAgent instanceof VpnNetworkAgentWrapper) {
                ((VpnNetworkAgentWrapper) networkAgent).setUnderlyingNetworks(asList);
            } else {
                networkAgent.setUnderlyingNetworks(asList);
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void setVpnForcedLocked(boolean z) {
        ArrayList arrayList;
        Collection collection;
        String str = this.mPackage;
        if (str == null || "[Legacy VPN]".equals(str)) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.mLockdownAllowlist);
            arrayList.add(this.mPackage);
        }
        ArraySet arraySet = new ArraySet(this.mBlockedUidsAsToldToConnectivity);
        if (z) {
            Set<Range> createUserAndRestrictedProfilesRanges = createUserAndRestrictedProfilesRanges(this.mUserId, null, arrayList);
            ArraySet arraySet2 = new ArraySet();
            for (Range range : createUserAndRestrictedProfilesRanges) {
                if (((Integer) range.getLower()).intValue() != 0 || ((Integer) range.getUpper()).intValue() == 0) {
                    if (((Integer) range.getLower()).intValue() != 0) {
                        arraySet2.add(new UidRangeParcel(((Integer) range.getLower()).intValue(), ((Integer) range.getUpper()).intValue()));
                    }
                } else if (MdfUtils.isMdfEnforced()) {
                    arraySet2.add(new UidRangeParcel(1, 1015));
                    arraySet2.add(new UidRangeParcel(1017, ((Integer) range.getUpper()).intValue()));
                } else {
                    arraySet2.add(new UidRangeParcel(1, ((Integer) range.getUpper()).intValue()));
                }
            }
            arraySet.removeAll((Collection<?>) arraySet2);
            arraySet2.removeAll(this.mBlockedUidsAsToldToConnectivity);
            collection = arraySet2;
        } else {
            collection = Collections.emptySet();
        }
        setAllowOnlyVpnForUids(false, arraySet);
        setAllowOnlyVpnForUids(true, collection);
    }

    public final void showNotificationForKnoxStrongSwan(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mStatusIntent = VpnConfig.getIntentForStatusPanelAsUser(this.mContext, i);
                NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                if (notificationManager != null) {
                    boolean z = getKnoxVpnService().getNotificationDismissibleFlagInternal(i) != 0;
                    Notification createNotification = createNotification(this.mStatusIntent, z);
                    if (z) {
                        notificationManager.notifyAsUser("Knox_SS_Notification", 100000, createNotification, new UserHandle(i));
                    } else {
                        notificationManager.notifyAsUser(null, 17304844, createNotification, new UserHandle(i));
                    }
                }
            } catch (Exception e) {
                Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean startAlwaysOnVpn() {
        synchronized (this) {
            try {
                String alwaysOnPackage = getAlwaysOnPackage();
                if (alwaysOnPackage == null) {
                    return true;
                }
                if (!isAlwaysOnPackageSupported(alwaysOnPackage)) {
                    setAlwaysOnPackage(null, null, false);
                    return false;
                }
                if (this.mNetworkInfo.isConnected()) {
                    return true;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(alwaysOnPackage);
                    if (vpnProfilePrivileged != null) {
                        startVpnProfilePrivileged(vpnProfilePrivileged, alwaysOnPackage);
                        return true;
                    }
                    this.mDeps.getClass();
                    ((DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class)).addPowerSaveTempWhitelistApp(Process.myUid(), alwaysOnPackage, 60000L, this.mUserId, false, 309, "vpn");
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startLegacyVpn(VpnProfile vpnProfile, LinkProperties linkProperties) {
        enforceControlPermission();
        if (linkProperties == null) {
            ConnectivityManager connectivityManager = this.mConnectivityManager;
            linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
            if (linkProperties == null) {
                throw new IllegalStateException("Missing active network connection");
            }
        }
        String interfaceName = linkProperties.getInterfaceName();
        if (isSettingsVpnLocked() && interfaceName != null && (interfaceName.startsWith(INetd.IPSEC_INTERFACE_PREFIX) || interfaceName.startsWith("tun"))) {
            stopVpnRunnerPrivileged();
            ConnectivityManager connectivityManager2 = this.mConnectivityManager;
            linkProperties = connectivityManager2.getLinkProperties(connectivityManager2.getActiveNetwork());
            if (linkProperties == null) {
                throw new IllegalStateException("Missing active network connection");
            }
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("startLegacyVpn changed egress: ", interfaceName, " > ");
            m.append(linkProperties.getInterfaceName());
            Log.d("Vpn", m.toString());
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            startLegacyVpnPrivileged(vpnProfile, null, linkProperties);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startLegacyVpnPrivileged(VpnProfile vpnProfile, Network network, LinkProperties linkProperties) {
        VpnProfile clone = vpnProfile.clone();
        if (this.mUserManager.getUserInfo(this.mUserId).isRestricted() || this.mUserManager.hasUserRestriction("no_config_vpn", new UserHandle(this.mUserId))) {
            throw new SecurityException("Restricted users cannot establish VPNs");
        }
        String str = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        boolean z = SystemProperties.getBoolean("security.ucmcrypto", false) || SystemProperties.getBoolean("persist.security.ucmcrypto", false);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("startLegacyVpnPrivileged: isUcmEnabled=", "Vpn", z);
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            String[] strArr = null;
            keyStore.load(null);
            if (!clone.ipsecUserCert.isEmpty()) {
                str = clone.ipsecUserCert;
                Certificate certificate = keyStore.getCertificate(str);
                str2 = certificate == null ? null : new String(Credentials.convertToPem(new Certificate[]{certificate}), StandardCharsets.UTF_8);
                if (str2 == null && z) {
                    str2 = getUcmCertificate("USRCERT_", clone.ipsecUserCert);
                } else {
                    str = makeKeystoreEngineGrantString(str);
                }
            }
            String str6 = str;
            String str7 = str2;
            if (!clone.ipsecCaCert.isEmpty()) {
                if (this.mUserId <= 0 || clone.ipsecCacertValue.isEmpty()) {
                    str3 = getCaCertificateFromKeystoreAsPem(keyStore, clone.ipsecCaCert);
                    if (str3 == null && z) {
                        str3 = getUcmCertificate("CACERT_", clone.ipsecCaCert);
                    }
                } else {
                    str3 = clone.ipsecCacertValue;
                }
            }
            String str8 = str3;
            if (!clone.ipsecServerCert.isEmpty()) {
                if (this.mUserId <= 0 || clone.ipsecServerCertValue.isEmpty()) {
                    Certificate certificate2 = keyStore.getCertificate(clone.ipsecServerCert);
                    str4 = certificate2 == null ? null : new String(Credentials.convertToPem(new Certificate[]{certificate2}), StandardCharsets.UTF_8);
                    if (str4 == null && z) {
                        str4 = getUcmCertificate("USRCERT_", clone.ipsecServerCert);
                    }
                } else {
                    str4 = clone.ipsecServerCertValue;
                }
            }
            String str9 = str4;
            if (!clone.ipsecCaCert.isEmpty()) {
                if (this.mUserId <= 0 || clone.allCert.isEmpty()) {
                    X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(clone.ipsecCaCert);
                    try {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        buildCertificatePathFromRoot(x509Certificate, keyStore, arrayList2);
                        String str10 = clone.ipsecServerCert;
                        boolean z2 = true;
                        if (!str10.isEmpty()) {
                            try {
                                X509Certificate x509Certificate2 = (X509Certificate) keyStore.getCertificate(str10);
                                if (x509Certificate2 != null && !arrayList2.isEmpty()) {
                                    Iterator it = arrayList2.iterator();
                                    while (it.hasNext()) {
                                        if (x509Certificate2.equals((X509Certificate) it.next())) {
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            z2 = false;
                        }
                        if (z2 && !arrayList2.isEmpty()) {
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                X509Certificate x509Certificate3 = (X509Certificate) it2.next();
                                Log.d("Vpn", "allCaCerts= " + String.valueOf(x509Certificate3.getSubjectDN()));
                                arrayList.add(new String(Credentials.convertToPem(new Certificate[]{x509Certificate3}), StandardCharsets.UTF_8));
                            }
                            str5 = String.join(",", arrayList);
                        }
                    } catch (Exception unused) {
                    }
                } else {
                    str5 = clone.allCert;
                }
            }
            String str11 = str5;
            if (str7 == null || str8 == null || str9 == null) {
                throw new IllegalStateException("Cannot load credentials");
            }
            this.mEgressIface = linkProperties.getInterfaceName();
            Log.d("Vpn", "Egress Iface (" + this.mEgressIface + ") activated");
            VpnProfile.decrypt(clone);
            if (!MdfUtils.isMdfEnforced()) {
                clone.ocspServerUrl = "";
                clone.searchDomains = "";
                clone.dnsServers = "";
                clone.routes = "";
            }
            switch (clone.type) {
                case 6:
                    strArr = new String[]{clone.server, "ikev2eap", clone.ipsecIdentifier, str8, clone.username, clone.password, MdfUtils.isMdfEnforced() ? "Enforcing" : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG, clone.isPFS ? "+pfs" : "nonpfs", ""};
                    break;
                case 7:
                    strArr = new String[]{clone.server, "ikev2psk", clone.ipsecIdentifier, clone.ipsecSecret, MdfUtils.isMdfEnforced() ? "Enforcing" : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG, "nonpfs", ""};
                    break;
                case 8:
                    strArr = new String[]{clone.server, "ikev2rsa", clone.ipsecIdentifier, clone.ipsecRemoteIdentifier, str6, str7, str8, str9, str11, clone.ocspServerUrl, MdfUtils.isMdfEnforced() ? "Enforcing" : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG, clone.isPFS ? "+pfs" : "nonpfs", ""};
                    break;
                case 9:
                    strArr = new String[]{clone.server, "ikev2eaptls", clone.ipsecIdentifier, clone.ipsecRemoteIdentifier, str6, str7, str8, str9, str11, clone.ocspServerUrl, MdfUtils.isMdfEnforced() ? "Enforcing" : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG, clone.isPFS ? "+pfs" : "nonpfs", ""};
                    break;
                case 10:
                    startVpnProfilePrivileged(clone, "[Legacy VPN]");
                    return;
            }
            VpnConfig vpnConfig = new VpnConfig();
            vpnConfig.legacy = true;
            vpnConfig.user = clone.key;
            vpnConfig.interfaze = this.mEgressIface;
            String str12 = clone.name;
            vpnConfig.session = str12;
            vpnConfig.isMetered = false;
            vpnConfig.proxyInfo = clone.proxy;
            if (network != null) {
                vpnConfig.underlyingNetworks = new Network[]{network};
            }
            this.mProfileName = str12;
            vpnConfig.addLegacyRoutes(clone.routes);
            if (!clone.dnsServers.isEmpty()) {
                vpnConfig.dnsServers = Arrays.asList(clone.dnsServers.split(" +"));
            }
            if (!clone.searchDomains.isEmpty()) {
                vpnConfig.searchDomains = Arrays.asList(clone.searchDomains.split(" +"));
            }
            synchronized (this) {
                stopVpnRunnerPrivileged();
                prepareInternal("[Legacy VPN]");
                updateState(NetworkInfo.DetailedState.CONNECTING, "startLegacyVpn");
                this.mVpnRunner = new LegacyVpnRunner(vpnConfig, strArr, clone);
                startLegacyVpnRunner();
            }
        } catch (IOException | java.security.KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
            throw new IllegalStateException("Failed to load credentials from AndroidKeyStore", e2);
        }
    }

    public void startLegacyVpnRunner() {
        this.mVpnRunner.start();
    }

    public final void startNewNetworkAgent(NetworkAgent networkAgent, String str) {
        this.mNetworkAgent = null;
        updateState(NetworkInfo.DetailedState.CONNECTING, str);
        agentConnect(null);
        if (networkAgent != null) {
            networkAgent.unregister();
        }
    }

    public final synchronized String startVpnProfile(String str) {
        Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        if (!prepare(2, str, null)) {
            throw new SecurityException("User consent not granted for package ".concat(str));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(str);
            if (vpnProfilePrivileged == null) {
                throw new IllegalArgumentException("No profile found for ".concat(str));
            }
            startVpnProfilePrivileged(vpnProfilePrivileged, str);
            if (!(this.mVpnRunner instanceof IkeV2VpnRunner)) {
                throw new IllegalStateException("mVpnRunner shouldn't be null and should also be an instance of Ikev2VpnRunner");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return getSessionKeyLocked();
    }

    public final synchronized void startVpnProfilePrivileged(VpnProfile vpnProfile, String str) {
        try {
            prepareInternal(str);
            updateState(NetworkInfo.DetailedState.CONNECTING, "startPlatformVpn");
            try {
                VpnConfig vpnConfig = new VpnConfig();
                if ("[Legacy VPN]".equals(str)) {
                    vpnConfig.legacy = true;
                    vpnConfig.session = vpnProfile.name;
                    vpnConfig.user = vpnProfile.key;
                    vpnConfig.isMetered = true;
                } else {
                    vpnConfig.user = str;
                    vpnConfig.isMetered = vpnProfile.isMetered;
                }
                vpnConfig.startTime = SystemClock.elapsedRealtime();
                vpnConfig.proxyInfo = vpnProfile.proxy;
                vpnConfig.requiresInternetValidation = vpnProfile.requiresInternetValidation;
                vpnConfig.excludeLocalRoutes = vpnProfile.excludeLocalRoutes;
                vpnConfig.allowBypass = vpnProfile.isBypassable;
                vpnConfig.disallowedApplications = getAppExclusionList(this.mPackage);
                this.mConfig = vpnConfig;
                int i = vpnProfile.type;
                if (i == 6 || i == 7 || i == 8 || i == 10) {
                    Ikev2VpnProfile fromVpnProfile = Ikev2VpnProfile.fromVpnProfile(vpnProfile);
                    this.mDeps.getClass();
                    IkeV2VpnRunner ikeV2VpnRunner = new IkeV2VpnRunner(fromVpnProfile, new ScheduledThreadPoolExecutor(1));
                    this.mVpnRunner = ikeV2VpnRunner;
                    ikeV2VpnRunner.start();
                } else {
                    this.mConfig = null;
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
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void stopVpnRunnerAndNotifyAppLocked() {
        int i = this.mOwnerUID;
        Intent buildVpnManagerEventIntent = isVpnApp(this.mPackage) ? buildVpnManagerEventIntent("android.net.category.EVENT_DEACTIVATED_BY_USER", -1, -1, this.mPackage, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null) : null;
        this.mVpnRunner.exit();
        if (buildVpnManagerEventIntent == null || !isVpnApp(this.mPackage)) {
            return;
        }
        String str = this.mPackage;
        synchronized (this) {
            this.mAppOpsManager.finishOp("android:establish_vpn_manager", i, str, null);
            this.mEventChanges.log("[VMEvent] " + str + " stopped");
            sendEventToVpnManagerApp(buildVpnManagerEventIntent, str);
        }
    }

    public final synchronized void stopVpnRunnerPrivileged() {
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

    public final boolean storeAppExclusionList(String str, List list) {
        try {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt("COLLECTION_LENGTH", list.size());
            for (int i = 0; i < list.size(); i++) {
                String format = String.format("LIST_ITEM_%d", Integer.valueOf(i));
                String str2 = (String) list.get(i);
                PersistableBundle persistableBundle2 = new PersistableBundle();
                persistableBundle2.putString("STRING_KEY", str2);
                persistableBundle.putPersistableBundle(format, persistableBundle2);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            persistableBundle.writeToStream(byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                getVpnProfileStore().put(getVpnAppExcludedForPackage(str), byteArray);
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

    public final void unregisterMockNetworkAgent() {
        VpnNetworkAgentWrapper vpnNetworkAgentWrapper = this.mMockNetworkAgent;
        if (vpnNetworkAgentWrapper != null) {
            vpnNetworkAgentWrapper.unregister();
            this.mMockNetworkAgent = null;
        }
        AnonymousClass1 anonymousClass1 = this.mVpnNetworkCallback;
        if (anonymousClass1 != null) {
            this.mConnectivityManager.unregisterNetworkCallback(anonymousClass1);
            this.mVpnNetworkCallback = null;
        }
        Log.i("Vpn", "unregisterMockNetworkAgent");
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
            intent.setComponent(ComponentName.unflattenFromString(this.mContext.getString(R.string.data_usage_rapid_body)));
            intent.putExtra("lockdown", this.mLockdown);
            intent.addFlags(268435456);
            notificationManager.notify("Vpn", 17, new Notification.Builder(this.mContext, "VPN").setSmallIcon(17304844).setContentTitle(this.mContext.getString(17043468)).setContentText(this.mContext.getString(17043465)).setContentIntent(PendingIntent.getActivity(this.mSystemServices.mContext.createContextAsUser(of, 0), 0, intent, 201326592)).setCategory("sys").setVisibility(1).setOngoing(true).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).build());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void updateAppExclusionList(List list) {
        if (this.mNetworkAgent != null && (this.mVpnRunner instanceof IkeV2VpnRunner)) {
            this.mConfig.disallowedApplications = List.copyOf(list);
            this.mNetworkCapabilities = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(createUserAndRestrictedProfilesRanges(this.mUserId, null, list)).build();
            String sessionKeyLocked = getSessionKeyLocked();
            int i = this.mUserId;
            VpnConfig vpnConfig = this.mConfig;
            BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda0(this, sessionKeyLocked, createUserAndRestrictedProfilesRanges(i, vpnConfig.allowedApplications, vpnConfig.disallowedApplications)));
            doSendNetworkCapabilities(this.mNetworkAgent, this.mNetworkCapabilities);
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

    public final void updateLocalProxyInfo(ProxyInfo proxyInfo) {
        this.mHttpProxyInfo = proxyInfo;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent == null || this.mInterface == null) {
            return;
        }
        networkAgent.sendLinkProperties(makeLinkProperties());
    }

    public final void updateNotificationIconForKnoxStrongSwan(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (!getknoxVpnTypeForStrongswanProfile()) {
                    this.mStatusIntent = VpnConfig.getIntentForStatusPanelAsUser(this.mContext, i);
                    NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                    if (notificationManager != null) {
                        boolean z = getKnoxVpnService().getNotificationDismissibleFlagInternal(i) != 0;
                        Notification createNotification = createNotification(this.mStatusIntent, z);
                        if (z) {
                            notificationManager.cancelAsUser(null, 17304844, new UserHandle(i));
                            notificationManager.notifyAsUser("Knox_SS_Notification", 100000, createNotification, new UserHandle(i));
                        } else {
                            notificationManager.cancelAsUser("Knox_SS_Notification", 100000, new UserHandle(i));
                            notificationManager.notifyAsUser(null, 17304844, createNotification, new UserHandle(i));
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Vpn", "Exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0087, code lost:
    
        r4[2] = r6.getHostAddress().toUpperCase();
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePortBypassConfigs() {
        /*
            r8 = this;
            java.lang.String r0 = "Vpn"
            com.android.internal.net.VpnConfig r1 = r8.mConfig
            r2 = 0
            r1.allowPortBypass = r2
            java.lang.String r1 = r1.session
            r3 = 1
            if (r1 == 0) goto L2c
            java.lang.String r4 = "_"
            java.lang.String[] r1 = r1.split(r4)
            if (r1 == 0) goto L2c
            int r4 = r1.length
            if (r4 <= r3) goto L2c
            com.android.internal.net.VpnConfig r4 = r8.mConfig     // Catch: java.lang.NumberFormatException -> L2c
            r5 = r1[r2]     // Catch: java.lang.NumberFormatException -> L2c
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> L2c
            if (r5 == 0) goto L23
            r5 = r3
            goto L24
        L23:
            r5 = r2
        L24:
            r4.allowPortBypass = r5     // Catch: java.lang.NumberFormatException -> L2c
            com.android.internal.net.VpnConfig r4 = r8.mConfig     // Catch: java.lang.NumberFormatException -> L2c
            r1 = r1[r3]     // Catch: java.lang.NumberFormatException -> L2c
            r4.session = r1     // Catch: java.lang.NumberFormatException -> L2c
        L2c:
            com.android.internal.net.VpnConfig r1 = r8.mConfig
            boolean r1 = r1.allowPortBypass
            if (r1 == 0) goto Lcd
            r1 = 2
            java.util.Enumeration r4 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch: java.lang.Exception -> L92
            if (r4 == 0) goto L98
        L39:
            boolean r5 = r4.hasMoreElements()     // Catch: java.lang.Exception -> L92
            if (r5 == 0) goto L98
            java.lang.Object r5 = r4.nextElement()     // Catch: java.lang.Exception -> L92
            java.net.NetworkInterface r5 = (java.net.NetworkInterface) r5     // Catch: java.lang.Exception -> L92
            java.lang.String r6 = r5.getName()     // Catch: java.lang.Exception -> L92
            java.lang.String r7 = "wlan"
            boolean r6 = r6.startsWith(r7)     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L39
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L92
            int r6 = r5.getIndex()     // Catch: java.lang.Exception -> L92
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L92
            r4[r2] = r6     // Catch: java.lang.Exception -> L92
            java.lang.String r6 = r5.getName()     // Catch: java.lang.Exception -> L92
            r4[r3] = r6     // Catch: java.lang.Exception -> L92
            java.util.List r5 = r5.getInterfaceAddresses()     // Catch: java.lang.Exception -> L92
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Exception -> L92
        L6d:
            boolean r6 = r5.hasNext()     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L99
            java.lang.Object r6 = r5.next()     // Catch: java.lang.Exception -> L92
            java.net.InterfaceAddress r6 = (java.net.InterfaceAddress) r6     // Catch: java.lang.Exception -> L92
            java.net.InetAddress r6 = r6.getAddress()     // Catch: java.lang.Exception -> L92
            boolean r7 = r6.isLoopbackAddress()     // Catch: java.lang.Exception -> L92
            if (r7 != 0) goto L6d
            boolean r7 = r6 instanceof java.net.Inet4Address     // Catch: java.lang.Exception -> L92
            if (r7 == 0) goto L6d
            java.lang.String r5 = r6.getHostAddress()     // Catch: java.lang.Exception -> L92
            java.lang.String r5 = r5.toUpperCase()     // Catch: java.lang.Exception -> L92
            r4[r1] = r5     // Catch: java.lang.Exception -> L92
            goto L99
        L92:
            r4 = move-exception
            java.lang.String r5 = "Failed to get wifi interface info: "
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r4, r5, r0)
        L98:
            r4 = 0
        L99:
            if (r4 != 0) goto La5
            java.lang.String r1 = "Failed to update port bypass configs due to no wifi interface info"
            android.util.Log.e(r0, r1)
            com.android.internal.net.VpnConfig r8 = r8.mConfig
            r8.allowPortBypass = r2
            return
        La5:
            com.android.internal.net.VpnConfig r0 = r8.mConfig
            r5 = 443(0x1bb, float:6.21E-43)
            r0.dport = r5
            r5 = 1546(0x60a, float:2.166E-42)
            r0.fwmark = r5
            r5 = 11500(0x2cec, float:1.6115E-41)
            r0.priority = r5
            r2 = r4[r2]
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            int r2 = r2 + 1000
            r0.netTableId = r2
            com.android.internal.net.VpnConfig r8 = r8.mConfig
            r0 = r4[r3]
            java.lang.String r0 = (java.lang.String) r0
            r8.netIfaceName = r0
            r0 = r4[r1]
            java.lang.String r0 = (java.lang.String) r0
            r8.netIfaceAddress = r0
        Lcd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.updatePortBypassConfigs():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x015f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(android.net.NetworkInfo.DetailedState r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.connectivity.Vpn.updateState(android.net.NetworkInfo$DetailedState, java.lang.String):void");
    }

    public final void updateUidListInNetworkCapabilities() {
        Iterator it = ((ArraySet) this.knoxVpnUidRanges).iterator();
        while (it.hasNext()) {
            Range range = (Range) it.next();
            StringBuilder sb = new StringBuilder("uidRange going to be updated is ");
            sb.append(range.getLower());
            sb.append(" ");
            sb.append(range.getUpper());
            sb.append(" for profile ");
            VpnManagerService$$ExternalSyntheticOutline0.m(sb, this.mProfileName, "Vpn");
        }
        NetworkCapabilities build = new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(this.knoxVpnUidRanges).build();
        this.mNetworkCapabilities = build;
        NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent != null) {
            networkAgent.sendNetworkCapabilities(build);
        }
    }

    public final void updateUidRangesToPerAppVpn(Set set, boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("updateUidRangesToPerAppVpn ", "Vpn", z);
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            num.getClass();
            if (z) {
                ((ArraySet) this.knoxVpnUidRanges).add(new Range(num, num));
            } else {
                ((ArraySet) this.knoxVpnUidRanges).remove(new Range(num, num));
            }
        }
        updateUidListInNetworkCapabilities();
    }

    public final void updateUidRangesToUserVpn(int i, boolean z) {
        Log.d("Vpn", "updateUidRangesToUserVpn " + z + " " + i);
        int i2 = i * 100000;
        int i3 = i2 + 1;
        int i4 = i2 + 99999;
        if (z) {
            ((ArraySet) this.knoxVpnUidRanges).add(new Range(Integer.valueOf(i3), Integer.valueOf(i4)));
        } else {
            ((ArraySet) this.knoxVpnUidRanges).remove(new Range(Integer.valueOf(i3), Integer.valueOf(i4)));
        }
        updateUidListInNetworkCapabilities();
    }

    public final void updateUidRangesToUserVpnWithBlackList(int i, Set set) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "updateUidRangesToUserVpnWithBlackList ", "Vpn");
        if (((HashSet) set).size() <= 0) {
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
                ((ArraySet) this.knoxVpnUidRanges).add(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue2 - 1)));
                intValue = intValue2 + 1;
            }
        }
        if (intValue <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
            ((ArraySet) this.knoxVpnUidRanges).add(new Range(Integer.valueOf(intValue), (Integer) createUidRangeForUser.getUpper()));
        }
        updateUidListInNetworkCapabilities();
    }

    public void validateRequiredFeatures(VpnProfile vpnProfile) {
        int i = vpnProfile.type;
        if ((i == 6 || i == 7 || i == 8 || i == 10) && !this.mContext.getPackageManager().hasSystemFeature("android.software.ipsec_tunnels")) {
            throw new UnsupportedOperationException("Ikev2VpnProfile(s) requires PackageManager.FEATURE_IPSEC_TUNNELS");
        }
    }

    public final void verifyCallingUidAndPackage(String str) {
        Dependencies dependencies = this.mDeps;
        Context context = this.mContext;
        dependencies.getClass();
        int callingUid = Binder.getCallingUid();
        if (getAppUid(context, str, this.mUserId) != callingUid) {
            throw new SecurityException(VpnManagerService$$ExternalSyntheticOutline0.m(callingUid, str, " does not belong to uid "));
        }
    }
}
