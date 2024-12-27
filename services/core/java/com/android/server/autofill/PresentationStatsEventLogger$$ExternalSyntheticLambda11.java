package com.android.server.autofill;

import android.service.autofill.Dataset;
import android.view.autofill.AutofillId;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda11
        implements Consumer {
    public final /* synthetic */ List f$0;
    public final /* synthetic */ AutofillId f$1;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda11(
            List list, AutofillId autofillId) {
        this.f$0 = list;
        this.f$1 = autofillId;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        int i2;
        int i3;
        List list = this.f$0;
        AutofillId autofillId = this.f$1;
        PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal =
                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
        if (list != null) {
            i = 0;
            i2 = 0;
            i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                Dataset dataset = (Dataset) list.get(i4);
                if (dataset != null
                        && dataset.getFieldIds() != null
                        && dataset.getFieldIds().contains(autofillId)) {
                    i++;
                    if (dataset.getEligibleReason() == 4) {
                        i3++;
                    } else if (dataset.getEligibleReason() != 5) {
                    }
                    i2++;
                }
            }
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        presentationStatsEventInternal.mAvailableCount = i;
        presentationStatsEventInternal.mAvailablePccCount = i2;
        presentationStatsEventInternal.mAvailablePccOnlyCount = i3;
        presentationStatsEventInternal.mIsDatasetAvailable = i > 0;
    }
}
