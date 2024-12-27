package com.android.systemui.statusbar;

import java.util.function.Predicate;

public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IndicationEventType f$0;

    public /* synthetic */ KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0(IndicationEventType indicationEventType, int i) {
        this.$r8$classId = i;
        this.f$0 = indicationEventType;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        IndicationEventType indicationEventType = this.f$0;
        IndicationItem indicationItem = (IndicationItem) obj;
        switch (i) {
            case 0:
                if (indicationItem.mEventType != indicationEventType || !indicationItem.isPersistantEvent()) {
                }
                break;
            case 1:
                if (indicationItem.mEventType != indicationEventType || !indicationItem.isPersistantEvent()) {
                }
                break;
            default:
                if (indicationItem.mEventType == indicationEventType) {
                }
                break;
        }
        return false;
    }
}
