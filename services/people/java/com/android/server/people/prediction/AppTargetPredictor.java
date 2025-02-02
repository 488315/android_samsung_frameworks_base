package com.android.server.people.prediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppTargetEvent;
import android.content.Context;
import com.android.server.people.data.DataManager;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class AppTargetPredictor {
  public final ExecutorService mCallbackExecutor = Executors.newSingleThreadExecutor();
  public final int mCallingUserId;
  public final DataManager mDataManager;
  public final AppPredictionContext mPredictionContext;
  public final Consumer mUpdatePredictionsMethod;

  public void onLaunchLocationShown(String str, List list) {}

  public void predictTargets() {}

  /* renamed from: reportAppTargetEvent, reason: merged with bridge method [inline-methods] */
  public void lambda$onAppTargetEvent$0(AppTargetEvent appTargetEvent) {}

  public static AppTargetPredictor create(
      AppPredictionContext appPredictionContext,
      Consumer consumer,
      DataManager dataManager,
      int i,
      Context context) {
    if ("share".equals(appPredictionContext.getUiSurface())) {
      return new ShareTargetPredictor(appPredictionContext, consumer, dataManager, i, context);
    }
    return new AppTargetPredictor(appPredictionContext, consumer, dataManager, i);
  }

  public AppTargetPredictor(
      AppPredictionContext appPredictionContext,
      Consumer consumer,
      DataManager dataManager,
      int i) {
    this.mPredictionContext = appPredictionContext;
    this.mUpdatePredictionsMethod = consumer;
    this.mDataManager = dataManager;
    this.mCallingUserId = i;
  }

  public void onAppTargetEvent(final AppTargetEvent appTargetEvent) {
    this.mCallbackExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AppTargetPredictor.this.lambda$onAppTargetEvent$0(appTargetEvent);
          }
        });
  }

  public void onSortAppTargets(final List list, final Consumer consumer) {
    this.mCallbackExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            AppTargetPredictor.this.lambda$onSortAppTargets$1(list, consumer);
          }
        });
  }

  public void onRequestPredictionUpdate() {
    this.mCallbackExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            AppTargetPredictor.this.predictTargets();
          }
        });
  }

  public Consumer getUpdatePredictionsMethod() {
    return this.mUpdatePredictionsMethod;
  }

  /* renamed from: sortTargets, reason: merged with bridge method [inline-methods] */
  public void lambda$onSortAppTargets$1(List list, Consumer consumer) {
    consumer.accept(list);
  }

  public AppPredictionContext getPredictionContext() {
    return this.mPredictionContext;
  }

  public DataManager getDataManager() {
    return this.mDataManager;
  }

  public void updatePredictions(List list) {
    this.mUpdatePredictionsMethod.accept(list);
  }
}
