package com.android.server.ambientcontext;

import android.app.ambientcontext.IAmbientContextObserver;
import android.os.RemoteCallback;

import java.util.function.Consumer;

public final /* synthetic */ class AmbientContextManagerPerUserService$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientContextManagerPerUserService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AmbientContextManagerPerUserService$$ExternalSyntheticLambda1(
            AmbientContextManagerPerUserService ambientContextManagerPerUserService,
            Object obj,
            int i) {
        this.$r8$classId = i;
        this.f$0 = ambientContextManagerPerUserService;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AmbientContextManagerPerUserService ambientContextManagerPerUserService = this.f$0;
                RemoteCallback remoteCallback = (RemoteCallback) this.f$1;
                ambientContextManagerPerUserService.getClass();
                AmbientContextManagerPerUserService.sendStatusCallback(
                        ((Integer) obj).intValue(), remoteCallback);
                break;
            default:
                AmbientContextManagerPerUserService ambientContextManagerPerUserService2 = this.f$0;
                IAmbientContextObserver iAmbientContextObserver =
                        (IAmbientContextObserver) this.f$1;
                ambientContextManagerPerUserService2.getClass();
                AmbientContextManagerPerUserService.completeRegistration(
                        iAmbientContextObserver, ((Integer) obj).intValue());
                break;
        }
    }
}
