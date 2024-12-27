package com.android.server.location.provider;

import android.location.provider.ProviderRequest;

public final /* synthetic */ class AbstractLocationProvider$Controller$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AbstractLocationProvider.Controller f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AbstractLocationProvider$Controller$$ExternalSyntheticLambda1(
            AbstractLocationProvider.Controller controller, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = controller;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AbstractLocationProvider.Controller controller = this.f$0;
                controller.this$0.onSetRequest((ProviderRequest) this.f$1);
                break;
            default:
                this.f$0.this$0.onFlush(
                        (LocationProviderManager$Registration$$ExternalSyntheticLambda0)
                                ((Runnable) this.f$1));
                break;
        }
    }
}
