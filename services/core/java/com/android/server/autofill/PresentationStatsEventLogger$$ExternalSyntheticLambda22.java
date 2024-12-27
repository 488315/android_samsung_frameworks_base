package com.android.server.autofill;

import android.os.SystemClock;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda22
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PresentationStatsEventLogger f$0;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda22(
            PresentationStatsEventLogger presentationStatsEventLogger, int i) {
        this.$r8$classId = i;
        this.f$0 = presentationStatsEventLogger;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PresentationStatsEventLogger presentationStatsEventLogger = this.f$0;
        PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal =
                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
        switch (i) {
            case 0:
                presentationStatsEventLogger.getClass();
                presentationStatsEventInternal.mSelectionTimestamp =
                        (int)
                                (SystemClock.elapsedRealtime()
                                        - presentationStatsEventLogger.mSessionStartTimestamp);
                break;
            default:
                presentationStatsEventLogger.getClass();
                if (presentationStatsEventInternal.shouldResetShownCount) {
                    presentationStatsEventInternal.shouldResetShownCount = false;
                    presentationStatsEventInternal.mCountShown = 0;
                }
                if (presentationStatsEventInternal.mCountShown == 0) {
                    presentationStatsEventLogger.mEventInternal.ifPresent(
                            new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                                    (int)
                                            (SystemClock.elapsedRealtime()
                                                    - presentationStatsEventLogger
                                                            .mSessionStartTimestamp),
                                    9));
                }
                presentationStatsEventInternal.mCountShown++;
                break;
        }
    }
}
