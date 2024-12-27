package com.android.server.smartspace;

import android.app.AppGlobals;
import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.infra.AbstractRemoteService;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.function.Consumer;

public final class SmartspacePerUserService extends AbstractPerUserSystemService
        implements RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks {
    public RemoteSmartspaceService mRemoteService;
    public final ArrayMap mSessionInfos;
    public boolean mZombie;

    public final class SmartspaceSessionInfo {
        public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
        public final IBinder.DeathRecipient mDeathRecipient;
        public final SmartspaceSessionId mSessionId;
        public final SmartspaceConfig mSmartspaceConfig;
        public final IBinder mToken;

        public SmartspaceSessionInfo(
                SmartspaceSessionId smartspaceSessionId,
                SmartspaceConfig smartspaceConfig,
                IBinder iBinder,
                SmartspacePerUserService$$ExternalSyntheticLambda3
                        smartspacePerUserService$$ExternalSyntheticLambda3) {
            this.mSessionId = smartspaceSessionId;
            this.mSmartspaceConfig = smartspaceConfig;
            this.mToken = iBinder;
            this.mDeathRecipient = smartspacePerUserService$$ExternalSyntheticLambda3;
        }
    }

    public SmartspacePerUserService(
            SmartspaceManagerService smartspaceManagerService, Object obj, int i) {
        super(smartspaceManagerService, obj, i);
        this.mSessionInfos = new ArrayMap();
    }

    public final void destroyAndRebindRemoteService$3() {
        if (this.mRemoteService == null) {
            return;
        }
        if (this.mMaster.debug) {
            Slog.d("SmartspacePerUserService", "Destroying the old remote service.");
        }
        this.mRemoteService.destroy();
        this.mRemoteService = null;
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        RemoteSmartspaceService remoteServiceLocked = getRemoteServiceLocked();
        this.mRemoteService = remoteServiceLocked;
        if (remoteServiceLocked != null) {
            if (this.mMaster.debug) {
                Slog.d("SmartspacePerUserService", "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
    }

    public final RemoteSmartspaceService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            String componentNameLocked = getComponentNameLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (componentNameLocked == null) {
                if (!((SmartspaceManagerService) abstractMasterSystemService).verbose) {
                    return null;
                }
                Slog.v("SmartspacePerUserService", "getRemoteServiceLocked(): not set");
                return null;
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(componentNameLocked);
            Context context = abstractMasterSystemService.getContext();
            SmartspaceManagerService smartspaceManagerService =
                    (SmartspaceManagerService) abstractMasterSystemService;
            this.mRemoteService =
                    new RemoteSmartspaceService(
                            context,
                            unflattenFromString,
                            this.mUserId,
                            this,
                            smartspaceManagerService.isBindInstantServiceAllowed(),
                            smartspaceManagerService.verbose);
        }
        return this.mRemoteService;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    public final void onCreateSmartspaceSessionLocked(
            SmartspaceConfig smartspaceConfig,
            final SmartspaceSessionId smartspaceSessionId,
            IBinder iBinder) {
        if (!resolveService$1(
                        new SmartspacePerUserService$$ExternalSyntheticLambda1(
                                smartspaceConfig, smartspaceSessionId))
                || this.mSessionInfos.containsKey(smartspaceSessionId)) {
            return;
        }
        SmartspaceSessionInfo smartspaceSessionInfo =
                new SmartspaceSessionInfo(
                        smartspaceSessionId,
                        smartspaceConfig,
                        iBinder,
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda3
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                SmartspacePerUserService smartspacePerUserService =
                                        SmartspacePerUserService.this;
                                SmartspaceSessionId smartspaceSessionId2 = smartspaceSessionId;
                                synchronized (smartspacePerUserService.mLock) {
                                    smartspacePerUserService.onDestroyLocked(smartspaceSessionId2);
                                }
                            }
                        });
        try {
            smartspaceSessionInfo.mToken.linkToDeath(smartspaceSessionInfo.mDeathRecipient, 0);
            this.mSessionInfos.put(smartspaceSessionId, smartspaceSessionInfo);
        } catch (RemoteException unused) {
            onDestroyLocked(smartspaceSessionId);
        }
    }

    public final void onDestroyLocked(SmartspaceSessionId smartspaceSessionId) {
        if (this.mMaster.debug) {
            Slog.d(
                    "SmartspacePerUserService",
                    "onDestroyLocked(): sessionId=" + smartspaceSessionId);
        }
        SmartspaceSessionInfo smartspaceSessionInfo =
                (SmartspaceSessionInfo) this.mSessionInfos.remove(smartspaceSessionId);
        if (smartspaceSessionInfo == null) {
            return;
        }
        resolveService$1(
                new SmartspacePerUserService$$ExternalSyntheticLambda0(smartspaceSessionId, 0));
        IBinder iBinder = smartspaceSessionInfo.mToken;
        if (iBinder != null) {
            iBinder.unlinkToDeath(smartspaceSessionInfo.mDeathRecipient, 0);
        }
        smartspaceSessionInfo.mCallbacks.kill();
    }

    public final void onServiceDied(Object obj) {
        RemoteSmartspaceService remoteSmartspaceService = (RemoteSmartspaceService) obj;
        if (this.mMaster.debug) {
            Slog.w(
                    "SmartspacePerUserService",
                    "onServiceDied(): service=" + remoteSmartspaceService);
        }
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        RemoteSmartspaceService remoteSmartspaceService2 = this.mRemoteService;
        if (remoteSmartspaceService2 != null) {
            remoteSmartspaceService2.destroy();
            this.mRemoteService = null;
        }
    }

    public final boolean resolveService$1(AbstractRemoteService.AsyncRequest asyncRequest) {
        RemoteSmartspaceService remoteServiceLocked = getRemoteServiceLocked();
        if (remoteServiceLocked != null) {
            remoteServiceLocked.executeOnResolvedService(asyncRequest);
        }
        return remoteServiceLocked != null;
    }

    public final void resurrectSessionsLocked$3() {
        int size = this.mSessionInfos.size();
        if (this.mMaster.debug) {
            Slog.d(
                    "SmartspacePerUserService",
                    "Resurrecting remote service ("
                            + this.mRemoteService
                            + ") on "
                            + size
                            + " sessions.");
        }
        for (final SmartspaceSessionInfo smartspaceSessionInfo : this.mSessionInfos.values()) {
            IBinder iBinder = smartspaceSessionInfo.mToken;
            smartspaceSessionInfo.mCallbacks.getRegisteredCallbackCount();
            onCreateSmartspaceSessionLocked(
                    smartspaceSessionInfo.mSmartspaceConfig,
                    smartspaceSessionInfo.mSessionId,
                    iBinder);
            smartspaceSessionInfo.mCallbacks.broadcast(
                    new Consumer() { // from class:
                                     // com.android.server.smartspace.SmartspacePerUserService$SmartspaceSessionInfo$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo2 =
                                    SmartspacePerUserService.SmartspaceSessionInfo.this;
                            SmartspacePerUserService smartspacePerUserService = this;
                            ISmartspaceCallback iSmartspaceCallback = (ISmartspaceCallback) obj;
                            SmartspaceSessionId smartspaceSessionId =
                                    smartspaceSessionInfo2.mSessionId;
                            SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo3 =
                                    (SmartspacePerUserService.SmartspaceSessionInfo)
                                            smartspacePerUserService.mSessionInfos.get(
                                                    smartspaceSessionId);
                            if (smartspaceSessionInfo3 != null
                                    && smartspacePerUserService.resolveService$1(
                                            new SmartspacePerUserService$$ExternalSyntheticLambda4(
                                                    smartspaceSessionId, iSmartspaceCallback, 1))) {
                                smartspaceSessionInfo3.mCallbacks.register(iSmartspaceCallback);
                            }
                        }
                    });
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked) {
            if (isEnabledLocked()) {
                resurrectSessionsLocked$3();
            } else {
                RemoteSmartspaceService remoteSmartspaceService = this.mRemoteService;
                if (remoteSmartspaceService != null) {
                    remoteSmartspaceService.destroy();
                    this.mRemoteService = null;
                }
            }
        }
        return updateLocked;
    }
}
