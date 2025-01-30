package com.android.server.autofill;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class SessionCommittedEventLogger {
  public Optional mEventInternal = Optional.of(new SessionCommittedEventInternal());
  public final int mSessionId;

  public final class SessionCommittedEventInternal {
    public int mComponentPackageUid = -1;
    public int mRequestCount = 0;
    public int mCommitReason = 0;
    public long mSessionDurationMillis = 0;
  }

  public SessionCommittedEventLogger(int i) {
    this.mSessionId = i;
  }

  public static SessionCommittedEventLogger forSessionId(int i) {
    return new SessionCommittedEventLogger(i);
  }

  public void maybeSetComponentPackageUid(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mComponentPackageUid =
                i;
          }
        });
  }

  public void maybeSetRequestCount(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mRequestCount = i;
          }
        });
  }

  public void maybeSetCommitReason(final int i) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mCommitReason = i;
          }
        });
  }

  public void maybeSetSessionDurationMillis(final long j) {
    this.mEventInternal.ifPresent(
        new Consumer() { // from class:
          // com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((SessionCommittedEventLogger.SessionCommittedEventInternal) obj)
                    .mSessionDurationMillis =
                j;
          }
        });
  }

  public void logAndEndEvent() {
    if (!this.mEventInternal.isPresent()) {
      Slog.w(
          "SessionCommittedEventLogger",
          "Shouldn't be logging AutofillSessionCommitted again for same session.");
      return;
    }
    SessionCommittedEventInternal sessionCommittedEventInternal =
        (SessionCommittedEventInternal) this.mEventInternal.get();
    if (Helper.sVerbose) {
      Slog.v(
          "SessionCommittedEventLogger",
          "Log AutofillSessionCommitted: sessionId="
              + this.mSessionId
              + " mComponentPackageUid="
              + sessionCommittedEventInternal.mComponentPackageUid
              + " mRequestCount="
              + sessionCommittedEventInternal.mRequestCount
              + " mCommitReason="
              + sessionCommittedEventInternal.mCommitReason
              + " mSessionDurationMillis="
              + sessionCommittedEventInternal.mSessionDurationMillis);
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.AUTOFILL_SESSION_COMMITTED,
        this.mSessionId,
        sessionCommittedEventInternal.mComponentPackageUid,
        sessionCommittedEventInternal.mRequestCount,
        sessionCommittedEventInternal.mCommitReason,
        sessionCommittedEventInternal.mSessionDurationMillis);
    this.mEventInternal = Optional.empty();
  }
}
