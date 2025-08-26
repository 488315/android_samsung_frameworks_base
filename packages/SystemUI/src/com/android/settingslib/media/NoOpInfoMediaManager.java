package com.android.settingslib.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class NoOpInfoMediaManager extends InfoMediaManager {
    public static final RoutingSessionInfo PLACEHOLDER_SESSION = new RoutingSessionInfo.Builder("FAKE_ROUTING_SESSION", "").addSelectedRoute("FAKE_SELECTED_ROUTE_ID").setVolumeMax(-1).setVolume(-1).build();

    public NoOpInfoMediaManager(Context context, String str, UserHandle userHandle, LocalBluetoothManager localBluetoothManager, MediaController mediaController) {
        super(context, str, userHandle, localBluetoothManager, mediaController);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getDeselectableRoutes(RoutingSessionInfo routingSessionInfo) {
        return Collections.EMPTY_LIST;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RouteListingPreference getRouteListingPreference() {
        return null;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getRoutingSessionsForPackage() {
        return List.of(PLACEHOLDER_SESSION);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectableRoutes(RoutingSessionInfo routingSessionInfo) {
        return Collections.EMPTY_LIST;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getSelectedRoutes(RoutingSessionInfo routingSessionInfo) {
        return Collections.EMPTY_LIST;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getTransferableRoutes(RoutingSessionInfo routingSessionInfo) {
        return Collections.EMPTY_LIST;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void registerRouter() {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void startScanOnRouter() {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void stopScanOnRouter() {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void unregisterRouter() {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void releaseSession(RoutingSessionInfo routingSessionInfo) {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void transferToRoute(MediaRoute2Info mediaRoute2Info) {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {
    }
}
