package com.android.server.people;

import android.app.prediction.IPredictionCallback;
import com.android.server.people.PeopleService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleService$LocalService$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IPredictionCallback f$0;

    public /* synthetic */ PeopleService$LocalService$$ExternalSyntheticLambda1(IPredictionCallback iPredictionCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = iPredictionCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        IPredictionCallback iPredictionCallback = this.f$0;
        SessionInfo sessionInfo = (SessionInfo) obj;
        switch (i) {
            case 0:
                int i2 = PeopleService.LocalService.$r8$clinit;
                sessionInfo.mCallbacks.register(iPredictionCallback);
                break;
            default:
                int i3 = PeopleService.LocalService.$r8$clinit;
                sessionInfo.mCallbacks.unregister(iPredictionCallback);
                break;
        }
    }
}
