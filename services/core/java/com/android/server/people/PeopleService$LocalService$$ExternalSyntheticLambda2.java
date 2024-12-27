package com.android.server.people;

import com.android.server.people.prediction.AppTargetPredictor;

import java.util.function.Consumer;

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
