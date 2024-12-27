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

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;

import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus;
import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxVpnTetherAuthentication {
    public static Context mContext;
    public static KnoxVpnTetherAuthentication mKnoxVpnTetherAuthentication;
    public final KnoxVpnFirewallHelper mFirewallHelper;
    public final KnoxVpnHelper mKnoxVpnHelper;
    public final Object syncObject;
    public final HashMap mTetherAuthConnectionList = new HashMap();
    public final List mknoxVpnTetherAuthClientStatus = new ArrayList();
    public Bundle mtetherAuthDetails = null;
    public String mProfileName = null;
    public volatile int mBindUserId = -1;
    public IKnoxVpnTetherAuthInterface mTetherAuthService = null;
    public CountDownLatch mCountDownLatch = null;
    public volatile boolean isTetherAuthSuccessful = false;
    public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthStatusCallback extends IAuthenticationStatus.Stub {
        public AuthStatusCallback() {}

        public final void getStatus(int i) {
            KnoxVpnTetherAuthentication.this.getClass();
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
                    KnoxVpnTetherAuthentication.m568$$Nest$mapplyTetheringRulesForVpn(
                            KnoxVpnTetherAuthentication.this);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TetherAuthConnection implements ServiceConnection {
        public TetherAuthConnection() {}

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onBindingDied is reached for user "),
                    KnoxVpnTetherAuthentication.this.mBindUserId,
                    "KnoxVpnTetherAuthentication");
            ((ArrayList) KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus)
                    .add(
                            "onBindingDied callback has been recieved for tetherAuth client in user"
                                + " "
                                    + KnoxVpnTetherAuthentication.this.mBindUserId
                                    + " and for profile "
                                    + KnoxVpnTetherAuthentication.this.mProfileName
                                    + " at "
                                    + System.currentTimeMillis());
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onServiceConnected is reached for user "),
                    KnoxVpnTetherAuthentication.this.mBindUserId,
                    "KnoxVpnTetherAuthentication");
            ((ArrayList) KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus)
                    .add(
                            "onServiceConnected callback has been recieved for tetherAuth client in"
                                + " user "
                                    + KnoxVpnTetherAuthentication.this.mBindUserId
                                    + " and for profile "
                                    + KnoxVpnTetherAuthentication.this.mProfileName
                                    + " at "
                                    + System.currentTimeMillis());
            synchronized (KnoxVpnTetherAuthentication.this.syncObject) {
                KnoxVpnTetherAuthentication.this.mTetherAuthService =
                        IKnoxVpnTetherAuthInterface.Stub.asInterface(iBinder);
                try {
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication.mTetherAuthService.setHtmlSignInPage(
                            knoxVpnTetherAuthentication.mtetherAuthDetails.getString(
                                    "key-tether-auth-login-page"));
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication2 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication2.mTetherAuthService.setHtmlResponsePage(
                            knoxVpnTetherAuthentication2.mtetherAuthDetails.getString(
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
                    KnoxVpnTetherAuthentication.this.mTetherAuthService.setClientAuthDetails(
                            bundle);
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication3 =
                            KnoxVpnTetherAuthentication.this;
                    KnoxVpnHelper knoxVpnHelper = knoxVpnTetherAuthentication3.mKnoxVpnHelper;
                    String str = knoxVpnTetherAuthentication3.mProfileName;
                    knoxVpnHelper.getClass();
                    String retrieveProfileCredentials =
                            KnoxVpnHelper.retrieveProfileCredentials(str, "captivecert_pwd");
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication4 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication4.mTetherAuthService.setCaptivePortalAlias(
                            knoxVpnTetherAuthentication4.mtetherAuthDetails.getString(
                                    "key-tether-captive-portal-alias"));
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication5 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication5.mTetherAuthService.setCaptivePortalCertificate(
                            knoxVpnTetherAuthentication5.mtetherAuthDetails.getByteArray(
                                    "key-tether-captive-portal-certificate"),
                            retrieveProfileCredentials);
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication6 =
                            KnoxVpnTetherAuthentication.this;
                    KnoxVpnHelper knoxVpnHelper2 = knoxVpnTetherAuthentication6.mKnoxVpnHelper;
                    String str2 = knoxVpnTetherAuthentication6.mProfileName;
                    knoxVpnHelper2.getClass();
                    String retrieveProfileCredentials2 =
                            KnoxVpnHelper.retrieveProfileCredentials(str2, "cacert_pwd");
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication7 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication7.mTetherAuthService.setCaAlias(
                            knoxVpnTetherAuthentication7.mtetherAuthDetails.getString(
                                    "key-tether-ca-alias"));
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication8 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication8.mTetherAuthService.setCACertificate(
                            knoxVpnTetherAuthentication8.mtetherAuthDetails.getByteArray(
                                    "key-tether-ca-certificate"),
                            retrieveProfileCredentials2);
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication9 =
                            KnoxVpnTetherAuthentication.this;
                    KnoxVpnHelper knoxVpnHelper3 = knoxVpnTetherAuthentication9.mKnoxVpnHelper;
                    String str3 = knoxVpnTetherAuthentication9.mProfileName;
                    knoxVpnHelper3.getClass();
                    String retrieveProfileCredentials3 =
                            KnoxVpnHelper.retrieveProfileCredentials(str3, "servercert_pwd");
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication10 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication10.mTetherAuthService.setServerAlias(
                            knoxVpnTetherAuthentication10.mtetherAuthDetails.getString(
                                    "key-tether-user-alias"));
                    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication11 =
                            KnoxVpnTetherAuthentication.this;
                    knoxVpnTetherAuthentication11.mTetherAuthService.setServerCertificate(
                            knoxVpnTetherAuthentication11.mtetherAuthDetails.getByteArray(
                                    "key-tether-user-certificate"),
                            retrieveProfileCredentials3);
                    if (!KnoxVpnTetherAuthentication.this.isTetherAuthSuccessful) {
                        KnoxVpnTetherAuthentication knoxVpnTetherAuthentication12 =
                                KnoxVpnTetherAuthentication.this;
                        VpnProfileInfo profileEntry =
                                knoxVpnTetherAuthentication12.vpnConfig.getProfileEntry(
                                        knoxVpnTetherAuthentication12.mProfileName);
                        if (profileEntry != null) {
                            String interfaceNameForUsbtethering =
                                    KnoxVpnTetherAuthentication.this.mKnoxVpnHelper
                                            .getInterfaceNameForUsbtethering();
                            String str4 = profileEntry.mInterfaceName;
                            int i = profileEntry.activateState;
                            if (interfaceNameForUsbtethering != null && str4 != null && i == 1) {
                                Log.d(
                                        "KnoxVpnTetherAuthentication",
                                        "start tether auth process after onServiceConnected event");
                                KnoxVpnTetherAuthentication.this.startTetherAuthProcess(
                                        profileEntry.personaId,
                                        interfaceNameForUsbtethering,
                                        KnoxVpnTetherAuthentication.this.mKnoxVpnHelper
                                                .getIpAddressForUsbTetheringInterface());
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "KnoxVpnTetherAuthentication",
                            "Error happened during remote process communication with the"
                                + " application "
                                    + Log.getStackTraceString(e));
                }
                CountDownLatch countDownLatch = KnoxVpnTetherAuthentication.this.mCountDownLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onServicedisconnected is reached for user "),
                    KnoxVpnTetherAuthentication.this.mBindUserId,
                    "KnoxVpnTetherAuthentication");
            ((ArrayList) KnoxVpnTetherAuthentication.this.mknoxVpnTetherAuthClientStatus)
                    .add(
                            "onServiceDisconnected callback has been recieved for tetherAuth client"
                                + " in user "
                                    + KnoxVpnTetherAuthentication.this.mBindUserId
                                    + " and for profile "
                                    + KnoxVpnTetherAuthentication.this.mProfileName
                                    + " at "
                                    + System.currentTimeMillis());
            KnoxVpnTetherAuthentication.this.mTetherAuthService = null;
        }
    }

    /* renamed from: -$$Nest$mapplyTetheringRulesForVpn, reason: not valid java name */
    public static void m568$$Nest$mapplyTetheringRulesForVpn(
            KnoxVpnTetherAuthentication knoxVpnTetherAuthentication) {
        knoxVpnTetherAuthentication.getClass();
        try {
            VpnProfileInfo profileEntry =
                    knoxVpnTetherAuthentication.vpnConfig.getProfileEntry(
                            knoxVpnTetherAuthentication.mProfileName);
            if (profileEntry != null) {
                String interfaceNameForUsbtethering =
                        knoxVpnTetherAuthentication.mKnoxVpnHelper
                                .getInterfaceNameForUsbtethering();
                String str = profileEntry.mInterfaceName;
                int i = profileEntry.activateState;
                int i2 = profileEntry.mInterface_type;
                if (interfaceNameForUsbtethering != null) {
                    if (str == null && i == 1) {
                        Log.d(
                                "KnoxVpnTetherAuthentication",
                                "Applying rules to drop tether packets after tether auth successful"
                                    + " due to vpn down");
                        knoxVpnTetherAuthentication.mFirewallHelper
                                .addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
                    } else if (str != null && i == 1) {
                        Log.d(
                                "KnoxVpnTetherAuthentication",
                                "Applying vpn tethering rules after tether auth successful");
                        String[] dnsServerListForInterface =
                                IVpnManager.Stub.asInterface(
                                                ServiceManager.getService("vpn_management"))
                                        .getDnsServerListForInterface(str);
                        knoxVpnTetherAuthentication.mKnoxVpnHelper.getClass();
                        knoxVpnTetherAuthentication.mFirewallHelper.addRulesForUsbTethering(
                                i2,
                                str,
                                interfaceNameForUsbtethering,
                                KnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering),
                                dnsServerListForInterface);
                    }
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Apply tether rules after successful auth failed "),
                    "KnoxVpnTetherAuthentication");
        }
    }

    public KnoxVpnTetherAuthentication(Context context) {
        this.mKnoxVpnHelper = null;
        this.mFirewallHelper = null;
        this.syncObject = null;
        mContext = context;
        this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(context);
        this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
        this.syncObject = new Object();
    }

    public static synchronized KnoxVpnTetherAuthentication getInstance(Context context) {
        KnoxVpnTetherAuthentication knoxVpnTetherAuthentication;
        synchronized (KnoxVpnTetherAuthentication.class) {
            try {
                if (mKnoxVpnTetherAuthentication == null) {
                    mKnoxVpnTetherAuthentication = new KnoxVpnTetherAuthentication(context);
                }
                knoxVpnTetherAuthentication = mKnoxVpnTetherAuthentication;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxVpnTetherAuthentication;
    }

    public final void bindTetherAuthService(int i, String str) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i,
                "Binding to the tetherAuth service in user after tether auth package install ",
                "KnoxVpnTetherAuthentication");
        if (this.mtetherAuthDetails == null) {
            return;
        }
        this.mProfileName = str;
        this.mBindUserId = i;
        if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "unbinding previous service before binding again for the user "),
                    this.mBindUserId,
                    "KnoxVpnTetherAuthentication");
            try {
                mContext.unbindService(
                        (ServiceConnection)
                                this.mTetherAuthConnectionList.get(
                                        Integer.valueOf(this.mBindUserId)));
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
                "com.samsung.knox.vpn.tether.auth",
                "com.samsung.knox.vpn.tether.auth.TetherAuthService");
        if (mContext.bindServiceAsUser(
                intent, tetherAuthConnection, 1073741829, new UserHandle(this.mBindUserId))) {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Binding to the tetherAuth service in user "),
                    this.mBindUserId,
                    " is successful",
                    "KnoxVpnTetherAuthentication");
            this.mTetherAuthConnectionList.put(
                    Integer.valueOf(this.mBindUserId), tetherAuthConnection);
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                this.mCountDownLatch = countDownLatch;
                countDownLatch.await(7000L, TimeUnit.MILLISECONDS);
                this.mCountDownLatch = null;
            } catch (InterruptedException unused2) {
            }
        }
    }

    public final void bindTetherAuthService(int i, String str, Bundle bundle) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "Binding to the tetherAuth service in user ", "KnoxVpnTetherAuthentication");
        this.mtetherAuthDetails = bundle;
        this.mProfileName = str;
        this.mBindUserId = i;
        if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "unbinding previous service before binding again for the user "),
                    this.mBindUserId,
                    "KnoxVpnTetherAuthentication");
            try {
                mContext.unbindService(
                        (ServiceConnection)
                                this.mTetherAuthConnectionList.get(
                                        Integer.valueOf(this.mBindUserId)));
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
                "com.samsung.knox.vpn.tether.auth",
                "com.samsung.knox.vpn.tether.auth.TetherAuthService");
        if (mContext.bindServiceAsUser(
                intent, tetherAuthConnection, 1073741829, new UserHandle(this.mBindUserId))) {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Binding to the tetherAuth service in user "),
                    this.mBindUserId,
                    " is successful",
                    "KnoxVpnTetherAuthentication");
            this.mTetherAuthConnectionList.put(
                    Integer.valueOf(this.mBindUserId), tetherAuthConnection);
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                this.mCountDownLatch = countDownLatch;
                countDownLatch.await(7000L, TimeUnit.MILLISECONDS);
                this.mCountDownLatch = null;
            } catch (InterruptedException unused2) {
            }
        }
    }

    public final List getKnoxVpnTetherAuthClientStatus() {
        return this.mknoxVpnTetherAuthClientStatus;
    }

    public final void startTetherAuthProcess(int i, String str, List list) {
        synchronized (this.syncObject) {
            try {
                IKnoxVpnTetherAuthInterface iKnoxVpnTetherAuthInterface = this.mTetherAuthService;
                if (iKnoxVpnTetherAuthInterface != null) {
                    Bundle startAuthenticationProcess =
                            iKnoxVpnTetherAuthInterface.startAuthenticationProcess(
                                    new AuthStatusCallback());
                    this.mKnoxVpnHelper.getClass();
                    this.mFirewallHelper.addRulesTetherAuth(
                            KnoxVpnHelper.getUIDForPackage(i, "com.samsung.knox.vpn.tether.auth"),
                            str,
                            list,
                            startAuthenticationProcess);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopTetheringAuthProcess(boolean z) {
        synchronized (this.syncObject) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindTetherAuthService() {
        synchronized (this.syncObject) {
            try {
                Log.d(
                        "KnoxVpnTetherAuthentication",
                        "start unbinding tetherAuth service in user " + this.mBindUserId);
                if (this.mTetherAuthConnectionList.containsKey(Integer.valueOf(this.mBindUserId))) {
                    IKnoxVpnTetherAuthInterface iKnoxVpnTetherAuthInterface =
                            this.mTetherAuthService;
                    if (iKnoxVpnTetherAuthInterface != null) {
                        Log.d(
                                "KnoxVpnTetherAuthentication",
                                "stopAuthenticationProcess before unbind and result is "
                                        + iKnoxVpnTetherAuthInterface.stopAuthenticationProcess());
                    }
                    mContext.unbindService(
                            (ServiceConnection)
                                    this.mTetherAuthConnectionList.get(
                                            Integer.valueOf(this.mBindUserId)));
                    Log.d(
                            "KnoxVpnTetherAuthentication",
                            "unbinding tetherAuth service in user " + this.mBindUserId);
                    this.mTetherAuthConnectionList.remove(Integer.valueOf(this.mBindUserId));
                    Log.d(
                            "KnoxVpnTetherAuthentication",
                            "start removing tether auth firewall rules during unbinding");
                    this.mFirewallHelper.removeRulesTetherAuth();
                    this.mTetherAuthService = null;
                    this.isTetherAuthSuccessful = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
