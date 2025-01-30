package com.android.server.knox.dar.ddar.proxy;

import android.app.KeyguardManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManagerInternal;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class DualDARComnService extends IProxyService.Stub implements EnterpriseServiceCallback {
  public Handler mBackgroundHandler;
  public final ProxyAgentBindingChecker mBindingChecker;
  public final Context mContext;
  public Handler mHandler;
  public LockPatternUtils mLockPatternUtils;
  public final Object mRegisteredProxyAgentsLock = new Object();
  public final HashMap mRegisteredProxyAgents = new HashMap();
  public final Object mProxyAgentWrapperLock = new Object();
  public final HashMap mProxyAgentWrapperMap = new HashMap();
  public final BroadcastReceiver mUserBroadcastReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.knox.dar.ddar.proxy.DualDARComnService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          if ("android.intent.action.USER_STARTED".equals(action)) {
            DualDARComnService.this.connectAgentsByUser(intExtra);
          }
        }
      };
  public final BroadcastReceiver mPackageBroadcastReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.knox.dar.ddar.proxy.DualDARComnService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          if ("android.intent.action.PACKAGE_ADDED".equals(action)
              || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
            try {
              if (intent.getData() != null) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                if (schemeSpecificPart != null) {
                  DualDARComnService.this.reConnectAgentsByPkgName(schemeSpecificPart);
                }
                if (DualDARComnService.this.hasKnoxKPUInternalPermission(schemeSpecificPart)
                    && DualDARComnService.this.isDualDAREnabled(intExtra)) {
                  DualDARComnService.this.addKPUAppToWhitelist(intExtra);
                  return;
                }
                return;
              }
              return;
            } catch (RuntimeException e) {
              DDLog.m34e(
                  "DualDARComnService",
                  "package added or changed uri runtime exception occurred",
                  new Object[0]);
              e.printStackTrace();
              return;
            }
          }
          if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            return;
          }
          "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action);
        }
      };
  public IDualDARPolicy mDualDARPolicyService = null;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  public DualDARComnService(Context context) {
    this.mBackgroundHandler = null;
    this.mHandler = null;
    this.mLockPatternUtils = null;
    this.mContext = context;
    this.mLockPatternUtils = new LockPatternUtils(context);
    HandlerThread handlerThread = new HandlerThread("DualDARComnService");
    handlerThread.start();
    this.mHandler =
        new Handler(
            handlerThread
                .getLooper()) { // from class:
                                // com.android.server.knox.dar.ddar.proxy.DualDARComnService.3
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
              DualDARComnService.this.handleAgentDied((ProxyAgentInfo) message.obj);
            } else if (i == 2) {
              DualDARComnService.this.mBindingChecker.scheduleChecker((String) message.obj);
            } else {
              if (i != 4) {
                return;
              }
              DualDARComnService.this.handleAgentStated((ProxyAgentInfo) message.obj);
            }
          }
        };
    HandlerThread handlerThread2 = new HandlerThread("DualDARComnService-bgThread");
    handlerThread2.start();
    this.mBackgroundHandler = new Handler(handlerThread2.getLooper());
    this.mBindingChecker = new ProxyAgentBindingChecker(this.mHandler);
    LocalServices.addService(KnoxProxyManagerInternal.class, new LocalService());
  }

  public Handler getHandler() {
    return this.mHandler;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    init();
  }

  public final void init() {
    DDLog.m33d("DualDARComnService", "initialize", new Object[0]);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_STARTED");
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    intentFilter.addAction("android.intent.action.USER_ADDED");
    this.mContext.registerReceiverAsUser(
        this.mUserBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
    intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter2.addDataScheme("package");
    this.mContext.registerReceiverAsUser(
        this.mPackageBroadcastReceiver, UserHandle.ALL, intentFilter2, null, null);
    SystemProxyAgent.getInstance(this.mContext).init();
    initKnownProxyAgents();
  }

  public final void initKnownProxyAgents() {
    DDLog.m33d("DualDARComnService", "initKnownProxyAgents", new Object[0]);
    initDualDARAgentsIfRequired();
  }

  public final boolean isDualDAREnabled(int i) {
    return SemPersonaManager.isDarDualEncryptionEnabled(i);
  }

  public final void initDualDARAgentsIfRequired() {
    if (PersonaServiceHelper.isDualDAREnabled()) {
      KnoxProxyManager.getInstance(this.mContext)
          .registerAgentByMetadata(
              "KNOXCORE_PROXY_AGENT",
              0,
              "com.samsung.android.knox.containercore",
              "proxyAgent.class");
    }
  }

  public void ensureProxyAgentBindingIfRequired(String str) {
    boolean z = false;
    if (str != null
        && !str.isEmpty()
        && !this.mLockPatternUtils.isSecure(0)
        && !checkProxyAgentBound(str)) {
      z = true;
    }
    if (z) {
      ensureProxyAgentBinding(str);
    }
  }

  public final boolean checkProxyAgentBound(String str) {
    ProxyAgentWrapper proxyAgentWrapper;
    ProxyAgentInfo findAgent = findAgent(str);
    if (findAgent == null) {
      DDLog.m34e("DualDARComnService", "Registered agent not found", new Object[0]);
      return false;
    }
    synchronized (this.mProxyAgentWrapperLock) {
      proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(findAgent);
    }
    if (proxyAgentWrapper == null) {
      DDLog.m34e("DualDARComnService", "Bound agent not found", new Object[0]);
      return false;
    }
    return proxyAgentWrapper.isServiceConnected();
  }

  public final ProxyAgentInfo findAgent(String str) {
    ProxyAgentInfo proxyAgentInfo;
    synchronized (this.mRegisteredProxyAgentsLock) {
      proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
    }
    return proxyAgentInfo;
  }

  public final void ensureProxyAgentBinding(String str) {
    DDLog.m33d("DualDARComnService", "ensureProxyAgentBinding: " + str, new Object[0]);
    if (findAgent(str) == null) {
      DDLog.m34e(
          "DualDARComnService",
          "Stop to ensure binding due to agent not registered",
          new Object[0]);
      return;
    }
    if (this.mHandler.hasMessages(2, str)) {
      this.mHandler.removeMessages(2, str);
    }
    Message obtainMessage = this.mHandler.obtainMessage(2, str);
    synchronized (this.mBindingChecker.getCheckerLock()) {
      this.mHandler.sendMessage(obtainMessage);
      try {
        this.mBindingChecker.getCheckerLock().wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    DDLog.m33d("DualDARComnService", "ensureProxyAgentBinding: finished", new Object[0]);
  }

  public boolean registerAgentByAction(String str, int i, String str2, String str3) {
    DDLog.m33d("DualDARComnService", "registerAgentByAction", new Object[0]);
    enforceCallingUser();
    return registerAgentByActionInternal(str, i, str2, str3, true);
  }

  public final void enforceCallingUser() {
    DDLog.m33d("DualDARComnService", "enforceCallingUser", new Object[0]);
    int callingUid = Binder.getCallingUid();
    if (UserHandle.getAppId(callingUid) != 5250
        && UserHandle.getAppId(callingUid) != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
  }

  public final boolean registerAgentByActionInternal(
      String str, int i, String str2, String str3, boolean z) {
    synchronized (this.mRegisteredProxyAgentsLock) {
      ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
      if (proxyAgentInfo != null) {
        DDLog.m33d(
            "DualDARComnService",
            "Knox Proxy Agent Already Registered Agent = " + proxyAgentInfo + "No action taken",
            new Object[0]);
        return true;
      }
      ProxyAgentInfo findProxyAgentByAction = findProxyAgentByAction(str, i, str2, str3);
      if (findProxyAgentByAction == null) {
        DDLog.m34e("DualDARComnService", "Knox Proxy Agent Not found", new Object[0]);
        return false;
      }
      this.mRegisteredProxyAgents.put(findProxyAgentByAction.mName, findProxyAgentByAction);
      if (z) {
        connectProxyAgentASync(findProxyAgentByAction);
      }
      return true;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x0095, code lost:

     r0 = new com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo(r6, r7, r2.serviceInfo.getComponentName());
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final ProxyAgentInfo findProxyAgentByAction(String str, int i, String str2, String str3) {
    DDLog.m33d(
        "DualDARComnService",
        "findProxyAgentByAction : agent = "
            + str
            + "User = "
            + i
            + " packageName = "
            + str2
            + " actionName = "
            + str3,
        new Object[0]);
    ProxyAgentInfo proxyAgentInfo = null;
    try {
      Intent intent = new Intent(str3);
      intent.setPackage(str2);
      List queryIntentServicesAsUser =
          this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 0, i);
      DDLog.m33d(
          "DualDARComnService",
          "Number of Apps with action = "
              + str3
              + " = "
              + String.valueOf(queryIntentServicesAsUser.size()),
          new Object[0]);
      if (queryIntentServicesAsUser.size() > 0) {
        Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          ResolveInfo resolveInfo = (ResolveInfo) it.next();
          if (resolveInfo.serviceInfo.packageName.equalsIgnoreCase(str2)) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (proxyAgentInfo == null) {
      DDLog.m34e(
          "DualDARComnService",
          "Knox Proxy Agent Not Found : for package:"
              + str2
              + " for action:"
              + str3
              + " for user:"
              + i,
          new Object[0]);
    } else {
      DDLog.m33d(
          "DualDARComnService", "Knox Proxy Agent Found =   " + proxyAgentInfo, new Object[0]);
    }
    return proxyAgentInfo;
  }

  public boolean registerAgentByMetadata(String str, int i, String str2, String str3) {
    DDLog.m33d("DualDARComnService", "registerAgentByMetadata", new Object[0]);
    enforceCallingUser();
    return registerAgentByMetadataInternal(str, i, str2, str3, true);
  }

  public final boolean registerAgentByMetadataInternal(
      String str, int i, String str2, String str3, boolean z) {
    synchronized (this.mRegisteredProxyAgentsLock) {
      ProxyAgentInfo findAgent = findAgent(str);
      if (findAgent != null) {
        DDLog.m33d(
            "DualDARComnService",
            "Knox Proxy Agent Already Registered Agent = " + findAgent + "No action taken",
            new Object[0]);
        return true;
      }
      ProxyAgentInfo findProxyAgentByMetaData = findProxyAgentByMetaData(str, i, str2, str3);
      if (findProxyAgentByMetaData == null) {
        DDLog.m34e("DualDARComnService", "Knox Proxy Agent Not found", new Object[0]);
        return false;
      }
      this.mRegisteredProxyAgents.put(findProxyAgentByMetaData.mName, findProxyAgentByMetaData);
      if (z) {
        connectProxyAgentASync(findProxyAgentByMetaData);
      }
      return true;
    }
  }

  public final ProxyAgentInfo findProxyAgentByMetaData(
      String str, int i, String str2, String str3) {
    IPackageManager asInterface =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    try {
      ApplicationInfo applicationInfo = asInterface.getApplicationInfo(str2, 128L, i);
      if (!asInterface.isPackageAvailable(str2, i) || applicationInfo == null) {
        DDLog.m34e("DualDARComnService", "package:" + str2 + " not found user:" + i, new Object[0]);
        return null;
      }
      Bundle bundle = applicationInfo.metaData;
      if (bundle == null) {
        return null;
      }
      String str4 = applicationInfo.packageName;
      String string = bundle.getString(str3);
      if (string != null && str4 != null) {
        DDLog.m35i("DualDARComnService", " appInfo.uid:" + applicationInfo.uid, new Object[0]);
        return new ProxyAgentInfo(str, i, new ComponentName(str4, string));
      }
      return null;
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void deregisterAgent(String str) {
    DDLog.m33d("DualDARComnService", "deregisterAgent", new Object[0]);
    enforceCallingUser();
    deregisterAgentInternal(str);
  }

  public final void deregisterAgentInternal(String str) {
    synchronized (this.mRegisteredProxyAgentsLock) {
      ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
      if (proxyAgentInfo == null) {
        DDLog.m33d(
            "DualDARComnService",
            "Knox Proxy Agent Not Registered Agent = " + proxyAgentInfo + "No action taken",
            new Object[0]);
        return;
      }
      disconnectProxyAgent(proxyAgentInfo);
      this.mRegisteredProxyAgents.remove(proxyAgentInfo.mName);
    }
  }

  public final void disconnectProxyAgent(ProxyAgentInfo proxyAgentInfo) {
    synchronized (this.mProxyAgentWrapperLock) {
      if (this.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
        ProxyAgentWrapper proxyAgentWrapper =
            (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
        if (proxyAgentWrapper.isServiceConnected()) {
          proxyAgentWrapper.disconnect();
        }
        proxyAgentWrapper.markStale();
        this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
      }
    }
  }

  public Bundle relay(String str, String str2, String str3, Bundle bundle) {
    Bundle relay;
    if (str.equalsIgnoreCase("SYSTEM_PROXY_AGENT")) {
      relay =
          SystemProxyAgent.getInstance(this.mContext)
              .relay(Binder.getCallingUid(), str2, str3, bundle);
    } else {
      ProxyAgentInfo findAgent = findAgent(str);
      if (findAgent == null) {
        DDLog.m34e("DualDARComnService", "relay: Agent not found, agent: " + str, new Object[0]);
      } else {
        ProxyAgentWrapper connectProxyAgentSync = connectProxyAgentSync(findAgent);
        if (connectProxyAgentSync != null) {
          relay = connectProxyAgentSync.relay(Binder.getCallingUid(), str2, str3, bundle);
        } else {
          DDLog.m34e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
        }
      }
      relay = null;
    }
    if (bundle != null) {
      bundle.clear();
    }
    return relay;
  }

  public Bundle relayAsync(
      final String str, final String str2, final String str3, final Bundle bundle) {
    if (str.equalsIgnoreCase("SYSTEM_PROXY_AGENT")) {
      this.mBackgroundHandler.post(
          new Runnable() { // from class:
                           // com.android.server.knox.dar.ddar.proxy.DualDARComnService.4
            @Override // java.lang.Runnable
            public void run() {
              DDLog.m33d(
                  "DualDARComnService",
                  "relayAsync from bg thread, relay start to System proxy agent : " + str,
                  new Object[0]);
              SystemProxyAgent.getInstance(DualDARComnService.this.mContext)
                  .relay(Binder.getCallingUid(), str2, str3, bundle);
              DDLog.m33d(
                  "DualDARComnService",
                  "relayAsync from bg thread, relay end to System proxy agent : " + str,
                  new Object[0]);
            }
          });
      Bundle bundle2 = new Bundle();
      bundle2.putBoolean("dual_dar_response", true);
      return bundle2;
    }
    final ProxyAgentInfo findAgent = findAgent(str);
    if (findAgent == null) {
      DDLog.m34e("DualDARComnService", "relay: Agent not found, agent: " + str, new Object[0]);
      return null;
    }
    this.mBackgroundHandler.post(
        new Runnable() { // from class:
                         // com.android.server.knox.dar.ddar.proxy.DualDARComnService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DualDARComnService.this.lambda$relayAsync$0(findAgent, str, str2, str3, bundle);
          }
        });
    Bundle bundle3 = new Bundle();
    bundle3.putBoolean("dual_dar_response", true);
    return bundle3;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$relayAsync$0(
      ProxyAgentInfo proxyAgentInfo, String str, String str2, String str3, Bundle bundle) {
    ProxyAgentWrapper connectProxyAgentSync = connectProxyAgentSync(proxyAgentInfo);
    DDLog.m33d(
        "DualDARComnService",
        "relayAsync from bg thread, relay start to agent : " + str,
        new Object[0]);
    if (connectProxyAgentSync != null) {
      connectProxyAgentSync.relay(Binder.getCallingUid(), str2, str3, bundle);
    } else {
      DDLog.m34e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
    }
    DDLog.m33d(
        "DualDARComnService",
        "relayAsync from bg thread, relay end to agent : " + str,
        new Object[0]);
  }

  public final void addKPUAppToWhitelist(final int i) {
    getDualDARPolicyService()
        .ifPresent(
            new Consumer() { // from class:
                             // com.android.server.knox.dar.ddar.proxy.DualDARComnService$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DualDARComnService.this.lambda$addKPUAppToWhitelist$1(i, (IDualDARPolicy) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addKPUAppToWhitelist$1(int i, IDualDARPolicy iDualDARPolicy) {
    int i2;
    try {
      i2 =
          this.mContext
              .getPackageManager()
              .getPackageUidAsUser(SemPersonaManager.getAdminComponentName(i).getPackageName(), i);
    } catch (Exception e) {
      DDLog.m34e(
          "DualDARComnService",
          "addKPUAppToWhitelist failed ! Component may be null" + e.getMessage(),
          new Object[0]);
      i2 = -1;
    }
    try {
      Bundle config = iDualDARPolicy.getConfig(new ContextInfo(i2, i));
      if (config != null) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(
            "dualdar-config-datalock-whitelistpackages",
            config.getParcelableArray("dualdar-config-datalock-whitelistpackages"));
        iDualDARPolicy.setConfig(new ContextInfo(i2, i), bundle);
      }
    } catch (RemoteException unused) {
      DDLog.m34e("DualDARComnService", "addKPUAppToWhitelist Failed", new Object[0]);
    }
  }

  public final Optional getDualDARPolicyService() {
    if (this.mDualDARPolicyService == null) {
      this.mDualDARPolicyService =
          IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
    }
    return Optional.ofNullable(this.mDualDARPolicyService);
  }

  public final void connectAgentsByUser(int i) {
    DDLog.m33d("DualDARComnService", "connectAgentsByUser User : " + i, new Object[0]);
    synchronized (this.mRegisteredProxyAgentsLock) {
      for (Map.Entry entry : this.mRegisteredProxyAgents.entrySet()) {
        ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) entry.getValue();
        if (proxyAgentInfo.mUserId == i) {
          connectProxyAgentASync(proxyAgentInfo);
        }
      }
    }
  }

  public final void reConnectAgentsByPkgName(String str) {
    DDLog.m33d("DualDARComnService", "reConnectAgentsByPkgName:: package: " + str, new Object[0]);
    synchronized (this.mRegisteredProxyAgentsLock) {
      for (Map.Entry entry : this.mRegisteredProxyAgents.entrySet()) {
        ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) entry.getValue();
        if (proxyAgentInfo.mCompName.getPackageName().equalsIgnoreCase(str)) {
          ProxyAgentWrapper connectProxyAgentASync = connectProxyAgentASync(proxyAgentInfo);
          if (connectProxyAgentASync != null) {
            connectProxyAgentASync.enableReconnectionFlag();
          } else {
            DDLog.m34e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
          }
        }
      }
    }
  }

  public final ProxyAgentWrapper connectProxyAgentSync(ProxyAgentInfo proxyAgentInfo) {
    ProxyAgentWrapper proxyAgentWrapper;
    DDLog.m33d("DualDARComnService", "connectProxyAgentSync", new Object[0]);
    synchronized (this.mProxyAgentWrapperLock) {
      proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
    }
    if (proxyAgentWrapper != null) {
      if (proxyAgentWrapper.isServiceConnected()) {
        DDLog.m33d("DualDARComnService", "Proxy Agent is already bound. So Noop", new Object[0]);
        return proxyAgentWrapper;
      }
      if (proxyAgentWrapper.isProxyAgentBindPending()) {
        DDLog.m33d("DualDARComnService", "Bind Pending. So just wait", new Object[0]);
        if (proxyAgentWrapper.connectSync()) {
          return proxyAgentWrapper;
        }
        DDLog.m34e(
            "DualDARComnService", "Failed to bind to " + proxyAgentInfo.toString(), new Object[0]);
        return null;
      }
      synchronized (this.mProxyAgentWrapperLock) {
        if (proxyAgentWrapper == this.mProxyAgentWrapperMap.get(proxyAgentInfo)) {
          proxyAgentWrapper.markStale();
          this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
        }
      }
    }
    ProxyAgentWrapper proxyAgentWrapper2 =
        new ProxyAgentWrapper(this.mContext, this, proxyAgentInfo);
    if (!proxyAgentWrapper2.connectSync()) {
      DDLog.m34e(
          "DualDARComnService", "Failed to bind to " + proxyAgentInfo.toString(), new Object[0]);
      return null;
    }
    synchronized (this.mProxyAgentWrapperLock) {
      if (this.mProxyAgentWrapperMap.get(proxyAgentInfo) != null) {
        if (((ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo))
            .isServiceConnected()) {
          proxyAgentWrapper2 = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
        } else {
          this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
        }
      } else {
        this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
      }
    }
    return proxyAgentWrapper2;
  }

  public final ProxyAgentWrapper connectProxyAgentASync(ProxyAgentInfo proxyAgentInfo) {
    DDLog.m33d("DualDARComnService", "connectProxyAgentASync", new Object[0]);
    synchronized (this.mProxyAgentWrapperLock) {
      if (this.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
        ProxyAgentWrapper proxyAgentWrapper =
            (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
        if (!proxyAgentWrapper.isServiceConnected()
            && !proxyAgentWrapper.isProxyAgentBindPending()) {
          proxyAgentWrapper.markStale();
          this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
        }
        DDLog.m35i(
            "DualDARComnService",
            "Proxy Agent is already bound or pending bound: Agent = " + proxyAgentInfo.mName,
            new Object[0]);
        return proxyAgentWrapper;
      }
      ProxyAgentWrapper proxyAgentWrapper2 =
          new ProxyAgentWrapper(this.mContext, this, proxyAgentInfo);
      if (!proxyAgentWrapper2.connectAsync()) {
        DDLog.m34e(
            "DualDARComnService", "Failed to bind to " + proxyAgentInfo.toString(), new Object[0]);
        return null;
      }
      this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
      return proxyAgentWrapper2;
    }
  }

  public void handleAgentDied(ProxyAgentInfo proxyAgentInfo) {
    DDLog.m33d("DualDARComnService", "handleAgentDied", new Object[0]);
    synchronized (this.mProxyAgentWrapperLock) {
      if (this.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
        ((ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo)).markStale();
        this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
      }
    }
    try {
      if (!IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
          .isPackageAvailable(proxyAgentInfo.mCompName.getPackageName(), proxyAgentInfo.mUserId)) {
        DDLog.m34e(
            "DualDARComnService",
            "Not installed service " + proxyAgentInfo.toString(),
            new Object[0]);
        return;
      }
      ProxyAgentWrapper connectProxyAgentASync = connectProxyAgentASync(proxyAgentInfo);
      if (connectProxyAgentASync != null) {
        connectProxyAgentASync.enableReconnectionFlag();
      } else {
        DDLog.m34e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
      }
    } catch (RemoteException e) {
      DDLog.m34e("DualDARComnService", "reconnectService remote exception", new Object[0]);
      e.printStackTrace();
    }
  }

  public final void handleAgentStated(ProxyAgentInfo proxyAgentInfo) {
    DDLog.m33d(
        "DualDARComnService",
        "handleAgentStarted : " + proxyAgentInfo.mCompName.getPackageName(),
        new Object[0]);
    try {
      if (!IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
          .isPackageAvailable(proxyAgentInfo.mCompName.getPackageName(), proxyAgentInfo.mUserId)) {
        DDLog.m34e(
            "DualDARComnService",
            "Not installed service " + proxyAgentInfo.toString(),
            new Object[0]);
        return;
      }
      int dualDARUser = PersonaServiceHelper.getDualDARUser();
      if (dualDARUser <= 0) {
        return;
      }
      if (!SemPersonaManager.isDualDARNativeCrypto(dualDARUser)) {
        String clientPackage =
            DualDarManager.getInstance(this.mContext).getClientPackage(dualDARUser);
        if (clientPackage == null
            || !clientPackage.equals(proxyAgentInfo.mCompName.getPackageName())) {
          return;
        }
        DDLog.m33d("DualDARComnService", "clientPkg connected : " + clientPackage, new Object[0]);
        setDeviceUnlockedForUserIfUnsecured(dualDARUser);
        return;
      }
      setDeviceUnlockedForUserIfUnsecured(dualDARUser);
    } catch (RemoteException e) {
      DDLog.m34e("DualDARComnService", "reconnectService remote exception", new Object[0]);
      e.printStackTrace();
    }
  }

  public final void setDeviceUnlockedForUserIfUnsecured(int i) {
    KeyguardManager keyguardManager =
        (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
    TrustManager trustManager = (TrustManager) this.mContext.getSystemService("trust");
    if (keyguardManager == null
        || trustManager == null
        || keyguardManager.isDeviceSecure()
        || keyguardManager.isDeviceSecure(i)) {
      return;
    }
    DDLog.m33d("DualDARComnService", "setDeviceUnlockedForUserIfUnsecured", new Object[0]);
    trustManager.setDeviceLockedForUser(0, false);
  }

  public final boolean hasKnoxKPUInternalPermission(String str) {
    if (this.mContext
            .getPackageManager()
            .checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", str)
        != 0) {
      return false;
    }
    DDLog.m33d("DualDARComnService", "hasKnoxKPUInternalPermission " + str, new Object[0]);
    return true;
  }

  public final class LocalService extends KnoxProxyManagerInternal {
    public LocalService() {}

    public void ensureProxyAgentBindingIfRequired(String str) {
      DualDARComnService.this.ensureProxyAgentBindingIfRequired(str);
    }
  }

  public final class ProxyAgentBindingChecker {
    public final Object mCheckerLock = new Object();
    public final Handler mHandler;

    public ProxyAgentBindingChecker(Handler handler) {
      this.mHandler = handler;
    }

    public Object getCheckerLock() {
      return this.mCheckerLock;
    }

    public void scheduleChecker(String str) {
      if (str == null || str.isEmpty()) {
        synchronized (this.mCheckerLock) {
          DDLog.m34e(
              "DualDARComnService",
              "Binding Checker : Invalid agent... cancel scheduling",
              new Object[0]);
          this.mCheckerLock.notifyAll();
        }
        return;
      }
      DDLog.m33d("DualDARComnService", "Binding Checker : Check binding to " + str, new Object[0]);
      if (DualDARComnService.this.checkProxyAgentBound(str)) {
        synchronized (this.mCheckerLock) {
          DDLog.m33d(
              "DualDARComnService", "Binding Checker : Found out bound agent", new Object[0]);
          this.mCheckerLock.notifyAll();
        }
        return;
      }
      DDLog.m33d("DualDARComnService", "Binding Checker : Maybe not bound yet", new Object[0]);
      if (this.mHandler.hasMessages(2, str)) {
        this.mHandler.removeMessages(2, str);
      }
      Handler handler = this.mHandler;
      handler.sendMessageDelayed(handler.obtainMessage(2, str), 1000L);
    }
  }
}
