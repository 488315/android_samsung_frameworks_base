package com.android.server.people;

import com.android.server.people.prediction.AppTargetPredictor;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PeopleService$LocalService$$ExternalSyntheticLambda2
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = PeopleService.LocalService.$r8$clinit;
        final AppTargetPredictor appTargetPredictor = ((SessionInfo) obj).mAppTargetPredictor;
        appTargetPredictor.mCallbackExecutor.execute(
                new Runnable() { // from class:
                                 // com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppTargetPredictor.this.predictTargets();
                    }
                });
    }
}
