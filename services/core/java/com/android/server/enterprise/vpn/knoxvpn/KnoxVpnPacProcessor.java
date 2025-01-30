package com.android.server.enterprise.vpn.knoxvpn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.net.IProxyService;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import libcore.net.InetAddressUtils;

/* loaded from: classes2.dex */
public class KnoxVpnPacProcessor {
  public static Context mContext;
  public static KnoxVpnPacProcessor mKnoxVpnPacProcessor;
  public static HashMap mDownloadStatus = new HashMap();
  public static HashMap mRetryAttempt = new HashMap();
  public static HashMap mDownloadUrlThread = new HashMap();
  public static HashMap mProxyServiceList = new HashMap();
  public static HashMap mproxyConnectionList = new HashMap();
  public static final boolean DBG = Debug.semIsProductDev();
  public List mKnoxVpnProxyClientStatus = new ArrayList();
  public VpnProfileConfig mVpnConfig = null;
  public KnoxVpnFirewallHelper mFirewallHelper = null;
  public KnoxVpnHelper mKnoxVpnHelper = null;

  public native int jnibindSocketToInterface(int i, int i2, int i3, String str);

  public KnoxVpnPacProcessor(Context context) {
    mContext = context;
  }

  public class KnoxVpnPacConnection implements ServiceConnection {
    public String profile;
    public int userId;

    public KnoxVpnPacConnection(int i, String str) {
      this.userId = i;
      this.profile = str;
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceDisconnected(ComponentName componentName) {
      Log.d(
          "KnoxVpnPacProcessor",
          "onServicedisconnected is reached for user " + this.userId + this.profile);
      KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus.add(
          "onServiceDisconnected callback has been recieved for the proxy client in user "
              + this.userId
              + " and for profile "
              + this.profile
              + " at "
              + System.currentTimeMillis());
      KnoxVpnPacProcessor.getDownloadStatus().clear();
      KnoxVpnPacProcessor.getCurrentRetryStatus().clear();
      KnoxVpnPacProcessor.getDownloadThread().clear();
      if (KnoxVpnPacProcessor.this.getVpnConfigInstance() != null) {
        Iterator it = KnoxVpnPacProcessor.this.mVpnConfig.getProfileEntries().iterator();
        while (it.hasNext()) {
          KnoxVpnPacProcessor.this.sendPacServiceStatus(
              0, ((VpnProfileInfo) it.next()).getProfileName());
        }
      }
      KnoxVpnPacProcessor.this.removePacInterface(this.userId);
      KnoxVpnPacProcessor.this.bindProxyService(this.profile, this.userId);
    }

    @Override // android.content.ServiceConnection
    public synchronized void onBindingDied(ComponentName componentName) {
      Log.d(
          "KnoxVpnPacProcessor",
          "onBindingDied is reached for user and trying to bind again "
              + this.userId
              + this.profile);
      KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus.add(
          "onBindingDied callback has been recieved for the proxy client in user "
              + this.userId
              + " and for profile "
              + this.profile
              + " at "
              + System.currentTimeMillis());
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.d(
          "KnoxVpnPacProcessor",
          "onServiceConnected is reached for user " + this.userId + this.profile);
      KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus.add(
          "onServiceConnected callback has been recieved for the proxy client in user "
              + this.userId
              + " and for profile "
              + this.profile
              + " at "
              + System.currentTimeMillis());
      IProxyService asInterface = IProxyService.Stub.asInterface(iBinder);
      if (asInterface == null) {
        Log.d("KnoxVpnPacProcessor", "Got a null IBinder reference, aborting...");
        return;
      }
      if (KnoxVpnPacProcessor.this.hasProxyService(this.userId)) {
        Log.d(
            "KnoxVpnPacProcessor",
            "onServiceConnected:Binder object already exists for the user " + this.userId);
        return;
      }
      Log.d(
          "KnoxVpnPacProcessor",
          "onServiceConnected:After onservice disconnect for unknown reason or for the first"
              + " onservice connect");
      KnoxVpnPacProcessor.this.setProxyService(this.userId, asInterface);
      if (KnoxVpnPacProcessor.this.getVpnConfigInstance() != null) {
        for (VpnProfileInfo vpnProfileInfo :
            KnoxVpnPacProcessor.this.mVpnConfig.getProfileEntries()) {
          String profileName = vpnProfileInfo.getProfileName();
          if (KnoxVpnPacProcessor.this.getConfiguredUser(profileName) == this.userId
              && vpnProfileInfo.getActivateState() == 1) {
            String interfaceName = vpnProfileInfo.getInterfaceName();
            Uri pacurl = vpnProfileInfo.getPacurl();
            String proxyServer = vpnProfileInfo.getProxyServer();
            int netId = vpnProfileInfo.getNetId();
            if (pacurl != Uri.EMPTY) {
              try {
                boolean startPacSystemForKnoxProfile =
                    asInterface.startPacSystemForKnoxProfile(profileName);
                Log.d(
                    "KnoxVpnPacProcessor",
                    "pac support for the profile"
                        + profileName
                        + " is started since the service is connected and the result is "
                        + startPacSystemForKnoxProfile);
                if (startPacSystemForKnoxProfile && interfaceName != null) {
                  KnoxVpnPacProcessor.this.setCurrentProxyScript(
                      profileName, interfaceName, pacurl);
                }
              } catch (RemoteException unused) {
              }
              KnoxVpnPacProcessor.this.startProxyServerForKnoxProfile(profileName);
              KnoxVpnPacProcessor.this.setMiscValueForPacProfile(profileName, interfaceName, netId);
              KnoxVpnPacProcessor.this.sendPacServiceStatus(1, profileName);
            } else if (proxyServer != KnoxVpnConstants.DEFAULT_PROXY_SERVER) {
              KnoxVpnPacProcessor.this.startProxyServerForKnoxProfile(profileName);
              KnoxVpnPacProcessor.this.setMiscValueForPacProfile(profileName, interfaceName, netId);
              KnoxVpnPacProcessor.this.sendPacServiceStatus(1, profileName);
            }
          }
        }
      }
    }
  }

  public synchronized void bindProxyService(String str, String str2, Uri uri, int i) {
    if (mContext == null) {
      Log.e("KnoxVpnPacProcessor", "No context for binding");
      return;
    }
    int configuredUser = getConfiguredUser(str);
    if (getProxyService(configuredUser) != null) {
      Log.e(
          "KnoxVpnPacProcessor", "Already binded to proxy service for the user " + configuredUser);
      try {
        if (uri != Uri.EMPTY) {
          boolean startPacSystemForKnoxProfile =
              getProxyService(configuredUser).startPacSystemForKnoxProfile(str);
          Log.d(
              "KnoxVpnPacProcessor",
              "pac support for the profile"
                  + str
                  + " is started since the service is connected and the result is "
                  + startPacSystemForKnoxProfile
                  + "user "
                  + configuredUser);
          if (startPacSystemForKnoxProfile) {
            setCurrentProxyScript(str, str2, uri);
          }
          startProxyServerForKnoxProfile(str);
          setMiscValueForPacProfile(str, str2, i);
          sendPacServiceStatus(1, str);
        } else {
          startProxyServerForKnoxProfile(str);
          setMiscValueForPacProfile(str, str2, i);
          sendPacServiceStatus(1, str);
        }
      } catch (RemoteException unused) {
      }
      return;
    }
    bindProxyService(str, configuredUser);
  }

  public void bindProxyService(String str, int i) {
    Log.d("KnoxVpnPacProcessor", "Binding to the proxy service for the user " + i);
    if (mproxyConnectionList.containsKey(Integer.valueOf(i))) {
      Log.d(
          "KnoxVpnPacProcessor",
          "unbinding previous service before binding again for the user " + i);
      try {
        mContext.unbindService((ServiceConnection) mproxyConnectionList.get(Integer.valueOf(i)));
      } catch (Exception unused) {
        Log.e(
            "KnoxVpnPacProcessor",
            "unbinding failed since the service is already unbinded or not existing");
      }
      mproxyConnectionList.remove(Integer.valueOf(i));
    }
    KnoxVpnPacConnection knoxVpnPacConnection = new KnoxVpnPacConnection(i, str);
    Intent intent = new Intent();
    intent.setClassName("com.knox.vpn.proxyhandler", "com.knox.vpn.proxyhandler.PacService");
    mContext.bindServiceAsUser(intent, knoxVpnPacConnection, 1073741829, new UserHandle(i));
    mproxyConnectionList.put(Integer.valueOf(i), knoxVpnPacConnection);
  }

  public synchronized void unBindPacService(int i) {
    if (mproxyConnectionList.containsKey(Integer.valueOf(i))) {
      mContext.unbindService((ServiceConnection) mproxyConnectionList.get(Integer.valueOf(i)));
      mproxyConnectionList.remove(Integer.valueOf(i));
    }
    if (getProxyService(i) != null) {
      removePacInterface(i);
    }
  }

  public static synchronized KnoxVpnPacProcessor getInstance(Context context) {
    KnoxVpnPacProcessor knoxVpnPacProcessor;
    synchronized (KnoxVpnPacProcessor.class) {
      if (mKnoxVpnPacProcessor == null) {
        mKnoxVpnPacProcessor = new KnoxVpnPacProcessor(context);
      }
      knoxVpnPacProcessor = mKnoxVpnPacProcessor;
    }
    return knoxVpnPacProcessor;
  }

  public static synchronized HashMap getDownloadStatus() {
    HashMap hashMap;
    synchronized (KnoxVpnPacProcessor.class) {
      if (mDownloadStatus == null) {
        mDownloadStatus = new HashMap();
      }
      hashMap = mDownloadStatus;
    }
    return hashMap;
  }

  public static synchronized HashMap getCurrentRetryStatus() {
    HashMap hashMap;
    synchronized (KnoxVpnPacProcessor.class) {
      if (mRetryAttempt == null) {
        mRetryAttempt = new HashMap();
      }
      hashMap = mRetryAttempt;
    }
    return hashMap;
  }

  public static synchronized HashMap getDownloadThread() {
    HashMap hashMap;
    synchronized (KnoxVpnPacProcessor.class) {
      if (mDownloadUrlThread == null) {
        mDownloadUrlThread = new HashMap();
      }
      hashMap = mDownloadUrlThread;
    }
    return hashMap;
  }

  public final synchronized VpnProfileConfig getVpnConfigInstance() {
    if (this.mVpnConfig == null) {
      this.mVpnConfig = VpnProfileConfig.getInstance();
    }
    return this.mVpnConfig;
  }

  public final KnoxVpnFirewallHelper getKnoxVpnFirewallInstance() {
    if (this.mFirewallHelper == null) {
      this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
    }
    return this.mFirewallHelper;
  }

  public final void sendPacServiceStatus(int i, String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Intent intent = new Intent();
      intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
      intent.putExtra("com.samsung.android.knox.intent.extra.ACTION_INTERNAL", "pac_info");
      intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", i);
      intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", str);
      mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL");
    } catch (Exception unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final IProxyService getProxyService(int i) {
    if (mProxyServiceList.containsKey(Integer.valueOf(i))) {
      return (IProxyService) mProxyServiceList.get(Integer.valueOf(i));
    }
    return null;
  }

  public final void setProxyService(int i, Object obj) {
    Log.d("KnoxVpnPacProcessor", "setProxyService is being called for the userId " + i);
    mProxyServiceList.put(Integer.valueOf(i), obj);
  }

  public final boolean hasProxyService(int i) {
    Log.d("KnoxVpnPacProcessor", "hasProxyService is being called for the userId " + i);
    return mProxyServiceList.containsKey(Integer.valueOf(i));
  }

  public final void removePacInterface(int i) {
    Log.d("KnoxVpnPacProcessor", "removePacInterface is being called for the userId " + i);
    if (mProxyServiceList.containsKey(Integer.valueOf(i))) {
      mProxyServiceList.remove(Integer.valueOf(i));
    }
  }

  public void updatePermissionForAppsToaccessLocalHost(String str, int i, int i2, HashMap hashMap) {
    try {
      VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
      if (profileEntry != null) {
        new HashSet();
        new HashSet();
        if (i == 0) {
          getKnoxVpnFirewallInstance().addRulesToDenyAccessToLocalHost(i2);
          getKnoxVpnFirewallInstance()
              .addRulesToAllowAccessToLocalHostWithValidMark(
                  profileEntry.getInterfaceName(), i2, profileEntry.getInterfaceType());
        } else if (i == 1) {
          getKnoxVpnFirewallInstance().removeRulesToDenyAccessToLocalHost(i2);
          getKnoxVpnFirewallInstance()
              .removeRulesToAllowAccessToLocalHostWithValidMark(
                  profileEntry.getInterfaceName(), i2, profileEntry.getInterfaceType());
        }
      }
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Exception occurred while trying to apply rules to access local host for the vpn profile "
              + str);
    }
  }

  public void handleScreenunlock() {
    try {
      Iterator it = mProxyServiceList.keySet().iterator();
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        Log.d(
            "KnoxVpnPacProcessor",
            "trying to see if authentication dialog is needed after screenlock in user "
                + intValue);
        getProxyService(intValue).handleScreenunlock();
      }
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Failed to show the proxy auth screen on screen unlock; might be the proxy server has not"
              + " started yet");
    }
  }

  public int getProxyPortForProfile(String str) {
    try {
      return getProxyService(getConfiguredUser(str)).getProxyPortForProfile(str);
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "The knox vpn proxy service has not yet started for profile while querying for port "
              + str);
      return -1;
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          "unknown error occured for profile while querying for port " + str);
      return -1;
    }
  }

  public boolean isProxyThreadRunning(String str) {
    try {
      return getProxyService(getConfiguredUser(str)).isProxyThreadRunning(str);
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "The knox vpn proxy service has not yet started for profile while querying for proxy"
              + " thread running or not "
              + str);
      return false;
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          "unknown error occured for profile while querying for proxy thread running or not "
              + str);
      return false;
    }
  }

  public String getProxythreadStatus(String str) {
    try {
      return getProxyService(getConfiguredUser(str)).getProxythreadStatus(str);
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "The knox vpn proxy service has not yet started for profile while querying for proxy"
              + " thread Status "
              + str);
      return "IntialValue";
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          "unknown error occured for profile while querying for proxy thread Status " + str);
      return "IntialValue";
    }
  }

  public boolean isProxyThreadAlive(String str) {
    try {
      return getProxyService(getConfiguredUser(str)).isProxyThreadAlive(str);
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "The knox vpn proxy service has not yet started for profile while querying for proxy"
              + " thread alive "
              + str);
      return false;
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          "unknown error occured for profile while querying for proxy thread alive " + str);
      return false;
    }
  }

  public List getKnoxVpnProxyClientStatus() {
    return this.mKnoxVpnProxyClientStatus;
  }

  public final int startProxyServerForKnoxProfile(String str) {
    VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
    if (profileEntry == null) {
      return -1;
    }
    try {
      return getProxyService(getConfiguredUser(str))
          .startProxyServerForKnoxProfile(
              str,
              profileEntry.isProxyAuthRequired(),
              profileEntry.getProxyUsername(),
              profileEntry.getProxyPassword(),
              profileEntry.isproxyCredentialsPreDefined(),
              profileEntry.getProxyServer(),
              profileEntry.getProxyPort());
    } catch (Exception e) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Error occured while trying to start the proxy server for the profile"
              + str
              + Log.getStackTraceString(e));
      return -1;
    }
  }

  public void stopPacSupport(String str) {
    try {
      getDownloadStatus();
      if (mDownloadStatus.containsKey(str)) {
        mDownloadStatus.remove(str);
      }
      getCurrentRetryStatus();
      if (getCurrentRetryStatus().containsKey(str)) {
        getCurrentRetryStatus().remove(str);
      }
      getDownloadThread();
      if (getDownloadThread().containsKey(str)) {
        getDownloadThread().remove(str);
      }
      boolean stopPacSystemForKnoxProfile =
          getProxyService(getConfiguredUser(str)).stopPacSystemForKnoxProfile(str);
      Log.d(
          "KnoxVpnPacProcessor",
          "check to see if the pac support is removed for the profile "
              + stopPacSystemForKnoxProfile);
      if (stopPacSystemForKnoxProfile) {
        getProxyService(getConfiguredUser(str)).stopProxyServerForKnoxProfile(str);
      }
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to remove the pac support due to some values being not"
              + " intialized successfully");
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          " error occured while trying to remove the pac script for the profile " + str);
    }
  }

  public boolean setMiscValueForPacProfile(String str, String str2, int i) {
    boolean z = false;
    try {
      z =
          getProxyService(getConfiguredUser(str))
              .setMiscValueForPacProfile(i, str, str2, getConfiguredUser(str));
      Log.d(
          "KnoxVpnPacProcessor",
          "checking if the Misc value has been set for the knox pac profile " + z);
      return z;
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Exception occurred while trying to set the misc value for the pac profile ");
      return z;
    }
  }

  public void resetInterfaceName(String str) {
    try {
      getProxyService(getConfiguredUser(str)).resetInterface(str);
    } catch (Exception unused) {
      Log.e("KnoxVpnPacProcessor", "error occured while trying to reset interface name");
    }
  }

  public void setCurrentProxyScript(String str, String str2, Uri uri) {
    try {
      if (uri == Uri.EMPTY) {
        Log.d(
            "KnoxVpnPacProcessor",
            "The proxy script is not being set since the pac url is empty or a static proxy has"
                + " been configured");
        return;
      }
      if (getProxyService(getConfiguredUser(str)) == null) {
        Log.d(
            "KnoxVpnPacProcessor",
            "The proxy service has not been intialized while trying to set the proxy script, trying"
                + " to bind again");
        bindProxyService(str, getConfiguredUser(str));
        return;
      }
      getDownloadStatus();
      if (mDownloadStatus.containsKey(str)) {
        if (((Integer) mDownloadStatus.get(str)).intValue() != 2) {
          return;
        }
        Log.d(
            "KnoxVpnPacProcessor",
            "Try to download the pac url for the profile after the interface is up for the profile "
                + str);
        if (getCurrentRetryStatus().containsKey(str)) {
          getCurrentRetryStatus().remove(str);
        }
        extractUrlParameters(str, str2, uri.toString());
        return;
      }
      extractUrlParameters(str, str2, uri.toString());
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to get current status pac file due to some values being not"
              + " intialized successfully");
    } catch (Exception unused2) {
      Log.e("KnoxVpnPacProcessor", " error occured while trying to get current status pac file");
    }
  }

  public final boolean getPacUrlDownloadStatus(String str) {
    boolean z = false;
    try {
      getDownloadStatus();
      if (mDownloadStatus.containsKey(str)) {
        if (((Integer) mDownloadStatus.get(str)).intValue() == 0) {
          z = true;
        }
      }
    } catch (NullPointerException unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to get download status pac file due to some values being not"
              + " intialized successfully");
    } catch (Exception unused2) {
      Log.e("KnoxVpnPacProcessor", " error occured while trying to get download status pac file");
    }
    Log.d(
        "KnoxVpnPacProcessor", "check to see if the pac url has been downloaded successfully " + z);
    return z;
  }

  public final void extractUrlParameters(String str, String str2, String str3) {
    try {
      URL url = new URL(str3);
      String host = url.getHost();
      String path = url.getPath();
      String protocol = url.getProtocol();
      int port = url.getPort();
      int portFromProtocol = port == -1 ? getPortFromProtocol(protocol) : port;
      if (getDownloadThread().get(str) != null) {
        Log.d(
            "KnoxVpnPacProcessor",
            "The current status of the download thread of the profile "
                + str
                + "is "
                + ((DownloadUrlThread) getDownloadThread().get(str)).getState());
        if (((DownloadUrlThread) getDownloadThread().get(str)).isAlive()) {
          ((DownloadUrlThread) getDownloadThread().get(str)).interrupt();
        }
        getDownloadThread().remove(str);
      }
      DownloadUrlThread downloadUrlThread =
          new DownloadUrlThread(str, host, path, portFromProtocol, str2);
      downloadUrlThread.start();
      getDownloadThread().put(str, downloadUrlThread);
    } catch (IllegalArgumentException unused) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to download the pac file due to unknown port number");
    } catch (NullPointerException unused2) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to download the pac file due to some values being not"
              + " intialized successfully");
    } catch (MalformedURLException unused3) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to download the pac file due to unknow url format, remove and"
              + " create the profile again");
    } catch (Exception unused4) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "error occured while trying to download the pac file after the vpn connection is"
              + " established");
    }
  }

  public final int getPortFromProtocol(String str) {
    int i;
    str.hashCode();
    switch (str) {
      case "ftp":
        i = 21;
        break;
      case "http":
        i = 80;
        break;
      case "https":
        i = 443;
        break;
      default:
        throw new IllegalArgumentException();
    }
    Log.d(
        "KnoxVpnPacProcessor", "The pac file is going to be downloaded from the remote port " + i);
    return i;
  }

  public final Socket getValidEndPointAddress(int i, String str, int i2, String str2, int i3) {
    InetAddress inetAddress;
    InetAddress[] allByNameOnNet;
    Socket socket;
    try {
      inetAddress = InetAddressUtils.parseNumericAddress(str);
    } catch (IllegalArgumentException unused) {
      Log.d(
          "KnoxVpnPacProcessor",
          "The pac url being entered is not of numeric form and need to do a dns look up");
      inetAddress = null;
    }
    if (inetAddress == null) {
      try {
        allByNameOnNet = InetAddress.getAllByNameOnNet(str, i3);
      } catch (Exception unused2) {
        Log.e(
            "KnoxVpnPacProcessor",
            "UnknownHostException occured while trying to query the ip address for the pac url"
                + " domain name");
        throw new UnknownHostException();
      }
    } else {
      allByNameOnNet = new InetAddress[] {inetAddress};
    }
    if (allByNameOnNet == null) {
      return null;
    }
    Socket socket2 = null;
    String str3 = null;
    for (InetAddress inetAddress2 : allByNameOnNet) {
      try {
        socket = new Socket();
      } catch (Exception unused3) {
      }
      try {
        if (inetAddress2 instanceof Inet4Address) {
          Log.d(
              "KnoxVpnPacProcessor",
              "The pac file is going to be downloaded from an interface having ipv4 address");
          str3 = inetAddress2.getHostAddress();
          if (DBG) {
            Log.d("KnoxVpnPacProcessor", "The resolved host address is a ipv4 address " + str3);
          }
          socket.bind(new InetSocketAddress(NetworkStackConstants.IPV4_ADDR_ANY, 0));
        } else if (inetAddress2 instanceof Inet6Address) {
          Log.d(
              "KnoxVpnPacProcessor",
              "The pac file is going to be downloaded from an interface having ipv6 address");
          str3 = inetAddress2.getHostAddress();
          if (DBG) {
            Log.d("KnoxVpnPacProcessor", "The resolved host address is a ipv6 address " + str3);
          }
          socket.bind(new InetSocketAddress(NetworkStackConstants.IPV6_ADDR_ANY, 0));
        }
        FileDescriptor fileDescriptor$ = socket.getFileDescriptor$();
        if (fileDescriptor$.getInt$() != -1) {
          bindSocketToInterfaceWrapper(i, i3, fileDescriptor$.getInt$(), str2);
        }
        socket.setSoTimeout(10000);
        if (str3 != null) {
          socket.connect(new InetSocketAddress(str3, i2), 10000);
        }
      } catch (Exception unused4) {
        socket2 = socket;
        Log.e(
            "KnoxVpnPacProcessor",
            "IO Exception occured while trying to connect to the pac url remote address " + str3);
        if (socket2 != null) {
          try {
            socket2.close();
            socket2 = null;
          } catch (IOException unused5) {
          }
        } else {
          socket = socket2;
          if (socket == null) {}
          socket2 = socket;
        }
      }
      if (socket == null && socket.isConnected()) {
        return socket;
      }
      socket2 = socket;
    }
    return socket2;
  }

  public final void downloadPacUrl(String str, String str2, String str3, int i, String str4) {
    BufferedReader bufferedReader;
    PrintStream printStream;
    VpnProfileInfo profileEntry;
    boolean z;
    synchronized (str) {
      if (getPacUrlDownloadStatus(str)) {
        return;
      }
      Socket socket = null;
      try {
        try {
          profileEntry = getVpnConfigInstance().getProfileEntry(str);
        } catch (Throwable th) {
          th = th;
        }
      } catch (IOException unused) {
        bufferedReader = null;
        printStream = null;
      } catch (IllegalArgumentException unused2) {
        bufferedReader = null;
        printStream = null;
      } catch (ConnectException unused3) {
        bufferedReader = null;
        printStream = null;
      } catch (SocketException unused4) {
        bufferedReader = null;
        printStream = null;
      } catch (UnknownHostException unused5) {
        bufferedReader = null;
        printStream = null;
      } catch (ClosedByInterruptException unused6) {
        bufferedReader = null;
        printStream = null;
      } catch (Exception unused7) {
        bufferedReader = null;
        printStream = null;
      } catch (Throwable th2) {
        th = th2;
        bufferedReader = null;
        printStream = null;
      }
      if (profileEntry == null) {
        closeExistingConnection(null, null, null);
        return;
      }
      int netId = profileEntry.getNetId();
      if (str4 == null || netId == -1) {
        bufferedReader = null;
        printStream = null;
      } else {
        if (DBG) {
          Log.d("KnoxVpnPacProcessor", "The host name to resolve is " + str2);
        }
        Socket validEndPointAddress =
            getValidEndPointAddress(profileEntry.getVpnType(), str2, i, str4, netId);
        try {
          if (validEndPointAddress == null) {
            throw new ConnectException();
          }
          StringBuilder sb = new StringBuilder();
          InputStream inputStream = validEndPointAddress.getInputStream();
          printStream = new PrintStream(validEndPointAddress.getOutputStream());
          try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
              printStream.println("GET " + str3 + " HTTP/1.1\r");
              printStream.println("Host: " + str2 + "\r");
              printStream.println("Connection: close\r");
              printStream.println("\r");
              printStream.flush();
              String readLine = bufferedReader.readLine();
              boolean z2 = false;
              while (readLine != null) {
                if (readLine.isEmpty()) {
                  readLine = bufferedReader.readLine();
                  z2 = true;
                } else {
                  if (z2) {
                    if (DBG) {
                      Log.d("KnoxVpnPacProcessor", "The pac line printed is " + readLine);
                    }
                    sb.append(readLine.trim());
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                  }
                  readLine = bufferedReader.readLine();
                }
              }
              String sb2 = sb.toString();
              if (sb2 == null || sb2.length() <= 0) {
                getDownloadStatus().put(str, 1);
              } else {
                try {
                  z = getProxyService(getConfiguredUser(str)).setPacFileForKnoxProfile(str, sb2);
                } catch (NullPointerException unused8) {
                  Log.e(
                      "KnoxVpnPacProcessor",
                      "Exception occured due to the pac service not intialized");
                  closeExistingConnection(validEndPointAddress, bufferedReader, printStream);
                  getDownloadStatus().put(str, 2);
                  checkIfRetryNeeded(str);
                  z = false;
                }
                if (z) {
                  getDownloadStatus().put(str, 0);
                  if (getCurrentRetryStatus().containsKey(str)) {
                    Log.d(
                        "KnoxVpnPacProcessor",
                        "The pac file has been downloaded successfully,cancelling further retry"
                            + " attempts");
                    getCurrentRetryStatus().remove(str);
                  }
                  removeDownloadThreadDetails(str);
                  Log.d(
                      "KnoxVpnPacProcessor",
                      "The Misc value for the pac file has been set successfully "
                          + setMiscValueForPacProfile(str, str4, netId));
                  sendPacServiceStatus(1, str);
                } else {
                  closeExistingConnection(validEndPointAddress, bufferedReader, printStream);
                  getDownloadStatus().put(str, 2);
                  checkIfRetryNeeded(str);
                }
              }
              socket = validEndPointAddress;
            } catch (IllegalArgumentException unused9) {
              socket = validEndPointAddress;
              Log.e("KnoxVpnPacProcessor", "cannot find the dns server to resolve the pac url");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 1);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (ConnectException unused10) {
              socket = validEndPointAddress;
              Log.e(
                  "KnoxVpnPacProcessor",
                  "ConnectException occured while trying to download the pac url");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 2);
              checkIfRetryNeeded(str);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (SocketException unused11) {
              socket = validEndPointAddress;
              Log.e(
                  "KnoxVpnPacProcessor",
                  "SocketException occured while trying to download the pac url");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 2);
              checkIfRetryNeeded(str);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (UnknownHostException unused12) {
              socket = validEndPointAddress;
              Log.e(
                  "KnoxVpnPacProcessor",
                  "UnknownHostException occured while trying to download the pac url ");
              try {
                str.wait(5000L);
                closeExistingConnection(socket, bufferedReader, printStream);
                getDownloadStatus().put(str, 2);
                checkIfRetryNeeded(str);
                closeExistingConnection(socket, bufferedReader, printStream);
              } catch (InterruptedException unused13) {
                Log.e(
                    "KnoxVpnPacProcessor",
                    "Got Interruption while trying to resolve the domain name ");
                closeExistingConnection(socket, bufferedReader, printStream);
                getDownloadStatus().put(str, 2);
                closeExistingConnection(socket, bufferedReader, printStream);
                return;
              }
            } catch (ClosedByInterruptException unused14) {
              socket = validEndPointAddress;
              Log.e(
                  "KnoxVpnPacProcessor",
                  "Downloading the pac url failed due to Interruption, close the existing"
                      + " connections");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 2);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (IOException unused15) {
              socket = validEndPointAddress;
              Log.e(
                  "KnoxVpnPacProcessor",
                  "IOException occured while trying to download the pac url ");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 2);
              checkIfRetryNeeded(str);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (Exception unused16) {
              socket = validEndPointAddress;
              Log.e("KnoxVpnPacProcessor", "Exception occured while trying to pac url ");
              closeExistingConnection(socket, bufferedReader, printStream);
              getDownloadStatus().put(str, 1);
              closeExistingConnection(socket, bufferedReader, printStream);
            } catch (Throwable th3) {
              th = th3;
              socket = validEndPointAddress;
              closeExistingConnection(socket, bufferedReader, printStream);
              throw th;
            }
          } catch (IllegalArgumentException unused17) {
            bufferedReader = null;
          } catch (ConnectException unused18) {
            bufferedReader = null;
          } catch (SocketException unused19) {
            bufferedReader = null;
          } catch (UnknownHostException unused20) {
            bufferedReader = null;
          } catch (ClosedByInterruptException unused21) {
            bufferedReader = null;
          } catch (IOException unused22) {
            bufferedReader = null;
          } catch (Exception unused23) {
            bufferedReader = null;
          } catch (Throwable th4) {
            th = th4;
            bufferedReader = null;
          }
        } catch (IllegalArgumentException unused24) {
          bufferedReader = null;
          printStream = null;
        } catch (ConnectException unused25) {
          bufferedReader = null;
          printStream = null;
        } catch (SocketException unused26) {
          bufferedReader = null;
          printStream = null;
        } catch (UnknownHostException unused27) {
          bufferedReader = null;
          printStream = null;
        } catch (ClosedByInterruptException unused28) {
          bufferedReader = null;
          printStream = null;
        } catch (IOException unused29) {
          bufferedReader = null;
          printStream = null;
        } catch (Exception unused30) {
          bufferedReader = null;
          printStream = null;
        } catch (Throwable th5) {
          th = th5;
          bufferedReader = null;
          printStream = null;
        }
      }
      closeExistingConnection(socket, bufferedReader, printStream);
    }
  }

  public final void closeExistingConnection(
      Socket socket, BufferedReader bufferedReader, PrintStream printStream) {
    if (socket != null) {
      try {
        socket.close();
      } catch (IOException unused) {
        Log.e("KnoxVpnPacProcessor", "IOException occured while trying to close the socket");
        return;
      }
    }
    if (printStream != null) {
      printStream.close();
    }
    if (bufferedReader != null) {
      bufferedReader.close();
    }
  }

  public final void checkIfRetryNeeded(String str) {
    try {
      VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
      if (profileEntry != null
          && profileEntry.getPackageCount() > 0
          && profileEntry.getActivateState() == 1) {
        if (getCurrentRetryStatus().containsKey(str)) {
          if (((Integer) getCurrentRetryStatus().get(str)).intValue() < 10) {
            getCurrentRetryStatus()
                .put(
                    str,
                    Integer.valueOf(((Integer) getCurrentRetryStatus().get(str)).intValue() + 1));
            Log.d(
                "KnoxVpnPacProcessor",
                "Downloading the pac url failed, going to retry for the "
                    + getCurrentRetryStatus().get(str)
                    + "attempt "
                    + str);
            try {
              Thread.currentThread();
              Thread.sleep(5000L);
            } catch (InterruptedException unused) {
            }
            startRetryAttempt(profileEntry);
            return;
          }
          if (((Integer) getCurrentRetryStatus().get(str)).intValue() == 10) {
            Log.d(
                "KnoxVpnPacProcessor",
                "Downloading the pac url failed even after the final retry attempt " + str);
            removeDownloadThreadDetails(str);
            return;
          }
          return;
        }
        Log.d(
            "KnoxVpnPacProcessor",
            "Downloading the pac url failed for the first time, start the retry process " + str);
        getCurrentRetryStatus().put(str, 0);
        startRetryAttempt(profileEntry);
        return;
      }
      Log.d(
          "KnoxVpnPacProcessor",
          "vpn profile is in unknown state, resetting the retry-Status to default value " + str);
      if (getCurrentRetryStatus().containsKey(str)) {
        getCurrentRetryStatus().remove(str);
      }
    } catch (Exception unused2) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Exception occured while retry attempt to download pac file " + str);
    }
  }

  public final void removeDownloadThreadDetails(String str) {
    if (getDownloadThread().get(str) != null) {
      getDownloadThread().remove(str);
    }
  }

  public final void startRetryAttempt(VpnProfileInfo vpnProfileInfo) {
    String str = null;
    try {
      str = vpnProfileInfo.getProfileName();
      String interfaceName = vpnProfileInfo.getInterfaceName();
      String uri = vpnProfileInfo.getPacurl().toString();
      URL url = new URL(uri);
      String host = url.getHost();
      String path = url.getPath();
      String protocol = url.getProtocol();
      int port = url.getPort();
      if (port == -1) {
        port = getPortFromProtocol(protocol);
      }
      int i = port;
      if (str == null
          || interfaceName == null
          || uri == null
          || ((Integer) getDownloadStatus().get(str)).intValue() != 2) {
        return;
      }
      downloadPacUrl(str, host, path, i, interfaceName);
    } catch (IllegalArgumentException unused) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "retry attempt: Invalid port value is defined when trying to download the pac url");
    } catch (NullPointerException unused2) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "retry attempt: Some profile values are not intialized when trying to download the pac"
              + " url");
    } catch (MalformedURLException unused3) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "retry attempt: error occured while trying to download the pac file due to unknow url"
              + " format");
    } catch (Exception unused4) {
      getDownloadStatus().put(str, 1);
      Log.e(
          "KnoxVpnPacProcessor",
          "retry attempt: error occured while trying to download the pac file");
    }
  }

  public void bindSocketToInterfaceWrapper(int i, int i2, int i3, String str) {
    try {
      Log.d(
          "KnoxVpnPacProcessor", "bindResult value is " + jnibindSocketToInterface(i, i2, i3, str));
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnPacProcessor",
          "Exception occured while trying to bind the socket to the interface ");
    }
  }

  public final int getConfiguredUser(String str) {
    VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
    if (profileEntry != null) {
      return profileEntry.getPersonaId();
    }
    return 0;
  }

  public class DownloadUrlThread extends Thread {
    public String hostname;
    public String interfaceName;
    public String path;
    public int port;
    public String profileName;

    public DownloadUrlThread(String str, String str2, String str3, int i, String str4) {
      this.profileName = str;
      this.hostname = str2;
      this.path = str3;
      this.port = i;
      this.interfaceName = str4;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      try {
        KnoxVpnPacProcessor.this.downloadPacUrl(
            this.profileName, this.hostname, this.path, this.port, this.interfaceName);
      } catch (IllegalArgumentException unused) {
        Log.e(
            "KnoxVpnPacProcessor",
            "Exception occured while downloading the pac url due to unknown format");
        KnoxVpnPacProcessor.getDownloadStatus().put(this.profileName, 1);
      } catch (Exception unused2) {
        Log.e(
            "KnoxVpnPacProcessor",
            "Exception occured while trying to start the download thread to start downloading the"
                + " pac url ");
      }
    }
  }
}
