package com.android.server.autofill;

import android.os.Handler;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteFillService$$ExternalSyntheticLambda4
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteFillService f$0;
    public final /* synthetic */ FillRequest f$1;
    public final /* synthetic */ AtomicReference f$2;

    public /* synthetic */ RemoteFillService$$ExternalSyntheticLambda4(
            RemoteFillService remoteFillService,
            FillRequest fillRequest,
            AtomicReference atomicReference,
            int i) {
        this.$r8$classId = i;
        this.f$0 = remoteFillService;
        this.f$1 = fillRequest;
        this.f$2 = atomicReference;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                final RemoteFillService remoteFillService = this.f$0;
                final FillRequest fillRequest = this.f$1;
                final AtomicReference atomicReference = this.f$2;
                final FillResponse fillResponse = (FillResponse) obj;
                final Throwable th = (Throwable) obj2;
                int i = RemoteFillService.$r8$clinit;
                remoteFillService.getClass();
                final int i2 = 1;
                Handler.getMain()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda7
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i2) {
                                            case 0:
                                                RemoteFillService
                                                        .$r8$lambda$BpMSr5I3_9bplEhls6FgDTRLWpg(
                                                                remoteFillService,
                                                                th,
                                                                fillRequest,
                                                                fillResponse,
                                                                atomicReference);
                                                break;
                                            default:
                                                RemoteFillService
                                                        .$r8$lambda$PXtmzf6bY2FCvCgDzGBClXL04mI(
                                                                remoteFillService,
                                                                th,
                                                                fillRequest,
                                                                fillResponse,
                                                                atomicReference);
                                                break;
                                        }
                                    }
                                });
                break;
            default:
                final RemoteFillService remoteFillService2 = this.f$0;
                final FillRequest fillRequest2 = this.f$1;
                final AtomicReference atomicReference2 = this.f$2;
                final FillResponse fillResponse2 = (FillResponse) obj;
                final Throwable th2 = (Throwable) obj2;
                int i3 = RemoteFillService.$r8$clinit;
                remoteFillService2.getClass();
                final int i4 = 0;
                Handler.getMain()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda7
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i4) {
                                            case 0:
                                                RemoteFillService
                                                        .$r8$lambda$BpMSr5I3_9bplEhls6FgDTRLWpg(
                                                                remoteFillService2,
                                                                th2,
                                                                fillRequest2,
                                                                fillResponse2,
                                                                atomicReference2);
                                                break;
                                            default:
                                                RemoteFillService
                                                        .$r8$lambda$PXtmzf6bY2FCvCgDzGBClXL04mI(
                                                                remoteFillService2,
                                                                th2,
                                                                fillRequest2,
                                                                fillResponse2,
                                                                atomicReference2);
                                                break;
                                        }
                                    }
                                });
                break;
        }
    }
}
