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
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.knox.dar.ddar.DDCache;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.core.DualDarManagerProxy;
import com.android.server.knox.dar.ddar.fsm.StateMachineProxy;
import com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy;
import com.android.server.knox.dar.ddar.ta.TAProxy;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManagerInternal;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class DualDARComnService extends IProxyService.Stub implements EnterpriseServiceCallback {
    public final Handler mBackgroundHandler;
    public final ProxyAgentBindingChecker mBindingChecker;
    public final Context mContext;
    public final AnonymousClass3 mHandler;
    public final LockPatternUtils mLockPatternUtils;
    public final AnonymousClass1 mPackageBroadcastReceiver;
    public final AnonymousClass1 mUserBroadcastReceiver;
    public final Object mRegisteredProxyAgentsLock = new Object();
    public final HashMap mRegisteredProxyAgents = new HashMap();
    public final Object mProxyAgentWrapperLock = new Object();
    public final HashMap mProxyAgentWrapperMap = new HashMap();
    public IDualDARPolicy mDualDARPolicyService = null;

    public final class LocalService extends KnoxProxyManagerInternal {
        public LocalService() {
        }

        public final void ensureProxyAgentBindingIfRequired(String str) {
            DualDARComnService dualDARComnService = DualDARComnService.this;
            dualDARComnService.getClass();
            if (str == null || str.isEmpty() || dualDARComnService.mLockPatternUtils.isSecure(0) || dualDARComnService.checkProxyAgentBound(str)) {
                return;
            }
            DDLog.d("DualDARComnService", "ensureProxyAgentBinding: ".concat(str), new Object[0]);
            if (dualDARComnService.findAgent(str) == null) {
                DDLog.e("DualDARComnService", "Stop to ensure binding due to agent not registered", new Object[0]);
                return;
            }
            if (dualDARComnService.mHandler.hasMessages(2, str)) {
                dualDARComnService.mHandler.removeMessages(2, str);
            }
            Message obtainMessage = dualDARComnService.mHandler.obtainMessage(2, str);
            synchronized (dualDARComnService.mBindingChecker.mCheckerLock) {
                dualDARComnService.mHandler.sendMessage(obtainMessage);
                try {
                    dualDARComnService.mBindingChecker.mCheckerLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DDLog.d("DualDARComnService", "ensureProxyAgentBinding: finished", new Object[0]);
        }
    }

    public final class ProxyAgentBindingChecker {
        public final Object mCheckerLock = new Object();
        public final Handler mHandler;

        public ProxyAgentBindingChecker(AnonymousClass3 anonymousClass3) {
            this.mHandler = anonymousClass3;
        }
    }

    /* renamed from: -$$Nest$mreConnectAgentsByPkgName, reason: not valid java name */
    public static void m632$$Nest$mreConnectAgentsByPkgName(DualDARComnService dualDARComnService, String str) {
        dualDARComnService.getClass();
        DDLog.d("DualDARComnService", "reConnectAgentsByPkgName:: package: ".concat(str), new Object[0]);
        synchronized (dualDARComnService.mRegisteredProxyAgentsLock) {
            try {
                for (Map.Entry entry : dualDARComnService.mRegisteredProxyAgents.entrySet()) {
                    ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) entry.getValue();
                    if (proxyAgentInfo.mCompName.getPackageName().equalsIgnoreCase(str)) {
                        ProxyAgentWrapper connectProxyAgentASync = dualDARComnService.connectProxyAgentASync(proxyAgentInfo);
                        if (connectProxyAgentASync != null) {
                            DDLog.d("KnoxService::ProxyAgentWrapper", "enableReconnectionFlag: " + connectProxyAgentASync, new Object[0]);
                            connectProxyAgentASync.mIsReconnection = true;
                        } else {
                            DDLog.e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.knox.dar.ddar.proxy.DualDARComnService$3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.knox.dar.ddar.proxy.DualDARComnService$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.knox.dar.ddar.proxy.DualDARComnService$1] */
    public DualDARComnService(Context context) {
        this.mBackgroundHandler = null;
        this.mHandler = null;
        this.mLockPatternUtils = null;
        final int i = 0;
        this.mUserBroadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService.1
            public final /* synthetic */ DualDARComnService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if ("android.intent.action.USER_STARTED".equals(action)) {
                            DualDARComnService dualDARComnService = this.this$0;
                            dualDARComnService.getClass();
                            DDLog.d("DualDARComnService", "connectAgentsByUser User : " + intExtra, new Object[0]);
                            synchronized (dualDARComnService.mRegisteredProxyAgentsLock) {
                                try {
                                    for (Map.Entry entry : dualDARComnService.mRegisteredProxyAgents.entrySet()) {
                                        ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) entry.getValue();
                                        if (proxyAgentInfo.mUserId == intExtra) {
                                            dualDARComnService.connectProxyAgentASync(proxyAgentInfo);
                                        }
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        String action2 = intent.getAction();
                        final int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (!"android.intent.action.PACKAGE_ADDED".equals(action2) && !"android.intent.action.PACKAGE_CHANGED".equals(action2)) {
                            if ("android.intent.action.PACKAGE_REMOVED".equals(action2)) {
                                return;
                            }
                            "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action2);
                            return;
                        }
                        try {
                            if (intent.getData() != null) {
                                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                                if (schemeSpecificPart != null) {
                                    DualDARComnService.m632$$Nest$mreConnectAgentsByPkgName(this.this$0, schemeSpecificPart);
                                }
                                if (this.this$0.mContext.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", schemeSpecificPart) == 0) {
                                    DDLog.d("DualDARComnService", "hasKnoxKPUInternalPermission " + schemeSpecificPart, new Object[0]);
                                    this.this$0.getClass();
                                    if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra2)) {
                                        final DualDARComnService dualDARComnService2 = this.this$0;
                                        if (dualDARComnService2.mDualDARPolicyService == null) {
                                            dualDARComnService2.mDualDARPolicyService = IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
                                        }
                                        Optional.ofNullable(dualDARComnService2.mDualDARPolicyService).ifPresent(new Consumer() { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i2;
                                                DualDARComnService dualDARComnService3 = DualDARComnService.this;
                                                int i3 = intExtra2;
                                                IDualDARPolicy iDualDARPolicy = (IDualDARPolicy) obj;
                                                dualDARComnService3.getClass();
                                                try {
                                                    i2 = dualDARComnService3.mContext.getPackageManager().getPackageUidAsUser(SemPersonaManager.getAdminComponentName(i3).getPackageName(), i3);
                                                } catch (Exception e) {
                                                    DDLog.e("DualDARComnService", "addKPUAppToWhitelist failed ! Component may be null" + e.getMessage(), new Object[0]);
                                                    i2 = -1;
                                                }
                                                try {
                                                    Bundle config = iDualDARPolicy.getConfig(new ContextInfo(i2, i3));
                                                    if (config != null) {
                                                        Bundle bundle = new Bundle();
                                                        bundle.putParcelableArray("dualdar-config-datalock-whitelistpackages", config.getParcelableArray("dualdar-config-datalock-whitelistpackages"));
                                                        iDualDARPolicy.setConfig(new ContextInfo(i2, i3), bundle);
                                                    }
                                                } catch (RemoteException unused) {
                                                    DDLog.e("DualDARComnService", "addKPUAppToWhitelist Failed", new Object[0]);
                                                }
                                            }
                                        });
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        } catch (RuntimeException e) {
                            DDLog.e("DualDARComnService", "package added or changed uri runtime exception occurred", new Object[0]);
                            e.printStackTrace();
                            return;
                        }
                }
            }
        };
        final int i2 = 1;
        this.mPackageBroadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService.1
            public final /* synthetic */ DualDARComnService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if ("android.intent.action.USER_STARTED".equals(action)) {
                            DualDARComnService dualDARComnService = this.this$0;
                            dualDARComnService.getClass();
                            DDLog.d("DualDARComnService", "connectAgentsByUser User : " + intExtra, new Object[0]);
                            synchronized (dualDARComnService.mRegisteredProxyAgentsLock) {
                                try {
                                    for (Map.Entry entry : dualDARComnService.mRegisteredProxyAgents.entrySet()) {
                                        ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) entry.getValue();
                                        if (proxyAgentInfo.mUserId == intExtra) {
                                            dualDARComnService.connectProxyAgentASync(proxyAgentInfo);
                                        }
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        String action2 = intent.getAction();
                        final int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (!"android.intent.action.PACKAGE_ADDED".equals(action2) && !"android.intent.action.PACKAGE_CHANGED".equals(action2)) {
                            if ("android.intent.action.PACKAGE_REMOVED".equals(action2)) {
                                return;
                            }
                            "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action2);
                            return;
                        }
                        try {
                            if (intent.getData() != null) {
                                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                                if (schemeSpecificPart != null) {
                                    DualDARComnService.m632$$Nest$mreConnectAgentsByPkgName(this.this$0, schemeSpecificPart);
                                }
                                if (this.this$0.mContext.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", schemeSpecificPart) == 0) {
                                    DDLog.d("DualDARComnService", "hasKnoxKPUInternalPermission " + schemeSpecificPart, new Object[0]);
                                    this.this$0.getClass();
                                    if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra2)) {
                                        final DualDARComnService dualDARComnService2 = this.this$0;
                                        if (dualDARComnService2.mDualDARPolicyService == null) {
                                            dualDARComnService2.mDualDARPolicyService = IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
                                        }
                                        Optional.ofNullable(dualDARComnService2.mDualDARPolicyService).ifPresent(new Consumer() { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i22;
                                                DualDARComnService dualDARComnService3 = DualDARComnService.this;
                                                int i3 = intExtra2;
                                                IDualDARPolicy iDualDARPolicy = (IDualDARPolicy) obj;
                                                dualDARComnService3.getClass();
                                                try {
                                                    i22 = dualDARComnService3.mContext.getPackageManager().getPackageUidAsUser(SemPersonaManager.getAdminComponentName(i3).getPackageName(), i3);
                                                } catch (Exception e) {
                                                    DDLog.e("DualDARComnService", "addKPUAppToWhitelist failed ! Component may be null" + e.getMessage(), new Object[0]);
                                                    i22 = -1;
                                                }
                                                try {
                                                    Bundle config = iDualDARPolicy.getConfig(new ContextInfo(i22, i3));
                                                    if (config != null) {
                                                        Bundle bundle = new Bundle();
                                                        bundle.putParcelableArray("dualdar-config-datalock-whitelistpackages", config.getParcelableArray("dualdar-config-datalock-whitelistpackages"));
                                                        iDualDARPolicy.setConfig(new ContextInfo(i22, i3), bundle);
                                                    }
                                                } catch (RemoteException unused) {
                                                    DDLog.e("DualDARComnService", "addKPUAppToWhitelist Failed", new Object[0]);
                                                }
                                            }
                                        });
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        } catch (RuntimeException e) {
                            DDLog.e("DualDARComnService", "package added or changed uri runtime exception occurred", new Object[0]);
                            e.printStackTrace();
                            return;
                        }
                }
            }
        };
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        ?? r0 = new Handler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("DualDARComnService").getLooper()) { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i3 = message.what;
                if (i3 == 1) {
                    ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) message.obj;
                    DualDARComnService dualDARComnService = DualDARComnService.this;
                    dualDARComnService.getClass();
                    DDLog.d("DualDARComnService", "handleAgentDied", new Object[0]);
                    synchronized (dualDARComnService.mProxyAgentWrapperLock) {
                        try {
                            if (dualDARComnService.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
                                ((ProxyAgentWrapper) dualDARComnService.mProxyAgentWrapperMap.get(proxyAgentInfo)).markStale();
                                dualDARComnService.mProxyAgentWrapperMap.remove(proxyAgentInfo);
                            }
                        } finally {
                        }
                    }
                    try {
                        if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isPackageAvailable(proxyAgentInfo.mCompName.getPackageName(), proxyAgentInfo.mUserId)) {
                            ProxyAgentWrapper connectProxyAgentASync = dualDARComnService.connectProxyAgentASync(proxyAgentInfo);
                            if (connectProxyAgentASync != null) {
                                DDLog.d("KnoxService::ProxyAgentWrapper", "enableReconnectionFlag: " + connectProxyAgentASync, new Object[0]);
                                connectProxyAgentASync.mIsReconnection = true;
                            } else {
                                DDLog.e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
                            }
                        } else {
                            DDLog.e("DualDARComnService", "Not installed service ".concat(proxyAgentInfo.toString()), new Object[0]);
                        }
                        return;
                    } catch (RemoteException e) {
                        DDLog.e("DualDARComnService", "reconnectService remote exception", new Object[0]);
                        e.printStackTrace();
                        return;
                    }
                }
                if (i3 == 2) {
                    String str = (String) message.obj;
                    ProxyAgentBindingChecker proxyAgentBindingChecker = DualDARComnService.this.mBindingChecker;
                    if (str != null) {
                        proxyAgentBindingChecker.getClass();
                        if (!str.isEmpty()) {
                            DDLog.d("DualDARComnService", "Binding Checker : Check binding to ".concat(str), new Object[0]);
                            if (DualDARComnService.this.checkProxyAgentBound(str)) {
                                synchronized (proxyAgentBindingChecker.mCheckerLock) {
                                    DDLog.d("DualDARComnService", "Binding Checker : Found out bound agent", new Object[0]);
                                    proxyAgentBindingChecker.mCheckerLock.notifyAll();
                                }
                                return;
                            }
                            DDLog.d("DualDARComnService", "Binding Checker : Maybe not bound yet", new Object[0]);
                            if (proxyAgentBindingChecker.mHandler.hasMessages(2, str)) {
                                proxyAgentBindingChecker.mHandler.removeMessages(2, str);
                            }
                            Handler handler = proxyAgentBindingChecker.mHandler;
                            handler.sendMessageDelayed(handler.obtainMessage(2, str), 1000L);
                            return;
                        }
                    }
                    synchronized (proxyAgentBindingChecker.mCheckerLock) {
                        DDLog.e("DualDARComnService", "Binding Checker : Invalid agent... cancel scheduling", new Object[0]);
                        proxyAgentBindingChecker.mCheckerLock.notifyAll();
                    }
                    return;
                }
                if (i3 != 4) {
                    return;
                }
                ProxyAgentInfo proxyAgentInfo2 = (ProxyAgentInfo) message.obj;
                DualDARComnService dualDARComnService2 = DualDARComnService.this;
                dualDARComnService2.getClass();
                DDLog.d("DualDARComnService", "handleAgentStarted : " + proxyAgentInfo2.mCompName.getPackageName(), new Object[0]);
                try {
                    if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isPackageAvailable(proxyAgentInfo2.mCompName.getPackageName(), proxyAgentInfo2.mUserId)) {
                        int dualDARUser = PersonaServiceHelper.getDualDARUser();
                        if (dualDARUser > 0) {
                            if (SemPersonaManager.isDualDARNativeCrypto(dualDARUser)) {
                                dualDARComnService2.setDeviceUnlockedForUserIfUnsecured(dualDARUser);
                            } else {
                                String clientPackage = DualDarManager.getInstance(dualDARComnService2.mContext).getClientPackage(dualDARUser);
                                if (clientPackage != null && clientPackage.equals(proxyAgentInfo2.mCompName.getPackageName())) {
                                    DDLog.d("DualDARComnService", "clientPkg connected : ".concat(clientPackage), new Object[0]);
                                    dualDARComnService2.setDeviceUnlockedForUserIfUnsecured(dualDARUser);
                                }
                            }
                        }
                    } else {
                        DDLog.e("DualDARComnService", "Not installed service ".concat(proxyAgentInfo2.toString()), new Object[0]);
                    }
                } catch (RemoteException e2) {
                    DDLog.e("DualDARComnService", "reconnectService remote exception", new Object[0]);
                    e2.printStackTrace();
                }
            }
        };
        this.mHandler = r0;
        this.mBackgroundHandler = new Handler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("DualDARComnService-bgThread").getLooper());
        this.mBindingChecker = new ProxyAgentBindingChecker(r0);
        LocalServices.addService(KnoxProxyManagerInternal.class, new LocalService());
    }

    public static void enforceCallingUser() {
        DDLog.d("DualDARComnService", "enforceCallingUser", new Object[0]);
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) != 5250 && UserHandle.getAppId(callingUid) != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
    }

    public static ProxyAgentInfo findProxyAgentByMetaData(int i, String str, String str2, String str3) {
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        try {
            ApplicationInfo applicationInfo = asInterface.getApplicationInfo(str2, 128L, i);
            if (!asInterface.isPackageAvailable(str2, i) || applicationInfo == null) {
                DDLog.e("DualDARComnService", "package:" + str2 + " not found user:" + i, new Object[0]);
                return null;
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle == null) {
                return null;
            }
            String str4 = applicationInfo.packageName;
            String string = bundle.getString(str3);
            if (string != null && str4 != null) {
                DDLog.i("DualDARComnService", " appInfo.uid:" + applicationInfo.uid, new Object[0]);
                return new ProxyAgentInfo(new ComponentName(str4, string), i, str);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final boolean checkProxyAgentBound(String str) {
        ProxyAgentWrapper proxyAgentWrapper;
        ProxyAgentInfo findAgent = findAgent(str);
        if (findAgent == null) {
            DDLog.e("DualDARComnService", "Registered agent not found", new Object[0]);
            return false;
        }
        synchronized (this.mProxyAgentWrapperLock) {
            proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(findAgent);
        }
        if (proxyAgentWrapper != null) {
            return proxyAgentWrapper.isServiceConnected();
        }
        DDLog.e("DualDARComnService", "Bound agent not found", new Object[0]);
        return false;
    }

    public final ProxyAgentWrapper connectProxyAgentASync(ProxyAgentInfo proxyAgentInfo) {
        boolean z;
        DDLog.d("DualDARComnService", "connectProxyAgentASync", new Object[0]);
        synchronized (this.mProxyAgentWrapperLock) {
            if (this.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
                ProxyAgentWrapper proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
                if (!proxyAgentWrapper.isServiceConnected()) {
                    synchronized (proxyAgentWrapper.mProxyAgentLock) {
                        z = proxyAgentWrapper.mBindPending;
                    }
                    if (!z) {
                        proxyAgentWrapper.markStale();
                        this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
                    }
                }
                DDLog.i("DualDARComnService", "Proxy Agent is already bound or pending bound: Agent = " + proxyAgentInfo.mName, new Object[0]);
                return proxyAgentWrapper;
            }
            ProxyAgentWrapper proxyAgentWrapper2 = new ProxyAgentWrapper(this.mContext, this, proxyAgentInfo);
            if (proxyAgentWrapper2.connectAsync()) {
                this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
                return proxyAgentWrapper2;
            }
            DDLog.e("DualDARComnService", "Failed to bind to ".concat(proxyAgentInfo.toString()), new Object[0]);
            return null;
        }
    }

    public final ProxyAgentWrapper connectProxyAgentSync(ProxyAgentInfo proxyAgentInfo) {
        ProxyAgentWrapper proxyAgentWrapper;
        boolean z;
        DDLog.d("DualDARComnService", "connectProxyAgentSync", new Object[0]);
        synchronized (this.mProxyAgentWrapperLock) {
            proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
        }
        if (proxyAgentWrapper != null) {
            if (proxyAgentWrapper.isServiceConnected()) {
                DDLog.d("DualDARComnService", "Proxy Agent is already bound. So Noop", new Object[0]);
                return proxyAgentWrapper;
            }
            synchronized (proxyAgentWrapper.mProxyAgentLock) {
                z = proxyAgentWrapper.mBindPending;
            }
            if (z) {
                DDLog.d("DualDARComnService", "Bind Pending. So just wait", new Object[0]);
                if (proxyAgentWrapper.connectSync()) {
                    return proxyAgentWrapper;
                }
                DDLog.e("DualDARComnService", "Failed to bind to ".concat(proxyAgentInfo.toString()), new Object[0]);
                return null;
            }
            synchronized (this.mProxyAgentWrapperLock) {
                try {
                    if (proxyAgentWrapper == this.mProxyAgentWrapperMap.get(proxyAgentInfo)) {
                        proxyAgentWrapper.markStale();
                        this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
                    }
                } finally {
                }
            }
        }
        ProxyAgentWrapper proxyAgentWrapper2 = new ProxyAgentWrapper(this.mContext, this, proxyAgentInfo);
        if (!proxyAgentWrapper2.connectSync()) {
            DDLog.e("DualDARComnService", "Failed to bind to ".concat(proxyAgentInfo.toString()), new Object[0]);
            return null;
        }
        synchronized (this.mProxyAgentWrapperLock) {
            try {
                if (this.mProxyAgentWrapperMap.get(proxyAgentInfo) == null) {
                    this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
                } else if (((ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo)).isServiceConnected()) {
                    proxyAgentWrapper2 = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
                } else {
                    this.mProxyAgentWrapperMap.put(proxyAgentInfo, proxyAgentWrapper2);
                }
            } finally {
            }
        }
        return proxyAgentWrapper2;
    }

    public final void deregisterAgent(String str) {
        DDLog.d("DualDARComnService", "deregisterAgent", new Object[0]);
        enforceCallingUser();
        synchronized (this.mRegisteredProxyAgentsLock) {
            try {
                ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
                if (proxyAgentInfo != null) {
                    disconnectProxyAgent(proxyAgentInfo);
                    this.mRegisteredProxyAgents.remove(proxyAgentInfo.mName);
                } else {
                    DDLog.d("DualDARComnService", "Knox Proxy Agent Not Registered Agent = " + proxyAgentInfo + "No action taken", new Object[0]);
                }
            } finally {
            }
        }
    }

    public final void disconnectProxyAgent(ProxyAgentInfo proxyAgentInfo) {
        synchronized (this.mProxyAgentWrapperLock) {
            try {
                if (this.mProxyAgentWrapperMap.containsKey(proxyAgentInfo)) {
                    ProxyAgentWrapper proxyAgentWrapper = (ProxyAgentWrapper) this.mProxyAgentWrapperMap.get(proxyAgentInfo);
                    if (proxyAgentWrapper.isServiceConnected()) {
                        proxyAgentWrapper.disconnect();
                    }
                    proxyAgentWrapper.markStale();
                    this.mProxyAgentWrapperMap.remove(proxyAgentInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ProxyAgentInfo findAgent(String str) {
        ProxyAgentInfo proxyAgentInfo;
        synchronized (this.mRegisteredProxyAgentsLock) {
            proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
        }
        return proxyAgentInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x007a, code lost:
    
        r1 = new com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo(r0.serviceInfo.getComponentName(), r6, r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo findProxyAgentByAction(int r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r5 = this;
            java.lang.String r0 = "Number of Apps with action = "
            java.lang.String r1 = "findProxyAgentByAction : agent = "
            java.lang.String r2 = "User = "
            java.lang.String r3 = " packageName = "
            java.lang.StringBuilder r1 = com.android.server.StorageManagerService$$ExternalSyntheticOutline0.m(r6, r1, r7, r2, r3)
            java.lang.String r2 = " actionName = "
            java.lang.String r1 = com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r1, r8, r2, r9)
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r4 = "DualDARComnService"
            com.android.server.knox.dar.ddar.DDLog.d(r4, r1, r3)
            r1 = 0
            android.content.Intent r3 = new android.content.Intent     // Catch: java.lang.Exception -> L7c
            r3.<init>(r9)     // Catch: java.lang.Exception -> L7c
            r3.setPackage(r8)     // Catch: java.lang.Exception -> L7c
            android.content.Context r5 = r5.mContext     // Catch: java.lang.Exception -> L7c
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: java.lang.Exception -> L7c
            java.util.List r5 = r5.queryIntentServicesAsUser(r3, r2, r6)     // Catch: java.lang.Exception -> L7c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L7c
            r3.<init>(r0)     // Catch: java.lang.Exception -> L7c
            r3.append(r9)     // Catch: java.lang.Exception -> L7c
            java.lang.String r0 = " = "
            r3.append(r0)     // Catch: java.lang.Exception -> L7c
            int r0 = r5.size()     // Catch: java.lang.Exception -> L7c
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch: java.lang.Exception -> L7c
            r3.append(r0)     // Catch: java.lang.Exception -> L7c
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Exception -> L7c
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch: java.lang.Exception -> L7c
            com.android.server.knox.dar.ddar.DDLog.d(r4, r0, r3)     // Catch: java.lang.Exception -> L7c
            int r0 = r5.size()     // Catch: java.lang.Exception -> L7c
            if (r0 <= 0) goto L80
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Exception -> L7c
        L59:
            boolean r0 = r5.hasNext()     // Catch: java.lang.Exception -> L7c
            if (r0 == 0) goto L80
            java.lang.Object r0 = r5.next()     // Catch: java.lang.Exception -> L7c
            android.content.pm.ResolveInfo r0 = (android.content.pm.ResolveInfo) r0     // Catch: java.lang.Exception -> L7c
            android.content.pm.ServiceInfo r3 = r0.serviceInfo     // Catch: java.lang.Exception -> L7c
            java.lang.String r3 = r3.packageName     // Catch: java.lang.Exception -> L7c
            boolean r3 = r3.equalsIgnoreCase(r8)     // Catch: java.lang.Exception -> L7c
            if (r3 == 0) goto L59
            com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo r5 = new com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo     // Catch: java.lang.Exception -> L7c
            android.content.pm.ServiceInfo r0 = r0.serviceInfo     // Catch: java.lang.Exception -> L7c
            android.content.ComponentName r0 = r0.getComponentName()     // Catch: java.lang.Exception -> L7c
            r5.<init>(r0, r6, r7)     // Catch: java.lang.Exception -> L7c
            r1 = r5
            goto L80
        L7c:
            r5 = move-exception
            r5.printStackTrace()
        L80:
            if (r1 != 0) goto L99
            java.lang.String r5 = "Knox Proxy Agent Not Found : for package:"
            java.lang.String r7 = " for action:"
            java.lang.String r0 = " for user:"
            java.lang.StringBuilder r5 = android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0.m(r5, r8, r7, r9, r0)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.android.server.knox.dar.ddar.DDLog.e(r4, r5, r6)
            goto Lac
        L99:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Knox Proxy Agent Found =   "
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.android.server.knox.dar.ddar.DDLog.d(r4, r5, r6)
        Lac:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.proxy.DualDARComnService.findProxyAgentByAction(int, java.lang.String, java.lang.String, java.lang.String):com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final boolean registerAgentByAction(String str, int i, String str2, String str3) {
        DDLog.d("DualDARComnService", "registerAgentByAction", new Object[0]);
        enforceCallingUser();
        synchronized (this.mRegisteredProxyAgentsLock) {
            try {
                ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) this.mRegisteredProxyAgents.get(str);
                if (proxyAgentInfo != null) {
                    DDLog.d("DualDARComnService", "Knox Proxy Agent Already Registered Agent = " + proxyAgentInfo + "No action taken", new Object[0]);
                } else {
                    ProxyAgentInfo findProxyAgentByAction = findProxyAgentByAction(i, str, str2, str3);
                    if (findProxyAgentByAction == null) {
                        DDLog.e("DualDARComnService", "Knox Proxy Agent Not found", new Object[0]);
                        return false;
                    }
                    this.mRegisteredProxyAgents.put(findProxyAgentByAction.mName, findProxyAgentByAction);
                    connectProxyAgentASync(findProxyAgentByAction);
                }
                return true;
            } finally {
            }
        }
    }

    public final boolean registerAgentByMetadata(String str, int i, String str2, String str3) {
        DDLog.d("DualDARComnService", "registerAgentByMetadata", new Object[0]);
        enforceCallingUser();
        synchronized (this.mRegisteredProxyAgentsLock) {
            try {
                ProxyAgentInfo findAgent = findAgent(str);
                if (findAgent != null) {
                    DDLog.d("DualDARComnService", "Knox Proxy Agent Already Registered Agent = " + findAgent + "No action taken", new Object[0]);
                } else {
                    ProxyAgentInfo findProxyAgentByMetaData = findProxyAgentByMetaData(i, str, str2, str3);
                    if (findProxyAgentByMetaData == null) {
                        DDLog.e("DualDARComnService", "Knox Proxy Agent Not found", new Object[0]);
                        return false;
                    }
                    this.mRegisteredProxyAgents.put(findProxyAgentByMetaData.mName, findProxyAgentByMetaData);
                    connectProxyAgentASync(findProxyAgentByMetaData);
                }
                return true;
            } finally {
            }
        }
    }

    public final Bundle relay(String str, String str2, String str3, Bundle bundle) {
        Bundle relay;
        if (str.equalsIgnoreCase("SYSTEM_PROXY_AGENT")) {
            relay = SystemProxyAgent.getInstance(this.mContext).relay(Binder.getCallingUid(), str2, str3, bundle);
        } else {
            ProxyAgentInfo findAgent = findAgent(str);
            if (findAgent == null) {
                DDLog.e("DualDARComnService", "relay: Agent not found, agent: ".concat(str), new Object[0]);
            } else {
                ProxyAgentWrapper connectProxyAgentSync = connectProxyAgentSync(findAgent);
                if (connectProxyAgentSync != null) {
                    relay = connectProxyAgentSync.relay(Binder.getCallingUid(), str2, str3, bundle);
                } else {
                    DDLog.e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
                }
            }
            relay = null;
        }
        if (bundle != null) {
            bundle.clear();
        }
        return relay;
    }

    public final Bundle relayAsync(final String str, final String str2, final String str3, final Bundle bundle) {
        if (str.equalsIgnoreCase("SYSTEM_PROXY_AGENT")) {
            this.mBackgroundHandler.post(new Runnable() { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService.4
                @Override // java.lang.Runnable
                public final void run() {
                    DDLog.d("DualDARComnService", "relayAsync from bg thread, relay start to System proxy agent : " + str, new Object[0]);
                    SystemProxyAgent.getInstance(DualDARComnService.this.mContext).relay(Binder.getCallingUid(), str2, str3, bundle);
                    DDLog.d("DualDARComnService", "relayAsync from bg thread, relay end to System proxy agent : " + str, new Object[0]);
                }
            });
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("dual_dar_response", true);
            return bundle2;
        }
        final ProxyAgentInfo findAgent = findAgent(str);
        if (findAgent == null) {
            DDLog.e("DualDARComnService", "relay: Agent not found, agent: ".concat(str), new Object[0]);
            return null;
        }
        this.mBackgroundHandler.post(new Runnable() { // from class: com.android.server.knox.dar.ddar.proxy.DualDARComnService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DualDARComnService dualDARComnService = DualDARComnService.this;
                ProxyAgentInfo proxyAgentInfo = findAgent;
                String str4 = str;
                String str5 = str2;
                String str6 = str3;
                Bundle bundle3 = bundle;
                ProxyAgentWrapper connectProxyAgentSync = dualDARComnService.connectProxyAgentSync(proxyAgentInfo);
                DDLog.d("DualDARComnService", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("relayAsync from bg thread, relay start to agent : ", str4), new Object[0]);
                if (connectProxyAgentSync != null) {
                    connectProxyAgentSync.relay(Binder.getCallingUid(), str5, str6, bundle3);
                } else {
                    DDLog.e("DualDARComnService", "ProxyAgentWrapper is NULL !", new Object[0]);
                }
                DDLog.d("DualDARComnService", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("relayAsync from bg thread, relay end to agent : ", str4), new Object[0]);
            }
        });
        Bundle bundle3 = new Bundle();
        bundle3.putBoolean("dual_dar_response", true);
        return bundle3;
    }

    public final void setDeviceUnlockedForUserIfUnsecured(int i) {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        TrustManager trustManager = (TrustManager) this.mContext.getSystemService("trust");
        if (keyguardManager == null || trustManager == null || keyguardManager.isDeviceSecure() || keyguardManager.isDeviceSecure(i)) {
            return;
        }
        DDLog.d("DualDARComnService", "setDeviceUnlockedForUserIfUnsecured", new Object[0]);
        trustManager.setDeviceLockedForUser(0, false);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        TAProxy tAProxy;
        StateMachineProxy stateMachineProxy;
        DDLog.LoggerProxy loggerProxy;
        DDLog.d("DualDARComnService", "initialize", new Object[0]);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        Context context = this.mContext;
        AnonymousClass1 anonymousClass1 = this.mUserBroadcastReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass1, userHandle, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageBroadcastReceiver, userHandle, intentFilter2, null, null);
        SystemProxyAgent systemProxyAgent = SystemProxyAgent.getInstance(this.mContext);
        systemProxyAgent.onCreate();
        TAProxy tAProxy2 = TAProxy.mInstance;
        synchronized (TAProxy.class) {
            try {
                if (TAProxy.mInstance == null) {
                    TAProxy tAProxy3 = new TAProxy();
                    tAProxy3.mTAMap = new HashMap();
                    DDLog.d("TAProxy", "TAProxy() called updated", new Object[0]);
                    TAProxy.mInstance = tAProxy3;
                }
                tAProxy = TAProxy.mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        systemProxyAgent.register("TA_PROXY_SERVICE", tAProxy);
        systemProxyAgent.register("DAEMON_PROXY_SERVICE", DualDARDaemonProxy.getInstance(systemProxyAgent.mContext));
        Context context2 = systemProxyAgent.mContext;
        StateMachineProxy stateMachineProxy2 = StateMachineProxy.mInstance;
        synchronized (StateMachineProxy.class) {
            try {
                if (StateMachineProxy.mInstance == null) {
                    StateMachineProxy.mInstance = new StateMachineProxy(context2);
                }
                stateMachineProxy = StateMachineProxy.mInstance;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        systemProxyAgent.register("STATE_MACHINE_SERVICE", stateMachineProxy);
        DDLog.LoggerProxy loggerProxy2 = DDLog.LoggerProxy.mInstance;
        synchronized (DDLog.LoggerProxy.class) {
            try {
                if (DDLog.LoggerProxy.mInstance == null) {
                    DDLog.LoggerProxy loggerProxy3 = new DDLog.LoggerProxy();
                    Log.d("DDLog", "Logger ready");
                    synchronized (DDLog.Logger.class) {
                        if (DDLog.Logger.mInstance == null) {
                            DDLog.Logger logger = new DDLog.Logger();
                            DDLog.Logger.mInstance = logger;
                            logger.start();
                        }
                    }
                    DDLog.LoggerProxy.mInstance = loggerProxy3;
                }
                loggerProxy = DDLog.LoggerProxy.mInstance;
            } catch (Throwable th3) {
                throw th3;
            }
        }
        systemProxyAgent.register("DDAR_LOG_SERVICE", loggerProxy);
        systemProxyAgent.register("DDAR_CACHE_SERVICE", DDCache.getInstance());
        systemProxyAgent.register("DDAR_PLATFORM_SERVICE", DualDARPlatformProxy.getInstance(systemProxyAgent.mContext));
        systemProxyAgent.register("DDAR_MANAGER_SERVICE", DualDarManagerProxy.getInstance(systemProxyAgent.mContext));
        DDLog.d("DualDARComnService", "initKnownProxyAgents", new Object[0]);
        if (PersonaServiceHelper.isDualDAREnabled()) {
            KnoxProxyManager.getInstance(this.mContext).registerAgentByMetadata("KNOXCORE_PROXY_AGENT", 0, "com.samsung.android.knox.containercore", "proxyAgent.class");
        }
    }
}
