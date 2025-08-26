package com.android.settingslib.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class ManagerInfoMediaManager extends InfoMediaManager {
    public static final boolean DEBUG = Log.isLoggable("ManagerInfoMediaManager", 3);
    public final Executor mExecutor;
    public boolean mIsScanning;
    final RouterManagerCallback mMediaRouterCallback;
    MediaRouter2Manager mRouterManager;

    public ManagerInfoMediaManager(Context context, String str, UserHandle userHandle, LocalBluetoothManager localBluetoothManager, MediaController mediaController) {
        super(context, str, userHandle, localBluetoothManager, mediaController);
        this.mMediaRouterCallback = new RouterManagerCallback();
        this.mIsScanning = false;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
        this.mRouterManager.deselectRoute(routingSessionInfo, mediaRoute2Info);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getDeselectableRoutes(RoutingSessionInfo routingSessionInfo) {
        return this.mRouterManager.getDeselectableRoutes(routingSessionInfo);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RouteListingPreference getRouteListingPreference() {
        return this.mRouterManager.getRouteListingPreference(this.mPackageName);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getRoutingSessionsForPackage() {
        return this.mRouterManager.getRoutingSessions(this.mPackageName);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectableRoutes(RoutingSessionInfo routingSessionInfo) {
        return this.mRouterManager.getSelectableRoutes(routingSessionInfo);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectedRoutes(RoutingSessionInfo routingSessionInfo) {
        return this.mRouterManager.getSelectedRoutes(routingSessionInfo);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getTransferableRoutes(RoutingSessionInfo routingSessionInfo) {
        return this.mRouterManager.getTransferableRoutes(routingSessionInfo);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void registerRouter() {
        this.mRouterManager.registerCallback(this.mExecutor, this.mMediaRouterCallback);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void releaseSession(RoutingSessionInfo routingSessionInfo) {
        this.mRouterManager.releaseSession(routingSessionInfo);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
        this.mRouterManager.selectRoute(routingSessionInfo, mediaRoute2Info);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
        this.mRouterManager.setRouteVolume(mediaRoute2Info, i);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {
        this.mRouterManager.setSessionVolume(routingSessionInfo, i);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void startScanOnRouter() {
        if (this.mIsScanning) {
            return;
        }
        this.mRouterManager.registerScanRequest();
        this.mIsScanning = true;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void stopScanOnRouter() {
        if (this.mIsScanning) {
            this.mRouterManager.unregisterScanRequest();
            this.mIsScanning = false;
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void transferToRoute(MediaRoute2Info mediaRoute2Info) {
        this.mRouterManager.transfer(this.mPackageName, mediaRoute2Info, this.mUserHandle);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void unregisterRouter() {
        this.mRouterManager.unregisterCallback(this.mMediaRouterCallback);
    }

    final class RouterManagerCallback implements MediaRouter2Manager.Callback {
        public RouterManagerCallback() {
        }

        public final void onPreferredFeaturesChanged(String str, List list) {
            if (TextUtils.equals(ManagerInfoMediaManager.this.mPackageName, str)) {
                ManagerInfoMediaManager.this.refreshDevices();
            }
        }

        public final void onRequestFailed(int i) {
            ManagerInfoMediaManager.this.dispatchOnRequestFailed(i);
        }

        public final void onRouteListingPreferenceUpdated(String str, RouteListingPreference routeListingPreference) {
            if (TextUtils.equals(ManagerInfoMediaManager.this.mPackageName, str)) {
                InfoMediaManager.Api34Impl.onRouteListingPreferenceUpdated(routeListingPreference, ManagerInfoMediaManager.this.mPreferenceItemMap);
                ManagerInfoMediaManager.this.refreshDevices();
            }
        }

        public final void onRoutesUpdated() {
            ManagerInfoMediaManager.this.refreshDevices();
        }

        public final void onSessionReleased(RoutingSessionInfo routingSessionInfo) {
            ManagerInfoMediaManager.this.refreshDevices();
        }

        public final void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
            ManagerInfoMediaManager.this.refreshDevices();
        }

        public final void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
            if (ManagerInfoMediaManager.DEBUG) {
                Log.d("ManagerInfoMediaManager", "onTransferred() oldSession : " + ((Object) routingSessionInfo.getName()) + ", newSession : " + ((Object) routingSessionInfo2.getName()));
            }
            ManagerInfoMediaManager.this.rebuildDeviceList();
            ManagerInfoMediaManager.this.notifyCurrentConnectedDeviceChanged();
        }

        public final void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        }
    }
}
