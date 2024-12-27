package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import com.android.server.appprediction.AppPredictionPerUserService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppPredictionSessionId f$0;

    public /* synthetic */ AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda1(AppPredictionSessionId appPredictionSessionId, int i) {
        this.$r8$classId = i;
        this.f$0 = appPredictionSessionId;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AppPredictionSessionId appPredictionSessionId = this.f$0;
        AppPredictionPerUserService appPredictionPerUserService = (AppPredictionPerUserService) obj;
        switch (i) {
            case 0:
                appPredictionPerUserService.onDestroyPredictionSessionLocked(appPredictionSessionId);
                break;
            default:
                AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = (AppPredictionPerUserService.AppPredictionSessionInfo) appPredictionPerUserService.mSessionInfos.get(appPredictionSessionId);
                if (appPredictionSessionInfo != null) {
                    appPredictionPerUserService.resolveService(true, appPredictionSessionInfo.mUsesPeopleService, new AppPredictionPerUserService$$ExternalSyntheticLambda3(appPredictionSessionId, 1));
                    break;
                }
                break;
        }
    }
}
