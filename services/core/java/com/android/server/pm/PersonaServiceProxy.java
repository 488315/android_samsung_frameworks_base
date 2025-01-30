package com.android.server.pm;

import android.app.KeyguardManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.ContainerServiceInfo;
import com.android.server.ContainerServiceWrapper;
import com.samsung.android.knox.SemPersonaManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PersonaServiceProxy {
  public Context mContext;
  public boolean mIsDoEnabled;
  public KeyguardManager mKeyguardManager;
  public LockPatternUtils mLockPatternUtils;
  public SemPersonaManager mPersonaManager;
  public TrustManager mTrustManager;
  public UserManager mUserManager;
  public HashMap mContainerServices = new HashMap();
  public Object mContainerServiceLock = new Object();
  public BroadcastReceiver mUserBroadcastReceiver =
      new BroadcastReceiver() { // from class: com.android.server.pm.PersonaServiceProxy.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          Log.i("PersonaManagerService::Proxy", "broadcast received. Action:" + action);
          if ("android.intent.action.USER_STARTED".equals(action)) {
            Log.i(
                "PersonaManagerService::Proxy",
                "user-" + intExtra + " started. Finding container service...");
            PersonaServiceProxy.this.findAndConnectToContainerService(intExtra);
            Log.i(
                "PersonaManagerService::Proxy",
                "Checking if " + intExtra + " is enabled COM container");
            if (PersonaServiceProxy.this.mUserManager != null) {
              PersonaServiceProxy.this.mUserManager.getUserInfo(intExtra);
            }
            return;
          }
          if ("android.intent.action.USER_REMOVED".equals(action)) {
            Log.d(
                "PersonaManagerService::Proxy",
                "Removed User - " + intent.getIntExtra("android.intent.extra.user_handle", -1));
            if (!PersonaServiceProxy.this.mIsDoEnabled
                && !PersonaServiceProxy.this.isKnoxProfileExist()) {
              Log.d(
                  "PersonaManagerService::Proxy",
                  "No Knox profile exist on device so stopping all Container service");
              synchronized (PersonaServiceProxy.this.mContainerServiceLock) {
                if (PersonaServiceProxy.this.mContainerServices != null
                    && PersonaServiceProxy.this.mContainerServices.size() > 0) {
                  for (Map.Entry entry : PersonaServiceProxy.this.mContainerServices.entrySet()) {
                    ContainerServiceInfo containerServiceInfo =
                        (ContainerServiceInfo) entry.getKey();
                    ContainerServiceWrapper containerServiceWrapper =
                        (ContainerServiceWrapper) entry.getValue();
                    Log.i(
                        "PersonaManagerService::Proxy",
                        "Stopping Container service - " + containerServiceInfo.toString());
                    containerServiceWrapper.disconnect();
                  }
                  PersonaServiceProxy.this.mContainerServices.clear();
                }
              }
              return;
            }
            Log.d(
                "PersonaManagerService::Proxy",
                "Knox profile exist on device so not stopping Container service...");
            return;
          }
          if ("android.intent.action.USER_ADDED".equals(action)) {
            int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
            Log.d("PersonaManagerService::Proxy", "Added User - " + intExtra2);
            if (SemPersonaManager.isKnoxId(intExtra2)) {
              PersonaServiceProxy.this.findAndConnectToContainerService(0);
              return;
            }
            Log.d(
                "PersonaManagerService::Proxy",
                "Added User - " + intExtra2 + " is a non-knox user, so disable Secure Folder");
            try {
              ((PackageManagerService.IPackageManagerImpl) ServiceManager.getService("package"))
                  .setApplicationEnabledSetting(
                      "com.samsung.knox.securefolder", 2, 0, intExtra2, null);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      };
  public BroadcastReceiver mPackageBroadcastReceiver =
      new BroadcastReceiver() { // from class: com.android.server.pm.PersonaServiceProxy.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          intent.getIntExtra("android.intent.extra.user_handle", -10000);
          if ("android.intent.action.PACKAGE_ADDED".equals(action)
              || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            Log.i("PersonaManagerService::Proxy", "newPackage is " + schemeSpecificPart);
            if (schemeSpecificPart == null
                || !schemeSpecificPart.equals("com.samsung.android.knox.containercore")) {
              return;
            }
            PersonaServiceProxy.this.findAndConnectToContainerService(0);
          }
        }
      };

  public PersonaServiceProxy(Context context) {
    this.mIsDoEnabled = false;
    this.mContext = context;
    this.mUserManager = (UserManager) context.getSystemService("user");
    this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
    this.mTrustManager = (TrustManager) this.mContext.getSystemService("trust");
    this.mLockPatternUtils = new LockPatternUtils(this.mContext);
    this.mPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
    this.mIsDoEnabled = SemPersonaManager.isDoEnabled(0);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_STARTED");
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    intentFilter.addAction("android.intent.action.USER_ADDED");
    this.mContext.registerReceiverAsUser(
        this.mUserBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter2.addDataScheme("package");
    intentFilter2.addDataSchemeSpecificPart("com.samsung.android.knox.containercore", 0);
    this.mContext.registerReceiverAsUser(
        this.mPackageBroadcastReceiver, UserHandle.ALL, intentFilter2, null, null);
    registerContainerReceiver();
  }

  public final void registerContainerReceiver() {
    ContainerStateReceiver.register(
        this.mContext,
        new ContainerStateReceiver() { // from class: com.android.server.pm.PersonaServiceProxy.1
          public void onDeviceOwnerActivated(Context context, Bundle bundle) {
            Log.d("PersonaManagerService::Proxy", "onDeviceOwnerActivated...");
            PersonaServiceProxy.this.mIsDoEnabled = true;
            PersonaServiceProxy.this.findAndConnectToContainerService(0);
          }
        });
  }

  public final void findAndConnectToContainerService(int i) {
    Log.d("PersonaManagerService::Proxy", "Finding container service in user - " + i);
    UserManager userManager = this.mUserManager;
    if (userManager == null) {
      Log.e("PersonaManagerService::Proxy", "Cannot find UMS");
      return;
    }
    if (!userManager.isUserRunning(i)) {
      Log.e(
          "PersonaManagerService::Proxy", "scanContainerService() user<" + i + "> is not running");
      return;
    }
    ContainerServiceInfo findContainerService =
        findContainerService(i, "com.samsung.android.knox.containercore");
    if (findContainerService != null) {
      maybeConnectContainerService(findContainerService);
    }
  }

  public final boolean isKnoxProfileExist() {
    boolean z = true;
    List<UserInfo> users = this.mUserManager.getUsers(true);
    if (users != null) {
      for (UserInfo userInfo : users) {
        Log.d("PersonaManagerService::Proxy", "Profile id - " + userInfo.id);
        if (SemPersonaManager.isKnoxId(userInfo.id)) {
          Log.d("PersonaManagerService::Proxy", "Knox Profile exist on device");
          break;
        }
      }
    }
    z = false;
    Log.d("PersonaManagerService::Proxy", "isKnoxProfileExist status - " + z);
    return z;
  }

  public final Intent createPrimaryHomeIntent() {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.HOME");
    intent.setFlags(268435456);
    ActivityInfo activityInfo =
        this.mContext.getPackageManager().resolveActivityAsUser(intent, 65536, 0).activityInfo;
    intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
    intent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
    return intent;
  }

  public final void lockContainer(UserInfo userInfo) {
    int focusedKnoxId = this.mPersonaManager.getFocusedKnoxId();
    this.mTrustManager.setDeviceLockedForUser(userInfo.id, true);
    this.mLockPatternUtils.requireStrongAuth(8, userInfo.id);
    if (focusedKnoxId == userInfo.id) {
      this.mContext.startActivityAsUser(createPrimaryHomeIntent(), UserHandle.SYSTEM);
    }
  }

  public void handleServiceDied(ContainerServiceInfo containerServiceInfo) {
    if (containerServiceInfo.category.equals("core")) {
      for (UserInfo userInfo : this.mUserManager.getUsers(true)) {
        if (userInfo.isManagedProfile() && this.mKeyguardManager.isDeviceSecure(userInfo.id)) {
          lockContainer(userInfo);
        }
      }
    }
    reconnectContainerService(containerServiceInfo);
  }

  public final void reconnectContainerService(ContainerServiceInfo containerServiceInfo) {
    Log.d(
        "PersonaManagerService::Proxy",
        "reconnectContainerService " + containerServiceInfo.toString());
    synchronized (this.mContainerServiceLock) {
    }
    if (!this.mUserManager.isUserRunning(containerServiceInfo.userid)) {
      Log.e(
          "PersonaManagerService::Proxy",
          "connectService() user<" + containerServiceInfo.userid + "> is not running");
      return;
    }
    try {
      if (!IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
          .isPackageAvailable(
              containerServiceInfo.name.getPackageName(), containerServiceInfo.userid)) {
        Log.e(
            "PersonaManagerService::Proxy",
            "Not installed service " + containerServiceInfo.toString());
        return;
      }
      maybeConnectContainerService(containerServiceInfo);
    } catch (RemoteException e) {
      Log.e("PersonaManagerService::Proxy", "reconnectService remote exception");
      e.printStackTrace();
    }
  }

  public final boolean maybeConnectContainerService(ContainerServiceInfo containerServiceInfo) {
    Log.d(
        "PersonaManagerService::Proxy",
        "connectContainerService is called for "
            + containerServiceInfo.toString()
            + ", mIsDoEnabled - "
            + this.mIsDoEnabled);
    if (this.mIsDoEnabled || isKnoxProfileExist()) {
      Log.d(
          "PersonaManagerService::Proxy",
          "Starting Container service because either Do/Po exist on device...");
      synchronized (this.mContainerServiceLock) {
        if (this.mContainerServices.containsKey(containerServiceInfo)) {
          if (((ContainerServiceWrapper) this.mContainerServices.get(containerServiceInfo))
              .isBound()) {
            Log.e(
                "PersonaManagerService::Proxy",
                "Container service already bound " + containerServiceInfo.toString());
            return true;
          }
          this.mContainerServices.remove(containerServiceInfo);
        } else {
          Log.i("PersonaManagerService::Proxy", "Service don't exist on cache...");
        }
        ContainerServiceWrapper containerServiceWrapper =
            new ContainerServiceWrapper(this.mContext, this, containerServiceInfo);
        if (!containerServiceWrapper.connect()) {
          Log.e(
              "PersonaManagerService::Proxy", "Failed to bind " + containerServiceInfo.toString());
          return false;
        }
        this.mContainerServices.put(containerServiceInfo, containerServiceWrapper);
        return true;
      }
    }
    Log.d("PersonaManagerService::Proxy", "Not starting Container service...");
    return false;
  }

  public final ContainerServiceInfo findContainerService(int i, String str) {
    IPackageManager asInterface =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    try {
      ApplicationInfo applicationInfo = asInterface.getApplicationInfo(str, 128L, i);
      if (!asInterface.isPackageAvailable(str, i) || applicationInfo == null) {
        Log.e("PersonaManagerService::Proxy", "package:" + str + " not found user:" + i);
        return null;
      }
      Bundle bundle = applicationInfo.metaData;
      if (bundle == null) {
        return null;
      }
      String str2 = applicationInfo.packageName;
      String string = bundle.getString("containerService.class");
      String string2 = bundle.getString("containerService.category");
      if (string != null && str2 != null && string2 != null) {
        Log.i("PersonaManagerService::Proxy", " appInfo.uid:" + applicationInfo.uid);
        if (applicationInfo.uid != 5250) {
          Log.e("PersonaManagerService::Proxy", "core app does not have core uid");
          return null;
        }
        ComponentName componentName = new ComponentName(str2, string);
        Log.i(
            "PersonaManagerService::Proxy",
            "findContainerService("
                + i
                + ") found "
                + componentName.flattenToShortString()
                + " category:"
                + string2);
        return new ContainerServiceInfo(i, componentName, string2);
      }
      return null;
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Bundle sendProxyMessage(String str, String str2, Bundle bundle) {
    StringBuilder sb = new StringBuilder();
    sb.append("sendProxyMessage() name:");
    sb.append(str2);
    sb.append(" bundle:");
    sb.append(bundle == null ? "null" : bundle.toString());
    Log.e("PersonaManagerService::Proxy", sb.toString());
    return sendMessage(str, str2, 0, bundle);
  }

  public final Bundle sendMessage(String str, String str2, int i, Bundle bundle) {
    ContainerServiceWrapper containerServiceWrapper;
    synchronized (this.mContainerServiceLock) {
      if (this.mContainerServices.isEmpty()) {
        Log.e("PersonaManagerService::Proxy", "sendProxyMessage() no container service");
        return null;
      }
      Iterator it = this.mContainerServices.entrySet().iterator();
      while (true) {
        if (!it.hasNext()) {
          containerServiceWrapper = null;
          break;
        }
        Map.Entry entry = (Map.Entry) it.next();
        ContainerServiceInfo containerServiceInfo = (ContainerServiceInfo) entry.getKey();
        if (containerServiceInfo.category.equals(str) && containerServiceInfo.userid == i) {
          Log.v(
              "PersonaManagerService::Proxy",
              "sending message:" + str2 + " to " + containerServiceInfo.toString());
          containerServiceWrapper = (ContainerServiceWrapper) entry.getValue();
          break;
        }
      }
      if (containerServiceWrapper != null) {
        if (bundle == null) {
          bundle = new Bundle();
        }
        bundle.putInt("knox.container.proxy.EXTRA_CALLING_UID", Binder.getCallingUid());
        Log.i(
            "PersonaManagerService::Proxy",
            "sendProxyAgentMessage() Calling UID:" + Binder.getCallingUid());
        bundle.putInt("knox.container.proxy.EXTRA_CALLING_PID", Binder.getCallingPid());
        Log.i(
            "PersonaManagerService::Proxy",
            "sendProxyAgentMessage() Calling PID:" + Binder.getCallingUid());
        Bundle onMessage = containerServiceWrapper.onMessage(str2, bundle);
        StringBuilder sb = new StringBuilder();
        sb.append("result:");
        sb.append(
            onMessage == null
                ? "null"
                : Integer.valueOf(onMessage.getInt("android.intent.extra.RETURN_RESULT")));
        Log.v("PersonaManagerService::Proxy", sb.toString());
        return onMessage;
      }
      Log.e("PersonaManagerService::Proxy", "service not found, name - " + str2);
      new Bundle().putInt("android.intent.extra.RETURN_RESULT", 99);
      return null;
    }
  }
}
