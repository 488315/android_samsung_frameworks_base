package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.os.IRemoteCallback;

import java.util.function.Consumer;

public final /* synthetic */
class AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppPredictionSessionId f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */
    AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda0(
            AppPredictionSessionId appPredictionSessionId, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = appPredictionSessionId;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AppPredictionSessionId appPredictionSessionId = this.f$0;
                IRemoteCallback iRemoteCallback = (IRemoteCallback) this.f$1;
                AppPredictionPerUserService appPredictionPerUserService =
                        (AppPredictionPerUserService) obj;
                AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo =
                        (AppPredictionPerUserService.AppPredictionSessionInfo)
                                appPredictionPerUserService.mSessionInfos.get(
                                        appPredictionSessionId);
                if (appPredictionSessionInfo != null) {
                    appPredictionPerUserService.resolveService(
                            true,
                            appPredictionSessionInfo.mUsesPeopleService,
                            new AppPredictionPerUserService$$ExternalSyntheticLambda1(
                                    appPredictionSessionId, iRemoteCallback, 1));
                    break;
                }
                break;
            default:
                AppPredictionSessionId appPredictionSessionId2 = this.f$0;
                AppTargetEvent appTargetEvent = (AppTargetEvent) this.f$1;
                AppPredictionPerUserService appPredictionPerUserService2 =
                        (AppPredictionPerUserService) obj;
                AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo2 =
                        (AppPredictionPerUserService.AppPredictionSessionInfo)
                                appPredictionPerUserService2.mSessionInfos.get(
                                        appPredictionSessionId2);
                if (appPredictionSessionInfo2 != null) {
                    appPredictionPerUserService2.resolveService(
                            false,
                            appPredictionSessionInfo2.mUsesPeopleService,
                            new AppPredictionPerUserService$$ExternalSyntheticLambda1(
                                    appPredictionSessionId2, appTargetEvent, 2));
                    break;
                }
                break;
        }
    }
}
