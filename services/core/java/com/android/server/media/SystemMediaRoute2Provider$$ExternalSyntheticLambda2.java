package com.android.server.media;


public final /* synthetic */ class SystemMediaRoute2Provider$$ExternalSyntheticLambda2
        implements BluetoothRouteController.BluetoothRoutesUpdatedListener {
    public final /* synthetic */ SystemMediaRoute2Provider f$0;

    @Override // com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener
    public void onBluetoothRoutesUpdated() {
        SystemMediaRoute2Provider systemMediaRoute2Provider = this.f$0;
        systemMediaRoute2Provider.updateProviderState();
        systemMediaRoute2Provider.notifyProviderState();
        if (systemMediaRoute2Provider.updateSessionInfosIfNeeded()) {
            systemMediaRoute2Provider.notifySessionInfoUpdated();
        }
    }
}
