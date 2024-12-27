package com.android.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.pm.PersonaServiceProxy;

import com.samsung.android.knox.IContainerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

public final class ContainerServiceWrapper {
    public final ContainerServiceInfo info;
    public boolean mBound;
    public IContainerService mContainerService;
    public final Context mContext;
    public final PersonaServiceProxy mService;
    public final ComponentName name;
    public final int userid;
    public final AnonymousClass1 mConnection =
            new ServiceConnection() { // from class: com.android.server.ContainerServiceWrapper.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    ContainerServiceWrapper.this.getClass();
                    Log.v(
                            "KnoxService::ContainerServiceWrapper",
                            "Container service started : "
                                    + ContainerServiceWrapper.this.info.toString());
                    removeMessages(1);
                    ContainerServiceWrapper.this.mContainerService =
                            IContainerService.Stub.asInterface(iBinder);
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    ContainerServiceWrapper.this.getClass();
                    Log.v(
                            "KnoxService::ContainerServiceWrapper",
                            "Container service disconnected : "
                                    + ContainerServiceWrapper.this.info.toString());
                    ContainerServiceWrapper containerServiceWrapper = ContainerServiceWrapper.this;
                    if (containerServiceWrapper.mBound) {
                        containerServiceWrapper.mBound = false;
                        AnonymousClass2 anonymousClass2 = containerServiceWrapper.mHandler;
                        anonymousClass2.removeMessages(1);
                        anonymousClass2.sendEmptyMessage(1);
                    }
                }
            };
    public final AnonymousClass2 mHandler =
            new Handler() { // from class: com.android.server.ContainerServiceWrapper.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    ContainerServiceWrapper containerServiceWrapper = ContainerServiceWrapper.this;
                    PersonaServiceProxy personaServiceProxy = containerServiceWrapper.mService;
                    ContainerServiceInfo containerServiceInfo = containerServiceWrapper.info;
                    personaServiceProxy.getClass();
                    if (containerServiceInfo.category.equals("core")) {
                        for (UserInfo userInfo : personaServiceProxy.mUserManager.getUsers(true)) {
                            if (userInfo.isManagedProfile()
                                    && personaServiceProxy.mKeyguardManager.isDeviceSecure(
                                            userInfo.id)) {
                                int focusedKnoxId =
                                        personaServiceProxy.mPersonaManager.getFocusedKnoxId();
                                personaServiceProxy.mTrustManager.setDeviceLockedForUser(
                                        userInfo.id, true);
                                personaServiceProxy.mLockPatternUtils.requireStrongAuth(
                                        8, userInfo.id);
                                if (focusedKnoxId == userInfo.id) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.MAIN");
                                    intent.addCategory("android.intent.category.HOME");
                                    intent.setFlags(268435456);
                                    ActivityInfo activityInfo =
                                            personaServiceProxy
                                                    .mContext
                                                    .getPackageManager()
                                                    .resolveActivityAsUser(
                                                            intent,
                                                            EndpointMonitorConst
                                                                    .FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT,
                                                            0)
                                                    .activityInfo;
                                    intent.setComponent(
                                            new ComponentName(
                                                    activityInfo.packageName, activityInfo.name));
                                    intent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
                                    personaServiceProxy.mContext.startActivityAsUser(
                                            intent, UserHandle.SYSTEM);
                                }
                            }
                        }
                    }
                    Log.d(
                            "PersonaManagerService::Proxy",
                            "reconnectContainerService " + containerServiceInfo.toString());
                    synchronized (personaServiceProxy.mContainerServiceLock) {
                    }
                    if (!personaServiceProxy.mUserManager.isUserRunning(
                            containerServiceInfo.userid)) {
                        Log.e(
                                "PersonaManagerService::Proxy",
                                "connectService() user<"
                                        + containerServiceInfo.userid
                                        + "> is not running");
                        return;
                    }
                    try {
                        if (IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
                                .isPackageAvailable(
                                        containerServiceInfo.name.getPackageName(),
                                        containerServiceInfo.userid)) {
                            personaServiceProxy.maybeConnectContainerService(containerServiceInfo);
                        } else {
                            Log.e(
                                    "PersonaManagerService::Proxy",
                                    "Not installed service " + containerServiceInfo.toString());
                        }
                    } catch (RemoteException e) {
                        Log.e("PersonaManagerService::Proxy", "reconnectService remote exception");
                        e.printStackTrace();
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.ContainerServiceWrapper$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.ContainerServiceWrapper$2] */
    public ContainerServiceWrapper(
            Context context,
            PersonaServiceProxy personaServiceProxy,
            ContainerServiceInfo containerServiceInfo) {
        this.mContext = context;
        this.mService = personaServiceProxy;
        this.info = containerServiceInfo;
        this.userid = containerServiceInfo.userid;
        this.name = containerServiceInfo.name;
    }

    public final boolean connect() {
        if (this.mBound) {
            Log.e(
                    "KnoxService::ContainerServiceWrapper",
                    "service " + this.name.flattenToShortString() + " already bound");
            return true;
        }
        boolean bindServiceAsUser =
                this.mContext.bindServiceAsUser(
                        new Intent().setComponent(this.name),
                        this.mConnection,
                        67108865,
                        new UserHandle(this.userid));
        this.mBound = bindServiceAsUser;
        if (!bindServiceAsUser) {
            Log.e(
                    "KnoxService::ContainerServiceWrapper",
                    "Can't bind to container service " + this.name.flattenToShortString());
        }
        return this.mBound;
    }
}
