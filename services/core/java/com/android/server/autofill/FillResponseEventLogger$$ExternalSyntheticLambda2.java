package com.android.server.autofill;

import android.os.SystemClock;
import android.service.autofill.Dataset;
import android.util.Slog;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FillResponseEventLogger$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FillResponseEventLogger$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        int i2;
        int i3 = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i3) {
            case 0:
                List list = (List) obj2;
                FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal =
                        (FillResponseEventLogger.FillResponseEventInternal) obj;
                int i4 = 0;
                if (list != null) {
                    i = list.size();
                    int i5 = 0;
                    i2 = 0;
                    while (i4 < list.size()) {
                        Dataset dataset = (Dataset) list.get(i4);
                        if (dataset != null) {
                            if (dataset.getEligibleReason() == 4) {
                                i5++;
                            } else if (dataset.getEligibleReason() != 5) {
                            }
                            i2++;
                        }
                        i4++;
                    }
                    i4 = i5;
                } else {
                    i = 0;
                    i2 = 0;
                }
                fillResponseEventInternal.mAvailablePccOnlyCount = i4;
                fillResponseEventInternal.mAvailablePccCount = i2;
                fillResponseEventInternal.mAvailableCount = i;
                break;
            default:
                FillResponseEventLogger fillResponseEventLogger = (FillResponseEventLogger) obj2;
                FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal2 =
                        (FillResponseEventLogger.FillResponseEventInternal) obj;
                if (fillResponseEventLogger.startResponseProcessingTimestamp == -1
                        && Helper.sVerbose) {
                    Slog.v(
                            "FillResponseEventLogger",
                            "uninitialized startResponseProcessingTimestamp");
                }
                fillResponseEventInternal2.mLatencyResponseProcessingMillis =
                        SystemClock.elapsedRealtime()
                                - fillResponseEventLogger.startResponseProcessingTimestamp;
                break;
        }
    }
}
