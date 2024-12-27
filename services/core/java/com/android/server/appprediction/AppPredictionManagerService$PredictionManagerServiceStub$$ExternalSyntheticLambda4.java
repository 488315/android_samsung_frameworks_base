package com.android.server.appprediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.IBinder;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ AppPredictionSessionId f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */
    AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4(
            AppPredictionContext appPredictionContext,
            AppPredictionSessionId appPredictionSessionId,
            IBinder iBinder) {
        this.$r8$classId = 0;
        this.f$0 = appPredictionContext;
        this.f$1 = appPredictionSessionId;
        this.f$2 = iBinder;
    }

    public /* synthetic */
    AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4(
            AppPredictionSessionId appPredictionSessionId, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$1 = appPredictionSessionId;
        this.f$0 = obj;
        this.f$2 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AppPredictionPerUserService) obj)
                        .onCreatePredictionSessionLocked(
                                (AppPredictionContext) this.f$0, this.f$1, (IBinder) this.f$2);
                break;
            case 1:
                AppPredictionSessionId appPredictionSessionId = this.f$1;
                ParceledListSlice parceledListSlice = (ParceledListSlice) this.f$0;
                IPredictionCallback iPredictionCallback = (IPredictionCallback) this.f$2;
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
                            new AppPredictionPerUserService$$ExternalSyntheticLambda0(
                                    appPredictionSessionId,
                                    parceledListSlice,
                                    iPredictionCallback));
                    break;
                }
                break;
            default:
                AppPredictionSessionId appPredictionSessionId2 = this.f$1;
                String str = (String) this.f$0;
                ParceledListSlice parceledListSlice2 = (ParceledListSlice) this.f$2;
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
                            new AppPredictionPerUserService$$ExternalSyntheticLambda0(
                                    appPredictionSessionId2, str, parceledListSlice2));
                    break;
                }
                break;
        }
    }
}
