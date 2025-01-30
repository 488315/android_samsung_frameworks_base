package com.android.server.connectivity;

import android.R;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.INetworkManagementEventObserver;
import android.net.IVpnManager;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkAgentConfig;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkProvider;
import android.net.NetworkScore;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.VpnTransportInfo;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FunctionalUtils;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.net.BaseNetworkObserver;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class EnterpriseVpn {
  public static final boolean DBG = Debug.semIsProductDev();
  public String mAddress;
  public VpnConfig mConfig;
  public Connection mConnection;
  public final ConnectivityManager mConnectivityManager;
  public final Context mContext;
  public String mInterface;
  public int mLegacyState;
  public final Looper mLooper;
  public final INetd mNetd;
  public NetworkAgent mNetworkAgent;
  public NetworkCapabilities mNetworkCapabilities;
  public final NetworkInfo mNetworkInfo;
  public final NetworkProvider mNetworkProvider;
  public final INetworkManagementService mNms;
  public int mOwnerUID;
  public String mPackage;
  public String mProfileName;
  public final int mUserId;
  public final UserManager mUserManager;
  public SemPersonaManager mPersonaManager = null;
  public boolean isMetaDataEnabled = false;
  public ProxyInfo mHttpProxyInfo = null;
  public String mV6Address = null;
  public boolean isVpnObjectRemoved = false;
  public final Set knoxVpnUidRanges = new ArraySet();
  public NotificationManager mNotificationManager = null;
  public INetworkManagementEventObserver mObserver =
      new BaseNetworkObserver() { // from class: com.android.server.connectivity.EnterpriseVpn.2
        public void interfaceRemoved(String str) {
          synchronized (EnterpriseVpn.this) {
            if (str.equals(EnterpriseVpn.this.mInterface)
                && EnterpriseVpn.this.jniCheck(str) == 0) {
              Log.d("knoxEnterpriseVpn", "Interface removed: " + str);
              try {
                if (EnterpriseVpn.this.mConnection != null) {
                  EnterpriseVpn.this.mContext.unbindService(EnterpriseVpn.this.mConnection);
                  EnterpriseVpn.this.closeInterface();
                  EnterpriseVpn.this.dismissConnectingNotification(str);
                  EnterpriseVpn.this.updateState(
                      NetworkInfo.DetailedState.DISCONNECTING, "agentDisconnect");
                  EnterpriseVpn.getService()
                      .disconnectKnoxVpn(
                          EnterpriseVpn.this.mProfileName, EnterpriseVpn.this.mOwnerUID);
                  EnterpriseVpn.this.cleanupVpnStateLocked();
                  EnterpriseVpn.getKnoxVpnService()
                      .removeVpnUidRanges(EnterpriseVpn.this.mProfileName);
                  if (EnterpriseVpn.this.isVpnObjectRemoved) {
                    Log.d(
                        "knoxEnterpriseVpn",
                        "resetting the network capability and unregistering the observer since the"
                            + " interface is removed");
                    EnterpriseVpn enterpriseVpn = EnterpriseVpn.this;
                    enterpriseVpn.hideNotification(enterpriseVpn.mUserId);
                    EnterpriseVpn.this.cleanupObjects();
                  }
                }
              } catch (Exception e) {
                Log.e(
                    "knoxEnterpriseVpn",
                    "Exception at  interfaceRemoved : " + Log.getStackTraceString(e));
              }
            }
          }
        }
      };
  public final BroadcastReceiver mLocaleChangedReceiver =
      new BroadcastReceiver() { // from class: com.android.server.connectivity.EnterpriseVpn.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (action != null && action.equals("android.intent.action.LOCALE_CHANGED")) {
            Log.d(
                "knoxEnterpriseVpn",
                "Locale changed. Updating Knox vpn notification and the number of user present is"
                    + " ");
            if (EnterpriseVpn.this.mNetworkInfo == null
                || !EnterpriseVpn.this.mNetworkInfo.isConnected()) {
              return;
            }
            EnterpriseVpn.this.updateNotification();
          }
        }
      };

  /* JADX INFO: Access modifiers changed from: private */
  public native int jniCheck(String str);

  private native int jniCreateknoxvpn(int i, boolean z);

  private native String jniGetName(int i);

  private native void jniReset(String str);

  private native int jniSetAddresses(String str, String str2);

  public final void dismissConnectingNotification() {}

  public final void dismissConnectingNotification(String str) {}

  public final int getActiveVpnType() {
    return 4;
  }

  public EnterpriseVpn(
      Looper looper,
      Context context,
      INetworkManagementService iNetworkManagementService,
      INetd iNetd,
      int i,
      String str,
      int i2) {
    this.mContext = context;
    this.mConnectivityManager =
        (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    this.mNms = iNetworkManagementService;
    this.mNetd = iNetd;
    this.mUserId = i;
    this.mLooper = looper;
    this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    this.mPackage = str;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        iNetworkManagementService.registerObserver(this.mObserver);
        int appUid = getAppUid(this.mPackage, i);
        this.mOwnerUID = appUid;
        if (appUid != -1 && i2 != 1) {
          iNetworkManagementService.allowProtect(appUid);
        }
      } catch (Exception e) {
        Log.e(
            "knoxEnterpriseVpn",
            "Unable to register mObserver or protecting socket failed : "
                + Log.getStackTraceString(e));
      }
      NetworkProvider networkProvider =
          new NetworkProvider(context, looper, "VpnNetworkProvider:" + this.mUserId);
      this.mNetworkProvider = networkProvider;
      this.mConnectivityManager.registerNetworkProvider(networkProvider);
      this.mNetworkInfo = new NetworkInfo(17, 0, "VPN", "");
      this.mNetworkCapabilities =
          new NetworkCapabilities.Builder()
              .addTransportType(4)
              .removeCapability(15)
              .addCapability(28)
              .setTransportInfo(new VpnTransportInfo(-1, (String) null))
              .build();
      registerLocaleChangedReceiver();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static IVpnManager getService() {
    return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
  }

  public static IKnoxVpnPolicy getKnoxVpnService() {
    return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
  }

  public void updateState(NetworkInfo.DetailedState detailedState, String str) {
    Log.d("knoxEnterpriseVpn", "setting state=" + detailedState + ", reason=" + str);
    this.mLegacyState = LegacyVpnInfo.stateFromNetworkInfo(detailedState);
    this.mNetworkInfo.setDetailedState(detailedState, str, null);
    int i =
        AbstractC10384.$SwitchMap$android$net$NetworkInfo$DetailedState[detailedState.ordinal()];
    if (i == 1) {
      NetworkAgent networkAgent = this.mNetworkAgent;
      if (networkAgent != null) {
        networkAgent.markConnected();
        return;
      }
      return;
    }
    if (i == 2 || i == 3) {
      NetworkAgent networkAgent2 = this.mNetworkAgent;
      if (networkAgent2 != null) {
        networkAgent2.unregister();
        this.mNetworkAgent = null;
        return;
      }
      return;
    }
    if (i == 4) {
      if (this.mNetworkAgent != null) {
        throw new IllegalStateException(
            "VPN can only go to CONNECTING state when the agent is null.");
      }
    } else {
      if (i == 5) {
        return;
      }
      throw new IllegalArgumentException("Illegal state argument " + detailedState);
    }
  }

  /* renamed from: com.android.server.connectivity.EnterpriseVpn$4 */
  public abstract /* synthetic */ class AbstractC10384 {
    public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState;

    static {
      int[] iArr = new int[NetworkInfo.DetailedState.values().length];
      $SwitchMap$android$net$NetworkInfo$DetailedState = iArr;
      try {
        iArr[NetworkInfo.DetailedState.CONNECTED.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        $SwitchMap$android$net$NetworkInfo$DetailedState[
                NetworkInfo.DetailedState.DISCONNECTED.ordinal()] =
            2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        $SwitchMap$android$net$NetworkInfo$DetailedState[
                NetworkInfo.DetailedState.FAILED.ordinal()] =
            3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        $SwitchMap$android$net$NetworkInfo$DetailedState[
                NetworkInfo.DetailedState.CONNECTING.ordinal()] =
            4;
      } catch (NoSuchFieldError unused4) {
      }
      try {
        $SwitchMap$android$net$NetworkInfo$DetailedState[
                NetworkInfo.DetailedState.DISCONNECTING.ordinal()] =
            5;
      } catch (NoSuchFieldError unused5) {
      }
    }
  }

  public final void resetNetworkCapabilities() {
    this.mNetworkCapabilities =
        new NetworkCapabilities.Builder(this.mNetworkCapabilities)
            .setUids((Set) null)
            .setTransportInfo(new VpnTransportInfo(-1, (String) null))
            .build();
  }

  public final LinkProperties makeLinkProperties() {
    LinkProperties linkProperties = new LinkProperties();
    linkProperties.setInterfaceName(this.mInterface);
    List<LinkAddress> list = this.mConfig.addresses;
    if (list != null) {
      for (LinkAddress linkAddress : list) {
        if (linkAddress.getAddress() instanceof Inet4Address) {
          this.mAddress = linkAddress.getAddress().getHostAddress();
          Log.d(
              "knoxEnterpriseVpn",
              "The ipv4 address added to the knox vpn interface is " + this.mAddress);
          linkProperties.addRoute(
              new RouteInfo(new IpPrefix(NetworkStackConstants.IPV4_ADDR_ANY, 0), null, null, 1));
        }
        if (linkAddress.getAddress() instanceof Inet6Address) {
          this.mV6Address = linkAddress.getAddress().getHostAddress();
          Log.d(
              "knoxEnterpriseVpn",
              "The ipv6 address added to the knox vpn interface is " + this.mV6Address);
          linkProperties.addRoute(
              new RouteInfo(new IpPrefix(NetworkStackConstants.IPV6_ADDR_ANY, 0), null, null, 1));
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
    return linkProperties;
  }

  public final void agentConnect() {
    LinkProperties makeLinkProperties = makeLinkProperties();
    NetworkCapabilities.Builder builder =
        new NetworkCapabilities.Builder(this.mNetworkCapabilities);
    builder.addCapability(12);
    updateState(NetworkInfo.DetailedState.CONNECTING, "agentConnect");
    NetworkAgentConfig build = new NetworkAgentConfig.Builder().setBypassableVpn(false).build();
    builder.setOwnerUid(this.mOwnerUID);
    builder.setAdministratorUids(new int[] {this.mOwnerUID});
    builder.setUids(this.knoxVpnUidRanges);
    builder.setTransportInfo(new VpnTransportInfo(getActiveVpnType(), (String) null));
    this.mNetworkCapabilities = builder.build();
    this.mNetworkAgent =
        new NetworkAgent(
            this.mContext,
            this.mLooper,
            "VPN",
            this.mNetworkCapabilities,
            makeLinkProperties,
            new NetworkScore.Builder().setLegacyInt(101).build(),
            build,
            this.mNetworkProvider) { // from class: com.android.server.connectivity.EnterpriseVpn.1
          public void onNetworkUnwanted() {}

          public void onNetworkCreated() {
            Log.d("knoxEnterpriseVpn", "knox vpn network is created");
            EnterpriseVpn.this.onKnoxVpnConnected();
          }

          public void onNetworkDestroyed() {
            Log.d("knoxEnterpriseVpn", "knox vpn network is destroyed");
          }
        };
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticLambda0
          public final void runOrThrow() {
            EnterpriseVpn.this.lambda$agentConnect$0();
          }
        });
    this.mNetworkAgent.setUnderlyingNetworks((List) null);
    updateState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$agentConnect$0() {
    try {
      this.mNetworkAgent.register();
    } catch (Exception e) {
      this.mNetworkAgent = null;
      throw e;
    }
  }

  public final void agentDisconnect() {
    updateState(NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
  }

  public synchronized ParcelFileDescriptor establish(VpnConfig vpnConfig) {
    if (Binder.getCallingUid() != this.mOwnerUID) {
      Log.e(
          "knoxEnterpriseVpn",
          "establish failed due to caller mismatch " + this.mOwnerUID + Binder.getCallingUid());
      return null;
    }
    Intent intent = new Intent("android.net.VpnService");
    intent.setClassName(this.mPackage, vpnConfig.user);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (this.mUserManager.getUserInfo(this.mUserId).isRestricted()
            || this.mUserManager.hasUserRestriction("no_config_vpn")) {
          throw new SecurityException("Restricted users cannot establish VPNs");
        }
        ResolveInfo resolveService =
            AppGlobals.getPackageManager().resolveService(intent, (String) null, 0L, this.mUserId);
        if (resolveService == null) {
          throw new SecurityException("Cannot find " + vpnConfig.user);
        }
        if (!"android.permission.BIND_VPN_SERVICE".equals(resolveService.serviceInfo.permission)) {
          throw new SecurityException(
              vpnConfig.user + " does not require android.permission.BIND_VPN_SERVICE");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mProfileName = vpnConfig.session;
        ParcelFileDescriptor adoptFd =
            ParcelFileDescriptor.adoptFd(jniCreateknoxvpn(vpnConfig.mtu, this.isMetaDataEnabled));
        try {
          try {
            IoUtils.setBlocking(adoptFd.getFileDescriptor(), vpnConfig.blocking);
            String jniGetName = jniGetName(adoptFd.getFd());
            StringBuilder sb = new StringBuilder();
            for (LinkAddress linkAddress : vpnConfig.addresses) {
              sb.append(" ");
              sb.append(linkAddress);
            }
            if (jniSetAddresses(jniGetName, sb.toString()) < 1) {
              throw new IllegalArgumentException("At least one address must be specified");
            }
            Connection connection = new Connection();
            if (!this.mContext.bindServiceAsUser(
                intent, connection, 67108865, new UserHandle(this.mUserId))) {
              throw new IllegalStateException("Cannot bind " + vpnConfig.user);
            }
            Connection connection2 = this.mConnection;
            if (connection2 != null) {
              this.mContext.unbindService(connection2);
            }
            String str = this.mInterface;
            if (str != null && !str.equals(jniGetName)) {
              jniReset(this.mInterface);
            }
            this.mConnection = connection;
            this.mInterface = jniGetName;
            vpnConfig.user = this.mPackage;
            vpnConfig.interfaze = jniGetName;
            vpnConfig.startTime = SystemClock.elapsedRealtime();
            this.mConfig = vpnConfig;
            this.mNetworkAgent = null;
            updateState(NetworkInfo.DetailedState.CONNECTING, "establish");
            agentConnect();
            Log.i(
                "knoxEnterpriseVpn", "Established by " + vpnConfig.user + " on " + this.mInterface);
            return adoptFd;
          } catch (RuntimeException e) {
            Log.e(
                "knoxEnterpriseVpn",
                "Exception in creating tun interface" + Log.getStackTraceString(e));
            dismissConnectingNotification();
            IoUtils.closeQuietly(adoptFd);
            agentDisconnect();
            throw e;
          }
        } catch (IOException e2) {
          throw new IllegalStateException(
              "Cannot set tunnel's fd as blocking=" + vpnConfig.blocking, e2);
        }
      } catch (RemoteException unused) {
        throw new SecurityException("Cannot find " + vpnConfig.user);
      }
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final void cleanupVpnStateLocked() {
    resetNetworkCapabilities();
    this.mConfig = null;
    this.mInterface = null;
    this.mConnection = null;
    agentDisconnect();
  }

  public final void closeInterface() {
    String str = this.mInterface;
    if (str != null) {
      jniReset(str);
    }
  }

  public class Connection implements ServiceConnection {
    public IBinder mService;

    public Connection() {}

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      this.mService = iBinder;
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      this.mService = null;
    }
  }

  public static Range createUidRangeForUser(int i) {
    return new Range(Integer.valueOf(i * 100000), Integer.valueOf(((i + 1) * 100000) - 1));
  }

  public void updateUidRangesToPerAppVpn(boolean z, Set set) {
    Log.d("knoxEnterpriseVpn", "updateUidRangesToPerAppVpn " + z);
    Iterator it = set.iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      if (z) {
        this.knoxVpnUidRanges.add(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue)));
      } else {
        this.knoxVpnUidRanges.remove(
            new Range(Integer.valueOf(intValue), Integer.valueOf(intValue)));
      }
    }
    updateUidListInNetworkCapabilities();
  }

  public void updateUidRangesToUserVpn(boolean z, int i) {
    Log.d("knoxEnterpriseVpn", "updateUidRangesToUserVpn " + z + " " + i);
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
    Log.d("knoxEnterpriseVpn", "updateUidRangesToUserVpnWithBlackList " + i);
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
        this.knoxVpnUidRanges.add(
            new Range(Integer.valueOf(intValue), Integer.valueOf(intValue2 - 1)));
        intValue = intValue2 + 1;
      }
    }
    if (intValue <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
      this.knoxVpnUidRanges.add(
          new Range(Integer.valueOf(intValue), (Integer) createUidRangeForUser.getUpper()));
    }
    updateUidListInNetworkCapabilities();
  }

  public final void updateUidListInNetworkCapabilities() {
    for (Range range : this.knoxVpnUidRanges) {
      Log.d(
          "knoxEnterpriseVpn",
          "uidRange going to be updated is "
              + range.getLower()
              + " "
              + range.getUpper()
              + " for profile "
              + this.mProfileName);
    }
    NetworkCapabilities build =
        new NetworkCapabilities.Builder(this.mNetworkCapabilities)
            .setUids(this.knoxVpnUidRanges)
            .build();
    this.mNetworkCapabilities = build;
    NetworkAgent networkAgent = this.mNetworkAgent;
    if (networkAgent != null) {
      networkAgent.sendNetworkCapabilities(build);
    }
  }

  public void resetUidListInNetworkCapabilities() {
    applyBlockingRulesToUidRange(false);
    this.knoxVpnUidRanges.clear();
    NetworkCapabilities build =
        new NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((Set) null).build();
    this.mNetworkCapabilities = build;
    NetworkAgent networkAgent = this.mNetworkAgent;
    if (networkAgent != null) {
      networkAgent.sendNetworkCapabilities(build);
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

  public void applyBlockingRulesToUidRange(boolean z) {
    try {
      this.mConnectivityManager.setRequireVpnForUids(z, this.knoxVpnUidRanges);
    } catch (RuntimeException e) {
      Log.e(
          "knoxEnterpriseVpn",
          "Updating blocked="
              + z
              + " for UIDs "
              + Arrays.toString(this.knoxVpnUidRanges.toArray())
              + " failed",
          e);
    }
  }

  public void cleanupObjects() {
    if (this.mInterface != null) {
      Log.e(
          "knoxEnterpriseVpn",
          "Delay resetting the network capability and unregistering the observer since the"
              + " interface is not removed yet");
      this.isVpnObjectRemoved = true;
    } else {
      resetUidListInNetworkCapabilities();
      try {
        this.mNms.unregisterObserver(this.mObserver);
      } catch (RemoteException unused) {
      }
      unregisterLocaleChangedReceiver();
      this.mConnectivityManager.unregisterNetworkProvider(this.mNetworkProvider);
    }
  }

  public final void onKnoxVpnConnected() {
    try {
      int chainingEnabledForProfile =
          getKnoxVpnService().getChainingEnabledForProfile(this.mOwnerUID);
      int netId = getNetId();
      if (chainingEnabledForProfile == 1) {
        this.mNms.setNetworkInfo(netId, true, this.mOwnerUID);
      }
      getKnoxVpnService()
          .addVpnUidRanges(
              this.mProfileName, netId, this.mInterface, this.mAddress, this.mV6Address);
      showNotification(this.mProfileName, true);
    } catch (Exception e) {
      Log.e("knoxEnterpriseVpn", "Error occured " + Log.getStackTraceString(e));
    }
  }

  public final Notification createNotification(PendingIntent pendingIntent) {
    String string = this.mContext.getString(R.string.oneMonthDurationPast);
    return new Notification.Builder(this.mContext, SystemNotificationChannels.VPN)
        .setSmallIcon(17304616)
        .setContentTitle(string)
        .setContentText(this.mContext.getString(R.string.older))
        .setContentIntent(pendingIntent)
        .setDefaults(0)
        .setOngoing(true)
        .setPriority(2)
        .getNotification();
  }

  public void showNotification(String str, boolean z) {
    boolean z2 = DBG;
    if (z2) {
      Log.d("knoxEnterpriseVpn", "showNotification > profileName : " + str + " , optionAdd : " + z);
    }
    if (z) {
      dismissConnectingNotification();
    }
    NotificationManager notificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    VpnConfig vpnConfig = this.mConfig;
    if (vpnConfig == null || notificationManager == null) {
      Log.d("knoxEnterpriseVpn", "Error : can not init");
      return;
    }
    vpnConfig.startTime = SystemClock.elapsedRealtime();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      List domainsByProfileName = getDomainsByProfileName(vpnConfig.session);
      if (domainsByProfileName != null && domainsByProfileName.size() > 0) {
        Iterator it = domainsByProfileName.iterator();
        while (it.hasNext()) {
          int parseInt = Integer.parseInt((String) it.next());
          Notification createNotification =
              createNotification(
                  VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                      this.mContext, vpnConfig, z, parseInt));
          if (getKnoxVpnService().getNotificationDismissibleFlagInternal(parseInt) == 1) {
            notificationManager.notifyAsUser(
                "Knox_Notification", 100000, createNotification, new UserHandle(parseInt));
          } else {
            notificationManager.notifyAsUser(
                null, parseInt + 100000, createNotification, new UserHandle(parseInt));
          }
          if (DBG) {
            Log.d("knoxEnterpriseVpn", "showNotification > keyicon notified to user " + parseInt);
          }
        }
      } else {
        Notification createNotification2 =
            createNotification(
                VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                    this.mContext, vpnConfig, false, this.mUserId));
        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(this.mUserId) == 1) {
          notificationManager.notifyAsUser(
              "Knox_Notification", 100000, createNotification2, new UserHandle(this.mUserId));
        } else {
          notificationManager.notifyAsUser(
              null, this.mUserId + 100000, createNotification2, new UserHandle(this.mUserId));
        }
        if (z2) {
          Log.d(
              "knoxEnterpriseVpn",
              "showNotification > keyicon notified to user for non installed apps " + this.mUserId);
        }
      }
    } catch (Exception e) {
      Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void updateNotification() {
    boolean z = DBG;
    if (z) {
      Log.d("knoxEnterpriseVpn", "updateNotification > user : " + this.mUserId);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        NotificationManager notificationManager =
            (NotificationManager) this.mContext.getSystemService("notification");
        this.mContext.getString(R.string.oneMonthDurationPast);
        this.mContext.getString(R.string.older);
        if (z) {
          Log.d("knoxEnterpriseVpn", "updateNotification > vpn type is per-app");
        }
        List domainsByProfileName = getDomainsByProfileName(this.mConfig.session);
        if (domainsByProfileName != null) {
          Iterator it = domainsByProfileName.iterator();
          while (it.hasNext()) {
            int parseInt = Integer.parseInt((String) it.next());
            Notification createNotification =
                createNotification(
                    VpnConfig.getIntentForStatusPanelRefreshAsUser(this.mContext, parseInt));
            if (getKnoxVpnService().getNotificationDismissibleFlagInternal(parseInt) == 1) {
              notificationManager.notifyAsUser(
                  "Knox_Notification", 100000, createNotification, new UserHandle(parseInt));
            } else {
              notificationManager.notifyAsUser(
                  null, 100000 + parseInt, createNotification, new UserHandle(parseInt));
            }
          }
        }
      } catch (Exception e) {
        Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void hideNotification(int i) {
    if (DBG) {
      Log.d("knoxEnterpriseVpn", "hideNotification > domain : " + i);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        NotificationManager notificationManager =
            (NotificationManager) this.mContext.getSystemService("notification");
        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
          notificationManager.cancelAsUser("Knox_Notification", 100000, new UserHandle(i));
        } else {
          notificationManager.cancelAsUser(null, 100000 + i, new UserHandle(i));
        }
      } catch (Exception e) {
        Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void refreshNotification(boolean z, int i) {
    if (DBG) {
      Log.d("knoxEnterpriseVpn", "refreshNotification > domain : " + i + ", optionAdd : " + z);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        NotificationManager notificationManager =
            (NotificationManager) this.mContext.getSystemService("notification");
        Notification createNotification =
            createNotification(
                VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                    this.mContext, this.mConfig, z, i));
        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
          notificationManager.notifyAsUser(
              "Knox_Notification", 100000, createNotification, new UserHandle(i));
        } else {
          notificationManager.notifyAsUser(null, 100000 + i, createNotification, new UserHandle(i));
        }
      } catch (Exception e) {
        Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setNotificationDismissibleFlag(int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        NotificationManager notificationManager =
            (NotificationManager) this.mContext.getSystemService("notification");
        Notification createNotification =
            createNotification(VpnConfig.getIntentForStatusPanelRefreshAsUser(this.mContext, i));
        if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
          notificationManager.cancelAsUser(null, i + 100000, new UserHandle(i));
          notificationManager.notifyAsUser(
              "Knox_Notification", 100000, createNotification, new UserHandle(i));
        } else {
          notificationManager.cancelAsUser("Knox_Notification", 100000, new UserHandle(i));
          notificationManager.notifyAsUser(null, 100000 + i, createNotification, new UserHandle(i));
        }
      } catch (Exception e) {
        Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final List getDomainsByProfileName(String str) {
    if (DBG) {
      Log.d("knoxEnterpriseVpn", "getDomainsByProfileName()");
    }
    try {
      return getKnoxVpnService().getDomainsByProfileName(str);
    } catch (Exception e) {
      Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
      return null;
    }
  }

  public final void registerLocaleChangedReceiver() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    this.mContext.registerReceiverAsUser(
        this.mLocaleChangedReceiver,
        UserHandle.ALL,
        new IntentFilter("android.intent.action.LOCALE_CHANGED"),
        null,
        null);
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void unregisterLocaleChangedReceiver() {
    if (this.mLocaleChangedReceiver != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      this.mContext.unregisterReceiver(this.mLocaleChangedReceiver);
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setMetaDataStateInIP(boolean z) {
    this.isMetaDataEnabled = z;
  }

  public final int getAppUid(String str, int i) {
    try {
      return this.mContext.getPackageManager().getPackageUidAsUser(str, i);
    } catch (PackageManager.NameNotFoundException unused) {
      return -1;
    }
  }

  public NetworkInfo getNetworkInfo() {
    return this.mNetworkInfo;
  }

  public synchronized int getNetId() {
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

  public final boolean isRunningLocked() {
    return (this.mNetworkAgent == null || this.mInterface == null) ? false : true;
  }

  public boolean isCallerEstablishedOwnerLocked() {
    return isRunningLocked() && Binder.getCallingUid() == this.mOwnerUID;
  }

  public String getInterfaceName() {
    return this.mInterface;
  }
}
