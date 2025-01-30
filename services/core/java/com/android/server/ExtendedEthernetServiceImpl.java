package com.android.server;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.net.BaseNetworkObserver;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.net.IExtendedEthernetManager;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ExtendedEthernetServiceImpl extends IExtendedEthernetManager.Stub {
  public ExtendedEthernetConfigStore mConfigStore;
  public final Context mContext;
  public EthernetManager mEthernetManager;
  public boolean mIsNotiShown;
  public INetworkManagementService mNMService;
  public EthernetManager.TetheredInterfaceRequest mTetheredRequest;
  public TetheringManager mTetheringManager;
  public Handler mHandler = new Handler();
  public String mIfaceMatch = "eth\\d";
  public int mInterfaceMode = 1;
  public final ConcurrentHashMap mIpConfigurations = new ConcurrentHashMap();
  public String mLastIntentState = "Disconnected";
  public EthernetManager.TetheredInterfaceCallback mEthernetCallback =
      new EthernetManager.TetheredInterfaceCallback() { // from class:
        // com.android.server.ExtendedEthernetServiceImpl.1
        public void onAvailable(String str) {
          Log.i("ExtendedEthernetServiceImpl", "TetheredInterfaceCallback onAvailable: " + str);
          if ("on"
              .equals(
                  Settings.System.getString(
                      ExtendedEthernetServiceImpl.this.mContext.getContentResolver(),
                      "ETHERNET_TETHERING_MODE"))) {
            TetheringManager.StartTetheringCallback startTetheringCallback =
                new TetheringManager.StartTetheringCallback() { // from class:
                  // com.android.server.ExtendedEthernetServiceImpl.1.1
                  public void onTetheringFailed(int i) {
                    Log.e("ExtendedEthernetServiceImpl", "onTetheringFailed resultCode: " + i);
                  }
                };
            TetheringManager.TetheringRequest build =
                new TetheringManager.TetheringRequest.Builder(5).build();
            TetheringManager tetheringManager = ExtendedEthernetServiceImpl.this.mTetheringManager;
            final Handler handler = ExtendedEthernetServiceImpl.this.mHandler;
            Objects.requireNonNull(handler);
            tetheringManager.startTethering(
                build,
                new Executor() { // from class:
                  // com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda0
                  @Override // java.util.concurrent.Executor
                  public final void execute(Runnable runnable) {
                    handler.post(runnable);
                  }
                },
                startTetheringCallback);
          }
        }

        public void onUnavailable() {
          Log.i("ExtendedEthernetServiceImpl", "TetheredInterfaceCallback onUnavailable");
        }
      };
  public EthernetManager.InterfaceStateListener mStateListener =
      new EthernetManager.InterfaceStateListener() { // from class:
        // com.android.server.ExtendedEthernetServiceImpl.2
        public void onInterfaceStateChanged(
            String str, int i, int i2, IpConfiguration ipConfiguration) {
          Log.i(
              "ExtendedEthernetServiceImpl",
              "onInterfaceStateChanged " + i2 + ", state: " + i + ", conf: " + ipConfiguration);
          ExtendedEthernetServiceImpl.this.mInterfaceMode = i2;
          if (ExtendedEthernetServiceImpl.this.mInterfaceMode == 1) {
            ExtendedEthernetServiceImpl.this.updateEthCableConnectNotification(i == 2);
          } else {
            ExtendedEthernetServiceImpl.this.updateEthCableConnectNotification(false);
          }
        }
      };
  public ContentObserver mStateObserver =
      new ContentObserver(
          new Handler()) { // from class: com.android.server.ExtendedEthernetServiceImpl.3
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
          boolean z2 =
              Settings.System.getIntForUser(
                      ExtendedEthernetServiceImpl.this.mContext.getContentResolver(),
                      "eth_disabled",
                      0,
                      0)
                  == 1;
          Log.i("ExtendedEthernetServiceImpl", "ETH_DISABLED is changed to " + z2);
          if (z2) {
            ExtendedEthernetServiceImpl.this.setLinkDown();
          }
        }
      };

  public ExtendedEthernetServiceImpl(Context context) {
    this.mContext = context;
    INetworkManagementService asInterface =
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    this.mNMService = asInterface;
    try {
      asInterface.registerObserver(new InterfaceObserver());
    } catch (RemoteException e) {
      Log.e("ExtendedEthernetServiceImpl", "Could not register InterfaceObserver " + e);
    }
  }

  public IpConfiguration getConfiguration(String str) {
    Log.d("ExtendedEthernetServiceImpl", "getConfiguration for: " + str);
    this.mConfigStore.read();
    ArrayMap ipConfigurations = this.mConfigStore.getIpConfigurations();
    for (int i = 0; i < ipConfigurations.size(); i++) {
      this.mIpConfigurations.put(
          (String) ipConfigurations.keyAt(i), (IpConfiguration) ipConfigurations.valueAt(i));
    }
    IpConfiguration ipConfiguration = (IpConfiguration) this.mIpConfigurations.get(str);
    if (ipConfiguration != null) {
      return ipConfiguration;
    }
    IpConfiguration ipConfiguration2 = new IpConfiguration();
    ipConfiguration2.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
    ipConfiguration2.setProxySettings(IpConfiguration.ProxySettings.NONE);
    return ipConfiguration2;
  }

  public void handleSystemReady() {
    Log.i("ExtendedEthernetServiceImpl", "handleSystemReady");
    this.mTetheringManager = (TetheringManager) this.mContext.getSystemService("tethering");
    EthernetManager ethernetManager =
        (EthernetManager) this.mContext.getSystemService(KnoxCustomManagerService.ETHERNET_SERVICE);
    this.mEthernetManager = ethernetManager;
    ethernetManager.addInterfaceStateListener(
        new HandlerExecutor(this.mHandler), this.mStateListener);
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if ("on".equals(Settings.System.getString(contentResolver, "ETHERNET_TETHERING_MODE"))) {
      Log.i("ExtendedEthernetServiceImpl", "ETHERNET_TETHERING_MODE is on");
      this.mTetheredRequest =
          this.mEthernetManager.requestTetheredInterface(
              new HandlerExecutor(this.mHandler), this.mEthernetCallback);
    }
    try {
      boolean z = true;
      if (Settings.System.getInt(contentResolver, "eth_disabled") != 1) {
        z = false;
      }
      if (z) {
        this.mEthernetManager.setEthernetEnabled(false);
        Log.e("ExtendedEthernetServiceImpl", "call setLinkDown");
        setLinkDown();
      }
    } catch (Settings.SettingNotFoundException unused) {
      Log.i("ExtendedEthernetServiceImpl", "Not found ETH_DISABLED");
      Settings.System.putInt(contentResolver, "eth_disabled", 0);
    }
    ExtendedEthernetConfigStore extendedEthernetConfigStore = new ExtendedEthernetConfigStore();
    this.mConfigStore = extendedEthernetConfigStore;
    extendedEthernetConfigStore.read();
    ArrayMap ipConfigurations = this.mConfigStore.getIpConfigurations();
    for (int i = 0; i < ipConfigurations.size(); i++) {
      this.mIpConfigurations.put(
          (String) ipConfigurations.keyAt(i), (IpConfiguration) ipConfigurations.valueAt(i));
    }
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("eth_disabled"), false, this.mStateObserver, 0);
  }

  public final void setLinkDown() {
    try {
      for (String str : this.mNMService.listInterfaces()) {
        Log.e("ExtendedEthernetServiceImpl", "iface: " + str);
        if (str.matches(this.mIfaceMatch)) {
          Log.e("ExtendedEthernetServiceImpl", "call setInterfaceDown");
          this.mNMService.setInterfaceDown(str);
        }
      }
    } catch (RemoteException | IllegalStateException unused) {
    }
  }

  public final void updateEthCableConnectNotification(boolean z) {
    if (this.mIsNotiShown == z) {
      return;
    }
    Log.i("ExtendedEthernetServiceImpl", "updateEthCableConnectNotification " + z);
    NotificationManager notificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    if (notificationManager == null) {
      return;
    }
    this.mIsNotiShown = z;
    if (z) {
      String string = this.mContext.getResources().getString(R.string.indeterminate_progress_15);
      String string2 = this.mContext.getResources().getString(R.string.indeterminate_progress_14);
      Intent intent = new Intent();
      intent.setClassName(
          "com.android.settings", "com.android.settings.Settings$EthernetSettingsActivity");
      intent.setAction("com.samsung.settings.ETHERNET_SETTINGS");
      intent.setFlags(268468224);
      PendingIntent activityAsUser =
          PendingIntent.getActivityAsUser(
              this.mContext, 0, intent, 33554432, null, UserHandle.CURRENT);
      Notification.Builder builder =
          new Notification.Builder(this.mContext, SystemNotificationChannels.ETHERNET);
      builder
          .setSmallIcon(17304208)
          .setWhen(0L)
          .setOngoing(true)
          .setTicker(string)
          .setDefaults(0)
          .setPriority(1)
          .setContentTitle(string)
          .setContentText(string2)
          .setContentIntent(activityAsUser);
      notificationManager.notifyAsUser(
          null, R.string.indeterminate_progress_15, builder.build(), UserHandle.ALL);
      return;
    }
    notificationManager.cancelAsUser(null, R.string.indeterminate_progress_15, UserHandle.ALL);
  }

  public final void onInterfaceAdded(String str) {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    Settings.System.putIntForUser(contentResolver, "eth_device_conn", 2, 0);
    Log.i(
        "ExtendedEthernetServiceImpl",
        "ETH_DEVICE_CONNECTED is : "
            + Settings.System.getIntForUser(contentResolver, "eth_device_conn", 0, 0));
    if ("on".equals(Settings.System.getString(contentResolver, "ETHERNET_TETHERING_MODE"))
        && this.mTetheredRequest == null) {
      Log.i("ExtendedEthernetServiceImpl", "onChange() call requestTetheredInterface()");
      this.mTetheredRequest =
          this.mEthernetManager.requestTetheredInterface(
              new HandlerExecutor(this.mHandler), this.mEthernetCallback);
    }
  }

  public final void onInterfaceRemoved(String str) {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    Settings.System.putIntForUser(contentResolver, "eth_device_conn", 1, 0);
    Log.i(
        "ExtendedEthernetServiceImpl",
        "ETH_DEVICE_CONNECTED is : "
            + Settings.System.getIntForUser(contentResolver, "eth_device_conn", 0, 0));
    this.mTetheredRequest = null;
  }

  public final void onInterfaceLinkStateChanged(String str, boolean z) {
    sendIntent(z);
  }

  public final void sendIntent(boolean z) {
    String str = z ? "Connected" : "Disconnected";
    if (str.equals(this.mLastIntentState)) {
      return;
    }
    this.mLastIntentState = str;
    Intent intent = new Intent("samsung.net.ethernet.ETH_STATE_CHANGED");
    intent.addFlags(67108864);
    intent.putExtra("eth_state", this.mLastIntentState);
    intent.setPackage("com.android.settings");
    Log.i("ExtendedEthernetServiceImpl", "send intent: " + str);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  public class InterfaceObserver extends BaseNetworkObserver {
    public InterfaceObserver() {}

    public void interfaceAdded(final String str) {
      if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
        Log.i("ExtendedEthernetServiceImpl", "interfaceAdded: " + str);
        ExtendedEthernetServiceImpl.this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                ExtendedEthernetServiceImpl.InterfaceObserver.this.lambda$interfaceAdded$0(str);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$interfaceAdded$0(String str) {
      ExtendedEthernetServiceImpl.this.onInterfaceAdded(str);
    }

    public void interfaceRemoved(final String str) {
      if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
        Log.i("ExtendedEthernetServiceImpl", "interfaceRemoved: " + str);
        ExtendedEthernetServiceImpl.this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                ExtendedEthernetServiceImpl.InterfaceObserver.this.lambda$interfaceRemoved$1(str);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$interfaceRemoved$1(String str) {
      ExtendedEthernetServiceImpl.this.onInterfaceRemoved(str);
    }

    public void interfaceLinkStateChanged(final String str, final boolean z) {
      if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
        Log.i("ExtendedEthernetServiceImpl", "interfaceLinkStateChanged: " + str + ", " + z);
        ExtendedEthernetServiceImpl.this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                ExtendedEthernetServiceImpl.InterfaceObserver.this
                    .lambda$interfaceLinkStateChanged$2(str, z);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$interfaceLinkStateChanged$2(String str, boolean z) {
      ExtendedEthernetServiceImpl.this.onInterfaceLinkStateChanged(str, z);
    }
  }
}
