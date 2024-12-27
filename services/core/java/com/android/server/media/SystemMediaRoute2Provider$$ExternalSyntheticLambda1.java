package com.android.server.media;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemMediaRoute2Provider$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SystemMediaRoute2Provider f$0;

    public /* synthetic */ SystemMediaRoute2Provider$$ExternalSyntheticLambda1(
            SystemMediaRoute2Provider systemMediaRoute2Provider, int i) {
        this.$r8$classId = i;
        this.f$0 = systemMediaRoute2Provider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SystemMediaRoute2Provider systemMediaRoute2Provider = this.f$0;
        switch (i) {
            case 0:
                systemMediaRoute2Provider.mBluetoothRouteController.stop();
                systemMediaRoute2Provider.mDeviceRouteController.stop();
                systemMediaRoute2Provider.notifyProviderState();
                break;
            case 1:
                systemMediaRoute2Provider.mDeviceRouteController.start(
                        systemMediaRoute2Provider.mUser);
                systemMediaRoute2Provider.mBluetoothRouteController.start(
                        systemMediaRoute2Provider.mUser);
                break;
            case 2:
                systemMediaRoute2Provider.updateProviderState();
                systemMediaRoute2Provider.notifyProviderState();
                if (systemMediaRoute2Provider.updateSessionInfosIfNeeded()) {
                    systemMediaRoute2Provider.notifySessionInfoUpdated();
                    break;
                }
                break;
            default:
                systemMediaRoute2Provider.updateVolume();
                break;
        }
    }
}
