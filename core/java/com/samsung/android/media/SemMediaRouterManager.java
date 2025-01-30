package com.samsung.android.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class SemMediaRouterManager {
    private static final String TAG = "SemMRManager";
    private static SemMediaRouterManager sInstance;
    private static final Object sLock = new Object();
    private final MediaRouter2Manager mManager;

    public static SemMediaRouterManager getInstance(Context context) {
        SemMediaRouterManager semMediaRouterManager;
        Objects.requireNonNull(context, "context must not be null");
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new SemMediaRouterManager(context);
            }
            semMediaRouterManager = sInstance;
        }
        return semMediaRouterManager;
    }

    private SemMediaRouterManager(Context context) {
        this.mManager = MediaRouter2Manager.getInstance(context);
    }

    public void registerCallback(Executor executor, Callback callback) {
        this.mManager.registerCallback(executor, new CallbackRecord(callback));
    }

    public void unregisterCallback(Callback callback) {
        this.mManager.unregisterCallback(new CallbackRecord(callback));
    }

    final class CallbackRecord implements MediaRouter2Manager.Callback {
        public final Callback mCallback;

        CallbackRecord(Callback callback) {
            this.mCallback = callback;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof CallbackRecord) && this.mCallback == ((CallbackRecord) obj).mCallback;
        }

        public int hashCode() {
            return this.mCallback.hashCode();
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onRoutesUpdated() {
            this.mCallback.onRoutesUpdated();
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onSessionUpdated(RoutingSessionInfo session) {
            this.mCallback.onSessionUpdated(session);
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onSessionReleased(RoutingSessionInfo session) {
            this.mCallback.onSessionReleased(session);
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onTransferred(RoutingSessionInfo oldSession, RoutingSessionInfo newSession) {
            this.mCallback.onTransferred(oldSession, newSession);
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onTransferFailed(RoutingSessionInfo session, MediaRoute2Info route) {
            this.mCallback.onTransferFailed(session, route);
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onPreferredFeaturesChanged(String packageName, List<String> preferredFeatures) {
            this.mCallback.onPreferredFeaturesChanged(packageName, preferredFeatures);
        }

        @Override // android.media.MediaRouter2Manager.Callback
        public void onRequestFailed(int reason) {
            this.mCallback.onRequestFailed(reason);
        }
    }

    public void startScan() {
        this.mManager.registerScanRequest();
    }

    public void stopScan() {
        this.mManager.unregisterScanRequest();
    }

    public MediaController getMediaControllerForRoutingSession(RoutingSessionInfo sessionInfo) {
        return this.mManager.getMediaControllerForRoutingSession(sessionInfo);
    }

    public List<MediaRoute2Info> getAvailableRoutes(String packageName) {
        return this.mManager.getAvailableRoutes(packageName);
    }

    public List<MediaRoute2Info> getTransferableRoutes(String packageName) {
        return this.mManager.getTransferableRoutes(packageName);
    }

    public List<MediaRoute2Info> getAvailableRoutes(RoutingSessionInfo sessionInfo) {
        return this.mManager.getAvailableRoutes(sessionInfo);
    }

    public List<MediaRoute2Info> getTransferableRoutes(RoutingSessionInfo sessionInfo) {
        return this.mManager.getTransferableRoutes(sessionInfo);
    }

    public RouteDiscoveryPreference getDiscoveryPreference(String packageName) {
        return this.mManager.getDiscoveryPreference(packageName);
    }

    public RoutingSessionInfo getSystemRoutingSession(String packageName) {
        return this.mManager.getSystemRoutingSession(packageName);
    }

    public RoutingSessionInfo getRoutingSessionForMediaController(MediaController mediaController) {
        return this.mManager.getRoutingSessionForMediaController(mediaController);
    }

    public List<RoutingSessionInfo> getRoutingSessions(String packageName) {
        return this.mManager.getRoutingSessions(packageName);
    }

    public List<RoutingSessionInfo> getRemoteSessions() {
        return this.mManager.getRemoteSessions();
    }

    public List<MediaRoute2Info> getAllRoutes() {
        return this.mManager.getAllRoutes();
    }

    public void selectRoute(String packageName, MediaRoute2Info route) {
        this.mManager.transfer(packageName, route);
    }

    public void transfer(RoutingSessionInfo sessionInfo, MediaRoute2Info route) {
        this.mManager.transfer(sessionInfo, route);
    }

    public void setRouteVolume(MediaRoute2Info route, int volume) {
        this.mManager.setRouteVolume(route, volume);
    }

    public void setSessionVolume(RoutingSessionInfo sessionInfo, int volume) {
        this.mManager.setSessionVolume(sessionInfo, volume);
    }

    public List<MediaRoute2Info> getSelectedRoutes(RoutingSessionInfo sessionInfo) {
        return this.mManager.getSelectedRoutes(sessionInfo);
    }

    public List<MediaRoute2Info> getSelectableRoutes(RoutingSessionInfo sessionInfo) {
        return this.mManager.getSelectableRoutes(sessionInfo);
    }

    public List<MediaRoute2Info> getDeselectableRoutes(RoutingSessionInfo sessionInfo) {
        return this.mManager.getDeselectableRoutes(sessionInfo);
    }

    public void selectRoute(RoutingSessionInfo sessionInfo, MediaRoute2Info route) {
        this.mManager.selectRoute(sessionInfo, route);
    }

    public void deselectRoute(RoutingSessionInfo sessionInfo, MediaRoute2Info route) {
        this.mManager.deselectRoute(sessionInfo, route);
    }

    public void releaseSession(RoutingSessionInfo sessionInfo) {
        this.mManager.releaseSession(sessionInfo);
    }

    public String getRouteAddress(MediaRoute2Info route) {
        return route.getAddress();
    }

    public int getRouteType(MediaRoute2Info route) {
        return route.getType();
    }

    public interface Callback {
        default void onRoutesUpdated() {
        }

        default void onSessionUpdated(RoutingSessionInfo session) {
        }

        default void onSessionReleased(RoutingSessionInfo session) {
        }

        default void onTransferred(RoutingSessionInfo oldSession, RoutingSessionInfo newSession) {
        }

        default void onTransferFailed(RoutingSessionInfo session, MediaRoute2Info route) {
        }

        default void onPreferredFeaturesChanged(String packageName, List<String> preferredFeatures) {
        }

        default void onRequestFailed(int reason) {
        }

        default void onDiscoveryPreferenceChanged(String packageName, RouteDiscoveryPreference discoveryPreference) {
        }
    }
}
