package com.android.server.media;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
