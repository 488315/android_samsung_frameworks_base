package com.android.server.autofill;

import android.util.ArraySet;
import android.util.Slog;
import android.view.autofill.AutofillId;
import com.android.server.autofill.PresentationStatsEventLogger;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                AutofillId autofillId = (AutofillId) obj2;
                PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal = (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
                presentationStatsEventInternal.mFocusedId = autofillId.getViewId();
                if (autofillId.isVirtualInt()) {
                    presentationStatsEventInternal.mFocusedVirtualAutofillId = autofillId.getVirtualChildIntId() % 100;
                    break;
                }
                break;
            case 1:
                AutofillId autofillId2 = (AutofillId) obj2;
                PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal2 = (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
                ArraySet arraySet = presentationStatsEventInternal2.mAutofillIdsAttemptedAutofill;
                if (arraySet != null) {
                    if (!arraySet.contains(autofillId2)) {
                        if (!presentationStatsEventInternal2.mAlreadyFilledAutofillIds.contains(autofillId2)) {
                            Slog.w("PresentationStatsEventLogger", "Successfully filled autofillId:" + autofillId2 + " not found in list of attempted autofill ids: " + arraySet);
                            presentationStatsEventInternal2.mViewFilledButUnexpectedCount = presentationStatsEventInternal2.mViewFilledButUnexpectedCount + 1;
                            break;
                        } else if (Helper.sVerbose) {
                            Slog.v("PresentationStatsEventLogger", "Successfully filled autofillId:" + autofillId2 + " already processed ");
                            break;
                        }
                    } else {
                        if (Helper.sVerbose) {
                            Slog.v("PresentationStatsEventLogger", "Logging autofill for id:" + autofillId2);
                        }
                        presentationStatsEventInternal2.mViewFillSuccessCount++;
                        arraySet.remove(autofillId2);
                        presentationStatsEventInternal2.mAlreadyFilledAutofillIds.add(autofillId2);
                        break;
                    }
                } else {
                    Slog.w("PresentationStatsEventLogger", "Attempted autofill ids is null, but received autofillId:" + autofillId2 + " successfully filled");
                    presentationStatsEventInternal2.mViewFilledButUnexpectedCount = presentationStatsEventInternal2.mViewFilledButUnexpectedCount + 1;
                    break;
                }
                break;
            default:
                PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal3 = (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
                ArraySet arraySet2 = new ArraySet((List) obj2);
                presentationStatsEventInternal3.mAutofillIdsAttemptedAutofill = arraySet2;
                presentationStatsEventInternal3.mViewFillableTotalCount = arraySet2.size();
                break;
        }
    }
}
