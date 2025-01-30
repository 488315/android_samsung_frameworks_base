package com.android.server.autofill;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class FieldClassificationEventLogger {
  public Optional mEventInternal = Optional.empty();

  public final class FieldClassificationEventInternal {
    public long mLatencyClassificationRequestMillis = -1;
  }

  public static FieldClassificationEventLogger createLogger() {
    return new FieldClassificationEventLogger();
  }

  public void startNewLogForRequest() {
    if (!this.mEventInternal.isEmpty()) {
      Slog.w(
          "FieldClassificationEventLogger",
          "FieldClassificationEventLogger is not empty before starting for a new request");
    }
    this.mEventInternal = Optional.of(new FieldClassificationEventInternal());
  }

  public void maybeSetLatencyMillis(final long j) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((FieldClassificationEventLogger.FieldClassificationEventInternal) obj)
                    .mLatencyClassificationRequestMillis =
                j;
          }
        });
  }

  public void logAndEndEvent() {
    if (!this.mEventInternal.isPresent()) {
      Slog.w(
          "FieldClassificationEventLogger",
          "Shouldn't be logging AutofillFieldClassificationEventInternal again for same event");
      return;
    }
    FieldClassificationEventInternal fieldClassificationEventInternal =
        (FieldClassificationEventInternal) this.mEventInternal.get();
    if (Helper.sVerbose) {
      Slog.v(
          "FieldClassificationEventLogger",
          "Log AutofillFieldClassificationEventReported: mLatencyClassificationRequestMillis="
              + fieldClassificationEventInternal.mLatencyClassificationRequestMillis);
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.AUTOFILL_FIELD_CLASSIFICATION_EVENT_REPORTED,
        fieldClassificationEventInternal.mLatencyClassificationRequestMillis);
    this.mEventInternal = Optional.empty();
  }
}
