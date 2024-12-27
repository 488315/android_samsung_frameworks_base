package com.android.server.people;

import android.app.prediction.AppTargetEvent;
import android.content.pm.ParceledListSlice;
import com.android.server.people.PeopleService;
import com.android.server.people.prediction.AppTargetPredictor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleService$LocalService$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                final AppTargetEvent appTargetEvent = (AppTargetEvent) obj2;
                int i2 = PeopleService.LocalService.$r8$clinit;
                final AppTargetPredictor appTargetPredictor = ((SessionInfo) obj).mAppTargetPredictor;
                appTargetPredictor.mCallbackExecutor.execute(new Runnable() { // from class: com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppTargetPredictor.this.reportAppTargetEvent(appTargetEvent);
                    }
                });
                break;
            default:
                int i3 = PeopleService.LocalService.$r8$clinit;
                AppTargetPredictor appTargetPredictor2 = ((SessionInfo) obj).mAppTargetPredictor;
                ((ParceledListSlice) obj2).getList();
                appTargetPredictor2.getClass();
                break;
        }
    }
}
