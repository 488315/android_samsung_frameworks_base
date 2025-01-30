package com.android.server.autofill;

import android.os.SystemClock;
import android.service.autofill.Dataset;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class FillResponseEventLogger {
  public final int mSessionId;
  public long startResponseProcessingTimestamp = -1;
  public Optional mEventInternal = Optional.empty();

  public final class FillResponseEventInternal {
    public int mRequestId = -1;
    public int mAppPackageUid = -1;
    public int mDisplayPresentationType = 0;
    public int mAvailableCount = 0;
    public int mSaveUiTriggerIds = -1;
    public int mLatencyFillResponseReceivedMillis = -1;
    public int mAuthenticationType = 0;
    public int mAuthenticationResult = 0;
    public int mAuthenticationFailureReason = -1;
    public int mLatencyAuthenticationUiDisplayMillis = -1;
    public int mLatencyDatasetDisplayMillis = -1;
    public int mResponseStatus = 0;
    public long mLatencyResponseProcessingMillis = -1;
    public int mAvailablePccCount = -1;
    public int mAvailablePccOnlyCount = -1;
    public int mTotalDatasetsProvided = -1;
    public int mDetectionPref = 0;
  }

  public FillResponseEventLogger(int i) {
    this.mSessionId = i;
  }

  public static FillResponseEventLogger forSessionId(int i) {
    return new FillResponseEventLogger(i);
  }

  public void startLogForNewResponse() {
    if (!this.mEventInternal.isEmpty()) {
      Slog.w(
          "FillResponseEventLogger",
          "FillResponseEventLogger is not empty before starting for a new request");
    }
    this.mEventInternal = Optional.of(new FillResponseEventInternal());
  }

  public void maybeSetRequestId(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda7
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mRequestId = i;
          }
        });
  }

  public void maybeSetAppPackageUid(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda9
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mAppPackageUid = i;
          }
        });
  }

  public void maybeSetAvailableCount(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mAvailableCount = i;
          }
        });
  }

  public void maybeSetTotalDatasetsProvided(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda5
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            FillResponseEventLogger.lambda$maybeSetTotalDatasetsProvided$5(
                i, (FillResponseEventLogger.FillResponseEventInternal) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$maybeSetTotalDatasetsProvided$5(
      int i, FillResponseEventInternal fillResponseEventInternal) {
    if (fillResponseEventInternal.mTotalDatasetsProvided == -1) {
      fillResponseEventInternal.mTotalDatasetsProvided = i;
    }
  }

  public void maybeSetSaveUiTriggerIds(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mSaveUiTriggerIds = i;
          }
        });
  }

  public void maybeSetLatencyFillResponseReceivedMillis(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda8
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj)
                    .mLatencyFillResponseReceivedMillis =
                i;
          }
        });
  }

  public void maybeSetResponseStatus(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mResponseStatus = i;
          }
        });
  }

  public void startResponseProcessingTime() {
    this.startResponseProcessingTimestamp = SystemClock.elapsedRealtime();
  }

  public void maybeSetLatencyResponseProcessingMillis() {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            FillResponseEventLogger.this.lambda$maybeSetLatencyResponseProcessingMillis$14(
                (FillResponseEventLogger.FillResponseEventInternal) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$maybeSetLatencyResponseProcessingMillis$14(
      FillResponseEventInternal fillResponseEventInternal) {
    if (this.startResponseProcessingTimestamp == -1 && Helper.sVerbose) {
      Slog.v("FillResponseEventLogger", "uninitialized startResponseProcessingTimestamp");
    }
    fillResponseEventInternal.mLatencyResponseProcessingMillis =
        SystemClock.elapsedRealtime() - this.startResponseProcessingTimestamp;
  }

  public void maybeSetDatasetsCountAfterPotentialPccFiltering(final List list) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            FillResponseEventLogger.lambda$maybeSetDatasetsCountAfterPotentialPccFiltering$17(
                list, (FillResponseEventLogger.FillResponseEventInternal) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$maybeSetDatasetsCountAfterPotentialPccFiltering$17(
      List list, FillResponseEventInternal fillResponseEventInternal) {
    int i;
    int i2;
    int i3 = 0;
    if (list != null) {
      i = list.size();
      int i4 = 0;
      i2 = 0;
      while (i3 < list.size()) {
        Dataset dataset = (Dataset) list.get(i3);
        if (dataset != null) {
          if (dataset.getEligibleReason() == 4) {
            i4++;
          } else if (dataset.getEligibleReason() != 5) {
          }
          i2++;
        }
        i3++;
      }
      i3 = i4;
    } else {
      i = 0;
      i2 = 0;
    }
    fillResponseEventInternal.mAvailablePccOnlyCount = i3;
    fillResponseEventInternal.mAvailablePccCount = i2;
    fillResponseEventInternal.mAvailableCount = i;
  }

  public void maybeSetDetectionPreference(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FillResponseEventLogger.FillResponseEventInternal) obj).mDetectionPref = i;
          }
        });
  }

  public void logAndEndEvent() {
    if (!this.mEventInternal.isPresent()) {
      Slog.w(
          "FillResponseEventLogger",
          "Shouldn't be logging AutofillFillRequestReported again for same event");
      return;
    }
    FillResponseEventInternal fillResponseEventInternal =
        (FillResponseEventInternal) this.mEventInternal.get();
    if (Helper.sVerbose) {
      Slog.v(
          "FillResponseEventLogger",
          "Log AutofillFillResponseReported: requestId="
              + fillResponseEventInternal.mRequestId
              + " sessionId="
              + this.mSessionId
              + " mAppPackageUid="
              + fillResponseEventInternal.mAppPackageUid
              + " mDisplayPresentationType="
              + fillResponseEventInternal.mDisplayPresentationType
              + " mAvailableCount="
              + fillResponseEventInternal.mAvailableCount
              + " mSaveUiTriggerIds="
              + fillResponseEventInternal.mSaveUiTriggerIds
              + " mLatencyFillResponseReceivedMillis="
              + fillResponseEventInternal.mLatencyFillResponseReceivedMillis
              + " mAuthenticationType="
              + fillResponseEventInternal.mAuthenticationType
              + " mAuthenticationResult="
              + fillResponseEventInternal.mAuthenticationResult
              + " mAuthenticationFailureReason="
              + fillResponseEventInternal.mAuthenticationFailureReason
              + " mLatencyAuthenticationUiDisplayMillis="
              + fillResponseEventInternal.mLatencyAuthenticationUiDisplayMillis
              + " mLatencyDatasetDisplayMillis="
              + fillResponseEventInternal.mLatencyDatasetDisplayMillis
              + " mResponseStatus="
              + fillResponseEventInternal.mResponseStatus
              + " mLatencyResponseProcessingMillis="
              + fillResponseEventInternal.mLatencyResponseProcessingMillis
              + " mAvailablePccCount="
              + fillResponseEventInternal.mAvailablePccCount
              + " mAvailablePccOnlyCount="
              + fillResponseEventInternal.mAvailablePccOnlyCount
              + " mTotalDatasetsProvided="
              + fillResponseEventInternal.mTotalDatasetsProvided
              + " mDetectionPref="
              + fillResponseEventInternal.mDetectionPref);
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED,
        fillResponseEventInternal.mRequestId,
        this.mSessionId,
        fillResponseEventInternal.mAppPackageUid,
        fillResponseEventInternal.mDisplayPresentationType,
        fillResponseEventInternal.mAvailableCount,
        fillResponseEventInternal.mSaveUiTriggerIds,
        fillResponseEventInternal.mLatencyFillResponseReceivedMillis,
        fillResponseEventInternal.mAuthenticationType,
        fillResponseEventInternal.mAuthenticationResult,
        fillResponseEventInternal.mAuthenticationFailureReason,
        fillResponseEventInternal.mLatencyAuthenticationUiDisplayMillis,
        fillResponseEventInternal.mLatencyDatasetDisplayMillis,
        fillResponseEventInternal.mResponseStatus,
        fillResponseEventInternal.mLatencyResponseProcessingMillis,
        fillResponseEventInternal.mAvailablePccCount,
        fillResponseEventInternal.mAvailablePccOnlyCount,
        fillResponseEventInternal.mTotalDatasetsProvided,
        fillResponseEventInternal.mDetectionPref);
    this.mEventInternal = Optional.empty();
  }
}
