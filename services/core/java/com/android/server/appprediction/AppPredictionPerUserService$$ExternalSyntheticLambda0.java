package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.IInterface;
import android.service.appprediction.IPredictionService;

import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppPredictionPerUserService$$ExternalSyntheticLambda0
        implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ AppPredictionSessionId f$0;
    public final /* synthetic */ ParceledListSlice f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda0(
            AppPredictionSessionId appPredictionSessionId,
            ParceledListSlice parceledListSlice,
            IPredictionCallback iPredictionCallback) {
        this.f$0 = appPredictionSessionId;
        this.f$1 = parceledListSlice;
        this.f$2 = iPredictionCallback;
    }

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda0(
            AppPredictionSessionId appPredictionSessionId,
            String str,
            ParceledListSlice parceledListSlice) {
        this.f$0 = appPredictionSessionId;
        this.f$2 = str;
        this.f$1 = parceledListSlice;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((IPredictionService) iInterface)
                        .sortAppTargets(this.f$0, this.f$1, (IPredictionCallback) this.f$2);
                break;
            default:
                ((IPredictionService) iInterface)
                        .notifyLaunchLocationShown(this.f$0, (String) this.f$2, this.f$1);
                break;
        }
    }
}
