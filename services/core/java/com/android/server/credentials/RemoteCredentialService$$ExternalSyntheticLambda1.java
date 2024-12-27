package com.android.server.credentials;

import android.os.Handler;
import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.BeginGetCredentialResponse;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RemoteCredentialService$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteCredentialService f$0;
    public final /* synthetic */ AtomicReference f$1;

    public /* synthetic */ RemoteCredentialService$$ExternalSyntheticLambda1(
            RemoteCredentialService remoteCredentialService,
            AtomicReference atomicReference,
            int i) {
        this.$r8$classId = i;
        this.f$0 = remoteCredentialService;
        this.f$1 = atomicReference;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                RemoteCredentialService remoteCredentialService = this.f$0;
                AtomicReference atomicReference = this.f$1;
                int i = RemoteCredentialService.$r8$clinit;
                remoteCredentialService.getClass();
                Handler.getMain()
                        .post(
                                new RemoteCredentialService$$ExternalSyntheticLambda7(
                                        remoteCredentialService,
                                        (BeginCreateCredentialResponse) obj,
                                        (Throwable) obj2,
                                        atomicReference));
                break;
            case 1:
                RemoteCredentialService remoteCredentialService2 = this.f$0;
                AtomicReference atomicReference2 = this.f$1;
                int i2 = RemoteCredentialService.$r8$clinit;
                remoteCredentialService2.getClass();
                Handler.getMain()
                        .post(
                                new RemoteCredentialService$$ExternalSyntheticLambda7(
                                        remoteCredentialService2,
                                        (BeginGetCredentialResponse) obj,
                                        (Throwable) obj2,
                                        atomicReference2,
                                        0));
                break;
            default:
                RemoteCredentialService remoteCredentialService3 = this.f$0;
                AtomicReference atomicReference3 = this.f$1;
                int i3 = RemoteCredentialService.$r8$clinit;
                remoteCredentialService3.getClass();
                Handler.getMain()
                        .post(
                                new RemoteCredentialService$$ExternalSyntheticLambda7(
                                        remoteCredentialService3,
                                        (Void) obj,
                                        (Throwable) obj2,
                                        atomicReference3,
                                        2));
                break;
        }
    }
}
