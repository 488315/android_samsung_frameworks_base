package com.android.server.wallpapereffectsgeneration;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService;
import android.util.Slog;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;

public final class RemoteWallpaperEffectsGenerationService
        extends AbstractMultiplePendingRequestsRemoteService {
    public final RemoteWallpaperEffectsGenerationServiceCallback mCallback;

    public interface RemoteWallpaperEffectsGenerationServiceCallback
            extends AbstractRemoteService.VultureCallback {}

    public RemoteWallpaperEffectsGenerationService(
            Context context,
            ComponentName componentName,
            int i,
            RemoteWallpaperEffectsGenerationServiceCallback
                    remoteWallpaperEffectsGenerationServiceCallback,
            boolean z,
            boolean z2) {
        super(
                context,
                "android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService",
                componentName,
                i,
                remoteWallpaperEffectsGenerationServiceCallback,
                context.getMainThreadHandler(),
                z ? 4194304 : 0,
                z2,
                1);
        this.mCallback = remoteWallpaperEffectsGenerationServiceCallback;
    }

    public final void executeOnResolvedService(
            WallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0
                    wallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0) {
        executeAsyncRequest(wallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0);
    }

    public final long getRemoteRequestMillis() {
        return 2000L;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IWallpaperEffectsGenerationService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 120000L;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        RemoteWallpaperEffectsGenerationServiceCallback
                remoteWallpaperEffectsGenerationServiceCallback = this.mCallback;
        if (remoteWallpaperEffectsGenerationServiceCallback != null) {
            WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService =
                    (WallpaperEffectsGenerationPerUserService)
                            remoteWallpaperEffectsGenerationServiceCallback;
            wallpaperEffectsGenerationPerUserService.getClass();
            if (z) {
                return;
            }
            Slog.w(
                    "WallpaperEffectsGenerationPerUserService",
                    "remote wallpaper effects generation service disconnected");
            wallpaperEffectsGenerationPerUserService.updateRemoteServiceLocked();
        }
    }

    public final void reconnect() {
        scheduleBind();
    }
}
