package com.android.server.appprediction;

import android.app.AppGlobals;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.service.appprediction.IPredictionService;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.LocalServices;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.appprediction.AppPredictionPerUserService;
import com.android.server.appprediction.RemoteAppPredictionService;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.people.PeopleServiceInternal;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppPredictionPerUserService extends AbstractPerUserSystemService implements RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks {
    public RemoteAppPredictionService mRemoteService;
    public final ArrayMap mSessionInfos;
    public boolean mZombie;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppPredictionSessionInfo {
        public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
        public final IBinder.DeathRecipient mDeathRecipient;
        public final AppPredictionContext mPredictionContext;
        public final AppPredictionSessionId mSessionId;
        public final IBinder mToken;
        public final boolean mUsesPeopleService;

        public AppPredictionSessionInfo(AppPredictionSessionId appPredictionSessionId, AppPredictionContext appPredictionContext, boolean z, IBinder iBinder, AppPredictionPerUserService$$ExternalSyntheticLambda2 appPredictionPerUserService$$ExternalSyntheticLambda2) {
            this.mSessionId = appPredictionSessionId;
            this.mPredictionContext = appPredictionContext;
            this.mUsesPeopleService = z;
            this.mToken = iBinder;
            this.mDeathRecipient = appPredictionPerUserService$$ExternalSyntheticLambda2;
        }
    }

    public AppPredictionPerUserService(AppPredictionManagerService appPredictionManagerService, Object obj, int i) {
        super(appPredictionManagerService, obj, i);
        this.mSessionInfos = new ArrayMap();
    }

    public final void destroyAndRebindRemoteService$1() {
        if (this.mRemoteService == null) {
            return;
        }
        if (this.mMaster.debug) {
            Slog.d("AppPredictionPerUserService", "Destroying the old remote service.");
        }
        this.mRemoteService.destroy();
        this.mRemoteService = null;
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        RemoteAppPredictionService remoteServiceLocked = getRemoteServiceLocked();
        this.mRemoteService = remoteServiceLocked;
        if (remoteServiceLocked != null) {
            if (this.mMaster.debug) {
                Slog.d("AppPredictionPerUserService", "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
    }

    public final RemoteAppPredictionService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            String componentNameLocked = getComponentNameLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (componentNameLocked == null) {
                if (!((AppPredictionManagerService) abstractMasterSystemService).verbose) {
                    return null;
                }
                Slog.v("AppPredictionPerUserService", "getRemoteServiceLocked(): not set");
                return null;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(componentNameLocked);
            Context context = abstractMasterSystemService.getContext();
            AppPredictionManagerService appPredictionManagerService = (AppPredictionManagerService) abstractMasterSystemService;
            this.mRemoteService = new RemoteAppPredictionService(context, unflattenFromString, this.mUserId, this, appPredictionManagerService.isBindInstantServiceAllowed(), appPredictionManagerService.verbose);
        }
        return this.mRemoteService;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Could not get service for "));
        }
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda2] */
    public final void onCreatePredictionSessionLocked(AppPredictionContext appPredictionContext, final AppPredictionSessionId appPredictionSessionId, IBinder iBinder) {
        boolean z = (appPredictionContext.getExtras() != null && appPredictionContext.getExtras().getBoolean("remote_app_predictor", false) && DeviceConfig.getBoolean("systemui", "dark_launch_remote_prediction_service_enabled", false)) ? false : DeviceConfig.getBoolean("systemui", "predict_using_people_service_" + appPredictionContext.getUiSurface(), false);
        if (!resolveService(true, z, new AppPredictionPerUserService$$ExternalSyntheticLambda1(appPredictionContext, appPredictionSessionId)) || this.mSessionInfos.containsKey(appPredictionSessionId)) {
            return;
        }
        AppPredictionSessionInfo appPredictionSessionInfo = new AppPredictionSessionInfo(appPredictionSessionId, appPredictionContext, z, iBinder, new IBinder.DeathRecipient() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                AppPredictionPerUserService appPredictionPerUserService = AppPredictionPerUserService.this;
                AppPredictionSessionId appPredictionSessionId2 = appPredictionSessionId;
                synchronized (appPredictionPerUserService.mLock) {
                    appPredictionPerUserService.onDestroyPredictionSessionLocked(appPredictionSessionId2);
                }
            }
        });
        try {
            appPredictionSessionInfo.mToken.linkToDeath(appPredictionSessionInfo.mDeathRecipient, 0);
            this.mSessionInfos.put(appPredictionSessionId, appPredictionSessionInfo);
        } catch (RemoteException unused) {
            onDestroyPredictionSessionLocked(appPredictionSessionId);
        }
    }

    public final void onDestroyPredictionSessionLocked(AppPredictionSessionId appPredictionSessionId) {
        if (this.mMaster.debug) {
            Slog.d("AppPredictionPerUserService", "onDestroyPredictionSessionLocked(): sessionId=" + appPredictionSessionId);
        }
        AppPredictionSessionInfo appPredictionSessionInfo = (AppPredictionSessionInfo) this.mSessionInfos.remove(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(false, appPredictionSessionInfo.mUsesPeopleService, new AppPredictionPerUserService$$ExternalSyntheticLambda3(appPredictionSessionId, 0));
        IBinder iBinder = appPredictionSessionInfo.mToken;
        if (iBinder != null) {
            iBinder.unlinkToDeath(appPredictionSessionInfo.mDeathRecipient, 0);
        }
        appPredictionSessionInfo.mCallbacks.kill();
    }

    public final void onServiceDied(Object obj) {
        RemoteAppPredictionService remoteAppPredictionService = (RemoteAppPredictionService) obj;
        if (this.mMaster.debug) {
            Slog.w("AppPredictionPerUserService", "onServiceDied(): service=" + remoteAppPredictionService);
        }
        synchronized (this.mLock) {
            this.mZombie = true;
        }
    }

    public final void registerPredictionUpdatesLocked(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) {
        AppPredictionSessionInfo appPredictionSessionInfo = (AppPredictionSessionInfo) this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        if (resolveService(true, appPredictionSessionInfo.mUsesPeopleService, new AppPredictionPerUserService$$ExternalSyntheticLambda4(appPredictionSessionId, iPredictionCallback, 0))) {
            appPredictionSessionInfo.mCallbacks.register(iPredictionCallback);
        }
    }

    public final boolean resolveService(boolean z, boolean z2, AbstractRemoteService.AsyncRequest asyncRequest) {
        if (!z2) {
            RemoteAppPredictionService remoteServiceLocked = getRemoteServiceLocked();
            if (remoteServiceLocked != null) {
                if (z) {
                    remoteServiceLocked.executeOnResolvedService(asyncRequest);
                } else {
                    remoteServiceLocked.scheduleOnResolvedService(asyncRequest);
                }
            }
            return remoteServiceLocked != null;
        }
        IPredictionService iPredictionService = (IPredictionService) LocalServices.getService(PeopleServiceInternal.class);
        if (iPredictionService != null) {
            try {
                asyncRequest.run(iPredictionService);
            } catch (RemoteException e) {
                Slog.w("AppPredictionPerUserService", "Failed to invoke service:" + iPredictionService, e);
            }
        }
        return iPredictionService != null;
    }

    public final void resurrectSessionsLocked() {
        int size = this.mSessionInfos.size();
        if (this.mMaster.debug) {
            Slog.d("AppPredictionPerUserService", "Resurrecting remote service (" + this.mRemoteService + ") on " + size + " sessions.");
        }
        for (final AppPredictionSessionInfo appPredictionSessionInfo : this.mSessionInfos.values()) {
            IBinder iBinder = appPredictionSessionInfo.mToken;
            appPredictionSessionInfo.mCallbacks.getRegisteredCallbackCount();
            onCreatePredictionSessionLocked(appPredictionSessionInfo.mPredictionContext, appPredictionSessionInfo.mSessionId, iBinder);
            appPredictionSessionInfo.mCallbacks.broadcast(new Consumer() { // from class: com.android.server.appprediction.AppPredictionPerUserService$AppPredictionSessionInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.registerPredictionUpdatesLocked(AppPredictionPerUserService.AppPredictionSessionInfo.this.mSessionId, (IPredictionCallback) obj);
                }
            });
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked && !isEnabledLocked()) {
            this.mRemoteService = null;
        }
        return updateLocked;
    }
}
