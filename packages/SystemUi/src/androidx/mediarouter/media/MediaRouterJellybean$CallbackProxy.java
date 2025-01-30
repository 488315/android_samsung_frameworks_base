package androidx.mediarouter.media;

import android.media.MediaRouter;
import androidx.mediarouter.media.MediaRouteDescriptor;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.SystemMediaRouteProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MediaRouterJellybean$CallbackProxy extends MediaRouter.Callback {
    public final MediaRouterJellybean$Callback mCallback;

    public MediaRouterJellybean$CallbackProxy(MediaRouterJellybean$Callback mediaRouterJellybean$Callback) {
        this.mCallback = mediaRouterJellybean$Callback;
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteAdded(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        SystemMediaRouteProvider.JellybeanImpl jellybeanImpl = (SystemMediaRouteProvider.JellybeanImpl) this.mCallback;
        if (jellybeanImpl.addSystemRouteNoPublish(routeInfo)) {
            jellybeanImpl.publishRoutes();
        }
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteChanged(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        int findSystemRouteRecord;
        SystemMediaRouteProvider.JellybeanImpl jellybeanImpl = (SystemMediaRouteProvider.JellybeanImpl) this.mCallback;
        jellybeanImpl.getClass();
        if (SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo) != null || (findSystemRouteRecord = jellybeanImpl.findSystemRouteRecord(routeInfo)) < 0) {
            return;
        }
        SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord systemRouteRecord = (SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord) jellybeanImpl.mSystemRouteRecords.get(findSystemRouteRecord);
        String str = systemRouteRecord.mRouteDescriptorId;
        CharSequence name = ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).getName(jellybeanImpl.mContext);
        MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(str, name != null ? name.toString() : "");
        jellybeanImpl.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
        systemRouteRecord.mRouteDescriptor = builder.build();
        jellybeanImpl.publishRoutes();
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteGrouped(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int i) {
        this.mCallback.getClass();
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteRemoved(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        int findSystemRouteRecord;
        SystemMediaRouteProvider.JellybeanImpl jellybeanImpl = (SystemMediaRouteProvider.JellybeanImpl) this.mCallback;
        jellybeanImpl.getClass();
        if (SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo) != null || (findSystemRouteRecord = jellybeanImpl.findSystemRouteRecord(routeInfo)) < 0) {
            return;
        }
        jellybeanImpl.mSystemRouteRecords.remove(findSystemRouteRecord);
        jellybeanImpl.publishRoutes();
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteSelected(android.media.MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
        MediaRouter.RouteInfo findRouteByDescriptorId;
        SystemMediaRouteProvider.JellybeanImpl jellybeanImpl = (SystemMediaRouteProvider.JellybeanImpl) this.mCallback;
        if (routeInfo != ((android.media.MediaRouter) jellybeanImpl.mRouterObj).getSelectedRoute(8388611)) {
            return;
        }
        SystemMediaRouteProvider.JellybeanImpl.UserRouteRecord userRouteRecord = SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo);
        if (userRouteRecord != null) {
            userRouteRecord.mRoute.select();
            return;
        }
        int findSystemRouteRecord = jellybeanImpl.findSystemRouteRecord(routeInfo);
        if (findSystemRouteRecord >= 0) {
            String str = ((SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord) jellybeanImpl.mSystemRouteRecords.get(findSystemRouteRecord)).mRouteDescriptorId;
            MediaRouter.GlobalMediaRouter globalMediaRouter = (MediaRouter.GlobalMediaRouter) jellybeanImpl.mSyncCallback;
            globalMediaRouter.mCallbackHandler.removeMessages(262);
            MediaRouter.ProviderInfo findProviderInfo = globalMediaRouter.findProviderInfo(globalMediaRouter.mSystemProvider);
            if (findProviderInfo == null || (findRouteByDescriptorId = findProviderInfo.findRouteByDescriptorId(str)) == null) {
                return;
            }
            findRouteByDescriptorId.select();
        }
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteUngrouped(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
        this.mCallback.getClass();
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
        this.mCallback.getClass();
    }

    @Override // android.media.MediaRouter.Callback
    public final void onRouteVolumeChanged(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        int findSystemRouteRecord;
        SystemMediaRouteProvider.JellybeanImpl jellybeanImpl = (SystemMediaRouteProvider.JellybeanImpl) this.mCallback;
        jellybeanImpl.getClass();
        if (SystemMediaRouteProvider.JellybeanImpl.getUserRouteRecord(routeInfo) != null || (findSystemRouteRecord = jellybeanImpl.findSystemRouteRecord(routeInfo)) < 0) {
            return;
        }
        SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord systemRouteRecord = (SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord) jellybeanImpl.mSystemRouteRecords.get(findSystemRouteRecord);
        int volume = routeInfo.getVolume();
        if (volume != systemRouteRecord.mRouteDescriptor.mBundle.getInt("volume")) {
            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor);
            builder.mBundle.putInt("volume", volume);
            systemRouteRecord.mRouteDescriptor = builder.build();
            jellybeanImpl.publishRoutes();
        }
    }
}
