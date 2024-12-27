package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppPredictionSessionId f$0;
    public final /* synthetic */ IPredictionCallback f$1;

    public /* synthetic */
    AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda2(
            AppPredictionSessionId appPredictionSessionId,
            IPredictionCallback iPredictionCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = appPredictionSessionId;
        this.f$1 = iPredictionCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AppPredictionSessionId appPredictionSessionId = this.f$0;
                IPredictionCallback iPredictionCallback = this.f$1;
                AppPredictionPerUserService appPredictionPerUserService =
                        (AppPredictionPerUserService) obj;
                AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo =
                        (AppPredictionPerUserService.AppPredictionSessionInfo)
                                appPredictionPerUserService.mSessionInfos.get(
                                        appPredictionSessionId);
                if (appPredictionSessionInfo != null) {
                    if (appPredictionPerUserService.resolveService(
                            false,
                            appPredictionSessionInfo.mUsesPeopleService,
                            new AppPredictionPerUserService$$ExternalSyntheticLambda4(
                                    appPredictionSessionId, iPredictionCallback, 1))) {
                        appPredictionSessionInfo.mCallbacks.unregister(iPredictionCallback);
                        break;
                    }
                }
                break;
            default:
                ((AppPredictionPerUserService) obj)
                        .registerPredictionUpdatesLocked(this.f$0, this.f$1);
                break;
        }
    }
}
