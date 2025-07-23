package android.app.wallpapereffectsgeneration;

import android.annotation.SystemApi;
import android.app.wallpapereffectsgeneration.ICinematicEffectListener;
import android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager;
import android.os.RemoteException;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public final class WallpaperEffectsGenerationManager {
    private final IWallpaperEffectsGenerationManager mService;

    public interface CinematicEffectListener {
        void onCinematicEffectGenerated(CinematicEffectResponse cinematicEffectResponse);
    }

    public WallpaperEffectsGenerationManager(IWallpaperEffectsGenerationManager iWallpaperEffectsGenerationManager) {
        this.mService = iWallpaperEffectsGenerationManager;
    }

    @SystemApi
    public void generateCinematicEffect(CinematicEffectRequest cinematicEffectRequest, Executor executor, CinematicEffectListener cinematicEffectListener) {
        try {
            this.mService.generateCinematicEffect(cinematicEffectRequest, new CinematicEffectListenerWrapper(cinematicEffectListener, executor));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CinematicEffectListenerWrapper extends ICinematicEffectListener.Stub {
        private final Executor mExecutor;
        private final CinematicEffectListener mListener;

        CinematicEffectListenerWrapper(CinematicEffectListener cinematicEffectListener, Executor executor) {
            this.mListener = cinematicEffectListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCinematicEffectGenerated$0(CinematicEffectResponse cinematicEffectResponse) {
            this.mListener.onCinematicEffectGenerated(cinematicEffectResponse);
        }

        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(final CinematicEffectResponse cinematicEffectResponse) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager$CinematicEffectListenerWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperEffectsGenerationManager.CinematicEffectListenerWrapper.this.lambda$onCinematicEffectGenerated$0(cinematicEffectResponse);
                }
            });
        }
    }
}
