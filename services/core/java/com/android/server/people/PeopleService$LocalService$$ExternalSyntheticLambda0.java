package com.android.server.people;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleService$LocalService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PeopleService.LocalService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PeopleService$LocalService$$ExternalSyntheticLambda0(
            PeopleService.LocalService localService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = localService;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PeopleService.LocalService localService = this.f$0;
                AppPredictionSessionId appPredictionSessionId = (AppPredictionSessionId) this.f$1;
                int i = PeopleService.LocalService.$r8$clinit;
                localService.getClass();
                ((SessionInfo) obj).mCallbacks.kill();
                ((ArrayMap) localService.mSessions).remove(appPredictionSessionId);
                break;
            default:
                PeopleService.LocalService localService2 = this.f$0;
                IPredictionCallback iPredictionCallback = (IPredictionCallback) this.f$1;
                List list = (List) obj;
                int i2 = PeopleService.LocalService.$r8$clinit;
                localService2.getClass();
                try {
                    iPredictionCallback.onResult(new ParceledListSlice(list));
                    break;
                } catch (RemoteException e) {
                    Slog.e("PeopleService", "Failed to calling callback" + e);
                }
        }
    }
}
