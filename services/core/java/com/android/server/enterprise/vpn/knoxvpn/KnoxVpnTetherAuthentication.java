package com.android.server.enterprise.vpn.knoxvpn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.IVpnManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus;
import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class KnoxVpnTetherAuthentication {
  public static Context mContext;
  public static KnoxVpnTetherAuthentication mKnoxVpnTetherAuthentication;
  public KnoxVpnFirewallHelper mFirewallHelper;
  public KnoxVpnHelper mKnoxVpnHelper;
  public Object syncObject;
  public HashMap mTetherAuthConnectionList = new HashMap();
  public List mknoxVpnTetherAuthClientStatus = new ArrayList();
  public Bundle mtetherAuthDetails = null;
  public String mProfileName = null;
  public volatile int mBindUserId = -1;
  public IKnoxVpnTetherAuthInterface mTetherAuthService = null;
  public CountDownLatch mCountDownLatch = null;
  public volatile boolean isTetherAuthSuccessful = false;
  public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();
  public volatile boolean isCallbackReceived = true;

  public KnoxVpnTetherAuthentication(Context context) {
    this.mKnoxVpnHelper = null;
    this.mFirewallHelper = null;
    this.syncObject = null;
    mContext = context;
    this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(context);
    this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
    this.syncObject = new Object();
  }

  public final IVpnManager getVpnManagerService() {
    return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
  }

  public class TetherAuthConnection implements ServiceConnection {
    public TetherAuthConnection() {}

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "onServicedisconnected is reached for user "
              + KnoxVpnTetherAuthentication.this.mBindUserId);
      KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus.add(
          "onServiceDisconnected callback has been recieved for tetherAuth client in user "
              + KnoxVpnTetherAuthentication.this.mBindUserId
              + " and for profile "
              + KnoxVpnTetherAuthentication.this.mProfileName
              + " at "
              + System.currentTimeMillis());
      KnoxVpnTetherAuthentication.this.mTetherAuthService = null;
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "onBindingDied is reached for user " + KnoxVpnTetherAuthentication.this.mBindUserId);
      KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus.add(
          "onBindingDied callback has been recieved for tetherAuth client in user "
              + KnoxVpnTetherAuthentication.this.mBindUserId
              + " and for profile "
              + KnoxVpnTetherAuthentication.this.mProfileName
              + " at "
              + System.currentTimeMillis());
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      VpnProfileInfo profileEntry;
      Log.d(
          "KnoxVpnTetherAuthentication",
          "onServiceConnected is reached for user " + KnoxVpnTetherAuthentication.this.mBindUserId);
      KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus.add(
          "onServiceConnected callback has been recieved for tetherAuth client in user "
              + KnoxVpnTetherAuthentication.this.mBindUserId
              + " and for profile "
              + KnoxVpnTetherAuthentication.this.mProfileName
              + " at "
              + System.currentTimeMillis());
      synchronized (KnoxVpnTetherAuthentication.this.syncObject) {
        KnoxVpnTetherAuthentication.this.mTetherAuthService =
            IKnoxVpnTetherAuthInterface.Stub.asInterface(iBinder);
        try {
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setHtmlSignInPage(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-auth-login-page"));
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setHtmlResponsePage(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-auth-response-page"));
          Bundle bundle = new Bundle();
          bundle.putString(
              "key-tether-client-certificate-issuer-cn",
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-client-certificate-issuer-cn"));
          bundle.putString(
              "key-tether-client-certificate-issued-cn",
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-client-certificate-issued-cn"));
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setClientAuthDetails(bundle);
          String retrieveProfileCredentials =
              KnoxVpnTetherAuthentication.this.mKnoxVpnHelper.retrieveProfileCredentials(
                  KnoxVpnTetherAuthentication.this.mProfileName, "captivecert_pwd");
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setCaptivePortalAlias(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-captive-portal-alias"));
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setCaptivePortalCertificate(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getByteArray(
                  "key-tether-captive-portal-certificate"),
              retrieveProfileCredentials);
          String retrieveProfileCredentials2 =
              KnoxVpnTetherAuthentication.this.mKnoxVpnHelper.retrieveProfileCredentials(
                  KnoxVpnTetherAuthentication.this.mProfileName, "cacert_pwd");
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setCaAlias(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString("key-tether-ca-alias"));
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setCACertificate(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getByteArray(
                  "key-tether-ca-certificate"),
              retrieveProfileCredentials2);
          String retrieveProfileCredentials3 =
              KnoxVpnTetherAuthentication.this.mKnoxVpnHelper.retrieveProfileCredentials(
                  KnoxVpnTetherAuthentication.this.mProfileName, "servercert_pwd");
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setServerAlias(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getString(
                  "key-tether-user-alias"));
          KnoxVpnTetherAuthentication.this.mTetherAuthService.setServerCertificate(
              KnoxVpnTetherAuthentication.this.mtetherAuthDetails.getByteArray(
                  "key-tether-user-certificate"),
              retrieveProfileCredentials3);
          if (!KnoxVpnTetherAuthentication.this.isTetherAuthSuccessful
              && (profileEntry =
                      KnoxVpnTetherAuthentication.this.vpnConfig.getProfileEntry(
                          KnoxVpnTetherAuthentication.this.mProfileName))
                  != null) {
            String interfaceNameForUsbtethering =
                KnoxVpnTetherAuthentication.this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
            String interfaceName = profileEntry.getInterfaceName();
            int activateState = profileEntry.getActivateState();
            if (interfaceNameForUsbtethering != null
                && interfaceName != null
                && activateState == 1) {
              Log.d(
                  "KnoxVpnTetherAuthentication",
                  "start tether auth process after onServiceConnected event");
              KnoxVpnTetherAuthentication.this.startTetherAuthProcess(
                  profileEntry.getPersonaId(),
                  interfaceNameForUsbtethering,
                  KnoxVpnTetherAuthentication.this.mKnoxVpnHelper
                      .getIpAddressForUsbTetheringInterface());
            }
          }
        } catch (RemoteException e) {
          Log.e(
              "KnoxVpnTetherAuthentication",
              "Error happened during remote process communication with the application "
                  + Log.getStackTraceString(e));
        }
        if (KnoxVpnTetherAuthentication.this.mCountDownLatch != null) {
          KnoxVpnTetherAuthentication.this.mCountDownLatch.countDown();
        }
      }
    }
  }

  public class AuthStatusCallback extends IAuthenticationStatus.Stub {
    public AuthStatusCallback() {}

    public void getStatus(int i) {
      KnoxVpnTetherAuthentication.this.isCallbackReceived = true;
      if (i == 0) {
        synchronized (KnoxVpnTetherAuthentication.this.syncObject) {
          KnoxVpnTetherAuthentication.this.isTetherAuthSuccessful = true;
          try {
            KnoxVpnTetherAuthentication.this.stopTetheringAuthProcess(false);
          } catch (RemoteException unused) {
            Log.e(
                "KnoxVpnTetherAuthentication",
                "unknown error occured when tried to stop the tether auth process");
          }
          KnoxVpnTetherAuthentication.this.applyTetheringRulesForVpn();
        }
      }
    }
  }

  public static synchronized KnoxVpnTetherAuthentication getInstance(Context context) {
    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication;
    synchronized (KnoxVpnTetherAuthentication.class) {
      if (mKnoxVpnTetherAuthentication == null) {
        mKnoxVpnTetherAuthentication = new KnoxVpnTetherAuthentication(context);
      }
      knoxVpnTetherAuthentication = mKnoxVpnTetherAuthentication;
    }
    return knoxVpnTetherAuthentication;
  }

  public void bindTetherAuthService(String str, int i, Bundle bundle) {
    Log.d("KnoxVpnTetherAuthentication", "Binding to the tetherAuth service in user " + i);
    this.mtetherAuthDetails = bundle;
    this.mProfileName = str;
    this.mBindUserId = i;
    if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "unbinding previous service before binding again for the user " + this.mBindUserId);
      try {
        mContext.unbindService(
            (ServiceConnection)
                this.mTetherAuthConnectionList.get(Integer.valueOf(this.mBindUserId)));
      } catch (Exception unused) {
        Log.e(
            "KnoxVpnTetherAuthentication",
            "unbinding failed since the service is already unbinded or not existing");
      }
      this.mTetherAuthConnectionList.remove(Integer.valueOf(this.mBindUserId));
    }
    TetherAuthConnection tetherAuthConnection = new TetherAuthConnection();
    Intent intent =
        new Intent("com.samsung.knox.vpn.tether.auth_knoxtetheringauthenticationservice");
    intent.setClassName(
        "com.samsung.knox.vpn.tether.auth", "com.samsung.knox.vpn.tether.auth.TetherAuthService");
    if (mContext.bindServiceAsUser(
        intent, tetherAuthConnection, 1073741829, new UserHandle(this.mBindUserId))) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "Binding to the tetherAuth service in user " + this.mBindUserId + " is successful");
      this.mTetherAuthConnectionList.put(Integer.valueOf(this.mBindUserId), tetherAuthConnection);
      try {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mCountDownLatch = countDownLatch;
        countDownLatch.await(7000L, TimeUnit.MILLISECONDS);
        this.mCountDownLatch = null;
      } catch (InterruptedException unused2) {
      }
    }
  }

  public void bindTetherAuthService(String str, int i) {
    Log.d(
        "KnoxVpnTetherAuthentication",
        "Binding to the tetherAuth service in user after tether auth package install " + i);
    if (this.mtetherAuthDetails == null) {
      return;
    }
    this.mProfileName = str;
    this.mBindUserId = i;
    if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "unbinding previous service before binding again for the user " + this.mBindUserId);
      try {
        mContext.unbindService(
            (ServiceConnection)
                this.mTetherAuthConnectionList.get(Integer.valueOf(this.mBindUserId)));
      } catch (Exception unused) {
        Log.e(
            "KnoxVpnTetherAuthentication",
            "unbinding failed since the service is already unbinded or not existing");
      }
      this.mTetherAuthConnectionList.remove(Integer.valueOf(this.mBindUserId));
    }
    TetherAuthConnection tetherAuthConnection = new TetherAuthConnection();
    Intent intent =
        new Intent("com.samsung.knox.vpn.tether.auth_knoxtetheringauthenticationservice");
    intent.setClassName(
        "com.samsung.knox.vpn.tether.auth", "com.samsung.knox.vpn.tether.auth.TetherAuthService");
    if (mContext.bindServiceAsUser(
        intent, tetherAuthConnection, 1073741829, new UserHandle(this.mBindUserId))) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "Binding to the tetherAuth service in user " + this.mBindUserId + " is successful");
      this.mTetherAuthConnectionList.put(Integer.valueOf(this.mBindUserId), tetherAuthConnection);
      try {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mCountDownLatch = countDownLatch;
        countDownLatch.await(7000L, TimeUnit.MILLISECONDS);
        this.mCountDownLatch = null;
      } catch (InterruptedException unused2) {
      }
    }
  }

  public void unbindTetherAuthService(boolean z) {
    synchronized (this.syncObject) {
      Log.d(
          "KnoxVpnTetherAuthentication",
          "start unbinding tetherAuth service in user " + this.mBindUserId);
      if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
        IKnoxVpnTetherAuthInterface iKnoxVpnTetherAuthInterface = this.mTetherAuthService;
        if (iKnoxVpnTetherAuthInterface != null) {
          Log.d(
              "KnoxVpnTetherAuthentication",
              "stopAuthenticationProcess before unbind and result is "
                  + iKnoxVpnTetherAuthInterface.stopAuthenticationProcess());
        }
        mContext.unbindService(
            (ServiceConnection)
                this.mTetherAuthConnectionList.get(Integer.valueOf(this.mBindUserId)));
        Log.d(
            "KnoxVpnTetherAuthentication",
            "unbinding tetherAuth service in user " + this.mBindUserId);
        this.mTetherAuthConnectionList.remove(Integer.valueOf(this.mBindUserId));
        Log.d(
            "KnoxVpnTetherAuthentication",
            "start removing tether auth firewall rules during unbinding");
        this.mFirewallHelper.removeRulesTetherAuth();
        this.mTetherAuthService = null;
        if (z) {
          this.isTetherAuthSuccessful = false;
        }
      }
    }
  }

  public void startTetherAuthProcess(int i, String str, List list) {
    synchronized (this.syncObject) {
      IKnoxVpnTetherAuthInterface iKnoxVpnTetherAuthInterface = this.mTetherAuthService;
      if (iKnoxVpnTetherAuthInterface != null) {
        Bundle startAuthenticationProcess =
            iKnoxVpnTetherAuthInterface.startAuthenticationProcess(new AuthStatusCallback());
        this.mFirewallHelper.addRulesTetherAuth(
            this.mKnoxVpnHelper.getUIDForPackage(i, "com.samsung.knox.vpn.tether.auth"),
            str,
            list,
            startAuthenticationProcess);
        this.isCallbackReceived = false;
      }
    }
  }

  public void stopTetheringAuthProcess(boolean z) {
    synchronized (this.syncObject) {
      IKnoxVpnTetherAuthInterface iKnoxVpnTetherAuthInterface = this.mTetherAuthService;
      if (iKnoxVpnTetherAuthInterface != null) {
        Log.d(
            "KnoxVpnTetherAuthentication",
            "stopAuthenticationProcess result is "
                + iKnoxVpnTetherAuthInterface.stopAuthenticationProcess());
        this.mFirewallHelper.removeRulesTetherAuth();
        if (z) {
          this.isTetherAuthSuccessful = false;
        }
      }
    }
  }

  public final void applyTetheringRulesForVpn() {
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(this.mProfileName);
      if (profileEntry == null) {
        return;
      }
      String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
      String interfaceName = profileEntry.getInterfaceName();
      int activateState = profileEntry.getActivateState();
      int interfaceType = profileEntry.getInterfaceType();
      if (interfaceNameForUsbtethering != null) {
        if (interfaceName == null && activateState == 1) {
          Log.d(
              "KnoxVpnTetherAuthentication",
              "Applying rules to drop tether packets after tether auth successful due to vpn down");
          this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
        } else if (interfaceName != null && activateState == 1) {
          Log.d(
              "KnoxVpnTetherAuthentication",
              "Applying vpn tethering rules after tether auth successful");
          this.mFirewallHelper.addRulesForUsbTethering(
              interfaceName,
              interfaceType,
              getVpnManagerService().getDnsServerListForInterface(interfaceName),
              interfaceNameForUsbtethering,
              this.mKnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering));
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnTetherAuthentication",
          "Apply tether rules after successful auth failed " + Log.getStackTraceString(e));
    }
  }

  public boolean getTetherAuthStatus() {
    return this.isTetherAuthSuccessful;
  }

  public List getKnoxVpnTetherAuthClientStatus() {
    return this.mknoxVpnTetherAuthClientStatus;
  }
}
