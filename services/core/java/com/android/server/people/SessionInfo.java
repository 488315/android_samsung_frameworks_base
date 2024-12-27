package com.android.server.people;

import android.app.prediction.AppPredictionContext;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.people.data.DataManager;
import com.android.server.people.prediction.AppTargetPredictor;
import com.android.server.people.prediction.ShareTargetPredictor;
import java.util.List;
import java.util.function.Consumer;

public final class SessionInfo {
    public final AppTargetPredictor mAppTargetPredictor;
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();

    public SessionInfo(AppPredictionContext appPredictionContext, DataManager dataManager, int i, Context context) {
        ?? r3 = new Consumer() { // from class: com.android.server.people.SessionInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SessionInfo sessionInfo = SessionInfo.this;
                List list = (List) obj;
                int beginBroadcast = sessionInfo.mCallbacks.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        sessionInfo.mCallbacks.getBroadcastItem(i2).onResult(new ParceledListSlice(list));
                    } catch (RemoteException e) {
                        Slog.e("SessionInfo", "Failed to calling callback" + e);
                    }
                }
                sessionInfo.mCallbacks.finishBroadcast();
            }
        };
        this.mAppTargetPredictor = "share".equals(appPredictionContext.getUiSurface()) ? new ShareTargetPredictor(appPredictionContext, r3, dataManager, i, context) : new AppTargetPredictor(appPredictionContext, r3, dataManager, i);
    }
}
