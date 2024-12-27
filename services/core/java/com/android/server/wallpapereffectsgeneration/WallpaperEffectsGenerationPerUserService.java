package com.android.server.wallpapereffectsgeneration;

import android.app.AppGlobals;
import android.app.wallpapereffectsgeneration.CinematicEffectResponse;
import android.app.wallpapereffectsgeneration.ICinematicEffectListener;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WallpaperEffectsGenerationPerUserService extends AbstractPerUserSystemService
        implements RemoteWallpaperEffectsGenerationService
                .RemoteWallpaperEffectsGenerationServiceCallback {
    public CinematicEffectListenerWrapper mCinematicEffectListenerWrapper;
    public RemoteWallpaperEffectsGenerationService mRemoteService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CinematicEffectListenerWrapper {
        public final ICinematicEffectListener mListener;
        public final String mTaskId;

        public CinematicEffectListenerWrapper(
                String str, ICinematicEffectListener iCinematicEffectListener) {
            this.mTaskId = str;
            this.mListener = iCinematicEffectListener;
        }
    }

    public final void destroyAndRebindRemoteService() {
        if (this.mRemoteService == null) {
            return;
        }
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        if (abstractMasterSystemService.debug) {
            Slog.d(
                    "WallpaperEffectsGenerationPerUserService",
                    "Destroying the old remote service.");
        }
        this.mRemoteService.destroy();
        this.mRemoteService = null;
        RemoteWallpaperEffectsGenerationService ensureRemoteServiceLocked =
                ensureRemoteServiceLocked();
        this.mRemoteService = ensureRemoteServiceLocked;
        if (ensureRemoteServiceLocked != null) {
            if (abstractMasterSystemService.debug) {
                Slog.d(
                        "WallpaperEffectsGenerationPerUserService",
                        "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
        CinematicEffectListenerWrapper cinematicEffectListenerWrapper =
                this.mCinematicEffectListenerWrapper;
        if (cinematicEffectListenerWrapper != null) {
            invokeCinematicListenerAndCleanup(
                    new CinematicEffectResponse.Builder(0, cinematicEffectListenerWrapper.mTaskId)
                            .build());
        }
    }

    public final RemoteWallpaperEffectsGenerationService ensureRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            ComponentName updateServiceInfoLocked = updateServiceInfoLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (updateServiceInfoLocked == null) {
                if (!((WallpaperEffectsGenerationManagerService) abstractMasterSystemService)
                        .verbose) {
                    return null;
                }
                Slog.v(
                        "WallpaperEffectsGenerationPerUserService",
                        "ensureRemoteServiceLocked(): not set");
                return null;
            }
            this.mRemoteService =
                    new RemoteWallpaperEffectsGenerationService(
                            abstractMasterSystemService.getContext(),
                            updateServiceInfoLocked,
                            this.mUserId,
                            this,
                            ((WallpaperEffectsGenerationManagerService) abstractMasterSystemService)
                                    .isBindInstantServiceAllowed(),
                            ((WallpaperEffectsGenerationManagerService) abstractMasterSystemService)
                                    .verbose);
        }
        return this.mRemoteService;
    }

    public final void invokeCinematicListenerAndCleanup(
            CinematicEffectResponse cinematicEffectResponse) {
        ICinematicEffectListener iCinematicEffectListener;
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        try {
            try {
                CinematicEffectListenerWrapper cinematicEffectListenerWrapper =
                        this.mCinematicEffectListenerWrapper;
                if (cinematicEffectListenerWrapper != null
                        && (iCinematicEffectListener = cinematicEffectListenerWrapper.mListener)
                                != null) {
                    iCinematicEffectListener.onCinematicEffectGenerated(cinematicEffectResponse);
                } else if (abstractMasterSystemService.debug) {
                    Slog.w(
                            "WallpaperEffectsGenerationPerUserService",
                            "Cinematic effect listener not found for task["
                                    + this.mCinematicEffectListenerWrapper.mTaskId
                                    + "]");
                }
            } catch (RemoteException unused) {
                if (abstractMasterSystemService.debug) {
                    Slog.w(
                            "WallpaperEffectsGenerationPerUserService",
                            "Error invoking cinematic effect listener for task["
                                    + this.mCinematicEffectListenerWrapper.mTaskId
                                    + "]");
                }
            }
            this.mCinematicEffectListenerWrapper = null;
        } catch (Throwable th) {
            this.mCinematicEffectListenerWrapper = null;
            throw th;
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager()
                            .getServiceInfo(componentName, 128L, this.mUserId);
            if ("android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE"
                    .equals(serviceInfo.permission)) {
                return serviceInfo;
            }
            Slog.w(
                    "WallpaperEffectsGenerationPerUserService",
                    "WallpaperEffectsGenerationService from '"
                            + serviceInfo.packageName
                            + "' does not require permission"
                            + " android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE");
            throw new SecurityException(
                    "Service does not require permission"
                        + " android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE");
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    public final void onServiceDied(Object obj) {
        Slog.w(
                "WallpaperEffectsGenerationPerUserService",
                "remote wallpaper effects generation service died");
        updateRemoteServiceLocked();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        updateRemoteServiceLocked();
        return updateLocked;
    }

    public final void updateRemoteServiceLocked() {
        RemoteWallpaperEffectsGenerationService remoteWallpaperEffectsGenerationService =
                this.mRemoteService;
        if (remoteWallpaperEffectsGenerationService != null) {
            remoteWallpaperEffectsGenerationService.destroy();
            this.mRemoteService = null;
        }
        CinematicEffectListenerWrapper cinematicEffectListenerWrapper =
                this.mCinematicEffectListenerWrapper;
        if (cinematicEffectListenerWrapper != null) {
            invokeCinematicListenerAndCleanup(
                    new CinematicEffectResponse.Builder(0, cinematicEffectListenerWrapper.mTaskId)
                            .build());
        }
    }
}
