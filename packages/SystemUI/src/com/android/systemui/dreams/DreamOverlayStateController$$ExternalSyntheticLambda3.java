package com.android.systemui.dreams;

import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.DreamOverlayStateController;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                DreamOverlayStateController dreamOverlayStateController = (DreamOverlayStateController) obj2;
                dreamOverlayStateController.getClass();
                int requiredTypeAvailability = ((Complication) obj).getRequiredTypeAvailability();
                if (!dreamOverlayStateController.mShouldShowComplications) {
                    if ((dreamOverlayStateController.mSupportedTypes & dreamOverlayStateController.mAvailableComplicationTypes & requiredTypeAvailability) == requiredTypeAvailability) {
                    }
                } else if ((dreamOverlayStateController.mAvailableComplicationTypes & requiredTypeAvailability) == requiredTypeAvailability) {
                }
                break;
            default:
                if (((WeakReference) obj).get() == ((DreamOverlayStateController.Callback) obj2)) {
                }
                break;
        }
        return false;
    }
}
