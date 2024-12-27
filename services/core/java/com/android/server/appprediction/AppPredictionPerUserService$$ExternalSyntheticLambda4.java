package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.os.IInterface;
import android.service.appprediction.IPredictionService;

import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppPredictionPerUserService$$ExternalSyntheticLambda4
        implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppPredictionSessionId f$0;
    public final /* synthetic */ IPredictionCallback f$1;

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda4(
            AppPredictionSessionId appPredictionSessionId,
            IPredictionCallback iPredictionCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = appPredictionSessionId;
        this.f$1 = iPredictionCallback;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((IPredictionService) iInterface).registerPredictionUpdates(this.f$0, this.f$1);
                break;
            default:
                ((IPredictionService) iInterface).unregisterPredictionUpdates(this.f$0, this.f$1);
                break;
        }
    }
}
