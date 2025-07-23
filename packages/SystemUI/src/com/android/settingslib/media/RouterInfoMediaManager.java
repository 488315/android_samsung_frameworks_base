package com.android.settingslib.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2;
import android.media.MediaRouter2Manager;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.UserHandle;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RouterInfoMediaManager extends InfoMediaManager {
    public final ControllerCallback mControllerCallback;
    public final Executor mExecutor;
    public final RouteCallback mRouteCallback;
    public final RouterInfoMediaManager$$ExternalSyntheticLambda3 mRouteListingPreferenceCallback;
    public final MediaRouter2 mRouter;
    public final MediaRouter2Manager mRouterManager;
    public MediaRouter2.ScanToken mScanToken;
    public final TransferCallback mTransferCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ControllerCallback extends MediaRouter2.ControllerCallback {
        public /* synthetic */ ControllerCallback(RouterInfoMediaManager routerInfoMediaManager, int i) {
            this();
        }

        @Override // android.media.MediaRouter2.ControllerCallback
        public final void onControllerUpdated(MediaRouter2.RoutingController routingController) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        private ControllerCallback() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RouteCallback extends MediaRouter2.RouteCallback {
        public /* synthetic */ RouteCallback(RouterInfoMediaManager routerInfoMediaManager, int i) {
            this();
        }

        public final void onPreferredFeaturesChanged(List list) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        @Override // android.media.MediaRouter2.RouteCallback
        public final void onRoutesUpdated(List list) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        private RouteCallback() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransferCallback extends MediaRouter2.TransferCallback {
        public /* synthetic */ TransferCallback(RouterInfoMediaManager routerInfoMediaManager, int i) {
            this();
        }

        public final void onRequestFailed(int i) {
            RouterInfoMediaManager.this.dispatchOnRequestFailed(i);
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onStop(MediaRouter2.RoutingController routingController) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransfer(MediaRouter2.RoutingController routingController, MediaRouter2.RoutingController routingController2) {
            RouterInfoMediaManager.this.rebuildDeviceList();
            RouterInfoMediaManager.this.notifyCurrentConnectedDeviceChanged();
        }

        private TransferCallback() {
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransferFailure(MediaRoute2Info mediaRoute2Info) {
        }
    }

    public RouterInfoMediaManager(Context context, String str, UserHandle userHandle, LocalBluetoothManager localBluetoothManager, MediaController mediaController) throws InfoMediaManager.PackageNotAvailableException {
        super(context, str, userHandle, localBluetoothManager, mediaController);
        MediaRouter2 mediaRouter2;
        this.mExecutor = Executors.newSingleThreadExecutor();
        int i = 0;
        this.mRouteCallback = new RouteCallback(this, i);
        this.mTransferCallback = new TransferCallback(this, i);
        this.mControllerCallback = new ControllerCallback(this, i);
        this.mRouteListingPreferenceCallback = new RouterInfoMediaManager$$ExternalSyntheticLambda3(this, 3);
        try {
            mediaRouter2 = MediaRouter2.getInstance(context, str, userHandle);
        } catch (IllegalArgumentException unused) {
            mediaRouter2 = null;
        }
        if (mediaRouter2 == null) {
            throw new InfoMediaManager.PackageNotAvailableException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Package name ", str, " does not exist."));
        }
        this.mRouter = mediaRouter2;
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controller = this.mRouter.getController(routingSessionInfo.getId());
        if (controller != null) {
            controller.deselectRoute(mediaRoute2Info);
        }
    }

    public final MediaRouter2.RoutingController getControllerForSession(RoutingSessionInfo routingSessionInfo) {
        return this.mRouter.getController(routingSessionInfo.getId());
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getDeselectableRoutes(RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controllerForSession = getControllerForSession(routingSessionInfo);
        return controllerForSession == null ? Collections.EMPTY_LIST : controllerForSession.getDeselectableRoutes();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RouteListingPreference getRouteListingPreference() {
        return this.mRouter.getRouteListingPreference();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getRoutingSessionsForPackage() {
        return (List) this.mRouter.getControllers().stream().map(new RouterInfoMediaManager$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectableRoutes(RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controllerForSession = getControllerForSession(routingSessionInfo);
        if (controllerForSession == null) {
            return Collections.EMPTY_LIST;
        }
        final List<String> selectedRoutes = controllerForSession.getRoutingSessionInfo().getSelectedRoutes();
        return (List) controllerForSession.getSelectableRoutes().stream().filter(new Predicate() { // from class: com.android.settingslib.media.RouterInfoMediaManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !selectedRoutes.contains(((MediaRoute2Info) obj).getId());
            }
        }).collect(Collectors.toList());
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectedRoutes(RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controllerForSession = getControllerForSession(routingSessionInfo);
        return controllerForSession == null ? Collections.EMPTY_LIST : controllerForSession.getSelectedRoutes();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getTransferableRoutes(RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controllerForSession = getControllerForSession(routingSessionInfo);
        HashMap hashMap = new HashMap();
        if (controllerForSession != null) {
            controllerForSession.getTransferableRoutes().forEach(new RouterInfoMediaManager$$ExternalSyntheticLambda3(hashMap, 0));
            if (controllerForSession.getRoutingSessionInfo().isSystemSession()) {
                final int i = 0;
                this.mRouter.getRoutes().stream().filter(new Predicate() { // from class: com.android.settingslib.media.RouterInfoMediaManager$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj;
                        switch (i) {
                            case 0:
                                return !mediaRoute2Info.isSystemRoute();
                            default:
                                return mediaRoute2Info.isSystemRoute();
                        }
                    }
                }).forEach(new RouterInfoMediaManager$$ExternalSyntheticLambda3(hashMap, 1));
            } else {
                final int i2 = 1;
                this.mRouter.getRoutes().stream().filter(new Predicate() { // from class: com.android.settingslib.media.RouterInfoMediaManager$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj;
                        switch (i2) {
                            case 0:
                                return !mediaRoute2Info.isSystemRoute();
                            default:
                                return mediaRoute2Info.isSystemRoute();
                        }
                    }
                }).forEach(new RouterInfoMediaManager$$ExternalSyntheticLambda3(hashMap, 2));
            }
        }
        return new ArrayList(hashMap.values());
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void registerRouter() {
        this.mRouter.registerRouteCallback(this.mExecutor, this.mRouteCallback, RouteDiscoveryPreference.EMPTY);
        this.mRouter.registerRouteListingPreferenceUpdatedCallback(this.mExecutor, this.mRouteListingPreferenceCallback);
        this.mRouter.registerTransferCallback(this.mExecutor, this.mTransferCallback);
        this.mRouter.registerControllerCallback(this.mExecutor, this.mControllerCallback);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void releaseSession(RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controllerForSession = getControllerForSession(routingSessionInfo);
        if (controllerForSession != null) {
            controllerForSession.release();
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
        MediaRouter2.RoutingController controller = this.mRouter.getController(routingSessionInfo.getId());
        if (controller != null) {
            controller.selectRoute(mediaRoute2Info);
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
        this.mRouter.setRouteVolume(mediaRoute2Info, i);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {
        this.mRouterManager.setSessionVolume(routingSessionInfo, i);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void startScanOnRouter() {
        synchronized (this) {
            try {
                if (this.mScanToken == null) {
                    this.mScanToken = this.mRouter.requestScan(new MediaRouter2.ScanRequest.Builder().build());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void stopScanOnRouter() {
        synchronized (this) {
            try {
                MediaRouter2.ScanToken scanToken = this.mScanToken;
                if (scanToken != null) {
                    this.mRouter.cancelScanRequest(scanToken);
                    this.mScanToken = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void transferToRoute(MediaRoute2Info mediaRoute2Info) {
        this.mRouter.transferTo(mediaRoute2Info);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void unregisterRouter() {
        this.mRouter.unregisterControllerCallback(this.mControllerCallback);
        this.mRouter.unregisterTransferCallback(this.mTransferCallback);
        this.mRouter.unregisterRouteListingPreferenceUpdatedCallback(this.mRouteListingPreferenceCallback);
        this.mRouter.unregisterRouteCallback(this.mRouteCallback);
    }
}
