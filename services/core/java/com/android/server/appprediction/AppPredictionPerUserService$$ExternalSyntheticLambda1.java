package com.android.server.appprediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.service.appprediction.IPredictionService;

import com.android.internal.infra.AbstractRemoteService;

public final /* synthetic */ class AppPredictionPerUserService$$ExternalSyntheticLambda1
        implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ AppPredictionSessionId f$1;

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda1(
            AppPredictionContext appPredictionContext,
            AppPredictionSessionId appPredictionSessionId) {
        this.$r8$classId = 0;
        this.f$0 = appPredictionContext;
        this.f$1 = appPredictionSessionId;
    }

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda1(
            AppPredictionSessionId appPredictionSessionId, Object obj, int i) {
        this.$r8$classId = i;
        this.f$1 = appPredictionSessionId;
        this.f$0 = obj;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((IPredictionService) iInterface)
                        .onCreatePredictionSession((AppPredictionContext) this.f$0, this.f$1);
                break;
            case 1:
                ((IPredictionService) iInterface)
                        .requestServiceFeatures(this.f$1, (IRemoteCallback) this.f$0);
                break;
            default:
                ((IPredictionService) iInterface)
                        .notifyAppTargetEvent(this.f$1, (AppTargetEvent) this.f$0);
                break;
        }
    }
}
