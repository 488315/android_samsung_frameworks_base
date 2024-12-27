package com.android.server.people.prediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppTargetEvent;

import com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda0;
import com.android.server.people.SessionInfo$$ExternalSyntheticLambda0;
import com.android.server.people.data.DataManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AppTargetPredictor {
    public final ExecutorService mCallbackExecutor = Executors.newSingleThreadExecutor();
    public final int mCallingUserId;
    public final DataManager mDataManager;
    public final AppPredictionContext mPredictionContext;
    public final Consumer mUpdatePredictionsMethod;

    public AppTargetPredictor(
            AppPredictionContext appPredictionContext,
            SessionInfo$$ExternalSyntheticLambda0 sessionInfo$$ExternalSyntheticLambda0,
            DataManager dataManager,
            int i) {
        this.mPredictionContext = appPredictionContext;
        this.mUpdatePredictionsMethod = sessionInfo$$ExternalSyntheticLambda0;
        this.mDataManager = dataManager;
        this.mCallingUserId = i;
    }

    public Consumer getUpdatePredictionsMethod() {
        return this.mUpdatePredictionsMethod;
    }

    public void predictTargets() {}

    public void reportAppTargetEvent(AppTargetEvent appTargetEvent) {}

    public void sortTargets(
            List list,
            PeopleService$LocalService$$ExternalSyntheticLambda0
                    peopleService$LocalService$$ExternalSyntheticLambda0) {
        peopleService$LocalService$$ExternalSyntheticLambda0.accept(list);
    }
}
