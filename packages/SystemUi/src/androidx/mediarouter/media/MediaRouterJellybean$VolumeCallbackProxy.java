package androidx.mediarouter.media;

import android.media.MediaRouter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouterJellybean$VolumeCallbackProxy extends MediaRouter.VolumeCallback {
    public final MediaRouterJellybean$VolumeCallback mCallback;

    public MediaRouterJellybean$VolumeCallbackProxy(MediaRouterJellybean$VolumeCallback mediaRouterJellybean$VolumeCallback) {
        this.mCallback = mediaRouterJellybean$VolumeCallback;
    }

    @Override // android.media.MediaRouter.VolumeCallback
    public final void onVolumeSetRequest(MediaRouter.RouteInfo routeInfo, int i) {
        this.mCallback.onVolumeSetRequest(i, routeInfo);
    }

    @Override // android.media.MediaRouter.VolumeCallback
    public final void onVolumeUpdateRequest(MediaRouter.RouteInfo routeInfo, int i) {
        this.mCallback.onVolumeUpdateRequest(i, routeInfo);
    }
}
